/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.ETLReports.finacials;

public class FacilityRevenueSummaries {
    public static final String getMonthlySummaryQueryM1() {
        return "select  tbl.transaction_date as transaction_date,\n" +
			"        SUM(tbl.total_sales) as total_sales,\n" +
			"        SUM(tbl.ipd_cash) as ipd_cash,\n" +
			"        SUM(tbl.maternity) as maternity,\n" +
			"        SUM(tbl.xray) as xray,\n" +
			"        SUM(tbl.lab) as lab,\n" +
			"        SUM(tbl.theatre) as theatre,\n" +
			"        SUM(tbl.mortuary) as mortuary,\n" +
			"        SUM(tbl.op_treatment) as op_treatment,\n" +
			"        SUM(tbl.pharmacy) as pharmacy,\n" +
			"        SUM(tbl.medical_exam) as medical_exam,\n" +
			"        SUM(tbl.medical_reports_including_P3) as medical_reports_including_P3,\n" +
			"        SUM(tbl.dental) as dental,\n" +
			"        SUM(tbl.physio_therapy) as physio_therapy,\n" +
			"        SUM(tbl.occupational_therapy) as occupational_therapy,\n" +
			"        SUM(tbl.medical_records_cards_and_files) as medical_records_cards_and_files,\n" +
			"        SUM(tbl.booking_fees) as booking_fees,\n" +
			"        SUM(tbl.rental_services) as rental_services,\n" +
			"        SUM(tbl.ambulance) as ambulance,\n" +
			"        SUM(tbl.public_health_services) as public_health_services,\n" +
			"        SUM(tbl.ent_and_other_clinics) as ent_and_other_clinics,\n" +
			"        SUM(tbl.other) as other,\n" +
			"        SUM(tbl.cash_receipts_cash_from_daily_services) as cash_receipts_cash_from_daily_services,\n" +
			"        SUM(tbl.cash_receipt_nhif_receipt) as cash_receipt_nhif_receipt,\n" +
			"        SUM(tbl.cash_receipt_other_debtors_receipt) as cash_receipt_other_debtors_receipt,\n" +
			"        SUM(tbl.revenue_not_collected_patient_not_yet_paid_nhif_patients) as revenue_not_collected_patient_not_yet_paid_nhif_patients,\n" +
			"        SUM(tbl.revenue_not_collected_patient_not_yet_paid_other_debtors) as revenue_not_collected_patient_not_yet_paid_other_debtors,\n" +
			"        SUM(tbl.revenue_not_collected_patient_not_yet_paid_waivers) as revenue_not_collected_patient_not_yet_paid_waivers,\n" +
			"        SUM(tbl.revenue_not_collected_write_offs_exemptions) as revenue_not_collected_write_offs_exemptions,\n" +
			"        SUM(tbl.revenue_not_collected_write_offs_absconders) as revenue_not_collected_write_offs_absconders\n" +
			"from (\n" +
			"         select  transaction_date,\n" +
			"                 total_sales,\n" +
			"                 ipd_cash,\n" +
			"                 maternity,\n" +
			"                 xray,\n" +
			"                 lab,\n" +
			"                 theatre,\n" +
			"                 mortuary,\n" +
			"                 op_treatment,\n" +
			"                 pharmacy,\n" +
			"                 medical_exam,\n" +
			"                 medical_reports_including_P3,\n" +
			"                 dental,\n" +
			"                 physio_therapy,\n" +
			"                 occupational_therapy,\n" +
			"                 medical_records_cards_and_files,\n" +
			"                 booking_fees,\n" +
			"                 rental_services,\n" +
			"                 ambulance,\n" +
			"                 public_health_services,\n" +
			"                 ent_and_other_clinics,\n" +
			"                 other,\n" +
			"                 cash_receipts_cash_from_daily_services,\n" +
			"                 cash_receipt_nhif_receipt,\n" +
			"                 cash_receipt_other_debtors_receipt,\n" +
			"                 revenue_not_collected_patient_not_yet_paid_nhif_patients,\n" +
			"                 revenue_not_collected_patient_not_yet_paid_other_debtors,\n" +
			"                 revenue_not_collected_patient_not_yet_paid_waivers,\n" +
			"                 revenue_not_collected_write_offs_exemptions,\n" +
			"                 revenue_not_collected_write_offs_absconders\n" +
			"             FROM kenyaemr_etl.etl_daily_revenue_summary  dr WHERE dr.transaction_date BETWEEN :startDate AND :endDate\n" +
			"                ) tbl group by transaction_date WITH ROLLUP ;";
    }

    public static final String getMonthlySummaryQueryM2() {
        return "SELECT transaction_date,\n" +
			"          total_sales,\n" +
			"          ipd_cash,\n" +
			"          maternity,\n" +
			"          xray,\n" +
			"          lab,\n" +
			"          theatre,\n" +
			"          mortuary,\n" +
			"          op_treatment,\n" +
			"          pharmacy,\n" +
			"          medical_exam,\n" +
			"          medical_reports_including_P3,\n" +
			"          dental,\n" +
			"          physio_therapy,\n" +
			"          occupational_therapy,\n" +
			"          medical_records_cards_and_files,\n" +
			"          booking_fees,\n" +
			"          rental_services,\n" +
			"          ambulance,\n" +
			"          public_health_services,\n" +
			"          ent_and_other_clinics,\n" +
			"          other,\n" +
			"          cash_receipts_cash_from_daily_services,\n" +
			"          cash_receipt_nhif_receipt,\n" +
			"          cash_receipt_other_debtors_receipt,\n" +
			"          revenue_not_collected_patient_not_yet_paid_nhif_patients,\n" +
			"          revenue_not_collected_patient_not_yet_paid_other_debtors,\n" +
			"          revenue_not_collected_patient_not_yet_paid_waivers,\n" +
			"          revenue_not_collected_write_offs_exemptions,\n" +
			"          revenue_not_collected_write_offs_absconders\n" +
			"    FROM kenyaemr_etl.etl_daily_revenue_summary  dr WHERE dr.transaction_date BETWEEN :startDate AND :endDate";
    }
}
