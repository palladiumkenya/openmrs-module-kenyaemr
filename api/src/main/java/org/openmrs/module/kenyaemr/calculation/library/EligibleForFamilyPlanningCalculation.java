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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;

/**
 * Calculates whether patients are eligible for family planning program
 * Eligibility: Alive, age 15-49 yo and female
 */
public class EligibleForFamilyPlanningCalculation extends AbstractPatientCalculation {
    /**
     * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(Collection,
     *      Map, PatientCalculationContext)
     */
    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params,
            PatientCalculationContext context) {

        Set<Integer> alive = Filters.alive(cohort, context);
        CalculationResultMap ret = new CalculationResultMap();
        PatientService patientService = Context.getPatientService();

        for (Integer ptId : cohort) {
            Patient patient = patientService.getPatient(ptId);

            boolean eligible = false;
            if (alive.contains(ptId) && patient.getAge() >= 15 && patient.getAge() <= 49
                    && patient.getGender().equals("F")) {
                eligible = true;
            }
            ret.put(ptId, new BooleanResult(eligible, this));
        }
        return ret;
    }
}
