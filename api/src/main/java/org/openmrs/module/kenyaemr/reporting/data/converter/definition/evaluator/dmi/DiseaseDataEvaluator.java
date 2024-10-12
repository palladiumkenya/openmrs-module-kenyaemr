/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.dmi;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.DiseaseDataDefinition;
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
 * Evaluates a DiseaseDataDefinition
 */
@Handler(supports= DiseaseDataDefinition.class, order=50)
public class DiseaseDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

//        String qry = "select a.patient_id,\n" +
//                "                       CASE\n" +
//                "                           WHEN FIND_IN_SET(117671, a.complaint) > 0 AND FIND_IN_SET(142412, a.complaint) > 0 THEN 'Dysentery'\n" +
//                "                           WHEN FIND_IN_SET(142412, a.complaint) > 0 AND FIND_IN_SET(122983, a.complaint) > 0 AND\n" +
//                "                                a.complaint_duration > 2\n" +
//                "                               THEN 'Cholera'\n" +
//                "                           WHEN a.complaint = 143264 AND a.complaint_duration < 10 AND\n" +
//                "                                date(a.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate) AND a.visit_type_id = 1 AND\n" +
//                "                                a.temperature >= 38 THEN 'ILI'\n" +
//                "                           WHEN a.complaint = 143264 AND a.complaint_duration < 10 AND\n" +
//                "                                date(a.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate) AND a.visit_type_id = 3 AND\n" +
//                "                                a.temperature >= 38 THEN 'SARI'\n" +
//                "                           WHEN FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(141830, a.complaint) > 0 AND\n" +
//                "                                FIND_IN_SET(136443, a.complaint) > 0 AND FIND_IN_SET(135367, a.complaint) > 0 AND\n" +
//                "                                a.fever_duration_from_days > 2 THEN 'Rift Valley Fever'\n" +
//                "                           WHEN FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(139084, a.complaint) > 0 AND\n" +
//                "                                FIND_IN_SET(871, a.complaint) > 0 AND a.fever_duration_from_days > 1 THEN 'Malaria'\n" +
//                "                           WHEN FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(116558, a.complaint) > 0 AND a.temperature > 38.5 AND a.fever_duration_from_days > 2\n" +
//                "                               THEN 'Chikungunya'\n" +
//                "                           WHEN FIND_IN_SET(157498, a.complaint) > 0 AND timestampdiff(YEAR, a.DOB, a.visit_date) <= 15\n" +
//                "                               THEN 'Poliomyelitis'\n" +
//                "                           WHEN FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(162628, a.complaint) > 0 AND\n" +
//                "                                a.fever_duration_from_days >= 3 THEN 'Viral Haemorrhagic Fever'\n" +
//                "                           WHEN FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(512, a.complaint) > 0 AND\n" +
//                "                                FIND_IN_SET(106, a.complaint) > 0 AND FIND_IN_SET(516, a.complaint) > 0 AND\n" +
//                "                                FIND_IN_SET(143264, a.complaint) > 0  AND a.fever_duration_from_days > 2 THEN 'Measles'\n" +
//                "                           WHEN FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(512, a.complaint) > 0 AND FIND_IN_SET(139084, a.complaint) > 0 AND a.temperature > 38.5\n" +
//                "                               or FIND_IN_SET(135488, a.complaint) > 0\n" +
//                "                               or FIND_IN_SET(121, a.complaint) > 0\n" +
//                "                               or FIND_IN_SET(148035, a.complaint) > 0 THEN 'Mpox'\n" +
//                "                           WHEN FIND_IN_SET(6017, a.complaint) > 0\n"+
//                "                               AND FIND_IN_SET(113054, a.complaint) > 0\n" +
//                "                               AND TIMESTAMPDIFF(DAY, d.DOB, a.visit_date) BETWEEN 2 AND 28 THEN 'Neurological Syndrome'\n" +
//                "                           WHEN    FIND_IN_SET(161887, a.complaint) > 0 \n"+
//                "                               AND a.complaint_duration < 14 THEN 'Acute Watery Diarrhoea'\n"+
//                "                           WHEN    FIND_IN_SET(140238, a.complaint) > 0 \n"+
//                "                               AND a.complaint_duration < 14 \n"+
//                "                               AND FIND_IN_SET(512, a.complaint) > 0 THEN 'Acute Febrile Rash Infections'\n"+
//                "                          WHEN    FIND_IN_SET(140238, a.complaint) > 0 \n"+
//                "                               AND a.complaint_duration < 14 \n"+
//                "                                date(a.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate) AND\n" +
//                "                                a.temperature >= 38 THEN 'Acute Febrile Illness'\n" +
//                "                          WHEN FIND_IN_SET(112721, a.complaint) > 0 THEN 'Acute Meningitis and Encephalitis'\n" +
//                "                           END AS disease\n" +
        String qry = "select a.patient_id,\n" +
                "                       CASE\n" +
                "                           WHEN FIND_IN_SET(117671, a.complaint) > 0 AND FIND_IN_SET(142412, a.complaint) > 0 THEN 'Dysentery'\n" +
                "                           WHEN FIND_IN_SET(142412, a.complaint) > 0 AND FIND_IN_SET(122983, a.complaint) > 0 AND\n" +
                "                                a.complaint_duration > 2 THEN 'Cholera'\n" +
                "                           WHEN a.complaint = 143264 AND a.complaint_duration < 10 AND\n" +
                "                                date(a.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate) AND a.visit_type_id = 1 AND\n" +
                "                                a.temperature >= 38 THEN 'ILI'\n" +
                "                           WHEN a.complaint = 143264 AND a.complaint_duration < 10 AND\n" +
                "                                date(a.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate) AND a.visit_type_id = 3 AND\n" +
                "                                a.temperature >= 38 THEN 'SARI'\n" +
                "                           WHEN FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(141830, a.complaint) > 0 AND\n" +
                "                                FIND_IN_SET(136443, a.complaint) > 0 AND FIND_IN_SET(135367, a.complaint) > 0 AND\n" +
                "                                a.fever_duration_from_days > 2 THEN 'Rift Valley Fever'\n" +
                "                           WHEN FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(139084, a.complaint) > 0 AND\n" +
                "                                FIND_IN_SET(871, a.complaint) > 0 AND a.fever_duration_from_days > 1 THEN 'Malaria'\n" +
                "                           WHEN FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(116558, a.complaint) > 0 AND a.temperature > 38.5 AND a.fever_duration_from_days > 2 THEN 'Chikungunya'\n" +
                "                           WHEN FIND_IN_SET(157498, a.complaint) > 0 AND timestampdiff(YEAR, a.DOB, a.visit_date) <= 15 THEN 'Acute Flaccid Paralysis'\n" +
                "                           WHEN FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(162628, a.complaint) > 0 AND\n" +
                "                                a.fever_duration_from_days >= 3 THEN 'Acute Haemorrhagic Fever'\n" +
                "                           WHEN FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(512, a.complaint) > 0 AND\n" +
                "                                FIND_IN_SET(106, a.complaint) > 0 AND FIND_IN_SET(516, a.complaint) > 0 AND\n" +
                "                                FIND_IN_SET(143264, a.complaint) > 0 AND a.fever_duration_from_days > 2 THEN 'Measles'\n" +
                "                           WHEN FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(512, a.complaint) > 0 AND FIND_IN_SET(139084, a.complaint) > 0 AND a.temperature > 38.5\n" +
                "                               OR FIND_IN_SET(135488, a.complaint) > 0\n" +
                "                               OR FIND_IN_SET(121, a.complaint) > 0\n" +
                "                               OR FIND_IN_SET(148035, a.complaint) > 0 THEN 'Mpox'\n" +
                "                           WHEN FIND_IN_SET(6017, a.complaint) > 0\n" +
                "                               AND FIND_IN_SET(113054, a.complaint) > 0\n" +
                "                               AND TIMESTAMPDIFF(DAY, a.DOB, a.visit_date) BETWEEN 2 AND 28 THEN 'Neurological Syndrome'\n" +
                "                           WHEN FIND_IN_SET(161887, a.complaint) > 0\n" +
                "                               AND a.complaint_duration < 14 THEN 'Acute Watery Diarrhoea'\n" +
                "                           WHEN FIND_IN_SET(140238, a.complaint) > 0\n" +
                "                               AND a.complaint_duration < 14\n" +
                "                               AND FIND_IN_SET(512, a.complaint) > 0 THEN 'Acute Febrile Rash Infections'\n" +
                "                           WHEN FIND_IN_SET(140238, a.complaint) > 0\n" +
                "                               AND a.complaint_duration < 14\n" +
                "                               AND date(a.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate) AND\n" +
                "                               a.temperature >= 38 THEN 'Acute Febrile Illness'\n" +
                "                           WHEN FIND_IN_SET(112721, a.complaint) > 0 THEN 'Acute Meningitis and Encephalitis'\n" +
                "                           END AS disease\n"+
                "                from (select c.patient_id,\n" +
                "                             group_concat(c.complaint) as complaint,\n" +
                "                             epd.dob,\n" +
                "                             DATE_SUB(c.visit_date, INTERVAL c.complaint_duration DAY),\n" +
                "                             c.complaint_duration,\n" +
                "                             c.visit_date,\n" +
                "                             v.visit_type_id,\n" +
                "                             t.temperature,\n" +
                "                             CASE\n" +
                "                                 WHEN group_concat(concat_ws('|', c.complaint, c.complaint_duration)) LIKE '%140238%' THEN\n" +
                "                                     SUBSTRING_INDEX(\n" +
                "                                             SUBSTRING_INDEX(group_concat(concat_ws('|', c.complaint, c.complaint_duration)), '|', -1),\n" +
                "                                             ',', 1)\n" +
                "                                 END                   AS fever_duration_from_days\n" +
                "                      from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "                               inner join kenyaemr_etl.etl_patient_demographics epd on c.patient_id = epd.patient_id\n" +
                "                               inner join openmrs.visit v ON c.patient_id = v.patient_id AND DATE(c.visit_date) = DATE(v.date_started)\n" +
                "                               left join kenyaemr_etl.etl_patient_triage t\n" +
                "                                         on c.patient_id = t.patient_id and\n" +
                "                                            date(t.visit_date) between date(:startDate) and date(:endDate)\n" +
                "                      group by patient_id) a\n" +
                "                where (FIND_IN_SET(117671, a.complaint) > 0 AND FIND_IN_SET(142412, a.complaint) > 0)\n" +
                "                   OR (FIND_IN_SET(142412, a.complaint) > 0 AND FIND_IN_SET(122983, a.complaint) > 0 AND\n" +
                "                       a.complaint_duration > 2)\n" +
                "                   OR (FIND_IN_SET(143264, a.complaint) > 0 AND a.complaint_duration < 10 AND\n" +
                "                       date(a.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate) AND a.visit_type_id IN (1, 3) AND\n" +
                "                       a.temperature >= 38)\n" +
                "                   OR (FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(141830, a.complaint) > 0 AND\n" +
                "                       FIND_IN_SET(136443, a.complaint) > 0 AND FIND_IN_SET(135367, a.complaint) > 0 AND a.fever_duration_from_days > 2)\n" +
                "                   OR (FIND_IN_SET(40238, a.complaint) > 0 AND FIND_IN_SET(139084, a.complaint) > 0 AND\n" +
                "                       FIND_IN_SET(871, a.complaint) > 0 AND a.fever_duration_from_days > 1)\n" +
                "                   OR (FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(116558, a.complaint) > 0 AND a.temperature > 38.5 AND a.fever_duration_from_days > 2)\n" +
                "                   OR (a.complaint = 157498 AND timestampdiff(YEAR, a.DOB, a.visit_date) <= 15)\n" +
                "                   OR (FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(162628, a.complaint) > 0 AND\n" +
                "                       a.fever_duration_from_days >= 3)\n" +
                "                   OR (FIND_IN_SET(140238, a.complaint) AND FIND_IN_SET(512, a.complaint) AND FIND_IN_SET(106, a.complaint) AND\n" +
                "                       FIND_IN_SET(516, a.complaint) AND FIND_IN_SET(143264, a.complaint) > 0 AND a.fever_duration_from_days > 2)\n" +
                "                    and date(a.visit_date) between date(:startDate) and date(:endDate)\n" +
                "                group by patient_id;";

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