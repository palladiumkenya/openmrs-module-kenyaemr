/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.advice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationService;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.module.kenyacore.calculation.CalculationManager;
import org.openmrs.module.kenyacore.calculation.PatientFlagCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.cache.CacheManager;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Refreshes patient flags cache from changes in patient clinical data
 */
public class RefreshPatientFlagsOnEncounterSave implements AfterReturningAdvice {
    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

        if (method.getName().equals("saveEncounter")) {
            Encounter enc = (Encounter) args[0];
            Patient patient = enc.getPatient();

            /**
             * We only want to refresh data for an active visit.
             * TODO: should we also limit this to Point-of-care visit?
             */
            if (enc != null && enc.getVisit() != null && enc.getVisit().getStopDatetime() == null && enc.getForm() != null && (CommonMetadata._Form.TRIAGE.equalsIgnoreCase(enc.getForm().getUuid()) || CommonMetadata._Form.CLINICAL_ENCOUNTER.equalsIgnoreCase(enc.getForm().getUuid()))) {
                // we can use global property to track this list for ease of management
                List<String> patientFlagsToRefresh = Arrays.asList(
                        "EligibleForIDSRFlagsCalculation"
                );
                CalculationManager calculationManager = Context.getRegisteredComponent("calculationManager", CalculationManager.class);

                CacheManager cacheManager = Context.getRegisteredComponent("apiCacheManager", CacheManager.class);
                calculationManager.refresh();
                // check if patient already has data in the cache
                Map<String,String> patientFlagsMap = new HashMap<>();
                if (cacheManager.getCache("patientFlagCache").get(patient.getUuid()) != null) {
                    patientFlagsMap = (HashMap<String, String>) cacheManager.getCache("patientFlagCache").get(patient.getUuid()).get();
                }

                for (PatientFlagCalculation calc : calculationManager.getFlagCalculations()) {
                    if (!patientFlagsToRefresh.contains(calc.getClass().getSimpleName())) {
                        continue;
                    }
                    try {
                        CalculationResult result = Context.getService(PatientCalculationService.class).evaluate(patient.getId(), calc);
                        if (result != null && (Boolean) result.getValue()) {
                            patientFlagsMap.put(calc.getClass().getSimpleName(), calc.getFlagMessage()); // add/update the flag
                        }
                    }
                    catch (Exception ex) {
                        log.error("Error updating patient flag for  " + calc.getClass(), ex);
                    }
                }
                if (!patientFlagsMap.isEmpty()) {
                    cacheManager.getCache("patientFlagCache").put(patient.getUuid(), patientFlagsMap);
                }
            }
        }

    }

}