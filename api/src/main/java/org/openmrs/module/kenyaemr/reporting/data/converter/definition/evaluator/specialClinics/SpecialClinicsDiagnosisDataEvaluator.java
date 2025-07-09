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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsDiagnosisDataDefinition;
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
@Handler(supports= SpecialClinicsDiagnosisDataDefinition.class, order=50)
public class SpecialClinicsDiagnosisDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        SpecialClinicsDiagnosisDataDefinition cohortDefinition = (SpecialClinicsDiagnosisDataDefinition) definition;
        String specialClinic = cohortDefinition.getSpecialClinic();

        String qry = "SELECT c.encounter_id, d.diagnosis\n" +
                "FROM kenyaemr_etl.etl_special_clinics c\n" +
                "INNER JOIN (\n" +
                "    SELECT d.patient_id,\n" +
                "           GROUP_CONCAT(DISTINCT n.name SEPARATOR '\r\n') AS diagnosis,\n" +
                "           DATE(d.date_created) AS visit_date\n" +
                "    FROM encounter_diagnosis d\n" +
                "    INNER JOIN concept_name n\n" +
                "        ON d.diagnosis_coded = n.concept_id\n" +
                "        AND n.locale = 'en'\n" +
                "    WHERE n.concept_name_type = 'FULLY_SPECIFIED'\n" +
                "      AND d.voided = 0\n" +
                "      AND (\n" +
                "          (d.dx_rank = 1 AND NOT EXISTS (\n" +
                "              SELECT 1 FROM encounter_diagnosis ed WHERE ed.patient_id = d.patient_id AND ed.dx_rank = 2\n" +
                "          ))\n" +
                "          OR (d.dx_rank = 2 AND NOT EXISTS (\n" +
                "              SELECT 1 FROM encounter_diagnosis ed WHERE ed.patient_id = d.patient_id AND ed.dx_rank = 1\n" +
                "          ))\n" +
                "          OR (d.dx_rank = 2 AND EXISTS (\n" +
                "              SELECT 1 FROM encounter_diagnosis ed WHERE ed.patient_id = d.patient_id AND ed.dx_rank = 1\n" +
                "          ))\n" +
                "      )\n" +
                "    GROUP BY d.patient_id, DATE(d.date_created)\n" +
                ") d ON c.patient_id = d.patient_id AND DATE(c.visit_date) = d.visit_date\n" +
                "WHERE c.visit_date between date(:startDate) and date(:endDate)\n" +
                "   and c.special_clinic_form_uuid = '"+specialClinic+"';";

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
