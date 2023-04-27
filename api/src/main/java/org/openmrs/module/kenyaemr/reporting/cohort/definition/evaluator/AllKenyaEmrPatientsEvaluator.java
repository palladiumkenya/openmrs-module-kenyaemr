/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.cohort.definition.evaluator;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.AllKenyaEmrPatientsCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.library.shared.queries.SharedQueries;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.evaluator.CohortDefinitionEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;


/**
 * Evaluates an PatientCharacteristicCohortDefinition and produces a Cohort
 */
@Handler(supports={AllKenyaEmrPatientsCohortDefinition.class})
public class AllKenyaEmrPatientsEvaluator implements CohortDefinitionEvaluator {

    private final EvaluationService evaluationService;

    @Autowired
    public AllKenyaEmrPatientsEvaluator(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @Override
    public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {
        AllKenyaEmrPatientsCohortDefinition def = (AllKenyaEmrPatientsCohortDefinition) cohortDefinition;
        EvaluatedCohort cohort = new EvaluatedCohort(context.getBaseCohort(), def, context);
        if (context.getBaseCohort() != null && context.getBaseCohort().isEmpty()) {
            return cohort;
        }
        SqlQueryBuilder query = new SqlQueryBuilder(SharedQueries.getBaseCohortQuery(def.getDefaultLocation()));

        List<Integer> ptIds = evaluationService.evaluateToList(query, Integer.class, context);
        cohort.setMemberIds(new HashSet<Integer>(ptIds));

        return cohort;
    }
}
