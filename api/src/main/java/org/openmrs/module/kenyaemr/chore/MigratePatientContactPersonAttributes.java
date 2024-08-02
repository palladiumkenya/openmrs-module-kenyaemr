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
 * Migrate Patient contacts from kenyaemr_hiv_testing_patient_contact
 */
@Component("kenyaemr.chore.MigratePatientContactPersonAttributes")
public class MigratePatientContactPersonAttributes extends AbstractChore {

    /**
     * @see AbstractChore#perform(PrintWriter)
     */

    @Override
    public void perform(PrintWriter out) {

        String insertPersonAttributes = "insert into person_attribute (person_id, value, person_attribute_type_id, creator, date_created, changed_by,\n" +
                "                              date_changed, voided, voided_by, date_voided, void_reason, uuid)\n" +
                "    (select c.patient_id,\n" +
                "            case c.baseline_hiv_status when 'Negative' then 664 when 'Positive' then 703 else 1067 end,\n" +
                "            (select person_attribute_type_id from person_attribute_type t where uuid ='3ca03c84-632d-4e53-95ad-91f1bd9d96d6'),\n" +
                "            c.changed_by,\n" +
                "            c.date_created,\n" +
                "            null,\n" +
                "            c.date_changed,\n" +
                "            c.voided,\n" +
                "            c.voided_by,\n" +
                "            c.date_voided,\n" +
                "            c.voided_reason,\n" +
                "            uuid()\n" +
                "     from kenyaemr_hiv_testing_patient_contact c\n" +
                "     where c.patient_id is not null)\n" +
                "union all\n" +
                "(select c.patient_id,\n" +
                "        ifnull(c.living_with_patient,1067),\n" +
                "        (select person_attribute_type_id from person_attribute_type t where uuid = '35a08d84-9f80-4991-92b4-c4ae5903536e'),\n" +
                "        c.changed_by,\n" +
                "        c.date_created,\n" +
                "        null,\n" +
                "        c.date_changed,\n" +
                "        c.voided,\n" +
                "        c.voided_by,\n" +
                "        c.date_voided,\n" +
                "        c.voided_reason,\n" +
                "        uuid()\n" +
                " from kenyaemr_hiv_testing_patient_contact c\n" +
                " where c.patient_id is not null)\n" +
                "union all\n" +
                "(select c.patient_id,\n" +
                "        ifnull(c.pns_approach,1067),\n" +
                "        (select person_attribute_type_id from person_attribute_type t where uuid ='59d1b886-90c8-4f7f-9212-08b20a9ee8cf'),\n" +
                "        c.changed_by,\n" +
                "        c.date_created,\n" +
                "        null,\n" +
                "        c.date_changed,\n" +
                "        c.voided,\n" +
                "        c.voided_by,\n" +
                "        c.date_voided,\n" +
                "        c.voided_reason,\n" +
                "        uuid()\n" +
                " from kenyaemr_hiv_testing_patient_contact c\n" +
                " where c.patient_id is not null)\n" +
                "union all\n" +
                "(select c.patient_id,\n" +
                "        1065,\n" +
                "        (select person_attribute_type_id from person_attribute_type t where uuid = '7c94bd35-fba7-4ef7-96f5-29c89a318fcf'),\n" +
                "        c.changed_by,\n" +
                "        c.date_created,\n" +
                "        null,\n" +
                "        c.date_changed,\n" +
                "        c.voided,\n" +
                "        c.voided_by,\n" +
                "        c.date_voided,\n" +
                "        c.voided_reason,\n" +
                "        uuid()\n" +
                " from kenyaemr_hiv_testing_patient_contact c\n" +
                " where c.patient_id is not null);";

        Context.getAdministrationService().executeSQL(insertPersonAttributes, false);
        out.println("Completed inserting person attributes for the contact");

        String createPatientContactEncounters = "insert into encounter (encounter_type, patient_id, location_id, form_id, encounter_datetime, creator, date_created,\n" +
                "                       voided, voided_by, date_voided, void_reason, changed_by, date_changed, visit_id, uuid)\n" +
                "    (select (select encounter_type_id from encounter_type where uuid='de1f9d67-b73e-4e1b-90d0-036166fc6995'),\n" +
                "            c.patient_id,\n" +
                "            null,\n" +
                "            44,\n" +
                "            c.date_created,\n" +
                "            c.changed_by,\n" +
                "            c.date_created,\n" +
                "            c.voided,\n" +
                "            c.voided_by,\n" +
                "            c.date_voided,\n" +
                "            c.voided_reason,\n" +
                "            c.changed_by,\n" +
                "            c.date_changed,\n" +
                "            null,\n" +
                "            uuid()\n" +
                "     from kenyaemr_hiv_testing_patient_contact c\n" +
                "     where c.patient_id is not null);";

        Context.getAdministrationService().executeSQL(createPatientContactEncounters, false);
        out.println("Completed creating encounters for migrated patient contacts");

        String insertRelationship = "insert into relationship\n" +
                "(person_a, relationship, person_b, start_date, end_date, creator, date_created, date_changed, changed_by, voided,\n" +
                " voided_by, date_voided, void_reason, uuid) (select c.patient_related_to,\n" +
                "                                                    (case c.relationship_type\n" +
                "                                                         when 970 then 3\n" +
                "                                                         when 971 then 3\n" +
                "                                                         when 1528 then 3\n" +
                "                                                         when 972 then 2\n" +
                "                                                         when 5617 then 6\n" +
                "                                                         when 163565 then 7\n" +
                "                                                         when 162221 then 8\n" +
                "                                                         when 157351 then 9\n" +
                "                                                         when 166606 then 12 end) as relationship,\n" +
                "                                                    c.patient_id,\n" +
                "                                                    c.date_created,\n" +
                "                                                    null,\n" +
                "                                                    c.changed_by,\n" +
                "                                                    c.date_created,\n" +
                "                                                    null,\n" +
                "                                                    c.changed_by,\n" +
                "                                                    c.voided,\n" +
                "                                                    c.voided_by,\n" +
                "                                                    c.date_voided,\n" +
                "                                                    c.voided_reason,\n" +
                "                                                    uuid()\n" +
                "                                             from kenyaemr_hiv_testing_patient_contact c\n" +
                "                                             where c.patient_id is not null\n" +
                "                                               AND NOT EXISTS (SELECT 1\n" +
                "                                                               FROM relationship r\n" +
                "                                                               WHERE r.person_a = c.patient_related_to\n" +
                "                                                                 AND r.relationship = CASE\n" +
                "                                                                                          WHEN c.relationship_type = 970\n" +
                "                                                                                              THEN 3\n" +
                "                                                                                          WHEN c.relationship_type = 971\n" +
                "                                                                                              THEN 3\n" +
                "                                                                                          WHEN c.relationship_type = 1528\n" +
                "                                                                                              THEN 3\n" +
                "                                                                                          WHEN c.relationship_type = 972\n" +
                "                                                                                              THEN 2\n" +
                "                                                                                          WHEN c.relationship_type = 5617\n" +
                "                                                                                              THEN 6\n" +
                "                                                                                          WHEN c.relationship_type = 163565\n" +
                "                                                                                              THEN 7\n" +
                "                                                                                          WHEN c.relationship_type = 162221\n" +
                "                                                                                              THEN 8\n" +
                "                                                                                          WHEN c.relationship_type = 157351\n" +
                "                                                                                              THEN 9\n" +
                "                                                                                          WHEN c.relationship_type = 166606\n" +
                "                                                                                              THEN 12\n" +
                "                                                                   END\n" +
                "                                                                 AND r.person_b = c.patient_id\n" +
                "                                                                 and r.voided = 0) group by patient_id,patient_related_to,relationship);";

        Context.getAdministrationService().executeSQL(insertRelationship, false);
        out.println("Completed migrating relationships");


        String updatePhysicalAddress = "update person_address a inner join (select patient_id, physical_address\n" +
                "                                    from kenyaemr_hiv_testing_patient_contact c\n" +
                "                                    where c.patient_id is not null) c on a.person_id = c.patient_id\n" +
                "set a.address1 = c.physical_address\n" +
                "where a.address1 is null;";

        Context.getAdministrationService().executeSQL(updatePhysicalAddress, false);
        out.println("Completed updating physical address");

    }
}
