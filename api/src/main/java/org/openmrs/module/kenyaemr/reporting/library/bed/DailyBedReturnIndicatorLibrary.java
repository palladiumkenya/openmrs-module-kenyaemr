/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.bed;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

/**
 * Indicators specific to the Daily Bed Return Indicator Library report
 */
@Component
public class DailyBedReturnIndicatorLibrary {

	@Autowired
	private DailyBedReturnCohortLibrary dailyBedReturnCohortLibrary;

	public CohortIndicator patientCurrentBedOccupationStatus( String occupationStatus) {
		return cohortIndicator("Bed Occupation Status", ReportUtils.map(dailyBedReturnCohortLibrary.patientCurrentBedOccupationStatus(occupationStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator patientPreviousBedOccupationStatus( String occupationStatus) {
		return cohortIndicator("Previous Bed Occupation Status", ReportUtils.map(dailyBedReturnCohortLibrary.patientPreviousBedOccupationStatus(occupationStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator patientCurrentlyBedTagsOccupationStatus( String occupationStatus) {
		return cohortIndicator("Currently Bed Tags Occupation Status", ReportUtils.map(dailyBedReturnCohortLibrary.patientCurrentlyBedTagsOccupationStatus(occupationStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator totalCurrentBedsAndBedTagsOccupiedStatus( String occupationStatus) {
		return cohortIndicator("Total current Beds and Bed tags", ReportUtils.map(dailyBedReturnCohortLibrary.totalCurrentBedsAndBedTagsOccupiedStatus(occupationStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator patientPreviouslyBedTagsOccupationStatus( String occupationStatus) {
		return cohortIndicator("Currently Bed Tags Occupation Status", ReportUtils.map(dailyBedReturnCohortLibrary.patientPreviouslyBedTagsOccupationStatus(occupationStatus), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator currentTotalBeds( String occupationStatus,String vacantStatus) {
		return cohortIndicator("Total beds Current count", ReportUtils.map(dailyBedReturnCohortLibrary.currentTotalBeds(occupationStatus,vacantStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator currentTotalBedTags( String occupationStatus,String vacantStatus) {
		return cohortIndicator("Total beds Tags Current count", ReportUtils.map(dailyBedReturnCohortLibrary.currentTotalBedTags(occupationStatus,vacantStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator totalCurrentBedsAndBedTags( String occupationStatus,String vacantStatus) {
		return cohortIndicator("All Total current Beds and Bed tags", ReportUtils.map(dailyBedReturnCohortLibrary.totalCurrentBedsAndBedTags(occupationStatus,vacantStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator previousTotalBeds( String occupationStatus, String vacantStatus) {
		return cohortIndicator("Total beds previous count", ReportUtils.map(dailyBedReturnCohortLibrary.previousTotalBeds(occupationStatus,vacantStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator allPreviousTotalBedsAndBedTags( String occupationStatus, String vacantStatus) {
		return cohortIndicator("All Previous Total Beds and Bed Tags", ReportUtils.map(dailyBedReturnCohortLibrary.allPreviousTotalBedsAndBedTags(occupationStatus,vacantStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator previousTotalBedTags( String occupationStatus, String vacantStatus) {
		return cohortIndicator("Total beds tags previous count", ReportUtils.map(dailyBedReturnCohortLibrary.previousTotalBedTags(occupationStatus,vacantStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator patientDischargedStatus(Integer dischargeStatus) {
		return cohortIndicator("Discharged Status", ReportUtils.map(dailyBedReturnCohortLibrary.patientDischargedStatus(dischargeStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator totalPatientDischargedStatus() {
		return cohortIndicator("Discharged Status", ReportUtils.map(dailyBedReturnCohortLibrary.totalPatientDischargedStatus(), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator patientAdmissionStatus(String admissionStatus) {
		return cohortIndicator("Discharged Status", ReportUtils.map(dailyBedReturnCohortLibrary.patientAdmissionStatus(admissionStatus), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator patientsAdmittedByEndOfToday( String admissionStatus) {
		return cohortIndicator("Patient Admissions Today", ReportUtils.map(dailyBedReturnCohortLibrary.patientsAdmittedByEndOfToday(admissionStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator totalPatientsByEndOfReportingPeriod( String admissionStatus, String occupationStatus) {
		return cohortIndicator("Total Patients by End of Reporting Period", ReportUtils.map(dailyBedReturnCohortLibrary.totalPatientsByEndOfReportingPeriod(admissionStatus,occupationStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator patientsDischargedByEndOfToday() {
		return cohortIndicator("Total Patients Discharged today", ReportUtils.map(dailyBedReturnCohortLibrary.patientsDischargedByEndOfToday(), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator totalPatientsRemainingInWardByEndOfReportingPeriod(String admissionStatus) {
		return cohortIndicator("Total Patients in Ward", ReportUtils.map(dailyBedReturnCohortLibrary.totalPatientsRemainingInWardByEndOfReportingPeriod(admissionStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator totalInterWardTransferDischargeReportingPeriod(String admissionStatus) {
		return cohortIndicator("Total Patients Inter ward discharge", ReportUtils.map(dailyBedReturnCohortLibrary.totalInterWardTransferDischargeReportingPeriod(admissionStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator totalInterWardAdmissionTransferReportingPeriod(String admissionStatus) {
		return cohortIndicator("Total Patients Inter ward Admission", ReportUtils.map(dailyBedReturnCohortLibrary.totalInterWardAdmissionTransferReportingPeriod(admissionStatus), "startDate=${startDate},endDate=${endDate}"));
	}

}