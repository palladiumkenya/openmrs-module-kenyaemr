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
import org.openmrs.module.kenyaemr.reporting.cohort.definition.dmi.IDSRSuspectedCasesCohortDefinition;
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
 * Evaluator for Surveillance cases Cohort
 */
@Handler(supports = { IDSRSuspectedCasesCohortDefinition.class })
public class IDSRSuspectedCasesCohortDefinitionEvaluator implements CohortDefinitionEvaluator {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	EvaluationService evaluationService;
	
	@Override
	public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

		IDSRSuspectedCasesCohortDefinition definition = (IDSRSuspectedCasesCohortDefinition) cohortDefinition;
		
		if (definition == null)
			return null;
		
		Cohort newCohort = new Cohort();
		
		String qry = "select a.patient_id\n" +
				"from (select c.patient_id,\n" +
				"             group_concat(c.complaint) as complaint,\n" +
				"             epd.dob,\n" +
				"             c.complaint_date,\n" +
				"             c.visit_date,\n" +
				"             v.visit_type_id,\n" +
				"             t.temperature,\n" +
				"             CASE\n" +
				"                 WHEN group_concat(concat_ws('|', c.complaint, c.complaint_duration)) LIKE '%140238%' THEN\n" +
				"                     SUBSTRING_INDEX(\n" +
				"                             SUBSTRING_INDEX(group_concat(concat_ws('|', c.complaint, c.complaint_duration)), '|', -1),\n" +
				"                             ',', 1)\n" +
				"                 END                   AS fever_duration_from_days\n" +
				"      from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
				"               inner join kenyaemr_etl.etl_patient_demographics epd on c.patient_id = epd.patient_id\n" +
				"               inner join openmrs.visit v ON c.patient_id = v.patient_id AND DATE(c.visit_date) = DATE(v.date_started)\n" +
				"               left join kenyaemr_etl.etl_patient_triage t\n" +
				"                         on c.patient_id = t.patient_id and\n" +
				"                            date(t.visit_date) between date(:startDate) and date(:endDate)\n" +
				"      group by patient_id) a\n" +
				"where ((FIND_IN_SET(117671, a.complaint) > 0 AND FIND_IN_SET(142412, a.complaint) > 0)\n" +
				"   OR (FIND_IN_SET(161887, a.complaint) > 0 AND FIND_IN_SET(122983, a.complaint) > 0 AND\n" +
				"       timestampdiff(YEAR, date(a.DOB), coalesce(date(a.complaint_date), date(a.visit_date))) > 2)\n" +
				"   OR (FIND_IN_SET(143264, a.complaint) > 0 AND timestampdiff(DAY, date(a.complaint_date), date(a.visit_date)) < 10 AND\n" +
				"       date(a.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate) AND a.visit_type_id IN (1, 3) AND\n" +
				"       a.temperature >= 38)\n" +
				"   OR (FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(141830, a.complaint) > 0 AND\n" +
				"       FIND_IN_SET(136443, a.complaint) > 0 AND FIND_IN_SET(135367, a.complaint) > 0 AND a.fever_duration_from_days > 2)\n" +
				"   OR (FIND_IN_SET(40238, a.complaint) > 0 AND FIND_IN_SET(139084, a.complaint) > 0 AND\n" +
				"       FIND_IN_SET(871, a.complaint) > 0 AND a.fever_duration_from_days > 1)\n" +
				"   OR (FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(116558, a.complaint) > 0 AND a.temperature > 38.5 AND a.fever_duration_from_days > 2)\n" +
				"   OR (a.complaint = 157498 AND timestampdiff(YEAR, a.DOB, a.visit_date) <= 15)\n" +
				"   OR (FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(162628, a.complaint) > 0 AND\n" +
				"       a.fever_duration_from_days >= 3)\n" +
				"   OR (FIND_IN_SET(140238, a.complaint) AND FIND_IN_SET(512, a.complaint) AND FIND_IN_SET(106, a.complaint) AND\n" +
				"       FIND_IN_SET(516, a.complaint) AND FIND_IN_SET(143264, a.complaint) > 0 AND a.fever_duration_from_days > 2))\n" +
				"    and date(a.visit_date) BETWEEN date(:startDate) AND date(:endDate)\n" +
				"group by patient_id;";
		
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
