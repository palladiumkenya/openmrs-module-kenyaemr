/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim;

import org.openmrs.module.kenyacore.report.ReportUtils;

import org.openmrs.module.kenyaemr.reporting.data.converter.definition.KPTypeDataDefinition;

import org.openmrs.module.kenyaemr.reporting.data.converter.definition.DurationToNextAppointmentDataDefinition;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimCohortLibrary;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;


/**
 * Library of DATIM related indicator definitions. All indicators require parameters ${startDate} and ${endDate}
 */
@Component
public class DatimIndicatorLibrary {
    @Autowired
    private DatimCohortLibrary datimCohorts;


    /**
     * Number of patients who are currently on ART
     * @return the indicator
     */
    public CohortIndicator currentlyOnArt() {
        return cohortIndicator("Currently on ART", ReportUtils.map(datimCohorts.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of Pregnant women with HIV infection receiving antiretroviral therapy (ART)
     * @return the indicator
     */
    public CohortIndicator pregnantCurrentlyOnART() {
        return cohortIndicator("Pregnant Currently on ART", ReportUtils.map(datimCohorts.pregnantCurrentOnArt(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * //Number of Breastfeeding mothers with HIV infection receiving antiretroviral therapy (ART
     * @return the indicator
     */
    public CohortIndicator bfMothersCurrentlyOnART() {
        return cohortIndicator("BF Currently on ART", ReportUtils.map(datimCohorts.bfCurrentOnArt(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * //Number of FSW with HIV infection receiving antiretroviral therapy (ART
     * @return the indicator
     */
    public CohortIndicator fswCurrentlyOnART(KPTypeDataDefinition fsw) {
        return cohortIndicator("FSW Currently on ART", ReportUtils.map(datimCohorts.kpCurrentOnArt(fsw), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * //Number of MSM with HIV infection receiving antiretroviral therapy (ART
     * @return the indicator
     */
    public CohortIndicator msmCurrentlyOnART(KPTypeDataDefinition msm) {
        return cohortIndicator("MSM Currently on ART", ReportUtils.map(datimCohorts.kpCurrentOnArt(msm), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * //Number PWID with HIV infection receiving antiretroviral therapy (ART
     * @return the indicator
     */
    public CohortIndicator pwidCurrentlyOnART(KPTypeDataDefinition pwid) {
        return cohortIndicator("PWID Currently on ART", ReportUtils.map(datimCohorts.kpCurrentOnArt(pwid), "startDate=${startDate},endDate=${endDate}"));
    }

     /**One Month to next appointment
     * @return the indicator
     */
    public CohortIndicator currentlyOnARTOneMonthDrugsDispensed(DurationToNextAppointmentDataDefinition duration) {
        return cohortIndicator("Currently on ART one Month Drugs Dispensed", ReportUtils.map(datimCohorts.drugDurationCurrentOnArt(duration), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * //Two Months to next appointment
     * @return the indicator
     */
    public CohortIndicator currentlyOnARTTwoMonthsDrugsDispensed(DurationToNextAppointmentDataDefinition duration) {
        return cohortIndicator("Currently on ART two Months Drugs Dispensed", ReportUtils.map(datimCohorts.drugDurationCurrentOnArt(duration), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * //Three Months to next appointment
     * @return the indicator
     */
    public CohortIndicator currentlyOnARTThreeMonthsDrugsDispensed(DurationToNextAppointmentDataDefinition duration) {
        return cohortIndicator("Currently on ART three Months Drugs Dispensed", ReportUtils.map(datimCohorts.drugDurationCurrentOnArt(duration), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * //Four months to next appointment
     * @return the indicator
     */
    public CohortIndicator currentlyOnARTFourMonthsDrugsDispensed(DurationToNextAppointmentDataDefinition duration) {
        return cohortIndicator("Currently on ART Four Months Drugs Dispensed", ReportUtils.map(datimCohorts.drugDurationCurrentOnArt(duration), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * //Five months to next appointment
     * @return the indicator
     */
    public CohortIndicator currentlyOnARTFiveMonthsDrugsDispensed(DurationToNextAppointmentDataDefinition duration) {
        return cohortIndicator("Currently on ART Five Months Drugs Dispensed", ReportUtils.map(datimCohorts.drugDurationCurrentOnArt(duration), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * //Six Months to next appointment
     * @return the indicator
     */
    public CohortIndicator currentlyOnARTSixMonthsDrugsDispensed(DurationToNextAppointmentDataDefinition duration) {
        return cohortIndicator("Currently on ART six Month Drugs Dispensed", ReportUtils.map(datimCohorts.drugDurationCurrentOnArt(duration), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * //Over Six Months to next appointment
     * @return the indicator
     */
    public CohortIndicator currentlyOnARTOverSixMonthsDrugsDispensed(DurationToNextAppointmentDataDefinition duration) {
        return cohortIndicator("Currently on ART Over six Month Drugs Dispensed", ReportUtils.map(datimCohorts.drugDurationCurrentOnArt(duration), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Number of patients who were started on Art
     * @return the indicator
     */
    public CohortIndicator startedOnArt() {
        return cohortIndicator("Started on ART", ReportUtils.map(datimCohorts.startedOnART(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of patients who were started on Art and are pregnant
     * @return the indicator
     */
    public CohortIndicator startedOnARTAndPregnant() {
        return cohortIndicator("Started on ART", ReportUtils.map(datimCohorts.startedOnARTAndPregnant(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of patients who were started on Art and are TB co-infected
     * @return the indicator
     */
    public CohortIndicator startedOnARTAndTBCoinfected() {
        return cohortIndicator("Started on ART", ReportUtils.map(datimCohorts.startedOnARTAndTBCoinfected(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of patients in the ART 12 month cohort
     * @return the indicator
     */
    public CohortIndicator art12MonthCohort() {
        return cohortIndicator("ART 12 Month Net Cohort", ReportUtils.map(datimCohorts.art12MonthCohort(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of patients in the 12 month cohort who are on ART
     * @return the indicator
     */
    public CohortIndicator onTherapyAt12Months() {
        return cohortIndicator("On therapy at 12 months", ReportUtils.map(datimCohorts.onTherapyAt12Months(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * No of patients with successful VL test/result in the last 12 months
     * @return the indicator
     */
    public CohortIndicator patientsWithVLResults() {
        return cohortIndicator("VL Results", ReportUtils.<CohortDefinition>map(datimCohorts.viralLoadResultsInLast12Months(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of patients with viral suppression in the last 12 months
     * @return the indicator
     */
    public CohortIndicator patientsWithViralLoadSuppression() {
        return cohortIndicator("Viral Suppression", ReportUtils.<CohortDefinition>map(datimCohorts.viralSuppressionInLast12Months(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of patients tested Negative for HIV at ANC
     * @return the indicator
     */
    public CohortIndicator patientsTestNegativeAtANC() {
        return cohortIndicator("HIV Negative Results at ANC", ReportUtils.<CohortDefinition>map(datimCohorts.patientHIVNegativeResultsATANC(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of patients tested positive for HIV at ANC
     * @return the indicator
     */
    public CohortIndicator patientsTestPositiveAtANC() {
        return cohortIndicator("HIV Positive Results at ANC", ReportUtils.<CohortDefinition>map(datimCohorts.patientHIVPositiveResultsAtANC(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of clients with known HIV status at ANC
     * @return the indicator
     */
    public CohortIndicator clientsWithKnownHIVStatusAtANC() {
        return cohortIndicator("Clients with Known HIV Status at ANC", ReportUtils.<CohortDefinition>map(datimCohorts.knownStatusAtANC(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Number of clients with known HIV status at ANC
     * @return the indicator
     */
    public CohortIndicator clientsWithUnKnownHIVStatusAtANC() {
        return cohortIndicator("Clients with Unknown HIV Status at ANC", ReportUtils.<CohortDefinition>map(datimCohorts.unKnownStatusAtANC(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of clients with positive HIV status before ANC-1
     * @return the indicator
     */
    public CohortIndicator clientsWithPositiveHivStatusBeforeAnc1() {
        return cohortIndicator("Clients with positive HIV Status before ANC-1", ReportUtils.<CohortDefinition>map(datimCohorts.positiveHivStatusBeforeAnc1(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of clients with Negative HIV status before ANC-1
     * @return the indicator
     */
    public CohortIndicator clientsWithNegativeHivStatusBeforeAnc1() {
        return cohortIndicator("Clients with Negative HIV Status before ANC-1", ReportUtils.<CohortDefinition>map(datimCohorts.negativeHivStatusBeforeAnc1(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Number of clients newly enrolled for ANC
     * @return the indicator
     */
    public CohortIndicator clientsNewlyEnrolledToANC() {
        return cohortIndicator("Clients newly Enrolled For ANC", ReportUtils.<CohortDefinition>map(datimCohorts.newANCClients(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of Infants with a Negative virology test result
     * @return the indicator
     */
    public CohortIndicator infantsSampleTakenForVirology() {
        return cohortIndicator("Infants sample taken for Virology", ReportUtils.<CohortDefinition>map(datimCohorts.infantVirologySampleTaken(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Number of Infants with a Negative virology test result
     * @return the indicator
     */
    public CohortIndicator infantsTestedNegativeForVirology() {
        return cohortIndicator("Infants tested negative for Virology", ReportUtils.<CohortDefinition>map(datimCohorts.infantVirologyNegativeResults(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Number of Infants with a positive virology test result
     * @return the indicator
     */
    public CohortIndicator infantsTestedPositiveForVirology() {
        return cohortIndicator("Infants tested positive for Virology", ReportUtils.<CohortDefinition>map(datimCohorts.infantVirologyPositiveResults(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of Infants with a virology test but no result
     * @return the indicator
     */
    public CohortIndicator infantsTestedForVirologyNoResult() {
        return cohortIndicator("Infants tested positive for Virology", ReportUtils.<CohortDefinition>map(datimCohorts.infantVirologyNoResults(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of Infants turned HIV Positive 12 months after birth
     * @return the indicator
     */
    public CohortIndicator infantsTurnedHIVPositive() {
        return cohortIndicator("Infants identified HIV Positive within 12 months after birth", ReportUtils.<CohortDefinition>map(datimCohorts.infantsTurnedHIVPositive(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * HIV Positive women on ART screened Negative for cervical cancer 1st time
     * @return the indicator
     */
    public CohortIndicator firstTimescreenedCXCANegative() {
        return cohortIndicator("HIV Positive women on ART screened Negative for cervical cancer 1st time", ReportUtils.<CohortDefinition>map(datimCohorts.firstTimescreenedCXCANegative(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * HIV Positive women on ART screened Positive for cervical cancer 1st time
     * @return the indicator
     */
    public CohortIndicator firstTimescreenedCXCAPositive() {
        return cohortIndicator("HIV Positive women on ART screened Positive for cervical cancer 1st time", ReportUtils.<CohortDefinition>map(datimCohorts.firstTimescreenedCXCAPositive(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * HIV Positive women on ART screened Negative for cervical cancer 1st time
     * @return the indicator
     */
    public CohortIndicator firstTimescreenedCXCAPresumed() {
        return cohortIndicator("HIV Positive women on ART with Presumed cervical cancer 1st time screening", ReportUtils.<CohortDefinition>map(datimCohorts.firstTimescreenedCXCAPresumed(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Women on ART and Cx treatment screened Negative for cervical cancer
     * @return the indicator
     */
    public CohortIndicator postTreatmentscreenedCXCANegative() {
        return cohortIndicator("Women on ART and Cx treatment screened Negative for cervical cancer", ReportUtils.<CohortDefinition>map(datimCohorts.postTreatmentCXCANegative(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Women on ART and Cx treatment screened Positive for cervical cancer
     * @return the indicator
     */
    public CohortIndicator postTreatmentscreenedCXCAPositive() {
        return cohortIndicator("Women on ART and Cx treatment screened Positive for cervical cancer", ReportUtils.<CohortDefinition>map(datimCohorts.postTreatmentCXCAPositive(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Women on ART and Cx treatment screened Positive for cervical cancer
     * @return the indicator
     */
    public CohortIndicator postTreatmentscreenedCXCAPresumed() {
        return cohortIndicator("Women on ART and Cx treatment screened Presumed for cervical cancer", ReportUtils.<CohortDefinition>map(datimCohorts.postTreatmentCXCAPresumed(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Number of OVC Current on ART reported to implementing partner
     * @return the indicator
     */
    public CohortIndicator ovcOnART() {
        return cohortIndicator("Number of OVC Current on ART reported to implementing partner", ReportUtils.<CohortDefinition>map(datimCohorts.ovcOnART(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Number of OVC Not on ART reported to implementing partner
     * @return the indicator
     */
    public CohortIndicator ovcNotOnART() {
        return cohortIndicator("Number of OVC Not on ART reported to implementing partner", ReportUtils.<CohortDefinition>map(datimCohorts.ovcNotOnART(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * HIV Positive women on ART re-screened Negative for cervical cancer
     * @return the indicator
     */
    public CohortIndicator rescreenedCXCANegative() {
        return cohortIndicator("HIV Positive Women on ART re-screened Negative for cervical cancere", ReportUtils.<CohortDefinition>map(datimCohorts.rescreenedCXCANegative(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * HIV Positive women on ART re-screened Positive for cervical cancer
     * @return the indicator
     */
    public CohortIndicator rescreenedCXCAPositive() {
        return cohortIndicator("HIV Positive women on ART re-screened Positive for cervical cancer", ReportUtils.<CohortDefinition>map(datimCohorts.rescreenedCXCAPositive(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * HIV Positive women on ART with presumed cervical cancer during re-screening
     * @return the indicator
     */
    public CohortIndicator rescreenedCXCAPresumed() {
        return cohortIndicator("HIV Positive women on ART with presumed cervical cancer during re-screening", ReportUtils.<CohortDefinition>map(datimCohorts.rescreenedCXCAPresumed(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of Infants turned HIV Positive 12 months after birth and started on ART
     * @return the indicator
     */
    public CohortIndicator infantsTurnedHIVPositiveOnART() {
        return cohortIndicator("Infants identified HIV Positive within 12 months after birth and Started ART", ReportUtils.<CohortDefinition>map(datimCohorts.infantsTurnedHIVPositiveOnART(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Number of Mothers Already on ART at the start of current Pregnancy
     * @return the indicator
     */
    public CohortIndicator mothersAlreadyOnARTAtStartOfCurrentPregnancy() {
        return cohortIndicator("Mothers Already on ART at the start of current Pregnancy", ReportUtils.<CohortDefinition>map(datimCohorts.alreadyOnARTAtBeginningOfPregnacy(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     *TB_ART Proportion of HIV-positive new and relapsed TB cases New on ART during TB treatment
     * @return the indicator
     */
    public CohortIndicator newOnARTTBInfected() {
        return cohortIndicator("TB patients new on ART", ReportUtils.<CohortDefinition>map(datimCohorts.newOnARTTBInfected(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * TB_ART Proportion of HIV-positive new and relapsed TB cases Already on ART during TB treatment
     * @return the indicator
     */
    public CohortIndicator alreadyOnARTTBInfected() {
        return cohortIndicator("TB patients already on ART", ReportUtils.<CohortDefinition>map(datimCohorts.alreadyOnARTTBInfected(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Starting TB treatment newly started ART
     * @return the indicator
     */
    public CohortIndicator startingTBTreatmentNewOnART() {
        return cohortIndicator("Starting TB treatment newly started ART", ReportUtils.<CohortDefinition>map(datimCohorts.startingTBTreatmentNewOnART(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Starting TB treatment previously on ART
     * @return the indicator
     */
    public CohortIndicator startingTBTreatmentPrevOnART() {
        return cohortIndicator("Starting TB treatment previously on ART", ReportUtils.<CohortDefinition>map(datimCohorts.startingTBTreatmentPrevOnART(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * New on ART Screened Positive
     * @return the indicator
     */
    public CohortIndicator newOnARTScreenedPositive() {
        return cohortIndicator("New on ART Screened Positive", ReportUtils.<CohortDefinition>map(datimCohorts.newOnARTScreenedPositive(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Previously on ART Screened Positive
     * @return the indicator
     */
    public CohortIndicator prevOnARTScreenedPositive() {
        return cohortIndicator("Previously on ART Screened Positive", ReportUtils.<CohortDefinition>map(datimCohorts.prevOnARTScreenedPositive(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * New on ART Screened Negative
     * @return the indicator
     */
    public CohortIndicator newOnARTScreenedNegative() {
        return cohortIndicator("New on ART Screened Negative", ReportUtils.<CohortDefinition>map(datimCohorts.newOnARTScreenedNegative(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Previously on ART Screened Negative
     * @return the indicator
     */
    public CohortIndicator prevOnARTScreenedNegative() {
        return cohortIndicator("Previously on ART Screened Negative", ReportUtils.<CohortDefinition>map(datimCohorts.prevOnARTScreenedNegative(), "startDate=${startDate},endDate=${endDate}"));
    }    /**
     * Specimen sent for bacteriologic diagnosis of active TB
     * @return the indicator
     */
    public CohortIndicator specimenSent() {
        return cohortIndicator("Specimen sent for bacteriologic diagnosis of active TB", ReportUtils.<CohortDefinition>map(datimCohorts.specimenSent(), "startDate=${startDate},endDate=${endDate}"));
    }    /**
     * GeneXpert MTB/RIF assay (with or without other testing)
     * @return the indicator
     */
    public CohortIndicator geneXpertMTBRIF() {
        return cohortIndicator("GeneXpert MTB/RIF assay (with or without other testing)", ReportUtils.<CohortDefinition>map(datimCohorts.geneXpertMTBRIF(), "startDate=${startDate},endDate=${endDate}"));
    }    /**
     * Smear microscopy only
     * @return the indicator
     */
    public CohortIndicator smearMicroscopy() {
        return cohortIndicator("Smear microscopy only", ReportUtils.<CohortDefinition>map(datimCohorts.smearMicroscopy(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Additional test other than GeneXpert
     * @return the indicator
     */
    public CohortIndicator additionalTBTests() {
        return cohortIndicator("Additional test other than GeneXpert", ReportUtils.<CohortDefinition>map(datimCohorts.additionalTBTests(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Positive result returned for bacteriologic diagnosis of active TB
     * @return the indicator
     */
    public CohortIndicator resultsReturned() {
        return cohortIndicator("Positive result returned for bacteriologic diagnosis of active TB", ReportUtils.<CohortDefinition>map(datimCohorts.resultsReturned(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Number of Mothers new on ART during current pregnancy
     * @return the indicator
     */
    public CohortIndicator mothersNewOnARTDuringCurrentPregnancy() {
        return cohortIndicator("Mothers new on ART during current pregnancy", ReportUtils.<CohortDefinition>map(datimCohorts.newOnARTDuringPregnancy(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Number Tested NegativeInpatient Services
     * @return the indicator
     */
    public CohortIndicator testedNegativeInpatientServices() {
        return cohortIndicator("Tested NegativeInpatient Services", ReportUtils.<CohortDefinition>map(datimCohorts.testedNegativeInpatientServices(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number Tested PositiveInpatient Services
     * @return the indicator
     */
    public CohortIndicator testedPositiveInpatientServices() {
        return cohortIndicator("Tested PositiveInpatient Services", ReportUtils.<CohortDefinition>map(datimCohorts.testedPositiveInpatientServices(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number Tested NegativePMTCT services ANC-1 only
     * @return the indicator
     */
    public CohortIndicator testedNegativePMTCTANC1() {
        return cohortIndicator("Tested NegativePMTCT services ANC-1", ReportUtils.<CohortDefinition>map(datimCohorts.negativePMTCTANC1(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number Tested PositivePMTCT services ANC-1 only
     * @return the indicator
     */
    public CohortIndicator testedPositivePMTCTANC1() {
        return cohortIndicator("Tested PositivePMTCT services ANC-1", ReportUtils.<CohortDefinition>map(datimCohorts.positivePMTCTANC1(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number Tested NegativePMTCT services Post ANC-1 (including labour and delivery and BF)
     * @return the indicator
     */
    public CohortIndicator testedNegativePMTCTPostANC1() {
        return cohortIndicator("Tested NegativePMTCT services Post ANC-1", ReportUtils.<CohortDefinition>map(datimCohorts.negativePMTCTPostANC1(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number Tested PositivePMTCT services Post ANC-1 (including labour and delivery and BF)
     * @return the indicator
     */
    public CohortIndicator testedPositivePMTCTPostANC1() {
        return cohortIndicator("Tested PositivePMTCT services Post ANC-1", ReportUtils.<CohortDefinition>map(datimCohorts.positivePMTCTPostANC1(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Number Tested PositivePaediatric services
     * @return the indicator
     */
    public CohortIndicator testedPositivePaediatricServices() {
        return cohortIndicator("Tested PositivePaediatric services", ReportUtils.<CohortDefinition>map(datimCohorts.testedPositivePaediatricServices(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number Tested NegativePaediatric services
     * @return the indicator
     */
    public CohortIndicator testedNegativePaediatricServices() {
        return cohortIndicator("Tested NegativePaediatric services", ReportUtils.<CohortDefinition>map(datimCohorts.testedNegativePaediatricServices(), "startDate=${startDate},endDate=${endDate}"));
    }


    /**
     * PITC Malnutrition Clinics Negative
     * @return the indicator
     */
    public CohortIndicator testedNegativeMalnutritionClinic() {
        return cohortIndicator("Tested NegativeMalnutrition Clinics", ReportUtils.<CohortDefinition>map(datimCohorts.testedNegativeMalnutritionClinics(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * PITC Malnutrition Clinics Positive
     * @return the indicator
     */
    public CohortIndicator testedPositiveMalnutritionClinic() {
        return cohortIndicator("Tested PositiveMalnutrition Clinics", ReportUtils.<CohortDefinition>map(datimCohorts.testedPositiveMalnutritionClinics(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * PITC TB Clinic Negative
     * @return the indicator
     */
    public CohortIndicator testedNegativeTBClinic() {
        return cohortIndicator("Tested NegativeTB Clinic", ReportUtils.<CohortDefinition>map(datimCohorts.testedNegativeTBClinic(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * PITC TB Clinic Positive
     * @return the indicator
     */
    public CohortIndicator testedPositiveTBClinic() {
        return cohortIndicator("Tested PositiveTB Clinic", ReportUtils.<CohortDefinition>map(datimCohorts.testedPositiveTBClinic(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Tested NegativeOther
     * @return the indicator
     */
    public CohortIndicator testedNegativeOther() {
        return cohortIndicator("Tested NegativeOther", ReportUtils.<CohortDefinition>map(datimCohorts.testedNagativeOther(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Tested PositiveOther
     * @return the indicator
     */
    public CohortIndicator testedPositiveOther() {
        return cohortIndicator("Tested PositiveOther", ReportUtils.<CohortDefinition>map(datimCohorts.testedPositiveOther(),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * PWID Tested Positive
     * @return the indicator
     */
    public CohortIndicator pwidTestedPositive() {
        return cohortIndicator("PWID Tested Positive", ReportUtils.<CohortDefinition>map(datimCohorts.pwidTestedPositive(),
                "startDate=${startDate},endDate=${endDate}"));
    }


    /**
     * PWID Tested Negative
     * @return the indicator
     */
    public CohortIndicator pwidTestedNegative() {
        return cohortIndicator("PWID Tested Negative", ReportUtils.<CohortDefinition>map(datimCohorts.pwidTestedNegative(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * MSM Tested Positive
     * @return the indicator
     */
    public CohortIndicator msmTestedPositive() {
        return cohortIndicator("MSM Tested Positive", ReportUtils.<CohortDefinition>map(datimCohorts.msmTestedPositive(),
                "startDate=${startDate},endDate=${endDate}"));
    }


    /**
     * MSM Tested Negative
     * @return the indicator
     */
    public CohortIndicator msmTestedNegative() {
        return cohortIndicator("MSM Tested Negative", ReportUtils.<CohortDefinition>map(datimCohorts.msmTestedNegative(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * FSW Tested Positive
     * @return the indicator
     */
    public CohortIndicator fswTestedPositive() {
        return cohortIndicator("FSW Tested Positive", ReportUtils.<CohortDefinition>map(datimCohorts.fswTestedPositive(),
                "startDate=${startDate},endDate=${endDate}"));
    }


    /**
     * FSW Tested Negative
     * @return the indicator
     */
    public CohortIndicator fswTestedNegative() {
        return cohortIndicator("FSW Tested Negative", ReportUtils.<CohortDefinition>map(datimCohorts.fswTestedNegative(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Tested NegativeVCT
     * @return the indicator
     */
    public CohortIndicator testedNegativeVCT() {
        return cohortIndicator("Tested NegativeVCT", ReportUtils.<CohortDefinition>map(datimCohorts.testedNagativeVCT(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Tested PositiveVCT
     * @return the indicator
     */
    public CohortIndicator testedPositiveVCT() {
        return cohortIndicator("Tested PositiveVCT", ReportUtils.<CohortDefinition>map(datimCohorts.testedPositiveVCT(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * PITC Index Negative
     * @return the indicator
     */
    public CohortIndicator indexTestedNegative() {
        return cohortIndicator("PITC Index Negative", ReportUtils.<CohortDefinition>map(datimCohorts.indexTestedNegative(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * PITC Index Positive
     * @return the indicator
     */
    public CohortIndicator indexTestedPositive() {
        return cohortIndicator("PITC Index Positive", ReportUtils.<CohortDefinition>map(datimCohorts.indextestedPositive(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Tested Negative Mobile outreach
     * @return the indicator
     */
    public CohortIndicator testedNegativeMobile() {
        return cohortIndicator("Tested Negative Mobile outreach", ReportUtils.<CohortDefinition>map(datimCohorts.testedNegativeMobile(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Tested Positive Mobile outreach
     * @return the indicator
     */
    public CohortIndicator testedPositiveMobile() {
        return cohortIndicator("Tested Positive Mobile outreach", ReportUtils.<CohortDefinition>map(datimCohorts.testedPositiveMobile(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Tested Positive Social Network
     * @return the indicator
     */
    public CohortIndicator testedPositiveSNS() {
        return cohortIndicator("Tested Positive Social Network", ReportUtils.<CohortDefinition>map(datimCohorts.testedPositiveSNS(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Tested Negative Social Network
     * @return the indicator
     */
    public CohortIndicator testedNegativeSNS() {
        return cohortIndicator("Tested Positive Social Network", ReportUtils.<CohortDefinition>map(datimCohorts.testedNegativeSNS(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number Newly Started ART While BreastFeeding
     * @return the indicator
     */
    public CohortIndicator newlyStartedARTWhileBF() {
        return cohortIndicator("Newly Started ART While Breastfeeding", ReportUtils.<CohortDefinition>map(datimCohorts.newlyStartedARTWhileBreastFeeding(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number who Newly Started ART While Pregnant
     * @return the indicator
     */
    public CohortIndicator newlyStartedARTWhilePregnant() {
        return cohortIndicator("Newly Started ART While Pregnant", ReportUtils.<CohortDefinition>map(datimCohorts.newlyStartedARTWhilePregnant(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number Newly Started ART While Confirmed TB and / or TB Treated
     * @return the indicator
     */
    public CohortIndicator newlyStartedARTWithTB() {
        return cohortIndicator("Newly Started ART While Confirmed TB and / or TB Treated", ReportUtils.<CohortDefinition>map(datimCohorts.newlyStartedARTWithTB(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Disaggregated by Age / Sex
     * @return the indicator
     */
    public CohortIndicator newlyStartedARTByAgeSex() {
        return cohortIndicator("Newly Started ART While Confirmed TB and / or TB Treated", ReportUtils.<CohortDefinition>map(datimCohorts.newlyStartedARTByAgeSex(),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /*Annual Cohort Indicators*/
    /**
     * PMTCT_FO Number of HIV-exposed infants who were born 24 months prior to the reporting period
     * @return the indicator
     */
    public CohortIndicator totalHEI() {
        return cohortIndicator("Total HEI Cohort", ReportUtils.<CohortDefinition>map(datimCohorts.totalHEICohort(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * PMTCT_FO HEI Cohort HIV infected
     * @return the indicator
     */
    public CohortIndicator hivInfectedHEI() {
        return cohortIndicator("HIV Infected HEI Cohort", ReportUtils.<CohortDefinition>map(datimCohorts.hivInfectedHEICohort(),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * PMTCT_FO HEI Cohort HIV uninfected
     * @return the indicator
     */
    public CohortIndicator hivUninfectedHEI() {
        return cohortIndicator("Uninfected HEI Cohort", ReportUtils.<CohortDefinition>map(datimCohorts.hivUninfectedHEICohort(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * PMTCT_FO HEI Cohort HIV-final status unknown
     * @return the indicator
     */
    public CohortIndicator unknownHIVStatusHEI() {
        return cohortIndicator("Unknown HIV Status HEI Cohort", ReportUtils.<CohortDefinition>map(datimCohorts.unknownHIVStatusHEICohort(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * PMTCT_FO HEI died with HIV-final status unknown
     * @return the indicator
     */
    public CohortIndicator heiDiedWithunknownHIVStatus() {
        return cohortIndicator("HEI Died with Unknown HIV Status", ReportUtils.<CohortDefinition>map(datimCohorts.heiDiedWithUnknownStatus(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_RTT Number restarted Treatment during the reporting period
     * @return the indicator
     */
    public CohortIndicator txRTT() {
        return cohortIndicator("Number restarted Treatment during the reporting period", ReportUtils.<CohortDefinition>map(datimCohorts.txRTT(),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * TX_RET Number of pregnant women who are still alive and on treatment at 12 months after initiating ART
     * @return the indicator
     */
    public CohortIndicator alivePregnantOnARTLast12Months() {
        return cohortIndicator("Alive, Pregnant and on ART for last 12 months", ReportUtils.<CohortDefinition>map(datimCohorts.pregnantAliveOnARTLast12Months(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_RET Number of breastfeeding mothers who are still alive and on treatment at 12 months after initiating ART
     * @return the indicator
     */
    public CohortIndicator aliveBfOnARTLast12Months() {
        return cohortIndicator("Alive, Breastfeeding and on ART for last 12 months", ReportUtils.<CohortDefinition>map(datimCohorts.bfAliveOnARTLast12Months(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_RET Number of adults and children who are still alive and on treatment at 12 months after initiating ART Disaggregated by age / sex
     * @return the indicator
     */
    public CohortIndicator aliveOnlyOnARTInLast12MonthsByAgeSex() {
        return cohortIndicator("Alive on ART in last 12 months by Age / Sex", ReportUtils.<CohortDefinition>map(datimCohorts.aliveOnARTInLast12MonthsByAgeSex(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_RET Denominator Started ART last 12 months and breastfeeding includes dead, stopped, lost follow-up
     * @return the indicator
     */
    public CohortIndicator totalBFStartedARTLast12Months() {
        return cohortIndicator("Total started ART in last 12 months and Breastfeeding", ReportUtils.<CohortDefinition>map(datimCohorts.breastfeedingAndstartedARTinLast12Months(),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * TX_RET Denominator Started ART last 12 months and Pregnant includes dead, stopped, lost follow-up
     * @return the indicator
     */
    public CohortIndicator totalPregnantStartedARTLast12Months() {
        return cohortIndicator("Total started ART in last 12 months and Pregnant", ReportUtils.<CohortDefinition>map(datimCohorts.pregnantAndstartedARTinLast12Months(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_RET (Denominator) All started ART last 12 months disaggregated by Age/sex includes dead, stopped, lost follow-up
     * @return the indicator
     */
    public CohortIndicator allOnARTLast12MonthsByAgeSex() {
        return cohortIndicator("Total on ART in last 12 months by Age / Sex", ReportUtils.<CohortDefinition>map(datimCohorts.totalOnARTLast12MonthsByAgeSex(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_PVLS Number of adults and pediatric patients on ART with suppressed Routine viral load results (<1,000 copies/ml) results within the past 12 months
     * @return the indicator
     */
    public CohortIndicator onARTSuppRoutineVLLast12Months() {
        return cohortIndicator("Patients on ART with Suppressed routine VL within last 12 Months", ReportUtils.<CohortDefinition>map(datimCohorts.onARTWithSuppressedRoutineVLLast12Months(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_PVLS Number of adults and pediatric patients on ART with suppressed Targeted viral load results (<1,000 copies/ml) results within the past 12 months
     * @return the indicator
     */
    public CohortIndicator onARTSuppTargetedVLLast12Months() {
        return cohortIndicator("Patients on ART with Suppressed targeted VL within last 12 Months", ReportUtils.<CohortDefinition>map(datimCohorts.onARTWithSuppressedTargetedVLLast12Months(),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * TX_PVLS Number of adults and pediatric patients on ART with suppressed undocumented viral load results (<1,000 copies/ml) results within the past 12 months
     * @return the indicator
     */
    public CohortIndicator onARTSuppUndocumentedVLLast12Months() {
        return cohortIndicator("Patients on ART with SuppresbfOnARTWithSuppressedTargetedVLLast12Monthssed undocumented VL within last 12 Months", ReportUtils.<CohortDefinition>map(datimCohorts.onARTWithSuppressedUndocumentedVLLast12Months(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_PVLS Number pregnant women on ART with suppressed viral load results (<1,000 copies/ml) within the past 12 months.
     */
    public CohortIndicator pregnantAndBFOnARTWithSuppressedVLLast12Months(String testType) {
        return cohortIndicator("Pregnant Women on ART with Suppressed VL within last 12 Months", ReportUtils.<CohortDefinition>map(datimCohorts.pregnantAndBFOnARTWithSuppressedVLLast12Months(testType),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * TX_PVLS Number pregnant women on ART with suppressed targeted viral load results (<1,000 copies/ml) within the past 12 months.
     */
    public CohortIndicator pregnantOnARTSuppTargetedVLLast12Months() {
        return cohortIndicator("Pregnant Women on ART with Suppressed targeted VL within last 12 Months", ReportUtils.<CohortDefinition>map(datimCohorts.pregnantOnARTWithSuppressedTargetedVLLast12Months(),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * TX_PVLS Number pregnant women with Undocumented ART with suppressed viral load results (<1,000 copies/ml) within the past 12 months.
     */
    public CohortIndicator kpOnARTSuppVLLast12Months(String testType,KPTypeDataDefinition kpType) {
        return cohortIndicator("Pregnant Women on ART with Suppressed undocumented VL within last 12 Months", ReportUtils.<CohortDefinition>map(datimCohorts.kpOnARTSuppVLLast12Months(testType,kpType),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_PVLS Number of Patients on Routine ART test with suppressed viral load results (<1,000 copies/ml) within the past 12 months Disaggregated by Sex/Age
     */
    public CohortIndicator onARTSuppVLAgeSex(String testType) {
        return cohortIndicator("Total on ART in last 12 months by Age / Sex", ReportUtils.<CohortDefinition>map(datimCohorts.onARTSuppVLAgeSex(testType),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_PVLS  Breastfeeding women on ART with viral load results within the past 12 months.
     */
    public CohortIndicator breastfeedingOnARTVLLast12Months(String testType) {
        return cohortIndicator("Breastfeeding Women on ART with VL within last 12 Months", ReportUtils.<CohortDefinition>map(datimCohorts.breastfeedingOnARTVLLast12Months(testType),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_PVLS KP on ART with viral load results within the past 12 months.
     */
    public CohortIndicator kpWithVLLast12Months(String testType,KPTypeDataDefinition kpType) {
        return cohortIndicator("KP on ART with VL within last 12 Months", ReportUtils.<CohortDefinition>map(datimCohorts.kpWithVLLast12Months(testType,kpType),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_PVLS Number of Patients on ART with viral load test within the past 12 months Disaggregated by Sex/Age
     */
    public CohortIndicator onARTAndVLLast12MonthsbyAgeSex(String testType) {
        return cohortIndicator("On ART with  VL within last 12 Months by sex/age", ReportUtils.<CohortDefinition>map(datimCohorts.onARTAndVLLast12MonthsbyAgeSex(testType),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_ML Number of ART patients with no clinical contact since their last expected contact
     */
    public CohortIndicator txML() {
        return cohortIndicator("Number of ART patients with no clinical contact since their last expected contact", ReportUtils.<CohortDefinition>map(datimCohorts.txML(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_ML_DIED Number of ART patients with no clinical contact since their last expected contact due to Death (confirmed)
     */
    public CohortIndicator txMlDied() {
        return cohortIndicator("ART patients with no clinical contact since their last expected contact due to death", ReportUtils.<CohortDefinition>map(datimCohorts.txMlDied(),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * TX_ML LTFU ON DRUGS <3 MONTHS Number of ART patients with no clinical contact since their last expected contact and have been on drugs for less than 3 months
     */
    public CohortIndicator txMLLTFUonDrugsUnder3Months() {
        return cohortIndicator("LTFU patients who have been on drugs for less than 3 months", ReportUtils.<CohortDefinition>map(datimCohorts.txMLLTFUonDrugsUnder3Months(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_ML LTFU ON DRUGS >3 MONTHS Number of ART patients with no clinical contact since their last expected contact and have been on drugs for more than 3 months
     */
    public CohortIndicator txMLLTFUonDrugsOver3Months() {
        return cohortIndicator("LTFU patients who have been on drugs for more than 3 months", ReportUtils.<CohortDefinition>map(datimCohorts.txMLLTFUonDrugsOver3Months(),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * TX_ML_DIED_TB Number of ART patients with no clinical contact since their last expected contact due to Death (confirmed) as a result of TB
     */
    public CohortIndicator onARTMissedAppointmentDiedTB() {
        return cohortIndicator("ART patients with no clinical contact since their last expected contact due to death as a result of TB", ReportUtils.<CohortDefinition>map(datimCohorts.onARTMissedAppointmentDiedTB(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_ML_DIED_CANCER Number of ART patients with no clinical contact since their last expected contact due to Death (confirmed) as a result of Cancer
     */
    public CohortIndicator onARTMissedAppointmentDiedCancer() {
        return cohortIndicator("ART patients with no clinical contact since their last expected contact due to death as a result of Cancer", ReportUtils.<CohortDefinition>map(datimCohorts.onARTMissedAppointmentDiedCancer(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_ML_DIED_OTHER_INFECTIOUS_DISEASE Number of ART patients with no clinical contact since their last expected contact due to Death (confirmed) as a result of other infectious disease
     */
    public CohortIndicator onARTMissedAppointmentDiedOtherInfectious() {
        return cohortIndicator("ART patients with no clinical contact since their last expected contact due to death as a result of other Infectious disease", ReportUtils.<CohortDefinition>map(datimCohorts.onARTMissedAppointmentDiedOtherInfectious(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_ML_DIED_OTHER_DISEASE Number of ART patients with no clinical contact since their last expected contact due to Death (confirmed) as a result of other disease/condition
     */
    public CohortIndicator onARTMissedAppointmentDiedOtherDisease() {
        return cohortIndicator("ART patients with no clinical contact since their last expected contact due to death as a result of other Infectious disease", ReportUtils.<CohortDefinition>map(datimCohorts.onARTMissedAppointmentDiedOtherDisease(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_ML_DIED_NATURAL Number of ART patients with no clinical contact since their last expected contact due to Death (confirmed) as a result of natural causes
     */
    public CohortIndicator onARTMissedAppointmentDiedNatural() {
        return cohortIndicator("ART patients with no clinical contact since their last expected contact due to death as a result of natural causes", ReportUtils.<CohortDefinition>map(datimCohorts.onARTMissedAppointmentDiedNatural(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_ML_DIED_NONNATURAL Number of ART patients with no clinical contact since their last expected contact due to Death (confirmed) as a result of non-natural causes
     */
    public CohortIndicator onARTMissedAppointmentDiedNonNatural() {
        return cohortIndicator("ART patients with no clinical contact since their last expected contact due to death as a result of non-natural causes", ReportUtils.<CohortDefinition>map(datimCohorts.onARTMissedAppointmentDiedNonNatural(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_ML_DIED_UNKNOWN Number of ART patients with no clinical contact since their last expected contact due to Death (confirmed) as a result of unknown causes
     */
    public CohortIndicator onARTMissedAppointmentDiedUnknown() {
        return cohortIndicator("ART patients with no clinical contact since their last expected contact due to death as a result of unknown causes", ReportUtils.<CohortDefinition>map(datimCohorts.onARTMissedAppointmentDiedUnknown(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_ML_PREV_UNDOCUMENTED_TRF Number of ART patients with no clinical contact since their last expected contact due to Previously undocumented transfer
     */
    public CohortIndicator txMLTrfOut() {
        return cohortIndicator("ART patients with no clinical contact since their last expected contact due to undocumented transfer", ReportUtils.<CohortDefinition>map(datimCohorts.txMLTrfOut(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_ML_STOPPED_TREATMENT Number of ART patients with no clinical contact since their last expected contact because they stopped treatment
     */
    public CohortIndicator txMLStoppedTreatment() {
        return cohortIndicator("ART patients with no clinical contact since their last expected contact because they stopped treatment", ReportUtils.<CohortDefinition>map(datimCohorts.txMLStoppedTreatment(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_ML_TRACED_UNLOCATED Number of ART patients with no clinical contact since their last expected contact due to un-traceability
     */
    public CohortIndicator onARTMissedAppointmentUntraceable() {
        return cohortIndicator("ART patients with no clinical contact since their last expected contact and were untraceable", ReportUtils.<CohortDefinition>map(datimCohorts.onARTMissedAppointmentUntraceable(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * TX_ML_NO_TRACE_ATTEMPTED Number of ART patients with no clinical contact since their last expected contact with no tracing attempted
     */
    public CohortIndicator onARTMissedAppointmentNotTraced() {
        return cohortIndicator("Number of ART patients with no clinical contact since their last expected contact and no tracing attempted", ReportUtils.<CohortDefinition>map(datimCohorts.onARTMissedAppointmentNotTraced(),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * HTS_INDEX_OFFERED Number of individuals who were offered index testing services
     */
    public CohortIndicator offeredIndexServices() {
        return cohortIndicator("Number of individuals who were offered Index testing services", ReportUtils.<CohortDefinition>map(datimCohorts.offeredIndexServices(),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * HTS_INDEX_CONTACTS_ELICITED
     */
    public CohortIndicator htsIndexContactsElicited() {
        return cohortIndicator("Number of male contacts under 15 years elicited", ReportUtils.<CohortDefinition>map(datimCohorts.htsIndexContactsElicited(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * HTS_INDEX_ACCEPTED Number of individuals who were offered and accepted index testing services
     */
    public CohortIndicator acceptedIndexServices() {
        return cohortIndicator("Number of individuals who accepted Index testing services", ReportUtils.<CohortDefinition>map(datimCohorts.acceptedIndexServices(),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * HTS_INDEX_POSITIVE Number of individuals who were tested Positive using Index testing services
     */
    public CohortIndicator contactTestedPositive() {
        return cohortIndicator("Number of individuals who were tested Positive using Index testing services", ReportUtils.<CohortDefinition>map(datimCohorts.hivPositiveContact(),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * HTS_INDEX_NEGATIVE Number of individuals who were tested HIV Negative using Index testing services
     */
    public CohortIndicator contactTestedNegative() {
        return cohortIndicator("Number of individuals who were tested HIV Negative using Index testing services", ReportUtils.<CohortDefinition>map(datimCohorts.hivNegativeContact(),
                "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * HTS_INDEX_KNOWN_POSITIVE Known HIV Positive contacts
     */
    public CohortIndicator contactKnownPositive() {
        return cohortIndicator("Known HIV Positive contacts", ReportUtils.<CohortDefinition>map(datimCohorts.knownPositiveContact(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * HTS_RECENT Persons aged ≥15 years newly diagnosed with HIV-1 infection who have a test for recent infection
     */
    public CohortIndicator recentHIVInfections() {
        return cohortIndicator("Persons aged ≥15 years newly diagnosed with HIV-1 infection", ReportUtils.<CohortDefinition>map(datimCohorts.recentHIVInfections(),
                "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Newly enrolled into PrEP
     */

    public CohortIndicator newlyEnrolledInPrEP() {
        return cohortIndicator("Number of individuals who are newly enrolled in PrEP", ReportUtils.<CohortDefinition>map(datimCohorts.newlyEnrolledInPrEP(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Newly eonrolled to prep with a recent HIV Positive results within 3 months into enrolment
     */

    public CohortIndicator newlyEnrolledInPrEPHIVPos() {
        return cohortIndicator("Newly eonrolled to prep with a recent HIV Positive results within 3 months into enrolment", ReportUtils.<CohortDefinition>map(datimCohorts.newlyEnrolledInPrEPHIVPos(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Newly eonrolled to prep with a recent HIV negative results within 3 months into enrolment
     */

    public CohortIndicator newlyEnrolledInPrEPHIVNeg() {
        return cohortIndicator("Newly eonrolled to prep with a recent HIV negative results within 3 months into enrolment", ReportUtils.<CohortDefinition>map(datimCohorts.newlyEnrolledInPrEPHIVNeg(), "startDate=${startDate},endDate=${endDate}"));
    }
    /**
     * Newly enrolled into PrEP
     */

    public CohortIndicator currentlyEnrolledInPrEP() {
        return cohortIndicator("Number of individuals who are newly enrolled in PrEP", ReportUtils.<CohortDefinition>map(datimCohorts.currEnrolledInPrEP(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Previously enrolled on IPT and have completed during this reporting period
     */

    public CohortIndicator previouslyOnIPTCompleted() {
        return cohortIndicator("Number of individuals who were previously on IPT and have completed", ReportUtils.<CohortDefinition>map(datimCohorts.previouslyOnIPTandCompleted(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of beneficiaries served by PEPFAR OVC comprehensive programs for children and families affected by HIV
     */
    public CohortIndicator totalBeneficiaryOfOVCComprehensiveProgram() {
        return cohortIndicator("Number of beneficiaries served by  PEPFAR OVC Comprehensive program", ReportUtils.<CohortDefinition>map(datimCohorts.totalBeneficiaryOfOVCComprehensiveProgram(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of beneficiaries served by PEPFAR OVC Dreams programs for children and families affected by HIV
     */
    public CohortIndicator totalBeneficiaryOfOVCDreamsProgram() {
        return cohortIndicator("Number of beneficiaries served by  PEPFAR OVC Comprehensive program", ReportUtils.<CohortDefinition>map(datimCohorts.totalBeneficiaryOfOVCDreamsProgram(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of beneficiaries served by PEPFAR OVC preventive programs for children and families affected by HIV
     */
    public CohortIndicator totalBeneficiaryOfOVCPreventiveProgram() {
        return cohortIndicator("Number of beneficiaries served by  PEPFAR OVC preventive program", ReportUtils.<CohortDefinition>map(datimCohorts.totalBeneficiaryOfOVCPreventiveProgram(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of KPs received prevention services
     */
    public CohortIndicator kpPrev(String kpType) {
        return cohortIndicator("Number of KPs received prevention services",
                ReportUtils.map(datimCohorts.kpPrev(kpType), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of beneficiaries for Sexual violence (post-rape care)
     */
    public CohortIndicator sexualGBV() {
        return cohortIndicator("Number of beneficiaries for Sexual violence (post-rape care)", ReportUtils.<CohortDefinition>map(datimCohorts.sexualGBV(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of beneficiaries for Physical and/or emotional violence (other Post-GBV) care
     */
    public CohortIndicator physicalEmotionalGBV() {
        return cohortIndicator("Number of beneficiaries for Physical and/or emotional violence (other Post-GBV) care", ReportUtils.<CohortDefinition>map(datimCohorts.physicalEmotionalGBV(), "startDate=${startDate},endDate=${endDate}"));
    }

    /**
     * Number of beneficiaries for Post-exposure prophylaxis (PEP) Services
     */
    public CohortIndicator receivedPEP() {
        return cohortIndicator("Number of beneficiaries for post-exposure prophylaxis (PEP) Services", ReportUtils.<CohortDefinition>map(datimCohorts.receivedPEP(), "startDate=${startDate},endDate=${endDate}"));
    }

}
