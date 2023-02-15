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

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.*;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.util.OpenmrsUtil;

import java.util.*;

import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.todaysDate;

/**
 * Calculates the eligibility for SARI/ILI patients
 */

        public class EligibleForSariILICalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
        protected static final Log log = LogFactory.getLog(EligibleForSariILICalculation.class);

       public static final EncounterType triageEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.TRIAGE);
       public static final Form triageScreeningForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.TRIAGE);



        @Override
        public String getFlagMessage() {
        return "Screen for SARI/ILI";
        }
        Integer MEASURE_FEVER = 140238;
        Integer COUGH_PRESENSE = 143264;
        Integer SYMPTOMS = 1729;
        Integer POSITIVE = 1065;
        /**
         * Evaluates the calculation
         */

        @Override
        public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {

            Set<Integer> alive = Filters.alive(cohort, context);
            PatientService patientService = Context.getPatientService();
            Date isToday = new Date();
            CalculationResultMap ret = new CalculationResultMap();


        for (Integer ptId :alive) {
            boolean result = false;
            Patient patient = patientService.getPatient(ptId);

            Encounter lastTriageEnc = EmrUtils.lastEncounter(patient, triageEncType, triageScreeningForm);
                ConceptService cs = Context.getConceptService();
                Concept measureFeverResult = cs.getConcept(MEASURE_FEVER);
                Concept coughPresenceResult = cs.getConcept(COUGH_PRESENSE);
                Concept symptomsResult = cs.getConcept(SYMPTOMS);
                Concept positive = cs.getConcept(POSITIVE);
                boolean patientSexualAbstainedResult = lastTriageEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEnc, measureFeverResult, positive) : false;
                boolean pantientLmpResult = lastTriageEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEnc, coughPresenceResult, positive) : false;
                boolean patientFamilyPlanningResult = lastTriageEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEnc, symptomsResult, positive) : false;
//||
                if(!DateUtils.isSameDay(new Date(),lastTriageEnc.getEncounterDatetime()) ){
                     result = false;
                 }else{
                     if ((patientSexualAbstainedResult && pantientLmpResult && patientFamilyPlanningResult) ) {
                         result = true;
                     }
                 }
            ret.put(ptId, new BooleanResult(result, this));
            }

            return ret;
        }
}
