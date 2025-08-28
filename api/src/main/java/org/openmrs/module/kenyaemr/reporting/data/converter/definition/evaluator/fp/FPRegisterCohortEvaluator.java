/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.fp;


import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.FPRegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.NCDRegisterCohortDefinition;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.openmrs.module.reporting.query.encounter.EncounterQueryResult;
import org.openmrs.module.reporting.query.encounter.definition.EncounterQuery;
import org.openmrs.module.reporting.query.encounter.evaluator.EncounterQueryEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Handler(supports= FPRegisterCohortDefinition.class, order=50)
public class FPRegisterCohortEvaluator implements EncounterQueryEvaluator {
    @Autowired
    private EvaluationService evaluationService;

    @Override
    public EncounterQueryResult evaluate(EncounterQuery definition, EvaluationContext context) throws EvaluationException {
        FPRegisterCohortDefinition fpRegisterCohortDefinition =new FPRegisterCohortDefinition();

        EncounterQueryResult c = new EncounterQueryResult(definition, context);

        // Ensure startDate and endDate are present in context
        Date startDate = (Date) context.getParameterValue("startDate");
        Date endDate = (Date) context.getParameterValue("endDate");

        if (startDate == null || endDate == null) {
            throw new EvaluationException("Missing required parameters: startDate or endDate");
        }

        String sql = "SELECT e.encounter_id " +
                "FROM kenyaemr_etl.etl_family_planning e " +
                "INNER JOIN kenyaemr_etl.etl_patient_demographics d ON e.patient_id = d.patient_id " +
                "WHERE date(e.visit_date) BETWEEN date(:startDate) AND date(:endDate)";

        SqlQueryBuilder q = new SqlQueryBuilder();
        q.append(sql);
        q.addParameter("startDate", startDate);
        q.addParameter("endDate", endDate);

        List<Integer> results = evaluationService.evaluateToList(q, Integer.class, context);
        c.getMemberIds().addAll(results);
        return c;
    }
}
