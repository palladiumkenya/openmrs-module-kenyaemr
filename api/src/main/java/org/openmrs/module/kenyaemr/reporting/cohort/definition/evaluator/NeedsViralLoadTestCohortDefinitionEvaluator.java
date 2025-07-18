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
import org.openmrs.module.kenyaemr.reporting.cohort.definition.ActivePatientsSnapshotCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.NeedsViralLoadTestCohortDefinition;
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
 * Evaluator for NeedsViralLoadTestCohortDefinition
 * Based on patients who are active on ART.
 */
@Handler(supports = {NeedsViralLoadTestCohortDefinition.class})
public class NeedsViralLoadTestCohortDefinitionEvaluator implements CohortDefinitionEvaluator {

	@Autowired
	EvaluationService evaluationService;

    @Override
    public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

		NeedsViralLoadTestCohortDefinition definition = (NeedsViralLoadTestCohortDefinition) cohortDefinition;

        if (definition == null)
            return null;

		Cohort newCohort = new Cohort();

		String qry="select t.patient_id\n" +
				"from (select fup.visit_date,\n" +
				"             fup.patient_id,\n" +
				"             max(e.visit_date)                                                      as enroll_date,\n" +
				"             greatest(max(fup.visit_date), ifnull(max(d.visit_date), '0000-00-00')) as latest_vis_date,\n" +
				"             greatest(mid(max(concat(fup.visit_date, fup.next_appointment_date)), 11),\n" +
				"                      ifnull(max(d.visit_date), '0000-00-00'))                      as latest_tca,\n" +
				"             d.patient_id                                                           as disc_patient,\n" +
				"             d.effective_disc_date                                                  as effective_disc_date,\n" +
				"             max(d.visit_date)                                                      as date_discontinued,\n" +
				"             de.patient_id                                                          as started_on_drugs\n" +
				"      from kenyaemr_etl.etl_patient_hiv_followup fup\n" +
				"               join kenyaemr_etl.etl_patient_demographics p on p.patient_id = fup.patient_id\n" +
				"               join kenyaemr_etl.etl_hiv_enrollment e on fup.patient_id = e.patient_id\n" +
				"               left join kenyaemr_etl.etl_drug_event de\n" +
				"                         on e.patient_id = de.patient_id and de.program = 'HIV' and\n" +
				"                            date(de.date_started) <= date(:endDate)\n" +
				"               left outer JOIN\n" +
				"           (select patient_id,\n" +
				"                   coalesce(date(effective_discontinuation_date), visit_date) visit_date,\n" +
				"                   max(date(effective_discontinuation_date)) as               effective_disc_date\n" +
				"            from kenyaemr_etl.etl_patient_program_discontinuation\n" +
				"            where date(visit_date) <= date(:endDate)\n" +
				"              and program_name = 'HIV'\n" +
				"            group by patient_id) d on d.patient_id = fup.patient_id\n" +
				"               inner join kenyaemr_etl.etl_viral_load_validity_tracker vt on vt.patient_id = fup.patient_id\n" +
				"      where fup.visit_date <= date(:endDate)\n" +
				"        and ((vt.vl_result >= 200 and timestampdiff(MONTH, date(vt.date_test_requested), date(:endDate)) >= 3)\n" +
				"          OR (timestampdiff(YEAR, p.DOB, date(vt.date_test_requested)) between 0 and 24 and\n" +
				"              ((vt.lab_test = 856 and vt.vl_result < 200) OR (vt.lab_test = 1305 and vt.vl_result = 1302)) and\n" +
				"              timestampdiff(MONTH, date(vt.date_test_requested), date(:endDate)) >= 6)\n" +
				"          OR (timestampdiff(MONTH, vt.date_started_art, date(:endDate)) >= 3 and vt.vl_result is null and\n" +
				"              vt.previous_test_result is null and vt.base_viral_load_test_result is null)\n" +
				"          OR (TIMESTAMPDIFF(MONTH, date(vt.date_started_art), date(:endDate)) >= 3 and\n" +
				"              (vt.breastfeeding_status = 1065 or vt.pregnancy_status = 1065) and\n" +
				"              (vt.latest_hiv_followup_visit > vt.date_test_requested OR vt.date_test_requested is null))\n" +
				"          OR (timestampdiff(MONTH, vt.date_test_requested, date(:endDate)) >= 6 and\n" +
				"              timestampdiff(YEAR, p.DOB, date(vt.date_test_requested)) between 0 and 24 and\n" +
				"              timestampdiff(MONTH, vt.date_started_art, date(:endDate)) >= 3 and\n" +
				"              ((vt.lab_test = 856 and vt.vl_result < 200) OR (vt.lab_test = 1305 and vt.vl_result = 1302)))\n" +
				"          OR (timestampdiff(YEAR, p.DOB, date(vt.date_test_requested)) > 24 and\n" +
				"              TIMESTAMPDIFF(MONTH, date(vt.date_started_art), date(:endDate)) >= 3 and\n" +
				"              ((vt.lab_test = 856 and vt.vl_result < 200) OR (vt.lab_test = 1305 and vt.vl_result = 1302)) and\n" +
				"              timestampdiff(MONTH, date(vt.date_test_requested), date(:endDate)) >= 12)\n" +
				"          OR (vt.pregnancy_status = 1065 or vt.breastfeeding_status = 1065 and\n" +
				"                                            ((vt.lab_test = 856 and vt.vl_result < 200) OR\n" +
				"                                             (vt.lab_test = 1305 and vt.vl_result = 1302)) and\n" +
				"                                            vt.previous_order_reason in (1434, 159882))\n" +
				"                 and timestampdiff(MONTH, date(vt.date_test_requested), date(:endDate)) >= 6)\n" +
				"      group by patient_id\n" +
				"      having (started_on_drugs is not null and started_on_drugs <> '')\n" +
				"         and (\n" +
				"          (\n" +
				"              (timestampdiff(DAY, date(latest_tca), date(:endDate)) <= 30 and\n" +
				"               ((date(d.effective_disc_date) > date(:endDate) or date(enroll_date) > date(d.effective_disc_date)) or\n" +
				"                d.effective_disc_date is null))\n" +
				"                  and\n" +
				"              (date(latest_vis_date) >= date(date_discontinued) or date(latest_tca) >= date(date_discontinued) or\n" +
				"               disc_patient is null)\n" +
				"              )\n" +
				"          )) t;";

		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.append(qry);
		Date endDate = (Date)context.getParameterValue("endDate");
		builder.addParameter("endDate", endDate);

		List<Integer> ptIds = evaluationService.evaluateToList(builder, Integer.class, context);
		newCohort.setMemberIds(new HashSet<Integer>(ptIds));
		return new EvaluatedCohort(newCohort, definition, context);
    }
}
