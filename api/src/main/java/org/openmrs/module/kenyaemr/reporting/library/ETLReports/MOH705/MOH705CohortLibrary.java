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
		String sqlQuery = "SELECT ce.patient_id\n" +
			"FROM kenyaemr_etl.etl_clinical_encounter ce\n" +
			"         INNER JOIN kenyaemr_etl.etl_patient_demographics p ON p.patient_id = ce.patient_id AND p.voided = 0\n" +
			"         INNER JOIN openmrs.encounter_diagnosis ed ON ed.patient_id = ce.patient_id AND ed.encounter_id = ce.encounter_id\n" +
			"WHERE date(ce.visit_date) BETWEEN date(:startDate) AND date(:endDate)\n" +
			"  AND ce.voided = 0\n" +
			"  AND ed.diagnosis_coded in " + diagnosis + "\n" +
			"  AND ce.diagnosis_category = 'New'\n" +
			"UNION ALL\n" +
			"SELECT sc.patient_id\n" +
			"FROM kenyaemr_etl.etl_special_clinics sc\n" +
			"         INNER JOIN kenyaemr_etl.etl_patient_demographics p ON p.patient_id = sc.patient_id AND p.voided = 0\n" +
			"         INNER JOIN openmrs.encounter_diagnosis ed ON ed.patient_id = sc.patient_id AND ed.encounter_id = sc.encounter_id\n" +
			"WHERE date(sc.visit_date) BETWEEN date(:startDate) AND date(:endDate)\n" +
			"  AND ed.diagnosis_coded in " + diagnosis + "\n" +
			"  AND sc.diagnosis_category = 'New';";
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
	public CohortDefinition allOtherDiseasesAboveFive(String age, String  diagnosisList) {
		String sqlQuery = "SELECT x.patient_id FROM encounter_diagnosis x\n" +
				"INNER JOIN kenyaemr_etl.etl_patient_demographics d on x.patient_id = d.patient_id and timestampdiff(YEAR, date(d.dob),date(:endDate)) "+ age +"\n" +
			    "WHERE x.diagnosis_coded NOT in ("+diagnosisList+") and date(x.date_created) between date(:startDate) and date(:endDate);\n";
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

	/**
	 * MOH705
	 * DeathDueToRoadTrafficInjuries
	 * @return
	 */
	public CohortDefinition deathDueToRoadTrafficInjuries(String age , String  diagnosisList) {

		String sqlQuery = "SELECT x.patient_id FROM encounter_diagnosis x\n" +
			"  INNER JOIN kenyaemr_etl.etl_patient_demographics d on x.patient_id = d.patient_id and timestampdiff(YEAR, date(d.dob),date(:endDate)) "+ age +"\n" +
			"  INNER JOIN kenyaemr_etl.etl_clinical_encounter v on x.encounter_id = v.encounter_id and v.patient_outcome = 159\n" +
			"    WHERE x.diagnosis_coded in ("+diagnosisList+") and date(x.date_created) between date(:startDate) and date(:endDate);\n";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("deathDueToRoadTrafficInjuries");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients died due to road traffic accidents");
		return cd;
	}

	/**
	 * MOH705
	 * RoadTrafficInjuries
	 * @return
	 */
	public CohortDefinition roadTrafficInjuries(String age , String  diagnosisList) {

		String sqlQuery = "SELECT x.patient_id FROM encounter_diagnosis x\n" +
			"  INNER JOIN kenyaemr_etl.etl_patient_demographics d on x.patient_id = d.patient_id and timestampdiff(YEAR, date(d.dob),date(:endDate)) "+ age +"\n" +
			"  INNER JOIN kenyaemr_etl.etl_clinical_encounter v on x.encounter_id = v.encounter_id and v.patient_outcome != 159\n" +
			"    WHERE x.diagnosis_coded in ("+diagnosisList+") and date(x.date_created) between date(:startDate) and date(:endDate);\n";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("roadTrafficInjuries");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients with road traffic injuries");
		return cd;
	}
	/**
	 * MOH705 and MOH705B
	 * Tests
	 * For Composition
	 * @return
	 */
	public CohortDefinition patientLabTests(List<Integer> labTestList) {
		String labTest = String.valueOf(labTestList).replaceAll("\\[", "(").replaceAll("\\]",")");
		String sqlQuery = "select x.patient_id from kenyaemr_etl.etl_laboratory_extract x\n" +
			" inner join kenyaemr_etl.etl_patient_demographics p on p.patient_id = x.patient_id and  p.voided = 0\n" +
			"where x.lab_test in " + labTest + " and date(visit_date) between date(:startDate) and date(:endDate);";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("patientsLabTest");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients lab test");
		return cd;
	}
	
	/**
	 * MOH705A and MOH705B
	 * Tested for Malaria per age
	 * Composition
	 * @return
	 */
	public CohortDefinition labTest(List<Integer> labTestList, String age) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("patientAge", ReportUtils.map(patientAge(age), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("labTest", ReportUtils.map(patientLabTests(labTestList), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("labTest AND patientAge");
		return cd;
	}
}
