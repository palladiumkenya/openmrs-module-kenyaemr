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
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.metadatadeploy.bundle.Requires;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.encounterType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.form;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.globalProperty;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.program;

/**
 * HIV metadata bundle
 */
@Component
@Requires({ CommonMetadata.class })
public class MATMetadata extends AbstractMetadataBundle {

	public static final class _Program {
		public static final String MAT = "4b898e20-9b2d-11ee-b9d1-0242ac120002";
	}
	public static final class _EncounterType {
		public static final String MAT_CLINICAL_ENCOUNTER = "c3518485-ee22-4a47-b6d4-6d0e8f297b02";
		public static final String MAT_CESSATION_ENCOUNTER = "b7addaab-bafd-445d-929d-d6631a0de63e";
		public static final String MAT_CLINICAL_ELIGIBILITY_ASSESSMENT_AND_REFERRAL_ENCOUNTER = "cb0ca111-6142-4d9b-a670-a32a64940cca";
		public static final String MAT_DISCONTINUATION_ENCOUNTER = "d36bd361-c155-4c3d-a44b-df99f4e111b3";
		public static final String MAT_INITIAL_REGISTRATION_ENCOUNTER = "b4b87535-90b7-4a26-b224-368576095f11";
		public static final String MAT_TREATMENT_ENCOUNTER = "9f49a7b8-367c-4a9f-a66e-9f697fc683fe";
		public static final String MAT_PSYCHIATRIC_INTAKE_AND_FOLLOWUP_ENCOUNTER = "fbf32c5d-e58e-4001-8330-3776c5691d6e";
		public static final String MAT_PSYCHO_SOCIAL_INTAKE_AND_FOLLOWUP_ENCOUNTER = "5ab99d58-b337-4796-94fb-101381b46618";
		public static final String MAT_TRANSIT_ENCOUNTER = "1d00fede-7491-485e-86a1-9a9c2bd772fa";
	}

	public static final class _Form {
		public static final String MAT_CESSATION = "fa58cbc1-91c8-4920-813b-fde7fd69533b";
		public static final String MAT_CLINICAL_ELIGIBILITY_ASSESSMENT_AND_REFERRAL = "7b470a63-4c20-453c-8d5d-d75a7bfb87d1";
		public static final String MAT_CLINICAL_ENCOUNTER = "5ed937a0-0933-41c3-b638-63d8a4779845";
		public static final String MAT_DISCONTINUATION = "38d6e116-b96c-4916-a821-b4dc83e2041d";
		public static final String MAT_INITIAL_REGISTRATION_FORM = "9a9cadd7-fba1-4a24-94aa-43edfbecf8d9";
		public static final String MAT_TREATMENT_FORM = "350d93cd-66da-4b7e-ae9a-fdfdc9195add";
		public static final String MAT_PSYCHIATRIC_INTAKE_AND_FOLLOWUP_FORM = "fdea46a1-9423-4ef9-b780-93b32b48a528";
		public static final String MAT_PSYCHO_SOCIAL_INTAKE_AND_FOLLOWUP_FORM = "cfd2109b-63b3-43de-8bb3-682e80c5a965";
		public static final String MAT_TRANSIT_FORM = "b9495048-eceb-4dd2-bfba-330dc4900ee9";
	}
	/**
	 * @see org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {

		install(program("MAT", "Methadone Assisted Therapy", Dictionary.MAT_PROGRAM, _Program.MAT));
		install(encounterType("MAT Clinical Encounter", "MAT Clinical Encounter", _EncounterType.MAT_CLINICAL_ENCOUNTER));
		install(encounterType("MAT Cessation Encounter", "MAT Cessation Encounter", _EncounterType.MAT_CESSATION_ENCOUNTER));
		install(encounterType("MAT Clinical eligibility assessment", "MAT Clinical Encounter", _EncounterType.MAT_CLINICAL_ELIGIBILITY_ASSESSMENT_AND_REFERRAL_ENCOUNTER));
		install(encounterType("MAT Discontinuation Encounter", "MAT Clinical Encounter", _EncounterType.MAT_DISCONTINUATION_ENCOUNTER));
		install(encounterType("MAT Initial registration Encounter", "MAT Clinical Encounter", _EncounterType.MAT_INITIAL_REGISTRATION_ENCOUNTER));
		install(encounterType("MAT Treatment Encounter", "MAT Clinical Encounter", _EncounterType.MAT_TREATMENT_ENCOUNTER));
		install(encounterType("MAT Psychiatric intake and followup", "MAT Clinical Encounter", _EncounterType.MAT_PSYCHIATRIC_INTAKE_AND_FOLLOWUP_ENCOUNTER));
		install(encounterType("MAT Psychosocial intake and followup", "MAT Clinical Encounter", _EncounterType.MAT_PSYCHO_SOCIAL_INTAKE_AND_FOLLOWUP_ENCOUNTER));
		install(encounterType("MAT Transit/Referral Encounter", "MAT Clinical Encounter", _EncounterType.MAT_TRANSIT_ENCOUNTER));

		install(form("MAT Clinical form", null, _EncounterType.MAT_CLINICAL_ENCOUNTER, "1", _Form.MAT_CLINICAL_ENCOUNTER));
		install(form("MAT Cessation form", null, _EncounterType.MAT_CESSATION_ENCOUNTER, "1", _Form.MAT_CESSATION));
		install(form("MAT Clinical eligibility assessment", null, _EncounterType.MAT_CLINICAL_ELIGIBILITY_ASSESSMENT_AND_REFERRAL_ENCOUNTER, "1", _Form.MAT_CLINICAL_ELIGIBILITY_ASSESSMENT_AND_REFERRAL));
		install(form("MAT Discontinuation form", null, _EncounterType.MAT_DISCONTINUATION_ENCOUNTER, "1", _Form.MAT_DISCONTINUATION));
		install(form("MAT Initial registration form", "", _EncounterType.MAT_INITIAL_REGISTRATION_ENCOUNTER, "1", _Form.MAT_INITIAL_REGISTRATION_FORM));
		install(form("MAT Treatment form", "", _EncounterType.MAT_TREATMENT_ENCOUNTER, "1", _Form.MAT_TREATMENT_FORM));
		install(form("MAT Psychiatric intake and followup form", null, _EncounterType.MAT_PSYCHIATRIC_INTAKE_AND_FOLLOWUP_ENCOUNTER, "1", _Form.MAT_PSYCHIATRIC_INTAKE_AND_FOLLOWUP_FORM));
		install(form("MAT Psychosocial intake and followup form", null, _EncounterType.MAT_PSYCHO_SOCIAL_INTAKE_AND_FOLLOWUP_ENCOUNTER, "1", _Form.MAT_PSYCHO_SOCIAL_INTAKE_AND_FOLLOWUP_FORM));
		install(form("MAT Transit/Referral form", null, _EncounterType.MAT_TRANSIT_ENCOUNTER, "1", _Form.MAT_TRANSIT_FORM));

	}
}