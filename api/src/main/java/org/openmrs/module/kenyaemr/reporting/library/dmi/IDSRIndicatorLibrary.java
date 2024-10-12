/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.dmi;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH731Greencard.ETLMoh731GreenCardCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimCohortLibrary;
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
public class IDSRIndicatorLibrary {
    @Autowired
    private IDSRCohortLibrary idsrCohortLibrary;


    /**
     * Dysentery Cases
     * @return indicator
     */
    public CohortIndicator dysenteryCases() {
        return cohortIndicator("Individuals tested at the facility", map(idsrCohortLibrary.dysenteryCases(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Cholera Cases
     * @return indicator
     */
    public CohortIndicator choleraCases() {
        return cohortIndicator("Individuals tested", map(idsrCohortLibrary.choleraCases(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * ILI Cases
     * @return indicator
     */
    public CohortIndicator iliCases() {
        return cohortIndicator("Individuals tested at the community", map(idsrCohortLibrary.iliCases(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Sari Cases
     * @return indicator
     */
    public CohortIndicator sariCases() {
        return cohortIndicator("New tests", map(idsrCohortLibrary.sariCases(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Riftvalley Fever Cases
     * @return indicator
     */
    public CohortIndicator riftvalleyFeverCases() {
        return cohortIndicator("Repeat tests", map(idsrCohortLibrary.riftvalleyFeverCases(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Malaria
     * @return indicator
     */
    public CohortIndicator malariaCases() {
        return cohortIndicator("Couple testing", map(idsrCohortLibrary.malariaCases(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Chikungunya Cases
     * @return indicator
     */
    public CohortIndicator chikungunyaCases() {
        return cohortIndicator("Key population testing", map(idsrCohortLibrary.chikungunyaCases(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Poliomyelitis Cases
     * @return indicator
     */
    public CohortIndicator poliomyelitisCases() {
        return cohortIndicator("Acute Flaccid Paralysis", map(idsrCohortLibrary.poliomyelitisCases(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Viral Haemorrhagic Fever Cases
     * @return indicator
     */
    public CohortIndicator viralHaemorrhagicFeverCases() {
        return cohortIndicator("Acute Haemorrhagic Fever", map(idsrCohortLibrary.viralHaemorrhagicFeverCases(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Measles cases
     * @return indicator
     */
    public CohortIndicator measlesCases() {
        return cohortIndicator("Measles cases", map(idsrCohortLibrary.measlesCases(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Monkey Pox Cases
     * @return indicator
     */
    public CohortIndicator monkeyPoxCases() {
        return cohortIndicator("Monkey Pox", map(idsrCohortLibrary.monkeyPoxCases(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Jaundice case
     * @return indicator
     */
    public CohortIndicator jaundiceCases() {
        return cohortIndicator("Acute Jaundice", map(idsrCohortLibrary.jaundiceCases(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Neurological Syndrome
     * @return
     */
    public CohortIndicator neurologicalSyndromeCases() {
        return cohortIndicator("Neurological Syndrome", map(idsrCohortLibrary.neurologicalSyndrome(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Acute Watery Diarrhoea
     * @return
     */
    public CohortIndicator acuteWateryDiarrhoeaCases() {
        return cohortIndicator("Acute Watery Diarrhoea", map(idsrCohortLibrary.acuteWateryDiarrhoea(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Acute Febrile Rash infections
     * @return
     */
    public CohortIndicator acuteFebrileRashCases() {
        return cohortIndicator("Acute Febrile Rash Infections", map(idsrCohortLibrary.acuteFebrileRashInfections(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Acute Febrile illness
     * @return
     */
    public CohortIndicator acuteFebrileIllnessCases() {
        return cohortIndicator("Acute Febrile Illness", map(idsrCohortLibrary.acuteFebrileIllness(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Acute Meningitis and Encephalitis
     */
    public CohortIndicator acuteMeningitisCases() {
        return cohortIndicator("Acute Meningitis and Encephalitis", map(idsrCohortLibrary.acuteMeningitisAndEncephalitis(), "startDate=${startDate},endDate=${endDate}"));
    }
}