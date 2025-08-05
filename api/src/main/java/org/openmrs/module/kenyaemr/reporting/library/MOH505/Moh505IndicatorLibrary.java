/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.MOH505;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyaemr.reporting.library.MOH505.Moh505CohortLibrary;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

@Component
public class Moh505IndicatorLibrary {

    @Autowired
    private Moh505CohortLibrary Moh505Cohorts;

    public CohortIndicator AEFICase() {
        return cohortIndicator("AEFI", map(Moh505Cohorts.jaundiceCases(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator diagnosis(int diagnosis) {
        return cohortIndicator("Diagnosis", ReportUtils.map(Moh505Cohorts.patientDiagnosis(diagnosis), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator maternalDeathCase() {
        return cohortIndicator("Maternal", map(Moh505Cohorts.maternalDeath(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientDiagnosedAndDeceased(int diagnosis, int patientOutcome) {
        return cohortIndicator("Deaths", ReportUtils.map(Moh505Cohorts.patientDeceased(diagnosis, patientOutcome), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator acuteJaundiceCase() {
        return cohortIndicator("Acute Jaundice", map(Moh505Cohorts.jaundiceCases(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator acuteJaundicePatientDeceased(int patientOutcome) {
        return cohortIndicator("Acute Jaundice Deaths", map(Moh505Cohorts.jaundiceDeceased(patientOutcome), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator sariPatientCases() {
        return cohortIndicator("SARI (Cluster ≥3 cases)", map(Moh505Cohorts.sariCases(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator sariPatientDeceased(int patientOutcome) {
        return cohortIndicator("SARI (Cluster ≥3 deaths)", map(Moh505Cohorts.sariDeceased(patientOutcome), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator malariaTested(int diagnosis, int mRDT) {
        return cohortIndicator("Malaria Test", ReportUtils.map(Moh505Cohorts.malariaTest(diagnosis, mRDT), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator malariaTestedPositive(int diagnosis, int mRDT) {
        return cohortIndicator("Malaria Test Positive", ReportUtils.map(Moh505Cohorts.malariaTestPositive(diagnosis, mRDT), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator typhoidTested(int typhoidTest) {
        return cohortIndicator("Typhoid Test", ReportUtils.map(Moh505Cohorts.typhoidTest(typhoidTest), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator typhoidTestedPositive(int typhoidTest) {
        return cohortIndicator("Typhoid Test Positive", ReportUtils.map(Moh505Cohorts.typhoidTestPositive(typhoidTest), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator tbTested(int tbTest) {
        return cohortIndicator("Tubercullosis Test", ReportUtils.map(Moh505Cohorts.tbTest(tbTest), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator tbTestedPositive(int tbTest) {
        return cohortIndicator("Tubercullosis Test Positive", ReportUtils.map(Moh505Cohorts.tbPositive(tbTest), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator dysentryTested(int dysentryTest) {
        return cohortIndicator("Shigella Dysentry Test", ReportUtils.map(Moh505Cohorts.dysentryTest(dysentryTest), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator dysentryTestedPositive(int dysentryTest) {
        return cohortIndicator("Shigella Dysentry Test Positive", ReportUtils.map(Moh505Cohorts.dysentryTestPositive(dysentryTest), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator bacterialTested(int bacteriaTest) {
        return cohortIndicator("Bacterial Meningitis", ReportUtils.map(Moh505Cohorts.bacterialTest(bacteriaTest), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator bacteriaTestedPositiveNM(int bacteriaTest1, int bacteriaTest2) {
        return cohortIndicator("BacterialMeningitis(+ve Nm)", ReportUtils.map(Moh505Cohorts.bacterialTestPositiveNM(bacteriaTest1, bacteriaTest2), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator bacteriaTestedPositiveSp(int bacteriaTest1, int bacteriaTest2) {
        return cohortIndicator("BacterialMeningitis(+ve Sp)", ReportUtils.map(Moh505Cohorts.bacterialTestPositiveSp(bacteriaTest1, bacteriaTest2), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator bacteriaTestedPositiveHInfluenza(int bacteriaTest) {
        return cohortIndicator("BacterialMeningitis(+ve H influenza)", ReportUtils.map(Moh505Cohorts.bacterialTestPositiveHInfluenza(bacteriaTest), "startDate=${startDate},endDate=${endDate}"));
    }



}


