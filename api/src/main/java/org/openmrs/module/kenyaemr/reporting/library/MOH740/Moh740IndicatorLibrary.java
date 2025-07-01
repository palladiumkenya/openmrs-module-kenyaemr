/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.MOH740;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

@Component
public class Moh740IndicatorLibrary {

    @Autowired
    private Moh740CohortLibrary Moh740Cohorts;

    public CohortIndicator cumulativeDiabetes() {
        return cohortIndicator("Cumulative no. of diabetes", map(Moh740Cohorts.cumulativePatientWithDiabetes(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator cumulativeHypertension() {
        return cohortIndicator("Cumulative no. of hypertension", map(Moh740Cohorts.cumulativePatientWithHypertension(), "startDate=${startDate},endDate=${endDate}"));
    }
    /*Diagnosed diabetes & hypertension */
    public CohortIndicator newDiagnosedDiabetes() {
        return cohortIndicator("Diagnosed Diabetes", map(Moh740Cohorts.newDiagnosedWithDiabetes(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator newDiagnosedHypertension() {
        return cohortIndicator("Diagnosed Hypertension", map(Moh740Cohorts.newDiagnosedWithHypertension(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator cumulativeDMAndHTN() {
        return cohortIndicator("Cumulative No. of co-morbid DM+HTN patients in care", map(Moh740Cohorts.cumulativePatientWithDMAndHTN(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator preExistingDM() {
        return cohortIndicator("Pre-Existing DM", map(Moh740Cohorts.preExistingPatientWithDM(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator preExistingHTN() {
        return cohortIndicator("Pre-Existing HTN", map(Moh740Cohorts.preExistingPatientWithHTN(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator preExistingDMandHTN() {
        return cohortIndicator("Pre-Existing DM and HTN", map(Moh740Cohorts.preExistingPatientWithComorbidDMandHTN(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator firstVisitToClinic(int visitType) {
        return cohortIndicator("First Visit To Clinic", map(Moh740Cohorts.patientFirstVisitToClinic(visitType), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator cumulativePatientsInCare() {
        return cohortIndicator("Cumulative No. of Patients", map(Moh740Cohorts.cumulativePatientInCare(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator diabetesByTypeOne() {

        return cohortIndicator("No. of Diabetes By Type One", map(Moh740Cohorts.patientWithDiabetesByTypeOne(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator diabetesByTypeTwo() {

        return cohortIndicator("No. of Diabetes By Type Two", map(Moh740Cohorts.patientWithDiabetesByTypeTwo(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator diabetesGestational() {

        return cohortIndicator("No. of Diabetes secondary to other causes", map(Moh740Cohorts.patientWithGestational(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator diabetesSecondaryToOther() {

        return cohortIndicator("No. of Diabetes secondary to other causes", map(Moh740Cohorts.patientWithDiabetesSecondaryToOther(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientOnInsulin() {

        return cohortIndicator("No. of patients on insulin", map(Moh740Cohorts.patientOnInsulinTreatment(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientOnOGLAs() {

        return cohortIndicator("No. of patients on OGLAs", map(Moh740Cohorts.patientOnOGLAsTreatment(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientOnInsulinAndOGLAs() {

        return cohortIndicator("No. of patients on both (Insulin and OGLAs)", map(Moh740Cohorts.patientOnInsulinAndOGLAsTreatment(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientOnDietAndExercise() {

        return cohortIndicator("No. of patients on diet and exercise only (DM and HTN)", map(Moh740Cohorts.patientOnDietAndExerciseTreatment(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientDoneHbA1c() {

        return cohortIndicator("No. of patients done HbA1c", map(Moh740Cohorts.patientNumberDoneHbA1c(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientMetHbA1cTarget() {

        return cohortIndicator("No. that met HbA1c target (< 7%)", map(Moh740Cohorts.patientNumberMetHbA1cTarget(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator totalHypertension() {

        return cohortIndicator("No. of Hypertension", map(Moh740Cohorts.patientWithHypertension(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientOnAntihypertensives() {

        return cohortIndicator("No. of patients on antihypertensives", map(Moh740Cohorts.patientWithAntihypertensives(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientWithHighBP() {

        return cohortIndicator("No. with high BP at clinic visit (≥140/90)", map(Moh740Cohorts.patientWithHighBPOnClinicVisit(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator newDiagnosedStroke() {

        return cohortIndicator("Total no. of patients with Stroke", map(Moh740Cohorts.newDiagnosedWithStroke(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator newDiagnosedHeartDisease() {

        return cohortIndicator("Total no. of patients with Ischemic heart disease", map(Moh740Cohorts.newDiagnosedWithHeartDisease(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator newDiagnosedPeripheralDisease() {

        return cohortIndicator("Total no. of patients with Peripheral vascular/artery disease", map(Moh740Cohorts.newDiagnosedWithPeripheralDisease(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator newDiagnosedHeartFailure() {

        return cohortIndicator("Total no. of patients with Heart failure", map(Moh740Cohorts.newDiagnosedWithHeartFailure(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator newDiagnosedWithNeuropathies() {

        return cohortIndicator("New diagnosis Patients with neuropathies", map(Moh740Cohorts.newDiagnosedPatientWithNeuropathies(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientScreenedForDiabeticFoot() {

        return cohortIndicator("No. of patients screened for diabetic foot", map(Moh740Cohorts.noPatientScreenedForDiabeticFoot(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator newDiagnosedWithFootUlcer() {

        return cohortIndicator("New diagnosis with diabetic foot ulcer", map(Moh740Cohorts.newDiagnosedPatientWithFootUlcer(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator noFeetSavedThroughTreatment() {

        return cohortIndicator("No. of feet saved through treatment", map(Moh740Cohorts.patientFeetSavedThroughTreatment(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator noAmputatedDiabeticFoot() {

        return cohortIndicator("No. of amputation due to diabetic foot", map(Moh740Cohorts.patientWithAmputatedDiabeticFoot(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator newDiagnosedWithKidneyComplication() {

        return cohortIndicator("New diagnosis with kidney complications", map(Moh740Cohorts.newPatientDiagnosedWithKidneyComplication(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator newDiagnosedWithDiabeticRetinopathy() {

        return cohortIndicator("New diagnosis with diabetic retinopathy ", map(Moh740Cohorts.newPatientDiagnosedWithDiabeticRetinopathy(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientScreenedForTuberculosis() {

        return cohortIndicator("No. Screened for Tuberculosis", map(Moh740Cohorts.noPatientScreenedForTuberculosis(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientScreenedPositiveTuberculosis() {

        return cohortIndicator("No. Screened Positive for Tuberculosis ", map(Moh740Cohorts.noPatientScreenedPositiveTuberculosis(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator noEnrolledSHA() {

        return cohortIndicator("No. enrolled with SHA", map(Moh740Cohorts.noPatientEnrolledSHA(), "startDate=${startDate},endDate=${endDate}"));
    }

}


