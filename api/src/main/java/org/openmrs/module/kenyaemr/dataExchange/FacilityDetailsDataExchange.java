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
import java.util.HashMap;
import java.util.Map;

import static org.openmrs.module.kenyaemr.util.EmrUtils.*;
import static org.openmrs.module.kenyaemr.util.EmrUtils.getGlobalPropertyValue;

/**
 * A scheduled task that automatically updates the facility status.
 */
public class FacilityDetailsDataExchange {
    private static final Logger log = LoggerFactory.getLogger(FacilityDetailsDataExchange.class);

    private static final LocationService locationService = Context.getLocationService();

    private static final String BASE_URL_KEY = CommonMetadata.GP_HIE_BASE_END_POINT_URL;
    private static final String API_USER_KEY = CommonMetadata.GP_HIE_API_USER;
    private static final String API_SECRET_KEY = CommonMetadata.GP_SHA_FACILITY_VERIFICATION_GET_API_SECRET;

    public static ResponseEntity<String> getFacilityStatus() {

        String bearerToken = getBearerToken();
        if (bearerToken.isEmpty()) {
            log.error("Bearer token is missing");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
        }

        try {
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSslConnectionFactory()).build();
            HttpGet getRequest = new HttpGet(getGlobalPropertyValue(BASE_URL_KEY) + "fhir/Organization?identifierType=mfl-code&identifier=" + getMFLCode());

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

    private static String getBearerToken() {
        String username = getGlobalPropertyValue(API_USER_KEY);
        String secret = getGlobalPropertyValue(API_SECRET_KEY);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet getRequest = new HttpGet(getGlobalPropertyValue(BASE_URL_KEY) + "hie-auth?key=" + username);
            getRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
            getRequest.setHeader("Authorization", createBasicAuthHeader(username, secret));

            try (CloseableHttpResponse response = httpClient.execute(getRequest)) {

                if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
                    String responseString = EntityUtils.toString(response.getEntity()).trim();
                    log.info("Bearer token retrieved successfully...");
                    return responseString;
                } else {
                    log.error("Failed to fetch Bearer Token. HTTP Status: {}", response.getStatusLine().getStatusCode());
                }
            }
        } catch (Exception e) {
            log.error("Error retrieving Bearer Token: {}", e.getMessage());
        }
        return "";
    }

    private static String createBasicAuthHeader(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }

    private static Map<String, String> extractFacilityStatus(String organizationResourceResponse) {
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("operationalStatus", "--");
        statusMap.put("kephLevel", "--");
        statusMap.put("approved", "--");
        statusMap.put("shaFacilityExpiryDate", "--");
        statusMap.put("shaFacilityId", "--");
        statusMap.put("shaFacilityLicenseNumber", "--");
        statusMap.put("shaFacilityReferencePayload", "--");

        try {
            JSONObject jsonResponse = new JSONObject(organizationResourceResponse);
            log.info("JSON Response: {}", jsonResponse.toString(2));

            statusMap.put("shaFacilityReferencePayload", organizationResourceResponse);

            JSONArray entries = jsonResponse.optJSONArray("entry");
            if (entries != null && !entries.isEmpty()) {
                JSONObject resource = entries.getJSONObject(0).optJSONObject("resource");
                if (resource != null && "Organization".equals(resource.optString("resourceType"))) {
                    // Extract "id"
                    String resourceId = resource.optString("id", "--");
                    statusMap.put("shaFacilityId", resourceId);

                    JSONArray extensions = resource.optJSONArray("extension");
                    if (extensions != null) {
                        for (int i = 0; i < extensions.length(); i++) {

                            JSONObject extension = extensions.optJSONObject(i);
                            String url = extension.optString("url");

                            switch (url) {
                                case "https://fr-kenyahie/StructureDefinition/license-status":
                                    statusMap.put("operationalStatus", extension.optString("valueString", "--"));
                                    break;

                                case "https://fr-kenyahie/StructureDefinition/keph-level":
                                    JSONObject valueCodeableConcept = extension.optJSONObject("valueCodeableConcept");
                                    if (valueCodeableConcept != null) {
                                        JSONArray coding = valueCodeableConcept.optJSONArray("coding");
                                        if (coding != null && !coding.isEmpty()) {
                                            statusMap.put("kephLevel", coding.getJSONObject(0).optString("display", "--"));
                                        }
                                    }
                                    break;
                                //TODO: Get url for SHA accreditation.
                                case "https://shr.tiberbuapps.com/fhir/StructureDefinition/approved":
                                    JSONObject valueCoding = extension.optJSONObject("valueCoding");
                                    if (valueCoding != null) {
                                        statusMap.put("approved", valueCoding.optString("display", "--"));
                                    }
                                    break;
                            }
                        }
                    }
                    JSONArray identifiers = resource.optJSONArray("identifier");
                    if (identifiers != null) {
                        for (int i = 0; i < identifiers.length(); i++) {
                            JSONObject identifier = identifiers.getJSONObject(i);

                            // Check for "license-number"
                            JSONArray codingArray = identifier.optJSONObject("type")
                                    .optJSONArray("coding");
                            if (codingArray != null) {
                                for (int j = 0; j < codingArray.length(); j++) {
                                    JSONObject coding = codingArray.getJSONObject(j);
                                    String code = coding.optString("code");

                                    if ("license-number".equals(code)) {
                                        statusMap.put("shaFacilityLicenseNumber", identifier.optString("value", "--"));
                                    }
                                }
                            }
                            // Check for "end" in "period"
                            JSONObject period = identifier.optJSONObject("period");
                            if (period != null) {
                                String end = period.optString("end", "--");
                                statusMap.put("shaFacilityExpiryDate", end);
                                break;
                            }
                        }
                    }
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

    public static void saveFacilityStatus() {
        try {
            ResponseEntity<String> responseEntity = getFacilityStatus();
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                Map<String, String> facilityStatus = extractFacilityStatus(responseEntity.getBody());

                String operationalStatus = facilityStatus.getOrDefault("operationalStatus", "--");
                String kephLevel = facilityStatus.getOrDefault("kephLevel", "--");
                String approved = facilityStatus.getOrDefault("approved", "--");
                String shaFacilityExpiryDate = facilityStatus.getOrDefault("shaFacilityExpiryDate", "--");
                String shaFacilityLicenseNumber = facilityStatus.getOrDefault("shaFacilityLicenseNumber", "--");
                String shaFacilityId = facilityStatus.getOrDefault("shaFacilityId", "--");
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

                //Handle SHA facility License number
                handleSHAFacilityLicenseNumberAttribute(location, shaFacilityLicenseNumber);

                //Handle SHA facility id
                handleSHAFacilityIdAttribute(location, shaFacilityId);

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

    private static void handleFacilityReferencePayloadAttribute(Location location, String shaFacilityReferencePayload) {
        LocationAttributeType shaFacilityReferencePayloadType = MetadataUtils.existing(LocationAttributeType.class, FacilityMetadata._LocationAttributeType.FACILITY_HIE_FHIR_REFERENCE);

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

    private static void handleKephLevelAttribute(Location location, String kephLevel) {
        LocationAttributeType shaKephLevelType = MetadataUtils.existing(LocationAttributeType.class, FacilityMetadata._LocationAttributeType.FACILITY_KEPH_LEVEL);

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

    public static void handleSHAAccreditationAttribute(Location location, String operationalStatus) {
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

    public static void handleSHAFacilityAttribute(Location location, String approved) {
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

    public static void handleSHAFacilityLicenseExpiryDateAttribute(Location location, String facilityExpiryDate) {
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

    public static void handleSHAFacilityIdAttribute(Location location, String facilityId) {
        LocationAttributeType facilityIdAttributeType = MetadataUtils.existing(LocationAttributeType.class, FacilityMetadata._LocationAttributeType.FACILITY_REGISTRY_CODE);

        LocationAttribute facilityIdAttribute = location.getActiveAttributes(facilityIdAttributeType)
                .stream()
                .filter(attr -> attr.getAttributeType().equals(facilityIdAttributeType))
                .findFirst()
                .orElse(null);

        if (facilityIdAttribute == null) {
            facilityIdAttribute = new LocationAttribute();
            facilityIdAttribute.setAttributeType(facilityIdAttributeType);
            facilityIdAttribute.setValue(facilityId);
            location.addAttribute(facilityIdAttribute);
            log.info("Facility Id attribute updated to new value: {}", facilityId);
        } else {
            if (!facilityId.equals(facilityIdAttribute.getValue())) {
                facilityIdAttribute.setValue(facilityId);
                log.info("Facility Id updated to new value: {}", facilityId);
            } else {
                log.info("No update needed. Facility Id is the same: {}", facilityId);
            }
        }
        locationService.saveLocation(location);
        log.info("Facility Id for MFL Code {} saved successfully: , Facility Id: {}", getMFLCode(), facilityId);
    }

    public static void handleSHAFacilityLicenseNumberAttribute(Location location, String facilityLicenseNumber) {
        LocationAttributeType facilityLicenseNumberAttributeType = MetadataUtils.existing(LocationAttributeType.class, FacilityMetadata._LocationAttributeType.FACILITY_LICENSE_NUMBER);

        LocationAttribute facilityLicenseNumberAttribute = location.getActiveAttributes(facilityLicenseNumberAttributeType)
                .stream()
                .filter(attr -> attr.getAttributeType().equals(facilityLicenseNumberAttributeType))
                .findFirst()
                .orElse(null);

        if (facilityLicenseNumberAttribute == null) {
            facilityLicenseNumberAttribute = new LocationAttribute();
            facilityLicenseNumberAttribute.setAttributeType(facilityLicenseNumberAttributeType);
            facilityLicenseNumberAttribute.setValue(facilityLicenseNumber);
            location.addAttribute(facilityLicenseNumberAttribute);
            log.info("License number attribute updated to new value: {}", facilityLicenseNumber);
        } else {
            if (!facilityLicenseNumber.equals(facilityLicenseNumberAttribute.getValue())) {
                facilityLicenseNumberAttribute.setValue(facilityLicenseNumber);
                log.info("Facility license number updated to new value: {}", facilityLicenseNumber);
            } else {
                log.info("No update needed.SHA Facility License number is the same: {}", facilityLicenseNumber);
            }
        }
        locationService.saveLocation(location);
        log.info("Facility License number for MFL Code {} saved successfully: , License number: {}", getMFLCode(), facilityLicenseNumber);
    }
}