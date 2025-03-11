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

import org.openmrs.api.context.Context;
import org.openmrs.util.PrivilegeConstants;

public class FacilityDashboardUtil {

	/**
	 * Surveillance dashboards
	 * 1. Count of clients tested positive not linked
	 * @return long value
	 */
	public static Long getHivPositiveNotLinked() {		
		String hivPositiveNotLinkedQuery = "SELECT COUNT(a.patient_id) number_hiv_positive\n" +
			"FROM ((SELECT av.patient_id\n" +
			"       FROM kenyaemr_etl.etl_mch_antenatal_visit av\n" +
			"                inner join kenyaemr_etl.etl_patient_demographics a on av.patient_id = a.patient_id\n" +
			"       WHERE av.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE\n" +
			"         AND av.final_test_result = 'Positive')\n" +
			"      UNION\n" +
			"      (SELECT d.patient_id\n" +
			"       FROM kenyaemr_etl.etl_mchs_delivery d\n" +
			"                inner join kenyaemr_etl.etl_patient_demographics a on a.patient_id = d.patient_id\n" +
			"       WHERE d.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE\n" +
			"         AND d.final_test_result = 'Positive')\n" +
			"      UNION\n" +
			"      (SELECT p.patient_id\n" +
			"       FROM kenyaemr_etl.etl_mch_postnatal_visit p\n" +
			"                inner join kenyaemr_etl.etl_patient_demographics d on p.patient_id = d.patient_id\n" +
			"       WHERE p.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE\n" +
			"         AND p.final_test_result = 'Positive')\n" +
			"      UNION\n" +
			"      (SELECT t.patient_id\n" +
			"       FROM kenyaemr_etl.etl_hts_test t\n" +
			"                inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = t.patient_id\n" +
			"           AND t.final_test_result = 'Positive'\n" +
			"           AND t.voided = 0\n" +
			"           AND t.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE)) a\n" +
			"         LEFT JOIN\n" +
			"     (SELECT l.patient_id, l.ccc_number\n" +
			"      FROM kenyaemr_etl.etl_hts_referral_and_linkage l\n" +
			"      WHERE date(l.visit_date) BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE\n" +
			"      GROUP BY l.patient_id) l ON a.patient_id = l.patient_id\n" +
			"         LEFT JOIN (SELECT e.patient_id\n" +
			"                    FROM kenyaemr_etl.etl_hiv_enrollment e\n" +
			"                    WHERE date(e.visit_date) BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE) e\n" +
			"                   ON e.patient_id = a.patient_id\n" +
			"where l.patient_id is null\n" +
			"  and e.patient_id is null;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(hivPositiveNotLinkedQuery, true).get(0).get(0);
		}
		finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}
	/**
	 * Surveillance dashboards
	 * 1.1 Count of clients tested positive baseline
	 * @return long value
	 */
	public static Long getPatientsTestedHivPositive() {
		String hivTestedPositiveQuery = "SELECT COUNT(patient_id) number_hiv_positive\n" +
			"FROM ((SELECT av.patient_id, av.encounter_id\n" +
			"       FROM kenyaemr_etl.etl_mch_antenatal_visit av\n" +
			"                inner join kenyaemr_etl.etl_patient_demographics a on av.patient_id = a.patient_id\n" +
			"       WHERE av.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE\n" +
			"         AND av.final_test_result = 'Positive')\n" +
			"      UNION\n" +
			"      (SELECT d.patient_id, d.encounter_id\n" +
			"       FROM kenyaemr_etl.etl_mchs_delivery d\n" +
			"                inner join kenyaemr_etl.etl_patient_demographics a on a.patient_id = d.patient_id\n" +
			"       WHERE d.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE\n" +
			"         AND d.final_test_result = 'Positive')\n" +
			"      UNION\n" +
			"      (SELECT p.patient_id, p.encounter_id\n" +
			"       FROM kenyaemr_etl.etl_mch_postnatal_visit p\n" +
			"                inner join kenyaemr_etl.etl_patient_demographics d on p.patient_id = d.patient_id\n" +
			"       WHERE p.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE\n" +
			"         AND p.final_test_result = 'Positive')\n" +
			"      UNION\n" +
			"      (SELECT t.patient_id, t.encounter_id\n" +
			"       FROM kenyaemr_etl.etl_hts_test t\n" +
			"                inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = t.patient_id \n" +
			"         AND t.final_test_result = 'Positive'\n" +
			"         AND t.voided = 0\n" +
			"         AND t.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE)) a;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(hivTestedPositiveQuery, true).get(0).get(0);
		}
		finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	/**
	 * Base query for pregnant or postpartum women
	 * @return
	 */
	public static Long getPregnantOrPostpartumClients() {
		String pregnantOrPostpartumQuery = "SELECT COUNT(s.patient_id) high_risk_preg_postpartum\n" +
				"FROM kenyaemr_etl.etl_hts_eligibility_screening s\n" +
				"         INNER JOIN (SELECT t.patient_id,\n" +
				"                            max(t.visit_date)                                           AS hts_date,\n" +
				"                            mid(max(concat(date(visit_date), t.final_test_result)), 11) AS hiv_test_results,\n" +
				"                            mid(max(concat(date(visit_date), t.hts_entry_point)), 11)   AS entry_point,\n" +
				"                            t.visit_date\n" +
				"                     FROM kenyaemr_etl.etl_hts_test t\n" +
				"                     GROUP BY t.patient_id\n" +
				"                     HAVING hts_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE\n" +
				"                        AND hiv_test_results = 'Negative'\n" +
				"                        AND entry_point in (160538, 160456, 1623)) t\n" +
				"                    ON s.patient_id = t.patient_id\n" +
				"WHERE s.hts_risk_category IN ('High', 'Very high')\n" +
				"  AND s.currently_on_prep in ('NO', 'Declined to answer')\n" +
				"  AND s.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(pregnantOrPostpartumQuery, true).get(0).get(0);
		}
		finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}
	/**
	 * Surveillance dashboards
	 * 2. Pregnant post partum women at High risk not linked to prep
	 * @return long value
	 */
	public static Long getPregnantPostpartumNotInPrep() {	
		String pregnantPostPartumNotPrepLinkedQuery = "SELECT COUNT(a.patient_id) high_risk_not_on_PrEP\n" +
			"FROM (SELECT s.patient_id\n" +
			"      FROM kenyaemr_etl.etl_hts_eligibility_screening s\n" +
			"               INNER JOIN (SELECT t.patient_id,\n" +
			"                                  max(t.visit_date)                                           AS hts_date,\n" +
			"                                  mid(max(concat(date(visit_date), t.final_test_result)), 11) AS hiv_test_results,\n" +
			"                                  mid(max(concat(date(visit_date), t.hts_entry_point)), 11)   AS entry_point,\n" +
			"                                  t.visit_date\n" +
			"                           from kenyaemr_etl.etl_hts_test t\n" +
			"                           GROUP BY t.patient_id\n" +
			"                           HAVING hts_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE\n" +
			"                              AND hiv_test_results = 'Negative'\n" +
			"                              AND entry_point in (160538, 160456, 1623)) t\n" +
			"                          ON s.patient_id = t.patient_id -- AND s.visit_date <= t.visit_date\n" +
			"    where s.hts_risk_category IN ('High', 'Very high') and s.currently_on_prep in ('NO','Declined to answer')\n" +
			"         AND s.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE) a\n" +
			"         left join (select e.patient_id,\n" +
			"                           max(e.visit_date)                                        as latest_enrollment_date,\n" +
			"                           f.latest_fup_date,\n" +
			"                           greatest(ifnull(f.latest_fup_app_date, '0000-00-00'),\n" +
			"                                    ifnull(latest_refill_app_date, '0000-00-00'))   as latest_appointment_date,\n" +
			"                           greatest(ifnull(latest_fup_date, '0000-00-00'),\n" +
			"                                    ifnull(latest_refill_visit_date, '0000-00-00')) as latest_visit_date,\n" +
			"                           r.latest_refill_visit_date,\n" +
			"                           f.latest_fup_app_date,\n" +
			"                           r.latest_refill_app_date,\n" +
			"                           d.latest_disc_date,\n" +
			"                           d.disc_patient\n" +
			"                    from kenyaemr_etl.etl_prep_enrolment e\n" +
			"                             left join\n" +
			"                         (select f.patient_id,\n" +
			"                                 max(f.visit_date)                                      as latest_fup_date,\n" +
			"                                 mid(max(concat(f.visit_date, f.appointment_date)), 11) as latest_fup_app_date\n" +
			"                          from kenyaemr_etl.etl_prep_followup f\n" +
			"                          where f.visit_date <= date(CURRENT_DATE)\n" +
			"                          group by f.patient_id) f on e.patient_id = f.patient_id\n" +
			"                             left join (select r.patient_id,\n" +
			"                                               max(r.visit_date)                                      as latest_refill_visit_date,\n" +
			"                                               mid(max(concat(r.visit_date, r.next_appointment)), 11) as latest_refill_app_date\n" +
			"                                        from kenyaemr_etl.etl_prep_monthly_refill r\n" +
			"                                        where r.visit_date <= date(CURRENT_DATE)\n" +
			"                                        group by r.patient_id) r on e.patient_id = r.patient_id\n" +
			"                             left join (select patient_id                                               as disc_patient,\n" +
			"                                               max(d.visit_date)                                        as latest_disc_date,\n" +
			"                                               mid(max(concat(d.visit_date, d.discontinue_reason)), 11) as latest_disc_reason\n" +
			"                                        from kenyaemr_etl.etl_prep_discontinuation d\n" +
			"                                        where d.visit_date <= date(CURRENT_DATE)\n" +
			"                                        group by patient_id\n" +
			"                                        having latest_disc_date <= date(CURRENT_DATE)) d\n" +
			"                                       on e.patient_id = d.disc_patient\n" +
			"                    group by e.patient_id\n" +
			"                    having timestampdiff(DAY, date(latest_appointment_date), date(CURRENT_DATE)) <= 7\n" +
			"                       and date(latest_appointment_date) >= date(latest_visit_date)\n" +
			"                       and ((latest_enrollment_date >= d.latest_disc_date\n" +
			"                        and latest_appointment_date > d.latest_disc_date) or d.disc_patient is null)) b\n" +
			"                   on a.patient_id = b.patient_id\n" +
			"where b.patient_id is null;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(pregnantPostPartumNotPrepLinkedQuery, true).get(0).get(0);
		}
		finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	/**
	 * Surveillance dashboards
	 * Clients eligible for VL test
	 * @return long value
	 */
	public static Long getEligibleForVl() {
		String eligibleForVlQuery = "select count(b.patient_id) as eligible_for_vl\n" +
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
				"                               on e.patient_id = de.patient_id and date(date_started) <= CURRENT_DATE\n" +
				"               left outer JOIN\n" +
				"           (select patient_id, visit_date\n" +
				"            from kenyaemr_etl.etl_patient_program_discontinuation\n" +
				"            where date(visit_date) <= CURRENT_DATE\n" +
				"              and program_name = 'HIV'\n" +
				"            group by patient_id) d on d.patient_id = fup.patient_id\n" +
				"      where fup.visit_date <= CURRENT_DATE\n" +
				"      group by patient_id\n" +
				"      having (started_on_drugs is not null and started_on_drugs <> \"\")\n" +
				"         and (\n" +
				"          (date(latest_tca) > CURRENT_DATE and\n" +
				"           (date(latest_tca) > date(date_discontinued) or disc_patient is null)) or\n" +
				"          (((date(latest_tca) between date_sub(CURRENT_DATE, interval 3 MONTH) and CURRENT_DATE) and\n" +
				"            (date(latest_vis_date) >= date(latest_tca)))) and\n" +
				"          (date(latest_tca) > date(date_discontinued) or disc_patient is null))) e\n" +
				"         INNER JOIN (select v.patient_id\n" +
				"                     from kenyaemr_etl.etl_viral_load_validity_tracker v\n" +
				"                              inner join kenyaemr_etl.etl_patient_demographics d on v.patient_id = d.patient_id\n" +
				"                     where (TIMESTAMPDIFF(MONTH, v.date_started_art, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) >= 3 and\n" +
				"                            v.base_viral_load_test_result is null)                              -- First VL new on ART\n" +
				"                        OR ((v.pregnancy_status = 1065 or v.breastfeeding_status = 1065) and\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_started_art, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) >= 3 and\n" +
				"                            (v.vl_result is not null and\n" +
				"                             v.date_test_requested < v.latest_hiv_followup_visit))              -- immediate for PG & BF\n" +
				"                        OR (v.vl_result >= 200 AND\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) >=\n" +
				"                            3)                                                                  -- Unsuppressed VL\n" +
				"                        OR (v.vl_result < 200 AND\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) >= 6 and\n" +
				"                            TIMESTAMPDIFF(YEAR, v.date_test_requested, d.DOB) BETWEEN 0 AND 24) -- 0-24 with last suppressed vl\n" +
				"                        OR (v.vl_result < 200 AND\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) >=\n" +
				"                            12 and\n" +
				"                            TIMESTAMPDIFF(YEAR, v.date_test_requested, d.DOB) > 24)             -- > 24 with last suppressed vl\n" +
				"                        OR ((v.pregnancy_status = 1065 or v.breastfeeding_status = 1065) and\n" +
				"                            TIMESTAMPDIFF(MONTH, v.date_started_art, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) >= 3\n" +
				"                         and (v.order_reason in (159882, 1434) and\n" +
				"                              TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) >=\n" +
				"                              12) and\n" +
				"                            v.vl_result < 200) -- PG & BF after PG/BF baseline < 200\n" +
				") b on e.patient_id = b.patient_id;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(eligibleForVlQuery, true).get(0).get(0);
		}
		finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}
	/**
	 * Surveillance dashboards
	 * 3. Delayed viral load testing. Eligible without VL test taken
	 * @return long value
	 */
	public static Long getEligibleForVlSampleNotTaken() {		
		String eligibleForVlSampleNotTakenQuery = "select count(b.patient_id) as eligible_for_vl_sample_not_taken\n" +
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
				"                               on e.patient_id = de.patient_id and date(date_started) <= CURRENT_DATE\n" +
				"               left outer JOIN\n" +
				"           (select patient_id, visit_date\n" +
				"            from kenyaemr_etl.etl_patient_program_discontinuation\n" +
				"            where date(visit_date) <= CURRENT_DATE\n" +
				"              and program_name = 'HIV'\n" +
				"            group by patient_id) d on d.patient_id = fup.patient_id\n" +
				"      where fup.visit_date <= CURRENT_DATE\n" +
				"      group by patient_id\n" +
				"      having (started_on_drugs is not null and started_on_drugs <> \"\")\n" +
				"         and (\n" +
				"          (date(latest_tca) > CURRENT_DATE and\n" +
				"           (date(latest_tca) > date(date_discontinued) or disc_patient is null)) or\n" +
				"          (((date(latest_tca) between date_sub(CURRENT_DATE, interval 3 MONTH) and CURRENT_DATE) and\n" +
				"            (date(latest_vis_date) >= date(latest_tca)))) and\n" +
				"          (date(latest_tca) > date(date_discontinued) or disc_patient is null))) e\n" +
				"         INNER JOIN (select v.patient_id\n" +
				"                     from kenyaemr_etl.etl_viral_load_validity_tracker v\n" +
				"                              inner join kenyaemr_etl.etl_patient_demographics d on v.patient_id = d.patient_id\n" +
				"                     where ((TIMESTAMPDIFF(MONTH, v.date_started_art, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) >= 3 and\n" +
				"                             v.base_viral_load_test_result is null) -- First VL new on ART\n" +
				"                         OR ((v.pregnancy_status = 1065 or v.breastfeeding_status = 1065) and\n" +
				"                             TIMESTAMPDIFF(MONTH, v.date_started_art, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) >= 3 and\n" +
				"                             (v.vl_result is not null and\n" +
				"                              v.date_test_requested < v.latest_hiv_followup_visit)) -- immediate for PG & BF\n" +
				"                         OR (v.vl_result >= 200 AND\n" +
				"                             TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) >=\n" +
				"                             3) -- Unsuppressed VL\n" +
				"                         OR (v.vl_result < 200 AND\n" +
				"                             TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) >=\n" +
				"                             6 and\n" +
				"                             TIMESTAMPDIFF(YEAR, v.date_test_requested, d.DOB) BETWEEN 0 AND 24) -- 0-24 with last suppressed vl\n" +
				"                         OR (v.vl_result < 200 AND\n" +
				"                             TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) >=\n" +
				"                             12 and\n" +
				"                             TIMESTAMPDIFF(YEAR, v.date_test_requested, d.DOB) > 24) -- > 24 with last suppressed vl\n" +
				"                         OR ((v.pregnancy_status = 1065 or v.breastfeeding_status = 1065) and\n" +
				"                             TIMESTAMPDIFF(MONTH, v.date_started_art, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) >= 3\n" +
				"                             and (v.order_reason in (159882, 1434) and\n" +
				"                                  TIMESTAMPDIFF(MONTH, v.date_test_requested, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) >=\n" +
				"                                  12) and\n" +
				"                             v.vl_result < 200)) -- PG & BF after PG/BF baseline < 200\n" +
				"                       and v.latest_hiv_followup_visit > v.date_test_requested) b on e.patient_id = b.patient_id;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(eligibleForVlSampleNotTakenQuery, true).get(0).get(0);
		}
		finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}
	/**
	 * Surveillance dashboards
	 * 4. Count of Virally unsuppressed clients
	 * @return long value
	 */
	public static Long getVirallyUnsuppressed(){
		String getVirallyUnsuppressedQuery = "SELECT count(x.patient_id) as vl_unsuppressed\n" +
				"FROM kenyaemr_etl.etl_laboratory_extract x\n" +
				"WHERE x.lab_test = 856\n" +
				"  AND test_result >= 200\n" +
				"  AND x.date_test_result_received BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 21 DAY) AND DATE_SUB(CURRENT_DATE, INTERVAL 14 DAY);";
		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(getVirallyUnsuppressedQuery, true).get(0).get(0);
		}
		finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}
	/**
	 * Surveillance dashboards
	 * 4. Count of Virally suppressed clients with +2 weeks without EAC
	 * @return long value
	 */
	public static Long getVirallyUnsuppressedWithoutEAC(){
		String getVirallyUnsuppressedWithoutEACQuery = "select count(b.patient_id) as unsuppressed_no_eac\n" +
			"from (select x.patient_id as patient_id,DATE_SUB(CURRENT_DATE, INTERVAL 21 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 14 DAY)\n" +
			"      from kenyaemr_etl.etl_laboratory_extract x\n" +
			"      where x.lab_test = 856\n" +
			"        and test_result >= 200\n" +
			"        and x.date_test_result_received BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 21 DAY) AND DATE_SUB(CURRENT_DATE, INTERVAL 14 DAY)) b\n" +
			"         left join (select e.patient_id, e.visit_date as eac_date\n" +
			"                    from kenyaemr_etl.etl_enhanced_adherence e\n" +
			"                    where e.visit_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 14 DAY) AND CURRENT_DATE) e\n" +
			"                   on b.patient_id = e.patient_id\n" +
			"where e.patient_id is null;\n";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(getVirallyUnsuppressedWithoutEACQuery, true).get(0).get(0);
		}
		finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}
	/**
	 * Surveillance dashboards
	 * 4. Count of HEIs turning 6-8 weeks old
	 * @return long value
	 */
	public static Long getHeiSixToEightWeeksOld(){
		String getHeiSixToEightWeeksOldQuery = "SELECT COUNT(e.patient_id)\n" +
				"FROM kenyaemr_etl.etl_hei_enrollment e\n" +
				"         INNER JOIN kenyaemr_etl.etl_patient_demographics d on e.patient_id = d.patient_id\n" +
				"WHERE TIMESTAMPDIFF(WEEK, d.DOB, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) BETWEEN 6 AND 8;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(getHeiSixToEightWeeksOldQuery, true).get(0).get(0);
		}
		finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}
	/**
	 * Surveillance dashboards
	 * 5. Count of HEI 6-8 weeks without DNA PCR results
	 * @return long value
	 */
	public static Long getHeiSixToEightWeeksWithoutPCRResults() {		
		String heiSixToEightWeeksWithoutPCRResultsQuery = "SELECT count(e.patient_id) as hei_without_pcr\n" +
			"FROM kenyaemr_etl.etl_hei_enrollment e\n" +
			"         INNER JOIN kenyaemr_etl.etl_patient_demographics d on e.patient_id = d.patient_id\n" +
			"         LEFT JOIN kenyaemr_etl.etl_hiv_enrollment hiv on e.patient_id = hiv.patient_id\n" +
			"         LEFT JOIN(SELECT x.patient_id week6pcr, x.test_result as week6results\n" +
			"                   FROM kenyaemr_etl.etl_laboratory_extract x\n" +
			"                   WHERE x.lab_test = 1030\n" +
			"                     AND x.order_reason = 1040) t ON e.patient_id = t.week6pcr\n" +
			"WHERE TIMESTAMPDIFF(WEEK, d.DOB, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) BETWEEN 6 AND 8\n" +
			"  AND hiv.patient_id IS NULL\n" +
			"  AND t.week6pcr IS NULL;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(heiSixToEightWeeksWithoutPCRResultsQuery, true).get(0).get(0);

		}
		finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}

	/**
	 * HEIs turning 24 months old
	 * @return long value
	 */
	public static Long getHei24MonthsOld() {
		String hei24MonthsOldQuery = "SELECT COUNT(e.patient_id)\n" +
				"FROM kenyaemr_etl.etl_hei_enrollment e\n" +
				"         INNER JOIN kenyaemr_etl.etl_patient_demographics d ON d.patient_id = e.patient_id\n" +
				"WHERE TIMESTAMPDIFF(MONTH, d.dob, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) = 24;";
		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
			return (Long) Context.getAdministrationService().executeSQL(hei24MonthsOldQuery, true).get(0).get(0);

		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}
	/**
	 * Surveillance dashboards
	 * 6. Count of HEI 24 months without Documented Outcome
	 * @return long value
	 */
	public static Long getHei24MonthsWithoutDocumentedOutcome() {
		String hei24MonthsWithoutDocumentedOutcomeQuery = "SELECT count(e.patient_id) as hei_without_outcome\n" +
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
			"                             INNER JOIN kenyaemr_etl.etl_patient_demographics d on e.patient_id = d.patient_id\n" +
			"                    WHERE visit_date <= CURRENT_DATE) hiv_prog\n" +
			"                   ON e.patient_id = hiv_prog.patient_id\n" +
			"WHERE TIMESTAMPDIFF(MONTH, d.dob, DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)) = 24\n" +
			"  AND has_test.patient_id IS NULL\n" +
			"  AND hiv_prog.patient_id IS NULL;";

		try {
			Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
	 		return (Long) Context.getAdministrationService().executeSQL(hei24MonthsWithoutDocumentedOutcomeQuery, true).get(0).get(0);

		}
		finally {
			Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		}
	}
}
