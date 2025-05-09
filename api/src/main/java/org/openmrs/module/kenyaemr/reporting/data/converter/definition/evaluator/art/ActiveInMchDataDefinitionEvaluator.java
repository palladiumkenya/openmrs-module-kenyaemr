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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ActiveInMchDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLLastVLDateDataDefinition;
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
 * Evaluates Active in MCH Data Definition
 */
@Handler(supports= ActiveInMchDataDefinition.class, order=50)
public class ActiveInMchDataDefinitionEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "SELECT pp.patient_id,\n" +
                "       CASE \n" +
                "         WHEN d.gender = 'M' THEN 'N/A'\n" +
                "         WHEN (pf.pregnant = 'Yes' OR hf.pregnancy_status = 'Yes' OR es.pregnant = 'Yes') THEN 'Pregnant'\n" +
                "         WHEN (pf.breastfeeding = 'Yes' OR hf.breastfeeding = 'Yes' OR es.breastfeeding_mother = 'Yes') THEN 'Breastfeeding'\n" +
                "         ELSE 'No'\n" +
                "       END AS program_status\n" +
                "FROM kenyaemr_etl.etl_patient_program pp\n" +
                "LEFT JOIN (SELECT patient_id, MAX(visit_date) AS latest_visit_date, pregnant, breastfeeding FROM kenyaemr_etl.etl_prep_followup GROUP BY patient_id) pf \n" +
                "       ON pp.patient_id = pf.patient_id\n" +
                "LEFT JOIN (SELECT patient_id, MAX(visit_date) AS latest_visit_date, pregnancy_status, breastfeeding FROM kenyaemr_etl.etl_patient_hiv_followup GROUP BY patient_id) hf \n" +
                "       ON pp.patient_id = hf.patient_id\n" +
                "LEFT JOIN (SELECT patient_id, MAX(visit_date) AS latest_visit_date, pregnant, breastfeeding_mother FROM kenyaemr_etl.etl_hts_eligibility_screening GROUP BY patient_id) es \n" +
                "       ON pp.patient_id = es.patient_id\n" +
                "LEFT JOIN kenyaemr_etl.etl_patient_demographics d \n" +
                "       ON pp.patient_id = d.patient_id\n" +
                "WHERE DATE(pp.date_completed) IS NULL\n" +
                "  AND pp.program IN ('MCH-Child Services', 'MCH-Mother Services')\n" +
                "GROUP BY pp.patient_id\n" +
                "HAVING DATE(MAX(pp.date_enrolled)) <= DATE(:endDate);";

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
