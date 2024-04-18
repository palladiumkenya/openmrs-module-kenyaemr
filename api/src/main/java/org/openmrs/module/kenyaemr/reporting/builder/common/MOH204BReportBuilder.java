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
import org.openmrs.module.kenyaemr.reporting.calculation.converter.RDQACalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.MOH204ARegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.MOH204BRegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.BloodPressureDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.*;
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
@Builds({"kenyaemr.ehrReports.report.moh204B"})
public class MOH204BReportBuilder extends AbstractReportBuilder {
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
        dsd.setName("MOH204B");
        dsd.setDescription("OPD Visit information");
        dsd.addSortCriteria("Visit Date", SortCriteria.SortDirection.ASC);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
        PatientIdentifierType pcn = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.PATIENT_CLINIC_NUMBER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition patientClinicNo = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(pcn.getName(), pcn), identifierFormatter);

		OPDHeightDataDefinition  opdHeightDataDefinition = new OPDHeightDataDefinition();
		opdHeightDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdHeightDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDWeightDataDefinition opdWeightDataDefinition = new OPDWeightDataDefinition();
		opdWeightDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdWeightDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));		
		OPDTemperatureDataDefinition opdTemperatureDataDefinition = new OPDTemperatureDataDefinition();
		opdTemperatureDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdTemperatureDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));		
		OPDMalariaAssessmentDataDefinition opdMalariaAssessmentDataDefinition = new OPDMalariaAssessmentDataDefinition();
		opdMalariaAssessmentDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdMalariaAssessmentDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDTreatmentPrescribedDataDefinition opdTreatmentPrescribedDataDefinition = new OPDTreatmentPrescribedDataDefinition();
		opdTreatmentPrescribedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdTreatmentPrescribedDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));		
		OPDTbScreeningDataDefinition opdTbScreeningDataDefinition = new OPDTbScreeningDataDefinition();
		opdTbScreeningDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdTbScreeningDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));		
		OPDReferredToDataDefinition opdReferredToDataDefinition = new OPDReferredToDataDefinition();
		opdReferredToDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdReferredToDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDReferredFromDataDefinition opdReferredFromDataDefinition = new OPDReferredFromDataDefinition();
		opdReferredFromDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdReferredFromDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDOutcomeDataDefinition opdOutcomeDataDefinition = new OPDOutcomeDataDefinition();
		opdOutcomeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdOutcomeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDBMIDataDefinition opdBMIDataDefinition = new OPDBMIDataDefinition();
		opdBMIDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdBMIDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDBloodPressureDataDefinition opdBloodPressureDataDefinition = new OPDBloodPressureDataDefinition();
		opdBloodPressureDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdBloodPressureDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDDiagnosisDataDefinition opdDiagnosisDataDefinition = new OPDDiagnosisDataDefinition();
		opdDiagnosisDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdDiagnosisDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		
		PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);

        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("id", new PatientIdDataDefinition(), "");
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));
        dsd.addColumn("Age", new AgeDataDefinition(), "");
        dsd.addColumn("Sex", new GenderDataDefinition(), "");
        dsd.addColumn("Parent/Caregiver Telephone No", new PersonAttributeDataDefinition(phoneNumber), "");   
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));         
		dsd.addColumn("OPD Number (New)", patientClinicNo, "");
		//dsd.addColumn("OPD Number (Revisit)", patientClinicNo, "");   //TODO:Determine revisit vs new visit
        dsd.addColumn("Referred From", opdReferredFromDataDefinition, paramMapping);
		dsd.addColumn("County",new CalculationDataDefinition("County", new CountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Sub County", new CalculationDataDefinition("Subcounty", new SubCountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Village", new CalculationDataDefinition("Village/Estate/Landmark", new PersonAddressCalculation()), "",new RDQACalculationResultConverter());
		dsd.addColumn("Weight", opdWeightDataDefinition, paramMapping);
		dsd.addColumn("Height", opdHeightDataDefinition, paramMapping);	
		dsd.addColumn("BMI", opdBMIDataDefinition, paramMapping);	
		dsd.addColumn("Temperature",opdTemperatureDataDefinition, paramMapping);
		dsd.addColumn("Blood Pressure", opdBloodPressureDataDefinition, paramMapping);		
		//dsd.addColumn("Visual Acuity", opdBloodPressureDataDefinition, paramMapping);		  //TODO: Determine how to get visual acuity. Is this an examination	
		dsd.addColumn("TB Screening",opdTbScreeningDataDefinition, paramMapping);
		dsd.addColumn("Malaria",opdMalariaAssessmentDataDefinition, paramMapping);
		dsd.addColumn("Diagnosis",opdDiagnosisDataDefinition, paramMapping);  //TODO: Add all diagnosis
		dsd.addColumn("Treatments Prescribed",opdTreatmentPrescribedDataDefinition, paramMapping);
		dsd.addColumn("Referred to",opdReferredToDataDefinition, paramMapping);  //TODO: missing  1=Community Unit
		dsd.addColumn("Remarks/Outcome",opdOutcomeDataDefinition, paramMapping);

		MOH204BRegisterCohortDefinition cd = new MOH204BRegisterCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		dsd.addRowFilter(cd, paramMapping);
		return dsd;

    }
}
