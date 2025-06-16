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
import java.util.*;

/**
 * Automates the process of checking out a patient from OPD and starting an inpatient visit
 */
public class OutpatientToInpatientCheckinOnAdmissionRequest implements AfterReturningAdvice {

    private Log log = LogFactory.getLog(this.getClass());

    public static final String INPATIENT_ADMISSION_REQUEST_QUESTION_CONCEPT = "160433AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    public static final String INPATIENT_ADMISSION_ANSWER_CONCEPT = "1654AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final Set<Integer> CONDITIONS_CONCEPTS = new HashSet<>(Arrays.asList(114662,115728,149019,117789,117855,2005642,127417, 2008582, 136373,145438, 119692,118631,126513,116004, 128653, 128825, 115968, 113701, 115986, 2003295, 128788, 113744, 116017, 116041, 113689,
            128988, 2003169, 129069, 113763, 128781,2003211, 116049, 116066, 128955, 113712, 2003278, 157622, 126901,1295,2010623,115115,142474, 2004524, 134721, 2004525, 2004526,2004527, 2004325, 2004326, 2004327, 2004329,2004330, 2004331, 2004332,2004335,121610, 2010615, 125971,
            2010616,  2010617, 121960, 2010621, 2010622 ,143320, 2010623, 115001, 2010618, 121918, 2010619, 158974, 2010620, 2010624, 2010625, 2010626, 2010627, 119821, 2010628, 2010629,2010630,2010631, 2010632, 2010633, 2010634, 2010635, 2010636,145438,117321,
            143893, 2004493, 2004494, 2004495, 2004496, 2004497, 2004498, 2004499, 2004500, 2004501,145131,140987, 113087,2010574, 2010580, 2010586,121375,2004715,2010437,2002858,2002837,2002460,2004420,2007861,126513,2005019));

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