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
 * handles alignment of nutritional status concept for data collected using the deprecated greencard with Triage Concepts
 */
@Component("kenyaemr.chore.NutritionalStatusConceptAlignment")
public class NutritionalStatusConceptAlignment extends AbstractChore {

    /**
     * @see AbstractChore#perform(PrintWriter)
     */
    @Override
    public void perform(PrintWriter out) {
        String updateConceptSql = "UPDATE obs o\n" +
                "    INNER JOIN (\n" +
                "        SELECT e.encounter_id, e.encounter_datetime\n" +
                "        FROM encounter e\n" +
                "                 INNER JOIN encounter_type t\n" +
                "                            ON e.encounter_type = t.encounter_type_id\n" +
                "                                AND t.uuid = 'a0034eee-1940-4e35-847f-97537a35d05e'\n" +
                "                                AND e.voided = 0\n" +
                "    ) e ON o.encounter_id = e.encounter_id AND o.voided = 0\n" +
                "    INNER JOIN person p\n" +
                "    ON o.person_id = p.person_id AND p.voided = 0\n" +
                "SET o.concept_id = CASE\n" +
                "                       WHEN TIMESTAMPDIFF(YEAR, p.birthdate, DATE(e.encounter_datetime)) > 5 THEN 167392\n" +
                "                       WHEN TIMESTAMPDIFF(YEAR, p.birthdate, DATE(e.encounter_datetime)) <= 5 THEN 163515\n" +
                "    END\n" +
                "WHERE o.concept_id = 163300\n" +
                "  AND o.value_coded IN (1115, 114413, 163303, 163302);";
        Context.getAdministrationService().executeSQL(updateConceptSql, false);
        out.println("Completed Updating deprecated concept for nutritional status");
    }

}
