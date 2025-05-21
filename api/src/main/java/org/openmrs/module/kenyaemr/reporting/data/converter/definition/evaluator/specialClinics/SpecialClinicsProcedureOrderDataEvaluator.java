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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsProcedureOrderDataDefinition;
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
@Handler(supports= SpecialClinicsProcedureOrderDataDefinition.class, order=50)
public class SpecialClinicsProcedureOrderDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        SpecialClinicsProcedureOrderDataDefinition cohortDefinition = (SpecialClinicsProcedureOrderDataDefinition) definition;
        String specialClinic = cohortDefinition.getSpecialClinic();

        String qry = "select c.encounter_id,d.procedures from kenyaemr_etl.etl_special_clinics c inner join\n" +
                "(SELECT d.patient_id, GROUP_CONCAT(DISTINCT n.name SEPARATOR '\\\\r\\\\n') AS procedures,DATE(d.date_created) as visit_date\n" +
                "FROM orders d INNER JOIN concept_name n ON d.concept_id = n.concept_id AND n.locale = 'en'\n" +
                "inner join kenyaemr_etl.etl_special_clinics c on d.patient_id = c.patient_id and date(c.visit_date) = date(d.date_created)\n" +
                "WHERE n.concept_name_type = 'FULLY_SPECIFIED' AND d.voided = 0\n" +
                "GROUP BY d.patient_id, d.encounter_id, DATE(d.date_created))d on c.patient_id = d.patient_id and c.visit_date = d.visit_date\n" +
                "where c.visit_date BETWEEN DATE(:startDate) AND DATE(:endDate) and c.special_clinic_form_uuid = '" + specialClinic + "';" ;

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
