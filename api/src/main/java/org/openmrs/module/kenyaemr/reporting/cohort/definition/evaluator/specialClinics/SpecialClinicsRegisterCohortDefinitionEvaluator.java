/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.cohort.definition.evaluator.specialClinics;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.specialClinics.SpecialClinicsRegisterCohortDefinition;
import org.openmrs.module.reporting.common.ObjectUtil;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.openmrs.module.reporting.query.encounter.EncounterQueryResult;
import org.openmrs.module.reporting.query.encounter.definition.EncounterQuery;
import org.openmrs.module.reporting.query.encounter.evaluator.EncounterQueryEvaluator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Evaluator for patients for OPD  Register
 *
 */
@Handler(supports = {SpecialClinicsRegisterCohortDefinition.class})
public class SpecialClinicsRegisterCohortDefinitionEvaluator implements EncounterQueryEvaluator {

    private final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	EvaluationService evaluationService;

	public EncounterQueryResult evaluate(EncounterQuery definition, EvaluationContext context) throws EvaluationException {
		context = ObjectUtil.nvl(context, new EvaluationContext());
		EncounterQueryResult queryResult = new EncounterQueryResult(definition, context);

		SpecialClinicsRegisterCohortDefinition cohortDefinition = (SpecialClinicsRegisterCohortDefinition) definition;
		String specialClinic = cohortDefinition.getSpecialClinic();
		String comparisonOperator = cohortDefinition.getComparisonOperator();
		Integer age = cohortDefinition.getAge();
		String qry = "SELECT ce.encounter_id \n" +
		"FROM kenyaemr_etl.etl_special_clinics ce\n" +
		"INNER JOIN kenyaemr_etl.etl_patient_demographics p \n" +
		"    ON p.patient_id = ce.patient_id \n" +
		"    AND p.voided = 0\n" +
		"WHERE date(ce.visit_date) BETWEEN date(:startDate) AND date(:endDate) \n" +
		"    AND ce.special_clinic_form_uuid = :specialClinic\n" +
		"    AND timestampdiff(YEAR, date(p.DOB), (SELECT MAX(ce.visit_date) FROM kenyaemr_etl.etl_special_clinics ce)) " + comparisonOperator + " :age \n" +
		"group by ce.patient_id";

		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.append(qry);
		Date startDate = (Date)context.getParameterValue("startDate");
		Date endDate = (Date)context.getParameterValue("endDate");
		builder.addParameter("endDate", endDate);
		builder.addParameter("startDate", startDate);
		builder.addParameter("specialClinic", specialClinic);
		builder.addParameter("comparisonOperator", comparisonOperator);
		builder.addParameter("age", age);
		List<Integer> results = evaluationService.evaluateToList(builder, Integer.class, context);
		queryResult.getMemberIds().addAll(results);
		return queryResult;
	}
}
