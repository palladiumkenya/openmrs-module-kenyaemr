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
import org.openmrs.module.kenyaemr.calculation.library.hiv.CountyAddressCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.SubCountyAddressCalculation;
import org.openmrs.module.kenyaemr.calculation.library.mchcs.PersonAddressCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.RDQACalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.MOH407ARegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.hei.HEICWCMuacDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.nutrition.*;
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
import org.openmrs.module.reporting.data.person.definition.*;
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
@Builds({"kenyaemr.ehrReports.report.moh407A"})
public class NutritionRegisterReportBuilder extends AbstractReportBuilder {
    public static final String ENC_DATE_FORMAT = "yyyy/MM/dd";
    public static final String DATE_FORMAT = "dd/MM/yyyy";

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
        dsd.setName("MOH407A");
        dsd.setDescription("Daily Nutrition MOH407A Report");
        dsd.addSortCriteria("Visit Date", SortCriteria.SortDirection.ASC);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));


        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
        PatientIdentifierType pcn = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.PATIENT_CLINIC_NUMBER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(upn.getName(), upn), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(nupi.getName(), nupi), identifierFormatter);

        NutritionHeightDataDefinition  nutritionHeightDataDefinition = new NutritionHeightDataDefinition();
        nutritionHeightDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionHeightDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionWeightDataDefinition nutritionWeightDataDefinition = new NutritionWeightDataDefinition();
        nutritionWeightDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionWeightDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionVisitTypeDataDefinition nutritionVisitTypeDataDefinition = new NutritionVisitTypeDataDefinition();
        nutritionVisitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionVisitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionLactatingDataDefinition nutritionLactatingDataDefinition = new NutritionLactatingDataDefinition();
        nutritionLactatingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionLactatingDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionSeroStatusDataDefinition nutritionSeroStatusDataDefinition = new NutritionSeroStatusDataDefinition();
        nutritionSeroStatusDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionSeroStatusDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionCoexialMedicalConditionDataDefinition nutritionCoexialMedicalConditionDataDefinition = new NutritionCoexialMedicalConditionDataDefinition();
        nutritionCoexialMedicalConditionDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionCoexialMedicalConditionDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionInterventionDataDefinition nutritionInterventionDataDefinition = new NutritionInterventionDataDefinition();
        nutritionInterventionDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionInterventionDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionFirst0To6DataDefinition nutritionFirst0To6DataDefinition = new NutritionFirst0To6DataDefinition();
        nutritionFirst0To6DataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionFirst0To6DataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionSecond6To12DataDefinition nutritionSecond6To12DataDefinition = new NutritionSecond6To12DataDefinition();
        nutritionSecond6To12DataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionSecond6To12DataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionPostnatalFeedingDataDefinition nutritionPostnatalFeedingDataDefinition = new NutritionPostnatalFeedingDataDefinition();
        nutritionPostnatalFeedingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionPostnatalFeedingDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionEdemaDataDefinition nutritionEdemaDataDefinition = new NutritionEdemaDataDefinition();
        nutritionEdemaDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionEdemaDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionPatientSAMorMAMDataDefinition nutritionPatientSAMorMAMDataDefinition = new NutritionPatientSAMorMAMDataDefinition();
        nutritionPatientSAMorMAMDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionPatientSAMorMAMDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionPatientOnARVsDataDefinition nutritionPatientOnARVsDataDefinition = new NutritionPatientOnARVsDataDefinition();
        nutritionPatientOnARVsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionPatientOnARVsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionAnaemiaLevelDataDefinition nutritionAnaemicLevelDataDefinition = new NutritionAnaemiaLevelDataDefinition();
        nutritionAnaemicLevelDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionAnaemicLevelDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionMetabolicDisordersDataDefinition nutritionMetabolicDisordersDataDefinition = new NutritionMetabolicDisordersDataDefinition();
        nutritionMetabolicDisordersDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionMetabolicDisordersDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionLatestCD4CountDataDefinition nutritionLatestCD4CountDataDefinition = new NutritionLatestCD4CountDataDefinition();
        nutritionLatestCD4CountDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionLatestCD4CountDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionLatestCD4CountDateDataDefinition nutritionLatestCD4CountDateDataDefinition = new NutritionLatestCD4CountDateDataDefinition();
        nutritionLatestCD4CountDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionLatestCD4CountDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionMaternalNutritionDataDefinition nutritionMaternalNutritionDataDefinition = new NutritionMaternalNutritionDataDefinition();
        nutritionMaternalNutritionDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionMaternalNutritionDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionCriticalNutritionPracticesDataDefinition nutritionCriticalNutritionPracticesDataDefinition = new NutritionCriticalNutritionPracticesDataDefinition();
        nutritionCriticalNutritionPracticesDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionCriticalNutritionPracticesDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionTherapeuticFoodsDataDefinition nutritionTherapeuticFoodsDataDefinition = new NutritionTherapeuticFoodsDataDefinition();
        nutritionTherapeuticFoodsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionTherapeuticFoodsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionSupplementalFoodsDataDefinition nutritionSupplementalFoodsDataDefinition = new NutritionSupplementalFoodsDataDefinition();
        nutritionSupplementalFoodsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionSupplementalFoodsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionMicronutrientsDataDefinition nutritionMicronutrientsDataDefinition = new NutritionMicronutrientsDataDefinition();
        nutritionMicronutrientsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionMicronutrientsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionPatientStatusForSAMorMAMDataDefinition nutritionPatientStatusForSAMorMAMDataDefinition = new NutritionPatientStatusForSAMorMAMDataDefinition();
        nutritionPatientStatusForSAMorMAMDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionPatientStatusForSAMorMAMDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionReferralStatusDataDefinition nutritionReferralStatusDataDefinition = new NutritionReferralStatusDataDefinition();
        nutritionReferralStatusDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionReferralStatusDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionCriteriaForAdmissionDataDefinition nutritionCriteriaForAdmissionDataDefinition = new NutritionCriteriaForAdmissionDataDefinition();
        nutritionCriteriaForAdmissionDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionCriteriaForAdmissionDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionTypeOfAdmissionDataDefinition nutritionTypeOfAdmissionDataDefinition = new NutritionTypeOfAdmissionDataDefinition();
        nutritionTypeOfAdmissionDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionTypeOfAdmissionDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionNextAppointmentDateDataDefinition nutritionNextAppointmentDateDataDefinition = new NutritionNextAppointmentDateDataDefinition();
        nutritionNextAppointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionNextAppointmentDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionCadreDataDefinition nutritionCadreDataDefinition = new NutritionCadreDataDefinition();
        nutritionCadreDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionCadreDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionDiagnosisDataDefinition nutritionDiagnosisDataDefinition = new NutritionDiagnosisDataDefinition();
        nutritionDiagnosisDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionDiagnosisDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

		PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);

        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("id", new PatientIdDataDefinition(), "");
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));
        dsd.addColumn("Unique Patient No", identifierDef, "");
        dsd.addColumn("NUPI", nupiDef, "");
        dsd.addColumn("Age", new AgeDataDefinition(), "");
        dsd.addColumn("Sex", new GenderDataDefinition(), "");
        dsd.addColumn("Parent/Caregiver Telephone No", new PersonAttributeDataDefinition(phoneNumber), "");
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));
        dsd.addColumn("Visit Type", nutritionVisitTypeDataDefinition, paramMapping);
		dsd.addColumn("County",new CalculationDataDefinition("County", new CountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Sub County", new CalculationDataDefinition("Subcounty", new SubCountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Village", new CalculationDataDefinition("Village/Estate/Landmark", new PersonAddressCalculation()), "",new RDQACalculationResultConverter());
		dsd.addColumn("Weight", nutritionWeightDataDefinition, paramMapping);
		dsd.addColumn("Height", nutritionHeightDataDefinition, paramMapping);
		dsd.addColumn("Muac", new HEICWCMuacDataDefinition(), "");
        dsd.addColumn("Lactating", nutritionLactatingDataDefinition, paramMapping);
        dsd.addColumn("Sero Status", nutritionSeroStatusDataDefinition, paramMapping);
        dsd.addColumn("Coexisting medical conditions", nutritionCoexialMedicalConditionDataDefinition, paramMapping);
        dsd.addColumn("Nutrition Intervention", nutritionInterventionDataDefinition, paramMapping);
        dsd.addColumn("First 0-6 months", nutritionFirst0To6DataDefinition, paramMapping);
        dsd.addColumn("Second 6-12 months", nutritionSecond6To12DataDefinition, paramMapping);
        dsd.addColumn("Postnatal Feeding", nutritionPostnatalFeedingDataDefinition, paramMapping);
        dsd.addColumn("Edema", nutritionEdemaDataDefinition, paramMapping);
        dsd.addColumn("SAM/MAM Patients", nutritionPatientSAMorMAMDataDefinition, paramMapping);
        dsd.addColumn("Patient on ARVs", nutritionPatientOnARVsDataDefinition, paramMapping);
        dsd.addColumn("Anaemic Level", nutritionAnaemicLevelDataDefinition, paramMapping);
        dsd.addColumn("Metabolic Disorders", nutritionMetabolicDisordersDataDefinition, paramMapping);
        dsd.addColumn("Latest CD4 Count", nutritionLatestCD4CountDataDefinition, paramMapping);
        dsd.addColumn("Latest CD4 Count Date", nutritionLatestCD4CountDateDataDefinition, paramMapping);
        dsd.addColumn("Maternal Nutrition", nutritionMaternalNutritionDataDefinition, paramMapping);
        dsd.addColumn("Diagnosis",nutritionDiagnosisDataDefinition,paramMapping);
        dsd.addColumn("Critical Nutrition Practices", nutritionCriticalNutritionPracticesDataDefinition, paramMapping);
        dsd.addColumn("Therapeutic Foods", nutritionTherapeuticFoodsDataDefinition, paramMapping);
        dsd.addColumn("Supplemental Foods", nutritionSupplementalFoodsDataDefinition, paramMapping);
        dsd.addColumn("Micronutrients", nutritionMicronutrientsDataDefinition, paramMapping);
        dsd.addColumn("Patient Status for SAM/MAM", nutritionPatientStatusForSAMorMAMDataDefinition, paramMapping);
        dsd.addColumn("Referral Status", nutritionReferralStatusDataDefinition, paramMapping);
        dsd.addColumn("Criteria for Admission", nutritionCriteriaForAdmissionDataDefinition, paramMapping);
        dsd.addColumn("Type of Admission", nutritionTypeOfAdmissionDataDefinition, paramMapping);
        dsd.addColumn("Next Appointment Date", nutritionNextAppointmentDateDataDefinition, paramMapping);
        dsd.addColumn("Cadre", nutritionCadreDataDefinition, paramMapping);
        MOH407ARegisterCohortDefinition cd = new MOH407ARegisterCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		dsd.addRowFilter(cd, paramMapping);
		return dsd;

    }
}
