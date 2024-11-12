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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.PopulationTypeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.CaseUniqueIDDataDefinition;
import org.openmrs.module.reporting.data.encounter.EvaluatedEncounterData;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDataDefinition;
import org.openmrs.module.reporting.data.encounter.evaluator.EncounterDataEvaluator;
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
 * Evaluates Visit type Data Definition
 */

@Handler(supports= CaseUniqueIDDataDefinition.class, order=50)
public class CaseUniqueIDDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry ="SELECT a.encounter_id, a.visit_id\n" +
                "                FROM (SELECT c.patient_id, c.encounter_id,c.visit_id, c.complaint AS complaint, DATE_SUB(c.visit_date, INTERVAL c.complaint_duration DAY) AS complaint_date, c.visit_date\n" +
                "                      FROM kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "                      WHERE DATE(c.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)) a\n" +
                "                JOIN openmrs.visit v\n" +
                "                     ON a.patient_id = v.patient_id AND DATE(a.visit_date) = DATE(v.date_started)\n" +
                "                JOIN kenyaemr_etl.etl_patient_triage t\n" +
                "                     ON a.patient_id = t.patient_id AND DATE(t.visit_date) = DATE(v.date_started)\n" +
                "                JOIN kenyaemr_etl.etl_clinical_encounter e\n" +
                "                     ON a.patient_id = e.patient_id AND DATE(a.visit_date) = DATE(e.visit_date);";

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
