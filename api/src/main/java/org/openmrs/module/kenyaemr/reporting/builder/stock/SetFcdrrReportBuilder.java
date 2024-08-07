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
	static final int ABACAVIR_300MG_TABS = 1325;
	static final int ABACAVIR_LAMIVUDINE_600MG_300MG_TABS = 4873;	
	static final int DARUNAVIR_600MG = 2221;
	static final int DOLUTEGRAVIR_50MG_TABS  = 2225;
	static final int LAMIVUDINE_150MG_ORAL_TABLET  = 1819;
	static final int ZIDOVUDINE_LAMIVUDINE_300MG__150MG_AZT_3TC_FDC = 1822;
	static final int RITONAVIR_RTV_80MG = 2105;
	static final int ZIDOVUDINE_10MG_100ML = 2205;	
	static final int RALTEGRAVIR_25MG = 2214;
	static final int RALTEGRAVIR_100MG = 2215;
	static final int NEVIRAPINE_10MG_100ML = 1940;	
	static final int LOPINAVIR_RITONAVIR_80_20MG = 1859;
	static final int LOPINAVIR_RITONAVIR_40_10MG = 1859;
	static final int LOPINAVIR_RITONAVIR_100_25MG = 1856;
	static final int LAMIVUDINE_10MG = 1856;
	static final int ETRAVIRINE_100MG = 2212;
	static final int ETRAVIRINE_200MG = 2213;
	static final int EFAVIRENZ_200MG = 1640;
	static final int EFAVIRENZ_600MG = 1643;
	static final int DOLUTEGRAVIR_10MG = 2223;
	static final int DARUNAVIR_100MG = 2218;
	static final int DARUNAVIR_75MG = 2219;
	static final int DARUNAVIR_150MG = 2220;
	static final int ABACAVIR_LAMIVUDINE_DOLUTEGRAVIR = 2251;
	static final int ABACAVIR_LAMIVUDINE_120_60MG_= 2242;
	static final int ZIDOVUDINE_LAMIVUDINE_NEVIRAPINE = 1817;
	static final int ZIDOVUDINE_LAMIVUDINE_300_150MG = 1818;
	static final int ZIDOVUDINE_300MG = 2209;
	static final int TENOFOVIR_LAMIVUDINE_EFAVIRENZ_300_300_400MG = 2240;
	static final int TENOFOVIR_LAMIVUDINE_DOLUTEGRAVIR_300_300_500MG = 2240;
	static final int TENOFOVIR_LAMIVUDINE_300_300MG = 2235;
	static final int TENOFOVIR_EMTRICITABINE_300_200MG = 2236;
	static final int TENOFOVIR_300MG = 2227;
	static final int RITONAVIR_100MG = 2102;
	static final int RALTEGRAVIR_400MG = 2216;
	static final int NEVIRAPINE_200MG = 1941;
	static final int LOPINAVIR_RITONAVIR_200_50MG = 1858;
	static final int ATAZANAVIR_RITONAVIR_300_100MG = 2241;
	static final int ZIDOVUDINE_LAMIVUDINE_NEVIRAPINE_60_30_50MG= 1821;
	static final int ACYCLOVIR_400MG= 2247;
	static final int AMPHOTERICIN_B_50_INJECTABLE= 1391;
	static final int COTRIMOXAZOLE_960MG= 2248;
	static final int COTRIMOXAZOLE_240MG= 2249;
	static final int FLUCONAZOLE_200MG= 2234;
	static final int ETHAMBUTOL_400MG= 8;
	static final int ISONIAZID_10MG= 1800;
	static final int ISONIAZID_100MG= 1801;
	static final int ISONIAZID_300MG= 1805;
	static final int PYRAZINAMIDE_400= 4;
	static final int PYRIDOXINE_50MG= 2244;
	static final int RIFABUTIN_150MG_TAB= 11;
	static final int RIFAPENTINE_150_TAB= 2245;
	static final int RIFAPENTINE_ISONIAZID_300MG_300MG= 2246;
	
	/*	
	 ZIDOVUDINE_10MG_240ML 
	 NEVIRAPINE_10MG_240ML
	 ATANAZAVIR_RITONAVIR_300_100MG_TABS
	TODO: Include the above 3 missing drugs into drugs list. The difference is in packaging units
	*/
	
	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), 
			                 new Parameter("endDate", "End Date", Date.class)
			);
	}

	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,
															ReportDefinition reportDefinition) {
		return Arrays.asList(ReportUtils.map(getDataSetDefinition("ABC", ABACAVIR_300MG_TABS), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("ABC3TC", ABACAVIR_LAMIVUDINE_600MG_300MG_TABS), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("DARUNAVIR600MG", DARUNAVIR_600MG), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("DOLUTEGRAVIR50MGTABS", DOLUTEGRAVIR_50MG_TABS), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("3TC_150MG", LAMIVUDINE_150MG_ORAL_TABLET), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(getDataSetDefinition("AZT_3TC_FDC", ZIDOVUDINE_LAMIVUDINE_300MG__150MG_AZT_3TC_FDC), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("RTV", RITONAVIR_RTV_80MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("AZT_100MG_100ML", ZIDOVUDINE_10MG_100ML), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("RAL_25MG", RALTEGRAVIR_25MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("RAL_100MG", RALTEGRAVIR_100MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("NVP_10MG_100ML", NEVIRAPINE_10MG_100ML), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("LPV_R_80_20MG", LOPINAVIR_RITONAVIR_80_20MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("LPV_R_40_10MG", LOPINAVIR_RITONAVIR_40_10MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("LPV_R_100_25MG", LOPINAVIR_RITONAVIR_100_25MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("3TC_10MG", LAMIVUDINE_10MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("ETV_100MG", ETRAVIRINE_100MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("ETV_200MG", ETRAVIRINE_200MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("EFV_200MG", EFAVIRENZ_200MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("EFV_600MG", EFAVIRENZ_600MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("DTG_10MG", DOLUTEGRAVIR_10MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("DRV_100MG", DARUNAVIR_100MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("DRV_75MG", DARUNAVIR_75MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("DRV_150MG", DARUNAVIR_150MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("ABC_3TC_DTG", ABACAVIR_LAMIVUDINE_DOLUTEGRAVIR), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("ABC_3TC_120_60MG", ABACAVIR_LAMIVUDINE_120_60MG_), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("AZT_3TC_NVP", ZIDOVUDINE_LAMIVUDINE_NEVIRAPINE), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("AZT_3TC__300_150MG", ZIDOVUDINE_LAMIVUDINE_300_150MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("AZT_300MG", ZIDOVUDINE_300MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("TDF_3TC_EFV_300_300_400MG", TENOFOVIR_LAMIVUDINE_EFAVIRENZ_300_300_400MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("TDF_3TC_DTG_300_300_50MG", TENOFOVIR_LAMIVUDINE_DOLUTEGRAVIR_300_300_500MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("TDF_3TC_300_300MG", TENOFOVIR_LAMIVUDINE_300_300MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("FTC_TDF_300_200MG", TENOFOVIR_EMTRICITABINE_300_200MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("TDF_300MG", TENOFOVIR_300MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("RTV_100MG", RITONAVIR_100MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("RAL_100MG", RALTEGRAVIR_400MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("NVP_200MG", NEVIRAPINE_200MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("LPV_R_200_50MG", LOPINAVIR_RITONAVIR_200_50MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("ATV_R_300_100MG", ATAZANAVIR_RITONAVIR_300_100MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("AZT_3TC_NVP_60_30_50MG", ZIDOVUDINE_LAMIVUDINE_NEVIRAPINE_60_30_50MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("ACYCLOVIR_400MG", ACYCLOVIR_400MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("AMPHOTERICIN_B_50_INJECTABLE", AMPHOTERICIN_B_50_INJECTABLE), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("COTRIMOXAZOLE_960MG", COTRIMOXAZOLE_960MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("COTRIMOXAZOLE_240MG", COTRIMOXAZOLE_240MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("FLUCONAZOLE_200MG", FLUCONAZOLE_200MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("ETHAMBUTOL_400MG", ETHAMBUTOL_400MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("ISONIAZID_10MG", ISONIAZID_10MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("ISONIAZID_100MG", ISONIAZID_100MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("ISONIAZID_300MG", ISONIAZID_300MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("PYRAZINAMIDE_400MG", PYRAZINAMIDE_400), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("PYRIDOXINE_50MG", PYRIDOXINE_50MG), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("RIFABUTIN_150MG_TAB", RIFABUTIN_150MG_TAB), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("RIFAPENTINE_150_TAB", RIFAPENTINE_150_TAB), "startDate=${startDate},endDate=${endDate}"),
			    ReportUtils.map(getDataSetDefinition("RF_INH_300MG_300MG", RIFAPENTINE_ISONIAZID_300MG_300MG), "startDate=${startDate},endDate=${endDate}")
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
			"           AND stit.date_created between :startDate and :endDate) dis_curr on dis_curr.stock_item_id = stit.stock_item_id\n" +
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

		return String.format(query, drugId, drugId, drugId, drugId, drugId, drugId, drugId, drugId, drugId, drugId, drugId);
	}
}
