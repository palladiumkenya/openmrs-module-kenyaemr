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


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

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

    private static final Logger log = LoggerFactory.getLogger(DataHandler.class);
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

    public static String getBearerToken() throws IOException {
		// Utility function to get auth token
		String ret = null;
		OkHttpClient client = new OkHttpClient();
		GlobalProperty globalGetJwtTokenUrl = Context.getAdministrationService()
			.getGlobalPropertyObject(CommonMetadata.GP_SHA_JWT_TOKEN_GET_END_POINT);
		String shaJwtTokenUrl = globalGetJwtTokenUrl.getPropertyValue();
		if (shaJwtTokenUrl == null || shaJwtTokenUrl.trim().isEmpty()) {
			System.out.println("Jwt token url configs not updated: ");
		}
		GlobalProperty globalGetJwtUsername = Context.getAdministrationService()
			.getGlobalPropertyObject(CommonMetadata.GP_SHA_JWT_TOKEN_USERNAME);
		String shaJwtUsername = globalGetJwtUsername.getPropertyValue();
		if (shaJwtUsername == null || shaJwtUsername.trim().isEmpty()) {
			System.out.println("Jwt token username not updated: ");
		}
		GlobalProperty globalGetJwtPassword = Context.getAdministrationService()
			.getGlobalPropertyObject(CommonMetadata.GP_SHA_JWT_TOKEN_PASSWORD);
		String shaJwtPassword = globalGetJwtPassword.getPropertyValue();
		if (shaJwtPassword == null || shaJwtPassword.trim().isEmpty()) {
			System.out.println("Jwt token password not updated: ");
		}

		// Encode username and password for Basic Auth
		String auth = Base64.getEncoder().encodeToString((shaJwtUsername + ":" + shaJwtPassword).getBytes());
		//System.out.println("Encoded hie token auth : " + auth);

		//Config to toggle GET and POST requests
		GlobalProperty gpHIEAuthMode = Context.getAdministrationService().getGlobalPropertyObject(CommonMetadata.GP_SHA_JWT_AUTH_MODE);
		if (gpHIEAuthMode == null) {
			System.out.println("Jwt Auth mode not configured: ");
		} else if (gpHIEAuthMode.getPropertyValue().trim().equalsIgnoreCase("get")) {
			// Build the GET request
			Request request = new Request.Builder()
				.url(shaJwtTokenUrl)
				.header("Authorization", "Basic " + auth)
				.build();

			// Execute the request
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				// System.out.println("Response: " + response.body().string());
				ret = response.body().string();
			} else {
				System.out.println("HIE Auth Get Request failed: " + response.code() + " - " + response.message());
			}
		} else if (gpHIEAuthMode.getPropertyValue().trim().equalsIgnoreCase("post")) {
			// Build the POST request
			System.out.println("Auth mode is post: ");
			okhttp3.MediaType mediaType = okhttp3.MediaType.parse("text/plain");
			okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "");
			Request postRequest = new Request.Builder()
				.url(shaJwtTokenUrl)
				.method("POST", body)
				.header("Authorization", "Basic " + auth)
				.header("Cookie", "incap_ses_6550_2912339=Gt0ldtSEr3gzhyyAnUbmWsc39mcAAAAAeRlLAEJa78AYqRNx3TRw5A==; visid_incap_2912339=cOIlO1QSR62/Wo6Ega29z/TjjWcAAAAAQUIPAAAAAAB6TxV9uQ87Ev365z8yUqhP")
				.build();
			Response postResponse = client.newCall(postRequest).execute();
			if (!postResponse.isSuccessful()) {
				System.err.println("Get HIE Post Auth: ERROR: Request failed: " + postResponse.code() + " - " + postResponse.message());
			} else {
				String payload = postResponse.body().string();
				//System.out.println("Got HIE Post Auth token payload: " + payload);
				com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
				com.fasterxml.jackson.databind.JsonNode rootNode = objectMapper.readTree(payload);
				ret = rootNode.path("access_token").asText();
			}
		}
		//System.out.println("HIE Auth Token retrieved ==>" + ret);
		return ret;
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
    public static void logDiagnostics(ResponseEntity<String> response) {
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
}
