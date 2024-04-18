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

import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.reporting.MohReportUtils.ReportingUtils.cohortIndicator;

@Component
public class Moh706IndicatorLibrary {
    private final Moh706LabCohortLibrary moh706LabCohortLibrary;

    public Moh706IndicatorLibrary(Moh706LabCohortLibrary moh706LabCohortLibrary) {
        this.moh706LabCohortLibrary = moh706LabCohortLibrary;
    }

    public CohortIndicator getAllUrineAnalysisGlucoseTestsPositives() {
        return cohortIndicator(
                "All patients who have urinalysis glucose",
                map(moh706LabCohortLibrary.getAllUrineAnalysisGlucoseTestsPositives(),
                        "startDate=${startDate},endDate=${endDate}"));
    }
}
