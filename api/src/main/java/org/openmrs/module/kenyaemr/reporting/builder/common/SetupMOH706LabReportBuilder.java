/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.builder.common;

import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.MohReportUtils.ReportAddonUtils;
import org.openmrs.module.kenyaemr.reporting.MohReportUtils.ReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.MOH706.Moh706LabCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.MOH706.Moh706IndicatorLibrary;
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

import static org.openmrs.module.kenyacore.report.ReportUtils.map;

@Component
@Builds({ "kenyaemr.ehrReports.706.report" })
public class SetupMOH706LabReportBuilder extends AbstractReportBuilder {


    private final Moh706IndicatorLibrary moh706IndicatorLibrary;

    @Autowired
    public SetupMOH706LabReportBuilder(Moh706LabCohortLibrary moh706CohortLibrary, Moh706IndicatorLibrary moh706IndicatorLibrary) {
        this.moh706IndicatorLibrary = moh706IndicatorLibrary;
    }

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
                Date.class), new Parameter("dateBasedReporting", "", String.class));
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,
                                                            ReportDefinition reportDefinition) {
        return Arrays.asList(map(getMoh706LabDatasetDefinition(),
                "startDate=${startDate},endDate=${endDate+23h}"));
    }

    private DataSetDefinition getMoh706LabDatasetDefinition() {
        CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
        String indParam = "startDate=${startDate},endDate=${endDate}";
        cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cohortDsd.setName("MOH706");
        cohortDsd.setDescription("MOH 706 for the lab");
        cohortDsd.addDimension("age", ReportUtils.map(ReportingUtils.getAge(), "effectiveDate=${endDate}"));


     //URINE ANALYSIS
        cohortDsd.addColumn("UAGL", "1.2 Glucose",
                ReportUtils.map(moh706IndicatorLibrary.getAllUrineAnalysisGlucoseTestsPositives(), "startDate=${startDate},endDate=${endDate}"), "");

        //#MOH706.UAKET#	Urine Analysis Ketones
        cohortDsd.addColumn("UAKET", "1.3 Ketones",
                ReportUtils.map(moh706IndicatorLibrary.getAllUrineAnalysisKetonesTestsPositives(), "startDate=${startDate},endDate=${endDate}"), "");

        cohortDsd.addColumn("UAPTN", "1.4 Proteins",
                ReportUtils.map(moh706IndicatorLibrary.getAllUrineAnalysisProteinsTestsPositives(),
                        indParam), "");

       cohortDsd.addColumn("UAPC", "1.6 Pus Cells (<5/hpf)", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(1000538, Arrays.asList(1000537, 164371)), indParam), "");

         cohortDsd.addColumn("UASH", "1.7 S. haematobium", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(1000540, Arrays.asList(1000537, 164371)), indParam), "");
        cohortDsd.addColumn("UATV", "1.8 T. vaginatis", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(163689, Arrays.asList(1499, 1160, 1408)), indParam), "");

        cohortDsd.addColumn("UAYC", "1.9 Urinalysis Yeast Cells", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(163686, Arrays.asList(1363, 1362, 1364)), indParam), "");
        cohortDsd.addColumn("UAB", "1.10 Bacteria", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(165561, Arrays.asList(1363, 1362, 1364)), indParam), "");

        //PARASITOLOGY
        //Malaria Test
        ReportingUtils.addRow(cohortDsd, "BST", "Parastology - Malaria test totals",
                ReportUtils.map(moh706IndicatorLibrary.getAllMalariaTests(), indParam), ReportAddonUtils.getAgeUnderOver5Columns());
        ReportingUtils.addRow(cohortDsd, "BSP", "Parasitology - Malaria test positive",
                ReportUtils.map(moh706IndicatorLibrary.getAllMalariaTestsPositiveCases(), indParam),
                ReportAddonUtils.getAgeUnderOver5Columns());

        //Rapid Malaria Test
        cohortDsd.addColumn("BSRT", "3.3 Malaria Rapid Diagnostic Tests totals",
                ReportUtils.map(moh706IndicatorLibrary.getAllRapidMalariaTests(), indParam), "");

        cohortDsd.addColumn("BSRTP", "3.3 Malaria Rapid Diagnostic Tests positives",
                ReportUtils.map(moh706IndicatorLibrary.getAllRapidMalariaTestsPositiveCases(), indParam), "");

        cohortDsd.addColumn("TSPP", "3.4 Taenia SPP", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(1000453, Arrays.asList(163748, 164371)), indParam), "");

        cohortDsd.addColumn("HNN", "3.5 Hymenolepis nana", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(1000454, Arrays.asList(163748, 164371)), indParam), "");
        cohortDsd.addColumn("HOW", "3.6 Hookworm", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(1000456, Arrays.asList(163748, 164371)), indParam), "");
        cohortDsd.addColumn("RW", "3.7 Round worms", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(1000457, Arrays.asList(163748, 164371)), indParam), "");
        cohortDsd.addColumn("SM", "3.8 S. mansoni", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(1000452, Arrays.asList(163748, 164371)), indParam), "");
        cohortDsd.addColumn("TT", "3.9 Trichuris trichura", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(1000458, Arrays.asList(163748, 164371)), indParam), "");
        cohortDsd.addColumn("AMB", "3.10 Amoeba",
						ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(1000458, Arrays.asList(163748,164371)), indParam),
						"");

        //Serology
        cohortDsd.addColumn("VDRLT", "7.1 VDRL Totals",
                ReportUtils.map(moh706IndicatorLibrary.getPatientsWithObsIndicator(299), indParam), "");
        cohortDsd.addColumn("VDRLP", "7.1 VDRL Positive",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(299, Arrays.asList(1228)), indParam), "");
        cohortDsd.addColumn("TPHAT", "7.2 TPHA Totals",
                ReportUtils.map(moh706IndicatorLibrary.getPatientsWithObsIndicator(1031), indParam), "");
        cohortDsd.addColumn(
                "TPHAP",
                "7.2 TPHA Positive",
                ReportUtils.map(
                        moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(1031,
                                Arrays.asList(1311, 1312, 1313, 1314, 1315, 1316, 1317, 163622, 163622, 163623, 163624)), indParam), "");
        cohortDsd.addColumn("HIVT", "7.4 HIV Totals",
                ReportUtils.map(moh706IndicatorLibrary.getPatientsWithObsIndicator(1169), indParam), "");
        cohortDsd.addColumn("HIVP", "7.4 HIV Positive",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(1169, Arrays.asList(703)), indParam), "");
        cohortDsd.addColumn("BRUT", "7.5 Brucella Totals",
                ReportUtils.map(moh706IndicatorLibrary.getPatientsWithObsIndicator(305), indParam), "");
        cohortDsd.addColumn("BRUP", "7.5 Brucella Positive",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(305, Arrays.asList(1228)), indParam), "");
        cohortDsd.addColumn("RFT", "7.6 Rheumatoid factor Totals",
                ReportUtils.map(moh706IndicatorLibrary.getPatientsWithObsIndicator(161470), indParam), "");
        cohortDsd.addColumn("RFP", "7.6 Rheumatoid factor Positive",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(161470, Arrays.asList(703)), indParam), "");
        cohortDsd.addColumn("HPT", "7.7 Helicobacter pylori Totals",
                ReportUtils.map(moh706IndicatorLibrary.getPatientsWithObsIndicator(163348), indParam), "");
        cohortDsd.addColumn("HPP", "7.7 Helicobacter pylori Positive",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(163348, Arrays.asList(703)), indParam), "");
        cohortDsd.addColumn("HPBT", "7.9 Hepatits B test Totals", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAlistOfQuestions(Arrays.asList(159430, 1322, 161472)), indParam), "");
        cohortDsd.addColumn(
                "HPBP",
                "7.9 Hepatits B test Positive",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAlistOfQuestionsAndListOfAnswers(
                        Arrays.asList(159430, 1322, 161472), Arrays.asList(703)), indParam), "");
        cohortDsd.addColumn("HPCT", "7.10 Hepatits C test Totals",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAlistOfQuestions(Arrays.asList(161471, 1325)), indParam), "");
        cohortDsd.addColumn(
                "HPCP",
                "7.10 Hepatits C test Positive",
                ReportUtils.map(
                        moh706IndicatorLibrary.getResponsesBasedOnAlistOfQuestionsAndListOfAnswers(Arrays.asList(161471, 1325),
                                Arrays.asList(703)), indParam), "");
        cohortDsd.addColumn("HCGT", "7.11 HCG test Totals",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAlistOfQuestions(Arrays.asList(45)), indParam), "");
        cohortDsd.addColumn("HCGP", "7.11 HCG test Positive", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAlistOfQuestionsAndListOfAnswers(Arrays.asList(45), Arrays.asList(703)),
                indParam), "");

        //Blood chemistry

        //4.Haematology
        cohortDsd.addColumn("FBCT", "4.1 Haematology tests Full blood count total",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(21), indParam), "");
        cohortDsd.addColumn("FBC5", "4.1 Haematology tests Full blood count <5 g/dl",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(21, 0, 5), indParam), "");
        cohortDsd.addColumn("FBC510", "4.1 Haematology tests Full blood count <5 g/dl",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(21, 5, 10), indParam), "");
        cohortDsd.addColumn("CD4T", "4.3 Haematology tests CD4 count total",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(5497), indParam), "");
        cohortDsd.addColumn("CD4T500", "4.3 Haematology tests CD4 count < 500",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(5497, 0, 500), indParam),
                "");

        //Other Haematology tests
        cohortDsd.addColumn("STT", "4.4 Other Haematology Tests Sickling test Totals",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAlistOfQuestions(Arrays.asList(160225)), indParam), "");
        cohortDsd.addColumn("STP", "4.4 Other Haematology Tests Sickling test Totals", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAlistOfQuestionsAndListOfAnswers(Arrays.asList(45), Arrays.asList(703)),
                indParam), "");
        cohortDsd.addColumn("PBFT", "4.5 Other Haematology Tests Peripherial blood films Totals",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAlistOfQuestions(Arrays.asList(1000071)), indParam), "");
        cohortDsd.addColumn("BMAT", "4.6 BMA Totals",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAlistOfQuestions(Arrays.asList(163420)), indParam), "");

        cohortDsd.addColumn("COAG", "4.7 Other Haematology Tests Coagulation",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAlistOfQuestions(Arrays.asList(163666)), indParam), "");

        cohortDsd.addColumn("RCT", "4.8 Other Haematology Tests Reticulocyte Count total",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(166395), indParam), "");
        cohortDsd.addColumn("BGT", "4.10 Blood Grouping Total Blood Groups total",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(300), indParam), "");
        cohortDsd.addColumn("BGT", "4.11 Blood Grouping Blood Units Grouped total",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(160232), indParam), "");
        //Blood Screening at facility
        cohortDsd.addColumn("BSHIVP", "4.18 Blood Screening at facility HIV Positive",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAnswerIndicator(1169, Arrays.asList(703)), indParam), "");

        cohortDsd.addColumn(
                "BSHPBP",
                "4.19 Blood Screening at facility Hepatiti B Positive",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnAlistOfQuestionsAndListOfAnswers(
                        Arrays.asList(159430, 1322, 161472), Arrays.asList(703)), indParam), "");

        cohortDsd.addColumn(
                "BSHPCP",
                "4.20 Blood Screening at facility Hepatitis C Positive",
                ReportUtils.map(
                        moh706IndicatorLibrary.getResponsesBasedOnAlistOfQuestionsAndListOfAnswers(Arrays.asList(161471, 1325),
                                Arrays.asList(703)), indParam), "");
        cohortDsd.addColumn("BSSP", "4.21 Blood Screening at facility Syphilis Positive", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnAlistOfQuestionsAndListOfAnswers(Arrays.asList(163626, 299, 1031),
                        Arrays.asList(703, 1228, 1311, 1312, 1313, 1314, 1315, 1316, 1317, 163621, 163622, 163623, 163624)),
                indParam), "");

        //2. BLOOD CHEMISTRY

        cohortDsd.addColumn("BCBST", "2.1 BLOOD CHEMISTRY - Blood Sugar Test - Blood Sugar total",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(887), indParam), "");
        cohortDsd.addColumn("BCBSL", "2.1 BLOOD CHEMISTRY - Blood Sugar Test - Low Blood Sugar total",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(887, 0, 4), indParam), "");
        cohortDsd.addColumn("BCBSH", "2.1 BLOOD CHEMISTRY - Blood Sugar Test - Low Blood Sugar total",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(887, 6, 20), indParam), "");
        cohortDsd.addColumn("OGTT", "2.2 BLOOD CHEMISTRY - Blood Sugar Test - OGTT total",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(163594), indParam), "");
        cohortDsd.addColumn("OGTTL", "2.2 BLOOD CHEMISTRY - Blood Sugar Test - Low OGTT total",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(163594, 20, 70), indParam),
                "");
        cohortDsd.addColumn("OGTTH", "2.2 BLOOD CHEMISTRY - Blood Sugar Test - High OGTT total", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(163594, 120, 450), indParam), "");
        cohortDsd.addColumn("BCLC", "2.4 BLOOD CHEMISTRY - Renal Function Test - Low Creatine",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(790, 0, 0.74), indParam),
                "");
        cohortDsd.addColumn("BCHC", "2.4 BLOOD CHEMISTRY -  Renal Function Test - High Creatine",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(790, 1.35, 2.0), indParam),
                "");
        cohortDsd.addColumn("BCLU", "2.5 BLOOD CHEMISTRY -  Renal Function Test - Low Urea",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(163699, 10, 20), indParam),
                "");
        cohortDsd.addColumn(
                "BCHU",
                "2.5 BLOOD CHEMISTRY -  Renal Function Test - High Urea",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(163699, 41, 200), indParam),
                "");
        cohortDsd.addColumn("BCLS", "2.6 BLOOD CHEMISTRY -  Renal Function Test - Low Sodium",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(159656, 0, 2), indParam),
                "");
        cohortDsd.addColumn("BCHS", "2.6 BLOOD CHEMISTRY - Renal Function Test - High Sodium",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(159656, 3, 100), indParam),
                "");

        cohortDsd.addColumn("BCLP", "2.7 BLOOD CHEMISTRY - Renal Function Test -  Low Potassium",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(159659, 0, 2), indParam),
                "");
        cohortDsd.addColumn("BCHP", "2.7 BLOOD CHEMISTRY - Renal Function Test -  High Potassium",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(159659, 3, 100), indParam),
                "");

        cohortDsd.addColumn("BCLCH", "2.8 BLOOD CHEMISTRY - Renal Function Test -  Low Chlorides",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(159657, 0, 2), indParam),
                "");
        cohortDsd.addColumn("BCHCH", "2.8 BLOOD CHEMISTRY - Renal Function Test -  High Chlorides",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(159657, 3, 100), indParam),
                "");

        cohortDsd.addColumn("LFTLTB", "2.10 BLOOD CHEMISTRY - Liver Function Test -  Low Total Bilirubin",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(655, 0, 2), indParam), "");
        cohortDsd.addColumn("LFTLTB", "2.10 BLOOD CHEMISTRY - Liver Function Test -  High Total Bilirubin",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(655, 3, 100), indParam), "");
        cohortDsd.addColumn("LFTLASAT", "2.11 BLOOD CHEMISTRY - Liver Function Test -  Low ASAT (SGOT)",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(653, 0, 24), indParam), "");
        cohortDsd.addColumn("LFTHASAT", "2.11 BLOOD CHEMISTRY - Liver Function Test -  High ASAT (SGOT)",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(653, 24, 50), indParam), "");
        cohortDsd.addColumn("LFTLSGPT", "2.12 BLOOD CHEMISTRY - Liver Function Test -  Low ALAT (SGPT)",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(654, 0, 1), indParam), "");
        cohortDsd.addColumn("LFTHSGPT", "2.12 BLOOD CHEMISTRY - Liver Function Test -  High ALAT (SGPT)",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(654, 2, 40), indParam), "");
        cohortDsd.addColumn("LFTLSP", "2.13 BLOOD CHEMISTRY - Liver Function Test -  Low Serum Protein",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(717, 0, 1), indParam), "");
        cohortDsd.addColumn("LFTHSP", "2.13 BLOOD CHEMISTRY - Liver Function Test -  High Serum Protein",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(717, 2, 40), indParam), "");
        cohortDsd.addColumn("LFTLA", "2.14 BLOOD CHEMISTRY - Liver Function Test -  Low Albumin",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(848, 0, 2.7), indParam), "");
        cohortDsd.addColumn("LFTHA", "2.14 BLOOD CHEMISTRY - Liver Function Test -  High Albumin",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(848, 2.9, 6.0), indParam),
                "");
        cohortDsd.addColumn("LFTLAP", "2.15 BLOOD CHEMISTRY - Liver Function Test -  Low Alkaline Phosphatase",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(785, 0, 2.7), indParam), "");
        cohortDsd.addColumn("LFTHAP", "2.15 BLOOD CHEMISTRY - Liver Function Test -  High Alkaline Phosphatase",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(785, 300, 541), indParam),
                "");
        cohortDsd.addColumn("LPTTC", "2.17 BLOOD CHEMISTRY - Lipid Profile Test -  Total Cholestrol ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(1006), indParam), "");
        cohortDsd.addColumn("LPTLC", "2.17 BLOOD CHEMISTRY - Lipid Profile Test -  Low Cholestrol ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(1006, 0, 1), indParam), "");
        cohortDsd.addColumn("LFTHC", "2.17 BLOOD CHEMISTRY - Lipid Profile Test -  High Cholestrol",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(1006, 5.18, 10), indParam),
                "");

        cohortDsd.addColumn("LPTT", "2.18 BLOOD CHEMISTRY - Lipid Profile Test -  Total Triglycerides ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(166039), indParam), "");
        cohortDsd.addColumn("LPTLT", "2.18 BLOOD CHEMISTRY - Lipid Profile Test -  Low Triglycerides ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(166039, 0, 30), indParam),
                "");
        cohortDsd.addColumn("LFTHT", "2.18 BLOOD CHEMISTRY - Lipid Profile Test -  High Triglycerides", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(166039, 160, 200), indParam), "");

        cohortDsd.addColumn("LPTLDL", "2.19 BLOOD CHEMISTRY - Lipid Profile Test -  Total LDL ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(166045), indParam), "");
        cohortDsd.addColumn("LPTLLDL", "2.19 BLOOD CHEMISTRY - Lipid Profile Test -  Low LDL ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(166045, 0, 30), indParam),
                "");
        cohortDsd.addColumn("LFTHLDL", "2.19 BLOOD CHEMISTRY - Lipid Profile Test -  High LDL", ReportUtils.map(
                moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(166045, 150, 200), indParam), "");

        cohortDsd.addColumn("HTTT3", "2.20 BLOOD CHEMISTRY - Hormonal Test -  Total T3 ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(161503), indParam), "");
        cohortDsd.addColumn("HTLT3", "2.20 BLOOD CHEMISTRY - Hormonal Test -  Low T3 ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(161503, 0, 0.1), indParam),
                "");
        cohortDsd.addColumn(
                "HTHT3",
                "2.20 BLOOD CHEMISTRY - Hormonal Test -  High T3",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(161503, 0.6, 10), indParam),
                "");

        cohortDsd.addColumn("HTTT4", "2.21 BLOOD CHEMISTRY - Hormonal Test -  Total T4 ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(161504), indParam), "");
        cohortDsd.addColumn("HTLT4", "2.21 BLOOD CHEMISTRY - Hormonal Test -  Low T4 ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(161504, 0, 0.1), indParam),
                "");
        cohortDsd.addColumn(
                "HTHT4",
                "2.21 BLOOD CHEMISTRY - Hormonal Test -  High T4",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(161504, 0.6, 10), indParam),
                "");

        cohortDsd.addColumn("HTTTSH", "2.22 BLOOD CHEMISTRY - Hormonal Test -  Total TSH ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(161505), indParam), "");
        cohortDsd.addColumn("HTLTSH", "2.22 BLOOD CHEMISTRY - Hormonal Test -  Low TSH ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(161505, 0, 0.1), indParam),
                "");
        cohortDsd.addColumn(
                "HTHTSH",
                "2.22 BLOOD CHEMISTRY - Hormonal Test -  High TSH",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(161505, 0.6, 10), indParam),
                "");
        cohortDsd.addColumn("HTTPSA", "2.23 BLOOD CHEMISTRY - Hormonal Test -  Total PSA ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(160913), indParam), "");
        cohortDsd.addColumn("HTLPSA", "2.23 BLOOD CHEMISTRY - Hormonal Test -  Low PSA ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(160913, 0, 0.1), indParam),
                "");
        cohortDsd.addColumn(
                "HTHPSA",
                "2.23 BLOOD CHEMISTRY - Hormonal Test -  High PSA",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(160913, 0.6, 10), indParam),
                "");
        cohortDsd.addColumn("HTTCEA", "2.24 BLOOD CHEMISTRY - Tumor Makers -  Total CEA ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(160915), indParam), "");
        cohortDsd.addColumn("HTLCEA", "2.24 BLOOD CHEMISTRY - Tumor Makers -  Low CEA ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(160915, 0, 0.1), indParam),
                "");
        cohortDsd.addColumn(
                "HTHCEA",
                "2.24 BLOOD CHEMISTRY - Tumor Makers -  High CEA",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(160915, 0.6, 10), indParam),
                "");

        cohortDsd.addColumn("CSFTP", "2.25 BLOOD CHEMISTRY - CSF Chemistry -  Total Proteins ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(159646), indParam), "");
        cohortDsd.addColumn("CSFLP", "2.25 BLOOD CHEMISTRY - CSF Chemistry -  Low Proteins",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(159646, 0, 0.1), indParam),
                "");
        cohortDsd.addColumn(
                "CSFHP",
                "2.25 BLOOD CHEMISTRY - CSF Chemistry -  High Proteins",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(159646, 0.6, 10), indParam),
                "");
        cohortDsd.addColumn("CSFTC", "2.26 BLOOD CHEMISTRY - CSF Chemistry -  Total Glucose ",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestion(159647), indParam), "");
        cohortDsd.addColumn("CSFLC", "2.26 BLOOD CHEMISTRY - CSF Chemistry -  Low Glucose",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(159647, 0, 0.1), indParam),
                "");
        cohortDsd.addColumn(
                "CSFHC",
                "2.26 BLOOD CHEMISTRY - CSF Chemistry -  High Glucose",
                ReportUtils.map(moh706IndicatorLibrary.getResponsesBasedOnValueNumericQuestionBetweenLimits(159647, 0.6, 10), indParam),
                "");

        return cohortDsd;
    }

}

