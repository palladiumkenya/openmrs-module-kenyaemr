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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.ClinicianWorkloadCohortDefinition;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.evaluator.CohortDefinitionEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Evaluator for ClinicianWorkloadCohortDefinition
 * This cohort definition is used to find patients who have been checked in within a period
 */
@Handler(supports = {ClinicianWorkloadCohortDefinition.class})
public class ClinicianWorkloadCohortDefinitionEvaluator implements CohortDefinitionEvaluator {

    private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	EvaluationService evaluationService;

    @Override
    public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

		ClinicianWorkloadCohortDefinition definition = (ClinicianWorkloadCohortDefinition) cohortDefinition;

        if (definition == null)
            return null;

		Cohort newCohort = new Cohort();

		String qry="WITH filtered_patients AS (SELECT openmrs_id, patient_id\n" +
				"                           FROM kenyaemr_etl.etl_patient_demographics\n" +
				"                           WHERE voided = 0),\n" +
				"     checked_in AS (SELECT v.visit_id, v.patient_id, v.creator, date(v.date_started) as checkin_date\n" +
				"                    FROM openmrs.visit v\n" +
				"                             JOIN filtered_patients p ON p.patient_id = v.patient_id\n" +
				"                    WHERE v.voided = 0\n" +
				"                      AND date(v.date_started) between date(:startDate) and date(:endDate))\n" +
				"SELECT v.patient_id\n" +
				"FROM filtered_patients fp\n" +
				"         INNER JOIN checked_in v ON v.patient_id = fp.patient_id\n" +
				"WHERE v.checkin_date between date(:startDate) and date(:endDate);";

		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.append(qry);
		Date startDate = (Date)context.getParameterValue("startDate");
		Date endDate = (Date)context.getParameterValue("endDate");
		builder.addParameter("startDate", startDate);
		builder.addParameter("endDate", endDate);

		List<Integer> ptIds = evaluationService.evaluateToList(builder, Integer.class, context);
		newCohort.setMemberIds(new HashSet<Integer>(ptIds));
		return new EvaluatedCohort(newCohort, definition, context);
    }
}
