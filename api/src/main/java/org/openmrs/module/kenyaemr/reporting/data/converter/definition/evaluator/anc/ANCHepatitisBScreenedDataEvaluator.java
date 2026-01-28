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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.anc.ANCHepatitisBScreenedDefinition;
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
 * Evaluates ANC Hepatitis B Screened Evaluator
 */
@Handler(supports= ANCHepatitisBScreenedDefinition.class, order=50)
public class ANCHepatitisBScreenedDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry = "WITH lab AS (\n" +
                "    SELECT\n" +
                "        l.patient_id,\n" +
                "        DATE(l.visit_date) AS visit_date,\n" +
                "        CASE l.test_result\n" +
                "            WHEN 664 THEN 'N'\n" +
                "            WHEN 703 THEN 'P'\n" +
                "            WHEN 1138 THEN 'I'\n" +
                "            ELSE NULL\n" +
                "            END AS lab_test_result,\n" +
                "        ROW_NUMBER() OVER (\n" +
                "            PARTITION BY l.patient_id, DATE(l.visit_date)\n" +
                "            ORDER BY l.date_test_result_received DESC, l.date_test_requested DESC\n" +
                "            ) AS rn\n" +
                "    FROM kenyaemr_etl.etl_laboratory_extract l\n" +
                "    WHERE l.lab_test = '159430'\n" +
                "      AND DATE(l.date_test_requested) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
                "),\n" +
                "     anc AS (\n" +
                "         SELECT\n" +
                "             v.encounter_id,\n" +
                "             v.patient_id,\n" +
                "             DATE(v.visit_date) AS visit_date,\n" +
                "             CASE v.hepatitis_b_screening\n" +
                "                 WHEN 703 THEN 'P'\n" +
                "                 WHEN 664 THEN 'N'\n" +
                "                 WHEN 165649 THEN 'I'\n" +
                "                 ELSE NULL\n" +
                "                 END AS anc_test_result\n" +
                "         FROM kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "         WHERE DATE(v.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
                "     )\n" +
                "SELECT\n" +
                "    a.encounter_id,\n" +
                "    COALESCE(lo.lab_test_result, a.anc_test_result, 'ND') AS HepB_Screening\n" +
                "FROM anc a\n" +
                "         LEFT JOIN lab lo\n" +
                "                   ON lo.patient_id = a.patient_id\n" +
                "                       AND lo.visit_date  = a.visit_date\n" +
                "                       AND lo.rn = 1;";

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
