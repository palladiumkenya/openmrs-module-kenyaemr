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
import org.openmrs.module.kenyacore.report.cohort.definition.CalculationCohortDefinition;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LostToFollowUpCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.IsTransferInCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.IsTransferOutCalculation;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.Datim.TXCurrLinelistCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH731Greencard.ETLMoh731GreenCardCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimIndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.publicHealthActionReport.PublicHealthActionCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.kp.KPMoh731PlusCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.kp.KPMonthlyReportCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.pmtct.PMTCTCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.tb.TbCohortLibrary;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
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
    private TbCohortLibrary tbCohortLibrary;
    @Autowired
    private PMTCTCohortLibrary pmtctCohortLibrary;
    @Autowired
    private  TXCurrLinelistCohortLibrary txCurrLinelistCohortLibrary;
    @Autowired
    private CommonCohortLibrary commonCohorts;
    @Autowired
    PublicHealthActionCohortLibrary publicHealthActionCohortLibrary;
    @Autowired
    DatimIndicatorLibrary datimIndicatorLibrary;

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
    public CohortDefinition htsPopulationType(Integer populationType) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.patient_id from kenyaemr_etl.etl_hts_eligibility_screening s where s.population_type = "+populationType+" and date(s.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("populationTypeScreened");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total HTS Screened");

        return cd;
    }

    public CohortDefinition htsKpScreened(Integer kpType) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.client_id from kenyaemr_etl.etl_contact s where s.key_population_type = "+kpType+" and date(s.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("kpScreened");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("KP Screened");

        return cd;
    }
//    public CohortDefinition htsPopulationType(Integer populationType) {
//        SqlCohortDefinition cd = new SqlCohortDefinition();
//        String sqlQuery = "select s.client_id from kenyaemr_etl.etl_contact s where s.key_population_type = "+kpType+" and date(s.visit_date) between date(:startDate) and date(:endDate);";
//        cd.setName("kpScreened");
//        cd.setQuery(sqlQuery);
//        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
//        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
//        cd.setDescription("KP Screened");
//
//        return cd;
//    }
//    population_type

    public CohortDefinition htsNewScreened(Integer entryPoint) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_eligibility_screening t where t.hts_entry_point = "+entryPoint+" and date(t.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("HTS tested");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("HTS tested");
        return cd;
    }
    public CohortDefinition htsTypeScreened(Integer entryPoint) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.patient_id from kenyaemr_etl.etl_hts_eligibility_screening s where s.hts_entry_point = "+entryPoint+" and date(s.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("totalHTSScreened");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total HTS Screened");

        return cd;
    }
    public CohortDefinition htsMCHAART() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select distinct v.patient_id from kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "          inner join kenyaemr_etl.etl_mch_enrollment e on e.patient_id= v.patient_id\n" +
                "          inner join kenyaemr_etl.etl_drug_event d on v.patient_id=d.patient_id\n" +
                "           where d.date_started >= v.visit_date and\n" +
                "                 v.visit_date between date(:startDate) AND date(:endDate);";
        cd.setName("MCH HAART");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("MCH HAART");

        return cd;
    }

    public CohortDefinition htsTestStrategy(Integer testStrategy) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.patient_id from kenyaemr_etl.etl_hts_eligibility_screening s where s.test_strategy = "+testStrategy+" and date(s.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("testStrategy");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Test Strategy");
        return cd;
    }

    public CohortDefinition htsHospitalPatient() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.patient_id from kenyaemr_etl.etl_hts_eligibility_screening s where s.patient_type = 164163 and date(s.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("HospitalPatient");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Hospital Patient");

        return cd;
    }
    public CohortDefinition htsNonHospitalPatient() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.patient_id from kenyaemr_etl.etl_hts_eligibility_screening s where s.patient_type = 164953 and date(s.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("HospitalPatient");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Non Hospital Patient");

        return cd;
    }
    //ANC 1 CLIENTS
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
    public CohortDefinition chronicillness(Integer chronicillness) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_allergy_chronic_illness t where date(t.visit_date) between date(:startDate) and date(:endDate) and t.chronic_illness = "+chronicillness+";";
        cd.setName("Chronic Illness");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Chronic Illness");

        return cd;
    }
    public CohortDefinition htsEligible() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_eligibility_screening t where t.eligible_for_test = 1065 and date(t.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("HTS tested");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("HTS tested");
        return cd;
    }
    public CohortDefinition htsEligible(Integer entryPoint) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_eligibility_screening t where  t.hts_entry_point = "+entryPoint+" and date(t.visit_date) between date(:startDate) and date(:endDate) and t.eligible_for_test = 1065;";
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

    public CohortDefinition htsRetestedDepartment(Integer department) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where date(t.visit_date) between date(:startDate) and date(:endDate) and t.test_type = 2 and t.hts_entry_point = "+department+";";
        cd.setName("HTS tested mobile");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("HTS retested mobile");

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
public CohortDefinition htsNewlyTestedPositive() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where date(t.visit_date) between date(:startDate) and date(:endDate) and t.test_type = 1 and t.final_test_result ='Positive';";
        cd.setName("HTS tested Positive");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("HTS tested Positive");

        return cd;
    }

    public CohortDefinition htsNewlyTestedNegative() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where date(t.visit_date) between date(:startDate) and date(:endDate) and t.test_type = 1 and t.final_test_result ='Negative';";
        cd.setName("HTS tested Positive");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("HTS tested Positive");

        return cd;
    }
    public CohortDefinition htsNewlyKnownPositive() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.patient_id from kenyaemr_etl.etl_hts_test s where  s.final_test_result=\"Positive\"\n" +
                "    and date(s.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("Known positive HTS screening");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Known positive HTS screening");

        return cd;
    }
//    public CohortDefinition htsTBTested() {
//        SqlCohortDefinition cd = new SqlCohortDefinition();
//        String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where date(t.visit_date) between date(:startDate) and date(:endDate) and t.tb_screening in (1660,142177,1662,160737,1111);";
//        cd.setName("HTS tested Positive");
//        cd.setQuery(sqlQuery);
//        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
//        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
//        cd.setDescription("HTS tested Positive");
//
//        return cd;
//    }


    public CohortDefinition patientWithValidVLANDLDL(Integer vl){
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select a.patient_id from (select c.patient_id,vl.vl_date from (select e.patient_id, max(e.visit_date) as latest_enr,min(e.visit_date) as first_enr, d.patient_id as disc_patient,d.latest_disc,p.dob as dob,timestampdiff(YEAR,p.DOB,date(:endDate)),date_sub(:endDate , interval )\n" +
                "  from kenyaemr_etl.etl_otz_enrollment e\n" +
                "         inner join kenyaemr_etl.etl_patient_demographics p on e.patient_id = p.patient_id\n" +
                "         left join (select d.patient_id, date(max(d.visit_date)) as latest_disc\n" +
                "                    from kenyaemr_etl.etl_patient_program_discontinuation d\n" +
                "                    where d.program_name = 'OTZ'\n" +
                "                    group by d.patient_id)d on e.patient_id = d.patient_id\n" +
                "  group by e.patient_id\n" +
                "  having \n" +
                "      latest_enr between date_sub(:startDate, interval) and date_sub(:endDate)\n" +
                "     and (disc_patient is null or latest_enr > d.latest_disc))c\n" +
                "inner join\n" +
                " (select\n" +
                "                          b.patient_id,\n" +
                "                          max(b.visit_date) as vl_date,\n" +
                "                          date_sub(:endDate , interval 12 MONTH),\n" +
                "                          mid(max(concat(b.visit_date,b.lab_test)),11) as lab_test,\n" +
                "                          if(mid(max(concat(b.visit_date,b.lab_test)),11) = 856, mid(max(concat(b.visit_date,b.test_result)),11), if(mid(max(concat(b.visit_date,b.lab_test)),11)=1305 and mid(max(concat(visit_date,test_result)),11) = 1302, \"LDL\",\"\")) as vl_result,\n" +
                "                                                                              mid(max(concat(b.visit_date, b.urgency)), 11)\n" +
                "                                                                              as urgency\n" +
                "                                                                              from (select x.patient_id  as patient_id,\n" +
                "                                                                                           x.visit_date  as visit_date,\n" +
                "                                                                                           x.lab_test    as lab_test,\n" +
                "                                                                                           x.test_result as test_result,\n" +
                "                                                                                           urgency       as urgency\n" +
                "                                                                                    from kenyaemr_etl.etl_laboratory_extract x\n" +
                "                                                                                    where x.lab_test in (1305, 856)\n" +
                "                                                                                    group by x.patient_id, x.visit_date\n" +
                "                                                                                    order by visit_date desc)b\n" +
                "                                                                              group by patient_id\n" +
                "                                                                              having max(visit_date) between\n" +
                "                                                                                  date_sub(:endDate, interval 12 MONTH) and date(:endDate))vl\n" +
                "                             on c.patient_id = vl.patient_id where vl.vl_result <"+vl+"  or vl_result = 'LDL')a;";
        cd.setName("ValidVLLess1000");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Patient with Valid VL < 1000");

        return cd;
    }
    public CohortDefinition patientEligibleForVL(Integer month){
        SqlCohortDefinition cd =  new SqlCohortDefinition();
        String sqlQuery = "select patient_id\n" +
                "from\n" +
                "  (\n" +
                "    select patient_id, date_started_art, inMCH, latest_mch_date, lastVLDate, lastVLDateWithinPeriod, dob,timestampdiff(YEAR,dob,:endDate) as age, if(lastVL !=null and (lastVL<1000 or lastVL=1302), 'Suppressed', if(lastVL !=null and lastVL>=1000, 'Unsuppressed', null)) lastVLWithinPeriod,lastVL,vl_result  from\n" +
                "      (select a.patient_id,\n" +
                "         a.date_started_art,\n" +
                "         mch.patient_id                                               as inMCH,\n" +
                "         mch.latest_mch_date                                          as latest_mch_date,\n" +
                "         mid(max(concat(l.visit_date, l.test_result)), 11)            as lastVL,\n" +
                "         left(max(concat(l.visit_date, l.test_result)), 10)           as lastVLDateWithinPeriod,\n" +
                "         left(max(concat(l_ever.visit_date, l_ever.test_result)), 10) as lastVLDate,\n" +
                "         if(l.lab_test = 856, mid(max(concat(l.visit_date, l.test_result)), 11), if(l.lab_test=1305 and mid(max(concat(l.visit_date, l.test_result)), 11) = 1302, \"LDL\",\"\")) as vl_result,\n" +
                "         a.dob                                                        as dob,\n" +
                "         encounter\n" +
                "       from (select e.patient_id, min(date_started) as date_started_art, p.DOB as dob,o.encounter_id as encounter from kenyaemr_etl.etl_otz_enrollment e\n" +
                "         inner join kenyaemr_etl.etl_patient_demographics p on e.patient_id = p.patient_id and p.voided = 0\n" +
                "         inner join kenyaemr_etl.etl_drug_event d on d.patient_id = e.patient_id and ifnull(d.voided, 0) = 0\n" +
                "         inner join openmrs.encounter enc on e.patient_id=enc.patient_id\n" +
                "         inner join openmrs.orders o on o.encounter_id =  enc.encounter_id\n" +
                "       where e.visit_date between date_sub(:startDate, interval "+month+" MONTH) and date_sub(:endDate, interval "+month+" MONTH)\n" +
                "       group by e.patient_id) a\n" +
                "         left join (select mch.patient_id,di.patient_id as disc_patient,max(date(mch.visit_date)) as latest_mch_date,max(date(di.visit_date)) as disc_date,di.program_name from kenyaemr_etl.etl_mch_enrollment mch\n" +
                "           left join kenyaemr_etl.etl_patient_program_discontinuation di on mch.patient_id = di.patient_id\n" +
                "         group by mch.patient_id\n" +
                "         having ((latest_mch_date > disc_date and di.program_name = 'MCH Mother') or di.patient_id is null)) mch on mch.patient_id = a.patient_id\n" +
                "         left join kenyaemr_etl.etl_laboratory_extract l on l.patient_id = a.patient_id and l.lab_test in (856, 1305)\n" +
                "         left join kenyaemr_etl.etl_laboratory_extract l_ever on l_ever.patient_id = a.patient_id and l_ever.lab_test in (856, 1305)\n" +
                "       group by a.patient_id) o\n" +
                "  ) e\n" +
                "where\n" +
                "  (\n" +
                "    (e.lastVL is null and  e.inMCH is null)\n" +
                "    or  (e.lastVL is null and  e.inMCH is not null and e.latest_mch_date >= e.date_started_art)\n" +
                "    or  (e.lastVL is not null  and (lastVL < 1000 or lastVL=1302)  and  timestampdiff(MONTH,e.lastVLDate, :endDate) >= 12)\n" +
                "    or  (e.lastVL is not null  and lastVL > 1000 and timestampdiff(MONTH,e.lastVLDate, :endDate) >= 3)\n" +
                "    or  (e.lastVL is not null and (lastVL < 1000 or lastVL=1302) and e.inMCH is not null and  timestampdiff(MONTH,e.lastVLDate, :endDate) >= 6)\n" +
                "  );";
        cd.setName("due for VL Test");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        return cd;
    }

    public CohortDefinition negativeTestANCCohortDefinition() {
        String sqlQuery = "select distinct v.patient_id from kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "          inner join kenyaemr_etl.etl_mch_enrollment e on e.patient_id= v.patient_id\n" +
                "          where e.hiv_status !=664 and v.final_test_result ='NEGATIVE' and\n" +
                "                v.visit_date between date(:startDate) AND date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("positiveTestAtANC");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Positive test at ANC  within the reporting period");
        return cd;
    }

    public CohortDefinition priorityPopulationByType(String ppType) {
        String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c where c.visit_date <= date(:endDate) group by c.client_id having mid(max(concat(c.visit_date,c.priority_population_type)),11) in (\""+ppType+"\");";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("priorityPopulationByType");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Priority pops by type");
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


    public CohortDefinition htsTbLinked(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("tbEnrollment",
                ReportUtils.map(moh731Cohorts.tbEnrollment(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("linkedHTS",
                ReportUtils.map(moh731PlusCohortLibrary.linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND tbEnrollment AND linkedHTS");
        return cd;
    }




    public CohortDefinition htsVctTested(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsVctTested",
                ReportUtils.map(datimCohorts.testedIntegratedVCT(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsVctTested");
        return cd;
    }

//Known positive for sti

//IPD HP



    public CohortDefinition htsIPDEligibleForTest(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("eligibleForTbTest",
                ReportUtils.map(htsEligible(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("screenedFromMobileDepartment",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsHospitalPatient",
                ReportUtils.map(htsHospitalPatient(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("eligibleForTbTest AND screenedFromMobileDepartment AND htsHospitalPatient");
        return cd;
    }

    public CohortDefinition IPDknownPositiveTest (Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("knownPositive",
                ReportUtils.map(htsKnownPositiveHTSScreening(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsHospitalPatient",
                ReportUtils.map(htsHospitalPatient(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND knownPositive AND htsHospitalPatient ");
        return cd;
    }

    public CohortDefinition htsIpdHPLinked (Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsLinked",
                ReportUtils.map(moh731PlusCohortLibrary.linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsHospitalPatient",
                ReportUtils.map(htsHospitalPatient(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsLinked AND htsHospitalPatient");
        return cd;
    }

    public CohortDefinition htsIpdHPNewPositive(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsTestedPositive",ReportUtils.map(htsNewlyTestedPositive(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsScreened", ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsHospitalPatient",ReportUtils.map(htsHospitalPatient(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsTestedPositive AND htsScreened AND htsHospitalPatient ");
        return cd;
    }
    public CohortDefinition htsIPDHPScreenedTest (Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsHospitalPatient",ReportUtils.map(htsHospitalPatient(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsHospitalPatient");
        return cd;
    }
    public CohortDefinition htsIPDHPTestedDone (Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsTested", ReportUtils.map(htsTested(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsHospitalPatient",ReportUtils.map(htsHospitalPatient(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsTested AND htsHospitalPatient ");
        return cd;
    }

    //TB
    public CohortDefinition knownPositiveTest (Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsTypeScreened(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("knownTbPositive",
                ReportUtils.map(htsKnownPositiveHTSScreening(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND knownTbPositive");
        return cd;
    }

    public CohortDefinition htsScreenedTest (Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",ReportUtils.map(htsNewScreened(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened");
        return cd;
    }
    public CohortDefinition htsTestedDone (Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsEligible(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsTested",
                ReportUtils.map(htsTested(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsTested");
        return cd;
    }
    public CohortDefinition htsLinked (Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsEligible(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsTBLinked",
                ReportUtils.map(moh731PlusCohortLibrary.linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsTBLinked");
        return cd;
    }
    public CohortDefinition htsEligibleForTest(Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("eligibleForTbTest",
                ReportUtils.map(htsEligible(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("screenedFromMobileDepartment",
                ReportUtils.map(htsTypeScreened(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("eligibleForTbTest AND screenedFromMobileDepartment");
        return cd;
    }
    public CohortDefinition htsNewPositiveMobile(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsTestedPositive",
                ReportUtils.map(htsNewlyTestedPositive(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsTestedMobile",
                ReportUtils.map(htsTypeScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsTestedPositive AND htsTestedMobile");
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
        String sqlQuery = "SELECT e.patient_id " +
                "FROM kenyaemr_etl.etl_tb_enrollment e " +
                "LEFT JOIN (" +
                "   SELECT f.patient_id " +
                "   FROM kenyaemr_etl.etl_mch_enrollment f " +
                "   JOIN kenyaemr_etl.etl_mch_antenatal_visit v ON f.patient_id = v.patient_id " +
                "   WHERE (f.hiv_status = 1067) " +
                "   AND v.visit_date BETWEEN DATE_SUB(:endDate, INTERVAL 3 MONTH) AND DATE(:endDate) " +
                "   GROUP BY f.patient_id " +
                ") AS subQuery ON e.patient_id = subQuery.patient_id " +
                "INNER JOIN kenyaemr_etl.etl_hiv_enrollment he ON he.patient_id = e.patient_id AND he.visit_date > e.visit_date " +
                "WHERE DATE(e.visit_date) BETWEEN :startDate AND :endDate;";
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


    public CohortDefinition viralSuppression(Integer vlLess, Integer vlMore, Integer month){
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select distinct e.patient_id\n" +
                "                from kenyaemr_etl.etl_otz_enrollment e\n" +
                "                   inner join\n" +
                "                 (\n" +
                "                   select\n" +
                "                     patient_id,\n" +
                "                     visit_date,\n" +
                "                     if(lab_test = 856, test_result, if(lab_test=1305 and test_result = 1302, \"LDL\",\"\")) as vl_result,\n" +
                "     urgency,order_id\n" +
                "   from kenyaemr_etl.etl_laboratory_extract\n" +
                "   where lab_test in (1305, 856)  and visit_date between date_sub(:endDate , interval "+month+" MONTH) and date(:endDate)\n" +
                " ) vl_result on vl_result.patient_id = e.patient_id\n" +
                " inner join openmrs.orders o on o.order_id =vl_result.order_id\n" +
                " where vl_result.order_id is not null\n" +
                "       and e.visit_date between date_sub(:startDate, interval "+month+" MONTH) and date_sub(:endDate, interval "+month+" MONTH)\n" +
                "       and o.order_reason in(843,163523)\n" +
                "group by e.patient_id\n" +
                "having mid(max(concat(vl_result.visit_date, vl_result.vl_result)), 11)=\"LDL\" or mid(max(concat(vl_result.visit_date, vl_result.vl_result)), 11)>="+vlLess+" AND mid(max(concat(vl_result.visit_date, vl_result.vl_result)), 11)<=" +vlMore+";";
        cd.setName("PatientWithRepeatVLLessThan400");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("on Therapy At 12 Months");

        return cd;
    }

    //}htsNewPositiveMobile,htsLinkedToHAARTMobile,moh731Cohorts.htsNumberTestedPositive,moh731Cohorts.htsAllNumberTestedPositiveAndLinked()


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
    public CohortDefinition ncdPatient() {
        SqlCohortDefinition cd = new SqlCohortDefinition();

        String sqlQuery = "select ci.patient_id,\n" +
                "group_concat(distinct case ci.chronic_illness\n" +
                "              when 149019 then 'Alzheimers Disease and other Dementias'\n" +
                "              when 148432 then 'Arthritis'\n" +
                "              when 153754 then 'Asthma'\n" +
                "              when 159351 then 'Cancer'\n" +
                "              when 119270 then 'Cardiovascular diseases'\n" +
                "              when 120637 then 'Chronic Hepatitis'\n" +
                "              when 145438 then 'Chronic Kidney Disease'\n" +
                "              when 1295 then 'Chronic Obstructive Pulmonary Disease(COPD)'\n" +
                "              when 120576 then 'Chronic Renal Failure'\n" +
                "              when 119692 then 'Cystic Fibrosis'\n" +
                "              when 120291 then 'Deafness and Hearing impairment'\n" +
                "              when 119481 then 'Diabetes'\n" +
                "              when 118631 then 'Endometriosis'\n" +
                "              when 117855 then 'Epilepsy'\n" +
                "              when 117789 then 'Glaucoma'\n" +
                "              when 139071 then 'Heart Disease'\n" +
                "              when 115728 then 'Hyperlipidaemia'\n" +
                "              when 117399 then 'Hypertension'\n" +
                "              when 117321 then 'Hypothyroidism'\n" +
                "              when 151342 then 'Mental illness'\n" +
                "              when 133687 then 'Multiple Sclerosis'\n" +
                "              when 115115 then 'Obesity'\n" +
                "              when 114662 then 'Osteoporosis'\n" +
                "              when 117703 then 'Sickle Cell Anaemia'\n" +
                "              when 118976 then 'Thyroid disease'\n" +
                "              when 141623 then 'Dyslipidemia'\n" +
                "              end SEPARATOR\n" +
                "          ',') as ChronicIllness\n" +
                "from kenyaemr_etl.etl_allergy_chronic_illness ci where ci.visit_date <= date(:endDate)\n" +
                "group by ci.patient_id;";
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
        public CohortDefinition htsTestedPositivePostANC(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsTypeScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsTestedPositivePostANC",
                ReportUtils.map(datimCohorts.testedPositivePmtctPostANC1(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsTestedPositivePostANC");
        return cd;
    }

    public CohortDefinition htsInitialHivTestingatPNC(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsTypeScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsTestedByDepartment",
                ReportUtils.map(htsTestedByDepartment(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsKnownPositiveHTSScreening",
                ReportUtils.map(htsKnownPositiveHTSScreening(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsTestedByDepartment AND htsKnownPositiveHTSScreening");
        return cd;
    }

    public CohortDefinition htsRetestingHivTestingAtPNC(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsTypeScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsTestedByDepartment",
                ReportUtils.map(htsRetestedDepartment(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsKnownPositiveHTSScreening",
                ReportUtils.map(htsKnownPositiveHTSScreening(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsTestedByDepartment AND htsKnownPositiveHTSScreening");
        return cd;
    }

    public CohortDefinition htsInitialTestingANC(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("testedNegativePmtctPostANC1",
                ReportUtils.map(datimCohorts.testedNegativePmtctPostANC1(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsTestedPositivePostANC",
                ReportUtils.map(datimCohorts.testedPositivePmtctPostANC1(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND (testedNegativePmtctPostANC1 OR htsTestedPositivePostANC)");
        return cd;
    }



    public CohortDefinition htsFirstANCKnownPositive(Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsTypeScreened(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsFirstANCKnownPositive",
                ReportUtils.map(moh731Cohorts.knownPositiveAtFirstANC(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsFirstANCKnownPositive");
        return cd;
    }
    public CohortDefinition htsFirstANCRecentKnownNegative(Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsTypeScreened(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("negativeHivStatusBeforeAnc1",
                ReportUtils.map(datimCohorts.negativeHivStatusBeforeAnc1(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND negativeHivStatusBeforeAnc1");
        return cd;
    }


    public CohortDefinition htsNewlyTestedPositivePmtctANC(Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsTypeScreened(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("testedPositivePmtctANC1",
                ReportUtils.map(datimCohorts.testedPositivePmtctANC1(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND testedPositivePmtctANC1");
        return cd;
    }
    public CohortDefinition unKnownStatusAtANC(Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsTypeScreened(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("unKnownStatusAtANC",
                ReportUtils.map(datimCohorts.unKnownStatusAtANC(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND unKnownStatusAtANC");
        return cd;
    }


    public CohortDefinition htsPositiveTestANC(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsPositiveTestANC",
                ReportUtils.map(pmtctCohortLibrary.positiveTestANCCohortDefinition(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsPositiveTestANC");
        return cd;
    }

    public CohortDefinition htsFirstANCVisitMchmsAntenatal(Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsTypeScreened(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsFirstANCVisitMchmsAntenatal",
                ReportUtils.map(datimCohorts.unKnownStatusAtANC(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsFirstANCVisitMchmsAntenatal");
        return cd;
    }
//
    public CohortDefinition seenANCVisitMchmsAntenatal(Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsTypeScreened(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("seenANCVisitMchmsAntenatal",
                ReportUtils.map(moh731Cohorts.firstANCVisitMchmsAntenatal(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND seenANCVisitMchmsAntenatal");
        return cd;
    }

    public CohortDefinition htsHAARTInANC(Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsTypeScreened(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("startedHAARTInANC",
                ReportUtils.map(moh731Cohorts.startedHAARTAtANC(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND startedHAARTInANC");
        return cd;
    }
    public CohortDefinition initialTestANC(Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsTypeScreened(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("initialTestANC",
                ReportUtils.map(pmtctCohortLibrary.initialTestANCCohortDefinition(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND initialTestANC");
        return cd;
    }


    public CohortDefinition htsNegativeTestANCCohortDefinition(Integer department) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsScreened(department), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("negativeTestANCCohortDefinition",
                ReportUtils.map(datimCohorts.negativeHivStatusBeforeAnc1(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND negativeTestANCCohortDefinition");
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



    public CohortDefinition snsHivPositive(String kpType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("snsHivPositive",
                ReportUtils.map(kpifCohorts.kpPrepCurrDice(kpType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsNewlyTestedPositive",
                ReportUtils.map(htsNewlyTestedPositive(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("snsHivPositive AND htsNewlyTestedPositive");
        return cd;
    }

    public CohortDefinition htsPopulationTypeScreened(Integer kpType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("snsHivPositive",
                ReportUtils.map(htsPopulationType(kpType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsNewlyTestedPositive",
                ReportUtils.map(htsNewlyTestedPositive(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("snsHivPositive AND htsNewlyTestedPositive");
        return cd;
    }

    public CohortDefinition snsLinked(String kpType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("snsHivPositive",
                ReportUtils.map(kpifCohorts.kpPrepCurrDice(kpType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("linkedHTS",
                ReportUtils.map(moh731PlusCohortLibrary.linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("snsHivPositive AND linkedHTS");
        return cd;
    }

    public CohortDefinition htsPopulationTypeLinked(Integer kpType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("snsHivPositive",
                ReportUtils.map(htsPopulationType(kpType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsNewlyTestedPositive",
                ReportUtils.map(moh731PlusCohortLibrary.linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("snsHivPositive AND htsNewlyTestedPositive");
        return cd;
    }
    public CohortDefinition snsTested(String kpType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("snsHivPositive",
                ReportUtils.map(kpifCohorts.kpPrepCurrDice(kpType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsTested",
                ReportUtils.map(htsTested(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("snsHivPositive AND htsTested");
        return cd;
    }

    public CohortDefinition htsPopulationTypeTested(Integer kpType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("snsHivPositive",
                ReportUtils.map(htsPopulationType(kpType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsNewlyTestedPositive",
                ReportUtils.map(htsTested(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("snsHivPositive AND htsNewlyTestedPositive");
        return cd;
    }

    public CohortDefinition snsHivNegative(String kpType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("snsHivPositive",
                ReportUtils.map(kpifCohorts.kpPrepCurrDice(kpType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsNewlyTestedNegative",
                ReportUtils.map(htsNewlyTestedNegative(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("snsHivPositive AND htsNewlyTestedNegative");
        return cd;
    }
    public CohortDefinition htsPopulationTypeNegativeClient(Integer kpType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("snsHivPositive",
                ReportUtils.map(htsPopulationType(kpType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsNewlyTestedNegative",
                ReportUtils.map(htsNewlyTestedNegative(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("snsHivPositive AND htsNewlyTestedNegative");
        return cd;
    }
    public CohortDefinition patientsLTFU() {
        CalculationCohortDefinition calcLtf = new CalculationCohortDefinition(new LostToFollowUpCalculation());
        calcLtf.setName("lost to follow up");
        calcLtf.addParameter(new Parameter("onDate", "On Date", Date.class));
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("ltf", ReportUtils.map(calcLtf, "onDate=${onOrBefore}"));
        cd.setCompositionString("ltf");
        return cd;
    }
    public CohortDefinition patientDied() {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
        cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
        cd.addSearch("currentlyInCare", ReportUtils.map(moh731Cohorts.currentlyInCare(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("deceased", ReportUtils.map(commonCohorts.deceasedPatients(), "onDate=${onOrBefore}"));
        cd.setCompositionString("currentlyInCare AND deceased");
        return cd;
    }
    public CohortDefinition transferOut() {
        CalculationCohortDefinition calcTout = new CalculationCohortDefinition(new IsTransferOutCalculation());
        calcTout.setName("to patients");
        calcTout.addParameter(new Parameter("onDate", "On Date", Date.class));
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addSearch("to", ReportUtils.map(calcTout, "onDate=${onOrBefore}"));
        cd.setCompositionString("to");
        return cd;
    }
    public CohortDefinition transferIn() {
        CalculationCohortDefinition calcTin = new CalculationCohortDefinition(new IsTransferInCalculation());
        calcTin.setName("to patients");
        calcTin.addParameter(new Parameter("onDate", "On Date", Date.class));
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addSearch("calcTin", ReportUtils.map(calcTin, "onDate=${onOrBefore}"));
        cd.setCompositionString("calcTin");
        return cd;
    }
    public CohortDefinition viralSuppressionBetween400To900(Integer vlLess, Integer vlMore, Integer months) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
        cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
        cd.addSearch("viralSuppression", ReportUtils.map(viralSuppression(vlLess,vlMore,months), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("viralSuppression");
        return cd;
    }
    public CohortDefinition patientWithValidVWithLDL(Integer vl) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
        cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
        cd.addSearch("patientWithValidVLLess200", ReportUtils.map(patientWithValidVLANDLDL(vl), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("patientWithValidVLLess200");
        return cd;
    }



    public CohortDefinition patientWithChronicillness(Integer chronicillness) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
        cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
        cd.addSearch("chronicillness", ReportUtils.map(chronicillness(chronicillness), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("chronicillness");
        return cd;
    }
    public CohortDefinition patientEligibleForVl(Integer months) {

        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
        cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
        cd.addSearch("patientWithValidVLMoreThan1000", ReportUtils.map(patientEligibleForVL(months), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("patientWithValidVLMoreThan1000");
        return cd;
    }


    public CohortDefinition OnDTGRegimen() {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("currentlyOnART", ReportUtils.map(datimCohorts.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("patientsOnDTGRegimen", ReportUtils.map(publicHealthActionCohortLibrary.patientsOnDTGRegimen(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("ineligibleForDTGRegimen", ReportUtils.map(publicHealthActionCohortLibrary.ineligibleForDTGRegimen(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("currentlyOnART AND  patientsOnDTGRegimen AND ineligibleForDTGRegimen");
        return cd;
    }
    public CohortDefinition ppPrevByType(String ppType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("priorityPopulationByType",ReportUtils.map(priorityPopulationByType(ppType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("kpPrevReceivedService",ReportUtils.map(datimCohorts.kpPrevReceivedService(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("kpPrevOfferedHTSServices",ReportUtils.map(datimCohorts.kpPrevOfferedHTSServices(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("kpPrevKnownPositiveSql",ReportUtils.map(datimCohorts.kpPrevKnownPositiveSql(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("priorityPopulationByType  AND ((kpPrevReceivedService AND kpPrevOfferedHTSServices) OR (kpPrevReceivedService AND kpPrevKnownPositiveSql))");
        return cd;

    }
    public CohortDefinition pmtcTested() {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("testedPmtct",ReportUtils.map(datimCohorts.testedPmtct(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("testedPmtct");
        return cd;

    }
    public CohortDefinition fnsEligibleForTest(Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("eligibleForTbTest",
                ReportUtils.map(htsTestStrategy(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("screenedFromMobileDepartment",
                ReportUtils.map(htsTypeScreened(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("eligibleForTbTest AND screenedFromMobileDepartment");
        return cd;
    }

    public CohortDefinition indexLinked (Integer entryPoint) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("htsScreened",
                ReportUtils.map(htsTestStrategy(entryPoint), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("htsTBLinked",
                ReportUtils.map(moh731PlusCohortLibrary.linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("htsScreened AND htsTBLinked");
        return cd;
    }

    public CohortDefinition fnsTestestedfnsTestested() {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("hivPositiveContact",
                ReportUtils.map(datimCohorts.hivPositiveContact(), "startDate=${startDate},endDate=${endDate}"));

        cd.addSearch("hivNegativeContact",
                ReportUtils.map(datimCohorts.hivNegativeContact(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("hivPositiveContact AND hivNegativeContact");
        return cd;
    }
    public CohortDefinition receivingSelfTestKits (String kpTest) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("receivingSelfTestKits",
                ReportUtils.map(moh731PlusCohortLibrary.receivingSelfTestKits(kpTest), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("receivingSelfTestKits");
        return cd;
    }
    public CohortDefinition receivingSelfTestKitsPositive (String kpTest) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("receivingSelfTestKits",
                ReportUtils.map(moh731PlusCohortLibrary.receivingSelfTestKits(kpTest), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("knownPositive",
                ReportUtils.map(htsKnownPositiveHTSScreening(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("receivingSelfTestKits AND knownPositive");
        return cd;
    }
}

