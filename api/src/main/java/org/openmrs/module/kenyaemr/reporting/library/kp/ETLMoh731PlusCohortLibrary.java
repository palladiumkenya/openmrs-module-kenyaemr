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
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH731Greencard.ETLMoh731GreenCardCohortLibrary;
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
 * Library of cohort definitions used specifically in the MOH731 report based on ETL tables. It has
 * incorporated green card components
 */
@Component
public class ETLMoh731PlusCohortLibrary {
	
	@Autowired
	private ETLMoh731GreenCardCohortLibrary moh731Cohorts;
	
	@Autowired
	private DatimCohortLibrary datimCohorts;
	/**
	 * Returns clients who belongs to a certain kp type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kpType(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		
		if (kpType.equals("TRANSMAN")) {
			sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
			        + "inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = c.client_id\n"
			        + "         where d.gender = 'F' and date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = :location\n"
			        + "         group by c.client_id having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'Transgender';";
		} else if (kpType.equals("TRANSWOMAN")) {
			sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
			        + "inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = c.client_id\n"
			        + "         where d.gender = 'M' and date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = :location\n"
			        + "         group by c.client_id having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'Transgender';";
		} else
			sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
			        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = :location\n"
			        + "group by c.client_id having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = '"
			        + kpType + "';";
		
		cd.setName("kpType");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("kpType");
		return cd;
	}
	
	/**
	 * KPs who received atleast 1 service within the last 3 months from the effective date either
	 * through clinical service encounter or peer educator/calendar
	 * 
	 * @return
	 */
	public CohortDefinition kpCurr() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "         left join (select p.client_id, max(date(visit_date)) as latest_peer_date\n"
		        + "                           from kenyaemr_etl.etl_peer_calendar p\n"
		        + "                           where p.voided = 0 and date(p.visit_date) <= date(:endDate)\n"
		        + "                           group by p.client_id\n"
		        + "                           having max(date(p.visit_date)) between DATE_SUB(date(date_sub(date(:endDate), interval 3 MONTH)), INTERVAL - 1 DAY) and date(:endDate)) p\n"
		        + "               on c.client_id = p.client_id\n"
		        + "                 left join (select v.client_id, max(visit_date) as latest_visit_date\n"
		        + "                            from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                            where v.voided = 0 and date(v.visit_date) <= date(:endDate)\n"
		        + "                            group by v.client_id\n"
		        + "                            having max(date(v.visit_date)) between DATE_SUB(date(date_sub(date(:endDate), interval 3 MONTH)), INTERVAL - 1 DAY) and date(:endDate)) v\n"
		        + "               on c.client_id = v.client_id\n"
		        + "         left join (select d.patient_id, max(date(d.visit_date)) latest_disc_date\n"
		        + "                    from kenyaemr_etl.etl_patient_program_discontinuation d\n"
		        + "                    where d.program_name = 'KP') d on c.client_id = d.patient_id\n"
		        + "where (d.patient_id is null or p.latest_peer_date > d.latest_disc_date or v.latest_visit_date > d.latest_disc_date) and c.voided = 0\n"
		        + "            and (p.client_id is not null or v.client_id is not null) group by c.client_id;";
		cd.setName("kpCurr");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpCurr");
		
		return cd;
	}
	
	/**
	 * FSW active in KP (Received atleast one service in the past 3 months going up to effective
	 * date)
	 * 
	 * @return
	 */
	public CohortDefinition activeKPs(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("kpCurr AND kpType");
		return cd;
	}
	
	/**
	 * KPs tested For HIV
	 * 
	 * @return
	 */
	public CohortDefinition htsAllNumberTestedKeyPopulation() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id from kenyaemr_etl.etl_hts_test where test_type = 1\n"
		        + "  and population_type = 'Key Population'\n"
		        + "  and visit_date between date(:startDate) and date(:endDate);";
		cd.setName("htsAllNumberTestedKeyPopulation");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("KPs tested For HIV");
		
		return cd;
	}
	
	/**
	 * Tested for HIV during the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition hivTestedKPs(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("htsAllNumberTestedKeyPopulation",
		    ReportUtils.map(htsAllNumberTestedKeyPopulation(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND htsAllNumberTestedKeyPopulation");
		return cd;
	}
	
	/**
	 * KPs tested For HIV at the facility/dice
	 * 
	 * @return
	 */
	public CohortDefinition htsNumberTestedAtFacility() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id from kenyaemr_etl.etl_hts_test where test_type = 1\n"
		        + "  and population_type = 'Key Population' and setting = 'Facility'\n"
		        + "  and visit_date between date(:startDate) and date(:endDate);";
		cd.setName("htsNumberTestedAtFacility");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("KPs tested For HIV at the facility");
		
		return cd;
	}
	
	/**
	 * KPs tested at the facility/dice by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition htsNumberTestedAtFacilityKPs(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("htsNumberTestedAtFacility",
		    ReportUtils.map(htsNumberTestedAtFacility(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND htsNumberTestedAtFacility");
		return cd;
	}
	
	/**
	 * KPs tested For HIV at the community level
	 * 
	 * @return
	 */
	public CohortDefinition htsNumberTestedAtCommunity() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id from kenyaemr_etl.etl_hts_test where test_type = 1\n"
		        + "  and population_type = 'Key Population' and setting = 'Community'\n"
		        + "  and date(visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("htsNumberTestedAtCommunity");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("KPs tested For HIV at the community level");
		
		return cd;
	}
	
	/**
	 * KPs Tested at community level by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition htsNumberTestedAtCommunityKPs(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("htsNumberTestedAtCommunity",
		    ReportUtils.map(htsNumberTestedAtCommunity(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND htsNumberTestedAtCommunity");
		
		return cd;
	}
	
	/**
	 * Newly Tested for HIV
	 * 
	 * @return
	 */
	public CohortDefinition newlyTestedForHIV() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "SELECT patient_id FROM kenyaemr_etl.etl_hts_test t WHERE test_type = 1\n"
		        + "GROUP BY patient_id having min(visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("newlyTestedForHIV");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("newlyTestedForHIV");
		
		return cd;
	}
	
	/**
	 * FSWs newly tested for HIV
	 * 
	 * @return
	 */
	public CohortDefinition kpsNewlyTestedForHIV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("newlyTestedForHIV", ReportUtils.map(newlyTestedForHIV(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND newlyTestedForHIV");
		
		return cd;
	}
	
	/**
	 * Returns clients with repeat HTS tests within reporting period. For a KP, a test is counted as
	 * repeat if the client reports that they have ever been tested before
	 * 
	 * @return
	 */
	public CohortDefinition repeatHTSTestWithinReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id\n" + "from kenyaemr_etl.etl_hts_test t\n"
		        + "where date(t.visit_date) <= date(:endDate)\n" + "group by t.patient_id\n"
		        + "having max(t.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "   and mid(max(concat(date(t.visit_date), t.test_type)), 11) = 1\n"
		        + "   and mid(max(concat(date(t.visit_date), t.population_type)), 11) = 'Key Population'\n"
		        + "   and min(t.visit_date) < max(t.visit_date) and\n"
		        + "mid(min(concat(date(t.visit_date),t.test_type)),11) = 1;";
		cd.setName("repeatHTSTestWithinReportingPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("repeatHTSTestWithinReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Clients with reported repeat HIV test during clinical visit within the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition reportedRepeatHIVTest() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v where v.hiv_tested = 'Yes' and v.test_frequency = 'Repeat'\n"
		        + "and date(v.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("reportedRepeatHIVTest");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("reportedRepeatHIVTest");
		
		return cd;
	}
	
	/**
	 * Repeat HIV tests within reporting period by KP type
	 * 
	 * @return
	 */
	public CohortDefinition testedRepeat(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("repeatHTSTestWithinReportingPeriod",
		    ReportUtils.map(repeatHTSTestWithinReportingPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("reportedRepeatHIVTest",
		    ReportUtils.map(reportedRepeatHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (reportedRepeatHIVTest OR repeatHTSTestWithinReportingPeriod)");
		return cd;
	}
	
	/**
	 * KP Client who reported self HIV test within the reporting period in HTS
	 * 
	 * @return
	 */
	public CohortDefinition selfHIVTestInHTS() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id from kenyaemr_etl.etl_hts_test\n"
		        + "where patient_had_hiv_self_test = 'Yes' and population_type = 'Key Population'\n"
		        + "  and visit_date between date(:startDate) and date(:endDate);";
		cd.setName("selfHIVTest");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("selfHIVTest");
		
		return cd;
	}
	
	/**
	 * KP Client who reported self HIV test within the reporting period in clinical visit
	 * 
	 * @return
	 */
	public CohortDefinition selfHIVTestClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.self_tested = 'Y' and v.self_test_date between date(:startDate) and date(:endDate);";
		cd.setName("selfHIVTestClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("selfHIVTestClinicalVisit");
		
		return cd;
	}
	
	/**
	 * Self HIV tests for within reporting period by KP type
	 * 
	 * @return
	 */
	public CohortDefinition selfTestedForHIV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("selfHIVTestClinicalVisit",
		    ReportUtils.map(selfHIVTestClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND selfHIVTestClinicalVisit");
		
		return cd;
	}
	
	/**
	 * Known Positive KPs
	 * 
	 * @return
	 */
	public CohortDefinition knownPositiveKPs() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.kp_client\n" + "from (select c.client_id           as kp_client,\n"
		        + "             max(c.visit_date)     as latest_kp_enrollment,\n"
		        + "             e.client_id           as kp_known_pos,\n"
		        + "             e.kp_enrollment_date  as kp_enrollment_date,\n"
		        + "             h.patient_id          as hiv_enrolled,\n"
		        + "             h.hiv_enrollment_date as hiv_enroll_date,\n"
		        + "             t.patient_id          as hts_positive_patient,\n"
		        + "             t.hts_date            as hts_date\n" + "      from kenyaemr_etl.etl_contact c\n"
		        + "               left join (select e.client_id, e.visit_date as kp_enrollment_date\n"
		        + "                          from kenyaemr_etl.etl_client_enrollment e\n"
		        + "                          where e.visit_date <= date(:endDate)\n"
		        + "                            and e.share_test_results = 'Yes I tested positive') e\n"
		        + "                         on c.client_id = e.client_id\n"
		        + "               left join (select h.patient_id, h.visit_date as hiv_enrollment_date\n"
		        + "                          from kenyaemr_etl.etl_hiv_enrollment h\n"
		        + "                          where h.visit_date <= date(:endDate)) h on c.client_id = h.patient_id\n"
		        + "               left join (select t.patient_id, t.visit_date as hts_date\n"
		        + "                          from kenyaemr_etl.etl_hts_test t\n"
		        + "                          where t.population_type= 'Key Population'\n"
		        + "                            and t.visit_date <= date(:endDate)\n"
		        + "                            and t.test_type = 1\n"
		        + "                            and t.final_test_result = 'Positive') t on c.client_id = t.patient_id\n"
		        + "      group by c.client_id\n" + "      having latest_kp_enrollment <= date(:endDate)\n"
		        + "         and (kp_known_pos is not null or hiv_enrolled is not null\n"
		        + "          or hts_positive_patient is not null)) a;";
		cd.setName("knownPositiveKPs");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("knownPositiveKPs");
		
		return cd;
	}
	
	/**
	 * KP_CURR FSWs Living with HIV
	 * 
	 * @return
	 */
	public CohortDefinition knownPositive(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND kpType AND knownPositiveKPs");
		
		return cd;
	}
	
	/**
	 * KPs who received HTS and received a positive result
	 * 
	 * @return
	 */
	public CohortDefinition receivedPositiveHTSResults() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where t.test_type = 2\n"
		        + "  and t.population_type = 'Key Population' and t.patient_given_result = 'Yes'\n"
		        + "  and t.final_test_result = 'Positive'\n"
		        + "  and date(t.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("receivedPositiveHTSResults");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivedPositiveHTSResults");
		
		return cd;
	}
	
	/**
	 * KPs who tested Positive within the reporting period and were given results
	 * 
	 * @return
	 */
	public CohortDefinition receivedPositiveHIVResults(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("receivedPositiveHTSResults",
		    ReportUtils.map(receivedPositiveHTSResults(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivedPositiveHTSResults");
		
		return cd;
	}
	
	/**
	 * Tested for HIV 3 months ago, before effective reporting date
	 * 
	 * @return
	 */
	public CohortDefinition testedHIVPositive3MonthsAgo() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where t.test_type = 2\n"
		        + "  and t.population_type = 'Key Population' and t.final_test_result = 'Positive'\n"
		        + "  and date(t.visit_date) between date_sub(date(:startDate), interval 3 MONTH)\n"
		        + "    and last_day(date_sub(:endDate, interval 3 MONTH));";
		cd.setName("testedHIVPositive3MonthsAgo");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("testedHIVPositive3MonthsAgo");
		
		return cd;
	}
	
	/**
	 * Linked in HTS
	 * 
	 * @return
	 */
	public CohortDefinition linkedHTS() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select r.patient_id from kenyaemr_etl.etl_hts_referral_and_linkage r\n"
		        + "where (r.ccc_number != '' or r.ccc_number IS NOT NULL)\n"
		        + "  and (r.facility_linked_to != '' or r.facility_linked_to IS NOT NULL)\n"
		        + "  and date(r.visit_date) between date_sub(date(:startDate), interval 3 MONTH) and date(:endDate);";
		cd.setName("linkedHTS");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("linkedHTS");
		
		return cd;
	}
	
	/**
	 * Reported Linked in clinical visit
	 * 
	 * @return
	 */
	public CohortDefinition linkedClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null or\n"
		        + "       v.self_test_link_facility is not null)\n"
		        + "  and v.visit_date between date_sub(date(:startDate), interval 3 MONTH) and date(:endDate)";
		cd.setName("linkedClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("linkedClinicalVisit");
		
		return cd;
	}
	
	/**
	 * Enrolled in HIV program
	 * 
	 * @return
	 */
	public CohortDefinition enrolledInHIVProgram() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.patient_id from kenyaemr_etl.etl_hiv_enrollment e \n"
		        + "where e.visit_date between date_sub(date(:startDate), interval 3 MONTH) and date(:endDate);";
		cd.setName("enrolledInHIVProgram");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("enrolledInHIVProgram");
		
		return cd;
	}
	
	/**
	 * Linked to care/treatment
	 * 
	 * @return
	 */
	public CohortDefinition linked(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("testedHIVPositive3MonthsAgo",
		    ReportUtils.map(testedHIVPositive3MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedHTS", ReportUtils.map(linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedClinicalVisit",
		    ReportUtils.map(linkedClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("enrolledInHIVProgram",
		    ReportUtils.map(enrolledInHIVProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND testedHIVPositive3MonthsAgo AND (linkedHTS OR linkedClinicalVisit OR enrolledInHIVProgram)");
		
		return cd;
	}
	
	/**
	 * Returns clients who tested HIV+ 3 months ago
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition testedHIVPositiveMonthsAgo(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("testedHIVPositive3MonthsAgo",
		    ReportUtils.map(testedHIVPositive3MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND testedHIVPositive3MonthsAgo");
		
		return cd;
	}
	
	/**
	 * Received condoms from clinical visit
	 * 
	 * @return
	 */
	public CohortDefinition receivingCondomsFromClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id\n"
		        + "having (coalesce(sum(v.female_condoms_no), 0) + coalesce(sum(v.male_condoms_no), 0)) > 0;";
		cd.setName("receivingCondomsFromClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingCondomsFromClinicalVisit");
		
		return cd;
	}
	
	/**
	 * Received condoms from sti treatment
	 * 
	 * @return
	 */
	public CohortDefinition receivingCondomsFromSTITreatment() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select s.client_id from kenyaemr_etl.etl_sti_treatment s\n"
		        + "where s.visit_date between date(:startDate) and date(:endDate) group by s.client_id\n"
		        + "having coalesce(sum(s.no_of_condoms), 0) > 0;";
		cd.setName("receivingCondomsFromSTITreatment");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingCondomsFromSTITreatment");
		
		return cd;
	}
	
	/**
	 * Received condoms from peer outreach
	 * 
	 * @return
	 */
	public CohortDefinition receivingCondomsFromPeerOutreach() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select p.client_id from kenyaemr_etl.etl_peer_calendar p\n"
		        + "where date(p.visit_date) between date(:startDate) and date(:endDate) group by p.client_id\n"
		        + "  having (coalesce(sum(p.monthly_male_condoms_distributed), 0) +\n"
		        + "       coalesce(sum(p.monthly_female_condoms_distributed), 0)) > 0;";
		cd.setName("receivingCondomsFromPeerOutreach");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingCondomsFromPeerOutreach");
		
		return cd;
	}
	
	/**
	 * KPs received Condoms within the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition receivingCondoms(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("receivingCondomsFromClinicalVisit",
		    ReportUtils.map(receivingCondomsFromClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromPeerOutreach",
		    ReportUtils.map(receivingCondomsFromPeerOutreach(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromSTITreatment",
		    ReportUtils.map(receivingCondomsFromSTITreatment(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (receivingCondomsFromClinicalVisit OR receivingCondomsFromPeerOutreach OR receivingCondomsFromSTITreatment)");
		
		return cd;
	}
	
	/**
	 * Received condoms per need within the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition receivingCondomsPerNeedPerNeedSql() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id\n"
		        + "from (select c.client_id,\n"
		        + "             (coalesce(sum(v.female_condoms_no), 0) + coalesce(sum(v.male_condoms_no), 0) +\n"
		        + "              coalesce(sum(s.no_of_condoms), 0) + coalesce(p.monthly_male_condoms_distributed, 0) +\n"
		        + "              coalesce(p.monthly_male_condoms_distributed, 0))\n"
		        + "                                                                                                       as condoms_distributed,\n"
		        + "             greatest(coalesce(p.monthly_condoms_required, 0),\n"
		        + "                      coalesce(c.avg_weekly_sex_acts * 4, 0))                                          as monthly_condom_requirement\n"
		        + "      from kenyaemr_etl.etl_contact c\n"
		        + "               left outer join\n"
		        + "           kenyaemr_etl.etl_peer_calendar p\n"
		        + "           on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "               left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and\n"
		        + "                                                                    date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "               left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and\n"
		        + "                                                                   date(s.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "      group by c.client_id       having condoms_distributed >= monthly_condom_requirement\n"
		        + "         and condoms_distributed > 0) k;";
		cd.setName("receivingCondomsPerNeedPerNeed");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingCondomsPerNeedPerNeed");
		
		return cd;
	}
	
	/**
	 * FSWs who received condoms per need
	 * 
	 * @return
	 */
	public CohortDefinition receivingCondomsPerNeedPerNeed(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("receivingCondomsPerNeedPerNeedSql",
		    ReportUtils.map(receivingCondomsPerNeedPerNeedSql(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivingCondomsPerNeedPerNeedSql");
		
		return cd;
	}
	
	/**
	 * Number receiving needles & syringes
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition receivingNeedlesAndSyringes(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0) + p.monthly_n_and_s_distributed) as needles_and_syringes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             where c.key_population_type = '"
		        + kpType
		        + "' and c.implementation_subcounty =  :location group by c.client_id having needles_and_syringes_given >=1 )k;";
		cd.setName("receivingNeedlesAndSyringes");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingNeedlesAndSyringes");
		
		return cd;
	}
	
	/**
	 * Number receiving needles & syringes per need
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition receivingNeedlesAndSyringesPerNeed(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0)+coalesce(p.monthly_n_and_s_distributed,0))\n"
		        + "                                                                                                                     as needles_syringes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_syringes_required,0),coalesce(c.avg_daily_drug_injections*28,0)) as monthly_needles_syringes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   where c.key_population_type = '"
		        + kpType
		        + "'\n"
		        + "and c.implementation_subcounty =  :location group by c.client_id\n"
		        + "                         having needles_syringes_distributed>=monthly_needles_syringes_requirements and needles_syringes_distributed !=0 ) k;";
		cd.setName("receivingNeedlesAndSyringesPerNeed");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingNeedlesAndSyringesPerNeed ");
		
		return cd;
	}
	
	/**
	 * Number receiving lubricants
	 * 
	 * @return
	 */
	public CohortDefinition receivingLubricants(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0) + p.monthly_lubes_distributed + t.no_of_lubes) as lubes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                                                                                                                                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = '"
		        + kpType
		        + "' and c.implementation_subcounty =  :location group by c.client_id\n"
		        + "                         having lubes_given >=1 )k;";
		cd.setName("receivingLubricants");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingLubricants");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsPerNeed(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0)+coalesce(p.monthly_lubes_distributed,0))\n"
		        + "                                                                                                                             as lubes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_lubes_required,0),coalesce(c.avg_weekly_anal_sex_acts*4,0)) as monthly_lubes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = '"
		        + kpType
		        + "'\n"
		        + "  and c.implementation_subcounty =  :location group by c.client_id\n"
		        + "                         having lubes_distributed >=monthly_lubes_requirements and lubes_distributed !=0 ) k;";
		cd.setName("receivingLubricantsPerNeed");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingLubricantsPerNeed");
		
		return cd;
	}
	
	/**
	 * Number receiving self-test kits
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition receivingSelfTestKits(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id\n"
		        + "from (select c.client_id, (coalesce(sum(v.self_test_kits_given), 0) + IFNULL(p.monthly_self_test_kits_distributed,0)) as self_test_kits_given\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "       left outer join\n"
		        + "   kenyaemr_etl.etl_peer_calendar p\n"
		        + "   on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "       left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and\n"
		        + "                                                            date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "       left outer join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id and\n"
		        + "                                                           date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "where c.key_population_type = '" + kpType
		        + "' and c.implementation_subcounty =  :location group by c.client_id\n"
		        + "having self_test_kits_given >= 1) k;\n";
		cd.setName("receivingSelfTestKits");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingSelfTestKits");
		
		return cd;
	}
	
	/**
	 * Returns number of clients screened for STI
	 * 
	 * @return
	 */
	public CohortDefinition numberScreenedForSTISQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v where v.sti_screened = 'Y' and v.visit_date\n"
		        + "      between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("numberScreenedForSTISQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("numberScreenedForSTISQL");
		
		return cd;
	}
	
	/**
	 * Number screened_STI
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition screenedForSTI(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("numberScreenedForSTISQL",
		    ReportUtils.map(numberScreenedForSTISQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND numberScreenedForSTISQL");
		
		return cd;
	}
	
	/**
	 * Returns number of clients diagnosed with STI
	 * 
	 * @return
	 */
	public CohortDefinition diagnosedWithSTISQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.sti_screened = 'Y' and v.sti_results = 'Positive'\n"
		        + "and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedWithSTISQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedWithSTISQL");
		
		return cd;
	}
	
	/**
	 * Number Diagnosed with STI
	 * 
	 * @return
	 */
	public CohortDefinition diagnosedWithSTI(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("diagnosedWithSTISQL",
		    ReportUtils.map(diagnosedWithSTISQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND diagnosedWithSTISQL");
		
		return cd;
	}
	
	/**
	 * Returns number of clients treated for STI
	 * 
	 * @return
	 */
	public CohortDefinition treatedForSTISQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.sti_screened = 'Y' and v.sti_results = 'Positive'   and sti_treated = 'Yes'\n"
		        + "  and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "  and v.voided = 0 group by v.client_id;";
		cd.setName("treatedForSTISQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedForSTISQL");
		
		return cd;
	}
	
	/**
	 * Number treated for STI
	 * 
	 * @return
	 */
	public CohortDefinition treatedForSTI(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("treatedForSTISQL", ReportUtils.map(treatedForSTISQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND treatedForSTISQL");
		
		return cd;
	}
	
	/**
	 * Number screened for Hepatitis C
	 * 
	 * @return
	 */
	public CohortDefinition screenedForHCVSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.hepatitisC_screened = 'Y'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHCVSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHCVSQL");
		
		return cd;
	}
	
	/**
	 * Number screened for Hepatitis C by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition screenedForHCV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("screenedForHCVSQL", ReportUtils.map(screenedForHCVSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND screenedForHCVSQL");
		
		return cd;
	}
	
	/**
	 * Number diagnosed with Hepatitis C
	 * 
	 * @return
	 */
	public CohortDefinition diagnosedWithHCVSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.hepatitisC_screened = 'Y' and v.hepatitisC_results = 'P'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedWithHCVSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedWithHCVSQL");
		
		return cd;
	}
	
	/**
	 * Number diagnosed with Hepatitis C by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition diagnosedWithHCV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("diagnosedWithHCVSQL",
		    ReportUtils.map(diagnosedWithHCVSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND diagnosedWithHCVSQL");
		
		return cd;
	}
	
	/**
	 * Number treated for Hepatitis C
	 * 
	 * @return
	 */
	public CohortDefinition treatedForHCVSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.hepatitisC_screened = 'Y' and v.hepatitisC_results = 'N'\n"
		        + "      and v.hepatitisC_treated = 'Y'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("treatedForHCVSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedForHCVSQL");
		
		return cd;
	}
	
	/**
	 * Number treated for Hepatitis C by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition treatedForHCV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("treatedForHCVSQL", ReportUtils.map(treatedForHCVSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND treatedForHCVSQL");
		
		return cd;
	}
	
	/**
	 * Number Screened for Hepatitis B
	 * 
	 * @return
	 */
	public CohortDefinition screenedForHBVSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.hepatitisB_screened = 'Y'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHBVSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number screened for Hepatitis B by KP Type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition screenedForHBV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("screenedForHBVSQL", ReportUtils.map(screenedForHBVSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND screenedForHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number diagnosed with Hepatitis B
	 * 
	 * @return
	 */
	public CohortDefinition diagnosedWithHBVSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.hepatitisB_screened = 'Y' and v.hepatitisB_results = 'P'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedWithHBVSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedWithHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number diagnosed with Hepatitis B by KP Type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition diagnosedWithHBV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("diagnosedWithHBVSQL",
		    ReportUtils.map(diagnosedWithHBVSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND diagnosedWithHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number treated for HBV
	 * 
	 * @return
	 */
	public CohortDefinition treatedForHBVSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.hepatitisB_screened = 'Y' and v.hepatitisB_results = 'P'\n"
		        + "      and v.hepatitisB_treated = 'Y'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("treatedForHBVSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedForHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number treated for Hepatitis B by KP Type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition treatedForHBV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("treatedForHBVSQL", ReportUtils.map(treatedForHBVSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND treatedForHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number vaccinated against HBV
	 * 
	 * @return
	 */
	public CohortDefinition vaccinatedAgainstHBVSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.hepatitisB_screened = 'Y' and v.hepatitisB_results = 'N'\n"
		        + "      and v.hepatitisB_treated = 'Vaccinated'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("vaccinatedAgainstHBVSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("vaccinatedAgainstHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number screened negative and vaccinated against Hepatitis B by KP Type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition vaccinatedAgainstHBV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("vaccinatedAgainstHBVSQL",
		    ReportUtils.map(vaccinatedAgainstHBVSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND vaccinatedAgainstHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number screened for TB
	 * 
	 * @return
	 */
	public CohortDefinition screenedTBSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v where v.tb_screened = 'Y'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("screenedTBSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedTBSQL");
		
		return cd;
	}
	
	/**
	 * Number screened for TB by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition screenedTB(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("screenedTBSQL", ReportUtils.map(screenedTBSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND screenedTBSQL");
		
		return cd;
	}
	
	/**
	 * Number diagnosed with TB
	 * 
	 * @return
	 */
	public CohortDefinition diagnosedTBSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.tb_screened = 'Y' and v.tb_results = 'Positive'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedTBSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedTBSQL");
		
		return cd;
	}
	
	/**
	 * Number diagnosed with TB by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition diagnosedTB(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("diagnosedTBSQL", ReportUtils.map(diagnosedTBSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND diagnosedTBSQL");
		
		return cd;
	}
	
	/**
	 * Number started TB treatment
	 * 
	 * @return
	 */
	public CohortDefinition startedTBTXSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.tb_screened = 'Y' and v.tb_results = 'Positive'       and v.tb_treated = 'Y'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("startedTBTXSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedTBTXSQL");
		
		return cd;
	}
	
	/**
	 * Number started TB treatment by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition startedTBTX(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("startedTBTXSQL", ReportUtils.map(startedTBTXSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND startedTBTXSQL");
		
		return cd;
	}
	
	/**
	 * Number of TB clients on HAART
	 * 
	 * @return
	 */
	public CohortDefinition tbClientsOnHAARTSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "        where v.tb_screened = 'Y' and v.tb_results = 'Positive'\n"
		        + "          and (v.active_art = 'Yes' or initiated_art_this_month = 'Yes')\n"
		        + "          and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "          and v.voided = 0 group by v.client_id;";
		cd.setName("tbClientsOnHAARTSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("tbClientsOnHAARTSQL ");
		
		return cd;
	}
	
	/**
	 * Number of TB clients on HAART by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition tbClientsOnHAART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("tbClientsOnHAARTSQL",
		    ReportUtils.map(tbClientsOnHAARTSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND tbClientsOnHAARTSQL");
		
		return cd;
	}
	
	/**
	 * Initiated PrEP within the reporting month as recorded in clinical visit form
	 * 
	 * @return
	 */
	public CohortDefinition initiatedPrEPReportingPeriodClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "        where v.prep_screened = 'Y' and v.prep_results = 'Eligible'\n"
		        + "          and v.prep_treated = 'Y'\n"
		        + "          and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "          and v.voided = 0 group by v.client_id;";
		cd.setName("initiatedPrEPReportingPeriodClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("initiatedPrEPReportingPeriodClinicalVisit");
		return cd;
	}
	
	/**
	 * Initiated PrEP before the reporting month as recorded in clinical visit form
	 * 
	 * @return
	 */
	public CohortDefinition initiatedPrEPBeforeReportingPeriodClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.prep_screened = 'Y' and v.prep_results = 'Eligible'   and v.prep_treated = 'Y'\n"
		        + "  and v.visit_date < date(:startDate)   and v.voided = 0 group by v.client_id;";
		cd.setName("initiatedPrEPBeforeReportingPeriodClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("initiatedPrEPBeforeReportingPeriodClinicalVisit");
		return cd;
	}
	
	/**
	 * Clients initiated on PrEP for the first time during the reporting period in PrEP program
	 * 
	 * @return
	 */
	public CohortDefinition initiatedPrEPReportingPeriodProgram() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.patient_id from kenyaemr_etl.etl_prep_enrolment e        group by e.patient_id\n"
		        + "       having min(date(e.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("initiatedPrEPReportingPeriodProgram");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("initiatedPrEPReportingPeriodProgram");
		return cd;
	}
	
	/**
	 * Initiated PrEP:number of HIV negative persons in each KP type who have been started on PrEP
	 * during the reporting month after meeting the eligibility criteria for PrEP.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition initiatedPrEP(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("initiatedPrEPReportingPeriodClinicalVisit",
		    ReportUtils.map(initiatedPrEPReportingPeriodClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("initiatedPrEPBeforeReportingPeriodClinicalVisit",
		    ReportUtils.map(initiatedPrEPBeforeReportingPeriodClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("initiatedPrEPReportingPeriodProgram",
		    ReportUtils.map(initiatedPrEPReportingPeriodProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND ((initiatedPrEPReportingPeriodClinicalVisit AND NOT initiatedPrEPBeforeReportingPeriodClinicalVisit) OR initiatedPrEPReportingPeriodProgram)");
		
		return cd;
	}
	
	/**
	 * Currently on PrEP as recorded in clinical visit
	 * 
	 * @return
	 */
	public CohortDefinition currentOnPrEPClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.prep_screened = 'Ongoing'   and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "  and v.voided = 0 group by v.client_id;";
		cd.setName("currentOnPrEPClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOnPrEPClinicalVisit ");
		
		return cd;
	}
	
	/**
	 * Clients with a PrEP visit within the reporting month (in PrEP program)
	 * 
	 * @return
	 */
	public CohortDefinition currentPrEPFollowupVisitProgram() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select f.patient_id from kenyaemr_etl.etl_prep_followup f\n"
		        + "where date(f.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("currentPrEPFollowupVisitProgram");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentPrEPFollowupVisitProgram ");
		
		return cd;
	}
	
	/**
	 * Clients with a PrEP refill appointment date that is within or beyond the reporting month, or
	 * given drugs to cover the reporting month
	 * 
	 * @return
	 */
	public CohortDefinition currentOrFuturePrEPRefillAppDateProgram() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select r.patient_id from kenyaemr_etl.etl_prep_monthly_refill r\n"
		        + "where r.prescribed_prep_today = 'Yes'\n"
		        + "  and (DATE_ADD(date(r.visit_date), INTERVAL r.prescribed_regimen_months MONTH) >= date(:startDate)\n"
		        + "    or date(r.next_appointment) >= date(:startDate));";
		cd.setName("currentOrFuturePrEPRefillAppDateProgram");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOrFuturePrEPRefillAppDateProgram ");
		
		return cd;
	}
	
	/**
	 * Current on PrEP: Number of people in each KP type who are currently receiving PrEP. This
	 * includes persons newly started on PrEP in the reporting month, persons who come for their
	 * PrEP refills in the reporting month, and those who do not come for medicine in the reporting
	 * month but have been given PrEP medications to take them through the reporting month.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition currentOnPrEP(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("currentOnPrEPClinicalVisit",
		    ReportUtils.map(currentOnPrEPClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("initiatedPrEP", ReportUtils.map(initiatedPrEP(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("currentPrEPFollowupVisitProgram",
		    ReportUtils.map(currentPrEPFollowupVisitProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("currentOrFuturePrEPRefillAppDateProgram",
		    ReportUtils.map(currentOrFuturePrEPRefillAppDateProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (currentOnPrEPClinicalVisit OR initiatedPrEP OR currentPrEPFollowupVisitProgram OR currentOrFuturePrEPRefillAppDateProgram)");
		
		return cd;
	}
	
	/**
	 * Tested Positive for HIV during the reporting period in HTS
	 * 
	 * @return
	 */
	public CohortDefinition testedHIVPositiveWithinPeriodHTS() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select hts.patient_id from kenyaemr_etl.etl_hts_test hts where hts.test_type = 2\n"
		        + "  and hts.final_test_result = 'Positive'   and hts.voided = 0\n"
		        + "  and hts.visit_date between date(:startDate) and date(:endDate) group by hts.patient_id;";
		cd.setName("testedHIVPositiveWithinPeriodHTS");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedHIVPositiveWithinPeriodHTS ");
		
		return cd;
	}
	
	/**
	 * Tested Positive for HIV during the reporting period - Self test reported in Clinical visit
	 * 
	 * @return
	 */
	public CohortDefinition testedHIVPositiveWithinPeriodClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(coalesce(v.self_test_date, v.visit_date)) between date(:startDate) and date(:endDate)\n"
		        + "  and v.test_confirmatory_results = 'Positive'   and v.voided = 0;";
		cd.setName("testedHIVPositiveWithinPeriodClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedHIVPositiveWithinPeriodClinicalVisit ");
		
		return cd;
	}
	
	/**
	 * Turning HIV positive while on PrEP: Number of people on PrEP in each KP type who tested
	 * positive for HIV in the reporting period.
	 * 
	 * @return
	 */
	public CohortDefinition turningPositiveWhileOnPrEP(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("currentOnPrEP",
		    ReportUtils.map(currentOnPrEP(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("testedHIVPositiveWithinPeriodHTS",
		    ReportUtils.map(testedHIVPositiveWithinPeriodHTS(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("testedHIVPositiveWithinPeriodClinicalVisit",
		    ReportUtils.map(testedHIVPositiveWithinPeriodClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("currentOnPrEP AND (testedHIVPositiveWithinPeriodHTS OR testedHIVPositiveWithinPeriodClinicalVisit)");
		
		return cd;
	}
	
	/**
	 * Number of people, who experienced violence in the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition experiencingViolenceSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id\n"
		        + "from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.violence_screened = 'Yes'\n"
		        + "  and v.violence_results in\n"
		        + "      ('Harrasment', 'Assault', 'Illegal arrest', 'Verbal Abuse', 'Rape/Sexual assault', 'Discrimination')\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("experiencingViolenceSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencingViolenceSQL");
		
		return cd;
	}
	
	/**
	 * Number of people, who experienced violence in the reporting period by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition experiencingViolence(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("experiencingViolenceSQL",
		    ReportUtils.map(experiencingViolenceSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND experiencingViolenceSQL");
		
		return cd;
	}
	
	/**
	 * Number of people in each KP type who were supported by the programme when they experienced
	 * violence in the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition receivingViolenceSupportSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.violence_treated = 'Supported'\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("receivingViolenceSupportSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingViolenceSupportSQL");
		
		return cd;
	}
	
	/**
	 * Receiving violence support:Number of people in each KP type who were supported by the
	 * programme when they experienced violence in the reporting period by KP type
	 * 
	 * @return
	 */
	public CohortDefinition receivingViolenceSupport(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("receivingViolenceSupportSQL",
		    ReportUtils.map(experiencingViolenceSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivingViolenceSupportSQL");
		
		return cd;
	}
	
	/**
	 * Number exposed: Number of people in each KP type who reported that they were exposed to HIV
	 * within 72 hours of exposure.
	 * 
	 * @return
	 */
	public CohortDefinition numberExposedSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.pep_eligible ='Y' and v.exposure_type in ('Rape', 'Condom burst', 'Others')\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("numberExposedSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("numberExposedSQL");
		
		return cd;
	}
	
	/**
	 * Number exposed: Number of people in each KP type who reported that they were exposed to HIV
	 * within 72 hours of exposure by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition numberExposed(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("numberExposedSQL", ReportUtils.map(numberExposedSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND numberExposedSQL");
		
		return cd;
	}
	
	/**
	 * Number receiving PEP<72hrs: Number of people in each KP type who were initiated on PEP within
	 * 72 hours of HIV exposure.
	 * 
	 * @return
	 */
	public CohortDefinition receivingPEPWithin72HrsSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("receivingPEPWithin72HrsSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingPEPWithin72HrsSQL");
		
		return cd;
	}
	
	/**
	 * Number receiving PEP<72hrs: Number of people in each KP type who were initiated on PEP within
	 * 72 hours of HIV exposure by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition receivingPEPWithin72Hrs(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("receivingPEPWithin72HrsSQL",
		    ReportUtils.map(receivingPEPWithin72HrsSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivingPEPWithin72HrsSQL");
		
		return cd;
	}
	
	/**
	 * Number completed PEP within 28 days: Number of people in each KP type who completed taking
	 * PEP within 28 days.
	 * 
	 * @return
	 */
	public CohortDefinition completedPEPWith28DaysSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("completedPEPWith28DaysSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("completedPEPWith28DaysSQL");
		
		return cd;
	}
	
	/**
	 * Number completed PEP within 28 days: Number of people in each KP type who completed taking
	 * PEP within 28 days by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition completedPEPWith28Days(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("completedPEPWith28DaysSQL",
		    ReportUtils.map(completedPEPWith28DaysSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND completedPEPWith28DaysSQL");
		
		return cd;
	}
	
	/**
	 * Receiving peer education: Number of people in each KP type who received peer education in the
	 * reporting period.
	 * 
	 * @return
	 */
	public CohortDefinition receivedPeerEducationSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_peer_calendar p\n"
		        + "on c.client_id = p.client_id where date(p.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("receivedPeerEducationSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedPeerEducationSQL");
		
		return cd;
	}
	
	/**
	 * Receiving peer education: Number of people in each KP type who received peer education in the
	 * reporting period by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition receivedPeerEducation(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("receivedPeerEducationSQL",
		    ReportUtils.map(receivedPeerEducationSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivedPeerEducationSQL");
		
		return cd;
	}
	
	/**
	 * Receiving clinical services: Number of people in each KP type who received clinical services
	 * in the reporting period.
	 * 
	 * @return
	 */
	public CohortDefinition receivedClinicalServicesSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_clinical_visit v\n"
		        + "on c.client_id = v.client_id where date(v.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("receivedClinicalServicesSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedClinicalServicesSQL");
		
		return cd;
	}
	
	/**
	 * Receiving clinical services: Number of people in each KP type who received clinical services
	 * in the reporting period by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition receivedClinicalServices(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("receivedClinicalServicesSQL",
		    ReportUtils.map(receivedClinicalServicesSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivedClinicalServicesSQL");
		
		return cd;
	}
	
	/**
	 * Clients enrolled in HIV care program within the reporting period - in this Facility/on-site
	 * 
	 * @return
	 */
	public CohortDefinition enrolledInCareReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.patient_id from kenyaemr_etl.etl_hiv_enrollment e\n"
		        + "where e.visit_date between date(:startDate)\n" + "and date(:endDate);";
		cd.setName("enrolledInCareReportingPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("enrolledInCareReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Clients enrolled in HIV care program before reporting period - in this Facility/on-site
	 * 
	 * @return
	 */
	public CohortDefinition enrolledInCareBeforeReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.patient_id\n" + "from (select e.patient_id,\n"
		        + "             max(e.visit_date)     as enroll_date,\n"
		        + "             d.effective_disc_date as disc_date,\n"
		        + "             d.patient_id          as disc_patient\n" + "      from kenyaemr_etl.etl_hiv_enrollment e\n"
		        + "               left join (\n" + "          select patient_id,\n"
		        + "                 coalesce(date(effective_discontinuation_date), visit_date) visit_date,\n"
		        + "                 max(date(effective_discontinuation_date)) as               effective_disc_date\n"
		        + "          from kenyaemr_etl.etl_patient_program_discontinuation\n"
		        + "          where date(visit_date) <= date(:endDate)\n" + "            and program_name = 'HIV'\n"
		        + "          group by patient_id\n" + "      ) d on e.patient_id = d.patient_id\n"
		        + "      where e.visit_date < date(:startDate)\n" + "      group by e.patient_id\n"
		        + "      having enroll_date > disc_date\n" + "          or disc_patient is null) a\n"
		        + "group by a.patient_id;";
		cd.setName("enrolledInCarePreviousBeforePeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("enrolledInCareBeforeReportingPeriod");
		
		return cd;
	}
	
	/**
	 * ART clients - This facility/on-site
	 * 
	 * @return
	 */
	public CohortDefinition artPatients() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select d.patient_id from kenyaemr_etl.etl_drug_event d where date(d.date_started) <=date(:endDate)\n"
		        + "and d.program = 'HIV';";
		cd.setName("artPatients");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("artPatients");
		
		return cd;
	}
	
	/**
	 * Clients with Clinical visits within reporting period and not started on ART
	 * 
	 * @return
	 */
	public CohortDefinition hadClinicalVisitReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v where date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "and v.initiated_art_this_month != 'Yes' and v.active_art !='Yes';\n";
		cd.setName("hadClinicalVisitReportingPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hadClinicalVisitReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Pre-ART Clients with Clinical visits within reporting period and receiving care elsewhere
	 * 
	 * @return
	 */
	public CohortDefinition preARTReceivingCareElsewhere() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v where date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "and v.initiated_art_this_month != 'Yes' and v.active_art !='Yes' and v.hiv_care_facility ='Provided elsewhere';";
		cd.setName("preARTReceivingCareElsewhere");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("preARTReceivingCareElsewhere");
		
		return cd;
	}
	
	/**
	 * Number of people of each KP type who were enrolled in care this month or in a previous month
	 * and made a clinical visit on site in preparation for ART but have not started on ART during
	 * this visit. They should be counted only if there is no intention to start them on ART during
	 * the reporting month in this site.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition onSitePreART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("enrolledInCareReportingPeriod",
		    ReportUtils.map(enrolledInCareReportingPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("enrolledInCareBeforeReportingPeriod",
		    ReportUtils.map(enrolledInCareBeforeReportingPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("artPatients", ReportUtils.map(artPatients(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("hadClinicalVisitReportingPeriod",
		    ReportUtils.map(hadClinicalVisitReportingPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND ((enrolledInCareBeforeReportingPeriod AND hadClinicalVisitReportingPeriod) OR enrolledInCareReportingPeriod) AND NOT artPatients");
		
		return cd;
	}
	
	/**
	 * Number of people of each KP type who were enrolled in care this month or in a previous month
	 * and made a clinical visit off site in preparation for ART but have not started on ART during
	 * this visit. They should be counted only if there is no intention to start them on ART during
	 * the reporting month in another site.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition offSitePreART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("preARTReceivingCareElsewhere",
		    ReportUtils.map(preARTReceivingCareElsewhere(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND preARTReceivingCareElsewhere");
		
		return cd;
	}
	
	/**
	 * Total Pre-ART = Onsite + Offsite
	 * 
	 * @return
	 */
	public CohortDefinition totalOnPreART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("offSitePreART",
		    ReportUtils.map(offSitePreART(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("onSitePreART",
		    ReportUtils.map(onSitePreART(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("onSitePreART OR offSitePreART");
		
		return cd;
	}
	
	/**
	 * Clients who had a clinical visit within the reporting period and were recorded to starting
	 * ART during the visit - This facility/on site
	 * 
	 * @return
	 */
	public CohortDefinition hadClinicalVisitReportingPeriodAndStartingARTOnSite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "      and v.initiated_art_this_month = 'Yes' and hiv_care_facility = 'Provided here';";
		cd.setName("hadClinicalVisitReportingPeriodAndStartingARTOnSite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hadClinicalVisitReportingPeriodAndStartingARTOnSite");
		
		return cd;
	}
	
	/**
	 * Clients who had a clinical visit within the reporting period and were recorded to starting
	 * ART during the visit - off site
	 * 
	 * @return
	 */
	public CohortDefinition hadClinicalVisitReportingPeriodAndStartingARTOffSite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "      and v.initiated_art_this_month = 'Yes' and hiv_care_facility = 'Provided elsewhere';";
		cd.setName("hadClinicalVisitReportingPeriodAndStartingARTOffSite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hadClinicalVisitReportingPeriodAndStartingARTOffSite");
		
		return cd;
	}
	
	/**
	 * Number of people of each KP type started on HAART for treatment at this site within the
	 * reporting period - This facility/On-site
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition onSiteStartingART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("startedARTReportingPeriod",
		    ReportUtils.map(datimCohorts.startedOnART(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("hadClinicalVisitReportingPeriodAndStartingARTOnSite", ReportUtils.map(
		    hadClinicalVisitReportingPeriodAndStartingARTOnSite(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (startedARTReportingPeriod OR hadClinicalVisitReportingPeriodAndStartingARTOnSite)");
		
		return cd;
	}
	
	/**
	 * Number of people of each KP type started on HAART for treatment in another site within the
	 * reporting period
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition offSiteStartingART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("hadClinicalVisitReportingPeriodAndStartingARTOffSite", ReportUtils.map(
		    hadClinicalVisitReportingPeriodAndStartingARTOffSite(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND hadClinicalVisitReportingPeriodAndStartingARTOffSite");
		
		return cd;
	}
	
	/**
	 * Total starting ART = On site + off site
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition totalStartingART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("onSiteStartingART",
		    ReportUtils.map(onSiteStartingART(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("offSiteStartingART",
		    ReportUtils.map(offSiteStartingART(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("onSiteStartingART OR offSiteStartingART");
		
		return cd;
	}
	
	/**
	 * Current ART - On site Number of people in each KP type who: 1. started therapy on site this
	 * month or 2. started therapy on site before this month but made a visit to collect drugs this
	 * month or 3. started therapy on site before this month but did not make a visit to the
	 * facility during this month because they had been given enough drugs during visits before this
	 * month to cover the reporting month.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition currentlyOnARTOnSite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("currentlyOnArt",
		    ReportUtils.map(datimCohorts.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND currentlyOnArt");
		
		return cd;
	}
	
	/**
	 * Current on ART elsewhere as reported during clinical visit within the reporting period
	 * Includes: 1.started therapy this month off site 2.started therapy off site before this month
	 * but visited the offsite clinic to collect drugs this month Should include <3.started therapy
	 * off site before this month but did not make a visit to the facility during this month because
	 * they had been given enough drugs during visits before this month to cover the reporting
	 * month> when hiv followup tca is collected during a KP clinical visit
	 * 
	 * @return
	 */
	public CohortDefinition reportedCurrentOnARTElsewhereClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "      and hiv_care_facility = 'Provided elsewhere' and (v.initiated_art_this_month = 'Yes' or v.active_art = 'Yes');";
		cd.setName("reportedCurrentOnARTElsewhereClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("reportedCurrentOnARTElsewhereClinicalVisit");
		
		return cd;
	}
	
	/**
	 * Current ART - Off site Number of people in each KP type who: 1. started therapy on site this
	 * month or 2. started therapy on site before this month but made a visit to collect drugs this
	 * month or 3. started therapy on site before this month but did not make a visit to the
	 * facility during this month because they had been given enough drugs during visits before this
	 * month to cover the reporting month.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition currentlyOnARTOffSite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("reportedCurrentOnARTElsewhereClinicalVisit",
		    ReportUtils.map(reportedCurrentOnARTElsewhereClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND reportedCurrentOnARTElsewhereClinicalVisit");
		
		return cd;
	}
	
	/**
	 * Current ART = On site + offsite
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition totalCurrentlyOnART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("currentlyOnARTOnSite",
		    ReportUtils.map(currentlyOnARTOnSite(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("currentlyOnARTOffSite",
		    ReportUtils.map(currentlyOnARTOffSite(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("currentlyOnARTOnSite OR currentlyOnARTOffSite");
		
		return cd;
	}
	
	/**
	 * Started ART 12 months ago
	 * 
	 * @return
	 */
	public CohortDefinition startedART12MonthsAgo() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id\n"
		        + "from (select e.patient_id,\n"
		        + "             e.date_started\n"
		        + "      from (select dr.patient_id,\n"
		        + "                   min(date(dr.date_started)) as date_started\n"
		        + "            from kenyaemr_etl.etl_drug_event dr\n"
		        + "                     join kenyaemr_etl.etl_hiv_enrollment e on dr.patient_id = e.patient_id\n"
		        + "                     join kenyaemr_etl.etl_patient_demographics p on p.patient_id = dr.patient_id and p.voided = 0\n"
		        + "            where dr.program = 'HIV'\n"
		        + "            group by dr.patient_id) e\n"
		        + "      where date(e.date_started) between date_sub(date(:startDate), INTERVAL 12 MONTH) and date_sub(date(:endDate), INTERVAL 12 MONTH)\n"
		        + "      group by e.patient_id) a;";
		cd.setName("startedART12MonthsAgo");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedART12MonthsAgo");
		
		return cd;
	}
	
	/**
	 * Transfered out within last 12 months to effective reporting date
	 * 
	 * @return
	 */
	public CohortDefinition transferOutsSince12MonthsAgo() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.patient_id\n"
		        + "from (select d.patient_id,\n"
		        + "             coalesce(mid(max(concat(date(d.visit_date), date(d.transfer_date))), 11),\n"
		        + "                      mid(max(concat(date(d.visit_date), date(date(d.effective_discontinuation_date)))), 11),\n"
		        + "                      max(date(d.visit_date))) as to_date\n"
		        + "      from kenyaemr_etl.etl_patient_program_discontinuation d\n" + "      where program_name = 'HIV'\n"
		        + "        and date(d.visit_date) <= date(:endDate)\n" + "        and d.discontinuation_reason = 159492\n"
		        + "      group by d.patient_id\n"
		        + "      having to_date between date_sub(date(:startDate), INTERVAL 12 MONTH) and (date(:endDate))) a;";
		cd.setName("transferOutsSince12MonthsAgo");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("transferOutsSince12MonthsAgo");
		
		return cd;
	}
	
	/**
	 * Transfered in within last 12 months to effective reporting date
	 * 
	 * @return
	 */
	public CohortDefinition transferInsSince12MonthsAgo() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.patient_id\n" + "from (select e.patient_id,\n"
		        + "             mid(max(concat(date(e.visit_date), e.patient_type)), 11) as patient_type,\n"
		        + "             coalesce(mid(max(concat(date(e.visit_date), date(e.transfer_in_date))), 11),\n"
		        + "                      max(date(e.visit_date)))                        as ti_date\n"
		        + "      from kenyaemr_etl.etl_hiv_enrollment e\n" + "      group by e.patient_id\n"
		        + "      having patient_type = 160563\n"
		        + "         and ti_date between date_sub(date(:startDate), INTERVAL 12 MONTH) and (date(:endDate))) a;";
		cd.setName("transferInsSince12MonthsAgo");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("transferInsSince12MonthsAgo");
		
		return cd;
	}
	
	/**
	 * 5.4 Retention on ART: On-site On ART at 12 months: Number of clients still on ART 12 months
	 * after starting ART regardless of regimen.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition onARTAt12MonthsOnsite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("currentlyOnArt",
		    ReportUtils.map(datimCohorts.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("startedART12MonthsAgo",
		    ReportUtils.map(startedART12MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND startedART12MonthsAgo AND currentlyOnArt");
		
		return cd;
	}
	
	/**
	 * Net cohort at 12 months : This refers to the number of clients started ART in the same month
	 * plus transfer ins and minus transfer outs. Take the number of patients in the original
	 * cohort, add the Transfers In (TIs), and subtract the Transfers Out (TOs) to get the net
	 * cohort
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition netCohortAt12MonthsOnsite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("startedART12MonthsAgo",
		    ReportUtils.map(startedART12MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("transferInsSince12MonthsAgo",
		    ReportUtils.map(transferInsSince12MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("transferOutsSince12MonthsAgo",
		    ReportUtils.map(transferOutsSince12MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND ((startedART12MonthsAgo AND NOT transferOutsSince12MonthsAgo) OR (startedART12MonthsAgo AND transferInsSince12MonthsAgo))");
		
		return cd;
	}
	
	/**
	 * Viral load result_12mths: On-site Number of people in the 12-month cohort in each KP type who
	 * had a viral load test on site at 12 months and whose results were available at the time of
	 * analysis
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition viralLoad12MonthsOnsite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("patientsWithVLResultsLast12Months",
		    ReportUtils.map(moh731Cohorts.patientsWithVLResultsLast12Months(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND patientsWithVLResultsLast12Months");
		
		return cd;
	}
	
/**
	 * Viral load <1000_12mths: On-site
	 * 	number of people in the 12-month cohort in each KP type whose 12-month viral load test
	 *  was done on site and who have a viral load less than 1000 copies per ML.
 	 * @param kpType
	 * @return
	 */
	public CohortDefinition suppressedViralLoad12MonthsCohortOnsite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("patientsWithSuppressedVlLast12Months", ReportUtils.map(
		    moh731Cohorts.patientsWithSuppressedVlLast12Months(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND patientsWithSuppressedVlLast12Months");
		
		return cd;
	}
	
	/**
	 * Had a offsite vl test done within the last 12 months
	 * 
	 * @return
	 */
	public CohortDefinition vlTestDoneWithinLast12MonthsOffsite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_clinical_visit c\n"
		        + "where date(c.visit_date) between date_sub(date(:startDate), INTERVAL 12 MONTH) and date(:endDate)\n"
		        + "  and c.hiv_care_facility = 'Provided elsewhere'\n" + "  and c.vl_results in ('Y', 'N');";
		cd.setName("vlTestDoneWithinLast12MonthsOffsite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("vlTestDoneWithinLast12MonthsOffsite");
		
		return cd;
	}
	
	/**
	 * Had a suppressed VL test result within the last 12 months
	 * 
	 * @return
	 */
	public CohortDefinition suppresedVlWithinLast12MonthsOffsite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_clinical_visit c\n"
		        + "where date(c.visit_date) between date_sub(date(:startDate), INTERVAL 12 MONTH) and date(:endDate)\n"
		        + "  and c.hiv_care_facility = 'Provided elsewhere'\n" + "  and c.vl_results = 'Y';";
		cd.setName("suppresedVlWithinLast12MonthsOffsite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("suppresedVlWithinLast12MonthsOffsite");
		
		return cd;
	}
	
	/**
	 * Current on ART elsewhere : Has latest clinical visit on or before reporting date and next
	 * appointment date >= reporting start date
	 * 
	 * @return
	 */
	public CohortDefinition currentOnArtOffsite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_clinical_visit c\n"
		        + "where date(c.visit_date) <= date(:endDate) and c.hiv_care_facility = 'Provided elsewhere'\n"
		        + "  and (c.active_art = 'Yes' or c.initiated_art_this_month = 'Yes') group by c.client_id\n"
		        + "having mid(max(concat(date(c.visit_date),date(c.appointment_date))),11) >= date(:startDate);";
		cd.setName("currentOnArtOffsite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOnArtOffsite");
		
		return cd;
	}
	
	/**
	 * Started ART 12 months ago elsewhere
	 * 
	 * @return
	 */
	public CohortDefinition startedArt12MonthsAgoOffsite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n"
		        + "from kenyaemr_etl.etl_clinical_visit c\n"
		        + "where date(c.visit_date) between date_sub(date(:startDate), INTERVAL 12 MONTH) and date_sub(date(:endDate), INTERVAL 12 MONTH)\n"
		        + "  and c.hiv_care_facility = 'Provided elsewhere'\n" + "  and c.initiated_art_this_month = 'Yes';";
		cd.setName("startedArt12MonthsAgoOffsite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedArt12MonthsAgoOffsite");
		
		return cd;
	}
	
	/**
	 * Viral load result_12mths: Off-site Number of people in the 12-month cohort in each KP type
	 * who had a viral load test on site at 12 months and whose results were available at the time
	 * of analysis
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition viralLoad12MonthsOffsite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("startedArt12MonthsAgoOffsite",
		    ReportUtils.map(startedArt12MonthsAgoOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("currentOnArtOffsite",
		    ReportUtils.map(currentOnArtOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("vlTestDoneWithinLast12MonthsOffsite",
		    ReportUtils.map(vlTestDoneWithinLast12MonthsOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND startedArt12MonthsAgoOffsite AND currentOnArtOffsite AND vlTestDoneWithinLast12MonthsOffsite");
		
		return cd;
	}
	
/**
	 * Viral load <1000_12mths: Off-site
	 * 	number of people in the 12-month cohort in each KP type whose 12-month viral load test
	 *  was done off site and who have a viral load less than 1000 copies per ML.
	 * @param kpType
	 * @return
	 */
	public CohortDefinition suppressedViralLoad12MonthsCohortOffsite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("startedArt12MonthsAgoOffsite",
		    ReportUtils.map(startedArt12MonthsAgoOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("currentOnArtOffsite",
		    ReportUtils.map(currentOnArtOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("suppresedVlWithinLast12MonthsOffsite",
		    ReportUtils.map(suppresedVlWithinLast12MonthsOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND startedArt12MonthsAgoOffsite AND currentOnArtOffsite AND suppresedVlWithinLast12MonthsOffsite");
		
		return cd;
	}
	
	/**
	 * Number experienced overdose in the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition experiencedOverdoseReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select r.client_id\n" + "from kenyaemr_etl.etl_overdose_reporting r\n"
		        + "where date(r.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("experiencedOverdoseReportingPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencedOverdoseReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Number of PWID/PWUD who experienced overdose in the reporting period
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition experiencedOverdose(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("experiencedOverdoseReportingPeriod",
		    ReportUtils.map(experiencedOverdoseReportingPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND experiencedOverdoseReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Number had overdose and received naloxone
	 * 
	 * @return
	 */
	public CohortDefinition experiencedOverdoseRcvdNaloxoneReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select r.client_id\n" + "from kenyaemr_etl.etl_overdose_reporting r\n"
		        + "where date(r.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and r.naloxone_provided = 1065;";
		cd.setName("experiencedOverdoseRcvdNaloxoneReportingPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencedOverdoseRcvdNaloxoneReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Number of PWID/PWUD who had overdose and received naloxone
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition experiencedOverdoseGivenNaloxone(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("experiencedOverdoseRcvdNaloxoneReportingPeriod",
		    ReportUtils.map(experiencedOverdoseRcvdNaloxoneReportingPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND experiencedOverdoseRcvdNaloxoneReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Number of deaths due to overdose in the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition overdoseDeaths() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select r.client_id from kenyaemr_etl.etl_overdose_reporting r where date(r.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "and r.outcome = 160034;";
		cd.setName("overdoseDeaths");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("overdoseDeaths");
		
		return cd;
	}
}
