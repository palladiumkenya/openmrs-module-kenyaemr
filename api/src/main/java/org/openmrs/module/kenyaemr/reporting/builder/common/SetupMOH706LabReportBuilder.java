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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;

@Component
@Builds({ "kenyaemr.ehrReports.706.report" })
public class SetupMOH706LabReportBuilder extends AbstractReportBuilder {


    private final Moh706IndicatorLibrary moh706IndicatorLibrary;
	//Lab test  - Question concept ids
	static final int  URINE_ANALYSIS_GLUCOSE = 159734;
	static final int  URINE_ANALYSIS_KETONES = 161442;
	static final int  URINE_ANALYSIS_PROTEINS = 1875;
	static final int  URINE_ANALYSIS_PUS_CELLS = 2001231;
	static final int  URINE_ANALYSIS_T_HAEMATOBIUM = 1000540;
	static final int  URINE_ANALYSIS_T_VAGINATIS  = 163648;
	static final int  URINE_ANALYSIS_YEAST_CELLS  = 163686;
	static final int  URINE_ANALYSIS_BACTERIA = 165561;
	static final int  PARASITOLOGY_MALARIA_BS = 2017917;
	static final int  PARASITOLOGY_MALARIA_RAPID = 1643;
	static final int  PARASITOLOGY_TAENIA_SPP = 1000453;
	static final int  PARASITOLOGY_HNANA = 1000454;
	static final int  PARASITOLOGY_HOOKWORM = 1000456;
	static final int  PARASITOLOGY_ROUNDWORM = 1000457;
	static final int  PARASITOLOGY_S_MASONII = 1000452;
	static final int  PARASITOLOGY_TRICHURIS= 1000458;
	static final int  HAEMATOLOGY_FULL_BLOOD_COUNT= 1019;
	static final int  HAEMATOLOGY_HAEMOGLOBIN= 21;
	static final int  HAEMATOLOGY_CD4= 5497;
	static final int  HAEMATOLOGY_SICKLE_CELL= 160225;
	static final int  HAEMATOLOGY_PERIPHERAL_BLOOD_FILM= 1000071;
	static final int  HAEMATOLOGY_BONE_MARROW_ASPIRATIONS= 163420;
	static final int  HAEMATOLOGY_COAGULATION= 163666;
	static final int  HAEMATOLOGY_RETICULOCYTE_COUNT= 166395;
	static final int  HAEMATOLOGY_ERYTHROCYTE_SEDIMENTATION_RATE= 855;
	static final int  BLOOD_GROUPING= 300;
	static final int  RHESUS_BLOOD_GROUP_UNITS= 160232;
	static final int  BLOOD_SCREENING_HIV= 163722;
	static final int  BLOOD_SCREENING_HEPATITIS_B_SURFACE_ANTIGEN = 159430;
	static final int  BLOOD_SCREENING_HEPATITIS_B_QUALITATIVE= 1322;
	static final int  BLOOD_SCREENING_HEPATITIS_B_SPOT = 161472;
	static final int  BLOOD_SCREENING_HEPATITIS_C_SPOT = 161471;
	static final int  BLOOD_SCREENING_HEPATITIS_C_QUALITATIVE = 1325;
	static final int  BLOOD_SCREENING_HEPATITIS_C_ANTIGEN = 167810;
	static final int  BLOOD_SCREENING_SYPHILIS= 299;
	static final int  BLOOD_CHEMISTRY_BLOOD_SUGAR= 1000443;
	static final int  BLOOD_CHEMISTRY_GLUCOSE_TOLERANCE= 163594;
	static final int  BLOOD_CHEMISTRY_CREATININE= 790;
	static final int  BLOOD_CHEMISTRY_UREA= 163699;
	static final int  BLOOD_CHEMISTRY_SODIUM= 159656;
	static final int  BLOOD_CHEMISTRY_POTASSIUM= 159659;
	static final int  BLOOD_CHEMISTRY_CHLORIDES= 159657;
	static final int  LFT_TOTAL_BILIRUBIN = 655;
	static final int  LFT_SGOT = 653;
	static final int  LFT_SGPT = 654;
	static final int  LFT_SERUM_PROTEIN = 717;
	static final int  LFT_SERUM_ALBUMIN = 848;
	static final int  LFT_ALKALINE_PHOSPHATASE = 785;
	static final int  LP_TOTAL_CHOLESTEROL = 1006;
	static final int  LP_TRIGLYCERIDES = 1009;
	static final int  LP_LDL= 1008;
	static final int  HORMONAL_TEST_T3= 161503;
	static final int  HORMONAL_TEST_T4= 161504;
	static final int  HORMONAL_TEST_TSH= 161505;
	static final int  HORMONAL_TEST_PSA= 160913;
	static final int  TUMOR_MARKERS_CEA= 160915;
	static final int  TUMOR_MARKERS_C15_3= 160920;
	static final int  CFS_CHEMISTRY_PROTEINS= 159646;
	static final int  CFS_CHEMISTRY_GLUCOSE= 159647;
	static final int  SEROLOGY_TPHA= 1031;
	static final int  BRUCELLA= 305;
	static final int  RHEUMATOID_FACTOR= 161470;
	static final int  HELICOBACTER_PYLORI= 163348;
	static final int  HEPATITIS_A= 2001225;
	static final int  URINE_PREGNANCY= 45;
		
	//Answers
	static final int  NIL = 2001195;
	static final int  TRACE = 1874;
	static final int  ONE_PLUS = 1362;
	static final int  TWO_PLUS = 1363;
	static final int  THREE_PLUS = 1364;
	static final int  FOUR_PLUS = 1365;
	static final int  ONE_FIVE_HPF  = 2001230;
	static final int  SIX_TEN_HPF = 2001232;
	static final int  TEN_TWENTY_HPF = 2001233;
	static final int  ABOVE_TWENTY_HPF = 2001234;
	static final int  SEEN = 1000537;
	static final int  RARELY = 159416;
	static final int  POSITIVE = 703;
	static final int  PRESENT = 163748;
	static final int  REACTIVE = 1228;	
	static final int  LESS_ONE_2 = 1311;
	static final int  ONE_2 = 1312;
	static final int  ONE_4 = 1313;
	static final int  ONE_8 = 1314;
	static final int  ONE_16 = 1315;
	static final int  ONE_32 = 1316;
	static final int  GREATER_ONE_32 = 1317;
	static final int  ONE_64 = 163621;
	static final int  ONE_128 = 163622;
	static final int  ONE_256 = 163623;
	static final int  GREATER_ONE_572 = 163624;	


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
		
     //URINALYSIS  	
		cohortDsd.addColumn("UAGLT", "1.2 Glucose Total",ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(URINE_ANALYSIS_GLUCOSE), indParam), "");
	    cohortDsd.addColumn("UAGLP", "1.2 Glucose Positive",ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(URINE_ANALYSIS_GLUCOSE, Arrays.asList(ONE_PLUS,TWO_PLUS,THREE_PLUS,FOUR_PLUS)), indParam), "");
        cohortDsd.addColumn("UAKETT", "1.3 Ketones Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(URINE_ANALYSIS_KETONES), indParam), "");
        cohortDsd.addColumn("UAKETP", "1.3 Ketones Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(URINE_ANALYSIS_KETONES, Arrays.asList(ONE_PLUS,TWO_PLUS,THREE_PLUS,FOUR_PLUS)), indParam), "");
		cohortDsd.addColumn("UAPT", "1.3 Proteins Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(URINE_ANALYSIS_PROTEINS), indParam), "");
        cohortDsd.addColumn("UAPP", "1.4 Proteins Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(URINE_ANALYSIS_PROTEINS, Arrays.asList(TRACE,ONE_PLUS,TWO_PLUS,THREE_PLUS)), indParam), "");
        cohortDsd.addColumn("UAPCT", "1.6 Pus Cells (<5/hpf) Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(URINE_ANALYSIS_PUS_CELLS), indParam), "");
        cohortDsd.addColumn("UAPCP", "1.6 Pus Cells (<5/hpf) Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(URINE_ANALYSIS_PUS_CELLS, Arrays.asList(ONE_FIVE_HPF,SIX_TEN_HPF,TEN_TWENTY_HPF,ABOVE_TWENTY_HPF)), indParam), "");
		cohortDsd.addColumn("UAHT", "1.7 S.haematobium Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(URINE_ANALYSIS_T_HAEMATOBIUM), indParam), "");
		cohortDsd.addColumn("UAHP", "1.7 S.haematobium Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(URINE_ANALYSIS_T_HAEMATOBIUM, Arrays.asList(SEEN)), indParam), "");
        cohortDsd.addColumn("UATVT", "1.8 T. vaginatis Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(URINE_ANALYSIS_T_VAGINATIS), indParam), "");
        cohortDsd.addColumn("UATVP", "1.8 T. vaginatis Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(URINE_ANALYSIS_T_VAGINATIS, Arrays.asList(SEEN)), indParam), "");
        cohortDsd.addColumn("UAYCT", "1.9 Urinalysis Yeast Cells Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(URINE_ANALYSIS_YEAST_CELLS), indParam), "");
		cohortDsd.addColumn("UAYCP", "1.9 Urinalysis Yeast Cells Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(URINE_ANALYSIS_YEAST_CELLS, Arrays.asList(RARELY,ONE_PLUS,TWO_PLUS,THREE_PLUS)), indParam), "");
        cohortDsd.addColumn("UABT", "1.10 Bacteria Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(URINE_ANALYSIS_BACTERIA), indParam), "");
        cohortDsd.addColumn("UABP", "1.10 Bacteria Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(URINE_ANALYSIS_BACTERIA, Arrays.asList(SEEN)), indParam), "");

        //PARASITOLOGY
		//TODO: BS for malaria is text based changes to coded
        ReportingUtils.addRow(cohortDsd, "BST", "Parastology - Malaria test totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(PARASITOLOGY_MALARIA_BS), indParam), ReportAddonUtils.getAgeUnderOver5Columns());
        ReportingUtils.addRow(cohortDsd, "BSP", "Parasitology - Malaria test positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(PARASITOLOGY_MALARIA_BS, Arrays.asList(POSITIVE))), ReportAddonUtils.getAgeUnderOver5Columns());
        
		cohortDsd.addColumn("BSRT", "3.3 Malaria Rapid Diagnostic Tests totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(PARASITOLOGY_MALARIA_RAPID), indParam), "");
        cohortDsd.addColumn("BSRTP", "3.3 Malaria Rapid Diagnostic Tests positives",ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(PARASITOLOGY_MALARIA_RAPID, Arrays.asList(POSITIVE)), indParam), "");
        cohortDsd.addColumn("TSPPT", "3.4 Taenia SPP Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(PARASITOLOGY_TAENIA_SPP), indParam), "");
        cohortDsd.addColumn("TSPPP", "3.4 Taenia SPP Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(PARASITOLOGY_TAENIA_SPP, Arrays.asList(PRESENT)), indParam), "");
        cohortDsd.addColumn("HNNT", "3.5 Hymenolepis nana Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(PARASITOLOGY_HNANA), indParam), "");
        cohortDsd.addColumn("HNNP", "3.5 Hymenolepis nana Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(PARASITOLOGY_HNANA, Arrays.asList(PRESENT)), indParam), "");
      
        cohortDsd.addColumn("HOWT", "3.6 Hookworm Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(PARASITOLOGY_HOOKWORM), indParam), "");
        cohortDsd.addColumn("HOWP", "3.6 Hookworm Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(PARASITOLOGY_HOOKWORM, Arrays.asList(PRESENT)), indParam), "");
		cohortDsd.addColumn("RWT", "3.7 Roundworm Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(PARASITOLOGY_ROUNDWORM), indParam), "");
		cohortDsd.addColumn("RWP", "3.7 Roundworm Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(PARASITOLOGY_ROUNDWORM, Arrays.asList(PRESENT)), indParam), "");
		cohortDsd.addColumn("SMT", "3.8 S. mansoni Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(PARASITOLOGY_S_MASONII), indParam), "");
		cohortDsd.addColumn("SMP", "3.8 S. mansoni Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(PARASITOLOGY_S_MASONII, Arrays.asList(PRESENT)), indParam), "");
		cohortDsd.addColumn("TTT", "3.9 Trichuris trichura Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(PARASITOLOGY_TRICHURIS), indParam), "");
		cohortDsd.addColumn("TTP", "3.9 Trichuris trichura Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(PARASITOLOGY_TRICHURIS, Arrays.asList(PRESENT)), indParam), "");
       //TODO: Need concept for Amoeba
		cohortDsd.addColumn("AMBT", "3.9 Amoeba Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(PARASITOLOGY_TRICHURIS), indParam), "");
		cohortDsd.addColumn("AMBP", "3.9 Amoeba Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(PARASITOLOGY_TRICHURIS, Arrays.asList(PRESENT)), indParam), "");

        //Serology
		cohortDsd.addColumn("VDRLT", "7.1 VDRL Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(BLOOD_SCREENING_SYPHILIS), indParam), "");
		cohortDsd.addColumn("VDRLP", "7.1 VDRL Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(BLOOD_SCREENING_SYPHILIS, Arrays.asList(REACTIVE)), indParam), "");

		cohortDsd.addColumn("TPHAT", "7.2 TPHA Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(SEROLOGY_TPHA), indParam), "");
		cohortDsd.addColumn("TPHAP", "7.2 TPHA Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(SEROLOGY_TPHA, Arrays.asList(REACTIVE,LESS_ONE_2,ONE_2,ONE_4,ONE_8,ONE_16,ONE_32,ONE_64,ONE_128,ONE_256,GREATER_ONE_32,GREATER_ONE_572)), indParam), "");
		//TODO: Need concept for Asot
		cohortDsd.addColumn("ASOT", "7.3 ASOT Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(SEROLOGY_TPHA), indParam), "");
		cohortDsd.addColumn("ASOT", "7.3 ASOT Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(SEROLOGY_TPHA, Arrays.asList(LESS_ONE_2,ONE_2,ONE_4,ONE_8,ONE_16,ONE_32,ONE_64,ONE_128,ONE_256,GREATER_ONE_32,GREATER_ONE_572)), indParam), "");

		cohortDsd.addColumn("HIVT", "7.4 HIV Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(BLOOD_SCREENING_HIV), indParam), "");
		cohortDsd.addColumn("HIVP", "7.4 HIV Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(BLOOD_SCREENING_HIV, Arrays.asList(POSITIVE)), indParam), "");

		cohortDsd.addColumn("BRUT", "7.5 Brucella Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(BRUCELLA), indParam), "");
		cohortDsd.addColumn("BRUP", "7.5 Brucella Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(BRUCELLA, Arrays.asList(REACTIVE)), indParam), "");
		
		cohortDsd.addColumn("RFT", "7.6 Rheumatoid factor Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(RHEUMATOID_FACTOR), indParam), "");
		cohortDsd.addColumn("RFP", "7.6 Rheumatoid factor Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(RHEUMATOID_FACTOR, Arrays.asList(POSITIVE)), indParam), "");

		cohortDsd.addColumn("HPT", "7.7 Helicobacter pylori Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(HELICOBACTER_PYLORI), indParam), "");
		cohortDsd.addColumn("HPP", "7.7 Helicobacter pylori Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(HELICOBACTER_PYLORI, Arrays.asList(POSITIVE)), indParam), "");

		cohortDsd.addColumn("HPAT", "7.8 Hepatitis A Test Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(HEPATITIS_A), indParam), "");
		cohortDsd.addColumn("HPAP", "7.8 Hepatitis A Test Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(HEPATITIS_A, Arrays.asList(REACTIVE)), indParam), "");


		cohortDsd.addColumn("HPBT", "7.9 Hepatitis B Test Totals", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnAlistOfQuestions(Arrays.asList(BLOOD_SCREENING_HEPATITIS_B_SURFACE_ANTIGEN, BLOOD_SCREENING_HEPATITIS_B_QUALITATIVE, BLOOD_SCREENING_HEPATITIS_B_SPOT)), indParam), "");
		cohortDsd.addColumn("HPBP", "7.9 Blood Screening at facility Hepatitis B Positive", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnAlistOfQuestionsAndListOfAnswers(Arrays.asList(BLOOD_SCREENING_HEPATITIS_B_SURFACE_ANTIGEN, BLOOD_SCREENING_HEPATITIS_B_QUALITATIVE, BLOOD_SCREENING_HEPATITIS_B_SPOT), Arrays.asList(POSITIVE)), indParam), "");

		cohortDsd.addColumn("HPCT", "7.10 Hepatitis C Test Totals", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnAlistOfQuestions(Arrays.asList(BLOOD_SCREENING_HEPATITIS_C_SPOT, BLOOD_SCREENING_HEPATITIS_C_QUALITATIVE, BLOOD_SCREENING_HEPATITIS_C_ANTIGEN)), indParam), "");
		cohortDsd.addColumn("HPCP", "7.10 Blood Screening at facility Hepatitis B Positive", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnAlistOfQuestionsAndListOfAnswers(Arrays.asList(BLOOD_SCREENING_HEPATITIS_C_SPOT, BLOOD_SCREENING_HEPATITIS_C_QUALITATIVE, BLOOD_SCREENING_HEPATITIS_C_ANTIGEN), Arrays.asList(POSITIVE,REACTIVE)), indParam), "");

		cohortDsd.addColumn("HCGT", "7.11 HCG test Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(URINE_PREGNANCY), indParam), "");
		cohortDsd.addColumn("HCGP", "7.11 HCG test Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(URINE_PREGNANCY, Arrays.asList(POSITIVE)), indParam), "");

        //4.Haematology
		cohortDsd.addColumn("FBCT", "4.1 Haematology tests: Full blood count total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(HAEMATOLOGY_FULL_BLOOD_COUNT), indParam), "");
        cohortDsd.addColumn("HB5", "4.1 Haematology tests: FBC HB <5 g/dl",ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(HAEMATOLOGY_HAEMOGLOBIN, 0, 5), indParam), "");
        cohortDsd.addColumn("HB510", "4.1 Haematology tests: FBC HB Between 5-10 g/dl",ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(HAEMATOLOGY_HAEMOGLOBIN, 5, 10), indParam), "");
        cohortDsd.addColumn("CD4T", "4.3 Haematology tests CD4 count total", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestion(HAEMATOLOGY_CD4), indParam), "");
        cohortDsd.addColumn("CD4T500", "4.3 Haematology tests CD4 count < 500", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(HAEMATOLOGY_CD4, 0, 500), indParam),"");

        //Other Haematology tests
		cohortDsd.addColumn("STT", "4.4 Other Haematology Tests Sickling test Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(HAEMATOLOGY_SICKLE_CELL), indParam), "");
		cohortDsd.addColumn("STP", "4.4 Other Haematology Tests Sickling test Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(HAEMATOLOGY_SICKLE_CELL, Arrays.asList(POSITIVE)), indParam), "");
		cohortDsd.addColumn("PBFT", "4.5 Other Haematology Tests Peripherial blood films Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(HAEMATOLOGY_PERIPHERAL_BLOOD_FILM), indParam), "");
		cohortDsd.addColumn("BMAT", "4.6 Other Haematology Tests BMA Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(HAEMATOLOGY_BONE_MARROW_ASPIRATIONS), indParam), "");
		cohortDsd.addColumn("COAGT", "4.7 Other Haematology Tests Coagulation Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(HAEMATOLOGY_COAGULATION), indParam), "");
		cohortDsd.addColumn("RCT", "4.8 Other Haematology Tests Reticulocyte Count Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(HAEMATOLOGY_RETICULOCYTE_COUNT), indParam), "");
		cohortDsd.addColumn("ESRT", "4.9 Other Haematology Tests Erythrocyte sedimentation rate Totals", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(HAEMATOLOGY_ERYTHROCYTE_SEDIMENTATION_RATE), indParam), "");
		//TODO:Which is the high ESR need to determine lower and upper limits
		cohortDsd.addColumn("ESRP", "4.9 Other Haematology Tests Erythrocyte sedimentation rate High", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(HAEMATOLOGY_ERYTHROCYTE_SEDIMENTATION_RATE, 100, 500), indParam),"");

       //Blood grouping
		cohortDsd.addColumn("BGT", "4.10 Blood Grouping Blood Groups Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(BLOOD_GROUPING), indParam), "");
		//TODO:How to compute Blood Units Grouped 
		cohortDsd.addColumn("BUGT", "4.11 Blood Grouping Blood Units Grouped Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(RHESUS_BLOOD_GROUP_UNITS), indParam), "");

        //Blood Screening at facility
		cohortDsd.addColumn("BSHIVP", "4.18 Blood Screening at facility HIV Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(BLOOD_SCREENING_HIV, Arrays.asList(POSITIVE)), indParam), "");
		cohortDsd.addColumn("BSHPBP", "4.19 Blood Screening at facility Hepatitis B Positive", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnAlistOfQuestionsAndListOfAnswers(Arrays.asList(BLOOD_SCREENING_HEPATITIS_B_SURFACE_ANTIGEN, BLOOD_SCREENING_HEPATITIS_B_QUALITATIVE, BLOOD_SCREENING_HEPATITIS_B_SPOT), Arrays.asList(POSITIVE)), indParam), "");
		cohortDsd.addColumn("BSHPCP", "4.20 Blood Screening at facility Hepatitis C Positive", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnAlistOfQuestionsAndListOfAnswers(Arrays.asList(BLOOD_SCREENING_HEPATITIS_C_SPOT, BLOOD_SCREENING_HEPATITIS_C_QUALITATIVE, BLOOD_SCREENING_HEPATITIS_C_ANTIGEN), Arrays.asList(POSITIVE,REACTIVE)), indParam), "");
		cohortDsd.addColumn("BSSP", "4.21 Blood Screening at facility Syphilis Positive", ReportUtils.map(moh706IndicatorLibrary.getTotalCodedLabsByConceptAndPositiveAnswer(BLOOD_SCREENING_SYPHILIS, Arrays.asList(REACTIVE)), indParam), "");

        //2. BLOOD CHEMISTRY
		//TODO: Check concept 887 for serum glucose is same as 1000443 for Random blood sugar used 
		cohortDsd.addColumn("BCBST", "2.1 BLOOD CHEMISTRY - Blood Sugar Test - Blood Sugar Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(BLOOD_CHEMISTRY_BLOOD_SUGAR), indParam), "");
		cohortDsd.addColumn("BCBSL", "2.1 BLOOD CHEMISTRY - Blood Sugar Test - Low Blood Sugar Total", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(BLOOD_CHEMISTRY_BLOOD_SUGAR, 0, 4), indParam), "");
		cohortDsd.addColumn("BCBSH", "2.1 BLOOD CHEMISTRY - Blood Sugar Test - High Blood Sugar Total", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(BLOOD_CHEMISTRY_BLOOD_SUGAR, 6, 30), indParam), "");

		cohortDsd.addColumn("OGTT", "2.2 BLOOD CHEMISTRY - Blood Sugar Test - Glucose Tolerance Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(BLOOD_CHEMISTRY_GLUCOSE_TOLERANCE), indParam), "");
		cohortDsd.addColumn("OGTTL", "2.2 BLOOD CHEMISTRY - Blood Sugar Test - Low Glucose Tolerance Total", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(BLOOD_CHEMISTRY_GLUCOSE_TOLERANCE, 0, 5.3), indParam), "");
		cohortDsd.addColumn("OGTTH", "2.2 BLOOD CHEMISTRY - Blood Sugar Test - High Glucose Tolerance Total", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(BLOOD_CHEMISTRY_GLUCOSE_TOLERANCE, 7, 30), indParam), "");

		cohortDsd.addColumn("BCCT", "2.4 BLOOD CHEMISTRY - Renal Function Test - Creatine Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(BLOOD_CHEMISTRY_CREATININE), indParam), "");
		cohortDsd.addColumn("BCLC", "2.4 BLOOD CHEMISTRY - Renal Function Test - Low Creatine", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(BLOOD_CHEMISTRY_CREATININE, 0, 0.7), indParam), "");
		cohortDsd.addColumn("BCHC", "2.4 BLOOD CHEMISTRY - Renal Function Test - High Creatine", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(BLOOD_CHEMISTRY_CREATININE, 1.2, 30), indParam), "");


		cohortDsd.addColumn("BCUT", "2.5 BLOOD CHEMISTRY -  Renal Function Test - Urea Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(BLOOD_CHEMISTRY_UREA), indParam), "");
		cohortDsd.addColumn("BCLU", "2.5 BLOOD CHEMISTRY -  Renal Function Test - Low Urea", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(BLOOD_CHEMISTRY_UREA, 0, 6), indParam), "");
		cohortDsd.addColumn("BCHU", "2.5 BLOOD CHEMISTRY -  Renal Function Test - High Urea", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(BLOOD_CHEMISTRY_UREA, 24, 40), indParam), "");

		cohortDsd.addColumn("BCST", "2.6 BLOOD CHEMISTRY -  Renal Function Test - Sodium Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(BLOOD_CHEMISTRY_SODIUM), indParam), "");
		cohortDsd.addColumn("BCLS", "2.6 BLOOD CHEMISTRY -  Renal Function Test - Low Sodium", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(BLOOD_CHEMISTRY_SODIUM, 0, 135), indParam), "");
		cohortDsd.addColumn("BCHS", "2.6 BLOOD CHEMISTRY -  Renal Function Test - High Sodium", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(BLOOD_CHEMISTRY_SODIUM, 155, 400), indParam), "");


		cohortDsd.addColumn("BCPT", "2.7 BLOOD CHEMISTRY -  Renal Function Test - Potassium Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(BLOOD_CHEMISTRY_POTASSIUM), indParam), "");
		cohortDsd.addColumn("BCLP", "2.7 BLOOD CHEMISTRY -  Renal Function Test - Low Potassium", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(BLOOD_CHEMISTRY_POTASSIUM, 0, 3.5), indParam), "");
		cohortDsd.addColumn("BCHP", "2.7 BLOOD CHEMISTRY -  Renal Function Test - High Potassium", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(BLOOD_CHEMISTRY_POTASSIUM, 5.5, 30), indParam), "");

		cohortDsd.addColumn("BCCHT", "2.8 BLOOD CHEMISTRY -  Renal Function Test - Chlorides Total", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(BLOOD_CHEMISTRY_CHLORIDES), indParam), "");
		cohortDsd.addColumn("BCLCH", "2.8 BLOOD CHEMISTRY -  Renal Function Test - Low Chlorides", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(BLOOD_CHEMISTRY_CHLORIDES, 0, 97), indParam), "");
		cohortDsd.addColumn("BCHCH", "2.8 BLOOD CHEMISTRY -  Renal Function Test - High Chlorides", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(BLOOD_CHEMISTRY_CHLORIDES, 111, 200), indParam), "");

		cohortDsd.addColumn("LFTTBT", "2.10 BLOOD CHEMISTRY -  Liver Function Test - Total Bilirubin", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(LFT_TOTAL_BILIRUBIN), indParam), "");
		cohortDsd.addColumn("LFTLTB", "2.10 BLOOD CHEMISTRY -  Liver Function Test -  Low Total Bilirubin", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LFT_TOTAL_BILIRUBIN, 0, 1.71), indParam), "");
		cohortDsd.addColumn("LFTHTB", "2.10 BLOOD CHEMISTRY -  Liver Function Test -  High Total Bilirubin", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LFT_TOTAL_BILIRUBIN, 20.5, 40), indParam), "");

		cohortDsd.addColumn("LFTASATT", "2.11 BLOOD CHEMISTRY -  Liver Function Test - Total ASAT (SGOT)", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(LFT_SGOT), indParam), "");
		cohortDsd.addColumn("LFTLASAT", "2.11 BLOOD CHEMISTRY -  Liver Function Test - Low ASAT (SGOT)", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LFT_SGOT, 0, 0), indParam), "");
		cohortDsd.addColumn("LFTHASAT", "2.11 BLOOD CHEMISTRY -  Liver Function Test - High ASAT (SGOT)", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LFT_SGOT, 41, 100), indParam), "");

		cohortDsd.addColumn("LFTSGPTT", "2.12 BLOOD CHEMISTRY -  Liver Function Test - Total ALAT (SGPT)", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(LFT_SGPT), indParam), "");
		cohortDsd.addColumn("LFTLSGPT", "2.12 BLOOD CHEMISTRY -  Liver Function Test - Low ALAT (SGPT)", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LFT_SGPT, 0, 0), indParam), "");
		cohortDsd.addColumn("LFTHSGPT", "2.12 BLOOD CHEMISTRY -  Liver Function Test - High ALAT (SGPT)", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LFT_SGPT, 40, 100), indParam), "");

		cohortDsd.addColumn("LFTSPT", "2.13 BLOOD CHEMISTRY -  Liver Function Test - Total Serum Protein", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(LFT_SERUM_PROTEIN), indParam), "");
		cohortDsd.addColumn("LFTLSP", "2.13 BLOOD CHEMISTRY -  Liver Function Test - Low Serum Protein", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LFT_SERUM_PROTEIN, 0, 6), indParam), "");
		cohortDsd.addColumn("LFTHSP", "2.13 BLOOD CHEMISTRY -  Liver Function Test - High Serum Protein", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LFT_SERUM_PROTEIN, 8.3, 30), indParam), "");

		cohortDsd.addColumn("LFTAT", "2.14 BLOOD CHEMISTRY -  Liver Function Test - Total Albumin", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(LFT_SERUM_ALBUMIN), indParam), "");
		cohortDsd.addColumn("LFTLA", "2.14 BLOOD CHEMISTRY -  Liver Function Test - Low Albumin", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LFT_SERUM_ALBUMIN, 0, 3.4), indParam), "");
		cohortDsd.addColumn("LFTHA", "2.14 BLOOD CHEMISTRY -  Liver Function Test - High Albumin", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LFT_SERUM_ALBUMIN, 5.4, 30), indParam), "");

		cohortDsd.addColumn("LFTAPT", "2.15 BLOOD CHEMISTRY -  Liver Function Test - Total Alkaline Phosphatase", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(LFT_ALKALINE_PHOSPHATASE), indParam), "");
		cohortDsd.addColumn("LFTLAP", "2.15 BLOOD CHEMISTRY -  Liver Function Test - Low Alkaline Phosphatase", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LFT_ALKALINE_PHOSPHATASE, 0, 30), indParam), "");
		cohortDsd.addColumn("LFTHAP", "2.15 BLOOD CHEMISTRY -  Liver Function Test - High Alkaline Phosphatase", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LFT_ALKALINE_PHOSPHATASE, 120, 300), indParam), "");


		cohortDsd.addColumn("LPTTC", "2.17 BLOOD CHEMISTRY -  Lipid Profile Test - Total Cholestrol", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(LP_TOTAL_CHOLESTEROL), indParam), "");
		cohortDsd.addColumn("LPTLC", "2.17 BLOOD CHEMISTRY - Lipid Profile Test - Low Cholestrol", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LP_TOTAL_CHOLESTEROL, 0, 0), indParam), "");
		cohortDsd.addColumn("LFTHC", "2.17 BLOOD CHEMISTRY - Lipid Profile Test - High Cholestrol", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LP_TOTAL_CHOLESTEROL, 5.2, 30), indParam), "");

		//TODO: Check concept 166039 for serum glucose is same as 1009 for Triglycerides used 
		cohortDsd.addColumn("LPTT", "2.18 BLOOD CHEMISTRY -  Lipid Profile Test - Total Triglycerides", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(LP_TRIGLYCERIDES), indParam), "");
		cohortDsd.addColumn("LPTLT", "2.18 BLOOD CHEMISTRY - Lipid Profile Test - Low Triglycerides", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LP_TRIGLYCERIDES, 0, 0), indParam), "");
		cohortDsd.addColumn("LPTHT", "2.18 BLOOD CHEMISTRY - Lipid Profile Test - High Triglycerides", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LP_TRIGLYCERIDES, 2.3, 30), indParam), "");

		//TODO: Check concept 166045 for serum glucose is same as 1008 for LDL used 
		cohortDsd.addColumn("LPTLDL", "2.19 BLOOD CHEMISTRY -  Lipid Profile Test - Total LDL", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(LP_LDL), indParam), "");
		cohortDsd.addColumn("LPTLLDL", "2.19 BLOOD CHEMISTRY - Lipid Profile Test - Low LDL", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LP_LDL, 0, 0), indParam), "");
		cohortDsd.addColumn("LPTHLDL", "2.19 BLOOD CHEMISTRY - Lipid Profile Test - High LDL", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(LP_LDL, 2.59, 30), indParam), "");

		//Hormonal Tests
		cohortDsd.addColumn("HTTT3", "2.20 BLOOD CHEMISTRY -  Hormonal Test -  Total T3", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(HORMONAL_TEST_T3), indParam), "");
		cohortDsd.addColumn("HTLT3", "2.20 BLOOD CHEMISTRY - Hormonal Test -  Low T3", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(HORMONAL_TEST_T3, 0, 3.5), indParam), "");
		cohortDsd.addColumn("HTHT3", "2.20 BLOOD CHEMISTRY - Hormonal Test -  High T3", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(HORMONAL_TEST_T3, 7, 30), indParam), "");

		cohortDsd.addColumn("HTTT4", "2.21 BLOOD CHEMISTRY - Hormonal Test -  Total T4", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(HORMONAL_TEST_T4), indParam), "");
		cohortDsd.addColumn("HTLT4", "2.21 BLOOD CHEMISTRY - Hormonal Test -  Low T4", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(HORMONAL_TEST_T4, 0, 10), indParam), "");
		cohortDsd.addColumn("HTHT4", "2.21 BLOOD CHEMISTRY - Hormonal Test -  High T4", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(HORMONAL_TEST_T4, 22, 50), indParam), "");

		cohortDsd.addColumn("HTTTSH", "2.22 BLOOD CHEMISTRY - Hormonal Test -  Total TSH", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(HORMONAL_TEST_TSH), indParam), "");
		cohortDsd.addColumn("HTLTSH", "2.22 BLOOD CHEMISTRY - Hormonal Test -  Low TSH", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(HORMONAL_TEST_TSH, 0, 3.5), indParam), "");
		cohortDsd.addColumn("HTHTSH", "2.22 BLOOD CHEMISTRY - Hormonal Test -  High TSH", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(HORMONAL_TEST_TSH, 4.75, 30), indParam), "");

		cohortDsd.addColumn("HTTPSA", "2.23 BLOOD CHEMISTRY - Hormonal Test -  Total PSA", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(HORMONAL_TEST_PSA), indParam), "");
		cohortDsd.addColumn("HTLPSA", "2.23 BLOOD CHEMISTRY - Hormonal Test -  Low PSA", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(HORMONAL_TEST_PSA, 0, 0), indParam), "");
		cohortDsd.addColumn("HTHPSA", "2.23 BLOOD CHEMISTRY - Hormonal Test -  High PSA", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(HORMONAL_TEST_PSA, 4, 30), indParam), "");
       
		//Tumor markers 
		cohortDsd.addColumn("TMTCEA", "2.24 BLOOD CHEMISTRY - Tumor Makers -  Total CEA", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(TUMOR_MARKERS_CEA), indParam), "");
		cohortDsd.addColumn("TMLCEA", "2.24 BLOOD CHEMISTRY - Tumor Makers  -  Low CEA", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(TUMOR_MARKERS_CEA, 0, 0), indParam), "");
		cohortDsd.addColumn("TMHCEA", "2.24 BLOOD CHEMISTRY - Tumor Makers  -  High CEA", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(TUMOR_MARKERS_CEA, 2.5, 30), indParam), "");

		cohortDsd.addColumn("TMTC153", "2.25 BLOOD CHEMISTRY - Tumor Makers -  Total C15-3", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(TUMOR_MARKERS_C15_3), indParam), "");
		cohortDsd.addColumn("TMLC153", "2.25 BLOOD CHEMISTRY - Tumor Makers  -  Low C15-3", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(TUMOR_MARKERS_C15_3, 0, 0), indParam), "");
		cohortDsd.addColumn("TMHC153", "2.25 BLOOD CHEMISTRY - Tumor Makers  -  High C15-3", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(TUMOR_MARKERS_C15_3, 30, 100), indParam), "");

		// CSF CHEMISTRY
		cohortDsd.addColumn("CSFTP", "2.26 BLOOD CHEMISTRY - CSF Chemistry -  Total Proteins", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(CFS_CHEMISTRY_PROTEINS), indParam), "");
		cohortDsd.addColumn("CSFLP", "2.26 BLOOD CHEMISTRY - CSF Chemistry -  Low Proteins", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(CFS_CHEMISTRY_PROTEINS, 0, 15), indParam), "");
		cohortDsd.addColumn("CSFHP", "2.26 BLOOD CHEMISTRY - CSF Chemistry -  High Proteins", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(CFS_CHEMISTRY_PROTEINS, 60, 200), indParam), "");

		cohortDsd.addColumn("CSFTG", "2.27 BLOOD CHEMISTRY - CSF Chemistry -  Total Glucose", ReportUtils.map(moh706IndicatorLibrary.getTotalTestsByConcept(CFS_CHEMISTRY_GLUCOSE), indParam), "");
		cohortDsd.addColumn("CSFLG", "2.27 BLOOD CHEMISTRY - CSF Chemistry -  Low Glucose", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(CFS_CHEMISTRY_GLUCOSE, 0, 50), indParam), "");
		cohortDsd.addColumn("CSFHG", "2.27 BLOOD CHEMISTRY - CSF Chemistry -  High Glucose", ReportUtils.map(moh706IndicatorLibrary.getResultsBasedOnValueNumericQuestionBetweenLimits(CFS_CHEMISTRY_GLUCOSE, 80, 200), indParam), "");

		return cohortDsd;
    }

}

