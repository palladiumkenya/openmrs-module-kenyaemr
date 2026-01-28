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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.anc.ANCDiabetesTestingDefinition;
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
 * Evaluates ANC Blood sugar testing for Diabetes Evaluator
 */
@Handler(supports= ANCDiabetesTestingDefinition.class, order=50)
public class ANCDiabetesTestingDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry = "WITH lab AS (\n" +
                "    SELECT\n" +
                "        l.patient_id,\n" +
                "        DATE(l.visit_date) AS visit_date,\n" +
                "        l.result_name,\n" +
                "        ROW_NUMBER() OVER (\n" +
                "            PARTITION BY l.patient_id, DATE(l.visit_date)\n" +
                "            ORDER BY l.date_test_result_received DESC, l.date_test_requested DESC\n" +
                "            ) AS rn\n" +
                "    FROM kenyaemr_etl.etl_laboratory_extract l\n" +
                "    WHERE l.lab_test = '1000443'\n" +
                "      AND DATE(l.date_test_requested) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
                ")\n" +
                "SELECT\n" +
                "    v.encounter_id,\n" +
                "    COALESCE(\n" +
                "            if(lo.result_name > 11.1, '>11.1 mmol/L',if(lo.result_name <= 11.1, '<=11.1 mmol/L',lo.result_name)) ,\n" +
                "            COALESCE(\n" +
                "                    v.blood_glucose,\n" +
                "                    CASE v.blood_sugar_test\n" +
                "                        WHEN 1118 THEN 'No Diabetes'\n" +
                "                        WHEN 1175 THEN 'Not Done'\n" +
                "                        WHEN 119481 THEN 'Has Diabetes'\n" +
                "                        END\n" +
                "            )\n" +
                "    ) AS blood_sugar\n" +
                "FROM kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "         LEFT JOIN lab lo\n" +
                "                   ON lo.patient_id = v.patient_id\n" +
                "                       AND lo.visit_date = DATE(v.visit_date)\n" +
                "                       AND lo.rn = 1\n" +
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
