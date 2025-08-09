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

	public CohortIndicator currentTotalBeds( String occupationStatus,String vacantStatus) {
		return cohortIndicator("Total beds Current count", ReportUtils.map(dailyBedReturnCohortLibrary.currentTotalBeds(occupationStatus,vacantStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator previousTotalBeds( String occupationStatus, String vacantStatus) {
		return cohortIndicator("Total beds previous count", ReportUtils.map(dailyBedReturnCohortLibrary.previousTotalBeds(occupationStatus,vacantStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator patientDischargedStatus(Integer dischargeStatus) {
		return cohortIndicator("Discharged Status", ReportUtils.map(dailyBedReturnCohortLibrary.patientDischargedStatus(dischargeStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator totalPatientDischargedStatus() {
		return cohortIndicator("Discharged Status", ReportUtils.map(dailyBedReturnCohortLibrary.totalPatientDischargedStatus(), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator patientAdmissionStatus(Integer admissionStatus) {
		return cohortIndicator("Discharged Status", ReportUtils.map(dailyBedReturnCohortLibrary.patientAdmissionStatus(admissionStatus), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator patientsAdmittedByEndOfToday( Integer admissionStatus) {
		return cohortIndicator("Patient Admissions Today", ReportUtils.map(dailyBedReturnCohortLibrary.patientsAdmittedByEndOfToday(admissionStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator totalPatientsByEndOfReportingPeriod( Integer admissionStatus, String occupationStatus) {
		return cohortIndicator("Total Patients by End of Reporting Period", ReportUtils.map(dailyBedReturnCohortLibrary.totalPatientsByEndOfReportingPeriod(admissionStatus,occupationStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator patientsDischargedByEndOfToday() {
		return cohortIndicator("Total Patients Discharged today", ReportUtils.map(dailyBedReturnCohortLibrary.patientsDischargedByEndOfToday(), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator totalPatientsRemainingInWardByEndOfReportingPeriod(Integer admissionStatus) {
		return cohortIndicator("Total Patients in Ward", ReportUtils.map(dailyBedReturnCohortLibrary.totalPatientsRemainingInWardByEndOfReportingPeriod(admissionStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator totalInterWardTransferDischargeReportingPeriod(Integer admissionStatus) {
		return cohortIndicator("Total Patients Inter ward discharge", ReportUtils.map(dailyBedReturnCohortLibrary.totalInterWardTransferDischargeReportingPeriod(admissionStatus), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator totalInterWardAdmissionTransferReportingPeriod(Integer admissionStatus) {
		return cohortIndicator("Total Patients Inter ward Admission", ReportUtils.map(dailyBedReturnCohortLibrary.totalInterWardAdmissionTransferReportingPeriod(admissionStatus), "startDate=${startDate},endDate=${endDate}"));
	}

}