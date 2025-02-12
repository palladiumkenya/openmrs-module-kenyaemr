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
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.HttpURLConnection;

import static org.openmrs.module.kenyaemr.dataExchange.DataHandler.*;
import static org.openmrs.module.kenyaemr.util.EmrUtils.getGlobalPropertyValue;

/**
 * A scheduled task that automatically updates the facility status.
 */
public class BenefitsPackageDataExchange {
    private static final Logger log = LoggerFactory.getLogger(BenefitsPackageDataExchange.class);

    private static final String BASE_URL_KEY = CommonMetadata.GP_HIE_BASE_END_POINT_URL;

  /*  public static ResponseEntity<String> getBenefitsPackage() {

        String bearerToken = getBearerToken();
        if (bearerToken.isEmpty()) {
            System.err.println("Bearer token is missing");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
        }

        try {
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSslConnectionFactory()).build();
            HttpGet getRequest = new HttpGet(getGlobalPropertyValue(BASE_URL_KEY) + "benefit-package");
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
            System.err.println("Error fetching benefits package: " + ex.getMessage());
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\": \"Error\"}");
        }
    }*/

}