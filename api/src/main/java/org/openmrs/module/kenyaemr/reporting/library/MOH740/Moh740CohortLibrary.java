/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.MOH740;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Moh740CohortLibrary {

    protected static final Log log = LogFactory.getLog(Moh740CohortLibrary.class);


    public CohortDefinition cumulativePatientWithDiabetes() {

        String sqlQuery = "";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Cumulative no. of diabetes");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Cumulative no. of diabetes");
        return cd;
    }
    public CohortDefinition cumulativePatientWithHypertension() {

        String sqlQuery = "";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Cumulative no. of Hypertension");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Cumulative no. of Hypertension");
        return cd;
    }

    public CohortDefinition patientWithDiabetesAndHypertension() {

        String sqlQuery = "";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Diagnosed Diabetes and Hypertension");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Diagnosed Diabetes and Hypertension");
        return cd;
    }

    public CohortDefinition patientWithPreExistingConditions() {

        String sqlQuery = "";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Pre-Existing Diabetes or Hypertension\"");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Pre-Existing Diabetes or Hypertension");
        return cd;
    }
    public CohortDefinition patientFirstVisitToClinic() {

        String sqlQuery = "";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("First Visit To Clinic");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("First Visit To Clinic");
        return cd;
    }
    public CohortDefinition cumulativePatientInCare() {

        String sqlQuery = "";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("Cumulative No. of Patients");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Cumulative No. of Patients");
        return cd;
    }
    public CohortDefinition patientWithDiabetesByType() {

        String sqlQuery = "";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("No. of Diabetes By Type");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of Diabetes By Type");
        return cd;
    }
    public CohortDefinition patientWithcondition() {

        String sqlQuery = "";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("No. of Patient Condition");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of Patient Condition");
        return cd;
    }
    public CohortDefinition patientWithHypertension() {

        String sqlQuery = "";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("No. of Hypertension");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of Hypertension");
        return cd;
    }

}
