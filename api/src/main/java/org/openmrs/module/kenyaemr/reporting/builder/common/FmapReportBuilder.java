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
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
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
      //Weight categories for pediatrics
	static final String MIN_WEIGHT_3 = ">= 3";
	static final String MAX_WEIGHT_6 = "<= 5.9";
	static final String MIN_WEIGHT_6 = ">= 6";
	static final String MAX_WEIGHT_10 = "<= 9.9";
	static final String MIN_WEIGHT_10 = ">= 10";
	static final String MAX_WEIGHT_14 = "<= 13.9";
	static final String MIN_WEIGHT_14 = ">= 14";
	static final String MAX_WEIGHT_20 = "<= 19.9";
	static final String MIN_WEIGHT_20 = ">= 20";
	static final String MAX_WEIGHT_25 = "<= 24.9";
	static final String MIN_WEIGHT_25 = ">= 25";
	static final String MAX_WEIGHT_30 = "<= 29.9";
	static final String MIN_WEIGHT_30 = ">= 30";
	static final String MAX_WEIGHT_ABOVE_30 = "<= 100";
	


	@Autowired
	private FmapIndicatorLibrary fmapIndicators;

	@Autowired
	private CommonDimensionLibrary commonDimensions;

	ColumnParameters infants_0_to_84= new ColumnParameters(null, "0-84", "age=0-84");
	ColumnParameters infants_84_to_184= new ColumnParameters(null, "85-184", "age=85-184");
	ColumnParameters infants_185_to_270= new ColumnParameters(null, "185-270", "age=185-270");
	ColumnParameters infants_271_to_300= new ColumnParameters(null, "271-300", "age=271-300");
	ColumnParameters infants_301_to_360= new ColumnParameters(null, "301-360", "age=301-360");
	ColumnParameters infants_361_And_Above= new ColumnParameters(null, ">=361", "age=361+");
	List<ColumnParameters> infantsAgeInDaysDisaggregation = Arrays.asList(
		infants_0_to_84,  infants_84_to_184, infants_185_to_270,
		infants_271_to_300, infants_301_to_360 , infants_361_And_Above);
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
	 * Creates the dataset for FMAPS
	 *
	 * @return the dataset
	 */
	protected DataSetDefinition fmapPatientRegimens() {
		CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
		cohortDsd.addDimension("age", ReportUtils.map(commonDimensions.datimFineAgeGroups(), "onDate=${endDate}"));
		cohortDsd.setName("patientRegimens");
		cohortDsd.setDescription("ARV Treatment Regimen");
		cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));

		String indParams = "startDate=${startDate},endDate=${endDate}";
		//Adult First Line
		cohortDsd.addColumn("AF2E", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG","First line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AF2B", "TDF+3TC+EFV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/EFV","First line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AF1D", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/DTG","First line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AF4C", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/DTG","First line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AF2D", "TDF+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/ATV/r","First line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AF2F", "TDF+3TC+LPVr(1L Adults < 40kg)", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/LPV/r","First line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AF1E", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/LPV/r","First line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AF1F", "AZT+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/ATV/r","First line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AF5X", "Any other first line adult regimens", ReportUtils.map(fmapIndicators.patientsOnOtherRegimen("Other","First line","adult", "False"), indParams),"");
		
		//Adult Second Line
		cohortDsd.addColumn("AS1A", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/LPV/r","Second line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AS1B", "AZT+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/ATV/r","Second line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AS1C", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/DTG","Second line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AS2A", "TDF+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/LPV/r","Second line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AS2B", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG","Second line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AS2C", "TDF+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/ATV/r","Second line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AS5A", "ABC+3TC+LPVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/LPV/r","Second line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AS5B", "ABC+3TC+ATVr", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/ATV/r","Second line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AS5C", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("ABC/3TC/DTG","Second line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AS6X", "Any other second line adult regimens", ReportUtils.map(fmapIndicators.patientsOnOtherRegimen("Other","Second line","adult", "False"), indParams),"");
				
		//Adult Third Line
		cohortDsd.addColumn("AT2D", "TDF+3TC+DTG+DRV+RTV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG/DRV/RTV","Third line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AT2F", "TDF+3TC+DTG+ETV+DRV+RTV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG/ETV/DRV/RTV","Third line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AT2G", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("TDF/3TC/DTG","Third line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AT2H", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/DTG","Third line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AT2I", "AZT+3TC+DTG+DRV+RTV", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("AZT/3TC/DTG/DRV/RTV","Third line","adult", "False"), indParams),"");
		cohortDsd.addColumn("AT2X", "Any other thirdline adult regimens", ReportUtils.map(fmapIndicators.patientsOnSpecificRegimen("Other","Third line","adult", "False"), indParams),"");
		
		//Pregnant women or HEI Mothers
		//PMTCT
		cohortDsd.addColumn("PM5_1", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/LPV/r", "First line", "True"), indParams),"");
		cohortDsd.addColumn("PM5_2", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/LPV/r", "Second line","True"), indParams),"");
		cohortDsd.addColumn("PM5_3", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/LPV/r", "Third line","True"), indParams),"");
		
		cohortDsd.addColumn("PM7_1", "TDF+3TC+LPVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/LPV/r", "First line", "True"), indParams),"");
		cohortDsd.addColumn("PM7_2", "TDF+3TC+LPVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/LPV/r", "Second line","True"), indParams),"");
		cohortDsd.addColumn("PM7_3", "TDF+3TC+LPVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/LPV/r", "Third line","True"), indParams),"");

		cohortDsd.addColumn("PM9_1", "TDF+3TC+EFV", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/EFV", "First line","True"), indParams),"");
		cohortDsd.addColumn("PM9_2", "TDF+3TC+EFV", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/EFV", "Second line","True"), indParams),"");
		cohortDsd.addColumn("PM9_3", "TDF+3TC+EFV", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/EFV", "Third line","True"), indParams),"");

		cohortDsd.addColumn("PM10_1", "AZT+3TC+ATVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/ATV/r", "First line","True"), indParams),"");
		cohortDsd.addColumn("PM10_2", "AZT+3TC+ATVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/ATV/r", "Second line","True"), indParams),"");
		cohortDsd.addColumn("PM10_3", "AZT+3TC+ATVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/ATV/r", "Third line","True"), indParams),"");


		cohortDsd.addColumn("PM11_1", "TDF+3TC+ATVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/ATV/r", "First line","True"), indParams),"");
		cohortDsd.addColumn("PM11_2", "TDF+3TC+ATVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/ATV/r", "Second line","True"), indParams),"");
		cohortDsd.addColumn("PM11_3", "TDF+3TC+ATVr", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/ATV/r", "Third line","True"), indParams),"");

		cohortDsd.addColumn("PM12_1", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/DTG", "First line","True"), indParams),"");
		cohortDsd.addColumn("PM12_2", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/DTG", "Second line","True"), indParams),"");
		cohortDsd.addColumn("PM12_3", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("TDF/3TC/DTG", "Third line","True"), indParams),"");

		cohortDsd.addColumn("PM13_1", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/DTG", "First line","True"), indParams),"");
		cohortDsd.addColumn("PM13_2", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/DTG", "Second line","True"), indParams),"");
		cohortDsd.addColumn("PM13_3", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("AZT/3TC/DTG", "Third line","True"), indParams),"");

		cohortDsd.addColumn("PM14_1", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("ABC/3TC/DTG", "First line","True"), indParams),"");
		cohortDsd.addColumn("PM14_2", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("ABC/3TC/DTG", "Second line","True"), indParams),"");
		cohortDsd.addColumn("PM14_3", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.pmtctPatientsRegimen("ABC/3TC/DTG", "Third line","True"), indParams),"");

		cohortDsd.addColumn("PM1X_1", "Any other PMTCT regimens for Women", ReportUtils.map(fmapIndicators.pmtctPatientsOnOtherRegimen("Other", "First line","True"), indParams),"");	
		cohortDsd.addColumn("PM1X_2", "Any other PMTCT regimens for Women", ReportUtils.map(fmapIndicators.pmtctPatientsOnOtherRegimen("Other", "Second line","True"), indParams),"");	
		cohortDsd.addColumn("PM1X_3", "Any other PMTCT regimens for Women", ReportUtils.map(fmapIndicators.pmtctPatientsOnOtherRegimen("Other", "Third line","True"), indParams),"");	
		
		//Paediatric
		//First Line																																																		
		cohortDsd.addColumn("CF2G_W1", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DTG","First line","child", "False", MIN_WEIGHT_3, MAX_WEIGHT_6 ), indParams),"");
		cohortDsd.addColumn("CF2G_W2", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DTG","First line","child", "False", MIN_WEIGHT_6, MAX_WEIGHT_10 ), indParams),"");
		cohortDsd.addColumn("CF2G_W3", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DTG","First line","child", "False", MIN_WEIGHT_10, MAX_WEIGHT_14 ), indParams),"");
		cohortDsd.addColumn("CF2G_W4", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DTG","First line","child", "False", MIN_WEIGHT_14, MAX_WEIGHT_20 ), indParams),"");
		cohortDsd.addColumn("CF2G_W5", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DTG","First line","child", "False", MIN_WEIGHT_20, MAX_WEIGHT_25 ), indParams),"");
		cohortDsd.addColumn("CF2G_W6", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DTG","First line","child", "False", MIN_WEIGHT_25, MAX_WEIGHT_30 ), indParams),"");
		cohortDsd.addColumn("CF2G_W7", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DTG","First line","child", "False", MIN_WEIGHT_30, MAX_WEIGHT_ABOVE_30 ), indParams),"");
		
		cohortDsd.addColumn("CF1D_W1", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DTG","First line","child", "False", MIN_WEIGHT_3, MAX_WEIGHT_6 ), indParams),"");
		cohortDsd.addColumn("CF1D_W2", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DTG","First line","child", "False", MIN_WEIGHT_6, MAX_WEIGHT_10 ), indParams),"");
		cohortDsd.addColumn("CF1D_W3", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DTG","First line","child", "False", MIN_WEIGHT_10, MAX_WEIGHT_14 ), indParams),"");
		cohortDsd.addColumn("CF1D_W4", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DTG","First line","child", "False", MIN_WEIGHT_14, MAX_WEIGHT_20 ), indParams),"");
		cohortDsd.addColumn("CF1D_W5", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DTG","First line","child", "False", MIN_WEIGHT_20, MAX_WEIGHT_25 ), indParams),"");
		cohortDsd.addColumn("CF1D_W6", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DTG","First line","child", "False", MIN_WEIGHT_25, MAX_WEIGHT_30 ), indParams),"");
		cohortDsd.addColumn("CF1D_W7", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DTG","First line","child", "False", MIN_WEIGHT_30, MAX_WEIGHT_ABOVE_30 ), indParams),"");

		cohortDsd.addColumn("CF4E_W1", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("TDF/3TC/DTG","First line","child", "False", MIN_WEIGHT_3, MAX_WEIGHT_6 ), indParams),"");
		cohortDsd.addColumn("CF4E_W2", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("TDF/3TC/DTG","First line","child", "False", MIN_WEIGHT_6, MAX_WEIGHT_10 ), indParams),"");
		cohortDsd.addColumn("CF4E_W3", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("TDF/3TC/DTG","First line","child", "False", MIN_WEIGHT_10, MAX_WEIGHT_14 ), indParams),"");
		cohortDsd.addColumn("CF4E_W4", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("TDF/3TC/DTG","First line","child", "False", MIN_WEIGHT_14, MAX_WEIGHT_20 ), indParams),"");
		cohortDsd.addColumn("CF4E_W5", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("TDF/3TC/DTG","First line","child", "False", MIN_WEIGHT_20, MAX_WEIGHT_25 ), indParams),"");
		cohortDsd.addColumn("CF4E_W6", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("TDF/3TC/DTG","First line","child", "False", MIN_WEIGHT_25, MAX_WEIGHT_30 ), indParams),"");
		cohortDsd.addColumn("CF4E_W7", "TDF+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("TDF/3TC/DTG","First line","child", "False", MIN_WEIGHT_30, MAX_WEIGHT_ABOVE_30 ), indParams),"");

		cohortDsd.addColumn("CF5X_W1", "Any other first line adult regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","First line","child", "False", MIN_WEIGHT_3, MAX_WEIGHT_6), indParams),"");
		cohortDsd.addColumn("CF5X_W2", "Any other first line adult regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","First line","child", "False", MIN_WEIGHT_6, MAX_WEIGHT_10), indParams),"");
		cohortDsd.addColumn("CF5X_W3", "Any other first line adult regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","First line","child", "False", MIN_WEIGHT_10, MAX_WEIGHT_14), indParams),"");
		cohortDsd.addColumn("CF5X_W4", "Any other first line adult regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","First line","child", "False", MIN_WEIGHT_14, MAX_WEIGHT_20), indParams),"");
		cohortDsd.addColumn("CF5X_W5", "Any other first line adult regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","First line","child", "False", MIN_WEIGHT_20, MAX_WEIGHT_25 ), indParams),"");
		cohortDsd.addColumn("CF5X_W6", "Any other first line adult regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","First line","child", "False", MIN_WEIGHT_25, MAX_WEIGHT_30), indParams),"");
		cohortDsd.addColumn("CF5X_W7", "Any other first line adult regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","First line","child", "False", MIN_WEIGHT_30, MAX_WEIGHT_ABOVE_30), indParams),"");

		//Paediatric Second Line		
		cohortDsd.addColumn("CS1A_W1", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/LPV/r","Second line","child", "False", MIN_WEIGHT_3, MAX_WEIGHT_6), indParams),"");
		cohortDsd.addColumn("CS1A_W2", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/LPV/r","Second line","child", "False", MIN_WEIGHT_6, MAX_WEIGHT_10), indParams),"");
		cohortDsd.addColumn("CS1A_W3", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/LPV/r","Second line","child", "False", MIN_WEIGHT_10, MAX_WEIGHT_14), indParams),"");
		cohortDsd.addColumn("CS1A_W4", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/LPV/r","Second line","child", "False", MIN_WEIGHT_14, MAX_WEIGHT_20), indParams),"");
		cohortDsd.addColumn("CS1A_W5", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/LPV/r","Second line","child", "False", MIN_WEIGHT_20, MAX_WEIGHT_25), indParams),"");
		cohortDsd.addColumn("CS1A_W6", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/LPV/r","Second line","child", "False", MIN_WEIGHT_25, MAX_WEIGHT_30), indParams),"");
		cohortDsd.addColumn("CS1A_W7", "AZT+3TC+LPVr", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/LPV/r","Second line","child", "False", MIN_WEIGHT_30, MAX_WEIGHT_ABOVE_30), indParams),"");

		cohortDsd.addColumn("CS1B_W1", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DTG","Second line","child", "False", MIN_WEIGHT_3, MAX_WEIGHT_6 ), indParams),"");
		cohortDsd.addColumn("CS1B_W2", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DTG","Second line","child", "False", MIN_WEIGHT_6, MAX_WEIGHT_10 ), indParams),"");
		cohortDsd.addColumn("CS1B_W3", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DTG","Second line","child", "False", MIN_WEIGHT_10, MAX_WEIGHT_14 ), indParams),"");
		cohortDsd.addColumn("CS1B_W4", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DTG","Second line","child", "False", MIN_WEIGHT_14, MAX_WEIGHT_20), indParams),"");
		cohortDsd.addColumn("CS1B_W5", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DTG","Second line","child", "False", MIN_WEIGHT_20, MAX_WEIGHT_25), indParams),"");
		cohortDsd.addColumn("CS1B_W6", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DTG","Second line","child", "False", MIN_WEIGHT_25, MAX_WEIGHT_30), indParams),"");
		cohortDsd.addColumn("CS1B_W7", "AZT+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DTG","Second line","child", "False", MIN_WEIGHT_30, MAX_WEIGHT_ABOVE_30), indParams),"");

		cohortDsd.addColumn("CS2A_W1", "ABC+3TC+LPVr", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/LPV/r","Second line","child", "False", MIN_WEIGHT_3, MAX_WEIGHT_6), indParams),"");
		cohortDsd.addColumn("CS2A_W2", "ABC+3TC+LPVr", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/LPV/r","Second line","child", "False", MIN_WEIGHT_6, MAX_WEIGHT_10 ), indParams),"");
		cohortDsd.addColumn("CS2A_W3", "ABC+3TC+LPVr", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/LPV/r","Second line","child", "False", MIN_WEIGHT_10, MAX_WEIGHT_14 ), indParams),"");
		cohortDsd.addColumn("CS2A_W4", "ABC+3TC+LPVr", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/LPV/r","Second line","child", "False", MIN_WEIGHT_14, MAX_WEIGHT_20), indParams),"");
		cohortDsd.addColumn("CS2A_W5", "ABC+3TC+LPVr", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/LPV/r","Second line","child", "False", MIN_WEIGHT_20, MAX_WEIGHT_25), indParams),"");
		cohortDsd.addColumn("CS2A_W6", "ABC+3TC+LPVr", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/LPV/r","Second line","child", "False", MIN_WEIGHT_25, MAX_WEIGHT_30), indParams),"");
		cohortDsd.addColumn("CS2A_W7", "ABC+3TC+LPVr", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/LPV/r","Second line","child", "False", MIN_WEIGHT_30, MAX_WEIGHT_ABOVE_30), indParams),"");

		cohortDsd.addColumn("CS2B_W1", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DTG","Second line","child", "False", MIN_WEIGHT_3, MAX_WEIGHT_6), indParams),"");
		cohortDsd.addColumn("CS2B_W2", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DTG","Second line","child", "False", MIN_WEIGHT_6, MAX_WEIGHT_10), indParams),"");
		cohortDsd.addColumn("CS2B_W3", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DTG","Second line","child", "False", MIN_WEIGHT_10, MAX_WEIGHT_14), indParams),"");
		cohortDsd.addColumn("CS2B_W4", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DTG","Second line","child", "False", MIN_WEIGHT_14, MAX_WEIGHT_20), indParams),"");
		cohortDsd.addColumn("CS2B_W5", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DTG","Second line","child", "False", MIN_WEIGHT_20, MAX_WEIGHT_25), indParams),"");
		cohortDsd.addColumn("CS2B_W6", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DTG","Second line","child", "False", MIN_WEIGHT_25, MAX_WEIGHT_30), indParams),"");
		cohortDsd.addColumn("CS2B_W7", "ABC+3TC+DTG", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DTG","Second line","child", "False", MIN_WEIGHT_30, MAX_WEIGHT_ABOVE_30), indParams),"");

		cohortDsd.addColumn("CS4X_W1", "Any other second line adult regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","Second line","child", "False", MIN_WEIGHT_3, MAX_WEIGHT_6), indParams),"");
		cohortDsd.addColumn("CS4X_W2", "Any other second line adult regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","Second line","child", "False", MIN_WEIGHT_6, MAX_WEIGHT_10), indParams),"");
		cohortDsd.addColumn("CS4X_W3", "Any other second line adult regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","Second line","child", "False", MIN_WEIGHT_10, MAX_WEIGHT_14), indParams),"");
		cohortDsd.addColumn("CS4X_W4", "Any other second line adult regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","Second line","child", "False", MIN_WEIGHT_14, MAX_WEIGHT_20), indParams),"");
		cohortDsd.addColumn("CS4X_W5", "Any other second line adult regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","Second line","child", "False", MIN_WEIGHT_20, MAX_WEIGHT_25), indParams),"");
		cohortDsd.addColumn("CS4X_W6", "Any other second line adult regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","Second line","child", "False", MIN_WEIGHT_25, MAX_WEIGHT_30), indParams),"");
		cohortDsd.addColumn("CS4X_W7", "Any other second line adult regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","Second line","child", "False", MIN_WEIGHT_30, MAX_WEIGHT_ABOVE_30), indParams),"");

		//Paediatric Third Line
		cohortDsd.addColumn("CT1H_W1", "AZT+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DRV/RTV/RAL","Third line","child", "False", MIN_WEIGHT_3, MAX_WEIGHT_6), indParams),"");
		cohortDsd.addColumn("CT1H_W2", "AZT+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DRV/RTV/RAL","Third line","child", "False", MIN_WEIGHT_6, MAX_WEIGHT_10), indParams),"");
		cohortDsd.addColumn("CT1H_W3", "AZT+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DRV/RTV/RAL","Third line","child", "False", MIN_WEIGHT_10, MAX_WEIGHT_14), indParams),"");
		cohortDsd.addColumn("CT1H_W4", "AZT+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DRV/RTV/RAL","Third line","child", "False", MIN_WEIGHT_14, MAX_WEIGHT_20), indParams),"");
		cohortDsd.addColumn("CT1H_W5", "AZT+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DRV/RTV/RAL","Third line","child", "False", MIN_WEIGHT_20, MAX_WEIGHT_25), indParams),"");
		cohortDsd.addColumn("CT1H_W6", "AZT+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DRV/RTV/RAL","Third line","child", "False", MIN_WEIGHT_25, MAX_WEIGHT_30), indParams),"");
		cohortDsd.addColumn("CT1H_W7", "AZT+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("AZT/3TC/DRV/RTV/RAL","Third line","child", "False", MIN_WEIGHT_30, MAX_WEIGHT_ABOVE_30), indParams),"");

		cohortDsd.addColumn("CT2D_W1", "ABC+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DRV/RTV/RAL","Third line","child", "False", MIN_WEIGHT_3, MAX_WEIGHT_6), indParams),"");
		cohortDsd.addColumn("CT2D_W2", "ABC+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DRV/RTV/RAL","Third line","child", "False", MIN_WEIGHT_6, MAX_WEIGHT_10), indParams),"");
		cohortDsd.addColumn("CT2D_W3", "ABC+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DRV/RTV/RAL","Third line","child", "False", MIN_WEIGHT_10, MAX_WEIGHT_14), indParams),"");
		cohortDsd.addColumn("CT2D_W4", "ABC+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DRV/RTV/RAL","Third line","child", "False", MIN_WEIGHT_14, MAX_WEIGHT_20), indParams),"");
		cohortDsd.addColumn("CT2D_W5", "ABC+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DRV/RTV/RAL","Third line","child", "False", MIN_WEIGHT_20, MAX_WEIGHT_25), indParams),"");
		cohortDsd.addColumn("CT2D_W6", "ABC+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DRV/RTV/RAL","Third line","child", "False", MIN_WEIGHT_25, MAX_WEIGHT_30), indParams),"");
		cohortDsd.addColumn("CT2D_W7", "ABC+3TC+DRV+RTV+RAL", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("ABC/3TC/DRV/RTV/RAL","Third line","child", "False", MIN_WEIGHT_30, MAX_WEIGHT_ABOVE_30), indParams),"");


		cohortDsd.addColumn("CT3X_W1", "Any other third line pead regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","Third line","child", "False", MIN_WEIGHT_3, MAX_WEIGHT_6), indParams),"");
		cohortDsd.addColumn("CT3X_W2", "Any other third line pead regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","Third line","child", "False", MIN_WEIGHT_6, MAX_WEIGHT_10), indParams),"");
		cohortDsd.addColumn("CT3X_W3", "Any other third line pead regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","Third line","child", "False", MIN_WEIGHT_10, MAX_WEIGHT_14), indParams),"");
		cohortDsd.addColumn("CT3X_W4", "Any other third line pead regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","Third line","child", "False", MIN_WEIGHT_14, MAX_WEIGHT_20), indParams),"");
		cohortDsd.addColumn("CT3X_W5", "Any other third line pead regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","Third line","child", "False", MIN_WEIGHT_20, MAX_WEIGHT_25), indParams),"");
		cohortDsd.addColumn("CT3X_W6", "Any other third line pead regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","Third line","child", "False", MIN_WEIGHT_25, MAX_WEIGHT_30), indParams),"");
		cohortDsd.addColumn("CT3X_W7", "Any other third line pead regimens", ReportUtils.map(fmapIndicators.peadPatientsOnSpecificRegimen("Other","Third line","child", "False", MIN_WEIGHT_30, MAX_WEIGHT_ABOVE_30), indParams),"");
		
		//Infants PC8 : Breastfeeding
		EmrReportingUtils.addRow(cohortDsd, "PC8", "AZT + NVP for 6 weeks then NVP Breastfeeding", ReportUtils.map(fmapIndicators.infantBreastfeedingPC8Regimen(), indParams), infantsAgeInDaysDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06"));
		EmrReportingUtils.addRow(cohortDsd, "PC7", "AZT + NVP for 6 weeks then NVP Not Breastfeeding", ReportUtils.map(fmapIndicators.infantNotBreastfeedingPC7Regimen(), indParams), infantsAgeInDaysDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06"));
		EmrReportingUtils.addRow(cohortDsd, "PC1X", "ANy other regimens for Infants", ReportUtils.map(fmapIndicators.infantAnyOtherRegimenRegimen(), indParams), infantsAgeInDaysDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06"));
		return cohortDsd;
	}

}
