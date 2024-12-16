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
//Urine Analysis
  public CohortIndicator getAllUrineTests(Integer testConceptId) {
	return cohortIndicator(
		"All patients who have urinalysis glucose",
		map(moh706LabCohortLibrary.getAllUrineTests(testConceptId),
			"startDate=${startDate},endDate=${endDate}"));
}
    public CohortIndicator getAllUrineAnalysisGlucoseTestsPositives(Integer testConceptId) {
        return cohortIndicator(
                "All patients who tested positive for urinalysis glucose",
                map(moh706LabCohortLibrary.getAllUrineAnalysisGlucoseTestsPositives(testConceptId),
                        "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getAllUrineAnalysisKetonesTestsPositives() {
        return cohortIndicator(
                "All patients who have urinalysis ketones",
                map(moh706LabCohortLibrary.getAllUrineAnalysisKetonesTestsPositives(),
                        "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getAllUrineAnalysisProteinsTestsPositives() {
        return cohortIndicator(
                "All patients who have urinalysis Proteins",
                map(moh706LabCohortLibrary.getAllUrineAnalysisProteinsTestsPositives(),
                        "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getAllMalariaTests() {
        return cohortIndicator("All patients who have malaria test done",
                map(moh706LabCohortLibrary.getAllMalariaTests(), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getAllRapidMalariaTests() {
        return cohortIndicator("All patients who have rapid malaria test done",
                map(moh706LabCohortLibrary.getAllRapidMalariaTests(), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getAllMalariaTestsPositiveCases() {
        return cohortIndicator("All patients who have malaria test done and are positive",
                map(moh706LabCohortLibrary.getAllMalariaTestsPositiveCases(), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getAllRapidMalariaTestsPositiveCases() {
        return cohortIndicator("All patients who have rapid malaria test done and are positive",
                map(moh706LabCohortLibrary.getAllRapidMalariaTestsPositiveCases(), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getResponsesBasedOnAnswerIndicator(int q, List<Integer> ans) {
        return cohortIndicator("All patients who have tests done based on a question and answers",
                map(moh706LabCohortLibrary.getResponsesBasedOnAnswer(q, ans), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getPatientsWithObsIndicator(int q) {
        return cohortIndicator("All patients who have Obs recorded",
                map(moh706LabCohortLibrary.getPatientsWithObs(q), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getResponsesBasedOnAlistOfQuestionsAndListOfAnswers(List<Integer> q, List<Integer> ans) {
        return cohortIndicator(
                "All patients who have tests done based on a question and answers based on list of questions and answers",
                map(moh706LabCohortLibrary.getResponsesBasedOnAlistOfQuestionsAndListOfAnswers(q, ans),
                        "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getResponsesBasedOnAlistOfQuestions(List<Integer> q) {
        return cohortIndicator("All patients who have tests done based on a question list",
                map(moh706LabCohortLibrary.getResponsesBasedOnAlistOfQuestions(q), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getResponsesBasedOnValueNumericQuestion(int q) {
        return cohortIndicator(
                "Get patients with obs recorded based on value numeric concept id indicators",
                map(moh706LabCohortLibrary.getResponsesBasedOnValueNumericQuestion(q),
                        "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getResponsesBasedOnValueNumericQuestionBetweenLimits(int question, double lower, double upper) {
        return cohortIndicator(
                "Get patients with obs recorded based on value numeric concept id within limits indicators",
                map(moh706LabCohortLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(question, lower, upper),
                        "startDate=${startDate},endDate=${endDate}"));
    }

}
