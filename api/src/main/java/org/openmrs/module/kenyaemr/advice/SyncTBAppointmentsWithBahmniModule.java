/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.advice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Person;
import org.openmrs.api.ConceptService;
import org.openmrs.api.ObsService;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointments.model.Appointment;
import org.openmrs.module.appointments.model.AppointmentKind;
import org.openmrs.module.appointments.model.AppointmentProvider;
import org.openmrs.module.appointments.model.AppointmentServiceDefinition;
import org.openmrs.module.appointments.service.AppointmentsService;
import org.openmrs.module.appointments.util.DateUtil;
import org.openmrs.module.kenyaemr.metadata.TbMetadata;
import org.springframework.aop.AfterReturningAdvice;
import org.openmrs.module.appointments.model.AppointmentServiceType;
import org.openmrs.module.appointments.service.AppointmentServiceDefinitionService;
import org.openmrs.module.kenyaemr.util.EmrUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Synchronizes appointments documented in HTML forms with Bahmni appointments module
 * Invoked after saving HFE forms
 */
public class SyncTBAppointmentsWithBahmniModule implements AfterReturningAdvice {

    private Log log = LogFactory.getLog(this.getClass());

    public static final String NEXT_APPOINTMENT_DATE_CONCEPT_UUID = "5096AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

    public static final String TB_SERVICE = "e4737031-7e3b-4c63-a929-588613b2c832";

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        AppointmentsService appointmentsService = Context.getService(AppointmentsService.class);
        AppointmentServiceDefinitionService appointmentServiceDefinitionService  = Context.getService(AppointmentServiceDefinitionService.class);
        PersonService personService = Context.getPersonService();
        ConceptService conceptService = Context.getConceptService();
        ObsService obsService = Context.getObsService();

        if (method.getName().equals("saveEncounter")) { // handles both create and edit
            Encounter enc = (Encounter) args[0];

            Person parent = personService.getPerson(enc.getPatient().getPersonId());
            List<Obs> obs = obsService.getObservations(
                    Arrays.asList(personService.getPerson(enc.getPatient().getPersonId())),
                    Arrays.asList(enc),
                    Arrays.asList(
                            conceptService.getConceptByUuid(NEXT_APPOINTMENT_DATE_CONCEPT_UUID)
                    ),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    false
            );

            for (Obs o : obs) { // Loop through the obs and compose Appointment object for Bahmni
                AppointmentServiceDefinition appointmentServiceDefinition = new AppointmentServiceDefinition();
                appointmentServiceDefinition.setAppointmentServiceId(appointmentServiceDefinitionService.getAppointmentServiceByUuid(TB_SERVICE).getId());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Appointment tbFollowUpAppointment = appointmentsService.getAppointmentByUuid(enc.getUuid());

                if(enc.getVoided() == true && tbFollowUpAppointment != null && enc.getForm() != null) {
                    //voiding appointment
                    tbFollowUpAppointment.setVoided(true);
                    tbFollowUpAppointment.setDateVoided(new Date());
                    tbFollowUpAppointment.setVoidedBy(Context.getAuthenticatedUser());
                    Appointment app = appointmentsService.validateAndSave(tbFollowUpAppointment);
                } else if(tbFollowUpAppointment != null && enc.getForm() != null && (enc.getForm().getUuid().equals(TbMetadata._Form.TB_FOLLOW_UP))) {
                    //editing appointment
                    Date tbApptStartDateTime = DateUtil.convertToDate(dateFormat.format(o.getValueDatetime()).concat("T07:00:00.0Z"), DateUtil.DateFormatType.UTC);
                    Date tbApptEndDateTime = DateUtil.convertToDate(dateFormat.format(o.getValueDatetime()).concat("T20:00:00.0Z"), DateUtil.DateFormatType.UTC);
                    tbFollowUpAppointment.setStartDateTime(tbApptStartDateTime);
                    tbFollowUpAppointment.setEndDateTime(tbApptEndDateTime);
                    if(enc.getVoided() == true) {
                        tbFollowUpAppointment.setVoided(true);
                        tbFollowUpAppointment.setDateVoided(new Date());
                        tbFollowUpAppointment.setVoidedBy(Context.getAuthenticatedUser());
                    }
                    Appointment app = appointmentsService.validateAndSave(tbFollowUpAppointment);
                } else {
                    //creating TB followup appointment
                    Appointment tbAppointment = new Appointment();
                    Date tbAppointmentStartTime = DateUtil.convertToDate(dateFormat.format(o.getValueDatetime()).concat("T07:00:00.0Z"), DateUtil.DateFormatType.UTC);
                    Date tbAppointmentEndTime = DateUtil.convertToDate(dateFormat.format(o.getValueDatetime()).concat("T20:00:00.0Z"), DateUtil.DateFormatType.UTC);
                    tbAppointment.setUuid(enc.getUuid());
                    tbAppointment.setPatient(enc.getPatient());
                    tbAppointment.setService(appointmentServiceDefinition);
                    tbAppointment.setStartDateTime(tbAppointmentStartTime);
                    tbAppointment.setEndDateTime(tbAppointmentEndTime);
                    tbAppointment.setLocation(enc.getLocation());
                    tbAppointment.setProvider(EmrUtils.getProvider(Context.getAuthenticatedUser()));
                    tbAppointment.setAppointmentKind(AppointmentKind.Scheduled);
                    Appointment app = appointmentsService.validateAndSave(tbAppointment);
                }
            }
        }
    }

}