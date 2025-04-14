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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ARTCacxScreeningDataDefinition;
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
 * Evaluates Patient Case Manager Date DataDefinition
 */
@Handler(supports= ARTCacxScreeningDataDefinition.class, order=50)
public class ARTCacxScreeningDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "SELECT hiv_subquery.patient_id,\n" +
                "       COALESCE(ccs.screened_for_cacx, hiv_subquery.hiv_screening_result)                                      AS screening_result\n" +
                "FROM (SELECT f.patient_id,\n" +
                "             MID(MAX(CONCAT(DATE(f.visit_date),\n" +
                "                            CASE\n" +
                "                                WHEN f.cacx_screening IN (703, 664, 1065) THEN 'Yes'\n" +
                "                                WHEN f.cacx_screening = 1066 THEN 'No'\n" +
                "                                WHEN f.cacx_screening = 1118 THEN 'No'\n" +
                "                                WHEN f.cacx_screening = 1175 THEN 'N/A'\n" +
                "                                END)), 11) AS hiv_screening_result,\n" +
                "             MAX(f.visit_date)             AS fup_date\n" +
                "      FROM kenyaemr_etl.etl_patient_hiv_followup f\n" +
                "      WHERE f.person_present = 978\n" +
                "        AND DATE(f.visit_date) <= DATE(:endDate)\n" +
                "      GROUP BY f.patient_id) hiv_subquery\n" +
                "         INNER JOIN kenyaemr_etl.etl_patient_demographics p\n" +
                "                    ON p.patient_id = hiv_subquery.patient_id\n" +
                "                        AND p.voided = 0\n" +
                "                        AND p.gender = 'F'\n" +
                "         LEFT JOIN (SELECT v.patient_id,\n" +
                "                           MID(MAX(CONCAT(v.visit_date, v.cervical_cancer)), 11) AS screened_for_cacx,\n" +
                "                           MAX(v.visit_date)                                     AS screening_date\n" +
                "                    FROM kenyaemr_etl.etl_cervical_cancer_screening v\n" +
                "                    WHERE DATE(v.visit_date) <= DATE(:endDate)\n" +
                "                      AND v.cervical_cancer IS NOT NULL\n" +
                "                    GROUP BY v.patient_id) ccs\n" +
                "                   ON ccs.patient_id = hiv_subquery.patient_id\n" +
                "                       AND ccs.screening_date >= hiv_subquery.fup_date\n" +
                "GROUP BY hiv_subquery.patient_id;";
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
