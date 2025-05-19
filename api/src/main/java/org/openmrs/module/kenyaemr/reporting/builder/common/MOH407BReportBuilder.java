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

import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
import org.openmrs.module.kenyaemr.calculation.library.mchcs.PersonAddressCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.RDQACalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.specialClinics.SpecialClinicsRegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.PatientDisabilityTypeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDBMIDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDHeightDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDWeightDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsActualAgeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsAllergyAndIntoleranceDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsAnaemicLevelDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsCadreDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsCriteriaForAdmissionDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsCriticalNutritionPracticesDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsEdemaDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsFeedingPractice0_6MonthsDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsFeedingPractice6_12MonthsDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsHeightDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsInfantFeedingCounselingDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsLastCD4DataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsLinkedToOVCDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsMUACDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsMetabolicDisordersDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsMicronutrientsDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsNextAppointmentDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsNutritionalOutcomeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsNutritionalSamMamDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsNutritionalStatusDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsPatientOnARVsDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsPatientPregnantDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsPostnatalFeedingDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsReferralStatusDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsSeroStatusDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsSupplementalFoodsDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsTherapeuticFoodsDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsTypeOfAdmissionDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsVisitTypeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsWFHZScoreDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsWeightDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialCoexialMedicalConditionDataDefinition;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDatetimeDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.ConvertedPersonDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonAttributeDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PreferredNameDataDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.EncounterDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Builds({"kenyaemr.ehrReports.report.moh407B"})
public class MOH407BReportBuilder extends AbstractReportBuilder {
    public static final String ENC_DATE_FORMAT = "yyyy/MM/dd";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String SPECIAL_CLINIC = CommonMetadata._Form.NUTRITION;
	private static final String COMPARATIVE_OPERATION = "<";
	private static final Integer AGE = 15;

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
                ReportUtils.map(datasetColumns(), "startDate=${startDate},endDate=${endDate}")
        );
    }

    protected DataSetDefinition datasetColumns() {
        EncounterDataSetDefinition dsd = new EncounterDataSetDefinition();
        dsd.setName("MOH407B");
        dsd.setDescription("MOH407B Visit information");
        dsd.addSortCriteria("Visit Date", SortCriteria.SortDirection.ASC);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
        PatientIdentifierType pcn = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.PATIENT_CLINIC_NUMBER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition patientClinicNo = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(pcn.getName(), pcn), identifierFormatter);
		PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
		PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
		PatientIdentifierType shaNumber = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.SHA_UNIQUE_IDENTIFICATION_NUMBER);
		DataDefinition shaDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(shaNumber.getName(), shaNumber), identifierFormatter);
		DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(nupi.getName(), nupi), identifierFormatter);

		SpecialClinicsSeroStatusDataDefinition seroStatusDataDefinition = new SpecialClinicsSeroStatusDataDefinition();
		seroStatusDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		seroStatusDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		seroStatusDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsActualAgeDataDefinition actualAgeDataDefinition = new SpecialClinicsActualAgeDataDefinition();
		actualAgeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		actualAgeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		actualAgeDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialCoexialMedicalConditionDataDefinition coexialMedicalConditionDataDefinition = new SpecialCoexialMedicalConditionDataDefinition();
		coexialMedicalConditionDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		coexialMedicalConditionDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		coexialMedicalConditionDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsPostnatalFeedingDataDefinition postnatalFeedingDataDefinition = new SpecialClinicsPostnatalFeedingDataDefinition();
		postnatalFeedingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		postnatalFeedingDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		postnatalFeedingDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsEdemaDataDefinition edemaDataDefinition = new SpecialClinicsEdemaDataDefinition();
		edemaDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		edemaDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		edemaDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsWeightDataDefinition weightDataDefinition = new SpecialClinicsWeightDataDefinition();
		weightDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		weightDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		weightDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsHeightDataDefinition heightDataDefinition = new SpecialClinicsHeightDataDefinition();
		heightDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		heightDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		heightDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsWFHZScoreDataDefinition zScoreWFHDataDefinition = new SpecialClinicsWFHZScoreDataDefinition();
		zScoreWFHDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		zScoreWFHDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		zScoreWFHDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsMUACDataDefinition muacDataDefinition = new SpecialClinicsMUACDataDefinition();
		muacDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		muacDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		muacDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsNutritionalStatusDataDefinition nutritionalStatusDataDefinition = new SpecialClinicsNutritionalStatusDataDefinition();
		nutritionalStatusDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		nutritionalStatusDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		nutritionalStatusDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsPatientOnARVsDataDefinition patientOnARVsDataDefinition = new SpecialClinicsPatientOnARVsDataDefinition();
		patientOnARVsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		patientOnARVsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		patientOnARVsDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsAnaemicLevelDataDefinition anaemicLevelDataDefinition = new SpecialClinicsAnaemicLevelDataDefinition();
		anaemicLevelDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		anaemicLevelDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		anaemicLevelDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsMetabolicDisordersDataDefinition metabolicDisordersDataDefinition = new SpecialClinicsMetabolicDisordersDataDefinition();
		metabolicDisordersDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		metabolicDisordersDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		metabolicDisordersDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsAllergyAndIntoleranceDataDefinition allergyAndIntoleranceDataDefinition = new SpecialClinicsAllergyAndIntoleranceDataDefinition();
		allergyAndIntoleranceDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		allergyAndIntoleranceDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		allergyAndIntoleranceDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsCriticalNutritionPracticesDataDefinition criticalNutritionPracticesDataDefinition = new SpecialClinicsCriticalNutritionPracticesDataDefinition();
		criticalNutritionPracticesDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		criticalNutritionPracticesDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		criticalNutritionPracticesDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsTherapeuticFoodsDataDefinition therapeuticFoodsDataDefinition = new SpecialClinicsTherapeuticFoodsDataDefinition();
		therapeuticFoodsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		therapeuticFoodsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		therapeuticFoodsDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsSupplementalFoodsDataDefinition supplementalFoodsDataDefinition = new SpecialClinicsSupplementalFoodsDataDefinition();
		supplementalFoodsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		supplementalFoodsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		supplementalFoodsDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsMicronutrientsDataDefinition micronutrientsDataDefinition = new SpecialClinicsMicronutrientsDataDefinition();
		micronutrientsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		micronutrientsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		micronutrientsDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsReferralStatusDataDefinition referralStatusDataDefinition = new SpecialClinicsReferralStatusDataDefinition();
		referralStatusDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		referralStatusDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		referralStatusDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsCriteriaForAdmissionDataDefinition criteriaForAdmissionDataDefinition = new SpecialClinicsCriteriaForAdmissionDataDefinition();
		criteriaForAdmissionDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		criteriaForAdmissionDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		criteriaForAdmissionDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsTypeOfAdmissionDataDefinition typeOfAdmissionDataDefinition = new SpecialClinicsTypeOfAdmissionDataDefinition();
		typeOfAdmissionDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		typeOfAdmissionDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		typeOfAdmissionDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsCadreDataDefinition cadreDataDefinition = new SpecialClinicsCadreDataDefinition();
		cadreDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		cadreDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cadreDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsVisitTypeDataDefinition specialClinicsVisitTypeDataDefinition = new SpecialClinicsVisitTypeDataDefinition();
		specialClinicsVisitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		specialClinicsVisitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		specialClinicsVisitTypeDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsLinkedToOVCDataDefinition specialClinicsLinkedToOVCDataDefinition = new SpecialClinicsLinkedToOVCDataDefinition();
		specialClinicsLinkedToOVCDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		specialClinicsLinkedToOVCDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		specialClinicsLinkedToOVCDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsPatientPregnantDataDefinition patientPregnantDataDefinition = new SpecialClinicsPatientPregnantDataDefinition();
		patientPregnantDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		patientPregnantDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		patientPregnantDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsNutritionalSamMamDataDefinition nutritionalSamMamDataDefinition = new SpecialClinicsNutritionalSamMamDataDefinition();
		nutritionalSamMamDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		nutritionalSamMamDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		nutritionalSamMamDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsFeedingPractice0_6MonthsDataDefinition feedingPractice0_6MonthsDataDefinition = new SpecialClinicsFeedingPractice0_6MonthsDataDefinition();
		feedingPractice0_6MonthsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		feedingPractice0_6MonthsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		feedingPractice0_6MonthsDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsFeedingPractice6_12MonthsDataDefinition feedingPractice6_12MonthsDataDefinition = new SpecialClinicsFeedingPractice6_12MonthsDataDefinition();
		feedingPractice6_12MonthsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		feedingPractice6_12MonthsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		feedingPractice6_12MonthsDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsInfantFeedingCounselingDataDefinition infantAndYoungChildrenFeeding = new SpecialClinicsInfantFeedingCounselingDataDefinition();
		infantAndYoungChildrenFeeding.addParameter(new Parameter("endDate", "End Date", Date.class));
		infantAndYoungChildrenFeeding.addParameter(new Parameter("startDate", "Start Date", Date.class));
		infantAndYoungChildrenFeeding.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsNutritionalOutcomeDataDefinition nutritionalOutcome = new SpecialClinicsNutritionalOutcomeDataDefinition();
		nutritionalOutcome.addParameter(new Parameter("endDate", "End Date", Date.class));
		nutritionalOutcome.addParameter(new Parameter("startDate", "Start Date", Date.class));
		nutritionalOutcome.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsLastCD4DataDefinition lastCD4ResultDataDefinition = new SpecialClinicsLastCD4DataDefinition();
		lastCD4ResultDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		lastCD4ResultDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		lastCD4ResultDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsNextAppointmentDateDataDefinition nextAppointmentDateDataDefinition = new SpecialClinicsNextAppointmentDateDataDefinition();
		nextAppointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		nextAppointmentDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		nextAppointmentDateDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		OPDHeightDataDefinition opdHeightDataDefinition = new OPDHeightDataDefinition();
		opdHeightDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdHeightDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDWeightDataDefinition opdWeightDataDefinition = new OPDWeightDataDefinition();
		opdWeightDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdWeightDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDBMIDataDefinition opdBMIDataDefinition = new OPDBMIDataDefinition();
		opdBMIDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdBMIDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

		PatientDisabilityTypeDataDefinition patientDisabilityTypeDataDefinition = new PatientDisabilityTypeDataDefinition();
		patientDisabilityTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		patientDisabilityTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

		PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);

		dsd.addColumn("id", new PatientIdDataDefinition(), "");
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(DATE_FORMAT));
		dsd.addColumn("SHA No", shaDef, "");
		dsd.addColumn("Name", nameDef, "");
		dsd.addColumn("Visit Type", specialClinicsVisitTypeDataDefinition, paramMapping);
		dsd.addColumn("Linked to OVC", specialClinicsLinkedToOVCDataDefinition, paramMapping);
		dsd.addColumn("Age", actualAgeDataDefinition, paramMapping);
		dsd.addColumn("Sex", new GenderDataDefinition(), "");
		dsd.addColumn("Village", new CalculationDataDefinition("Village/Estate/Landmark", new PersonAddressCalculation()), "",new RDQACalculationResultConverter());
		dsd.addColumn("Guardian Telephone No", new PersonAttributeDataDefinition(phoneNumber), "");
		dsd.addColumn("Weight", weightDataDefinition, paramMapping);
		dsd.addColumn("Edema", edemaDataDefinition, paramMapping);
		dsd.addColumn("Height", heightDataDefinition, paramMapping);
		dsd.addColumn("ZScore (Weight for Height)", zScoreWFHDataDefinition, paramMapping);
		//dsd.addColumn("ZScore (Height For Age)", zScoreHFADataDefinition, paramMapping);
		//dsd.addColumn("BMI For Age ZScore", new SpecialClinicsHeightDataDefinition(), paramMapping);
		dsd.addColumn("MUAC", muacDataDefinition, paramMapping);
		dsd.addColumn("Nutritional Status", nutritionalStatusDataDefinition, paramMapping);
		dsd.addColumn("SAM and MAM", nutritionalSamMamDataDefinition, paramMapping);
		dsd.addColumn("Sero Status", seroStatusDataDefinition, paramMapping);
		dsd.addColumn("Patient on ARVs", patientOnARVsDataDefinition, paramMapping);
		dsd.addColumn("Co-existing Conditions", coexialMedicalConditionDataDefinition, paramMapping);
		dsd.addColumn("Anaemic Level", anaemicLevelDataDefinition, paramMapping);
		dsd.addColumn("Metabolic Disorders", metabolicDisordersDataDefinition, paramMapping);
		dsd.addColumn("Allergies And Intolerance", allergyAndIntoleranceDataDefinition, paramMapping);
		dsd.addColumn("CD4",  lastCD4ResultDataDefinition, paramMapping);
		// dsd.addColumn("Food Secure",  lastCD4ResultDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Feeding Practice (First 0-6 months)", feedingPractice0_6MonthsDataDefinition, paramMapping);
		dsd.addColumn("Feeding Practice (6-12 months)", feedingPractice6_12MonthsDataDefinition, paramMapping);
		dsd.addColumn("Nutrition counselling (Infant and Young Child feeding)", infantAndYoungChildrenFeeding, paramMapping);
		dsd.addColumn("Nutrition counselling (Critical Nutrition Practices)", criticalNutritionPracticesDataDefinition, paramMapping);
		dsd.addColumn("Therapeutic Foods", therapeuticFoodsDataDefinition, paramMapping);
		dsd.addColumn("Supplemental Foods", supplementalFoodsDataDefinition, paramMapping);
		dsd.addColumn("Micronutrients", micronutrientsDataDefinition, paramMapping);
		dsd.addColumn("Nutritional outcome", nutritionalOutcome, paramMapping);
		dsd.addColumn("Referral Status", referralStatusDataDefinition, paramMapping);
		dsd.addColumn("TCA", nextAppointmentDateDataDefinition, paramMapping, new DateConverter(DATE_FORMAT));
		dsd.addColumn("Clinicians Designation", cadreDataDefinition, paramMapping);

		SpecialClinicsRegisterCohortDefinition cd = new SpecialClinicsRegisterCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setSpecialClinic(SPECIAL_CLINIC);
		dsd.addRowFilter(cd, paramMapping);
		cd.setAge(AGE);
		cd.setComparisonOperator(COMPARATIVE_OPERATION);
		return dsd;
    }

}
