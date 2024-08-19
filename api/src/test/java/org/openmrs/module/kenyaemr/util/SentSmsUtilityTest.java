/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.util.Base64;

public class SentSmsUtilityTest {

    private static final String SMS_SENDER_ID ="";
    private static final String SMS_GATEWAY= "";
    private  static final String SMS_URL = "https://sms-service/api/sender";
    private static final String SMS_API_TOKEN ="";
    //test sending
    @Test
    public void sendKenyaEEmrSms() throws Exception {
        _sendSms("2547...", "Hello, this is a test message");
    }
    public static String _sendSms(String recipient, String message) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String responseMsg = null;
        try {
            HttpPost postRequest = new HttpPost(SMS_URL);
            postRequest.setHeader("api-token", SMS_API_TOKEN);
            JSONObject json = new JSONObject();
            json.put("destination", recipient);
            json.put("msg", message);
            json.put("sender_id", SMS_SENDER_ID);
            json.put("gateway", SMS_GATEWAY );

            StringEntity entity = new StringEntity(json.toString());
            postRequest.setEntity(entity);
            postRequest.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());

                if (statusCode == 200) {
                    responseMsg = "SMS sent successfully" + responseBody;
                    System.out.println("SMS sent successfully \n" + responseBody);
                } else {
                    responseMsg = "Failed to send SMS " + responseBody;
                    System.err.println("Failed to send SMS \n" + responseBody);
                }
            }
        } finally {
            httpClient.close();
        }
        return responseMsg;
    }
}
