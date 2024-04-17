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

import org.openmrs.api.db.hibernate.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Query;
import org.openmrs.Drug;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimCohortLibrary;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class FcdrrCohortLibrary {

    @Autowired
    private DatimCohortLibrary datimCohortLibrary;

    /**
     * FIRST REVIEW: 12 Months Cohort composition
     */

    /**
     * Number in HEI Cohort 12 months
     * @return the indicator
     */
    public int drugunitpackagesizeCl(String regimenName) throws ResponseException{ 
        int quantity = 0;
        Session session = null;

        try {
            session = Context.getRegisteredComponents(org.hibernate.SessionFactory.class).get(0).openSession();
            Query query = session.createQuery("SELECT sum(dr.quantity) FROM Drug dr WHERE dr.name = :name");
            query.setParameter("name", regimenName);
            Number result = (Number) query.uniqueResult();
            if (result != null) {
                quantity = result.intValue();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return quantity;
    }

    public List<Map<String, Object>> getDrugStockReport() {
        List<Map<String, Object>> reportData = new ArrayList<>();

        // Define the SQL query
        String sqlQuery = "SELECT d.name AS drug_name, " +
                          "SUM(stock.quantity) AS total_quantity, " +
                          "location.name AS location_name " +
                          "FROM drug d " +
                          "JOIN drug_inventory stock ON d.drug_id = stock.drug_id " +
                          "LEFT JOIN location location ON stock.location_id = location.location_id " +
                          "GROUP BY d.name, location.name";

        // Execute the SQL query
        List<Object[]> results = Context.getRegisteredComponent("sessionFactory", SessionFactory.class).getCurrentSession().createSQLQuery(sqlQuery).list();

        // Process the results
        for (Object[] row : results) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("drug_name", row[0]);
            rowData.put("total_quantity", row[1]);
            rowData.put("location_name", row[2]);
            reportData.add(rowData);
        }

        return reportData;
    }

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

    public CohortDefinition patientOnSpecificRegimenAndRegimenLine(String regimenName,String regimenLine,String ageGroup){
        String sqlQuery = "SELECT regimeData.patient_id\n" +
                "FROM\n" +
                "  (SELECT\n" +
                "     de.patient_id as patient_id,\n" +
                "     CASE WHEN timestampdiff(YEAR, date(d.DOB), max(fup.visit_date)) >= 15\n" +
                "       THEN 'adult'\n" +
                "     ELSE 'child' END AS agegroup,\n" +
                "     de.program       AS program,\n" +
                "     de.date_started AS date_started,\n" +
                "     de.regimen AS regimen,\n" +
                "     de.regimen_name,\n" +
                "     de.regimen_line AS regimen_line,\n" +
                "     de.discontinued,\n" +
                "     de.regimen_discontinued,\n" +
                "     de.date_discontinued,\n" +
                "     de.reason_discontinued,\n" +
                "     de.reason_discontinued_other\n" +
                "   FROM kenyaemr_etl.etl_drug_event de\n" +
                "     INNER JOIN kenyaemr_etl.etl_patient_demographics d ON d.patient_id = de.patient_id\n" +
                "     INNER JOIN kenyaemr_etl.etl_patient_hiv_followup fup ON de.patient_id = fup.patient_id\n" +
                "     GROUP BY de.encounter_id\n" +
                "  ) regimeData\n" +
                "      WHERE regimen NOT IN  ('RHZE', 'RHZ', 'SRHZE', 'RfbHZE', 'RfbHZ', 'SRfbHZE', 'S (1 gm vial)', 'E', 'RHE', 'EH')\n" +
                "      AND  date_started <= :endDate AND regimen = ':regimenName'\n" +
                "      AND  regimen_line = ':regimenLine'\n" +
                "      AND  patient_id IS NOT NULL AND agegroup = ':ageGroup';";
        sqlQuery = sqlQuery.replaceAll(":regimenName", regimenName);
        sqlQuery = sqlQuery.replaceAll(":regimenLine", regimenLine);
        sqlQuery = sqlQuery.replaceAll(":ageGroup", ageGroup);
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Regimens");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Regimens");
        return cd;
    }

    public CohortDefinition txCurrpatientOnSpecificRegimenAndRegimenLine(String regimenName,String regimenLine,String ageGroup) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("txcurr", ReportUtils.map(datimCohortLibrary.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("patientOnSpecificRegimenAndRegimenLine", ReportUtils.map(patientOnSpecificRegimenAndRegimenLine(regimenName, regimenLine, ageGroup), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("(txcurr AND patientOnSpecificRegimenAndRegimenLine");
        return cd;
    }

    public CohortDefinition patientOnAnyOtherRegimenandRegimenLine(String regimenName,String regimenLine,String ageGroup){
        String sqlQuery = "SELECT regimeData.patient_id\n" +
                "FROM\n" +
                "  (SELECT\n" +
                "     de.patient_id as patient_id,\n" +
                "     CASE WHEN timestampdiff(YEAR, date(d.DOB), max(fup.visit_date)) >= 15\n" +
                "       THEN 'adult'\n" +
                "     ELSE 'child' END AS agegroup,\n" +
                "     de.program       AS program,\n" +
                "     de.date_started AS date_started,\n" +
                "     de.regimen AS regimen,\n" +
                "     de.regimen_name,\n" +
                "     de.regimen_line AS regimen_line,\n" +
                "     de.discontinued,\n" +
                "     de.regimen_discontinued,\n" +
                "     de.date_discontinued,\n" +
                "     de.reason_discontinued,\n" +
                "     de.reason_discontinued_other\n" +
                "   FROM kenyaemr_etl.etl_drug_event de\n" +
                "     INNER JOIN kenyaemr_etl.etl_patient_demographics d ON d.patient_id = de.patient_id\n" +
                "     INNER JOIN kenyaemr_etl.etl_patient_hiv_followup fup ON de.patient_id = fup.patient_id\n" +
                "   GROUP BY de.encounter_id\n" +
                "  ) regimeData\n" +
                "WHERE regimen NOT IN  ('RHZE', 'RHZ', 'SRHZE', 'RfbHZE', 'RfbHZ', 'SRfbHZE', 'S (1 gm vial)', 'E', 'RHE', 'EH',\n" +
                "                               'AZT/3TC/NVP','AZT/3TC/EFV','AZT/3TC/DTG','AZT/3TC/LPV/r','AZT/3TC/ATV/R','TDF/3TC/NVP','TDF/3TC/EFV','TDF/3TC/ATV/r','TDF/3TC/DTG','TDF/3TC/LPV/r','ABC/3TC/NVP','ABC/3TC/EFV','ABC/3TC/DTG','ABC/3TC/LPV/r','ABC/3TC/ATV/r')\n" +
                "      AND  date_started <= :endDate AND regimen = ':regimenName'\n" +
                "      AND  regimen_line = ':regimenLine'\n" +
                "      AND  patient_id IS NOT NULL AND agegroup = ':ageGroup';";
        sqlQuery = sqlQuery.replaceAll(":regimenName", regimenName);
        sqlQuery = sqlQuery.replaceAll(":regimenLine", regimenLine);
        sqlQuery = sqlQuery.replaceAll(":ageGroup", ageGroup);
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Regimens");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Regimens");
        return cd;
    }

    public CohortDefinition txCurrpatientOnAnyOtherRegimenandRegimenLine(String regimenName,String regimenLine,String ageGroup) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("txcurr", ReportUtils.map(datimCohortLibrary.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("patientOnAnyOtherRegimenandRegimenLine", ReportUtils.map(patientOnAnyOtherRegimenandRegimenLine(regimenName, regimenLine, ageGroup), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("(txcurr AND patientOnAnyOtherRegimenandRegimenLine");
        return cd;
    }
}