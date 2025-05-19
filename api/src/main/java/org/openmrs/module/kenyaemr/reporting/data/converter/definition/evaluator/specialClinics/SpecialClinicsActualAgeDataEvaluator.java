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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsActualAgeDataDefinition;
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
@Handler(supports = SpecialClinicsActualAgeDataDefinition.class, order = 50)
public class SpecialClinicsActualAgeDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        SpecialClinicsActualAgeDataDefinition cohortDefinition = (SpecialClinicsActualAgeDataDefinition) definition;
        String specialClinic = cohortDefinition.getSpecialClinic();

        String qry = "SELECT a.encounter_id, a.age\n" +
                "from (SELECT d.patient_id,\n" +
                "             sc.encounter_id,\n" +
                "             d.dob,\n" +
                "             CASE\n" +
                "                 WHEN DATEDIFF(sc.visit_date, d.dob) < 7 THEN CONCAT(DATEDIFF(DATE(:endDate), d.dob), ' days')\n" +
                "                 WHEN DATEDIFF(sc.visit_date, d.dob) < 30.44\n" +
                "                     THEN CONCAT(FLOOR(DATEDIFF(sc.visit_date, d.dob) / 7), ' weeks')\n" +
                "                 WHEN FLOOR(DATEDIFF(sc.visit_date, d.dob) / 365) > 0 AND\n" +
                "                      FLOOR((DATEDIFF(sc.visit_date, d.dob) % 365) / 30.44) > 0\n" +
                "                     THEN CONCAT(FLOOR(DATEDIFF(sc.visit_date, d.dob) / 365), ' years, ',\n" +
                "                                 FLOOR((DATEDIFF(sc.visit_date, d.dob) % 365) / 30.44), ' months')\n" +
                "                 WHEN FLOOR(DATEDIFF(sc.visit_date, d.dob) / 365) > 0\n" +
                "                     THEN CONCAT(FLOOR(DATEDIFF(sc.visit_date, d.dob) / 365), ' years')\n" +
                "                 WHEN FLOOR((DATEDIFF(sc.visit_date, d.dob) % 365) / 30.44) > 0 THEN CONCAT(\n" +
                "                         FLOOR((DATEDIFF(sc.visit_date, d.dob) % 365) / 30.44), ' months')\n" +
                "                 ELSE '0 days'\n" +
                "                 END AS age\n" +
                "      FROM kenyaemr_etl.etl_patient_demographics d\n" +
                "               INNER JOIN kenyaemr_etl.etl_special_clinics sc on d.patient_id = sc.patient_id\n" +
                "      WHERE sc.visit_date BETWEEN date(:startDate) AND date(:endDate)\n" +
                "        AND sc.special_clinic_form_uuid = '"+specialClinic+"') a;";

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
