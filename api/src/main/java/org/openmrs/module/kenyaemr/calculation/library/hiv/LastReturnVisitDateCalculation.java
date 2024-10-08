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

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;

import org.openmrs.api.EncounterService;
import org.openmrs.api.FormService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.appointments.service.AppointmentServiceDefinitionService;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by codehub on 23/06/15.
 */
public class LastReturnVisitDateCalculation extends AbstractPatientCalculation {

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> map, PatientCalculationContext context) {

        String PREP_MONTHLY_REFILL_FORM = "291c03c8-a216-11e9-a2a3-2a2ae2dbcce4";
        String PREP_INITIAL_FORM = "1bfb09fc-56d7-4108-bd59-b2765fd312b8";
        String PREP_CONSULTATION_FORM = "ee3e2017-52c0-4a54-99ab-ebb542fb8984";
        String PREP_MONTHLY_REFILL_ENCOUNTERTYPE = "291c0828-a216-11e9-a2a3-2a2ae2dbcce4";
        String PREP_INITIAL_ENCOUNTERTYPE = "706a8b12-c4ce-40e4-aec3-258b989bf6d3";
        String PREP_CONSULTATION_ENCOUNTERTYPE = "c4a2be28-6673-4c36-b886-ea89b0a42116";
        CalculationResultMap ret = new CalculationResultMap();
        FormService formService = Context.getFormService();
        EncounterService encounterService = Context.getEncounterService();


        Form pocHivFollowup = MetadataUtils.existing(Form.class, HivMetadata._Form.HIV_GREEN_CARD);
        Form rdeHivFollowup = MetadataUtils.existing(Form.class, HivMetadata._Form.MOH_257_VISIT_SUMMARY);
        Form prepInitial = formService.getFormByUuid(PREP_INITIAL_FORM);
        Form prepFollowup = formService.getFormByUuid(PREP_CONSULTATION_FORM);
        Form preMonthlyRefill = formService.getFormByUuid(PREP_MONTHLY_REFILL_FORM);
        EncounterType hivFollowup = MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_CONSULTATION);
        EncounterType etPrepInitial = encounterService.getEncounterTypeByUuid(PREP_INITIAL_ENCOUNTERTYPE);
        EncounterType etPrepFollowup = encounterService.getEncounterTypeByUuid(PREP_CONSULTATION_ENCOUNTERTYPE);
        EncounterType etPrepMonthlyRefill = encounterService.getEncounterTypeByUuid(PREP_MONTHLY_REFILL_ENCOUNTERTYPE);
        AppointmentServiceDefinitionService appointmentServiceDefinitionService = Context.getService(AppointmentServiceDefinitionService.class);		


        for (Integer ptId : cohort) {
            Date returnVisitDate = null;
            ArrayList<Date> prepReturnVisitDates = new ArrayList<Date>();

            Encounter lastFollowUpEncounter = EmrUtils.lastEncounter(Context.getPatientService().getPatient(ptId), hivFollowup,Arrays.asList(pocHivFollowup, rdeHivFollowup));   //last hiv followup form
            Encounter lastPrePFollowUpEncounter = EmrUtils.lastEncounter(Context.getPatientService().getPatient(ptId), etPrepFollowup,prepFollowup);
            Encounter lastPreMonthlyRefillEncounter = EmrUtils.lastEncounter(Context.getPatientService().getPatient(ptId), etPrepMonthlyRefill,preMonthlyRefill);
            Encounter lastPreInitialEncounter = EmrUtils.lastEncounter(Context.getPatientService().getPatient(ptId), etPrepInitial,prepInitial);
            Integer hivConsultationServiceId = appointmentServiceDefinitionService.getAppointmentServiceByUuid(CommonMetadata.HIV_CONSULTATION_SERVICE).getAppointmentServiceId();
            Integer drugRefillServiceId = appointmentServiceDefinitionService.getAppointmentServiceByUuid(CommonMetadata.DRUG_REFILL_SERVICE).getAppointmentServiceId();
            Integer prepFollowupServiceId = appointmentServiceDefinitionService.getAppointmentServiceByUuid(CommonMetadata.PREP_FOLLOWUP_SERVICE).getAppointmentServiceId();
            Integer prepInitialServiceId = appointmentServiceDefinitionService.getAppointmentServiceByUuid(CommonMetadata.PREP_INITIAL_SERVICE).getAppointmentServiceId();
            Integer prepMonthlyRefillServiceId = appointmentServiceDefinitionService.getAppointmentServiceByUuid(CommonMetadata.PREP_MONTHLY_REFILL_SERVICE).getAppointmentServiceId();





            if (lastFollowUpEncounter != null) {
                Date latestTcaDate = EmrUtils.getLatestAppointmentDateForService( ptId, hivConsultationServiceId);
                Date latestRefillDate = EmrUtils.getLatestAppointmentDateForService( ptId, drugRefillServiceId);
                if(latestRefillDate == null) {
                    returnVisitDate = latestTcaDate;

                } else if(latestTcaDate == null) {
                    returnVisitDate = latestRefillDate;

                } else {
                    returnVisitDate = latestRefillDate.before(latestTcaDate) ? latestTcaDate : latestRefillDate;
                }

                ret.put(ptId, new SimpleResult(returnVisitDate, this));

            }

            if (lastPrePFollowUpEncounter != null) {
                Date latestTcaDate = EmrUtils.getLatestAppointmentDateForService( ptId, prepFollowupServiceId);
                prepReturnVisitDates.add(latestTcaDate);
            }

            if (lastPreInitialEncounter != null) {
                Date latestTcaDate = EmrUtils.getLatestAppointmentDateForService( ptId, prepInitialServiceId);
                prepReturnVisitDates.add(latestTcaDate);  
            }

            if (lastPreMonthlyRefillEncounter != null) {
                Date latestTcaDate = EmrUtils.getLatestAppointmentDateForService( ptId, prepMonthlyRefillServiceId);
                prepReturnVisitDates.add(latestTcaDate);
            }

            if (prepReturnVisitDates.size() > 0) {
                if (prepReturnVisitDates.size() == 1) {
                    returnVisitDate = prepReturnVisitDates.get(0);
                    ret.put(ptId, new SimpleResult(returnVisitDate, this));

                } else {
                    returnVisitDate = Collections.max(prepReturnVisitDates);
                    ret.put(ptId, new SimpleResult(returnVisitDate, this));

                }
            }


        }
        return ret;
    }

}

