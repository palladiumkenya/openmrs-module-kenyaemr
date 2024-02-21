/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.calculation.library.mat;

import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.reporting.common.Age;

import java.util.Collection;
import java.util.Map;

/**
 * Calculates whether patients are eligible for the MAT program
 */
public class EligibleForMATProgramCalculation extends AbstractPatientCalculation {

	public static Integer MAT_ENROLLMENT_ELIGIBILITY_AGE_CUT_OFF = 16;
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(Collection, Map, PatientCalculationContext)
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) {
		CalculationResultMap ret = new CalculationResultMap();

		CalculationResultMap ages = Calculations.ages(cohort, context);

		for (int ptId : cohort) {
			Integer ageInYears = ((Age) ages.get(ptId).getValue()).getFullYears();
			boolean eligible = (ageInYears != null && ageInYears >= MAT_ENROLLMENT_ELIGIBILITY_AGE_CUT_OFF);

			ret.put(ptId, new BooleanResult(eligible, this));
		}
		return ret;
	}
}