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

import org.openmrs.*;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.*;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.util.*;

//*
// Provide alerts for HPV clients who are due for vaccination. This is for girls aged 9 - 14 years and HPV vaccination question in triage is NO.
//
public class HpvVaccinationCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
    public static final EncounterType triageEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.TRIAGE);
    public static final Form TriageForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.TRIAGE);
    Integer NO = 1066;
    Integer HPV_VACCINATION = 160325;
    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) {
        CalculationResultMap ret = new CalculationResultMap();
        PatientService patientService = Context.getPatientService();
        Set<Integer> aliveAndFemale = Filters.female(Filters.alive(cohort, context), context);
        for(Integer ptId:aliveAndFemale) {
            Patient patient = patientService.getPatient(ptId);
            boolean hpvTest = false;
            ConceptService cs = Context.getConceptService();
            Concept hpvNoAnswer = cs.getConcept(NO);
            Concept hpvVaccinationQuestion = cs.getConcept(HPV_VACCINATION);
            Encounter lastTriageEnc = EmrUtils.lastEncounter(patient, triageEncType, TriageForm);
            boolean patientVaccinatedHPV = lastTriageEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEnc, hpvVaccinationQuestion, hpvNoAnswer) : false;
            if(patient.getAge() >= 9 && patient.getAge() <= 14){
                if(lastTriageEnc != null && patientVaccinatedHPV ) {
                    hpvTest = true;
                }
            }
            ret.put(ptId, new BooleanResult(hpvTest, this));
        }
        return ret;
    }
    @Override
    public String getFlagMessage() {
        return "Due for HPV Dose";
    }

}

