/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.cacx;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.cacx.CACXScreeningResultsDataDefinition;
import org.openmrs.module.reporting.data.encounter.EvaluatedEncounterData;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDataDefinition;
import org.openmrs.module.reporting.data.encounter.evaluator.EncounterDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Date;

/**
 * Evaluates CACX Screening Results
 */
@Handler(supports=CACXScreeningResultsDataDefinition.class, order=50)
public class CACXScreeningResultsDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry = "SELECT\n" +
                "    encounter_id,\n" +
                "    CONCAT_WS(\n" +
                "            ', ',\n" +
                "            CASE\n" +
                "                WHEN NULLIF(TRIM(via_vili_screening_method), '') IS NOT NULL\n" +
                "                    AND NULLIF(TRIM(via_vili_screening_result), '') IS NOT NULL\n" +
                "                    THEN TRIM(via_vili_screening_result)\n" +
                "                ELSE NULL\n" +
                "                END,\n" +
                "            CASE\n" +
                "                WHEN NULLIF(TRIM(hpv_screening_method), '') IS NOT NULL\n" +
                "                    AND NULLIF(TRIM(hpv_screening_result), '') IS NOT NULL\n" +
                "                    THEN TRIM(hpv_screening_result)\n" +
                "                ELSE NULL\n" +
                "                END,\n" +
                "            CASE\n" +
                "                WHEN NULLIF(TRIM(pap_smear_screening_method), '') IS NOT NULL\n" +
                "                    AND NULLIF(TRIM(pap_smear_screening_result), '') IS NOT NULL\n" +
                "                    THEN TRIM(pap_smear_screening_result)\n" +
                "                ELSE NULL\n" +
                "                END,\n" +
                "            CASE\n" +
                "                WHEN NULLIF(TRIM(colposcopy_screening_method), '') IS NOT NULL\n" +
                "                    AND NULLIF(TRIM(colposcopy_screening_result), '') IS NOT NULL\n" +
                "                    THEN TRIM(colposcopy_screening_result)\n" +
                "                ELSE NULL\n" +
                "                END\n" +
                "    ) AS screening_results\n" +
                "FROM kenyaemr_etl.etl_cervical_cancer_screening\n" +
                "WHERE DATE(visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate);";

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