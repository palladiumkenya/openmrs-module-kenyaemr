/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.threepm;

/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.kp.KPMoh731PlusCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.kp.KPMonthlyReportCohortLibrary;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by dev on 1/14/17.
 */

/**
 * Library of cohort definitions used specifically in the MOH731 report based on ETL tables. It has incorporated green card components
 */

@Component
public class ThreePMCohortLibrary {

    @Autowired
    private DatimCohortLibrary datimCohorts;
    @Autowired
    private KPMoh731PlusCohortLibrary moh731PlusCohortLibrary;
    @Autowired
    private KPMonthlyReportCohortLibrary kpifCohorts;
    /**
     * Screened for HIV test
     * @return
     */
    public CohortDefinition htsScreened() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.patient_id from kenyaemr_etl.etl_hts_eligibility_screening s where date(s.visit_date) between date(:startDate) and date(:endDate);";
        cd.setName("totalHTSScreened");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total HTS Screened");

        return cd;
    }

    public CohortDefinition eligibleForHIVTest() {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select s.patient_id\n" +
                "from kenyaemr_etl.etl_hts_eligibility_screening s\n" +
                "where s.visit_date between date(:startDate) and date(:endDate)\n" +
                "group by s.patient_id\n" +
                "having mid(max(concat(s.visit_date, s.eligible_for_test)), 11) = 1065;";
        cd.setName("Eligible for HIV test");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Eligible for HIV test");

        return cd;
    }

    public CohortDefinition kpCurrOnPrEPWithSTI(String kpType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("diagnosedWithSTISQL",
                ReportUtils.map(moh731PlusCohortLibrary.diagnosedWithSTISQL(), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("kpPrepCurrDice",
                ReportUtils.map(kpifCohorts.kpPrepCurrDice(kpType), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("kpPrepCurrDice AND diagnosedWithSTISQL");
        return cd;
    }

    public CohortDefinition ppType(String ppType) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
                + "where date(c.visit_date) <= date(:endDate)\n"
                + "group by c.client_id having mid(max(concat(date(c.visit_date), c.priority_population_type)), 11) = '" + ppType
                + "';";
        cd.setName("ppType");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("ppType");
        return cd;
    }

    public CohortDefinition ppCurrentOnARTOffsite() {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        //cd.addSearch("ppType", ReportUtils.map(ppType(ppType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("currOnARTOffsite",ReportUtils.map(moh731PlusCohortLibrary.reportedCurrentOnARTElsewhereClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("currOnARTOffsite");
        return cd;
    }

    public CohortDefinition ppCurrentOnARTOnSite() {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
      //  cd.addSearch("ppType", ReportUtils.map(ppType(ppType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("currentOnART",ReportUtils.map(datimCohorts.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("currentOnART");
        return cd;
    }

    public CohortDefinition kpCurrentOnARTOffsite(String ppType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("kpType", ReportUtils.map(kpifCohorts.kpType(ppType), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("currOnARTOffsite",ReportUtils.map(moh731PlusCohortLibrary.reportedCurrentOnARTElsewhereClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("kpType AND currOnARTOffsite");
        return cd;
    }
}

