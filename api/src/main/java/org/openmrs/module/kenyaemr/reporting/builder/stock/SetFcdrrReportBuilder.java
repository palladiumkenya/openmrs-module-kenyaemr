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
@Builds({ "kenyaemr.ehrReports.report.fcdrr" })
public class SetFcdrrReportBuilder extends AbstractReportBuilder {
	//FCDRR is meant only for drugs, so we use drug_ID for uniqueness
	static final int ABACAVIR_300MG_TABS = 1384;
	static final int ABACAVIR_LAMIVUDINE_600MG_300MG_TABS = 4873;
	static final int ATANAZAVIR_RITONAVIR_300_100MG_TABS = 122;
	static final int DARUNAVIR_600MG = 2221;
	static final int DOLUTEGRAVIR_50MG_TABS  = 2225;
	static final int LAMIVUDINE_150MG_ORAL_TABLET  = 1819;
	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
			Date.class), new Parameter("dateBasedReporting", "", String.class));
	}

	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,
															ReportDefinition reportDefinition) {
		return Arrays.asList(ReportUtils.map(getDataSetDefinition("ABC", ABACAVIR_300MG_TABS), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ABC3TC", ABACAVIR_LAMIVUDINE_600MG_300MG_TABS), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("DARUNAVIR600MG", DARUNAVIR_600MG), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("DOLUTEGRAVIR50MGTABS", DOLUTEGRAVIR_50MG_TABS), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("LAMIVUDINE150MGORALTABLET", LAMIVUDINE_150MG_ORAL_TABLET), "startDate=${startDate},endDate=${endDate}")
		);

	}

	private DataSetDefinition getDataSetDefinition(String label, int drugId) {
		SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
		sqlDataSetDefinition.setName(label);
		sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		sqlDataSetDefinition.setSqlQuery(getFcdrrDrugSummary(drugId));
		return sqlDataSetDefinition;
	}


	private String getFcdrrDrugSummary(int drugId) {

		String query =	"select  ifnull(sspu.factor,0) as unit_pack_size,\n" +
				"                           ifnull(SUM(stit.quantity),0)  +   ifnull(SUM(rc.quantity),0) - ifnull(SUM(si.quantity),0) as opening_balance,\n" +
				"                           ifnull(rc_curr.quantity,0) as curr_receipts\n" +
				"     from stockmgmt_stock_item_transaction stit\n" +
				"              inner join stockmgmt_stock_item ssi on (ssi.stock_item_id = stit.stock_item_id) and ssi.drug_id =:d\n" +
				"              inner join stockmgmt_stock_item_packaging_uom sspu on sspu.stock_item_id = stit.stock_item_id\n" +
				"              inner join stockmgmt_stock_operation ssto on stit.stock_operation_id = ssto.operation_type_id and ssto.status = 'COMPLETED'\n" +
				"                         and ssto.operation_type_id in (4, 9) and stit.date_created between date_sub(date(:startDate) , interval 1 MONTH) and date_sub(date(:endDate) , interval 1 MONTH)\n" +
				"        left join (\n" +
				"   select SUM(stit.quantity) as quantity, stit.stock_item_id\n" +
				"   from stockmgmt_stock_item_transaction stit\n" +
				"            inner join stockmgmt_stock_item ssi on ssi.stock_item_id = stit.stock_item_id\n" +
				"            inner join stockmgmt_stock_operation ssto on stit.stock_operation_id = ssto.operation_type_id\n" +
				"   WHERE ssi.drug_id = :d\n" +
				"     AND ssto.status = 'COMPLETED'\n" +
				"     AND stit.date_created between date_sub(date(:startDate) , interval 1 MONTH) and date_sub(date(:endDate) , interval 1 MONTH)\n" +
				"     AND ssto.operation_type_id in (4)) rc on rc.stock_item_id = stit.stock_item_id\n" +
				"        left join (\n" +
				"   select SUM(stit.quantity) as quantity, stit.stock_item_id\n" +
				"   from stockmgmt_stock_item_transaction stit\n" +
				"            inner join stockmgmt_stock_item ssi on ssi.stock_item_id = stit.stock_item_id\n" +
				"            inner join stockmgmt_stock_operation ssto on stit.stock_operation_id = ssto.operation_type_id\n" +
				"   WHERE ssi.drug_id = :d\n" +
				"     AND ssto.status = 'COMPLETED'\n" +
				"     AND stit.date_created between date_sub(date(:startDate) , interval 1 MONTH) and date_sub(date(:endDate) , interval 1 MONTH)\n" +
				"     AND ssto.operation_type_id in (6, 3, 2)) si on si.stock_item_id = stit.stock_item_id\n" +
				"              left join (\n" +
				"         select SUM(stit.quantity) as quantity, stit.stock_item_id\n" +
				"         from stockmgmt_stock_item_transaction stit\n" +
				"                  inner join stockmgmt_stock_item ssi on ssi.stock_item_id = stit.stock_item_id\n" +
				"                  inner join stockmgmt_stock_operation ssto on stit.stock_operation_id = ssto.operation_type_id\n" +
				"         WHERE ssi.drug_id = :d\n" +
				"           AND ssto.status = 'COMPLETED'\n" +
				"           AND stit.date_created between :startDate and :endDate\n" +
				"           AND ssto.operation_type_id in (4)) rc_curr on rc_curr.stock_item_id = stit.stock_item_id";

		return String.format(query, drugId, drugId, drugId, drugId);
	}
}
