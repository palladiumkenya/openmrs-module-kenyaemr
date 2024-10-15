/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.cohort.definition.evaluator.dmi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.dmi.NeurologicalSyndromeCohortDefinition;
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
 * Evaluator for Neurological Syndrome Cohort
 */
@Handler(supports = { NeurologicalSyndromeCohortDefinition.class })
public class NeurologicalSyndromeCohortDefinitionEvaluator implements CohortDefinitionEvaluator {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	EvaluationService evaluationService;
	
	@Override
	public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

		NeurologicalSyndromeCohortDefinition definition = (NeurologicalSyndromeCohortDefinition) cohortDefinition;
		
		if (definition == null)
			return null;
		
		Cohort newCohort = new Cohort();

		String qry = " SELECT a.patient_id\n" +
				"FROM (\n" +
				"    SELECT patient_id, c.visit_date, GROUP_CONCAT(c.complaint) AS complaint\n" +
				"    FROM kenyaemr_etl.etl_allergy_chronic_illness c\n" +
				"    WHERE c.complaint IN (6017, 113054)\n" +
				"      AND DATE(c.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
				"    GROUP BY patient_id\n" +
				") a\n" +
				"JOIN kenyaemr_etl.etl_patient_demographics d ON a.patient_id = d.patient_id\n" +
				"JOIN kenyaemr_etl.etl_patient_triage t ON a.patient_id = t.patient_id\n" +
				"    AND DATE(t.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
				"WHERE FIND_IN_SET(6017, a.complaint) > 0\n" +
				"  AND FIND_IN_SET(113054, a.complaint) > 0\n" +
				"  AND TIMESTAMPDIFF(DAY, d.DOB, a.visit_date) BETWEEN 2 AND 28;";

		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.append(qry);
		Date startDate = (Date) context.getParameterValue("startDate");
		Date endDate = (Date) context.getParameterValue("endDate");
		builder.addParameter("startDate", startDate);
		builder.addParameter("endDate", endDate);
		
		List<Integer> ptIds = evaluationService.evaluateToList(builder, Integer.class, context);
		newCohort.setMemberIds(new HashSet<Integer>(ptIds));
		return new EvaluatedCohort(newCohort, definition, context);
	}
}
