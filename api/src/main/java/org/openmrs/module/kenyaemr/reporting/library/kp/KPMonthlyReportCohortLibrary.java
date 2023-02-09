/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.kp;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimCohortLibrary;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by dev on 1/14/17.
 */

/**
 * Library of cohort definitions used specifically in the monthly reporting tool.
 */
@Component
//activeFsw
public class KPMonthlyReportCohortLibrary {
	
	@Autowired
	private DatimCohortLibrary datimCohortLibrary;
	
	@Autowired
	private KPMoh731PlusCohortLibrary moh731BCohorts;
	
	static String startOfYear = "0000-10-01";
	
	/**
	 * Returns Kps of a given typology
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kpType(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "where date(c.visit_date) <= date(:endDate)\n"
		        + "group by c.client_id having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = '" + kpType
		        + "';";
		cd.setName("kpType");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpType");
		return cd;
	}
	
	public CohortDefinition contactAll(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select p.client_id from kenyaemr_etl.etl_peer_calendar p inner join kenyaemr_etl.etl_contact c on p.client_id = c.client_id\n"
		        + "where date(p.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type = '"
		        + kpType + "' group by c.client_id;";
		cd.setName("contactAll");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("contactAll");
		
		return cd;
	}
	
	/**
	 * KP_EVER_ENROLLED ever enrolled through KP contact form Not transfer in since they enrolled
	 * where they came from Compositions for KP_EVER_ENROLLED indicator
	 * 
	 * @return
	 */
	public CohortDefinition everEnroll(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "     inner join kenyaemr_etl.etl_patient_demographics d on c.client_id = d.patient_id\n"
		        + " where c.key_population_type = '" + kpType
		        + "' and (c.patient_type is null or c.patient_type !='Transfer in')\n"
		        + "group by c.client_id having max(date(c.visit_date)) <= DATE(:endDate);\n";
		cd.setName("everEnroll");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("everEnroll");
		
		return cd;
	}
	
	public CohortDefinition contactNew(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_patient_demographics d on c.client_id = d.patient_id\n"
		        + "where date(c.visit_date) between date(:startDate) and date(:endDate)  and c.key_population_type = '"
		        + kpType + "'   \n" + "group by c.client_id ;";
		cd.setName("contactNew");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("contactNew");
		
		return cd;
	}
	
	public CohortDefinition contactHCW(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "left join (select v.client_id from kenyaemr_etl.etl_clinical_visit v where v.voided = 0 and v.visit_date between date(:startDate) and date(:endDate) group by v.client_id ) v on c.client_id=v.client_id\n"
		        + "left join (select p.client_id from kenyaemr_etl.etl_peer_calendar p where p.voided = 0 and p.visit_date between date(:startDate) and date(:endDate) group by p.client_id ) p on c.client_id=p.client_id\n"
		        + "where (v.client_id is not null or p.client_id is not null ) and c.voided = 0 and c.key_population_type = '"
		        + kpType + "' group by c.client_id;";
		cd.setName("contactHCW");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("contactHCW");
		
		return cd;
	}
	
	/**
	 * KPs net enrolled: Currently enrolled (including transferred in) and not discontinued due to
	 * death or Transferring out
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition netEnroll(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select b.client_id\n"
		        + "from (select c.client_id                                                     as client_id,\n"
		        + "             max(c.visit_date)                                               as enr_date,\n"
		        + "             mid(max(concat(date(c.visit_date), c.key_population_type)), 11) as kp_type,\n"
		        + "             d.patient_id                                                    as disc_patient,\n"
		        + "             d.disc_date                                                     as disc_date\n"
		        + "      from kenyaemr_etl.etl_contact c\n"
		        + "               left join (select a.patient_id, a.disc_date, a.disc_reason\n"
		        + "                          from (select patient_id,\n"
		        + "                                       max(coalesce(date(transfer_date), date(visit_date)))           as disc_date,\n"
		        + "                                       mid(max(concat(date(visit_date), discontinuation_reason)), 11) as disc_reason\n"
		        + "                                from kenyaemr_etl.etl_patient_program_discontinuation\n"
		        + "                                where date(visit_date) <= date(:endDate)\n"
		        + "                                  and program_name = 'KP'\n"
		        + "                                group by patient_id) a\n"
		        + "                          where a.disc_date <= date(:endDate)\n"
		        + "                            and a.disc_reason in (160034, 159492)) d on c.client_id = d.patient_id\n"
		        + "      group by c.client_id) b\n" + "where b.enr_date <= date(:endDate)\n" + "  and b.kp_type = '"
		        + kpType + "'\n" + "  and (b.disc_patient is null or b.enr_date >= b.disc_date);";
		cd.setName("netEnroll");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("netEnroll");
		
		return cd;
	}
	
	/**
	 * Returns KPs who had a clinical encounter or peer outreach encounter within the reporting
	 * period
	 * @return
	 */
	public CohortDefinition kpPrevBaseCurrentPeriod() {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_contact c\n" + "inner join (select e.client_id\n"
		        + "     from kenyaemr_etl.etl_client_enrollment e\n" + "     where e.visit_date <= date(:endDate)) e\n"
		        + "    on c.client_id = e.client_id\n" + "left join (select v.client_id, v.visit_date\n"
		        + "    from kenyaemr_etl.etl_clinical_visit v) v on c.client_id = v.client_id\n"
		        + "left join (select p.client_id, p.visit_date as first_peer_enc\n"
		        + "    from kenyaemr_etl.etl_peer_calendar p\n" + "    ) p on c.client_id = p.client_id\n"
		        + "where (((v.visit_date between date(:startDate) and date(:endDate))\n"
		        + "or (p.first_peer_enc between date(:startDate) and date(:endDate)) and c.voided = 0))\n"
		        + "group by c.client_id;";
		cd.setName("kpPrevBaseCurrentPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpPrevBaseCurrentPeriod");
		
		return cd;
	}
	
	/**
	 * Returns KPs who had a clinical encounter or peer outreach encounter since beginning of FY
	 * till previous month to reporting period
	 * @return
	 */
	public CohortDefinition kpPrevBasePreviousPeriod() {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_contact c\n" + "inner join (select e.client_id\n"
		        + "from kenyaemr_etl.etl_client_enrollment e\n"
		        + "where e.visit_date <= date(:endDate)) e on c.client_id = e.client_id\n"
		        + "left join (select v.client_id, v.visit_date\n" + "from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.visit_date <= date(:endDate)) v on c.client_id = v.client_id\n"
		        + "left join (select p.client_id, p.visit_date as first_peer_enc\n"
		        + "from kenyaemr_etl.etl_peer_calendar p\n"
		        + "where p.visit_date <= date(:endDate)) p on c.client_id = p.client_id\n"
		        + "where ((v.visit_date between (CASE MONTH(date(:startDate))\n" + "when 11 then\n"
		        + "date_sub(date(:startDate), INTERVAL 1 MONTH)\n"
		        + "when 12 then date_sub(date(:startDate), INTERVAL 2 MONTH)\n" + "when 1 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 2 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 3 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 4 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 5 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 6 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 7 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 8 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 9 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "else null end) and (case MONTH(date(:endDate))\n"
		        + "when 10 then null\n"
		        + "else\n"
		        + "   last_day(date_sub(date(:endDate), INTERVAL 1 MONTH)) end))\n"
		        + "or (p.first_peer_enc between (CASE MONTH(date(:startDate))\n"
		        + "when 11 then\n"
		        + "date_sub(date(:startDate), INTERVAL 1 MONTH)\n"
		        + "when 12 then date_sub(date(:startDate), INTERVAL 2 MONTH)\n"
		        + "when 1 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 2 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 3 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 4 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 5 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 6 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 7 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 8 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "when 9 then replace('"
		        + startOfYear
		        + "', '0000',\n"
		        + "YEAR(date_sub(date(:startDate), INTERVAL 1 YEAR)))\n"
		        + "else null end) and (case MONTH(date(:endDate))\n"
		        + "  when 10 then null\n"
		        + "  else\n"
		        + "      last_day(date_sub(date(:endDate), INTERVAL 1 MONTH)) end)\n"
		        + "and\n"
		        + "c.voided = 0))\n"
		        + "group by c.client_id;";
		cd.setName("kpPrevBasePreviousPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpPrevBasePreviousPeriod");
		
		return cd;
	}
	
	/**
	 * KPs who received services for the 1st time this month within the current FY
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kpPrev(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpPrevBaseCurrentPeriod",
		    ReportUtils.map(kpPrevBaseCurrentPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("kpPrevBasePreviousPeriod",
		    ReportUtils.map(kpPrevBasePreviousPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("kpType", ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("(kpPrevBaseCurrentPeriod AND NOT kpPrevBasePreviousPeriod) AND kpType");
		return cd;
	}
	
	public CohortDefinition kpCurr() {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" +
				"from kenyaemr_etl.etl_contact c\n" +
				"         left join (select p.client_id\n" +
				"                    from kenyaemr_etl.etl_peer_calendar p\n" +
				"                    where p.voided = 0 and p.visit_date <= date(:endDate)\n" +
				"                    group by p.client_id\n" +
				"                    having max(p.visit_date) between date_sub(date(:endDate), INTERVAL 3 MONTH) and date(:endDate)) cp\n" +
				"                   on c.client_id = cp.client_id\n" +
				"         left join (select v.client_id,v.visit_date\n" +
				"                    from kenyaemr_etl.etl_clinical_visit v\n" +
				"                    where v.voided = 0 and v.visit_date <= date(:endDate)\n" +
				"                    group by v.client_id\n" +
				"                    having max(v.visit_date) between date_sub(date(:endDate), INTERVAL 3 MONTH) and date(:endDate)\n" +
				"             ) cv\n" +
				"                   on c.client_id = cv.client_id\n" +
				"         left join (select d.patient_id, max(d.visit_date) latest_visit\n" +
				"                    from kenyaemr_etl.etl_patient_program_discontinuation d\n" +
				"                    where d.program_name = 'KP'\n" +
				"                    group by d.patient_id) d on c.client_id = d.patient_id\n" +
				"where (d.patient_id is null or d.latest_visit > date(:endDate))\n" +
				"  and c.voided = 0\n" +
				"  and (cp.client_id is not null or cv.client_id is not null)\n" +
				"group by c.client_id;";
		cd.setName("kpCurr");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpCurr");
		
		return cd;
	}
	
	public CohortDefinition enrollNew(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_patient_demographics d on c.client_id = d.patient_id\n"
		        + "where c.key_population_type = '"
		        + kpType
		        + "'  and (c.patient_type is null or c.patient_type !='Transfer in')\n"
		        + "group by c.client_id having max(date(c.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("enrollNew");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("enrollNew");
		
		return cd;
	}
	
	/**
	 * Number of KPs ever identified HIV positive in this DICE irrespective of their current status.
	 * This includes those known positive at enrolment into the program.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kpKnownPositiveEnrolled(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpType", ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND knownPositiveKPs");
		return cd;
	}
	
	public CohortDefinition enrollHtsTst(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  inner join kenyaemr_etl.etl_hts_test t on c.client_id = t.patient_id  where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0\n"
		        + "group by c.client_id\n"
		        + "having(max(t.visit_date) between date(:startDate) and date(:endDate) and max(c.visit_date) between date(:startDate) and date(:endDate));";
		cd.setName("enrollHtsTst");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("enrollHtsTst");
		
		return cd;
	}
	
	public CohortDefinition enrollHtsTstPos(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  inner join kenyaemr_etl.etl_hts_test t on c.client_id = t.patient_id where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0\n"
		        + "  group by c.client_id\n"
		        + "having max(t.visit_date) between date(:startDate) and date(:endDate)  and max(c.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "       and mid(max(concat(t.visit_date,t.final_test_result)),11)='Positive';";
		cd.setName("enrollHtsTstPos");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("enrollHtsTstPos");
		
		return cd;
	}
	
	public CohortDefinition assistedSelfTested(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id\n"
		        + "where c.key_population_type = '"
		        + kpType
		        + "' group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.self_test_education)),11)='Yes'\n"
		        + "         and mid(max(concat(v.visit_date,v.self_use_kits)),11) >0 and max(v.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("assistedSelfTested");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("assistedSelfTested");
		
		return cd;
	}
	
	public CohortDefinition unAssistedSelfTested(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id\n"
		        + "where c.key_population_type = '"
		        + kpType
		        + "' group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.self_test_education)),11)='No'\n"
		        + "       and mid(max(concat(v.visit_date,v.self_use_kits)),11) >0 and max(v.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("unAssistedSelfTested");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("unAssistedSelfTested");
		
		return cd;
	}
	
	public CohortDefinition htsTstSelfConfirmedPositive(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id\n"
		        + "where c.key_population_type = '"
		        + kpType
		        + "'\n"
		        + " group by c.client_id\n"
		        + " having mid(max(concat(v.visit_date,v.self_tested)),11)='Y' and  mid(max(concat(v.visit_date,v.test_confirmatory_results)),11) ='Positive'\n"
		        + " and max(v.visit_date) between date(:startDate) and date(:endDate);\n";
		cd.setName("htsTstSelfConfirmedPositive");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("htsTstSelfConfirmedPositive");
		
		return cd;
	}
	
	public CohortDefinition htsTestedNegative(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_contact c\n"
		        + "         inner join kenyaemr_etl.etl_hts_test t on c.client_id = t.patient_id\n"
		        + "    and c.voided = 0\n" + "    and t.visit_date between date(:startDate) and date(:endDate)\n"
		        + "    and t.test_type = 1\n" + "    and t.final_test_result = 'Negative'\n"
		        + "and date(c.visit_date) <= date(:endDate)\n" + "group by c.client_id\n"
		        + "having mid(max(concat(c.visit_date, c.key_population_type)), 11) = '" + kpType + "';";
		cd.setName("htsTestedNegative");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("htsTestedNegative");
		
		return cd;
	}
	
	/**
	 * Known Positive KPS
	 * 
	 * @return
	 */
	public CohortDefinition knownPositiveKPs() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.kp_client from (select c.client_id as kp_client,\n"
		        + "  max(e.kp_enrollment_date) as latest_kp_enrollment,\n"
		        + "  e.client_id           as kp_known_pos, e.kp_enrollment_date as kp_enrollment_date,\n"
		        + "  h.patient_id          as hiv_enrolled, h.hiv_enrollment_date as hiv_enroll_date,\n"
		        + "  t.patient_id          as hts_positive_patient,\n"
		        + "  t.hts_date            as hts_date from kenyaemr_etl.etl_contact c\n"
		        + "    left join (select e.client_id, e.visit_date as kp_enrollment_date\n"
		        + "               from kenyaemr_etl.etl_client_enrollment e\n"
		        + "               where e.visit_date <= date(:endDate)\n"
		        + "                 and e.share_test_results = 'Yes I tested positive') e\n"
		        + "              on c.client_id = e.client_id\n"
		        + "    left join (select h.patient_id, h.visit_date as hiv_enrollment_date\n"
		        + "               from kenyaemr_etl.etl_hiv_enrollment h\n"
		        + "               where h.visit_date <= date(:endDate)) h on c.client_id = h.patient_id\n"
		        + "    left join (select t.patient_id, t.visit_date as hts_date\n"
		        + "               from kenyaemr_etl.etl_hts_test t\n"
		        + "               where t.population_type= 'Key Population'\n"
		        + "                 and t.visit_date <= date(:endDate) and t.test_type = 1\n"
		        + "                 and t.final_test_result = 'Positive') t on c.client_id = t.patient_id\n"
		        + "group by c.client_id having latest_kp_enrollment <= date(:endDate)\n"
		        + "and (kp_known_pos is not null or hiv_enrolled is not null or hts_positive_patient is not null)) a;";
		cd.setName("knownPositiveKPs");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("knownPositiveKPs");
		
		return cd;
	}
	
	/**
	 * KP Living with HIV currentInKP AND livingWithHiv Compositions for KPLHIV_CURR indicator
	 * 
	 * @return
	 */
	public CohortDefinition kpLHIVCurr(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("currentInKP", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("currentInKP AND knownPositiveKPs");
		return cd;
	}
	
	/**
	 * Living with HIV Regardless of ART status Compositions for KPLHIV_CURR indicator
	 * 
	 * @return
	 */
	public CohortDefinition livingWithHiv() {
		String sqlQuery = "select d.patient_id\n"
		        + "from kenyaemr_etl.etl_patient_demographics d\n"
		        + "  left join (select t.patient_id as patient_id,t.final_test_result from kenyaemr_etl.etl_hts_test t where t.final_test_result = 'Positive'\n"
		        + "             and t.test_type = 1 group by t.patient_id) ht on ht.patient_id = d.patient_id\n"
		        + "  left join (select e.patient_id as patient_id from kenyaemr_etl.etl_hiv_enrollment e group by e.patient_id) he on he.patient_id = d.patient_id\n"
		        + " where (ht.patient_id is not null or he.patient_id is not null)\n" + "GROUP BY d.patient_id;\n";
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("LHIV");
		cd.setQuery(sqlQuery);
		cd.setDescription("Living with HIV");
		return cd;
	}
	
	/**
	 * KP TX_CURR currentInKP AND Tx_Curr from Datim Compositions for TX_CURR_DICE indicator
	 * 
	 * @return
	 */
	public CohortDefinition currOnARTKP(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpType", ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("txcurr",
		    ReportUtils.map(datimCohortLibrary.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("knownPositiveKPs AND txcurr and kpType");
		return cd;
	}
	
	/**
	 * KP TX_NEW currentInKP AND Tx_New from Datim Compositions for TX_NEW_DICE indicator
	 * 
	 * @return
	 */
	public CohortDefinition newOnARTKP(String kpType) {
		
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("currentInKP", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("newlyStartedOnART",
		    ReportUtils.map(datimCohortLibrary.startedOnART(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("currentInKP AND newlyStartedOnART");
		return cd;
	}
	
	public CohortDefinition screenedForSTIWithinPeriod() {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(v.visit_date) between date(:startDate) and date(:endDate) and v.sti_screened = 'Y';";
		cd.setName("screenedForSTIWithinPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForSTIWithinPeriod");
		
		return cd;
	}
	
	/**
	 * Clients screened for STI within period
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition screenedForSTI(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpType", ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("screenedForSTIWithinPeriod",
		    ReportUtils.map(screenedForSTIWithinPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND screenedForSTIWithinPeriod");
		return cd;
	}
	
	public CohortDefinition screenedPositiveForSTIWithinPeriod() {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(v.visit_date) between date(:startDate) and date(:endDate) and v.sti_screened = 'Y'\n"
		        + "  and v.sti_results = 'Positive';";
		cd.setName("screenedPositiveForSTIWithinPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedPositiveForSTIWithinPeriod");
		
		return cd;
	}
	
	/**
	 * Clients who screened positive for STI within period
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition screenedPositiveForSTI(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpType", ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("screenedPositiveForSTIWithinPeriod",
		    ReportUtils.map(screenedPositiveForSTIWithinPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND screenedPositiveForSTIWithinPeriod");
		return cd;
	}
	
	public CohortDefinition startedSTITxWithinPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(v.visit_date) between date(:startDate) and date(:endDate) and v.sti_screened = 'Y'\n"
		        + "  and v.sti_results = 'Positive' and v.sti_treated = 'Yes';";
		cd.setName("startedSTITxWithinPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedSTITxWithinPeriod");
		
		return cd;
	}
	
	/**
	 * Clients started STI treatment within period
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition startedSTITx(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpType", ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("startedSTITxWithinPeriod",
		    ReportUtils.map(startedSTITxWithinPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND startedSTITxWithinPeriod");
		return cd;
	}
	
	public CohortDefinition screenedForGbv(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + " left join (select b.patient_id,b.visit_date from kenyaemr_etl.etl_gbv_screening b where b.voided = 0\n"
		        + " group by b.patient_id having max(b.visit_date) between date(:startDate) and date(:endDate)) gb on c.client_id=gb.patient_id\n"
		        + "  left join (select v.client_id,v.visit_date from kenyaemr_etl.etl_clinical_visit v where v.voided = 0 and v.violence_screened = 'Yes'\n"
		        + "  group by v.client_id having max(v.visit_date) between date(:startDate) and date(:endDate)) vg on c.client_id=vg.client_id\n"
		        + "where c.voided = 0 and c.key_population_type = '" + kpType
		        + "' and (gb.patient_id is not null or vg.client_id is not null)\n" + "group by c.client_id;";
		cd.setName("screenedForGbv");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForGbv");
		
		return cd;
	}
	
	//experiencedGbv
	public CohortDefinition experiencedGbv(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + " left join (select b.patient_id,b.visit_date from kenyaemr_etl.etl_gbv_screening b where b.voided = 0\n"
		        + " and b.ipv in (1065,158358,118688,152370,1582) group by b.patient_id having max(b.visit_date) between date(:startDate) and date(:endDate)) gb on c.client_id=gb.patient_id\n"
		        + "  left join (select v.client_id,v.visit_date from kenyaemr_etl.etl_clinical_visit v where v.voided = 0 and v.violence_results in ('Harrasment','Assault','Illegal arrest','Verbal Abuse','Rape/Sexual assault','Discrimination')\n"
		        + "  group by v.client_id having max(v.visit_date) between date(:startDate) and date(:endDate)) vg on c.client_id=vg.client_id\n"
		        + "where c.voided = 0 and c.key_population_type =  '" + kpType
		        + "' and (gb.patient_id is not null or vg.client_id is not null)\n" + "group by c.client_id;";
		cd.setName("experiencedGbv");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencedGbv");
		
		return cd;
	}
	
	//receivedGbvClinicalCare
	public CohortDefinition receivedGbvClinicalCare(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  left join (select a.patient_id,a.visit_date from kenyaemr_etl.etl_gbv_screening_action a where a.voided = 0 and a.action_taken in (165070,165203,160570,1356,165200,1185,165171,127910,165184,165274)\n"
		        + "  group by a.patient_id having max(a.visit_date) between date(:startDate) and date(:endDate)) ac on c.client_id=ac.patient_id\n"
		        + "  left join (select v.client_id,v.visit_date from kenyaemr_etl.etl_clinical_visit v where v.voided = 0 and v.violence_treated = 'Supported'\n"
		        + "  group by v.client_id having max(v.visit_date) between date(:startDate) and date(:endDate)) vg on c.client_id=vg.client_id\n"
		        + "where c.voided = 0 and c.key_population_type = '" + kpType
		        + "' and (ac.patient_id is not null or vg.client_id is not null)\n" + "group by c.client_id;";
		cd.setName("receivedGbvClinicalCare");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedGbvClinicalCare");
		
		return cd;
	}
	
	//receivedGbvLegalSupport
	public CohortDefinition receivedGbvLegalSupport(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  inner join (select a.patient_id,a.visit_date from kenyaemr_etl.etl_gbv_screening_action a where a.voided = 0 and a.action_taken in (130719,135914,165228,165192,5618,165180)\n"
		        + "    group by a.patient_id having max(a.visit_date) between date(:startDate) and date(:endDate)) ac on c.client_id=ac.patient_id\n"
		        + "where c.voided = 0 and c.key_population_type = '" + kpType + "' group by c.client_id;";
		cd.setName("receivedGbvLegalSupport");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedGbvLegalSupport");
		
		return cd;
	}
	
	//Tested Negative for HIV over 3 months ago
	public CohortDefinition testedHIVNegOver3MonthsAgo() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.patient_id from (select t.patient_id,\n"
		        + "       max(t.visit_date)                                       as last_test_date,\n"
		        + "       mid(max(concat(t.visit_date, t.final_test_result)), 11) as last_test_result\n"
		        + "from kenyaemr_etl.etl_hts_test t\n" + "where voided = 0 and t.visit_date < date(:endDate)\n"
		        + "group by t.patient_id\n" + "having timestampdiff(DAY, date(last_test_date),date(:endDate)) > 90\n"
		        + "and last_test_result = 'Negative')a;";
		cd.setName("testedHIVNegOver3MonthsAgo");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedHIVNegOver3MonthsAgo");
		
		return cd;
	}
	
	/**
	 * KPs eligible for HIV retest
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition eligibleForRetest(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpPrev", ReportUtils.map(kpPrev(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("testedHIVNegOver3MonthsAgo",
		    ReportUtils.map(testedHIVNegOver3MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpPrev AND testedHIVNegOver3MonthsAgo");
		return cd;
	}
	
	/**
	 * Tested for HIV within period
	 * 
	 * @return
	 */
	public CohortDefinition testedForHIVWithinPeriod() {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where t.visit_date between date(:startDate) and date(:endDate) and t.final_test_result is not null;";
		cd.setName("testedForHIVWithinPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedForHIVWithinPeriod");
		
		return cd;
	}
	
	/**
	 * Retested for HIV within the reporting period
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition htsTstEligibleRetested(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("eligibleForRetest",
		    ReportUtils.map(eligibleForRetest(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("testedForHIVWithinPeriod",
		    ReportUtils.map(testedForHIVWithinPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("eligibleForRetest AND testedForHIVWithinPeriod");
		return cd;
	}
	
	/**
	 * Tested HIV positive within period
	 * 
	 * @return
	 */
	public CohortDefinition testedHIVPositiveWithinPeriod() {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where t.visit_date between date(:startDate) and date(:endDate) and t.test_type = 1\n"
		        + "and t.final_test_result = 'Positive';";
		cd.setName("testedHIVPositiveWithinPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedHIVPositiveWithinPeriod");
		
		return cd;
	}
	
	/**
	 * Retested HIV positive within period
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition retestedHIVPositive(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("eligibleForRetest",
		    ReportUtils.map(eligibleForRetest(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("testedHIVPositiveWithinPeriod",
		    ReportUtils.map(testedHIVPositiveWithinPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("eligibleForRetest AND testedHIVPositiveWithinPeriod");
		return cd;
	}
	
	//referredAndInitiatedPrEPPepfar
	public CohortDefinition referredAndInitiatedPrEPPepfar(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select p.client_id from kenyaemr_etl.etl_PrEP_verification p group by p.client_id having max(p.visit_date) <= date(:endDate)\n"
		        + "and mid(max(concat(p.visit_date,p.prep_status)),11) !='Discontinue' and mid(max(concat(p.visit_date,p.is_pepfar_site)),11)='Yes' )p on c.client_id = p.client_id\n"
		        + "where c.voided = 0 and c.key_population_type = '" + kpType + "' group by c.client_id;";
		cd.setName("referredAndInitiatedPrEPPepfar");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("referredAndInitiatedPrEPPepfar");
		
		return cd;
	}
	
	//referredAndInitiatedPrEPNonPepfar
	public CohortDefinition referredAndInitiatedPrEPNonPepfar(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select p.client_id from kenyaemr_etl.etl_PrEP_verification p group by p.client_id having max(p.visit_date) <= date(:endDate)\n"
		        + "and mid(max(concat(p.visit_date,p.prep_status)),11) !='Discontinue' and mid(max(concat(p.visit_date,p.is_pepfar_site)),11)='No' )p on c.client_id = p.client_id\n"
		        + "where c.voided = 0 and c.key_population_type = '" + kpType + "' group by c.client_id;";
		cd.setName("referredAndInitiatedPrEPNonPepfar");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("referredAndInitiatedPrEPNonPepfar");
		
		return cd;
	}
	
	//kplhivInitiatedARTOtherPEPFAR
	public CohortDefinition kplhivInitiatedARTOtherPEPFAR(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.client_id from kenyaemr_etl.etl_treatment_verification t group by t.client_id having mid(max(concat(t.visit_date,t.date_initiated_art)),11) between date(:startDate) and date(:endDate)\n"
		        + "          and mid(max(concat(t.visit_date,t.is_pepfar_site)),11)='Yes' )t on c.client_id = t.client_id\n"
		        + "where c.voided = 0 and c.key_population_type = '" + kpType + "' group by c.client_id;";
		cd.setName("kplhivInitiatedARTOtherPEPFAR");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivInitiatedARTOtherPEPFAR");
		
		return cd;
	}
	
	//kplhivInitiatedARTNonPEPFAR
	public CohortDefinition kplhivInitiatedARTNonPEPFAR(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.client_id from kenyaemr_etl.etl_treatment_verification t group by t.client_id having mid(max(concat(t.visit_date,t.date_initiated_art)),11) between date(:startDate) and date(:endDate)\n"
		        + "and mid(max(concat(t.visit_date,t.is_pepfar_site)),11)='No' )t on c.client_id = t.client_id\n"
		        + "where c.voided = 0 and c.key_population_type = '" + kpType + "' group by c.client_id;";
		cd.setName("kplhivInitiatedARTNonPEPFAR");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedNewMsw");
		
		return cd;
	}
	
	//kplhivCurrentOnARTOtherPEPFAR
	public CohortDefinition kplhivCurrentOnARTOtherPEPFAR(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.client_id from kenyaemr_etl.etl_treatment_verification t group by t.client_id having mid(max(concat(t.visit_date,t.date_initiated_art)),11) <= date(:endDate)\n"
		        + "and mid(max(concat(t.visit_date,t.is_pepfar_site)),11)='Yes' )t on c.client_id = t.client_id\n"
		        + "where c.voided = 0 and c.key_population_type = '" + kpType + "' group by c.client_id;";
		cd.setName("kplhivCurrentOnARTOtherPEPFAR");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivCurrentOnARTOtherPEPFAR");
		
		return cd;
	}
	
	//kplhivCurrentOnARTNonPEPFAR
	public CohortDefinition kplhivCurrentOnARTNonPEPFAR(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.client_id from kenyaemr_etl.etl_treatment_verification t group by t.client_id having mid(max(concat(t.visit_date,t.date_initiated_art)),11) <= date(:endDate)\n"
		        + "and mid(max(concat(t.visit_date,t.is_pepfar_site)),11)='No' )t on c.client_id = t.client_id\n"
		        + "where c.voided = 0 and c.key_population_type = '" + kpType + "' group by c.client_id;";
		cd.setName("kplhivCurrentOnARTNonPEPFAR");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivCurrentOnARTNonPEPFAR");
		
		return cd;
	}
	
	/**
	 * Number of KPLHIV receiving ART at the DICE who interupted treatment this month
	 * 
	 * @return
	 */
	public CohortDefinition kplhivLTFURecently(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  join  kenyaemr_etl.etl_hiv_enrollment e on c.client_id = e.patient_id\n"
		        + "  left join (select v.client_id, max(v.visit_date),mid(max(concat(v.visit_date,v.appointment_date)),11) as appointment_date from kenyaemr_etl.etl_clinical_visit v )v\n"
		        + "    on c.client_id = v.client_id\n"
		        + "  left join (select t.client_id, max(t.visit_date) as latest_track, mid(max(concat(t.visit_date,t.status_in_program)),11) as status_in_program from kenyaemr_etl.etl_peer_tracking t) t on c.client_id = t.client_id\n"
		        + "  left join (select d.patient_id, date(max(d.visit_date)) latest_visit from kenyaemr_etl.etl_patient_program_discontinuation d where d.program_name='KP' group by d.patient_id) d on c.client_id = d.patient_id\n"
		        + "where (d.patient_id is null or d.latest_visit > date(:endDate))\n"
		        + "      and (timestampdiff(DAY, date(v.appointment_date), date(date(:endDate) )) >= 30\n"
		        + "      or (t.latest_track between date(:startDate) and date(:endDate) and status_in_program = 'Lost to follow up'))\n"
		        + "      and c.voided = 0 and c.key_population_type = '" + kpType + "' group by c.client_id;";
		cd.setName("kplhivLTFURecently");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivLTFURecently");
		
		return cd;
	}
	
	/**
	 * KPLHIV TX_RTT currentInKP AND RTT Compositions for KPLHIV_RTT indicator
	 * 
	 * @return
	 */
	public CohortDefinition kplhivTXRtt(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("currentInKP", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("txRtt", ReportUtils.map(datimCohortLibrary.txRTT(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("currentInKP AND txRtt");
		return cd;
	}
	
	//TX_PVLS_N
	public CohortDefinition kplhivSuppressedVl(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "kenyaemr_etl.etl_drug_event e on c.client_id = e.patient_id\n"
		        + "inner join(select\n"
		        + "x.patient_id,\n"
		        + "max(x.visit_date) as latest_vl_date,\n"
		        + "mid(max(concat(x.visit_date,if(x.lab_test = 856, x.test_result, if(x.lab_test=1305 and x.test_result = 1302, 'LDL','')))),11) as latest_vl_result\n"
		        + "from kenyaemr_etl.etl_laboratory_extract x\n"
		        + "where x.lab_test in (1305, 856)\n"
		        + "group by x.patient_id\n"
		        + ") vl on c.client_id= vl.patient_id where (vl.latest_vl_result <1000 or vl.latest_vl_result = 'LDL') and vl.latest_vl_date between date_sub(:endDate, interval 1 YEAR) and date(:endDate)\n"
		        + "and c.key_population_type = '" + kpType + "' and c.voided = 0\n" + "group by c.client_id;";
		cd.setName("kplhivSuppressedVl");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivSuppressedVl");
		
		return cd;
	}
	
	//kplhivWithVlResult
	public CohortDefinition kplhivWithVlResult(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "kenyaemr_etl.etl_drug_event e on c.client_id = e.patient_id\n"
		        + "inner join(select\n"
		        + "x.patient_id,\n"
		        + "max(x.visit_date) as latest_vl_date,\n"
		        + "mid(max(concat(x.visit_date,if(x.lab_test = 856, x.test_result, if(x.lab_test=1305 and x.test_result = 1302, 'LDL','')))),11) as latest_vl_result\n"
		        + "from kenyaemr_etl.etl_laboratory_extract x\n"
		        + "where x.lab_test in (1305, 856)\n"
		        + "group by x.patient_id\n"
		        + ") vl on c.client_id= vl.patient_id where (vl.latest_vl_result is not null or vl.latest_vl_result !='') and vl.latest_vl_date between date_sub(:endDate, interval 1 YEAR) and date(:endDate)\n"
		        + "and c.key_population_type = '" + kpType + "' and c.voided = 0\n" + "group by c.client_id;";
		cd.setName("kplhivWithVlResult");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivWithVlResult");
		
		return cd;
	}
	
	//kplhivSuppressedVlArtOtherPEPFARSite
	public CohortDefinition kplhivSuppressedVlArtOtherPEPFARSite(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.client_id from kenyaemr_etl.etl_treatment_verification t group by t.client_id having mid(max(concat(t.visit_date,t.is_pepfar_site)),11)='Yes'\n"
		        + "and mid(max(concat(t.visit_date,t.vl_test_date)),11) between date(:startDate) and date(:endDate) and mid(max(concat(t.visit_date,t.viral_load)),11) <1000 or UPPER(mid(max(concat(t.visit_date,t.viral_load)),11))='LDL') v\n"
		        + "on c.client_id = v.client_id where c.key_population_type = '" + kpType
		        + "' and c.voided = 0 group by c.client_id;";
		cd.setName("kplhivSuppressedVlArtOtherPEPFARSite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivSuppressedVlArtOtherPEPFARSite");
		
		return cd;
	}
	
	//kplhivVlResultArtOtherPEPFARSite
	public CohortDefinition kplhivVlResultArtOtherPEPFARSite(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.client_id from kenyaemr_etl.etl_treatment_verification t group by t.client_id having mid(max(concat(t.visit_date,t.is_pepfar_site)),11)='Yes'\n"
		        + "and mid(max(concat(t.visit_date,t.vl_test_date)),11) between date(:startDate) and date(:endDate) and mid(max(concat(t.visit_date,t.viral_load)),11) is not null or (mid(max(concat(t.visit_date,t.viral_load)),11))!='') v\n"
		        + "on c.client_id = v.client_id where c.key_population_type = '" + kpType
		        + "' and c.voided = 0 group by c.client_id;";
		cd.setName("kplhivVlResultArtOtherPEPFARSite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivVlResultArtOtherPEPFARSite");
		
		return cd;
	}
	
	//kplhivSuppressedVlArtNonPEPFARSite
	public CohortDefinition kplhivSuppressedVlArtNonPEPFARSite(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.client_id from kenyaemr_etl.etl_treatment_verification t group by t.client_id having mid(max(concat(t.visit_date,t.is_pepfar_site)),11)='No'\n"
		        + "and mid(max(concat(t.visit_date,t.vl_test_date)),11) between date(:startDate) and date(:endDate) and mid(max(concat(t.visit_date,t.viral_load)),11) <1000 or UPPER(mid(max(concat(t.visit_date,t.viral_load)),11))='LDL') v\n"
		        + "on c.client_id = v.client_id where c.key_population_type = '" + kpType
		        + "' and c.voided = 0 group by c.client_id;";
		cd.setName("kplhivSuppressedVlArtNonPEPFARSite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivSuppressedVlArtNonPEPFARSite");
		
		return cd;
	}
	
	public CohortDefinition kplhivVlResultArtNonPEPFARSite(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.client_id from kenyaemr_etl.etl_treatment_verification t group by t.client_id having mid(max(concat(t.visit_date,t.is_pepfar_site)),11)='No'\n"
		        + "and mid(max(concat(t.visit_date,t.vl_test_date)),11) between date(:startDate) and date(:endDate) and mid(max(concat(t.visit_date,t.viral_load)),11) is not null or (mid(max(concat(t.visit_date,t.viral_load)),11))!='') v\n"
		        + "on c.client_id = v.client_id where c.key_population_type = '" + kpType
		        + "' and c.voided = 0 group by c.client_id;";
		cd.setName("kplhivVlResultArtNonPEPFARSite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivVlResultArtNonPEPFARSite");
		
		return cd;
	}
	
	public CohortDefinition kpOnMultiMonthART(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c left join\n"
		        + "(select t.client_id, max(t.visit_date) as visit_date from kenyaemr_etl.etl_treatment_verification t where t.on_diff_care = 'Yes' group by t.client_id) t on c.client_id = t.client_id\n"
		        + "left join\n"
		        + "(select fup.patient_id,max(fup.visit_date) as visit_date,mid(max(concat(fup.visit_date,fup.next_appointment_date)),11),timestampdiff(MONTH,max(fup.visit_date),mid(max(concat(fup.visit_date,fup.next_appointment_date)),11))\n"
		        + " from kenyaemr_etl.etl_patient_hiv_followup fup  group by fup.patient_id having timestampdiff(MONTH,max(fup.visit_date),mid(max(concat(fup.visit_date,fup.next_appointment_date)),11)) >1)\n"
		        + "fup on c.client_id = fup.patient_id where ((fup.patient_id is not null and fup.visit_date between date(:startDate) and date(:endDate)) or (t.client_id is not null and t.visit_date between date(:startDate) and date(:endDate))) and c.voided = 0 and c.key_population_type = '"
		        + kpType + "' group by c.client_id;\n";
		cd.setName("kpOnMultiMonthART");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpOnMultiMonthART");
		
		return cd;
	}
	
	//kpEnrolledInARTSupportGroup
	public CohortDefinition kpEnrolledInARTSupportGroup(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id where c.key_population_type = '"
		        + kpType + "'\n" + "    and v.voided = 0 group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.linked_to_psychosocial)),11)= 'Yes'\n"
		        + "       and max(date(v.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("kpEnrolledInARTSupportGroup");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpEnrolledInARTSupportGroup");
		
		return cd;
	}
	
	//offeredPNS
	public CohortDefinition offeredPNS(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  inner join (select c.patient_related_to from kenyaemr_hiv_testing_patient_contact c where c.relationship_type in (163565, 5617, 162221)\n"
		        + " group by c.patient_related_to having max(date(c.date_created)) between date(:startDate) and date(:endDate))pns on c.client_id = pns.patient_related_to\n"
		        + " where c.key_population_type = '" + kpType + "' and c.voided = 0 group by c.client_id;";
		cd.setName("offeredPNS");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("offeredPNS");
		
		return cd;
	}
	
	//acceptedPNS
	public CohortDefinition acceptedPNSSql(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_contact c\n"
		        + "         inner join (select c.patient_related_to\n"
		        + "                     from kenyaemr_hiv_testing_patient_contact c\n"
		        + "                     where c.relationship_type in (163565, 5617, 162221)\n"
		        + "                       and c.voided = 0\n"
		        + "                       and date(c.date_created) between date(:startDate) and date(:endDate)\n"
		        + "                     group by c.patient_related_to) pns\n"
		        + "                    on c.client_id = pns.patient_related_to\n" + "where c.key_population_type = '"
		        + kpType + "';";
		cd.setName("acceptedPNSSql");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("acceptedPNSSql");
		
		return cd;
	}
	
	/**
	 * Number of KPLHIV who accepted to provide details of their sexual partners this month.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition acceptedPNS(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("acceptedPNSSql", ReportUtils.map(acceptedPNSSql(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("(knownPositiveKPs AND acceptedPNSSql");
		return cd;
	}
	
	//elicitedPNS
	public CohortDefinition elicitedPNS(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select p.patient_id from kenyaemr_hiv_testing_patient_contact p\n"
		        + "  inner join kenyaemr_etl.etl_patient_demographics d on  d.patient_id = p.patient_id and d.voided = 0\n"
		        + "  inner join (select c.client_id,c.visit_date from kenyaemr_etl.etl_contact c\n"
		        + "  where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0) cc on cc.client_id = p.patient_related_to\n"
		        + "where  p.relationship_type in (163565, 5617, 162221) and date(p.date_created) between date(:startDate) and date(:endDate)\n"
		        + "group by p.patient_id;";
		cd.setName("elicitedPNS");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("elicitedPNS ");
		
		return cd;
	}
	
	//pnsKnownPositiveAtEntry
	public CohortDefinition pnsKnownPositiveAtEntry(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select p.patient_id from openmrs.kenyaemr_hiv_testing_patient_contact p\n"
		        + "  inner join kenyaemr_etl.etl_patient_demographics d on  d.patient_id = p.patient_id and d.voided = 0\n"
		        + "  inner join (select c.client_id,c.visit_date from kenyaemr_etl.etl_contact c\n"
		        + "  where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0) cc on cc.client_id = p.patient_related_to\n"
		        + "where  p.relationship_type in (163565, 5617, 162221) and p.baseline_hiv_status ='Positive'and date(p.date_created) between date(:startDate) and date(:endDate)\n"
		        + "group by p.patient_id;";
		cd.setName("pnsKnownPositiveAtEntry");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("pnsKnownPositiveAtEntry");
		
		return cd;
	}
	
	public CohortDefinition pnsTestedPositive(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select p.patient_id from openmrs.kenyaemr_hiv_testing_patient_contact p\n"
		        + "  inner join kenyaemr_etl.etl_patient_demographics d on  d.patient_id = p.patient_id and d.voided = 0\n"
		        + "  inner join (select c.client_id,c.visit_date from kenyaemr_etl.etl_contact c\n"
		        + "  where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0) cc on cc.client_id = p.patient_related_to\n"
		        + "  inner join kenyaemr_etl.etl_hts_test t on t.patient_id = p.patient_id\n"
		        + "where  p.relationship_type in (163565, 5617, 162221) and date(p.date_created) between date(:startDate) and date(:endDate)\n"
		        + "group by p.patient_id\n"
		        + "having mid(max(concat(t.visit_date,t.final_test_result)),11)='Positive' and max(t.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("pnsTestedPositive");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("pnsTestedPositive");
		
		return cd;
	}
	
	public CohortDefinition pnsTestedNegative(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select p.patient_id from openmrs.kenyaemr_hiv_testing_patient_contact p\n"
		        + "  inner join kenyaemr_etl.etl_patient_demographics d on  d.patient_id = p.patient_id and d.voided = 0\n"
		        + "  inner join (select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0) cc on cc.client_id = p.patient_related_to\n"
		        + "  inner join kenyaemr_etl.etl_hts_test t on t.patient_id = p.patient_id\n"
		        + "where  p.relationship_type in (163565, 5617, 162221) and date(p.date_created) between date(:startDate) and date(:endDate)\n"
		        + "group by p.patient_id\n"
		        + "having mid(max(concat(t.visit_date,t.final_test_result)),11)='Negative' and max(t.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("pnsTestedNegative");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("pnsTestedNegative");
		
		return cd;
	}
	
	//KP_EVER_POS
	public CohortDefinition kpEverPos(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpType", ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND knownPositiveKPs");
		return cd;
	}
	
	//TX_EVER_DICE
	public CohortDefinition txEverDice(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from  kenyaemr_etl.etl_contact c\n"
		        + "inner join (select e.patient_id,mid(min(concat(e.visit_date,e.date_started)),11) as date_started from kenyaemr_etl.etl_drug_event e where e.voided is null group by e.patient_id)e on c.client_id = e.patient_id\n"
		        + "where e.date_started <=date(:endDate) and c.key_population_type = '" + kpType + "' group by c.client_id;";
		cd.setName("txEverDice");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("txEverDice");
		
		return cd;
	}
	
	//TX_EVER_VERIFY_PEPFAR_SITE
	public CohortDefinition txEverVerifyPEPFAR(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.client_id from kenyaemr_etl.etl_treatment_verification t group by t.client_id having mid(max(concat(t.visit_date,t.date_initiated_art)),11) <= date(:endDate)\n"
		        + "and mid(max(concat(t.visit_date,t.is_pepfar_site)),11)='Yes' )t on c.client_id = t.client_id\n"
		        + "where c.voided = 0 and c.key_population_type = '" + kpType + "' group by c.client_id;";
		cd.setName("txEverVerifyPEPFAR");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("txEverVerifyPEPFAR");
		
		return cd;
	}
	
	//TX_EVER_VERIFY_NON_PEPFAR_SITE
	public CohortDefinition txEverVerifyNonPEPFAR(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.client_id from kenyaemr_etl.etl_treatment_verification t group by t.client_id having mid(max(concat(t.visit_date,t.date_initiated_art)),11) <= date(:endDate)\n"
		        + "and mid(max(concat(t.visit_date,t.is_pepfar_site)),11)='No')t on c.client_id = t.client_id\n"
		        + "where c.voided = 0 and c.key_population_type = '" + kpType + "' group by c.client_id;";
		cd.setName("txEverVerifyNonPEPFAR");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("txEverVerifyNonPEPFAR");
		
		return cd;
	}
	
	//TX_PVLS_ELIGIBLE_DICE
	public CohortDefinition txPvlsEligibleDice(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select client_id\n"
		        + "from\n"
		        + "(\n"
		        + "select client_id, date_started_art, inMCH, latest_mch_date, lastVLDate, lastVLDateWithinPeriod, dob,timestampdiff(YEAR,dob,:endDate) as age, if(lastVL !=null and (lastVL<1000 or lastVL=1302), 'Suppressed', if(lastVL !=null and lastVL>=1000, 'Unsuppressed', null)) lastVLWithinPeriod,lastVL  from\n"
		        + "(select a.client_id,\n"
		        + "a.date_started_art,\n"
		        + "mch.patient_id                                               as inMCH,\n"
		        + "mch.latest_mch_date                                          as latest_mch_date,\n"
		        + "mid(max(concat(l.visit_date, l.test_result)), 11)            as lastVL,\n"
		        + "left(max(concat(l.visit_date, l.test_result)), 10)           as lastVLDateWithinPeriod,\n"
		        + "left(max(concat(l_ever.visit_date, l_ever.test_result)), 10) as lastVLDate,\n"
		        + "a.dob                                                        as dob\n"
		        + "from (select c.client_id, min(date_started) as date_started_art, p.DOB as dob\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "inner join kenyaemr_etl.etl_patient_demographics p\n"
		        + "on c.client_id = p.patient_id and p.voided = 0\n"
		        + "inner join kenyaemr_etl.etl_drug_event d\n"
		        + "on d.patient_id = c.client_id and ifnull(d.voided, 0) = 0 where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0\n"
		        + "group by c.client_id) a\n"
		        + "left join (select mch.patient_id,di.patient_id as disc_patient,max(date(mch.visit_date)) as latest_mch_date,max(date(di.visit_date)) as disc_date,di.program_name from kenyaemr_etl.etl_mch_enrollment mch\n"
		        + "left join kenyaemr_etl.etl_patient_program_discontinuation di on mch.patient_id = di.patient_id\n"
		        + "group by mch.patient_id having ((latest_mch_date > disc_date and di.program_name = 'MCH Mother') or di.patient_id is null) and latest_mch_date between date_sub(:endDate, interval 12 month) and :endDate) mch on mch.patient_id = a.client_id\n"
		        + "left join kenyaemr_etl.etl_laboratory_extract l on l.patient_id = a.client_id and l.lab_test in (856, 1305)\n"
		        + "left join kenyaemr_etl.etl_laboratory_extract l_ever on l_ever.patient_id = a.client_id and l_ever.lab_test in (856, 1305)\n"
		        + "group by a.client_id) o\n"
		        + ") e where\n"
		        + "(\n"
		        + "(e.lastVL is null and  e.inMCH is null)\n"
		        + "or  e.lastVL is null and  e.inMCH is not null and e.latest_mch_date >= e.date_started_art\n"
		        + "or  e.lastVL is not null  and (lastVL < 1000 or lastVL=1302) and (timestampdiff(YEAR,e.dob,:endDate))<25 and  timestampdiff(MONTH,e.lastVLDate, :endDate) >= 6\n"
		        + "or  e.lastVL is not null  and (lastVL < 1000 or lastVL=1302) and (timestampdiff(YEAR,e.dob,:endDate))>25 and  (timestampdiff(MONTH,e.lastVLDate, :endDate) >= 12)\n"
		        + "or  e.lastVL is not null  and (lastVL > 1000 and lastVL!=1302 and timestampdiff(MONTH,e.lastVLDate, :endDate) >= 3)\n"
		        + "or  e.lastVL is not null and (lastVL < 1000 or lastVL=1302) and e.inMCH is not null and  timestampdiff(MONTH,e.lastVLDate, :endDate) >= 6\n"
		        + ");";
		cd.setName("txPvlsEligibleDice");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("txPvlsEligibleDice");
		
		return cd;
	}
	
	//TX_PVLS_ELIGIBLE_DONE_DICE
	public CohortDefinition txPvlsEligibleDoneDice(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select client_id\n"
		        + "from\n"
		        + "(\n"
		        + "select client_id, date_started_art, inMCH, latest_mch_date, lastVLDate, lastVLDateWithinPeriod, dob,timestampdiff(YEAR,dob,:endDate) as age, if(lastVL !=null and (lastVL<1000 or lastVL=1302), 'Suppressed', if(lastVL !=null and lastVL>=1000, 'Unsuppressed', null)) lastVLWithinPeriod,lastVL  from\n"
		        + "(select a.client_id,\n"
		        + "a.date_started_art,\n"
		        + "mch.patient_id                                               as inMCH,\n"
		        + "mch.latest_mch_date                                          as latest_mch_date,\n"
		        + "mid(max(concat(l.visit_date, l.test_result)), 11)            as lastVL,\n"
		        + "left(max(concat(l.visit_date, l.test_result)), 10)           as lastVLDateWithinPeriod,\n"
		        + "left(max(concat(l_ever.visit_date, l_ever.test_result)), 10) as lastVLDate,\n"
		        + "a.dob                                                        as dob\n"
		        + "from (select c.client_id, min(date_started) as date_started_art, p.DOB as dob\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "inner join kenyaemr_etl.etl_patient_demographics p\n"
		        + "on c.client_id = p.patient_id and p.voided = 0\n"
		        + "inner join kenyaemr_etl.etl_drug_event d\n"
		        + "on d.patient_id = c.client_id and ifnull(d.voided, 0) = 0 where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0\n"
		        + "group by c.client_id) a\n"
		        + "left join (select mch.patient_id,di.patient_id as disc_patient,max(date(mch.visit_date)) as latest_mch_date,max(date(di.visit_date)) as disc_date,di.program_name from kenyaemr_etl.etl_mch_enrollment mch\n"
		        + "left join kenyaemr_etl.etl_patient_program_discontinuation di on mch.patient_id = di.patient_id\n"
		        + "group by mch.patient_id having ((latest_mch_date > disc_date and di.program_name = 'MCH Mother') or di.patient_id is null) and latest_mch_date between date_sub(:endDate, interval 12 month) and :endDate) mch on mch.patient_id = a.client_id\n"
		        + "left join kenyaemr_etl.etl_laboratory_extract l on l.patient_id = a.client_id and l.lab_test in (856, 1305)\n"
		        + "left join kenyaemr_etl.etl_laboratory_extract l_ever on l_ever.patient_id = a.client_id and l_ever.lab_test in (856, 1305)\n"
		        + "group by a.client_id) o\n"
		        + "inner join (select l.patient_id from kenyaemr_etl.etl_laboratory_extract l where l.lab_test in (856,1305) and l.visit_date between date_sub(:endDate, interval 12 month) and date(:endDate) group by l.patient_id)l on l.patient_id = o.client_id\n"
		        + ") e\n"
		        + "where\n"
		        + "(\n"
		        + "(e.lastVL is null and  e.inMCH is null)\n"
		        + "or  e.lastVL is null and  e.inMCH is not null and e.latest_mch_date >= e.date_started_art\n"
		        + "or  e.lastVL is not null  and (lastVL < 1000 or lastVL=1302) and (timestampdiff(YEAR,e.dob,:endDate))<25 and  timestampdiff(MONTH,e.lastVLDate, :endDate) >= 6\n"
		        + "or  e.lastVL is not null  and (lastVL < 1000 or lastVL=1302) and (timestampdiff(YEAR,e.dob,:endDate))>25 and  (timestampdiff(MONTH,e.lastVLDate, :endDate) >= 12)\n"
		        + "or  e.lastVL is not null  and (lastVL > 1000 and lastVL!=1302 and timestampdiff(MONTH,e.lastVLDate, :endDate) >= 3)\n"
		        + "or  e.lastVL is not null and (lastVL < 1000 or lastVL=1302) and e.inMCH is not null and  timestampdiff(MONTH,e.lastVLDate, :endDate) >= 6\n"
		        + ");";
		cd.setName("txPvlsEligibleNonDice");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("txPvlsEligibleNonDice");
		
		return cd;
	}
	
	//TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE
	public CohortDefinition txPvlsEligibleVerifyPEPFAR(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select client_id\n"
		        + "from\n"
		        + "(\n"
		        + "select client_id,lastVL,lastVLDate,isPepfarSite, dob,timestampdiff(YEAR,dob,:endDate) as age from\n"
		        + "(select a.client_id,\n"
		        + "mid(max(concat(l.visit_date, l.viral_load)), 11)            as lastVL,\n"
		        + "mid(max(concat(l.visit_date, l.vl_test_date)), 11)          as lastVLDate,\n"
		        + "mid(max(concat(l.visit_date, l.is_pepfar_site)), 11)        as isPepfarSite,\n"
		        + "a.dob                                                       as dob\n"
		        + "from (select c.client_id, p.DOB as dob\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "inner join kenyaemr_etl.etl_patient_demographics p\n"
		        + "on c.client_id = p.patient_id and p.voided = 0\n"
		        + "where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0\n"
		        + "group by c.client_id) a\n"
		        + "inner join kenyaemr_etl.etl_treatment_verification l on l.client_id = a.client_id\n"
		        + "group by a.client_id) o\n"
		        + ") e  where\n"
		        + "(\n"
		        + "e.lastVL is not null  and (e.lastVL < 1000 or e.lastVL=1302) and (timestampdiff(YEAR,e.dob,:endDate))<25 and  timestampdiff(MONTH,e.lastVLDate, :endDate) >= 6\n"
		        + "or  e.lastVL is not null  and (e.lastVL < 1000 or e.lastVL=1302) and (timestampdiff(YEAR,e.dob,:endDate))>25 and  (timestampdiff(MONTH,e.lastVLDate, :endDate) >= 12)\n"
		        + "or  e.lastVL is not null  and (e.lastVL > 1000 and e.lastVL!=1302 and timestampdiff(MONTH,e.lastVLDate, :endDate) >= 3)\n"
		        + ") and e.isPepfarSite = 'Yes';";
		cd.setName("txPvlsEligibleVerifyPEPFAR");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("txPvlsEligibleVerifyPEPFAR");
		
		return cd;
	}
	
	//TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE
	public CohortDefinition txPvlsEligibleVerifyNonPEPFAR(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select client_id\n"
		        + "from\n"
		        + "(\n"
		        + "select client_id,lastVL,lastVLDate,isPepfarSite, dob,timestampdiff(YEAR,dob,:endDate) as age from\n"
		        + "(select a.client_id,\n"
		        + "mid(max(concat(l.visit_date, l.viral_load)), 11)            as lastVL,\n"
		        + "mid(max(concat(l.visit_date, l.vl_test_date)), 11)          as lastVLDate,\n"
		        + "mid(max(concat(l.visit_date, l.is_pepfar_site)), 11)        as isPepfarSite,\n"
		        + "a.dob                                                       as dob\n"
		        + "from (select c.client_id, p.DOB as dob\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "inner join kenyaemr_etl.etl_patient_demographics p\n"
		        + "on c.client_id = p.patient_id and p.voided = 0\n"
		        + "where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0\n"
		        + "group by c.client_id) a\n"
		        + "inner join kenyaemr_etl.etl_treatment_verification l on l.client_id = a.client_id\n"
		        + "group by a.client_id) o\n"
		        + ") e  where\n"
		        + "(\n"
		        + "e.lastVL is not null  and (e.lastVL < 1000 or e.lastVL=1302) and (timestampdiff(YEAR,e.dob,:endDate))<25 and  timestampdiff(MONTH,e.lastVLDate, :endDate) >= 6\n"
		        + "or  e.lastVL is not null  and (e.lastVL < 1000 or e.lastVL=1302) and (timestampdiff(YEAR,e.dob,:endDate))>25 and  (timestampdiff(MONTH,e.lastVLDate, :endDate) >= 12)\n"
		        + "or  e.lastVL is not null  and (e.lastVL > 1000 and e.lastVL!=1302 and timestampdiff(MONTH,e.lastVLDate, :endDate) >= 3)\n"
		        + ") and e.isPepfarSite = 'No';";
		cd.setName("txPvlsEligibleVerifyNonPEPFAR");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("txPvlsEligibleVerifyNonPEPFAR");
		
		return cd;
	}
	
	//TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE
	public CohortDefinition txPvlsEligibleDoneVerifyPEPFAR(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select client_id\n"
		        + "from\n"
		        + "(\n"
		        + "select client_id,lastVL,lastVLDate,isPepfarSite, dob,timestampdiff(YEAR,dob,:endDate) as age from\n"
		        + "(select a.client_id,\n"
		        + "l.lastVL,\n"
		        + "l.lastVLDate,\n"
		        + "l.isPepfarSite,\n"
		        + "a.dob  as dob\n"
		        + "from (select c.client_id, p.DOB as dob\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "inner join kenyaemr_etl.etl_patient_demographics p\n"
		        + "on c.client_id = p.patient_id and p.voided = 0\n"
		        + "where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0\n"
		        + "group by c.client_id) a\n"
		        + "inner join (select l.client_id,mid(max(concat(l.visit_date, l.viral_load)), 11) as lastVL,mid(max(concat(l.visit_date, l.vl_test_date)), 11) as lastVLDate,mid(max(concat(l.visit_date, l.is_pepfar_site)), 11) as isPepfarSite\n"
		        + " from kenyaemr_etl.etl_treatment_verification l group by l.client_id having max(vl_test_date) between date_sub(:endDate, interval 12 month) and date(:endDate) )l on l.client_id = a.client_id\n"
		        + "group by a.client_id) o\n"
		        + ") e  where\n"
		        + "(\n"
		        + "e.lastVL is not null  and (e.lastVL < 1000 or e.lastVL=1302) and (timestampdiff(YEAR,e.dob,:endDate))<25 and  timestampdiff(MONTH,e.lastVLDate, :endDate) >= 6\n"
		        + "or  e.lastVL is not null  and (e.lastVL < 1000 or e.lastVL=1302) and (timestampdiff(YEAR,e.dob,:endDate))>25 and  (timestampdiff(MONTH,e.lastVLDate, :endDate) >= 12)\n"
		        + "or  e.lastVL is not null  and (e.lastVL > 1000 and e.lastVL!=1302 and timestampdiff(MONTH,e.lastVLDate, :endDate) >= 3)\n"
		        + ") and e.isPepfarSite = 'No';";
		cd.setName("txPvlsEligibleDoneVerifyPEPFAR");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("txPvlsEligibleDoneVerifyPEPFAR");
		
		return cd;
	}
	
	//TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE
	public CohortDefinition txPvlsEligibleDoneVerifyNonPEPFAR(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select client_id\n"
		        + "from\n"
		        + "(\n"
		        + "select client_id,lastVL,lastVLDate,isPepfarSite, dob,timestampdiff(YEAR,dob,:endDate) as age from\n"
		        + "(select a.client_id,\n"
		        + "l.lastVL,\n"
		        + "l.lastVLDate,\n"
		        + "l.isPepfarSite,\n"
		        + "a.dob  as dob\n"
		        + "from (select c.client_id, p.DOB as dob\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "inner join kenyaemr_etl.etl_patient_demographics p\n"
		        + "on c.client_id = p.patient_id and p.voided = 0\n"
		        + "where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0\n"
		        + "group by c.client_id) a\n"
		        + "inner join (select l.client_id,mid(max(concat(l.visit_date, l.viral_load)), 11) as lastVL,mid(max(concat(l.visit_date, l.vl_test_date)), 11) as lastVLDate,mid(max(concat(l.visit_date, l.is_pepfar_site)), 11) as isPepfarSite\n"
		        + " from kenyaemr_etl.etl_treatment_verification l group by l.client_id having max(vl_test_date) between date_sub(:endDate, interval 12 month) and date(:endDate) )l on l.client_id = a.client_id\n"
		        + "group by a.client_id) o\n"
		        + ") e  where\n"
		        + "(\n"
		        + "e.lastVL is not null  and (e.lastVL < 1000 or e.lastVL=1302) and (timestampdiff(YEAR,e.dob,:endDate))<25 and  timestampdiff(MONTH,e.lastVLDate, :endDate) >= 6\n"
		        + "or  e.lastVL is not null  and (e.lastVL < 1000 or e.lastVL=1302) and (timestampdiff(YEAR,e.dob,:endDate))>25 and  (timestampdiff(MONTH,e.lastVLDate, :endDate) >= 12)\n"
		        + "or  e.lastVL is not null  and (e.lastVL > 1000 and e.lastVL!=1302 and timestampdiff(MONTH,e.lastVLDate, :endDate) >= 3)\n"
		        + ") and e.isPepfarSite = 'No';";
		cd.setName("txPvlsEligibleDoneVerifyNonPEPFAR");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("txPvlsEligibleDoneVerifyNonPEPFAR");
		
		return cd;
	}
	
	/*
	*Number KPs Screened for PrEP this month
	 */
	public CohortDefinition kpPrepScreenedDuringVisit(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_clinical_visit v\n"
		        + "         inner join kenyaemr_etl.etl_contact c on c.client_id = v.client_id\n"
		        + "where v.prep_screened = 'Yes'\n" + "  and c.voided = 0\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and date(c.visit_date) <= date(:endDate)\n" + "group by c.client_id\n"
		        + "having mid(max(concat(c.visit_date, c.key_population_type)), 11) = '" + kpType + "';";
		cd.setName("kpPrepScreenedDuringVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpPrepScreenedDuringVisit");
		
		return cd;
	}
	
	/**
	 * Number of HIV negative KPs Screened for PrEP this month
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kpPrepScreened(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpPrepScreenedDuringVisit",
		    ReportUtils.map(kpPrepScreenedDuringVisit(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("htsTestedNegative",
		    ReportUtils.map(htsTestedNegative(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpPrepScreenedDuringVisit AND htsTestedNegative");
		return cd;
	}
	
	/*
	* Number of KPs eligible for PrEP among those screened during visit
	*/
	public CohortDefinition kpPrepEligibleDuringVisit(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_clinical_visit v\n"
		        + "         inner join kenyaemr_etl.etl_contact c on c.client_id = v.client_id\n"
		        + "where v.prep_results = 'Eligible'\n" + "  and c.voided = 0\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and date(c.visit_date) <= date(:endDate)\n" + "group by c.client_id\n"
		        + "having mid(max(concat(c.visit_date, c.key_population_type)), 11) = '" + kpType + "';";
		cd.setName("kpPrepEligible");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpPrepEligible");
		
		return cd;
	}
	
	/**
	 * Number of HIV negative KPs eligible for PrEP
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kpPrepEligible(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpPrepEligibleDuringVisit",
		    ReportUtils.map(kpPrepEligibleDuringVisit(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("htsTestedNegative",
		    ReportUtils.map(htsTestedNegative(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpPrepEligibleDuringVisit AND htsTestedNegative");
		return cd;
	}
	
	/*
	*Number of KPs started on PrEP this month in this DICE
	 */
	public CohortDefinition kpPrepNewDiceDuringVisit(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_prep_enrolment e\n"
		        + "  inner join kenyaemr_etl.etl_contact c on c.client_id=e.patient_id\n"
		        + "where e.patient_type = 'New Patient' and c.voided = 0 and c.key_population_type = '" + kpType + "'\n"
		        + "      and date(e.visit_date) between date(:startDate) and date(:endDate)\n" + "group by c.client_id;";
		cd.setName("kpPrepNewDiceDuringVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpPrepNewDiceDuringVisit");
		
		return cd;
	}
	
	/**
	 * HIV negative KPs started on PrEP within period
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kpPrepNewDice(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpPrepNewDiceDuringVisit",
		    ReportUtils.map(kpPrepNewDiceDuringVisit(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("htsTestedNegative",
		    ReportUtils.map(htsTestedNegative(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpPrepNewDiceDuringVisit AND htsTestedNegative");
		return cd;
	}
	
	/**
	 * KP PrEP_CURR_DICE currentInKP AND prepCT from Datim Compositions for PrEP_CURR_DICE indicator
	 * Includes newly started on PrEP
	 * 
	 * @return
	 */
	public CohortDefinition kpPrepCurrDice(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("currentInKP", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("prepCT", ReportUtils.map(datimCohortLibrary.prepCT(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("kpPrepNewDice", ReportUtils.map(kpPrepNewDice(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("currentInKP AND (prepCT or kpPrepNewDice)");
		return cd;
	}
	
	/*
	 *KP Number ever put on MAT
	 */
	public CohortDefinition kpEverOnMat(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id where c.key_population_type = '"
		        + kpType + "'\n"
		        + "    and v.voided = 0 and v.mat_treated = 'Yes' and  date(v.visit_date) <= date(:endDate)\n"
		        + "group by c.client_id;";
		cd.setName("kpEverOnMat");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpEverOnMat");
		
		return cd;
	}
	
	/*
	 *KP Number prepared or screened for MAT
	 */
	public CohortDefinition kpMatPrepared(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id where c.key_population_type = '"
		        + kpType
		        + "'\n"
		        + "   and v.voided = 0 group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.mat_screened)),11)= 'Yes' and max(date(v.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("kpMatPrepared");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpMatPrepared");
		
		return cd;
	}
	
	/*
	 *KP Number eligible for MAT
	 */
	public CohortDefinition kpMatEligible(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id where c.key_population_type = '"
		        + kpType
		        + "'\n"
		        + "   and v.voided = 0 group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.mat_results)),11)= 'Positive' and max(date(v.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("kpMatEligible");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpMatEligible");
		
		return cd;
	}
	
	/*
	 *KP New MAT
	 */
	public CohortDefinition kpMatNew(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id where c.key_population_type = '"
		        + kpType
		        + "'\n"
		        + "   and v.voided = 0 group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.mat_treated)),11)= 'Yes' and max(date(v.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("kpMatNew");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpMatNew");
		
		return cd;
	}
}
