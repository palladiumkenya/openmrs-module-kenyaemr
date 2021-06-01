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

import org.openmrs.Concept;
import org.openmrs.api.PatientSetService.TimeModifier;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.CalculationUtils;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.DateObsCohortDefinition;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.common.RangeComparator;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Returns whether patients have a scheduled visit on the specified date
 */
public class ScheduledVisitOnDayCalculation extends AbstractPatientCalculation {

	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection, java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
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

		DateObsCohortDefinition clinicalAppt = new DateObsCohortDefinition();
		clinicalAppt.setName("clinicalAppointment");
		clinicalAppt.setTimeModifier(TimeModifier.ANY);
		clinicalAppt.setQuestion(returnVisitDate);
		clinicalAppt.setOperator1(RangeComparator.GREATER_EQUAL);
		clinicalAppt.setValue1(startOfDay);
		clinicalAppt.setOperator2(RangeComparator.LESS_EQUAL);
		clinicalAppt.setValue2(endOfDay);

		DateObsCohortDefinition homeVisitAppt = new DateObsCohortDefinition();
		homeVisitAppt.setName("homeVisitAppointment");
		homeVisitAppt.setTimeModifier(TimeModifier.ANY);
		homeVisitAppt.setQuestion(returnVisitDate);
		homeVisitAppt.setOperator1(RangeComparator.GREATER_EQUAL);
		homeVisitAppt.setValue1(startOfDay);
		homeVisitAppt.setOperator2(RangeComparator.LESS_EQUAL);
		homeVisitAppt.setValue2(endOfDay);

		CompositionCohortDefinition compositionCohortDefinition = new CompositionCohortDefinition();
		compositionCohortDefinition.setName("has clinical or home visit appointment");
		compositionCohortDefinition.addSearch("clinicalAppointment", ReportUtils.map(clinicalAppt));
		compositionCohortDefinition.addSearch("homeVisitAppointment", ReportUtils.map(homeVisitAppt));
		compositionCohortDefinition.setCompositionString("clinicalAppointment OR homeVisitAppointment");

		EvaluatedCohort withScheduledVisit = CalculationUtils.evaluateWithReporting(compositionCohortDefinition, cohort, null, context);

		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			ret.put(ptId, new BooleanResult(withScheduledVisit.contains(ptId), this));
		}
		return ret;
	}
}