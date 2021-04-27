/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.fragment.controller.program.mchps;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.patient.PatientCalculationService;
import org.openmrs.module.kenyaemr.form.velocity.EmrVelocityFunctions;
import org.openmrs.module.kenyaemr.metadata.MchMetadata;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for MCH care summary
 */
public class MchpsCarePanelFragmentController {
    protected static final Log log = LogFactory.getLog(EmrVelocityFunctions.class);

    public void controller(@FragmentParam("patient") Patient patient,
                           @FragmentParam("complete") Boolean complete,
                           FragmentModel model) {
        Map<String, Object> calculations = new HashMap<String, Object>();
        PatientCalculationContext context = Context.getService(PatientCalculationService.class).createCalculationContext();
        context.setNow(new Date());
        PatientWrapper patientWrapper = new PatientWrapper(patient);

        Encounter lastMchEnrollment = patientWrapper.lastEncounter(MetadataUtils.existing(EncounterType.class, MchMetadata._EncounterType.MCHPS_ENROLLMENT));
        Encounter lastMchFollowup = patientWrapper.lastEncounter(MetadataUtils.existing(EncounterType.class, MchMetadata._EncounterType.MCH_SURVEY));

        if (lastMchEnrollment != null) {
            calculations.put("enrollmentDate", lastMchEnrollment.getEncounterDatetime());
        }
        if (lastMchFollowup != null) {
            calculations.put("lastSurveyDate", lastMchFollowup.getEncounterDatetime());
        }
        model.addAttribute("calculations", calculations);

    }
}