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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Moh740CohortLibrary {

    protected static final Log log = LogFactory.getLog(Moh740CohortLibrary.class);


    public CohortDefinition cumulativePatientWithDiabetes() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.disease_type = 142486 \n"+
                "and ne.visit_date between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Cumulative no. of diabetes");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Cumulative no. of diabetes");
        return cd;
    }
    public CohortDefinition cumulativePatientWithHypertension() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.disease_type = 117399 \n"+
                "and ne.visit_date between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Cumulative no. of Hypertension");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Cumulative no. of Hypertension");
        return cd;
    }

    public CohortDefinition newDiagnosedWithDiabetes() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.disease_type = 117399 \n"+
                "and ne.diabetes_condition = 1000488 and ne.visit_date between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("New Diagnosed with Diabetes");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("New Diagnosed with Diabetes");
        return cd;
    }

    public CohortDefinition newDiagnosedWithHypertension() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.disease_type = 117399 \n"+
                "and ne.hypertension_condition = 1000490 and ne.visit_date between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("New Diagnosed with Hypertension");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("New Diagnosed with Hypertension");
        return cd;
    }

    public CohortDefinition cumulativePatientWithDMAndHTN() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.disease_type = 166020 \n"+
                "and ne.visit_date between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Cumulative with DMA and HTN");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Cumulative No. with DMA and HTN");
        return cd;
    }

    public CohortDefinition patientWithPreExistingDMandHTN() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.disease_type = 166020 \n"+
                "and ne.comorbid_condition = 1000492 and ne.visit_date between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Diagnosed Diabetes and Hypertension");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Pre-Existing DM and HTN");
        return cd;
    }

    public CohortDefinition patientWithKnownDM() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.disease_type = 142486 \n"+
                "and ne.diabetes_condition = 1000489 and ne.visit_date between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Known DM");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Patient with Known DM");
        return cd;
    }

    public CohortDefinition preExistingPatientWithDM() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.disease_type = 142486 \n"+
                "and ne.diabetes_condition = 1000489 and ne.visit_date between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Known DM");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Patient with Known DM");
        return cd;
    }

    public CohortDefinition preExistingPatientWithHTN() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.disease_type = 117399 \n"+
                "and ne.hypertension_condition = 1000491 and ne.visit_date between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Known DM");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Patient with Known DM");
        return cd;
    }

    public CohortDefinition preExistingPatientWithComorbidDMandHTN() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.disease_type = 166020 \n"+
                "and ne.comorbid_condition = 1000493 and ne.visit_date between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Known DM");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Patient with Known DM");
        return cd;
    }

    public CohortDefinition patientFirstVisitToClinic(int visitType) {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) " +
                "and ne.visit_type = "+visitType+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("First Visit To Clinic");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("First Visit To Clinic");
        return cd;
    }

    public CohortDefinition cumulativePatientInCare() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate)";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Cumulative No. of Patients");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Cumulative No. of Patients");
        return cd;
    }

    public CohortDefinition patientWithDiabetesByTypeOne() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.diabetes_type = 142474";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Diabetes By Type One");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of Diabetes By Type One");
        return cd;
    }

    public CohortDefinition patientWithDiabetesByTypeTwo() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.diabetes_type = 2004524";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Diabetes By Type Two");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of Diabetes By Type Two");
        return cd;
    }

    public CohortDefinition patientWithGestational() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.diabetes_type = 117807";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Gestational diabetes melitus");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. diagnosed for gestational diabetes melitus");
        return cd;
    }

    public CohortDefinition patientWithDiabetesSecondaryToOther() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.diabetes_type = 126985";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Diabetes By Type Two");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of Diabetes By Type Two");
        return cd;
    }

    public CohortDefinition patientOnInsulinTreatment() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.treatment_given like '%Insulin%'";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("patients on insulin");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of patients on insulin");
        return cd;
    }

    public CohortDefinition patientOnOGLAsTreatment() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.treatment_given like '%OGLAs%' ";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("patients on OGLAs");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of patients on OGLAs");
        return cd;
    }

    public CohortDefinition patientOnInsulinAndOGLAsTreatment() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.treatment_given like '%Insulin and OGLAs%' ";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("patients on both (Insulin and OGLAs)");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of patients on both (Insulin and OGLAs)");
        return cd;
    }

    public CohortDefinition patientOnDietAndExerciseTreatment() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.treatment_given like '%Diet & physical activity%' ";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("patients on diet and exercise only (DM and HTN)");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of patients on diet and exercise only (DM and HTN)");
        return cd;
    }

    public CohortDefinition patientNumberDoneHbA1c() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne inner join kenyaemr_etl.etl_laboratory_extract la on ne.patient_id = la.patient_id " +
                "where ne.visit_date between date(:startDate) and date(:endDate) and la.lab_test = 159644";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("patients done HbA1c");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of patients done HbA1c");
        return cd;
    }

    public CohortDefinition patientNumberMetHbA1cTarget() { // 159644

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne inner join kenyaemr_etl.etl_laboratory_extract la on ne.patient_id = la.patient_id " +
                "where ne.visit_date between date(:startDate) and date(:endDate) and la.lab_test = 159644 and la.test_result < 7";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Patients that met HbA1c target (< 7%)");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. that met HbA1c target (< 7%)");
        return cd;
    }

    public CohortDefinition patientWithHypertension() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.disease_type = 117399;";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("No. of Hypertension");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of Hypertension");
        return cd;
    }

    public CohortDefinition patientWithAntihypertensives() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.treatment_given like '%Anti hypertensives%'";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("patients on antihypertensives");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of patients on antihypertensives");
        return cd;
    }

    public CohortDefinition patientWithHighBPOnClinicVisit() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne inner join kenyaemr_etl.etl_patient_triage pt on ne.patient_id = pt.patient_id " +
                "where ne.visit_date between date(:startDate) and date(:endDate) and pt.systolic_pressure >= 140 and pt.diastolic_pressure >=90";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("patients with high BP at clinic visit (≥140/90)");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. with high BP at clinic visit (≥140/90)");
        return cd;
    }

    public CohortDefinition newDiagnosedWithStroke() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.new_complications like '%Stroke%'";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("new no. of patients with Stroke");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total new no. of patients with Stroke");
        return cd;
    }

    public CohortDefinition newDiagnosedWithHeartDisease() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.new_complications like '%Ischaemic Heart Disease%'";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("new no. of patients with Ischemic heart disease");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total no. of patients with Ischemic heart disease");
        return cd;
    }

    public CohortDefinition newDiagnosedWithPeripheralDisease() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.new_complications like '%Peripheral Vascular Disease%'";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("new no. of patients with Peripheral vascular/artery disease");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total no. of patients with Peripheral vascular/artery disease");
        return cd;
    }

    public CohortDefinition newDiagnosedWithHeartFailure() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.new_complications like '%Heart failure%'";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("new no. of patients with Heart failure");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total no. of patients with Heart failure");
        return cd;
    }

    public CohortDefinition newDiagnosedPatientWithNeuropathies() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.new_complications like '%Nephropathy%'";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("new diagnosis Patients with neuropathies");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("New diagnosis Patients with neuropathies");
        return cd;
    }

    public CohortDefinition noPatientScreenedForDiabeticFoot() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne " +
                "where ne.visit_date between date(:startDate) and date(:endDate) and ne.new_complications like '%Diabetic Foot%' or ne.existing_complications like '%Diabetic Foot%' ";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("No. of patients screened for diabetic foot");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of patients screened for diabetic foot");
        return cd;
    }

    public CohortDefinition newDiagnosedPatientWithFootUlcer() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.new_complications like '%Diabetic Foot Ulcer%'";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("New diagnosis with diabetic foot ulcer");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("New diagnosis with diabetic foot ulcer");
        return cd;
    }

    public CohortDefinition patientFeetSavedThroughTreatment() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.footcare_outcome = 162130 ";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("No. of feet saved through treatment");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of feet saved through treatment");
        return cd;
    }

    public CohortDefinition patientWithAmputatedDiabeticFoot() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.footcare_outcome = 164009";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("No. of amputation due to diabetic foot");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of amputation due to diabetic foot");
        return cd;
    }

    public CohortDefinition newPatientDiagnosedWithKidneyComplication() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.new_complications like '%Kidney Failure(CKD)%'";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("New diagnosis with kidney complications");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("New diagnosis with kidney complications");
        return cd;
    }

    public CohortDefinition newPatientDiagnosedWithDiabeticRetinopathy() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.new_complications like '%Retinopathy%'";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("New diagnosis with diabetic retinopathy ");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("New diagnosis with diabetic retinopathy");
        return cd;
    }

    public CohortDefinition noPatientScreenedForTuberculosis() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and (ne.tb_screening = 142177 or ne.tb_screening = 1660 or ne.tb_screening = 1662)";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("No. Screened for Tuberculosis");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. Screened for Tuberculosis");
        return cd;
    }

    public CohortDefinition noPatientScreenedPositiveTuberculosis() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.tb_screening = 1662";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("No. Screened Positive for Tuberculosis ");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. Screened Positive for Tuberculosis ");
        return cd;
    }

    public CohortDefinition noPatientEnrolledSHA() {

        String sqlQuery = "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne inner join kenyaemr_etl.etl_patient_demographics pd on ne.patient_id = pd.patient_id " +
                "where ne.visit_date between date(:startDate) and date(:endDate) and pd.sha_number is not null";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("No. enrolled with SHA ");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. enrolled with SHA ");
        return cd;
    }

    public CohortDefinition patientWithcondition() {

        String sqlQuery =  "select ne.patient_id from kenyaemr_etl.etl_ncd_enrollment ne where ne.visit_date between date(:startDate) and date(:endDate) and ne.disease_type = 117399 ";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("No. of Patient Condition");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of Patient Condition");
        return cd;
    }

}
