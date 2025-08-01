/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.kenyaemr.reporting.library.ETLReports.publicHealthActionReport;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.viralSuppression.ViralSuppressionCohortLibrary;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

/**
 * Library of HIV related indicator definitions.
 */
@Component
public class PublicHealthActionIndicatorLibrary {
    @Autowired
    private PublicHealthActionCohortLibrary cohortLibrary;

    @Autowired
    private ViralSuppressionCohortLibrary vlSuppressionCohorts;

    /**
     * Number of HIV+ patients not linked to care
     * @return the indicator
     */
    public CohortIndicator notLinked() {
        return cohortIndicator("HIV+ patients not linked to care", ReportUtils.map(cohortLibrary.notLinked(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of HEIs with undocumented status
     * @return
     */
    public CohortIndicator undocumentedHEIStatus() {
        return cohortIndicator("HEIs with undocumented status", ReportUtils.map(cohortLibrary.undocumentedHEIStatus(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of patients with no current vl result
     * Valid means VL was taken <= 12 months ago and invalid means VL was taken > 12 months ago
     * @return the indicator
     */
    public CohortIndicator invalidVL() {
        return cohortIndicator("No Current VL Results", ReportUtils.map(cohortLibrary.invalidVL(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of patients with unsuppressed VL result in their last VL. Indicated if valid or invalid vl.
     * Valid means VL was taken <= 12 months and invalid means VL was taken > 12 months ago
     * @return the indicator
     */
    public CohortIndicator unsuppressedWithValidVL() {
        return cohortIndicator("Unsuppressed VL result", ReportUtils.map(vlSuppressionCohorts.unsuppressed(), "endDate=${endDate}"));
    }

    /**
     * Number of patients with unsuppressed VL result in their last VL. Indicated if valid or invalid vl.
     * Valid means VL was taken <= 12 months and invalid means VL was taken > 12 months ago
     * @return the indicator
     */
    public CohortIndicator unsuppressedWithoutValidVL() {
        return cohortIndicator("Unsuppressed VL result", ReportUtils.map(cohortLibrary.unsuppressedWithoutValidVL(), "startDate=${startDate},endDate=${endDate}"));
    }
	/**
	 * Number of patients with unsuppressed VL result without Enhanced Adherence Counseling
	 * @return the indicator
	 */
	public CohortIndicator txCUrrUnsuppressedWithoutEAC() {
		return cohortIndicator("Tx Curr Unsuppressed VL without EAC", ReportUtils.map(cohortLibrary.txCUrrUnsuppressedWithoutEAC(), "startDate=${startDate},endDate=${endDate}"));
	}

    public CohortIndicator delayedVLTesting() {
        return cohortIndicator("Tx Curr Unsuppressed VL without EAC", ReportUtils.map(cohortLibrary.delayedVLTesting(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator delayedVLTestingZeroGracePeriod() {
        return cohortIndicator("Tx Curr Due for VL sample not taken", ReportUtils.map(cohortLibrary.delayedVLTestingZeroGracePeriod(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Tx Curr with unsuppressed VL without EAC
     * @return
     */
    public CohortIndicator txCUrrUnsuppressedWithoutEACCs() {
        return cohortIndicator("Tx Curr with unsuppressed VL without EAC", ReportUtils.map(cohortLibrary.txCUrrUnsuppressedWithoutEACCs(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Number of undocumented LTFU patients
     * @return the indicator
     */
    public CohortIndicator undocumentedLTFU() {
        return cohortIndicator("Undocumented LTFU patients", ReportUtils.map(cohortLibrary.undocumentedLTFU(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of patients who missed HIV appointments
     * @return the indicator
     */
    public CohortIndicator recentDefaulters() {
        return cohortIndicator("Missed appointments", ReportUtils.map(cohortLibrary.recentDefaulters(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of HEIs not linked to Mothers
     * @return the indicator
     */
    public CohortIndicator unlinkedHEI() {
        return cohortIndicator("HEIs not linked to Mothers", ReportUtils.map(cohortLibrary.unlinkedHEI(), "startDate=${startDate},endDate=${endDate}"));
    }
	/**
	 * Number of HEIs missing HIV Tests
	 * @return the indicator
	 */
    public CohortIndicator heiMissedHIVTests() {
        return cohortIndicator("HEIs Missed HIV tests", ReportUtils.map(cohortLibrary.heiMissedHIVTests(), "startDate=${startDate},endDate=${endDate}"));
    }
	/**
	 * Number of HEIs  6-8 weeks missing HIV DNA PCR
	 * @return the indicator
	 */
	public CohortIndicator heiSixToEightWeeksMissingPCRTests() {
		return cohortIndicator("HEIs 6-8 weeks Missing DNA PCR HIV tests", ReportUtils.map(cohortLibrary.heiSixToEightWeeksMissingPCRTests(), "startDate=${startDate},endDate=${endDate}"));
	}
	/**
	 * Number of HEIs  24 Months with undocumented Outcome
	 * @return the indicator
	 */
	public CohortIndicator hei24MonthsUndocumentedOutcome() {
		return cohortIndicator("HEIs 24 Months with undocumented Outcome", ReportUtils.map(cohortLibrary.hei24MonthsUndocumentedOutcome(), "startDate=${startDate},endDate=${endDate}"));
	}
    /**
     * Number of HEIs  24 Months with undocumented Outcome Case surveillance
     * @return the indicator
     */
    public CohortIndicator hei24MonthsUndocumentedOutcomeCs() {
        return cohortIndicator("HEIs 24 Months with undocumented Outcome", ReportUtils.map(cohortLibrary.hei24MonthsUndocumentedOutcomeCs(), "startDate=${startDate},endDate=${endDate}"));
    }
	/**
	 * Number of Pregnant and postpartum women at high risk (ML-based) not linked to PrEP
	 * @return the indicator
	 */
	public CohortIndicator pregnantPostPartumNotLinkedToPrep() {
		return cohortIndicator("Pregnant and postpartum women at high risk (ML-based) not linked to PrEP", ReportUtils.map(cohortLibrary.pregnantPostPartumNotLinkedToPrep(), "startDate=${startDate},endDate=${endDate}"));
	}
    /**
     * Number of adolescents not in OTZ
     * @return the indicator
     */
    public CohortIndicator adolescentsNotInOTZ() {
        return cohortIndicator("Adolescents not in OTZ", ReportUtils.map(cohortLibrary.adolescentsNotInOTZ(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of children and adolescents living with HIV not in OVC
     * @return the indicator
     */
    public CohortIndicator calhivNotInOVC() {
        return cohortIndicator("Children not in OVC", ReportUtils.map(cohortLibrary.calhivNotInOVC(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of children and adolescents living with HIV not on DTG regimen
     * @return the indicator
     */
    public CohortIndicator calhivNotOnDTGRegimen() {
        return cohortIndicator("Number of CALHIV not on DTG regimen", ReportUtils.map(cohortLibrary.calhivNotOnDTGRegimen(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Number of contacts with undocumented HIV status
     * @return the indicator
     */
    public CohortIndicator contactsUndocumentedHIVStatus() {
        return cohortIndicator("Contacts with undocumented HIV status", ReportUtils.map(cohortLibrary.contactsUndocumentedHIVStatus(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Number of children contacts with undocumented HIV status
     * @return the indicator
     */
    public CohortIndicator childrenContactsUndocumentedHIVStatus() {
        return cohortIndicator("Children Contacts with undocumented HIV status", ReportUtils.map(cohortLibrary.childrenContactsUndocumentedHIVStatus(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of SNS Contacts with undocumented HIV status
     * @return the indicator
     */
    public CohortIndicator snsContactsUndocumentedHIVStatus() {
        return cohortIndicator("SNS Contacts with undocumented HIV status", ReportUtils.map(cohortLibrary.snsContactsUndocumentedHIVStatus(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of TX_CURR clients without NUPI
     * @return the indicator
     */
    public CohortIndicator txCurrclientsWithoutNUPI() {
        return cohortIndicator("TX_Curr Clients without NUPI", ReportUtils.map(cohortLibrary.txCurrclientsWithoutNUPI(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of patients died
     * @return the indicator
     */
    public CohortIndicator numberOfDeaths() {
        return cohortIndicator("Number of deaths", ReportUtils.map(cohortLibrary.numberOfDeaths(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of patients not vaccinated for Covid-19
     * @return the indicator
     */
    public CohortIndicator notVaccinatedForCovid19() {
        return cohortIndicator("Number of patients not vaccinated for Covid-19", ReportUtils.map(cohortLibrary.notVaccinatedForCovid19(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of patients not assessed for Covid-19
     * @return the indicator
     */
    public CohortIndicator notAssessedForCovid19() {
        return cohortIndicator("Number of patients not assessed for Covid-19", ReportUtils.map(cohortLibrary.notAssessedForCovid19(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * HEI turning 6-8 weeks without DNA PCR test
     * @return the indicator
     */
    public CohortIndicator heiSixToEightWeeksMissingPCRTestsCs() {
        return cohortIndicator("HEI turning 6-8 weeks without DNA PCR test", ReportUtils.map(cohortLibrary.heiSixToEightWeeksMissingPCRTestsCs(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator pregnantPostPartumNotLinkedToPrepCs() {
        return cohortIndicator("HEI turning 6-8 weeks without DNA PCR test", ReportUtils.map(cohortLibrary.pregnantPostPartumNotLinkedToPrepCs(), "startDate=${startDate},endDate=${endDate}"));
    }
}
