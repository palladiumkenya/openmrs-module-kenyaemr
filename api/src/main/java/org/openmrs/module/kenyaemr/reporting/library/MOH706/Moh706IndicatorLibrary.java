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

import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.reporting.MohReportUtils.ReportingUtils.cohortIndicator;

@Component
public class Moh706IndicatorLibrary {
	private final Moh706LabCohortLibrary moh706LabCohortLibrary;

	public Moh706IndicatorLibrary(Moh706LabCohortLibrary moh706LabCohortLibrary) {
		this.moh706LabCohortLibrary = moh706LabCohortLibrary;
	}

	public CohortIndicator getTotalTestsByConcept(Integer labSetConceptId) {
		return cohortIndicator(
			"All patients who have tests",
			map(moh706LabCohortLibrary.getTotalTestsByConcept(labSetConceptId),
				"startDate=${startDate},endDate=${endDate}"));
	}


	public CohortIndicator getTotalCodedLabsByConceptAndPositiveAnswer(Integer q, List<Integer> ans) {
		return cohortIndicator("All patients who have tests based on a question and answers",
			map(moh706LabCohortLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(q, ans), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getResultsBasedOnAlistOfQuestionsAndListOfAnswers(List<Integer> question, List<Integer> ans) {
		return cohortIndicator("All patients who have tests based on a question and answers",
			map(moh706LabCohortLibrary.getResultsBasedOnAlistOfQuestionsAndListOfAnswers(question, ans), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getResultsBasedOnAlistOfQuestions(List<Integer> q) {
		return cohortIndicator("All patients who have tests done based on a question list",
			map(moh706LabCohortLibrary.getResultsBasedOnAlistOfQuestions(q), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getResultsBasedOnValueNumericQuestionBetweenLimits(Integer question, Double lower, Double upper) {
		return cohortIndicator(
			"Get patients with results recorded based on  concept id within limits indicators",
			map(moh706LabCohortLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(question, lower, upper),
				"startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getResultsBasedOnValueNumericQuestion(Integer question) {
		return cohortIndicator(
			"Get patients with tests recorded based on concept id indicators",
			map(moh706LabCohortLibrary.getResultsBasedOnValueNumericQuestion(question),
				"startDate=${startDate},endDate=${endDate}"));
	}

}
