/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.calculation.library;

import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.ListResult;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.CalculationUtils;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.common.TimeQualifier;
import org.openmrs.module.reporting.data.person.definition.ObsForPersonDataDefinition;
import org.openmrs.module.reporting.evaluation.EvaluationContext;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Returns whether patients have a scheduled visit on the specified date
 */
public class AppointmentTypeCalculation extends AbstractPatientCalculation {

	private static final Integer HOME_VISIT_APPOINTMENT_CONCEPT = 165577;
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(Collection, Map, PatientCalculationContext)
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
		Date date = (Date) parameterValues.get("date");
		if (date == null) {
			date = new Date();
		}

		Date startOfDay = DateUtil.getStartOfDay(date);
		Date endOfDay = DateUtil.getEndOfDay(date);

		Concept returnVisitDate = Dictionary.getConcept(Dictionary.RETURN_VISIT_DATE);

		ObsForPersonDataDefinition d = new ObsForPersonDataDefinition();
		d.setValueDatetimeOrAfter(startOfDay);
		d.setValueDatetimeOnOrBefore(endOfDay);
		d.setWhich(TimeQualifier.ANY);
		d.setQuestion(returnVisitDate);

		ObsForPersonDataDefinition homeVisitDataDefinition = new ObsForPersonDataDefinition();
		homeVisitDataDefinition.setValueDatetimeOrAfter(startOfDay);
		homeVisitDataDefinition.setValueDatetimeOnOrBefore(endOfDay);
		homeVisitDataDefinition.setWhich(TimeQualifier.ANY);
		homeVisitDataDefinition.setQuestion(Context.getConceptService().getConcept(HOME_VISIT_APPOINTMENT_CONCEPT));

		EvaluationContext ctx = new EvaluationContext();
		ctx.setBaseCohort(new Cohort(cohort));

		CalculationResultMap apptObsResult = CalculationUtils.evaluateWithReporting(d, cohort, parameterValues, null, context);
		CalculationResultMap homeVisitObsResult = CalculationUtils.evaluateWithReporting(homeVisitDataDefinition, cohort, parameterValues, null, context);


		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			ListResult result = (ListResult) apptObsResult.get(ptId);
			ListResult homeBasedresult = (ListResult) homeVisitObsResult.get(ptId);
			List<Obs> obs = CalculationUtils.extractResultValues(result);
			List<Obs> homeBasedobs = CalculationUtils.extractResultValues(homeBasedresult);

			if (!obs.isEmpty()) {
				if (obs.get(0).getConcept().getUuid().equals(Dictionary.RETURN_VISIT_DATE)) {
					ret.put(ptId, new SimpleResult("ANC Clinic Visit", this));
				}
			} else if (!homeBasedobs.isEmpty()) {
				if (homeBasedobs.get(0).getConcept().getConceptId().equals(HOME_VISIT_APPOINTMENT_CONCEPT)) {
					ret.put(ptId, new SimpleResult("ANC Home visit", this));
				}
			}
		}
		return ret;
	}


}