/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.Moh705ReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.dimension.CohortDefinitionDimension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;

@Component
public class Moh705ReportDimension {
	
	/**
	 * Patients with encounters on a given date
	 * 
	 * @return @{@link org.openmrs.module.reporting.indicator.dimension.CohortDimension}
	 */
	public CohortDefinitionDimension encountersOfMonthPerDay() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("Patient with encounters on date of day");
		dim.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dim.addParameter(new Parameter("endDate", "End Date", Date.class));
		dim.addCohortDefinition("1", map(getPatientsSeenOnDay(0), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("2", map(getPatientsSeenOnDay(1), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("3", map(getPatientsSeenOnDay(2), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("4", map(getPatientsSeenOnDay(3), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("5", map(getPatientsSeenOnDay(4), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("6", map(getPatientsSeenOnDay(5), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("7", map(getPatientsSeenOnDay(6), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("8", map(getPatientsSeenOnDay(7), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("9", map(getPatientsSeenOnDay(8), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("10", map(getPatientsSeenOnDay(9), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("11", map(getPatientsSeenOnDay(10), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("12", map(getPatientsSeenOnDay(11), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("13", map(getPatientsSeenOnDay(12), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("14", map(getPatientsSeenOnDay(13), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("15", map(getPatientsSeenOnDay(14), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("16", map(getPatientsSeenOnDay(15), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("17", map(getPatientsSeenOnDay(16), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("18", map(getPatientsSeenOnDay(17), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("19", map(getPatientsSeenOnDay(18), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("20", map(getPatientsSeenOnDay(19), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("21", map(getPatientsSeenOnDay(20), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("22", map(getPatientsSeenOnDay(21), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("23", map(getPatientsSeenOnDay(22), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("24", map(getPatientsSeenOnDay(23), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("25", map(getPatientsSeenOnDay(24), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("26", map(getPatientsSeenOnDay(25), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("27", map(getPatientsSeenOnDay(26), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("28", map(getPatientsSeenOnDay(27), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("29", map(getPatientsSeenOnDay(28), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("30", map(getPatientsSeenOnDay(29), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("31", map(getPatientsSeenOnDay(30), "startDate=${startDate},endDate=${endDate}"));
		
		return dim;
	}
	

	public CohortDefinition getPatientsSeenOnDay(int day) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Get patients seen on a given day");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery("SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id"
		        + " WHERE p.voided= 0 AND e.voided = 0 AND e.encounter_datetime BETWEEN DATE_ADD(:startDate, INTERVAL "
		        + day + " DAY) AND DATE_ADD(DATE_ADD(DATE_ADD(:startDate, INTERVAL " + day
		        + " DAY), INTERVAL 23 HOUR), INTERVAL 59 MINUTE) AND e.encounter_datetime <= :endDate");
		return cd;
	}	
	
}
