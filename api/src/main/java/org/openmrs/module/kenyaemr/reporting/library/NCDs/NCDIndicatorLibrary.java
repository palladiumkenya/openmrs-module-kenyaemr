/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.NCDs;

import org.openmrs.module.kenyaemr.reporting.MohReportUtils.ReportingUtils;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.openmrs.module.reporting.indicator.CohortIndicator;

import static org.openmrs.module.kenyaemr.reporting.MohReportUtils.ReportingUtils.cohortIndicator;

@Component
public class NCDIndicatorLibrary {
    @Autowired
    private NCDCohortLibrary ncdCohortLibrary;


    private final String defaultParams = "startDate=${startDate},endDate=${endDate},formUuids=${formUuids}";

    public CohortIndicator serialNumber() {
        return cohortIndicator("Serial No", ReportUtils.map(ncdCohortLibrary.allNcdPatients(), defaultParams));
    }

    public CohortIndicator visitDate() {
        return cohortIndicator("Visit Date", ReportUtils.map(ncdCohortLibrary.hasVisitDate(), defaultParams));
    }
    public CohortIndicator countyCode() {
        return cohortIndicator("County Code", ReportUtils.map(ncdCohortLibrary.allNcdPatients(), defaultParams));
    }
    public CohortIndicator mflCode() {
        return cohortIndicator("MFL Code", ReportUtils.map(ncdCohortLibrary.allNcdPatients(), defaultParams));
    }

    public CohortIndicator patientNumber() {
        return cohortIndicator("Patient No", ReportUtils.map(ncdCohortLibrary.hasPatientNumber(), defaultParams));
    }

    public CohortIndicator patientName() {
        return cohortIndicator("Name", ReportUtils.map(ncdCohortLibrary.allNcdPatients(), defaultParams));
    }

    public CohortIndicator DOB() {
        return cohortIndicator("Date of Birth ", ReportUtils.map(ncdCohortLibrary.hasDateOfBirth(), defaultParams));
    }

    public CohortIndicator sex() {
        return cohortIndicator("Sex", ReportUtils.map(ncdCohortLibrary.hasGender(), defaultParams));
    }

    public CohortIndicator telephone() {
        return cohortIndicator("Telephone", ReportUtils.map(ncdCohortLibrary.hasPhoneNumber(), defaultParams));
    }
    public CohortIndicator subcounty() {
        return cohortIndicator("Sub County", ReportUtils.map(ncdCohortLibrary.allNcdPatients(), defaultParams));
    }
    public CohortIndicator vilage() {
        return cohortIndicator("Village", ReportUtils.map(ncdCohortLibrary.hasVisitDate(), defaultParams));
    }

    public CohortIndicator landmark() {
        return cohortIndicator("Landmark", ReportUtils.map(ncdCohortLibrary.allNcdPatients(), defaultParams));
    }

    public CohortIndicator treatmentSupporter() {
        return cohortIndicator("Treatment Supporter", ReportUtils.map(ncdCohortLibrary.allNcdPatients(), defaultParams));
    }

    public CohortIndicator diagnosis() {
        return cohortIndicator("Diagnosis", ReportUtils.map(ncdCohortLibrary.allNcdPatients(), defaultParams));
    }
    public CohortIndicator yearOfDiagnosis() {
        return cohortIndicator("Year of Diagnosis", ReportUtils.map(ncdCohortLibrary.allNcdPatients(), defaultParams));
    }
    public CohortIndicator complications() {
        return cohortIndicator("Complications", ReportUtils.map(ncdCohortLibrary.allNcdPatients(), defaultParams));
    }
    public CohortIndicator treatment() {
        return cohortIndicator("Treatment", ReportUtils.map(ncdCohortLibrary.allNcdPatients(), defaultParams));
    }
    public CohortIndicator patiententStatus() {
        return cohortIndicator("Patient Status", ReportUtils.map(ncdCohortLibrary.allNcdPatients(), defaultParams));
    }
    public CohortIndicator SHIF() {
        return cohortIndicator("SHIF", ReportUtils.map(ncdCohortLibrary.hasShifNumber(), defaultParams));
    }

    public CohortIndicator Remarks() {
        return cohortIndicator("Remarks", ReportUtils.map(ncdCohortLibrary.allNcdPatients(), defaultParams));
    }


}
