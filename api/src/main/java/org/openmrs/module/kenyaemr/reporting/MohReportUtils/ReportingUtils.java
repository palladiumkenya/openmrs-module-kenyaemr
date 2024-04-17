/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.MohReportUtils;

import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.reporting.query.encounter.definition.EncounterQuery;
import org.openmrs.module.reporting.query.encounter.definition.SqlEncounterQuery;

import java.util.Date;
import java.util.List;

public class ReportingUtils {
	
	public ReportingUtils() {
	}
	
	public static CohortIndicator cohortIndicator(String name, Mapped<CohortDefinition> cohort) {
		CohortIndicator ind = new CohortIndicator(name);
		ind.addParameter(new Parameter("startDate", "Start Date", Date.class));
		ind.addParameter(new Parameter("endDate", "End Date", Date.class));
		ind.setCohortDefinition(cohort);
		return ind;
	}
	
	/**
	 * Adds a row to a dataset based on an indicator and a list of column parameters
	 * 
	 * @param cohortDsd the dataset
	 * @param baseName the base columm name
	 * @param baseLabel the base column label
	 * @param indicator the indicator
	 * @param columns the column parameters
	 */
	public static void addRow(CohortIndicatorDataSetDefinition cohortDsd, String baseName, String baseLabel,
	        Mapped<CohortIndicator> indicator, List<ColumnParameters> columns) {
		
		for (ColumnParameters column : columns) {
			String name = baseName + "-" + column.getColumn();
			String label = baseLabel + " (" + column.getLabel() + ")";
			cohortDsd.addColumn(name, label, indicator, column.getDimensions());
		}
	}
	
	public static EncounterQuery getEncounterLimitsByDate() {
		SqlEncounterQuery query = new SqlEncounterQuery();
		query.setName("Encounter per the given date");
		query.addParameter(new Parameter("startDate", "Start Date", Date.class));
		query.addParameter(new Parameter("endDate", "End Date", Date.class));
		query.setQuery("SELECT encounter_id FROM encounter WHERE encounter_datetime BETWEEN :startDate AND :endDate");
		return query;
	}

	/**
	 * MOH705
	 * New Attendances
	 * @return
	 */
	public static CohortDefinition newAttendances(String age) {
		String sqlQuery = "SELECT v.patient_id FROM kenyaemr_etl.etl_clinical_encounter v\n" +
				"INNER JOIN kenyaemr_etl.etl_patient_demographics d on v.patient_id = d.patient_id and timestampdiff(YEAR, date(d.dob),date(:endDate)) "+ age +"\n" +
				"WHERE v.visit_type = 'New visit' and date(v.visit_date) between date(:startDate) and date(:endDate);";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("newAttendances");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients who are new attendances");
		return cd;
	}
	/**
	 * MOH705
	 * Re-Attendances
	 * @return
	 */
	public static CohortDefinition reAttendances(String age) {
		String sqlQuery = "SELECT v.patient_id FROM kenyaemr_etl.etl_clinical_encounter v\n" +
				"INNER JOIN kenyaemr_etl.etl_patient_demographics d on v.patient_id = d.patient_id and timestampdiff(YEAR, date(d.dob),date(:endDate)) "+ age +"\n" +
				"WHERE v.visit_type = 'Revisit' and date(v.visit_date) between date(:startDate) and date(:endDate);";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("reAttendances");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients who are revisit attendances");
		return cd;
	}
	
}
