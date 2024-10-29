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

        String qry = "SELECT ci.patient_id,\n" +
                "       SUBSTRING_INDEX(\n" +
                "           MAX(CONCAT(visit_date, ',', \n" +
                "           COALESCE(CASE ci.chronic_illness\n" +
                "               WHEN 149019 THEN 'Alzheimers Disease and other Dementias'\n" +
                "               WHEN 148432 THEN 'Arthritis'\n" +
                "               WHEN 153754 THEN 'Asthma'\n" +
                "               WHEN 159351 THEN 'Cancer'\n" +
                "               WHEN 119270 THEN 'Cardiovascular diseases'\n" +
                "               WHEN 120637 THEN 'Chronic Hepatitis'\n" +
                "               WHEN 145438 THEN 'Chronic Kidney Disease'\n" +
                "               WHEN 1295 THEN 'Chronic Obstructive Pulmonary Disease(COPD)'\n" +
                "               WHEN 120576 THEN 'Chronic Renal Failure'\n" +
                "               WHEN 119692 THEN 'Cystic Fibrosis'\n" +
                "               WHEN 120291 THEN 'Deafness and Hearing impairment'\n" +
                "               WHEN 119481 THEN 'Diabetes'\n" +
                "               WHEN 118631 THEN 'Endometriosis'\n" +
                "               WHEN 117855 THEN 'Epilepsy'\n" +
                "               WHEN 117789 THEN 'Glaucoma'\n" +
                "               WHEN 139071 THEN 'Heart Disease'\n" +
                "               WHEN 115728 THEN 'Hyperlipidaemia'\n" +
                "               WHEN 117399 THEN 'Hypertension'\n" +
                "               WHEN 117321 THEN 'Hypothyroidism'\n" +
                "               WHEN 151342 THEN 'Mental illness'\n" +
                "               WHEN 133687 THEN 'Multiple Sclerosis'\n" +
                "               WHEN 115115 THEN 'Obesity'\n" +
                "               WHEN 114662 THEN 'Osteoporosis'\n" +
                "               WHEN 117703 THEN 'Sickle Cell Anaemia'\n" +
                "               WHEN 118976 THEN 'Thyroid disease'\n" +
                "               WHEN 141623 THEN 'Dyslipidemia'\n" +
                "           END))), ',', -1) AS ChronicIllness\n" +
                "FROM kenyaemr_etl.etl_allergy_chronic_illness ci\n" +
                "WHERE ci.visit_date <= DATE(:endDate)\n" +
                "GROUP BY ci.patient_id;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Date endDate = (Date)context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);

        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
