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
import org.openmrs.module.kenyaemr.reporting.cohort.definition.dmi.MpoxCohortDefinition;
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
 * Evaluator for MPox Cohort
 */
@Handler(supports = { MpoxCohortDefinition.class })
public class MpoxCohortDefinitionEvaluator implements CohortDefinitionEvaluator {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	EvaluationService evaluationService;
	
	@Override
	public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

		MpoxCohortDefinition definition = (MpoxCohortDefinition) cohortDefinition;
		
		if (definition == null)
			return null;
		
		Cohort newCohort = new Cohort();

		String sqlQuery = "select a.patient_id\n" +
				"        from (select patient_id, c.visit_date, group_concat(c.complaint) as complaint,\n" +
				"        CASE\n" +
				"            WHEN group_concat(concat_ws('|', c.complaint, c.complaint_duration)) LIKE '%140238%' THEN\n" +
				"                SUBSTRING_INDEX(SUBSTRING_INDEX(group_concat(concat_ws('|', c.complaint, c.complaint_duration)), '|', -1), ',', 1)\n" +
				"        END AS fever_duration_from_days\n" +
				"        from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
				"        where c.complaint in (140238, 512, 139084, 135488, 121, 148035)\n" +
				"        and date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
				"        group by patient_id) a\n" +
				"        join kenyaemr_etl.etl_patient_demographics d on a.patient_id = d.patient_id\n" +
				"        join kenyaemr_etl.etl_patient_triage t on a.patient_id = t.patient_id\n" +
				"        and date(t.visit_date) between date(:startDate) and date(:endDate)\n" +
				"        and t.temperature > 38.5\n" +
				"        and date(a.visit_date) between date(:startDate) and date(:endDate)\n" +
				"        where FIND_IN_SET(140238, a.complaint) > 0\n" +
				"        and FIND_IN_SET(512, a.complaint) > 0\n" +
				"        and (FIND_IN_SET(139084, a.complaint) > 0\n" +
				"        or FIND_IN_SET(135488, a.complaint) > 0\n" +
				"        or FIND_IN_SET(121, a.complaint) > 0\n" +
				"        or FIND_IN_SET(148035, a.complaint) > 0);";

		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.append(sqlQuery);
		Date startDate = (Date) context.getParameterValue("startDate");
		Date endDate = (Date) context.getParameterValue("endDate");
		builder.addParameter("startDate", startDate);
		builder.addParameter("endDate", endDate);
		
		List<Integer> ptIds = evaluationService.evaluateToList(builder, Integer.class, context);
		newCohort.setMemberIds(new HashSet<Integer>(ptIds));
		return new EvaluatedCohort(newCohort, definition, context);
	}
}
