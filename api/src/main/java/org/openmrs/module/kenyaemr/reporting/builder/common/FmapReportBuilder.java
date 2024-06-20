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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.library.shared.hiv.art.FmapIndicatorLibrary;
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
 * FMAP MOH-729 Report
 */
@Component
@Builds({"kenyaemr.etl.common.report.fmap"})
public class FmapReportBuilder extends AbstractReportBuilder {

	protected static final Log log = LogFactory.getLog(FmapReportBuilder.class);

	@Autowired
	private FmapIndicatorLibrary fmapIndicators;

	@Autowired
	private CommonDimensionLibrary commonDimensions;

	/**
	 * @see org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder#getParameters(org.openmrs.module.kenyacore.report.ReportDescriptor)
	 */
	@Override
	protected List<Parameter> getParameters(ReportDescriptor descriptor) {
		return Arrays.asList(
			new Parameter("startDate", "Start Date", Date.class),
			new Parameter("endDate", "End Date", Date.class)
			//new Parameter("dateBasedReporting", "", String.class)
		);
	}

	/**
	 * @see org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder#buildDataSets(org.openmrs.module.kenyacore.report.ReportDescriptor, org.openmrs.module.reporting.report.definition.ReportDefinition)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
		return Arrays.asList(
			ReportUtils.map(fmapPatientRegimens(), "startDate=${startDate},endDate=${endDate}")
		);
	}


	/**
	 * Creates the dataset for Adult first line regimens
	 *
	 * @return the dataset
	 */
	protected DataSetDefinition fmapPatientRegimens() {
		CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
		cohortDsd.setName("patientReegimen");
		cohortDsd.setDescription("ARV Treatment Regimen");
		cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));

		String indParams = "startDate=${startDate},endDate=${endDate}";
		//Adult First Line
		//Male
		cohortDsd.addColumn("AF1A_M", "AZT+3TC+NVP", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/NVP","First line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AF1B_M", "AZT+3TC+EFV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/EFV","First line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AF1D_M", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/DTG","First line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AF1E_M", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/LPV/r","First line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AF1F_M", "AZT+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/ATV/r","First line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AF2A_M", "TDF+3TC+NVP", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/NVP","First line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AF2B_M", "TDF+3TC+EFV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/EFV","First line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AF2D_M", "TDF+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/ATV/r","First line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AF2E_M", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG","First line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AF2F_M", "TDF+3TC+LPVr(1L Adults < 40kg)", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/LPV/r","First line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AF4A_M", "ABC+3TC+NVP", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/NVP","First line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AF4B_M", "ABC+3TC+EFV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/EFV","First line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AF4C_M", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/DTG","First line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AF5X_M", "Any other first line adult regimens", ReportUtils.map(fmapIndicators.patientsOnOtherRegimen("Other","First line","adult", "Male", "False"), indParams),"");
		//Female
		cohortDsd.addColumn("AF1A_F", "AZT+3TC+NVP", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/NVP","First line","adult", "Female", "False"), indParams),"");
		cohortDsd.addColumn("AF1B_F", "AZT+3TC+EFV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/EFV","First line","adult","Female", "False"), indParams),"");
		cohortDsd.addColumn("AF1D_F", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/DTG","First line","adult","Female", "False"), indParams),"");
		cohortDsd.addColumn("AF1E_F", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/LPV/r","First line","adult","Female", "False"), indParams),"");
		cohortDsd.addColumn("AF1F_F", "AZT+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/ATV/r","First line","adult","Female", "False"), indParams),"");
		cohortDsd.addColumn("AF2A_F", "TDF+3TC+NVP", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/NVP","First line","adult","Female", "False"), indParams),"");
		cohortDsd.addColumn("AF2B_F", "TDF+3TC+EFV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/EFV","First line","adult","Female", "False"), indParams),"");
		cohortDsd.addColumn("AF2D_F", "TDF+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/ATV/r","First line","adult","Female", "False"), indParams),"");
		cohortDsd.addColumn("AF2E_F", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG","First line","adult","Female", "False"), indParams),"");
		cohortDsd.addColumn("AF2F_F", "TDF+3TC+LPVr(1L Adults < 40kg)", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/LPV/r","First line","adult","Female", "False"), indParams),"");
		cohortDsd.addColumn("AF4A_F", "ABC+3TC+NVP", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/NVP","First line","adult","Female", "False"), indParams),"");
		cohortDsd.addColumn("AF4B_F", "ABC+3TC+EFV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/EFV","First line","adult","Female", "False"), indParams),"");
		cohortDsd.addColumn("AF4C_F", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/DTG","First line","adult","Female", "False"), indParams),"");
		cohortDsd.addColumn("AF5X_F", "Any other first line adult regimens", ReportUtils.map(fmapIndicators.patientsOnOtherRegimen("Other","First line","adult","Female", "False"), indParams),"");
		//Adult Second Line
		//Male
		cohortDsd.addColumn("AS1A_M", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/LPV/r","Second line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AS1B_M", "AZT+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/ATV/r","Second line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AS1C_M", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/DTG","Second line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AS2A_M", "TDF+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/LPV/r","Second line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AS2B_M", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG","Second line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AS2C_M", "TDF+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/ATV/r","Second line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AS5A_M", "ABC+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/LPV/r","Second line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AS5B_M", "ABC+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/ATV/r","Second line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AS5C_M", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/DTG","Second line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AS6X_M", "Any other second line adult regimens", ReportUtils.map(fmapIndicators.patientsOnOtherRegimen("Other","Second line","adult", "Male", "False"), indParams),"");
		//Female
		cohortDsd.addColumn("AS1A_F", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/LPV/r","Second line","adult", "Female", "False"), indParams),"");
		cohortDsd.addColumn("AS1B_F", "AZT+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/ATV/r","Second line","adult", "Female", "False"), indParams),"");
		cohortDsd.addColumn("AS1C_F", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/DTG","Second line","adult", "Female", "False"), indParams),"");
		cohortDsd.addColumn("AS2A_F", "TDF+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/LPV/r","Second line","adult", "Female", "False"), indParams),"");
		cohortDsd.addColumn("AS2B_F", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG","Second line","adult", "Female", "False"), indParams),"");
		cohortDsd.addColumn("AS2C_F", "TDF+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/ATV/r","Second line","adult", "Female", "False"), indParams),"");
		cohortDsd.addColumn("AS5A_F", "ABC+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/LPV/r","Second line","adult", "Female", "False"), indParams),"");
		cohortDsd.addColumn("AS5B_F", "ABC+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/ATV/r","Second line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AS5C_F", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/DTG","Second line","adult", "Female", "False"), indParams),"");
		cohortDsd.addColumn("AS6X_F", "Any other second line adult regimens", ReportUtils.map(fmapIndicators.patientsOnOtherRegimen("Other","Second line","adult", "Female", "False"), indParams),"");

		//Adult Third Line
		//Male
		cohortDsd.addColumn("AT2D_M", "TDF+3TC+DTG+DRV+RTV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG/DRV/RTV","Third line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AT2E_M", "TDF+3TC+RAL+DRV+RTV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/RAL/DRV/RTV","Third line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AT2F_M", "TDF+3TC+DTG+ETV+DRV+RTV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG/ETV/DRV/RTV","Third line","adult", "Male", "False"), indParams),"");
		cohortDsd.addColumn("AT2X_M", "Any other thirdline adult regimens", ReportUtils.map(fmapIndicators.patientsOnOtherRegimen("Other","Third line","adult", "Male", "False"), indParams),"");
		//Female
		cohortDsd.addColumn("AT2D_F", "TDF+3TC+DTG+DRV+RTV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG/DRV/RTV","Third line","adult", "Female", "False"), indParams),"");
		cohortDsd.addColumn("AT2E_F", "TDF+3TC+RAL+DRV+RTV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/RAL/DRV/RTV","Third line","adult", "Female", "False"), indParams),"");
		cohortDsd.addColumn("AT2F_F", "TDF+3TC+DTG+ETV+DRV+RTV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG/ETV/DRV/RTV","Third line","adult", "Female", "False"), indParams),"");
		cohortDsd.addColumn("AT2X_F", "Any other thirdline adult regimens", ReportUtils.map(fmapIndicators.patientsOnOtherRegimen("Other","Third line","adult", "Female", "False"), indParams),"");

		//Paediatric First Line
		//Male		
		cohortDsd.addColumn("CF1A_M", "AZT+3TC+NVP", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/NVP","First line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CF1B_M", "AZT+3TC+EFV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/EFV","First line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CF1C_M", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/LPV/r","First line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CF2A_M", "ABC+3TC+NVP", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/NVP","First line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CF2B_M", "ABC+3TC+EFV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/EFV","First line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CF2D_M", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/LPV/r","First line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CF2G_M", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/DTG","First line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CF4E_M", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG","First line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CF5X_M", "Any other first line adult regimens", ReportUtils.map(fmapIndicators.patientsOnOtherRegimen("Other","First line","child", "Male", "False"), indParams),"");

		//Female		
		cohortDsd.addColumn("CF1A_F", "AZT+3TC+NVP", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/NVP","First line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CF1B_F", "AZT+3TC+EFV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/EFV","First line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CF1C_F", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/LPV/r","First line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CF2A_F", "ABC+3TC+NVP", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/NVP","First line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CF2B_F", "ABC+3TC+EFV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/EFV","First line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CF2D_F", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/LPV/r","First line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CF2G_F", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/DTG","First line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CF4E_F", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG","First line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CF5X_F", "Any other first line adult regimens", ReportUtils.map(fmapIndicators.patientsOnOtherRegimen("Other","First line","child", "Female", "False"), indParams),"");


		//Paediatric Second Line
		//Male
		cohortDsd.addColumn("CS1A_M", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/LPV/r","Second line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CS1C_M", "AZT+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/DRV/RTV/RAL","Second line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CS2A_M", "ABC+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/LPV/r","Second line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CS2B_M", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/DTG","Second line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CS2D_M", "ABC+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/DRV/RTV/RAL","Second line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CS4X_M", "Any other second line adult regimens", ReportUtils.map(fmapIndicators.patientsOnOtherRegimen("Other","Second line","child", "Male", "False"), indParams),"");
		//Female
		cohortDsd.addColumn("CS1A_F", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/LPV/r","Second line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CS1C_F", "AZT+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/DRV/RTV/RAL","Second line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CS2A_F", "ABC+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/LPV/r","Second line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CS2B_F", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/DTG","Second line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CS2D_F", "ABC+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/DRV/RTV/RAL","Second line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CS4X_F", "Any other second line adult regimens", ReportUtils.map(fmapIndicators.patientsOnOtherRegimen("Other","Second line","child", "Female", "False"), indParams),"");

		//Paediatric Third Line
		cohortDsd.addColumn("CT1H_M", "AZT+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/DRV/RTV/RAL","Third line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CT2D_M", "ABC+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/DRV/RTV/RAL","Third line","child", "Male", "False"), indParams),"");
		cohortDsd.addColumn("CT3X_M", "Any other thirdline adult regimens", ReportUtils.map(fmapIndicators.patientsOnOtherRegimen("Other","Third line","child", "Male", "False"), indParams),"");

		cohortDsd.addColumn("CT1H_F", "AZT+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/DRV/RTV/RAL","Third line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CT2D_F", "ABC+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/DRV/RTV/RAL","Third line","child", "Female", "False"), indParams),"");
		cohortDsd.addColumn("CT3X_F", "Any other thirdline adult regimens", ReportUtils.map(fmapIndicators.patientsOnOtherRegimen("Other","Third line","child", "Female", "False"), indParams),"");

		//Pregnant women or HEI Mothers
		cohortDsd.addColumn("PM3", "AZT+3TC+NVP", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/NVP","Female", "True"), indParams),"");
		cohortDsd.addColumn("PM4", "AZT+3TC+EFV", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/EFV","Female", "True"), indParams),"");
		cohortDsd.addColumn("PM5", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/LPV/r","Female", "True"), indParams),"");
		cohortDsd.addColumn("PM6", "TDF+3TC+NVP", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/NVP","Female", "True"), indParams),"");
		cohortDsd.addColumn("PM7", "TDF+3TC+LPVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/LPV/r","Female", "True"), indParams),"");
		cohortDsd.addColumn("PM9", "TDF+3TC+EFV", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/EFV","Female", "True"), indParams),"");
		cohortDsd.addColumn("PM10", "AZT+3TC+ATVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/ATV/r","Female", "True"), indParams),"");
		cohortDsd.addColumn("PM11", "TDF+3TC+ATVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/ATV/r","Female", "True"), indParams),"");
		cohortDsd.addColumn("PM12", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/DTG","Female", "True"), indParams),"");
		cohortDsd.addColumn("PM13", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/DTG","Female", "True"), indParams),"");
		cohortDsd.addColumn("PM14", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("ABC/3TC/DTG","Female", "True"), indParams),"");
		cohortDsd.addColumn("PM15", "ABC+3TC+EFV", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("ABC/3TC/EFV","Female", "True"), indParams),"");
		cohortDsd.addColumn("PM1X", "Any other PMTCT regimens for Women", ReportUtils.map(fmapIndicators.pmtctPatientsOnOtherRegimen("Other","Female", "True"), indParams),"");
		
		
		return cohortDsd;
	}

}
