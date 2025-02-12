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
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Base64;

import static org.openmrs.module.kenyaemr.util.EmrUtils.getGlobalPropertyValue;


public abstract class DataHandler {

    private static final Logger log = LoggerFactory.getLogger(InterventionsDataExchange.class);

    private static final String BASE_JWT_URL_KEY = CommonMetadata.GP_SHA_FACILITY_VERIFICATION_JWT_GET_END_POINT;
    private static final String API_USER_KEY = CommonMetadata.GP_HIE_API_USER;
    private static final String API_SECRET_KEY = CommonMetadata.GP_SHA_FACILITY_VERIFICATION_GET_API_SECRET;
    private final String localFilePath;
    protected final ObjectMapper objectMapper = new ObjectMapper();

    public DataHandler(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    protected abstract JsonNode fetchRemoteData();

    protected void ensureDirectoryExists() {
        String directoryPath = new File(localFilePath).getParent();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Directory created: " + directoryPath);
            } else {
                System.err.println("Failed to create directory: " + directoryPath);
            }

            try {
                Files.setPosixFilePermissions(
                        Paths.get(directoryPath),
                        PosixFilePermissions.fromString("rwxr-xr-x")
                );
            } catch (IOException e) {
                System.err.println("Failed to set directory permissions: " + e.getMessage());
            }
        }
    }

    public void saveToLocalFile(JsonNode data) {
        ensureDirectoryExists();
        try {
            objectMapper.writeValue(new File(localFilePath), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonNode readFromLocalFile() {
        File file = new File(localFilePath);

        // Check if the file exists, if not, fetch and save data from the remote source
        if (!file.exists()) {
            return fetchAndSaveFromRemote();
        }

        try {
            // Read the file contents
            byte[] jsonData = Files.readAllBytes(file.toPath());

            // Convert the byte array to a JsonNode
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            return jsonNode;
        } catch (IOException e) {
            e.printStackTrace();

            // If reading the file fails, fetch and save the remote data
            return fetchAndSaveFromRemote();
        }
    }

    public JsonNode fetchAndSaveFromRemote() {
        JsonNode remoteData = fetchRemoteData();
        if (remoteData != null) {
            saveToLocalFile(remoteData);
        }
        return remoteData;
    }

    public static SSLConnectionSocketFactory createSslConnectionFactory() throws Exception {
        return new SSLConnectionSocketFactory(
                SSLContexts.createDefault(),
                new String[]{"TLSv1.2"},
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier()
        );
    }

    public static String getBearerToken() {
        String username = getGlobalPropertyValue(API_USER_KEY).trim();
        String secret = getGlobalPropertyValue(API_SECRET_KEY).trim();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet getRequest = new HttpGet(getGlobalPropertyValue(BASE_JWT_URL_KEY));
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

    public static String createSuccessResponse(HttpResponse response) {
        try {
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("Error parsing response", e);
        }
    }
}
