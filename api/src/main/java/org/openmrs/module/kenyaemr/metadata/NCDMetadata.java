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
 * OTZ metadata bundle
 */
@Component
@Requires({ CommonMetadata.class })
public class NCDMetadata extends AbstractMetadataBundle {

	public static final class _EncounterType {
		public static final String NCD_INITIAL = "dfcbe5d0-1afb-48a0-8f1e-5e5988b11f15";
		public static final String NCD_FOLLOWUP = "b402d094-bff3-4b31-b167-82426b4e3e28";
		public static final String NCD_DISCONTINUATION = "c31d1a11-f0a5-4a64-817d-25e5134f2e37";
	}

	public static final class _Form {
		public static final String NCD_INITIAL_FORM = "c4994dd7-f2b6-4c28-bdc7-8b1d9d2a6a97";
		public static final String NCD_FOLLOWUP_FORM = "3e1057da-f130-44d9-b2bb-53e039b953c6";
		public static final String NCD_DISCONTINUATION_FORM = "63182d28-a23f-4d14-b48e-38077bbd8ed2";
	}


	public static final class _Program {
		public static final String NCD = "ffee43c4-9ccd-4e55-8a70-93194e7fafc6";
	}

	public static final class _Concept {

		public static final String NCD = "a8085b61-45c0-41f0-b3f6-8a2d21715840";
	}

	/**
	 * @see AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {
		install(encounterType("NCD Initial", "Enrolment into NCD", _EncounterType.NCD_INITIAL));
		install(encounterType("NCD Followup", "NCD followup encounter", _EncounterType.NCD_FOLLOWUP));
		install(encounterType("NCD Discontinuation", "NCD program discontinuation encounter", _EncounterType.NCD_DISCONTINUATION));

		install(form("NCD Enrollment Form", null, _EncounterType.NCD_INITIAL, "1", _Form.NCD_INITIAL_FORM));
		install(form("NCD Followup Form", null, _EncounterType.NCD_FOLLOWUP, "1", _Form.NCD_FOLLOWUP_FORM));
		install(form("NCD Discontinuation Form", null, _EncounterType.NCD_DISCONTINUATION, "1", _Form.NCD_DISCONTINUATION_FORM));


		install(program("NCD", "NCD program", _Concept.NCD, _Program.NCD));


	}
}