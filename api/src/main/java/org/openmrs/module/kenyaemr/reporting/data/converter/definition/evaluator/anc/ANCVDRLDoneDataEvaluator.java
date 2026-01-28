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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.anc.ANCSyphilisTestDoneDataDefinition;
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
 * Evaluates Definition if test results for syphilis
 */
@Handler(supports= ANCSyphilisTestDoneDataDefinition.class, order=50)
public class ANCVDRLDoneDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry = "SELECT\n" +
                "    v.encounter_id,\n" +
                "    CASE\n" +
                "        WHEN v.syphilis_test_status IS NOT NULL AND COALESCE(l.has_vdrl, 0) = 1 THEN 'Duo Test & VDRL'\n" +
                "        WHEN v.syphilis_test_status IS NOT NULL THEN 'Duo Test'\n" +
                "        WHEN COALESCE(l.has_vdrl, 0) = 1 THEN 'VDRL'\n" +
                "        END AS syphilis\n" +
                "FROM kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "         LEFT JOIN (\n" +
                "    SELECT\n" +
                "        x.encounter_id,\n" +
                "        x.patient_id,\n" +
                "        DATE(x.date_test_requested) AS test_date,\n" +
                "        1 AS has_vdrl\n" +
                "    FROM kenyaemr_etl.etl_laboratory_extract x\n" +
                "    WHERE x.lab_test = 299\n" +
                "      AND DATE(x.date_test_requested) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
                "    GROUP BY x.encounter_id, x.patient_id, DATE(x.date_test_requested)\n" +
                ") l\n" +
                "                   ON (\n" +
                "                       (l.encounter_id IS NOT NULL AND l.encounter_id = v.encounter_id)\n" +
                "                           OR (l.encounter_id IS NULL AND l.patient_id = v.patient_id AND l.test_date = DATE(v.visit_date))\n" +
                "                       )\n" +
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
