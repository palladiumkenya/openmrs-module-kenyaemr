/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.kenyaemr.reporting.builder.nupi;

import javax.persistence.AttributeConverter;

import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.report.CohortReportDescriptor;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyacore.report.builder.CalculationReportBuilder;
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.DateOfEnrollmentArtCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.DateArtStartDateConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.IdentifierConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLArtStartDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLCurrentRegLineDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLCurrentRegimenDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLFirstRegimenDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLNextAppointmentDateDataDefinition;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.ConvertedPersonDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonAttributeDataDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.springframework.stereotype.Component;

@Component
@Builds({ "kenyaemr.common.report.duplicateNUPI" })
public class DuplicateNUPIReportBuilder extends CalculationReportBuilder {
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	@Override
	protected void addColumns(CohortReportDescriptor report, PatientDataSetDefinition dsd) {
		PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
		DataDefinition cccIdentifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(upn.getName(), upn), new IdentifierConverter());
		PatientIdentifierType nupiIdentifierType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
		DataDefinition nupiIdentifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(nupiIdentifierType.getName(), nupiIdentifierType), new IdentifierConverter());
		PersonAttributeType duplicateSitesPA = Context.getPersonService().getPersonAttributeTypeByUuid(CommonMetadata._PersonAttributeType.DUPLICATE_NUPI_SITES_WITH_NATIONAL_REGISTRY);
        PersonAttributeType duplicateTotalSitesPA = Context.getPersonService().getPersonAttributeTypeByUuid(CommonMetadata._PersonAttributeType.DUPLICATE_NUPI_TOTALSITES_WITH_NATIONAL_REGISTRY);
		DataDefinition duplicateSiteNamesDef = new ConvertedPersonDataDefinition("attribute", new PersonAttributeDataDefinition(duplicateSitesPA.getName(), duplicateSitesPA));
		DataDefinition duplicateTotalSitesDef = new ConvertedPersonDataDefinition("attribute", new PersonAttributeDataDefinition(duplicateTotalSitesPA.getName(), duplicateTotalSitesPA));
		
		addStandardColumns(report, dsd);
		dsd.addColumn("CCC", cccIdentifierDef, "");
		dsd.addColumn("NUPI", nupiIdentifierDef, "");
		dsd.addColumn("Active Facilities", duplicateSiteNamesDef, "");
		dsd.addColumn("Total Sites", duplicateTotalSitesDef, "");
		dsd.addColumn("Enrollment Date", new CalculationDataDefinition("Enrollment Date", new DateOfEnrollmentArtCalculation()), "", new DateArtStartDateConverter());
		dsd.addColumn("Art Start Date", new ETLArtStartDateDataDefinition(), "", new DateConverter(DATE_FORMAT));
		dsd.addColumn("First Regimen", new ETLFirstRegimenDataDefinition(), "");
		dsd.addColumn("Current Regimen", new ETLCurrentRegimenDataDefinition(), "");
		dsd.addColumn("Current Regimen Line", new ETLCurrentRegLineDataDefinition(), "");
		dsd.addColumn("Next Appointment Date", new ETLNextAppointmentDateDataDefinition(), "", new DateConverter(DATE_FORMAT));
	}
}
