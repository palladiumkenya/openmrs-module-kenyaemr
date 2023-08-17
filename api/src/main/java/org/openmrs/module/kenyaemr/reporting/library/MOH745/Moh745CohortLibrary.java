/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.MOH745;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.KPTypeDataDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Component
public class Moh745CohortLibrary {

    protected static final Log log = LogFactory.getLog(Moh745CohortLibrary.class);


    public CohortDefinition patientVisitType(String visitType) {

        String sqlQuery = "select i.patient_id from kenyaemr_etl.etl_cervical_cancer_screening i where i.visit_type = '"+visitType+"' and  i.visit_date between date(:startDate) and date(:endDate) group by i.patient_id;";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("patientVisitType");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Cervical Cancer Patients Visit Type");
        return cd;
    }

    public CohortDefinition receivedViaScreeningCl(String visitType) {
        String sqlQuery = "select i.patient_id from kenyaemr_etl.etl_cervical_cancer_screening i where i.via_vili_screening_method = 'VIA' and i.visit_type = '"+visitType+"' and i.visit_date between date(:startDate) and date(:endDate) group by i.patient_id;";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("viaScreeningMethod");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Cervical Cancer Via Screening Method");
        return cd;
    }

    public CohortDefinition positiveViaResult(String visitType) {
        String sqlQuery = "select i.patient_id from kenyaemr_etl.etl_cervical_cancer_screening i where i.via_vili_screening_method = 'VIA' and i.via_vili_screening_result ='Positive' and i.visit_type = '"+visitType+"' and i.visit_date between date(:startDate) and date(:endDate) group by i.patient_id;";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("positiveViaResult");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Positivie VIA result");
        return cd;
    }

    public CohortDefinition receivedPapSmearScreeningCl(String visitType) {
        String sqlQuery = "select i.patient_id from kenyaemr_etl.etl_cervical_cancer_screening i where i.pap_smear_screening_method ='Pap Smear' and i.visit_type = '"+visitType+"' and i.visit_date between date(:startDate) and date(:endDate) group by i.patient_id;";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("papSmearScreeningMethod");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Cervical Cancer Pap smear Screening Method");
        return cd;
    }

    public CohortDefinition positivePapSmearResult(String visitType) {
        String sqlQuery = "select i.patient_id from kenyaemr_etl.etl_cervical_cancer_screening i where i.pap_smear_screening_method ='Pap Smear' and i.pap_smear_screening_result ='Positive' and i.visit_type = '"+visitType+"' and i.visit_date between date(:startDate) and date(:endDate) group by i.patient_id;";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("positivePapSmearResult");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Positivie Pap smear result");
        return cd;
    }

    public CohortDefinition receivedHpvTestScreeningCl(String visitType) {
        String sqlQuery = "select i.patient_id from kenyaemr_etl.etl_cervical_cancer_screening i where i.hpv_screening_method ='HPV' and i.visit_type = '"+visitType+"' and i.visit_date between date(:startDate) and date(:endDate) group by i.patient_id;";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("hpvTestScreeningMethod");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Cervical Cancer HPV test Screening Method");
        return cd;
    }

    public CohortDefinition positiveHpvResult(String visitType) {
        String sqlQuery = "select i.patient_id from kenyaemr_etl.etl_cervical_cancer_screening i where i.hpv_screening_method ='HPV' and i.hpv_screening_result ='Positive' and i.visit_type = '"+visitType+"' and i.visit_date between date(:startDate) and date(:endDate) group by i.patient_id;";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("positiveViaResult");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Positivie VIA result");
        return cd;
    }

    public CohortDefinition suspiciousScreeningCl(String visitType) {
        String sqlQuery = "select i.patient_id from kenyaemr_etl.etl_cervical_cancer_screening i where ((i.hpv_screening_method ='HPV' and i.hpv_screening_result in ('Suspicious for Cancer','Presumed')) " +
        "or (i.via_vili_screening_method = 'VIA' and i.via_vili_screening_result in ('Suspicious for Cancer' ,'Presumed'))" +
        "or (i.colposcopy_screening_method ='Colposcopy(for positive HPV,VIA or PAP smear)' and i.colposcopy_screening_result in ('Suspicious for Cancer', 'Presumed')))" +
        "and i.visit_type = '"+visitType+"' and i.visit_date between date(:startDate) and date(:endDate) group by i.patient_id;";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("suspiciousCancer");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Suspicious for Cancer");
        return cd;
    }

    public CohortDefinition patientTreatment(String[] treatmentMethod) {

        String val = StringUtils.join(treatmentMethod,"','");

        String sqlQuery = "select i.patient_id from kenyaemr_etl.etl_cervical_cancer_screening i where " +
                " i.treatment_method IN ('"+val+"') and visit_date between date(:startDate) and date(:endDate) group by i.patient_id;";
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName("patientTreatmentMethod");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("Cervical Cancer Treatment Method");
        return cd;
    }

    public CohortDefinition treatmentMethodCl(String[] treatmentMethod, String visitType) {
        CompositionCohortDefinition cd = new CompositionCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.addSearch("receivedPositiveScreening", ReportUtils.map(patientTreatment(treatmentMethod), "startDate=${startDate},endDate=${endDate}"));
        cd.addSearch("patientVisitType", ReportUtils.map(patientVisitType(visitType), "startDate=${startDate},endDate=${endDate}"));
        cd.setCompositionString("receivedPositiveScreening AND patientVisitType");
        return cd;
    }

    /*HIV Positive Clients Screened*/
    public CohortDefinition HIVPositiveClientsScreenedCl(String visitType) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select i.patient_id from kenyaemr_etl.etl_cervical_cancer_screening i left join (select h.patient_id from kenyaemr_etl.etl_hiv_enrollment h " +
                "where h.visit_date <= date(:endDate)) h on h.patient_id = i.patient_id left join (select t.patient_id from kenyaemr_etl.etl_hts_test t where " +
                "t.final_test_result = 'Positive' and t.test_type = 2 and t.visit_date <= date(:endDate)) t on t.patient_id = i.patient_id where" +
                " i.visit_type = '"+visitType+"' and (h.patient_id is not null or t.patient_id is not null) and i.visit_date between date(:startDate) and date(:endDate)" +
                "group by i.patient_id;";

        cd.setName("HIV Positive Clients Screened");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("HIV Positive Clients Screened");

        return cd;
    }

    /*HIV Positive With Positive Screening Results*/
    public CohortDefinition HIVPositiveClientsScreenedWithPositiveResultsCl(String visitType) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        String sqlQuery = "select i.patient_id from kenyaemr_etl.etl_cervical_cancer_screening i left join (select h.patient_id from kenyaemr_etl.etl_hiv_enrollment h " +
                "where h.visit_date <= date(:endDate)) h on h.patient_id = i.patient_id left join (select t.patient_id from kenyaemr_etl.etl_hts_test t where " +
                "t.final_test_result = 'Positive' and t.test_type = 2 and t.visit_date <= date(:endDate)) t on t.patient_id = i.patient_id where i.visit_type = '"+visitType+"' " +
                " and i.screening_result = 'Positive' and (h.patient_id is not null or t.patient_id is not null) and  i.visit_date between date(:startDate) and " +
                "date(:endDate) group by i.patient_id;";

        cd.setName("HIV Positive With Positive Screening Results");
        cd.setQuery(sqlQuery);
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setDescription("HIV Positive With Positive Screening Results");

        return cd;
    }

}
