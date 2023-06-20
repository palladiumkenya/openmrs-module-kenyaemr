/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.threepm;

/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH731Greencard.ETLMoh731GreenCardCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.kp.KPMoh731PlusCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.kp.KPMonthlyReportCohortLibrary;
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
 * Library of cohort definitions used specifically in the MOH731 report based on ETL tables. It has incorporated green card components
 */

@Component
public class ThreePMCohortLibrary {

    @Autowired
    private DatimCohortLibrary datimCohorts;
    @Autowired
    private KPMoh731PlusCohortLibrary moh731PlusCohortLibrary;
    @Autowired
    private KPMonthlyReportCohortLibrary kpifCohorts;
    @Autowired
    private ETLMoh731GreenCardCohortLibrary moh731Cohorts;


    /**
     * Screened for HIV test
     * @return
     */

    public CohortDefinition screenedFromMobileDepartment(Integer department) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.patient_id from kenyaemr_etl.etl_hts_eligibility_screening s where s.department = "+department+" and date(s.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("Screening department");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Screening department");

        return cd;
    }
    public CohortDefinition htsScreened(Integer department) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.patient_id from kenyaemr_etl.etl_hts_eligibility_screening s where s.department = "+department+" and date(s.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("totalHTSScreened");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total HTS Screened");

        return cd;
    }
    //ANC 1 CLIENTS

    public CohortDefinition htsTested() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where date(t.visit_date) between date(:startDate) and date(:endDate) and t.test_type = 1;";
        cd.setName("HTS tested");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("HTS tested");

        return cd;
    }

    public CohortDefinition htsTestedByDepartment(Integer department) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where date(t.visit_date) between date(:startDate) and date(:endDate) and t.test_type = 1 and t.hts_entry_point = "+department+";";
        cd.setName("HTS tested mobile");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("HTS tested mobile");

        return cd;
    }
/*    public CohortDefinition htsTestedPositiveMobileDepartment() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where date(t.visit_date) between date(:startDate) and date(:endDate) and t.test_type = 1 and t.final_test_result ='Positive' and t.hts_entry_point = 159939;";
        cd.setName("HTS tested Positive mobile");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("HTS tested Positive mobile");

        return cd;
    }*/
    public CohortDefinition htsTestedPositive() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where date(t.visit_date) between date(:startDate) and date(:endDate) and t.test_type = 1 and t.final_test_result ='Positive';";
        cd.setName("HTS tested Positive");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("HTS tested Positive");

        return cd;
    }
    public CohortDefinition htsTBTested() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where date(t.visit_date) between date(:startDate) and date(:endDate) and t.tb_screening in (1660,142177,1662,160737,1111);";
        cd.setName("HTS tested Positive");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("HTS tested Positive");

        return cd;
    }
    public CohortDefinition knownTbPositive() {
        String sqlQuery = "select v.patient_id from kenyaemr_etl.etl_tb_enrollment v\n" +
                "join kenyaemr_etl.etl_patient_demographics p on p.patient_id=v.patient_id \n" +
                "left join (select t.patient_id,t.visit_date from kenyaemr_etl.etl_hts_test t where t.test_type = 1 and t.final_test_result='Positive' and t.hts_entry_point = 160538)t on v.patient_id = t.patient_id and v.visit_date = t.visit_date\n" +
                "    where v.anc_visit_number = 1 and v.visit_date between date(:startDate) and date(:endDate) and (v.final_test_result = 'Positive' or t.patient_id is not null);";

        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("knownTbPositive");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Clients with Known HIV Positive TB");
        return cd;

    }


    public CohortDefinition htsScreenedMobile(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("screenedFromMobileDepartment",
                ReportUtils.map(screenedFromMobileDepartment(department), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND screenedFromMobileDepartment");
        return cd;
    }


    public CohortDefinition htsTbNewKnownPositive (Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("tbNewKnownPositive",
                ReportUtils.map(moh731Cohorts.tbNewKnownPositive(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND tbNewKnownPositive");
        return cd;
    }
    public CohortDefinition knownTbPositive (Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("knownTbPositive",
                ReportUtils.map(knownTbPositive(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND tbNewKnownPositive");
        return cd;
    }

    public CohortDefinition htsTbScreenedForTb (Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("screenedForTb",
                ReportUtils.map(moh731Cohorts.screenedForTbWithinPeriod(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND screenedForTb");
        return cd;
    }
    public CohortDefinition htsTBTested (Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsTBTested",
                ReportUtils.map(htsTBTested(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsTBTested");
        return cd;
    }


    public CohortDefinition htsEligibleMobile(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("eligibleForHIVTest",
                ReportUtils.map(eligibleForHIVTest(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("screenedFromMobileDepartment",
                ReportUtils.map(screenedFromMobileDepartment(department), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("eligibleForHIVTest AND screenedFromMobileDepartment");
        return cd;
    }
    public CohortDefinition htsEligibleForTbTest(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("eligibleForTbTest",
                ReportUtils.map(eligibleForTbTest(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("screenedFromMobileDepartment",
                ReportUtils.map(screenedFromMobileDepartment(department), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("eligibleForTbTest AND screenedFromMobileDepartment");
        return cd;
    }
    public CohortDefinition htsScreenedANC1(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("firstANCVisit",
                ReportUtils.map(moh731Cohorts.firstANCVisitMchmsAntenatal(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("screened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("firstANCVisit AND screened");
        return cd;
    }

    public CohortDefinition htsTested(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        /*cd.addSearch("htsScreened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));*/
        cd.addSearch("htsTestedByDepartment",
                ReportUtils.map(htsTestedByDepartment(department), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsTestedByDepartment");
        return cd;
    }

    // Known positive from HTS screening
    public CohortDefinition htsKnownPositiveHTSScreening() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.patient_id from kenyaemr_etl.etl_hts_eligibility_screening s where s.tested_hiv_before = 1065 and s.test_results = 703\n" +
                "    and date(s.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("Known positive HTS screening");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Known positive HTS screening");

        return cd;
    }

    /**
     * Known positive individuals from hts eligibility screening through mobile department
     * @return
     */
    public CohortDefinition htsKnownPositive(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsKnownPositiveHTSScreening",
                ReportUtils.map(htsKnownPositiveHTSScreening(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsKnownPositiveHTSScreening");
        return cd;
    }
    public CohortDefinition eligibleForHIVTest() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.patient_id from kenyaemr_etl.etl_hts_eligibility_screening s where s.eligible_for_test = 1065 and date(s.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("Eligible for HIV test");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Eligible for HIV test");

        return cd;
    }
    public CohortDefinition eligibleForTbTest() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.patient_id from kenyaemr_etl.etl_hts_eligibility_screening s where s.eligible_for_test = 1065 and date(s.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("Eligible for TB test");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Eligible for TB test");

        return cd;
    }
    public CohortDefinition htsTbTest() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.patient_id from kenyaemr_etl.etl_hts_eligibility_screening s where s.eligible_for_test = 1065 and date(s.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("Eligible for HIV test");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Eligible for HIV test");

        return cd;
    }
    //}htsNewPositiveMobile,htsLinkedToHAARTMobile,moh731Cohorts.htsNumberTestedPositive,moh731Cohorts.htsAllNumberTestedPositiveAndLinked()
    public CohortDefinition htsNewPositiveMobile(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsTestedPositive",
                ReportUtils.map(htsTestedPositive(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsTestedMobile",
                ReportUtils.map(htsTested(department), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsTestedPositive AND htsTestedMobile");
        return cd;
    }

    public CohortDefinition htsLinkedToHAARTMobile(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsNewPositiveMobile",
                ReportUtils.map(htsNewPositiveMobile(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsAllNumberTestedPositiveAndLinked",
                ReportUtils.map(moh731Cohorts.htsNumberTestedPositiveAndLinked(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsNewPositiveMobile AND htsAllNumberTestedPositiveAndLinked");
        return cd;
    }

    public CohortDefinition kpCurrOnPrEPWithSTI(String kpType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("diagnosedWithSTISQL",
                ReportUtils.map(moh731PlusCohortLibrary.diagnosedWithSTISQL(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("kpPrepCurrDice",
                ReportUtils.map(kpifCohorts.kpPrepCurrDice(kpType), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("kpPrepCurrDice AND diagnosedWithSTISQL");
        return cd;
    }

    public CohortDefinition ppType(String ppType) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
                + "where date(c.visit_date) <= date(:endDate)\n"
                + "group by c.client_id having mid(max(concat(date(c.visit_date), c.priority_population_type)), 11) = '" + ppType
                + "';";
        cd.setName("ppType");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("ppType");
        return cd;
    }

    public CohortDefinition ppCurrentOnARTOffsite() {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        //cd.addSearch("ppType", ReportUtils.map(ppType(ppType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("currOnARTOffsite",ReportUtils.map(moh731PlusCohortLibrary.reportedCurrentOnARTElsewhereClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("currOnARTOffsite");
        return cd;
    }

    public CohortDefinition ppCurrentOnARTOnSite() {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
      //  cd.addSearch("ppType", ReportUtils.map(ppType(ppType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("currentOnART",ReportUtils.map(datimCohorts.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("currentOnART");
        return cd;
    }

    public CohortDefinition kpCurrentOnARTOffsite(String ppType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("kpType", ReportUtils.map(kpifCohorts.kpType(ppType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("currOnARTOffsite",ReportUtils.map(moh731PlusCohortLibrary.reportedCurrentOnARTElsewhereClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("kpType AND currOnARTOffsite");
        return cd;
    }
}

