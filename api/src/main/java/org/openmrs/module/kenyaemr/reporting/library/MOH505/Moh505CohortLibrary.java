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
import java.util.List;

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

    public CohortDefinition jaundiceDeceased(int patientOutcome) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "SELECT a.patient_id\n" +
                "FROM (\n" +
                "    SELECT patient_id, GROUP_CONCAT(c.general_examination) AS general_examination\n" +
                "    FROM kenyaemr_etl.etl_clinical_encounter c\n" +
                "    WHERE c.general_examination LIKE '%Jaundice%'\n" +
                "     AND c.patient_outcome = " + patientOutcome + " AND DATE(c.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
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
        cd.setDescription("Acute Jaundice Deaths");

        return cd;
    }

    public CohortDefinition sariCases() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select a.patient_id\n" +
                "from (select patient_id, c.complaint as complaint, DATE_SUB(c.visit_date, INTERVAL c.complaint_duration DAY) as complaint_date, c.visit_date\n" +
                "      from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "     where c.complaint = 143264\n" +
                "       and c.complaint_duration < 10\n" +
                "        and date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
                "      group by patient_id) a\n" +
                "             left join kenyaemr_etl.etl_clinical_encounter e\n" +
                "                       on a.patient_id = e.patient_id and date(e.visit_date) between date(:startDate) and date(:endDate)\n" +
                "             left join kenyaemr_etl.etl_patient_triage t\n" +
                "                       on a.patient_id = t.patient_id and date(t.visit_date) between date(:startDate) and date(:endDate) and t.temperature >= 38\n" +
                "             join openmrs.visit v\n" +
                "                  on a.patient_id = v.patient_id and (v.visit_type_id = 3 or v.visit_type_id = 1) or e.patient_outcome = 1654\n" +
                "where e.patient_id is not null or t.patient_id is not null;";
        cd.setName("SARI");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("SARI (Cluster ≥3 cases)");

        return cd;
    }

    public CohortDefinition sariDeceased(int patientOutcome) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select a.patient_id\n" +
                "from (select patient_id, c.complaint as complaint, DATE_SUB(c.visit_date, INTERVAL c.complaint_duration DAY) as complaint_date, c.visit_date\n" +
                "      from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "     where c.complaint = 143264\n" +
                "       and c.complaint_duration < 10\n" +
                "        and date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
                "      group by patient_id) a\n" +
                "             left join kenyaemr_etl.etl_clinical_encounter e\n" +
                "                       on a.patient_id = e.patient_id and date(e.visit_date) between date(:startDate) and date(:endDate)\n" +
                "             left join kenyaemr_etl.etl_patient_triage t\n" +
                "                       on a.patient_id = t.patient_id and date(t.visit_date) between date(:startDate) and date(:endDate) and t.temperature >= 38\n" +
                "             join openmrs.visit v\n" +
                "                  on a.patient_id = v.patient_id and (v.visit_type_id = 3 or v.visit_type_id = 1) or e.patient_outcome = 1654\n" +
                "where (e.patient_outcome = " + patientOutcome + " and e.patient_id is not null) or t.patient_id is not null;";
        cd.setName("SARI Deaths");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("SARI (Cluster ≥3 deaths)");

        return cd;
    }

    public CohortDefinition patientDiagnosis(int diagnosis) {
        String sqlQuery = "select patient_id from encounter_diagnosis where diagnosis_coded = " + diagnosis + " and dx_rank = '2' and date(date_created) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("patientsDiagnosisCases");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Patients diagnosis cases");
        return cd;
    }

    public CohortDefinition patientDeceased(int diagnosis, int patientOutcome) {
        String sqlQuery = "select d.patient_id from openmrs.encounter_diagnosis d\n" +
                "inner join kenyaemr_etl.etl_clinical_encounter c on d.patient_id = c.patient_id\n" +
                "where d.diagnosis_coded = " + diagnosis + " and d.dx_rank = '2' and c.patient_outcome = " + patientOutcome + "\n" +
                "and date(c.visit_date) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("patientsDeceased");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Patients Deceased");
        return cd;
    }

    public CohortDefinition malariaTest(int diagnosis, int mRDT) {
        String sqlQuery = "select ed.patient_id from openmrs.encounter_diagnosis ed\n" +
                "inner join kenyaemr_etl.etl_laboratory_extract le on ed.patient_id = le.patient_id\n" +
                "where ed.diagnosis_coded = " + diagnosis + " and ed.dx_rank = '2' and le.lab_test = " + mRDT + "\n" +
                "and date(le.date_test_requested) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("mRDT Test");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Malaria Test");
        return cd;
    }

    public CohortDefinition malariaTestPositive(int diagnosis, int mRDT) {
        String sqlQuery = "select ed.patient_id from openmrs.encounter_diagnosis ed\n" +
                "inner join kenyaemr_etl.etl_laboratory_extract le on ed.patient_id = le.patient_id\n" +
                "where ed.diagnosis_coded = " + diagnosis + " and ed.dx_rank = '2' and le.lab_test = " + mRDT + " and le.result_name = 'POSITIVE'\n" +
                "and date(le.date_test_requested) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("mRDT Test Positive");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Malaria Test Positive");
        return cd;
    }

    public CohortDefinition typhoidTest(int typhoidTest) {
        String sqlQuery = "select patient_id from kenyaemr_etl.etl_laboratory_extract where lab_test = " + typhoidTest + " and date(date_test_requested) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("typhoidTest");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Typhoid Test");
        return cd;
    }

    public CohortDefinition typhoidTestPositive(int typhoidTest) {
        String sqlQuery = "select patient_id from kenyaemr_etl.etl_laboratory_extract where lab_test = " + typhoidTest + " and result_name = 'POSITIVE' and date(date_test_requested) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("typhoidPositive");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Typhoid Positive");
        return cd;
    }

    public CohortDefinition tbTest(int tbTest) {
        String sqlQuery = "select patient_id from kenyaemr_etl.etl_laboratory_extract where lab_test = " + tbTest + " and date(date_test_requested) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("tbTest");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Tubercullosis Test");
        return cd;
    }

    public CohortDefinition tbPositive(int tbTest) {
        String sqlQuery = "select patient_id from kenyaemr_etl.etl_laboratory_extract where lab_test = " + tbTest + " and result_name = 'POSITIVE' and date(date_test_requested) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("tbPositive");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Tubercullosis Positive");
        return cd;
    }

    public CohortDefinition dysentryTest(int dysentryTest) {
        String sqlQuery = "select patient_id from kenyaemr_etl.etl_laboratory_extract where lab_test = " + dysentryTest + " and date(date_test_requested) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("ShigellaDysentryTest");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Shigella Dysentry Test");
        return cd;
    }

    public CohortDefinition dysentryTestPositive(int dysentryTest) {
        String sqlQuery = "select patient_id from kenyaemr_etl.etl_laboratory_extract where lab_test = " + dysentryTest + " and result_name LIKE '%positive%' and date(date_test_requested) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("ShigellaDysentryTestPositive");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Shigella Dysentry Positive");
        return cd;
    }

    public CohortDefinition maternalDeath() {
        String sqlQuery = "select patient_id from kenyaemr_etl.etl_mchs_delivery where maternal_death_audited = '1065' and date(visit_date) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("maternal");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Maternal Death");
        return cd;
    }

    public CohortDefinition bacterialTest(int bacteriaTest) {
        String sqlQuery = "select patient_id from kenyaemr_etl.etl_laboratory_extract where lab_test = " + bacteriaTest + " and date(date_test_requested) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("BacterialMeningitis");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Bacterial Meningitis");
        return cd;
    }

    public CohortDefinition bacterialTestPositiveNM(int bacteriaTest1, int bacteriaTest2) {
        String sqlQuery = "select patient_id from kenyaemr_etl.etl_laboratory_extract where (lab_test = " + bacteriaTest1 + " or lab_test = " + bacteriaTest2 + " ) and result_name LIKE '%+ve Nm%' and date(date_test_requested) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("BacterialMeningitis(+ve Nm)");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Bacterial Meningitis(+ve Nm)");
        return cd;
    }

    public CohortDefinition bacterialTestPositiveSp(int bacteriaTest1, int bacteriaTest2) {
        String sqlQuery = "select patient_id from kenyaemr_etl.etl_laboratory_extract where (lab_test = " + bacteriaTest1 + " or lab_test = " + bacteriaTest2 + " ) and result_name LIKE '%+ve Sp%' and date(date_test_requested) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("BacterialMeningitis(+ve Sp)");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Bacterial Meningitis(+ve Sp)");
        return cd;
    }

    public CohortDefinition bacterialTestPositiveHInfluenza(int bacteriaTest) {
        String sqlQuery = "select patient_id from kenyaemr_etl.etl_laboratory_extract where lab_test = " + bacteriaTest + " and result_name LIKE '%+ve H influenza%' and date(date_test_requested) between date(:startDate) and date(:endDate);";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("BacterialMeningitis(+ve H influenza)");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Bacterial Meningitis(+ve H influenza)");
        return cd;
    }


}
