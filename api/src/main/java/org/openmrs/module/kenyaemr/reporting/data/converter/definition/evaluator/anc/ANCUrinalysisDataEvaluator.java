/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.anc;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.anc.ANCUrinalysisDataDefinition;
import org.openmrs.module.reporting.data.encounter.EvaluatedEncounterData;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDataDefinition;
import org.openmrs.module.reporting.data.encounter.evaluator.EncounterDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Evaluates a Visit Number Data Definition to produce a Visit Number
 */
@Handler(supports= ANCUrinalysisDataDefinition.class, order=50)
public class ANCUrinalysisDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry = "SELECT\n" +
                "    v.encounter_id,\n" +
                "    IF(\n" +
                "            (\n" +
                "                v.urine_microscopy IS NOT NULL\n" +
                "                    OR v.urinary_albumin IS NOT NULL\n" +
                "                    OR v.glucose_measurement IS NOT NULL\n" +
                "                    OR v.urine_ph IS NOT NULL\n" +
                "                    OR v.urine_gravity IS NOT NULL\n" +
                "                    OR v.urine_nitrite_test IS NOT NULL\n" +
                "                    OR v.urine_dipstick_for_blood IS NOT NULL\n" +
                "                    OR v.urine_leukocyte_esterace_test IS NOT NULL\n" +
                "                    OR v.urinary_ketone IS NOT NULL\n" +
                "                    OR v.urine_bile_pigment_test IS NOT NULL\n" +
                "                    OR v.urine_bile_salt_test IS NOT NULL\n" +
                "                    OR v.urine_colour IS NOT NULL\n" +
                "                    OR v.urine_turbidity IS NOT NULL\n" +
                "                )\n" +
                "                OR EXISTS (\n" +
                "                SELECT 1\n" +
                "                FROM kenyaemr_etl.etl_laboratory_extract l\n" +
                "                WHERE l.patient_id = v.patient_id\n" +
                "                  AND DATE(l.visit_date) = DATE(v.visit_date)\n" +
                "                  AND l.lab_test = '1000073'\n" +
                "                  AND DATE(l.date_test_requested) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
                "            ),\n" +
                "            'Y', 'N'\n" +
                "    ) AS urinalysis\n" +
                "FROM kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "WHERE DATE(v.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate);";

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
