/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.MOH505;

import org.openmrs.module.kenyaemr.reporting.library.MOH505.Moh505CohortLibrary;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

@Component
public class Moh505IndicatorLibrary {

    @Autowired
    private Moh505CohortLibrary Moh505Cohorts;

    public CohortIndicator AEFICase() {
        return cohortIndicator("AEFI", map(Moh505Cohorts.jaundiceCases(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator acuteJaundiceCase() {
        return cohortIndicator("Acute Jaundice", map(Moh505Cohorts.jaundiceCases(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator acuteMalnutritionCase(int acuteMalnutrition) {
        return cohortIndicator("Acute Malnutrition", map(Moh505Cohorts.malnutritionCase(acuteMalnutrition), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator poliomyelitisCase(int poliomyelitis) {
        return cohortIndicator("AFP (Poliomyelitis)", map(Moh505Cohorts.poliomyelitisCohortCase(poliomyelitis), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator anthraxCase(int anthrax) {
        return cohortIndicator("Anthrax", map(Moh505Cohorts.anthraxCohortCase(anthrax), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator choleraCase(int cholera) {
        return cohortIndicator("Cholera", map(Moh505Cohorts.choleraCohortCase(cholera), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator dengueCase(int dengue) {
        return cohortIndicator("Dengue", map(Moh505Cohorts.dengueCohortCase(dengue), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator dysenteryCase(int dysentery) {
        return cohortIndicator("Dysentery", map(Moh505Cohorts.dysenteryCohortCase(dysentery), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator guineaWormDiseaseCase(int guineaWorm) {
        return cohortIndicator("Guinea Worm Disease", map(Moh505Cohorts.guineaWormCohortCase(guineaWorm), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator measlesCase(int measles) {
        return cohortIndicator("Measles", map(Moh505Cohorts.measlesCohortCase(measles), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator suspectedMalariaCase(int suspectedMalaria) {
        return cohortIndicator("Suspected Malaria", map(Moh505Cohorts.suspectedMalariaCohortCase(suspectedMalaria), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator deathsDueToMalariaCase() {
        return cohortIndicator("Deaths due to Malaria", map(Moh505Cohorts.jaundiceCases(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator maternalDeathsCase() {
        return cohortIndicator("Maternal deaths", map(Moh505Cohorts.jaundiceCases(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator meningococcalMeningitisCase(int meningococcalMeningitis) {
        return cohortIndicator("Meningococcal Meningitis", map(Moh505Cohorts.meningococcalMeningitisCohortCase(meningococcalMeningitis), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator neonatalDeathsCase() {
        return cohortIndicator("Neonatal deaths", map(Moh505Cohorts.jaundiceCases(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator neonatalTetanusCase(int neonatalTetanus) {
        return cohortIndicator("Neonatal Tetanus", map(Moh505Cohorts.neonatalTetanusCohortCase(neonatalTetanus), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator plagueCase(int plague) {
        return cohortIndicator("Plague", map(Moh505Cohorts.plagueCohortCase(plague), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator rabiesCase(int rabies) {
        return cohortIndicator("Rabies", map(Moh505Cohorts.rabiesCohortCase(rabies), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator riftValleyFeverCase(int riftValleyFever) {
        return cohortIndicator("Rift Valley Fever", map(Moh505Cohorts.riftValleyFeverCohortCase(riftValleyFever), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator SARICase() {
        return cohortIndicator("SARI (Cluster ≥3 cases)", map(Moh505Cohorts.jaundiceCases(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator suspectedTBCase(int suspectedTB) {
        return cohortIndicator("Suspected MDR/XDR TB", map(Moh505Cohorts.suspectedTBCohortCase(suspectedTB), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator typhoidCase(int typhoid) {
        return cohortIndicator("Typhoid", map(Moh505Cohorts.typhoidCohortCase(typhoid), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator VHFCase() {
        return cohortIndicator("VHF", map(Moh505Cohorts.jaundiceCases(), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator yellowFeverCase(int yellowFever) {
        return cohortIndicator("Yellow Fever", map(Moh505Cohorts.yellowFeverCohortCase(yellowFever), "startDate=${startDate},endDate=${endDate}"));
    }
    public CohortIndicator otherCase() {
        return cohortIndicator("Others (Specify)", map(Moh505Cohorts.jaundiceCases(), "startDate=${startDate},endDate=${endDate}"));
    }

}


