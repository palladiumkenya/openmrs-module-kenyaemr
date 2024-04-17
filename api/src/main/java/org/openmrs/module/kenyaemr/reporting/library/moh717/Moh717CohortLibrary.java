/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.moh717;

import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Moh717CohortLibrary {

    public CohortDefinition getAllPatientsWithEncountersWithinReportingPeriod() {
        SqlCohortDefinition sql = new SqlCohortDefinition();
        sql.setName("Patients with encounters within date period");
        sql.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sql.addParameter(new Parameter("endDate", "End Date", Date.class));
        sql.setQuery(
                "SELECT patient_id FROM patient p "
                + " INNER JOIN encounter e ON p.patient_id=e.patient_id "
                + " WHERE e.encounter_datetime <= :endDate "
                + " AND p.voided = 0 AND e.voided = 0"
        );

        return sql;
    }
}
