/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.GlobalProperty;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.LocationAttributeType;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.FacilityMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.scheduler.tasks.AbstractTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;

/**
 * A scheduled task that automatically updates the facility status.
 */
public class FacilityStatusTask extends AbstractTask {

    private static final Logger log = LoggerFactory.getLogger(FacilityStatusTask.class);
    private static final AdministrationService administrationService = Context.getAdministrationService();
    private static final LocationService locationService = Context.getLocationService();

    private static final Integer LOCATION_ID = Integer.parseInt(administrationService.getGlobalProperty("kenyaemr.defaultLocation"));
    private static final Location LOCATION = locationService.getLocation(LOCATION_ID);
    private static final String BASE_URL_KEY = CommonMetadata.GP_SHA_FACILITY_VERIFICATION_GET_END_POINT;
    private static final String API_USER_KEY = CommonMetadata.GP_SHA_FACILITY_VERIFICATION_GET_API_USER;
    private static final String API_SECRET_KEY = CommonMetadata.GP_SHA_FACILITY_VERIFICATION_GET_API_SECRET;
    private static final String DEFAULT_BASE_URL = "https://sandbox.tiberbu.health/api/v4";
    private static final int TIMEOUT = 10000;
    private static final String MFL_CODE = "15204"; //LOCATION != null ? new Facility(LOCATION).getMflCode() : "Unknown";

    @Override
    public void execute() {
        if (!isExecuting) {
            log.debug("Starting Facility Status Task...");
            startExecuting();

            try {
                saveFacilityStatus();
            } catch (Exception e) {
                log.error("Error during facility status update", e);
            } finally {
                stopExecuting();
            }
        }
    }

    public static ResponseEntity<String> getFacilityStatus() {
        String baseURL = getBaseUrl().trim();
        String auth = getAuthCredentials();
        String operationalStatus = "";

        try {
            HttpsURLConnection connection = createConnection(new URL(baseURL + "/Organization?facility-code=" + MFL_CODE), auth);
            String response = getResponse(connection);

            // Log the raw response to diagnose the issue
            System.out.println("Raw response: {}"+ response);

            operationalStatus = extractOperationalStatus(response);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(createSuccessResponse(operationalStatus));

        } catch (Exception ex) {
            log.error("Error fetching facility status: {}", ex.getMessage());
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
        }
    }

    private static String getBaseUrl() {
        GlobalProperty globalGetUrl = administrationService.getGlobalPropertyObject(BASE_URL_KEY);
        return globalGetUrl.getPropertyValue() != null ? globalGetUrl.getPropertyValue().trim() : DEFAULT_BASE_URL.trim();
    }

    private static String getAuthCredentials() {
        String username = administrationService.getGlobalPropertyObject(API_USER_KEY).getPropertyValue().trim();
        String password = administrationService.getGlobalPropertyObject(API_SECRET_KEY).getPropertyValue().trim();
        return Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    private static HttpsURLConnection createConnection(URL url, String encodedAuth) throws Exception {
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
        //connection.setRequestProperty("Accept", "application/json; charset=utf-8");
        // Log the request details
        log.error("Request URL: {}", url);
        log.error("Authorization Header: {}", "Basic " + encodedAuth);
        //log.error("Accept Header: application/json; charset=utf-8");

        connection.setConnectTimeout(TIMEOUT);
        return connection;
    }

    private static String getResponse(HttpsURLConnection connection) throws Exception {
        System.out.println("*******We are inside getResponse");
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (Exception ex) {
            System.err.println("We just got an exception here: {}" + ex.getMessage());
        }
        System.out.println("-------Response--- inside getResponse----" + response);
        return response.toString();
    }

    private static String extractOperationalStatus(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray extensions = jsonResponse.getJSONArray("extension");
            for (int i = 0; i < extensions.length(); i++) {
                JSONObject extension = extensions.getJSONObject(i);
                if ("https://shr.tiberbuapps.com/fhir/StructureDefinition/operational-status".equals(extension.getString("url"))) {
                    return extension.getJSONObject("valueCoding").getString("display");
                }
            }
        } catch (JSONException e) {
            log.error("Failed to parse JSON response: {}", e.getMessage());
            // Return a specific error message or handle it appropriately
            return "Invalid JSON Response";
        }
        return "Unknown";
    }

    private static String createSuccessResponse(String operationalStatus) {
        return "{\"operationalStatus\": \"" + operationalStatus + "\"}";
    }

    public static void saveFacilityStatus() {

        ResponseEntity<String> responseEntity = getFacilityStatus();
        System.out.println("+===========ResponseEntity======" + responseEntity.getBody());
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();
            JSONObject jsonResponse = new JSONObject(responseBody);
            String operationalStatus = jsonResponse.getString("operationalStatus");

            LocationAttribute locationAttribute = new LocationAttribute();
            locationAttribute.setAttributeType(MetadataUtils.existing(LocationAttributeType.class, FacilityMetadata._LocationAttributeType.SHA_ACCREDITATION));
            locationAttribute.setValue(operationalStatus);
            LOCATION.setAttribute(locationAttribute);

            locationService.saveLocation(LOCATION);
            log.info("Facility status for MFL Code {} saved successfully: {}", MFL_CODE, operationalStatus);
        } else {
            log.error("Failed to get facility status for MFL Code {}: {}", MFL_CODE, responseEntity.getBody());
        }
    }
}