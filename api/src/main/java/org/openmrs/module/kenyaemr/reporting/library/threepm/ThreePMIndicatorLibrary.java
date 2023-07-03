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
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimCohortLibrary;
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
    @Autowired
    private DatimCohortLibrary datimCohortLibrary;

    public CohortIndicator htsScreenedMobile(Integer department) {
        return cohortIndicator("Number screened",
                map(threePMCohorts.htsScreenedMobile(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsRetestingHivTestingAtPNC(Integer department) {
        return cohortIndicator("Client Hiv Retesting at PNC",
                map(threePMCohorts.htsRetestingHivTestingAtPNC(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsTbLinked(Integer department) {
        return cohortIndicator("Tb Linked",
                map(threePMCohorts.htsTbLinked(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsFirstANCKnownPositive(Integer department) {
        return cohortIndicator("First ANC Known Positive",
                map(threePMCohorts.htsFirstANCKnownPositive(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsFirstANCRecentKnownNegative(Integer entryPoint) {
        return cohortIndicator("First ANC Recent Known Negative",
                map(threePMCohorts.htsFirstANCRecentKnownNegative(entryPoint), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsNewlyTestedPositivePmtctANC(Integer entryPoint) {
        return cohortIndicator("Newly identified positive",
                map(threePMCohorts.htsNewlyTestedPositivePmtctANC(entryPoint), "startDate=${startDate},endDate=${endDate}")
        );
    }


    public CohortIndicator htsTestedPositivePostANC(Integer department) {
        return cohortIndicator("Tested Positive Post ANC1",
                map(threePMCohorts.htsTestedPositivePostANC(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator unKnownStatusAtANC(Integer department) {
        return cohortIndicator("Unknown status at ANC1",
                map(threePMCohorts.unKnownStatusAtANC(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator patientStoppedTreatment() {
        return cohortIndicator("Patient stopped treatment",
                map(datimCohortLibrary.patientStoppedTreatment(), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsInitialHivTestingatPNC(Integer department) {
        return cohortIndicator("Initial Tested Positive at PNC",
                map(threePMCohorts.htsInitialHivTestingatPNC(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsInitialTestingANC(Integer department) {
        return cohortIndicator("Initial Testing  ANC1",
                map(threePMCohorts.htsInitialTestingANC(department), "startDate=${startDate},endDate=${endDate}")
        );
    }

    public CohortIndicator htsFirstANCVisitMchmsAntenatal(Integer department) {
        return cohortIndicator("First ANC Visit",
                map(threePMCohorts.htsFirstANCVisitMchmsAntenatal(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsInitialTestingAtPNC(Integer department) {
        return cohortIndicator("Initial Testing at PNC",
                map(threePMCohorts.htsTestedByDepartment(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsRetestingAtPNC(Integer department) {
        return cohortIndicator(" Retesting at PNC",
                map(threePMCohorts.htsRetestedDepartment(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator seenANCVisitMchmsAntenatal(Integer department) {
        return cohortIndicator("Seen at ANC Visit",
                map(threePMCohorts.seenANCVisitMchmsAntenatal(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsPositiveTestANC(Integer department) {
        return cohortIndicator("Positive Test at ANC",
                map(threePMCohorts.htsPositiveTestANC(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsHAARTInANC(Integer department) {
        return cohortIndicator("ANC visit clients - started on HAART",
                map(threePMCohorts.htsHAARTInANC(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator initialTestANC(Integer department) {
        return cohortIndicator("ANC clients Test",
                map(threePMCohorts.initialTestANC(department), "startDate=${startDate},endDate=${endDate}")
        );
    }

   public CohortIndicator htsNegativeTestANCCohortDefinition(Integer department) {
        return cohortIndicator("Negative Test at ANC",
                map(threePMCohorts.htsNegativeTestANCCohortDefinition(department), "startDate=${startDate},endDate=${endDate}")
        );
    }

    public CohortIndicator htsVctTested(Integer department) {
        return cohortIndicator("VCT tested",
                map(threePMCohorts.htsVctTested(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator knownPositiveTest(Integer department) {
        return cohortIndicator("Newly identified positive",
                map(threePMCohorts.knownPositiveTest(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsIPDEligibleForTest(Integer department) {
        return cohortIndicator("IPD HP Eligible",
                map(threePMCohorts.htsIPDEligibleForTest(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator IPDknownPositiveTest(Integer department) {
        return cohortIndicator("IPD HP Known Positive",
                map(threePMCohorts.IPDknownPositiveTest(department), "startDate=${startDate},endDate=${endDate}")
        );
    }

    public CohortIndicator htsIpdHPLinked(Integer department) {
        return cohortIndicator("IPD HP Linked ",
                map(threePMCohorts.htsIpdHPLinked(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsIPDHPTestedDone(Integer department) {
        return cohortIndicator("IPD HP Tested ",
                map(threePMCohorts.htsIPDHPTestedDone(department), "startDate=${startDate},endDate=${endDate}")
        );
    }

    public CohortIndicator htsIpdHPNewPositive(Integer department) {
        return cohortIndicator("IPD HP Newly  Positive ",
                map(threePMCohorts.htsIpdHPNewPositive(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsIPDHPScreenedTest(Integer department) {
        return cohortIndicator("IPD HP Screened",
                map(threePMCohorts.htsIPDHPScreenedTest(department), "startDate=${startDate},endDate=${endDate}")
        );
    }

    public CohortIndicator htsScreenedTest(Integer department) {
        return cohortIndicator("screened",
                map(threePMCohorts.htsScreenedTest(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsTestedDone(Integer department) {
        return cohortIndicator("Tested",
                map(threePMCohorts.htsTestedDone(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
    public CohortIndicator htsLinked(Integer department) {
        return cohortIndicator("Linked",
                map(threePMCohorts.htsLinked(department), "startDate=${startDate},endDate=${endDate}")
        );
    }


    public CohortIndicator htsEligibleForTest(Integer department) {
        return cohortIndicator("Eligible for TB Test",
                map(threePMCohorts.htsEligibleForTest(department), "startDate=${startDate},endDate=${endDate}")
        );
    }
//    public CohortIndicator knownTbPositive(Integer department) {
//        return cohortIndicator("TB Known Positive",
//                map(threePMCohorts.knownTbPositive(department), "startDate=${startDate},endDate=${endDate}")
//        );
//    }

//    public CohortIndicator htsScreenedANC1(Integer department) {
//        return cohortIndicator("Screened for HIV Test at ANC 1",
//                map(threePMCohorts.htsScreenedANC(department), "startDate=${startDate},endDate=${endDate}")
//        );
//    }
    public CohortIndicator htsTested(Integer department) {
        return cohortIndicator("Individuals tested", map(threePMCohorts.htsTested(department), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator htsKnownPositive(Integer department) {
        return cohortIndicator("Known Positive from HTS mobile screening", map(threePMCohorts.htsKnownPositive(department), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator htsNewPositiveMobile(Integer department) {
        return cohortIndicator("Newly tested positive", map(threePMCohorts.htsNewPositiveMobile(department), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator htsLinkedToHAARTMobile(Integer department) {
        return cohortIndicator("Linked to care", map(threePMCohorts.htsLinkedToHAARTMobile(department), "startDate=${startDate},endDate=${endDate}"));
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
    public CohortIndicator snsHivPositive(String testStrategy ) {
        return cohortIndicator("SNS: Number HIV Positive", map(threePMCohorts.snsHivPositive(testStrategy), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator snsPopulationTypeHivPositive(Integer testStrategy ) {
        return cohortIndicator("SNS: Number HIV Positive  Population", map(threePMCohorts.htsPopulationTypeScreened(testStrategy), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator htsPopulationTypeLinked(Integer testStrategy ) {
        return cohortIndicator("SNS: Linked to ART Population", map(threePMCohorts.htsPopulationTypeLinked(testStrategy), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator htsPopulationTypeTested(Integer testStrategy ) {
        return cohortIndicator("SNS: Tested Population", map(threePMCohorts.htsPopulationTypeTested(testStrategy), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator snsLinked(String testStrategy ) {
        return cohortIndicator("SNS: Linked to ART ", map(threePMCohorts.snsLinked(testStrategy), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator snsTested(String testStrategy ) {
        return cohortIndicator("SNS: Tested", map(threePMCohorts.snsTested(testStrategy), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator snsHivNegative(String testStrategy ) {
        return cohortIndicator("SNS: SNS_SEED_NEGATIVE", map(threePMCohorts.snsHivNegative(testStrategy), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator htsPopulationTypeNegativeClient(Integer testStrategy ) {
        return cohortIndicator("SNS: Newly Tested Negative Population", map(threePMCohorts.htsPopulationTypeNegativeClient(testStrategy), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientsLTFU( ) {
        return cohortIndicator("Patient Lost to follow", map(threePMCohorts.patientsLTFU(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientDied( ) {
        return cohortIndicator("Patient Died", map(threePMCohorts.patientDied(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator transferOut( ) {
        return cohortIndicator("Patient transfer out", map(threePMCohorts.transferOut(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator transferIn( ) {
        return cohortIndicator("Patient transfer in", map(threePMCohorts.transferIn(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator viralSuppressionBetween400To900( Integer vlLess,Integer vlMore, Integer months) {
        return cohortIndicator("viral Suppression Between 400 To 900", map(threePMCohorts.viralSuppressionBetween400To900(vlLess,vlMore,months), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientWithValidVLLess200WithLDL(Integer vl) {
        return cohortIndicator("viral Suppression Less With LDL", map(threePMCohorts.patientWithValidVWithLDL(vl), "startDate=${startDate},endDate=${endDate}"));
    }

    public CohortIndicator patientWithChronicillness(Integer chronicIllness) {
        return cohortIndicator("viral Suppression More than 1000", map(threePMCohorts.patientWithChronicillness(chronicIllness), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator patientEligibleForVl(Integer months) {
        return cohortIndicator("Eligible for vl", map(threePMCohorts.patientEligibleForVl(months), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator ncdPatient() {
        return cohortIndicator("NCD Patient", map(threePMCohorts.ncdPatient(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator newOnTreatment() {
        return cohortIndicator("New on Treatment", map(datimCohortLibrary.newOnARTAndNotTxCur(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator OnDTGRegimen() {
        return cohortIndicator("New on Treatment", map(threePMCohorts.OnDTGRegimen(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator ppPrevByType(String kpType) {
        return cohortIndicator("KP type", map(threePMCohorts.ppPrevByType(kpType), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator pmtcTested() {
        return cohortIndicator("PMTC Tested", map(threePMCohorts.pmtcTested(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator fnsEligibleForTest(Integer testing) {
        return cohortIndicator("Eligible for testing", map(threePMCohorts.fnsEligibleForTest(testing), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator indexLinked(Integer testing) {
        return cohortIndicator("Linked for testing", map(threePMCohorts.indexLinked(testing), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator fnsTestestedfnsTestested() {
        return cohortIndicator("Tested", map(threePMCohorts.fnsTestestedfnsTestested(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator receivingSelfTestKits(String kpTest) {
        return cohortIndicator("Self Test Kit", map(threePMCohorts.receivingSelfTestKits(kpTest), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator receivingSelfTestKitsPositive(String kpTest) {
        return cohortIndicator("Self Test Kit Positive", map(threePMCohorts.receivingSelfTestKitsPositive(kpTest), "startDate=${startDate},endDate=${endDate}"));
    }

}

