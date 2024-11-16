/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.chore;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.chore.AbstractChore;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.parameter.EncounterSearchCriteria;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * handles alignment of nutritional status concept for data collected using the deprecated greencard with Triage Concepts
 */
@Component("kenyaemr.chore.NutritionalStatusConceptAlignment")
public class NutritionalStatusConceptAlignment extends AbstractChore {

    /**
     * @see AbstractChore#perform(PrintWriter)
     */

    @Override
    public void perform(PrintWriter out) {
        EncounterService encounterService = Context.getEncounterService();
        ConceptService conceptService = Context.getConceptService();
        List<Integer> conceptIds = Arrays.asList(1115, 114413, 163303, 163302);

        List<Concept> answerConcepts = new ArrayList<>();
        for (Integer id : conceptIds) {
            Concept concept = conceptService.getConcept(id);
            if (concept != null) {
                answerConcepts.add(concept);
            }
        }
        EncounterSearchCriteria encounterSearchCriteria = new EncounterSearchCriteria(null, null, null, null, null, Collections.singleton(MetadataUtils.existing(Form.class, HivMetadata._Form.HIV_GREEN_CARD)), Collections.singleton(MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_CONSULTATION)), null, null, null, false);
        List<Encounter> hivEncounters = encounterService.getEncounters(encounterSearchCriteria);
        List<Obs> obs = Context.getObsService().getObservations(null, hivEncounters, Collections.singletonList(conceptService.getConcept(163300)), answerConcepts, null, null, null, null, null, null, null, false);

        for (Obs oldObs : obs) {
            Obs newObs = new Obs();
            Date obsDate = oldObs.getObsDatetime();

            newObs.setPerson(oldObs.getPerson());
            newObs.setEncounter(oldObs.getEncounter());
            newObs.setObsDatetime(obsDate);
            if (oldObs.getPerson().getAge(obsDate) > 5) {
                newObs.setConcept(conceptService.getConcept(167392));
            } else {
                newObs.setConcept(conceptService.getConcept(163515));
            }
            newObs.setValueCoded(oldObs.getValueCoded());
            newObs.setLocation(oldObs.getLocation());
            newObs.setCreator(oldObs.getCreator());
            newObs.setDateCreated(oldObs.getDateCreated());
            newObs.setObsGroup(oldObs.getObsGroup());

            try {
                Context.getObsService().saveObs(newObs, "Replaced deprecated nutritional status concept");

                try {
                    Context.getObsService().voidObs(oldObs, "Replaced by new obs with new nutritional status concept");

                } catch (Exception voidException) {
                    Context.getObsService().voidObs(newObs, "Rollback due to failure to void old observation");
                    out.println("Failed to void old observation for patient " + oldObs.getPerson().getId() + ". Rolled back new observation.");
                    voidException.printStackTrace(out);
                }

            } catch (Exception saveException) {
                out.println("Failed to save new observation for patient " + oldObs.getPerson().getId() + ": " + saveException.getMessage());
                saveException.printStackTrace(out);
            }
        }
        out.println("Completed Updating deprecated concept for nutritional status");
    }

}
