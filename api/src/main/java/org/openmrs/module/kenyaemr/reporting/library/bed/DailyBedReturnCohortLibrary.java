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
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Library of cohort definitions used in Daily bed return
 */
@Component
public class DailyBedReturnCohortLibrary {

    public CohortDefinition patientCurrentBedOccupationStatus(String occupationStatus) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select ba.patient_id \n" +
                "from bed_patient_assignment_map ba \n" +
                "inner join bed bd on ba.bed_id = bd.bed_id\n" +
                "where date(bd.date_created) between date(:startDate) and date(:endDate) \n" +
                "and bd.status = '"+ occupationStatus+"' \n" +
                " GROUP BY ba.patient_id;";
        cd.setName("Bed Occupation Status");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Bed Occupation Status");
        return cd;
    }
    public CohortDefinition patientPreviousBedOccupationStatus(String occupationStatus) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select ba.patient_id \n" +
                "from bed_patient_assignment_map ba \n" +
                "inner join bed bd on ba.bed_id = bd.bed_id\n" +
                "where date(bd.date_created) <= DATE_SUB(DATE(:startDate), INTERVAL 1 DAY)\n" +
                "and bd.status = '"+ occupationStatus+"' \n" +
                " GROUP BY ba.patient_id;";
        cd.setName("Previous Bed Occupation Status");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Previous Bed Occupation Status");
        return cd;
    }
    public CohortDefinition patientCurrentlyBedTagsOccupationStatus(String occupationStatus) {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery = "select bd.patient_id from bed_patient_assignment_map bd \n" +
                " inner join bed_tag_map bt on bd.bed_id = bt.bed_id\n" +
                "inner join bed ba on bd.bed_id = ba.bed_id\n" +
                "  where date(bd.date_created) between date(:startDate) and date(:endDate)\n" +
                " and ba.status = '"+ occupationStatus+"' \n" +
                "GROUP BY bd.patient_id;";
        cd.setName("Bed Tags Occupation Status");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Bed Tags Occupation Status");
        return cd;
    }


    public CohortDefinition totalCurrentBedsAndBedTagsOccupiedStatus(String occupationStatus) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.setName("Total current Beds and Bed tags");
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("patientCurrentlyBedTagsOccupationStatus", ReportUtils.map(patientCurrentlyBedTagsOccupationStatus(occupationStatus), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("patientCurrentBedOccupationStatus", ReportUtils.map(patientCurrentBedOccupationStatus(occupationStatus), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("patientCurrentlyBedTagsOccupationStatus OR patientCurrentBedOccupationStatus");
        cd.setDescription("Total current Beds and Bed tags");
        return cd;
    }


    public CohortDefinition patientPreviouslyBedTagsOccupationStatus(String occupationStatus) {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery = "select bd.patient_id from bed_patient_assignment_map bd \n" +
                " inner join bed_tag_map bt on bd.bed_id = bt.bed_id\n" +
                "inner join bed ba on bd.bed_id = ba.bed_id\n" +
                "where date(bd.date_created) <= DATE_SUB(DATE(:startDate), INTERVAL 1 DAY)\n" +
                " and ba.status = '"+ occupationStatus+"' \n" +
                "GROUP BY bd.patient_id;";
        cd.setName("Bed Occupation Status");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Bed Occupation Status");
        return cd;
    }
    public CohortDefinition patientDischargedStatus(Integer dischargeStatus) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select patient_id\n" +
                "from kenyaemr_etl.etl_inpatient_discharge \n" +
                "where date(visit_date) between date(:startDate) and date(:endDate) \n" +
                "and discharge_status = '" + dischargeStatus + "';";
        cd.setName("Patient Discharged");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Patient Discharged");
        return cd;
    }
    public CohortDefinition patientAdmissionStatus(String admissionStatus) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select patient_id\n" +
                "from encounter \n" +
                "where date(date_created) between date(:startDate) and date(:endDate) \n" +
                "and uuid = '" + admissionStatus + "';";

        cd.setName("Patient Discharged");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Patient Discharged");
        return cd;
    }

    public CohortDefinition patientTransferStatus() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select patient_id\n" +
                "from encounter \n" +
                "where date(date_created) between date(:startDate) and date(:endDate) \n" +
                "and encounter_type = 148;";

        cd.setName("Patient Transfer");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Patient Transfer");
        return cd;
    }
    public CohortDefinition totalPatientDischargedStatus() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select patient_id\n" +
                "from kenyaemr_etl.etl_inpatient_discharge \n" +
                "where date(visit_date) between date(:startDate) and date(:endDate) \n" +
                "and discharge_status in (160431,162677,1694,159,164165)";
        cd.setName("Total Patient Discharged");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total Patient Discharged");
        return cd;
    }

    public CohortDefinition totalInterWardTransferDischargeReportingPeriod(String admissionStatus) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.setName("Total Patients Inter ward discharge");
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("patientAdmissionStatus", ReportUtils.map(patientAdmissionStatus(admissionStatus), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("totalPatientDischargedStatus", ReportUtils.map(totalPatientDischargedStatus(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("patientAdmissionStatus AND totalPatientDischargedStatus");
        cd.setDescription("Total Patients Inter ward discharge");
        return cd;
    }

    public CohortDefinition currentTotalBeds(String occupationStatus, String vacantStatus) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select ba.patient_id \n" +
                "from bed_patient_assignment_map ba \n" +
                "inner join bed bd on ba.bed_id = bd.bed_id\n" +
                "where date(bd.date_created) between date(:startDate) and date(:endDate) \n" +
                "and bd.status in ('" + occupationStatus + "', '" + vacantStatus + "') \n"+
                "GROUP BY ba.patient_id;";
        cd.setName("Total Bed Occupation Status");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total Bed Occupation Status");
        return cd;
    }
    public CohortDefinition currentTotalBedTags(String occupationStatus, String vacantStatus) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery =
                "select ba.patient_id \n" +
                "from bed_patient_assignment_map ba \n" +
                "inner join bed_tag_map bd on ba.bed_id = bd.bed_id\n" +
                "inner join bed bt on bd.bed_id = bt.bed_id\n" +
                "where date(bd.date_created) between date(:startDate) and date(:endDate) \n" +
                "and bt.status in ('" + occupationStatus + "', '" + vacantStatus + "') \n"+
                "GROUP BY ba.patient_id;";
        cd.setName("Total current bed tag Occupation Status");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total current Bed tag Occupation Status");
        return cd;
    }

    public CohortDefinition totalCurrentBedsAndBedTags(String occupationStatus, String vacantStatus) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.setName("Total current Beds and Bed tags");
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("currentTotalBeds", ReportUtils.map(currentTotalBeds(occupationStatus, vacantStatus), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("currentTotalBedTags", ReportUtils.map(currentTotalBedTags(occupationStatus, vacantStatus), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("currentTotalBeds OR currentTotalBedTags");
        cd.setDescription("Total current Beds and Bed tags");
        return cd;
    }

    public CohortDefinition previousTotalBedTags(String occupationStatus, String vacantStatus) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select ba.patient_id \n" +
                "from bed_patient_assignment_map ba \n" +
                "inner join bed_tag_map bt on ba.bed_id = bt.bed_id\n" +
                "inner join bed bd on bt.bed_id = bd.bed_id\n" +
                "where date(bd.date_created) <= DATE_SUB(DATE(:startDate), INTERVAL 1 DAY)\n" +
                "and bd.status in ('" + occupationStatus + "', '" + vacantStatus + "') \n" +
                "GROUP BY ba.patient_id;";
        cd.setName("Previous Total Bed Tags Occupation Status");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Previous Total Bed Tags Occupation Status");
        return cd;
    }
    public CohortDefinition previousTotalBeds(String occupationStatus, String vacantStatus) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select ba.patient_id \n" +
                "from bed_patient_assignment_map ba \n" +
                "inner join bed bd on ba.bed_id = bd.bed_id\n" +
                "where date(bd.date_created) <= DATE_SUB(DATE(:startDate), INTERVAL 1 DAY)\n" +
                "and bd.status in ('" + occupationStatus + "', '" + vacantStatus + "') \n" +
                "GROUP BY ba.patient_id;"; // Ensure unique patient counts
        cd.setName("Previous Total Bed Occupation Status");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Previous Total Bed Occupation Status");
        return cd;
    }

    public CohortDefinition allPreviousTotalBedsAndBedTags(String occupationStatus, String vacantStatus) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.setName("Previous Total Beds and Bed Tags");
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("previousTotalBeds", ReportUtils.map(previousTotalBeds(occupationStatus, vacantStatus), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("previousTotalBedTags", ReportUtils.map(previousTotalBedTags(occupationStatus, vacantStatus), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("previousTotalBeds OR previousTotalBedTags");
        cd.setDescription("All Previous Total Beds and Bed Tags");
        return cd;
    }
    public CohortDefinition patientsAdmittedByEndOfToday(String admissionStatus) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select patient_id\n" +
                "from encounter \n" +
                "where date(date_created) = CURDATE() \n" +
                "and encounter_type = '" + admissionStatus + "';";
        cd.setName("Patient Admissions Today");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Patient Admissions Today");
        return cd;
    }
    public CohortDefinition patientsDischargedByEndOfToday() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select patient_id\n" +
                "from kenyaemr_etl.etl_inpatient_discharge\n" +
                "where date(visit_date) = CURDATE();";
        cd.setName("Patient Discharged Today");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Patients Discharged today.");
        return cd;
    }
    public CohortDefinition totalInterWardAdmissionTransferReportingPeriod(String admissionStatus) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.setName("Total Patients by End of Reporting Period");
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("patientAdmissionStatus", ReportUtils.map(patientAdmissionStatus(admissionStatus), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("patientTransferStatus", ReportUtils.map(patientTransferStatus(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("patientAdmissionStatus AND patientTransferStatus");
        cd.setDescription("Total Patients by End of Reporting Period");
        return cd;
    }
    public CohortDefinition totalPatientsByEndOfReportingPeriod(String admissionStatus, String occupationStatus) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.setName("Total Patients by End of Reporting Period");
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("patientAdmissionStatus", ReportUtils.map(patientAdmissionStatus(admissionStatus), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("patientPreviousBedOccupationStatus", ReportUtils.map(patientPreviousBedOccupationStatus(occupationStatus), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("patientAdmissionStatus OR patientPreviousBedOccupationStatus");
        cd.setDescription("Total Patients by End of Reporting Period");
        return cd;
    }
    public CohortDefinition totalPatientsRemainingInWardByEndOfReportingPeriod(String admissionStatus) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.setName("Total Patients at the ward at the End of Reporting Period");
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("patientAdmissionStatus", ReportUtils.map(patientAdmissionStatus(admissionStatus), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("totalPatientDischargedStatus", ReportUtils.map(totalPatientDischargedStatus(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("patientAdmissionStatus AND NOT totalPatientDischargedStatus");
        cd.setDescription("Total Patients in Ward by End of Reporting Period");
        return cd;
    }
}