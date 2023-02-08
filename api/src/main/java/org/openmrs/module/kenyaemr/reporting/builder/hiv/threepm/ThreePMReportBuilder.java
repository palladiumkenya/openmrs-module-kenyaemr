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
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.kenyaemr.reporting.library.threepm.ThreePMIndicatorLibrary;
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
    private ThreePMIndicatorLibrary threePMIndicators;

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String FSW = "FSW";
    public static final String MSM = "MSM";
    public static final String PWID = "PWID";
    public static final String TG = "Transgender";
    public static final String IN_PRISONS = "People in prison and other closed settings";

    /*Age/sex disaggregations*/
    ColumnParameters female = new ColumnParameters(null, "<Female", "gender=F");
    ColumnParameters male = new ColumnParameters(null, "<male", "gender=M");
    ColumnParameters infant = new ColumnParameters(null, "<1, Male and Female", "age=<1");
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
    ColumnParameters f45_to49 = new ColumnParameters(null, "45-49, Female", "gender=F|age=45-49");
    ColumnParameters m45_to49 = new ColumnParameters(null, "45-49, Male", "gender=M|age=45-49");
    ColumnParameters f25AndAbove = new ColumnParameters(null, "25+, Female", "gender=F|age=25+");
    ColumnParameters m25AndAbove = new ColumnParameters(null, "25+, Male", "gender=M|age=25+");
    ColumnParameters fAbove50 = new ColumnParameters(null, "50+, Female", "gender=F|age=50+");
    ColumnParameters mAbove50 = new ColumnParameters(null, "50+, Male", "gender=M|age=50+");

    ColumnParameters colTotal = new ColumnParameters(null, "Total", "");

    List<ColumnParameters> childrenAgeDisaggregation = Arrays.asList(
            infant, all1_to9);
    List<ColumnParameters> standardAgeAndSexDisaggregation = Arrays.asList(
            f10_to14, m10_to14, f15_to19, m15_to19, f20_to24, m20_to24, f25_to29, m25_to29, f30_to34, m30_to34, f35_to39, m35_to39, f40_to44, m40_to44, f45_to49, m45_to49, fAbove50, mAbove50);

    List<ColumnParameters> ct3geAndSexDisaggregation = Arrays.asList(
            f0_to14, m0_to14, f15_to19, m15_to19, f20_to24, m20_to24,f25AndAbove, m25AndAbove);
    List<ColumnParameters> sexDisaggregation = Arrays.asList(female,male);

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
                ReportUtils.map(careAndTreatmentDataset(), "startDate=${startDate},endDate=${endDate}")
                /*//HTS
                ReportUtils.map(htsDataset(), "startDate=${startDate},endDate=${endDate}"),
                //VMMC
                ReportUtils.map(vmmcDataset(), "startDate=${startDate},endDate=${endDate}"),
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

        String indParams = "startDate=${startDate},endDate=${endDate}";
        dsd.addColumn("Screened", "Screened", ReportUtils.map(threePMIndicators.htsScreened(), indParams), "");
        //dsd.addColumn("Known Positive", "Known Positive", ReportUtils.map(threePMIndicators.getKnownPositive(), indParams), "");
        dsd.addColumn("Eligible", "Total Screened", ReportUtils.map(threePMIndicators.eligibleForHIVTest(), indParams), "");
        dsd.addColumn("Tested", "Total tested", ReportUtils.map(threePMIndicators.htsNumberTested(), indParams), "");
        dsd.addColumn("New Positive", "Newly tested HIV positive", ReportUtils.map(threePMIndicators.newPositive(), indParams), "");
        dsd.addColumn("Linked", "Newly tested HIV positive", ReportUtils.map(threePMIndicators.linked(), indParams), "");

        //MNCH-ANC
        dsd.addColumn("1st ANC visit clients", "1st ANC visit clients", ReportUtils.map(threePMIndicators.firstANCVisitMchmsAntenatal(), indParams), "");
        //dsd.addColumn("1st ANC clients - recent known negative", "1st ANC visit clients - recent known negative", ReportUtils.map(threePMIndicators.knownPositiveAtFirstANC(), indParams), "");
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

        String indParams = "startDate=${startDate},endDate=${endDate}";
        EmrReportingUtils.addRow(dsd, "Kekp0WkqyBp", "CTV2_Result:Below 10 years", ReportUtils.map(moh731GreenCardIndicators.currentlyOnArt(), indParams), childrenAgeDisaggregation, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd, "ynQPCzoY3Vp", "CTV2_Result:10 years and above", ReportUtils.map(moh731GreenCardIndicators.currentlyOnArt(), indParams), standardAgeAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"));
        EmrReportingUtils.addRow(dsd, "FEyLrThIhS3", "CTV3: Current on treatment", ReportUtils.map(moh731GreenCardIndicators.currentlyOnArt(), indParams), ct3geAndSexDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));

        EmrReportingUtils.addRow(dsd, "foX53QuynMF", "KPV2_Result: Number of KPs currently active in the DICE/Program-PWID", ReportUtils.map(threePMIndicators.kpCurr(PWID), indParams), sexDisaggregation, Arrays.asList("01", "02"));
        dsd.addColumn("foX53QuynMF", "KPV2_Result: Number of KPs currently active in the DICE/Program-FSW", ReportUtils.map(threePMIndicators.kpCurr(FSW), indParams), "");
        dsd.addColumn("foX53QuynMF", "KPV2_Result: Number of KPs currently active in the DICE/Program-MSM", ReportUtils.map(threePMIndicators.kpCurr(MSM), indParams), "");
        EmrReportingUtils.addRow(dsd, "mFqL1MztVdm", "KPV2_Result: Number of KPs currently active on ART at the DICE-PWID", ReportUtils.map(threePMIndicators.currentlyOnARTOnSite(PWID), indParams), sexDisaggregation, Arrays.asList("01", "02"));
        dsd.addColumn( "mFqL1MztVdm", "KPV2_Result: Number of KPs currently active on ART at the DICE-FSW", ReportUtils.map(threePMIndicators.currentlyOnARTOnSite(FSW), indParams), "");
        dsd.addColumn("mFqL1MztVdm", "KPV2_Result: Number of KPs currently active on ART at the DICE-MSM", ReportUtils.map(threePMIndicators.currentlyOnARTOnSite(MSM), indParams), "");

        EmrReportingUtils.addRow(dsd, "AMVV35LIVS7", "KPV2_Result: Number of KPs currently on PrEP-PWID", ReportUtils.map(threePMIndicators.kpCurrOnPrEP(PWID), indParams), sexDisaggregation, Arrays.asList("01", "02"));
        dsd.addColumn("AMVV35LIVS7", "KPV2_Result: Number of KPs currently on PrEP-FSW", ReportUtils.map(threePMIndicators.kpCurrOnPrEP(FSW), indParams), "");
        dsd.addColumn("AMVV35LIVS7", "KPV2_Result: Number of KPs currently on PrEP-MSM", ReportUtils.map(threePMIndicators.kpCurrOnPrEP(MSM), indParams), "");

        EmrReportingUtils.addRow(dsd, "QITtH6Wk9fc", "KPV2_Result: Number of KPs currently on PrEP who were diagnosed with STI-PWID", ReportUtils.map(threePMIndicators.kpCurrOnPrEPWithSTI(PWID), indParams), sexDisaggregation, Arrays.asList("01", "02"));
        dsd.addColumn("QITtH6Wk9fc", "KPV2_Result: Number of KPs currently on PrEP who were diagnosed with STI-FSW", ReportUtils.map(threePMIndicators.kpCurrOnPrEPWithSTI(FSW), indParams), "");
        dsd.addColumn("QITtH6Wk9fc", "KPV2_Result: Number of KPs currently on PrEP who were diagnosed with STI-MSM", ReportUtils.map(threePMIndicators.kpCurrOnPrEPWithSTI(MSM), indParams), "");

        EmrReportingUtils.addRow(dsd, "Eh9TBtkpGgP", "KPV2_Result: Number of KPs currently active on ART at other CCC-PWID", ReportUtils.map(threePMIndicators.kpCurrOnPrEPWithSTI(PWID), indParams), sexDisaggregation, Arrays.asList("01", "02"));
        dsd.addColumn("Eh9TBtkpGgP", "KPV2_Result: Number of KPs currently active on ART at other CCC-FSW", ReportUtils.map(threePMIndicators.kpCurrOnPrEPWithSTI(FSW), indParams), "");
        dsd.addColumn("Eh9TBtkpGgP", "KPV2_Result: Number of KPs currently active on ART at other CCC-MSM", ReportUtils.map(threePMIndicators.kpCurrOnPrEPWithSTI(MSM), indParams), "");

        //moh731bIndicators.currentlyOnARTOffSite

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