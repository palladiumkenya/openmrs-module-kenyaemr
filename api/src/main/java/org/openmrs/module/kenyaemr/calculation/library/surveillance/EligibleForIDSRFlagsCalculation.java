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
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Calculates the eligibility for ILI screening flag for  patients
 *
 * @should calculate Active visit
 * @should calculate cough for <= 10 days
 * @should calculate fever for <= 10 days
 * @should calculate temperature  for >= 38.0 same day
 * @should calculate duration < 10 days
 * <p>
 * SARI
 * * @should calculate admitted
 */
public class EligibleForIDSRFlagsCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
	protected static final Log log = LogFactory.getLog(EligibleForIDSRFlagsCalculation.class);

	StringBuilder idsrMessage = new StringBuilder();

	public static final EncounterType triageEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.TRIAGE);
	public static final Form triageScreeningForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.TRIAGE);
	public static final EncounterType consultationEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.CONSULTATION);
	public static final Form clinicalEncounterForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.CLINICAL_ENCOUNTER);
	public static final EncounterType greenCardEncType = MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_CONSULTATION);
	public static final Form greenCardForm = MetadataUtils.existing(Form.class, HivMetadata._Form.HIV_GREEN_CARD);

	Integer SCREENING_QUESTION = 5219;

	Integer FEVER = 140238;
	Integer COUGH_PRESENCE = 143264;
	Integer DURATION = 159368;

	Integer TEMPERATURE = 5088;
	Integer PATIENT_OUTCOME = 160433;
	Integer INPATIENT_ADMISSION = 1654;
	//Chikungunya
	Integer JOINT_PAIN = 116558;
	//Cholera
	Integer VOMITING = 122983;
	Integer WATERY_DIARRHEA = 161887;
	//Dysentry
	Integer BLOOD_IN_STOOL = 117671;
	Integer DIARRHEA = 142412;
	//Viral Haemorrhagic Fever
	Integer BLEEDING_TENDENCIES = 162628;
	//MALARIA
	Integer HEADACHE = 139084;
	Integer CHILLS = 871;
	//Measles
	Integer RASH = 512;
	Integer CORYZA = 106;
	Integer CONJUCTIVITIS = 127777;
	//Rift Valley Fever
	Integer JAUNDICE = 136443;
	Integer DIZZINESS = 141830;
	Integer MALAISE = 135367;
	Integer SCREENING_QUESTION_EXAMINATION = 162737;
	//Polio
	Integer LIMBS_WEAKNESS = 157498;

	/**
	 * Evaluates the calculation
	 */

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

				Encounter lastTriageEncounter = EmrUtils.lastEncounter(patient, triageEncType, triageScreeningForm);
				Encounter lastHivFollowUpEncounter = EmrUtils.lastEncounter(patient, greenCardEncType, greenCardForm);   //last greencard followup form
				Encounter lastClinicalEncounter = EmrUtils.lastEncounter(patient, consultationEncType, clinicalEncounterForm);   //last clinical encounter form

				ConceptService cs = Context.getConceptService();
				Concept screeningQuestion = cs.getConcept(SCREENING_QUESTION);
				Concept screeningQuestionExam = cs.getConcept(SCREENING_QUESTION_EXAMINATION);

				Concept measureFeverResult = cs.getConcept(FEVER);
				Concept coughPresenceResult = cs.getConcept(COUGH_PRESENCE);
				Concept adminQuestion = cs.getConcept(PATIENT_OUTCOME);
				Concept admissionAnswer = cs.getConcept(INPATIENT_ADMISSION);
				Concept jointPainResult = cs.getConcept(JOINT_PAIN);
				Concept vomitingResult = cs.getConcept(VOMITING);
				Concept wateryDiarrheaResult = cs.getConcept(WATERY_DIARRHEA);
				Concept bloodyStoolResult = cs.getConcept(BLOOD_IN_STOOL);
				Concept diarrheaResult = cs.getConcept(DIARRHEA);
				Concept bleedingResult = cs.getConcept(BLEEDING_TENDENCIES);
				Concept headacheResult = cs.getConcept(HEADACHE);
				Concept chillsResult = cs.getConcept(CHILLS);
				Concept rashResult = cs.getConcept(RASH);
				Concept coryzaResult = cs.getConcept(CORYZA);
				Concept conjunctivitisResult = cs.getConcept(CONJUCTIVITIS);
				Concept jaundiceResult = cs.getConcept(JAUNDICE);
				Concept dizzinessResult = cs.getConcept(DIZZINESS);
				Concept malaiseResult = cs.getConcept(MALAISE);
				Concept limbsWeaknessResult = cs.getConcept(LIMBS_WEAKNESS);
				//Teperature
				CalculationResultMap tempMap = Calculations.lastObs(cs.getConcept(TEMPERATURE), cohort, context);
				//Fever
				boolean patientFeverResultTriage = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, measureFeverResult) : false;
				boolean patientFeverResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, measureFeverResult) : false;
				boolean patientFeverResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, measureFeverResult) : false;
				//Cough
				boolean patientCoughResult = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, coughPresenceResult) : false;
				boolean patientCoughResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, coughPresenceResult) : false;
				boolean patientCoughResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, coughPresenceResult) : false;
				//Joint Pains
				boolean patientJointPainResultTriage = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, jointPainResult) : false;
				boolean patientJointPainResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, jointPainResult) : false;
				boolean patientJointPainResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, jointPainResult) : false;
				// Vomiting
				boolean patientVomitTriageEncResult = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, vomitingResult) : false;
				boolean patientVomitGreenCardResult = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, vomitingResult) : false;
				boolean patientVomitClinicalEncResult = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, vomitingResult) : false;
				//Watery diarrhea
				boolean patientWateryDiarrheaTriageEncResult = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, wateryDiarrheaResult) : false;
				boolean patientWateryDiarrheaGreenCardResult = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, wateryDiarrheaResult) : false;
				boolean patientWateryDiarrheaClinicalEncResult = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, wateryDiarrheaResult) : false;
				//Diarrhea
				boolean patientDiarrheaTriageEncResult = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, diarrheaResult) : false;
				boolean patientDiarrheaGreenCardResult = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, diarrheaResult) : false;
				boolean patientDiarrheaClinicalEncResult = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, diarrheaResult) : false;
				//Blood in stool
				boolean patientBloodyStoolTriageEncResult = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, bloodyStoolResult) : false;
				boolean patientBloodyStoolGreenCardResult = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, bloodyStoolResult) : false;
				boolean patientBloodyStoolClinicalEncResult = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, bloodyStoolResult) : false;
				//Bleeding tendencies
				boolean patientBleedingResulTriage = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, bleedingResult) : false;
				boolean patientBleedingResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, bleedingResult) : false;
				boolean patientBleedingResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, bleedingResult) : false;
				//Headache
				boolean patientHeadacheResultTriage = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, headacheResult) : false;
				boolean patientHeadacheResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, headacheResult) : false;
				boolean patientHeadacheResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, headacheResult) : false;
				//Chills
				boolean patientChillsResultTriage = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, chillsResult) : false;
				boolean patientChillsResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, chillsResult) : false;
				boolean patientChillsResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, chillsResult) : false;
				//Rash
				boolean patientRashResultTriage = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, rashResult) : false;
				boolean patientRashResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, rashResult) : false;
				boolean patientRashResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, rashResult) : false;
				//Coryza
				boolean patientCoryzaResultTriage = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, coryzaResult) : false;
				boolean patientCoryzaResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, coryzaResult) : false;
				boolean patientCoryzaResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, coryzaResult) : false;
				//Conjunctivitis
				boolean patientConjunctivitisResultTriage = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, conjunctivitisResult) : false;
				boolean patientConjunctivitisResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, conjunctivitisResult) : false;
				boolean patientConjunctivitisResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, conjunctivitisResult) : false;
				//Jaundice
				boolean patientJaundiceResultTriage = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestionExam, jaundiceResult) : false;
				boolean patientJaundiceResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestionExam, jaundiceResult) : false;
				boolean patientJaundiceResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestionExam, jaundiceResult) : false;
				//Dizziness
				boolean patientDizzinessResultTriage = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, dizzinessResult) : false;
				boolean patientDizzinessResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, dizzinessResult) : false;
				boolean patientDizzinessResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, dizzinessResult) : false;
				//Malaise
				boolean patientMalaiseResultTriage = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, malaiseResult) : false;
				boolean patientMalaiseResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, malaiseResult) : false;
				boolean patientMalaiseResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, malaiseResult) : false;
				//Weakness of limbs
				boolean patientWeakLimbsResultTriage = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, limbsWeaknessResult) : false;
				boolean patientWeakLimbsResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, limbsWeaknessResult) : false;
				boolean patientWeakLimbsResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, limbsWeaknessResult) : false;

				//Check admission status : Found in clinical encounter and type of visit
				boolean patientAdmissionStatus = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, adminQuestion, admissionAnswer) : false;
				//Visit type is inpatient
				Visit currentVisit = activeVisits.get(0);
				Obs lastTempObs = EmrCalculationUtils.obsResultForPatient(tempMap, ptId);
				if (lastTempObs != null) {
					tempValue = lastTempObs.getValueNumeric();
				}
				//Triage
				if (lastTriageEncounter != null) {
					//1. SARI and ILI
					if (patientFeverResultTriage && patientCoughResult) {
						for (Obs obs : lastTriageEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (obs.getConcept().getConceptId().equals(DURATION)) {
								duration = obs.getValueNumeric();
							}
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if ((duration > 0.0 && duration < 10) && tempValue != null && tempValue >= 38.0) {
									if (createdDate.equals(todayDate)) {
										if (!patientAdmissionStatus && !currentVisit.getVisitType().getUuid().equals("a73e2ac6-263b-47fc-99fc-e0f2c09fc914")) {
											eligible = true;
											idsrMessage.append(" Suspected ILI Case");
											break;
										} else {
											eligible = true;
											idsrMessage.append(" Suspected SARI Case");
											break;
										}
									}
								}
							}							
						}
					}
					//2. CHIKUNGUNYA
					if (patientJointPainResultTriage && patientFeverResultTriage) {
						for (Obs obs : lastTriageEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (obs.getConcept().getConceptId().equals(DURATION)) {
								duration = obs.getValueNumeric();
							}
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (duration > 2 && tempValue != null && tempValue > 38.5) {
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Chikungunya case");
										break;
									}
								}
							}							
						}
					}
					//3. CHOLERA
					if (patientVomitTriageEncResult && patientWateryDiarrheaTriageEncResult) {
						if (patient.getAge() > 2) {
							for (Obs obs : lastTriageEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Cholera case");
										break;
									}
								}								
							}
						}
					}
					//4.Dysentry
					if (patientBloodyStoolTriageEncResult && patientDiarrheaTriageEncResult) {
						for (Obs obs : lastTriageEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (createdDate.equals(todayDate)) {
									eligible = true;
									idsrMessage.append(" Suspected Dysentery case");
									break;
								}
							}							
						}
					}
					//5. Viral Haemorrhagic fever
					if (patientFeverResultTriage && patientBleedingResulTriage) {
						for (Obs obs : lastTriageEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (createdDate.equals(todayDate)) {
									eligible = true;
									idsrMessage.append(" Suspected Haemorrhagic Fever");
									break;
								}
							}							
						}
					}
					//6. Malaria
					if (patientHeadacheResultTriage && patientChillsResultTriage && patientFeverResultTriage) {
						for (Obs obs : lastTriageEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (obs.getConcept().getConceptId().equals(DURATION)) {
								duration = obs.getValueNumeric();
							}
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (duration > 1 && tempValue != null && tempValue >= 37.5) {
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Malaria case");
										break;
									}
								}
							}							
						}
					}
					//7.Measles				
					if (patientFeverResultTriage && patientRashResultTriage && patientCoryzaResultTriage && patientCoughResult && patientConjunctivitisResultTriage) {
						for (Obs obs : lastTriageEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (obs.getConcept().getConceptId().equals(DURATION)) {
								duration = obs.getValueNumeric();
							}
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (duration > 2) {
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Measles case");
										break;
									}
								}
							}							
						}
					}

					//8.Rift Valley Fever					
					if (patientJaundiceResultTriage && patientDizzinessResultTriage && patientMalaiseResultTriage && patientFeverResultTriage) {
						for (Obs obs : lastTriageEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (obs.getConcept().getConceptId().equals(DURATION)) {
								duration = obs.getValueNumeric();
							}
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (duration > 2 && tempValue != null && tempValue > 37.5) {
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Rift Valley Fever case");
										break;
									}
								}
							}							
						}
					}
					//9.Poliomyelitis
					if (patientWeakLimbsResultTriage) {
						if (patient.getAge() < 15) {
							for (Obs obs : lastTriageEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Poliomyelitis case");
										break;
									}
								}								
							}
						}

					}
				}

				//Hiv followup encounter
				if (lastHivFollowUpEncounter != null) {
					//1. SARI and ILI
					if (patientFeverResultGreenCard && patientCoughResultGreenCard) {
						for (Obs obs : lastHivFollowUpEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (obs.getConcept().getConceptId().equals(DURATION)) {
								duration = obs.getValueNumeric();
							}
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if ((duration > 0.0 && duration < 10) && tempValue != null && tempValue >= 38.0) {
									if (createdDate.equals(todayDate)) {
										if (!patientAdmissionStatus && !currentVisit.getVisitType().getUuid().equals("a73e2ac6-263b-47fc-99fc-e0f2c09fc914")) {
											eligible = true;
											idsrMessage.append(" Suspected ILI Case");
											break;
										} else {
											eligible = true;
											idsrMessage.append(" Suspected SARI Case");
											break;
										}
									}
								}
							}							
						}
					}
					//2. CHIKUNGUNYA
					if (patientJointPainResultGreenCard && patientFeverResultGreenCard) {
						for (Obs obs : lastHivFollowUpEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (obs.getConcept().getConceptId().equals(DURATION)) {
								duration = obs.getValueNumeric();
							}
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (duration > 2 && tempValue != null && tempValue > 38.5) {
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Chikungunya case");
										break;
									}
								}
							}							
						}
					}
					//3. CHOLERA
					if (patientVomitGreenCardResult && patientWateryDiarrheaGreenCardResult) {
						if (patient.getAge() > 2) {
							for (Obs obs : lastHivFollowUpEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Cholera case");
										break;
									}
								}	break;
							}							
						}
					}

					//4.Dysentry
					if (patientBloodyStoolGreenCardResult && patientDiarrheaGreenCardResult) {
						for (Obs obs : lastHivFollowUpEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (createdDate.equals(todayDate)) {
									eligible = true;
									idsrMessage.append(" Suspected Dysentery case");
									break;
								}
							}							
						}
					}
					//5. Viral Haemorrhagic fever
					if (patientFeverResultGreenCard && patientBleedingResultGreenCard) {
						for (Obs obs : lastHivFollowUpEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (createdDate.equals(todayDate)) {
									eligible = true;
									idsrMessage.append(" Suspected Haemorrhagic Fever");
									break;
								}
							}							
						}
					}
					//6. Malaria
					if (patientHeadacheResultGreenCard && patientChillsResultGreenCard && patientFeverResultGreenCard) {
						for (Obs obs : lastHivFollowUpEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (obs.getConcept().getConceptId().equals(DURATION)) {
								duration = obs.getValueNumeric();
							}
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (duration > 1 && tempValue != null && tempValue >= 37.5) {
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Malaria case");
										break;
									}
								}
							}							
						}
					}
					//7.Measles
					if (patientFeverResultGreenCard && patientRashResultGreenCard && patientCoryzaResultGreenCard && patientCoughResultGreenCard && patientConjunctivitisResultGreenCard) {
						for (Obs obs : lastHivFollowUpEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (obs.getConcept().getConceptId().equals(DURATION)) {
								duration = obs.getValueNumeric();
							}
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (duration > 2) {
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Measles case");
										break;
									}
								}
							}							
						}
					}
					//8.Rift Valley Fever
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
										idsrMessage.append(" Suspected Rift Valley Fever case");
										break;
									}
								}
							}							
						}
					}
					//9.Poliomyelitis
					if (patientWeakLimbsResultGreenCard) {
						if (patient.getAge() < 15) {
							for (Obs obs : lastHivFollowUpEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Poliomyelitis case");
										break;
									}
								}							
							}

						}
					}

				}
				//Clinical Encounter
				if (lastClinicalEncounter != null) {
					//1. SARI and ILI
					if (patientFeverResultClinical && patientCoughResultClinical) {
						for (Obs obs : lastClinicalEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (obs.getConcept().getConceptId().equals(DURATION)) {
								duration = obs.getValueNumeric();
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if ((duration > 0.0 && duration < 10) && tempValue != null && tempValue >= 38.0) {
										if (createdDate.equals(todayDate)) {									
											if (!patientAdmissionStatus && !currentVisit.getVisitType().getUuid().equals("a73e2ac6-263b-47fc-99fc-e0f2c09fc914")) {
												eligible = true;
												idsrMessage.append(" Suspected ILI Case");
												break;
											} else {
												eligible = true;
												idsrMessage.append(" Suspected SARI Case");
												break;
											}
										}
									}
								}
							}							
						}
					}
					//2. CHIKUNGUNYA
					if (patientJointPainResultClinical && patientFeverResultClinical) {
						for (Obs obs : lastClinicalEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (obs.getConcept().getConceptId().equals(DURATION)) {
								duration = obs.getValueNumeric();
							}
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (duration > 2 && tempValue != null && tempValue > 38.5) {
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Chikungunya case");
										break;
									}
								}
							}							
						}
					}
					//3. CHOLERA
					if (patientVomitClinicalEncResult && patientWateryDiarrheaClinicalEncResult) {
						if (patient.getAge() > 2) {
							for (Obs obs : lastClinicalEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Cholera case");
										break;
									}
								}								
							}
							
						}
					}
					//4.DYSENTRY
					if (patientDiarrheaClinicalEncResult && patientBloodyStoolClinicalEncResult) {
						for (Obs obs : lastClinicalEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (createdDate.equals(todayDate)) {
									eligible = true;
									idsrMessage.append(" Suspected Dysentery case");
									break;
								}
							}							
						}
					}
					//5. Viral Haemorrhagic fever
					if (patientFeverResultClinical && patientBleedingResultClinical) {
						for (Obs obs : lastClinicalEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (createdDate.equals(todayDate)) {
									eligible = true;
									idsrMessage.append(" Suspected Haemorrhagic Fever");
									break;
								}
							}							
						}
					}
					//6. Malaria
					if (patientHeadacheResultClinical && patientChillsResultClinical && patientFeverResultClinical) {
						for (Obs obs : lastClinicalEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (obs.getConcept().getConceptId().equals(DURATION)) {
								duration = obs.getValueNumeric();
							}
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (duration > 1 && tempValue != null && tempValue >= 37.5) {
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Malaria case");
										break;
									}
								}
							}							
						}
					}
					//7.Measles					
					if (patientFeverResultClinical && patientRashResultClinical && patientCoryzaResultClinical && patientCoughResultClinical && patientConjunctivitisResultClinical) {
						for (Obs obs : lastClinicalEncounter.getObs()) {
							dateCreated = obs.getDateCreated();
							if (obs.getConcept().getConceptId().equals(DURATION)) {
								duration = obs.getValueNumeric();
							}
							if (dateCreated != null) {
								String createdDate = dateFormat.format(dateCreated);
								if (duration > 2) {
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Measles case");
										break;
									}
								}
							}							
						}
					}
					//8.Rift Valley Fever
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
										idsrMessage.append(" Suspected Rift Valley Fever case");
										break;
									}
								}
							}							
						}
					}
					//9.Poliomyelitis
					if (patientWeakLimbsResultClinical) {
						if (patient.getAge() < 15) {
							for (Obs obs : lastClinicalEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.append(" Suspected Poliomyelitis case");
										break;
									}
								}								
							}
						}
					}
				}
			}
			ret.put(ptId, new BooleanResult(eligible, this));
		}
		return ret;
	}

	@Override
	public String getFlagMessage() {
		return idsrMessage.toString();

	}
}
