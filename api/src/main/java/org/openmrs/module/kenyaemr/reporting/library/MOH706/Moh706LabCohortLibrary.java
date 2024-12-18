/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.MOH706;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class Moh706LabCohortLibrary {
	
	//1.2 Urine Analysis
	
	public CohortDefinition getTotalTestsByConcept(Integer labSetConceptId) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get total tests by lab concept");
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"    join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"    where le.set_member_conceptId = " + labSetConceptId + "\n" +
			"  and date(le.visit_date) between :startDate and :endDate;"

		);
		return sql;
	}

    public CohortDefinition getAllUrineAnalysisGlucosePositives(Integer testConceptId) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Get positive urine analysis patients - glucose");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"    join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"    where le.set_member_conceptId = " + testConceptId + " and le.test_result in (1362,1363,1364,1365)\n" +
			"  and date(le.visit_date) between :startDate and :endDate;"

        );
        return sql;
    }

    public CohortDefinition getAllUrineAnalysisKetonesPositives(Integer testConceptId) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Get urine analysis patients - ketones");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "E    nd Date", Date.class));
        sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"    join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"    where le.set_member_conceptId = " + testConceptId + " and le.test_result in (1362,1363,1364,1365)\n" +
			" and date(le.visit_date) between :startDate and :endDate;"

        );
        return sql;
    }

    public CohortDefinition getAllUrineAnalysisProteinsPositives(Integer testConceptId) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Get urine analysis patients - Proteins");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"   join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"    where le.set_member_conceptId = " + testConceptId + " and le.test_result in (1874,1362,1363,1364)\n" +
			"  and date(le.visit_date) between :startDate and :endDate;"

        );
        return sql;
    }

	public CohortDefinition getAllUrineAnalysisPusCellsPositives(Integer testConceptId) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get urine analysis patients - Pus cells");
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"   join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"    where le.set_member_conceptId = " + testConceptId + " and le.test_result in (2001230,2001232,2001233,2001234)\n" +
			"  and date(le.visit_date) between :startDate and :endDate;"

		);
		return sql;
	}

	public CohortDefinition getAllUrineAnalysisSHaematobiumPositives(Integer testConceptId) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get urine analysis patients - S haemoatobium");
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"   join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"    where le.set_member_conceptId = " + testConceptId + " and le.test_result in (1000537,164371)\n" +
			"  and date(le.visit_date) between :startDate and :endDate;"

		);
		return sql;
	}
	public CohortDefinition getAllUrineAnalysisTVaginatisPositives(Integer testConceptId) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get urine analysis patients - T Vaginatis");
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"   join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"    where le.set_member_conceptId = " + testConceptId + " and le.test_result in (1000537,164371)\n" +
			"  and date(le.visit_date) between :startDate and :endDate;"

		);
		return sql;
	}

	public CohortDefinition getAllUrineAnalysisYeastCellsPositives(Integer testConceptId) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get urine analysis patients - Yeast Cells");
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"   join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"    where le.set_member_conceptId = " + testConceptId + " and le.test_result in (1363,159416,1364,1362)\n" +
			"  and date(le.visit_date) between :startDate and :endDate;"

		);
		return sql;
	}

	public CohortDefinition getAllUrineAnalysisBacteriaPositives(Integer testConceptId) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get urine analysis patients - Bacteria Cells");
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"   join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"    where le.set_member_conceptId = " + testConceptId + " and le.test_result = 1000537\n" +
			"  and date(le.visit_date) between :startDate and :endDate;"

		);
		return sql;
	}
	
    public CohortDefinition getAllBSMalariaTestsPositiveCases(Integer testConceptId) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Get patients with malaria tests positive cases");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("#Malaria BS\n" +
			"select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"                               join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"where le.set_member_conceptId = " + testConceptId + " and le.test_result like '%seen%'\n" +
			"  and date(le.visit_date) between :startDate and :endDate;"

        );
        return sql;
    }
    public CohortDefinition getAllRapidMalariaTestsPositiveCases(Integer testConceptId) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Get patients with rapid malaria tests positive cases");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"                               join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"where le.set_member_conceptId = " + testConceptId + "  and le.test_result = 703\n" +
			"  and date(le.visit_date) between :startDate and :endDate;"

        );
        return sql;
    }

	public CohortDefinition getAllTaeniaSPPTestsPositiveCases(Integer testConceptId) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get patients with  Taenia SPP tests positive cases");
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"                               join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"where le.set_member_conceptId = " + testConceptId + "  and le.test_result = 163748\n" +
			"  and date(le.visit_date) between :startDate and :endDate;"

		);
		return sql;
	}
	public CohortDefinition getHymenolepisnanaTestsPositiveCases(Integer testConceptId) {
		SqlCohortDefinition sql = new SqlCohortDefinition();
		sql.setName("Get patients with  Taenia SPP tests positive cases");
		sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sql.addParameter(new Parameter("endDate", "End Date", Date.class));
		sql.setQuery("select  le.patient_id from kenyaemr_etl.etl_laboratory_extract le\n" +
			"                               join kenyaemr_etl.etl_patient_demographics p on p.patient_id = le.patient_id\n" +
			"where le.set_member_conceptId = " + testConceptId + "  and le.test_result = 163748\n" +
			"  and date(le.visit_date) between :startDate and :endDate;"

		);
		return sql;
	}

    public CohortDefinition getResponsesBasedOnAnswer(int question, List<Integer> ans) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Get patients with obs recorded based on value coded");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id = e.patient_id INNER JOIN obs o ON e.encounter_id = o.encounter_id "
                + " WHERE p.voided=0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id="
                + question
                + " AND o.value_coded IN ("
                + StringUtils.join(ans, ',')
                + ") "
                + " AND e.encounter_datetime BETWEEN :startDate AND :endDate "

        );
        System.out.println("Cohoort query: MOH 706" + sql.getQuery());
        return sql;
    }


    public CohortDefinition getPatientsWithObs(int question) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Get patients with obs recorded");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id = e.patient_id INNER JOIN obs o ON e.encounter_id = o.encounter_id "
                + " WHERE p.voided=0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id="
                + question
                + " AND e.encounter_datetime BETWEEN :startDate AND :endDate "

        );
        return sql;
    }

    public CohortDefinition getResponsesBasedOnAlistOfQuestionsAndListOfAnswers(List<Integer> question, List<Integer> ans) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Get patients with obs recorded based on value coded list and question list and list of answers");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id = e.patient_id INNER JOIN obs o ON e.encounter_id = o.encounter_id "
                + " WHERE p.voided=0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id IN ("
                + StringUtils.join(question, ',')
                + ") "
                + " AND o.value_coded IN ("
                + StringUtils.join(ans, ',')
                + ") "
                + " AND e.encounter_datetime BETWEEN :startDate AND :endDate "

        );
        return sql;
    }

    public CohortDefinition getResponsesBasedOnAlistOfQuestions(List<Integer> question) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Get patients with obs recorded based on value coded list and question list");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id = e.patient_id INNER JOIN obs o ON e.encounter_id = o.encounter_id "
                + " WHERE p.voided=0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id IN ("
                + StringUtils.join(question, ',') + ") " + " AND e.encounter_datetime BETWEEN :startDate AND :endDate "

        );
        return sql;
    }

    public CohortDefinition getResponsesBasedOnValueNumericQuestion(int question) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Get patients with obs recorded based on value numeric concept id");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id = e.patient_id INNER JOIN obs o ON e.encounter_id = o.encounter_id "
                + " WHERE p.voided=0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id IN ("
                + question
                + ") "
                + " AND e.encounter_datetime BETWEEN :startDate AND :endDate AND o.value_numeric IS NOT NULL "

        );
        return sql;
    }

    public CohortDefinition getResponsesBasedOnValueNumericQuestionBetweenLimits(int question, double lower, double upper) {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Get patients with obs recorded based on value numeric concept id within limits");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id = e.patient_id INNER JOIN obs o ON e.encounter_id = o.encounter_id "
                + " WHERE p.voided=0 AND e.voided = 0 AND o.voided = 0 AND o.concept_id IN ("
                + question
                + ") "
                + " AND e.encounter_datetime BETWEEN :startDate AND :endDate AND o.value_numeric IS NOT NULL AND o.value_numeric BETWEEN "
                + lower + " AND " + upper

        );
        return sql;
    }



}
