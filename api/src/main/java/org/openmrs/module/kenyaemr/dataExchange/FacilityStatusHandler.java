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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonNode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import org.openmrs.util.OpenmrsUtil;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.openmrs.module.kenyaemr.util.EmrUtils.getGlobalPropertyValue;

public class FacilityStatusHandler extends DataHandler {
    private static String LOCAL_FILE_PATH = OpenmrsUtil.getApplicationDataDirectory() + "/sha/sha_facility_status.json";

    private static final Logger log = LoggerFactory.getLogger(FacilityStatusHandler.class);

    private static final String BASE_URL_KEY = CommonMetadata.GP_HIE_BASE_END_POINT_URL;
    private static final String FACILITY_REGISTRATION_NUMBER = CommonMetadata.GP_SHA_FACILITY_REGISTRATION_NUMBER;

    public FacilityStatusHandler() {
        super(LOCAL_FILE_PATH);
    }

    @Override
    protected JsonNode fetchRemoteData() {
        try {
            String response = extractFacilityStatus().getBody();
            if (response != null) {
                JsonNode jsonNode = objectMapper.readTree(response);
                return jsonNode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseEntity<String> getFacilityStatus() throws IOException {

        String bearerToken = DataHandler.getBearerToken();
        if (bearerToken.isEmpty()) {
            System.err.println("Bearer token is missing");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
        }

        String faciltyRegistrationNumber = getGlobalPropertyValue(FACILITY_REGISTRATION_NUMBER).trim();
        if (faciltyRegistrationNumber.trim().isEmpty()) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\", \"message\": \"Facility registration number is missing\"}");
        }

        try {
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSslConnectionFactory()).build();
            HttpGet getRequest = new HttpGet(getGlobalPropertyValue(BASE_URL_KEY) + "fhir/Organization?identifierType=registration-number&identifier=" + faciltyRegistrationNumber);
            getRequest.setHeader("Authorization", "Bearer " + bearerToken);

            HttpResponse response = httpClient.execute(getRequest);

            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(createSuccessResponse(response));
            } else {
                System.err.println("Error: failed to connect: " + responseCode);
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
            }
        } catch (Exception ex) {
            System.err.println("Error fetching facility status: " + ex.getMessage());
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
        }
    }

    private static ResponseEntity<String> extractFacilityStatus() throws IOException {
        ResponseEntity<String> organizationResourceResponse = getFacilityStatus();
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("operationalStatus", "--");
        statusMap.put("kephLevel", "--");
        statusMap.put("approved", "--");
        statusMap.put("shaFacilityExpiryDate", "--");
        statusMap.put("shaFacilityId", "--");
        statusMap.put("shaFacilityLicenseNumber", "--");
        statusMap.put("mflCode", "--");
        statusMap.put("facilityRegistryCode", "--");
        statusMap.put("registrationNumber", "--");

        try {
            JSONObject jsonResponse = new JSONObject(Objects.requireNonNull(organizationResourceResponse.getBody()));

            JSONArray entries = jsonResponse.optJSONArray("entry");
            if (entries != null && !entries.isEmpty()) {
                JSONObject resource = entries.getJSONObject(0).optJSONObject("resource");
                if (resource != null && "Organization".equals(resource.optString("resourceType"))) {
                    // Extract "id"
                    statusMap.put("shaFacilityId", resource.optString("id", "--"));

                    // Process "extension"
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
                                case "https://shr.tiberbuapps.com/fhir/StructureDefinition/approved":
                                    JSONObject valueCoding = extension.optJSONObject("valueCoding");
                                    if (valueCoding != null) {
                                        statusMap.put("approved", valueCoding.optString("display", "--"));
                                    }
                                    break;
                            }
                        }
                    }

                    // Process "identifier"
                    JSONArray identifiers = resource.optJSONArray("identifier");
                    if (identifiers != null) {
                        for (int i = 0; i < identifiers.length(); i++) {
                            JSONObject identifier = identifiers.getJSONObject(i);
                            JSONArray codingArray = identifier.optJSONObject("type").optJSONArray("coding");
                            if (codingArray != null) {
                                for (int j = 0; j < codingArray.length(); j++) {
                                    JSONObject coding = codingArray.getJSONObject(j);
                                    String code = coding.optString("code");

                                    switch (code) {
                                        case "license-number":
                                            statusMap.put("shaFacilityLicenseNumber", identifier.optString("value", "--"));
                                            break;
                                        case "mfl-code":
                                            statusMap.put("mflCode", identifier.optString("value", "--"));
                                            break;
                                        case "fid":
                                            statusMap.put("shaFacilityId", identifier.optString("value", "--"));
                                            break;
                                        case "fr-code":
                                            statusMap.put("facilityRegistryCode", identifier.optString("value", "--"));
                                            break;
                                        case "registration-number":
                                            statusMap.put("registrationNumber", identifier.optString("value", "--"));
                                            break;
                                    }
                                }
                            }

                            // Check for "end" in "period" for expiry date
                            JSONObject period = identifier.optJSONObject("period");
                            if (period != null) {
                                statusMap.put("shaFacilityExpiryDate", period.optString("end", "--"));
                            }
                        }
                    }
                }
            }
            // Serialize statusMap to JSON string
            String jsonResponseString = new JSONObject(statusMap).toString();
            return ResponseEntity.ok(jsonResponseString);

        } catch (JSONException e) {
            System.err.println("Error parsing facility status JSON: " + e.getMessage());
            logDiagnostics(organizationResourceResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Failed to parse facility status.\"}");
        }
    }
}
