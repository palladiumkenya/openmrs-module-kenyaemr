/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * handles alignment of Differentiated Care and ART Distribution group concepts
 * Concept ID 164947 was wrongly used to collect ART distribution group data. Correcting this with Concept ID 166448
 * Concept IDs 164946 and 165287 wrongly used to collect Differentiated Care models data. Corrected with 164947, which is the same as what was used in 2.x
 */
@Component("kenyaemr.chore.DifferentiatedCareConceptAlignment")
public class DifferentiatedCareConceptAlignment extends AbstractChore {

    private static final String ART_DISTRIBUTION_REMARK = "Replaced wrongly used concept for ART distribution group";
    private static final String DIFF_CARE_MODEL_REMARK = "Replaced wrongly used concept for differentiated care model";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * @see AbstractChore#perform(PrintWriter)
     */

    @Override
    public void perform(PrintWriter out) {
        EncounterService encounterService = Context.getEncounterService();
        ConceptService conceptService = Context.getConceptService();
        Concept artDistReplacementConcept = conceptService.getConcept(166448);
        Concept diffCareModelReplacementConcept = conceptService.getConcept(164947);
        List<Concept> artDistributionAnswerConcepts = getConcepts(conceptService, 1537, 163488);
        List<Concept> artDiffCareModelAnswerConcepts = getConcepts(conceptService, 164942, 164943, 166443, 166444, 1555, 164945, 1000478, 164944, 166583, 164946);
        List<Concept> diffCareModelConcepts = getConcepts(conceptService, 164946, 165287);

        Date dateFrom = parseDate("2024-07-01");

        EncounterSearchCriteria encounterSearchCriteria = new EncounterSearchCriteria(null, null, dateFrom, null, null, Collections.singleton(MetadataUtils.existing(Form.class, HivMetadata._Form.HIV_GREEN_CARD)), Collections.singleton(MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_CONSULTATION)), null, null, null, false);
        List<Encounter> hivEncounters = encounterService.getEncounters(encounterSearchCriteria);

        processObservations(out, hivEncounters, Collections.singletonList(diffCareModelReplacementConcept), artDistributionAnswerConcepts, artDistReplacementConcept, ART_DISTRIBUTION_REMARK);
        processObservations(out, hivEncounters, diffCareModelConcepts, artDiffCareModelAnswerConcepts, diffCareModelReplacementConcept, DIFF_CARE_MODEL_REMARK);

        out.println("Finished Updating concepts for DCM and ART distribution group");
    }

    private Date parseDate(String dateStr) {
        try {
            return SIMPLE_DATE_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            logWarning("Failed to parse date: " + dateStr + ". Using current date as fallback.");
            return new Date();
        }
    }

    private List<Concept> getConcepts(ConceptService conceptService, Integer... ids) {
        List<Concept> concepts = new ArrayList<>();
        for (Integer id : ids) {
            Concept concept = conceptService.getConcept(id);
            if (concept != null) {
                concepts.add(concept);
            }
        }
        return concepts;
    }

    private void processObservations(PrintWriter out, List<Encounter> encounters, List<Concept> questionConcepts, List<Concept> answerConcepts, Concept replacementConcept, String remark) {
        List<Obs> observations = Context.getObsService().getObservations(null, encounters, questionConcepts, answerConcepts, null, null, null, null, null, null, null, false, null);
        for (Obs oldObs : observations) {
            Obs newObs = createNewObs(oldObs, replacementConcept);

            try {
                Context.getObsService().saveObs(newObs, remark);
                voidOldObs(out, oldObs, newObs);
            } catch (Exception saveException) {
                logException(out, "Failed to save new Obs for patient " + oldObs.getPerson().getId(), saveException);
            }
        }
    }

    private Obs createNewObs(Obs oldObs, Concept replacementConcept) {
        Obs newObs = new Obs();
        newObs.setPerson(oldObs.getPerson());
        newObs.setEncounter(oldObs.getEncounter());
        newObs.setObsDatetime(oldObs.getObsDatetime());
        newObs.setConcept(replacementConcept);
        newObs.setValueCoded(oldObs.getValueCoded());
        newObs.setLocation(oldObs.getLocation());
        newObs.setCreator(oldObs.getCreator());
        newObs.setDateCreated(new Date());
        newObs.setObsGroup(oldObs.getObsGroup());
        return newObs;
    }

    private void voidOldObs(PrintWriter out, Obs oldObs, Obs newObs) {
        try {
            Context.getObsService().voidObs(oldObs, "Replaced by new Obs");
        } catch (Exception voidException) {
            Context.getObsService().voidObs(newObs, "Rollback due to failure to void old Obs");
            logException(out, "Failed to void old Obs for patient " + oldObs.getPerson().getId() + ". Rolled back new Obs.", voidException);
        }
    }

    private void logException(PrintWriter out, String message, Exception exception) {
        out.println(message + ": " + exception.getMessage());
        exception.printStackTrace(out);
    }

    private void logWarning(String message) {
        System.out.println("[WARNING] " + message);
    }

}
