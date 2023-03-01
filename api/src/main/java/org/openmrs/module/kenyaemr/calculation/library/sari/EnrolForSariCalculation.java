/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.calculation.library.sari;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.ObsService;
import org.openmrs.api.PatientService;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.*;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.SARIMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.ui.framework.SimpleObject;

import java.util.*;

/**
 * Calculates the eligibility for SARI/ILI patients
 */


public class EnrolForSariCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
    protected static final Log log = LogFactory.getLog(EnrolForSariCalculation.class);

    @Override
    public String getFlagMessage() {
        return "Enroll to SARI";
    }
    public static final EncounterType sariEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.SARI_SCREENING);
    public static final Form sariScreeningForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.SARI_SCREENING);
    public static final EncounterType sariEnrollType = MetadataUtils.existing(EncounterType.class, SARIMetadata._EncounterType.SARI_ENROLLMENT);
    public static final Form sariEnrolForm = MetadataUtils.existing(Form.class, SARIMetadata._Form.SARI_ENROLLMENT_FORM);

    /**
     * Evaluates the calculation
     */

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {

        Set<Integer> alive = Filters.alive(cohort, context);
        ObsService obsService = Context.getObsService();
        Concept symptomsQ = Context.getConceptService().getConcept(1729);
        List<Form> formCollectingSymptoms = Arrays.asList(
                Context.getFormService().getFormByUuid("c56140ca-7ad5-4069-9236-ef3495bbd43d")
        );

        PatientService patientService = Context.getPatientService();
        CalculationResultMap ret = new CalculationResultMap();
        for (Integer ptId : alive) {
            boolean result = false;
            Patient patient = patientService.getPatient(ptId);
            List<Person> patients = new ArrayList<Person>();
            Encounter lastSariInc = EmrUtils.lastEncounter(patient, sariEncType, sariScreeningForm);
            Encounter lastSariEnrolInc = EmrUtils.lastEncounter(patient, sariEnrollType, sariEnrolForm);
            patients.add(patient);
            List<Encounter> encounters = Context.getEncounterService().getEncounters(patient, null, null, null, formCollectingSymptoms, null, null, null, null,false);
            List<Concept> question = new ArrayList<Concept>();
            question.add(symptomsQ);

            List<Obs> symptoms = obsService.getObservations(
                    patients,
                    encounters,
                    question,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null, null, false);
            List<SimpleObject> symptomsList = new ArrayList<SimpleObject>();
            Concept abnormalBreath= Context.getConceptService().getConcept(113316);
            Concept wheezing= Context.getConceptService().getConcept(122496);
            Concept soreThroat= Context.getConceptService().getConcept(158843);
            Concept difficultyBreathing= Context.getConceptService().getConcept(122496);
            Concept chestPain= Context.getConceptService().getConcept(120749);
            Concept rhinorrhea= Context.getConceptService().getConcept(165501);
            Concept soreMuscles= Context.getConceptService().getConcept(133632);
            Concept haemoptysis= Context.getConceptService().getConcept(138905);
            Concept chills= Context.getConceptService().getConcept(871);
            Concept diarrhea= Context.getConceptService().getConcept(142412);
            Concept vomiting= Context.getConceptService().getConcept(122983);
            Concept earPain= Context.getConceptService().getConcept(131602);
            Concept skinRash= Context.getConceptService().getConcept(512);
            Concept lackOfAppetite= Context.getConceptService().getConcept(163484);
            Concept conjunctivitis= Context.getConceptService().getConcept(119905);
            Concept convulsions= Context.getConceptService().getConcept(113054);
            Concept rigors= Context.getConceptService().getConcept(127361);

            int symptomsCounter = 0;
            for(Obs obs: symptoms){
                symptomsCounter++;
                String abnormalBreaths = obs.getValueCoded().equals(abnormalBreath)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", abnormalBreaths));
                String wheezings = obs.getValueCoded().equals(wheezing)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", wheezings));
                String soreThroats = obs.getValueCoded().equals(soreThroat)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", soreThroats));
                String difficultyBreathings = obs.getValueCoded().equals(difficultyBreathing)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", difficultyBreathings));
                String chestPains = obs.getValueCoded().equals(chestPain)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", chestPains));
                String rhinorrheas = obs.getValueCoded().equals(rhinorrhea)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", rhinorrheas));
                String soreMuscless = obs.getValueCoded().equals(soreMuscles)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", soreMuscless));
                String haemoptysiss = obs.getValueCoded().equals(haemoptysis)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", haemoptysiss));
                String chillss = obs.getValueCoded().equals(chills)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", chillss));
                String diarrheas = obs.getValueCoded().equals(diarrhea)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", diarrheas));
                String vomitings = obs.getValueCoded().equals(vomiting)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", vomitings));
                String earPains = obs.getValueCoded().equals(earPain)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", earPains));
                String skinRashs = obs.getValueCoded().equals(skinRash)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", skinRashs));
                String lackOfAppetites = obs.getValueCoded().equals(lackOfAppetite)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", lackOfAppetites));
                String conjunctivitiss = obs.getValueCoded().equals(conjunctivitis)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", conjunctivitiss));
                String convulsionss = obs.getValueCoded().equals(convulsions)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", convulsionss));
                String rigorss = obs.getValueCoded().equals(rigors)? "": symptomsCounter + "." + obs.getValueCoded().getName().getName();
                symptomsList.add(SimpleObject.create("", rigorss));

              }
            if(lastSariInc == null || lastSariInc.getEncounterDatetime() == null || !DateUtils.isSameDay(new Date(), lastSariInc.getEncounterDatetime())) {
                result = false;
            }else if(lastSariInc != null  && lastSariEnrolInc !=null){
                result = false;
            } else {
                if (symptomsCounter >= 5) {
                    result = true;
                }
            }

                ret.put(ptId, new BooleanResult(result, this));
            }

            return ret;
        }
    }
