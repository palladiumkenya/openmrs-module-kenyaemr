/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.fp;

public class FPQueryVisitTypeLibrary {
    public static final String TYPE_OF_VISIT_QUERY =
            "SELECT f.encounter_id, " +
                    "       CASE f.type_of_visit_for_method " +
                    "            WHEN 164180 THEN 'New' " +
                    "            WHEN 164142 THEN 'Re-visit' " +
                    "       END AS type_of_visit_for_method " +
                    "FROM kenyaemr_etl.etl_family_planning f " +
                    "WHERE f.type_of_visit_for_method IN (164180, 164142)"+
                    "  AND DATE(f.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate);";
}
