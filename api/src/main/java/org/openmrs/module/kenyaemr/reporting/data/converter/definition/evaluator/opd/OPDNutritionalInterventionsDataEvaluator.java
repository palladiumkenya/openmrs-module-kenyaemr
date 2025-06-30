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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDNutritionalInterventionsDataDefinition;
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
 * Evaluates Nutritional Interventions  
 * OPD Register
 */
@Handler(supports= OPDNutritionalInterventionsDataDefinition.class, order=50)
public class OPDNutritionalInterventionsDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry = "SELECT\n" +
			"   ce.encounter_id,\n" +
			"   GROUP_CONCAT(sp.val SEPARATOR '|') AS nutritional_services\n" +
			"FROM kenyaemr_etl.etl_clinical_encounter ce\n" +
			"        INNER JOIN (\n" +
			"   SELECT\n" +
			"       v.encounter_id,\n" +
			"       v.patient_id,\n" +
			"       CASE WHEN v.counselling_ordered = 'Nutritional and Dietary' THEN 1  END AS val\n" +
			"    FROM kenyaemr_etl.etl_clinical_encounter v\n" +
			"   WHERE DATE(v.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
			"   UNION\n" +
			"       SELECT\n" +
			"           v.encounter_id,\n" +
			"           v.patient_id,\n" +
			"           CASE WHEN v.critical_nutrition_practices IS NOT NULL THEN 1 END AS val\n" +
			"       FROM kenyaemr_etl.etl_special_clinics v\n" +
			"       WHERE DATE(v.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
			"       UNION\n" +
			"   SELECT\n" +
			"       v.encounter_id,\n" +
			"       v.patient_id,\n" +
			"       CASE WHEN v.micronutrients IS NOT NULL THEN 2 END\n" +
			"   FROM kenyaemr_etl.etl_special_clinics v\n" +
			"   WHERE DATE(v.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
			"   UNION\n" +
			"   SELECT\n" +
			"       v.encounter_id,\n" +
			"       v.patient_id,\n" +
			"       CASE WHEN v.supplemental_food IS NOT NULL THEN 3 END\n" +
			"   FROM kenyaemr_etl.etl_special_clinics v\n" +
			"   WHERE DATE(v.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
			") sp\n" +
			"                   ON ce.patient_id = sp.patient_id\n" +
			"GROUP BY ce.encounter_id;\n";

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
