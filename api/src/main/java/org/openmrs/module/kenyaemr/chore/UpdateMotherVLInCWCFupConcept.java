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
 * update Mother VL concept from 856 to 163545
 */
@Component("kenyaemr.chore.UpdateMotherVLInCWCFupConcept")
public class UpdateMotherVLInCWCFupConcept extends AbstractChore {

    /**
     * @see AbstractChore#perform(PrintWriter)
     */

    @Override
    public void perform(PrintWriter out) {
        String updateConceptSql = "UPDATE obs o\n" +
                "    JOIN encounter e ON o.encounter_id = e.encounter_id\n" +
                "    JOIN encounter_type et ON e.encounter_type = et.encounter_type_id\n" +
                "SET o.concept_id = 163545\n" +
                "WHERE o.concept_id = 856\n" +
                "  AND et.uuid = 'bcc6da85-72f2-4291-b206-789b8186a021';";
        Context.getAdministrationService().executeSQL(updateConceptSql, false);
        out.println("Completed updating Mother VL concept in CWC followup");

    }

}

