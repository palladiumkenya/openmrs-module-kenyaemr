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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsNutritionalStatusDataDefinition;
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
 * Evaluates Nutritional status
 */
@Handler(supports= SpecialClinicsNutritionalStatusDataDefinition.class, order=50)
public class SpecialClinicsNutritionalStatusDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        SpecialClinicsNutritionalStatusDataDefinition cohortDefinition = (SpecialClinicsNutritionalStatusDataDefinition) definition;
        String specialClinic = cohortDefinition.getSpecialClinic();

        String qry = "select c.encounter_id,\n" +
                "       case t.nutritional_status\n" +
                "           when 1115 then 'Normal'\n" +
                "           when 163303 then 'Moderate acute malnutrition'\n" +
                "           when 163302 then 'Severe acute malnutrition'\n" +
                "           when 114413 then 'Overweight'\n" +
                "           when 115115 then 'Obese' end as nutritional_status\n" +
                "from kenyaemr_etl.etl_special_clinics c\n" +
                "         inner join kenyaemr_etl.etl_patient_triage t on c.patient_id = t.patient_id\n" +
                "    and c.visit_date = t.visit_date\n" +
                "where c.visit_date between date(:startDate) and date(:endDate)\n" +
                "  and c.special_clinic_form_uuid = '"+specialClinic+"';";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Date startDate = (Date) context.getParameterValue("startDate");
        Date endDate = (Date) context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);
        queryBuilder.addParameter("startDate", startDate);
        queryBuilder.addParameter("specialClinic", specialClinic); // Corrected parameter name
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }


}
