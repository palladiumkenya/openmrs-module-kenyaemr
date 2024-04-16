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

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

@Component
public class Moh717IndicatorLibrary {

    private final Moh717CohortLibrary moh717CohortLibrary;

    @Autowired
    public Moh717IndicatorLibrary(Moh717CohortLibrary moh717CohortLibrary) {
        this.moh717CohortLibrary = moh717CohortLibrary;
    }

    public CohortIndicator getAllPatients() {
        return cohortIndicator("All Patients", ReportUtils.map(moh717CohortLibrary.getAllPatients(), "startDate=${startDate},endDate=${endDate}"));
    }
}
