/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.opd;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDMalariaAssessmentDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDTracerDrugsPrescribedDataDefinition;
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
 * Evaluates Tracer drugs prescribed 
 * OPD Register
 */
@Handler(supports= OPDTracerDrugsPrescribedDataDefinition.class, order=50)
public class OPDTracerDrugsPrescribedDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry = "SELECT\n" +
			"    v.encounter_id,\n" +
			"    GROUP_CONCAT(\n" +
			"            CASE d.drug_concept_id\n" +
			"                WHEN 351 THEN 1\n" +
			"                WHEN 86672 THEN 2\n" +
			"                WHEN 71161 THEN 4\n" +
			"                WHEN 86339 THEN 5\n" +
			"                WHEN 81341 THEN 6\n" +
			"                WHEN 70439 THEN 7\n" +
			"                WHEN 162650 THEN 8\n" +
			"                ELSE NULL\n" +
			"                END\n" +
			"            SEPARATOR '|'\n" +
			"    ) AS tracer_drugs_prescribed\n" +
			"FROM kenyaemr_etl.etl_clinical_encounter v\n" +
			"         INNER JOIN kenyaemr_etl.etl_drug_order d\n" +
			"                    ON v.patient_id = d.patient_id\n" +
			"WHERE DATE(v.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
			"GROUP BY v.encounter_id";

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
