/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.calculation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openmrs.Patient;
import org.openmrs.Program;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
// import org.openmrs.module.kenyaemrml.api.MLinKenyaEMRService;
// import org.openmrs.module.kenyaemrml.iit.PatientRiskScore;
import org.openmrs.module.metadatadeploy.MetadataUtils;

/**
 * Calculate whether a patient has high IIT risk score based on data pulled from NDWH
 */
public class HTSPredictionCalculation extends AbstractPatientCalculation {
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(Collection, Map,
	 *      PatientCalculationContext)
	 * @should determine whether a patient has high IIT risk
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues,
	        PatientCalculationContext context) {
		
		CalculationResultMap ret = new CalculationResultMap();
		// Get HIV program
		Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
		// Get all patients who are alive and in HIV program
		Set<Integer> alive = Filters.alive(cohort, context);
		Set<Integer> inHivProgram = Filters.inProgram(hivProgram, alive, context);

		for (Integer ptId : inHivProgram) {
			Patient currentPatient = Context.getPatientService().getPatient(ptId);
			// PatientRiskScore latestRiskScore = Context.getService(MLinKenyaEMRService.class).getLatestPatientRiskScoreByPatient(currentPatient);
			// if (latestRiskScore != null) {
			// 	String riskGroup = latestRiskScore.getDescription();
			// 	Date evaluationDate = latestRiskScore.getEvaluationDate();
			// 	Date currentDate = new Date();
			// 	// Ensure the evaluation date is less than a month ago (30 days)
			// 	long diffInMillies = Math.abs(currentDate.getTime() - evaluationDate.getTime());
    		// 	long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			// 	// Check if last visit is after evaluation date
			// 	List<Visit> allVisits = Context.getVisitService().getVisitsByPatient(currentPatient);
			// 	Date latestVisitDate = getLastVisitDate(allVisits);
			// 	Boolean checkVisit = latestVisitDate.after(evaluationDate);
			// 	// Create the High Risk flag given certain conditions (less than 30 days since score was done, no visit after score date)
			// 	if (riskGroup.trim().equalsIgnoreCase("High Risk") && diff <= 30 && !checkVisit) {
			// 		ret.put(ptId, new BooleanResult(true, this, context));
			// 	}				
			// }
			ret.put(ptId, new BooleanResult(true, this, context));
		}
		
		return ret;
	}

	/**
	 * Get the last visit date
	 */
	private Date getLastVisitDate(List<Visit> allVisits) {
		Date latestDate = null;
		List<Date> visitDates = new ArrayList<Date>();
		for(Visit visit:allVisits) {
			visitDates.add(visit.getStartDatetime());
		}
		latestDate = Collections.max(visitDates);
		return(latestDate);
	}
	
}
