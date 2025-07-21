/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr;

import org.junit.platform.commons.function.Try;
import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.util.PrivilegeConstants;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacilityDashboardUtil {

	/**
	 * This query counts the number of HIV positive clients who were not linked to
	 * care (numerator).
	 * 
	 * @param startDate the start date of the period to consider
	 * @param endDate   the end date of the period to consider
	 * @return the count of HIV positive clients not linked to care
	 */
	public static Long getHivPositiveNotLinked(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String hivPositiveNotLinkedQuery = "SELECT COUNT(DISTINCT(a.patient_id)) number_hiv_positive\n" +
				"FROM ((SELECT av.patient_id\n" +
				"       FROM kenyaemr_etl.etl_mch_antenatal_visit av\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics a on av.patient_id = a.patient_id\n" +
				"       WHERE av.visit_date BETWEEN DATE_SUB(date(date('" + endDate + "')), INTERVAL "
				+ days + " DAY) AND date(date('" + endDate + "'))\n" +
				"         AND av.final_test_result = 'Positive')\n" +
				"      UNION\n" +
				"      (SELECT d.patient_id\n" +
				"       FROM kenyaemr_etl.etl_mchs_delivery d\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics a on a.patient_id = d.patient_id\n" +
				"       WHERE d.visit_date BETWEEN DATE_SUB(date(date('" + endDate + "')), INTERVAL "
				+ days + " DAY) AND date(date('" + endDate + "'))\n" +
				"         AND d.final_test_result = 'Positive')\n" +
				"      UNION\n" +
				"      (SELECT p.patient_id\n" +
				"       FROM kenyaemr_etl.etl_mch_postnatal_visit p\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics d on p.patient_id = d.patient_id\n" +
				"       WHERE p.visit_date BETWEEN DATE_SUB(date(date('" + endDate + "')), INTERVAL "
				+ days + " DAY) AND date(date('" + endDate + "'))\n" +
				"         AND p.final_test_result = 'Positive')\n" +
				"      UNION\n" +
				"      (SELECT t.patient_id\n" +
				"       FROM kenyaemr_etl.etl_hts_test t\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = t.patient_id\n" +
				"           AND t.final_test_result = 'Positive'\n" +
				"           AND t.voided = 0\n" +
				"           AND t.visit_date BETWEEN DATE_SUB(date(date('" + endDate + "')), INTERVAL "
				+ days + " DAY) AND date(date('" + endDate + "')))) a\n" +
				"         LEFT JOIN\n" +
				"     (SELECT l.patient_id, l.ccc_number,l.art_start_date\n" +
				"      FROM kenyaemr_etl.etl_hts_referral_and_linkage l\n" +
				"      WHERE date(l.visit_date) BETWEEN DATE_SUB(date(date('" + endDate + "')), INTERVAL "
				+ days + " DAY) AND date(date('" + endDate + "'))\n" +
				"      GROUP BY l.patient_id) l ON a.patient_id = l.patient_id\n" +
				"         LEFT JOIN (SELECT e.patient_id\n" +
				"                    FROM kenyaemr_etl.etl_drug_event e\n" +
				"                    WHERE e.program = 'HIV' and COALESCE(date(e.date_started),date(e.visit_date)) <= date('" + endDate + "')) e\n" +
				"                   ON e.patient_id = a.patient_id\n" +
				"where (l.patient_id is null or l.art_start_date is null)\n" +
				"  and e.patient_id is null;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(hivPositiveNotLinkedQuery, true).get(0).get(0);
		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	/**
	 * This query counts the number of HIV positive clients (denominator).
	 * 
	 * @param startDate the start date of the period to consider
	 * @param endDate   the end date of the period to consider
	 * @return the count of HIV positive clients
	 */
	public static Long getPatientsTestedHivPositive(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String hivTestedPositiveQuery = "SELECT COUNT(DISTINCT(patient_id)) number_hiv_positive\n" +
				"FROM ((SELECT av.patient_id, av.encounter_id\n" +
				"       FROM kenyaemr_etl.etl_mch_antenatal_visit av\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics a on av.patient_id = a.patient_id\n" +
				"       WHERE av.visit_date BETWEEN DATE_SUB(date('" + endDate + "'), INTERVAL "
				+ days + " DAY) AND date('" + endDate + "')\n" +
				"         AND av.final_test_result = 'Positive')\n" +
				"      UNION\n" +
				"      (SELECT d.patient_id, d.encounter_id\n" +
				"       FROM kenyaemr_etl.etl_mchs_delivery d\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics a on a.patient_id = d.patient_id\n" +
				"       WHERE d.visit_date BETWEEN DATE_SUB(date('" + endDate + "'), INTERVAL "
				+ days + " DAY) AND date('" + endDate + "')\n" +
				"         AND d.final_test_result = 'Positive')\n" +
				"      UNION\n" +
				"      (SELECT p.patient_id, p.encounter_id\n" +
				"       FROM kenyaemr_etl.etl_mch_postnatal_visit p\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics d on p.patient_id = d.patient_id\n" +
				"       WHERE p.visit_date BETWEEN DATE_SUB(date('" + endDate + "'), INTERVAL "
				+ days + " DAY) AND date('" + endDate + "')\n" +
				"         AND p.final_test_result = 'Positive')\n" +
				"      UNION\n" +
				"      (SELECT t.patient_id, t.encounter_id\n" +
				"       FROM kenyaemr_etl.etl_hts_test t\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = t.patient_id \n" +
				"         AND t.final_test_result = 'Positive'\n" +
				"         AND t.voided = 0\n" +
				"         AND t.visit_date BETWEEN DATE_SUB(date('" + endDate + "'), INTERVAL "
				+ days + " DAY) AND date('" + endDate + "'))) a;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(hivTestedPositiveQuery, true).get(0).get(0);
		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	/**
	 * This query counts the number of pregnant or postpartum women (denominator).
	 * 
	 * @param startDate the start date for the query
	 * @param endDate   the end date for the query
	 * @return the count of pregnant or postpartum women
	 */
	public static Long getPregnantOrPostpartumClients(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String pregnantOrPostpartumQuery = "SELECT COUNT(s.patient_id) high_risk_preg_postpartum\n" +
				"FROM kenyaemr_etl.etl_hts_eligibility_screening s\n" +
				"         INNER JOIN (SELECT t.patient_id,\n" +
				"                            max(t.visit_date)                                           AS hts_date,\n"
				+
				"                            mid(max(concat(date(visit_date), t.final_test_result)), 11) AS hiv_test_results,\n"
				+
				"                            mid(max(concat(date(visit_date), t.hts_entry_point)), 11)   AS entry_point,\n"
				+
				"                            t.visit_date\n" +
				"                     FROM kenyaemr_etl.etl_hts_test t\n" +
				"                     GROUP BY t.patient_id\n" +
				"                     HAVING hts_date BETWEEN DATE_SUB('" + endDate
				+ "', INTERVAL " + days + " DAY) AND date('" + endDate + "')\n" +
				"                        AND hiv_test_results = 'Negative'\n" +
				"                        AND entry_point in (160538, 160456, 1623)) t\n" +
				"                    ON s.patient_id = t.patient_id\n" +
				"WHERE s.hts_risk_category IN ('High', 'Very high')\n" +
				"  AND s.visit_date BETWEEN DATE_SUB(date('" + endDate + "'), INTERVAL " + days
				+ " DAY) AND date('" + endDate + "');";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(pregnantOrPostpartumQuery, true).get(0).get(0);
		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	/**
	 * This query counts the number of pregnant or postpartum women not on PrEP
	 * (numerator).
	 *
	 * @param startDate the start date for the query
	 * @param endDate   the end date for the query
	 * @return the count of pregnant or postpartum women not on PrEP
	 */
	public static Long getPregnantPostpartumNotInPrep(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String pregnantPostPartumNotPrepLinkedQuery = "SELECT COUNT(DISTINCT(a.patient_id)) high_risk_not_on_PrEP\n" +
				"FROM (SELECT s.patient_id\n" +
				"      FROM kenyaemr_etl.etl_hts_eligibility_screening s\n" +
				"               INNER JOIN (SELECT t.patient_id,\n" +
				"                                  max(t.visit_date)                                           AS hts_date,\n"
				+
				"                                  mid(max(concat(date(visit_date), t.final_test_result)), 11) AS hiv_test_results,\n"
				+
				"                                  mid(max(concat(date(visit_date), t.hts_entry_point)), 11)   AS entry_point,\n"
				+
				"                                  t.visit_date\n" +
				"                           from kenyaemr_etl.etl_hts_test t\n" +
				"                           GROUP BY t.patient_id\n" +
				"                           HAVING hts_date BETWEEN DATE_SUB('" + endDate
				+ "', INTERVAL " + days + " DAY) AND date('" + endDate + "')\n" +
				"                              AND hiv_test_results = 'Negative'\n" +
				"                              AND entry_point in (160538, 160456, 1623)) t\n" +
				"                          ON s.patient_id = t.patient_id -- AND s.visit_date <= t.visit_date\n" +
				"    where s.hts_risk_category IN ('High', 'Very high') and s.currently_on_prep in ('NO','Declined to answer')\n"
				+
				"         AND s.visit_date BETWEEN DATE_SUB(date('" + endDate + "'), INTERVAL "
				+ days + " DAY) AND date('" + endDate + "')) a\n" +
				"         left join (select e.patient_id,\n" +
				"                           max(e.visit_date)                                        as latest_enrollment_date,\n"
				+
				"                           f.latest_fup_date,\n" +
				"                           greatest(ifnull(f.latest_fup_app_date, '0000-00-00'),\n" +
				"                                    ifnull(latest_refill_app_date, '0000-00-00'))   as latest_appointment_date,\n"
				+
				"                           greatest(ifnull(latest_fup_date, '0000-00-00'),\n" +
				"                                    ifnull(latest_refill_visit_date, '0000-00-00')) as latest_visit_date,\n"
				+
				"                           r.latest_refill_visit_date,\n" +
				"                           f.latest_fup_app_date,\n" +
				"                           r.latest_refill_app_date,\n" +
				"                           d.latest_disc_date,\n" +
				"                           d.disc_patient\n" +
				"                    from kenyaemr_etl.etl_prep_enrolment e\n" +
				"                             left join\n" +
				"                         (select f.patient_id,\n" +
				"                                 max(f.visit_date)                                      as latest_fup_date,\n"
				+
				"                                 mid(max(concat(f.visit_date, f.appointment_date)), 11) as latest_fup_app_date\n"
				+
				"                          from kenyaemr_etl.etl_prep_followup f\n" +
				"                          where f.visit_date <= date('" + endDate + "')\n" +
				"                          group by f.patient_id) f on e.patient_id = f.patient_id\n" +
				"                             left join (select r.patient_id,\n" +
				"                                               max(r.visit_date)                                      as latest_refill_visit_date,\n"
				+
				"                                               mid(max(concat(r.visit_date, r.next_appointment)), 11) as latest_refill_app_date\n"
				+
				"                                        from kenyaemr_etl.etl_prep_monthly_refill r\n" +
				"                                        where r.visit_date <= date('" + endDate
				+ "')\n" +
				"                                        group by r.patient_id) r on e.patient_id = r.patient_id\n" +
				"                             left join (select patient_id                                               as disc_patient,\n"
				+
				"                                               max(d.visit_date)                                        as latest_disc_date,\n"
				+
				"                                               mid(max(concat(d.visit_date, d.discontinue_reason)), 11) as latest_disc_reason\n"
				+
				"                                        from kenyaemr_etl.etl_prep_discontinuation d\n" +
				"                                        where d.visit_date <= date('" + endDate
				+ "')\n" +
				"                                        group by patient_id\n" +
				"                                        having latest_disc_date <= date('" + endDate
				+ "')) d\n" +
				"                                       on e.patient_id = d.disc_patient\n" +
				"                    group by e.patient_id\n" +
				"                    having timestampdiff(DAY, date(latest_appointment_date), date('"
				+ endDate + "')) <= " + days + " \n" +
				"                       and date(latest_appointment_date) >= date(latest_visit_date)\n" +
				"                       and ((latest_enrollment_date >= d.latest_disc_date\n" +
				"                        and latest_appointment_date > d.latest_disc_date) or d.disc_patient is null)) b\n"
				+
				"                   on a.patient_id = b.patient_id\n" +
				"where b.patient_id is null;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(pregnantPostPartumNotPrepLinkedQuery, true)
					.get(0).get(0);
		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	/**
	 * Get number of eligible patients for viral load testing (denominator)
	 * 
	 * @param startDate the start date in "dd/MM/yyyy" format
	 * @param endDate   the end date in "dd/MM/yyyy" format
	 * @return the number of eligible patients for viral load testing
	 */
	public static Long getEligibleForVl(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String eligibleForVlQuery = "select COUNT(DISTINCT(b.patient_id)) as eligible_for_vl\n" +
				"from (select fup.visit_date,\n" +
				"             fup.patient_id,\n" +
				"             max(e.visit_date)                                                                as enroll_date,\n" +
				"             greatest(max(fup.visit_date), ifnull(max(d.visit_date), '0000-00-00'))           as latest_vis_date,\n" +
				"             greatest(mid(max(concat(fup.visit_date, fup.next_appointment_date)), 11),\n" +
				"                      ifnull(max(d.visit_date), '0000-00-00'))                                as latest_tca,\n" +
				"             d.patient_id                                                                     as disc_patient,\n" +
				"             d.effective_disc_date                                                            as effective_disc_date,\n" +
				"             max(d.visit_date)                                                                as date_discontinued,\n" +
				"             de.patient_id                                                   as started_on_drugs\n" +
				"      from kenyaemr_etl.etl_patient_hiv_followup fup\n" +
				"               join kenyaemr_etl.etl_patient_demographics p on p.patient_id = fup.patient_id\n" +
				"               join kenyaemr_etl.etl_hiv_enrollment e on fup.patient_id=e.patient_id\n" +
				"               left join kenyaemr_etl.etl_drug_event de\n" +
				"                         on e.patient_id = de.patient_id and de.program = 'HIV' and date(de.date_started) <= date('" + endDate + "')\n" +
				"               left outer JOIN\n" +
				"           (select patient_id,\n" +
				"                   coalesce(date(effective_discontinuation_date), visit_date) visit_date,\n" +
				"                   max(date(effective_discontinuation_date)) as               effective_disc_date\n" +
				"            from kenyaemr_etl.etl_patient_program_discontinuation\n" +
				"            where date(visit_date) <= date('" + endDate + "')\n" +
				"              and program_name = 'HIV'\n" +
				"            group by patient_id) d on d.patient_id = fup.patient_id\n" +
				"      where fup.visit_date BETWEEN date('" + startDate + "') AND date('" + endDate + "')\n" +
				"      group by patient_id\n" +
				"      having (started_on_drugs is not null and started_on_drugs <> '')\n" +
				"         and (\n" +
				"          (\n" +
				"              (timestampdiff(DAY, date(latest_tca), date('" + endDate + "')) <= 30 and\n" +
				"               ((date(d.effective_disc_date) > date('" + endDate + "') or date(enroll_date) > date(d.effective_disc_date)) or\n" +
				"                d.effective_disc_date is null))\n" +
				"                  and\n" +
				"              (date(latest_vis_date) >= date(date_discontinued) or date(latest_tca) >= date(date_discontinued) or\n" +
				"               disc_patient is null)\n" +
				"              )\n" +
				"          )) e\n" +
				"         INNER JOIN (select v.patient_id\n" +
				"                     from kenyaemr_etl.etl_viral_load_validity_tracker v\n" +
				"                              inner join kenyaemr_etl.etl_patient_demographics d on v.patient_id = d.patient_id\n" +
				"                     where ((TIMESTAMPDIFF(MONTH, v.date_started_art, date('" + endDate + "')) >= 3 and\n" +
				"                             v.base_viral_load_test_result is null)                              -- First VL new on ART+\n" +
				"                         OR ((v.pregnancy_status = 1065 or v.breastfeeding_status = 1065) and\n" +
				"                             TIMESTAMPDIFF(MONTH, v.date_started_art, date('" + endDate + "')) >= 3 and\n" +
				"                             (v.vl_result is not null and\n" +
				"                              v.date_test_requested < date('" + endDate + "')) and (v.order_reason not in (159882, 1434, 2001237, 163718)))              -- immediate for PG & BF+\n" +
				"                         OR (v.lab_test = 856 AND v.vl_result >= 200 AND\n" +
				"                             TIMESTAMPDIFF(MONTH, v.date_test_requested, date('" + endDate + "')) >= 3)  -- Unsuppressed VL+\n" +
				"                         OR (((v.lab_test = 1305 AND v.vl_result = 1302) OR v.vl_result < 200) AND\n" +
				"                             TIMESTAMPDIFF(MONTH, v.date_test_requested, date('" + endDate + "')) >= 6 and\n" +
				"                             TIMESTAMPDIFF(YEAR, d.DOB, v.date_test_requested) BETWEEN 0 AND 24) -- 0-24 with last suppressed vl+\n" +
				"                         OR (((v.lab_test = 1305 AND v.vl_result = 1302) OR v.vl_result < 200) AND\n" +
				"                             TIMESTAMPDIFF(MONTH, v.date_test_requested, date('" + endDate + "')) >= 12 and\n" +
				"                             TIMESTAMPDIFF(YEAR, d.DOB, v.date_test_requested) > 24)             -- > 24 with last suppressed vl+\n" +
				"                         OR ((v.pregnancy_status = 1065 or v.breastfeeding_status = 1065) and\n" +
				"                             TIMESTAMPDIFF(MONTH, v.date_started_art, date('" + endDate + "')) >= 3\n" +
				"                             and (v.order_reason in (159882, 1434, 2001237, 163718) and\n" +
				"                                  TIMESTAMPDIFF(MONTH, v.date_test_requested, date('" + endDate + "')) >= 6) and\n" +
				"                             ((v.lab_test = 1305 AND v.vl_result = 1302) OR (v.vl_result < 200 ))) -- PG & BF after PG/BF baseline < 200\n" +
				"                         )) b on e.patient_id = b.patient_id;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(eligibleForVlQuery, true).get(0).get(0);
		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	/**
	 * This query counts the number of patients eligible for viral load testing
	 * who have not taken a sample (numerator).
	 *
	 * @param startDate the start date in "dd/MM/yyyy" format
	 * @param endDate   the end date in "dd/MM/yyyy" format
	 * @return the number of eligible patients for viral load testing who have not
	 *         taken a sample
	 */
	public static Long getEligibleForVlSampleNotTaken(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String eligibleForVlSampleNotTakenQuery = "select COUNT(DISTINCT(b.patient_id)) as eligible_for_vl_sample_not_taken\n" +
				"          from (select fup.visit_date,\n" +
				"                         fup.patient_id,\n" +
				"                         max(e.visit_date)                                                                as enroll_date,\n"
				+
				"                         greatest(max(fup.visit_date), ifnull(max(d.visit_date), '0000-00-00'))           as latest_vis_date,\n"
				+
				"                         greatest(mid(max(concat(fup.visit_date, fup.next_appointment_date)), 11),\n" +
				"                                  ifnull(max(d.visit_date), '0000-00-00'))                                as latest_tca,\n"
				+
				"                         d.patient_id                                                                     as disc_patient,\n"
				+
				"                         d.effective_disc_date                                                            as effective_disc_date,\n"
				+
				"                         max(d.visit_date)                                                                as date_discontinued,\n"
				+
				"                         de.patient_id                                                   as started_on_drugs\n"
				+
				"                  from kenyaemr_etl.etl_patient_hiv_followup fup\n" +
				"                           join kenyaemr_etl.etl_patient_demographics p on p.patient_id = fup.patient_id\n"
				+
				"                           join kenyaemr_etl.etl_hiv_enrollment e on fup.patient_id=e.patient_id\n" +
				"                           left join kenyaemr_etl.etl_drug_event de\n" +
				"                                     on e.patient_id = de.patient_id and de.program = 'HIV' and date(de.date_started) <= '"
				+ endDate + "'\n" +
				"                           left outer JOIN\n" +
				"                       (select patient_id,\n" +
				"                               coalesce(date(effective_discontinuation_date), visit_date) visit_date,\n"
				+
				"                               max(date(effective_discontinuation_date)) as               effective_disc_date\n"
				+
				"                        from kenyaemr_etl.etl_patient_program_discontinuation\n" +
				"                        where date(visit_date) <= date('" + endDate + "')\n" +
				"                          and program_name = 'HIV'\n" +
				"                        group by patient_id) d on d.patient_id = fup.patient_id\n" +
				"                  where fup.visit_date BETWEEN date('" + startDate
				+ "') AND date('" + endDate + "')\n" +
				"                  group by patient_id\n" +
				"                  having (started_on_drugs is not null and started_on_drugs <> '')\n" +
				"                     and (\n" +
				"                      (\n" +
				"                          (timestampdiff(DAY, date(latest_tca), '" + endDate
				+ "') <= 30 and\n" +
				"                           ((date(d.effective_disc_date) > '" + endDate
				+ "' or date(enroll_date) > date(d.effective_disc_date)) or\n" +
				"                            d.effective_disc_date is null))\n" +
				"                              and\n" +
				"                          (date(latest_vis_date) >= date(date_discontinued) or date(latest_tca) >= date(date_discontinued) or\n"
				+
				"                           disc_patient is null)\n" +
				"                          )\n" +
				"                      )) e\n" +
				"                   INNER JOIN (select v.patient_id\n" +
				"                     from kenyaemr_etl.etl_viral_load_validity_tracker v\n" +
				"                              inner join kenyaemr_etl.etl_patient_demographics d on v.patient_id = d.patient_id\n"+
				"                     where ((TIMESTAMPDIFF(MONTH, v.date_started_art, v.latest_hiv_followup_visit) >= 3 and\n" +
				"                            v.base_viral_load_test_result is null)                              -- First VL new on ART\n"+
				"                        OR ((v.pregnancy_status = 1065 or v.breastfeeding_status = 1065) and\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_started_art, v.latest_hiv_followup_visit) >= 3 and\n" +
				"                            (v.vl_result is not null and\n" +
				"                             v.date_test_requested < v.latest_hiv_followup_visit) and (v.order_reason not in (159882, 1434, 2001237, 163718)))              -- immediate for PG & BF\n"+
				"                        OR (v.lab_test = 856 AND v.vl_result >= 200 AND\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_test_requested, v.latest_hiv_followup_visit) >= 3)  -- Unsuppressed VL\n"+
				"                        OR (((v.lab_test = 1305 AND v.vl_result = 1302) OR v.vl_result < 200) AND\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_test_requested, v.latest_hiv_followup_visit) >= 6 and\n" +
				"                            TIMESTAMPDIFF(YEAR, d.DOB, v.date_test_requested) BETWEEN 0 AND 24) -- 0-24 with last suppressed vl\n"+
				"                        OR (((v.lab_test = 1305 AND v.vl_result = 1302) OR v.vl_result < 200) AND\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_test_requested, v.latest_hiv_followup_visit) >= 12 and\n" +
				"                            TIMESTAMPDIFF(YEAR, d.DOB, v.date_test_requested) > 24)             -- > 24 with last suppressed vl\n"+
				"                        OR ((v.pregnancy_status = 1065 or v.breastfeeding_status = 1065) and\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_started_art, v.latest_hiv_followup_visit) >= 3\n" +
				"                         and (v.order_reason in (159882, 1434, 2001237, 163718) and\n" +
				"                              TIMESTAMPDIFF(MONTH, v.date_test_requested, v.latest_hiv_followup_visit) >= 6) and\n" +
				"                            ((v.lab_test = 1305 AND v.vl_result = 1302) OR (v.vl_result < 200 ))) -- PG & BF after PG/BF baseline < 200\n" +
				"                                 and (v.latest_hiv_followup_visit > IFNULL(v.date_test_requested,'0000-00-00')))) b on e.patient_id = b.patient_id;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(eligibleForVlSampleNotTakenQuery, true).get(0)
					.get(0);
		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	/**
	 * This query counts the number of virally unsuppressed clients
	 * (denominator).
	 *
	 * @param startDate the start date in "dd/MM/yyyy" format
	 * @param endDate   the end date in "dd/MM/yyyy" format
	 * @return the count of virally unsuppressed clients
	 */
	public static Long getVirallyUnsuppressed
			(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String getVirallyUnsuppressedQuery = "select COUNT(DISTINCT(a.patient_id)) as vl_unsuppressed\n" +
				"from (SELECT x.patient_id,x.date_test_result_received\n" +
				"      FROM kenyaemr_etl.etl_laboratory_extract x\n" +
				"      WHERE x.lab_test = 856\n" +
				"        AND test_result >= 200\n" +
				"        AND x.date_test_result_received BETWEEN DATE_SUB(DATE_SUB(date('" + endDate + "'), INTERVAL 14 DAY), INTERVAL DATEDIFF(date('" + endDate + "'),date('" + startDate + "')) DAY)\n" +
				"          AND  DATE_SUB(date('" + endDate + "'), INTERVAL 14 DAY)) a\n" +
				"         inner join\n" +
				"     (select t.patient_id\n" +
				"      from (select fup.visit_date,\n" +
				"                   fup.patient_id,\n" +
				"                   max(e.visit_date)                                                      as enroll_date,\n" +
				"                   greatest(max(fup.visit_date), ifnull(max(d.visit_date), '0000-00-00')) as latest_vis_date,\n" +
				"                   greatest(mid(max(concat(fup.visit_date, fup.next_appointment_date)), 11),\n" +
				"                            ifnull(max(d.visit_date), '0000-00-00'))                      as latest_tca,\n" +
				"                   d.patient_id                                                           as disc_patient,\n" +
				"                   d.effective_disc_date                                                  as effective_disc_date,\n" +
				"                   max(d.visit_date)                                                      as date_discontinued,\n" +
				"                   de.patient_id                                                          as started_on_drugs\n" +
				"            from kenyaemr_etl.etl_patient_hiv_followup fup\n" +
				"                     join kenyaemr_etl.etl_patient_demographics p on p.patient_id = fup.patient_id\n" +
				"                     join kenyaemr_etl.etl_hiv_enrollment e on fup.patient_id = e.patient_id\n" +
				"                     left join kenyaemr_etl.etl_drug_event de\n" +
				"                               on e.patient_id = de.patient_id and de.program = 'HIV' and\n" +
				"                                  date(de.date_started) <= date('" + endDate + "')\n" +
				"                     left outer JOIN\n" +
				"                 (select patient_id,\n" +
				"                         coalesce(date(effective_discontinuation_date), visit_date) visit_date,\n" +
				"                         max(date(effective_discontinuation_date)) as               effective_disc_date\n" +
				"                  from kenyaemr_etl.etl_patient_program_discontinuation\n" +
				"                  where date(visit_date) <= date('" + endDate + "')\n" +
				"                    and program_name = 'HIV'\n" +
				"                  group by patient_id) d on d.patient_id = fup.patient_id\n" +
				"            where fup.visit_date <= date('" + endDate + "')\n" +
				"            group by patient_id\n" +
				"            having (started_on_drugs is not null and started_on_drugs <> '')\n" +
				"               and (\n" +
				"                (\n" +
				"                    (timestampdiff(DAY, date(latest_tca), date('" + endDate + "')) <= 30 and\n" +
				"                     ((date(d.effective_disc_date) > date('" + endDate + "') or\n" +
				"                       date(enroll_date) > date(d.effective_disc_date)) or\n" +
				"                      d.effective_disc_date is null))\n" +
				"                        and\n" +
				"                    (date(latest_vis_date) >= date(date_discontinued) or date(latest_tca) >= date(date_discontinued) or\n" +
				"                     disc_patient is null)\n" +
				"                    )\n" +
				"                )) t) b on a.patient_id = b.patient_id;";
		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(getVirallyUnsuppressedQuery, true).get(0)
					.get(0);
		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}
	/**
	 * This query counts the number of virally unsuppressed clients
	 *
	 * (numerator).
	 *
	 * @param startDate the start date in "dd/MM/yyyy" format
	 * @param endDate   the end date in "dd/MM/yyyy" format
	 * @return the count of virally unsuppressed clients
	 */
	public static Long getVirallyUnsuppressedWithoutEAC(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String getVirallyUnsuppressedWithoutEACQuery = "select COUNT(DISTINCT(a.patient_id)) as unsuppressed_no_eac\n" +
				"from (select b.patient_id\n" +
				"      from (select x.patient_id as patient_id, x.date_test_result_received\n" +
				"            from kenyaemr_etl.etl_laboratory_extract x\n" +
				"            where x.lab_test = 856\n" +
				"              and test_result >= 200\n" +
				"              and x.date_test_result_received BETWEEN DATE_SUB(DATE_SUB(date('" + endDate + "'), INTERVAL 14 DAY), INTERVAL DATEDIFF(date('" + endDate + "'),date('" + startDate + "')) DAY)\n" +
				"                AND  DATE_SUB(date('" + endDate + "'), INTERVAL 14 DAY)) b\n" +
				"               left join (select e.patient_id, e.visit_date as eac_date\n" +
				"                          from kenyaemr_etl.etl_enhanced_adherence e\n" +
				"                          where e.visit_date BETWEEN DATE_SUB(DATE_SUB(date('" + endDate + "'), INTERVAL 14 DAY), INTERVAL DATEDIFF(date('" + endDate + "'),date('" + startDate + "')) DAY) AND date('" + endDate + "')) e\n" +
				"                         on b.patient_id = e.patient_id\n" +
				"      where e.patient_id is null AND TIMESTAMPDIFF(DAY,b.date_test_result_received,CURRENT_DATE) > 14) a\n" +
				"         inner join\n" +
				"     (select t.patient_id\n" +
				"      from (select fup.visit_date,\n" +
				"                   fup.patient_id,\n" +
				"                   max(e.visit_date)                                                      as enroll_date,\n" +
				"                   greatest(max(fup.visit_date), ifnull(max(d.visit_date), '0000-00-00')) as latest_vis_date,\n" +
				"                   greatest(mid(max(concat(fup.visit_date, fup.next_appointment_date)), 11),\n" +
				"                            ifnull(max(d.visit_date), '0000-00-00'))                      as latest_tca,\n" +
				"                   d.patient_id                                                           as disc_patient,\n" +
				"                   d.effective_disc_date                                                  as effective_disc_date,\n" +
				"                   max(d.visit_date)                                                      as date_discontinued,\n" +
				"                   de.patient_id                                                          as started_on_drugs\n" +
				"            from kenyaemr_etl.etl_patient_hiv_followup fup\n" +
				"                     join kenyaemr_etl.etl_patient_demographics p on p.patient_id = fup.patient_id\n" +
				"                     join kenyaemr_etl.etl_hiv_enrollment e on fup.patient_id = e.patient_id\n" +
				"                     left join kenyaemr_etl.etl_drug_event de\n" +
				"                               on e.patient_id = de.patient_id and de.program = 'HIV' and\n" +
				"                                  date(de.date_started) <= date('" + endDate + "')\n" +
				"                     left outer JOIN\n" +
				"                 (select patient_id,\n" +
				"                         coalesce(date(effective_discontinuation_date), visit_date) visit_date,\n" +
				"                         max(date(effective_discontinuation_date)) as               effective_disc_date\n" +
				"                  from kenyaemr_etl.etl_patient_program_discontinuation\n" +
				"                  where date(visit_date) <= date('" + endDate + "')\n" +
				"                    and program_name = 'HIV'\n" +
				"                  group by patient_id) d on d.patient_id = fup.patient_id\n" +
				"            where fup.visit_date <= date('" + endDate + "')\n" +
				"            group by patient_id\n" +
				"            having (started_on_drugs is not null and started_on_drugs <> '')\n" +
				"               and (\n" +
				"                (\n" +
				"                    (timestampdiff(DAY, date(latest_tca), date('" + endDate + "')) <= 30 and\n" +
				"                     ((date(d.effective_disc_date) > date('" + endDate + "') or\n" +
				"                       date(enroll_date) > date(d.effective_disc_date)) or\n" +
				"                      d.effective_disc_date is null))\n" +
				"                        and\n" +
				"                    (date(latest_vis_date) >= date(date_discontinued) or date(latest_tca) >= date(date_discontinued) or\n" +
				"                     disc_patient is null)\n" +
				"                    )\n" +
				"                )) t) c on a.patient_id = c.patient_id;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(getVirallyUnsuppressedWithoutEACQuery, true)
					.get(0).get(0);
		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	/**
	 * This query counts the number of HEI 6-8 weeks old (denominator).
	 *
	 * @param startDate the start date in "dd/MM/yyyy" format
	 * @param endDate   the end date in "dd/MM/yyyy" format
	 * @return the count of HEI 6-8 weeks old
	 */
	public static Long getHeiSixToEightWeeksOld(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String getHeiSixToEightWeeksOldQuery = "SELECT COUNT(DISTINCT(e.patient_id))\n" +
				"FROM kenyaemr_etl.etl_hei_enrollment e\n" +
				"         INNER JOIN kenyaemr_etl.etl_patient_demographics d on e.patient_id = d.patient_id\n" +
				"WHERE TIMESTAMPDIFF(WEEK, d.DOB, DATE_SUB(date('" + endDate + "'), INTERVAL " + days
				+ " DAY)) BETWEEN 6 AND 8;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(getHeiSixToEightWeeksOldQuery, true).get(0)
					.get(0);
		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	/**
	 * This query counts the number of HEI 6-8 weeks without DNA PCR results
	 * (numerator).
	 *
	 * @param startDate the start date in "dd/MM/yyyy" format
	 * @param endDate   the end date in "dd/MM/yyyy" format
	 * @return the count of HEI 6-8 weeks without DNA PCR results
	 */
	public static Long getHeiSixToEightWeeksWithoutPCRResults(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String heiSixToEightWeeksWithoutPCRResultsQuery = "SELECT COUNT(DISTINCT(e.patient_id)) as hei_without_pcr\n" +
				"FROM kenyaemr_etl.etl_hei_enrollment e\n" +
				"         INNER JOIN kenyaemr_etl.etl_patient_demographics d on e.patient_id = d.patient_id\n" +
				"         LEFT JOIN kenyaemr_etl.etl_hiv_enrollment hiv on e.patient_id = hiv.patient_id\n" +
				"         LEFT JOIN(SELECT x.patient_id week6pcr, x.test_result as week6results\n" +
				"                   FROM kenyaemr_etl.etl_laboratory_extract x\n" +
				"                   WHERE x.lab_test = 1030\n" +
				"                     AND x.order_reason = 1040) t ON e.patient_id = t.week6pcr\n" +
				"WHERE TIMESTAMPDIFF(WEEK, d.DOB, DATE_SUB(date('" + endDate + "'), INTERVAL " + days
				+ " DAY)) BETWEEN 6 AND 8\n" +
				"  AND hiv.patient_id IS NULL\n" +
				"  AND t.week6pcr IS NULL;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(heiSixToEightWeeksWithoutPCRResultsQuery, true)
					.get(0).get(0);

		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	/**
	 * This query counts the number of HEI 24 months old (denominator).
	 *
	 * @param startDate the start date in "dd/MM/yyyy" format
	 * @param endDate   the end date in "dd/MM/yyyy" format
	 * @return the count of HEI 24 months old
	 */
	public static Long getHei24MonthsOld(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String hei24MonthsOldQuery = "SELECT COUNT(DISTINCT(e.patient_id))\n" +
				"FROM kenyaemr_etl.etl_hei_enrollment e\n" +
				"         INNER JOIN kenyaemr_etl.etl_patient_demographics d ON d.patient_id = e.patient_id\n" +
				"WHERE DATE_ADD(d.dob, INTERVAL 24 MONTH) BETWEEN DATE_SUB(date('" + endDate + "'), INTERVAL '" + days + "' DAY) AND date('" + endDate + "');";
		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(hei24MonthsOldQuery, true).get(0).get(0);

		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	/**
	 * This query counts the number of HEI 24 months old without documented outcome
	 * (numerator).
	 *
	 * @param startDate the start date in "dd/MM/yyyy" format
	 * @param endDate   the end date in "dd/MM/yyyy" format
	 * @return the count of HEI 24 months old without documented outcome
	 */
	public static Long getHei24MonthsWithoutDocumentedOutcome(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String hei24MonthsWithoutDocumentedOutcomeQuery = "SELECT COUNT(DISTINCT(e.patient_id)) as hei_without_outcome\n" +
				"FROM kenyaemr_etl.etl_hei_enrollment e\n" +
				"         INNER JOIN kenyaemr_etl.etl_patient_demographics d ON d.patient_id = e.patient_id\n" +
				"         LEFT JOIN (SELECT v.patient_id\n" +
				"                    FROM kenyaemr_etl.etl_hei_follow_up_visit v\n" +
				"                    WHERE v.dna_pcr_result IS NOT NULL\n" +
				"                       OR v.first_antibody_result IS NOT NULL\n" +
				"                       OR v.final_antibody_result IS NOT NULL\n" +
				"                    GROUP BY v.patient_id) has_test ON e.patient_id = has_test.patient_id\n" +
				"         LEFT JOIN (SELECT e.patient_id\n" +
				"                    from kenyaemr_etl.etl_hiv_enrollment e\n" +
				"                             INNER JOIN kenyaemr_etl.etl_patient_demographics d on e.patient_id = d.patient_id\n"
				+
				"                    WHERE visit_date <= date('" + endDate + "')) hiv_prog\n" +
				"                   ON e.patient_id = hiv_prog.patient_id\n" +
				"WHERE DATE_ADD( d.dob, INTERVAL 24 MONTH) BETWEEN DATE_SUB(date('" + endDate + "'), INTERVAL "
				+ days + " DAY) AND date('" + endDate + "')\n" +
				"  AND has_test.patient_id IS NULL\n" +
				"  AND hiv_prog.patient_id IS NULL;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(hei24MonthsWithoutDocumentedOutcomeQuery, true)
					.get(0).get(0);

		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	private static long getNumberOfDays(String startDate, String endDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate start = LocalDate.parse(startDate, formatter);
		LocalDate end = LocalDate.parse(endDate, formatter);
		long days = ChronoUnit.DAYS.between(start, end);
		return days;
	}

	/**
	 * This query gets 30-day running total of HIV-positive patients who were not
	 * linked to care.
	 *
	 * @param startDate The start date of the period
	 * @param endDate   The end date of the period
	 * @return A {@link SimpleObject} containing an array of records with:
	 *         - "number_hiv_positive": The count of HIV-positive patients.
	 *         - "visit_date": The visit date formatted as dd-MM-yyyy.
	 *         - "group": The tracing outcome category
	 */
	public static SimpleObject getMonthlyHivPositiveNotLinked(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String hivMonthlyPositiveNotLinkedQuery = "SELECT COUNT(DISTINCT(a.patient_id)) as number_hiv_positive,a.visit_date as visit_date\n" +
				"FROM ((SELECT av.patient_id, av.visit_date\n" +
				"       FROM kenyaemr_etl.etl_mch_antenatal_visit av\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics a on av.patient_id = a.patient_id\n" +
				"       WHERE av.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE\n" +
				"         AND av.final_test_result = 'Positive')\n" +
				"      UNION\n" +
				"      (SELECT d.patient_id, d.visit_date\n" +
				"       FROM kenyaemr_etl.etl_mchs_delivery d\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics a on a.patient_id = d.patient_id\n" +
				"       WHERE d.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE\n" +
				"         AND d.final_test_result = 'Positive')\n" +
				"      UNION\n" +
				"      (SELECT p.patient_id, p.visit_date\n" +
				"       FROM kenyaemr_etl.etl_mch_postnatal_visit p\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics d on p.patient_id = d.patient_id\n" +
				"       WHERE p.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE\n" +
				"         AND p.final_test_result = 'Positive')\n" +
				"      UNION\n" +
				"      (SELECT t.patient_id, t.visit_date\n" +
				"       FROM kenyaemr_etl.etl_hts_test t\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = t.patient_id\n" +
				"           AND t.final_test_result = 'Positive'\n" +
				"           AND t.voided = 0\n" +
				"           AND t.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE)) a\n" +
				"         LEFT JOIN\n" +
				"     (SELECT l.patient_id, l.ccc_number\n" +
				"      FROM kenyaemr_etl.etl_hts_referral_and_linkage l\n" +
				"      WHERE date(l.visit_date) BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE\n" +
				"      GROUP BY l.patient_id) l ON a.patient_id = l.patient_id\n" +
				"         LEFT JOIN (SELECT e.patient_id\n" +
				"                    FROM kenyaemr_etl.etl_hiv_enrollment e\n" +
				"                    WHERE date(e.visit_date) BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE) e\n"
				+
				"                   ON e.patient_id = a.patient_id\n" +
				"	where date(a.visit_date) >= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)\n" +
				"  and l.patient_id is null\n" +
				"  and e.patient_id is null\n" +
				"  group by date(visit_date)" +
				"order by date(visit_date) ASC;";

		return getSimpleObject(hivMonthlyPositiveNotLinkedQuery);
	}

	/**
	 * Retrieves a summary of HIV-positive patients who were not linked to care
	 * within a given date range.
	 *
	 * @param startDate The start date of the period (not currently used in the
	 *                  query).
	 * @param endDate   The end date of the period (not currently used in the
	 *                  query).
	 * @return A {@link SimpleObject} containing an array of records with:
	 *         - "day" (formatted as dd-MM-yyyy): The visit date.
	 *         - "value": The count of patients.
	 *         - "group": The tracing outcome category (e.g., "NotContacted",
	 *         "ContactedButNotLinked", "ContactedAndLinked").
	 */
	public static SimpleObject getMonthlyHivPositiveNotLinkedPatients(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String hivPositiveMonthlyNotLinkedPatients = "SELECT COUNT(DISTINCT(l.patient_id)) ,\n" +
				"       if(l.tracing_outcome = 1118,'NotContacted',\n" +
				"       if(l.tracing_outcome = 1066,'ContactedButNotLinked',\n" +
				"       if(l.tracing_outcome = 1065,'ContactedAndLinked',''))) as grouped_tracing_status,\n" +
				"    l.visit_date\n" +
				"    FROM kenyaemr_etl.etl_hts_linkage_tracing l\n" +
				"             inner join kenyaemr_etl.etl_patient_demographics a on l.patient_id = a.patient_id\n" +
				"    WHERE date(l.visit_date) BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE\n" +
				"group by date(visit_date),grouped_tracing_status\n" +
				"order by date(visit_date) ASC;";
		return getSimpleObject(hivPositiveMonthlyNotLinkedPatients);
	}

	/**
	 * Retrieves a summary of high-risk pregnant and breastfeeding women (PBFW) who
	 * are not on PrEP
	 * within a given date range.
	 *
	 * @param startDate The start date of the period.
	 * @param endDate   The end date of the period.
	 * @return A {@link SimpleObject} containing an array of records with:
	 *         - "day" (formatted as dd-MM-yyyy): The visit date.
	 *         - "value": The count of high-risk PBFW not on PrEP.
	 *         If no data is found, an empty data array is returned.
	 */
	public static SimpleObject getMonthlyHighRiskPBFWNotOnPrep(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String highRiskPBFWNotOnPrepQuery = "SELECT COUNT(DISTINCT(a.patient_id)) as high_risk_not_on_PrEP, a.visit_date as visit_date\n"
				+
				"FROM (SELECT s.patient_id,s.visit_date\n" +
				"      FROM kenyaemr_etl.etl_hts_eligibility_screening s\n" +
				"               INNER JOIN (SELECT t.patient_id,\n" +
				"                                  max(t.visit_date)                                           AS hts_date,\n"
				+
				"                                  mid(max(concat(date(visit_date), t.final_test_result)), 11) AS hiv_test_results,\n"
				+
				"                                  mid(max(concat(date(visit_date), t.hts_entry_point)), 11)   AS entry_point,\n"
				+
				"                                  t.visit_date\n" +
				"                           from kenyaemr_etl.etl_hts_test t\n" +
				"                           GROUP BY t.patient_id\n" +
				"                           HAVING hts_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE\n"
				+
				"                              AND hiv_test_results = 'Negative'\n" +
				"                              AND entry_point in (160538, 160456, 1623)) t\n" +
				"                          ON s.patient_id = t.patient_id -- AND s.visit_date <= t.visit_date\n" +
				"    where s.hts_risk_category IN ('High', 'Very high') and s.currently_on_prep in ('NO','Declined to answer')\n"
				+
				"         AND s.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE) a\n" +
				"         left join (select e.patient_id,\n" +
				"                           max(e.visit_date)                                        as latest_enrollment_date,\n"
				+
				"                           f.latest_fup_date,\n" +
				"                           greatest(ifnull(f.latest_fup_app_date, '0000-00-00'),\n" +
				"                                    ifnull(latest_refill_app_date, '0000-00-00'))   as latest_appointment_date,\n"
				+
				"                           greatest(ifnull(latest_fup_date, '0000-00-00'),\n" +
				"                                    ifnull(latest_refill_visit_date, '0000-00-00')) as latest_visit_date,\n"
				+
				"                           r.latest_refill_visit_date,\n" +
				"                           f.latest_fup_app_date,\n" +
				"                           r.latest_refill_app_date,\n" +
				"                           d.latest_disc_date,\n" +
				"                           d.disc_patient\n" +
				"                    from kenyaemr_etl.etl_prep_enrolment e\n" +
				"                             left join\n" +
				"                         (select f.patient_id,\n" +
				"                                 max(f.visit_date)                                      as latest_fup_date,\n"
				+
				"                                 mid(max(concat(f.visit_date, f.appointment_date)), 11) as latest_fup_app_date\n"
				+
				"                          from kenyaemr_etl.etl_prep_followup f\n" +
				"                          where f.visit_date <= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE\n"
				+
				"                          group by f.patient_id) f on e.patient_id = f.patient_id\n" +
				"                             left join (select r.patient_id,\n" +
				"                                               max(r.visit_date)                                      as latest_refill_visit_date,\n"
				+
				"                                               mid(max(concat(r.visit_date, r.next_appointment)), 11) as latest_refill_app_date\n"
				+
				"                                        from kenyaemr_etl.etl_prep_monthly_refill r\n" +
				"                                        where r.visit_date <= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE\n"
				+
				"                                        group by r.patient_id) r on e.patient_id = r.patient_id\n" +
				"                             left join (select patient_id                                               as disc_patient,\n"
				+
				"                                               max(d.visit_date)                                        as latest_disc_date,\n"
				+
				"                                               mid(max(concat(d.visit_date, d.discontinue_reason)), 11) as latest_disc_reason\n"
				+
				"                                        from kenyaemr_etl.etl_prep_discontinuation d\n" +
				"                                        where d.visit_date <= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE\n"
				+
				"                                        group by patient_id\n" +
				"                                        having latest_disc_date <= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE) d\n"
				+
				"                                       on e.patient_id = d.disc_patient\n" +
				"                    group by e.patient_id\n" +
				"                    having timestampdiff(DAY, date(latest_appointment_date), DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE)\n"
				+
				"                       and date(latest_appointment_date) >= date(latest_visit_date)\n" +
				"                       and ((latest_enrollment_date >= d.latest_disc_date\n" +
				"                        and latest_appointment_date > d.latest_disc_date) or d.disc_patient is null)) b\n"
				+
				"                   on a.patient_id = b.patient_id\n" +
				"		where a.visit_date <= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)\n" +
				"       and b.patient_id is null\n" +
				"       group by date(latest_appointment_date)\n" +
				"		order by date(latest_appointment_date) ASC\n;";

		return getSimpleObject(highRiskPBFWNotOnPrepQuery);
	}

	/**
	 * Executes a given SQL query and returns the results formatted as a
	 * {@link SimpleObject}.
	 *
	 * @param query The SQL query to execute.
	 * @return A {@link SimpleObject} containing an array of records with:
	 *         - "day" (formatted as dd-MM-yyyy): The date from the result set.
	 *         - "value": The numeric count or metric from the result set.
	 *         If the query returns no results, an empty data array is returned.
	 */
	private static SimpleObject getSimpleObject(String query) {
		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			List<List<Object>> resultSet = Context.getAdministrationService().executeSQL(query, true);

			List<SimpleObject> simpleObjectArrayList = new ArrayList<>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

			if (resultSet != null) {
				for (List<Object> row : resultSet) {
					if (row == null || row.isEmpty())
						continue;
					int value = ((Number) row.get(0)).intValue();

					if (row.size() == 2) {
						Date date = (Date) row.get(1);
						String day = (date != null) ? dateFormat.format(date) : "";
						simpleObjectArrayList.add(SimpleObject.create("day", day, "value", value));
					} else if (row.size() == 3) {
						String grouped_tracing_status = (String) row.get(1);
						Date sqlDate = (Date) row.get(2);
						String day = (sqlDate != null) ? dateFormat.format(sqlDate) : "";
						simpleObjectArrayList
								.add(SimpleObject.create("day", day, "value", value, "group", grouped_tracing_status));
					}
				}
			}

			return SimpleObject.create("data", simpleObjectArrayList);
		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	/**
	 * Retrieves the count of HEI (HIV-Exposed Infants) without a DNA PCR test
	 * within a specified date range, grouped by visit date.
	 *
	 * @param startDate The start date of the period.
	 * @param endDate   The end date of the period.
	 * @return A {@code SimpleObject} containing the results, with each record
	 *         including:
	 *         - `hei_without_pcr`: The count of HEI without a PCR test.
	 *         - `visit_date`: The date of the HEI enrollment visit.
	 */
	public static SimpleObject getMonthlyHeiDNAPCRPending(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String heiDNAPCRPendingQuery = "SELECT COUNT(DISTINCT(e.patient_id)) AS hei_without_pcr, DATE(e.visit_date) AS visit_date\n"
				+
				"FROM kenyaemr_etl.etl_hei_enrollment e\n" +
				"INNER JOIN kenyaemr_etl.etl_patient_demographics d ON e.patient_id = d.patient_id\n" +
				"LEFT JOIN kenyaemr_etl.etl_hiv_enrollment hiv ON e.patient_id = hiv.patient_id\n" +
				"LEFT JOIN (\n" +
				"    SELECT x.patient_id AS week6pcr, x.test_result AS week6results\n" +
				"    FROM kenyaemr_etl.etl_laboratory_extract x\n" +
				"    WHERE x.lab_test = 1030\n" +
				"    AND x.order_reason = 1040\n" +
				") t ON e.patient_id = t.week6pcr\n" +
				"WHERE DATE(e.visit_date) >= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)\n" +
				"AND TIMESTAMPDIFF(WEEK, d.DOB, DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)) BETWEEN 6 AND 8\n" +
				"AND hiv.patient_id IS NULL\n" +
				"AND t.week6pcr IS NULL\n" +
				"GROUP BY DATE(e.visit_date)\n" +
				"ORDER BY DATE(e.visit_date) ASC;";
		return getSimpleObject(heiDNAPCRPendingQuery);
	}

	/**
	 * Retrieves the count of patients eligible for a viral load (VL) sample but
	 * have not had one taken
	 * within a specified date range. The data is grouped by visit date.
	 * 
	 * @param startDate The start date of the period.
	 * @param endDate   The end date of the period.
	 * @return A {@code SimpleObject} containing the aggregated count of eligible
	 *         patients grouped by their visit dates.
	 */
	public static SimpleObject getMonthlyEligibleForVlSampleNotTaken(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String eligibleForVlSampleNotTakenQuery = "select COUNT(DISTINCT(b.patient_id)) as eligible_for_vl_sample_not_taken, e.visit_date as visit_date\n"
				+
				"          from (select fup.visit_date,\n" +
				"                         fup.patient_id,\n" +
				"                         max(e.visit_date)                                                                as enroll_date,\n"
				+
				"                         greatest(max(fup.visit_date), ifnull(max(d.visit_date), '0000-00-00'))           as latest_vis_date,\n"
				+
				"                         greatest(mid(max(concat(fup.visit_date, fup.next_appointment_date)), 11),\n" +
				"                                  ifnull(max(d.visit_date), '0000-00-00'))                                as latest_tca,\n"
				+
				"                         d.patient_id                                                                     as disc_patient,\n"
				+
				"                         d.effective_disc_date                                                            as effective_disc_date,\n"
				+
				"                         max(d.visit_date)                                                                as date_discontinued,\n"
				+
				"                         de.patient_id                                                   as started_on_drugs\n"
				+
				"                  from kenyaemr_etl.etl_patient_hiv_followup fup\n" +
				"                           join kenyaemr_etl.etl_patient_demographics p on p.patient_id = fup.patient_id\n"
				+
				"                           join kenyaemr_etl.etl_hiv_enrollment e on fup.patient_id=e.patient_id\n" +
				"                           left join kenyaemr_etl.etl_drug_event de\n" +
				"                                     on e.patient_id = de.patient_id and de.program = 'HIV' and date(de.date_started) <= current_date\n"
				+
				"                           left outer JOIN\n" +
				"                       (select patient_id,\n" +
				"                               coalesce(date(effective_discontinuation_date), visit_date) visit_date,\n"
				+
				"                               max(date(effective_discontinuation_date)) as               effective_disc_date\n"
				+
				"                        from kenyaemr_etl.etl_patient_program_discontinuation\n" +
				"                        where date(visit_date) <= current_date\n" +
				"                          and program_name = 'HIV'\n" +
				"                        group by patient_id) d on d.patient_id = fup.patient_id\n" +
				"                  where fup.visit_date BETWEEN DATE_SUB(current_date, INTERVAL 30 DAY) AND current_date\n"
				+
				"                  group by patient_id\n" +
				"                  having (started_on_drugs is not null and started_on_drugs <> '')\n" +
				"                     and (\n" +
				"                      (\n" +
				"                          (timestampdiff(DAY, date(latest_tca), current_date) <= 30 and\n" +
				"                           ((date(d.effective_disc_date) > current_date or date(enroll_date) > date(d.effective_disc_date)) or\n"
				+
				"                            d.effective_disc_date is null))\n" +
				"                              and\n" +
				"                          (date(latest_vis_date) >= date(date_discontinued) or date(latest_tca) >= date(date_discontinued) or\n"
				+
				"                           disc_patient is null)\n" +
				"                          )\n" +
				"                      )) e\n" +
				"                   INNER JOIN (select v.patient_id,v.date_test_requested, v.latest_hiv_followup_visit\n"
				+
				"                               from kenyaemr_etl.etl_viral_load_validity_tracker v\n" +
				"                                        inner join kenyaemr_etl.etl_patient_demographics d on v.patient_id = d.patient_id\n"
				+
				"                               where ((TIMESTAMPDIFF(MONTH, v.date_started_art, current_date) >= 3 and\n"
				+
				"                                       v.base_viral_load_test_result is null) -- First VL new on ART\n"
				+
				"                                   OR ((v.pregnancy_status = 1065 or v.breastfeeding_status = 1065) and\n"
				+
				"                                       TIMESTAMPDIFF(MONTH, v.date_started_art, current_date) >= 3 and\n"
				+
				"                                       (v.vl_result is not null and\n" +
				"                                        v.date_test_requested < v.latest_hiv_followup_visit)) -- immediate for PG & BF\n"
				+
				"                                   OR (v.vl_result >= 200 AND\n" +
				"                                       TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(current_date, INTERVAL 30 DAY)) >=\n"
				+
				"                                       3) -- Unsuppressed VL\n" +
				"                                   OR (v.vl_result < 200 AND\n" +
				"                                       TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(current_date, INTERVAL 30 DAY)) >=\n"
				+
				"                                       6 and\n" +
				"                                       TIMESTAMPDIFF(YEAR, v.date_test_requested, d.DOB) BETWEEN 0 AND 24) -- 0-24 with last suppressed vl\n"
				+
				"\n" +
				"                                   OR (v.vl_result < 200 AND\n" +
				"                                       TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(current_date, INTERVAL 30 DAY)) >=\n"
				+
				"                                       12 and\n" +
				"                                       TIMESTAMPDIFF(YEAR, v.date_test_requested, d.DOB) > 24) -- > 24 with last suppressed vl\n"
				+
				"\n" +
				"                                   OR ((v.pregnancy_status = 1065 or v.breastfeeding_status = 1065) and\n"
				+
				"                                       TIMESTAMPDIFF(MONTH, v.date_started_art, DATE_SUB(current_date, INTERVAL 30 DAY)) >= 3\n"
				+
				"                                       and (v.order_reason in (159882, 1434) and\n" +
				"                                            TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(current_date, INTERVAL 30 DAY)) >=\n"
				+
				"                                            12) and\n" +
				"                                       v.vl_result < 200)) -- PG & BF after PG/BF baseline < 200\n" +
				"                                 and (v.latest_hiv_followup_visit > IFNULL(v.date_test_requested,'0000-00-00'))) b on e.patient_id = b.patient_id\n"
				+
				"group by date(e.visit_date)\n" +
				"order by date(e.visit_date) asc;";
		return getSimpleObject(eligibleForVlSampleNotTakenQuery);
	}

	/**
	 * Retrieves the count of children aged 24 months who have not had a documented
	 * outcome
	 *
	 * @param startDate The start date of the period.
	 * @param endDate   The end date of the period.
	 * @return A {@code SimpleObject} containing the result of the query, with
	 *         counts of patients without documented outcomes
	 *         grouped by their visit dates.
	 */
	public static SimpleObject getMonthlyHei24MonthsWithoutDocumentedOutcome(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String hei24MonthsWithoutDocumentedOutcomeQuery = "SELECT COUNT(e.patient_id) AS hei_without_outcome, DATE_ADD(d.dob, INTERVAL 24 MONTH) AS date\n"
				+
				"FROM kenyaemr_etl.etl_hei_enrollment e\n" +
				"INNER JOIN kenyaemr_etl.etl_patient_demographics d ON d.patient_id = e.patient_id\n" +
				"LEFT JOIN (\n" +
				"    SELECT v.patient_id\n" +
				"    FROM kenyaemr_etl.etl_hei_follow_up_visit v\n" +
				"    WHERE v.dna_pcr_result IS NOT NULL\n" +
				"        OR v.first_antibody_result IS NOT NULL\n" +
				"        OR v.final_antibody_result IS NOT NULL\n" +
				"    GROUP BY v.patient_id\n" +
				") has_test ON e.patient_id = has_test.patient_id\n" +
				"LEFT JOIN (\n" +
				"    SELECT e.patient_id\n" +
				"    FROM kenyaemr_etl.etl_hiv_enrollment e\n" +
				"    INNER JOIN kenyaemr_etl.etl_patient_demographics d ON e.patient_id = d.patient_id\n" +
				"    WHERE visit_date <= CURRENT_DATE\n" +
				") hiv_prog ON e.patient_id = hiv_prog.patient_id\n" +
				"WHERE DATE_ADD(d.dob, INTERVAL 24 MONTH) BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE\n"
				+
				"	AND has_test.patient_id IS NULL\n" +
				"	AND hiv_prog.patient_id IS NULL\n" +
				"GROUP BY DATE(date)\n" +
				"ORDER BY DATE(date) ASC;";
		return getSimpleObject(hei24MonthsWithoutDocumentedOutcomeQuery);
	}

	/**
	 * Retrieves the count of virally unsuppressed patients who have not received an
	 * Enhanced Adherence Counseling (EAC)
	 * session within a specified date range.
	 *
	 * @param startDate The start date of the period.
	 * @param endDate   The end date of the period.
	 * @return A {@code SimpleObject} containing an array of records with count of
	 *         virally unsuppressed patients
	 *         who have not received EAC, grouped by the EAC visit date.
	 */
	public static SimpleObject getMonthlyVirallyUnsuppressedWithoutEAC(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String virallyUnsuppressedWithoutEACQuery = "SELECT COUNT(DISTINCT(a.patient_id)) AS unsuppressed_no_eac, DATE(eac_date) AS eac_date FROM\n" +
				"(SELECT b.patient_id, eac_date\n" +
				"FROM (\n" +
				"    SELECT \n" +
				"        x.patient_id AS patient_id,\n" +
				"        DATE_SUB(CURRENT_DATE, INTERVAL 21 DAY), \n" +
				"        DATE_SUB(CURRENT_DATE, INTERVAL 14 DAY)\n" +
				"    FROM kenyaemr_etl.etl_laboratory_extract x\n" +
				"    WHERE x.lab_test = 856\n" +
				"        AND test_result >= 200\n" +
				"        AND x.date_test_result_received \n" +
				"            BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 21 DAY) \n" +
				"            AND DATE_SUB(CURRENT_DATE, INTERVAL 14 DAY)\n" +
				") b\n" +
				"LEFT JOIN (\n" +
				"    SELECT e.patient_id, e.visit_date AS eac_date\n" +
				"    FROM kenyaemr_etl.etl_enhanced_adherence e\n" +
				"    WHERE e.visit_date \n" +
				"        BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 14 DAY) \n" +
				"        AND CURRENT_DATE\n" +
				") e ON b.patient_id = e.patient_id\n" +
				"WHERE e.patient_id IS NULL\n" +
				"GROUP BY DATE(eac_date)\n" +
				"ORDER BY DATE(eac_date) ASC) a\n" +
				"    inner join\n" +
				"    (select t.patient_id\n" +
				"    from (select fup.visit_date,\n" +
				"    fup.patient_id,\n" +
				"    max(e.visit_date)                                                      as enroll_date,\n" +
				"    greatest(max(fup.visit_date), ifnull(max(d.visit_date), '0000-00-00')) as latest_vis_date,\n" +
				"    greatest(mid(max(concat(fup.visit_date, fup.next_appointment_date)), 11),\n" +
				"    ifnull(max(d.visit_date), '0000-00-00'))                      as latest_tca,\n" +
				"    d.patient_id                                                           as disc_patient,\n" +
				"    d.effective_disc_date                                                  as effective_disc_date,\n" +
				"    max(d.visit_date)                                                      as date_discontinued,\n" +
				"    de.patient_id                                                          as started_on_drugs\n" +
				"    from kenyaemr_etl.etl_patient_hiv_followup fup\n" +
				"    join kenyaemr_etl.etl_patient_demographics p on p.patient_id = fup.patient_id\n" +
				"    join kenyaemr_etl.etl_hiv_enrollment e on fup.patient_id = e.patient_id\n" +
				"    left join kenyaemr_etl.etl_drug_event de\n" +
				"    on e.patient_id = de.patient_id and de.program = 'HIV' and\n" +
				"    date(de.date_started) <= date('" + endDate + "')\n" +
				"    left outer JOIN\n" +
				"    (select patient_id,\n" +
				"    coalesce(date(effective_discontinuation_date), visit_date) visit_date,\n" +
				"    max(date(effective_discontinuation_date)) as               effective_disc_date\n" +
				"    from kenyaemr_etl.etl_patient_program_discontinuation\n" +
				"    where date(visit_date) <= date('" + endDate + "')\n" +
				"    and program_name = 'HIV'\n" +
				"    group by patient_id) d on d.patient_id = fup.patient_id\n" +
				"    where fup.visit_date <= date('" + endDate + "')\n" +
				"    group by patient_id\n" +
				"    having (started_on_drugs is not null and started_on_drugs <> '')\n" +
				"    and (\n" +
				"    (\n" +
				"    (timestampdiff(DAY, date(latest_tca), date('" + endDate + "')) <= 30 and\n" +
				"    ((date(d.effective_disc_date) > date('" + endDate + "') or\n" +
				"    date(enroll_date) > date(d.effective_disc_date)) or\n" +
				"    d.effective_disc_date is null))\n" +
				"    and\n" +
				"    (date(latest_vis_date) >= date(date_discontinued) or date(latest_tca) >= date(date_discontinued) or\n" +
				"    disc_patient is null)\n" +
				"    )\n" +
				"    )) t) c on a.patient_id = c.patient_id;";
		return getSimpleObject(virallyUnsuppressedWithoutEACQuery);
	}

	/**
	 * Retrieves the count of patients who tested HIV positive within a specified
	 * date range.
	 *
	 * @param startDate The start date of the period.
	 * @param endDate   The end date of the period.
	 * @return A {@code SimpleObject} containing the count of patients who tested
	 *         HIV positive, grouped by visit date.
	 */
	public static SimpleObject getMonthlyPatientsTestedHivPositive(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String hivTestedPositiveQuery = "SELECT COUNT(DISTINCT(a.patient_id)) number_hiv_positive ,a.visit_date as visit_date\n" +
				"FROM ((SELECT av.patient_id, av.encounter_id, av.visit_date\n" +
				"       FROM kenyaemr_etl.etl_mch_antenatal_visit av\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics a on av.patient_id = a.patient_id\n" +
				"       WHERE av.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE\n" +
				"         AND av.final_test_result = 'Positive')\n" +
				"      UNION\n" +
				"      (SELECT d.patient_id, d.encounter_id, d.visit_date\n" +
				"       FROM kenyaemr_etl.etl_mchs_delivery d\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics a on a.patient_id = d.patient_id\n" +
				"       WHERE d.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE\n" +
				"         AND d.final_test_result = 'Positive')\n" +
				"      UNION\n" +
				"      (SELECT p.patient_id, p.encounter_id, p.visit_date\n" +
				"       FROM kenyaemr_etl.etl_mch_postnatal_visit p\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics d on p.patient_id = d.patient_id\n" +
				"       WHERE p.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE\n" +
				"         AND p.final_test_result = 'Positive')\n" +
				"      UNION\n" +
				"      (SELECT t.patient_id, t.encounter_id, t.visit_date\n" +
				"       FROM kenyaemr_etl.etl_hts_test t\n" +
				"                inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = t.patient_id \n" +
				"         AND t.final_test_result = 'Positive'\n" +
				"         AND t.voided = 0\n" +
				"         AND t.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE)) a\n" +
				"	where date(a.visit_date) >= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)\n" +
				"  	group by date(visit_date)" +
				"	order by date(visit_date) ASC;";

		return getSimpleObject(hivTestedPositiveQuery);
	}

	/**
	 * Retrieves the count of high-risk pregnant and postpartum clients who are not
	 * currently on PrEP
	 * within a specified date range.
	 *
	 * @param startDate The start date of the period.
	 * @param endDate   The end date of the period.
	 * @return A {@code SimpleObject} containing the count of high-risk pregnant and
	 *         postpartum clients,
	 *         grouped by visit date.
	 */
	public static SimpleObject getMonthlyPregnantOrPostpartumClients(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String pregnantOrPostpartumQuery = "SELECT COUNT(DISTINCT(s.patient_id)) AS high_risk_preg_postpartum, s.visit_date AS visit_date\n"
				+ //
				"FROM kenyaemr_etl.etl_hts_eligibility_screening s\n" + //
				"INNER JOIN (\n" + //
				"    SELECT \n" + //
				"        t.patient_id,\n" + //
				"        MAX(t.visit_date) AS hts_date,\n" + //
				"        MID(MAX(CONCAT(DATE(t.visit_date), t.final_test_result)), 11) AS hiv_test_results,\n" + //
				"        MID(MAX(CONCAT(DATE(t.visit_date), t.hts_entry_point)), 11) AS entry_point,\n" + //
				"        t.visit_date\n" + //
				"    FROM kenyaemr_etl.etl_hts_test t\n" + //
				"    GROUP BY t.patient_id\n" + //
				"    HAVING hts_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)\n" + //
				"                        AND CURRENT_DATE\n" + //
				"       AND hiv_test_results = 'Negative'\n" + //
				"       AND entry_point IN (160538, 160456, 1623)\n" + //
				") t ON s.patient_id = t.patient_id\n" + //
				"WHERE s.hts_risk_category IN ('High', 'Very high')\n" + //
				"  AND s.currently_on_prep IN ('NO', 'Declined to answer')\n" + //
				"  AND s.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)\n" + //
				"                       AND CURRENT_DATE\n" +
				"GROUP BY DATE(s.visit_date)\n" +
				"ORDER BY DATE(s.visit_date) ASC;";

		return getSimpleObject(pregnantOrPostpartumQuery);
	}

	/**
	 * Retrieves the count of HIV-exposed infants (HEI) aged 6 to 8 weeks within a
	 * specified date range.
	 *
	 * @param startDate The start date of the period.
	 * @param endDate   The end date of the period.
	 * @return A {@code SimpleObject} containing the count of HEI aged 6 to 8 weeks,
	 *         grouped by visit date.
	 */
	public static SimpleObject getMonthlyHeiSixToEightWeeksOld(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String getHeiSixToEightWeeksOldQuery = "SELECT COUNT(DISTINCT(e.patient_id)) AS hei, e.visit_date as visit_date\n" +
				"FROM kenyaemr_etl.etl_hei_enrollment e\n" +
				"         INNER JOIN kenyaemr_etl.etl_patient_demographics d on e.patient_id = d.patient_id\n" +
				"WHERE TIMESTAMPDIFF(WEEK, d.DOB, DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)) BETWEEN 6 AND 8\n" +
				"GROUP BY DATE(e.visit_date)\n" +
				"ORDER BY DATE(e.visit_date) ASC;";

		return getSimpleObject(getHeiSixToEightWeeksOldQuery);
	}

	/**
	 * Retrieves a summary of HIV patients eligible for viral load testing within a
	 * specified monthly period.
	 *
	 * @param startDate
	 * @param endDate
	 * @return a {@link SimpleObject} containing the number of eligible patients and
	 *         the associated visit dates,
	 */
	public static SimpleObject getMonthlyEligibleForVl(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String eligibleForVlQuery = "select COUNT(DISTINCT(b.patient_id)) as eligible_for_vl, e.visit_date as visit_date\n" +
				"from (select fup.visit_date,\n" +
				"             fup.patient_id,\n" +
				"             min(e.visit_date)                                               as enroll_date,\n" +
				"             max(fup.visit_date)                                             as latest_vis_date,\n" +
				"             mid(max(concat(fup.visit_date, fup.next_appointment_date)), 11) as latest_tca,\n" +
				"             max(d.visit_date)                                               as date_discontinued,\n" +
				"             d.patient_id                                                    as disc_patient,\n" +
				"             de.patient_id                                                   as started_on_drugs\n" +
				"      from kenyaemr_etl.etl_patient_hiv_followup fup\n" +
				"               join kenyaemr_etl.etl_patient_demographics p on p.patient_id = fup.patient_id\n" +
				"               join kenyaemr_etl.etl_hiv_enrollment e on fup.patient_id = e.patient_id\n" +
				"               left outer join kenyaemr_etl.etl_drug_event de\n" +
				"                               on e.patient_id = de.patient_id and date(date_started) <= current_date\n"
				+
				"               left outer JOIN\n" +
				"           (select patient_id, visit_date\n" +
				"            from kenyaemr_etl.etl_patient_program_discontinuation\n" +
				"            where date(visit_date) <= current_date\n" +
				"              and program_name = 'HIV'\n" +
				"            group by patient_id) d on d.patient_id = fup.patient_id\n" +
				"      where fup.visit_date BETWEEN DATE_SUB(current_date, INTERVAL 30 DAY) AND current_date\n" +
				"      group by patient_id\n" +
				"      having (started_on_drugs is not null and started_on_drugs <> \"\")\n" +
				"         and (\n" +
				"          (date(latest_tca) > current_date and\n" +
				"           (date(latest_tca) > date(date_discontinued) or disc_patient is null)) or\n" +
				"          (((date(latest_tca) between date_sub(current_date, interval 3 MONTH) and current_date) and\n"
				+
				"            (date(latest_vis_date) >= date(latest_tca)))) and\n" +
				"          (date(latest_tca) > date(date_discontinued) or disc_patient is null))) e\n" +
				"         INNER JOIN (select v.patient_id\n" +
				"                     from kenyaemr_etl.etl_viral_load_validity_tracker v\n" +
				"                              inner join kenyaemr_etl.etl_patient_demographics d on v.patient_id = d.patient_id\n"
				+
				"                     where (TIMESTAMPDIFF(MONTH, v.date_started_art, DATE_SUB(current_date, INTERVAL 30 DAY)) >= 3 and\n"
				+
				"                            v.base_viral_load_test_result is null)                              -- First VL new on ART\n"
				+
				"                        OR ((v.pregnancy_status = 1065 or v.breastfeeding_status = 1065) and\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_started_art, DATE_SUB(current_date, INTERVAL 30 DAY)) >= 3 and\n"
				+
				"                            (v.vl_result is not null and\n" +
				"                             v.date_test_requested < v.latest_hiv_followup_visit))              -- immediate for PG & BF\n"
				+
				"                        OR (v.vl_result >= 200 AND\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(current_date, INTERVAL 30 DAY)) >=\n"
				+
				"                            3)                                                                  -- Unsuppressed VL\n"
				+
				"                        OR (v.vl_result < 200 AND\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(current_date, INTERVAL 30 DAY)) >= 6 and\n"
				+
				"                            TIMESTAMPDIFF(YEAR, v.date_test_requested, d.DOB) BETWEEN 0 AND 24) -- 0-24 with last suppressed vl\n"
				+
				"                        OR (v.vl_result < 200 AND\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(current_date, INTERVAL 30 DAY)) >=\n"
				+
				"                            12 and\n" +
				"                            TIMESTAMPDIFF(YEAR, v.date_test_requested, d.DOB) > 24)             -- > 24 with last suppressed vl\n"
				+
				"                        OR ((v.pregnancy_status = 1065 or v.breastfeeding_status = 1065) and\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_started_art, DATE_SUB(current_date, INTERVAL 30 DAY)) >= 3\n"
				+
				"                         and (v.order_reason in (159882, 1434) and\n" +
				"                              TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(current_date, INTERVAL 30 DAY)) >=\n"
				+
				"                              12) and\n" +
				"                            v.vl_result < 200) -- PG & BF after PG/BF baseline < 200\n" +
				") b on e.patient_id = b.patient_id\n" +
				"group by date(e.visit_date)\n" +
				"order by date(e.visit_date) asc;";

		return getSimpleObject(eligibleForVlQuery);
	}

	/**
	 * Retrieves the count of HIV-exposed infants (HEI) aged 24 months within a
	 * specified date range.
	 *
	 * @param startDate The start date of the period.
	 * @param endDate   The end date of the period.
	 * @return A {@code SimpleObject} containing the count of HEI aged 24 months,
	 *         grouped by visit date.
	 */
	public static SimpleObject getMonthlyHei24MonthsOld(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String hei24MonthsOldQuery = "SELECT COUNT(DISTINCT(e.patient_id)) AS hei_24_months, DATE_ADD(d.dob, INTERVAL 24 MONTH) AS date\n"
				+
				"FROM kenyaemr_etl.etl_hei_enrollment e\n" +
				"         INNER JOIN kenyaemr_etl.etl_patient_demographics d ON d.patient_id = e.patient_id\n" +
				"WHERE DATE_ADD(d.dob, INTERVAL 24 MONTH) BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AND CURRENT_DATE\n"
				+
				"GROUP BY DATE(date)\n" +
				"ORDER BY DATE(date) ASC;";
		return getSimpleObject(hei24MonthsOldQuery);
	}

	/**
	 * Retrieves the count of patients who are virally unsuppressed within a
	 * specified date range.
	 *
	 * @param startDate The start date of the period.
	 * @param endDate   The end date of the period.
	 * @return A {@code SimpleObject} containing the count of virally unsuppressed
	 *         patients, grouped by visit date.
	 */
	public static SimpleObject getMonthlyVirallyUnsuppressed(String startDate, String endDate) {
		long days = getNumberOfDays(startDate, endDate);
		String getVirallyUnsuppressedQuery = "SELECT COUNT(DISTINCT(a.patient_id)) AS vl_unsuppressed, a.visit_date AS results_enc_date FROM\n" +
				"(SELECT x.patient_id, COALESCE(x.date_test_result_received,x.visit_date) AS visit_date\n" +
				"FROM kenyaemr_etl.etl_laboratory_extract x\n" +
				"WHERE x.lab_test = 856\n" +
				"  AND x.test_result >= 200\n" +
				"  AND x.date_test_result_received BETWEEN DATE_SUB(DATE_SUB(date('" + endDate + "'), INTERVAL 14 DAY), INTERVAL DATEDIFF(date('" + endDate + "'),date('" + startDate + "')) DAY)\n" +
				"          AND  DATE_SUB(date('" + endDate + "'), INTERVAL 14 DAY)\n" +
				"GROUP BY DATE(x.visit_date)\n" +
				"ORDER BY DATE(x.visit_date) ASC) a\n" +
				"    inner join\n" +
				"    (select t.patient_id\n" +
				"    from (select fup.visit_date,\n" +
				"    fup.patient_id,\n" +
				"    max(e.visit_date)                                                      as enroll_date,\n" +
				"    greatest(max(fup.visit_date), ifnull(max(d.visit_date), '0000-00-00')) as latest_vis_date,\n" +
				"    greatest(mid(max(concat(fup.visit_date, fup.next_appointment_date)), 11),\n" +
				"    ifnull(max(d.visit_date), '0000-00-00'))                      as latest_tca,\n" +
				"    d.patient_id                                                           as disc_patient,\n" +
				"    d.effective_disc_date                                                  as effective_disc_date,\n" +
				"    max(d.visit_date)                                                      as date_discontinued,\n" +
				"    de.patient_id                                                          as started_on_drugs\n" +
				"    from kenyaemr_etl.etl_patient_hiv_followup fup\n" +
				"    join kenyaemr_etl.etl_patient_demographics p on p.patient_id = fup.patient_id\n" +
				"    join kenyaemr_etl.etl_hiv_enrollment e on fup.patient_id = e.patient_id\n" +
				"    left join kenyaemr_etl.etl_drug_event de\n" +
				"    on e.patient_id = de.patient_id and de.program = 'HIV' and\n" +
				"    date(de.date_started) <= date('" + endDate + "')\n" +
				"    left outer JOIN\n" +
				"    (select patient_id,\n" +
				"    coalesce(date(effective_discontinuation_date), visit_date) visit_date,\n" +
				"    max(date(effective_discontinuation_date)) as               effective_disc_date\n" +
				"    from kenyaemr_etl.etl_patient_program_discontinuation\n" +
				"    where date(visit_date) <= date('" + endDate + "')\n" +
				"    and program_name = 'HIV'\n" +
				"    group by patient_id) d on d.patient_id = fup.patient_id\n" +
				"    where fup.visit_date <= date('" + endDate + "')\n" +
				"    group by patient_id\n" +
				"    having (started_on_drugs is not null and started_on_drugs <> '')\n" +
				"    and (\n" +
				"    (\n" +
				"    (timestampdiff(DAY, date(latest_tca), date('" + endDate + "')) <= 30 and\n" +
				"    ((date(d.effective_disc_date) > date('" + endDate + "') or\n" +
				"    date(enroll_date) > date(d.effective_disc_date)) or\n" +
				"    d.effective_disc_date is null))\n" +
				"    and\n" +
				"    (date(latest_vis_date) >= date(date_discontinued) or date(latest_tca) >= date(date_discontinued) or\n" +
				"    disc_patient is null)\n" +
				"    )\n" +
				"    )) t) c on a.patient_id = c.patient_id;";
		return getSimpleObject(getVirallyUnsuppressedQuery);
	}
}
