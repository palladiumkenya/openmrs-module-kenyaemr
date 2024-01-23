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
import org.openmrs.Visit;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * Automates the process of checking out a patient from OPD and starting an inpatient visit
 */
public class OutpatientToInpatientCheckinOnAdmissionRequest implements AfterReturningAdvice {

    private Log log = LogFactory.getLog(this.getClass());

    public static final String INPATIENT_ADMISSION_REQUEST_QUESTION_CONCEPT = "160433AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    public static final String INPATIENT_ADMISSION_ANSWER_CONCEPT = "1654AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

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
        }

    }

}