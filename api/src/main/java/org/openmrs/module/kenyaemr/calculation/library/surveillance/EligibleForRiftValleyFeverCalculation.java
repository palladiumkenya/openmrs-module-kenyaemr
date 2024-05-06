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
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyacore.calculation.PatientFlagCalculation;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Calculates the eligibility for Rift Valley Fever screening flag for  patients
 * @should calculate Active visit
 * @should calculate Fever
 * @should calculate Temperature  >37.5C
 * @should calculate Dizziness
 * @should calculate Jaundice from general examinations finding
 * @should calculate General body malaise
 * @should calculate Duration > 2
 */
public class EligibleForRiftValleyFeverCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
    protected static final Log log = LogFactory.getLog(EligibleForRiftValleyFeverCalculation.class);
    public static final EncounterType triageEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.TRIAGE);
    public static final Form triageScreeningForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.TRIAGE);
    public static final EncounterType consultationEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.CONSULTATION);
    public static final Form clinicalEncounterForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.CLINICAL_ENCOUNTER);
    public static final EncounterType greenCardEncType = MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_CONSULTATION);
    public static final Form greenCardForm = MetadataUtils.existing(Form.class, HivMetadata._Form.HIV_GREEN_CARD);

    @Override
    public String getFlagMessage() {
        return "Suspected Rift Valley Fever case";
    }

    Integer JAUNDICE = 136443;
    Integer DIZZINESS = 141830;
    Integer MALAISE = 135367;
    Integer TEMPERATURE = 5088;
    Integer FEVER = 140238;
    Integer DURATION = 159368;
    Integer SCREENING_QUESTION_COMPLAINTS= 5219;
    Integer SCREENING_QUESTION_EXAMINATION= 162737;

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {

        Set<Integer> alive = Filters.alive(cohort, context);
        PatientService patientService = Context.getPatientService();
        CalculationResultMap ret = new CalculationResultMap();

        for (Integer ptId : alive) {
            boolean eligible = false;
            List<Visit> activeVisits = Context.getVisitService().getActiveVisitsByPatient(patientService.getPatient(ptId));
            if (!activeVisits.isEmpty()) {
                Date currentDate = new Date();
                Double tempValue = 0.0;
                Double duration = 0.0;
                Date dateCreated = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String todayDate = dateFormat.format(currentDate);
                Patient patient = patientService.getPatient(ptId);

                Encounter lastTriageEnc = EmrUtils.lastEncounter(patient, triageEncType, triageScreeningForm);
                Encounter lastHivFollowUpEncounter = EmrUtils.lastEncounter(patient, greenCardEncType, greenCardForm);   //last greencard followup form
                Encounter lastClinicalEncounter = EmrUtils.lastEncounter(patient, consultationEncType, clinicalEncounterForm);   //last clinical encounter form

                ConceptService cs = Context.getConceptService();
                Concept feverResult = cs.getConcept(FEVER);
                Concept jaundiceResult = cs.getConcept(JAUNDICE);
                Concept dizzinessResult = cs.getConcept(DIZZINESS);
                Concept malaiseResult = cs.getConcept(MALAISE);
                Concept screeningQuestionComp = cs.getConcept(SCREENING_QUESTION_COMPLAINTS);
                Concept screeningQuestionExam = cs.getConcept(SCREENING_QUESTION_EXAMINATION);

                CalculationResultMap tempMap = Calculations.lastObs(cs.getConcept(TEMPERATURE), cohort, context);

				boolean patientJaundiceResultTriage = lastTriageEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEnc, screeningQuestionExam, jaundiceResult) : false;
				boolean patientDizzinessResultTriage = lastTriageEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEnc, screeningQuestionComp, dizzinessResult) : false;
				boolean patientMalaiseResultTriage = lastTriageEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEnc, screeningQuestionComp, malaiseResult) : false;
				boolean patientFeverResultTriage = lastTriageEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEnc, screeningQuestionComp, feverResult) : false;
                boolean patientJaundiceResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestionExam, jaundiceResult) : false;
                boolean patientDizzinessResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestionComp, dizzinessResult) : false;
                boolean patientMalaiseResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestionComp, malaiseResult) : false;
                boolean patientFeverResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestionComp, feverResult) : false;
                boolean patientJaundiceResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestionExam, jaundiceResult) : false;
                boolean patientDizzinessResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestionComp, dizzinessResult) : false;
                boolean patientMalaiseResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestionComp, malaiseResult) : false;
                boolean patientFeverResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestionComp, feverResult) : false;

                Obs lastTempObs = EmrCalculationUtils.obsResultForPatient(tempMap, ptId);
                if (lastTempObs != null) {
                    tempValue = lastTempObs.getValueNumeric();
                }

                if (lastHivFollowUpEncounter != null) {
                    if (patientJaundiceResultGreenCard && patientDizzinessResultGreenCard && patientFeverResultGreenCard && patientMalaiseResultGreenCard) {
                        for (Obs obs : lastHivFollowUpEncounter.getObs()) {
                            dateCreated = obs.getDateCreated();
                            if (obs.getConcept().getConceptId().equals(DURATION)) {
                                duration = obs.getValueNumeric();
                            }
                            if (dateCreated != null) {
                                String createdDate = dateFormat.format(dateCreated);
                                if (duration > 2 && tempValue != null && tempValue > 37.5) {
                                    if (createdDate.equals(todayDate)) {
                                        eligible = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (lastClinicalEncounter != null) {
                    if (patientJaundiceResultClinical && patientDizzinessResultClinical && patientFeverResultClinical && patientMalaiseResultClinical) {
                        for (Obs obs : lastClinicalEncounter.getObs()) {
                            dateCreated = obs.getDateCreated();
                            if (obs.getConcept().getConceptId().equals(DURATION)) {
                                duration = obs.getValueNumeric();
                            }
                            if (dateCreated != null) {
                                String createdDate = dateFormat.format(dateCreated);
                                if (duration > 2 && tempValue != null && tempValue > 37.5) {
                                    if (createdDate.equals(todayDate)) {
                                        eligible = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
				if (lastTriageEnc != null) {
					if (patientJaundiceResultTriage && patientDizzinessResultTriage && patientMalaiseResultTriage && patientFeverResultTriage) {
						for (Obs obs : lastTriageEnc.getObs()) {
							dateCreated = obs.getDateCreated();
							if (obs.getConcept().getConceptId().equals(DURATION)) {
								duration = obs.getValueNumeric();
							}
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (duration > 2 && tempValue != null && tempValue > 37.5) {
									if (createdDate.equals(todayDate)) {
										eligible = true;
										break;
									}
								}
							}
						}
					}
				}
                ret.put(ptId, new BooleanResult(eligible, this));
            }
        }

        return ret;
    }
}
