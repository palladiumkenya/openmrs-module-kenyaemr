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

import static org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils.daysSince;
//*
// Provide alerts for HPV clients who are due for vaccination. This is for girls aged 9 - 14 years.
//The vaccination is administered in 3 doses. The first dose, second given 1-2 months after receiving the 1st Dose then the third dose given at month 6.
// */
public class HpvVaccinationCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
    private String hvpVaccination;
    public static final EncounterType cacxEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.CACX_SCREENING);
    public static final Form cacxScreeningForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.CACX_SCREENING_FORM);
    Integer HPV_TEST_CONCEPT_ID = 159859;
    Integer CACX_SCREEENING_METHOD_QUESTION_CONCEPT_ID = 163589;
    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) {
        CalculationResultMap ret = new CalculationResultMap();
        PatientService patientService = Context.getPatientService();
        Set<Integer> aliveAndFemale = Filters.female(Filters.alive(cohort, context), context);
        for(Integer ptId:aliveAndFemale) {
            Patient patient = patientService.getPatient(ptId);
            boolean hpvTest = false;
            ConceptService cs = Context.getConceptService();
            Concept cacxHpvScreeningMethod = cs.getConcept(HPV_TEST_CONCEPT_ID);
            Concept cacxScreeningMethodQuestion = cs.getConcept(CACX_SCREEENING_METHOD_QUESTION_CONCEPT_ID);
            Encounter lastCacxScreeningEnc = EmrUtils.lastEncounter(patient, cacxEncType, cacxScreeningForm);
            boolean patientScreenedUsingHPV = lastCacxScreeningEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastCacxScreeningEnc, cacxScreeningMethodQuestion, cacxHpvScreeningMethod) : false;
            if(patient.getAge() >= 9 && patient.getAge() <= 14){
                if(lastCacxScreeningEnc != null && patientScreenedUsingHPV ) {
                    if ((daysSince(lastCacxScreeningEnc.getEncounterDatetime(), context) >= 31)  && (daysSince(lastCacxScreeningEnc.getEncounterDatetime(), context) <= 62)) {
                        hvpVaccination = "Eligible for HPV second dose";
                        hpvTest = true;
                    } else if ((daysSince(lastCacxScreeningEnc.getEncounterDatetime(), context) >= 183)) {
                        hvpVaccination = "Eligible for HPV Third dose";
                        hpvTest = true;
                    }
                } else {
                    hpvTest = false;
                }
            }
            ret.put(ptId, new BooleanResult(hpvTest, this));
        }
        return ret;
    }
    @Override
    public String getFlagMessage() {
        return hvpVaccination;
    }
}

