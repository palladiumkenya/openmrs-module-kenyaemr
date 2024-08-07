/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.builder.dmi;

import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
import org.openmrs.module.kenyaemr.calculation.library.hiv.CountyAddressCalculation;
import org.openmrs.module.kenyaemr.calculation.library.mchcs.PersonAddressCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.RDQACalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.dmi.*;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.AgeAtReportingDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLNextAppointmentDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.*;
import org.openmrs.module.kenyaemr.reporting.library.dmi.IDSRIndicatorLibrary;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.ConvertedPersonDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonIdDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PreferredNameDataDefinition;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Builds({"kenyaemr.ehrReports.report.IDSRSuspectedCaseList"})
public class IDSRSuspectedCaseListReportBuilder extends AbstractReportBuilder {

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    @Autowired
    private IDSRIndicatorLibrary idsrIndicatorLibrary;

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
                Date.class), new Parameter("dateBasedReporting", "", String.class));
    }
    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
        return Arrays.asList(ReportUtils.map(idsrSuspectedCasesDataSetDefinitionColumns(),
                        "startDate=${startDate},endDate=${endDate}"),ReportUtils.map(caseIdentificationClassificationIndicators(),
                        "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(iliDataSetDefinitionColumns(),
                        "startDate=${startDate},endDate=${endDate}"), ReportUtils.map(
                        sariDataSetDefinitionColumns(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(dysenteryDataSetDefinitionColumns(),
                        "startDate=${startDate},endDate=${endDate}"), ReportUtils.map(
                        choleraDataSetDefinitionColumns(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(riftValleyFeverDataSetDefinitionColumns(),
                        "startDate=${startDate},endDate=${endDate}"), ReportUtils.map(
                        malariaDataSetDefinitionColumns(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(chikungunyaDataSetDefinitionColumns(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(poliomyelitisDataSetDefinitionColumns(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(viralHaemorrhagicFeverDataSetDefinitionColumns(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(measlesDataSetDefinitionColumns(), "startDate=${startDate},endDate=${endDate}"));
    }

    protected DataSetDefinition caseIdentificationClassificationIndicators() {
        CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
        cohortDsd.setName("IDSRSuspectedCaseList");
        cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String indParams = "startDate=${startDate},endDate=${endDate}";
        cohortDsd.addColumn("Dysentery", "",
                ReportUtils.map(idsrIndicatorLibrary.dysenteryCases(), indParams), "");
        cohortDsd.addColumn("Cholera", "",
                ReportUtils.map(idsrIndicatorLibrary.choleraCases(), indParams), "");
        cohortDsd.addColumn("ILI", "",
                ReportUtils.map(idsrIndicatorLibrary.iliCases(), indParams), "");
        cohortDsd.addColumn("SARI", "",
                ReportUtils.map(idsrIndicatorLibrary.sariCases(), indParams), "");
        cohortDsd.addColumn(
                "Riftvalley Fever", "",
                ReportUtils.map(idsrIndicatorLibrary.riftvalleyFeverCases(), indParams), "");
        cohortDsd.addColumn(
                "Malaria", "",
                ReportUtils.map(idsrIndicatorLibrary.malariaCases(), indParams), "");
        cohortDsd.addColumn("Chikungunya", "",
                ReportUtils.map(idsrIndicatorLibrary.chikungunyaCases(), indParams), "");
        cohortDsd.addColumn(
                "Poliomyelitis", "",
                ReportUtils.map(idsrIndicatorLibrary.poliomyelitisCases(), indParams), "");
        cohortDsd.addColumn(
                "Viral Haemorrhagic Fever",
                "", ReportUtils.map(idsrIndicatorLibrary.viralHaemorrhagicFeverCases(), indParams), "");
        cohortDsd.addColumn(
                "Measles",
                "", ReportUtils.map(idsrIndicatorLibrary.measlesCases(), indParams), "");

        return cohortDsd;
    }

    protected DataSetDefinition idsrSuspectedCasesDataSetDefinitionColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("idsrSuspectedCases");
        dsd.setDescription("IDSR Suspected Cases information");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
                HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class,
                CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                upn.getName(), upn), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                nupi.getName(), nupi), identifierFormatter);
        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
        ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ETLNextAppointmentDateDataDefinition lastAppointmentDateDataDefinition = new ETLNextAppointmentDateDataDefinition();
        lastAppointmentDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        lastAppointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VisitTypeWithComplaintsDataDefinition visitTypeDataDefinition = new VisitTypeWithComplaintsDataDefinition();
        visitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VisitDateWithComplaintsDataDefinition visitDateWithComplaintsDataDefinition = new VisitDateWithComplaintsDataDefinition();
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintAttendantProviderDataDefinition attendedByDataDefinition = new ComplaintAttendantProviderDataDefinition();
        attendedByDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        attendedByDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DiseaseDataDefinition diseaseDataDefinition = new DiseaseDataDefinition();
        diseaseDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        diseaseDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DataConverter formatter = new ObjectFormatter("{familyName}, {givenName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name",
                new PreferredNameDataDefinition(), formatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class,
                CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);
        dsd.addColumn("id", new PersonIdDataDefinition(), "");
        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("Visit Type", visitTypeDataDefinition, paramMapping);
        dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
        dsd.addColumn("Age", ageAtReportingDataDefinition, "endDate=${endDate}");
        dsd.addColumn("Visit Date", visitDateWithComplaintsDataDefinition, paramMapping);
        dsd.addColumn("Attended By", attendedByDataDefinition, paramMapping);
        dsd.addColumn("Next Visit", lastAppointmentDateDataDefinition, paramMapping);
        dsd.addColumn("Disease", diseaseDataDefinition, paramMapping);

        IDSRSuspectedCasesCohortDefinition cd = new IDSRSuspectedCasesCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition dysenteryDataSetDefinitionColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("dysentery");
        dsd.setDescription("Dysentery cases information");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
                HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class,
                CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                upn.getName(), upn), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                nupi.getName(), nupi), identifierFormatter);
        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
        ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ETLNextAppointmentDateDataDefinition lastAppointmentDateDataDefinition = new ETLNextAppointmentDateDataDefinition();
        lastAppointmentDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        lastAppointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VisitTypeWithComplaintsDataDefinition visitTypeDataDefinition = new VisitTypeWithComplaintsDataDefinition();
        visitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintAttendantProviderDataDefinition attendedByDataDefinition = new ComplaintAttendantProviderDataDefinition();
        attendedByDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        attendedByDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VisitDateWithComplaintsDataDefinition visitDateWithComplaintsDataDefinition = new VisitDateWithComplaintsDataDefinition();
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DataConverter formatter = new ObjectFormatter("{familyName}, {givenName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name",
                new PreferredNameDataDefinition(), formatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class,
                CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);
        dsd.addColumn("id", new PersonIdDataDefinition(), "");
        dsd.addColumn("Name", nameDef, "");
        // dsd.addColumn("Hospital Unit", new MFLCodeDataDefinition(), "");
        dsd.addColumn("Visit Type", visitTypeDataDefinition, paramMapping);
        dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
        dsd.addColumn("Age", ageAtReportingDataDefinition, "endDate=${endDate}");
        dsd.addColumn("Visit Date", visitDateWithComplaintsDataDefinition, paramMapping);
        dsd.addColumn("Attended By", attendedByDataDefinition, paramMapping);
        dsd.addColumn("Next Visit", lastAppointmentDateDataDefinition, paramMapping, new DateConverter(DATE_FORMAT));

        DysenteryCohortDefinition cd = new DysenteryCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition choleraDataSetDefinitionColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("cholera");
        dsd.setDescription("Cholera");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
                HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class,
                CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                upn.getName(), upn), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                nupi.getName(), nupi), identifierFormatter);
        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
        ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ETLNextAppointmentDateDataDefinition lastAppointmentDateDataDefinition = new ETLNextAppointmentDateDataDefinition();
        lastAppointmentDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        lastAppointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        VisitTypeWithComplaintsDataDefinition visitTypeDataDefinition = new VisitTypeWithComplaintsDataDefinition();
        visitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintAttendantProviderDataDefinition attendedByDataDefinition = new ComplaintAttendantProviderDataDefinition();
        attendedByDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        attendedByDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VisitDateWithComplaintsDataDefinition visitDateWithComplaintsDataDefinition = new VisitDateWithComplaintsDataDefinition();
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DataConverter formatter = new ObjectFormatter("{familyName}, {givenName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name",
                new PreferredNameDataDefinition(), formatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class,
                CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);
        dsd.addColumn("id", new PersonIdDataDefinition(), "");
        dsd.addColumn("Name", nameDef, "");
        // dsd.addColumn("Hospital Unit", new MFLCodeDataDefinition(), "");
        dsd.addColumn("Visit Type", visitTypeDataDefinition, paramMapping);
        dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
        dsd.addColumn("Age", ageAtReportingDataDefinition, "endDate=${endDate}");
        dsd.addColumn("Visit Date", visitDateWithComplaintsDataDefinition, paramMapping);
        dsd.addColumn("Attended By", attendedByDataDefinition, paramMapping);
        dsd.addColumn("Next Visit", lastAppointmentDateDataDefinition, paramMapping, new DateConverter(DATE_FORMAT));

        CholeraCohortDefinition cd = new CholeraCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition iliDataSetDefinitionColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("ili");
        dsd.setDescription("ILI Cases");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
                HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class,
                CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                upn.getName(), upn), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                nupi.getName(), nupi), identifierFormatter);
        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
        ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ETLNextAppointmentDateDataDefinition lastAppointmentDateDataDefinition = new ETLNextAppointmentDateDataDefinition();
        lastAppointmentDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        lastAppointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        VisitTypeWithComplaintsDataDefinition visitTypeDataDefinition = new VisitTypeWithComplaintsDataDefinition();
        visitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintAttendantProviderDataDefinition attendedByDataDefinition = new ComplaintAttendantProviderDataDefinition();
        attendedByDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        attendedByDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VisitDateWithComplaintsDataDefinition visitDateWithComplaintsDataDefinition = new VisitDateWithComplaintsDataDefinition();
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintsOnsetDateDataDefinition complaintsOnsetDateDataDefinition = new ComplaintsOnsetDateDataDefinition();
        complaintsOnsetDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        complaintsOnsetDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DurationOfComplaintsDataDefinition durationOfComplaintsDataDefinition = new DurationOfComplaintsDataDefinition();
        durationOfComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        durationOfComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        MedicalConditionsDataDefinition medicalConditionsDataDefinition = new MedicalConditionsDataDefinition();
        medicalConditionsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        medicalConditionsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        PresentingComplaintsDataDefinition presentingComplaintsDataDefinition = new PresentingComplaintsDataDefinition();
        presentingComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        presentingComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        OxygenSaturationDataDefinition oxygenSaturationDataDefinition = new OxygenSaturationDataDefinition();
        oxygenSaturationDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        oxygenSaturationDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ModeOfOxygenSaturationCollectionDataDefinition idsrModeOfOxygenCollection = new ModeOfOxygenSaturationCollectionDataDefinition();
        idsrModeOfOxygenCollection.addParameter(new Parameter("startDate", "Start Date", Date.class));
        idsrModeOfOxygenCollection.addParameter(new Parameter("endDate", "End Date", Date.class));

        RespiratoryRateDataDefinition respiratoryRateDataDefinition = new RespiratoryRateDataDefinition();
        respiratoryRateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        respiratoryRateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        IDSRTemperatureDataDefinition idsrTemperatureDataDefinition = new IDSRTemperatureDataDefinition();
        idsrTemperatureDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        idsrTemperatureDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        IDSRModeOfTempCollectionDataDefinition idsrModeOfTempCollection = new IDSRModeOfTempCollectionDataDefinition();
        idsrModeOfTempCollection.addParameter(new Parameter("startDate", "Start Date", Date.class));
        idsrModeOfTempCollection.addParameter(new Parameter("endDate", "End Date", Date.class));

        VaccinatedAgainstCovid19DataDefinition vaccinatedAgainstCovid19DataDefinition = new VaccinatedAgainstCovid19DataDefinition();
        vaccinatedAgainstCovid19DataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        vaccinatedAgainstCovid19DataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VaccinatedAgainstInfluenzaDataDefinition vaccinatedAgainstInfluenzaDataDefinition = new VaccinatedAgainstInfluenzaDataDefinition();
        vaccinatedAgainstInfluenzaDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        vaccinatedAgainstInfluenzaDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        SpecimenForInfluenzaCollectedDataDefinition specimenForInfluenzaCollectedDataDefinition = new SpecimenForInfluenzaCollectedDataDefinition();
        specimenForInfluenzaCollectedDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        specimenForInfluenzaCollectedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DateSpecimenForInfluenzaCollectedDataDefinition dateSpecimenForInfluenzaCollectedDataDefinition = new DateSpecimenForInfluenzaCollectedDataDefinition();
        dateSpecimenForInfluenzaCollectedDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dateSpecimenForInfluenzaCollectedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DataConverter formatter = new ObjectFormatter("{familyName}, {givenName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name",
                new PreferredNameDataDefinition(), formatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class,
                CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);
        dsd.addColumn("id", new PersonIdDataDefinition(), "");
        dsd.addColumn("Name", nameDef, "");
        // dsd.addColumn("Hospital Unit", new MFLCodeDataDefinition(), "");
        dsd.addColumn("Visit Type", visitTypeDataDefinition, paramMapping);
        dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
        dsd.addColumn("Age", ageAtReportingDataDefinition, "endDate=${endDate}");
        dsd.addColumn("County",new CalculationDataDefinition("County", new CountyAddressCalculation()), "",new CalculationResultConverter());
        dsd.addColumn("Village_Estate_Landmark", new CalculationDataDefinition("Village/Estate/Landmark", new PersonAddressCalculation()), "", new RDQACalculationResultConverter());
        dsd.addColumn("Visit Date", visitDateWithComplaintsDataDefinition, paramMapping);
       // dsd.addColumn("Next Visit", lastAppointmentDateDataDefinition, paramMapping, new DateConverter(DATE_FORMAT));
        dsd.addColumn("Presenting Complaints", presentingComplaintsDataDefinition, paramMapping);
        dsd.addColumn("Duration of Presenting Complaints", durationOfComplaintsDataDefinition, paramMapping);
        dsd.addColumn("Date of onset of the current Illness", complaintsOnsetDateDataDefinition, paramMapping);
        dsd.addColumn("Medical Conditions", medicalConditionsDataDefinition, paramMapping);
        dsd.addColumn("Temperature", idsrTemperatureDataDefinition, paramMapping);
        dsd.addColumn("Mode of Temperature Collection", idsrModeOfTempCollection, paramMapping);
        dsd.addColumn("Respiratory Rate", respiratoryRateDataDefinition, paramMapping);
        dsd.addColumn("Oxygen saturation", oxygenSaturationDataDefinition, paramMapping);
        dsd.addColumn("Mode of Oxygen saturation Collection", idsrModeOfOxygenCollection, paramMapping);
        //dsd.addColumn("Vaccinated Against Covid19", vaccinatedAgainstCovid19DataDefinition, paramMapping);
        dsd.addColumn("Vaccinated Against Influenza", vaccinatedAgainstInfluenzaDataDefinition, paramMapping);
        dsd.addColumn("Specimen Collected", specimenForInfluenzaCollectedDataDefinition, paramMapping);
        dsd.addColumn("Date Specimen Collected", dateSpecimenForInfluenzaCollectedDataDefinition, paramMapping);

        ILICohortDefinition cd = new ILICohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition sariDataSetDefinitionColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("sari");
        dsd.setDescription("SARI");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
                HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class,
                CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                upn.getName(), upn), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                nupi.getName(), nupi), identifierFormatter);
        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
        ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ETLNextAppointmentDateDataDefinition lastAppointmentDateDataDefinition = new ETLNextAppointmentDateDataDefinition();
        lastAppointmentDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        lastAppointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        VisitTypeWithComplaintsDataDefinition visitTypeDataDefinition = new VisitTypeWithComplaintsDataDefinition();
        visitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintAttendantProviderDataDefinition attendedByDataDefinition = new ComplaintAttendantProviderDataDefinition();
        attendedByDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        attendedByDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VisitDateWithComplaintsDataDefinition visitDateWithComplaintsDataDefinition = new VisitDateWithComplaintsDataDefinition();
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintsOnsetDateDataDefinition complaintsOnsetDateDataDefinition = new ComplaintsOnsetDateDataDefinition();
        complaintsOnsetDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        complaintsOnsetDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DurationOfComplaintsDataDefinition durationOfComplaintsDataDefinition = new DurationOfComplaintsDataDefinition();
        durationOfComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        durationOfComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        MedicalConditionsDataDefinition medicalConditionsDataDefinition = new MedicalConditionsDataDefinition();
        medicalConditionsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        medicalConditionsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DateOfHospitalAdmissionDataDefinition dateOfHospitalAdmissionDataDefinition = new DateOfHospitalAdmissionDataDefinition();
        dateOfHospitalAdmissionDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dateOfHospitalAdmissionDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        PresentingComplaintsDataDefinition presentingComplaintsDataDefinition = new PresentingComplaintsDataDefinition();
        presentingComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        presentingComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        OxygenSaturationDataDefinition oxygenSaturationDataDefinition = new OxygenSaturationDataDefinition();
        oxygenSaturationDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        oxygenSaturationDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ModeOfOxygenSaturationCollectionDataDefinition idsrModeOfOxygenCollection = new ModeOfOxygenSaturationCollectionDataDefinition();
        idsrModeOfOxygenCollection.addParameter(new Parameter("startDate", "Start Date", Date.class));
        idsrModeOfOxygenCollection.addParameter(new Parameter("endDate", "End Date", Date.class));

        RespiratoryRateDataDefinition respiratoryRateDataDefinition = new RespiratoryRateDataDefinition();
        respiratoryRateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        respiratoryRateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        IDSRTemperatureDataDefinition idsrTemperatureDataDefinition = new IDSRTemperatureDataDefinition();
        idsrTemperatureDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        idsrTemperatureDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        IDSRModeOfTempCollectionDataDefinition idsrModeOfTempCollection = new IDSRModeOfTempCollectionDataDefinition();
        idsrModeOfTempCollection.addParameter(new Parameter("startDate", "Start Date", Date.class));
        idsrModeOfTempCollection.addParameter(new Parameter("endDate", "End Date", Date.class));

        AdmittedInICUOrHDUDataDefinition admittedInICUOrHDUDataDefinition = new AdmittedInICUOrHDUDataDefinition();
        admittedInICUOrHDUDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        admittedInICUOrHDUDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VaccinatedAgainstCovid19DataDefinition vaccinatedAgainstCovid19DataDefinition = new VaccinatedAgainstCovid19DataDefinition();
        vaccinatedAgainstCovid19DataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        vaccinatedAgainstCovid19DataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VaccinatedAgainstInfluenzaDataDefinition vaccinatedAgainstInfluenzaDataDefinition = new VaccinatedAgainstInfluenzaDataDefinition();
        vaccinatedAgainstInfluenzaDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        vaccinatedAgainstInfluenzaDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        SpecimenForInfluenzaCollectedDataDefinition specimenForInfluenzaCollectedDataDefinition = new SpecimenForInfluenzaCollectedDataDefinition();
        specimenForInfluenzaCollectedDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        specimenForInfluenzaCollectedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DateSpecimenForInfluenzaCollectedDataDefinition dateSpecimenForInfluenzaCollectedDataDefinition = new DateSpecimenForInfluenzaCollectedDataDefinition();
        dateSpecimenForInfluenzaCollectedDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dateSpecimenForInfluenzaCollectedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DataConverter formatter = new ObjectFormatter("{familyName}, {givenName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name",
                new PreferredNameDataDefinition(), formatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class,
                CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);
        dsd.addColumn("id", new PersonIdDataDefinition(), "");
        dsd.addColumn("Name", nameDef, "");
        // dsd.addColumn("Hospital Unit", new MFLCodeDataDefinition(), "");
        dsd.addColumn("Visit Type", visitTypeDataDefinition, paramMapping);
        dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
        dsd.addColumn("Age", ageAtReportingDataDefinition, "endDate=${endDate}");
        dsd.addColumn("County",new CalculationDataDefinition("County", new CountyAddressCalculation()), "",new CalculationResultConverter());
        dsd.addColumn("Village_Estate_Landmark", new CalculationDataDefinition("Village/Estate/Landmark", new PersonAddressCalculation()), "", new RDQACalculationResultConverter());
        dsd.addColumn("Visit Date", visitDateWithComplaintsDataDefinition, paramMapping);
        //dsd.addColumn("Attended By", attendedByDataDefinition, paramMapping);
        //dsd.addColumn("Next Visit", lastAppointmentDateDataDefinition, paramMapping);
        dsd.addColumn("Admission Date", dateOfHospitalAdmissionDataDefinition, paramMapping);
        dsd.addColumn("Presenting Complaints", presentingComplaintsDataDefinition, paramMapping);
        dsd.addColumn("Duration of Presenting Complaints", durationOfComplaintsDataDefinition, paramMapping);
        dsd.addColumn("Date of onset of the current Illness", complaintsOnsetDateDataDefinition, paramMapping);
        dsd.addColumn("Medical Conditions", medicalConditionsDataDefinition, paramMapping);
        dsd.addColumn("Temperature", idsrTemperatureDataDefinition, paramMapping);
        dsd.addColumn("Mode of Temperature Collection", idsrModeOfTempCollection, paramMapping);
        dsd.addColumn("Respiratory Rate", respiratoryRateDataDefinition, paramMapping);
        dsd.addColumn("Oxygen saturation", oxygenSaturationDataDefinition, paramMapping);
        dsd.addColumn("Mode of Oxygen saturation Collection", idsrModeOfOxygenCollection, paramMapping);
        dsd.addColumn("Admitted to ICU or HDU", admittedInICUOrHDUDataDefinition, paramMapping);
        //dsd.addColumn("Vaccinated Against Covid19", vaccinatedAgainstCovid19DataDefinition, paramMapping);
        dsd.addColumn("Vaccinated Against Influenza", vaccinatedAgainstInfluenzaDataDefinition, paramMapping);
        dsd.addColumn("Specimen Collected", specimenForInfluenzaCollectedDataDefinition, paramMapping);
        dsd.addColumn("Date Specimen Collected", dateSpecimenForInfluenzaCollectedDataDefinition, paramMapping);

        SARICohortDefinition cd = new SARICohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate",
                "End Date", Date.class));

        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition riftValleyFeverDataSetDefinitionColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("riftvalleyFever");
        dsd.setDescription("Riftvalley Fever");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
                HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class,
                CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                upn.getName(), upn), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                nupi.getName(), nupi), identifierFormatter);
        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
        ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ETLNextAppointmentDateDataDefinition lastAppointmentDateDataDefinition = new ETLNextAppointmentDateDataDefinition();
        lastAppointmentDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        lastAppointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        VisitTypeWithComplaintsDataDefinition visitTypeDataDefinition = new VisitTypeWithComplaintsDataDefinition();
        visitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintAttendantProviderDataDefinition attendedByDataDefinition = new ComplaintAttendantProviderDataDefinition();
        attendedByDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        attendedByDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VisitDateWithComplaintsDataDefinition visitDateWithComplaintsDataDefinition = new VisitDateWithComplaintsDataDefinition();
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DataConverter formatter = new ObjectFormatter("{familyName}, {givenName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name",
                new PreferredNameDataDefinition(), formatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class,
                CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);
        dsd.addColumn("id", new PersonIdDataDefinition(), "");
        dsd.addColumn("Name", nameDef, "");
        // dsd.addColumn("Hospital Unit", new MFLCodeDataDefinition(), "");
        dsd.addColumn("Visit Type", visitTypeDataDefinition, paramMapping);
        dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
        dsd.addColumn("Age", ageAtReportingDataDefinition, "endDate=${endDate}");
        dsd.addColumn("Visit Date", visitDateWithComplaintsDataDefinition, paramMapping);
        dsd.addColumn("Attended By", attendedByDataDefinition, paramMapping);
        dsd.addColumn("Next Visit", lastAppointmentDateDataDefinition, paramMapping);

        RiftValleyFeverCohortDefinition cd = new RiftValleyFeverCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition malariaDataSetDefinitionColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("malaria");
        dsd.setDescription("Malaria Information");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
                HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class,
                CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                upn.getName(), upn), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                nupi.getName(), nupi), identifierFormatter);
        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
        ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ETLNextAppointmentDateDataDefinition lastAppointmentDateDataDefinition = new ETLNextAppointmentDateDataDefinition();
        lastAppointmentDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        lastAppointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        VisitTypeWithComplaintsDataDefinition visitTypeDataDefinition = new VisitTypeWithComplaintsDataDefinition();
        visitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintAttendantProviderDataDefinition attendedByDataDefinition = new ComplaintAttendantProviderDataDefinition();
        attendedByDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        attendedByDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VisitDateWithComplaintsDataDefinition visitDateWithComplaintsDataDefinition = new VisitDateWithComplaintsDataDefinition();
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DataConverter formatter = new ObjectFormatter("{familyName}, {givenName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name",
                new PreferredNameDataDefinition(), formatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class,
                CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);
        dsd.addColumn("id", new PersonIdDataDefinition(), "");
        dsd.addColumn("Name", nameDef, "");
        // dsd.addColumn("Hospital Unit", new MFLCodeDataDefinition(), "");
        dsd.addColumn("Visit Type", visitTypeDataDefinition, paramMapping);
        dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
        dsd.addColumn("Age", ageAtReportingDataDefinition, "endDate=${endDate}");
        dsd.addColumn("Visit Date", visitDateWithComplaintsDataDefinition, paramMapping);
        dsd.addColumn("Attended By", attendedByDataDefinition, paramMapping);
        dsd.addColumn("Next Visit", lastAppointmentDateDataDefinition, paramMapping);

        MalariaCohortDefinition cd = new MalariaCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition chikungunyaDataSetDefinitionColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("chikungunya");
        dsd.setDescription("Chikungunya");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
                HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class,
                CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                upn.getName(), upn), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                nupi.getName(), nupi), identifierFormatter);
        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
        ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ETLNextAppointmentDateDataDefinition lastAppointmentDateDataDefinition = new ETLNextAppointmentDateDataDefinition();
        lastAppointmentDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        lastAppointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        VisitTypeWithComplaintsDataDefinition visitTypeDataDefinition = new VisitTypeWithComplaintsDataDefinition();
        visitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintAttendantProviderDataDefinition attendedByDataDefinition = new ComplaintAttendantProviderDataDefinition();
        attendedByDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        attendedByDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VisitDateWithComplaintsDataDefinition visitDateWithComplaintsDataDefinition = new VisitDateWithComplaintsDataDefinition();
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DataConverter formatter = new ObjectFormatter("{familyName}, {givenName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name",
                new PreferredNameDataDefinition(), formatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class,
                CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);
        dsd.addColumn("id", new PersonIdDataDefinition(), "");
        dsd.addColumn("Name", nameDef, "");
        // dsd.addColumn("Hospital Unit", new MFLCodeDataDefinition(), "");
        dsd.addColumn("Visit Type", visitTypeDataDefinition, paramMapping);
        dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
        dsd.addColumn("Age", ageAtReportingDataDefinition, "endDate=${endDate}");
        dsd.addColumn("Visit Date", visitDateWithComplaintsDataDefinition, paramMapping);
        dsd.addColumn("Attended By", attendedByDataDefinition, paramMapping);
        dsd.addColumn("Next Visit", lastAppointmentDateDataDefinition, paramMapping);

        ChikungunyaCohortDefinition cd = new ChikungunyaCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition poliomyelitisDataSetDefinitionColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("poliomyelitis");
        dsd.setDescription("Poliomyelitis");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
                HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class,
                CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                upn.getName(), upn), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                nupi.getName(), nupi), identifierFormatter);
        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
        ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ETLNextAppointmentDateDataDefinition lastAppointmentDateDataDefinition = new ETLNextAppointmentDateDataDefinition();
        lastAppointmentDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        lastAppointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        VisitTypeWithComplaintsDataDefinition visitTypeDataDefinition = new VisitTypeWithComplaintsDataDefinition();
        visitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintAttendantProviderDataDefinition attendedByDataDefinition = new ComplaintAttendantProviderDataDefinition();
        attendedByDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        attendedByDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VisitDateWithComplaintsDataDefinition visitDateWithComplaintsDataDefinition = new VisitDateWithComplaintsDataDefinition();
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DataConverter formatter = new ObjectFormatter("{familyName}, {givenName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name",
                new PreferredNameDataDefinition(), formatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class,
                CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);
        dsd.addColumn("id", new PersonIdDataDefinition(), "");
        dsd.addColumn("Name", nameDef, "");
        // dsd.addColumn("Hospital Unit", new MFLCodeDataDefinition(), "");
        dsd.addColumn("Visit Type", visitTypeDataDefinition, paramMapping);
        dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
        dsd.addColumn("Age", ageAtReportingDataDefinition, "endDate=${endDate}");
        dsd.addColumn("Visit Date", visitDateWithComplaintsDataDefinition, paramMapping);
        dsd.addColumn("Attended By", attendedByDataDefinition, paramMapping);
        dsd.addColumn("Next Visit", lastAppointmentDateDataDefinition, paramMapping);

        PoliomyelitisCohortDefinition cd = new PoliomyelitisCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition viralHaemorrhagicFeverDataSetDefinitionColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("viralHaemorrhagicFever");
        dsd.setDescription("Viral Haemorrhagic Fever Information");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
                HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class,
                CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                upn.getName(), upn), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                nupi.getName(), nupi), identifierFormatter);
        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
        ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ETLNextAppointmentDateDataDefinition lastAppointmentDateDataDefinition = new ETLNextAppointmentDateDataDefinition();
        lastAppointmentDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        lastAppointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        VisitTypeWithComplaintsDataDefinition visitTypeDataDefinition = new VisitTypeWithComplaintsDataDefinition();
        visitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintAttendantProviderDataDefinition attendedByDataDefinition = new ComplaintAttendantProviderDataDefinition();
        attendedByDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        attendedByDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VisitDateWithComplaintsDataDefinition visitDateWithComplaintsDataDefinition = new VisitDateWithComplaintsDataDefinition();
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DataConverter formatter = new ObjectFormatter("{familyName}, {givenName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name",
                new PreferredNameDataDefinition(), formatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class,
                CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);
        dsd.addColumn("id", new PersonIdDataDefinition(), "");
        dsd.addColumn("Name", nameDef, "");
        // dsd.addColumn("Hospital Unit", new MFLCodeDataDefinition(), "");
        dsd.addColumn("Visit Type", visitTypeDataDefinition, paramMapping);
        dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
        dsd.addColumn("Age", ageAtReportingDataDefinition, "endDate=${endDate}");
        dsd.addColumn("Visit Date", visitDateWithComplaintsDataDefinition, paramMapping);
        dsd.addColumn("Attended By", attendedByDataDefinition, paramMapping);
        dsd.addColumn("Next Visit", lastAppointmentDateDataDefinition, paramMapping);

        ViralHaemorrhagicFeverCohortDefinition cd = new ViralHaemorrhagicFeverCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition measlesDataSetDefinitionColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("measles");
        dsd.setDescription("Measles");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
                HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class,
                CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                upn.getName(), upn), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                nupi.getName(), nupi), identifierFormatter);
        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
        ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ETLNextAppointmentDateDataDefinition lastAppointmentDateDataDefinition = new ETLNextAppointmentDateDataDefinition();
        lastAppointmentDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        lastAppointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        VisitTypeWithComplaintsDataDefinition visitTypeDataDefinition = new VisitTypeWithComplaintsDataDefinition();
        visitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintAttendantProviderDataDefinition attendedByDataDefinition = new ComplaintAttendantProviderDataDefinition();
        attendedByDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        attendedByDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        VisitDateWithComplaintsDataDefinition visitDateWithComplaintsDataDefinition = new VisitDateWithComplaintsDataDefinition();
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        visitDateWithComplaintsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DataConverter formatter = new ObjectFormatter("{familyName}, {givenName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name",
                new PreferredNameDataDefinition(), formatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class,
                CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);
        dsd.addColumn("id", new PersonIdDataDefinition(), "");
        dsd.addColumn("Name", nameDef, "");
        // dsd.addColumn("Hospital Unit", new MFLCodeDataDefinition(), "");
        dsd.addColumn("Visit Type", visitTypeDataDefinition, paramMapping);
        dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
        dsd.addColumn("Age", ageAtReportingDataDefinition, "endDate=${endDate}");
        dsd.addColumn("Visit Date", visitDateWithComplaintsDataDefinition, paramMapping);
        dsd.addColumn("Attended By", attendedByDataDefinition, paramMapping);
        dsd.addColumn("Next Visit", lastAppointmentDateDataDefinition, paramMapping);

        MeaslesCohortDefinition cd = new MeaslesCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }
}
