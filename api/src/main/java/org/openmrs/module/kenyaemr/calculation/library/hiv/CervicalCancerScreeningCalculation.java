/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.calculation.library.hiv;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils.daysSince;

/*
* Women within this age group who have never been screened for cervical cancer
* Women within this age group who have had a screening done before but they are due for another screening based on the previous screening test and result
* For clients previously screened using any other method except HPV and they had a negative result, they are eligible for rescreening after 12 months
* For clients previously screened using HPV test and had a Negative result, they are eligible for re screening after 2 years
*
* */
public class CervicalCancerScreeningCalculation extends AbstractPatientCalculation {
    Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
    protected static final Log log = LogFactory.getLog(CervicalCancerScreeningCalculation.class);
    CalculationResultMap ret = new CalculationResultMap();
    PatientService patientService = Context.getPatientService();
    public static final EncounterType cacxEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.CACX_SCREENING);
    public static final Form cacxScreeningForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.CACX_SCREENING_FORM);
    public static final Integer CACX_TEST_RESULT_QUESTION_CONCEPT_ID = 164934;
    public static final Integer CACX_SCREEENING_METHOD_QUESTION_CONCEPT_ID = 163589;
    Integer HPV_TEST_CONCEPT_ID = 159859;
    Integer NORMAL = 1115;
    ConceptService cs = Context.getConceptService();
    Concept cacxTestResultQuestion = cs.getConcept(CACX_TEST_RESULT_QUESTION_CONCEPT_ID);
    Concept cacxNegativeResult = Dictionary.getConcept(Dictionary.NEGATIVE);
    Concept cacxHpvScreeningMethod = cs.getConcept(HPV_TEST_CONCEPT_ID);
    Concept cacxScreeningMethodQuestion = cs.getConcept(CACX_SCREEENING_METHOD_QUESTION_CONCEPT_ID);
    Concept cacxNormalResult = cs.getConcept(NORMAL);
    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) {
        Set<Integer> aliveAndFemale = Filters.female(Filters.alive(cohort, context), context);
        Set<Integer> alive = Filters.alive(cohort, context);
        Set<Integer> inHivProgram = Filters.inProgram(hivProgram, alive, context);
        for(Integer ptId:aliveAndFemale) {
            boolean needsCacxTest = false;
            Patient patient = patientService.getPatient(ptId);
            Encounter lastCacxScreeningEnc = EmrUtils.lastEncounter(patient, cacxEncType, cacxScreeningForm);
            boolean patientHasNegativeTestResult = lastCacxScreeningEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastCacxScreeningEnc, cacxTestResultQuestion, cacxNegativeResult) : false;
            boolean patientScreenedUsingHPV = lastCacxScreeningEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastCacxScreeningEnc, cacxScreeningMethodQuestion, cacxHpvScreeningMethod) : false;
            boolean patientHasNormalTestResult = lastCacxScreeningEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastCacxScreeningEnc, cacxTestResultQuestion, cacxNormalResult) : false;
            if(patient.getAge() >= 25 && patient.getAge() <= 49) {
                if (inHivProgram.contains(ptId)) {
                    if (lastCacxScreeningEnc == null) {
                        needsCacxTest = true;
                    }
                    if (lastCacxScreeningEnc != null && patientScreenedUsingHPV && patientHasNegativeTestResult && (daysSince(lastCacxScreeningEnc.getEncounterDatetime(), context) >= 730)) {
                        needsCacxTest = true;
                    }
                    if (lastCacxScreeningEnc != null && !patientScreenedUsingHPV && (patientHasNegativeTestResult || patientHasNormalTestResult) && (daysSince(lastCacxScreeningEnc.getEncounterDatetime(), context) >= 365)) {
                        needsCacxTest = true;
                    }
                }

            }
            ret.put(ptId, new BooleanResult(needsCacxTest, this));
        }

        return ret;
    }
}
