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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.clinicianWorkload.TriageProviderDataDefinition;
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
 * Evaluates a TriageProviderDataDefinition
 */
@Handler(supports = TriageProviderDataDefinition.class, order = 50)
public class TriageProviderDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "WITH filtered_patients AS (\n" +
                "    SELECT patient_id\n" +
                "    FROM kenyaemr_etl.etl_patient_demographics\n" +
                "    WHERE voided = 0\n" +
                "),\n" +
                "     users AS (\n" +
                "         SELECT u.user_id, u.username, u.person_id, CONCAT_WS(' ', n.given_name, n.middle_name, n.family_name) AS full_name\n" +
                "         FROM openmrs.users u\n" +
                "                  inner join person_name n on u.person_id = n.person_id\n" +
                "         WHERE u.retired = 0\n" +
                "           AND n.voided = 0\n" +
                "           AND n.preferred = 1\n" +
                "     ),\n" +
                "     checked_in AS (\n" +
                "         SELECT\n" +
                "             v.visit_id,\n" +
                "             v.patient_id,\n" +
                "             v.date_started AS checkin_date\n" +
                "         FROM openmrs.visit v\n" +
                "                  JOIN filtered_patients p ON p.patient_id = v.patient_id\n" +
                "                  JOIN users u ON u.user_id = v.creator\n" +
                "         WHERE v.voided = 0\n" +
                "           AND DATE(v.date_started) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
                "     ),\n" +
                "     triage_filtered AS (\n" +
                "         SELECT\n" +
                "             t.visit_id,\n" +
                "             t.patient_id,\n" +
                "             t.visit_date AS triage_date,\n" +
                "             u.full_name AS triaged_by\n" +
                "         FROM kenyaemr_etl.etl_patient_triage t\n" +
                "                  JOIN filtered_patients p ON p.patient_id = t.patient_id\n" +
                "                  LEFT JOIN checked_in v ON v.patient_id = t.patient_id AND v.visit_id = t.visit_id\n" +
                "           JOIN users u ON u.user_id = t.encounter_provider\n" +
                "         WHERE t.voided = 0\n" +
                "           AND t.visit_date BETWEEN :startDate AND :endDate\n" +
                "     )\n" +
                "SELECT\n" +
                "    v.patient_id,\n" +
                "    t.triaged_by\n" +
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