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
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimIndicatorLibrary;
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

    @Autowired
    private DatimIndicatorLibrary datimIndicators;

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    ColumnParameters colInfants = new ColumnParameters(null, "<1", "age=<1");

    ColumnParameters maleInfants = new ColumnParameters(null, "<1, Male", "gender=M|age=<1");
    ColumnParameters femaleInfants = new ColumnParameters(null, "<1, Female", "gender=F|age=<1");

    ColumnParameters children_0_to_9 = new ColumnParameters(null, "1-9", "age=0-9");
    ColumnParameters children_1_to_9 = new ColumnParameters(null, "1-9", "age=1-9");
    ColumnParameters adult_10_to_14 = new ColumnParameters(null, "10-14", "age=10-14");
    ColumnParameters adult_15_to_19 = new ColumnParameters(null, "15-19", "age=15-19");
    ColumnParameters adult_20_to_24 = new ColumnParameters(null, "20-24", "age=20-24");
    ColumnParameters adult_25_and_above = new ColumnParameters(null, "25+", "age=25+");

    // specific to pre-art
    ColumnParameters adult_0_to_14 = new ColumnParameters(null, "0-14", "age=0-14");
    ColumnParameters under15 = new ColumnParameters(null, "<15", "age=<15");
    ColumnParameters adult_15_and_above = new ColumnParameters(null, "15+", "age=15+");
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
    ColumnParameters f_18Plus = new ColumnParameters(null, "18+, Female", "gender=F|age=18+");
    ColumnParameters m_20_to_24 = new ColumnParameters(null, "20-24, Male", "gender=M|age=20-24");
    ColumnParameters f_20_to_24 = new ColumnParameters(null, "20-24, Female", "gender=F|age=20-24");
    ColumnParameters m_25_to_29 = new ColumnParameters(null, "25-29, Male", "gender=M|age=25-29");
    ColumnParameters f_25_to_29 = new ColumnParameters(null, "25-29, Female", "gender=F|age=25-29");
    ColumnParameters m_25_and_above = new ColumnParameters(null, "25+, Male", "gender=M|age=25+");
    ColumnParameters f_25_and_above = new ColumnParameters(null, "25+, Female", "gender=F|age=25+");
    ColumnParameters m_30_and_above = new ColumnParameters(null, "30+, Male", "gender=M|age=30+");
    ColumnParameters f_30_and_above = new ColumnParameters(null, "30+, Female", "gender=F|age=30+");
    ColumnParameters m_1_to_9 = new ColumnParameters(null, "1-9, Male", "gender=M|age=1-9");
    ColumnParameters males = new ColumnParameters(null, "Male", "gender=M");
    ColumnParameters females = new ColumnParameters(null, "Female", "gender=F");

    ColumnParameters colTotal = new ColumnParameters(null, "Total", "");

    List<ColumnParameters> standardDisaggregationAgeAndSex = Arrays.asList(
            colInfants, children_1_to_9,  m_10_to_14, f_10_to_14, m_15_to_19, f_15_to_19,
            m_20_to_24, f_20_to_24, m_25_and_above, f_25_and_above , colTotal);

    List<ColumnParameters> standardAgeOnlyDisaggregation = Arrays.asList(
            children_0_to_9,  adult_10_to_14, adult_15_to_19,
            adult_20_to_24, adult_25_and_above , colTotal);

    List<ColumnParameters> standardAgeOnlyDisaggregationWithInfants = Arrays.asList(
            colInfants, children_1_to_9,  adult_10_to_14, adult_15_to_19,
            adult_20_to_24, adult_25_and_above , colTotal);

    List<ColumnParameters> hivAssessmentDisaggregations = Arrays.asList(
             m_15_to_19, m_20_to_24, m_25_to_29, m_30_and_above, f_15_to_19, f_20_to_24, f_25_to_29, f_30_and_above, colTotal);

    List<ColumnParameters> disaggregationWithInfants = Arrays.asList(
            children_0_to_9,  m_10_to_14, f_10_to_14,m_15_to_19, f_15_to_19,
            m_20_to_24,f_20_to_24,m_25_and_above, f_25_and_above , colTotal);

    List<ColumnParameters> allAgeDisaggregation = Arrays.asList(
            maleInfants, femaleInfants, m_1_to_4,  f_1_to_4, m_5_to_9, f_5_to_9, m_10_to_14, f_10_to_14,m_15_to_19, f_15_to_19,
            m_20_to_24, f_20_to_24, m_25_and_above, f_25_and_above);

    List<ColumnParameters> preARTDisaggregation = Arrays.asList(
            adult_0_to_14,  adult_15_and_above , colTotal);
    List<ColumnParameters> females18PlusDisaggregation = Arrays.asList(f_18Plus);
    List<ColumnParameters> vmmcDisaggregation = Arrays.asList(
            maleInfants, m_1_to_9, m_10_to_14, m_15_to_19, m_20_to_24, m_25_and_above, colTotal);

    List<ColumnParameters> adolescentWomenAgeDisagregation = Arrays.asList(f10_to_19,colTotal);
    List<ColumnParameters> adolescentYoungWomenAgeDisaggregation = Arrays.asList(f10_to_19,f_20_to_24);
    List<ColumnParameters> maleDisaggregation = Arrays.asList(males);
    List<ColumnParameters> femaleDisaggregation = Arrays.asList(females);
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

       //1.1 HIV Tests
        cohortDsd.addColumn("HV01-01", "Tested (M)", ReportUtils.map(moh731GreenCardIndicators.htsTestsMales(), indParams),"");
        cohortDsd.addColumn("HV01-02", "Tested (F)", ReportUtils.map(moh731GreenCardIndicators.hivTestsFemales(), indParams),"");
      //  EmrReportingUtils.addRow(cohortDsd, "HV02", "Tested", ReportUtils.map(moh731GreenCardIndicators.htsNumberTested(), indParams), genderDisaggregation, Arrays.asList("01", "02"));
        cohortDsd.addColumn("HV01-03" , "Tested Facility (M)", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedAtFacilityMales(), indParams),"");
        cohortDsd.addColumn("HV01-04", "Tested Community (M)", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedAtCommunityMales(), indParams),"");
        cohortDsd.addColumn("HV01-05", "Tested KVP", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedKVPMales(), indParams),"");

        // 1.2 HIV Positive Results
        EmrReportingUtils.addRow(cohortDsd, "HV01", "Tested Positive", ReportUtils.map(moh731GreenCardIndicators.htsPositiveMales(), indParams), htsMaleDisaggregation, Arrays.asList("06", "08", "10", "12", "14"));
        EmrReportingUtils.addRow(cohortDsd, "HV01", "Tested Positive", ReportUtils.map(moh731GreenCardIndicators.htsPositiveFemales(), indParams), htsFemaleDisaggregation, Arrays.asList("07", "09", "11", "13", "15"));
        cohortDsd.addColumn("HV01-16", "Tested Positive (KVP)", ReportUtils.map(moh731GreenCardIndicators.htsPositiveKVP(), indParams),"");
        EmrReportingUtils.addRow(cohortDsd, "HV01", "Tested Discordant", ReportUtils.map(moh731GreenCardIndicators.htsDiscordant(), indParams), genderDisaggregation, Arrays.asList("17", "18"));

        //1.3. No. Initiated on PrEP (NEW)

        EmrReportingUtils.addRow(cohortDsd, "HV01", "GP Initiated on PrEP (NEW)", ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPGP(), indParams), genderDisaggregation, Arrays.asList("19", "20"));

        cohortDsd.addColumn("HV01-21", "MSM/MSW Initiated on PrEP (NEW)", ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPMSMAndMSW(), indParams), "");

        cohortDsd.addColumn("HV01-22", "FSW Initiated on PrEP (NEW)",ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPFSW(), indParams),"");

        EmrReportingUtils.addRow(cohortDsd, "HV01", "PWID/PWUD Initiated on PrEP (NEW)", ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPPWIDAndPWUD(), indParams), genderDisaggregation, Arrays.asList("23", "24"));

        EmrReportingUtils.addRow(cohortDsd, "HV01", "Discordant Couple Initiated on PrEP (NEW)", ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPDiscordantCouple(), indParams), genderDisaggregation, Arrays.asList("25", "26"));

        EmrReportingUtils.addRow(cohortDsd, "HV01", "Vulnerable Population Initiated on PrEP (NEW)", ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPVulnerablePop(), indParams), genderDisaggregation, Arrays.asList("27", "28"));

        EmrReportingUtils.addRow(cohortDsd, "HV01", "AYP (15-24yrs) Initiated on PrEP (NEW)", ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPAdolescentsYoungPeople(), indParams), genderDisaggregation, Arrays.asList("29", "30"));

        cohortDsd.addColumn("HV01-31", "Pregnant and breastfeeding Initiated on PrEP (NEW)", ReportUtils.map(moh731GreenCardIndicators.initiatedOnPrEPPregnantOrBreastfeeding(), indParams),"");

        // 1.4. No. Current on PrEP
        EmrReportingUtils.addRow(cohortDsd, "HV01", "Current on PrEP GP", ReportUtils.map(moh731GreenCardIndicators.currentOnPrEPGP(), indParams), genderDisaggregation, Arrays.asList("32", "33"));

        cohortDsd.addColumn("HV01-34", "Current on PrEP MSM and MSW", ReportUtils.map(moh731GreenCardIndicators.currentOnPrEPMSMAndMSW(), indParams),"");

        cohortDsd.addColumn("HV01-35","Current on PrEP FSW", ReportUtils.map(moh731GreenCardIndicators.currentOnPrEPFSW(), indParams), "");
        EmrReportingUtils.addRow(cohortDsd, "HV01", "Current on PrEP PWID and PWUD", ReportUtils.map(moh731GreenCardIndicators.currentOnPrEPPWIDAndPWUD(), indParams), genderDisaggregation, Arrays.asList("36", "37"));
        EmrReportingUtils.addRow(cohortDsd, "HV01", "Current on PrEP Discordant Couple", ReportUtils.map(moh731GreenCardIndicators.currentOnPrEPDiscordantCouple(), indParams), genderDisaggregation, Arrays.asList("38", "39"));
        EmrReportingUtils.addRow(cohortDsd, "HV01", "Current on PrEP Vulnerable Population", ReportUtils.map(moh731GreenCardIndicators.currentOnPrEPVulnerablePopulation(), indParams), genderDisaggregation, Arrays.asList("40", "41"));

        EmrReportingUtils.addRow(cohortDsd, "HV01", "Current on PrEP AYP", ReportUtils.map(moh731GreenCardIndicators.currentOnPrEPAdolescentsYoungPeople(), indParams), genderDisaggregation, Arrays.asList("42", "43"));

        cohortDsd.addColumn("HV01-44", "Current on PrEP Pregnant or Breastfeeding", ReportUtils.map(moh731GreenCardIndicators.currentOnPrEPPregnantOrBreastfeeding(), indParams),"");

        // 1.5. Number on PrEP Diagnosed with STI
        EmrReportingUtils.addRow(cohortDsd, "HV01", "On PrEP Diagnosed with STI GP",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIGP(), indParams), genderDisaggregation,
                Arrays.asList("45", "46"));

        cohortDsd.addColumn("HV01-47", "On PrEP Diagnosed with STI MSM & MSW",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIMSMMSW(), indParams),"");

        cohortDsd.addColumn("HV01-48", "On PrEP Diagnosed with STI FSW",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIFSW(), indParams),"");

        EmrReportingUtils.addRow(cohortDsd, "HV01", "On PrEP Diagnosed with STI PWID & PWUD",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIPWIDOrPWUD(), indParams), genderDisaggregation,
                Arrays.asList("49", "50"));
        EmrReportingUtils.addRow(cohortDsd, "HV01", "On PrEP Diagnosed with STI Discordant Couple",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIDiscordant(), indParams), genderDisaggregation,
                Arrays.asList("51", "52"));
        EmrReportingUtils.addRow(cohortDsd, "HV01", "On PrEP Diagnosed with STI Vulnerable Population",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIVP(), indParams), genderDisaggregation, Arrays.asList("53", "54"));
        EmrReportingUtils.addRow(cohortDsd, "HV01", "On PrEP Diagnosed with STI AYP",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIAYP(), indParams), genderDisaggregation, Arrays.asList("55", "56"));

        cohortDsd.addColumn("HV01-57", "Current on PrEP Pregnant or Breastfeeding", ReportUtils.map(moh731GreenCardIndicators.onPrEPDiagnosedWithSTIPrEOrBF(), indParams),"");

        // 1.6. No. Seroconverted while on PrEP
        EmrReportingUtils.addRow(cohortDsd, "HV01", "Sero-converted-General Population",
                ReportUtils.map(moh731GreenCardIndicators.onPrEPSeroconverted
                        (), indParams), genderDisaggregation,
                Arrays.asList("58", "59"));

       // cohortDsd.addColumn("HV01-13", "Tested New", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedNew(), indParams),"");
        //cohortDsd.addColumn("HV01-14", "Tested Repeat", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedRepeat(), indParams),"");
        //cohortDsd.addColumn("HV01-15", "Tested Couples", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedAsCouple(), indParams),"");
        //cohortDsd.addColumn("HV01-16", "Tested Key Pop", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedKeyPopulation(), indParams),"");

        // EmrReportingUtils.addRow(cohortDsd, "HV01", "Positive", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedPositive(), indParams), disaggregationWithInfants, Arrays.asList("17", "18", "19", "20", "21", "22", "23", "24", "25", "26"));
        // cohortDsd.addColumn("HV01-27", "Negative Total", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedNegative(), indParams),"");
        // cohortDsd.addColumn("HV01-28", "Discordant", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedDiscordant(), indParams),"");
        // cohortDsd.addColumn("HV01-29", "Positive Key Pop", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedKeypopPositive(), indParams),"");

        // number linked
       // EmrReportingUtils.addRow(cohortDsd, "HV01", "Linked", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedPositiveAndLinked(), indParams), standardAgeOnlyDisaggregation, Arrays.asList("30", "31", "32", "33", "34", "35"));
        //cohortDsd.addColumn("HV01-36", "Total tested positive (3 months ago)", ReportUtils.map(moh731GreenCardIndicators.htsNumberTestedPositiveThreeMonthsAgo(), indParams),"");
        //EmrReportingUtils.addRow(cohortDsd, "HV01", "Number assessed for HIV risk", ReportUtils.map(moh731GreenCardIndicators.numberAssessedForHIVRisk(), indParams), hivAssessmentDisaggregations, Arrays.asList("37", "38", "39", "40", "41", "42", "43", "44","45"));
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
        dsd.addColumn("HV02-01", "Known Positive at 1st ANC (Antenatal)", ReportUtils.map(moh731GreenCardIndicators.knownPositiveAtFirstANC(), indParams), "");
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

        //dsd.addColumn("HV02-54", "Exclusive Replacement Feeding at 6 months", ReportUtils.map(moh731GreenCardIndicators.exclusiveRFAt6Months12MonthCohort(), indParams), "");
        //dsd.addColumn("HV02-55", "Mixed Feeding at 6 months", ReportUtils.map(moh731GreenCardIndicators.mixedFeedingAt6Months12MonthCohort(), indParams), "");
        //dsd.addColumn("HV02-56", "Breast Feeding at 12 months", ReportUtils.map(moh731GreenCardIndicators.breastFeedingAt12Months12MonthCohort(), indParams), "");
        //dsd.addColumn("HV02-57", "Not Breast Feeding at 12 months", ReportUtils.map(moh731GreenCardIndicators.notBreastFeedingAt12Months12MonthCohort(), indParams), "");


        //dsd.addColumn("HV02-20", "Total Maternal HAART", ReportUtils.map(moh731GreenCardIndicators.totalMaternalHAART(), indParams), "");

       // dsd.addColumn("HV02-22", "On HAART Upto 12 months", ReportUtils.map(moh731GreenCardIndicators.onHAARTUpto12Months(), indParams), "");

        //Updates
        //dsd.addColumn("HV02-01", "First ANC Visit", ReportUtils.map(moh731GreenCardIndicators.firstANCVisitMchmsAntenatal(), indParams), "");
        //dsd.addColumn("HV02-02", "Delivery from HIV+ Mothers(Labor and Delivery)", ReportUtils.map(moh731GreenCardIndicators.deliveryFromHIVPositiveMothers(), indParams), "");


        //dsd.addColumn("HV02-07", "Known HIV Status Total)", ReportUtils.map(moh731GreenCardIndicators.testedForHivInMchms(), indParams), "");


       // dsd.addColumn("HV02-15", "HIV Positive results PNC >6 weeks and <=6 months", ReportUtils.map(moh731GreenCardIndicators.pncHIVPositiveBetween7weeksAnd6Months(), indParams), "");

        //dsd.addColumn("HV02-23", "Net Cohort at 12 months", ReportUtils.map(moh731GreenCardIndicators.netCohortAt12Months(), indParams), "");
        //dsd.addColumn("HV02-24", "Syphilis screened at ANC", ReportUtils.map(moh731GreenCardIndicators.syphilisScreenedAtANC(), indParams), "");
        //dsd.addColumn("HV02-25", "Syphilis screened Positive", ReportUtils.map(moh731GreenCardIndicators.syphilisScreenedPositive(), indParams), "");
        //dsd.addColumn("HV02-26", "Syphilis Treated", ReportUtils.map(moh731GreenCardIndicators.syphilisTreated(), indParams), "");
        //dsd.addColumn("HV02-27", "HIV+ on Modern FP at 6 weeks", ReportUtils.map(moh731GreenCardIndicators.HIVPositiveOnModernFPUpto6Weeks(), indParams), "");
        //dsd.addColumn("HV02-28", "HIV+ PNC visits at 6 weeks", ReportUtils.map(moh731GreenCardIndicators.HIVPositivePNCVisitsAt6Weeks(), indParams), "");
        //dsd.addColumn("HV02-29", "Known partner HIV status at 1st Contact", ReportUtils.map(moh731GreenCardIndicators.knownHIVStatusAt1stContact(), indParams), "");
        //dsd.addColumn("HV02-30", "Initial Test at ANC Male", ReportUtils.map(moh731GreenCardIndicators.initialTestAtANCForMale(), indParams), "");
        //dsd.addColumn("HV02-31", "Initial Test at Delivery Male", ReportUtils.map(moh731GreenCardIndicators.initialTestAtDeliveryForMale(), indParams), "");
        //dsd.addColumn("HV02-32", "Initial Test at PNC Male", ReportUtils.map(moh731GreenCardIndicators.initialTestAtPNCForMale(), indParams), "");
        //dsd.addColumn("HV02-32", "Total Known status Male", ReportUtils.map(moh731GreenCardIndicators.totalKnownHIVStatusMale(), indParams), "");
        /*Adolescents (10-19 years) Testing results*/

        /*Infant HIV Exposure status at Penta 1*/
        // dsd.addColumn("HV02-37", "Known Exposure at Penta 1", ReportUtils.map(moh731GreenCardIndicators.knownExposureAtPenta1(), indParams), "");
        // dsd.addColumn("HV02-38", "Total given Penta 1", ReportUtils.map(moh731GreenCardIndicators.totalGivenPenta1(), indParams), "");
        /*Infant ARV Prophylaxis*/
  /*      dsd.addColumn("HV02-43", "HEI CTS/DDS Start <2 months", ReportUtils.map(moh731GreenCardIndicators.heiDDSCTXStartLessThan2Months(), indParams), "");
        dsd.addColumn("HV02-44", "Initial PCR <8 weeks", ReportUtils.map(moh731GreenCardIndicators.initialPCRLessThan8Weeks(), indParams), "");
        dsd.addColumn("HV02-45", "Initial PCR >8 weeks to 12 months", ReportUtils.map(moh731GreenCardIndicators.initialPCROver8WeeksTo12Months(), indParams), "");
        dsd.addColumn("HV02-46", "Total Initial PCR Test <12 months", ReportUtils.map(moh731GreenCardIndicators.totalInitialPCRTestLessThan12Months(), indParams), "");
        dsd.addColumn("HV02-47", "Infected in 24 months", ReportUtils.map(datimIndicators.hivInfectedHEI(), indParams), "");
        dsd.addColumn("HV02-48", "Uninfected in 24 months", ReportUtils.map(datimIndicators.hivUninfectedHEI(), indParams), "");
        dsd.addColumn("HV02-49", "Unknown Outcomes in 24 months", ReportUtils.map(datimIndicators.unknownHIVStatusHEI(), indParams), "");
        dsd.addColumn("HV02-50", "Net Cohort HEI in 24 months", ReportUtils.map(moh731GreenCardIndicators.netCohortHeiIn24Months(), indParams), "");
        dsd.addColumn("HV02-51", "Mother-baby pairs in 24 months", ReportUtils.map(moh731GreenCardIndicators.motherBabyPairsIn24Months(), indParams), "");
        dsd.addColumn("HV02-52", "Pair net cohort in 24 months", ReportUtils.map(moh731GreenCardIndicators.pairNetCohortIn24Months(), indParams), "");
*/
        //End updates

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

       /* cohortDsd.addColumn("HV03-01", "HIV Exposed Infants (within 2 months)", ReportUtils.map(moh731GreenCardIndicators.hivExposedInfantsWithin2Months(), indParams), "");
        cohortDsd.addColumn("HV03-02", "HIV Exposed Infants (Eligible for CTX at 2 months)", ReportUtils.map(moh731GreenCardIndicators.hivExposedInfantsWithin2MonthsAndEligibleForCTX(), indParams), "");*/

        // 3.1 (Starting ART)
        EmrReportingUtils.addRow(cohortDsd, "HV03", "Starting ART", ReportUtils.map(moh731GreenCardIndicators.startingArt(), indParams), allAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13","14","15"));

        // 3.2 (Currently on ART [All])
        EmrReportingUtils.addRow(cohortDsd, "HV03", "Current on ART", ReportUtils.map(moh731GreenCardIndicators.currentlyOnArt(), indParams), allAgeDisaggregation, Arrays.asList("15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25","26","27","28"));

        // 3.3 TB Screening
        EmrReportingUtils.addRow(cohortDsd, "HV03", "TB Screening", ReportUtils.map(moh731GreenCardIndicators.screenedForTb(), indParams), under15And15PlusDisaggregation, Arrays.asList("29", "30"));
        //cohortDsd.addColumn("HV03-058", "Presumed TB_Total", ReportUtils.map(moh731GreenCardIndicators.presumedForTb(), indParams),"");

        // 3.4 Starting TPT
        EmrReportingUtils.addRow(cohortDsd, "HV03", "Started on TPT", ReportUtils.map(moh731GreenCardIndicators.startedOnIPT(), indParams), under15And15PlusDisaggregation, Arrays.asList("31", "32"));
        //cohortDsd.addColumn("HV03-066", "Completed TPT 12 months", ReportUtils.map(moh731GreenCardIndicators.ipt12MonthsCohort(), indParams),"");

        // 3.1 (Enrolled in Care)
        EmrReportingUtils.addRow(cohortDsd, "HV03", "Enrolled in care", ReportUtils.map(moh731GreenCardIndicators.newHivEnrollment(), indParams), standardDisaggregationAgeAndSex, Arrays.asList("001", "002", "003", "004", "005", "006", "007", "008", "009", "010", "011"));

        // 3.1 (KPs Enrolled in Care)
        cohortDsd.addColumn( "HV03-12", "KPs Enrolled in care", ReportUtils.map(moh731GreenCardIndicators.hivEnrolledKPs(), indParams), "");

        // 3.2 (Pre-ART)
        EmrReportingUtils.addRow(cohortDsd, "HV03", "Pre-Art", ReportUtils.map(moh731GreenCardIndicators.preArtCohort(), indParams), preARTDisaggregation, Arrays.asList("013", "014", "015"));


        // 3.3 (KPs starting ART)
        cohortDsd.addColumn( "HV03-027", "KPs Starting ART", ReportUtils.map(moh731GreenCardIndicators.kpsStartedOnART(), indParams), "");


        // 3.4 (KPs Currently on ART)
        cohortDsd.addColumn( "HV03-039", "KPs Current on ART", ReportUtils.map(moh731GreenCardIndicators.kpsCurrentlyOnArtOnART(), indParams), "");

        // 3.5 (Survival and Retention on ART at 12 months)
        cohortDsd.addColumn("HV03-040", "On therapy at 12 months (Total) ", ReportUtils.map(moh731GreenCardIndicators.onTherapyAt12Months(), indParams), "");
        cohortDsd.addColumn("HV03-041", "ART Net Cohort at 12 months", ReportUtils.map(moh731GreenCardIndicators.art12MonthNetCohort(), indParams), "");

        cohortDsd.addColumn("HV03-042", " Viral load Suppressed <1000cp/mls last 12 mths ", ReportUtils.map(moh731GreenCardIndicators.patientsWithSuppressedVlLast12Months(), indParams), "");
        cohortDsd.addColumn("HV03-043", " Patients with Viral load results last 12 mths ", ReportUtils.map(moh731GreenCardIndicators.patientsWithVLResultsLast12Months(), indParams), "");

        // 3.6 on CTX/Dapsone
        EmrReportingUtils.addRow(cohortDsd, "HV03", "On CTX/Dapsone", ReportUtils.map(moh731GreenCardIndicators.onCotrimoxazoleProphylaxis(), indParams), standardAgeOnlyDisaggregationWithInfants, Arrays.asList("044", "045", "046", "047", "048", "049", "050"));


        //3.9 Nutrition and HIV
        EmrReportingUtils.addRow(cohortDsd, "HV03", "Nutrition assessment", ReportUtils.map(moh731GreenCardIndicators.assessedForNutritionInHIV(), indParams), preARTDisaggregation, Arrays.asList("067", "068","069"));
        EmrReportingUtils.addRow(cohortDsd, "HV03", "Malnourished", ReportUtils.map(moh731GreenCardIndicators.malnourishedInHIV(), indParams), preARTDisaggregation, Arrays.asList("070","071","072"));
        // 3.10
        cohortDsd.addColumn("HV03-076", "TB new cases", ReportUtils.map(moh731GreenCardIndicators.tbEnrollment(), indParams),"");
        cohortDsd.addColumn("HV03-077", "TB new cases, Known Positive", ReportUtils.map(moh731GreenCardIndicators.tbNewKnownPositive(), indParams),"");
        cohortDsd.addColumn("HV03-078", "TB new cases, tested for HIV", ReportUtils.map(moh731GreenCardIndicators.tbTestedForHIV(), indParams),"");
        cohortDsd.addColumn("HV03-080", "TB new cases, HIV positive", ReportUtils.map(moh731GreenCardIndicators.tbNewTestedHIVPositive(), indParams),"");
        cohortDsd.addColumn("HV03-082", "TB already on HAART", ReportUtils.map(moh731GreenCardIndicators.tbNewAlreadyOnHAART(), indParams),"");
        cohortDsd.addColumn("HV03-083", "TB new cases start HAART", ReportUtils.map(moh731GreenCardIndicators.tbNewStartingHAART(), indParams),"");
        cohortDsd.addColumn("HV03-084", "TB total on HAART", ReportUtils.map(moh731GreenCardIndicators.tbTotalOnHAART(), indParams),"");
        // 3.12
        cohortDsd.addColumn("HV03-087", "Screen Cacx new F18+", ReportUtils.map(moh731GreenCardIndicators.screenedforCaCx(), indParams),"");
        EmrReportingUtils.addRow(cohortDsd, "HV03-088","Clinical Visits", ReportUtils.map(moh731GreenCardIndicators.hivCareVisitsTotal(), indParams),females18PlusDisaggregation, Arrays.asList("01"));
        cohortDsd.addColumn("HV03-089", "Modern contraceptive methods", ReportUtils.map(moh731GreenCardIndicators.modernContraceptivesProvided(), indParams), "");
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

        // 4.1 Voluntary Male Circumcision
        EmrReportingUtils.addRow(cohortDsd, "HV04", "Tested", ReportUtils.map(moh731GreenCardIndicators.numberCircumcised(), indParams), vmmcDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07"));
        cohortDsd.addColumn("HV04-08", "Circumcised HIV+", ReportUtils.map(moh731GreenCardIndicators.numberCircumcisedHivPositive(), indParams),"");
        cohortDsd.addColumn("HV04-09", "Circumcised HIV-", ReportUtils.map(moh731GreenCardIndicators.numberCircumcisedHivNegative(), indParams),"");
        cohortDsd.addColumn("HV04-10", "Circumcised HIV NK", ReportUtils.map(moh731GreenCardIndicators.numberCircumcisedHivUnknown(), indParams),"");
        cohortDsd.addColumn("HV04-11", "Circumcised Surgical", ReportUtils.map(moh731GreenCardIndicators.numberCircumcisedSurgically(), indParams),"");
        cohortDsd.addColumn("HV04-12", "Circumcised Device", ReportUtils.map(moh731GreenCardIndicators.numberCircumcisedUsingDevice(), indParams),"");
        cohortDsd.addColumn("HV04-13", "AE During Moderate", ReportUtils.map(moh731GreenCardIndicators.circumcisedWithModerateAEDuringProcedure(), indParams),"");
        cohortDsd.addColumn("HV04-14", "AE During Severe", ReportUtils.map(moh731GreenCardIndicators.circumcisedWithSevereAEDuringProcedure(), indParams),"");
        cohortDsd.addColumn("HV04-15", "AE Post Moderate", ReportUtils.map(moh731GreenCardIndicators.circumcisedWithModerateAEPostProcedure(), indParams),"");
        cohortDsd.addColumn("HV04-16", "AE Post Servere", ReportUtils.map(moh731GreenCardIndicators.circumcisedWithSevereAEPostProcedure(), indParams),"");
        cohortDsd.addColumn("HV04-17", "Follow Up Visit <14 days", ReportUtils.map(moh731GreenCardIndicators.followedUpWithin14daysOfVMMCProcedure(), indParams),"");
        return cohortDsd;

    }
}
