/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.calculation.library.surveillance;

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
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Calculates the eligibility for Cholera screening flag for  patients
 * @should calculate Active visit
 * @should calculate person over 2 years Old
 * @should calculate  diarrhoea
 * @should calculate Vomiting
 * @should calculate no duration
 */
public class EligibleForCholeraCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
    protected static final Log log = LogFactory.getLog(EligibleForCholeraCalculation.class);

    public static final EncounterType consultationEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.CONSULTATION);
    public static final Form clinicalEncounterForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.CLINICAL_ENCOUNTER);
    public static final EncounterType greenCardEncType = MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_CONSULTATION);
    public static final Form greenCardForm = MetadataUtils.existing(Form.class, HivMetadata._Form.HIV_GREEN_CARD);

    @Override
    public String getFlagMessage() {
        return "Suspected Cholera case";
    }

    Integer VOMITING = 122983;   
	Integer WATERY_DIARRHEA = 161887;
    Integer SCREENING_QUESTION = 5219;

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
        Set<Integer> alive = Filters.alive(cohort, context);
        PatientService patientService = Context.getPatientService();
        CalculationResultMap ret = new CalculationResultMap();
        for (Integer ptId : alive) {
            boolean result = false;
            List<Visit> activeVisits = Context.getVisitService().getActiveVisitsByPatient(patientService.getPatient(ptId));
            if (!activeVisits.isEmpty()) {
                Date dateCreated = null;
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String todayDate = dateFormat.format(currentDate);
                Patient patient = patientService.getPatient(ptId);
                Encounter lastClinicalEncounter = EmrUtils.lastEncounter(patient, consultationEncType, clinicalEncounterForm); //last clinical encounter form
                Encounter lastGreenCardEnc = EmrUtils.lastEncounter(patient, greenCardEncType, greenCardForm);   //last greencard followup form
                ConceptService cs = Context.getConceptService();
                Concept vomitingResult = cs.getConcept(VOMITING);
                Concept diarrheaResult = cs.getConcept(WATERY_DIARRHEA);
                Concept screeningQuestion = cs.getConcept(SCREENING_QUESTION);
                boolean patientVomitClinicalEncResult = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, vomitingResult) : false;
                boolean patientDiarrheaClinicalEncResult = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, diarrheaResult) : false;
                boolean patientVomitGreenCardResult = lastGreenCardEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastGreenCardEnc, screeningQuestion, vomitingResult) : false;
                boolean patientDiarrheaGreenCardResult = lastGreenCardEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastGreenCardEnc, screeningQuestion, diarrheaResult) : false;
                if (patient.getAge() > 2) {
                    if (lastClinicalEncounter != null) {
                        for (Obs obs : lastClinicalEncounter.getObs()) {
                            if (patientVomitClinicalEncResult && patientDiarrheaClinicalEncResult) {
                                dateCreated = obs.getDateCreated();
                                if (dateCreated != null) {
                                    String createdDate = dateFormat.format(dateCreated);
                                    if (createdDate.equals(todayDate)) {
                                        result = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (lastGreenCardEnc != null) {
                        for (Obs obs : lastGreenCardEnc.getObs()) {
                            if (patientVomitGreenCardResult && patientDiarrheaGreenCardResult) {
                                dateCreated = obs.getDateCreated();
                                if (dateCreated != null) {
                                    String createdDate = dateFormat.format(dateCreated);
                                    if (createdDate.equals(todayDate)) {
                                        result = true;
                                        break;
                                    }
                                }
                            }
                        }

                    }
                }

                ret.put(ptId, new BooleanResult(result, this));
            }
        }

        return ret;
    }
}
