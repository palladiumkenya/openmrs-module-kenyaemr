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
import org.openmrs.module.kenyaemr.reporting.cohort.definition.ScheduledARTDrugRefillsCohortDefinition;
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
 * Returns a list of patients scheduled to have drug refills within a period
 */
@Handler(supports = ScheduledARTDrugRefillsCohortDefinition.class)
public class ScheduledARTDrugRefillsCohortDefinitionEvaluator implements CohortDefinitionEvaluator {

	protected static final Log log = LogFactory.getLog(ScheduledARTDrugRefillsCohortDefinition.class);

	@Autowired
	EvaluationService evaluationService;


	/**
	 * @see CohortDefinitionEvaluator#evaluate(CohortDefinition, EvaluationContext)
	 */
	@Override
	public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

		ScheduledARTDrugRefillsCohortDefinition cohortddef = (ScheduledARTDrugRefillsCohortDefinition) cohortDefinition;

		Date startDate = (Date) context.getParameterValue("startDate");
		Date endDate = (Date) context.getParameterValue("endDate");


		Cohort newCohort = new Cohort();

		String qry=" select e.patient_id\n" +
				"from (\n" +
				"select fup.visit_date,fup.patient_id, min(e.visit_date) as enroll_date,\n" +
				"    max(fup.visit_date) as latest_vis_date,\n" +
				"    mid(max(concat(fup.visit_date,fup.next_appointment_date)),11) as latest_tca,\n" +
				"    max(d.visit_date) as date_discontinued,\n" +
				"    d.patient_id as disc_patient,\n" +
				"  de.patient_id as started_on_drugs,\n" +
				"  de.program as hiv_program\n" +
				"from kenyaemr_etl.etl_patient_hiv_followup fup\n" +
				"join kenyaemr_etl.etl_patient_demographics p on p.patient_id=fup.patient_id\n" +
				"join kenyaemr_etl.etl_hiv_enrollment e on fup.patient_id=e.patient_id\n" +
				"left outer join kenyaemr_etl.etl_drug_event de on e.patient_id = de.patient_id and date(date_started) <= date(:endDate)\n" +
				"left outer JOIN\n" +
				"(select patient_id, visit_date from kenyaemr_etl.etl_patient_program_discontinuation\n" +
				"where date(visit_date) <= date(:endDate) and program_name='HIV'\n" +
				"group by patient_id\n" +
				") d on d.patient_id = fup.patient_id\n" +
				"where de.program = 'HIV' and fup.visit_date <= date(:endDate) and fup.refill_date between date(:startDate) and date(:endDate)\t\n" +
				"group by patient_id\n" +
				"having (started_on_drugs is not null and started_on_drugs <> \"\") and (\n" +
				"(date(latest_tca) > date(:endDate) and (date(latest_tca) > date(date_discontinued) or disc_patient is null )) or\n" +
				"(((date(latest_tca) between date(:startDate) and date(:endDate)) and (date(latest_vis_date) >= date(latest_tca)) or date(latest_tca) > curdate()) ) and (date(latest_tca) > date(date_discontinued) or disc_patient is null ))\n" +
				") e\n" +
				";";

		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.append(qry);
		builder.addParameter("startDate", startDate);
		builder.addParameter("endDate", endDate);
		List<Integer> ptIds = evaluationService.evaluateToList(builder, Integer.class, context);

		newCohort.setMemberIds(new HashSet<Integer>(ptIds));


		return new EvaluatedCohort(newCohort, cohortDefinition, context);
	}
}