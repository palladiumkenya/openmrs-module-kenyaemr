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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsLinkedToOVCDataDefinition;
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
 */
@Handler(supports= SpecialClinicsLinkedToOVCDataDefinition.class, order=50)
public class SpecialClinicsLinkedToOVCDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        SpecialClinicsLinkedToOVCDataDefinition cohortDefinition = (SpecialClinicsLinkedToOVCDataDefinition) definition;
        String specialClinic = cohortDefinition.getSpecialClinic();

        String qry = "select sc.encounter_id, if(ovc.patient_id is not null, 'Yes', 'No') as linked_to_ovc\n" +
                "from kenyaemr_etl.etl_special_clinics sc\n" +
                "         left join\n" +
                "     (select a.patient_id\n" +
                "      from ( -- OVC Clients\n" +
                "               select e.patient_id,\n" +
                "                      d.patient_id        as disc_patient,\n" +
                "                      d.date_discontinued as date_discontinued,\n" +
                "                      max(e.visit_date)   as enrollment_date\n" +
                "               from kenyaemr_etl.etl_ovc_enrolment e\n" +
                "                        join kenyaemr_etl.etl_patient_demographics p\n" +
                "                             on p.patient_id = e.patient_id and p.voided = 0 and p.dead = 0\n" +
                "                        left outer JOIN\n" +
                "                    (select patient_id, max(visit_date) as date_discontinued\n" +
                "                     from kenyaemr_etl.etl_patient_program_discontinuation\n" +
                "                     where program_name = 'OVC'\n" +
                "                     group by patient_id) d on d.patient_id = e.patient_id\n" +
                "               group by e.patient_id\n" +
                "               having (disc_patient is null or date(enrollment_date) >= date(date_discontinued))) a\n" +
                "               inner join\n" +
                "           -- TX_CURR Clients\n" +
                "               (select fup.visit_date,\n" +
                "                       fup.patient_id,\n" +
                "                       max(e.visit_date)                                                      as enroll_date,\n" +
                "                       greatest(max(fup.visit_date), ifnull(max(d.visit_date), '0000-00-00')) as latest_vis_date,\n" +
                "                       greatest(mid(max(concat(fup.visit_date, fup.next_appointment_date)), 11),\n" +
                "                                ifnull(max(d.visit_date), '0000-00-00'))                      as latest_tca,\n" +
                "                       d.patient_id                                                           as disc_patient,\n" +
                "                       d.effective_disc_date                                                  as effective_disc_date,\n" +
                "                       max(d.visit_date)                                                      as date_discontinued,\n" +
                "                       de.patient_id                                                          as started_on_drugs\n" +
                "                from kenyaemr_etl.etl_patient_hiv_followup fup\n" +
                "                         join kenyaemr_etl.etl_patient_demographics p on p.patient_id = fup.patient_id\n" +
                "                         join kenyaemr_etl.etl_hiv_enrollment e on fup.patient_id = e.patient_id\n" +
                "                         left join kenyaemr_etl.etl_drug_event de\n" +
                "                                   on e.patient_id = de.patient_id and de.program = 'HIV' and\n" +
                "                                      date(date_started) <= date(:endDate)\n" +
                "                         left outer JOIN\n" +
                "                     (select patient_id,\n" +
                "                             coalesce(date(effective_discontinuation_date), visit_date) visit_date,\n" +
                "                             max(date(effective_discontinuation_date)) as               effective_disc_date\n" +
                "                      from kenyaemr_etl.etl_patient_program_discontinuation\n" +
                "                      where date(visit_date) <= date(:endDate)\n" +
                "                        and program_name = 'HIV'\n" +
                "                      group by patient_id) d on d.patient_id = fup.patient_id\n" +
                "                where fup.visit_date <= date(:endDate)\n" +
                "                group by patient_id\n" +
                "                having (started_on_drugs is not null and started_on_drugs <> '')\n" +
                "                   and (\n" +
                "                    (\n" +
                "                        (timestampdiff(DAY, date(latest_tca), date(:endDate)) <= 30 and\n" +
                "                         ((date(d.effective_disc_date) > date(:endDate) or\n" +
                "                           date(enroll_date) > date(d.effective_disc_date)) or d.effective_disc_date is null))\n" +
                "                            and\n" +
                "                        (date(latest_vis_date) >= date(date_discontinued) or\n" +
                "                         date(latest_tca) >= date(date_discontinued) or\n" +
                "                         disc_patient is null)\n" +
                "                        )\n" +
                "                    )) t\n" +
                "           on a.patient_id = t.patient_id) ovc on sc.patient_id = ovc.patient_id\n" +
                "where sc.visit_date between date(:startDate) and date(:endDate) and sc.special_clinic_form_uuid = '"+specialClinic+"';";

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
