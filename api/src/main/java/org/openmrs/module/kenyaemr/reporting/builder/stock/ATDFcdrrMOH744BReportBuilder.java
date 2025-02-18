/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.builder.stock;

import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Builds({ "kenyaemr.ehrReports.report.assistiveTechnologyFcdrr" })
public class ATDFcdrrMOH744BReportBuilder extends AbstractReportBuilder {

	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), 
			                 new Parameter("endDate", "End Date", Date.class)
			);
	}

	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,
															ReportDefinition reportDefinition) {
		return Arrays.asList(
				ReportUtils.map(getDataSetDefinition("ATD_COM_1A", CommonMetadata._AssistiveTechnology.ATD_COM_1A,CommonMetadata._AssistiveTechnology.COMMUNICATION), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COM_2A", CommonMetadata._AssistiveTechnology.ATD_COM_2A,CommonMetadata._AssistiveTechnology.COMMUNICATION), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COM_3A", CommonMetadata._AssistiveTechnology.ATD_COM_3A,CommonMetadata._AssistiveTechnology.COMMUNICATION), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COM_4A", CommonMetadata._AssistiveTechnology.ATD_COM_4A,CommonMetadata._AssistiveTechnology.COMMUNICATION), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COM_5A", CommonMetadata._AssistiveTechnology.ATD_COM_5A,CommonMetadata._AssistiveTechnology.COMMUNICATION), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COM_7A", CommonMetadata._AssistiveTechnology.ATD_COM_7A,CommonMetadata._AssistiveTechnology.COMMUNICATION), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COM_9A", CommonMetadata._AssistiveTechnology.ATD_COM_9A,CommonMetadata._AssistiveTechnology.COMMUNICATION), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COM_10A", CommonMetadata._AssistiveTechnology.ATD_COM_10A,CommonMetadata._AssistiveTechnology.COMMUNICATION), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COM_11A", CommonMetadata._AssistiveTechnology.ATD_COM_11A,CommonMetadata._AssistiveTechnology.COMMUNICATION), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COM_12A", CommonMetadata._AssistiveTechnology.ATD_COM_12A,CommonMetadata._AssistiveTechnology.COMMUNICATION), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COM_13A", CommonMetadata._AssistiveTechnology.ATD_COM_13A,CommonMetadata._AssistiveTechnology.COMMUNICATION), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COM_14A", CommonMetadata._AssistiveTechnology.ATD_COM_14A,CommonMetadata._AssistiveTechnology.COMMUNICATION), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COM_15A", CommonMetadata._AssistiveTechnology.ATD_COM_15A,CommonMetadata._AssistiveTechnology.COMMUNICATION), "startDate=${startDate},endDate=${endDate}"),
				//Selfcare
				ReportUtils.map(getDataSetDefinition("ATD_SC_1A", CommonMetadata._AssistiveTechnology.ATD_SC_1A,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_2A", CommonMetadata._AssistiveTechnology.ATD_SC_2A,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_3A", CommonMetadata._AssistiveTechnology.ATD_SC_3A,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_3B", CommonMetadata._AssistiveTechnology.ATD_SC_3B,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_3C_HAC", CommonMetadata._AssistiveTechnology.ATD_SC_3C_HAC,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_3C_ASP", CommonMetadata._AssistiveTechnology.ATD_SC_3C_ASP,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_5A_WM", CommonMetadata._AssistiveTechnology.ATD_SC_5A_WM,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_5A_FW", CommonMetadata._AssistiveTechnology.ATD_SC_5A_FW,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_5A_FD", CommonMetadata._AssistiveTechnology.ATD_SC_5A_FD,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_6A", CommonMetadata._AssistiveTechnology.ATD_SC_6A,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_7AA", CommonMetadata._AssistiveTechnology.ATD_SC_7AA,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_7AC", CommonMetadata._AssistiveTechnology.ATD_SC_7AC,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_8A", CommonMetadata._AssistiveTechnology.ATD_SC_8A,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_9A", CommonMetadata._AssistiveTechnology.ATD_SC_9A,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_10A_P1", CommonMetadata._AssistiveTechnology.ATD_SC_10A_P1,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_10A_P2", CommonMetadata._AssistiveTechnology.ATD_SC_10A_P2,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_10A_P3", CommonMetadata._AssistiveTechnology.ATD_SC_10A_P3,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_11A", CommonMetadata._AssistiveTechnology.ATD_SC_11A,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_12A", CommonMetadata._AssistiveTechnology.ATD_SC_12A,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_13A", CommonMetadata._AssistiveTechnology.ATD_SC_13A,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_16ACR", CommonMetadata._AssistiveTechnology.ATD_SC_16ACR,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_16APGR", CommonMetadata._AssistiveTechnology.ATD_SC_16APGR,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_16ARRJ", CommonMetadata._AssistiveTechnology.ATD_SC_16ARRJ,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_16ATD", CommonMetadata._AssistiveTechnology.ATD_SC_16ATD,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_16AWS", CommonMetadata._AssistiveTechnology.ATD_SC_16AWS,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_14AHT", CommonMetadata._AssistiveTechnology.ATD_SC_14AHT,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_14ALT", CommonMetadata._AssistiveTechnology.ATD_SC_14ALT,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_15AHFB", CommonMetadata._AssistiveTechnology.ATD_SC_15AHFB,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_15ALS", CommonMetadata._AssistiveTechnology.ATD_SC_15ALS,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_15ASOS", CommonMetadata._AssistiveTechnology.ATD_SC_15ASOS,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_15APIS", CommonMetadata._AssistiveTechnology.ATD_SC_15APIS,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_15APLS", CommonMetadata._AssistiveTechnology.ATD_SC_15APLS,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_15ASAS", CommonMetadata._AssistiveTechnology.ATD_SC_15ASAS,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_15AMSPS", CommonMetadata._AssistiveTechnology.ATD_SC_15AMSPS,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_15AWBS", CommonMetadata._AssistiveTechnology.ATD_SC_15AWBS,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_SC_17A", CommonMetadata._AssistiveTechnology.ATD_SC_17A,CommonMetadata._AssistiveTechnology.SELF_CARE), "startDate=${startDate},endDate=${endDate}"),

				//Hearing
				ReportUtils.map(getDataSetDefinition("ATD_H_1A", CommonMetadata._AssistiveTechnology.ATD_H_1A,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_1AE", CommonMetadata._AssistiveTechnology.ATD_H_1AE,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_2A", CommonMetadata._AssistiveTechnology.ATD_H_2A,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_3A", CommonMetadata._AssistiveTechnology.ATD_H_3A,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_4A", CommonMetadata._AssistiveTechnology.ATD_H_4A,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_5B", CommonMetadata._AssistiveTechnology.ATD_H_5B,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_5C", CommonMetadata._AssistiveTechnology.ATD_H_5C,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_6A", CommonMetadata._AssistiveTechnology.ATD_H_6A,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_7A", CommonMetadata._AssistiveTechnology.ATD_H_7A,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_8A", CommonMetadata._AssistiveTechnology.ATD_H_8A,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_9A", CommonMetadata._AssistiveTechnology.ATD_H_9A,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_10A", CommonMetadata._AssistiveTechnology.ATD_H_10A,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_11A", CommonMetadata._AssistiveTechnology.ATD_H_11A,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_12A", CommonMetadata._AssistiveTechnology.ATD_H_12A,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_13A", CommonMetadata._AssistiveTechnology.ATD_H_13A,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_14A", CommonMetadata._AssistiveTechnology.ATD_H_14A,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_H_15A", CommonMetadata._AssistiveTechnology.ATD_H_15A,CommonMetadata._AssistiveTechnology.HEARING), "startDate=${startDate},endDate=${endDate}"),

				//Cognitive
				ReportUtils.map(getDataSetDefinition("ATD_COG_1A", CommonMetadata._AssistiveTechnology.ATD_COG_1A,CommonMetadata._AssistiveTechnology.COGNITIVE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COG_2A", CommonMetadata._AssistiveTechnology.ATD_COG_2A,CommonMetadata._AssistiveTechnology.COGNITIVE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COG_3A", CommonMetadata._AssistiveTechnology.ATD_COG_3A,CommonMetadata._AssistiveTechnology.COGNITIVE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COG_3A", CommonMetadata._AssistiveTechnology.ATD_COG_3A,CommonMetadata._AssistiveTechnology.COGNITIVE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COG_4A", CommonMetadata._AssistiveTechnology.ATD_COG_4A,CommonMetadata._AssistiveTechnology.COGNITIVE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COG_5A", CommonMetadata._AssistiveTechnology.ATD_COG_5A,CommonMetadata._AssistiveTechnology.COGNITIVE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COG_6A", CommonMetadata._AssistiveTechnology.ATD_COG_6A,CommonMetadata._AssistiveTechnology.COGNITIVE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COG_6B", CommonMetadata._AssistiveTechnology.ATD_COG_6B,CommonMetadata._AssistiveTechnology.COGNITIVE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COG_6C", CommonMetadata._AssistiveTechnology.ATD_COG_6C,CommonMetadata._AssistiveTechnology.COGNITIVE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COG_7A", CommonMetadata._AssistiveTechnology.ATD_COG_7A,CommonMetadata._AssistiveTechnology.COGNITIVE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COG_8A", CommonMetadata._AssistiveTechnology.ATD_COG_8A,CommonMetadata._AssistiveTechnology.COGNITIVE), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_COG_9A", CommonMetadata._AssistiveTechnology.ATD_COG_9A,CommonMetadata._AssistiveTechnology.COGNITIVE), "startDate=${startDate},endDate=${endDate}"),

				//Physical
				ReportUtils.map(getDataSetDefinition("ATD_PHY_1A", CommonMetadata._AssistiveTechnology.ATD_PHY_1A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_1B", CommonMetadata._AssistiveTechnology.ATD_PHY_1B,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_1C", CommonMetadata._AssistiveTechnology.ATD_PHY_1C,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_1D", CommonMetadata._AssistiveTechnology.ATD_PHY_1D,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_2A", CommonMetadata._AssistiveTechnology.ATD_PHY_2A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_2B", CommonMetadata._AssistiveTechnology.ATD_PHY_2B,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_1E", CommonMetadata._AssistiveTechnology.ATD_PHY_1E,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_1F", CommonMetadata._AssistiveTechnology.ATD_PHY_1F,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_3A", CommonMetadata._AssistiveTechnology.ATD_PHY_3A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_4A", CommonMetadata._AssistiveTechnology.ATD_PHY_4A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_4B", CommonMetadata._AssistiveTechnology.ATD_PHY_4B,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_5A", CommonMetadata._AssistiveTechnology.ATD_PHY_5A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_6AC", CommonMetadata._AssistiveTechnology.ATD_PHY_6AC,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_6AY", CommonMetadata._AssistiveTechnology.ATD_PHY_6AY,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_6AAM", CommonMetadata._AssistiveTechnology.ATD_PHY_6AAM,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_6AAT", CommonMetadata._AssistiveTechnology.ATD_PHY_6AAT,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_7A", CommonMetadata._AssistiveTechnology.ATD_PHY_7A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_7B", CommonMetadata._AssistiveTechnology.ATD_PHY_7B,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_8A", CommonMetadata._AssistiveTechnology.ATD_PHY_8A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_8B", CommonMetadata._AssistiveTechnology.ATD_PHY_8B,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_9AS", CommonMetadata._AssistiveTechnology.ATD_PHY_9AS,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_9AM", CommonMetadata._AssistiveTechnology.ATD_PHY_9AM,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_9AL", CommonMetadata._AssistiveTechnology.ATD_PHY_9AL,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_9B", CommonMetadata._AssistiveTechnology.ATD_PHY_9B,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_9BA", CommonMetadata._AssistiveTechnology.ATD_PHY_9BA,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_9C", CommonMetadata._AssistiveTechnology.ATD_PHY_9C,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_9D", CommonMetadata._AssistiveTechnology.ATD_PHY_9D,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10A", CommonMetadata._AssistiveTechnology.ATD_PHY_10A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10B", CommonMetadata._AssistiveTechnology.ATD_PHY_10B,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10C", CommonMetadata._AssistiveTechnology.ATD_PHY_10C,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10AAK", CommonMetadata._AssistiveTechnology.ATD_PHY_10AAK,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10ABK", CommonMetadata._AssistiveTechnology.ATD_PHY_10ABK,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10BAK", CommonMetadata._AssistiveTechnology.ATD_PHY_10BAK,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10BBK", CommonMetadata._AssistiveTechnology.ATD_PHY_10BBK,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10ACSS", CommonMetadata._AssistiveTechnology.ATD_PHY_10ACSS,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10ACSM", CommonMetadata._AssistiveTechnology.ATD_PHY_10ACSM,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10ACSL", CommonMetadata._AssistiveTechnology.ATD_PHY_10ACSL,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10ASM", CommonMetadata._AssistiveTechnology.ATD_PHY_10ASM,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10AMD", CommonMetadata._AssistiveTechnology.ATD_PHY_10AMD,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10ALG", CommonMetadata._AssistiveTechnology.ATD_PHY_10ALG,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10AXL", CommonMetadata._AssistiveTechnology.ATD_PHY_10AXL,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_10AXXL", CommonMetadata._AssistiveTechnology.ATD_PHY_10AXXL,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_13A", CommonMetadata._AssistiveTechnology.ATD_PHY_13A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_14AU", CommonMetadata._AssistiveTechnology.ATD_PHY_14AU,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_14AXL", CommonMetadata._AssistiveTechnology.ATD_PHY_14AXL,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_15A", CommonMetadata._AssistiveTechnology.ATD_PHY_15A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_16A", CommonMetadata._AssistiveTechnology.ATD_PHY_16A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_17A", CommonMetadata._AssistiveTechnology.ATD_PHY_17A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_18AP", CommonMetadata._AssistiveTechnology.ATD_PHY_18AP,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_18AA", CommonMetadata._AssistiveTechnology.ATD_PHY_18AA,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_19AP", CommonMetadata._AssistiveTechnology.ATD_PHY_19AP,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_19AA", CommonMetadata._AssistiveTechnology.ATD_PHY_19AA,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_20A", CommonMetadata._AssistiveTechnology.ATD_PHY_20A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_21A", CommonMetadata._AssistiveTechnology.ATD_PHY_21A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_21B", CommonMetadata._AssistiveTechnology.ATD_PHY_21B,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_22B", CommonMetadata._AssistiveTechnology.ATD_PHY_22B,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_23A1", CommonMetadata._AssistiveTechnology.ATD_PHY_23A1,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_23B32", CommonMetadata._AssistiveTechnology.ATD_PHY_23B32,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_23B45", CommonMetadata._AssistiveTechnology.ATD_PHY_23B45,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_23B60", CommonMetadata._AssistiveTechnology.ATD_PHY_23B60,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_23B75", CommonMetadata._AssistiveTechnology.ATD_PHY_23B75,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_24AP", CommonMetadata._AssistiveTechnology.ATD_PHY_24AP,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_25AA", CommonMetadata._AssistiveTechnology.ATD_PHY_25AA,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_26AAI", CommonMetadata._AssistiveTechnology.ATD_PHY_26AAI,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_26AST", CommonMetadata._AssistiveTechnology.ATD_PHY_26AST,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_26ATI", CommonMetadata._AssistiveTechnology.ATD_PHY_26ATI,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_26B", CommonMetadata._AssistiveTechnology.ATD_PHY_26B,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_26C", CommonMetadata._AssistiveTechnology.ATD_PHY_26C,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_27AS", CommonMetadata._AssistiveTechnology.ATD_PHY_27AS,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_27AL", CommonMetadata._AssistiveTechnology.ATD_PHY_27AL,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_28A", CommonMetadata._AssistiveTechnology.ATD_PHY_28A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_29A", CommonMetadata._AssistiveTechnology.ATD_PHY_29A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_30A1", CommonMetadata._AssistiveTechnology.ATD_PHY_30A1,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_30A2", CommonMetadata._AssistiveTechnology.ATD_PHY_30A2,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_31A", CommonMetadata._AssistiveTechnology.ATD_PHY_31A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_32A", CommonMetadata._AssistiveTechnology.ATD_PHY_32A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_32B", CommonMetadata._AssistiveTechnology.ATD_PHY_32B,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_32C", CommonMetadata._AssistiveTechnology.ATD_PHY_32C,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_33A", CommonMetadata._AssistiveTechnology.ATD_PHY_33A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_34A", CommonMetadata._AssistiveTechnology.ATD_PHY_34A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_37A230", CommonMetadata._AssistiveTechnology.ATD_PHY_37A230,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_37A265", CommonMetadata._AssistiveTechnology.ATD_PHY_37A265,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_37A280", CommonMetadata._AssistiveTechnology.ATD_PHY_37A280,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_37A340", CommonMetadata._AssistiveTechnology.ATD_PHY_37A340,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_37A400", CommonMetadata._AssistiveTechnology.ATD_PHY_37A400,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_37A470", CommonMetadata._AssistiveTechnology.ATD_PHY_37A470,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_37B16", CommonMetadata._AssistiveTechnology.ATD_PHY_37B16,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_37B20", CommonMetadata._AssistiveTechnology.ATD_PHY_37B20,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_37B24", CommonMetadata._AssistiveTechnology.ATD_PHY_37B24,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_37B26", CommonMetadata._AssistiveTechnology.ATD_PHY_37B26,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_37B28", CommonMetadata._AssistiveTechnology.ATD_PHY_37B28,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_37B32", CommonMetadata._AssistiveTechnology.ATD_PHY_37B32,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_37B38", CommonMetadata._AssistiveTechnology.ATD_PHY_37B38,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_37B44", CommonMetadata._AssistiveTechnology.ATD_PHY_37B44,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_38A", CommonMetadata._AssistiveTechnology.ATD_PHY_38A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_39A", CommonMetadata._AssistiveTechnology.ATD_PHY_39A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_40A", CommonMetadata._AssistiveTechnology.ATD_PHY_40A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_41AA", CommonMetadata._AssistiveTechnology.ATD_PHY_41AA,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_41AP", CommonMetadata._AssistiveTechnology.ATD_PHY_41AP,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_43AP", CommonMetadata._AssistiveTechnology.ATD_PHY_43AP,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_43AA", CommonMetadata._AssistiveTechnology.ATD_PHY_43AA,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_44A3", CommonMetadata._AssistiveTechnology.ATD_PHY_44A3,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_44A4", CommonMetadata._AssistiveTechnology.ATD_PHY_44A4,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_44A7", CommonMetadata._AssistiveTechnology.ATD_PHY_44A7,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_45A", CommonMetadata._AssistiveTechnology.ATD_PHY_45A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_46A", CommonMetadata._AssistiveTechnology.ATD_PHY_46A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_47A", CommonMetadata._AssistiveTechnology.ATD_PHY_47A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_48A2", CommonMetadata._AssistiveTechnology.ATD_PHY_48A2,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_48A3", CommonMetadata._AssistiveTechnology.ATD_PHY_48A3,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_48A4", CommonMetadata._AssistiveTechnology.ATD_PHY_48A4,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_48A5", CommonMetadata._AssistiveTechnology.ATD_PHY_48A5,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_48A6", CommonMetadata._AssistiveTechnology.ATD_PHY_48A6,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_49A2", CommonMetadata._AssistiveTechnology.ATD_PHY_49A2,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_49A3", CommonMetadata._AssistiveTechnology.ATD_PHY_49A3,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_49A4", CommonMetadata._AssistiveTechnology.ATD_PHY_49A4,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_49A5", CommonMetadata._AssistiveTechnology.ATD_PHY_49A5,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_49A6", CommonMetadata._AssistiveTechnology.ATD_PHY_49A6,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_50A50", CommonMetadata._AssistiveTechnology.ATD_PHY_50A50,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_50A25", CommonMetadata._AssistiveTechnology.ATD_PHY_50A25,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_51A", CommonMetadata._AssistiveTechnology.ATD_PHY_51A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_52A4", CommonMetadata._AssistiveTechnology.ATD_PHY_52A4,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_52A6", CommonMetadata._AssistiveTechnology.ATD_PHY_52A6,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_52A8", CommonMetadata._AssistiveTechnology.ATD_PHY_52A8,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_53AATI", CommonMetadata._AssistiveTechnology.ATD_PHY_53AATI,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_53BPTI", CommonMetadata._AssistiveTechnology.ATD_PHY_53BPTI,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_53BPST", CommonMetadata._AssistiveTechnology.ATD_PHY_53BPST,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_53AAST", CommonMetadata._AssistiveTechnology.ATD_PHY_53AAST,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_55A", CommonMetadata._AssistiveTechnology.ATD_PHY_55A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_56APS", CommonMetadata._AssistiveTechnology.ATD_PHY_56APS,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_56APM", CommonMetadata._AssistiveTechnology.ATD_PHY_56APM,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_56APL", CommonMetadata._AssistiveTechnology.ATD_PHY_56APL,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_56AAS", CommonMetadata._AssistiveTechnology.ATD_PHY_56AAS,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_56AAM", CommonMetadata._AssistiveTechnology.ATD_PHY_56AAM,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_56AAL", CommonMetadata._AssistiveTechnology.ATD_PHY_56AAL,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_57A", CommonMetadata._AssistiveTechnology.ATD_PHY_57A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_58AS", CommonMetadata._AssistiveTechnology.ATD_PHY_58AS,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_58AL", CommonMetadata._AssistiveTechnology.ATD_PHY_58AL,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_59AH", CommonMetadata._AssistiveTechnology.ATD_PHY_59AH,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_59AS", CommonMetadata._AssistiveTechnology.ATD_PHY_59AS,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_60A", CommonMetadata._AssistiveTechnology.ATD_PHY_60A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_61A", CommonMetadata._AssistiveTechnology.ATD_PHY_61A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_62A", CommonMetadata._AssistiveTechnology.ATD_PHY_62A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_63A", CommonMetadata._AssistiveTechnology.ATD_PHY_63A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_64A", CommonMetadata._AssistiveTechnology.ATD_PHY_64A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_65A", CommonMetadata._AssistiveTechnology.ATD_PHY_65A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_66A", CommonMetadata._AssistiveTechnology.ATD_PHY_66A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_67A", CommonMetadata._AssistiveTechnology.ATD_PHY_67A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_68A", CommonMetadata._AssistiveTechnology.ATD_PHY_68A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_69A", CommonMetadata._AssistiveTechnology.ATD_PHY_69A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_70A", CommonMetadata._AssistiveTechnology.ATD_PHY_70A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_71A", CommonMetadata._AssistiveTechnology.ATD_PHY_71A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_72A", CommonMetadata._AssistiveTechnology.ATD_PHY_72A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_73A", CommonMetadata._AssistiveTechnology.ATD_PHY_73A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_74A", CommonMetadata._AssistiveTechnology.ATD_PHY_74A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_76A", CommonMetadata._AssistiveTechnology.ATD_PHY_76A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_77A", CommonMetadata._AssistiveTechnology.ATD_PHY_77A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_78AS", CommonMetadata._AssistiveTechnology.ATD_PHY_78AS,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_78AM", CommonMetadata._AssistiveTechnology.ATD_PHY_78AM,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_78AL", CommonMetadata._AssistiveTechnology.ATD_PHY_78AL,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_79A", CommonMetadata._AssistiveTechnology.ATD_PHY_79A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_80A", CommonMetadata._AssistiveTechnology.ATD_PHY_80A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_81A", CommonMetadata._AssistiveTechnology.ATD_PHY_81A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_82A1", CommonMetadata._AssistiveTechnology.ATD_PHY_82A1,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_82A5", CommonMetadata._AssistiveTechnology.ATD_PHY_82A5,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_83A1", CommonMetadata._AssistiveTechnology.ATD_PHY_83A1,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_83A5", CommonMetadata._AssistiveTechnology.ATD_PHY_83A5,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_84A316", CommonMetadata._AssistiveTechnology.ATD_PHY_84A316,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_84A318", CommonMetadata._AssistiveTechnology.ATD_PHY_84A318,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_85A", CommonMetadata._AssistiveTechnology.ATD_PHY_85A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_86A14", CommonMetadata._AssistiveTechnology.ATD_PHY_86A14,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_86A16", CommonMetadata._AssistiveTechnology.ATD_PHY_86A16,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_86A20", CommonMetadata._AssistiveTechnology.ATD_PHY_86A20,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_86A22", CommonMetadata._AssistiveTechnology.ATD_PHY_86A22,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_87A6", CommonMetadata._AssistiveTechnology.ATD_PHY_87A6,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_87A12", CommonMetadata._AssistiveTechnology.ATD_PHY_87A12,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_88A12", CommonMetadata._AssistiveTechnology.ATD_PHY_88A12,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_88A34", CommonMetadata._AssistiveTechnology.ATD_PHY_88A34,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_89A", CommonMetadata._AssistiveTechnology.ATD_PHY_89A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_90A", CommonMetadata._AssistiveTechnology.ATD_PHY_90A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_91A", CommonMetadata._AssistiveTechnology.ATD_PHY_91A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_92A", CommonMetadata._AssistiveTechnology.ATD_PHY_92A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_93A", CommonMetadata._AssistiveTechnology.ATD_PHY_93A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_94A1", CommonMetadata._AssistiveTechnology.ATD_PHY_94A1,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_94A2", CommonMetadata._AssistiveTechnology.ATD_PHY_94A2,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_94A3", CommonMetadata._AssistiveTechnology.ATD_PHY_94A3,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_PHY_95A", CommonMetadata._AssistiveTechnology.ATD_PHY_95A,CommonMetadata._AssistiveTechnology.PHYSICAL), "startDate=${startDate},endDate=${endDate}"),
				//Visual

				ReportUtils.map(getDataSetDefinition("ATD_VIS_1A", CommonMetadata._AssistiveTechnology.ATD_VIS_1A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_2A", CommonMetadata._AssistiveTechnology.ATD_VIS_2A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_3A", CommonMetadata._AssistiveTechnology.ATD_VIS_3A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_4A", CommonMetadata._AssistiveTechnology.ATD_VIS_4A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_5A", CommonMetadata._AssistiveTechnology.ATD_VIS_5A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_6A", CommonMetadata._AssistiveTechnology.ATD_VIS_6A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_7A", CommonMetadata._AssistiveTechnology.ATD_VIS_7A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_8A", CommonMetadata._AssistiveTechnology.ATD_VIS_8A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_9A", CommonMetadata._AssistiveTechnology.ATD_VIS_9A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_10A", CommonMetadata._AssistiveTechnology.ATD_VIS_10A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_11A", CommonMetadata._AssistiveTechnology.ATD_VIS_11A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_12A", CommonMetadata._AssistiveTechnology.ATD_VIS_12A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_13A", CommonMetadata._AssistiveTechnology.ATD_VIS_13A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_14A", CommonMetadata._AssistiveTechnology.ATD_VIS_14A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_15A", CommonMetadata._AssistiveTechnology.ATD_VIS_15A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_16A", CommonMetadata._AssistiveTechnology.ATD_VIS_16A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_17A", CommonMetadata._AssistiveTechnology.ATD_VIS_17A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_18A", CommonMetadata._AssistiveTechnology.ATD_VIS_18A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_VIS_19A", CommonMetadata._AssistiveTechnology.ATD_VIS_19A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_LEN_1A", CommonMetadata._AssistiveTechnology.ATD_LEN_1A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_LEN_2A", CommonMetadata._AssistiveTechnology.ATD_LEN_2A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_LEN_3A", CommonMetadata._AssistiveTechnology.ATD_LEN_3A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_LEN_4A", CommonMetadata._AssistiveTechnology.ATD_LEN_4A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_LEN_5A", CommonMetadata._AssistiveTechnology.ATD_LEN_5A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_LEN_6A", CommonMetadata._AssistiveTechnology.ATD_LEN_6A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_LEN_7A", CommonMetadata._AssistiveTechnology.ATD_LEN_7A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_LEN_8A", CommonMetadata._AssistiveTechnology.ATD_LEN_8A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_LEN_9A", CommonMetadata._AssistiveTechnology.ATD_LEN_9A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_LEN_10A", CommonMetadata._AssistiveTechnology.ATD_LEN_10A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_LEN_11A", CommonMetadata._AssistiveTechnology.ATD_LEN_11A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_LEN_12A", CommonMetadata._AssistiveTechnology.ATD_LEN_12A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_LEN_13A", CommonMetadata._AssistiveTechnology.ATD_LEN_13A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_OPT_1A", CommonMetadata._AssistiveTechnology.ATD_OPT_1A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_OPT_2A", CommonMetadata._AssistiveTechnology.ATD_OPT_2A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_OPT_3A", CommonMetadata._AssistiveTechnology.ATD_OPT_3A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_OPT_4A", CommonMetadata._AssistiveTechnology.ATD_OPT_4A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_OPT_5A", CommonMetadata._AssistiveTechnology.ATD_OPT_5A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_OPT_6A", CommonMetadata._AssistiveTechnology.ATD_OPT_6A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_OPT_7A", CommonMetadata._AssistiveTechnology.ATD_OPT_7A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_1A", CommonMetadata._AssistiveTechnology.ATD_FRM_1A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_2A", CommonMetadata._AssistiveTechnology.ATD_FRM_2A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_3A", CommonMetadata._AssistiveTechnology.ATD_FRM_3A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_4A", CommonMetadata._AssistiveTechnology.ATD_FRM_4A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_5A", CommonMetadata._AssistiveTechnology.ATD_FRM_5A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_6A", CommonMetadata._AssistiveTechnology.ATD_FRM_6A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_7A", CommonMetadata._AssistiveTechnology.ATD_FRM_7A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_8A", CommonMetadata._AssistiveTechnology.ATD_FRM_8A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_9A", CommonMetadata._AssistiveTechnology.ATD_FRM_9A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_10A", CommonMetadata._AssistiveTechnology.ATD_FRM_10A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_11A", CommonMetadata._AssistiveTechnology.ATD_FRM_11A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_12A", CommonMetadata._AssistiveTechnology.ATD_FRM_12A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_13A", CommonMetadata._AssistiveTechnology.ATD_FRM_13A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_14A", CommonMetadata._AssistiveTechnology.ATD_FRM_14A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_15A", CommonMetadata._AssistiveTechnology.ATD_FRM_15A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_16A", CommonMetadata._AssistiveTechnology.ATD_FRM_16A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_17A", CommonMetadata._AssistiveTechnology.ATD_FRM_17A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_18A", CommonMetadata._AssistiveTechnology.ATD_FRM_18A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_19A", CommonMetadata._AssistiveTechnology.ATD_FRM_19A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_20A", CommonMetadata._AssistiveTechnology.ATD_FRM_20A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_21A", CommonMetadata._AssistiveTechnology.ATD_FRM_21A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_22A", CommonMetadata._AssistiveTechnology.ATD_FRM_22A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_23A", CommonMetadata._AssistiveTechnology.ATD_FRM_23A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_24A", CommonMetadata._AssistiveTechnology.ATD_FRM_24A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_25A", CommonMetadata._AssistiveTechnology.ATD_FRM_25A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_26A", CommonMetadata._AssistiveTechnology.ATD_FRM_26A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_27A", CommonMetadata._AssistiveTechnology.ATD_FRM_27A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_28A", CommonMetadata._AssistiveTechnology.ATD_FRM_28A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_29A", CommonMetadata._AssistiveTechnology.ATD_FRM_29A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_30A", CommonMetadata._AssistiveTechnology.ATD_FRM_30A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_31A", CommonMetadata._AssistiveTechnology.ATD_FRM_31A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_32A", CommonMetadata._AssistiveTechnology.ATD_FRM_32A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_33A", CommonMetadata._AssistiveTechnology.ATD_FRM_33A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_34A", CommonMetadata._AssistiveTechnology.ATD_FRM_34A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_35A", CommonMetadata._AssistiveTechnology.ATD_FRM_35A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_36A", CommonMetadata._AssistiveTechnology.ATD_FRM_36A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_37A", CommonMetadata._AssistiveTechnology.ATD_FRM_37A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_38A", CommonMetadata._AssistiveTechnology.ATD_FRM_38A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_39A", CommonMetadata._AssistiveTechnology.ATD_FRM_39A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_40A", CommonMetadata._AssistiveTechnology.ATD_FRM_40A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_41A", CommonMetadata._AssistiveTechnology.ATD_FRM_41A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_42A", CommonMetadata._AssistiveTechnology.ATD_FRM_42A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_43A", CommonMetadata._AssistiveTechnology.ATD_FRM_43A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_44A", CommonMetadata._AssistiveTechnology.ATD_FRM_44A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_45A", CommonMetadata._AssistiveTechnology.ATD_FRM_45A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_46A", CommonMetadata._AssistiveTechnology.ATD_FRM_46A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_47A", CommonMetadata._AssistiveTechnology.ATD_FRM_47A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_48A", CommonMetadata._AssistiveTechnology.ATD_FRM_48A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_49A", CommonMetadata._AssistiveTechnology.ATD_FRM_49A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_50A", CommonMetadata._AssistiveTechnology.ATD_FRM_50A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_51A", CommonMetadata._AssistiveTechnology.ATD_FRM_51A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_52A", CommonMetadata._AssistiveTechnology.ATD_FRM_52A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_53A", CommonMetadata._AssistiveTechnology.ATD_FRM_53A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_54A", CommonMetadata._AssistiveTechnology.ATD_FRM_54A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_55A", CommonMetadata._AssistiveTechnology.ATD_FRM_55A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_56A", CommonMetadata._AssistiveTechnology.ATD_FRM_56A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_57A", CommonMetadata._AssistiveTechnology.ATD_FRM_57A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_58A", CommonMetadata._AssistiveTechnology.ATD_FRM_58A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_59A", CommonMetadata._AssistiveTechnology.ATD_FRM_59A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_60A", CommonMetadata._AssistiveTechnology.ATD_FRM_60A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_61A", CommonMetadata._AssistiveTechnology.ATD_FRM_61A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_62A", CommonMetadata._AssistiveTechnology.ATD_FRM_62A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_63A", CommonMetadata._AssistiveTechnology.ATD_FRM_63A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_64A", CommonMetadata._AssistiveTechnology.ATD_FRM_64A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_65A", CommonMetadata._AssistiveTechnology.ATD_FRM_65A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_66A", CommonMetadata._AssistiveTechnology.ATD_FRM_66A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_67A", CommonMetadata._AssistiveTechnology.ATD_FRM_67A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_68A", CommonMetadata._AssistiveTechnology.ATD_FRM_68A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_69A", CommonMetadata._AssistiveTechnology.ATD_FRM_69A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_70A", CommonMetadata._AssistiveTechnology.ATD_FRM_70A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_71A", CommonMetadata._AssistiveTechnology.ATD_FRM_71A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_FRM_72A", CommonMetadata._AssistiveTechnology.ATD_FRM_72A,CommonMetadata._AssistiveTechnology.VISUAL), "startDate=${startDate},endDate=${endDate}"),

				//INCLUSIVE THERAPEUTIC PLAY
				ReportUtils.map(getDataSetDefinition("ATD_ITP_1A", CommonMetadata._AssistiveTechnology.ATD_ITP_1A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_2A", CommonMetadata._AssistiveTechnology.ATD_ITP_2A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_3A", CommonMetadata._AssistiveTechnology.ATD_ITP_3A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_4A", CommonMetadata._AssistiveTechnology.ATD_ITP_4A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_5A", CommonMetadata._AssistiveTechnology.ATD_ITP_5A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_6A", CommonMetadata._AssistiveTechnology.ATD_ITP_6A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_7A", CommonMetadata._AssistiveTechnology.ATD_ITP_7A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_8A", CommonMetadata._AssistiveTechnology.ATD_ITP_8A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_9A", CommonMetadata._AssistiveTechnology.ATD_ITP_9A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_10A", CommonMetadata._AssistiveTechnology.ATD_ITP_10A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_11A", CommonMetadata._AssistiveTechnology.ATD_ITP_11A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_11B", CommonMetadata._AssistiveTechnology.ATD_ITP_11B,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_11C", CommonMetadata._AssistiveTechnology.ATD_ITP_11C,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_11D", CommonMetadata._AssistiveTechnology.ATD_ITP_11D,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_11E", CommonMetadata._AssistiveTechnology.ATD_ITP_11E,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_11F", CommonMetadata._AssistiveTechnology.ATD_ITP_11F,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_12A", CommonMetadata._AssistiveTechnology.ATD_ITP_12A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_12B", CommonMetadata._AssistiveTechnology.ATD_ITP_12B,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_13A", CommonMetadata._AssistiveTechnology.ATD_ITP_13A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_14A", CommonMetadata._AssistiveTechnology.ATD_ITP_14A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_14B", CommonMetadata._AssistiveTechnology.ATD_ITP_14B,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_15A", CommonMetadata._AssistiveTechnology.ATD_ITP_15A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_16A", CommonMetadata._AssistiveTechnology.ATD_ITP_16A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_17A", CommonMetadata._AssistiveTechnology.ATD_ITP_17A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_19A", CommonMetadata._AssistiveTechnology.ATD_ITP_19A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_20A", CommonMetadata._AssistiveTechnology.ATD_ITP_20A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_21A", CommonMetadata._AssistiveTechnology.ATD_ITP_21A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_22A", CommonMetadata._AssistiveTechnology.ATD_ITP_22A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_23A", CommonMetadata._AssistiveTechnology.ATD_ITP_23A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_24A", CommonMetadata._AssistiveTechnology.ATD_ITP_24A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_25A", CommonMetadata._AssistiveTechnology.ATD_ITP_25A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_26A", CommonMetadata._AssistiveTechnology.ATD_ITP_26A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_27A", CommonMetadata._AssistiveTechnology.ATD_ITP_27A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_28A", CommonMetadata._AssistiveTechnology.ATD_ITP_28A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_ITP_29A", CommonMetadata._AssistiveTechnology.ATD_ITP_29A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPN1A", CommonMetadata._AssistiveTechnology.ATD_IPN1A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPN2A", CommonMetadata._AssistiveTechnology.ATD_IPN2A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPN3A", CommonMetadata._AssistiveTechnology.ATD_IPN3A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPN4A", CommonMetadata._AssistiveTechnology.ATD_IPN4A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPN5A", CommonMetadata._AssistiveTechnology.ATD_IPN5A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPN6A", CommonMetadata._AssistiveTechnology.ATD_IPN6A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPV1A", CommonMetadata._AssistiveTechnology.ATD_IPV1A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPV2A", CommonMetadata._AssistiveTechnology.ATD_IPV2A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPV3A", CommonMetadata._AssistiveTechnology.ATD_IPV3A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPV4A", CommonMetadata._AssistiveTechnology.ATD_IPV4A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPV5A", CommonMetadata._AssistiveTechnology.ATD_IPV5A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPV6A", CommonMetadata._AssistiveTechnology.ATD_IPV6A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPV7A", CommonMetadata._AssistiveTechnology.ATD_IPV7A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPV8A", CommonMetadata._AssistiveTechnology.ATD_IPV8A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPV9A", CommonMetadata._AssistiveTechnology.ATD_IPV9A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPV10A", CommonMetadata._AssistiveTechnology.ATD_IPV10A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ATD_IPV11A", CommonMetadata._AssistiveTechnology.ATD_IPV11A,CommonMetadata._AssistiveTechnology.INCLUSIVE_THERAPEUTIC), "startDate=${startDate},endDate=${endDate}")
		);

	}

	private DataSetDefinition getDataSetDefinition(String label, int drugId, int assistiveTechnology) {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName(label);
		sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		sqlDataSetDefinition.setSqlQuery(getFcdrrDrugSummary(drugId,assistiveTechnology));
		return sqlDataSetDefinition;
	}


	private String getFcdrrDrugSummary(int drugId, int assistiveTechnology) {

		String query =	"select ifnull(sspu.factor,0) as unit_pack_size,\n" +
				"      ifnull(SUM(stit.quantity),0)  +   ifnull(SUM(rc.quantity),0) - ifnull(SUM(si.quantity),0) as opening_balance,\n" +
				"      ifnull(rc_curr.quantity,0) as curr_receipts,\n" +
				"      ifnull(dis_curr.quantity,0) as curr_dispensed,\n" +
				"      ifnull(curr_loss.quantity,0) as curr_loss,\n" +
				"      ifnull(pos_adj.quantity,0) as pos_adj,\n" +
				"      ifnull(neg_adj.quantity,0) as neg_adj,\n" +
				"      ifnull(stck_take.quantity,0) as stck_take,\n" +
				"      ifnull(early_exp.quantity,0) as earliest_expiry_quantity,\n" +
				"      ifnull(early_exp.earliest_expiry_date,0) as earliest_expiry_date,\n" +
				"      ifnull(cur_req.quantity,0) as curr_requested\n" +
				"#Unit pack size\n" +
				"     from stockmgmt_stock_item_transaction stit\n" +
				"              inner join stockmgmt_stock_item ssi on (ssi.stock_item_id = stit.stock_item_id) and ssi.drug_id =%d\n" +
				"              inner join stockmgmt_stock_item_packaging_uom sspu on sspu.stock_item_id = stit.stock_item_id\n" +
				"              left join stockmgmt_stock_operation ssto on stit.stock_operation_id = ssto.operation_type_id and ssto.status = 'COMPLETED'\n" +
				"                         and ssto.operation_type_id in (4, 9) and stit.date_created between date_sub(date(:startDate) , interval 1 MONTH) and date_sub(date(:endDate) , interval 1 MONTH)\n" +
				"        left join (\n" +
				"# Previous Receipt\n" +
				"   select SUM(stit.quantity) as quantity, stit.stock_item_id\n" +
				"   from stockmgmt_stock_item_transaction stit\n" +
				"            inner join stockmgmt_stock_item ssi on ssi.stock_item_id = stit.stock_item_id\n" +
				"            inner join stockmgmt_stock_operation ssto on stit.stock_operation_id = ssto.stock_operation_id\n" +
				"   WHERE ssi.drug_id = %d\n" +
				"     AND ssto.status = 'COMPLETED'\n" +
				"     AND stit.date_created between date_sub(date(:startDate) , interval 1 MONTH) and date_sub(date(:endDate) , interval 1 MONTH)\n" +
				"     AND ssto.operation_type_id in (4)) rc on rc.stock_item_id = stit.stock_item_id\n" +
				"#Previous Issues + Disposals + TO\n" +
				"        left join (\n" +
				"   select SUM(stit.quantity) as quantity, stit.stock_item_id\n" +
				"   from stockmgmt_stock_item_transaction stit\n" +
				"            inner join stockmgmt_stock_item ssi on ssi.stock_item_id = stit.stock_item_id\n" +
				"            inner join stockmgmt_stock_operation ssto on stit.stock_operation_id = ssto.stock_operation_id\n" +
				"   WHERE ssi.drug_id = %d\n" +
				"     AND ssto.status = 'COMPLETED'\n" +
				"     AND stit.date_created between date_sub(date(:startDate) , interval 1 MONTH) and date_sub(date(:endDate) , interval 1 MONTH)\n" +
				"     AND ssto.operation_type_id in (6, 3, 2)) si on si.stock_item_id = stit.stock_item_id\n" +
				"#Current receipts\n" +
				"              left join (\n" +
				"         select SUM(stit.quantity) as quantity, stit.stock_item_id\n" +
				"         from stockmgmt_stock_item_transaction stit\n" +
				"                  inner join stockmgmt_stock_item ssi on ssi.stock_item_id = stit.stock_item_id\n" +
				"                  left join stockmgmt_stock_operation ssto on stit.stock_operation_id = ssto.stock_operation_id\n" +
				"         WHERE ssi.drug_id = %d\n" +
				"           AND ssto.status = 'COMPLETED'\n" +
				"           AND stit.date_created between :startDate and :endDate\n" +
				"           AND ssto.operation_type_id in (4)\n" +
				"          GROUP BY stit.stock_item_id) rc_curr on rc_curr.stock_item_id = stit.stock_item_id\n" +
				"#Current Dispense\n" +
				"              left join (\n" +
				"         select SUM(stit.quantity) *-1 as quantity, stit.stock_item_id\n" +
				"         from stockmgmt_stock_item_transaction stit\n" +
				"                  inner join stockmgmt_stock_item ssi on ssi.stock_item_id = stit.stock_item_id\n" +
				"         WHERE ssi.drug_id = %d\n" +
				"           AND stit.patient_id IS NOT NULL\n" +
				"           AND stit.date_created between :startDate and :endDate\n" +
				"           AND EXISTS (\n" +
				"               SELECT 1 \n" +
				"               FROM kenyaemr_etl.etl_special_clinics esc\n" +
				"               WHERE esc.patient_id = stit.patient_id \n" +
				"               AND esc.assistive_technology = %d\n" +
				"           )) dis_curr on dis_curr.stock_item_id = stit.stock_item_id\n" +
				"#Current Disposals, losses and wastages\n" +
				"left join (\n" +
				"         select SUM(stit.quantity) as quantity, stit.stock_item_id\n" +
				"         from stockmgmt_stock_item_transaction stit\n" +
				"                  inner join stockmgmt_stock_item ssi on ssi.stock_item_id = stit.stock_item_id\n" +
				"                  left join stockmgmt_stock_operation ssto on stit.stock_operation_id = ssto.stock_operation_id\n" +
				"         WHERE ssi.drug_id = %d\n" +
				"           AND ssto.status = 'COMPLETED'\n" +
				"           AND stit.date_created between :startDate and :endDate\n" +
				"           AND ssto.operation_type_id in (2)\n" +
				"         GROUP BY stit.stock_item_id) curr_loss on curr_loss.stock_item_id = stit.stock_item_id\n" +
				"#Current Positive adjustments\n" +
				" left join (\n" +
				"         select SUM(stit.quantity) as quantity, stit.stock_item_id\n" +
				"         from stockmgmt_stock_item_transaction stit\n" +
				"                  inner join stockmgmt_stock_item ssi on ssi.stock_item_id = stit.stock_item_id\n" +
				"                  left join stockmgmt_stock_operation ssto on stit.stock_operation_id = ssto.stock_operation_id\n" +
				"         WHERE ssi.drug_id = %d\n" +
				"           AND ssto.status = 'COMPLETED'\n" +
				"           AND stit.date_created between :startDate and :endDate\n" +
				"           AND ssto.operation_type_id in (1)\n" +
				"         GROUP BY stit.stock_item_id) pos_adj on pos_adj.stock_item_id = stit.stock_item_id\n" +
				"#Current Negative adjustments : Transfer out\n" +
				"              left join (\n" +
				"         select SUM(stit.quantity) as quantity, stit.stock_item_id\n" +
				"         from stockmgmt_stock_item_transaction stit\n" +
				"                  inner join stockmgmt_stock_item ssi on ssi.stock_item_id = stit.stock_item_id\n" +
				"                  left join stockmgmt_stock_operation ssto on stit.stock_operation_id = ssto.stock_operation_id\n" +
				"         WHERE ssi.drug_id = %d\n" +
				"           AND stit.quantity > 0\n" +
				"           AND ssto.status = 'COMPLETED'\n" +
				"           AND stit.date_created between :startDate and :endDate\n" +
				"           AND ssto.operation_type_id in (3)\n" +
				"         GROUP BY stit.stock_item_id) neg_adj on neg_adj.stock_item_id = stit.stock_item_id\n" +
				"#Stock take\n" +
				"              left join (\n" +
				"         select  mid(max(concat(ssoi.date_created,ssoi.quantity)),20) as quantity, stit.stock_item_id\n" +
				"         from stockmgmt_stock_item_transaction stit\n" +
				"                  inner join stockmgmt_stock_item ssi on ssi.stock_item_id = stit.stock_item_id\n" +
				"                  left join stockmgmt_stock_operation ssto on stit.stock_operation_id = ssto.stock_operation_id\n" +
				"                  left join stockmgmt_stock_operation_item ssoi on ssoi.stock_operation_id = stit.stock_operation_id\n" +
				"         WHERE ssi.drug_id = %d\n" +
				"           AND ssto.status = 'COMPLETED'\n" +
				"           AND stit.date_created between :startDate and :endDate\n" +
				"           AND ssto.operation_type_id in (8)\n" +
				"         GROUP BY stit.stock_item_id) stck_take on stck_take.stock_item_id = stit.stock_item_id\n" +
				"#Expiring in 6 months\n" +
				"              left join (\n" +
				"         select  SUM(stit.quantity) as quantity, stit.stock_item_id, MIN(ssbt.expiration) as earliest_expiry_date\n" +
				"         from stockmgmt_stock_item_transaction stit\n" +
				"                  inner join stockmgmt_stock_item ssi on ssi.stock_item_id = stit.stock_item_id\n" +
				"                  inner join stockmgmt_stock_batch ssbt on ssbt.stock_item_id = stit.stock_item_id\n" +
				"         WHERE ssi.drug_id = %d\n" +
				"           AND stit.date_created between :startDate and :endDate\n" +
				"           AND date(ssbt.expiration)  between date(:endDate) and date_add(date(:endDate) , interval 6 MONTH)\n" +
				"         GROUP BY stit.stock_item_id) early_exp on early_exp.stock_item_id = stit.stock_item_id\n" +
				"\n" +
				"#Current requisitions\n" +
				"              left join (\n" +
				"         select SUM(stit.quantity) as quantity, stit.stock_item_id\n" +
				"         from stockmgmt_stock_item_transaction stit\n" +
				"                  inner join stockmgmt_stock_item ssi on ssi.stock_item_id = stit.stock_item_id\n" +
				"                  left join stockmgmt_stock_operation ssto on stit.stock_operation_id = ssto.stock_operation_id\n" +
				"         WHERE ssi.drug_id = %d\n" +
				"           AND ssto.status = 'COMPLETED'\n" +
				"           AND stit.date_created between :startDate and :endDate\n" +
				"           AND ssto.operation_type_id in (7)\n" +
				"         GROUP BY stit.stock_item_id) cur_req on cur_req.stock_item_id = stit.stock_item_id;";

		return String.format(query, drugId, drugId, drugId, drugId, drugId, drugId, drugId, drugId, drugId, drugId, drugId, assistiveTechnology);

}





}
