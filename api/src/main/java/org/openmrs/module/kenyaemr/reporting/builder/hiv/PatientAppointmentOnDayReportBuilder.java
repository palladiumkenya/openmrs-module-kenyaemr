/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.builder.hiv;

import org.openmrs.EncounterType;
import org.openmrs.PatientIdentifierType;
import org.openmrs.module.kenyacore.report.HybridReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractHybridReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
import org.openmrs.module.kenyaemr.calculation.library.TelephoneNumberCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.EncounterDatetimeConverter;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.ScheduledAppointmentCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.ArtDrugRefillAppointmentConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.CustomDateStringConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.HivProgramLastDiscontinuationDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.HonouredAppointmentDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLNextAppointmentBetweenDatesDataDefinition;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.TimeQualifier;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.EncountersForPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.*;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Builds({"kenyaemr.hiv.report.scheduledAppointment"})
public class PatientAppointmentOnDayReportBuilder extends AbstractHybridReportBuilder {
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	String paramMapping = "startDate=${startDate},endDate=${endDate}";

	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(
				new Parameter("startDate", "Start Date", Date.class),
				new Parameter("endDate", "End Date", Date.class),
				new Parameter("dateBasedReporting", "", String.class)
		);
	}
	/**
	 *
	 * @see org.openmrs.module.kenyacore.report.builder.AbstractCohortReportBuilder#addColumns(org.openmrs.module.kenyacore.report.CohortReportDescriptor, PatientDataSetDefinition)
	 */
	@Override
	protected void addColumns(HybridReportDescriptor report, PatientDataSetDefinition dsd) {

		PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
		DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
		DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(upn.getName(), upn), identifierFormatter);

		DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName}");
		DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
		dsd.setName("patientsScheduledForAppointment");
		dsd.addColumn("id", new PersonIdDataDefinition(), "");
		dsd.addColumn("Name", nameDef, "");
		dsd.addColumn("Unique Patient No", identifierDef, "");
		dsd.addColumn("Age", new AgeDataDefinition(), "", new DataConverter[0]);
		dsd.addColumn("Sex", new GenderDataDefinition(), "", new DataConverter[0]);
		dsd.addColumn("Phone number", new CalculationDataDefinition("Phone number", new TelephoneNumberCalculation()), "", new DataConverter[]{new CalculationResultConverter()});

		EncountersForPatientDataDefinition definition = new EncountersForPatientDataDefinition();
		EncounterType hivConsultation = MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_CONSULTATION);
		//EncounterType hivEnrollment = MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_ENROLLMENT); TODO: establish whether this is needed
		EncounterType consultation = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.CONSULTATION);

		HonouredAppointmentDataDefinition honouredVisitDs = new HonouredAppointmentDataDefinition();
		honouredVisitDs.addParameter(new Parameter("startDate", "Start Date", Date.class));
		honouredVisitDs.addParameter(new Parameter("endDate", "End Date", Date.class));

		ETLNextAppointmentBetweenDatesDataDefinition nextAppointmentDateDataDefinition = new ETLNextAppointmentBetweenDatesDataDefinition();
		nextAppointmentDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		nextAppointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

		List<EncounterType> encounterTypes = Arrays.asList(hivConsultation, consultation);

		definition.setWhich(TimeQualifier.LAST);
		definition.setTypes(encounterTypes);
		dsd.addColumn("Last Visit Date", definition, "", new EncounterDatetimeConverter());
		dsd.addColumn("Next HIV Appointment date", nextAppointmentDateDataDefinition, "startDate=${startDate},endDate=${endDate}", new DateConverter(DATE_FORMAT));
		dsd.addColumn("Last Discontinuation Date", new HivProgramLastDiscontinuationDateDataDefinition(), "", new CustomDateStringConverter());
		dsd.addColumn("Has visit on day", honouredVisitDs, paramMapping, new ArtDrugRefillAppointmentConverter());

	}

	@Override
	protected Mapped<CohortDefinition> buildCohort(HybridReportDescriptor descriptor, PatientDataSetDefinition dsd) {
		CohortDefinition cd = new ScheduledAppointmentCohortDefinition();
        cd.setName("Patients scheduled for appointment");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		return ReportUtils.map(cd, paramMapping);
	}
}
