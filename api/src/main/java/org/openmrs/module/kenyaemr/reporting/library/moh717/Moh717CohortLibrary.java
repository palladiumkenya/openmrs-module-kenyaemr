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
    public CohortDefinition fpVisit(Integer visitType) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Patients with Family planning visits");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select v.patient_id from kenyaemr_etl.etl_family_planning v where date(v.visit_date) between date(:startDate) and date(:endDate)\n" +
                "and v.type_of_visit_for_method = "+visitType+";");
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
    public CohortDefinition neonatalDeaths() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("neonatalDeaths");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id\n" +
                "from kenyaemr_etl.etl_patient_demographics d\n" +
                "where d.death_date between date(:startDate) and date(:endDate)\n" +
                "  and TIMESTAMPDIFF(DAY, d.DOB, d.death_date) <= 28;");
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

    public CohortDefinition xrayAndImaging(String xrayAndImaging) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Number of imagings and xrays");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select o.encounter_id from orders o where o.order_type_id = 4 and o.concept_id in ("+xrayAndImaging+") and o.voided = 0 and date(o.date_activated) between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition specialClinic(String clinicFormUUID, int visitType) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Special Clinic");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select s.patient_id from kenyaemr_etl.etl_special_clinics s where s.special_clinic_form_uuid = '"+clinicFormUUID+"' and s.visit_type = "+visitType+" and s.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition otherSpecialClinics(String clinicsFormUUIDS, int visitType) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Other Special Clinics");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select s.patient_id from kenyaemr_etl.etl_special_clinics s where s.special_clinic_form_uuid in ("+clinicsFormUUIDS+") and s.visit_type = "+visitType+" and s.visit_date between date(:startDate) and date(:endDate);");
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

    public CohortDefinition newOnTBClinic() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("New on TB Clinic");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select e.patient_id from kenyaemr_etl.etl_tb_enrollment e where e.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }

    public CohortDefinition dentalVisits(int visitType) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Dental visits");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select c.patient_id from kenyaemr_etl.etl_special_clinics c where c.special_clinic_form_uuid = 'a3c01460-c346-4f3d-a627-5c7de9494ba0' and c.visit_type = "+visitType+" and date(c.visit_date) between date(:startDate) and date(:endDate);");
        return sql;
    }

    public CohortDefinition dentalFillings(String fillingsList, int visitType) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Dental Fillings");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from orders d inner join kenyaemr_etl.etl_special_clinics c on d.patient_id = c.patient_id and date(c.visit_date) = date(d.date_created)\n" +
                "         where concept_id in ("+fillingsList+") and d.voided = 0 and d.order_type_id = 4 and d.fulfiller_status = 'COMPLETED' and date(c.visit_date) between date(:startDate) and date(:endDate) and c.special_clinic_form_uuid = 'a3c01460-c346-4f3d-a627-5c7de9494ba0' and c.visit_type = "+visitType+";");
        return sql;
    }
    public CohortDefinition dentalExtractions(String extractionsList,int visitType) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Dental extractions");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from orders d inner join kenyaemr_etl.etl_special_clinics c on d.patient_id = c.patient_id and date(c.visit_date) = date(d.date_created)\n" +
                "where concept_id in ("+extractionsList+") and d.voided = 0 and d.order_type_id = 4 and d.fulfiller_status = 'COMPLETED' and date(c.visit_date) between date(:startDate) and date(:endDate) and c.special_clinic_form_uuid = 'a3c01460-c346-4f3d-a627-5c7de9494ba0' and c.visit_type = "+visitType+";");
        return sql;
    }
    public CohortDefinition dentalAttendances(String fillingsList, String extractionsList, int visitType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("dentalVisits",ReportUtils.map(dentalVisits(visitType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("dentalExtractions",ReportUtils.map(dentalExtractions(extractionsList, visitType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("dentalFillings",ReportUtils.map(dentalFillings(fillingsList, visitType), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("dentalVisits AND NOT (dentalExtractions OR dentalFillings)");
        return cd;
    }

    public CohortDefinition dressings(String dressingsList) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Dressings");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from orders d where d.concept_id in ("+dressingsList+") and d.order_type_id = 4 and d.fulfiller_status = 'COMPLETED' and d.voided = 0 and date(d.date_activated) between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition removalOfStitches(String removalOfStitchesList) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Removal of Stitches");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from orders d where d.concept_id in ("+removalOfStitchesList+") and d.order_type_id = 4 and d.fulfiller_status = 'COMPLETED' and d.voided = 0 and date(d.date_activated) between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition injections(String injectionsList) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Injections");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from orders d where d.concept_id in ("+injectionsList+") and d.order_type_id = 4 and d.fulfiller_status = 'COMPLETED' and d.voided = 0 and date(d.date_activated) between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition stitching(String stitchingList) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("stitching");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select d.patient_id from orders d where d.concept_id in ("+stitchingList+") and d.order_type_id = 4 and d.fulfiller_status = 'COMPLETED' and d.voided = 0 and date(d.date_activated) between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition revisitTBClinic() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Revisiting TB Clinic");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select e.patient_id\n" +
                "from kenyaemr_etl.etl_tb_enrollment e\n" +
                "         inner join (select f.patient_id, min(f.visit_date) as first_visit, max(f.visit_date) as latest_visit\n" +
                "                     from kenyaemr_etl.etl_tb_follow_up_visit f\n" +
                "                     group by f.patient_id) f on e.patient_id = f.patient_id\n" +
                "where f.latest_visit between date(:startDate) and date(:endDate)\n" +
                "  and f.first_visit < date(:startDate)\n" +
                "  and e.visit_date < date(:startDate);");
        return sql;
    }
    public CohortDefinition newOnCCClinic() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("New on CCC Clinic");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select e.patient_id from kenyaemr_etl.etl_hiv_enrollment e where e.patient_type = 164144 and e.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition revisitCCClinic() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Revisiting CCC Clinic");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select e.patient_id\n" +
                "from kenyaemr_etl.etl_hiv_enrollment e\n" +
                "         inner join (select f.patient_id, min(f.visit_date) as first_visit, max(f.visit_date) as latest_visit\n" +
                "                     from kenyaemr_etl.etl_patient_hiv_followup f\n" +
                "                     group by f.patient_id) f on e.patient_id = f.patient_id\n" +
                "where f.latest_visit between date(:startDate) and date(:endDate)\n" +
                "  and f.first_visit < date(:startDate)\n" +
                "  and e.visit_date < date(:startDate);");
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
    public CohortDefinition specialClinics(String specialClinicFormUuid) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("specialClinics");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select s.patient_id from kenyaemr_etl.etl_special_clinics s where s.special_clinic_form_uuid = '"+specialClinicFormUuid+"' and s.visit_date between date(:startDate) and date(:endDate)");
        return sql;
    }
    public CohortDefinition orthopaedicTraumaServices(String orthopedicFormUuid, String traumaServicesList, int visitType) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("orthopaedicTraumaServices");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select o.patient_id from openmrs.orders o\n" +
                "         inner join kenyaemr_etl.etl_special_clinics s\n" +
                "                    on o.patient_id = s.patient_id and o.order_type_id = 4 and date(o.date_activated) = s.visit_date\n" +
                "where s.special_clinic_form_uuid = '"+orthopedicFormUuid+"'\n" +
                "  and s.visit_type = "+visitType+"\n" +
                "  and o.concept_id in ("+traumaServicesList+") and o.voided = 0 and s.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }
    public CohortDefinition orthopaedicRemovalServices(String orthopedicFormUuid,String removalServicesList) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("orthopaedicRemovalServices");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select o.patient_id from openmrs.orders o\n" +
                "                         inner join kenyaemr_etl.etl_special_clinics s\n" +
                "                                    on o.patient_id = s.patient_id and o.order_type_id = 4 and date(o.date_activated) = s.visit_date\n" +
                "                where s.special_clinic_form_uuid = '"+orthopedicFormUuid+"'\n" +
                "                  and o.concept_id in ("+removalServicesList+") and o.voided = 0 and s.visit_date between date(:startDate) and date(:endDate);");
        return sql;
    }

    public CohortDefinition curedDischarges(int cured,String wardType) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("curedDischarges");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select b.patient_id\n" +
                "from location_tag_map ltm\n" +
                "         inner join openmrs.location_tag t on ltm.location_tag_id = t.location_tag_id\n" +
                "         inner join (select p.patient_id, p.bed_id, p.date_started, p.date_stopped, m.location_id, d.discharge_status\n" +
                "                     from bed_patient_assignment_map p\n" +
                "                              inner join bed_location_map m on p.bed_id = m.bed_id\n" +
                "                              inner join kenyaemr_etl.etl_inpatient_discharge d on p.patient_id = d.patient_id and date(p.date_started) = d.visit_date) b\n" +
                "                    on ltm.location_id = b.location_id\n" +
                "where b.discharge_status = "+cured+" and t.uuid = '"+wardType+"';");
        return sql;
    }
    public CohortDefinition leftAgainstMedicalAdviseDischarges(int leftAgainstMedicalAdvise,String wardType) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("leftAgainstMedicalAdviseDischarges");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select b.patient_id\n" +
                "from location_tag_map ltm\n" +
                "         inner join openmrs.location_tag t on ltm.location_tag_id = t.location_tag_id\n" +
                "         inner join (select p.patient_id, p.bed_id, p.date_started, p.date_stopped, m.location_id, d.discharge_status\n" +
                "                     from bed_patient_assignment_map p\n" +
                "                              inner join bed_location_map m on p.bed_id = m.bed_id\n" +
                "                              inner join kenyaemr_etl.etl_inpatient_discharge d on p.patient_id = d.patient_id and date(p.date_started) = d.visit_date) b\n" +
                "                    on ltm.location_id = b.location_id\n" +
                "where b.discharge_status = "+leftAgainstMedicalAdvise+" and t.uuid = '"+wardType+"';");
        return sql;
    }
    public CohortDefinition inpatientDischarges(int cured, int leftAgainstMedicalAdvise,String wardType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("leftAgainstMedicalAdviseDischarges", ReportUtils.map(leftAgainstMedicalAdviseDischarges(leftAgainstMedicalAdvise, wardType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("curedDischarges", ReportUtils.map(curedDischarges(cured, wardType), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("leftAgainstMedicalAdviseDischarges OR curedDischarges");
        return cd;
    }
    public CohortDefinition curedDischargesOtherWards(int cured,String wardTypeList) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("leftAgainstMedicalAdviseDischargesOtherWards");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select b.patient_id\n" +
                "from location_tag_map ltm\n" +
                "         inner join openmrs.location_tag t on ltm.location_tag_id = t.location_tag_id\n" +
                "         inner join (select p.patient_id, p.bed_id, p.date_started, p.date_stopped, m.location_id, d.discharge_status\n" +
                "                     from bed_patient_assignment_map p\n" +
                "                              inner join bed_location_map m on p.bed_id = m.bed_id\n" +
                "                              inner join kenyaemr_etl.etl_inpatient_discharge d on p.patient_id = d.patient_id and date(p.date_started) = d.visit_date) b\n" +
                "                    on ltm.location_id = b.location_id\n" +
                "where b.discharge_status = "+cured+" and t.uuid in ("+wardTypeList+");");
        return sql;
    }
    public CohortDefinition leftAgainstMedicalAdviseDischargesOtherWards(int leftAgainstMedicalAdvise,String wardTypeList) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("leftAgainstMedicalAdviseDischargesOtherWards");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select b.patient_id\n" +
                "from location_tag_map ltm\n" +
                "         inner join openmrs.location_tag t on ltm.location_tag_id = t.location_tag_id\n" +
                "         inner join (select p.patient_id, p.bed_id, p.date_started, p.date_stopped, m.location_id, d.discharge_status\n" +
                "                     from bed_patient_assignment_map p\n" +
                "                              inner join bed_location_map m on p.bed_id = m.bed_id\n" +
                "                              inner join kenyaemr_etl.etl_inpatient_discharge d on p.patient_id = d.patient_id and date(p.date_started) = d.visit_date) b\n" +
                "                    on ltm.location_id = b.location_id\n" +
                "where b.discharge_status = "+leftAgainstMedicalAdvise+" and t.uuid in ("+wardTypeList+");");
        return sql;
    }
    public CohortDefinition otherInpatientDischarges(int cured, int leftAgainstMedicalAdvise,String wardTypeList) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("leftAgainstMedicalAdviseDischargesOtherWards", ReportUtils.map(leftAgainstMedicalAdviseDischargesOtherWards(leftAgainstMedicalAdvise, wardTypeList), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("curedDischargesOtherWards", ReportUtils.map(curedDischargesOtherWards(cured, wardTypeList), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("curedDischargesOtherWards OR leftAgainstMedicalAdviseDischargesOtherWards");
        return cd;
    }
    public CohortDefinition inpatientExitStatus(int dischargeReason, String wardType) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("inpatientExitStatus");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select b.patient_id\n" +
                "from location_tag_map ltm\n" +
                "         inner join openmrs.location_tag t on ltm.location_tag_id = t.location_tag_id\n" +
                "         inner join (select p.patient_id, p.bed_id, p.date_started, p.date_stopped, m.location_id, d.discharge_status\n" +
                "                     from bed_patient_assignment_map p\n" +
                "                              inner join bed_location_map m on p.bed_id = m.bed_id\n" +
                "                              inner join kenyaemr_etl.etl_inpatient_discharge d on p.patient_id = d.patient_id and date(p.date_started) = d.visit_date) b\n" +
                "                    on ltm.location_id = b.location_id\n" +
                "where b.discharge_status = "+dischargeReason+" and t.uuid = '"+wardType+"';");
        return sql;
    }
    public CohortDefinition otherInpatientExitStatus(int dischargeReason, String wardTypeList) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("otherInpatientExitStatus");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select b.patient_id\n" +
                "from location_tag_map ltm\n" +
                "         inner join openmrs.location_tag t on ltm.location_tag_id = t.location_tag_id\n" +
                "         inner join (select p.patient_id, p.bed_id, p.date_started, p.date_stopped, m.location_id, d.discharge_status\n" +
                "                     from bed_patient_assignment_map p\n" +
                "                              inner join bed_location_map m on p.bed_id = m.bed_id\n" +
                "                              inner join kenyaemr_etl.etl_inpatient_discharge d on p.patient_id = d.patient_id and date(p.date_started) = d.visit_date) b\n" +
                "                    on ltm.location_id = b.location_id\n" +
                "where b.discharge_status = "+dischargeReason+" and t.uuid in ("+wardTypeList+");");
        return sql;
    }
}