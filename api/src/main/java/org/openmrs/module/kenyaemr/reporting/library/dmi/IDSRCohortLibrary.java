/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.dmi;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by dev on 2/19/24.
 */

/**
 * Library of cohort definitions used for IDSR cases
 */

@Component
public class IDSRCohortLibrary {

    /**
     * Dysentery cases
     * @return
     */
    public CohortDefinition dysenteryCases() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select a.patient_id\n" +
                "from (select patient_id, group_concat(c.complaint) as complaint\n" +
                "      from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "      where c.complaint in (117671, 142412)\n" +
                "        and date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
                "      group by patient_id) a\n" +
                "where FIND_IN_SET(117671, a.complaint) > 0\n" +
                "  and FIND_IN_SET(142412, a.complaint) > 0;";
        cd.setName("dysenteryCases");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Dysentery cases");

        return cd;
    }

    /**
     * Cholera cases
     * @return
     */
    public CohortDefinition choleraCases() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select a.patient_id\n" +
                "                from (select patient_id, c.visit_date,group_concat(c.complaint) as complaint, DATE_SUB(c.visit_date, INTERVAL c.complaint_duration DAY) as complaint_date,\n" +
                "                            c.complaint_duration\n" +
                "                      from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "                      where c.complaint in (161887,122983)\n" +
                "                        and date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
                "                      group by patient_id) a\n" +
                "                         join kenyaemr_etl.etl_patient_demographics d on a.patient_id = d.patient_id\n" +
                "                where timestampdiff(YEAR,date(d.DOB),coalesce(date(DATE_SUB(a.visit_date, INTERVAL a.complaint_duration DAY)),date(a.visit_date))) > 2 and FIND_IN_SET(122983, a.complaint) > 0\n" +
                "                  and FIND_IN_SET(161887, a.complaint) > 0;";
        cd.setName("choleraCases");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Cholera cases");

        return cd;
    }

    /**
     * ILI Cases
     * @return
     */
    public CohortDefinition iliCases() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select a.patient_id\n" +
                "from (select patient_id, c.complaint as complaint, DATE_SUB(c.visit_date, INTERVAL c.complaint_duration DAY) as complaint_date, c.visit_date\n" +
                "      from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "      where c.complaint = 143264\n" +
                "        and c.complaint_duration < 10\n" +
                "        and date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
                "      group by patient_id) a\n" +
                "         join openmrs.visit v\n" +
                "              on a.patient_id = v.patient_id and date(a.visit_date) = date(v.date_started) and v.visit_type_id = 1\n" +
                "         join kenyaemr_etl.etl_patient_triage t\n" +
                "              on a.patient_id = t.patient_id and date(t.visit_date) = date(v.date_started) and t.temperature >= 38;";
        cd.setName("iliCases");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("ILI cases");

        return cd;
    }

    /**
     * SARI Cases
     * @return
     */
    public CohortDefinition sariCases() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select a.patient_id\n" +
                "from (select patient_id, c.complaint as complaint, DATE_SUB(c.visit_date, INTERVAL c.complaint_duration DAY) as complaint_date, c.visit_date\n" +
                "      from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "      where c.complaint = 143264\n" +
                "        and c.complaint_duration < 10\n" +
                "        and date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
                "      group by patient_id) a\n" +
                "         join openmrs.visit v\n" +
                "              on a.patient_id = v.patient_id and date(a.visit_date) = date(v.date_started) and v.visit_type_id = 3\n" +
                "         join kenyaemr_etl.etl_patient_triage t\n" +
                "              on a.patient_id = t.patient_id and date(t.visit_date) = date(v.date_started) and t.temperature >= 38;";
        cd.setName("sariCases");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("SARI cases");

        return cd;
    }

    /**
     * Riftvalley Fever Cases
     * @return
     */
    public CohortDefinition riftvalleyFeverCases() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select a.patient_id\n" +
                "from (select patient_id, c.visit_date,group_concat(c.complaint) as complaint,\n" +
                "    CASE\n" +
                "    WHEN group_concat(concat_ws('|',c.complaint,c.complaint_duration))  LIKE '%140238%' THEN\n" +
                "    SUBSTRING_INDEX(SUBSTRING_INDEX(group_concat(concat_ws('|',c.complaint,c.complaint_duration)) , '|', -1), ',', 1)\n" +
                "    END AS fever_duration_from_days\n" +
                "      from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "      where c.complaint in (140238,141830,136443,135367)\n" +
                "        and date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
                "      group by patient_id) a\n" +
                "         join kenyaemr_etl.etl_patient_demographics d on a.patient_id = d.patient_id\n" +
                "         join kenyaemr_etl.etl_patient_triage t\n" +
                "              on a.patient_id = t.patient_id and date(t.visit_date) between date(:startDate) and date(:endDate) and\n" +
                "                 t.temperature > 37.5 and date(a.visit_date) between date(:startDate) and date(:endDate)\n" +
                "where FIND_IN_SET(140238, a.complaint) > 0\n" +
                "  and (FIND_IN_SET(141830, a.complaint) > 0 and a.fever_duration_from_days > 2)\n" +
                "  and FIND_IN_SET(136443, a.complaint) > 0\n" +
                "  and FIND_IN_SET(135367, a.complaint) > 0;";
        cd.setName("riftvalleyFeverCases");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Riftvalley Fever Cases");

        return cd;
    }

    /**
     * Malaria Cases
     * @return
     */
    public CohortDefinition malariaCases() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select a.patient_id\n" +
                "from (select patient_id, c.visit_date,group_concat(c.complaint) as complaint,\n" +
                "             CASE\n" +
                "                 WHEN group_concat(concat_ws('|',c.complaint,c.complaint_duration))  LIKE '%140238%' THEN\n" +
                "                     SUBSTRING_INDEX(SUBSTRING_INDEX(group_concat(concat_ws('|',c.complaint,c.complaint_duration)) , '|', -1), ',', 1)\n" +
                "                 END AS fever_duration_from_days\n" +
                "      from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "      where c.complaint in (140238,139084,871)\n" +
                "        and date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
                "      group by patient_id) a\n" +
                "         join kenyaemr_etl.etl_patient_demographics d on a.patient_id = d.patient_id\n" +
                "         join kenyaemr_etl.etl_patient_triage t\n" +
                "              on a.patient_id = t.patient_id and date(t.visit_date) between date(:startDate) and date(:endDate) and\n" +
                "                 t.temperature > 37.5 and date(a.visit_date) between date(:startDate) and date(:endDate)\n" +
                "where FIND_IN_SET(140238, a.complaint) > 0\n" +
                "  and FIND_IN_SET(139084, a.complaint) > 0\n" +
                "  and FIND_IN_SET(871, a.complaint) > 0\n" +
                "  and a.fever_duration_from_days > 1;";
        cd.setName("malariaCases");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Malaria cases");

        return cd;
    }

    /**
     * Chikungunya Cases
     * @return
     */
    public CohortDefinition chikungunyaCases() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select a.patient_id\n" +
                "        from (select patient_id, c.visit_date,group_concat(c.complaint) as complaint,\n" +
                "        CASE\n" +
                "                         WHEN group_concat(concat_ws('|',c.complaint,c.complaint_duration))  LIKE '%140238%' THEN\n" +
                "                             SUBSTRING_INDEX(SUBSTRING_INDEX(group_concat(concat_ws('|',c.complaint,c.complaint_duration)) , '|', -1), ',', 1)\n" +
                "                         END AS fever_duration_from_days\n" +
                "              from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "              where c.complaint in (140238, 116558)\n" +
                "                and date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
                "              group by patient_id) a\n" +
                "                 join kenyaemr_etl.etl_patient_demographics d on a.patient_id = d.patient_id\n" +
                "                 join kenyaemr_etl.etl_patient_triage t\n" +
                "                      on a.patient_id = t.patient_id and date(t.visit_date) between date(:startDate) and date(:endDate) and\n" +
                "                         t.temperature > 38.5 and date(a.visit_date) between date(:startDate) and date(:endDate)\n" +
                "        where fever_duration_from_days > 2\n" +
                "        and FIND_IN_SET(140238, a.complaint) > 0\n" +
                "          and FIND_IN_SET(116558, a.complaint) > 0";
        cd.setName("chikungunyaCases");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Chikungunya cases");

        return cd;
    }

    /**
     * Poliomyelitis Cases
     * @return
     */
    public CohortDefinition poliomyelitisCases() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select a.patient_id\n" +
                "from (select patient_id, c.visit_date,group_concat(c.complaint) as complaint\n" +
                "      from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "      where c.complaint = 157498\n" +
                "        and date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
                "      group by patient_id) a\n" +
                "         join kenyaemr_etl.etl_patient_demographics d on a.patient_id = d.patient_id and\n" +
                "                                                         timestampdiff(YEAR,d.DOB,a.visit_date) < 15\n" +
                "         join kenyaemr_etl.etl_patient_triage t\n" +
                "              on a.patient_id = t.patient_id and date(t.visit_date) between date(:startDate) and date(:endDate)\n" +
                "where FIND_IN_SET(157498, a.complaint) > 0;";
        cd.setName("poliomyelitisCases");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Poliomyelitis Cases");

        return cd;
    }
    /**
     * viral Haemorrhagic Fever Cases
     * @return
     */
    public CohortDefinition viralHaemorrhagicFeverCases() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select a.patient_id\n" +
                "from (select patient_id, c.visit_date,group_concat(c.complaint) as complaint,\n" +
                "             CASE\n" +
                "                 WHEN group_concat(concat_ws('|',c.complaint,c.complaint_duration))  LIKE '%140238%' THEN\n" +
                "                     SUBSTRING_INDEX(SUBSTRING_INDEX(group_concat(concat_ws('|',c.complaint,c.complaint_duration)) , '|', -1), ',', 1)\n" +
                "                 END AS fever_duration_from_days\n" +
                "      from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "      where c.complaint in (140238,162628)\n" +
                "        and date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
                "      group by patient_id) a\n" +
                "where FIND_IN_SET(140238, a.complaint) > 0\n" +
                "  and FIND_IN_SET(162628, a.complaint) > 0\n" +
                "  and a.fever_duration_from_days >= 3;";
        cd.setName("viralHaemorrhagicFeverCases");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("viral Haemorrhagic Fever Cases");

        return cd;}
    /**
     * Measles Cases
     * @return
     */
    public CohortDefinition measlesCases() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select a.patient_id\n" +
                "        from (select patient_id, c.visit_date,group_concat(c.complaint) as complaint,\n" +
                "                    CASE\n" +
                "                         WHEN group_concat(concat_ws('|',c.complaint,c.complaint_duration))  LIKE '%140238%' THEN\n" +
                "                             SUBSTRING_INDEX(SUBSTRING_INDEX(group_concat(concat_ws('|',c.complaint,c.complaint_duration)) , '|', -1), ',', 1)\n" +
                "                         END AS fever_duration_from_days\n" +
                "              from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "              where c.complaint in (140238,512,106,516,143264)\n" +
                "                and date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
                "              group by patient_id) a\n" +
                "        where fever_duration_from_days > 2\n" +
                "          and FIND_IN_SET(140238, a.complaint) > 0\n" +
                "          and FIND_IN_SET(512, a.complaint) > 0\n" +
                "          and FIND_IN_SET(106, a.complaint) > 0\n" +
                "          and FIND_IN_SET(516, a.complaint) > 0\n" +
                "          and FIND_IN_SET(143264, a.complaint) > 0;";
        cd.setName("measlesCases");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Measles Cases");

        return cd;
    }
}