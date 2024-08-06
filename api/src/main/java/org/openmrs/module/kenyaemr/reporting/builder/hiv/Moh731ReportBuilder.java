/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.builder.hiv;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH731Greencard.ETLMoh731GreenCardIndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Report builder for ETL MOH 731
 */
@Component
@Builds({"kenyaemr.etl.common.report.moh731"})
public class Moh731ReportBuilder extends AbstractReportBuilder {
    @Autowired
    private CommonDimensionLibrary commonDimensions;

    @Autowired
    private ETLMoh731GreenCardIndicatorLibrary moh731GreenCardIndicators;

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    ColumnParameters boys_0_to_60_days = new ColumnParameters(null, "0-60 days", "gender=M|age=0-60");
    ColumnParameters boys_61_days_to_9_years = new ColumnParameters(null, "61 days-9 yrs", "gender=M|age=61-9");

    ColumnParameters maleInfants = new ColumnParameters(null, "<1, Male", "gender=M|age=<1");
    ColumnParameters femaleInfants = new ColumnParameters(null, "<1, Female", "gender=F|age=<1");

    ColumnParameters children_0_to_4 = new ColumnParameters(null, "0-4", "age=0-4");
    ColumnParameters children_5_to_9 = new ColumnParameters(null, "5-9", "age=5-9");
    ColumnParameters children_10_to_14 = new ColumnParameters(null, "10-14", "age=10-14");
    ColumnParameters adult_15_to_19 = new ColumnParameters(null, "15-19", "age=15-19");
    ColumnParameters adult_20_and_above = new ColumnParameters(null, "20+", "age=20+");

    // specific to pre-art
    ColumnParameters under15 = new ColumnParameters(null, "<15", "age=<15");
    ColumnParameters adult_15_and_above = new ColumnParameters(null, "15+", "age=15+");
    ColumnParameters male_15_and_above = new ColumnParameters(null, "15+", "gender=M|age=15+");
    // end of pre-art

    ColumnParameters m_1_to_4 = new ColumnParameters(null, "1-4, Male", "gender=M|age=1-4");
    ColumnParameters f_1_to_4 = new ColumnParameters(null, "1-4, Female", "gender=F|age=1-4");
    ColumnParameters m_2_to_9 = new ColumnParameters(null, "2-9, Male", "gender=M|age=2-9");
    ColumnParameters f_2_to_9 = new ColumnParameters(null, "2-9, Female", "gender=F|age=2-9");
    ColumnParameters m_5_to_9 = new ColumnParameters(null, "5-9, Male", "gender=M|age=5-9");
    ColumnParameters f_5_to_9 = new ColumnParameters(null, "5-9, Female", "gender=F|age=5-9");

    ColumnParameters m_10_to_14 = new ColumnParameters(null, "10-14, Male", "gender=M|age=10-14");
    ColumnParameters f_10_to_14 = new ColumnParameters(null, "10-14, Female", "gender=F|age=10-14");
    ColumnParameters m_15_to_19 = new ColumnParameters(null, "15-19, Male", "gender=M|age=15-19");
    ColumnParameters f_15_to_19 = new ColumnParameters(null, "15-19, Female", "gender=F|age=15-19");
    ColumnParameters f10_to_19 = new ColumnParameters(null, "10-19, Female", "gender=F|age=10-19");
    ColumnParameters m_20_to_24 = new ColumnParameters(null, "20-24, Male", "gender=M|age=20-24");
    ColumnParameters f_20_to_24 = new ColumnParameters(null, "20-24, Female", "gender=F|age=20-24");
    ColumnParameters m_25_and_above = new ColumnParameters(null, "25+, Male", "gender=M|age=25+");
    ColumnParameters f_25_and_above = new ColumnParameters(null, "25+, Female", "gender=F|age=25+");
    ColumnParameters males = new ColumnParameters(null, "Male", "gender=M");
    ColumnParameters females = new ColumnParameters(null, "Female", "gender=F");

    ColumnParameters colTotal = new ColumnParameters(null, "Total", "");

    List<ColumnParameters> standardAgeOnlyDisaggregation = Arrays.asList(
            children_0_to_4, children_5_to_9, children_10_to_14, adult_15_to_19,
            adult_20_and_above);

    List<ColumnParameters> allAgeDisaggregation = Arrays.asList(
            maleInfants, femaleInfants, m_1_to_4,  f_1_to_4, m_5_to_9, f_5_to_9, m_10_to_14, f_10_to_14,m_15_to_19, f_15_to_19,
            m_20_to_24, f_20_to_24, m_25_and_above, f_25_and_above);

    List<ColumnParameters> vmmcDisaggregation = Arrays.asList(
            boys_0_to_60_days, boys_61_days_to_9_years, m_10_to_14, male_15_and_above);

    List<ColumnParameters> adolescentYoungWomenAgeDisaggregation = Arrays.asList(f10_to_19,f_20_to_24);
    List<ColumnParameters> genderDisaggregation = Arrays.asList(males,females);
    List<ColumnParameters> htsMaleDisaggregation = Arrays.asList(m_2_to_9,m_10_to_14,m_15_to_19,m_20_to_24,m_25_and_above);
    List<ColumnParameters> htsFemaleDisaggregation = Arrays.asList(f_2_to_9,f_10_to_14,f_15_to_19,f_20_to_24,f_25_and_above);
    List<ColumnParameters> under15And15PlusDisaggregation = Arrays.asList(under15,adult_15_and_above);

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
                ReportUtils.map(hivTestingAndCouselingDatasetDefinition(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(pmtctDataSet(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(careAndTreatmentDataSet(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(voluntaryMaleCircumcisionDatasetDefinition(), "startDate=${startDate},endDate=${endDate}")
        );
    }
    /**
     * Creates the dataset for section #1
     * : hiv testing and counseling
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

       //1.1 HIV Tests
        cohortDsd.addColumn("HV01-01", "Tested (M)", ReportUtils.map(moh731GreenCardIndicators.htsTestsMales(), indParams),"");
        cohortDsd.addColumn("HV01-02", "Tested (F)", ReportUtils.map(moh731GreenCardIndicators.hivTestsFemales(), indParams),"");
        cohortDsd.addColumn("HV01-03" , "Tested Facility", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedAtFacility(), indParams),"");
        cohortDsd.addColumn("HV01-04", "Tested Community", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedAtCommunity(), indParams),"");
        cohortDsd.addColumn("HV01-05", "Tested (KVP)", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedKVP(), indParams),"");

        // 1.2 HIV Positive Results
        EmrReportingUtils.addRow(cohortDsd, "HV01", "Tested Positive", ReportUtils.map(moh731GreenCardIndicators.htsPositiveMales(), indParams), htsMaleDisaggregation, Arrays.asList("06", "08", "10", "12", "14"));
        EmrReportingUtils.addRow(cohortDsd, "HV01", "Tested Positive", ReportUtils.map(moh731GreenCardIndicators.htsPositiveFemales(), indParams), htsFemaleDisaggregation, Arrays.asList("07", "09", "11", "13", "15"));
        cohortDsd.addColumn("HV01-16", "Tested Positive (KVP)", ReportUtils.map(moh731GreenCardIndicators.htsPositiveKVP(), indParams),"");
        EmrReportingUtils.addRow(cohortDsd, "HV01", "Tested Discordant", ReportUtils.map(moh731GreenCardIndicators.htsDiscordant(), indParams), genderDisaggregation, Arrays.asList("17", "18"));
        EmrReportingUtils.addRow(cohortDsd, "HV01", "Inconclusive", ReportUtils.map(moh731GreenCardIndicators.inconclusiveResults(), indParams), genderDisaggregation, Arrays.asList("47", "48"));

        //1.3. No. Initiated on PrEP (NEW)
        EmrReportingUtils.addRow(cohortDsd, "HV01", "GP Initiated on PrEP (NEW)", ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPGP(), indParams), genderDisaggregation, Arrays.asList("19", "20"));

        cohortDsd.addColumn("HV01-21", "MSM/MSW Initiated on PrEP (NEW)", ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPMSMAndMSW(), indParams), "");

        cohortDsd.addColumn("HV01-22", "FSW Initiated on PrEP (NEW)",ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPFSW(), indParams),"");

        EmrReportingUtils.addRow(cohortDsd, "HV01", "PWID/PWUD Initiated on PrEP (NEW)", ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPPWIDAndPWUD(), indParams), genderDisaggregation, Arrays.asList("23", "24"));

        EmrReportingUtils.addRow(cohortDsd, "HV01", "Discordant Couple Initiated on PrEP (NEW)", ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPDiscordantCouple(), indParams), genderDisaggregation, Arrays.asList("25", "26"));

        EmrReportingUtils.addRow(cohortDsd, "HV01", "Vulnerable Population Initiated on PrEP (NEW)", ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPVulnerablePop(), indParams), genderDisaggregation, Arrays.asList("27", "28"));

        EmrReportingUtils.addRow(cohortDsd, "HV01", "AYP (15-24yrs) Initiated on PrEP (NEW)", ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPAdolescentsYoungPeople(), indParams), genderDisaggregation, Arrays.asList("29", "30"));

        cohortDsd.addColumn("HV01-31", "Pregnant and breastfeeding Initiated on PrEP (NEW)", ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPPregnantOrBreastfeeding(), indParams),"");

        // 1.4. Number on PrEP Diagnosed with STI
        EmrReportingUtils.addRow(cohortDsd, "HV01", "On PrEP Diagnosed with STI GP",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIGP(), indParams), genderDisaggregation,
                Arrays.asList("32", "33"));

        cohortDsd.addColumn("HV01-34", "On PrEP Diagnosed with STI MSM & MSW",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIMSMMSW(), indParams),"");

        cohortDsd.addColumn("HV01-35", "On PrEP Diagnosed with STI FSW",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIFSW(), indParams),"");

        EmrReportingUtils.addRow(cohortDsd, "HV01", "On PrEP Diagnosed with STI PWID & PWUD",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIPWIDOrPWUD(), indParams), genderDisaggregation,
                Arrays.asList("36", "37"));
        EmrReportingUtils.addRow(cohortDsd, "HV01", "On PrEP Diagnosed with STI Discordant Couple",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIDiscordant(), indParams), genderDisaggregation,
                Arrays.asList("38", "39"));
        EmrReportingUtils.addRow(cohortDsd, "HV01", "On PrEP Diagnosed with STI Vulnerable Population",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIVP(), indParams), genderDisaggregation, Arrays.asList("40", "41"));
        EmrReportingUtils.addRow(cohortDsd, "HV01", "On PrEP Diagnosed with STI AYP",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIAYP(), indParams), genderDisaggregation, Arrays.asList("42", "43"));

        cohortDsd.addColumn("HV01-44", "On PrEP Diagnosed with STI Pregnant or Breastfeeding", ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIPrEOrBF(), indParams),"");

        // 1.5. No. Seroconverted while on PrEP
        EmrReportingUtils.addRow(cohortDsd, "HV01", "Sero-converted-General Population",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPSeroconverted
                        (), indParams), genderDisaggregation,
                Arrays.asList("45", "46"));

        return cohortDsd;

    }

    /**
     * Creates the dataset for section #2: Prevention of Mother-to-Child Transmission
     *
     * @return the dataset
     */
    protected DataSetDefinition pmtctDataSet() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName("2");
        dsd.setDescription("Elimination of Mother-to-Child Transmission");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addDimension("age", ReportUtils.map(commonDimensions.moh731GreenCardAgeGroups(), "onDate=${endDate}"));
        dsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        // 2.1  Maternal HIV Testing
        dsd.addColumn("HV02-01A", "Known Positive at 1st ANC (Antenatal)", ReportUtils.map(moh731GreenCardIndicators.knownPositiveAtFirstANC(), indParams), "");
        dsd.addColumn("HV02-02", "Initial test at ANC (Antenatal)", ReportUtils.map(moh731GreenCardIndicators.initialTestAtANC(), indParams), "");
        dsd.addColumn("HV02-03", "Retest at ANC (Antenatal)", ReportUtils.map(moh731GreenCardIndicators.retestAtANC(), indParams), "");
        dsd.addColumn("HV02-04", "Initial Test at Labor and Delivery", ReportUtils.map(moh731GreenCardIndicators.initialTestAtLabourAndDelivery(), indParams), "");
        dsd.addColumn("HV02-05", "Retest at Labor and Delivery", ReportUtils.map(moh731GreenCardIndicators.retestAtLabourAndDelivery(), indParams), "");
        dsd.addColumn("HV02-06", "Initial Test at PNC <=6 Weeks)", ReportUtils.map(moh731GreenCardIndicators.initialTestAtPNCWithin6Weeks(), indParams), "");
        dsd.addColumn("HV02-07", "PNC Retest within 6 weeks)", ReportUtils.map(moh731GreenCardIndicators.reestAtPNCWithin6Weeks(), indParams), "");
        dsd.addColumn("HV02-08", "PNC Initial Test >6 weeks - 6 months)", ReportUtils.map(moh731GreenCardIndicators.initialTestAtPNCBtwn6WeeksAnd6Months(), indParams), "");
        dsd.addColumn("HV02-09", "PNC retest Test >6 weeks - 6 months)", ReportUtils.map(moh731GreenCardIndicators.retestAtPNCBtwn6WeeksAnd6Months(), indParams), "");

        // 2.2 HIV Positive Results
        dsd.addColumn("HV02-10", "HIV positive ANC", ReportUtils.map(moh731GreenCardIndicators.hivPositiveAtANC(), indParams), "");
        dsd.addColumn("HV02-11", "HIV positive results L&D (Labour & Delivery)", ReportUtils.map(moh731GreenCardIndicators.hivPositiveAtLabourAndDelivery(), indParams), "");
        dsd.addColumn("HV02-12", "HIV positive results PNC <=6 weeks", ReportUtils.map(moh731GreenCardIndicators.hivPositiveAtPNCWithin6Weeks(), indParams), "");
        dsd.addColumn("HV02-13", "HIV positive results PNC >6 weeks - 6 Months)", ReportUtils.map(moh731GreenCardIndicators.hivPositiveAtPNCBtwn6WeeksAnd6Months(), indParams), "");

        //2.3  Maternal HAART
        dsd.addColumn("HV02-14", "On HAART at 1st ANC", ReportUtils.map(moh731GreenCardIndicators.onHAARTAtFirstANC(), indParams), "");
        dsd.addColumn("HV02-15", "Started HAART at ANC", ReportUtils.map(moh731GreenCardIndicators.startHAARTANC(), indParams), "");
        dsd.addColumn("HV02-16", "Started HAART During Labour and Delivery", ReportUtils.map(moh731GreenCardIndicators.startedHAARTLabourAndDelivery(), indParams), "");
        dsd.addColumn("HV02-17", "Started HAART PNC <=6 weeks", ReportUtils.map(moh731GreenCardIndicators.startedHAARTPNCUpto6Weeks(), indParams), "");
        dsd.addColumn("HV02-18", "Started HAART from 7 weeks to 6 months", ReportUtils.map(moh731GreenCardIndicators.onHAARTFrom7WeeksTo6Months(), indParams), "");

        //2.4  HBV Screening at ANC
        dsd.addColumn("HV02-19", "Screened for HBV at ANC", ReportUtils.map(moh731GreenCardIndicators.screenedForHBVAtANC(), indParams), "");
        dsd.addColumn("HV02-20", "Screened Positive for HBV", ReportUtils.map(moh731GreenCardIndicators.screenedPositiveForHBVAtANC(), indParams), "");

        // 2.5  Adolescents girls & Young Women (10-24 Yrs) testing & results

        EmrReportingUtils.addRow(dsd, "HV02","AdolescentS KP at 1st ANC", ReportUtils.map(moh731GreenCardIndicators.knownPositiveAtFirstANC(), indParams), adolescentYoungWomenAgeDisaggregation, Arrays.asList("21", "22"));
        EmrReportingUtils.addRow(dsd, "HV02", "Adolescents New HIV Positive", ReportUtils.map(moh731GreenCardIndicators.newHIVPositiveAtMCH(), indParams), adolescentYoungWomenAgeDisaggregation, Arrays.asList("23", "24"));
        EmrReportingUtils.addRow(dsd, "HV02", "Adolescents on HAART at 1st ANC", ReportUtils.map(moh731GreenCardIndicators.onHAARTAtFirstANC(), indParams), adolescentYoungWomenAgeDisaggregation, Arrays.asList("25", "26"));
        EmrReportingUtils.addRow(dsd, "HV02", "Adolescents started on HAART New (MCH)", ReportUtils.map(moh731GreenCardIndicators.startedHAARTAtMCH(), indParams), adolescentYoungWomenAgeDisaggregation, Arrays.asList("27", "28"));

        // 2.6  Infant Prophylaxis
        dsd.addColumn("HV02-29", "Infant ARV Prophylaxis at ANC", ReportUtils.map(moh731GreenCardIndicators.infantArvProphylaxisANC(), indParams), "");
        dsd.addColumn("HV02-30", "Infant ARV Prophylaxis at Labour and Delivery", ReportUtils.map(moh731GreenCardIndicators.infantArvProphylaxisLabourAndDelivery(), indParams), "");
        dsd.addColumn("HV02-31", "Infant ARV Prophylaxis <=8 weeks PNC", ReportUtils.map(moh731GreenCardIndicators.infantArvProphylaxisPNCLessThan8Weeks(), indParams), "");

        // 2.7 Infant Feeding
        dsd.addColumn("HV02-32", "Exclusive Breastfeeding (EBF)", ReportUtils.map(moh731GreenCardIndicators.exclusiveBFAt6Months6MonthCohort(), indParams), "");
        dsd.addColumn("HV02-33", "Breastfeeding at 6-24 months", ReportUtils.map(moh731GreenCardIndicators.breastFeedingAt6To24Months24MonthCohort(), indParams), "");
        dsd.addColumn("HV02-34", "Not Breastfeeding (NBF)", ReportUtils.map(moh731GreenCardIndicators.notBreastFeedingAt6To24Months24MonthCohort(), indParams), "");

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
        cohortDsd.setDescription("3.HIV and  TB treatment");
        cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cohortDsd.addDimension("age", ReportUtils.map(commonDimensions.moh731GreenCardAgeGroups(), "onDate=${endDate}"));
        cohortDsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));

        String indParams = "startDate=${startDate},endDate=${endDate}";

        // 3.1 (Starting ART)
        EmrReportingUtils.addRow(cohortDsd, "HV03", "Starting ART", ReportUtils.map(moh731GreenCardIndicators.startingArt(), indParams), allAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13","14","15"));

        // 3.2 (Currently on ART [All])
        EmrReportingUtils.addRow(cohortDsd, "HV03", "Current on ART", ReportUtils.map(moh731GreenCardIndicators.currentlyOnArt(), indParams), allAgeDisaggregation, Arrays.asList("15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25","26","27","28"));

        // 3.3 TB Screening
        EmrReportingUtils.addRow(cohortDsd, "HV03", "TB Screening", ReportUtils.map(moh731GreenCardIndicators.screenedForTb(), indParams), under15And15PlusDisaggregation, Arrays.asList("29", "30"));

        // 3.4 Starting TPT
        EmrReportingUtils.addRow(cohortDsd, "HV03", "Started on TPT", ReportUtils.map(moh731GreenCardIndicators.startedOnIPT(), indParams), under15And15PlusDisaggregation, Arrays.asList("31", "32"));

        // 3.5 Differentiated Service Delivery
        cohortDsd.addColumn("HV03-33", "DSD-Established", ReportUtils.map(moh731GreenCardIndicators.dsdEstablished(), indParams),"");
        cohortDsd.addColumn("HV03-34", "DSD-Not Established", ReportUtils.map(moh731GreenCardIndicators.dsdNotEstablished(), indParams),"");
        cohortDsd.addColumn("HV03-35", "Community", ReportUtils.map(moh731GreenCardIndicators.dsdCommunity(), indParams),"");
        cohortDsd.addColumn("HV03-36", "Facility", ReportUtils.map(moh731GreenCardIndicators.dsdFacility(), indParams),"");

        // 3.6 Nutrition and HIV
        EmrReportingUtils.addRow(cohortDsd, "HV03", "SAM+", ReportUtils.map(moh731GreenCardIndicators.severeMalnutrition(), indParams), standardAgeOnlyDisaggregation, Arrays.asList("37", "38","39","40","41"));
        cohortDsd.addColumn("HV03-42", "SAM+ (Pregnant or Lactating)", ReportUtils.map(moh731GreenCardIndicators.severeMalnutritionPregnantOrLactating(), indParams),"");
        EmrReportingUtils.addRow(cohortDsd, "HV03", "MAM+", ReportUtils.map(moh731GreenCardIndicators.moderateMalnutrition(), indParams), standardAgeOnlyDisaggregation, Arrays.asList("43", "44","45","46","47"));
        cohortDsd.addColumn("HV03-48", "MAM+ (Pregnant or Lactating)", ReportUtils.map(moh731GreenCardIndicators.moderateMalnutritionPregnantOrLactating(), indParams),"");
        EmrReportingUtils.addRow(cohortDsd, "HV03", "SAM+ Receiving therapeutic foods", ReportUtils.map(moh731GreenCardIndicators.severeMalnutritionOnTherapeuticFoods(), indParams), standardAgeOnlyDisaggregation, Arrays.asList("49", "50","51","52","53"));
        cohortDsd.addColumn("HV03-54", "SAM+ Receiving Therapeutic foods (Pregnant or Lactating)", ReportUtils.map(moh731GreenCardIndicators.severeMalnutritionOnTherapeuticFoodsPregnantOrLactating(), indParams),"");
        EmrReportingUtils.addRow(cohortDsd, "HV03", "MAM+ Receiving supplemental foods", ReportUtils.map(moh731GreenCardIndicators.moderateMalnutritionOnSupplementalFoods(), indParams), standardAgeOnlyDisaggregation, Arrays.asList("55", "56","57","58","59"));
        cohortDsd.addColumn("HV03-60", "MAM+ Receiving supplemental foods (Pregnant or Lactating)", ReportUtils.map(moh731GreenCardIndicators.moderateMalnutritionOnSupplementaryFoodsPregnantOrLactating(), indParams),"");

        // 3.7 HIV in TB Clinic
        cohortDsd.addColumn("HV03-61", "TB new cases", ReportUtils.map(moh731GreenCardIndicators.tbEnrollment(), indParams),"");
        cohortDsd.addColumn("HV03-62", "TB new cases, Known Positive", ReportUtils.map(moh731GreenCardIndicators.tbNewKnownPositive(), indParams),"");
        cohortDsd.addColumn("HV03-63", "TB new cases, HIV positive", ReportUtils.map(moh731GreenCardIndicators.tbNewTestedHIVPositive(), indParams),"");
        cohortDsd.addColumn("HV03-64", "TB new KP already on HAART", ReportUtils.map(moh731GreenCardIndicators.tbNewAlreadyOnHAART(), indParams),"");
        cohortDsd.addColumn("HV03-65", "TB new start HAART", ReportUtils.map(moh731GreenCardIndicators.tbNewStartingHAART(), indParams),"");

        return cohortDsd;

    }
    /**
     * Creates the dataset for section #4: Voluntary Male Circumcision
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

        // 4.1 Voluntary Male Circumcision
        EmrReportingUtils.addRow(cohortDsd, "HV04", "Number circumcised", ReportUtils.map(moh731GreenCardIndicators.numberCircumcised(), indParams), vmmcDisaggregation, Arrays.asList("01", "02", "03", "04"));

        cohortDsd.addColumn("HV04-05", "Circumcised HIV+", ReportUtils.map(moh731GreenCardIndicators.numberCircumcisedHivPositive(), indParams),"");
        cohortDsd.addColumn("HV04-06", "Circumcised HIV-", ReportUtils.map(moh731GreenCardIndicators.numberCircumcisedHivNegative(), indParams),"");

        cohortDsd.addColumn("HV04-07", "Circumcised Surgical", ReportUtils.map(moh731GreenCardIndicators.numberCircumcisedSurgically(), indParams),"");
        cohortDsd.addColumn("HV04-08", "Circumcised Device", ReportUtils.map(moh731GreenCardIndicators.numberCircumcisedUsingDevice(), indParams),"");

        cohortDsd.addColumn("HV04-09", "AE During Moderate", ReportUtils.map(moh731GreenCardIndicators.circumcisedWithModerateAEDuringProcedure(), indParams),"");
        cohortDsd.addColumn("HV04-10", "AE During Severe", ReportUtils.map(moh731GreenCardIndicators.circumcisedWithSevereAEDuringProcedure(), indParams),"");

        cohortDsd.addColumn("HV04-11", "AE Post Moderate", ReportUtils.map(moh731GreenCardIndicators.circumcisedWithModerateAEPostProcedure(), indParams),"");
        cohortDsd.addColumn("HV04-12", "AE Post Severe", ReportUtils.map(moh731GreenCardIndicators.circumcisedWithSevereAEPostProcedure(), indParams),"");
        cohortDsd.addColumn("HV04-13", "Follow Up Visit <14 days", ReportUtils.map(moh731GreenCardIndicators.followedUpWithin14daysOfVMMCProcedure(), indParams),"");
        return cohortDsd;

    }
}
