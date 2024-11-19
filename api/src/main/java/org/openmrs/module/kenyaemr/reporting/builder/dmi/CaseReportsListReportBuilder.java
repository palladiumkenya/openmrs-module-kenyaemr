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
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
import org.openmrs.module.kenyaemr.calculation.library.hiv.CountyAddressCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.SubCountyAddressCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.dmi.casereport.CaseComplaintsCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.dmi.casereport.CaseDiagnosisCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.dmi.casereport.CaseLabsCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.dmi.casereport.CaseVitalsCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.dmi.casereport.IllCasesCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.MFLCodeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.AgeAtReportingDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.AdmissionDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.CaseUniqueIdDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.ComplaintDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.ComplaintDurationDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.ComplaintIdDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.ComplaintOnsetDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.DateCreatedDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.DateUpdatedDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.DiagnosisCaseDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.DiagnosisCaseDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.DiagnosisCaseIdDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.DiagnosisCaseSystemCodeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.DiagnosisCaseSystemDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.DiagnosisUniqueCaseIdDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.FinalPatientManagementOutcomeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.FinalPatientManagementOutcomeDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.InterviewDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.LabOrderDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.LabOrderIdDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.LabOrderCaseUniqueIdDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.LabOrderTestNameDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.LabOrderTestResultDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.OutpatientDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.VitalSignsDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.VitalsCaseUniqueIdDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.VitalsOxygenSaturationDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.VitalsOxygenSaturationModeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.VitalsRespiratoryRateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.VitalsSignsIdDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.VitalsTempCollectionModeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.VitalsTemperatureDataDefinition;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.VisitDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Builds({"kenyaemr.common.report.caseReports"})
public class CaseReportsListReportBuilder extends AbstractReportBuilder {

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
                Date.class), new Parameter("dateBasedReporting", "", String.class));
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
        return Arrays.asList(
                ReportUtils.map(illnessCasesDataSetDefinitionColumns(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(complaintDataSetDefinitionColumns(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(diagnosisDataSetDefinitionColumns(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(vitalSignsDataSetDefinitionColumns(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(LabsDataSetDefinitionColumns(),"startDate=${startDate},endDate=${endDate}")
        );
    }

    protected DataSetDefinition illnessCasesDataSetDefinitionColumns() {
        VisitDataSetDefinition dsd = new VisitDataSetDefinition();
        dsd.setName("illness");
        dsd.setDescription("Cases illness information");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        PatientIdentifierType openmrsId = MetadataUtils.existing(PatientIdentifierType.class,
                CommonMetadata._PatientIdentifierType.OPENMRS_ID);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class,
                CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                openmrsId.getName(), openmrsId), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                nupi.getName(), nupi), identifierFormatter);
        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
        ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        CaseUniqueIdDataDefinition caseUniqueIdDataDefinition = new CaseUniqueIdDataDefinition();
        caseUniqueIdDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        caseUniqueIdDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        InterviewDateDataDefinition interviewDateDataDefinition = new InterviewDateDataDefinition();
        interviewDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        interviewDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        AdmissionDateDataDefinition admissionDateDataDefinition = new AdmissionDateDataDefinition();
        admissionDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        admissionDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        OutpatientDateDataDefinition outpatientDateDataDefinition = new OutpatientDateDataDefinition();
        outpatientDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        outpatientDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DateCreatedDataDefinition dateCreatedDataDefinition = new DateCreatedDataDefinition();
        dateCreatedDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dateCreatedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DateUpdatedDataDefinition dateUpdatedDataDefinition = new DateUpdatedDataDefinition();
        dateUpdatedDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dateUpdatedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FinalPatientManagementOutcomeDataDefinition finalOutcomeDataDefinition = new FinalPatientManagementOutcomeDataDefinition();
        finalOutcomeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        finalOutcomeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FinalPatientManagementOutcomeDateDataDefinition finalOutcomeDateDataDefinition = new FinalPatientManagementOutcomeDateDataDefinition();
        finalOutcomeDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        finalOutcomeDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addColumn("Patient Unique Id", identifierDef, "");
        dsd.addColumn("NUPI", nupiDef, "");
        dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
        dsd.addColumn("Date of Birth", new BirthdateDataDefinition(), "", new BirthdateConverter(DATE_FORMAT));
        dsd.addColumn("MFL Code", new MFLCodeDataDefinition(), "");
        dsd.addColumn("County", new CalculationDataDefinition("County", new CountyAddressCalculation()), "",
                new CalculationResultConverter());
        dsd.addColumn("Sub County", new CalculationDataDefinition("Subcounty", new SubCountyAddressCalculation()), "",
                new CalculationResultConverter());
        dsd.addColumn("Case Unique ID", caseUniqueIdDataDefinition, paramMapping);

        dsd.addColumn("Interview Date", interviewDateDataDefinition, paramMapping);
        dsd.addColumn("Admission Date", admissionDateDataDefinition, paramMapping);
        dsd.addColumn("Outpatient Date", outpatientDateDataDefinition, paramMapping);
        dsd.addColumn("Created Date", dateCreatedDataDefinition, paramMapping);
        dsd.addColumn("Updated Date", dateUpdatedDataDefinition, paramMapping);
        dsd.addColumn("Final Outcome", finalOutcomeDataDefinition, paramMapping);
        dsd.addColumn("Final Outcome Date", finalOutcomeDateDataDefinition, paramMapping);

        IllCasesCohortDefinition cd = new IllCasesCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition complaintDataSetDefinitionColumns() {
        VisitDataSetDefinition dsd = new VisitDataSetDefinition();
        dsd.setName("complaint");
        dsd.setDescription("Cases Complaint information");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        CaseUniqueIdDataDefinition caseUniqueIdDataDefinition = new CaseUniqueIdDataDefinition();
        caseUniqueIdDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        caseUniqueIdDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintIdDataDefinition complaintIdDataDefinition = new ComplaintIdDataDefinition();
        complaintIdDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        complaintIdDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintDataDefinition complaintDataDefinition = new ComplaintDataDefinition();
        complaintDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        complaintDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintOnsetDateDataDefinition complaintOnsetDateDataDefinition = new ComplaintOnsetDateDataDefinition();
        complaintOnsetDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        complaintOnsetDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        ComplaintDurationDataDefinition complaintDurationDataDefinition = new ComplaintDurationDataDefinition();
        complaintDurationDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        complaintDurationDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addColumn("Case Unique ID", caseUniqueIdDataDefinition, paramMapping);
        dsd.addColumn("Complaint Id", complaintIdDataDefinition, paramMapping);
        dsd.addColumn("Complaint", complaintDataDefinition, paramMapping);
        dsd.addColumn("Complaint Onset Date", complaintOnsetDateDataDefinition, paramMapping);
        dsd.addColumn("Complaint Duration", complaintDurationDataDefinition, paramMapping);

        CaseComplaintsCohortDefinition cd = new CaseComplaintsCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition diagnosisDataSetDefinitionColumns() {
        VisitDataSetDefinition dsd = new VisitDataSetDefinition();
        dsd.setName("diagnosis");
        dsd.setDescription("Cases Diagnosis information");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        DiagnosisUniqueCaseIdDataDefinition diagnosisUniqueCaseIdDataDefinition = new DiagnosisUniqueCaseIdDataDefinition();
        diagnosisUniqueCaseIdDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        diagnosisUniqueCaseIdDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DiagnosisCaseIdDataDefinition diagnosisCaseIdDataDefinition = new DiagnosisCaseIdDataDefinition();
        diagnosisCaseIdDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        diagnosisCaseIdDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DiagnosisCaseDataDefinition diagnosisCaseDataDefinition = new DiagnosisCaseDataDefinition();
        diagnosisCaseDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        diagnosisCaseDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DiagnosisCaseDateDataDefinition diagnosisCaseDateDataDefinition = new DiagnosisCaseDateDataDefinition();
        diagnosisCaseDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        diagnosisCaseDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DiagnosisCaseSystemDataDefinition diagnosisCaseSystemDataDefinition = new DiagnosisCaseSystemDataDefinition();
        diagnosisCaseSystemDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        diagnosisCaseSystemDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        DiagnosisCaseSystemCodeDataDefinition diagnosisCaseSystemCodeDataDefinition = new DiagnosisCaseSystemCodeDataDefinition();
        diagnosisCaseSystemCodeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        diagnosisCaseSystemCodeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addColumn("Diagnosis Unique Case Id", diagnosisUniqueCaseIdDataDefinition, paramMapping);
        dsd.addColumn("Diagnosis Id", diagnosisCaseIdDataDefinition, paramMapping);
        dsd.addColumn("Diagnosis", diagnosisCaseDataDefinition, paramMapping);
        dsd.addColumn("Diagnosis Date", diagnosisCaseDateDataDefinition, paramMapping);
        dsd.addColumn("Diagnosis System", diagnosisCaseSystemDataDefinition, paramMapping);
        //dsd.addColumn("Diagnosis System Code", diagnosisCaseSystemCodeDataDefinition, paramMapping);

        CaseDiagnosisCohortDefinition cd = new CaseDiagnosisCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition vitalSignsDataSetDefinitionColumns() {
        VisitDataSetDefinition dsd = new VisitDataSetDefinition();
        dsd.setName("vitals");
        dsd.setDescription("Cases Vitals information");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        VitalsCaseUniqueIdDataDefinition vitalsCaseUniqueIdDataDefinition = new VitalsCaseUniqueIdDataDefinition();
        vitalsCaseUniqueIdDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        vitalsCaseUniqueIdDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        VitalsTemperatureDataDefinition vitalsTemperatureDataDefinition = new VitalsTemperatureDataDefinition();
        vitalsTemperatureDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        vitalsTemperatureDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        VitalsTempCollectionModeDataDefinition vitalsTempCollectionModeDataDefinition = new VitalsTempCollectionModeDataDefinition();
        vitalsTempCollectionModeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        vitalsTempCollectionModeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        VitalsRespiratoryRateDataDefinition vitalsRespiratoryRateDataDefinition = new VitalsRespiratoryRateDataDefinition();
        vitalsRespiratoryRateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        vitalsRespiratoryRateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        VitalsOxygenSaturationDataDefinition vitalsOxygenSaturationDataDefinition = new VitalsOxygenSaturationDataDefinition();
        vitalsOxygenSaturationDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        vitalsOxygenSaturationDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        VitalsOxygenSaturationModeDataDefinition vitalsOxygenSaturationModeDataDefinition = new VitalsOxygenSaturationModeDataDefinition();
        vitalsOxygenSaturationModeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        vitalsOxygenSaturationModeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        VitalsSignsIdDataDefinition vitalsSignsIdDataDefinition = new VitalsSignsIdDataDefinition();
        vitalsSignsIdDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        vitalsSignsIdDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        VitalSignsDateDataDefinition vitalSignsDateDataDefinition = new VitalSignsDateDataDefinition();
        vitalSignsDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        vitalSignsDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addColumn("Case Unique ID", vitalsCaseUniqueIdDataDefinition, paramMapping);
        dsd.addColumn("Temperature", vitalsTemperatureDataDefinition, paramMapping);
        dsd.addColumn("Temperature Mode", vitalsTempCollectionModeDataDefinition, paramMapping);
        dsd.addColumn("Respiratory Rate", vitalsRespiratoryRateDataDefinition, paramMapping);
        dsd.addColumn("Oxygen Saturation", vitalsOxygenSaturationDataDefinition, paramMapping);
        dsd.addColumn("Oxygen Saturation Mode", vitalsOxygenSaturationModeDataDefinition, paramMapping);
        dsd.addColumn("Vital Signs Date", vitalSignsDateDataDefinition, paramMapping);

        CaseVitalsCohortDefinition cd = new CaseVitalsCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition LabsDataSetDefinitionColumns() {
        VisitDataSetDefinition dsd = new VisitDataSetDefinition();
        dsd.setName("labs");
        dsd.setDescription("Cases Labs information");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        LabOrderCaseUniqueIdDataDefinition labOrdersCaseUniqueIdDataDefinition = new LabOrderCaseUniqueIdDataDefinition();
        labOrdersCaseUniqueIdDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        labOrdersCaseUniqueIdDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        LabOrderIdDataDefinition labOrderIdDataDefinition = new LabOrderIdDataDefinition();
        labOrderIdDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        labOrderIdDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        LabOrderTestResultDataDefinition labOrderTestResultDataDefinition = new LabOrderTestResultDataDefinition();
        labOrderTestResultDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        labOrderTestResultDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        LabOrderDateDataDefinition labOrderDateDataDefinition = new LabOrderDateDataDefinition();
        labOrderDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        labOrderDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        LabOrderTestNameDataDefinition labOrderTestNameDataDefinition = new LabOrderTestNameDataDefinition();
        labOrderTestNameDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        labOrderTestNameDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        VitalsOxygenSaturationModeDataDefinition vitalsOxygenSaturationModeDataDefinition = new VitalsOxygenSaturationModeDataDefinition();
        vitalsOxygenSaturationModeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        vitalsOxygenSaturationModeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        VitalsSignsIdDataDefinition vitalsSignsIdDataDefinition = new VitalsSignsIdDataDefinition();
        vitalsSignsIdDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        vitalsSignsIdDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        VitalSignsDateDataDefinition vitalSignsDateDataDefinition = new VitalSignsDateDataDefinition();
        vitalSignsDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        vitalSignsDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addColumn("Lab Case Unique Id", labOrdersCaseUniqueIdDataDefinition, paramMapping);
        dsd.addColumn("Lab Order Id", labOrderIdDataDefinition, paramMapping);
        dsd.addColumn("Lab Test Date", labOrderDateDataDefinition, paramMapping);
        dsd.addColumn("Lab Test Name", labOrderTestNameDataDefinition, paramMapping);
        dsd.addColumn("Lab Test Result", labOrderTestResultDataDefinition, paramMapping);

        CaseLabsCohortDefinition cd = new CaseLabsCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

}
