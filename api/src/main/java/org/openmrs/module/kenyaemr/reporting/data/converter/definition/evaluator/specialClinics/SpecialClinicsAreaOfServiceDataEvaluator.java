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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsAreaOfServiceDataDefinition;
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
@Handler(supports= SpecialClinicsAreaOfServiceDataDefinition.class, order=50)
public class SpecialClinicsAreaOfServiceDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        SpecialClinicsAreaOfServiceDataDefinition cohortDefinition = (SpecialClinicsAreaOfServiceDataDefinition) definition;
        String specialClinic = cohortDefinition.getSpecialClinic();

        String qry = "select s.encounter_id,\n" +
                "       (case s.area_of_service\n" +
                "           when 160542 then 'CBR(Community-based rehabilitation)'\n" +
                "           when 159937 then 'MCH'\n" +
                "           when 164103 then 'Diabetic clinic'\n" +
                "           when 167396 then 'NBU(newborn unit)'\n" +
                "           when 5622 then 'Others'\n" +
                "           when 159927 then 'Outreach/Mobile Clinic' else '' end) as area_of_service \n" +
                "from kenyaemr_etl.etl_special_clinics s\n" +
                "where s.visit_date between date(:startDate) and date(:endDate)\n" +
                "  and s.special_clinic_form_uuid = '"+specialClinic+"';";

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
