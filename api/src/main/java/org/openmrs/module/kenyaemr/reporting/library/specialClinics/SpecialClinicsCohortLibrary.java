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
 * Library of cohort definitions used specifically in Special report
 */
@Component
public class SpecialClinicsCohortLibrary {
    public CohortDefinition visitType(int visitType, String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select f.patient_id from kenyaemr_etl.etl_special_clinics f where f.visit_type = '"+visitType+"' and f.visit_date between date(:startDate) and date(:endDate) and f.special_clinic_form_uuid = '" + specialClinic + "';";
        cd.setName("Visit Type");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Visit Type");
        return cd;
    }

    public CohortDefinition otIntervention(String interventionType, String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select f.patient_id from kenyaemr_etl.etl_special_clinics f where f.ot_intervention = '"+interventionType+"' and f.visit_date between date(:startDate) and date(:endDate) and f.special_clinic_form_uuid = '" + specialClinic + "';";
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
                "select f.patient_id from kenyaemr_etl.etl_special_clinics f where f.visit_date between date(:startDate) and date(:endDate)\n" +
                        "and f.visit_type in (%d, %d) and f.special_clinic_form_uuid = '" + specialClinic + "';", newVisit, reVisit) ;
        cd.setName("Total Number Count of Visits");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total Number Count of Visits");
        return cd;
    }

    public CohortDefinition totalATsDispensed(String intervention, String referredIn, String referredOut, String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = String.format(
                "select f.patient_id from kenyaemr_etl.etl_special_clinics f where f.visit_date between date(:startDate) and date(:endDate)\n" +
                        "and f.ot_intervention in ('%s', '%s', '%s') and f.special_clinic_form_uuid = '" + specialClinic + "';", intervention, referredIn, referredOut);
        cd.setName("Total ATs Dispensed");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Total ATs Dispensed");
        return cd;
    }
    public CohortDefinition totalNumberOfATsDispensed(String intervention, String referredIn,String referredOut,  int visitType, String specialClinic) {
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
        String sqlQuery = "SELECT DISTINCT v.patient_id\n" +
                "FROM kenyaemr_etl.etl_special_clinics v\n" +
                "WHERE v.neuron_developmental_findings LIKE '%Cerebral palsy%'\n" +
                "   OR v.neuron_developmental_findings LIKE '%Down syndrome%'\n" +
                "   OR v.neuron_developmental_findings LIKE '%Hydrocephalus%'\n" +
                "   OR v.neuron_developmental_findings LIKE '%Spina bifida%'\n" +
                "   AND v.visit_date between date(:startDate) and date(:endDate)\n" +
                "   AND v.special_clinic_form_uuid = '" + specialClinic + "';";
        cd.setName("Neurodevelopmental");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Neurodevelopmental");
        return cd;
    }

    public CohortDefinition learningFindings(String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "SELECT DISTINCT v.patient_id\n" +
                "FROM kenyaemr_etl.etl_special_clinics v\n" +
                "WHERE v.learning_findings LIKE '%Dysgraphia%'\n" +
                "   OR v.learning_findings LIKE '%Auditory processing%'\n" +
                "   OR v.learning_findings LIKE '%Dyslexia%'\n" +
                "   OR v.learning_findings LIKE '%Dyscalculia%'\n" +
                "   OR v.learning_findings LIKE '%Language processing disorder%'\n" +
                "   OR v.learning_findings LIKE '%Nonverbal learning disabilities%'\n" +
                "   OR v.learning_findings LIKE '%Visual perceptual/visual motor deficit%'\n" +
                "   AND v.visit_date between date(:startDate) and date(:endDate)\n" +
                "   AND v.special_clinic_form_uuid = '" + specialClinic + "';";

        cd.setName("Learning Findings");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Learning Findings");
        return cd;
    }
    public CohortDefinition neurodiversityConditions(String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "SELECT DISTINCT v.patient_id\n" +
                "FROM kenyaemr_etl.etl_special_clinics v\n" +
                "WHERE v.neurodiversity_conditions LIKE '%ADHD(Attention deficit hyperactivity disorder)%'\n" +
                "   OR v.neurodiversity_conditions LIKE '%Autism%'\n" +
                "   AND v.visit_date between date(:startDate) and date(:endDate)\n" +
                "   AND v.special_clinic_form_uuid = '" + specialClinic + "';";
        cd.setName("Neurodiversity Conditions");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Neurodiversity Conditions");
        return cd;
    }

    public CohortDefinition childrenWithIntellectualDisabilities(String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "SELECT DISTINCT v.patient_id\n" +
                "FROM kenyaemr_etl.etl_special_clinics v\n" +
                "WHERE v.disability_classification LIKE '%Intelectual disability%'\n" +
                "   AND v.visit_date between date(:startDate) and date(:endDate)\n" +
                "   AND v.special_clinic_form_uuid = '" + specialClinic + "';";
        cd.setName("Children with Intellectual Disabilities");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Children with Intellectual Disabilities");
        return cd;
    }

    public CohortDefinition delayedDevelopmentalMilestones(String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "SELECT DISTINCT v.patient_id\n" +
                "FROM kenyaemr_etl.etl_special_clinics v\n" +
                "WHERE v.disability_classification LIKE '%Delayed developmental milestone%'\n" +
                "   AND v.visit_date between date(:startDate) and date(:endDate)\n" +
                "   AND v.special_clinic_form_uuid = '" + specialClinic + "';";
        cd.setName("Delayed developmental milestones");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Delayed developmental milestones");
        return cd;
    }
    public CohortDefinition childrenTrainedOnAT(String specialClinic) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "SELECT DISTINCT v.patient_id\n" +
                "FROM kenyaemr_etl.etl_special_clinics v\n" +
                "WHERE v.assistive_technology LIKE '%None%'\n" +
                "   OR v.assistive_technology LIKE '%Communication%'\n" +
                "   OR v.assistive_technology LIKE '%Self Care%'\n" +
                "   OR v.assistive_technology LIKE '%Physical%'\n" +
                "   OR v.assistive_technology LIKE '%Cognitive/Intellectual%'\n" +
                "   OR v.assistive_technology LIKE '%Hearing Devices%'\n" +
                "   AND v.visit_date between date(:startDate) and date(:endDate)\n" +
                "   AND v.special_clinic_form_uuid = '" + specialClinic + "';";
        cd.setName("No. of children  identified, prescribed, provided and trained on assistive technology");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("No. of children  identified, prescribed, provided and trained on assistive technology");
        return cd;
    }


    public CohortDefinition totalNoOfOtInterventions(int newVisit, int reVisit,  String interventionType, String specialClinic) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("totalCountVisits", ReportUtils.map(totalCountVisits(newVisit,reVisit, specialClinic), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("interventionType", ReportUtils.map(otIntervention(interventionType, specialClinic), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("totalCountVisits AND interventionType");
        return cd;
    }

    public CohortDefinition noOtIntervention(int visitType, String interventionType, String specialClinic) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("visitType", ReportUtils.map(visitType(visitType, specialClinic), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("interventionType", ReportUtils.map(otIntervention(interventionType, specialClinic), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("visitType AND interventionType");
        return cd;
    }

}