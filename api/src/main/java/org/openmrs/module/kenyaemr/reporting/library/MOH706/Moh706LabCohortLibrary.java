/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.MOH706;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class Moh706LabCohortLibrary {

	//1.2 Urine Analysis

	public CohortDefinition getTotalTestsByConcept(Integer labSetConceptId) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get total tests by lab concept");
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"    join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"    where le.set_member_conceptId = " + labSetConceptId + "\n" +
			"  and date(le.visit_date) between :startDate and :endDate;"

		);
		return sql;
	}

	public CohortDefinition getTotalCodedLabsByConceptAndPositiveAnswer(int question, List<Integer> ans) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get patients with tests recorded based on concept id");
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"                               join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"where le.set_member_conceptId = " + question + "\n" +
			"  and le.test_result in  (" + StringUtils.join(ans, ',') + ")\n" +
			"  and date(le.visit_date) between :startDate and :endDate;");
		return sql;
	}

	public CohortDefinition getResultsBasedOnAlistOfQuestionsAndListOfAnswers(List<Integer> question, List<Integer> ans) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get patients with tests recorded based on concept id");
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"                               join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"where le.set_member_conceptId in  (" + StringUtils.join(question, ',') + ")\n" +
			"  and le.test_result in  (" + StringUtils.join(ans, ',') + ")\n" +
			"  and date(le.visit_date) between :startDate and :endDate;");
		return sql;
	}

	public CohortDefinition getResultsBasedOnAlistOfQuestions(List<Integer> question) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get patients with tests recorded based on concept id");
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"                               join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"where le.set_member_conceptId in  (" + StringUtils.join(question, ',') + ")\n" +
			"  and date(le.visit_date) between :startDate and :endDate;");
		return sql;
	}

	public CohortDefinition getResultsBasedOnValueNumericQuestionBetweenLimits(int question, double lower, double upper) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get patients with tests recorded based on concept id within limits");
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"                               join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"where le.set_member_conceptId = " + question + "\n" +
			"  and le.test_result between " + lower + "  and  " + upper + "\n" +
			"  and date(le.visit_date) between :startDate and :endDate;");
		return sql;
	}

	public CohortDefinition getResultsBasedOnValueNumericQuestion(int question) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get patients with tests recorded based on concept id ");
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"                               join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"where le.set_member_conceptId = " + question + "\n" +
			"  and le.test_result is not null\n" +
			"  and date(le.visit_date) between :startDate and :endDate;");
		return sql;
	}

	
}
