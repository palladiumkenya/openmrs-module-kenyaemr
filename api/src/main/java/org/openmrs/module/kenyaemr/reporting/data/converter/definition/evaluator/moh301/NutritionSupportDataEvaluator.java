/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.moh301;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.NutritionSupportDataDefinition;
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
 * Evaluates a PrescriptionDateDataDefinition
 */
@Handler(supports = NutritionSupportDataDefinition.class, order = 50)
public class NutritionSupportDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "select n.patient_id,CONCAT_WS(',', if(\n" +
                "FIND_IN_SET('Drug food interactions side effects', n.critical_nutrition_practices) > 0\n" +
                "or FIND_IN_SET('Dietary/Energy needs', n.critical_nutrition_practices) > 0\n" +
                "or FIND_IN_SET('Nutrition counselling', n.critical_nutrition_practices) > 0\n" +
                "or FIND_IN_SET('Positive living behaviour', n.critical_nutrition_practices) > 0\n" +
                "or FIND_IN_SET('Exercise', n.critical_nutrition_practices) > 0\n" +
                "or FIND_IN_SET('Safe drinking water', n.critical_nutrition_practices) > 0\n" +
                "or FIND_IN_SET('Prompt treatment for Opportunistic Infections', n.critical_nutrition_practices) > 0,\n" +
                "'Nutrition Education', null),\n" +
                "IF(FIND_IN_SET('Nutrition status assessment', n.critical_nutrition_practices) > 0, 'Nutrition Assessment',null),\n" +
                "if((n.supplemental_food is not null and n.supplemental_food != 'None' and n.supplemental_food != '')\n" +
                "or (n.therapeutic_food is not null and n.therapeutic_food != 'None' and n.therapeutic_food != '')\n" +
                "or (n.micronutrients is not null and n.micronutrients != 'None' and n.micronutrients != ''),\n" +
                "'Nutrition supplements', null)\n" +
                ") as nutrition_intervention\n" +
                "from kenyaemr_etl.etl_special_clinics n where\n" +
                "n.special_clinic = 'Nutrition' and date(n.visit_date) between date(:startDate) and date(:endDate);\n";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        Date startDate = (Date) context.getParameterValue("startDate");
        Date endDate = (Date) context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);
        queryBuilder.addParameter("startDate", startDate);
        queryBuilder.append(qry);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}