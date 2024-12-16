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
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.report.HybridReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractHybridReportBuilder;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
import org.openmrs.module.kenyaemr.calculation.library.hiv.CountyAddressCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.SubCountyAddressCalculation;
import org.openmrs.module.kenyaemr.calculation.library.mchcs.PersonAddressCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.RDQACalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.MOH204BRegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.MOH240LabRegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.HTSProviderDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.*;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
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
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Builds({ "kenyaemr.ehrReports.report.240" })
public class MOH240LabReportBuilder extends AbstractReportBuilder {
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
		dsd.setName("MOH240");
		dsd.setDescription("OPD Lab Visit information");
		dsd.addSortCriteria("Visit Date", SortCriteria.SortDirection.ASC);
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

		String paramMapping = "startDate=${startDate},endDate=${endDate}";

		DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
		DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
		PatientIdentifierType pcn = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.OPENMRS_ID);
		DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
		DataDefinition patientClinicNo = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(pcn.getName(), pcn), identifierFormatter);
	
		OPDDateSampleCollectedDataDefinition opdDateSampleCollectedDataDefinition = new OPDDateSampleCollectedDataDefinition();
		opdDateSampleCollectedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdDateSampleCollectedDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDDateSampleReceivedDataDefinition opdDateSampleReceivedDataDefinition = new OPDDateSampleReceivedDataDefinition();
		opdDateSampleReceivedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdDateSampleReceivedDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDLabResultsDataDefinition opdLabResultsDataDefinition = new OPDLabResultsDataDefinition();
		opdLabResultsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdLabResultsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDLabResultsDateDataDefinition opdLabResultsDateDataDefinition = new OPDLabResultsDateDataDefinition();
		opdLabResultsDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdLabResultsDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));	
		OPDClinicalDiagnosisDataDefinition opdClinicalDiagnosisDataDefinition = new OPDClinicalDiagnosisDataDefinition();
		opdClinicalDiagnosisDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdClinicalDiagnosisDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDPriorTreatmentsPrescribedDataDefinition opdPriorTreatmentsPrescribedDataDefinition = new OPDPriorTreatmentsPrescribedDataDefinition();
		opdPriorTreatmentsPrescribedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdPriorTreatmentsPrescribedDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDLabReferralsDataDefinition opdLabReferralsDataDefinition = new OPDLabReferralsDataDefinition();
		opdLabReferralsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdLabReferralsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDLabRemarksDataDefinition opdLabRemarksDataDefinition = new OPDLabRemarksDataDefinition();
		opdLabRemarksDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdLabRemarksDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDInvestigationRequiredDataDefinition opdInvestigationRequiredDataDefinition = new OPDInvestigationRequiredDataDefinition();
		opdInvestigationRequiredDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdInvestigationRequiredDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));


		PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);

		dsd.addColumn("Name", nameDef, "");
		dsd.addColumn("id", new PatientIdDataDefinition(), "");
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));
		dsd.addColumn("OPD Reference No", patientClinicNo, "");
		dsd.addColumn("Age", new AgeDataDefinition(), "");
		dsd.addColumn("Sex", new GenderDataDefinition(), "");
		dsd.addColumn("Telephone No", new PersonAttributeDataDefinition(phoneNumber), "");
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));		
		dsd.addColumn("County",new CalculationDataDefinition("County", new CountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Sub County", new CalculationDataDefinition("Subcounty", new SubCountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Village", new CalculationDataDefinition("Village/Estate/Landmark", new PersonAddressCalculation()), "",new RDQACalculationResultConverter());

		dsd.addColumn("Revisit", patientClinicNo, "");
		// dsd.addColumn("Lab Number", opdLabNumberDataDefinition, paramMapping); //TODO: missing  as Specimen ID
		dsd.addColumn("Clinical Diagnosis",opdClinicalDiagnosisDataDefinition, paramMapping);
		dsd.addColumn("Prior Treatment",opdPriorTreatmentsPrescribedDataDefinition, paramMapping);
		//dsd.addColumn("Type of Specimen",opdTreatmentPrescribedDataDefinition, paramMapping); //TODO: missing Specimen Type
		//dsd.addColumn("Condition of Specimen",opdTreatmentPrescribedDataDefinition, paramMapping); //TODO: missing  Specimen Condition
		dsd.addColumn("Investigation required",opdInvestigationRequiredDataDefinition, paramMapping); 
		dsd.addColumn("Date Sample Collected",opdDateSampleCollectedDataDefinition, paramMapping, new DateConverter(ENC_DATE_FORMAT));  
		//dsd.addColumn("Date Sample Received",opdDateSampleReceivedDataDefinition, paramMapping);  
		
		dsd.addColumn("Clinician Name", new OPDOrderingClinicianDataDefinition(), null);
		//dsd.addColumn("Date Sample Analysed",opdDateSampleReceivedDataDefinition, paramMapping);   //TODO: missing Date sample analysed

		dsd.addColumn("Results",opdLabResultsDataDefinition, paramMapping);
		//dsd.addColumn("Date results dispatched",opdLabResultsDateDataDefinition, paramMapping); //TODO: missing Date results dispatched
		//dsd.addColumn("Amount charged",opdLabResultsDateDataDefinition, paramMapping); //TODO: missing Amount charged
		//dsd.addColumn("Receipt number",opdLabResultsDateDataDefinition, paramMapping); //TODO: missing Receipt number


		dsd.addColumn("Referred to",opdLabReferralsDataDefinition, paramMapping);  
		dsd.addColumn("Comments",opdLabRemarksDataDefinition, paramMapping);
		dsd.addColumn("Analysing Officer",new OPDOrderingClinicianDataDefinition(), null);

		MOH240LabRegisterCohortDefinition cd = new MOH240LabRegisterCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));

		dsd.addRowFilter(cd, paramMapping);
		return dsd;

	}
}
