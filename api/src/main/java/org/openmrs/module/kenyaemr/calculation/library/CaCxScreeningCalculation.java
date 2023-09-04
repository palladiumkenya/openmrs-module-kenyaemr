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
import org.openmrs.*;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils.daysSince;

public class CaCxScreeningCalculation extends AbstractPatientCalculation {
    protected static final Log log = LogFactory.getLog(CaCxScreeningCalculation.class);
    public static final EncounterType cacxEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.CACX_SCREENING);
    public static final EncounterType cacxAscEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.CACX_SCREENING);
    public static final Form cacxScreeningForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.CACX_SCREENING_FORM);
    public static final Form cacxAscForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.CACX_ASSESSMENT_FORM);
    public static final Integer CACX_TEST_RESULT_QUESTION_CONCEPT_ID = 164934;
    public static final Integer CACX_SCREEENING_METHOD_QUESTION_CONCEPT_ID = 163589;
    Integer NOT_DONE = 1066;
    Integer NEGATIVE = 664;
    Integer HPV_TEST_CONCEPT_ID = 159859;

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) {
        Set<Integer> aliveAndFemale = Filters.female(Filters.alive(cohort, context), context);
        CalculationResultMap ret = new CalculationResultMap();
        PatientService patientService = Context.getPatientService();
        for(Integer ptId:aliveAndFemale) {
            Patient patient = patientService.getPatient(ptId);
            boolean needsCacxTest = false;
            ConceptService cs = Context.getConceptService();
            Concept cacxTestResultQuestion = cs.getConcept(CACX_TEST_RESULT_QUESTION_CONCEPT_ID);
            Concept cacxNotDoneResult = cs.getConcept(NOT_DONE);
            Concept cacxNegativeResult = cs.getConcept(NEGATIVE);
            Concept cacxHpvScreeningMethod = cs.getConcept(HPV_TEST_CONCEPT_ID);
            Concept cacxScreeningMethodQuestion = cs.getConcept(CACX_SCREEENING_METHOD_QUESTION_CONCEPT_ID);


            Encounter lastCacxScreeningEnc = EmrUtils.lastEncounter(patient, cacxEncType, cacxScreeningForm);
            Encounter lastCacxAscEnc = EmrUtils.lastEncounter(patient, cacxAscEncType, cacxAscForm);

            boolean patientHasNotDoneTestResult = lastCacxAscEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastCacxAscEnc, cacxTestResultQuestion, cacxNotDoneResult) : false;
            boolean patientHasNegativeTestResult = lastCacxScreeningEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastCacxScreeningEnc, cacxTestResultQuestion, cacxNegativeResult) : false;
            boolean patientScreenedUsingHPV = lastCacxScreeningEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastCacxScreeningEnc, cacxScreeningMethodQuestion, cacxHpvScreeningMethod) : false;

            if(patient.getAge() >= 25 && patient.getAge() <= 49) {
                if (lastCacxAscEnc != null && patientHasNotDoneTestResult ){
                    needsCacxTest = true;
                }

                if(lastCacxScreeningEnc != null && !patientScreenedUsingHPV && patientHasNegativeTestResult && (daysSince(lastCacxScreeningEnc.getEncounterDatetime(), context) >= 365)) {
                    needsCacxTest = true;
                }
                if(lastCacxScreeningEnc != null && patientScreenedUsingHPV && patientHasNegativeTestResult && (daysSince(lastCacxScreeningEnc.getEncounterDatetime(), context) >= 730)) {
                    needsCacxTest = true;
                }

            }
            ret.put(ptId, new BooleanResult(needsCacxTest, this));
        }

        return ret;
    }
}
