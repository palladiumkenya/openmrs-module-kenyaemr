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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.openmrs.*;
import org.openmrs.api.AdministrationService;
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
public class EligibleForIDSRSymptomsFlagsCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
	protected static final Log log = LogFactory.getLog(EligibleForIDSRSymptomsFlagsCalculation.class);

	List<String> idsrMessage = new ArrayList<String>();
	String idsrMessageString = "";
	private AdministrationService administrationService = Context.getAdministrationService();
	final String isDmi = (administrationService.getGlobalProperty("kenyaemr.isDmi"));
	public static final EncounterType triageEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.TRIAGE);
	public static final Form triageScreeningForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.TRIAGE);
	public static final EncounterType consultationEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.CONSULTATION);
	public static final Form clinicalEncounterForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.CLINICAL_ENCOUNTER);
	public static final EncounterType greenCardEncType = MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_CONSULTATION);
	public static final Form greenCardForm = MetadataUtils.existing(Form.class, HivMetadata._Form.HIV_GREEN_CARD);

	String SCREENING_QUESTION = "5219AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

	String FEVER = "140238AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String COUGH_PRESENCE = "143264AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String DURATION = "159368AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

	String TEMPERATURE = "5088AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String PATIENT_OUTCOME = "160433AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String INPATIENT_ADMISSION = "1654AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	//Chikungunya
	String JOINT_PAIN = "116558AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	//Cholera
	String VOMITING = "122983AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	//String WATERY_DIARRHEA = "161887AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	//Dysentry
	String BLOOD_IN_STOOL = "117671AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String DIARRHEA = "142412AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	//Viral Haemorrhagic Fever
	String BLEEDING_TENDENCIES = "162628AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	//MALARIA
	//String HEADACHE = "139084AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String CHILLS = "871AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	//Measles
//	String RASH = "512AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String CORYZA = "106AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String CONJUCTIVITIS = "127777AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	//Rift Valley Fever
	//String JAUNDICE = "136443AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String DIZZINESS = "141830AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String MALAISE = "135367AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String SCREENING_QUESTION_EXAMINATION = "162737AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String 	TYPE_OF_SERVICE_PROVIDED = "168146";
	//Polio
	String LIMBS_WEAKNESS = "157498AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String ONSET_QUESTION = "d7a3441d-6aeb-49be-b7d6-b2a3bb39e78d";
	String SUDDEN_ONSET = "162707AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	//Acute Jaundice Syndrome
	String JAUNDICE = "136443AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	//Acute Febrile Rash Infection
	String RASH = "512AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

	//Neurological Syndrome
	String REFUSAL_TO_FEED = "6017AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String CONVULSIONS = "113054AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

	//Acute Watery Diarrhoeal Disease
	String NUMBER_OF_MOTIONS = "164456AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String WATERY_DIARRHEA = "161887AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

	////
	String HEADACHE = "139084AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String LYMPHADENOPATHY = "135488AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String MYALGIA = "121AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	String BACKPAIN = "148035AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

	//NECK STIFFNESS
	String NECK_STIFFNESS = "112721AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	/**
	 * Evaluates the calculation
	 */

	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {

		CalculationResultMap ret = new CalculationResultMap();
		if (isDmi.equals("true")) {
			Set<Integer> alive = Filters.alive(cohort, context);
			PatientService patientService = Context.getPatientService();

			for (Integer ptId : alive) {
				boolean eligible = false;
				List<Visit> activeVisits = Context.getVisitService().getActiveVisitsByPatient(patientService.getPatient(ptId));
				if (!activeVisits.isEmpty()) {
					Date currentDate = new Date();
					Double tempValue = 0.0;
					Double duration = 0.0;
					Double motions = 0.0;
					Date dateCreated = null;
					String onsetStatus = null;
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String todayDate = dateFormat.format(currentDate);
					Patient patient = patientService.getPatient(ptId);

					Encounter lastTriageEncounter = EmrUtils.lastEncounter(patient, triageEncType, triageScreeningForm);
					Encounter lastHivFollowUpEncounter = EmrUtils.lastEncounter(patient, greenCardEncType, greenCardForm);   //last greencard followup form
					Encounter lastClinicalEncounter = EmrUtils.lastEncounter(patient, consultationEncType, clinicalEncounterForm);   //last clinical encounter form

					ConceptService cs = Context.getConceptService();
					Concept screeningQuestion = cs.getConceptByUuid(SCREENING_QUESTION);
					Concept screeningQuestionExam = cs.getConceptByUuid(SCREENING_QUESTION_EXAMINATION);
					Concept typeOfServiceProvided = cs.getConceptByUuid(TYPE_OF_SERVICE_PROVIDED);

					Concept measureFeverResult = cs.getConceptByUuid(FEVER);
					Concept coughPresenceResult = cs.getConceptByUuid(COUGH_PRESENCE);
					Concept adminQuestion = cs.getConceptByUuid(PATIENT_OUTCOME);
					Concept admissionAnswer = cs.getConceptByUuid(INPATIENT_ADMISSION);
					Concept jointPainResult = cs.getConceptByUuid(JOINT_PAIN);
					Concept vomitingResult = cs.getConceptByUuid(VOMITING);
					Concept wateryDiarrheaResult = cs.getConceptByUuid(WATERY_DIARRHEA);
					Concept bloodyStoolResult = cs.getConceptByUuid(BLOOD_IN_STOOL);
					Concept diarrheaResult = cs.getConceptByUuid(DIARRHEA);
					Concept bleedingResult = cs.getConceptByUuid(BLEEDING_TENDENCIES);
					Concept headacheResult = cs.getConceptByUuid(HEADACHE);
					Concept chillsResult = cs.getConceptByUuid(CHILLS);
					Concept rashResult = cs.getConceptByUuid(RASH);
					Concept coryzaResult = cs.getConceptByUuid(CORYZA);
					Concept conjunctivitisResult = cs.getConceptByUuid(CONJUCTIVITIS);
					Concept jaundiceResult = cs.getConceptByUuid(JAUNDICE);
					Concept dizzinessResult = cs.getConceptByUuid(DIZZINESS);
					Concept malaiseResult = cs.getConceptByUuid(MALAISE);
					Concept limbsWeaknessResult = cs.getConceptByUuid(LIMBS_WEAKNESS);
					Concept meningitisResult = cs.getConceptByUuid(SUDDEN_ONSET);
					Concept acuteFebrileRashInfectionResult = cs.getConceptByUuid(RASH);
					Concept refusalToFeedResult = cs.getConceptByUuid(REFUSAL_TO_FEED);
					Concept convulsionsResult = cs.getConceptByUuid(CONVULSIONS);
					Concept lymphadenopathyResult = cs.getConceptByUuid(LYMPHADENOPATHY);
					Concept myalgiaResult = cs.getConceptByUuid(MYALGIA);
					Concept backpainResult = cs.getConceptByUuid(BACKPAIN);
					Concept neckStiffnessResult = cs.getConceptByUuid(NECK_STIFFNESS);
					//Conditions
					String ili = "ILI";
					String sari = "SARI";
					String haemorrhagic_fever = "Acute Haemorrhagic Fever";
					String poliomyelitis = "Acute Flaccid Paralysis";
					// up to here
					String jaundice = "Acute Jaundice";
					String meningitis = "Acute Meningitis and Encephalitis";
					String acute_febrile_rash_infection = "Acute Febrile Rash Infection";
					String acute_watery_diarrhoeal = "Acute Watery Diarrhoeal";
					String neurological_syndrome = "Neurological Syndrome";
					String acute_febrile_illness = "Acute Febrile Illness";
					String mpox = "Mpox";
					//Temperature
					CalculationResultMap tempMap = Calculations.lastObs(cs.getConceptByUuid(TEMPERATURE), cohort, context);
					//Fever
					boolean triageEncounterHasFever = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, measureFeverResult) : false;
					boolean hivFollowupEncounterHasFever = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, measureFeverResult) : false;
					boolean clinicalEncounterHasFever = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, measureFeverResult) : false;
					//Cough
					boolean triageEncounterHasCough = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, coughPresenceResult) : false;
					boolean hivFollowupEncounterHasCough = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, coughPresenceResult) : false;
					boolean clinicalEncounterHasCough = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, coughPresenceResult) : false;
					//Joint Pains
					boolean triageEncounterHasJointPain = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, jointPainResult) : false;
					boolean hivFollowupEncounterHasJointPain = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, jointPainResult) : false;
					boolean clinicalEncounterHasJointPain = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, jointPainResult) : false;
					// Vomiting
					boolean triageEncounterHasVomit = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, vomitingResult) : false;
					boolean hivFollowupEncounterHasVomit = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, vomitingResult) : false;
					boolean clinicalEncounterHasVomit = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, vomitingResult) : false;
					//Watery diarrhea
					boolean triageEncounterHasWateryDiarrhea = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, wateryDiarrheaResult) : false;
					boolean hivFollowupEncounterHasWateryDiarrhea = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, wateryDiarrheaResult) : false;
					boolean clinicalEncounterHasWateryDiarrhea = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, wateryDiarrheaResult) : false;
					//Diarrhea
					boolean triageEncounterHasDiarrhea = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, diarrheaResult) : false;
					boolean hivFollowupEncounterHasDiarrhea = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, diarrheaResult) : false;
					boolean clinicalEncounterHasDiarrhea = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, diarrheaResult) : false;

					//neckStiffnessResult
					boolean triageEncounterHasNeckStiffness = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, neckStiffnessResult) : false;
					boolean hivFollowupEncounterHasNeckStiffness = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, neckStiffnessResult) : false;
					boolean clinicalEncounterHasNeckStiffness = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, neckStiffnessResult) : false;

					//Blood in stool
					boolean triageEncounterHasBloodyStool = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, bloodyStoolResult) : false;
					boolean hivFollowupEncounterHasBloodyStool = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, bloodyStoolResult) : false;
					boolean clinicalEncounterHasBloodyStool = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, bloodyStoolResult) : false;
					//Bleeding tendencies
					boolean triageEncounterHasBleeding = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, bleedingResult) : false;
					boolean hivFollowupEncounterHasBleeding = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, bleedingResult) : false;
					boolean clinicalEncounterHasBleeding = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, bleedingResult) : false;
					//Headache
					boolean triageEncounterHasHeadache = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, headacheResult) : false;
					boolean hivFollowupEncounterHasHeadache = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, headacheResult) : false;
					boolean clinicalEncounterHasHeadache = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, headacheResult) : false;
					//Chills
					boolean triageEncounterHasChills = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, chillsResult) : false;
					boolean hivFollowupEncounterHasChills = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, chillsResult) : false;
					boolean clinicalEncounterHasChills = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, chillsResult) : false;
					//Rash
					boolean triageEncounterHasRash = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, rashResult) : false;
					boolean hivFollowupEncounterHasRash = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, rashResult) : false;
					boolean clinicalEncounterHasRash = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, rashResult) : false;

					//Acute Febrile Rash Infection
					boolean triageEncounterHasAcuteRashInfection = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, acuteFebrileRashInfectionResult) : false;
					boolean hivFollowupEncounterHasAcuteRashInfection = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, acuteFebrileRashInfectionResult) : false;
					boolean clinicalEncounterHasAcuteRashInfection = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, acuteFebrileRashInfectionResult) : false;

					//Refusal to feed
					boolean triageEncounterHasRefusalToFeed = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, refusalToFeedResult) : false;
					boolean hivFollowupEncounterHasRefusalToFeed = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, refusalToFeedResult) : false;
					boolean clinicalEncounterHasRefusalToFeed = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, refusalToFeedResult) : false;

					//Convulsions
					boolean triageEncounterHasConvulsions = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, convulsionsResult) : false;
					boolean hivFollowupEncounterHasConvulsions = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, convulsionsResult) : false;
					boolean clinicalEncounterHasConvulsions = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, convulsionsResult) : false;

					//Coryza
					boolean triageEncounterHasCoryza = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, coryzaResult) : false;
					boolean hivFollowupEncounterHasCoryza = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, coryzaResult) : false;
					boolean clinicalEncounterHasCoryza = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, coryzaResult) : false;
					//Conjunctivitis
					boolean triageEncounterHasConjunctivitis = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, conjunctivitisResult) : false;
					boolean hivFollowupEncounterHasConjunctivitis = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, conjunctivitisResult) : false;
					boolean clinicalEncounterHasConjunctivitis = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, conjunctivitisResult) : false;
					//Jaundice
					boolean triageEncounterHasJaundice = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestionExam, jaundiceResult) : false;
					boolean hivFollowupEncounterHasJaundice = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestionExam, jaundiceResult) : false;
					boolean clinicalEncounterHasJaundice = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestionExam, jaundiceResult) : false;
					//Dizziness
					boolean triageEncounterHasDizziness = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, dizzinessResult) : false;
					boolean hivFollowupEncounterHasDizziness = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, dizzinessResult) : false;
					boolean clinicalEncounterHasDizziness = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, dizzinessResult) : false;
					//Malaise
					boolean triageEncounterHasMalaise = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, malaiseResult) : false;
					boolean hivFollowupEncounterHasMalaise = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, malaiseResult) : false;
					boolean clinicalEncounterHasMalaise = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, malaiseResult) : false;
					//Weakness of limbs
					boolean triageEncounterHasWeakLimbs = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, limbsWeaknessResult) : false;
					boolean hivFollowupEncounterHasWeakLimbs = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, limbsWeaknessResult) : false;
					boolean clinicalEncounterHasWeakLimbs = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, limbsWeaknessResult) : false;

					//Acute Meningitis and Encephalitis Syndrome
//				boolean triageEncounterHasMeningitis = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, meningitisResult) : false;
//				boolean hivFollowupEncounterHasMeningitis = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, meningitisResult) : false;
//				boolean clinicalEncounterHasMeningitis = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, meningitisResult) : false;

					//Sudden Onset
					boolean triageEncounterHasSuddenOnset = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, meningitisResult) : false;
					boolean hivFollowupEncounterHasSuddenOnset = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, meningitisResult) : false;
					boolean clinicalEncounterHasSuddenOnset = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, meningitisResult) : false;

					//lymphadenopathy
					boolean triageEncounterHasLymphadenopathy = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, lymphadenopathyResult) : false;
					boolean hivFollowupEncounterHasLymphadenopathy = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, lymphadenopathyResult) : false;
					boolean clinicalEncounterHasLymphadenopathy = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, lymphadenopathyResult) : false;
					//myalgia
					boolean triageEncounterHasMyalgia = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, myalgiaResult) : false;
					boolean hivFollowupEncounterHasMyalgia = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, myalgiaResult) : false;
					boolean clinicalEncounterHasMyalgia = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, myalgiaResult) : false;
					//backpain
					boolean triageEncounterHasBackpain = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, backpainResult) : false;
					boolean hivFollowupEncounterHasBackpain = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, backpainResult) : false;
					boolean clinicalEncounterHasBackpain = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, backpainResult) : false;

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
						if (triageEncounterHasFever && triageEncounterHasCough) {
							for (Obs obs : lastTriageEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
								}
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if ((duration > 0.0 && duration < 10) && tempValue != null && tempValue >= 38.0) {
										if (createdDate.equals(todayDate)) {
											if (!patientAdmissionStatus && !currentVisit.getVisitType().getUuid().equals("a73e2ac6-263b-47fc-99fc-e0f2c09fc914")) {
												eligible = true;
												idsrMessage.add(ili);
												break;
											} else {
												eligible = true;
												idsrMessage.add(sari);
												break;
											}
										}
									}
								}
							}
						}
						//2. Viral Haemorrhagic fever
						if (triageEncounterHasFever && triageEncounterHasBleeding) {
							for (Obs obs : lastTriageEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
								}
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (duration > 0.0 && duration < 14) {
										if (createdDate.equals(todayDate)) {
											eligible = true;
											idsrMessage.add(haemorrhagic_fever);
											break;
										}
									}
								}
							}
						}
						//3.Poliomyelitis
						if (triageEncounterHasWeakLimbs) {
							if (patient.getAge() < 15) {
								for (Obs obs : lastTriageEncounter.getObs()) {
									dateCreated = obs.getDateCreated();
									if (obs.getConcept().getUuid().equals(ONSET_QUESTION)) {
										onsetStatus = obs.getValueCoded().getUuid();
									}
									if (dateCreated != null && onsetStatus != null) {
										String createdDate = dateFormat.format(dateCreated);
										if (createdDate.equals(todayDate) && onsetStatus.equals(SUDDEN_ONSET)) {
											eligible = true;
											idsrMessage.add(poliomyelitis);
											break;
										}
									}
								}
							}

						}
						//4. Acute Meningitis and Encephalitis Syndrome
						if (triageEncounterHasNeckStiffness) {
							for (Obs obs : lastTriageEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(ONSET_QUESTION)) {
									onsetStatus = obs.getValueCoded().getUuid();
								}
								if (dateCreated != null && onsetStatus != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (createdDate.equals(todayDate) && onsetStatus.equals(SUDDEN_ONSET)) {
										eligible = true;
										idsrMessage.add(meningitis);
										break;
									}
								}
							}
						}
						//5. Acute Febrile Rash Infection
						if (triageEncounterHasFever && triageEncounterHasAcuteRashInfection) {
							for (Obs obs : lastTriageEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
								}
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if ((duration > 0.0 && duration < 14)) {
										if (createdDate.equals(todayDate)) {
											eligible = true;
											idsrMessage.add(acute_febrile_rash_infection);
											break;
										}
									}
								}
							}
						}
						//6. Neurological Syndrome
						if (triageEncounterHasRefusalToFeed && triageEncounterHasConvulsions) {
							DateTime birthDate = new DateTime(patient.getBirthdate());
							int ageInDays = Days.daysBetween(birthDate, new DateTime()).getDays();
							if (ageInDays >= 2 && ageInDays <= 28) {
								for (Obs obs : lastTriageEncounter.getObs()) {
									dateCreated = obs.getDateCreated();
									if (dateCreated != null) {
										String createdDate = dateFormat.format(dateCreated);
										if (createdDate.equals(todayDate)) {
											eligible = true;
											idsrMessage.add(neurological_syndrome);
											break;
										}
									}
								}
							}
						}
						//7. Acute Febrile Illness
						if (triageEncounterHasFever) {
							for (Obs obs : lastTriageEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
								}
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if ((duration > 0.0 && duration < 14) && tempValue != null && tempValue >= 38.0) {
										if (createdDate.equals(todayDate)) {
											eligible = true;
											idsrMessage.add(acute_febrile_illness);
											break;
										}
									}
								}
							}
						}
						//8. Acute Watery Diarrhoeal Disease
						if (triageEncounterHasWateryDiarrhea) {
							for (Obs obs : lastTriageEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(NUMBER_OF_MOTIONS)) {
									motions = obs.getValueNumeric();
								}
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
								}
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if ((motions < 3)) {
										if ((duration > 0.0 && duration < 14)) {
											if (createdDate.equals(todayDate)) {
												eligible = true;
												idsrMessage.add(acute_watery_diarrhoeal);
												break;
											}
										}
									}
								}
							}
						}
						//9. Jaundice
						if (triageEncounterHasJaundice) {
							for (Obs obs : lastTriageEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.add(jaundice);
										break;
									}
								}
							}
						}
						//10. Monkeypox
						if (triageEncounterHasRash && triageEncounterHasFever) {
							if (triageEncounterHasHeadache || triageEncounterHasLymphadenopathy || triageEncounterHasMyalgia || triageEncounterHasBackpain)
								for (Obs obs : lastTriageEncounter.getObs()) {
									dateCreated = obs.getDateCreated();
									if (dateCreated != null) {
										String createdDate = dateFormat.format(dateCreated);
										if (tempValue != null && tempValue >= 38.5) {
											if (createdDate.equals(todayDate)) {
												eligible = true;
												idsrMessage.add(mpox);
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
						if (hivFollowupEncounterHasFever && hivFollowupEncounterHasCough) {
							for (Obs obs : lastHivFollowUpEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
								}
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if ((duration > 0.0 && duration < 10) && tempValue != null && tempValue >= 38.0) {
										if (createdDate.equals(todayDate)) {
											if (!patientAdmissionStatus && !currentVisit.getVisitType().getUuid().equals("a73e2ac6-263b-47fc-99fc-e0f2c09fc914")) {
												eligible = true;
												idsrMessage.add(ili);
												break;
											} else {
												eligible = true;
												idsrMessage.add(sari);
												break;
											}
										}
									}
								}
							}
						}
						//2. Acute Haemorrhagic fever
						if (hivFollowupEncounterHasFever && hivFollowupEncounterHasBleeding) {
							for (Obs obs : lastHivFollowUpEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
								}
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (duration > 0.0 && duration < 14) {
										if (createdDate.equals(todayDate)) {
											eligible = true;
											idsrMessage.add(haemorrhagic_fever);
											break;
										}
									}
								}
							}
						}
						//3.Poliomyelitis
						if (hivFollowupEncounterHasWeakLimbs) {
							if (patient.getAge() < 15) {
								for (Obs obs : lastHivFollowUpEncounter.getObs()) {
									dateCreated = obs.getDateCreated();
									if (obs.getConcept().getUuid().equals(ONSET_QUESTION)) {
										onsetStatus = obs.getValueCoded().getUuid();
									}
									if (dateCreated != null && onsetStatus != null) {
										String createdDate = dateFormat.format(dateCreated);
										if (createdDate.equals(todayDate) && onsetStatus.equals(SUDDEN_ONSET)) {
											eligible = true;
											idsrMessage.add(poliomyelitis);
											break;
										}
									}
								}

							}
						}
						// 4. Jaundice
						if (hivFollowupEncounterHasJaundice) {
							for (Obs obs : lastHivFollowUpEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.add(jaundice);
										break;
									}
								}
							}
						}
						//5. Acute Menigitis and Encephalitis Syndrome
						if (hivFollowupEncounterHasNeckStiffness) {
							for (Obs obs : lastHivFollowUpEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(ONSET_QUESTION)) {
									onsetStatus = obs.getValueCoded().getUuid();
								}
								if (dateCreated != null && onsetStatus != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (createdDate.equals(todayDate) && onsetStatus.equals(SUDDEN_ONSET)) {
										eligible = true;
										idsrMessage.add(meningitis);
										break;
									}
								}
							}
						}
						//6. Acute Febrile Rash Infection
						if (hivFollowupEncounterHasFever && hivFollowupEncounterHasAcuteRashInfection) {
							for (Obs obs : lastHivFollowUpEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
								}
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if ((duration > 0.0 && duration < 14)) {
										if (createdDate.equals(todayDate)) {
											eligible = true;
											idsrMessage.add(acute_febrile_rash_infection);
											break;
										}
									}
								}
							}
						}
						//7. Neurological Syndrome
						if (hivFollowupEncounterHasRefusalToFeed && hivFollowupEncounterHasConvulsions) {
							DateTime birthDate = new DateTime(patient.getBirthdate());
							int ageInDays = Days.daysBetween(birthDate, new DateTime()).getDays();
							if (ageInDays >= 2 && ageInDays <= 28) {
								for (Obs obs : lastTriageEncounter.getObs()) {
									dateCreated = obs.getDateCreated();
									if (dateCreated != null) {
										String createdDate = dateFormat.format(dateCreated);
										if (createdDate.equals(todayDate)) {
											eligible = true;
											idsrMessage.add(neurological_syndrome);
											break;
										}
									}
								}
							}
						}
						//8. Acute Febrile Illness
						if (hivFollowupEncounterHasFever) {
							for (Obs obs : lastHivFollowUpEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
								}
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if ((duration > 0.0 && duration < 14) && tempValue != null && tempValue >= 38.0) {
										if (createdDate.equals(todayDate)) {
											eligible = true;
											idsrMessage.add(acute_febrile_illness);
											break;
										}
									}
								}
							}
						}
						//9. Acute Watery Diarrhoeal Disease
						if (hivFollowupEncounterHasWateryDiarrhea) {
							for (Obs obs : lastHivFollowUpEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(NUMBER_OF_MOTIONS)) {
									motions = obs.getValueNumeric();
								}
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
								}
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if ((motions < 3)) {
										if ((duration > 0.0 && duration < 14)) {
											if (createdDate.equals(todayDate)) {
												eligible = true;
												idsrMessage.add(acute_watery_diarrhoeal);
												break;
											}
										}
									}
								}
							}
						}
						//10. Mpox
						if (hivFollowupEncounterHasRash && hivFollowupEncounterHasFever) {
							if (hivFollowupEncounterHasHeadache || hivFollowupEncounterHasLymphadenopathy || hivFollowupEncounterHasMyalgia || hivFollowupEncounterHasBackpain) {
								for (Obs obs : lastHivFollowUpEncounter.getObs()) {
									dateCreated = obs.getDateCreated();
									if (dateCreated != null) {
										String createdDate = dateFormat.format(dateCreated);
										if (tempValue != null && tempValue >= 38.5) {
											if (createdDate.equals(todayDate)) {
												eligible = true;
												idsrMessage.add(mpox);
												break;
											}
										}
									}
								}
							}
						}
					}
					//Clinical Encounter
					if (lastClinicalEncounter != null) {
						//1. SARI and ILI
						if (clinicalEncounterHasFever && clinicalEncounterHasCough) {
							for (Obs obs : lastClinicalEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
									if (dateCreated != null) {
										String createdDate = dateFormat.format(dateCreated);
										if ((duration > 0.0 && duration < 10) && tempValue != null && tempValue >= 38.0) {
											if (createdDate.equals(todayDate)) {
												if (!patientAdmissionStatus && !currentVisit.getVisitType().getUuid().equals("a73e2ac6-263b-47fc-99fc-e0f2c09fc914")) {
													eligible = true;
													idsrMessage.add(ili);
													break;
												} else {
													eligible = true;
													idsrMessage.add(sari);
													break;
												}
											}
										}
									}
								}
							}
						}
						//2. Acute Haemorrhagic fever
						if (clinicalEncounterHasFever && clinicalEncounterHasBleeding) {
							for (Obs obs : lastClinicalEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
								}
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (duration > 0.0 && duration < 14) {
										if (createdDate.equals(todayDate)) {
											eligible = true;
											idsrMessage.add(haemorrhagic_fever);
											break;
										}
									}
								}
							}

						}
						//3.Poliomyelitis
						if (clinicalEncounterHasWeakLimbs) {
							if (patient.getAge() < 15) {
								for (Obs obs : lastClinicalEncounter.getObs()) {
									dateCreated = obs.getDateCreated();
									if (obs.getConcept().getUuid().equals(ONSET_QUESTION)) {
										onsetStatus = obs.getValueCoded().getUuid();
									}
									if (dateCreated != null && onsetStatus != null) {
										String createdDate = dateFormat.format(dateCreated);
										if (createdDate.equals(todayDate) && onsetStatus.equals(SUDDEN_ONSET)) {
											eligible = true;
											idsrMessage.add(poliomyelitis);
											break;
										}
									}
								}
							}
						}
						//4. Jaundice
						if (clinicalEncounterHasJaundice) {
							for (Obs obs : lastClinicalEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (createdDate.equals(todayDate)) {
										eligible = true;
										idsrMessage.add(jaundice);
										break;
									}
								}
							}
						}
						//5. Acute Meningitis and Encephalitis Syndrome
						if (clinicalEncounterHasNeckStiffness) {
							for (Obs obs : lastClinicalEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(ONSET_QUESTION)) {
									onsetStatus = obs.getValueCoded().getUuid();
								}
								if (dateCreated != null && onsetStatus != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (createdDate.equals(todayDate) && onsetStatus.equals(SUDDEN_ONSET)) {
										eligible = true;
										idsrMessage.add(meningitis);
										break;
									}
								}
							}
						}
						//6. Acute Febrile Rash Infection
						if (clinicalEncounterHasFever && clinicalEncounterHasAcuteRashInfection) {
							for (Obs obs : lastClinicalEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
								}
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if ((duration > 0.0 && duration < 14)) {
										if (createdDate.equals(todayDate)) {
											eligible = true;
											idsrMessage.add(acute_febrile_rash_infection);
											break;
										}
									}
								}
							}
						}
						//7. Neurological Syndrome
						if (clinicalEncounterHasRefusalToFeed && clinicalEncounterHasConvulsions) {
							DateTime birthDate = new DateTime(patient.getBirthdate());
							int ageInDays = Days.daysBetween(birthDate, new DateTime()).getDays();
							if (ageInDays >= 2 && ageInDays <= 28) {
								for (Obs obs : lastClinicalEncounter.getObs()) {
									dateCreated = obs.getDateCreated();
									if (dateCreated != null) {
										String createdDate = dateFormat.format(dateCreated);
										if (createdDate.equals(todayDate)) {
											eligible = true;
											idsrMessage.add(neurological_syndrome);
											break;
										}
									}
								}
							}
						}
						//8. Acute Febrile Illness
						if (clinicalEncounterHasFever) {
							for (Obs obs : lastClinicalEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
								}
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if ((duration > 0.0 && duration < 14) && tempValue != null && tempValue >= 38.0) {
										if (createdDate.equals(todayDate)) {
											eligible = true;
											idsrMessage.add(acute_febrile_illness);
											break;
										}
									}
								}
							}
						}
						//9. Acute Watery Diarrhoeal Disease
						if (clinicalEncounterHasWateryDiarrhea) {
							for (Obs obs : lastClinicalEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (obs.getConcept().getUuid().equals(NUMBER_OF_MOTIONS)) {
									motions = obs.getValueNumeric();
								}
								if (obs.getConcept().getUuid().equals(DURATION)) {
									duration = obs.getValueNumeric();
								}
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if ((motions < 3)) {
										if ((duration > 0.0 && duration < 14)) {
											if (createdDate.equals(todayDate)) {
												eligible = true;
												idsrMessage.add(acute_watery_diarrhoeal);
												break;
											}
										}
									}
								}
							}
						}
						//10. Mpox
						if (clinicalEncounterHasRash && clinicalEncounterHasFever) {
							if (clinicalEncounterHasHeadache || clinicalEncounterHasLymphadenopathy || clinicalEncounterHasMyalgia || clinicalEncounterHasBackpain) {
								for (Obs obs : lastClinicalEncounter.getObs()) {
									dateCreated = obs.getDateCreated();
									if (dateCreated != null) {
										String createdDate = dateFormat.format(dateCreated);
										if (tempValue != null && tempValue >= 38.5) {
											if (createdDate.equals(todayDate)) {
												eligible = true;
												idsrMessage.add(mpox);
												break;
											}
										}
									}
								}
							}
						}

					}


				}

				if (idsrMessage.size() > 0) {
					idsrMessageString = StringUtils.join(idsrMessage, ",");

				}
				ret.put(ptId, new BooleanResult(eligible, this));
			}
		}
		return ret;
	}

	@Override
	public String getFlagMessage() {
			return "Suspected " + idsrMessageString;
	}
}
