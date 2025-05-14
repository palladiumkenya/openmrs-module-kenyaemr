/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.art;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLDifferentiatedCareModelDataDefinition;
import org.openmrs.module.reporting.data.person.EvaluatedPersonData;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.data.person.evaluator.PersonDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Evaluates Differentiated care model Data Definition
 */
@Handler(supports= ETLDifferentiatedCareModelDataDefinition.class, order=50)
public class ETLDifferentiatedCareModelDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry="WITH patient_followup AS (SELECT f.patient_id,\n" +
                "     max(f.visit_date)                                               as visit_date,\n" +
                "     MID(MAX(CONCAT(f.visit_date, f.differentiated_care_group)), 11) AS differentiated_care_group,\n" +
                "     MID(MAX(CONCAT(f.visit_date, f.differentiated_care)), 11)       AS differentiated_care\n" +
                "FROM kenyaemr_etl.etl_patient_hiv_followup f\n" +
                "       INNER JOIN\n" +
                "   kenyaemr_etl.etl_patient_demographics d\n" +
                "   ON\n" +
                "       f.patient_id = d.patient_id\n" +
                "WHERE f.stability IS NOT NULL\n" +
                "AND f.person_present = 978\n" +
                "AND DATE(f.visit_date) <= DATE(:endDate)\n" +
                "AND f.voided = 0\n" +
                "GROUP BY f.patient_id),\n" +
                "differentiated_care AS (SELECT patient_id,\n" +
                "        CASE differentiated_care_group\n" +
                "            WHEN 1555 THEN 'Health care worker Led Community ART group(HCAG)'\n" +
                "            WHEN 1537 THEN 'Facility ART distribution group'\n" +
                "            WHEN 163488 THEN 'Community ART distribution group'\n" +
                "            WHEN 164942 then 'Standard Care'\n" +
                "            WHEN 164943 then 'Fast Track'\n" +
                "            WHEN 164944 then 'Community ART Distribution - HCW Led'\n" +
                "            WHEN 164945 then 'Community ART Distribution - Peer Led'\n" +
                "            WHEN 164946 then 'Facility ART Distribution Group'\n" +
                "            WHEN 166443 THEN 'Health care worker Led facility ART group(HFAG)'\n" +
                "            WHEN 166444 THEN 'Peer Led Facility ART Group(PFAG)'\n" +
                "            END                                                                       AS differentiated_care_group,\n" +
                "        COALESCE(CASE differentiated_care\n" +
                "                     WHEN 164942 THEN CONCAT_WS('-', 'Standard Care',\n" +
                "                                                CASE differentiated_care_group\n" +
                "                                                    WHEN 1537\n" +
                "                                                        THEN 'Facility ART distribution group'\n" +
                "                                                    WHEN 163488\n" +
                "                                                        THEN 'Community ART distribution group'\n" +
                "                                                    END)\n" +
                "                     WHEN 164943 THEN CONCAT_WS('-', 'Fast Track',\n" +
                "                                                CASE differentiated_care_group\n" +
                "                                                    WHEN 1537\n" +
                "                                                        THEN 'Facility ART distribution group'\n" +
                "                                                    WHEN 163488\n" +
                "                                                        THEN 'Community ART distribution group'\n" +
                "                                                    END)\n" +
                "                     WHEN 166443 THEN 'Health care worker Led facility ART group(HFAG)'\n" +
                "                     WHEN 166444 THEN 'Peer Led Facility ART Group(PFAG)'\n" +
                "                     WHEN 1555 THEN 'Health care worker Led Community ART group(HCAG)'\n" +
                "                     WHEN 164945 THEN 'Peer Led Community ART Group(PCAG)'\n" +
                "                     WHEN 1000478 THEN 'Community Pharmacy(CP)'\n" +
                "                     WHEN 164944 THEN 'Community ART Distribution Points(CAPD)'\n" +
                "                     WHEN 166583 THEN 'Individual patient ART Community Distribution(IACD)'\n" +
                "                     END, CASE differentiated_care_group\n" +
                "                              WHEN 164942 then 'Standard Care'\n" +
                "                              WHEN 1555 THEN 'Health care worker Led Community ART group(HCAG)'\n" +
                "                              WHEN 1537 THEN 'Facility ART distribution group'\n" +
                "                              WHEN 163488 THEN 'Community ART distribution group'\n" +
                "                              WHEN 164943 then 'Fast Track'\n" +
                "                              WHEN 164944 then 'Community ART Distribution - HCW Led'\n" +
                "                              WHEN 164945 then 'Community ART Distribution - Peer Led'\n" +
                "                              WHEN 166443 THEN 'Health care worker Led facility ART group(HFAG)'\n" +
                "                              WHEN 166444 THEN 'Peer Led Facility ART Group(PFAG)'\n" +
                "                              WHEN 164946 then 'Facility ART Distribution Group' END)         AS differentiated_care_model,\n" +
                "        visit_date\n" +
                " FROM patient_followup)\n" +
                "SELECT patient_id,\n" +
                "differentiated_care_model\n" +
                "FROM differentiated_care;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Date startDate = (Date)context.getParameterValue("startDate");
        Date endDate = (Date)context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);
        queryBuilder.addParameter("startDate", startDate);

        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
