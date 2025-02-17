/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.specialClinics;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH731Greencard.ETLMoh731GreenCardCohortLibrary;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Library of cohort definitions used specifically in the MOH711 report
 */
@Component
public class SpecialClinicsCohortLibrary {
    @Autowired
    private ETLMoh731GreenCardCohortLibrary moh731GreenCardCohort;

    // TODO



    public CohortDefinition visitType(int visitType, String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select f.encounter_id from kenyaemr_etl.etl_special_clinics f where f.visit_type = "+visitType+" and f.visit_date between date(:startDate) and date(:endDate) and f.special_clinic = '" + specialClinic + "';";
        cd.setName("Visit Type");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Visit Type");
        return cd;
    }

    public CohortDefinition otIntervention(int interventionType, String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select f.encounter_id from kenyaemr_etl.etl_special_clinics f where f.ot_intervention = "+interventionType+" and f.visit_date between date(:startDate) and date(:endDate) and f.special_clinic = '" + specialClinic + "';";
        cd.setName("OT Intervention");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("OT Intervention");
        return cd;
    }


    public CohortDefinition totalCountVisits(int newVisit, int reVisit,String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = String.format(
                "select f.encounter_id from kenyaemr_etl.etl_special_clinics f where f.visit_date between date(:startDate) and date(:endDate)\n" +
                        "and f.visit_type in (%d, %d) and f.special_clinic = '" + specialClinic + "';", newVisit, reVisit) ;
        cd.setName("Total Number Count of Visits");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total Number Count of Visits");
        return cd;
    }
    public CohortDefinition totalATsDispensed(int intervention, int referredIn,int referredOut,String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = String.format(
                "select f.encounter_id from kenyaemr_etl.etl_special_clinics f where f.visit_date between date(:startDate) and date(:endDate)\n" +
                        "and f.ot_intervention in (%d, %d,%d) and f.special_clinic = '" + specialClinic + "';", intervention, referredIn,referredOut) ;
        cd.setName("Total ATs Dispensed");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total ATs Dispensed");
        return cd;
    }
    public CohortDefinition totalNumberOfATsDispensed(int intervention, int referredIn,int referredOut,  int visitType, String specialClinic) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("totalATsDispensed", ReportUtils.map(totalATsDispensed(intervention,referredIn,referredOut, specialClinic), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("visitType", ReportUtils.map(visitType(visitType, specialClinic), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("totalATsDispensed AND visitType");
        return cd;
    }

public CohortDefinition neurodevelopmental(String specialClinic) {
    SqlCohortDefinition cd = new SqlCohortDefinition();
    String sqlQuery = "select he.patient_id from kenyaemr_etl.etl_special_clinics he where he.neuron_developmental_findings in (152492,144481,117470,126208) and he.special_clinic = '" + specialClinic + "' and he.visit_date between date(:startDate) and date(:endDate);";
    cd.setName("Neurodevelopmental");
    cd.setQuery(sqlQuery);
    cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
    cd.addParameter(new Parameter("endDate", "End Date", Date.class));
    cd.setDescription("Neurodevelopmental");
    return cd;
}

    public CohortDefinition learningFindings(String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery ="select he.patient_id from kenyaemr_etl.etl_special_clinics he where he.visit_date between date(:startDate) and date(:endDate) and he.learning_findings in (118795, 118800, 141644,153271,121529,155205,126456) and he.special_clinic = '" + specialClinic + "';";
        cd.setName("Learning Findings");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Learning Findings");
        return cd;
    }
    public CohortDefinition neurodiversityConditions(String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery ="select he.patient_id from kenyaemr_etl.etl_special_clinics he where he.visit_date between date(:startDate) and date(:endDate) and he.neurodiversity_conditions in (121317,121303) and he.special_clinic = '" + specialClinic + "';";
        cd.setName("Neurodiversity Conditions");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Neurodiversity Conditions");
        return cd;
    }

    public CohortDefinition childrenWithIntellectualDisabilities(String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery ="select he.patient_id from kenyaemr_etl.etl_special_clinics he where he.visit_date between date(:startDate) and date(:endDate) and he.disability_classification = 156923 and he.special_clinic = '" + specialClinic + "';";
        cd.setName("Children with Intellectual Disabilities");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Children with Intellectual Disabilities");
        return cd;
    }

    public CohortDefinition delayedDevelopmentalMilestones(String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery ="select he.patient_id from kenyaemr_etl.etl_special_clinics he where he.visit_date between date(:startDate) and date(:endDate) and he.disability_classification = 142616 and he.special_clinic = '" + specialClinic + "';";
        cd.setName("Delayed developmental milestones");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Delayed developmental milestones");
        return cd;
    }

    public CohortDefinition childrenTrainedOnAT(String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery ="select he.patient_id from kenyaemr_etl.etl_special_clinics he where he.visit_date between date(:startDate) and date(:endDate) and he.neurodiversity_conditions in (121317,121303) and he.special_clinic = '" + specialClinic + "';";
        cd.setName("No. of children  identified, prescribed, provided and trained on assistive technology");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of children  identified, prescribed, provided and trained on assistive technology");
        return cd;
    }
    public CohortDefinition totalNoOfOtInterventions(int newVisit, int reVisit,  int interventionType, String specialClinic) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("totalCountVisits", ReportUtils.map(totalCountVisits(newVisit,reVisit, specialClinic), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("interventionType", ReportUtils.map(otIntervention(interventionType, specialClinic), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("totalCountVisits AND interventionType");
        return cd;
    }

    public CohortDefinition noOtIntervention(int visitType, int interventionType, String specialClinic) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("visitType", ReportUtils.map(visitType(visitType, specialClinic), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("interventionType", ReportUtils.map(otIntervention(interventionType, specialClinic), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("visitType AND interventionType");
        return cd;
    }




}