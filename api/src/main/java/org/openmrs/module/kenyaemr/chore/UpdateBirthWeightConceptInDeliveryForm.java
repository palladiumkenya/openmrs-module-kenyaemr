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
 * updates concept for birth weight variable in delivery form
 */
@Component("kenyaemr.chore.UpdateBirthWeightConceptInDeliveryForm")
public class UpdateBirthWeightConceptInDeliveryForm extends AbstractChore {

    /**
     * @see AbstractChore#perform(PrintWriter)
     */

    @Override
    public void perform(PrintWriter out) {
        String updateConceptSql = "UPDATE obs o\n" +
                "  inner join (\n" +
                "               select o.obs_id from obs o\n" +
                "                 inner join encounter e on e.encounter_id = o.encounter_id\n" +
                "                 inner join\n" +
                "                 (\n" +
                "                   select form_id, uuid,name from form where\n" +
                "                     uuid in('496c7cc3-0eea-4e84-a04c-2292949e2f7f')\n" +
                "                 ) f on f.form_id= e.form_id\n" +
                "               where o.concept_id = 5916) d on d.obs_id = o.obs_id\n" +
                "set o.concept_id = 165578, o.value_numeric = if(o.value_numeric < 1000, o.value_numeric * 1000, o.value_numeric);";


        Context.getAdministrationService().executeSQL(updateConceptSql, false);


        out.println("Completed updating birth weight concept in MCH delivery form");

    }




}
