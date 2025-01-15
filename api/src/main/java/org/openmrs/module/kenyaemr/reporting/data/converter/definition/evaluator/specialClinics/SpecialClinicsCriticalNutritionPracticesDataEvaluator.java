/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.specialClinics;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsCriticalNutritionPracticesDataDefinition;
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
 * Evaluates Referred to  
 * OPD Register
 */
@Handler(supports= SpecialClinicsCriticalNutritionPracticesDataDefinition.class, order=50)
public class SpecialClinicsCriticalNutritionPracticesDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        SpecialClinicsCriticalNutritionPracticesDataDefinition cohortDefinition = (SpecialClinicsCriticalNutritionPracticesDataDefinition) definition;
        String specialClinic = cohortDefinition.getSpecialClinic();


        String qry = "select v.encounter_id, \n" +
                "(case v.critical_nutrition_practices when 1107 then 'None' when 163300 then 'Nutrition status assessment' when 161648 then 'Dietary/Energy needs' \n" +
                "  when 1906 then 'Sanitation' when 135797 then 'Positive living behaviour' when  159364 then 'Exercise' when 154358 then 'Safe drinking water' \n" +
                "when 1611 then 'Prompt treatment for Opportunistic Infections' when 164377 then 'Drug food interactions side effects' else '' end) as critical_nutrition_practices\n"+
                "from kenyaemr_etl.etl_special_clinics v\n" +
                "where date(v.visit_date) between date(:startDate) and date(:endDate) and special_clinic = :specialClinic;";
        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Date startDate = (Date)context.getParameterValue("startDate");
        Date endDate = (Date)context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);
        queryBuilder.addParameter("startDate", startDate);
        queryBuilder.addParameter("specialClinic", specialClinic);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
