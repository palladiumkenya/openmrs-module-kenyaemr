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
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.CalculationUtils;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.calculation.library.EligibleForPregnancyTestCalculation;
import org.openmrs.module.kenyaemr.calculation.library.IsPregnantCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.SARIMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class EligibleForSariFollowupCalculation extends AbstractPatientCalculation {
    protected static final Log log = LogFactory.getLog(EligibleForPregnancyTestCalculation.class);

    public static final EncounterType sariEnrollType = MetadataUtils.existing(EncounterType.class, SARIMetadata._EncounterType.SARI_ENROLLMENT);
    public static final Form sariEnrolForm = MetadataUtils.existing(Form.class, SARIMetadata._Form.SARI_ENROLLMENT_FORM);
    public static final EncounterType iliEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.ILI_SCREENING);
    public static final Form iliScreeningForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.ILI_SCREENING);

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {

        Set<Integer> alive = Filters.alive(cohort, context);
        PatientService patientService = Context.getPatientService();
        CalculationResultMap ret = new CalculationResultMap();
        for (Integer ptId :alive) {
            boolean result = false;
            Patient patient = patientService.getPatient(ptId);
                Encounter lastSariEnc = EmrUtils.lastEncounter(patient, sariEnrollType, sariEnrolForm);
                Encounter lastiLiEnc = EmrUtils.lastEncounter(patient, iliEncType, iliScreeningForm);
                if (lastSariEnc != null  || lastiLiEnc !=null ) {
                    result = true;
                }
            ret.put(ptId, new BooleanResult(result, this));
        }
        return ret;
    }}
