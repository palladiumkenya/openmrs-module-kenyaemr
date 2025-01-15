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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsOTInterventionDataDefinition;
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
 */
@Handler(supports= SpecialClinicsOTInterventionDataDefinition.class, order=50)
public class SpecialClinicsOTInterventionDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        SpecialClinicsOTInterventionDataDefinition cohortDefinition = (SpecialClinicsOTInterventionDataDefinition) definition;
        String specialClinic = cohortDefinition.getSpecialClinic();

        String qry = "        select v.encounter_id,\n" +
                "                (case v.ot_intervention when 1107 then 'None' when 164806 then 'Neonatal Screening' when 168287 then 'Initial Assessment'\n" +
                "                when 168031 then 'Re-Assessment' when 2001261 then 'PWDs Assessment and Categorisation' when 169149 then 'Environmental Assessment'\n" +
                "                when 2001260 then 'Assistive Technology Assessment' when 527 then 'Splinting' when 2002061 then 'Activities of Daily Living Training' \n" +
                "                when 2002060 then 'Hand Therapy' when 163318 then 'Developmental Skills Training' when 1000534 then 'Multi sensory screening' \n" +
                "                when 2002026 then 'Therapeutic Activities' when 2000823 then 'Sensory Stimulation' when 160130 then 'Vocational Training'\n" +
                "                when 164518 then 'Bladder and Bowel Management' when 164872 then 'Environmental Adaptations' when 2002045 then 'OT Screening' \n" +
                "                when 167696 then 'Individual Psychotherapy' when 166724 then 'Scar Management' when 167695 then 'Group Psychotherapy'  when 167809 then 'Health Education/ Patient Education' \n" +
                "                when 165001 then 'Home Visits (Interventions)' when 167277 then 'Recreation Therapy' when 2001249 then 'Therapeutic play' when 161625 then 'OT in critical care'  \n" +
                "                when 167813 then 'OT Sexual health' when 160351 then 'Teletherapy' when 163579 then 'Fine and gross motor skills training' when 160563 then 'Referrals IN' \n" +
                "                when 159492 then 'Referrals OUT' when 1692 then 'Discharge on recovery' when 5622 then 'Other' else '' end) as ot_intervention\n" +
                "                from kenyaemr_etl.etl_special_clinics v\n" +
                "                where date(v.visit_date) between date(:startDate) and date(:endDate)  and special_clinic = :specialClinic;";

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
