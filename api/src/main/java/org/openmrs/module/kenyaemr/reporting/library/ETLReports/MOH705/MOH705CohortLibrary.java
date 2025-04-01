/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH705;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Library of cohort definitions for MOH705A
 */
@Component
public class MOH705CohortLibrary {
	/**
	 * MOH705
	 * Diagnosis
	 * For Composition
	 * @return
	 */
	public CohortDefinition patientDiagnosis(List<Integer> diagnosisList) {
		String diagnosis = String.valueOf(diagnosisList).replaceAll("\\[", "(").replaceAll("\\]",")");
		String sqlQuery = "select patient_id from encounter_diagnosis where diagnosis_coded in " + diagnosis + " and date(date_created) between date(:startDate) and date(:endDate);";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("patientsDiagnosis");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients diagnosis");
		return cd;
	}
	/**
	 * MOH705A
	 * Age
	 * For Composition
	 * @return
	 */
	public CohortDefinition patientAge(String age) {
		String sqlQuery = "select d.patient_id from kenyaemr_etl.etl_patient_demographics d where timestampdiff(YEAR, date(d.dob),date(:endDate)) " + age + ";";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("patientsAge");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients age");
		return cd;
	}

	/**
	 * MOH705A and MOH705B
	 * Diagnosis per age
	 * Composition
	 * @return
	 */
	public CohortDefinition diagnosis(List<Integer> diagnosis, String age) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("patientAge", ReportUtils.map(patientAge(age), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("diagnosis", ReportUtils.map(patientDiagnosis(diagnosis), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("diagnosis AND patientAge");
		return cd;
	}
	/**
	 * MOH705
	 * OtherDiseasesUnderFive
	 * @return
	 */
	public CohortDefinition allOtherDiseasesUnderFive(String age , String  diagnosisList) {

		String sqlQuery = "SELECT x.patient_id FROM encounter_diagnosis x\n" +
				"  INNER JOIN kenyaemr_etl.etl_patient_demographics d on x.patient_id = d.patient_id and timestampdiff(YEAR, date(d.dob),date(:endDate)) "+ age +"\n" +
				"    WHERE x.diagnosis_coded NOT in ("+diagnosisList+") and date(x.date_created) between date(:startDate) and date(:endDate);\n";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("allOtherDiseasesUnderFive");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients Other Diagnosis Under Five");
		return cd;
	}

	/**
	 * MOH705
	 * OtherDiseasesAboveFive
	 * @return
	 */
	public CohortDefinition allOtherDiseasesAboveFive(String age) {
		String sqlQuery = "SELECT x.patient_id FROM encounter_diagnosis x\n" +
				"INNER JOIN kenyaemr_etl.etl_patient_demographics d on x.patient_id = d.patient_id and timestampdiff(YEAR, date(d.dob),date(:endDate)) "+ age +"\n" +
				"WHERE x.diagnosis_coded NOT in (5018,160156,152,145622,134369,124957,5258,892,134561,116986,133671,166623,160148,168740,160152,124,141,112992,111633,117152,116699,134725,121629,140832,71,123093,121375,114100,998,117399,77,78,123964,119022,148432,114088,86,123160,146623,126323,166,119481,155,121005,119270,118994,114413,124078,113217,122759,123112,120743,7592,116350,143074,168741) and date(x.date_created) between date(:startDate) and date(:endDate);\n";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("allOtherDiseasesAboveFive");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients Other Diagnosis Above Five");
		return cd;
	}
	/**
	 * MOH705
	 * New Attendances
	 * @return
	 */


	public CohortDefinition newAttendances(String age) {
		String sqlQuery = "SELECT v.patient_id FROM kenyaemr_etl.etl_clinical_encounter v " +
				"INNER JOIN kenyaemr_etl.etl_patient_demographics d ON v.patient_id = d.patient_id " +
				"WHERE timestampdiff(YEAR, date(d.dob), date(:endDate)) " + age + " " +
				"AND v.visit_type = 'New visit' " +
				"AND date(v.visit_date) BETWEEN date(:startDate) AND date(:endDate);";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("newAttendances");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients who are new attendances");
		return cd;
	}
	/**
	 * MOH705
	 * Re-Attendances
	 * @return
	 */

	public CohortDefinition reAttendances(String age) {
		String sqlQuery = "SELECT v.patient_id FROM kenyaemr_etl.etl_clinical_encounter v " +
				"INNER JOIN kenyaemr_etl.etl_patient_demographics d ON v.patient_id = d.patient_id " +
				"WHERE timestampdiff(YEAR, date(d.dob), date(:endDate)) " + age + " " +
				"AND v.visit_type = 'Revisit' " +
				"AND date(v.visit_date) BETWEEN date(:startDate) AND date(:endDate);";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("reAttendances");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients who are revisit attendances");
		return cd;
	}
	/**
	 * MOH705
	 * Referrals from other health facilities
	 * @return
	 */
	public CohortDefinition referralsFromOtherFacilities(String age) {
		String sqlQuery = "SELECT v.patient_id FROM kenyaemr_etl.etl_clinical_encounter v\n" +
			"INNER JOIN kenyaemr_etl.etl_patient_demographics d on v.patient_id = d.patient_id and timestampdiff(YEAR, date(d.dob),date(:endDate)) "+ age +"\n" +
			"WHERE v.referral_to = 'This health facility' and date(v.visit_date) between date(:startDate) and date(:endDate);";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("referralsFromOther");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients who are referrals To From Other");
		return cd;
	}
	/**
	 * MOH705
	 * Referrals to other health facilities
	 * @return
	 */
	public CohortDefinition referralsToOtherFacilities(String age) {
		String sqlQuery = "SELECT v.patient_id FROM kenyaemr_etl.etl_clinical_encounter v\n" +
			"INNER JOIN kenyaemr_etl.etl_patient_demographics d on v.patient_id = d.patient_id and timestampdiff(YEAR, date(d.dob),date(:endDate)) "+ age +"\n" +
			"WHERE v.referral_to = 'Other health facility' and date(v.visit_date) between date(:startDate) and date(:endDate);";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("referralsToOther");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients who are referrals To Other");
		return cd;
	}
}
