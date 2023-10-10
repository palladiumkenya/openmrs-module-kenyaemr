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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.openmrs.*;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.*;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class EligibleForIliScreeningCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
    protected static final Log log = LogFactory.getLog(EligibleForSariScreeningCalculation.class);

    public static final EncounterType triageEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.TRIAGE);
    public static final Form triageScreeningForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.TRIAGE);

    //sari screening

    @Override
    public String getFlagMessage() {
        return "Suspected ILI case";
    }
    Integer MEASURE_FEVER = 140238;
    Integer COUGH_PRESENCE = 143264;
    Integer ONSET_DATE = 159948;
    Integer SCRENING_QUESTION = 5219;
    Integer TEMPERATURE = 5088;
    /**
     * Evaluates the calculation
     */

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {

        Set<Integer> alive = Filters.alive(cohort, context);
        PatientService patientService = Context.getPatientService();
        CalculationResultMap ret = new CalculationResultMap();
        for (Integer ptId :alive) {
            boolean result = false;
            Date currentDate = new Date();
            Double tempValue = 0.0;
            Integer triageDateDifference = 0;
            Integer greenCardDateDifference = 0;
            Date triageOnsetDate = null;
            Date greenCardOnsetDate = null;
            Date dateCreated = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String todayDate = dateFormat.format(currentDate);
            Patient patient = patientService.getPatient(ptId);
            Encounter lastTriageEnc = EmrUtils.lastEncounter(patient, triageEncType, triageScreeningForm);

            ConceptService cs = Context.getConceptService();
            Concept measureFeverResult = cs.getConcept(MEASURE_FEVER);
            Concept coughPresenceResult = cs.getConcept(COUGH_PRESENCE);
            Concept screeningQuestion = cs.getConcept(SCRENING_QUESTION);
            CalculationResultMap tempMap = Calculations.lastObs(cs.getConcept(TEMPERATURE), cohort, context);
            Encounter lastFollowUpEncounter = EmrUtils.lastEncounter(Context.getPatientService().getPatient(ptId), Context.getEncounterService().getEncounterTypeByUuid("a0034eee-1940-4e35-847f-97537a35d05e"));   //last greencard followup form

            boolean patientFeverResultGreenCard = lastFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastFollowUpEncounter, screeningQuestion, measureFeverResult) : false;
            boolean patientCoughResultGreenCard = lastFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastFollowUpEncounter, screeningQuestion, coughPresenceResult) : false;
            boolean patientFeverResult = lastTriageEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEnc, screeningQuestion, measureFeverResult) : false;
            boolean pantientCoughResult = lastTriageEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEnc, screeningQuestion, coughPresenceResult) : false;

            if (lastTriageEnc !=null){

                    Obs lastTempObs = EmrCalculationUtils.obsResultForPatient(tempMap, ptId);
                    if (lastTempObs != null) {
                        tempValue = lastTempObs.getValueNumeric();
                    }

                    for (Obs obs : lastTriageEnc.getObs()) {
                        dateCreated = obs.getDateCreated();
                        if (obs.getConcept().getConceptId().equals(ONSET_DATE)) {
                            triageOnsetDate = obs.getValueDatetime();
                            triageDateDifference = daysBetween(currentDate, triageOnsetDate);
                        }
                    }
                String createdDate = dateFormat.format(dateCreated);
                if(createdDate != null && createdDate.equals(todayDate)) {
                        if (triageDateDifference <= 10 && tempValue != null && tempValue >= 38.0) {
                            if ((patientFeverResult && pantientCoughResult)) {
                                result = true;
                            }
                        }
                }
            }
            if (lastFollowUpEncounter != null){
                    Obs lastTempObs = EmrCalculationUtils.obsResultForPatient(tempMap, ptId);
                    if (lastTempObs != null) {
                        tempValue = lastTempObs.getValueNumeric();
                    }
                    for (Obs obs : lastFollowUpEncounter.getObs()) {
                        dateCreated = obs.getDateCreated();
                        if (obs.getConcept().getConceptId().equals(ONSET_DATE)) {
                            greenCardOnsetDate = obs.getValueDatetime();
                            greenCardDateDifference = daysBetween(currentDate, greenCardOnsetDate);
                        }
                    }
                String createdDate = dateFormat.format(dateCreated);
                if(createdDate != null && createdDate.equals(todayDate)) {
                        if (greenCardDateDifference <= 10 && tempValue != null && tempValue >= 38.0) {
                            if (patientFeverResultGreenCard && patientCoughResultGreenCard) {
                                result = true;
                            }
                        }
                  }
            }
            ret.put(ptId, new BooleanResult(result, this));
        }

        return ret;
    }
    private int daysBetween(Date date1, Date date2) {
        DateTime d1 = new DateTime(date1.getTime());
        DateTime d2 = new DateTime(date2.getTime());
        return Math.abs(Days.daysBetween(d1, d2).getDays());
    }
}
