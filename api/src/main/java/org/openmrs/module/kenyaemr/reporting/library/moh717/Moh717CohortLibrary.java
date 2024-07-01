/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.moh717;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Moh717CohortLibrary {

    public CohortDefinition getPatientsWithNewClinicalEncounterWithinReportingPeriod() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Patients with new encounters within date period");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery(
                "SELECT e.patient_id FROM kenyaemr_etl.etl_clinical_encounter e WHERE e.visit_type = 'New visit' and e.visit_date BETWEEN date(:startDate) and date(:endDate);"
        );
        return sql;
    }
    public CohortDefinition getPatientsWithReturnClinicalEncounterWithinReportingPeriod() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Patients with return encounters within date period");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery(
                "SELECT e.patient_id FROM kenyaemr_etl.etl_clinical_encounter e WHERE e.visit_type = 'Revisit' and e.visit_date BETWEEN date(:startDate) and date(:endDate);"
        );
        return sql;
    }

    public CohortDefinition newANCVisits() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Patients with new cwc visits");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery(
                "select v.patient_id from kenyaemr_etl.etl_mch_antenatal_visit v where date(v.visit_date) between date(:startDate) and date(:endDate) and v.anc_visit_number = 1;"
        );
        return sql;
    }

    public CohortDefinition ancRevisits() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Patients with ANC revisit");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery(
                "select v.patient_id from kenyaemr_etl.etl_mch_antenatal_visit v where date(v.visit_date) between date(:startDate) and date(:endDate) and v.anc_visit_number > 1;"
        );
        return sql;
    }

    public CohortDefinition newPNCVisits() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Patients with new PNC visits");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery(
                "select v.patient_id from kenyaemr_etl.etl_mch_postnatal_visit v where date(v.visit_date) between date(:startDate) and date(:endDate) and v.pnc_visit_no = 1;"
        );
        return sql;
    }

    public CohortDefinition pncReVisits() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Patients with PNC re-visits");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery(
                "select v.patient_id from kenyaemr_etl.etl_mch_postnatal_visit v where date(v.visit_date) between date(:startDate) and date(:endDate) and v.pnc_visit_no > 1;"
        );
        return sql;
    }


    public CohortDefinition newCWCVisits() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Patients with new cwc visits");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery(
                "select v.patient_id from kenyaemr_etl.etl_hei_follow_up_visit v where date(v.visit_date) between date(:startDate) and date(:endDate) and v.revisit_this_year = 1066;"
        );
        return sql;
    }

    public CohortDefinition cwcRevisits() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Patients with cwc re-visits");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery(
                "select v.patient_id from kenyaemr_etl.etl_hei_follow_up_visit v where date(v.visit_date) between date(:startDate) and date(:endDate) and v.revisit_this_year = 1065;"
        );
        return sql;
    }

    public CohortDefinition normalDeliveries() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Normal Deliveries");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from kenyaemr_etl.etl_mchs_delivery d where d.mode_of_delivery = 1170 and d.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition caesareanSections() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Caesarean Sections");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from kenyaemr_etl.etl_mchs_delivery d where d.mode_of_delivery = 1171 and d.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition breechDeliveries() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Breech Deliveries");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from kenyaemr_etl.etl_mchs_delivery d where d.mode_of_delivery = 1172 and d.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition assistedVaginalDelivery() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Assisted vaginal Deliveries");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from kenyaemr_etl.etl_mchs_delivery d where d.mode_of_delivery = 118159 and d.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition bornBeforeArrival() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Born before arrival");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from kenyaemr_etl.etl_mchs_delivery d where d.place_of_delivery = 1601 and d.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition maternalDeaths() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Maternal Deaths");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from kenyaemr_etl.etl_mchs_delivery d where d.condition_of_mother = 134612 and d.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }

    public CohortDefinition maternalDeathsAudited() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Maternal Deaths Audited");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from kenyaemr_etl.etl_mchs_delivery d where d.maternal_death_audited = 1065 and d.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition liveBirths() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Live births");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from kenyaemr_etl.etl_mchs_delivery d where d.baby_condition = 151849 and d.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }

    public CohortDefinition stillBirths() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Still births");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from kenyaemr_etl.etl_mchs_delivery d where d.baby_condition in (159916,135436) and d.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }

    public CohortDefinition lowBirthWeightBabies() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Low births weights");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from kenyaemr_etl.etl_mchs_delivery d where (d.birth_weight) * 1000 < 2500  and d.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }

    public CohortDefinition totalDischarges() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Total Discharges");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from kenyaemr_etl.etl_mchs_discharge d where d.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition laboratoryTests() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Number of Laboratory tests");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select encounter_id from kenyaemr_etl.etl_laboratory_extract x where x.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition totalAmountCollected() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Total Amount Collected");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select CAST(IFNULL(sum(ifnull(r.total_sales,0)),0) AS SIGNED) as total_amount_collected from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition totalAmountReceived() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Total Amount Received");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select CAST(ifnull(sum(ifnull(r.cash_receipts_cash_from_daily_services, 0) + ifnull(r.cash_receipt_nhif_receipt, 0) +\n" +
                "                           ifnull(r.cash_receipt_other_debtors_receipt, 0)), 0) AS SIGNED) as total_amount_received\n" +
                "                from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "                where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition clientsWaived() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Number of Clients Waived");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select CAST(IFNULL(count(ifnull(r.revenue_not_collected_patient_not_yet_paid_waivers, 0)), 0) AS SIGNED) as clients_waived\n" +
                "from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition totalAmountWaived() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Total Amount Waived");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select CAST(ifnull(sum(ifnull(r.revenue_not_collected_patient_not_yet_paid_waivers, 0)),0) AS SIGNED) as total_waived\n" +
                "from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition clientsExempted() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Number of Clients Exempted");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select CAST(ifnull(count(ifnull(r.revenue_not_collected_write_offs_exemptions, 0)), 0) AS SIGNED) as clients_exempted\n" +
                "from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition totalAmountExempted() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Total Amount Exempted");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select CAST(IFNULL(sum(ifnull(r.revenue_not_collected_write_offs_exemptions, 0)), 0) AS SIGNED) as total_exempted\n" +
                "from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sql;
    }
}