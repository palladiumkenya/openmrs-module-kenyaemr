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

@Component
public class FmapIndicatorLibrary {

	@Autowired
	private FmapCohortLibrary fmapCohortLibrary;

	//Indicator Libraries

	public CohortIndicator patientsOnSpecificRegimen(String regimenName, String regimenLine, String ageGroup, String pmtct) {
		return cohortIndicator("TDF+3TC+EFV",map(fmapCohortLibrary.txCurrpatientOnSpecificRegimenAndRegimenLine(regimenName,regimenLine, ageGroup, pmtct), "startDate=${startDate},endDate=${endDate}")
		);
	}

	public CohortIndicator patientsOnOtherRegimen(String regimenName, String regimenLine, String ageGroup, String pmtct) {
		return cohortIndicator("TDF+3TC+EFV",map(fmapCohortLibrary.txCurrpatientOnAnyOtherRegimenandRegimenLine(regimenName,regimenLine, ageGroup, pmtct), "startDate=${startDate},endDate=${endDate}")
		);
	}

	public CohortIndicator pmtctPatientsRegimen(String regimenName, String regimenLine, String pmtct) {
		return cohortIndicator("TDF+3TC+EFV",map(fmapCohortLibrary.txCurrPmtctPatientOnSpecificRegimen(regimenName, regimenLine, pmtct), "startDate=${startDate},endDate=${endDate}")
		);
	}

	public CohortIndicator pmtctPatientsOnOtherRegimen(String regimenName, String regimenLine, String pmtct) {
		return cohortIndicator("TDF+3TC+EFV",map(fmapCohortLibrary.txCurrPmtctPatientOnAnyOtherRegimen(regimenName, regimenLine, pmtct), "startDate=${startDate},endDate=${endDate}")
		);
	}

	public CohortIndicator peadPatientsOnSpecificRegimen(String regimenName,String regimenLine,String ageGroup,String pmtct, String minAge, String maxAge) {
		return cohortIndicator("TDF+3TC+EFV",map(fmapCohortLibrary.txCurrPeadOnSpecificRegimenAndRegimenLine(regimenName,regimenLine,ageGroup,pmtct, minAge,maxAge), "startDate=${startDate},endDate=${endDate}")
		);
	}


}
