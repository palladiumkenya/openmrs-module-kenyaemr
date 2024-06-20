/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.shared.hiv.art;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class FcdrrIndicatorLibrary {

    @Autowired
    private FcdrrCohortLibrary fcdrrCohortLibrary;

    //Indicator Libraries based on Queries and MOH710 dimensions

//    public CohortIndicator adultfirstlinetld() {
//
//        return cohortIndicator("tdf3tcdtg",map(fcdrrCohortLibrary.mothersReceivedARV12MonthsCohort(), "startDate=${startDate},endDate=${endDate}")
//        );
//    }

    // public FcdrrIndicatorLibrary(fcdrrCohortLibrary drugStockService) {
    //     this.drugStockService = drugStockService;
    // }

    public List<Map<String, Object>> generateDrugStockReport() {
        return fcdrrCohortLibrary.getDrugStockReport();
    }

    // public int drugunitpackagesize(String regimenName) {
    //     return cohortIndicator("Adults",map(fcdrrCohortLibrary.getDrugStockReport(regimenName), "startDate=${startDate},endDate=${endDate}")
    //     );
    // }

    public CohortIndicator adultFirstLineTLD() {

        return cohortIndicator("TDF+3TC+DTG",map(fcdrrCohortLibrary.AdultFirstTLD(), "startDate=${startDate},endDate=${endDate}")
        );
    }

    public CohortIndicator adultFirstLineTLE() {

        return cohortIndicator("TDF+3TC+EFV",map(fcdrrCohortLibrary.AdultFirstTLE(), "startDate=${startDate},endDate=${endDate}")
        );
    }

    public CohortIndicator patientsOnAdultFirstLine(String regimenName, String regimenLine, String ageGroup) {
        return cohortIndicator("AdultFirstLine",map(fcdrrCohortLibrary.txCurrpatientOnSpecificRegimenAndRegimenLine(regimenName,regimenLine, ageGroup), "startDate=${startDate},endDate=${endDate}")
        );
    }

    public CohortIndicator patientsOnAdultSecondLine(String regimenName, String regimenLine, String ageGroup) {
        return cohortIndicator("AdultSecondLine",map(fcdrrCohortLibrary.txCurrpatientOnSpecificRegimenAndRegimenLine(regimenName,regimenLine, ageGroup), "startDate=${startDate},endDate=${endDate}")
        );
    }

    public CohortIndicator patientsOnAdultThirdLine(String regimenName, String regimenLine, String ageGroup) {
        return cohortIndicator("AdultThirdLine",map(fcdrrCohortLibrary.txCurrpatientOnSpecificRegimenAndRegimenLine(regimenName,regimenLine, ageGroup), "startDate=${startDate},endDate=${endDate}")
        );
    }

    public CohortIndicator patientsOnSpecificRegimen(String regimenName, String regimenLine, String ageGroup) {
        return cohortIndicator("TDF+3TC+EFV",map(fcdrrCohortLibrary.txCurrpatientOnSpecificRegimenAndRegimenLine(regimenName,regimenLine, ageGroup), "startDate=${startDate},endDate=${endDate}")
        );
    }

    public CohortIndicator patientsOnOtherRegimen(String regimenName, String regimenLine, String ageGroup) {
        return cohortIndicator("TDF+3TC+EFV",map(fcdrrCohortLibrary.txCurrpatientOnAnyOtherRegimenandRegimenLine(regimenName,regimenLine, ageGroup), "startDate=${startDate},endDate=${endDate}")
        );
    }

}
