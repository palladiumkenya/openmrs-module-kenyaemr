/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Encounter;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.kenyaemr.util.ServerInformation;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.service.ReportService;
import org.openmrs.util.OpenmrsUtil;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class DwapiMetricsUtil {
    public static String getKenyaemrVersion() {
        //TODO: Read this from a global property:kenyaemr.version
        Map<String, Object> kenyaemrInfo = ServerInformation.getKenyaemrInformation();
        String moduleVersion = (String) kenyaemrInfo.get("version");
        return moduleVersion;
    }

    public static String getLastLogin() {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MMM-dd");


        Encounter lastFollowupEnc = EmrUtils.lastEncounter(null, Context.getEncounterService().getEncounterTypeByUuid(HivMetadata._EncounterType.HIV_CONSULTATION));
        if (lastFollowupEnc != null) {
            return DATE_FORMAT.format(lastFollowupEnc.getDateCreated());
        }
        return null;

    }

    public static String getDateofLastMOH731() {
        String moh731ReportDefUuid = "a66bf454-2a11-4e51-b28d-3d7ece76aa13";
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MMM-dd");
        List<ReportRequest> reports = fetchRequests(moh731ReportDefUuid, true, Context.getService(ReportService.class));
        if (reports != null && !reports.isEmpty()) {
            return DATE_FORMAT.format(reports.get(0).getEvaluateStartDatetime());
        }

        return null;
    }

    public static List<ReportRequest> fetchRequests(String reportUuid, boolean finishedOnly, ReportService reportService) {
        ReportDefinition definition = null;

        // Hack to avoid loading (and thus de-serialising) the entire report
        if (StringUtils.isNotEmpty(reportUuid)) {
            definition = new ReportDefinition();
            definition.setUuid(reportUuid);
        }

        List<ReportRequest> requests = (finishedOnly)
                ? reportService.getReportRequests(definition, null, null, ReportRequest.Status.COMPLETED, ReportRequest.Status.FAILED)
                : reportService.getReportRequests(definition, null, null);

        // Sort by requested date desc (more sane than the default sorting)
        Collections.sort(requests, new Comparator<ReportRequest>() {
            @Override
            public int compare(ReportRequest request1, ReportRequest request2) {
                return OpenmrsUtil.compareWithNullAsEarliest(request2.getRequestDate(), request1.getRequestDate());
            }
        });

        return requests;
    }

}
