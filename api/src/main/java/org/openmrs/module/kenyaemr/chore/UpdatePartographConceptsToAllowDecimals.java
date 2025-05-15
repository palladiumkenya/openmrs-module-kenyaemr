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

import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.chore.AbstractChore;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

/**
 * update Partograph form concepts to allow decimals
 * concepts  (167149,162261,1789,1440)
 *Example is 0.5
 * */
@Component("kenyaemr.chore.UpdatePartographConceptsToAllowDecimals")
public class UpdatePartographConceptsToAllowDecimals extends AbstractChore {

    /**
     * @see AbstractChore#perform(PrintWriter)
     */

    @Override
    public void perform(PrintWriter out) {
		
        String rapturedMembranesConcept = "UPDATE concept_numeric SET allow_decimal = 1  WHERE concept_id = 167149;";
        String cervicalDilationConcept = "UPDATE concept_numeric SET allow_decimal = 1  WHERE concept_id = 162261;";
        String pregnancyDurationConcept = "UPDATE concept_numeric SET allow_decimal = 1  WHERE concept_id = 1789;";
		String fetalHeartRateConcept = "UPDATE concept_numeric SET allow_decimal = 1  WHERE concept_id = 1440;";

        Context.getAdministrationService().executeSQL(rapturedMembranesConcept, false);
        Context.getAdministrationService().executeSQL(cervicalDilationConcept, false);
        Context.getAdministrationService().executeSQL(pregnancyDurationConcept, false);
        Context.getAdministrationService().executeSQL(fetalHeartRateConcept, false);
     
        out.println("Completed updating Partograph form concepts");

    }




}
