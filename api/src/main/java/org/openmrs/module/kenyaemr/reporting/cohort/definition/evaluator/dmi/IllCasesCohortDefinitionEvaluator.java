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
import org.openmrs.module.kenyaemr.reporting.cohort.definition.dmi.IllCasesCohortDefinition;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.evaluator.CohortDefinitionEvaluator;
import org.openmrs.module.reporting.common.ObjectUtil;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.openmrs.module.reporting.query.encounter.EncounterQueryResult;
import org.openmrs.module.reporting.query.encounter.definition.EncounterQuery;
import org.openmrs.module.reporting.query.encounter.evaluator.EncounterQueryEvaluator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.Context;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Evaluator for Surveillance cases Cohort
 */
@Handler(supports = { IllCasesCohortDefinition.class })
public class IllCasesCohortDefinitionEvaluator implements EncounterQueryEvaluator {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	EvaluationService evaluationService;
	
	@Override
	public EncounterQueryResult evaluate(EncounterQuery encounterQuery, EvaluationContext context) throws EvaluationException {
		context = ObjectUtil.nvl(context, new EvaluationContext());
		EncounterQueryResult queryResult = new EncounterQueryResult(encounterQuery, context);

		
		Cohort newCohort = new Cohort();

		String qry ="SELECT a.encounter_id\n" +
				"FROM (\n" +
				"    SELECT c.patient_id,\n" +
				"           c.encounter_id,\n" +
				"           c.complaint,\n" +
				"           epd.dob,\n" +
				"           DATE_SUB(c.visit_date, INTERVAL c.complaint_duration DAY) AS complaint_date,\n" +
				"           c.visit_date,\n" +
				"           v.visit_type_id,\n" +
				"           t.temperature,\n" +
				"           CASE\n" +
				"               WHEN c.complaint = 140238 THEN c.complaint_duration\n" +
				"               ELSE NULL\n" +
				"           END AS fever_duration_from_days\n" +
				"    FROM kenyaemr_etl.etl_allergy_chronic_illness c\n" +
				"    INNER JOIN kenyaemr_etl.etl_patient_demographics epd ON c.patient_id = epd.patient_id\n" +
				"    INNER JOIN openmrs.visit v ON c.patient_id = v.patient_id AND DATE(c.visit_date) = DATE(v.date_started)\n" +
				"    LEFT JOIN kenyaemr_etl.etl_patient_triage t ON c.patient_id = t.patient_id AND DATE(t.visit_date) between date(:startDate) and date(:endDate)\n" +
				") a\n" +
				"WHERE ((FIND_IN_SET(117671, a.complaint) > 0 AND FIND_IN_SET(142412, a.complaint) > 0)\n" +
				"   OR (FIND_IN_SET(161887, a.complaint) > 0 AND FIND_IN_SET(122983, a.complaint) > 0 AND\n" +
				"       TIMESTAMPDIFF(YEAR, DATE(a.dob), COALESCE(DATE(a.complaint_date), DATE(a.visit_date))) > 2)\n" +
				"   OR (FIND_IN_SET(143264, a.complaint) > 0 AND TIMESTAMPDIFF(DAY, DATE(a.complaint_date), DATE(a.visit_date)) < 10 AND\n" +
				"       DATE(a.visit_date) between date(:startDate) and date(:endDate) AND a.visit_type_id IN (1, 3) AND\n" +
				"       a.temperature >= 38)\n" +
				"   OR (FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(141830, a.complaint) > 0 AND\n" +
				"       FIND_IN_SET(136443, a.complaint) > 0 AND FIND_IN_SET(135367, a.complaint) > 0 AND a.fever_duration_from_days > 2)\n" +
				"   OR (FIND_IN_SET(40238, a.complaint) > 0 AND FIND_IN_SET(139084, a.complaint) > 0 AND\n" +
				"       FIND_IN_SET(871, a.complaint) > 0 AND a.fever_duration_from_days > 1)\n" +
				"   OR (FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(116558, a.complaint) > 0 AND a.temperature > 38.5 AND a.fever_duration_from_days > 2)\n" +
				"   OR (a.complaint = 157498 AND TIMESTAMPDIFF(YEAR, a.dob, a.visit_date) <= 15)\n" +
				"   OR (FIND_IN_SET(140238, a.complaint) > 0 AND FIND_IN_SET(162628, a.complaint) > 0 AND\n" +
				"       a.fever_duration_from_days >= 3)\n" +
				"   OR (FIND_IN_SET(140238, a.complaint) AND FIND_IN_SET(512, a.complaint) AND FIND_IN_SET(106, a.complaint) AND\n" +
				"       FIND_IN_SET(516, a.complaint) AND FIND_IN_SET(143264, a.complaint) > 0 AND a.fever_duration_from_days > 2))\n" +
				"AND DATE(a.visit_date) between date(:startDate) and date(:endDate);";

		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.append(qry);
		Date startDate = (Date)context.getParameterValue("startDate");
		Date endDate = (Date)context.getParameterValue("endDate");
		builder.addParameter("endDate", endDate);
		builder.addParameter("startDate", startDate);

		List<Integer> results = evaluationService.evaluateToList(builder, Integer.class, context);
		queryResult.getMemberIds().addAll(results);
		return queryResult;
	}
}
