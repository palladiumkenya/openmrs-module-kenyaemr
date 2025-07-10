/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.MOH505;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Moh505CohortLibrary {

    protected static final Log log = LogFactory.getLog(Moh505CohortLibrary.class);


    public CohortDefinition jaundiceCases() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "SELECT a.patient_id\n" +
                "FROM (\n" +
                "    SELECT patient_id, GROUP_CONCAT(c.general_examination) AS general_examination\n" +
                "    FROM kenyaemr_etl.etl_clinical_encounter c\n" +
                "    WHERE c.general_examination LIKE '%Jaundice%'\n" +
                "      AND DATE(c.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
                "    GROUP BY patient_id\n" +
                "    UNION ALL\n" +
                "    SELECT patient_id, GROUP_CONCAT(h.general_examination) AS general_examination\n" +
                "    FROM kenyaemr_etl.etl_patient_hiv_followup h\n" +
                "    WHERE h.general_examination LIKE '%Jaundice%'\n" +
                "      AND DATE(h.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
                "    GROUP BY patient_id\n" +
                ") a\n" +
                "WHERE a.general_examination IS NOT NULL\n" +
                "  AND FIND_IN_SET('Jaundice', a.general_examination) > 0;";
        cd.setName("jaundice");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Acute Jaundice Case");

        return cd;
    }

    public CohortDefinition malnutritionCase(int acuteMalnutrition) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+acuteMalnutrition+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Acute Malnutrition");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Acute Malnutrition Cases");
        return cd;
    }

    public CohortDefinition poliomyelitisCohortCase(int poliomyelitis) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+poliomyelitis+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Poliomyelitis");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("AFP (Poliomyelitis) Cases");
        return cd;
    }

    public CohortDefinition anthraxCohortCase(int anthrax) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+anthrax+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Anthrax");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Anthrax Cases");
        return cd;
    }

    public CohortDefinition choleraCohortCase(int cholera) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+cholera+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Cholera");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Cholera Cases");
        return cd;
    }

    public CohortDefinition dengueCohortCase(int dengue) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+dengue+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Dengue");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Dengue Cases");
        return cd;
    }

    public CohortDefinition dysenteryCohortCase(int dysentery) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+dysentery+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Dysentery");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Dysentery Cases");
        return cd;
    }

    public CohortDefinition guineaWormCohortCase(int guineaWorm) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+guineaWorm+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Guinea Worm Disease");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Guinea Worm Disease Cases");
        return cd;
    }

    public CohortDefinition measlesCohortCase(int measles) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+measles+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Measles");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Measles Cases");
        return cd;
    }

    public CohortDefinition suspectedMalariaCohortCase(int suspectedMalaria) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+suspectedMalaria+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Suspected Malaria");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Suspected Malaria Cases");
        return cd;
    }

    public CohortDefinition meningococcalMeningitisCohortCase(int meningococcalMeningitis) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+meningococcalMeningitis+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Meningococcal Meningitis");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Meningococcal Meningitis Cases");
        return cd;
    }

    public CohortDefinition neonatalTetanusCohortCase(int neonatalTetanus) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+neonatalTetanus+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Neonatal Tetanus");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Neonatal Tetanus Cases");
        return cd;
    }

    public CohortDefinition plagueCohortCase(int plague) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+plague+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Plague");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Plague Cases");
        return cd;
    }

    public CohortDefinition rabiesCohortCase(int rabies) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+rabies+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Rabies");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Rabies Cases");
        return cd;
    }

    public CohortDefinition riftValleyFeverCohortCase(int riftValleyFever) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+riftValleyFever+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Rift Valley Fever");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Rift Valley Fever Cases");
        return cd;
    }

    public CohortDefinition suspectedTBCohortCase(int suspectedTB) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+suspectedTB+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Suspected MDR/XDR TB");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Suspected MDR/XDR TB Cases");
        return cd;
    }

    public CohortDefinition typhoidCohortCase(int typhoid) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+typhoid+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Typhoid");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Typhoid Cases");
        return cd;
    }

    public CohortDefinition yellowFeverCohortCase(int yellowFever) {

        String sqlQuery = "SELECT d.patient_id FROM openmrs.encounter_diagnosis d WHERE date(d.date_created) BETWEEN date(:startDate) and date(:endDate) " +
                "and d.diagnosis_coded = "+yellowFever+";";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Yellow Fever");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Yellow Fever Cases");
        return cd;
    }


}
