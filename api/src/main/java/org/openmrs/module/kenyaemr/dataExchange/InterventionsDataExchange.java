/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.dataExchange;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.LocationAttributeType;
import org.openmrs.User;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.FacilityMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.openmrs.module.kenyaemr.util.EmrUtils.*;

/**
 * A scheduled task that automatically updates the facility status.
 */
public class InterventionsDataExchange {
    private static final Logger log = LoggerFactory.getLogger(InterventionsDataExchange.class);

    private static final LocationService locationService = Context.getLocationService();

    private static final String BASE_JWT_URL_KEY = CommonMetadata.GP_SHA_FACILITY_VERIFICATION_JWT_GET_END_POINT;
    private static final String BASE_URL_KEY = CommonMetadata.GP_HIE_BASE_END_POINT_URL;
    private static final String API_USER_KEY = CommonMetadata.GP_HIE_API_USER;
    private static final String API_SECRET_KEY = CommonMetadata.GP_SHA_FACILITY_VERIFICATION_GET_API_SECRET;

    public static ResponseEntity<String> getInterventions() {

        String bearerToken = getBearerToken();
        if (bearerToken.isEmpty()) {
            System.err.println("Bearer token is missing");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
        }

        try {
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSslConnectionFactory()).build();
            HttpGet getRequest = new HttpGet(getGlobalPropertyValue(BASE_URL_KEY) + "interventions");
            getRequest.setHeader("Authorization", "Bearer " + bearerToken);

            HttpResponse response = httpClient.execute(getRequest);

            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(createSuccessResponse(response));
            } else {
                System.err.println("Error: failed to connect: "+ responseCode);
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
            }
        } catch (Exception ex) {
            System.err.println("Error fetching interventions: "+ ex.getMessage());
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
        }
    }

    private static SSLConnectionSocketFactory createSslConnectionFactory() throws Exception {
        return new SSLConnectionSocketFactory(
                SSLContexts.createDefault(),
                new String[]{"TLSv1.2"},
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier()
        );
    }

    private static String getBearerToken() {
        String username = getGlobalPropertyValue(API_USER_KEY).trim();
        String secret = getGlobalPropertyValue(API_SECRET_KEY).trim();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet getRequest = new HttpGet(getGlobalPropertyValue(BASE_JWT_URL_KEY) + "hie-auth?key=O7B-DHABP00278");
            getRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
            getRequest.setHeader("Authorization", createBasicAuthHeader(username, secret));

            try (CloseableHttpResponse response = httpClient.execute(getRequest)) {

                if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
                    String responseString = EntityUtils.toString(response.getEntity()).trim();
                    log.info("Bearer token retrieved successfully...");
                    return responseString;
                } else {
                    System.err.println("Failed to fetch Bearer Token. HTTP Status:"+ response.getStatusLine().getStatusCode());
                }
            }
        } catch (Exception e) {
            System.err.println("Error retrieving Bearer Token: "+ e.getMessage());
        }
        return "";
    }

    private static String createBasicAuthHeader(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }

    private static Map<String, String> extractInterventions(String interventions) {
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("shaInterventions", "--");

        if(interventions != null) {
            statusMap.put("shaInterventions", interventions);
        }

        return statusMap;
    }

    private static String createSuccessResponse(HttpResponse response) {
        try {
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("Error parsing response", e);
        }
    }
    private static LocationAttribute getOrUpdateAttribute(Location location, LocationAttributeType type, String value, User creator) {
        // Check if the attribute already exists
        LocationAttribute existingAttribute = location.getActiveAttributes(type)
                .stream()
                .filter(attr -> attr.getAttributeType().equals(type))
                .findFirst()
                .orElse(null);

        if (existingAttribute == null) {
            // Create a new attribute if none exists
            LocationAttribute newAttribute = new LocationAttribute();
            newAttribute.setAttributeType(type);
            newAttribute.setValue(value);
            newAttribute.setCreator(creator);
            newAttribute.setDateCreated(new Date());
            location.addAttribute(newAttribute);
            return newAttribute;
        } else if (!existingAttribute.getValue().equals(value)) {
            // Update the value if it differs
            existingAttribute.setValue(value);
            return existingAttribute;
        }

        // No changes needed
        return null;
    }

    public static boolean saveInterventions() {
        try {
            ResponseEntity<String> responseEntity = getInterventions();
            String responseBody = responseEntity.getBody();

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseBody != null) {
                Map<String, String> interventions = extractInterventions(responseBody);

                if (interventions.get("shaInterventions").equals("--")) {
                    System.err.println("No valid interventions found in the response.");
                    return false;
                }

                Location location = getDefaultLocation();
                User authenticatedUser = Context.getAuthenticatedUser();

                if (authenticatedUser == null) {
                    throw new IllegalStateException("No authenticated user in context");
                }
                // Update or create attributes
                getOrUpdateAttribute(location, MetadataUtils.existing(LocationAttributeType.class, FacilityMetadata._LocationAttributeType.SHA_INTERVENTIONS), interventions.get("shaInterventions"), authenticatedUser);

                locationService.saveLocation(location);  // Persist changes
                return true;
            } else {
                System.err.println("Failed to save interventions: " + responseEntity.getBody());
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error in saving interventions: " + e);
            return false;
        }
    }
}