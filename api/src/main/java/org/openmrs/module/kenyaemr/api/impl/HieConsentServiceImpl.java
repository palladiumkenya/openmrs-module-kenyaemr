/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.api.impl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;

import java.util.List;

public class HieConsentServiceImpl {

    /**
     * Sends a consent OTP request to an external consent service.
     *
     * @param identifierType  the type of identifier used (e.g., "National ID")
     * @param identifierNumber the unique identifier number of the client
     * @param phoneNumber     the recipient's phone number to which the OTP should be sent
     * @param scope           a list of scopes for which consent is being requested (e.g., "CLIENT_REGISTRY", "SHARED_HEALTH_RECORD")
     * @param facility        the facility initiating the request
     * @param token           the bearer token used for authenticating the request to the consent service
     * @return a {@link SimpleObject} containing the response message from the consent service,
     *         indicating whether the OTP was sent successfully or not
     * @throws Exception if an error occurs during the HTTP request or while constructing the response object
     */

    public static SimpleObject ConsentSendOTP(String identifierType, String identifierNumber, String phoneNumber, List<String> scope, String facility, String token) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String responseMsg = null;
        try {
            AdministrationService administrationService = Context.getAdministrationService();

            HttpPost postRequest = new HttpPost(administrationService.getGlobalProperty("kenyaemr.hie.consent.sms"));
            JSONObject json = new JSONObject();
            json.put("identifierType", identifierType);
            json.put("identifierNumber", identifierNumber);
            json.put("phoneNumber", phoneNumber);
            json.put("facility", facility);
            json.put("scope", scope);
            StringEntity entity = new StringEntity(json.toString());
            postRequest.setEntity(entity);
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.setHeader("Authorization", "Bearer " + token);

            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());
                if (statusCode == 200) {
                    responseMsg = responseBody;
                    System.out.println("OTP sent successfully \n" + responseBody);
                } else {
                    responseMsg = "Failed to send consent otp " + responseBody;
                    System.err.println("Failed to send consent otp \n" + responseBody);
                }
            }

        } finally {
            httpClient.close();
        }

        SimpleObject response = new SimpleObject();
        try {
            response.add("response", responseMsg);
        } catch (Exception e) {
            response.add("response", "Failed to send consent otp " + e.getMessage());
            throw new RuntimeException(e);
        }

        return response;
    }

    /**
     * Sends an OTP validation request to an external consent service.
     *
     * @param id     the unique request ID associated with the previously sent OTP
     * @param otp    the one-time password (OTP) to be validated
     * @param token  the bearer token used for authenticating the request to the consent service
     * @return a {@link SimpleObject} containing the response message from the consent service,
     *         indicating whether the OTP validation was successful or not
     * @throws Exception if an error occurs during the HTTP request or response processing
     */

    public static SimpleObject ConsentOTPValidation(String id, String otp, String token) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String responseMsg = null;
        try {
            AdministrationService administrationService = Context.getAdministrationService();
            HttpPost postRequest = new HttpPost(administrationService.getGlobalProperty("kenyaemr.hie.consent.validation"));
            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("otp", otp);
            StringEntity entity = new StringEntity(json.toString());
            postRequest.setEntity(entity);
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.setHeader("Authorization", "Bearer " + token);

            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());
                if (statusCode == 200) {
                    responseMsg = responseBody;
                    System.out.println("OTP validation successfully \n" + responseBody);
                } else {
                    responseMsg = "Failed to validate otp " + responseBody;
                    System.err.println("Failed to validate otp \n" + responseBody);
                }
            }

        } finally {
            httpClient.close();
        }

        SimpleObject response = new SimpleObject();
        try {
            response.add("response", responseMsg);
        } catch (Exception e) {
            response.add("response", "Failed to validate otp " + e.getMessage());
            throw new RuntimeException(e);
        }

        return response;
    }
}
