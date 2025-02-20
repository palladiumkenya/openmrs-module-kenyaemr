/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.shared.hiv.art;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimCohortLibrary;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class FmapCohortLibrary {

	@Autowired
	private DatimCohortLibrary datimCohortLibrary;

	/**
	 * FIRST REVIEW: 12 Months Cohort composition
	 */

	/**
	 * Number in HEI Cohort 12 months
	 * @return the indicator
	 */
	public CohortDefinition AdultFirstTLD() {
		String sqlQuery = "SELECT he.patient_id FROM kenyaemr_etl.etl_drug_order he\n" +
			"WHERE DATE(he.visit_date) between date_sub(date(:startDate),interval 1 YEAR) and  date_sub(date(:endDate),interval 1 YEAR) and drug_name = 'TENOFOVIR DISOPROXIL FUMARATE+LAMIVUDINE+DOLUTEGRAVIR';";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("adultFirstLineTLD");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("TDF+3TC+DTG");
		return cd;
	}

	public CohortDefinition AdultFirstTLE() {
		String sqlQuery = "SELECT he.patient_id FROM kenyaemr_etl.etl_drug_order he\n" +
			"WHERE DATE(he.visit_date) between date_sub(date(:startDate),interval 1 YEAR) and  date_sub(date(:endDate),interval 1 YEAR) and drug_name = 'TENOFOVIR DISOPROXIL FUMARATE+LAMIVUDINE+EFAVIRENZ';";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("adultFirstLineTLE");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("TDF+3TC+EFV");
		return cd;
	}

	public CohortDefinition patientOnSpecificRegimenAndRegimenLine(String regimenName,String regimenLine,String ageGroup,String pmtct){
		String sqlQuery = "SELECT regimeData.patient_id\n" +
				"FROM (SELECT de.patient_id                                          as patient_id,\n" +
				"             CASE\n" +
				"                 WHEN timestampdiff(YEAR, date(d.DOB), max(fup.visit_date)) >= 15\n" +
				"                     THEN 'adult'\n" +
				"                 ELSE 'child' END                                   AS agegroup,\n" +
				"             CASE\n" +
				"                 WHEN (fup.pregnancy_status = 1065 OR fup.breastfeeding = 1065) THEN 'True'\n" +
				"                 ELSE 'False' END                                   AS pmtct,\n" +
				"             mid(max(concat(de.date_started, de.regimen)), 11)      AS regimen,\n" +
				"             mid(max(concat(de.date_started, de.regimen_line)), 11) AS regimen_line\n" +
				"      FROM kenyaemr_etl.etl_drug_event de\n" +
				"               INNER JOIN kenyaemr_etl.etl_patient_demographics d ON d.patient_id = de.patient_id\n" +
				"               INNER JOIN kenyaemr_etl.etl_patient_hiv_followup fup ON de.patient_id = fup.patient_id\n" +
				"      where de.date_started <= :endDate\n" +
				"      GROUP BY de.patient_id) regimeData\n" +
				"WHERE regimen NOT IN ('RHZE', 'RHZ', 'SRHZE', 'RfbHZE', 'RfbHZ', 'SRfbHZE', 'S (1 gm vial)', 'E', 'RHE', 'EH')\n" +
				"  AND regimen = ':regimenName'\n" +
				"  AND regimen_line = ':regimenLine'\n" +
				"  AND agegroup = ':ageGroup'\n" +
				"  AND pmtct = ':pmtct';";
		sqlQuery = sqlQuery.replaceAll(":regimenName", regimenName);
		sqlQuery = sqlQuery.replaceAll(":regimenLine", regimenLine);
		sqlQuery = sqlQuery.replaceAll(":ageGroup", ageGroup);		
		sqlQuery = sqlQuery.replaceAll(":pmtct", pmtct);
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Regimens");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Regimens");
		return cd;
	}
	
	public CohortDefinition txCurrpatientOnSpecificRegimenAndRegimenLine(String regimenName,String regimenLine,String ageGroup,String pmtct) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("txcurr", ReportUtils.map(datimCohortLibrary.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("patientOnSpecificRegimenAndRegimenLine", ReportUtils.map(patientOnSpecificRegimenAndRegimenLine(regimenName, regimenLine, ageGroup, pmtct), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("(txcurr AND patientOnSpecificRegimenAndRegimenLine");
		return cd;
	}

	public CohortDefinition patientOnAnyOtherRegimenandRegimenLine(String regimenName,String regimenLine,String ageGroup, String pmtct){
		String sqlQuery = "SELECT regimenData.patient_id\n" +
				"FROM\n" +
				"  (SELECT\n" +
				"     de.patient_id as patient_id,\n" +
				"     CASE WHEN timestampdiff(YEAR, date(d.DOB), max(fup.visit_date)) >= 15\n" +
				"       THEN 'adult'\n" +
				"     ELSE 'child' END AS agegroup,\n" +
				"     CASE WHEN (fup.pregnancy_status = 1065 OR fup.breastfeeding = 1065) THEN 'True'\n" +
				"          ELSE 'False' END AS pmtct,\n" +
				"             mid(max(concat(de.date_started, de.regimen)), 11) AS regimen,\n" +
				"             mid(max(concat(de.date_started, de.regimen_line)), 11) AS regimen_line\n" +
				"   FROM kenyaemr_etl.etl_drug_event de\n" +
				"     INNER JOIN kenyaemr_etl.etl_patient_demographics d ON d.patient_id = de.patient_id\n" +
				"     INNER JOIN kenyaemr_etl.etl_patient_hiv_followup fup ON de.patient_id = fup.patient_id\n" +
				"           WHERE date_started <= :endDate\n" +
				"   GROUP BY de.patient_id\n" +
				"  ) regimenData\n" +
				"WHERE regimen NOT IN  ('RHZE', 'RHZ', 'SRHZE', 'RfbHZE', 'RfbHZ', 'SRfbHZE', 'S (1 gm vial)', 'E', 'RHE', 'EH',\n" +
				"                               'AZT/3TC/DTG','AZT/3TC/LPV/r','AZT/3TC/ATV/R','TDF/3TC/EFV','TDF/3TC/ATV/r','TDF/3TC/DTG','TDF/3TC/LPV/r','ABC/3TC/DTG','ABC/3TC/LPV/r','ABC/3TC/ATV/r','AZT/3TC/DTG/DRV/RTV')\n" +
				"      AND regimen = ':regimenName'\n" +
				"      AND  regimen_line = ':regimenLine'\n" +
				"      AND agegroup = ':ageGroup'\n" +
				"      AND pmtct = ':pmtct';";
		sqlQuery = sqlQuery.replaceAll(":regimenName", regimenName);
		sqlQuery = sqlQuery.replaceAll(":regimenLine", regimenLine);
		sqlQuery = sqlQuery.replaceAll(":ageGroup", ageGroup);		
		sqlQuery = sqlQuery.replaceAll(":pmtct", pmtct);
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Regimens");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Regimens");
		return cd;
	}

	public CohortDefinition txCurrpatientOnAnyOtherRegimenandRegimenLine(String regimenName,String regimenLine,String ageGroup,String pmtct) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("txcurr", ReportUtils.map(datimCohortLibrary.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("patientOnAnyOtherRegimenandRegimenLine", ReportUtils.map(patientOnAnyOtherRegimenandRegimenLine(regimenName, regimenLine, ageGroup, pmtct), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("(txcurr AND patientOnAnyOtherRegimenandRegimenLine");
		return cd;
	}

	public CohortDefinition pmtctPatientOnSpecificRegimen(String regimenName, String regimenLine, String pmtct){
		String sqlQuery = "SELECT regimenData.patient_id\n" +
				"FROM\n" +
				"    (SELECT\n" +
				"         de.patient_id as patient_id,\n" +
				"         CASE WHEN (fup.pregnancy_status = 1065 OR fup.breastfeeding = 1065) THEN 'True'\n" +
				"              ELSE 'False' END AS pmtct,\n" +
				"                 mid(max(concat(de.date_started, de.regimen)), 11) AS regimen,\n" +
				"                 mid(max(concat(de.date_started, de.regimen_line)), 11) AS regimen_line\n" +
				"     FROM kenyaemr_etl.etl_drug_event de\n" +
				"              INNER JOIN kenyaemr_etl.etl_patient_demographics d ON d.patient_id = de.patient_id\n" +
				"              INNER JOIN kenyaemr_etl.etl_patient_hiv_followup fup ON de.patient_id = fup.patient_id\n" +
				"     where de.date_started <= :endDate\n" +
				"     GROUP BY de.patient_id\n" +
				"    ) regimenData\n" +
				"WHERE regimen NOT IN  ('RHZE', 'RHZ', 'SRHZE', 'RfbHZE', 'RfbHZ', 'SRfbHZE', 'S (1 gm vial)', 'E', 'RHE', 'EH')\n" +
				"  AND  regimen = ':regimenName'\n" +
				"  AND  regimen_line = ':regimenLine'\n" +
				"  AND pmtct = ':pmtct';";
		sqlQuery = sqlQuery.replaceAll(":regimenName", regimenName);
		sqlQuery = sqlQuery.replaceAll(":regimenLine", regimenLine);
		sqlQuery = sqlQuery.replaceAll(":pmtct", pmtct);
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("PmtctRegimens");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("PmtctRegimens");
		return cd;
	}

	public CohortDefinition txCurrPmtctPatientOnSpecificRegimen(String regimenName,String regimenLine, String pmtct) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("txcurr", ReportUtils.map(datimCohortLibrary.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("pmtctPatientOnSpecificRegimen", ReportUtils.map(pmtctPatientOnSpecificRegimen(regimenName, regimenLine, pmtct), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("(txcurr AND pmtctPatientOnSpecificRegimen");
		return cd;
	}
	
	public CohortDefinition pmtctPatientOnAnyOtherRegimen(String regimenName, String regimenLine, String pmtct){
		String sqlQuery = "SELECT regimenData.patient_id\n" +
				"FROM\n" +
				"  (SELECT\n" +
				"     de.patient_id as patient_id,\n" +
				"     CASE WHEN (fup.pregnancy_status = 1065 OR fup.breastfeeding = 1065) THEN 'True'\n" +
				"          ELSE 'False' END AS pmtct,\n" +
				"             mid(max(concat(de.date_started, de.regimen)), 11) AS regimen,\n" +
				"             mid(max(concat(de.date_started, de.regimen_line)), 11) AS regimen_line\n" +
				"   FROM kenyaemr_etl.etl_drug_event de\n" +
				"     INNER JOIN kenyaemr_etl.etl_patient_demographics d ON d.patient_id = de.patient_id\n" +
				"     INNER JOIN kenyaemr_etl.etl_patient_hiv_followup fup ON de.patient_id = fup.patient_id\n" +
				"               WHERE date_started <= :endDate\n" +
				"   GROUP BY de.encounter_id\n" +
				"  ) regimenData\n" +
				"WHERE regimen NOT IN ('RHZE', 'RHZ', 'SRHZE', 'RfbHZE', 'RfbHZ', 'SRfbHZE', 'S (1 gm vial)', 'E', 'RHE', 'EH',\n" +
				"                               'AZT/3TC/DTG','AZT/3TC/LPV/r','AZT/3TC/ATV/R','TDF/3TC/EFV','TDF/3TC/ATV/r','TDF/3TC/DTG','TDF/3TC/LPV/r','ABC/3TC/DTG','ABC/3TC/LPV/r','ABC/3TC/ATV/r','AZT/3TC/DTG/DRV/RTV')\n" +
				"      AND regimen = ':regimenName'\n" +
				"      AND  regimen_line = ':regimenLine'\n" +
				"      AND pmtct = ':pmtct';";
		sqlQuery = sqlQuery.replaceAll(":regimenName", regimenName);
		sqlQuery = sqlQuery.replaceAll(":regimenLine", regimenLine);
		sqlQuery = sqlQuery.replaceAll(":pmtct", pmtct);
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("pmtctOtherRegimens");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("pmtctOtherRegimens");
		return cd;
	}

	public CohortDefinition txCurrPmtctPatientOnAnyOtherRegimen(String regimenName, String regimenLine, String pmtct) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("txcurr", ReportUtils.map(datimCohortLibrary.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("pmtctPatientOnAnyOtherRegimen", ReportUtils.map(pmtctPatientOnAnyOtherRegimen(regimenName, regimenLine, pmtct), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("(txcurr AND pmtctPatientOnAnyOtherRegimen");
		return cd;
	}

	public CohortDefinition txCurrPeadOnSpecificRegimenAndRegimenLine(String regimenName,String regimenLine,String ageGroup,String pmtct, String minWeight, String maxWeight) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("txcurr", ReportUtils.map(datimCohortLibrary.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("patientOnSpecificRegimenAndRegimenLine", ReportUtils.map(patientOnSpecificRegimenAndRegimenLine(regimenName, regimenLine, ageGroup, pmtct), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("peadWeight", ReportUtils.map(peadWeight(minWeight, maxWeight)));
		cd.setCompositionString("(txcurr AND patientOnSpecificRegimenAndRegimenLine AND peadWeight");
		return cd;
	}

	public CohortDefinition peadWeight(String minWeight, String maxWeight) {
		String sqlQuery = "SELECT tf.patient_id FROM (SELECT t.patient_id,\n" +
			"                                  mid(max(concat(t.visit_date, t.weight)), 11) AS weight\n" +
			"                           FROM kenyaemr_etl.etl_patient_triage t\n" +
			"                                    INNER JOIN kenyaemr_etl.etl_patient_hiv_followup f\n" +
			"                                               on t.patient_id = f.patient_id and t.visit_date = f.visit_date\n" +
			"                           WHERE f.weight " + minWeight + " and f.weight " + maxWeight + "\n" +
			"                           GROUP BY t.patient_id) tf";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("latestGreenCardWeight");
		cd.setQuery(sqlQuery);
		cd.setDescription("latestGreenCardWeight");
		return cd;
	}

	public CohortDefinition infantBreastfeedingPC8Regimen() {
		String sqlQuery = "SELECT hv.patient_id\n" +
			"      FROM kenyaemr_etl.etl_hei_follow_up_visit hv\n" +
			"      INNER JOIN kenyaemr_etl.etl_patient_demographics d on d.patient_id = hv.patient_id\n" +
			"WHERE (hv.nvp_given = 86663 OR  hv.ctx_given = 80586)\n" +
			"  AND hv.infant_feeding IN (5526,5632)\n" +			
			"  AND date(hv.visit_date) BETWEEN date(:startDate) and date(:endDate);";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("latestGreenCardWeight");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("infantProphylaxisBreastFeeding");
		return cd;
	}

	public CohortDefinition infantNotBreastfeedingPC7Regimen() {
		String sqlQuery = "SELECT hv.patient_id\n" +
			"      FROM kenyaemr_etl.etl_hei_follow_up_visit hv\n" +
			"      INNER JOIN kenyaemr_etl.etl_patient_demographics d on d.patient_id = hv.patient_id\n" +
			"WHERE (hv.nvp_given = 86663 OR  hv.ctx_given = 80586)\n" +
			"  AND hv.infant_feeding IN (164478,1595)\n" +			
			"  AND date(hv.visit_date) BETWEEN date(:startDate) and date(:endDate);";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("infantProphylaxisNotBreastFeeding");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("infantProphylaxisNotBreastFeeding");
		return cd;
	}

	public CohortDefinition infantAnyOtherRegimenRegimen() {
		String sqlQuery = "SELECT hv.patient_id\n" +
			"FROM kenyaemr_etl.etl_hei_follow_up_visit hv\n" +
			"         INNER JOIN kenyaemr_etl.etl_patient_demographics d on d.patient_id = hv.patient_id\n" +
			"WHERE (hv.nvp_given != 86663 OR  hv.ctx_given != 80586)\n" +			
			"  AND date(hv.visit_date) BETWEEN date(:startDate) and date(:endDate);";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("infantProphylaxisOther");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("infantProphylaxisOther");
		return cd;
	}

	public CohortDefinition prepTdfFtcRegimen() {
		String sqlQuery = "select d.patient_id\n" +
				"from kenyaemr_etl.etl_patient_demographics d\n" +
				"         left join(select pf.patient_id,\n" +
				"                          mid(max(concat(pf.visit_date, pf.regimen_prescribed)), 11) as regimen_prescribed\n" +
				"                   from kenyaemr_etl.etl_prep_followup pf\n" +
				"                   where date(pf.visit_date) between date(:startDate) and date(:endDate)\n" +
				"                   group by pf.patient_id) pf on d.patient_id = pf.patient_id\n" +
				"         left join(select pm.patient_id,\n" +
				"                          mid(max(concat(pm.visit_date, pm.prescribed_regimen)), 11) as regimen_prescribed\n" +
				"                   from kenyaemr_etl.etl_prep_monthly_refill pm\n" +
				"                   where date(pm.visit_date) between date(:startDate) and date(:endDate)\n" +
				"                   group by pm.patient_id) pm on d.patient_id = pm.patient_id\n" +
				"where (pf.patient_id is not null and pf.regimen_prescribed = 'TDF/FTC(Preferred)')\n" +
				"   or (pm.patient_id is not null and pm.regimen_prescribed = 'TDF/FTC(Preferred)')\n" +
				"group by d.patient_id;";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("prepTdfFtcRegimen");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("prepTdfFtcRegimen");
		return cd;
	}
	public CohortDefinition prepTdf3tcRegimen() {
		String sqlQuery = "select d.patient_id\n" +
				"from kenyaemr_etl.etl_patient_demographics d\n" +
				"         left join(select pf.patient_id,\n" +
				"                          mid(max(concat(pf.visit_date, pf.regimen_prescribed)), 11) as regimen_prescribed\n" +
				"                   from kenyaemr_etl.etl_prep_followup pf\n" +
				"                   where date(pf.visit_date) between date(:startDate) and date(:endDate)\n" +
				"                   group by pf.patient_id) pf on d.patient_id = pf.patient_id\n" +
				"         left join(select pm.patient_id,\n" +
				"                          mid(max(concat(pm.visit_date, pm.prescribed_regimen)), 11) as regimen_prescribed\n" +
				"                   from kenyaemr_etl.etl_prep_monthly_refill pm\n" +
				"                   where date(pm.visit_date) between date(:startDate) and date(:endDate)\n" +
				"                   group by pm.patient_id) pm on d.patient_id = pm.patient_id\n" +
				"where (pf.patient_id is not null and pf.regimen_prescribed = 'TDF/3TC')\n" +
				"   or (pm.patient_id is not null and pm.regimen_prescribed = 'TDF/3TC')\n" +
				"group by d.patient_id;";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("prepTdf3tcRegimen");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("prepTdf3tcRegimen");
		return cd;
	}

	public CohortDefinition dapivirinePrepType() {
		String sqlQuery = "select d.patient_id\n" +
				"from kenyaemr_etl.etl_patient_demographics d\n" +
				"         left join(select pf.patient_id,\n" +
				"                          mid(max(concat(pf.visit_date, pf.prep_type)), 11) as prep_type\n" +
				"                   from kenyaemr_etl.etl_prep_followup pf\n" +
				"                   where date(pf.visit_date) between date(:startDate) and date(:endDate)\n" +
				"                   group by pf.patient_id) pf on d.patient_id = pf.patient_id\n" +
				"         left join(select pm.patient_id, mid(max(concat(pm.visit_date, pm.prep_type)), 11) as prep_type\n" +
				"                   from kenyaemr_etl.etl_prep_monthly_refill pm\n" +
				"                   where date(pm.visit_date) between date(:startDate) and date(:endDate)\n" +
				"                   group by pm.patient_id) pm on d.patient_id = pm.patient_id\n" +
				"where (pf.patient_id is not null and pf.prep_type = 'Dapivirine ring')\n" +
				"   or (pm.patient_id is not null and pm.prep_type = 'Dapivirine ring')\n" +
				"group by d.patient_id;";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("dapivirinePrepType");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("dapivirinePrepType");
		return cd;
	}

	public CohortDefinition cabotegravirPrepType() {
		String sqlQuery = "select d.patient_id\n" +
				"from kenyaemr_etl.etl_patient_demographics d\n" +
				"         left join(select pf.patient_id,\n" +
				"                          mid(max(concat(pf.visit_date, pf.prep_type)), 11) as prep_type\n" +
				"                   from kenyaemr_etl.etl_prep_followup pf\n" +
				"                   where date(pf.visit_date) between date(:startDate) and date(:endDate)\n" +
				"                   group by pf.patient_id) pf on d.patient_id = pf.patient_id\n" +
				"         left join(select pm.patient_id, mid(max(concat(pm.visit_date, pm.prep_type)), 11) as prep_type\n" +
				"                   from kenyaemr_etl.etl_prep_monthly_refill pm\n" +
				"                   where date(pm.visit_date) between date(:startDate) and date(:endDate)\n" +
				"                   group by pm.patient_id) pm on d.patient_id = pm.patient_id\n" +
				"where (pf.patient_id is not null and pf.prep_type = 'CAB-LA')\n" +
				"   or (pm.patient_id is not null and pm.prep_type = 'CAB-LA')\n" +
				"group by d.patient_id;";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("cabotegravirPrepType");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("cabotegravirPrepType");
		return cd;
	}

	public CohortDefinition otherPrepRegimenPrepType() {
		String sqlQuery = "select d.patient_id\n" +
				"from kenyaemr_etl.etl_patient_demographics d\n" +
				"         left join(select pf.patient_id,\n" +
				"                          mid(max(concat(pf.visit_date, pf.prep_type)), 11) as prep_type,\n" +
				"                          mid(max(concat(pf.visit_date, pf.regimen_prescribed)), 11) as regimen_prescribed\n" +
				"                   from kenyaemr_etl.etl_prep_followup pf\n" +
				"                   where date(pf.visit_date) between date(:startDate) and date(:endDate)\n" +
				"                   group by pf.patient_id) pf on d.patient_id = pf.patient_id\n" +
				"         left join(select pm.patient_id, mid(max(concat(pm.visit_date, pm.prep_type)), 11) as prep_type,\n" +
				"                          mid(max(concat(pm.visit_date, pm.prescribed_regimen)), 11) as regimen_prescribed\n" +
				"                   from kenyaemr_etl.etl_prep_monthly_refill pm\n" +
				"                   where date(pm.visit_date) between date(:startDate) and date(:endDate)\n" +
				"                   group by pm.patient_id) pm on d.patient_id = pm.patient_id\n" +
				"where (pf.patient_id is not null and pf.regimen_prescribed not in ('','TDF/3TC', 'TDF/FTC(Preferred)') and pf.prep_type not in ('CAB-LA', 'Dapivirine ring'))\n" +
				"   or (pm.patient_id is not null and pm.regimen_prescribed not in ('','TDF/3TC', 'TDF/FTC(Preferred)') and pm.prep_type not in ('CAB-LA', 'Dapivirine ring'))\n" +
				"group by d.patient_id;";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("otherPrepRegimenPrepType");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("otherPrepRegimenPrepType");
		return cd;
	}
}
