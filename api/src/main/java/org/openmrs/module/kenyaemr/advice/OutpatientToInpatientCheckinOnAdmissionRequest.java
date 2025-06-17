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
import org.openmrs.User;
import org.openmrs.Visit;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.springframework.aop.AfterReturningAdvice;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Automates the process of checking out a patient from OPD and starting an inpatient visit
 */
public class OutpatientToInpatientCheckinOnAdmissionRequest implements AfterReturningAdvice {

    private Log log = LogFactory.getLog(this.getClass());
    public static final String INPATIENT_ADMISSION_REQUEST_QUESTION_CONCEPT = "160433AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    public static final String INPATIENT_ADMISSION_ANSWER_CONCEPT = "1654AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

    String conditionsConfig = Context.getAdministrationService().getGlobalProperty("kenyaemr.conditions.config");
    Set<Integer> CONDITIONS_CONCEPTS = new HashSet<>();
    {
        if (conditionsConfig != null && !conditionsConfig.trim().isEmpty()) {
            for (String id : conditionsConfig.split("\\s*,\\s*")) {
                try {
                    CONDITIONS_CONCEPTS.add(Integer.parseInt(id));
                } catch (NumberFormatException ignored) {}
            }
        }
    }
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

        if (method.getName().equals("saveEncounter")) {
            Encounter enc = (Encounter) args[0];
            VisitService visitService = Context.getVisitService();
            if (enc != null && enc.getVisit() != null && enc.getVisit().getVisitType().getUuid().equals(CommonMetadata._VisitType.OUTPATIENT) && enc.getForm() != null && CommonMetadata._Form.CLINICAL_ENCOUNTER.equalsIgnoreCase(enc.getForm().getUuid())) {
                for (Obs o : enc.getAllObs()) {
                    if (o.getConcept().getUuid().equals(INPATIENT_ADMISSION_REQUEST_QUESTION_CONCEPT) && o.getValueCoded().getUuid().equals(INPATIENT_ADMISSION_ANSWER_CONCEPT)) {
                        // end the OPD visit
                        Visit opdVisit = enc.getVisit();
                        opdVisit.setStopDatetime(new Date());
                        visitService.saveVisit(opdVisit);

                        Visit visit = new Visit();
                        visit.setStartDatetime(new Date());
                        visit.setLocation(enc.getLocation());
                        visit.setPatient(enc.getPatient());
                        visit.setVisitType(visitService.getVisitTypeByUuid(CommonMetadata._VisitType.INPATIENT));
                        Context.getVisitService().saveVisit(visit);
                        System.out.println("Started a new inpatient visit......");
                        break;
                    }
                }
            }
            // Check if the encounter has a clinical diagnosis

            if (enc != null && enc.getForm() != null && (CommonMetadata._Form.CLINICAL_ENCOUNTER.equalsIgnoreCase(enc.getForm().getUuid()) || HivMetadata._Form.HIV_GREEN_CARD.equalsIgnoreCase(enc.getForm().getUuid()))) {
                Integer encounterId = enc.getEncounterId();
                Integer patientId = enc.getPatient().getId();
                User creator = enc.getCreator();
                Date now = new Date();

                // Query to fetch diagnoses from encounter_diagnosis table
                String diagnosisQuery = String.format(
                        "SELECT diagnosis_coded FROM encounter_diagnosis " +
                                "WHERE encounter_id = %d AND voided = 0", encounterId);
                try {
                    List<List<Object>> diagnosisResults = Context.getAdministrationService()
                            .executeSQL(diagnosisQuery, true);
                    for (List<Object> row : diagnosisResults) {
                        if (row != null && !row.isEmpty()) {
                            Integer diagnosisCoded = (Integer) row.get(0);
                            if (CONDITIONS_CONCEPTS.contains(diagnosisCoded)) {
                                // Query to check if the condition already exists in the conditions table
                                String checkConditionQuery = String.format(
                                        "SELECT condition_id FROM conditions " +
                                                "WHERE patient_id = %d AND condition_coded = %d AND voided = 0",
                                        patientId, diagnosisCoded);
                                List<List<Object>> conditionResults = Context.getAdministrationService()
                                        .executeSQL(checkConditionQuery, true);
                                if (conditionResults.isEmpty()) {
                                    // Insert new condition into conditions table
                                    String insertConditionQuery = String.format(
                                            "INSERT INTO conditions (" +
                                                    "patient_id, encounter_id, condition_coded, clinical_status, " +
                                                    "onset_date, date_created, creator, uuid, voided) " +
                                                    "VALUES (%d, %d, %d, 'ACTIVE', '%s', '%s', %d, '%s', 0)",
                                            patientId,
                                            encounterId,
                                            diagnosisCoded,
                                            new java.sql.Timestamp(now.getTime()).toString(),
                                            new java.sql.Timestamp(now.getTime()).toString(),
                                            creator.getUserId(),
                                            UUID.randomUUID().toString()
                                    );
                                    Context.getAdministrationService().executeSQL(insertConditionQuery, false);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("Error auto-populating conditions for encounter " + encounterId, e);
                }
            }
        }

    }

}