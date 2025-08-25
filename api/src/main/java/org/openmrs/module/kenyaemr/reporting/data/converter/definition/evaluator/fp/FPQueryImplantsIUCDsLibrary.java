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

public class FPQueryImplantsIUCDsLibrary {

    public static final String TYPE_OF_VISIT_QUERY =
            "SELECT \n" +
                    "    f.encounter_id,\n" +
                    "    CASE f.type_of_visit_for_method\n" +
                    "        WHEN 164180 THEN '1st time insertion'\n" +
                    "        WHEN 164161 THEN 'Removal'\n" +
                    "        WHEN 1000049 THEN 'Re-insertion'\n" +
                    "        WHEN 2002014 THEN 'Check-up'\n" +
                    "    END AS type_of_visit_label\n" +
                    "FROM kenyaemr_etl.etl_family_planning f\n" +
                    "WHERE DATE(f.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate);\n;";
}
