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
import org.openmrs.module.kenyaemr.reporting.MohReportUtils.DiagnosisLists;
import org.openmrs.module.kenyaemr.reporting.MohReportUtils.ReportAddonUtils;
import org.openmrs.module.kenyaemr.reporting.MohReportUtils.ReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH705.MOH705IndicatorLibrary;
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
 * Report builder for MOH705B
 * Equal and above 5 years
 * Diagnosis
 */
@Component
@Builds({"kenyaemr.common.report.moh705B"})
public class MOH705BReportBuilder extends AbstractReportBuilder {
    static final String EQUAL_AND_OVER_FIVE = ">=5";
	static final int TESTED_MALARIA = 168740;
	static final int SUSPECTED_MALARIA = 166623;

    @Autowired
    private MOH705IndicatorLibrary moh705indicatorLibrary;

    private final CommonDimensionLibrary commonDimensionLibrary;

    @Autowired
    public MOH705BReportBuilder(MOH705IndicatorLibrary moh705indicatorLibrary, CommonDimensionLibrary commonDimensionLibrary) {
        this.moh705indicatorLibrary = moh705indicatorLibrary;
        this.commonDimensionLibrary = commonDimensionLibrary;
    }


    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
                Date.class), new Parameter("dateBasedReporting", "", String.class));
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,
                                                            ReportDefinition reportDefinition) {
        return Arrays.asList(ReportUtils.map(moh705BDataset(), "startDate=${startDate},endDate=${endDate}"));
    }

    protected DataSetDefinition moh705BDataset() {
        String indParams = "startDate=${startDate},endDate=${endDate}";

        CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
        cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cohortDsd.setName("MOH705B");
        cohortDsd.setDescription("MOH 705B");
        cohortDsd.addDimension("day", ReportUtils.map(commonDimensionLibrary.encountersOfMonthPerDay(), indParams));
        ReportingUtils.addRow(cohortDsd, "DA", "Diarrhoea", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getDiarrheaDiagnosisList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "TBA", "Tuberculosis", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getTuberculosisDiagnosisList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "DYA", "Dysentery", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getDysenteryList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "CLA", "Cholera", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getCholeraList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "MMA", "Meningococcal Meningitis", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getMeningococcalMeningitisList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "MOA", "Other Meningitis", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getOtherMenigitisList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "NTA", "Neonatal Tetanus", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getNeonatalTetanusList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "PMA", "Poliomyelitis", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getPoliomyelitisList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "CPA", "Chicken Pox", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getChickenPoxList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "MEA", "Measles", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getMeaslesList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "HEA", "Hepatitis", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getHepatitisList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "MPSA", "Mumps", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getMumpsList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
		ReportingUtils.addRow(cohortDsd, "TMC", "Tested Malaria", ReportUtils.map(moh705indicatorLibrary.diagnosis(Arrays.asList(TESTED_MALARIA), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "SUA", "Suspected malaria", ReportUtils.map(moh705indicatorLibrary.diagnosis(Arrays.asList(SUSPECTED_MALARIA),EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "COA",  "Confirmed Malaria positive",ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getMalariaList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "MPA", "Malaria In Pregnancy", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getMalariaInPregnancyList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "TYA", "Typhoid Fever", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getTyphoidList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "STIA", "STI", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getSexuallyTransmittedInfectionsList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "URA", "Urinary tract infection", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getUrinaryTractInfectionList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "BIA", "Bilharzia",ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getBilharziaList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "INA", "Intestinal worms", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getInterstinalwormsList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "MLA", "Malnutrition", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getMalnutritionList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "ANEA", "Aneamia", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getAnaemiaList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "EYA", "Eye Infections", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getEyeInfectionsList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "EIA", "Ear Infection Conditions", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getEarInfectionConditionsList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "UPA", "Upper Respiratory Tract Infections", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getUpperRespiratoryTractInfectionsList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "ASA", "Asthma",ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getAsthmaList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "PNA", "Pneumonia",ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getPneumoniaList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "OTRA", "Other Dis Of Respiratory System",ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getOtherDisOfRespiratorySystemList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "ABA", "Abortion",ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getAbortionList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "DPA", "Dis Of Puerperium & Childbath", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getDisOfPuerperiumChildbathList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "HYA", "Hypertension", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getHypertensionList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "MDA", "Mental Disorders", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getMentalDisordersList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "DEA", "Dental Disorders", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getDentalDisordersList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "JIA", "Jiggers Infestation", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getJiggersInfestationList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "DSA", "Disease Of The Skin", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getDiseaseOfTheSkinList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "AJPA", "Anthritis Joint Pains", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getAnthritisJointPainsList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "POA", "Poisoning", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getPoisoningList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "ROA", "Road Traffic Injuries", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getRoadTrafficInjuriesList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "OIA", "Other Injuries", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getOtherInjuriesList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "SEA", "Sexual Assault", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getSexualAssaultList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "VRA", "Violence Related Injuries", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getViolenceRelatedInjuriesList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "BUA", "Burns", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getBurnsList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "SNA", "Snake Bites", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getSnakeBitesList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "DOA", "Dog Bites", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getDogBitesList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "OBA", "Other Bites", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getOtherBitesList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "DTA", "Diabetes", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getDiabetesList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "EPA", "Epilepsy", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getEpilepsyList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "BRLA", "Brucellosis", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getBrucellosisList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "CAA", "Cardiovascular Conditions", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getCardiovascularConditionsList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "CNSA", "Central Nervous System Conditions", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getOtherCentralNervousSystemConditionsList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "OVA", "Overweight", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getOvrerweightList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "MSA", "Muscular Skeletal Conditions",ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getMuscularSkeletalConditionsList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "FIA", "Fistula Birth Related", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getFistulaBirthRelatedList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "NSA", "Neoplams",ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getNeoplamsList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "PHA", "Physical Disability", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getPhysicalDisabilityList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "TRA", "Tryponomiasis", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getTryponomiasisList(), EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "KAA", "Kalazar Leishmaniasis", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getKalazarLeishmaniasisList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "YEA", "Yellow Fever", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getYellowFeverList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "VHA", "Viral Haemorrhagic Fever", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getViralHaemorrhagicFeverList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "DRA", "Death due to road traffic injuries",ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getRoadTrafficInjuriesList(),EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
		ReportingUtils.addRow(cohortDsd, "AM", "AMOEBIASIS",	ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getAmoebiasis(),EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
		ReportingUtils.addRow(cohortDsd, "RVF", "Rift valley fever", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getRiftValleyFeverList(),EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
		ReportingUtils.addRow(cohortDsd, "CKU", "Chikungunya fever", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getChikungunyaFeverList(),EQUAL_AND_OVER_FIVE), indParams),	ReportAddonUtils.getAdultChildrenColumns());
		ReportingUtils.addRow(cohortDsd, "DENF", "Dengue fever", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getDengueFeverList(),EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
		ReportingUtils.addRow(cohortDsd, "CL", "Cutaneous Leishmaniasis", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getCutaneousLeishmaniasisList(),EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
		ReportingUtils.addRow(cohortDsd, "ANT", "Suspected Anthrax",	ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getAnthraxList(),EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
           
        ReportingUtils.addRow(cohortDsd, "AODA", "All other diseases for adults", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getAllOtherDiseasesListForAdults(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "NFAA", "No. Of First Attendances",ReportUtils.map(moh705indicatorLibrary.newAttendances(EQUAL_AND_OVER_FIVE)), ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "RETA", "Re-Attendances", ReportUtils.map(moh705indicatorLibrary.reAttendances(EQUAL_AND_OVER_FIVE)),ReportAddonUtils.getAdultChildrenColumns());
		ReportingUtils.addRow(cohortDsd,"RFHA","Referrals from other health facility", ReportUtils.map(moh705indicatorLibrary.referralsFromOtherFacilities(EQUAL_AND_OVER_FIVE), indParams),ReportAddonUtils.getAdultChildrenColumns());
		ReportingUtils.addRow(cohortDsd,"RFCA","Referrals to other health facility", ReportUtils.map(moh705indicatorLibrary.referralsToOtherFacilities(EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());


        return cohortDsd;
    }
}
