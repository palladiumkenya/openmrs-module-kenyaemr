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

@Component("kenyaemr.chore.migratePatientAllergiesFromObs")
public class MigratePatientAllergiesFromObs extends AbstractChore {
    @Override
    public void perform(PrintWriter printWriter) throws APIException {
        String migrateAllergies = "insert into allergy (allergy_id, patient_id, severity_concept_id, coded_allergen, non_coded_allergen, allergen_type,\n" +
                "                     comments, creator, date_created, changed_by, date_changed, voided, voided_by, date_voided,\n" +
                "                     void_reason, uuid, form_namespace_and_path, encounter_id)  \n" +
                "select null,o1.person_id,cn4.concept_id, case when cn1.concept_id is not null then cn1.concept_id else cn2.concept_id end,\n" +
                "    case when cn1.concept_id is not null then cn1.name else cn2.name end,\n" +
                "    case when cn2.concept_id is not null then 'Medication' else 'other' end,null,o1.creator,o1.date_created,null,null,o1.voided,\n" +
                "    o1.voided_by,o1.date_voided,reaction.obs_id,o1.uuid,null,null\n" +
                "    from obs o1\n" +
                "    left join (select * from obs where concept_id = 160643) allergen1\n" +
                "    on (o1.obs_group_id = allergen1.obs_group_id)\n" +
                "    LEFT JOIN concept_name cn1\n" +
                "    ON ( cn1.concept_id = allergen1.value_coded )\n" +
                "    left join (select * from obs where concept_id = 1193) allergen2\n" +
                "    on (o1.obs_group_id = allergen2.obs_group_id)\n" +
                "    LEFT JOIN concept_name cn2\n" +
                "    ON ( cn2.concept_id = allergen2.value_coded )\n" +
                "    left join (select * from obs where concept_id = 159935) reaction\n" +
                "    on (o1.obs_group_id = reaction.obs_group_id)\n" +
                "    LEFT JOIN concept_name cn3\n" +
                "    ON ( cn3.concept_id = reaction.value_coded )\n" +
                "    left join (select * from obs where concept_id = 162760) severity\n" +
                "    on (o1.obs_group_id = severity.obs_group_id)\n" +
                "    LEFT JOIN concept_name cn4\n" +
                "    ON ( cn4.concept_id = severity.value_coded )\n" +
                "    where (allergen1.value_coded != 1066 or allergen2.value_coded != 1066)\n" +
                "    AND o1.obs_group_id IS NOT NULL AND o1.concept_id in (160643,159935,162760,1193)  \n" +
                " group by o1.person_id, o1.obs_group_id;";

        String migrateAllergyReactions = "insert into allergy_reaction (allergy_reaction_id, allergy_id, reaction_concept_id, reaction_non_coded, uuid) \n" +
                "select null,allergy_id, cn3.concept_id, cn3.name,reaction.uuid\n" +
                "    from obs o1\n" +
                "    left join (select * from obs where concept_id = 159935) reaction\n" +
                "    on (o1.obs_group_id = reaction.obs_group_id)\n" +
                "    LEFT JOIN concept_name cn3\n" +
                "    ON ( cn3.concept_id = reaction.value_coded )\n" +
                "    inner join allergy al on al.void_reason = reaction.obs_id\n" +
                "    where o1.obs_group_id IS NOT NULL AND o1.concept_id in (159935) \n" +
                " group by o1.person_id, o1.obs_group_id;";

        String removeJoinColumn = "update allergy set void_reason = null;";

        Context.getAdministrationService().executeSQL(migrateAllergies, false);
        Context.getAdministrationService().executeSQL(migrateAllergyReactions, false);
        Context.getAdministrationService().executeSQL(removeJoinColumn, false);


        printWriter.println("Completed migrating patients allergies");
    }
}
