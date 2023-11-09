/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.sari;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.sari.BloodInStoolDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.sari.SuspectedDataDefinition;
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
 * Evaluates TemperatureDataDefinition
 */
@Handler(supports= SuspectedDataDefinition.class, order=50)
public class SuspectedCaseDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
        String qry = "SELECT d.patient_id,\n" +
                "       CONCAT(\n" +
                "         CASE WHEN suspected_ili_case.suspectedIliCase IS NOT NULL THEN suspected_ili_case.suspectedIliCase ELSE '' END,\n" +
                "         ' ',\n" +
                "         CASE WHEN suspected_sari_case.suspectedSariCase IS NOT NULL THEN suspected_sari_case.suspectedSariCase ELSE '' END,\n" +
                "         ' ',\n" +
                "         CASE WHEN suspected_cholera_case.suspectedCholeraCase IS NOT NULL THEN suspected_cholera_case.suspectedCholeraCase ELSE '' END,\n" +
                "         ' ',\n" +
                "         CASE WHEN suspected_dysentery_case.suspectedDysenteryCase IS NOT NULL THEN suspected_dysentery_case.suspectedDysenteryCase ELSE '' END\n" +
                "       ) AS suspectedCase\n" +
                "FROM kenyaemr_etl.etl_allergy_chronic_illness d\n" +
                "LEFT JOIN (\n" +
                "    SELECT d.patient_id, 'Suspected Ili Case' AS suspectedIliCase\n" +
                "    FROM kenyaemr_etl.etl_allergy_chronic_illness d\n" +
                "    INNER JOIN kenyaemr_etl.etl_patient_triage t ON d.patient_id = t.patient_id\n" +
                "    INNER JOIN kenyaemr_etl.etl_patient_hiv_followup h ON d.patient_id = h.patient_id\n" +
                "    WHERE (t.temperature >= 38 OR h.temperature >= 38)\n" +
                "    AND d.complaint IN (143264, 140238)\n" +
                "    AND timestampdiff(DAY, date(d.complaint_date), date(:endDate)) <= 10\n" +
                ") AS suspected_ili_case ON d.patient_id = suspected_ili_case.patient_id\n" +
                "LEFT JOIN (\n" +
                "    SELECT d.patient_id, 'Suspected Sari Case' AS suspectedSariCase\n" +
                "    FROM kenyaemr_etl.etl_allergy_chronic_illness d\n" +
                "    INNER JOIN kenyaemr_etl.etl_patient_triage t ON d.patient_id = t.patient_id\n" +
                "    INNER JOIN kenyaemr_etl.etl_patient_hiv_followup h ON d.patient_id = h.patient_id\n" +
                "    WHERE (t.temperature >= 38 OR h.temperature >= 38)\n" +
                "    AND d.complaint IN (143264, 140238)\n" +
                "    AND timestampdiff(DAY, date(d.complaint_date), date(:endDate)) <= 10\n" +
                "    AND h.patient_admitted = 1065\n" +
                ") AS suspected_sari_case ON d.patient_id = suspected_sari_case.patient_id\n" +
                "LEFT JOIN (\n" +
                "    SELECT d.patient_id, 'Suspected Cholera Case' AS suspectedCholeraCase\n" +
                "    FROM kenyaemr_etl.etl_allergy_chronic_illness d\n" +
                "    WHERE d.complaint IN (142412, 122983)\n" +
                ") AS suspected_cholera_case ON d.patient_id = suspected_cholera_case.patient_id\n" +
                "LEFT JOIN (\n" +
                "    SELECT d.patient_id, 'Suspected Dysentery Case' AS suspectedDysenteryCase\n" +
                "    FROM kenyaemr_etl.etl_allergy_chronic_illness d\n" +
                "    WHERE d.complaint = 132494\n" +
                "    AND timestampdiff(DAY, date(d.complaint_date), date(:endDate)) < 2\n" +
                ") AS suspected_dysentery_case ON d.patient_id = suspected_dysentery_case.patient_id\n" +
                "GROUP BY d.patient_id;";

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
