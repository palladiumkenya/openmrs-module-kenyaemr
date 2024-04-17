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

import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.chore.AbstractChore;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Component("kenyaemr.chore.migratePatientConditionsFromObs")
public class MigratePatientConditionsFromObs extends AbstractChore {
    @Override
    public void perform(PrintWriter printWriter) throws APIException {
        String migrateConditions = "insert into conditions (condition_id, additional_detail, previous_version, condition_coded, condition_non_coded,\n" +
                "                        condition_coded_name, clinical_status, verification_status, onset_date, date_created, voided,\n" +
                "                        date_voided, void_reason, uuid, creator, voided_by, changed_by, patient_id, end_date,\n" +
                "                        date_changed, encounter_id, form_namespace_and_path)  \n" +
                "select null, null, null,cn1.concept_id,null,cn1.concept_id , 'INACTIVE', 'PROVISIONAL',t2.value_datetime, o1.obs_datetime,\n" +
                "        0, null,null,o1.uuid,o1.creator,null,null,o1.person_id,null,null,null,null\n" +
                "    from obs o1\n" +
                "    left join (select * from obs where concept_id = 1284) t1\n" +
                "    on (o1.obs_group_id = t1.obs_group_id)\n" +
                "    LEFT JOIN concept_name cn1\n" +
                "    ON ( cn1.concept_id = t1.value_coded )\n" +
                "    left join (select * from obs where concept_id = 159948) t2\n" +
                "    on (o1.obs_group_id = t2.obs_group_id)\n" +
                "    left join (select * from obs where concept_id = 166937) t3\n" +
                "    on (o1.obs_group_id = t3.obs_group_id)\n" +
                "    LEFT JOIN concept_name cn3\n" +
                "    ON ( cn3.concept_id = t3.value_coded )\n" +
                "    where o1.obs_group_id is not null and o1.concept_id in (1284,159948,166937)\n" +
                "    and t1.concept_id is not null  \n" +
                " group by o1.person_id, o1.obs_group_id;";


        Context.getAdministrationService().executeSQL(migrateConditions, false);


        printWriter.println("Completed migrating conditions");
    }
}