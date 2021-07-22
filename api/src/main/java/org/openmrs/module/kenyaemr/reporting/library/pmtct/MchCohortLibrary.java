/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.pmtct;

import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * Library of cohort definitions used specifically in MCH Reports - Ilara health
 */
@Component
public class MchCohortLibrary {
    /**
     * Patients enrolled in MCH during a period
     * @return
     */
    public  CohortDefinition enrolledInMchDuringReportingPeriod() {
        String sqlQuery="SELECT e.patient_id\n" +
                "FROM kenyaemr_etl.etl_mch_enrollment e\n" +
                "inner join kenyaemr_etl.etl_patient_demographics p on p.patient_id = e.patient_id and p.voided = 0\n" +
                "where date(e.visit_date) BETWEEN date(:startDate) and date(:endDate)\n" +
                ";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("MCH_Enrollment");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Patients enrolled in MCH during a reporting period");
        return cd;
    }

    /**
     * Patients ever enrolled in MCH
     * @return
     */
    public  CohortDefinition everEnrolledInMch() {
        String sqlQuery="SELECT e.patient_id\n" +
                "FROM kenyaemr_etl.etl_mch_enrollment e\n" +
                "inner join kenyaemr_etl.etl_patient_demographics p on p.patient_id = e.patient_id and p.voided = 0\n" +
                ";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("MCH_Enrollment_ever");
        cd.setQuery(sqlQuery);/*
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));*/
        cd.setDescription("Patients ever enrolled in MCH");
        return cd;
    }

    /**
     * Patients enrolled at 1st anc attendance
     * @return
     */
    public  CohortDefinition enrolledInMCHAtFirstAncAttendance() {
        String sqlQuery="SELECT e.patient_id\n" +
                "FROM kenyaemr_etl.etl_mch_enrollment e\n" +
                "  inner join kenyaemr_etl.etl_patient_demographics p on p.patient_id = e.patient_id and p.voided = 0\n" +
                "  inner join kenyaemr_etl.etl_mch_antenatal_visit v on date(v.visit_date) = date(e.first_anc_visit_date) and v.anc_visit_number = 1\n" +
                "where date(v.visit_date) BETWEEN date(:startDate) and date(:endDate)\n" +
                ";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("enrolled_at_1st_anc_attendance");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Enrolled at first anc attendance");
        return cd;
    }

    /**
     * Patients enrolled at 4th anc attendance
     * @return
     */
    public  CohortDefinition fourthAncVisitAttendance() {
        String sqlQuery="SELECT v.patient_id\n" +
                "FROM kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "  inner join kenyaemr_etl.etl_patient_demographics p on p.patient_id = v.patient_id and p.voided = 0 \n" +
                "where date(v.visit_date) BETWEEN date(:startDate) and date(:endDate) and v.anc_visit_number = 4 \n" +
                ";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("enrolled_at_4th_anc_attendance");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Enrolled at fourth anc attendance");
        return cd;
    }

    /**
     * Patients enrolled at 8th anc attendance
     * @return
     */
    public  CohortDefinition eighthAncVisitAttendance() {
        String sqlQuery="SELECT v.patient_id\n" +
                "FROM kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "  inner join kenyaemr_etl.etl_patient_demographics p on p.patient_id = v.patient_id and p.voided = 0\n" +
                "where date(v.visit_date) BETWEEN date(:startDate) and date(:endDate) and v.anc_visit_number = 8\n" +
                ";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("enrolled_at_8th_anc_attendance");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Enrolled at eighth anc attendance");
        return cd;
    }

    /**
     * Patients who received all preventive services
     * @return
     */
    public  CohortDefinition receivedAllPreventiveServices() {
        String sqlQuery="SELECT patient_id from (\n" +
                "select\n" +
                "  v.encounter_id,v.patient_id,\n" +
                "  max(if(ps.preventive_service = 160428, v.bed_nets, null))  bed_nets,\n" +
                "  max(if(ps.preventive_service = 79413, v.deworming, null))  deworming,\n" +
                "  max(if(ps.preventive_service = 104677, v.iron_supplement, null))  iron_supplement,\n" +
                "  max(if(ps.preventive_service = 159610, v.IPT_malaria, null))  ipt_malaria,\n" +
                "  max(if(ps.preventive_service = 84879, v.TTT, null))  TTT\n" +
                "from kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "  left join kenyaemr_etl.etl_anc_preventive_services ps on ps.encounter_id = v.encounter_id\n" +
                "                                                           and ps.preventive_service in (160428,79413,104677,159610,84879)\n" +
                "WHERE date(v.visit_date) BETWEEN date(:startDate) and date(:endDate) and v.bed_nets = 'Yes' or v.deworming = 'Yes' or v.iron_supplement = 'Yes' or v.ipt_malaria = 'Yes' or v.TTT = 'Yes'\n" +
                "GROUP BY v.patient_id) ps\n" +
                "where bed_nets = 'Yes' and deworming = 'Yes' and iron_supplement = 'Yes' and ipt_malaria = 'Yes' and TTT = 'Yes';\n";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("received_all_preventive_services");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Received all preventive services");
        return cd;
    }
    /**
     * Patients who received some preventive services
     * @return
     */
    public CohortDefinition receivedSomePreventiveServices() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT patient_id from (\n" +
                "    select\n" +
                "      v.encounter_id,v.patient_id,\n" +
                "      max(if(ps.preventive_service = 160428, v.bed_nets, null))  bed_nets,\n" +
                "      max(if(ps.preventive_service = 79413, v.deworming, null))  deworming,\n" +
                "      max(if(ps.preventive_service = 104677, v.iron_supplement, null))  iron_supplement,\n" +
                "      max(if(ps.preventive_service = 159610, v.IPT_malaria, null))  ipt_malaria,\n" +
                "      max(if(ps.preventive_service = 84879, v.TTT, null))  TTT\n" +
                "    from kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "      left join kenyaemr_etl.etl_anc_preventive_services ps on ps.encounter_id = v.encounter_id\n" +
                "                                                               and ps.preventive_service in (160428,79413,104677,159610,84879)\n" +
                "    WHERE date(v.visit_date) BETWEEN date(:startDate) and date(:endDate) and v.bed_nets = 'Yes' or v.deworming = 'Yes' or v.iron_supplement = 'Yes' or v.ipt_malaria = 'Yes' or v.TTT = 'Yes'\n" +
                "    GROUP BY v.patient_id) ps\n" +
                "where bed_nets is null or deworming is null or iron_supplement is null or ipt_malaria is null or TTT is null;\n" +
                ";";

        cd.setName("received_some_preventive_services");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Received some preventive services");
        return cd;
    }

    /**
     * Patients who received some anc profiling
     * @return
     */
    public CohortDefinition receivedSomeAncProfiling() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT patient_id from (\n" +
                "SELECT\n" +
                "  patient_id,\n" +
                "  max(if(blood_group is not null, 'Yes', null)) blood_group,\n" +
                "  max(if(glucose_measurement is not null, 'Yes', null)) glucose_measurement,\n" +
                "  max(if(syphilis_test_status is not null, 'Yes', null)) syphilis_test_status,\n" +
                "  max(if(mps_at_enrollment is not null, 'Yes', null)) mps_at_enrollment,\n" +
                "  max(if(mps_at_visit is not null, 'Yes', null)) mps_at_visit,\n" +
                "  max(if(hemoglobin is not null, 'Yes', null)) hemoglobin,\n" +
                "  max(if(hiv_status_at_enrollment is not null and hiv_status_at_enrollment = 703, 703, null)) hiv_status_at_enrollment,\n" +
                "  max(if(hiv_test_at_visit is not null, 'Yes', null)) hiv_test_at_visit\n" +
                "from (\n" +
                "       SELECT\n" +
                "         e.patient_id,\n" +
                "         e.visit_date AS     enrollment_date,\n" +
                "         v.visit_date,\n" +
                "         e.blood_group,\n" +
                "         v.glucose_measurement,\n" +
                "         v.syphilis_test_status,\n" +
                "         v.bs_mps            mps_at_visit,\n" +
                "         e.bs_for_mps        mps_at_enrollment,\n" +
                "         v.hemoglobin,\n" +
                "         e.hiv_status        hiv_status_at_enrollment,\n" +
                "         v.final_test_result hiv_test_at_visit\n" +
                "       FROM kenyaemr_etl.etl_mch_enrollment e\n" +
                "         INNER JOIN kenyaemr_etl.etl_patient_demographics p ON p.patient_id = e.patient_id AND p.voided = 0\n" +
                "         LEFT JOIN kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "           ON v.patient_id = e.patient_id\n" +
                "        WHERE date(v.visit_date) BETWEEN date(:startDate) and date(:endDate)\n" +
                "     ) pr\n" +
                "GROUP BY patient_id\n" +
                "having coalesce(\n" +
                "    blood_group,\n" +
                "    glucose_measurement,\n" +
                "    syphilis_test_status,\n" +
                "    mps_at_enrollment,\n" +
                "    mps_at_visit,\n" +
                "    hemoglobin,\n" +
                "    hiv_status_at_enrollment,\n" +
                "    hiv_test_at_visit) is not null\n" +
                ") t;";

        cd.setName("received_some_anc_profiling");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Received some anc profiling");
        return cd;
    }

    /**
     * Patients who received all anc profiling
     * @return
     */
    public CohortDefinition receivedAllAncProfiling() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery="SELECT patient_id from (\n" +
                "SELECT\n" +
                "  patient_id,\n" +
                "  max(if(blood_group is not null, 'Yes', null)) blood_group,\n" +
                "  max(if(glucose_measurement is not null, 'Yes', null)) glucose_measurement,\n" +
                "  max(if(syphilis_test_status is not null, 'Yes', null)) syphilis_test_status,\n" +
                "  max(if(mps_at_enrollment is not null, 'Yes', null)) mps_at_enrollment,\n" +
                "  max(if(mps_at_visit is not null, 'Yes', null)) mps_at_visit,\n" +
                "  max(if(hemoglobin is not null, 'Yes', null)) hemoglobin,\n" +
                "  max(if(hiv_status_at_enrollment is not null, hiv_status_at_enrollment, null)) hiv_status_at_enrollment,\n" +
                "  max(if(hiv_test_at_visit is not null, 'Yes', null)) hiv_test_at_visit\n" +
                "from (\n" +
                "       SELECT\n" +
                "         e.patient_id,\n" +
                "         e.visit_date AS     enrollment_date,\n" +
                "         e.blood_group,\n" +
                "         v.glucose_measurement,\n" +
                "         v.syphilis_test_status,\n" +
                "         v.bs_mps            mps_at_visit,\n" +
                "         e.bs_for_mps        mps_at_enrollment,\n" +
                "         v.hemoglobin,\n" +
                "         e.hiv_status        hiv_status_at_enrollment,\n" +
                "         v.final_test_result hiv_test_at_visit\n" +
                "       FROM kenyaemr_etl.etl_mch_enrollment e\n" +
                "         INNER JOIN kenyaemr_etl.etl_patient_demographics p ON p.patient_id = e.patient_id AND p.voided = 0\n" +
                "         LEFT JOIN kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "           ON v.patient_id = e.patient_id\n" +
                "       WHERE date(v.visit_date) BETWEEN date(:startDate) and date(:endDate)\n" +
                "     ) pr\n" +
                "GROUP BY patient_id\n" +
                "having blood_group = 'Yes'\n" +
                "       and glucose_measurement = 'Yes'\n" +
                "       and syphilis_test_status = 'Yes'\n" +
                "       and (mps_at_enrollment = 'Yes' or mps_at_visit = 'Yes')\n" +
                "       and hemoglobin = 'Yes'\n" +
                "       and ((hiv_status_at_enrollment != 703 and hiv_test_at_visit is not null) or hiv_status_at_enrollment = 703)\n" +
                ") t\n" +
                ";";

        cd.setName("received_all_anc_profiling");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Received all anc profiling");
        return cd;
    }

}


