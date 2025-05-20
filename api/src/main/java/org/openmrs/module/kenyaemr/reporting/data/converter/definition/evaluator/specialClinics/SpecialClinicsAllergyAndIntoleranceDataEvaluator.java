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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsAllergyAndIntoleranceDataDefinition;
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
 * Evaluates Linked to OVC
 * OPD Register
 */
@Handler(supports = SpecialClinicsAllergyAndIntoleranceDataDefinition.class, order = 50)
public class SpecialClinicsAllergyAndIntoleranceDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        SpecialClinicsAllergyAndIntoleranceDataDefinition cohortDefinition = (SpecialClinicsAllergyAndIntoleranceDataDefinition) definition;
        String specialClinic = cohortDefinition.getSpecialClinic();

        String qry = "\n" +
                "select a.encounter_id,GROUP_CONCAT(a.AllergyCausativeAgent,'') as allergy_intolerance from (\n" +
                "select sc.encounter_id,\n" +
                "       sc.patient_id,\n" +
                "       group_concat(DISTINCT case ci.allergy_causative_agent\n" +
                "                        when 162543 then 'Beef'\n" +
                "                        when 72609 then 'Caffeine'\n" +
                "                        when 162544 then 'Chocolate'\n" +
                "                        when 162545 then 'Dairy Food'\n" +
                "                        when 162171 then 'Eggs'\n" +
                "                        when 162546 then 'Fish'\n" +
                "                        when 162547 then 'Milk Protein'\n" +
                "                        when 162172 then 'Peanuts'\n" +
                "                        when 162175 then 'Shellfish'\n" +
                "                        when 162176 then 'Soy'\n" +
                "                        when 162548 then 'Strawberries'\n" +
                "                        when 162177 then 'Wheat'\n" +
                "                        when 162542 then 'Adhesive Tape'\n" +
                "                        when 162536 then 'Bee Stings'\n" +
                "                        when 162537 then 'Dust'\n" +
                "                        when 162538 then 'Latex'\n" +
                "                        when 162539 then 'Mold'\n" +
                "                        when 162540 then 'Pollen'\n" +
                "                        when 162541 then 'Ragweed'\n" +
                "                        when 5622 then 'Other'\n" +
                "                        end SEPARATOR\n" +
                "                    ',') as                          AllergyCausativeAgent\n" +
                "from kenyaemr_etl.etl_allergy_chronic_illness ci\n" +
                "         inner join kenyaemr_etl.etl_special_clinics sc on ci.patient_id = sc.patient_id\n" +
                "where sc.special_clinic_form_uuid = '"+specialClinic+"'\n" +
                "and ci.allergy_causative_agent is not null\n" +
                "and date(sc.visit_date) BETWEEN date(:startDate) AND date(:endDate)\n" +
                "group by sc.encounter_id, sc.patient_id\n" +
                "UNION ALL\n" +
                "#Adverse events for C&T\n" +
                "select sc.encounter_id,sc.patient_id,\n" +
                "       group_concat(case a.cause\n" +
                "                        when 70056 then 'Abicavir'\n" +
                "                        when 162298 then 'ACE inhibitors'\n" +
                "                        when 70878 then 'Allopurinol'\n" +
                "                        when 155060 then 'Aminoglycosides'\n" +
                "                        when 162299 then 'ARBs (angiotensin II receptor blockers)'\n" +
                "                        when 103727 then 'Aspirin'\n" +
                "                        when 71647 then 'Atazanavir'\n" +
                "                        when 72822 then 'Carbamazepine'\n" +
                "                        when 162301 then 'Cephalosporins'\n" +
                "                        when 73300 then 'Chloroquine'\n" +
                "                        when 73667 then 'Codeine'\n" +
                "                        when 74807 then 'Didanosine'\n" +
                "                        when 75523 then 'Efavirenz'\n" +
                "                        when 162302 then 'Erythromycins'\n" +
                "                        when 75948 then 'Ethambutol'\n" +
                "                        when 77164 then 'Griseofulvin'\n" +
                "                        when 162305 then 'Heparins'\n" +
                "                        when 77675 then 'Hydralazine'\n" +
                "                        when 78280 then 'Isoniazid'\n" +
                "                        when 794 then 'Lopinavir/ritonavir'\n" +
                "                        when 80106 then 'Morphine'\n" +
                "                        when 80586 then 'Nevirapine'\n" +
                "                        when 80696 then 'Nitrofurans'\n" +
                "                        when 162306 then 'Non-steroidal anti-inflammatory drugs'\n" +
                "                        when 81723 then 'Penicillamine'\n" +
                "                        when 81724 then 'Penicillin'\n" +
                "                        when 81959 then 'Phenolphthaleins'\n" +
                "                        when 82023 then 'Phenytoin'\n" +
                "                        when 82559 then 'Procainamide'\n" +
                "                        when 82900 then 'Pyrazinamide'\n" +
                "                        when 83018 then 'Quinidine'\n" +
                "                        when 767 then 'Rifampin'\n" +
                "                        when 162307 then 'Statins'\n" +
                "                        when 84309 then 'Stavudine'\n" +
                "                        when 162170 then 'Sulfonamides'\n" +
                "                        when 84795 then 'Tenofovir'\n" +
                "                        when 84893 then 'Tetracycline'\n" +
                "                        when 86663 then 'Zidovudine'\n" +
                "                        when 5622 then 'Other' end SEPARATOR ',')   as allergy_intolerance\n" +
                "from kenyaemr_etl.etl_adverse_events a\n" +
                "inner join kenyaemr_etl.etl_special_clinics sc on a.patient_id = sc.patient_id\n" +
                "where sc.special_clinic_form_uuid = '"+specialClinic+"'\n" +
                "AND date(sc.visit_date) BETWEEN date(:startDate) AND date(:endDate)\n" +
                "group by sc.patient_id, sc.encounter_id\n" +
                "UNION ALL\n" +
                "SELECT sc.encounter_id,\n" +
                "       sc.patient_id,\n" +
                "       GROUP_CONCAT(DISTINCT n.name SEPARATOR ',') AS allergy_intolerance\n" +
                "FROM kenyaemr_etl.etl_special_clinics sc\n" +
                "INNER JOIN openmrs.allergy a on sc.patient_id = a.patient_id\n" +
                "         INNER JOIN openmrs.concept_name n\n" +
                "                    ON a.coded_allergen = n.concept_id\n" +
                "                        AND n.locale = 'en'\n" +
                "WHERE n.concept_name_type = 'FULLY_SPECIFIED'\n" +
                "  AND a.voided = 0 AND sc.special_clinic_form_uuid = '"+specialClinic+"'\n" +
                "AND date(sc.visit_date) BETWEEN date(:startDate) AND date(:endDate)\n" +
                "GROUP BY sc.patient_id, sc.encounter_id)a group by a.patient_id;";

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
