/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.nupi;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.openmrs.CohortMembership;
import org.openmrs.Location;
import org.openmrs.Person;
import org.openmrs.PersonAttribute;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.nupi.FacilityDataDefinition;
import org.openmrs.module.reporting.data.person.EvaluatedPersonData;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.data.person.evaluator.PersonDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

@Handler(supports = FacilityDataDefinition.class)
public class FacilityDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    @Override
    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        Map<Integer, Object> result = new HashMap<Integer, Object>();
        EvaluatedPersonData evaluatedEncounterData = new EvaluatedPersonData(definition, context);
        
        // Retrieve the cohort memberships from the context
        Collection<CohortMembership> cohortMemberships = context.getBaseCohort().getMemberships();

        for (CohortMembership membership : cohortMemberships) {
            Integer patientId = membership.getPatientId();

            // Get the person
            Person person = Context.getPersonService().getPerson(patientId);
            if (person != null) {
                FacilityDataDefinition facilityDataDefinition = (FacilityDataDefinition) definition;
                PersonAttribute attribute = person.getAttribute(facilityDataDefinition.getPersonAttributeType());
                if (attribute != null) {
                    // Get the facility names and append
                    String originalValue = attribute.getValue();
                    String modifiedValue = getFacilityNames(originalValue);
                    result.put(patientId, modifiedValue);
                }
            }
        }

        evaluatedEncounterData.setData(result);
        return evaluatedEncounterData;
    }

    /**
     * Returns the facility names given a list of MFL codes
     * @param facilities the MFL codes separated by commas
     * @return
     */
    private String getFacilityNames(String facilities) {
        String ret = "";
        if (facilities != null && !facilities.isEmpty()) {
            String[] ids = facilities.split(",");
            StringBuilder result = new StringBuilder();

            for (String id : ids) {
                try {
                    // int key = Integer.parseInt(id.trim());
                    id = id.trim();
                    Location facility = Context.getService(KenyaEmrService.class).getLocationByMflCode(id);
                    String fname = facility.getName();

                    if (fname != null) {
                        result.append(id + ": " + fname).append(",\n<br />"); // Add the fname, a comma and a newline
                    }
                } catch(Exception e) {}
            }

            // Remove the trailing comma and newline if there are entries
            if (result.length() > 0) {
                try {
                    result.setLength(result.length() - 8);
                } catch(Exception e){}
            }

            ret = result.toString();
        }
        return(ret);
    }

}

