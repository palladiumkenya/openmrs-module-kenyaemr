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
import org.openmrs.module.kenyaemr.reporting.cohort.definition.dmi.WateryDiarrhoeaCohortDefinition;
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
 * Evaluator for Watery Diarrhoea Cohort
 */
@Handler(supports = { WateryDiarrhoeaCohortDefinition.class })
public class WateryDiarrhoeaCohortDefinitionEvaluator implements CohortDefinitionEvaluator {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	EvaluationService evaluationService;
	
	@Override
	public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

		WateryDiarrhoeaCohortDefinition definition = (WateryDiarrhoeaCohortDefinition) cohortDefinition;
		
		if (definition == null)
			return null;
		
		Cohort newCohort = new Cohort();

		String qry = "SELECT a.patient_id\n" +
				"FROM (\n" +
				"    SELECT \n" +
				"        patient_id, \n" +
				"        c.visit_date,\n" +
				"        GROUP_CONCAT(c.complaint) AS complaint, \n" +
				"        DATE_SUB(c.visit_date, INTERVAL c.complaint_duration DAY) AS complaint_date,\n" +
				"        c.complaint_duration\n" +
				"    FROM \n" +
				"        kenyaemr_etl.etl_allergy_chronic_illness c\n" +
				"    WHERE \n" +
				"        c.complaint = 161887\n" +
				"        AND c.complaint_duration < 14\n" +
				"        AND DATE(c.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
				"    GROUP BY patient_id) a\n" +
				"WHERE FIND_IN_SET(161887, a.complaint) > 0;;";
		
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
