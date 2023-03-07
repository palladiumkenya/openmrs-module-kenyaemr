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

import org.openmrs.module.kenyaemr.Metadata;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.metadatadeploy.bundle.Requires;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.*;

/**
 * SARI metadata bundle
 */
@Component
@Requires({ CommonMetadata.class })
public class SARIMetadata extends AbstractMetadataBundle {
	public static final class _EncounterType {
		public static final String SARI_ENROLLMENT = "eccd0ba3-1cde-4c6f-9a0d-d4d4d6ca41cb";
		public static final String SARI_EXIT = "ba7c9ff0-7c5d-4150-9094-30bbd718267b";
		public static final String SARI_FOLLOW_UP = "9b5307a4-0f20-4e20-998d-2f82461f6262";
		public static final String SARI_ILI_SCREENING = "97f73bf8-e087-4cfb-b811-b3df8e92d4cf";
	}

	public static final class _Form {
		public static final String SARI_ENROLLMENT_FORM = "1bdadb8f-a01a-4c6f-9174-25e85bcd73cb";
		public static final String SARI_EXIT_FORM = "617e91eb-d900-4904-b9be-08bd06707173";
		public static final String SARI_FOLLOW_UP_FORM = "cc3a9c9a-55f6-4e78-bc13-104cf91f62d3";
		public static final String SARI_ILI_SCREENING_FORM = "8dc7f6c1-4316-4818-9e65-0db7b991f19e";

	}
	public static final class _Program {
		public static final String SARI = Metadata.Program.SARI;
	}
	public static final class _Concept {
		public static final String SARI = "165933AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	}
	/**
	 * @see AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {
		install(encounterType("SARI Enrollment", "Enrollment onto SARI program", _EncounterType.SARI_ENROLLMENT));
		install(encounterType("SARI Exit", "Exit from SARI program", _EncounterType.SARI_EXIT));
		install(encounterType("SARI Follow up", "Follow up into SARI program", _EncounterType.SARI_FOLLOW_UP));
		install(encounterType("ILI Screening", "ILI Screening into SARI program", _EncounterType.SARI_ILI_SCREENING));

		install(form("SARI Enrollment Form", null, _EncounterType.SARI_ENROLLMENT, "1", _Form.SARI_ENROLLMENT_FORM));
		install(form("SARI Exit Form", null, _EncounterType.SARI_EXIT, "1", _Form.SARI_EXIT_FORM));
		install(form("SARI Followup Form", null, _EncounterType.SARI_FOLLOW_UP, "1", _Form.SARI_FOLLOW_UP_FORM));
		install(form("ILI Screening Form", null, _EncounterType.SARI_ILI_SCREENING, "1", _Form.SARI_ILI_SCREENING_FORM));

		//Installing identifiers
		install(program("SARI", "SARI program", _Concept.SARI, _Program.SARI));


	}
}