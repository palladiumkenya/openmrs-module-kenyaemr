/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.threepm;

import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH731Greencard.ETLMoh731GreenCardCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.kp.KPMoh731PlusCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.kp.KPMonthlyReportCohortLibrary;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

/**
 * Created by dev on 1/14/17.
 */

/**
 * Library of HIV related indicator definitions. All indicators require parameters ${startDate} and ${endDate}
 */
@Component
public class ThreePMIndicatorLibrary {
    @Autowired
    private ETLMoh731GreenCardCohortLibrary moh731Cohorts;
    @Autowired
    private ThreePMCohortLibrary threePMCohorts;
    @Autowired
    private KPMonthlyReportCohortLibrary kpifCohorts;
    @Autowired
    private KPMoh731PlusCohortLibrary kpCohorts;

    public CohortIndicator htsScreened() {
        return cohortIndicator("Number screened",
                map(threePMCohorts.htsScreened(), "startDate=${startDate},endDate=${endDate}")
        );
    }

    public CohortIndicator eligibleForHIVTest() {
        return cohortIndicator("Eligible for HIV Test",
                map(threePMCohorts.eligibleForHIVTest(), "startDate=${startDate},endDate=${endDate}")
        );
    }

    public CohortIndicator htsNumberTested() {
        return cohortIndicator("Individuals tested", map(moh731Cohorts.htsNumberTested(), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator newPositive() {
        return cohortIndicator("Newly tested HIV positive", map(moh731Cohorts.htsNumberTestedPositive(), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator linked() {
        return cohortIndicator("Linked to care", map(moh731Cohorts.htsAllNumberTestedPositiveAndLinked(), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator firstANCVisitMchmsAntenatal() {
        return cohortIndicator("1st ANC visit clients", map(moh731Cohorts.firstANCVisitMchmsAntenatal(), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator knownPositiveAtFirstANC() {
        return cohortIndicator("1st ANC visit clients Known positive", map(moh731Cohorts.knownPositiveAtFirstANC(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator kpCurr() {
        return cohortIndicator("KPV2_Result: Number of KPs currently active in the DICE/Program", map(kpifCohorts.kpCurr(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator currentlyOnARTOnSite(String kpType) {
        return cohortIndicator("KPV2_Result: Number of KPs currently active on ART at the DICE", map(kpifCohorts.currOnARTKP(kpType), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator kpCurrOnPrEP(String kpType) {
        return cohortIndicator("KPV2_Result: Number of KPs currently on PrEP", map(kpifCohorts.kpPrepCurrDice(kpType), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator kpCurrOnPrEPWithSTI(String kpType) {
        return cohortIndicator("KPV2_Result: Number of KPs currently on PrEP", map(threePMCohorts.kpCurrOnPrEPWithSTI(kpType), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator kpCurrentOnARTOffsite(String kpType) {
        return cohortIndicator("KPV2_Result: Number of KPs currently active on ART at other CCC", map(threePMCohorts.kpCurrentOnARTOffsite(kpType), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator ppCurrentOnARTOffsite() {
        return cohortIndicator("PP: Number of PP current on ART - Other Facilities", map(threePMCohorts.ppCurrentOnARTOffsite(), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator ppCurrentOnARTOnSite() {
        return cohortIndicator("PP: Number of PP current on ART - This PP DICE", map(threePMCohorts.ppCurrentOnARTOnSite(), "startDate=${startDate},endDate=${endDate}"));
    }
}

