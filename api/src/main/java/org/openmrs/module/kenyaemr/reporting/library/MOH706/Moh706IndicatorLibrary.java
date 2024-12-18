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
  public CohortIndicator getTotalTestsByConcept(Integer labSetConceptId) {
	return cohortIndicator(
		"All patients who have urinalysis glucose",
		map(moh706LabCohortLibrary.getTotalTestsByConcept(labSetConceptId),
			"startDate=${startDate},endDate=${endDate}"));
}
    public CohortIndicator getAllUrineAnalysisGlucosePositives(Integer testConceptId) {
        return cohortIndicator(
                "All patients who tested positive for urinalysis glucose",
                map(moh706LabCohortLibrary.getAllUrineAnalysisGlucosePositives(testConceptId),
                        "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getAllUrineAnalysisKetonesPositives(Integer testConceptId) {
        return cohortIndicator(
                "All patients who have urinalysis ketones",
                map(moh706LabCohortLibrary.getAllUrineAnalysisKetonesPositives(testConceptId),
                        "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getAllUrineAnalysisProteinsPositives(Integer testConceptId) {
        return cohortIndicator(
                "All patients who have urinalysis Proteins",
                map(moh706LabCohortLibrary.getAllUrineAnalysisProteinsPositives(testConceptId),
                        "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getAllUrineAnalysisPusCellsPositives(Integer testConceptId) {
        return cohortIndicator("All patients who have urinalysis Pus cells",
                map(moh706LabCohortLibrary.getAllUrineAnalysisPusCellsPositives(testConceptId), 
					"startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getAllUrineAnalysisSHaematobiumPositives(Integer testConceptId) {
        return cohortIndicator("All patients who have urinalysis S Haematobium tests done",
                map(moh706LabCohortLibrary.getAllUrineAnalysisSHaematobiumPositives(testConceptId), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator getAllUrineAnalysisTVaginatisPositives(Integer testConceptId) {
        return cohortIndicator("All patients who have urinalysis T Vaginatis tests done",
                map(moh706LabCohortLibrary.getAllUrineAnalysisTVaginatisPositives(testConceptId), "startDate=${startDate},endDate=${endDate}"));
    }
	public CohortIndicator getAllUrineAnalysisYeastCellsPositives(Integer testConceptId) {
		return cohortIndicator("All patients who have urinalysis Yeasts Cells tests done",
			map(moh706LabCohortLibrary.getAllUrineAnalysisYeastCellsPositives(testConceptId), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator getAllUrineAnalysisBacteriaPositives(Integer testConceptId) {
		return cohortIndicator("All patients who have urinalysis Bacteria Cells tests done",
			map(moh706LabCohortLibrary.getAllUrineAnalysisBacteriaPositives(testConceptId), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator getAllBSMalariaTestsPositiveCases(Integer testConceptId) {
		return cohortIndicator("All patients who have malaria test done and are positive",
			map(moh706LabCohortLibrary.getAllBSMalariaTestsPositiveCases(testConceptId), "startDate=${startDate},endDate=${endDate}"));
	}

   public CohortIndicator getAllRapidMalariaTestsPositiveCases(Integer testConceptId) {
        return cohortIndicator("All patients who have rapid malaria test done and are positive",
                map(moh706LabCohortLibrary.getAllRapidMalariaTestsPositiveCases(testConceptId), "startDate=${startDate},endDate=${endDate}"));
    }
	public CohortIndicator getAllTaeniaSPPTestsPositiveCases(Integer testConceptId) {
		return cohortIndicator("All patients who have Taenia SPP test done and are positive",
			map(moh706LabCohortLibrary.getAllTaeniaSPPTestsPositiveCases(testConceptId), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator getHymenolepisnanaTestsPositiveCases(Integer testConceptId) {
		return cohortIndicator("All patients who have Hymenolepis nana test done and are positive",
			map(moh706LabCohortLibrary.getHymenolepisnanaTestsPositiveCases(testConceptId), "startDate=${startDate},endDate=${endDate}"));
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
