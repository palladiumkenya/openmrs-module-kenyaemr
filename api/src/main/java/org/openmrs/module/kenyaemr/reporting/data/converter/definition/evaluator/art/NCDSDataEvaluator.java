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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.NCDsDataDefinition;
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
 * Evaluates NCDs DataDefinition
 */
@Handler(supports= NCDsDataDefinition.class, order=50)
public class NCDSDataEvaluator implements PersonDataEvaluator {
    @Autowired
    private EvaluationService evaluationService;
    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "SELECT ci.patient_id, " +
                "       GROUP_CONCAT(CASE ci.chronic_illness " +
                "            WHEN 149019 THEN 'Alzheimers Disease and other Dementias' " +
                "            WHEN 148432 THEN 'Arthritis' " +
                "            WHEN 153754 THEN 'Asthma' " +
                "            WHEN 159351 THEN 'Cancer' " +
                "            WHEN 119270 THEN 'Cardiovascular diseases' " +
                "            WHEN 120637 THEN 'Chronic Hepatitis' " +
                "            WHEN 145438 THEN 'Chronic Kidney Disease' " +
                "            WHEN 1295 THEN 'Chronic Obstructive Pulmonary Disease(COPD)' " +
                "            WHEN 120576 THEN 'Chronic Renal Failure' " +
                "            WHEN 119692 THEN 'Cystic Fibrosis' " +
                "            WHEN 120291 THEN 'Deafness and Hearing impairment' " +
                "            WHEN 119481 THEN 'Diabetes' " +
                "            WHEN 118631 THEN 'Endometriosis' " +
                "            WHEN 117855 THEN 'Epilepsy' " +
                "            WHEN 117789 THEN 'Glaucoma' " +
                "            WHEN 139071 THEN 'Heart Disease' " +
                "            WHEN 115728 THEN 'Hyperlipidaemia' " +
                "            WHEN 117399 THEN 'Hypertension' " +
                "            WHEN 117321 THEN 'Hypothyroidism' " +
                "            WHEN 151342 THEN 'Mental illness' " +
                "            WHEN 133687 THEN 'Multiple Sclerosis' " +
                "            WHEN 115115 THEN 'Obesity' " +
                "            WHEN 114662 THEN 'Osteoporosis' " +
                "            WHEN 117703 THEN 'Sickle Cell Anaemia' " +
                "            WHEN 118976 THEN 'Thyroid disease' " +
                "            WHEN 141623 THEN 'Dyslipidemia' " +
                "       END SEPARATOR ', ') AS ChronicIllness " +
                "FROM kenyaemr_etl.etl_allergy_chronic_illness ci " +
                "JOIN ( " +
                "    SELECT patient_id, MAX(visit_date) AS latest_visit_date " +
                "    FROM kenyaemr_etl.etl_allergy_chronic_illness " +
                "    WHERE visit_date <= DATE(:endDate)" +
                "    GROUP BY patient_id " +
                ") latest ON ci.patient_id = latest.patient_id AND ci.visit_date = latest.latest_visit_date " +
                "GROUP BY ci.patient_id, ci.visit_date;";
        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Date endDate = (Date)context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);

        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
