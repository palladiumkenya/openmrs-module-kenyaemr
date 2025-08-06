/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.clinicianWorkload;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.clinicianWorkload.TriageDateDataDefinition;
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
 * Evaluates a TriageDateDataDefinition
 */
@Handler(supports = TriageDateDataDefinition.class, order = 50)
public class TriageDateDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "WITH filtered_patients AS (SELECT patient_id\n" +
                "                           FROM kenyaemr_etl.etl_patient_demographics\n" +
                "                           WHERE voided = 0),\n" +
                "     checked_in AS (SELECT v.visit_id,\n" +
                "                           v.patient_id,\n" +
                "                           v.date_started AS checkin_date\n" +
                "                    FROM openmrs.visit v\n" +
                "                             JOIN filtered_patients p ON p.patient_id = v.patient_id\n" +
                "                             JOIN users u ON u.user_id = v.creator\n" +
                "                    WHERE v.voided = 0\n" +
                "                      AND DATE(v.date_started) BETWEEN DATE(:startDate) AND DATE(:endDate)),\n" +
                "     triage_filtered AS (SELECT t.visit_id,\n" +
                "                                t.patient_id,\n" +
                "                                t.visit_date AS triage_date\n" +
                "                         FROM kenyaemr_etl.etl_patient_triage t\n" +
                "                                  JOIN filtered_patients p ON p.patient_id = t.patient_id\n" +
                "                                  LEFT JOIN checked_in v ON v.patient_id = t.patient_id AND v.visit_id = t.visit_id\n" +
                "                         WHERE t.voided = 0\n" +
                "                           AND t.visit_date BETWEEN :startDate AND :endDate)\n" +
                "SELECT v.patient_id,\n" +
                "       IFNULL(t.triage_date, 'Not Done') AS triage_date\n" +
                "FROM checked_in v\n" +
                "         JOIN filtered_patients fp ON fp.patient_id = v.patient_id\n" +
                "         LEFT JOIN triage_filtered t ON t.patient_id = v.patient_id AND t.visit_id = v.visit_id\n" +
                "WHERE date(v.checkin_date) BETWEEN DATE(:startDate) AND DATE(:endDate);";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        Date startDate = (Date) context.getParameterValue("startDate");
        Date endDate = (Date) context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);
        queryBuilder.addParameter("startDate", startDate);
        queryBuilder.append(qry);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}