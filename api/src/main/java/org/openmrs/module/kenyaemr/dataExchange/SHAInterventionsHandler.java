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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonNode;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.util.OpenmrsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import static org.openmrs.module.kenyaemr.util.EmrUtils.getGlobalPropertyValue;

public class SHAInterventionsHandler extends DataHandler {
    private static final String LOCAL_FILE_PATH = OpenmrsUtil.getApplicationDataDirectory() + "/sha/sha_interventions.json";
    private static final GlobalProperty gpHIEAuthMode = Context.getAdministrationService().getGlobalPropertyObject(CommonMetadata.GP_SHA_JWT_AUTH_MODE);
    private static final Logger log = LoggerFactory.getLogger(SHAInterventionsHandler.class);

    private static final String SHA_INTERVENTIONS = CommonMetadata.GP_SHA_INTERVENTIONS;
    private static final String INTERVENTIONS_PAGE_SIZE = CommonMetadata.GP_SHA_INTERVENTIONS_PAGE_SIZE;

    public SHAInterventionsHandler() {
        super(LOCAL_FILE_PATH);
    }

    @Override
    protected JsonNode fetchRemoteData() {
        try {
            String response = getInterventions().getBody();
            if (response != null) {
                JsonNode jsonNode = objectMapper.readTree(response);
                return jsonNode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseEntity<String> getInterventions() throws IOException {

        String bearerToken = getBearerToken();
        if (bearerToken.isEmpty()) {
            System.err.println("Bearer token is missing");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
        }

        try {
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSslConnectionFactory()).build();

            String url = getGlobalPropertyValue(SHA_INTERVENTIONS);

            if (url.trim().isEmpty()) {
                System.err.println("SHA_INTERVENTIONS URL is missing");
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
                        .body("{\"status\": \"Error\", \"message\": \"URL is missing\"}");
            }

            int responseCode = 0;
            HttpResponse response = null;

            if (gpHIEAuthMode != null) {
                String authMode = gpHIEAuthMode.getPropertyValue().trim();
                if ("get".equalsIgnoreCase(authMode)) {
                    HttpPost postRequest = new HttpPost(url);
                    postRequest.setHeader("Authorization", "Bearer " + bearerToken);
                    postRequest.setHeader("Content-Type", "application/json");
                    String jsonPayload = "{\"searchKeyAndValues\": {}}";
                    postRequest.setEntity(new StringEntity(jsonPayload, StandardCharsets.UTF_8));
                    response = httpClient.execute(postRequest);
                    responseCode = response.getStatusLine().getStatusCode();
                } else if ("post".equalsIgnoreCase(authMode)) {
                    String pageSize = getGlobalPropertyValue(INTERVENTIONS_PAGE_SIZE).trim();
                    if (!pageSize.trim().isEmpty()) {
                        url += (url.contains("?") ? "&" : "?") + "page_size=" + pageSize;
                    }
                    else {
                        System.err.println("WARN - SHA Interventions page size is not configured. Using default page size.");
                    }

                    HttpGet getRequest = new HttpGet(url);
                    getRequest.setHeader("Authorization", "Bearer " + bearerToken);
                    response = httpClient.execute(getRequest);
                    responseCode = response.getStatusLine().getStatusCode();
                } else {
                    System.err.println("Invalid Authentication mode: "+authMode);
                }
            } else {
                System.err.println("Authentication mode is missing");
            }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(createSuccessResponse(response));
            } else {
                System.err.println("Error: failed to connect: " + responseCode);
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
            }
        } catch (Exception ex) {
            System.err.println("Error fetching interventions: " + ex.getMessage());

            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
        }
    }
}