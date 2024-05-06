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
 * Calculates the eligibility for Poliomyelitis screening flag for  patients
 * @should calculate Active visit
 * @should calculate Child less than 15years of age.
 * @should calculate limb weakness
 * @should calculate no duration
 */
public class EligibleForPoliomyelitisCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
    protected static final Log log = LogFactory.getLog(EligibleForPoliomyelitisCalculation.class);

	public static final EncounterType triageEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.TRIAGE);
	public static final Form triageScreeningForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.TRIAGE);
    public static final EncounterType consultationEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.CONSULTATION);
    public static final Form clinicalEncounterForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.CLINICAL_ENCOUNTER);
    public static final EncounterType greenCardEncType = MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_CONSULTATION);
    public static final Form greenCardForm = MetadataUtils.existing(Form.class, HivMetadata._Form.HIV_GREEN_CARD);

    @Override
    public String getFlagMessage() {
        return "Suspected Poliomyelitis case";
    }

    Integer LIMBS_WEAKNESS = 157498;
    Integer SCREENING_QUESTION = 5219;

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
                Date dateCreated = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String todayDate = dateFormat.format(currentDate);
                Patient patient = patientService.getPatient(ptId);

				Encounter lastTriageEncounter = EmrUtils.lastEncounter(patient, triageEncType, triageScreeningForm);   //last triage form
                Encounter lastHivFollowUpEncounter = EmrUtils.lastEncounter(patient, greenCardEncType, greenCardForm);   //last greencard followup form
                Encounter lastClinicalEncounter = EmrUtils.lastEncounter(patient, consultationEncType, clinicalEncounterForm);   //last clinical encounter form

                ConceptService cs = Context.getConceptService();
                Concept limbsWeaknessResult = cs.getConcept(LIMBS_WEAKNESS);
                Concept screeningQuestion = cs.getConcept(SCREENING_QUESTION);

                boolean patientWeakLimbsResultTriage = lastTriageEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageEncounter, screeningQuestion, limbsWeaknessResult) : false;
                boolean patientWeakLimbsResultGreenCard = lastHivFollowUpEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastHivFollowUpEncounter, screeningQuestion, limbsWeaknessResult) : false;
                boolean patientWeakLimbsResultClinical = lastClinicalEncounter != null ? EmrUtils.encounterThatPassCodedAnswer(lastClinicalEncounter, screeningQuestion, limbsWeaknessResult) : false;

                if (patient.getAge() < 15) {
                    if (lastHivFollowUpEncounter != null) {
                        if (patientWeakLimbsResultGreenCard) {
                            for (Obs obs : lastHivFollowUpEncounter.getObs()) {
                                dateCreated = obs.getDateCreated();
                                if (dateCreated != null) {
                                    String createdDate = dateFormat.format(dateCreated);
                                    if (createdDate.equals(todayDate)) {
                                        eligible = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
					if (lastClinicalEncounter != null) {
						if (patientWeakLimbsResultClinical) {
							for (Obs obs : lastClinicalEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
									if (createdDate.equals(todayDate)) {
										eligible = true;
										break;
									}
								}
							}
						}
					}
					if (lastTriageEncounter != null) {
						if (patientWeakLimbsResultTriage) {
							for (Obs obs : lastTriageEncounter.getObs()) {
								dateCreated = obs.getDateCreated();
								if (dateCreated != null) {
									String createdDate = dateFormat.format(dateCreated);
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
