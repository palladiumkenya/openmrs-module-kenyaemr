/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.MOH706;

import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Moh706LabCohortLibrary {

    public CohortDefinition getAllUrineAnalysisGlucoseTestsPositives() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Get urine analysis patients - glucose");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery("SELECT d.patient_id FROM kenyaemr_etl.etl_patient_demographics d\n" +
			"                             INNER JOIN kenyaemr_etl.etl_laboratory_extract x ON x.patient_id = d.patient_id\n" +
			"                             WHERE x.lab_test = 1305 AND x.test_result = 1302 AND date(x.visit_date) BETWEEN :startDate AND :endDate\n" +
			"                            UNION\n" +
			"                            SELECT d.patient_id FROM kenyaemr_etl.etl_patient_demographics d\n" +
			"                             INNER JOIN kenyaemr_etl.etl_laboratory_extract x ON x.patient_id = d.patient_id\n" +
			"                             WHERE x.lab_test = 856 AND x.test_result IS NOT NULL AND date(x.visit_date) BETWEEN :startDate AND :endDate;"

        );
        return sql;
    }
}
