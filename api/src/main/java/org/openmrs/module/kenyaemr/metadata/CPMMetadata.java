/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.metadata;

import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.Metadata;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.metadatadeploy.bundle.Requires;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.*;

/**
 * CPM metadata bundle
 */
@Component
@Requires({ CommonMetadata.class })
public class CPMMetadata extends AbstractMetadataBundle {

	public static final class _Program {
		public static final String CPM = Metadata.Program.CPM;		
	}
	public static final class _EncounterType {
		public static final String CPM_ENROLLMENT_ENCOUNTER = "e0700664-90b6-4480-aad2-6e0a00babd66";
		public static final String CPM_REFERRAL_ENCOUNTER = "c49952bf-218b-44b4-8d2a-1947cbf00fff";
		public static final String CPM_DISCONTINUATION_ENCOUNTER = "ddc73ecc-ad85-40bc-ad43-318ed9abfd00";
		public static final String CPM_SCREENING_ENCOUNTER = "f0b27e6c-57cd-4dec-ad6b-43eee6e571ee";

	}

	public static final class _Form {
		public static final String CPM_ENROLLMENT_FORM = "f01c67f7-2293-4a6a-b0f6-5db0fb5934dd";
		public static final String CPM_REFERRAL_FORM = "b1e1f6fe-1894-4d06-bd8c-2b87f7cd9577";
		public static final String CPM_DISCONTINUATION_FORM = "d1e7ebb4-afc0-412b-a98b-6720ab1169cc";
		public static final String CPM_SCREENING_FORM = "f7dbe6b6-2a5a-46e6-af52-45bf2962f4aa";
		public static final String CPM_INITIAL_FORM = "bf55e6ad-096d-4223-a2bb-2aa3e3495af2";
	}
	/**
	 * @see AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {

		install(program("CPM", "Community Pharmacy Model", Dictionary.CPM_PROGRAM, _Program.CPM));
		install(encounterType("CPM Enrollment Encounter", "CPM Enrollment Encounter", _EncounterType.CPM_ENROLLMENT_ENCOUNTER));
		install(encounterType("CPM Referral Encounter", "CPM Referral Encounter", _EncounterType.CPM_REFERRAL_ENCOUNTER));
		install(encounterType("CPM Discontinuation Encounter", "CPM Discontinuation Encounter", _EncounterType.CPM_DISCONTINUATION_ENCOUNTER));		
		install(encounterType("CPM Screening Encounter", "CPM Screening Encounter", _EncounterType.CPM_SCREENING_ENCOUNTER));		

		install(form("CPM Enrollment Form", "Community Pharmacy Model Enrollment form", _EncounterType.CPM_ENROLLMENT_ENCOUNTER, "1", _Form.CPM_ENROLLMENT_FORM));
		install(form("CPM Referral Form", "Community Pharmacy Model Referral form", _EncounterType.CPM_REFERRAL_ENCOUNTER, "1", _Form.CPM_REFERRAL_FORM));
		install(form("CPM Discontinuation Form", "Community Pharmacy Model Discontinuation  Form", _EncounterType.CPM_DISCONTINUATION_ENCOUNTER, "1", _Form.CPM_DISCONTINUATION_FORM));
		install(form("CPM Screening Form", "Community Pharmacy Model Screening  Form", _EncounterType.CPM_SCREENING_ENCOUNTER, "1", _Form.CPM_SCREENING_FORM));
		install(form("CPM Screening Form", "Community Pharmacy Model Initial  Form", _EncounterType.CPM_ENROLLMENT_ENCOUNTER, "1", _Form.CPM_INITIAL_FORM));
	
	}
}
