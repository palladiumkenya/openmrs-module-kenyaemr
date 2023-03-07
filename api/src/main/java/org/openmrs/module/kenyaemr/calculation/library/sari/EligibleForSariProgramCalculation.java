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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.*;
import org.openmrs.module.kenyaemr.calculation.library.EligibleForPregnancyTestCalculation;
import org.openmrs.module.kenyaemr.calculation.library.IsPregnantCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class EligibleForSariProgramCalculation extends AbstractPatientCalculation {
    protected static final Log log = LogFactory.getLog(EligibleForPregnancyTestCalculation.class);

    public static final EncounterType sariEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.SARI_SCREENING);
    public static final Form sariScreeningForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.SARI_SCREENING);

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {

        Set<Integer> alive = Filters.alive(cohort, context);
        PatientService patientService = Context.getPatientService();
        CalculationResultMap ret = new CalculationResultMap();
        for (Integer ptId :alive) {
            boolean result = false;
            Patient patient = patientService.getPatient(ptId);
            Encounter lastSariEnc = EmrUtils.lastEncounter(patient, sariEncType, sariScreeningForm);
                if (lastSariEnc != null) {
                    result = true;
                }
            ret.put(ptId, new BooleanResult(result, this));
        }
        return ret;
    }}
