/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.builder.hiv.threepm;

import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH731Greencard.ETLMoh731GreenCardIndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimIndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.otz.ETLOtzCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.otz.ETLOtzIndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.pmtct.PMTCTIndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.hiv.art.ArtIndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.threepm.ThreePMIndicatorLibrary;
import org.openmrs.module.kenyaemr.util.HtsConstants;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Report builder for 3PM
 */
@Component
@Builds({"kenyaemr.etl.common.report.threePM"})
public class ThreePMReportBuilder extends AbstractReportBuilder {
    @Autowired
    private CommonDimensionLibrary commonDimensions;

    @Autowired
    private ThreePMIndicatorLibrary threePMIndicators;

    @Autowired
    private ETLMoh731GreenCardIndicatorLibrary moh731GreenCardIndicators;
    @Autowired
    private ArtIndicatorLibrary artIndicators;
    @Autowired
    private DatimCohortLibrary datimCohortLibrary;
    @Autowired
    private ETLOtzCohortLibrary otzCohorts;
    @Autowired
    ETLOtzIndicatorLibrary etlOtzIndicatorLibrary;
    @Autowired
    private DatimIndicatorLibrary datimIndicators;
    @Autowired
    private PMTCTIndicatorLibrary pnc;

    //Key Populations
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String FSW = "FSW";
    public static final String MSM = "MSM";
    public static final String PWID = "PWID";
    public static final String TG = "Transgender";
    public static final String IN_PRISONS = "People in prison and other closed settings";

    //Priority Populations
    public static final String SEX_WORKERS_CLIENTS = "Clients of sex workers";
    public static final String DISPLACED_PERSONS = "Displaced persons";
    public static final String FISHING_COMMUNITIES = "Fishing communities";
    public static final String MILITARY_UNIFORMED_SERVICES = "Military and other uniformed services";
    public static final String MOBILE_POPULATIONS = "Mobile populations";
    public static final String NON_INJECTING_DRUG_USER = "Non-injecting drug user";
    public static final String OTHER = "Other";

    /*Age/sex disaggregations*/
    ColumnParameters female_pwid = new ColumnParameters(null, "Female,PWID", "gender=F|kpType=PWID");
    ColumnParameters male_pwid = new ColumnParameters(null, "Male,PWID", "gender=M|kpType=PWID");
    ColumnParameters infant = new ColumnParameters(null, "<1, Male and Female", "age=<1");
    ColumnParameters fUnder1 = new ColumnParameters(null, "<1", "gender=F|age=<1");

    ColumnParameters f1To9 = new ColumnParameters(null, "1-9", "gender=F|age=1-9");
    ColumnParameters all1_to9 = new ColumnParameters(null, "1-9, Male and female", "age=1-9");
    ColumnParameters f0_to14 = new ColumnParameters(null, "0-14, Female", "gender=F|age=0-14");
    ColumnParameters m0_to14 = new ColumnParameters(null, "0-14, Male", "gender=M|age=0-14");
    ColumnParameters f10_to14 = new ColumnParameters(null, "10-14, Female", "gender=F|age=10-14");
    ColumnParameters m10_to14 = new ColumnParameters(null, "10-14, Male", "gender=M|age=10-14");
    ColumnParameters f15_to19 = new ColumnParameters(null, "15-19, Female", "gender=F|age=15-19");
    ColumnParameters m15_to19 = new ColumnParameters(null, "15-19, Male", "gender=M|age=15-19");
    ColumnParameters f20_to24 = new ColumnParameters(null, "20-24, Female", "gender=F|age=20-24");
    ColumnParameters m20_to24 = new ColumnParameters(null, "20-24, Male", "gender=M|age=20-24");
    ColumnParameters f25_to29 = new ColumnParameters(null, "25-29, Female", "gender=F|age=25-29");
    ColumnParameters m25_to29 = new ColumnParameters(null, "25-29, Male", "gender=M|age=25-29");
    ColumnParameters f30_to34 = new ColumnParameters(null, "30-34, Female", "gender=F|age=30-34");
    ColumnParameters m30_to34 = new ColumnParameters(null, "30-34, Male", "gender=M|age=30-34");
    ColumnParameters f35_to39 = new ColumnParameters(null, "35-39, Female", "gender=F|age=35-39");
    ColumnParameters m35_to39 = new ColumnParameters(null, "35-39, Male", "gender=M|age=35-39");
    ColumnParameters f40_to44 = new ColumnParameters(null, "40-44, Female", "gender=F|age=40-44");
    ColumnParameters m40_to44 = new ColumnParameters(null, "40-44, Male", "gender=M|age=40-44");
    ColumnParameters f40_to49 = new ColumnParameters(null, "40-49, Female", "gender=F|age=40-49");
    ColumnParameters m40_to49 = new ColumnParameters(null, "40-49, Male", "gender=M|age=40-49");
    ColumnParameters f45_to49 = new ColumnParameters(null, "45-49, Female", "gender=F|age=45-49");
    ColumnParameters m45_to49 = new ColumnParameters(null, "45-49, Male", "gender=M|age=45-49");
    ColumnParameters f25AndAbove = new ColumnParameters(null, "25+, Female", "gender=F|age=25+");
    ColumnParameters m25AndAbove = new ColumnParameters(null, "25+, Male", "gender=M|age=25+");
    ColumnParameters fAbove50 = new ColumnParameters(null, "50+, Female", "gender=F|age=50+");
    ColumnParameters mAbove50 = new ColumnParameters(null, "50+, Male", "gender=M|age=50+");
    ColumnParameters all10_to14 = new ColumnParameters(null, "10-14, Male and female", "age=10-14");
    ColumnParameters all15_to19 = new ColumnParameters(null, "15-19, Male and female", "age=15-19");
    ColumnParameters all120_to24 = new ColumnParameters(null, "20-24, Male and female", "age=20-24");
    ColumnParameters all25_to29 = new ColumnParameters(null, "25-29, Male and female", "age=25-29");
    ColumnParameters all30_to34 = new ColumnParameters(null, "30-34, Male and female", "age=30-34");
    ColumnParameters all35_to39 = new ColumnParameters(null, "35-39, Male and female", "age=35-39");
    ColumnParameters all40_to44 = new ColumnParameters(null, "40-44, Male and female", "age=40-44");
    ColumnParameters all45_to49 = new ColumnParameters(null, "45-49, Male and female", "age=45-49");
    ColumnParameters allAbove50 = new ColumnParameters(null, "50+, Male and female", "age=50+");

//HTS -SNS
    ColumnParameters f10_to14_fsw = new ColumnParameters(null, "10-14,Female FSW", "gender=F|age=10-14");
    ColumnParameters m10_to14_fsw = new ColumnParameters(null, "10-14,Male FSW ", "gender=M|age=10-14");
    ColumnParameters f1To4_fsw = new ColumnParameters(null, "1-4, Female FSW ", "gender=F|age=1-4");
    ColumnParameters m1To4_fsw = new ColumnParameters(null, "1-4, Male FSW", "gender=M|age=1-4");
    ColumnParameters f15To19_fsw = new ColumnParameters(null, "15-19, Female FSW ", "gender=F|age=15-19");
    ColumnParameters m15To19_fsw = new ColumnParameters(null, "15-19, Male FSW", "gender=M|age=15-19");
    ColumnParameters fUnder1_fsw = new ColumnParameters(null, "<1, Female FSW", "gender=F|age=<1");
    ColumnParameters mUnder1_fsw = new ColumnParameters(null, "<1, Male FSW", "gender=M|age=<1");
    ColumnParameters f20To24_fsw = new ColumnParameters(null, "20-24, Female FSW ", "gender=F|age=20-24");
    ColumnParameters m20To24_fsw = new ColumnParameters(null, "20-24, Male FSW", "gender=M|age=20-24");
    ColumnParameters f25To29_fsw = new ColumnParameters(null, "25-29, Female FSW ", "gender=F|age=25-29");
    ColumnParameters m25To29_fsw = new ColumnParameters(null, "25-29, Male FSW", "gender=M|age=25-29");
    ColumnParameters f30To34_fsw = new ColumnParameters(null, "30-34, Female FSW ", "gender=F|age=30-34");
    ColumnParameters m30To34_fsw = new ColumnParameters(null, "30-34, Male FSW", "gender=M|age=30-34");
    ColumnParameters f35To39_fsw = new ColumnParameters(null, "35-39, Female FSW ", "gender=F|age=35-39");
    ColumnParameters m35To39_fsw = new ColumnParameters(null, "35-39, Male FSW", "gender=M|age=35-39");
    ColumnParameters f40To44_fsw = new ColumnParameters(null, "40-44, Female FSW ", "gender=F|age=40-44");
    ColumnParameters m40To44_fsw = new ColumnParameters(null, "40-44, Male FSW", "gender=M|age=40-44");
    ColumnParameters f45To49_fsw = new ColumnParameters(null, "45-49, Female FSW ", "gender=F|age=45-49");
    ColumnParameters m45To49_fsw = new ColumnParameters(null, "45-49, Male FSW", "gender=M|age=45-49");
    ColumnParameters f50AndAbove_fsw = new ColumnParameters(null, "50+, Female FSW", "gender=F|age=50+");
    ColumnParameters m50AndAbove_fsw = new ColumnParameters(null, "50+, Male FSW", "gender=M|age=50+");
    ColumnParameters f5To9_fsw = new ColumnParameters(null, "5-9, Female FSW ", "gender=F|age=5-9");
    ColumnParameters m5To9_fsw = new ColumnParameters(null, "5-9, Male FSW", "gender=M|age=5-9");


    ColumnParameters f10_to14_general_population = new ColumnParameters(null, "10-14,Female General Population", "gender=F|age=10-14");
    ColumnParameters m10_to14_general_population = new ColumnParameters(null, "10-14,Male General Population ", "gender=M|age=10-14");
    ColumnParameters f1To4_general_population = new ColumnParameters(null, "1-4, Female General Population ", "gender=F|age=1-4");
    ColumnParameters m1To4_general_population = new ColumnParameters(null, "1-4, Male General Population", "gender=M|age=1-4");
    ColumnParameters f15To19_general_population = new ColumnParameters(null, "15-19, Female General Population ", "gender=F|age=15-19");
    ColumnParameters m15To19_general_population = new ColumnParameters(null, "15-19, Male General Population", "gender=M|age=15-19");
    ColumnParameters fUnder1_general_population = new ColumnParameters(null, "<1, Female General Population", "gender=F|age=<1");
    ColumnParameters mUnder1_general_population = new ColumnParameters(null, "<1, Male General Population", "gender=M|age=<1");
    ColumnParameters f20To24_general_population = new ColumnParameters(null, "20-24, Female General Population ", "gender=F|age=20-24");
    ColumnParameters m20To24_general_population = new ColumnParameters(null, "20-24, Male General Population", "gender=M|age=20-24");
    ColumnParameters f25To29_general_population = new ColumnParameters(null, "25-29, Female General Population ", "gender=F|age=25-29");
    ColumnParameters m25To29_general_population = new ColumnParameters(null, "25-29, Male General Population", "gender=M|age=25-29");
    ColumnParameters f30To34_general_population = new ColumnParameters(null, "30-34, Female General Population ", "gender=F|age=30-34");
    ColumnParameters m30To34_general_population = new ColumnParameters(null, "30-34, Male General Population", "gender=M|age=30-34");
    ColumnParameters f35To39_general_population = new ColumnParameters(null, "35-39, Female General Population ", "gender=F|age=35-39");
    ColumnParameters m35To39_general_population = new ColumnParameters(null, "35-39, Male General Population", "gender=M|age=35-39");
    ColumnParameters f40To44_general_population = new ColumnParameters(null, "40-44, Female General Population ", "gender=F|age=40-44");
    ColumnParameters m40To44_general_population = new ColumnParameters(null, "40-44, Male General Population", "gender=M|age=40-44");
    ColumnParameters f45To49_general_population = new ColumnParameters(null, "45-49, Female General Population ", "gender=F|age=45-49");
    ColumnParameters m45To49_general_population = new ColumnParameters(null, "45-49, Male General Population", "gender=M|age=45-49");
    ColumnParameters f50AndAbove_general_population = new ColumnParameters(null, "50+, Female General Population", "gender=F|age=50+");
    ColumnParameters m50AndAbove_general_population = new ColumnParameters(null, "50+, Male General Population", "gender=M|age=50+");
    ColumnParameters f5To9_general_population = new ColumnParameters(null, "5-9, Female General Population ", "gender=F|age=5-9");
    ColumnParameters m5To9_general_population = new ColumnParameters(null, "5-9, Male General Population", "gender=M|age=5-9");

    ColumnParameters f10_to14_msm = new ColumnParameters(null, "10-14,Female MSM", "gender=F|age=10-14");
    ColumnParameters m10_to14_msm = new ColumnParameters(null, "10-14,Male MSM ", "gender=M|age=10-14");
    ColumnParameters f1To4_msm = new ColumnParameters(null, "1-4, Female MSM ", "gender=F|age=1-4");
    ColumnParameters m1To4_msm = new ColumnParameters(null, "1-4, Male MSM", "gender=M|age=1-4");
    ColumnParameters f15To19_msm = new ColumnParameters(null, "15-19, Female MSM ", "gender=F|age=15-19");
    ColumnParameters m15To19_msm = new ColumnParameters(null, "15-19, Male MSM", "gender=M|age=15-19");
    ColumnParameters fUnder1_msm = new ColumnParameters(null, "<1, Female MSM", "gender=F|age=<1");
    ColumnParameters mUnder1_msm = new ColumnParameters(null, "<1, Male MSM", "gender=M|age=<1");
    ColumnParameters f20To24_msm = new ColumnParameters(null, "20-24, Female MSM ", "gender=F|age=20-24");
    ColumnParameters m20To24_msm = new ColumnParameters(null, "20-24, Male MSM", "gender=M|age=20-24");
    ColumnParameters f25To29_msm = new ColumnParameters(null, "25-29, Female MSM ", "gender=F|age=25-29");
    ColumnParameters m25To29_msm = new ColumnParameters(null, "25-29, Male MSM", "gender=M|age=25-29");
    ColumnParameters f30To34_msm = new ColumnParameters(null, "30-34, Female MSM ", "gender=F|age=30-34");
    ColumnParameters m30To34_msm = new ColumnParameters(null, "30-34, Male MSM", "gender=M|age=30-34");
    ColumnParameters f35To39_msm = new ColumnParameters(null, "35-39, Female MSM ", "gender=F|age=35-39");
    ColumnParameters m35To39_msm = new ColumnParameters(null, "35-39, Male MSM", "gender=M|age=35-39");
    ColumnParameters f40To44_msm = new ColumnParameters(null, "40-44, Female MSM ", "gender=F|age=40-44");
    ColumnParameters m40To44_msm = new ColumnParameters(null, "40-44, Male MSM", "gender=M|age=40-44");
    ColumnParameters f45To49_msm = new ColumnParameters(null, "45-49, Female MSM ", "gender=F|age=45-49");
    ColumnParameters m45To49_msm = new ColumnParameters(null, "45-49, Male MSM", "gender=M|age=45-49");
    ColumnParameters f50AndAbove_msm = new ColumnParameters(null, "50+, Female MSM", "gender=F|age=50+");
    ColumnParameters m50AndAbove_msm = new ColumnParameters(null, "50+, Male MSM", "gender=M|age=50+");
    ColumnParameters f5To9_msm = new ColumnParameters(null, "5-9, Female MSM ", "gender=F|age=5-9");
    ColumnParameters m5To9_msm = new ColumnParameters(null, "5-9, Male MSM", "gender=M|age=5-9");

    ColumnParameters f15To19_people_in_prison = new ColumnParameters(null, "15-19, Female People in prison ", "gender=F|age=15-19");
    ColumnParameters m15To19_people_in_prison = new ColumnParameters(null, "15-19, Male People in prison", "gender=M|age=15-19");
    ColumnParameters f20To24_people_in_prison = new ColumnParameters(null, "20-24, Female People in prison ", "gender=F|age=20-24");
    ColumnParameters m20To24_people_in_prison = new ColumnParameters(null, "20-24, Male People in prison", "gender=M|age=20-24");
    ColumnParameters f25To29_people_in_prison = new ColumnParameters(null, "25-29, Female People in prison ", "gender=F|age=25-29");
    ColumnParameters m25To29_people_in_prison = new ColumnParameters(null, "25-29, Male People in prison", "gender=M|age=25-29");
    ColumnParameters f30To34_people_in_prison = new ColumnParameters(null, "30-34, Female People in prison ", "gender=F|age=30-34");
    ColumnParameters m30To34_people_in_prison = new ColumnParameters(null, "30-34, Male People in prison", "gender=M|age=30-34");
    ColumnParameters f35To39_people_in_prison = new ColumnParameters(null, "35-39, Female People in prison ", "gender=F|age=35-39");
    ColumnParameters m35To39_people_in_prison = new ColumnParameters(null, "35-39, Male People in prison", "gender=M|age=35-39");
    ColumnParameters f40To44_people_in_prison = new ColumnParameters(null, "40-44, Female People in prison ", "gender=F|age=40-44");
    ColumnParameters m40To44_people_in_prison = new ColumnParameters(null, "40-44, Male People in prison", "gender=M|age=40-44");
    ColumnParameters f45To49_people_in_prison = new ColumnParameters(null, "45-49, Female People in prison ", "gender=F|age=45-49");
    ColumnParameters m45To49_people_in_prison = new ColumnParameters(null, "45-49, Male People in prison", "gender=M|age=45-49");
    ColumnParameters f50AndAbove_people_in_prison = new ColumnParameters(null, "50+, Female People in prison", "gender=F|age=50+");
    ColumnParameters m50AndAbove_people_in_prison = new ColumnParameters(null, "50+, Male People in prison", "gender=M|age=50+");


    ColumnParameters f10_to14_pwid = new ColumnParameters(null, "10-14,Female PWID", "gender=F|age=10-14");
    ColumnParameters m10_to14_pwid = new ColumnParameters(null, "10-14,Male PWID ", "gender=M|age=10-14");
    ColumnParameters f1To4_pwid = new ColumnParameters(null, "1-4, Female PWID ", "gender=F|age=1-4");
    ColumnParameters m1To4_pwid = new ColumnParameters(null, "1-4, Male PWID", "gender=M|age=1-4");
    ColumnParameters f15To19_pwid = new ColumnParameters(null, "15-19, Female PWID ", "gender=F|age=15-19");
    ColumnParameters m15To19_pwid = new ColumnParameters(null, "15-19, Male PWID", "gender=M|age=15-19");
    ColumnParameters fUnder1_pwid = new ColumnParameters(null, "<1, Female PWID", "gender=F|age=<1");
    ColumnParameters mUnder1_pwid = new ColumnParameters(null, "<1, Male PWID", "gender=M|age=<1");
    ColumnParameters f20To24_pwid = new ColumnParameters(null, "20-24, Female PWID ", "gender=F|age=20-24");
    ColumnParameters m20To24_pwid = new ColumnParameters(null, "20-24, Male PWID", "gender=M|age=20-24");
    ColumnParameters f25To29_pwid = new ColumnParameters(null, "25-29, Female PWID ", "gender=F|age=25-29");
    ColumnParameters m25To29_pwid = new ColumnParameters(null, "25-29, Male PWID", "gender=M|age=25-29");
    ColumnParameters f30To34_pwid = new ColumnParameters(null, "30-34, Female PWID ", "gender=F|age=30-34");
    ColumnParameters m30To34_pwid = new ColumnParameters(null, "30-34, Male PWID", "gender=M|age=30-34");
    ColumnParameters f35To39_pwid = new ColumnParameters(null, "35-39, Female PWID ", "gender=F|age=35-39");
    ColumnParameters m35To39_pwid = new ColumnParameters(null, "35-39, Male PWID", "gender=M|age=35-39");
    ColumnParameters f40To44_pwid = new ColumnParameters(null, "40-44, Female PWID ", "gender=F|age=40-44");
    ColumnParameters m40To44_pwid = new ColumnParameters(null, "40-44, Male PWID", "gender=M|age=40-44");
    ColumnParameters f45To49_pwid = new ColumnParameters(null, "45-49, Female PWID ", "gender=F|age=45-49");
    ColumnParameters m45To49_pwid = new ColumnParameters(null, "45-49, Male PWID", "gender=M|age=45-49");
    ColumnParameters f50AndAbove_pwid = new ColumnParameters(null, "50+, Female PWID", "gender=F|age=50+");
    ColumnParameters m50AndAbove_pwid = new ColumnParameters(null, "50+, Male PWID", "gender=M|age=50+");
    ColumnParameters f5To9_pwid = new ColumnParameters(null, "5-9, Female PWID ", "gender=F|age=5-9");
    ColumnParameters m5To9_pwid = new ColumnParameters(null, "5-9, Male PWID", "gender=M|age=5-9");

    ColumnParameters f15To19_tg = new ColumnParameters(null, "15-19, Female TG ", "gender=F|age=15-19");
    ColumnParameters m15To19_tg = new ColumnParameters(null, "15-19, Male TG", "gender=M|age=15-19");
    ColumnParameters f20To24_tg = new ColumnParameters(null, "20-24, Female TG ", "gender=F|age=20-24");
    ColumnParameters m20To24_tg = new ColumnParameters(null, "20-24, Male TG", "gender=M|age=20-24");
    ColumnParameters f25To29_tg = new ColumnParameters(null, "25-29, Female TG ", "gender=F|age=25-29");
    ColumnParameters m25To29_tg = new ColumnParameters(null, "25-29, Male TG", "gender=M|age=25-29");
    ColumnParameters f30To34_tg = new ColumnParameters(null, "30-34, Female TG ", "gender=F|age=30-34");
    ColumnParameters m30To34_tg = new ColumnParameters(null, "30-34, Male TG", "gender=M|age=30-34");
    ColumnParameters f35To39_tg = new ColumnParameters(null, "35-39, Female TG ", "gender=F|age=35-39");
    ColumnParameters m35To39_tg = new ColumnParameters(null, "35-39, Male TG", "gender=M|age=35-39");
    ColumnParameters f40To44_tg = new ColumnParameters(null, "40-44, Female TG ", "gender=F|age=40-44");
    ColumnParameters m40To44_tg = new ColumnParameters(null, "40-44, Male TG", "gender=M|age=40-44");
    ColumnParameters f45To49_tg = new ColumnParameters(null, "45-49, Female TG ", "gender=F|age=45-49");
    ColumnParameters m45To49_tg = new ColumnParameters(null, "45-49, Male TG", "gender=M|age=45-49");
    ColumnParameters f50AndAbove_tg = new ColumnParameters(null, "50+, Female TG", "gender=F|age=50+");
    ColumnParameters m50AndAbove_tg = new ColumnParameters(null, "50+, Male TG", "gender=M|age=50+");


    // PP
    ColumnParameters f10_to14_csw = new ColumnParameters(null, "10-14, Female Clients of sex workers", "gender=F|age=10-14|ppType=Clients of sex workers");
    ColumnParameters m10_to14_csw = new ColumnParameters(null, "10-14, Male Clients of sex workers", "gender=M|age=10-14|ppType=Clients of sex workers");
    ColumnParameters f15_to19_csw = new ColumnParameters(null, "15-19, Female Clients of sex workers", "gender=F|age=15-19|ppType=Clients of sex workers");
    ColumnParameters m15_to19_csw = new ColumnParameters(null, "15-19, Male Clients of sex workers", "gender=M|age=15-19|ppType=Clients of sex workers");
    ColumnParameters f20_to24_csw = new ColumnParameters(null, "20-24, Female Clients of sex workers", "gender=F|age=20-24|ppType=Clients of sex workers");
    ColumnParameters m20_to24_csw = new ColumnParameters(null, "20-24, Male Clients of sex workers", "gender=M|age=20-24|ppType=Clients of sex workers");
    ColumnParameters f25_to29_csw = new ColumnParameters(null, "25-29, Female Clients of sex workers", "gender=F|age=25-29|ppType=Clients of sex workers");
    ColumnParameters m25_to29_csw = new ColumnParameters(null, "25-29, Male Clients of sex workers", "gender=M|age=25-29|ppType=Clients of sex workers");
    ColumnParameters f30_to34_csw = new ColumnParameters(null, "30-34, Female Clients of sex workers", "gender=F|age=30-34|ppType=Clients of sex workers");
    ColumnParameters m30_to34_csw = new ColumnParameters(null, "30-34, Male Clients of sex workers", "gender=M|age=30-34|ppType=Clients of sex workers");
    ColumnParameters f35_to39_csw = new ColumnParameters(null, "35-39, Female Clients of sex workers", "gender=F|age=35-39|ppType=Clients of sex workers");
    ColumnParameters m35_to39_csw = new ColumnParameters(null, "35-39, Male Clients of sex workers", "gender=M|age=35-39|ppType=Clients of sex workers");
    ColumnParameters f40_to44_csw = new ColumnParameters(null, "40-44, Female Clients of sex workers", "gender=F|age=40-44|ppType=Clients of sex workers");
    ColumnParameters m40_to44_csw = new ColumnParameters(null, "40-44, Male Clients of sex workers", "gender=M|age=40-44|ppType=Clients of sex workers");
    ColumnParameters f45_to49_csw = new ColumnParameters(null, "45-49, Female Clients of sex workers", "gender=F|age=45-49|ppType=Clients of sex workers");
    ColumnParameters m45_to49_csw = new ColumnParameters(null, "45-49, Male Clients of sex workers", "gender=M|age=45-49|ppType=Clients of sex workers");
    ColumnParameters fAbove50_csw = new ColumnParameters(null, "50+, Female Clients of sex workers", "gender=F|age=50+|ppType=Clients of sex workers");
    ColumnParameters mAbove50_csw = new ColumnParameters(null, "50+, Male Clients of sex workers", "gender=M|age=50+|ppType=Clients of sex workers");

    ColumnParameters f10_to14_displaced = new ColumnParameters(null, "10-14, Female Displaced persons", "gender=F|age=10-14|ppType=Displaced persons");
    ColumnParameters m10_to14_displaced = new ColumnParameters(null, "10-14, Male Displaced persons", "gender=M|age=10-14|ppType=Displaced persons");
    ColumnParameters f15_to19_displaced = new ColumnParameters(null, "15-19, Female Displaced persons", "gender=F|age=15-19|ppType=Displaced persons");
    ColumnParameters m15_to19_displaced = new ColumnParameters(null, "15-19, Male Displaced persons", "gender=M|age=15-19|ppType=Displaced persons");
    ColumnParameters f20_to24_displaced = new ColumnParameters(null, "20-24, Female Displaced persons", "gender=F|age=20-24|ppType=Displaced persons");
    ColumnParameters m20_to24_displaced = new ColumnParameters(null, "20-24, Male Displaced persons", "gender=M|age=20-24|ppType=Displaced persons");
    ColumnParameters f25_to29_displaced = new ColumnParameters(null, "25-29, Female Displaced persons", "gender=F|age=25-29|ppType=Displaced persons");
    ColumnParameters m25_to29_displaced = new ColumnParameters(null, "25-29, Male Displaced persons", "gender=M|age=25-29|ppType=Displaced persons");
    ColumnParameters f30_to34_displaced = new ColumnParameters(null, "30-34, Female Displaced persons", "gender=F|age=30-34|ppType=Displaced persons");
    ColumnParameters m30_to34_displaced = new ColumnParameters(null, "30-34, Male Displaced persons", "gender=M|age=30-34|ppType=Displaced persons");
    ColumnParameters f35_to39_displaced = new ColumnParameters(null, "35-39, Female Displaced persons", "gender=F|age=35-39|ppType=Displaced persons");
    ColumnParameters m35_to39_displaced = new ColumnParameters(null, "35-39, Male Displaced persons", "gender=M|age=35-39|ppType=Displaced persons");
    ColumnParameters f40_to44_displaced = new ColumnParameters(null, "40-44, Female Displaced persons", "gender=F|age=40-44|ppType=Displaced persons");
    ColumnParameters m40_to44_displaced = new ColumnParameters(null, "40-44, Male Displaced persons", "gender=M|age=40-44|ppType=Displaced persons");
    ColumnParameters f45_to49_displaced = new ColumnParameters(null, "45-49, Female Displaced persons", "gender=F|age=45-49|ppType=Displaced persons");
    ColumnParameters m45_to49_displaced = new ColumnParameters(null, "45-49, Male Displaced persons", "gender=M|age=45-49|ppType=Displaced persons");
    ColumnParameters fAbove50_displaced = new ColumnParameters(null, "50+, Female Displaced persons", "gender=F|age=50+|ppType=Displaced persons");
    ColumnParameters mAbove50_displaced = new ColumnParameters(null, "50+, Male Displaced persons", "gender=M|age=50+|ppType=Displaced persons");
    ColumnParameters colTotal = new ColumnParameters(null, "Total", "");

    List<ColumnParameters> childrenAgeDisaggregation = Arrays.asList(
            infant, all1_to9);
    List<ColumnParameters> standardAgeSexCSWPPTypeDisaggregation = Arrays.asList(
            f10_to14_csw, m10_to14_csw, f15_to19_csw, m15_to19_csw, f20_to24_csw, m20_to24_csw, f25_to29_csw, m25_to29_csw, f30_to34_csw, m30_to34_csw, f35_to39_csw, m35_to39_csw, f40_to44_csw, m40_to44_csw, f45_to49_csw, m45_to49_csw, fAbove50_csw, mAbove50_csw);
    List<ColumnParameters> standardAgeSexDisplacedPPTypeDisaggregation = Arrays.asList(
            f10_to14_displaced, m10_to14_displaced, f15_to19_displaced, m15_to19_displaced, f20_to24_displaced, m20_to24_displaced, f25_to29_displaced, m25_to29_displaced, f30_to34_displaced, m30_to34_displaced, f35_to39_displaced, m35_to39_displaced, f40_to44_displaced, m40_to44_displaced, f45_to49_displaced, m45_to49_displaced, fAbove50_displaced, mAbove50_displaced);
    List<ColumnParameters> standardAgeAndSexDisaggregation = Arrays.asList(
            f10_to14, m10_to14, f15_to19, m15_to19, f20_to24, m20_to24, f25_to29, m25_to29, f30_to34, m30_to34, f35_to39, m35_to39, f40_to44, m40_to44, f45_to49, m45_to49, fAbove50, mAbove50);
    List<ColumnParameters> ppAgeAndSexDisaggregation = Arrays.asList(
            f10_to14, m10_to14, f15_to19, m15_to19, f20_to24, m20_to24, f25_to29, m25_to29, f30_to34, m30_to34, f35_to39, m35_to39, f40_to44, m40_to44, f45_to49, m45_to49, fAbove50, mAbove50);

    List<ColumnParameters> sparseAgeAndSexDisaggregation = Arrays.asList(
            f0_to14, m0_to14, f15_to19, m15_to19, f20_to24, m20_to24, f25AndAbove, m25AndAbove);
    List<ColumnParameters> mnchMotherAgeADisaggregation = Arrays.asList(
            fUnder1,f1To9,f10_to14,f15_to19,f20_to24,f25_to29,f30_to34,f35_to39,f40_to49,fAbove50);

    List<ColumnParameters> kpAgeAndSexDisaggregation = Arrays.asList(
            f10_to14, m10_to14, f15_to19, m15_to19, f20_to24, m20_to24, f25_to29, m25_to29, f30_to34, m30_to34, f35_to39, m35_to39, f40_to44, m40_to44, f45_to49, m45_to49, fAbove50, mAbove50);
    List<ColumnParameters> priorityPopulationAgeAndSexDisaggregation = Arrays.asList(
            f10_to14, m10_to14, f15_to19, m15_to19, f20_to24, m20_to24, f25_to29, m25_to29, f30_to34, m30_to34, f35_to39, m35_to39, f40_to49, m40_to49, fAbove50, mAbove50);

    List<ColumnParameters> ctAgeAndSexDisaggregation = Arrays.asList(
            f0_to14, m0_to14, f15_to19, m15_to19, f20_to24, m20_to24, f25AndAbove, m25AndAbove);
    List<ColumnParameters> standardAgeDisaggregation = Arrays.asList(
            all10_to14, all15_to19,all120_to24,all25_to29,all30_to34,all35_to39,all40_to44,all45_to49,allAbove50);

//
List<ColumnParameters> generalAgeAndSexDisaggregation = Arrays.asList(
                f15_to19, m15_to19, f20_to24, m20_to24, f25_to29, m25_to29, f30_to34, m30_to34, f35_to39, m35_to39, f40_to44, m40_to44, f45_to49, m45_to49, fAbove50, mAbove50);
    List<ColumnParameters> sexKPTypeDisaggregation = Arrays.asList(female_pwid, male_pwid);

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(
                new Parameter("startDate", "Start Date", Date.class),
                new Parameter("endDate", "End Date", Date.class),
                new Parameter("dateBasedReporting", "", String.class)
        );
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor, ReportDefinition reportDefinition) {
        return Arrays.asList(
                ReportUtils.map(careAndTreatmentDataset(), "startDate=${startDate},endDate=${endDate}"),
              //HTS
                ReportUtils.map(htsDataset(), "startDate=${startDate},endDate=${endDate}")
                //VMMC
               /*   ReportUtils.map(vmmcDataset(), "startDate=${startDate},endDate=${endDate}"),
                //HCT
                ReportUtils.map(hctDataset(), "startDate=${startDate},endDate=${endDate}"),
                //PMTCT
                ReportUtils.map(pmtctDataset(), "startDate=${startDate},endDate=${endDate}"),
                //OTZ
                ReportUtils.map(otzDataset(), "startDate=${startDate},endDate=${endDate}"),
                //TB
                ReportUtils.map(tbDataset(), "startDate=${startDate},endDate=${endDate}"),
                //PrEP
                ReportUtils.map(prepDataset(), "startDate=${startDate},endDate=${endDate}"),
                //PVCT
                ReportUtils.map(pvctDataset(), "startDate=${startDate},endDate=${endDate}"),
                //LAB
                ReportUtils.map(labDataset(), "startDate=${startDate},endDate=${endDate}"),
                //ARV and TB Meds
                ReportUtils.map(arvDataset(), "startDate=${startDate},endDate=${endDate}"),
                //DQA
                ReportUtils.map(dqaDataset(), "startDate=${startDate},endDate=${endDate}"),
                //LAB Commodities
                ReportUtils.map(labCommoditiesDataset(), "startDate=${startDate},endDate=${endDate}"),
                //BMT
                ReportUtils.map(bmtDataset(), "startDate=${startDate},endDate=${endDate}")*/
        );
    }

    /**
     * Creates the dataset for HTS
     *
     * @return the dataset
     */
    protected DataSetDefinition htsDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("HTS");
        dsd.setDescription("HIV testing services");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addDimension("age", ReportUtils.map(commonDimensions.datimFineAgeGroups(), "onDate=${endDate}"));
        dsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        EmrReportingUtils.addRow(dsd, "HTSV3: IPD HP - eligible", "", ReportUtils.map(threePMIndicators.htsIPDEligibleForTest(HtsConstants.IN_PATIENT_DEPARTMENT_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: IPD HP - known positive", "", ReportUtils.map(threePMIndicators.IPDknownPositiveTest(HtsConstants.IN_PATIENT_DEPARTMENT_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: IPD HP - linked", "", ReportUtils.map(threePMIndicators.htsIpdHPLinked(HtsConstants.IN_PATIENT_DEPARTMENT_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: IPD HP - newly identified positive", "", ReportUtils.map(threePMIndicators.htsIpdHPNewPositive(HtsConstants.IN_PATIENT_DEPARTMENT_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: IPD HP - screened", "", ReportUtils.map(threePMIndicators.htsIPDHPScreenedTest(HtsConstants.IN_PATIENT_DEPARTMENT_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: IPD HP - tested", "", ReportUtils.map(threePMIndicators.htsIPDHPTestedDone(HtsConstants.IN_PATIENT_DEPARTMENT_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));

        EmrReportingUtils.addRow(dsd, "HTSV3: OPD HP - eligible", "", ReportUtils.map(threePMIndicators.htsIPDEligibleForTest(HtsConstants.OUT_PATIENT_DEPARTMENT_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: OPD HP - known positive", "", ReportUtils.map(threePMIndicators.IPDknownPositiveTest(HtsConstants.OUT_PATIENT_DEPARTMENT_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: OPD HP - linked", "", ReportUtils.map(threePMIndicators.htsIpdHPLinked(HtsConstants.OUT_PATIENT_DEPARTMENT_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: OPD HP - newly identified positive", "", ReportUtils.map(threePMIndicators.htsIpdHPNewPositive(HtsConstants.OUT_PATIENT_DEPARTMENT_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: OPD HP - screened", "", ReportUtils.map(threePMIndicators.htsIPDHPScreenedTest(HtsConstants.OUT_PATIENT_DEPARTMENT_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: OPD HP - tested", "", ReportUtils.map(threePMIndicators.htsIPDHPTestedDone(HtsConstants.OUT_PATIENT_DEPARTMENT_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));



//HTS PNS
        EmrReportingUtils.addRow(dsd, "HTSV3: Index Testing - FPNS - contacts eligible", "", ReportUtils.map(threePMIndicators.fnsEligibleForTest(HtsConstants.INDEX_CONTACT), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: Index Testing - FPNS - contacts identified", "", ReportUtils.<CohortIndicator>map((CohortIndicator) datimCohortLibrary.htsIndexContactsElicited(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: Index Testing - FPNS - contacts known positive", "", ReportUtils.map(datimIndicators.contactKnownPositive(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: Index Testing - FPNS - contacts linked", "", ReportUtils.map(threePMIndicators.indexLinked(HtsConstants.INDEX_CONTACT), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: Index Testing - FPNS - contacts newly identified positive", "", ReportUtils.map(datimIndicators.indexTestedPositive(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: Index Testing - FPNS - contacts tested", "", ReportUtils.map(threePMIndicators.fnsTestestedfnsTestested(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: Index Testing - FPNS - index clients", "", ReportUtils.map(datimIndicators.offeredIndexServices(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: Index Testing - FPNS - index clients accepting pns", "", ReportUtils.map(datimIndicators.acceptedIndexServices(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: Index Testing - FPNS - index clients offered pns", "", ReportUtils.map(datimIndicators.htsIndexContactsElicited(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));


        //HTS MNCH




        EmrReportingUtils.addRow(dsd, "HTSV4: 1st ANC visit clients - eligible", "", ReportUtils.map(threePMIndicators.htsFirstANCVisitMchmsAntenatal(HtsConstants.MNCH_ANC_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: 1st ANC visit clients - known positive", "", ReportUtils.map(moh731GreenCardIndicators.testedForHivInMchms(), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: 1st ANC visit clients - newly identified positive", "", ReportUtils.map(threePMIndicators.htsNewlyTestedPositivePmtctANC(HtsConstants.MNCH_ANC_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: 1st ANC visit clients - recent known negative", "", ReportUtils.map(threePMIndicators.htsNegativeTestANCCohortDefinition(HtsConstants.MNCH_ANC_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: 1st ANC visit clients - seen", "", ReportUtils.map(threePMIndicators.seenANCVisitMchmsAntenatal(HtsConstants.MNCH_ANC_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: 1st ANC visit clients - started on HAART", "", ReportUtils.map(threePMIndicators.htsHAARTInANC(HtsConstants.MNCH_ANC_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: 1st ANC visit clients - tested", "", ReportUtils.map(threePMIndicators.htsInitialTestingANC(HtsConstants.MNCH_ANC_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Client Tested - Initial testing (Post ANC1)", "", ReportUtils.map(moh731GreenCardIndicators.initialHIVTestInMchmsAntenatal(), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Client Tested - Retesting (Post ANC1)", "", ReportUtils.map(moh731GreenCardIndicators.pncRetestUpto6Weeks(), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Client Tested HIV Positive - Initial testing (Post ANC1)", "", ReportUtils.map(moh731GreenCardIndicators.testedHivPositiveInMchmsAntenatal(), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Client Tested HIV Positive - Retesting (Post ANC1)", "", ReportUtils.map(moh731GreenCardIndicators.testedHivPositiveInMchmsAntenatal(), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Clients in maternity - known positive", "", ReportUtils.map(moh731GreenCardIndicators.testedHivPositiveInMchmsDelivery(), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Clients in maternity - seen", "", ReportUtils.map(threePMIndicators.seenANCVisitMchmsAntenatal(HtsConstants.MNCH_MAT_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Clients in maternity - started on HAART (Post ANC1)", "", ReportUtils.map(threePMIndicators.htsHAARTInANC(HtsConstants.MNCH_MAT_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Clients in maternity - unknown HIV status at ANC", "", ReportUtils.map(threePMIndicators.unKnownStatusAtANC(HtsConstants.MNCH_MAT_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));

        EmrReportingUtils.addRow(dsd, "HTSV4: Clients in PNC - known positive", "", ReportUtils.map(pnc.hivPositiveResultAtPNC(), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Clients in PNC - seen", "", ReportUtils.map(threePMIndicators.seenANCVisitMchmsAntenatal(HtsConstants.MNCH_PNC_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Clients in PNC - started on HAART (Post ANC1)", "", ReportUtils.map(threePMIndicators.htsHAARTInANC(HtsConstants.MNCH_PNC_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Clients in PNC - unknown HIV status in ANC and/or maternity", "", ReportUtils.map(threePMIndicators.unKnownStatusAtANC(HtsConstants.MNCH_PNC_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: MNCH clients - number issued with self-test kits", "", ReportUtils.map(threePMIndicators.receivingSelfTestKits(HtsConstants.SELF_TEST_KIT), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: MNCH clients - number with positive self-test results", "", ReportUtils.map(threePMIndicators.receivingSelfTestKitsPositive(HtsConstants.SELF_TEST_KIT), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: PNC - Client Tested - Initial testing (Post ANC1)", "", ReportUtils.map(threePMIndicators.pmtcTested(), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));

        EmrReportingUtils.addRow(dsd, "HTSV4: PNC - Client Tested - Retesting (Post ANC1)", "", ReportUtils.map(threePMIndicators.htsScreenedMobile(HtsConstants.MNCH_PNC_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: PNC - Client Tested HIV Positive - Initial testing (Post ANC1)", "", ReportUtils.map(threePMIndicators.htsNewlyTestedPositivePmtctANC(HtsConstants.MNCH_PNC_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: PNC - Client Tested HIV Positive - Retesting (Post ANC1)", "", ReportUtils.map(threePMIndicators.htsScreenedMobile(HtsConstants.MNCH_PNC_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));

        EmrReportingUtils.addRow(dsd, "HTSV4: Post ANC1 Client Tested(Initial testing)", "", ReportUtils.map(threePMIndicators.htsScreenedMobile(HtsConstants.MNCH_MAT_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Post ANC1 Client Tested HIV Positive (Initial testing)", "", ReportUtils.map(threePMIndicators.htsScreenedMobile(HtsConstants.MNCH_MAT_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Post ANC1 Client Tested HIV Positive (Retesting)", "", ReportUtils.map(threePMIndicators.htsScreenedMobile(HtsConstants.MNCH_MAT_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Post ANC1 Client Tested( Retesting)", "", ReportUtils.map(threePMIndicators.htsScreenedMobile(HtsConstants.MNCH_MAT_CLINIC_CONCEPT_ID), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Post ANC1 client: started on HAART", "", ReportUtils.map(moh731GreenCardIndicators.startedHAARTLabourAndDelivery(), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));
        EmrReportingUtils.addRow(dsd, "HTSV4: Post ANC1 visit: started on HAART (missed opportunities)", "", ReportUtils.map(moh731GreenCardIndicators.startedHAARTPNCUpto6Weeks(), indParams), standardAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08","09"));

        //HTS -STI
        EmrReportingUtils.addRow(dsd, "HTSV3: STI - eligible", "", ReportUtils.map(threePMIndicators.htsEligibleForTest(HtsConstants.STI_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: STI - known positive", "", ReportUtils.map(threePMIndicators.knownPositiveTest(HtsConstants.STI_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: STI - linked", "", ReportUtils.map(threePMIndicators.htsLinked(HtsConstants.STI_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: STI - newly identified positive", "", ReportUtils.map(threePMIndicators.htsNewPositiveMobile(HtsConstants.STI_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: STI - screened", "", ReportUtils.map(threePMIndicators.htsScreenedTest(HtsConstants.STI_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: STI - tested", "", ReportUtils.map(threePMIndicators.htsTestedDone(HtsConstants.STI_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));

        //HTS -TB
        EmrReportingUtils.addRow(dsd, "HTSV3: TB - eligible", "", ReportUtils.map(threePMIndicators.htsEligibleForTest(HtsConstants.TB_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: TB - known positive", "", ReportUtils.map(threePMIndicators.knownPositiveTest(HtsConstants.TB_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: TB - linked", "", ReportUtils.map(threePMIndicators.htsLinked(HtsConstants.TB_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: TB - newly identified positive", "", ReportUtils.map(threePMIndicators.htsNewPositiveMobile(HtsConstants.TB_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: TB - screened", "", ReportUtils.map(threePMIndicators.htsScreenedTest(HtsConstants.TB_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: TB - tested", "", ReportUtils.map(threePMIndicators.htsTestedDone(HtsConstants.TB_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
       //HTS VCT
        EmrReportingUtils.addRow(dsd, "HTSV3: VCT - eligible", "", ReportUtils.map(threePMIndicators.htsEligibleForTest(HtsConstants.VCT_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: VCT - known positive", "", ReportUtils.map(threePMIndicators.knownPositiveTest(HtsConstants.VCT_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: VCT - linked", "", ReportUtils.map(threePMIndicators.htsLinked(HtsConstants.VCT_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: VCT - newly identified positive", "", ReportUtils.map(threePMIndicators.htsNewPositiveMobile(HtsConstants.VCT_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: VCT - screened", "", ReportUtils.map(threePMIndicators.htsScreenedTest(HtsConstants.VCT_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "HTSV3: VCT - tested", "", ReportUtils.map(threePMIndicators.htsTestedDone(HtsConstants.VCT_CLINIC_CONCEPT_ID), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));


        EmrReportingUtils.addRow(dsd, "SNS: Number HIV Positive FWS", "", ReportUtils.map(threePMIndicators.snsHivPositive(HtsConstants.FSW), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: Number HIV Positive General Population", "", ReportUtils.map(threePMIndicators.snsPopulationTypeHivPositive(HtsConstants.GENERAL_POPULATION), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: Number HIV Positive MSM", "", ReportUtils.map(threePMIndicators.snsHivPositive(HtsConstants.MSM), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: Number HIV Positive People in prison", "", ReportUtils.map(threePMIndicators.snsHivPositive(HtsConstants.PEOPLE_IN_PRISON), indParams), generalAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));
        EmrReportingUtils.addRow(dsd, "SNS: Number HIV Positive PWID", "", ReportUtils.map(threePMIndicators.snsHivPositive(HtsConstants.PWID), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: Number HIV Positive Transgender", "", ReportUtils.map(threePMIndicators.snsHivPositive(HtsConstants.TRANSGENDER), indParams), generalAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));


        EmrReportingUtils.addRow(dsd, "SNS: Number linked to ART treatment FWS", "", ReportUtils.map(threePMIndicators.snsLinked(HtsConstants.FSW), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: Number linked to ART treatment General Population", "", ReportUtils.map(threePMIndicators.htsPopulationTypeLinked(HtsConstants.GENERAL_POPULATION), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: Number linked to ART treatment MSM", "", ReportUtils.map(threePMIndicators.snsLinked(HtsConstants.MSM), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: Number linked to ART treatment People in prison", "", ReportUtils.map(threePMIndicators.snsLinked(HtsConstants.PEOPLE_IN_PRISON), indParams), generalAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));
        EmrReportingUtils.addRow(dsd, "SNS: Number linked to ART treatment PWID", "", ReportUtils.map(threePMIndicators.snsLinked(HtsConstants.PWID), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: Number linked to ART treatment Transgender", "", ReportUtils.map(threePMIndicators.snsLinked(HtsConstants.TRANSGENDER), indParams), generalAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));


        EmrReportingUtils.addRow(dsd, "SNS: Number Tested for HIV FWS", "", ReportUtils.map(threePMIndicators.snsTested(HtsConstants.FSW), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: Number Tested for HIV General Population", "", ReportUtils.map(threePMIndicators.htsPopulationTypeTested(HtsConstants.GENERAL_POPULATION), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: Number Tested for HIV MSM", "", ReportUtils.map(threePMIndicators.snsTested(HtsConstants.MSM), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: Number Tested for HIV People in prison", "", ReportUtils.map(threePMIndicators.snsTested(HtsConstants.PEOPLE_IN_PRISON), indParams), generalAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));
        EmrReportingUtils.addRow(dsd, "SNS: Number Tested for HIV PWID", "", ReportUtils.map(threePMIndicators.snsTested(HtsConstants.PWID), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: Number Tested for HIV Transgender", "", ReportUtils.map(threePMIndicators.snsTested(HtsConstants.TRANSGENDER), indParams), generalAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));

        EmrReportingUtils.addRow(dsd, "SNS: SNS_SEED_NEGATIVE FWS", "", ReportUtils.map(threePMIndicators.snsHivNegative(HtsConstants.FSW), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: SNS_SEED_NEGATIVE General Population", "", ReportUtils.map(threePMIndicators.htsPopulationTypeNegativeClient(HtsConstants.GENERAL_POPULATION), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: SNS_SEED_NEGATIVE HIV MSM", "", ReportUtils.map(threePMIndicators.snsHivNegative(HtsConstants.MSM), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: SNS_SEED_NEGATIVE People in prison", "", ReportUtils.map(threePMIndicators.snsHivNegative(HtsConstants.PEOPLE_IN_PRISON), indParams), generalAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));
        EmrReportingUtils.addRow(dsd, "SNS: SNS_SEED_NEGATIVE PWID", "", ReportUtils.map(threePMIndicators.snsHivNegative(HtsConstants.PWID), indParams), kpAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16","17", "18"));
        EmrReportingUtils.addRow(dsd, "SNS: SNS_SEED_NEGATIVE Transgender", "", ReportUtils.map(threePMIndicators.snsHivNegative(HtsConstants.TRANSGENDER), indParams), generalAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));


//        HTSV3: OPD HP - eligible
//        HTSV3: OPD HP - known positive
//        HTSV3: OPD HP - linked
//        HTSV3: OPD HP - newly identified positive
//        HTSV3: OPD HP - screened
//        HTSV3: OPD HP - tested

       //Mobile
        EmrReportingUtils.addRow(dsd, "QnSletgoPla", "CHTSV2_Result: Mobile Testing - Screened", ReportUtils.map(threePMIndicators.htsScreenedMobile(HtsConstants.MOBILE_OUTREACH_TESTING_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));
        EmrReportingUtils.addRow(dsd, "XGm5lTnVaOu", "CHTSV2_Result: Mobile Testing - Eligible", ReportUtils.map(threePMIndicators.htsScreenedMobile(HtsConstants.MOBILE_OUTREACH_TESTING_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));
        EmrReportingUtils.addRow(dsd, "N42RUbWfr6F", "CHTSV2_Result: Mobile Testing - Tested", ReportUtils.map(threePMIndicators.htsTested(HtsConstants.MOBILE_OUTREACH_TESTING_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));
        EmrReportingUtils.addRow(dsd, "Ct0CcuOl9Wb", "CHTSV2_Result: Mobile Testing - Known HIV Positive", ReportUtils.map(threePMIndicators.htsKnownPositive(HtsConstants.MOBILE_OUTREACH_TESTING_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));
        //EmrReportingUtils.addRow(dsd, "yF5DNgyb3PC", "CHTSV2_Result: Mobile Testing - Seen", ReportUtils.map(threePMIndicators.htsKnownPositiveMobile(), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));
        EmrReportingUtils.addRow(dsd, "YHyt08aRsoD", "CHTSV2_Result: Mobile Testing - Newly Identified Positive", ReportUtils.map(threePMIndicators.htsNewPositiveMobile(HtsConstants.MOBILE_OUTREACH_TESTING_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));
        // Review the linked query in moh731
        EmrReportingUtils.addRow(dsd, "jn8Boh188o6", "CHTSV2_Result: Mobile Testing - Linked to HAART", ReportUtils.map(threePMIndicators.htsLinkedToHAARTMobile(HtsConstants.MOBILE_OUTREACH_TESTING_CONCEPT_ID), indParams), sparseAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));

        //MNCH -ANC
       // EmrReportingUtils.addRow(dsd, "yHHWaNDFzES", "HTSV2_Result:MNCH - ANC Department - Number of 1st ANC visit clients Screened", ReportUtils.map(threePMIndicators.htsScreenedANC1(HtsConstants.MNCH_ANC_CLINIC_CONCEPT_ID), indParams), mnchMotherAgeADisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10"));
        EmrReportingUtils.addRow(dsd, "R6BJK9kAjtA", "HTSV2_Result:MNCH - ANC Department - Number of clients on 1st ANC visit", ReportUtils.map(moh731GreenCardIndicators.firstANCVisitMchmsAntenatal(), indParams), mnchMotherAgeADisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10"));
      //  EmrReportingUtils.addRow(dsd, "XGm5lTnVaOu", "CHTSV2_Result: Mobile Testing - Eligible", ReportUtils.map(threePMIndicators.htsEligibleMobile(HtsConstants.MNCH_ANC_CLINIC_CONCEPT_ID), indParams), mnchMotherAgeADisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10"));
        EmrReportingUtils.addRow(dsd, "COn6aObrQm8", "HTSV2_Result:MNCH - ANC Department - Tested", ReportUtils.map(threePMIndicators.htsTested(HtsConstants.MNCH_ANC_CLINIC_CONCEPT_ID), indParams), mnchMotherAgeADisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10"));
        EmrReportingUtils.addRow(dsd, "Gwk0JOcf33J", "HTSV2_Result:MNCH - ANC Department - Known Positive", ReportUtils.map(threePMIndicators.htsKnownPositive(HtsConstants.MNCH_ANC_CLINIC_CONCEPT_ID), indParams), mnchMotherAgeADisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10"));
       // EmrReportingUtils.addRow(dsd, "yF5DNgyb3PC", "CHTSV2_Result: Mobile Testing - Seen", ReportUtils.map(threePMIndicators.htsKnownPositiveMobile(), indParams), mnchMotherAgeADisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"));
        EmrReportingUtils.addRow(dsd, "YHyt08aRsoD", "CHTSV2_Result: Mobile Testing - Newly Identified Positive", ReportUtils.map(threePMIndicators.htsNewPositiveMobile(HtsConstants.MNCH_ANC_CLINIC_CONCEPT_ID), indParams), mnchMotherAgeADisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10"));
        // Review the linked query in moh731
        EmrReportingUtils.addRow(dsd, "jn8Boh188o6", "CHTSV2_Result: Mobile Testing - Linked to HAART", ReportUtils.map(threePMIndicators.htsLinkedToHAARTMobile(HtsConstants.MNCH_ANC_CLINIC_CONCEPT_ID), indParams), mnchMotherAgeADisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"));

        //MNCH-ANC
        dsd.addColumn("1st ANC visit clients", "1st ANC visit clients", ReportUtils.map(threePMIndicators.firstANCVisitMchmsAntenatal(), indParams), "");
        dsd.addColumn("1st ANC clients - recent known negative", "1st ANC visit clients - recent known negative", ReportUtils.map(threePMIndicators.knownPositiveAtFirstANC(), indParams), "");
        dsd.addColumn("1st ANC clients - known Positive", "1st ANC visit clients - recent known positive", ReportUtils.map(threePMIndicators.knownPositiveAtFirstANC(), indParams), "");

        return (dsd);
    }

    /**
     * Creates the dataset for Care and treatment
     *
     * @return the dataset
     */
    protected DataSetDefinition careAndTreatmentDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("Care and Treatment");
        dsd.setDescription("Care and Treatment");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addDimension("age", ReportUtils.map(commonDimensions.datimFineAgeGroups(), "onDate=${endDate}"));
        dsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));
        dsd.addDimension("kpType", ReportUtils.map(commonDimensions.kpType()));
        dsd.addDimension("ppType", ReportUtils.map(commonDimensions.priorityPopulationType()));

        ColumnParameters f15_to19 = new ColumnParameters(null, "15-19, Female", "gender=F|age=15-19");
        ColumnParameters kp15_19 = new ColumnParameters(null, "15-19, Female", "kpType=F|age=15-19");
        ColumnParameters pp15_19 = new ColumnParameters(null, "15-19, Female", "ppType=F|age=15-19");

        String indParams = "startDate=${startDate},endDate=${endDate}";

        //Care and Treatment indicators


        EmrReportingUtils.addRow(dsd, "CTV3: Current on treatment", "", ReportUtils.map(moh731GreenCardIndicators.currentlyOnArt(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: On DTG-based regimen", "", ReportUtils.map(threePMIndicators.OnDTGRegimen(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: Stopped treatment", "", ReportUtils.map(threePMIndicators.patientStoppedTreatment(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: Stopped treatment", "", ReportUtils.map(threePMIndicators.patientStoppedTreatment(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: Lost (LTFU) (> 30 days)", "", ReportUtils.map(threePMIndicators.patientsLTFU(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: Current on treatment - (Number with Hypertension)", "", ReportUtils.map(threePMIndicators.patientWithChronicillness(HtsConstants.HYPERTENSION), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
   //     EmrReportingUtils.addRow(dsd, "CTV3: Current on treatment - (Number with Hypertension, well controlled)", "", ReportUtils.map(threePMIndicators.ltfu(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: Eligible for routine vl", "", ReportUtils.map(threePMIndicators.patientEligibleForVl(6), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: Transfer outs", "", ReportUtils.map(threePMIndicators.transferOut(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
       // EmrReportingUtils.addRow(dsd, "CTV3: Current on treatment - (Number with Diabetes, well controlled)", "", ReportUtils.map(threePMIndicators.ltfu(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: With viral load results 200 - 999cp/ml", "", ReportUtils.map(threePMIndicators.viralSuppressionBetween400To900(HtsConstants.VL_200,HtsConstants.VL_999,12), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: On MMS/MMD/DSD", "", ReportUtils.<CohortIndicator>map((CohortIndicator) datimCohortLibrary.currentlyOnARTUnder3MonthsMMD(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: New on treatment", "", ReportUtils.map(threePMIndicators.newOnTreatment(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: Previously lost but returned to treatment (Tx_RTT)", "", ReportUtils.<CohortIndicator>map((CohortIndicator) datimCohortLibrary.txRTTIITBelow3Months(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: With vl results < 400cp/ml (includes LDL)", "", ReportUtils.map(threePMIndicators.patientWithValidVLLess200WithLDL(HtsConstants.VL_400), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: Number switched to 3rd line", "", ReportUtils.map(etlOtzIndicatorLibrary.patientsSwithedToThirdlineArt(12), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: With vl results >= 1000cp/ml", "", ReportUtils.map(etlOtzIndicatorLibrary.patientWithRepeatVLMoreThan1000(12), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: Current on treatment - (Number with NCD well controlled)", "", ReportUtils.map(threePMIndicators.ncdPatient(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: Number switched to 2nd line", "", ReportUtils.map(etlOtzIndicatorLibrary.patientsSwithedToSecondlineArt(12), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: Transfer ins", "", ReportUtils.map(threePMIndicators.transferIn(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: Current on treatment - (Number with Diabetes)", "", ReportUtils.map(threePMIndicators.patientWithChronicillness(HtsConstants.DIABETES), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: Died", "", ReportUtils.map(threePMIndicators.patientDied(), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: With viral load results < 200cp/ml (includes LDL)", "", ReportUtils.map(threePMIndicators.patientWithValidVLLess200WithLDL(HtsConstants.VL_200), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
        EmrReportingUtils.addRow(dsd, "CTV3: With vl results 400 - 999cp/ml", "", ReportUtils.map(threePMIndicators.viralSuppressionBetween400To900(HtsConstants.VL_400,HtsConstants.VL_999,12), indParams), ctAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));



        //Check this for and kp types
        EmrReportingUtils.addRow(dsd, /*"foX53QuynMF",*/ "KPV2_Result: Number of KPs currently active in the DICE/Program","", ReportUtils.map(threePMIndicators.kpCurr(), indParams), sexKPTypeDisaggregation, Arrays.asList("01", "02"));
        dsd.addColumn("foX53QuynMF", "KPV2_Result: Number of KPs currently active in the DICE/Program-FSW", ReportUtils.map(threePMIndicators.currentlyOnARTOnSite(FSW), indParams), "");
        dsd.addColumn("foX53QuynMF", "KPV2_Result: Number of KPs currently active in the DICE/Program-MSM", ReportUtils.map(threePMIndicators.currentlyOnARTOnSite(MSM), indParams), "");
        EmrReportingUtils.addRow(dsd, "mFqL1MztVdm", "KPV2_Result: Number of KPs currently active on ART at the DICE-PWID", ReportUtils.map(threePMIndicators.currentlyOnARTOnSite(PWID), indParams), sexKPTypeDisaggregation, Arrays.asList("01", "02"));
        dsd.addColumn("mFqL1MztVdm", "KPV2_Result: Number of KPs currently active on ART at the DICE-FSW", ReportUtils.map(threePMIndicators.currentlyOnARTOnSite(FSW), indParams), "");
        dsd.addColumn("mFqL1MztVdm", "KPV2_Result: Number of KPs currently active on ART at the DICE-MSM", ReportUtils.map(threePMIndicators.currentlyOnARTOnSite(MSM), indParams), "");

        EmrReportingUtils.addRow(dsd, "AMVV35LIVS7", "KPV2_Result: Number of KPs currently on PrEP-PWID", ReportUtils.map(threePMIndicators.kpCurrOnPrEP(PWID), indParams), sexKPTypeDisaggregation, Arrays.asList("01", "02"));
        dsd.addColumn("AMVV35LIVS7", "KPV2_Result: Number of KPs currently on PrEP-FSW", ReportUtils.map(threePMIndicators.kpCurrOnPrEP(FSW), indParams), "");
        dsd.addColumn("AMVV35LIVS7", "KPV2_Result: Number of KPs currently on PrEP-MSM", ReportUtils.map(threePMIndicators.kpCurrOnPrEP(MSM), indParams), "");

        EmrReportingUtils.addRow(dsd, "QITtH6Wk9fc", "KPV2_Result: Number of KPs currently on PrEP who were diagnosed with STI-PWID", ReportUtils.map(threePMIndicators.kpCurrOnPrEPWithSTI(PWID), indParams), sexKPTypeDisaggregation, Arrays.asList("01", "02"));
        dsd.addColumn("QITtH6Wk9fc", "KPV2_Result: Number of KPs currently on PrEP who were diagnosed with STI-FSW", ReportUtils.map(threePMIndicators.kpCurrOnPrEPWithSTI(FSW), indParams), "");
        dsd.addColumn("QITtH6Wk9fc", "KPV2_Result: Number of KPs currently on PrEP who were diagnosed with STI-MSM", ReportUtils.map(threePMIndicators.kpCurrOnPrEPWithSTI(MSM), indParams), "");

        EmrReportingUtils.addRow(dsd, "Eh9TBtkpGgP", "KPV2_Result: Number of KPs currently active on ART at other CCC-PWID", ReportUtils.map(threePMIndicators.kpCurrentOnARTOffsite(PWID), indParams), sexKPTypeDisaggregation, Arrays.asList("01", "02"));
        dsd.addColumn("Eh9TBtkpGgP", "KPV2_Result: Number of KPs currently active on ART at other CCC-FSW", ReportUtils.map(threePMIndicators.kpCurrentOnARTOffsite(FSW), indParams), "");
        dsd.addColumn("Eh9TBtkpGgP", "KPV2_Result: Number of KPs currently active on ART at other CCC-MSM", ReportUtils.map(threePMIndicators.kpCurrentOnARTOffsite(MSM), indParams), "");

        EmrReportingUtils.addRow(dsd, /*"jw59Ex21zJ3", */"PP: Number of PP current on ART - Other Facilities", "",ReportUtils.map(threePMIndicators.ppCurrentOnARTOffsite(), indParams), standardAgeSexCSWPPTypeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));
        EmrReportingUtils.addRow(dsd, "jw59Ex21zJ3", "PP: Number of PP current on ART - Other Facilities", ReportUtils.map(threePMIndicators.ppCurrentOnARTOffsite(), indParams), standardAgeSexDisplacedPPTypeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));
        EmrReportingUtils.addRow(dsd, "jw59Ex21zJ3", "PP: Number of PP current on ART - Other Facilities-Fishing communities", ReportUtils.map(threePMIndicators.ppPrevByType(FISHING_COMMUNITIES), indParams), standardAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));
        EmrReportingUtils.addRow(dsd, "jw59Ex21zJ3", "PP: Number of PP current on ART - Other Facilities-Military and other uniformed services", ReportUtils.map(threePMIndicators.ppPrevByType(MILITARY_UNIFORMED_SERVICES), indParams), standardAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));
        EmrReportingUtils.addRow(dsd, "jw59Ex21zJ3", "PP: Number of PP current on ART - Other Facilities-Mobile populations", ReportUtils.map(threePMIndicators.ppPrevByType(MOBILE_POPULATIONS), indParams), standardAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));
        EmrReportingUtils.addRow(dsd, "jw59Ex21zJ3", "PP: Number of PP current on ART - Other Facilities-Non-injecting drug users", ReportUtils.map(threePMIndicators.ppPrevByType(NON_INJECTING_DRUG_USER), indParams), standardAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));
        EmrReportingUtils.addRow(dsd, "jw59Ex21zJ3", "PP: Number of PP current on ART - Other Facilities-Other Priority populations", ReportUtils.map(threePMIndicators.ppPrevByType(OTHER), indParams), standardAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));

        EmrReportingUtils.addRow(dsd, "c1umVKipZDf", "PP: Number of PP current on ART - This PP DICE-Clients of Sex Workers", ReportUtils.map(threePMIndicators.ppPrevByType(SEX_WORKERS_CLIENTS), indParams), standardAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));
        EmrReportingUtils.addRow(dsd, "c1umVKipZDf", "PP: Number of PP current on ART - This PP DICE-Displaced persons", ReportUtils.map(threePMIndicators.ppPrevByType(DISPLACED_PERSONS), indParams), standardAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));
        EmrReportingUtils.addRow(dsd, "c1umVKipZDf", "PP: Number of PP current on ART - This PP DICE-Fishing communities", ReportUtils.map(threePMIndicators.ppPrevByType(FISHING_COMMUNITIES), indParams), standardAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));
        EmrReportingUtils.addRow(dsd, "c1umVKipZDf", "PP: Number of PP current on ART - This PP DICE-Military and other uniformed services", ReportUtils.map(threePMIndicators.ppPrevByType(MILITARY_UNIFORMED_SERVICES), indParams), standardAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));
        EmrReportingUtils.addRow(dsd, "c1umVKipZDf", "PP: Number of PP current on ART - This PP DICE-Mobile populations", ReportUtils.map(threePMIndicators.ppPrevByType(MOBILE_POPULATIONS), indParams), standardAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));
        EmrReportingUtils.addRow(dsd, "c1umVKipZDf", "PP: Number of PP current on ART - This PP DICE-Non-injecting drug users", ReportUtils.map(threePMIndicators.ppPrevByType(NON_INJECTING_DRUG_USER), indParams), standardAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));
        EmrReportingUtils.addRow(dsd, "c1umVKipZDf", "PP: Number of PP current on ART - This PP DICE-Other Priority populations", ReportUtils.map(threePMIndicators.ppCurrentOnARTOnSite(), indParams), standardAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));

        return (dsd);
    }

    /**
     * Creates the dataset for VMMC
     *
     * @return the dataset
     */
    protected DataSetDefinition vmmcDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("2");
        dsd.setDescription("VMMC");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        return (dsd);
    }

    /**
     * Creates the dataset for HCT
     *
     * @return the dataset
     */
    protected DataSetDefinition hctDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("3");
        dsd.setDescription("HCT");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        return (dsd);
    }

    /**
     * Creates the dataset for PMTCT
     *
     * @return the dataset
     */
    protected DataSetDefinition pmtctDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("4");
        dsd.setDescription("Prevention of Mother-To-Child Transmission");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        return (dsd);
    }

    /**
     * Creates the dataset for OTZ
     *
     * @return the dataset
     */
    protected DataSetDefinition otzDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("5");
        dsd.setDescription("OTZ");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        return (dsd);
    }

    /**
     * Creates the dataset for TB
     *
     * @return the dataset
     */
    protected DataSetDefinition tbDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("6");
        dsd.setDescription("TB");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        return (dsd);
    }

    /**
     * Creates the dataset for PREP
     *
     * @return the dataset
     */
    protected DataSetDefinition prepDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("7");
        dsd.setDescription("PREP");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        return (dsd);
    }

    /**
     * Creates the dataset for PVCT
     *
     * @return the dataset
     */
    protected DataSetDefinition pvctDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("8");
        dsd.setDescription("PVCT");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        return (dsd);
    }

    /**
     * Creates the dataset for LAB
     *
     * @return the dataset
     */
    protected DataSetDefinition labDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("9");
        dsd.setDescription("LAB");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        return (dsd);
    }

    /**
     * Creates the dataset for ARV
     *
     * @return the dataset
     */
    protected DataSetDefinition arvDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("10");
        dsd.setDescription("ARV");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        return (dsd);
    }

    /**
     * Creates the dataset for DQA
     *
     * @return the dataset
     */
    protected DataSetDefinition dqaDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("11");
        dsd.setDescription("DQA");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        return (dsd);
    }

    /**
     * Creates the dataset for LAB COMMODITIES
     *
     * @return the dataset
     */
    protected DataSetDefinition labCommoditiesDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("12");
        dsd.setDescription("LAB COMMODITIES");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        return (dsd);
    }

    /**
     * Creates the dataset for BMT
     *
     * @return the dataset
     */
    protected DataSetDefinition bmtDataset() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();

        dsd.setName("13");
        dsd.setDescription("BMT");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        return (dsd);
    }

    /**
     * Creates the dataset for section #2: Prevention of Mother-to-Child Transmission
     *
     * @return the dataset
     */
    protected DataSetDefinition pmtctDataSet() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName("2");
        dsd.setDescription("Prevention of Mother-to-Child Transmission");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        return dsd;
    }


    /**
     * Creates the dataset for section #3: Care and Treatment
     *
     * @return the dataset
     */
    protected DataSetDefinition careAndTreatmentDataSet() {
        CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
        cohortDsd.setName("3");
        cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cohortDsd.addDimension("age", ReportUtils.map(commonDimensions.moh731GreenCardAgeGroups(), "onDate=${endDate}"));
        cohortDsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        return cohortDsd;

    }

    /**
     * Creates the dataset for section #1: hiv testing and counseling
     *
     * @return the dataset
     */
    protected DataSetDefinition hivTestingAndCouselingDatasetDefinition() {
        CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
        cohortDsd.setName("1");
        cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cohortDsd.addDimension("age", ReportUtils.map(commonDimensions.moh731GreenCardAgeGroups(), "onDate=${endDate}"));
        cohortDsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));
        String indParams = "startDate=${startDate},endDate=${endDate}";

        return cohortDsd;

    }

    /**
     * Creates the dataset for section #4: Voluntary Male Circumcision
     *
     * @return the dataset
     */
    protected DataSetDefinition voluntaryMaleCircumcisionDatasetDefinition() {
        CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
        cohortDsd.setName("4");
        cohortDsd.setDescription("Voluntary Male Circumcision");
        cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cohortDsd.addDimension("age", ReportUtils.map(commonDimensions.moh731GreenCardAgeGroups(), "onDate=${endDate}"));
        cohortDsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));
        String indParams = "startDate=${startDate},endDate=${endDate}";

        return cohortDsd;

    }
}