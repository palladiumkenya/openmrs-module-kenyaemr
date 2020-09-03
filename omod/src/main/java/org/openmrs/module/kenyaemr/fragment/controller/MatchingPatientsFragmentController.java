/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.fragment.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.registrationcore.api.RegistrationCoreService;
import org.openmrs.module.registrationcore.api.mpi.common.MpiPatient;
import org.openmrs.module.registrationcore.api.search.PatientAndMatchQuality;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.BindParams;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

public class MatchingPatientsFragmentController {
    public static final int MAX_RESULTS = 10;
    public static final double CUTOFF = 2.0;

    public static final String[] PATIENT_PROPERTIES = new String[]{"uuid", "givenName", "familyName",
            "gender", "birthdate", "personAddress"};

    public static final String[] MPI_PATIENT_PROPERTIES = new String[]{"uuid", "givenName", "familyName",
            "gender", "birthdate", "personAddress", "mpiPatient"};

    public List<SimpleObject> getSimilarPatients(@RequestParam("appId") AppDescriptor app,
                                                 @SpringBean("registrationCoreService") RegistrationCoreService service,
                                                 @ModelAttribute("patient") @BindParams Patient patient,
                                                 @ModelAttribute("personName") @BindParams PersonName name,
                                                 @ModelAttribute("personAddress") @BindParams PersonAddress address,
                                                 @RequestParam(value="birthdateYears", required = false) Integer birthdateYears,
                                                 @RequestParam(value="birthdateMonths", required = false) Integer birthdateMonths,
                                                 HttpServletRequest request, UiUtils ui) throws Exception {
        addToPatient(patient, app, name, address, request);

        Map<String, Object> otherDataPoints = createDataPoints(birthdateYears, birthdateMonths);

        List<PatientAndMatchQuality> matches = service.findFastSimilarPatients(patient, otherDataPoints, CUTOFF, MAX_RESULTS);
        return getSimpleObjects(app, ui, matches);
    }

    public List<SimpleObject> getExactPatients(@RequestParam("appId") AppDescriptor app,
                                               @SpringBean("registrationCoreService") RegistrationCoreService service,
                                               @ModelAttribute("patient") @BindParams Patient patient,
                                               @ModelAttribute("personName") @BindParams PersonName name,
                                               @ModelAttribute("personAddress") @BindParams PersonAddress address,
                                               @RequestParam(value="birthdateYears", required = false) Integer birthdateYears,
                                               @RequestParam(value="birthdateMonths", required = false) Integer birthdateMonths,
                                               HttpServletRequest request, UiUtils ui) throws Exception {
        addToPatient(patient, app, name, address, request);

        Map<String, Object> otherDataPoints = createDataPoints(birthdateYears, birthdateMonths);

        List<PatientAndMatchQuality> matches = service.findPreciseSimilarPatients(patient, otherDataPoints, CUTOFF, determineMaxResults(app));
        return getSimpleObjects(app, ui, matches);
    }


    private void addToPatient(Patient patient, AppDescriptor app, PersonName name, PersonAddress address, HttpServletRequest request) throws IOException {

        patient.addName(name);
        patient.addAddress(address);

    }

    private Map<String, Object> createDataPoints(Integer birthdateYears, Integer birthdateMonths) {
        Map<String, Object> otherDataPoints = new HashMap<String, Object>();
        otherDataPoints.put("birthdateYears", birthdateYears);
        otherDataPoints.put("birthdateMonths", birthdateMonths);
        return otherDataPoints;
    }

    private List<SimpleObject> getSimpleObjects(AppDescriptor app, UiUtils ui, List<PatientAndMatchQuality> matches) {
        List<SimpleObject> result = new ArrayList<SimpleObject>();

        for (PatientAndMatchQuality matchedPatient : matches) {
            Patient patientEntry = matchedPatient.getPatient();
            SimpleObject patientSimple;
            if (patientEntry instanceof MpiPatient) {
                patientSimple = SimpleObject.fromObject(patientEntry, ui, MPI_PATIENT_PROPERTIES);
            } else {
                patientSimple = SimpleObject.fromObject(patientEntry, ui, PATIENT_PROPERTIES);
            }
            addIdentifiersToPatientSimple(patientEntry, patientSimple);
            result.add(patientSimple);
        }
        return result;
    }

    private void addIdentifiersToPatientSimple(Patient patientEntry, SimpleObject patientSimple) {
        LinkedList<SimpleObject> identifiersList = new LinkedList<SimpleObject>();
        for (PatientIdentifier identifier : patientEntry.getIdentifiers()) {
            SimpleObject identifierEntry = new SimpleObject();
            identifierEntry.put("name", identifier.getIdentifierType().getName());
            identifierEntry.put("value", identifier.getIdentifier());
            if (identifier.isPreferred()) {
                identifiersList.addFirst(identifierEntry);
            } else {
                identifiersList.add(identifierEntry);
            }
        }
        patientSimple.put("identifiers", identifiersList);
    }

    private String [] determinePropertiesToInclude(AppDescriptor app, String[] defaultProperties) {
        List<String> propertiesToIncludeList = null;
        String [] propertiesToIncludeArray;

        if (app.getConfig().get("matchingPatientsPropertiesToDisplay") != null) {
            propertiesToIncludeList = new ArrayList<String>();
            if (Arrays.asList(defaultProperties).contains("mpiPatient")) {
                propertiesToIncludeList.add("mpiPatient");
            }
            addRequiredPropertiesToInclude(propertiesToIncludeList);  // these are properties hardcoded into the default template, so must be included
        }

        if (propertiesToIncludeList != null) {
            propertiesToIncludeArray = propertiesToIncludeList.toArray(new String[propertiesToIncludeList.size()]);
        }
        else {
            propertiesToIncludeArray =  defaultProperties;
        }

            return propertiesToIncludeArray;
    }

    private void addRequiredPropertiesToInclude(List<String> propertiesToInclude) {
        addIfMissing("uuid", propertiesToInclude);
        addIfMissing("patientId", propertiesToInclude);
        addIfMissing("givenName", propertiesToInclude);
        addIfMissing("familyName", propertiesToInclude);
        addIfMissing("gender", propertiesToInclude);
        addIfMissing("personAddress", propertiesToInclude);
        addIfMissing("birthdate", propertiesToInclude);
    }

    private void addIfMissing(String property, List<String> propertiesToInclude) {
        if (!propertiesToInclude.contains(property)) {
            propertiesToInclude.add(property);
        }
    }

    private Integer determineMaxResults(AppDescriptor app) {
        if (app.getConfig().get("maxPatientSearchResults") != null) {
            return app.getConfig().get("maxPatientSearchResults").getIntValue();
        }
        else {
            return MAX_RESULTS;
        }
    }	
	
}
