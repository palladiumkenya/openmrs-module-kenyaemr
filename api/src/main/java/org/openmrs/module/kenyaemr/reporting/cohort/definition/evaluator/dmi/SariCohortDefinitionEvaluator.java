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
import org.openmrs.module.kenyaemr.reporting.cohort.definition.dmi.SARICohortDefinition;
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
 * Evaluator for SARI Cohort
 */
@Handler(supports = { SARICohortDefinition.class })
public class SariCohortDefinitionEvaluator implements CohortDefinitionEvaluator {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	EvaluationService evaluationService;
	
	@Override
	public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

		SARICohortDefinition definition = (SARICohortDefinition) cohortDefinition;
		
		if (definition == null)
			return null;
		
		Cohort newCohort = new Cohort();
		
		String qry = "select a.patient_id\n" +
			"from (select patient_id, c.complaint as complaint, DATE_SUB(c.visit_date, INTERVAL c.complaint_duration DAY) as complaint_date, c.visit_date\n" +
			"      from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
			"     where c.complaint = 143264\n" +
			"       and c.complaint_duration < 10\n" +
			"        and date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
			"      group by patient_id) a\n" +
			"             left join kenyaemr_etl.etl_clinical_encounter e\n" +
			"                       on a.patient_id = e.patient_id and date(e.visit_date) between date(:startDate) and date(:endDate)\n" +
			"             left join kenyaemr_etl.etl_patient_triage t\n" +
			"                       on a.patient_id = t.patient_id and date(t.visit_date) between date(:startDate) and date(:endDate) and t.temperature >= 38\n" +
			"             join openmrs.visit v\n" +
			"                  on a.patient_id = v.patient_id and (v.visit_type_id = 3 or v.visit_type_id = 1) or e.patient_outcome = 1654\n" +
			"where e.patient_id is not null or t.patient_id is not null;";
		
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
