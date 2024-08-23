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

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
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
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.FacilityMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.scheduler.tasks.AbstractTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * A scheduled task that automatically updates the facility status.
 */
public class FacilityStatusTask extends AbstractTask {
    private static final Logger log = LoggerFactory.getLogger(FacilityStatusTask.class);
    private static final AdministrationService administrationService = Context.getAdministrationService();
    private static final LocationService locationService = Context.getLocationService();
    private static final Integer LOCATION_ID = Integer.parseInt(administrationService.getGlobalProperty("kenyaemr.defaultLocation"));
    private static final String BASE_URL_KEY = CommonMetadata.GP_SHA_FACILITY_VERIFICATION_GET_END_POINT;
    private static final String API_USER_KEY = CommonMetadata.GP_SHA_FACILITY_VERIFICATION_GET_API_USER;
    private static final String API_SECRET_KEY = CommonMetadata.GP_SHA_FACILITY_VERIFICATION_GET_API_SECRET;
    private static final String DEFAULT_BASE_URL = "https://sandbox.tiberbu.health/api/v4";
    private static final String MFL_CODE = Context.getService(KenyaEmrService.class).getDefaultLocationMflCode();

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
        String auth = getAuthCredentials();
        if (auth.isEmpty()) {
            log.error("Configure authentication credentials");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
        }

        try {
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSslConnectionFactory()).build();
            HttpGet getRequest = new HttpGet(getBaseUrl() + "/Organization?facility-code=" + MFL_CODE);
            getRequest.setHeader("Authorization", "Basic " + auth);

            HttpResponse response = httpClient.execute(getRequest);

            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(createSuccessResponse(response));
            } else {
                log.error("Error: failed to connect: {}", responseCode);
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
            }
        } catch (Exception ex) {
            log.error("Error fetching facility status: {}", ex.getMessage());
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

    private static String getBaseUrl() {
        GlobalProperty globalGetUrl = administrationService.getGlobalPropertyObject(BASE_URL_KEY);
        return globalGetUrl.getPropertyValue() != null ? globalGetUrl.getPropertyValue().trim() : DEFAULT_BASE_URL.trim();
    }

    private static String getAuthCredentials() {
        String username = administrationService.getGlobalPropertyObject(API_USER_KEY).getPropertyValue();
        String password = administrationService.getGlobalPropertyObject(API_SECRET_KEY).getPropertyValue();
        return Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    private static Map<String, String> extractFacilityStatus(String response) {
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("operationalStatus", "Unknown");
        statusMap.put("approved", "Unknown");

        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray extensions = jsonResponse.optJSONArray("extension");
            if (extensions != null) {
                for (int i = 0; i < extensions.length(); i++) {
                    JSONObject extension = extensions.optJSONObject(i);
                    if (extension != null) {
                        String url = extension.getString("url");
                        if ("https://shr.tiberbuapps.com/fhir/StructureDefinition/operational-status".equals(url)) {
                            statusMap.put("operationalStatus", extension.getJSONObject("valueCoding").getString("display"));
                        } else if ("https://shr.tiberbuapps.com/fhir/StructureDefinition/approved".equals(url)) {
                            statusMap.put("approved", extension.getJSONObject("valueCoding").getString("display"));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            logDiagnostics(response);
        }
        return statusMap;
    }

    private static void logDiagnostics(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray issueArray = jsonObject.optJSONArray("issue");

            if (issueArray != null && !issueArray.isEmpty()) {
                String diagnostics = issueArray.getJSONObject(0).optString("diagnostics", "mlf code not found");
                log.error("Diagnostics: {}", diagnostics);
            }
        } catch (JSONException e) {
            log.error("Failed to parse JSON diagnostics: {}", e.getMessage());
        }
    }

    private static String createSuccessResponse(HttpResponse response) {
        try {
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("Error parsing response", e);
        }
    }

    public void saveFacilityStatus() {
        try {
            ResponseEntity<String> responseEntity = getFacilityStatus();
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                Map<String, String> facilityStatus = extractFacilityStatus(responseEntity.getBody());

                String operationalStatus = facilityStatus.getOrDefault("operationalStatus", "Unknown");
                String approved = facilityStatus.getOrDefault("approved", "Unknown");

                final Location LOCATION = locationService.getLocation(LOCATION_ID);

                // Handle SHA Accreditation Attribute
                handleSHAAccreditationAttribute(LOCATION, operationalStatus);

                // Handle SHA Facility Attribute
                handleSHAFacilityAttribute(LOCATION, approved);

            } else {
                log.error("Failed to save facility status: {}", responseEntity.getBody());
            }
        } catch (Exception e) {
            log.error("Error in saving Facility Status: ", e);
            throw e;
        }
    }

    public void handleSHAAccreditationAttribute(Location LOCATION, String operationalStatus) {
        LocationAttributeType shaAccreditationType = MetadataUtils.existing(LocationAttributeType.class, FacilityMetadata._LocationAttributeType.SHA_ACCREDITATION);

        LocationAttribute shaAccreditationAttribute = LOCATION.getActiveAttributes(shaAccreditationType)
                .stream()
                .filter(attr -> attr.getAttributeType().equals(shaAccreditationType))
                .findFirst()
                .orElse(null);

        if (shaAccreditationAttribute == null) {
            shaAccreditationAttribute = new LocationAttribute();
            shaAccreditationAttribute.setAttributeType(shaAccreditationType);
            shaAccreditationAttribute.setValue(operationalStatus);
            LOCATION.addAttribute(shaAccreditationAttribute);
            log.info("New SHA Accreditation attribute created and set: {}", operationalStatus);
        } else {
            if (!operationalStatus.equals(shaAccreditationAttribute.getValue())) {
                shaAccreditationAttribute.setValue(operationalStatus);
                log.info("SHA Accreditation attribute updated to new value: {}", operationalStatus);
            } else {
                log.info("No update needed. SHA Accreditation attribute value is the same: {}", operationalStatus);
            }
        }
        locationService.saveLocation(LOCATION);
        log.info("Facility status for MFL Code {} saved successfully: Operational Status: {}", MFL_CODE, operationalStatus);
    }

    public void handleSHAFacilityAttribute(Location LOCATION, String approved) {
        LocationAttributeType isSHAFacility = MetadataUtils.existing(LocationAttributeType.class, FacilityMetadata._LocationAttributeType.SHA_CONTRACTED_FACILITY);

        LocationAttribute isSHAFacilityAttribute = LOCATION.getActiveAttributes(isSHAFacility)
                .stream()
                .filter(attr -> attr.getAttributeType().equals(isSHAFacility))
                .findFirst()
                .orElse(null);

        if (isSHAFacilityAttribute == null) {
            isSHAFacilityAttribute = new LocationAttribute();
            isSHAFacilityAttribute.setAttributeType(isSHAFacility);
            isSHAFacilityAttribute.setValue(approved);
            LOCATION.addAttribute(isSHAFacilityAttribute);
            log.info("New SHA Facility attribute created and set: {}", approved);
        } else {
            if (!approved.equals(isSHAFacilityAttribute.getValue())) {
                isSHAFacilityAttribute.setValue(approved);
                log.info("SHA Facility attribute updated to new value: {}", approved);
            } else {
                log.info("No update needed. SHA Facility attribute value is the same: {}", approved);
            }
        }
        locationService.saveLocation(LOCATION);
        log.info("Facility status for MFL Code {} saved successfully: , Approved: {}", MFL_CODE, approved);
    }
}