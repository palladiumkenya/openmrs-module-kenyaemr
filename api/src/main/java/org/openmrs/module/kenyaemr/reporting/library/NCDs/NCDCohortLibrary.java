/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.NCDs;

import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;

import java.util.Date;

public class NCDCohortLibrary {



    public CohortDefinition hasVisitDate() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Patients with Visit Date");
        cd.setQuery("SELECT DISTINCT patient_id FROM kenyaemr_etl.etl_patient_demographics " +
                "WHERE visit_date IS NOT NULL AND visit_date BETWEEN :startDate AND :endDate");
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        return cd;
    }
    //demographics data
    // Patients with a clinic number
    public CohortDefinition hasPatientNumber() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Patients with Clinic Number");
        cd.setQuery("SELECT DISTINCT patient_id FROM kenyaemr_etl.etl_patient_demographics " +
                "WHERE patient_clinic_number IS NOT NULL");
        return cd;
    }

    // Patients with date of birth
    public CohortDefinition hasDateOfBirth() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Patients with DOB");
        cd.setQuery("SELECT DISTINCT patient_id FROM kenyaemr_etl.etl_patient_demographics " +
                "WHERE dob IS NOT NULL");
        return cd;
    }

   // Patient's Sex
    public CohortDefinition hasGender() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Patients with Gender");
        cd.setQuery("SELECT DISTINCT patient_id FROM kenyaemr_etl.etl_patient_demographics " +
                "WHERE gender IS NOT NULL");
        return cd;
    }

    // Patients with phone number
    public CohortDefinition hasPhoneNumber() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Patients with Phone Number");
        cd.setQuery("SELECT DISTINCT patient_id FROM kenyaemr_etl.etl_patient_demographics " +
                "WHERE phone_number IS NOT NULL");
        return cd;
    }

    // Patients with next of kin contact
    public CohortDefinition hasNextOfKinPhone() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Patients with Next of Kin Phone");
        cd.setQuery("SELECT DISTINCT patient_id FROM kenyaemr_etl.etl_patient_demographics " +
                "WHERE next_of_kin_phone IS NOT NULL");
        return cd;
    }

    // Patients with SHIF number
    public CohortDefinition hasShifNumber() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Patients with SHIF Number");
        cd.setQuery("SELECT DISTINCT patient_id FROM kenyaemr_etl.etl_patient_demographics " +
                "WHERE shif_number IS NOT NULL");
        return cd;
    }

    //ncd forms data

    // Patients with Diagnosis
    public CohortDefinition hasDiagnosis() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Patients with SHIF Number");
        cd.setQuery("SELECT DISTINCT patient_id FROM kenyaemr_etl.etl_patient_demographics " +
                "WHERE Diagnosis IS NOT NULL");
        return cd;
    }



    public CohortDefinition allNcdPatients() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("All NCD Patients");
        cd.setQuery("SELECT DISTINCT patient_id FROM kenyaemr_etl.etl_ncd_visit " +
                "WHERE visit_date BETWEEN :startDate AND :endDate");
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        return cd;
    }



}
