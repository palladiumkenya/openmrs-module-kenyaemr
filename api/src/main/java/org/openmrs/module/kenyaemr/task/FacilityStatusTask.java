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
    private static final String GP_MFL_CODE = administrationService.getGlobalProperty("facility.mflcode").trim();
    private static final String BASE_URL_KEY = CommonMetadata.GP_SHA_FACILITY_VERIFICATION_GET_END_POINT;
    private static final String API_USER_KEY = CommonMetadata.GP_SHA_FACILITY_VERIFICATION_GET_API_USER;
    private static final String API_SECRET_KEY = CommonMetadata.GP_SHA_FACILITY_VERIFICATION_GET_API_SECRET;
    private static final String DEFAULT_BASE_URL = "https://api.dha.go.ke/v1/fhir";
    private static final String DEFAULT_MFL_CODE = Context.getService(KenyaEmrService.class).getDefaultLocationMflCode().trim();

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
        String bearerToken = getBearerToken();

        if (bearerToken.isEmpty()) {
            log.error("Bearer token is missing");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
        }

        try {
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSslConnectionFactory()).build();
            HttpGet getRequest = new HttpGet(getBaseUrl() + "/Organization?facility-code=" + getMFLCode());
            getRequest.setHeader("Authorization", "Bearer " + bearerToken);

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
    private static String getMFLCode() {
        return  GP_MFL_CODE != null ? GP_MFL_CODE : DEFAULT_MFL_CODE;
    }

    private static String getBearerToken() {
        String tokenUrl = administrationService.getGlobalProperty(API_TOKEN_ENDPOINT);
        String username = administrationService.getGlobalPropertyObject(API_USER_KEY).getPropertyValue();
        String password = administrationService.getGlobalPropertyObject(API_SECRET_KEY).getPropertyValue();
        return Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    private static Map<String, String> extractFacilityStatus(String organizationResourceResponse) {
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("operationalStatus", "--");
        statusMap.put("kephLevel", "--");
        statusMap.put("approved", "--");
        statusMap.put("shaFacilityExpiryDate", "--");
        statusMap.put("shaFacilityReferencePayload", "--");

        try {

            JSONObject jsonResponse = new JSONObject(organizationResourceResponse);
            log.info("JSON Response: {}", jsonResponse.toString(2));

            statusMap.put("shaFacilityReferencePayload", organizationResourceResponse);

            JSONArray extensions = jsonResponse.optJSONArray("extension");
            if (extensions != null) {
                for (int i = 0; i < extensions.length(); i++) {

                    JSONObject extension = extensions.optJSONObject(i);
                        String url = extension.optString("url");
                        log.debug("Processing extension URL: {}", url);

                        switch (url) {
                            case "https://fr-kenyahie/StructureDefinition/license-status":
                                statusMap.put("operationalStatus", extension.optString("valueString"));
                                break;

                            case "https://fr-kenyahie/StructureDefinition/keph-level":
                            statusMap.put("kephLevel", extension.optJSONObject("valueCodeableConcept").optJSONArray("coding").optJSONObject(0).optString("display"));
                            break;
                            //TODO: Get url for SHA accreditation.
                            case "https://shr.tiberbuapps.com/fhir/StructureDefinition/approved":
                                statusMap.put("approved", extension.getJSONObject("valueCoding").optString("display", "--"));
                                break;
                        }
                }
            }
            JSONArray identifiers = jsonResponse.optJSONArray("identifier");
            if (identifiers != null) {
                for (int i = 0; i < identifiers.length(); i++) {
                    JSONObject identifier = identifiers.getJSONObject(i);
                        statusMap.put("shaFacilityExpiryDate", identifier.optJSONObject("period").optString("end"));
                    }
                }
            return statusMap;

        } catch (JSONException e) {
            log.error("Error parsing facility status JSON: {}", e.getMessage());
            logDiagnostics(organizationResourceResponse);
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

                String operationalStatus = facilityStatus.getOrDefault("operationalStatus", "--");
                String kephLevel = facilityStatus.getOrDefault("kephLevel", "--");
                String approved = facilityStatus.getOrDefault("approved", "--");
                String shaFacilityExpiryDate = facilityStatus.getOrDefault("shaFacilityExpiryDate", "--");
                String shaFacilityReferencePayload = facilityStatus.getOrDefault("shaFacilityReferencePayload", "--");
                
                final Location location = locationService.getLocation(LOCATION_ID);

                // Handle SHA Accreditation Attribute
                handleSHAAccreditationAttribute(location, operationalStatus);

                // Handle KMPDC facility classification Attribute
                handleKephLevelAttribute(location, kephLevel);

                // Handle SHA Facility Attribute
                handleSHAFacilityAttribute(location, approved);
                
                //Handle SHA facility expiry date
                handleSHAFacilityLicenseExpiryDateAttribute(location, shaFacilityExpiryDate);

                // Handle SHA Facility reference payload attribute
                handleFacilityReferencePayloadAttribute(location, shaFacilityReferencePayload);
            } else {
                log.error("Failed to save facility status: {}", responseEntity.getBody());
            }
        } catch (Exception e) {
            log.error("Error in saving Facility Status: ", e);
            throw e;
        }
    }

    private void handleFacilityReferencePayloadAttribute(Location location, String shaFacilityReferencePayload) {
        LocationAttributeType shaFacilityReferencePayloadType = MetadataUtils.existing(LocationAttributeType.class, FacilityMetadata._LocationAttributeType.HIE_FACILITY_REFERENCE_PAYLOAD);

        LocationAttribute shaFacilityReferencePayloadAttribute = location.getActiveAttributes(shaFacilityReferencePayloadType)
                .stream()
                .filter(attr -> attr.getAttributeType().equals(shaFacilityReferencePayloadType))
                .findFirst()
                .orElse(null);

        if (shaFacilityReferencePayloadAttribute == null) {
            shaFacilityReferencePayloadAttribute = new LocationAttribute();
            shaFacilityReferencePayloadAttribute.setAttributeType(shaFacilityReferencePayloadType);
            shaFacilityReferencePayloadAttribute.setValue(shaFacilityReferencePayload);
            location.addAttribute(shaFacilityReferencePayloadAttribute);
            log.info("New Facility reference payload attribute created: {}", shaFacilityReferencePayload);
        } else {
            if (!shaFacilityReferencePayload.equals(shaFacilityReferencePayloadAttribute.getValue())) {
                shaFacilityReferencePayloadAttribute.setValue(shaFacilityReferencePayload);
                log.info("SHA Facility reference payload attribute updated to new value: {}", shaFacilityReferencePayload);
            } else {
                log.info("No update needed. Facility reference payload attribute value is the same: {}", shaFacilityReferencePayload);
            }
        }
        locationService.saveLocation(location);
        log.info("Facility status for MFL Code {} saved successfully: Facility reference payload: {}", getMFLCode(), shaFacilityReferencePayload);
    }

    private void handleKephLevelAttribute(Location location, String kephLevel) {
        LocationAttributeType shaKephLevelType = MetadataUtils.existing(LocationAttributeType.class, FacilityMetadata._LocationAttributeType.KMPDC_CLASSFICATION);

        LocationAttribute shaKephLevelAttribute = location.getActiveAttributes(shaKephLevelType)
                .stream()
                .filter(attr -> attr.getAttributeType().equals(shaKephLevelType))
                .findFirst()
                .orElse(null);

        if (shaKephLevelAttribute == null) {
            shaKephLevelAttribute = new LocationAttribute();
            shaKephLevelAttribute.setAttributeType(shaKephLevelType);
            shaKephLevelAttribute.setValue(kephLevel);
            location.addAttribute(shaKephLevelAttribute);
            log.info("New Keph Level attribute created: {}", kephLevel);
        } else {
            if (!kephLevel.equals(shaKephLevelAttribute.getValue())) {
                shaKephLevelAttribute.setValue(kephLevel);
                log.info("SHA Keph Level attribute updated to new value: {}", kephLevel);
            } else {
                log.info("No update needed. Keph Level value is the same: {}", kephLevel);
            }
        }
        locationService.saveLocation(location);
        log.info("Keph Level for MFL Code {} saved successfully: Keph Level: {}", getMFLCode(), kephLevel);
    }

    public void handleSHAAccreditationAttribute(Location location, String operationalStatus) {
        LocationAttributeType shaAccreditationType = MetadataUtils.existing(LocationAttributeType.class, FacilityMetadata._LocationAttributeType.SHA_ACCREDITATION);

        LocationAttribute shaAccreditationAttribute = location.getActiveAttributes(shaAccreditationType)
                .stream()
                .filter(attr -> attr.getAttributeType().equals(shaAccreditationType))
                .findFirst()
                .orElse(null);

        if (shaAccreditationAttribute == null) {
            shaAccreditationAttribute = new LocationAttribute();
            shaAccreditationAttribute.setAttributeType(shaAccreditationType);
            shaAccreditationAttribute.setValue(operationalStatus);
            location.addAttribute(shaAccreditationAttribute);
            log.info("New SHA Accreditation attribute created and set: {}", operationalStatus);
        } else {
            if (!operationalStatus.equals(shaAccreditationAttribute.getValue())) {
                shaAccreditationAttribute.setValue(operationalStatus);
                log.info("SHA Accreditation attribute updated to new value: {}", operationalStatus);
            } else {
                log.info("No update needed. SHA Accreditation attribute value is the same: {}", operationalStatus);
            }
        }
        locationService.saveLocation(location);
        log.info("Facility status for MFL Code {} saved successfully: Operational Status: {}", getMFLCode(), operationalStatus);
    }

    public void handleSHAFacilityAttribute(Location location, String approved) {
        LocationAttributeType isSHAFacility = MetadataUtils.existing(LocationAttributeType.class, FacilityMetadata._LocationAttributeType.SHA_CONTRACTED_FACILITY);

        LocationAttribute isSHAFacilityAttribute = location.getActiveAttributes(isSHAFacility)
                .stream()
                .filter(attr -> attr.getAttributeType().equals(isSHAFacility))
                .findFirst()
                .orElse(null);

        if (isSHAFacilityAttribute == null) {
            isSHAFacilityAttribute = new LocationAttribute();
            isSHAFacilityAttribute.setAttributeType(isSHAFacility);
            isSHAFacilityAttribute.setValue(approved);
            location.addAttribute(isSHAFacilityAttribute);
            log.info("New SHA Facility attribute created and set: {}", approved);
        } else {
            if (!approved.equals(isSHAFacilityAttribute.getValue())) {
                isSHAFacilityAttribute.setValue(approved);
                log.info("SHA Facility attribute updated to new value: {}", approved);
            } else {
                log.info("No update needed. SHA Facility attribute value is the same: {}", approved);
            }
        }
        locationService.saveLocation(location);
        log.info("Facility status for MFL Code {} saved successfully: , Approved: {}", getMFLCode(), approved);
    }

    public void handleSHAFacilityLicenseExpiryDateAttribute(Location location, String facilityExpiryDate) {
        LocationAttributeType facilityExpiryDateAttributeType = MetadataUtils.existing(LocationAttributeType.class, FacilityMetadata._LocationAttributeType.SHA_FACILITY_EXPIRY_DATE);

        LocationAttribute facilityExpiryDateAttribute = location.getActiveAttributes(facilityExpiryDateAttributeType)
                .stream()
                .filter(attr -> attr.getAttributeType().equals(facilityExpiryDateAttributeType))
                .findFirst()
                .orElse(null);

        if (facilityExpiryDateAttribute == null) {
            facilityExpiryDateAttribute = new LocationAttribute();
            facilityExpiryDateAttribute.setAttributeType(facilityExpiryDateAttributeType);
            facilityExpiryDateAttribute.setValue(facilityExpiryDate);
            location.addAttribute(facilityExpiryDateAttribute);
            log.info("SHA License expiry date attribute updated to new value: {}", facilityExpiryDate);
        } else {
            if (!facilityExpiryDate.equals(facilityExpiryDateAttribute.getValue())) {
                facilityExpiryDateAttribute.setValue(facilityExpiryDate);
                log.info("SHA Facility attribute updated to new value: {}", facilityExpiryDate);
            } else {
                log.info("No update needed.SHA Facility License expiry date attribute value is the same: {}", facilityExpiryDate);
            }
        }
        locationService.saveLocation(location);
        log.info("Facility SHA License expiry date for MFL Code {} saved successfully: , License expiry date: {}", getMFLCode(), facilityExpiryDate);
    }
}