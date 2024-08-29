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
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Program;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.appointments.service.AppointmentsService;
import org.openmrs.module.appointments.model.Appointment;
import org.openmrs.module.appointments.model.AppointmentSearchRequest;
import org.openmrs.module.appointments.model.AppointmentStatus;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.CalculationUtils;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyacore.calculation.PatientFlagCalculation;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * A calculation that returns patients who are eligible for fastrack
 * Eligibility criteria include:
 * longer follow-up intervals (depends on clinician ==>longer than 30 days)
 *
 **/
public class EligibleForFastrackPatientsCalculation extends AbstractPatientCalculation {

    protected static final Log log = LogFactory.getLog(EligibleForFastrackPatientsCalculation.class);
    static ConceptService conceptService = Context.getConceptService();
    static PatientService patientService = Context.getPatientService();


    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {

        String hivConsultationService = "885b4ad3-fd4c-4a16-8ed3-08813e6b01fa";
        AppointmentsService appointmentsService = Context.getService(AppointmentsService.class);
        Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
        Set<Integer> alive = Filters.alive(cohort, context);
        Set<Integer> inHivProgram = Filters.inProgram(hivProgram, alive, context);
        Set<Integer> ltfu = CalculationUtils.patientsThatPass(calculate(new LostToFollowUpCalculation(), cohort, context));
        CalculationResultMap ret = new CalculationResultMap();
        Date currentDate = new Date();
        AppointmentSearchRequest appointmentSearchRequest = new AppointmentSearchRequest();
        appointmentSearchRequest.setStatus(AppointmentStatus.Scheduled);
        appointmentSearchRequest.setStartDate(currentDate);


        for(Integer ptId: cohort){
            Integer tcaPlus30days = 0;
            Date appointmentDate = null;
            boolean patientInHivProgram = false;
            boolean eligible = false;
            appointmentSearchRequest.setPatientUuid(patientService.getPatient(ptId).getUuid());
            List<Appointment> appointments = appointmentsService.search(appointmentSearchRequest);
      
            // Check if patient has a future appointment and get the appointment date
            Optional<Appointment> futureAppointment = appointments.stream()
                .filter(appointment -> appointment.getService().getUuid().equals(hivConsultationService))
                .findFirst();

            if (futureAppointment.isPresent()) {
                appointmentDate = futureAppointment.get().getStartDateTime();
                
            }

            //With appointment date more than 30 days
            Encounter lastFollowUpEncounter = EmrUtils.lastEncounter(patientService.getPatient(ptId), Context.getEncounterService().getEncounterTypeByUuid("a0034eee-1940-4e35-847f-97537a35d05e"));   //last greencard followup form
            
            if (lastFollowUpEncounter != null && appointmentDate != null) {
                tcaPlus30days = daysBetween(appointmentDate, lastFollowUpEncounter.getEncounterDatetime());
            }

            if (inHivProgram.contains(ptId) && !ltfu.contains(ptId)) {
                patientInHivProgram = true;
            }

            if(patientInHivProgram && appointmentDate != null && appointmentDate.after(new Date()) && tcaPlus30days >= 30) {
                eligible = true;
            }
            ret.put(ptId, new BooleanResult(eligible, this));
        }
        return ret;
    }

    private Integer daysBetween(Date d1, Date d2) {
        DateTime date1 = new DateTime(d1.getTime());
        DateTime date2 = new DateTime(d2.getTime());
        return Math.abs(Days.daysBetween(date1, date2).getDays());
    }

}