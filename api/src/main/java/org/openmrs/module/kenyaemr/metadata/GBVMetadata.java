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
 * GBV metadata bundle
 */
@Component
@Requires({ CommonMetadata.class })
public class GBVMetadata extends AbstractMetadataBundle {

	public static final class _Program {
		public static final String GBV = Metadata.Program.GBV;
	}
	public static final class _EncounterType {
		public static final String GBV_ENROLLMENT_ENCOUNTER = "998a55ca-f5af-461b-b4ec-3346220f0bcc";
		public static final String GBV_TRAUMA_COUNSELLING_ENCOUNTER = "61a5d2aa-1d34-4c84-9eb1-2714d7bd0d22";
		public static final String GBV_DISCONTINUATION_ENCOUNTER = "e6924edb-b5b5-441d-a2dc-fa940243bd66";
		public static final String GBV_COMMUNITY_LINKAGE_ENCOUNTER = "19a77c1b-efba-4070-af2b-5e21037b31bf";
		public static final String GBV_LEGAL_ENCOUNTER = "3c92b068-b362-4ae1-900d-bf26cca45d67";
		public static final String GBV_PERPETRATOR_DETAILS_ENCOUNTER = "1a290c2e-cb1c-4736-acdf-c12f809a0462";
		public static final String GBV_CONSENT_ENCOUNTER = "2715d976-f4a1-4fb1-adb4-accca540d610";
		public static final String PEP_MANAGEMENT_NON_OCN_ENCOUNTER = "6dd20d4e-d7c5-4c04-b648-373d2619b5be";
		public static final String PEP_MANAGEMENT_OCN_ENCOUNTER = "2feae60e-2cb8-47fc-83dc-24ccff32eb06";
		public static final String PEP_MANAGEMENT_SURVIVOR_ENCOUNTER = "7fee62dc-6252-40d4-b216-f459ac5bd54c";
		public static final String SGBV_POST_RAPE_CARE_PART_A_ENCOUNTER = "8396c582-7242-4eb7-a580-15fc0c3951ea";
		public static final String SGBV_PSYCHOLOGICAL_ASSESSMENT_PART_B_ENCOUNTER = "d459d923-4d6a-426e-a4ae-871608e12c5b";

	}

	public static final class _Form {
		public static final String GBV_ENROLLMENT_FORM = "461ca30e-a7f4-4dd9-9a7d-b6da63b92bdd";
		public static final String GBV_TRAUMA_COUNSELLING_FORM = "48c995d4-200d-4259-b2f7-d3b1b13f8c77";
		public static final String GBV_DISCONTINUATION_FORM = "4e563850-9c17-48fb-908e-5bbdce85f900";
		public static final String GBV_COMMUNITY_LINKAGE_FORM = "f3d7f7b9-af4d-4545-9834-102905fb8790";
		public static final String GBV_LEGAL_FORM = "60fa054e-7144-4671-8111-555f669ab285";
		public static final String GBV_PERPETRATOR_DETAILS_FORM = "c8add03a-8b3f-4e8e-9265-a55809af676e";
		public static final String GBV_CONSENT_FORM = "b3c4e545-b9b3-42f6-a41c-9758679af178";
		public static final String PEP_MANAGEMENT_NON_OCN_FORM = "70d46a54-d07c-4658-8ccd-feba6bc941ec";
		public static final String PEP_MANAGEMENT_OCN_FORM = "9f9dacfa-7952-4f9f-b191-1ff557ae2be4";
		public static final String PEP_MANAGEMENT_SURVIVOR_FORM = "f6539ca2-fa98-4007-8945-85cbf7d19cc3";
		public static final String SGBV_POST_RAPE_CARE_PART_A_FORM = "c3101e2e-ac1e-4b18-8ad1-0fe8a514cf8a";
		public static final String SGBV_PSYCHOLOGICAL_ASSESSMENT_PART_B_FORM = "c766da7b-efa3-45e5-b090-1ed3b4339cab";
	}
	/**
	 * @see AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {

		install(program("GBV", "Gender Based Violence Program", Dictionary.GBV_PROGRAM, _Program.GBV));
		install(encounterType("GBV Enrollment Encounter", "GBV Enrollment Encounter", _EncounterType.GBV_ENROLLMENT_ENCOUNTER));
		install(encounterType("GBV Trauma Counselling Encounter", "GBV Trauma Counselling Encounter", _EncounterType.GBV_TRAUMA_COUNSELLING_ENCOUNTER));
		install(encounterType("GBV Discontinuation Encounter", "GBV Discontinuation Encounter", _EncounterType.GBV_DISCONTINUATION_ENCOUNTER));
		install(encounterType("GBV Community Linkage Encounter", "GBV Community Linkage Encounter", _EncounterType.GBV_COMMUNITY_LINKAGE_ENCOUNTER));
		install(encounterType("GBV Legal Encounter", "GBV Legal Encounter", _EncounterType.GBV_LEGAL_ENCOUNTER));
		install(encounterType("GBV Perpetrator Details Encounter", "GBV Perpetrator Details Encounter", _EncounterType.GBV_PERPETRATOR_DETAILS_ENCOUNTER));
		install(encounterType("GBV Consent Encounter", "GBV Consent Encounter", _EncounterType.GBV_CONSENT_ENCOUNTER));
		install(encounterType("PEP Management Non OCN Encounter", "PEP Management Non OCN Encounter", _EncounterType.PEP_MANAGEMENT_NON_OCN_ENCOUNTER));
		install(encounterType("PEP Management OCN Encounter", "PEP Management OCN Encounter", _EncounterType.PEP_MANAGEMENT_OCN_ENCOUNTER));
		install(encounterType("PEP Management Survivor Encounter", "PEP Management Survivor Encounter", _EncounterType.PEP_MANAGEMENT_SURVIVOR_ENCOUNTER));
		install(encounterType("SGBV Post Rape Care 363A Encounter", "SGBV Post Rape Care 363A Encounter", _EncounterType.SGBV_POST_RAPE_CARE_PART_A_ENCOUNTER));
		install(encounterType("SGBV PRC Psychological Assessment 363B Encounter", "SGBV PRC Psychological Assessment 363B Encounter", _EncounterType.SGBV_PSYCHOLOGICAL_ASSESSMENT_PART_B_ENCOUNTER));

		install(form("GBV Enrollment Form", "Gender Based Violence Enrollment Form", _EncounterType.GBV_ENROLLMENT_ENCOUNTER, "1", _Form.GBV_ENROLLMENT_FORM));
		install(form("GBV Trauma Counselling Form", "Gender Based Violence Trauma Counselling Form", _EncounterType.GBV_TRAUMA_COUNSELLING_ENCOUNTER, "1", _Form.GBV_TRAUMA_COUNSELLING_FORM));
		install(form("GBV Discontinuation Form", "Gender Based Violence Discontinuation Form", _EncounterType.GBV_DISCONTINUATION_ENCOUNTER, "1", _Form.GBV_DISCONTINUATION_FORM));
		install(form("GBV Community Linkage Form", "Gender Based Violence Community Linkage Form", _EncounterType.GBV_COMMUNITY_LINKAGE_ENCOUNTER, "1", _Form.GBV_COMMUNITY_LINKAGE_FORM));
		install(form("GBV Legal Form", "Gender Based Violence Legal Form", _EncounterType.GBV_LEGAL_ENCOUNTER, "1", _Form.GBV_LEGAL_FORM));
		install(form("GBV Perpetrator Details Form", "Gender Based Violence Perpetrator Details Form", _EncounterType.GBV_PERPETRATOR_DETAILS_ENCOUNTER, "1", _Form.GBV_PERPETRATOR_DETAILS_FORM));
		install(form("GBV Consent Form", "Gender Based Violence Consent Form", _EncounterType.GBV_CONSENT_ENCOUNTER, "1", _Form.GBV_CONSENT_FORM));
		install(form("PEP Management Non OCN Form", "Gender Based Violence PEP Management Non OCN Form", _EncounterType.PEP_MANAGEMENT_NON_OCN_ENCOUNTER, "1", _Form.PEP_MANAGEMENT_NON_OCN_FORM));
		install(form("PEP Management OCN Form", "Post Exposure Prophylaxis Management OCN Form", _EncounterType.PEP_MANAGEMENT_OCN_ENCOUNTER, "1", _Form.PEP_MANAGEMENT_OCN_FORM));
		install(form("PEP Management Survivor Form", "Post Exposure Prophylaxis Management Survivor Form", _EncounterType.PEP_MANAGEMENT_SURVIVOR_ENCOUNTER, "1", _Form.PEP_MANAGEMENT_SURVIVOR_FORM));
		install(form("SGBV Post Rape Care 363A Form", "SGBV Post Rape Care 363A Form", _EncounterType.SGBV_POST_RAPE_CARE_PART_A_ENCOUNTER, "1", _Form.SGBV_POST_RAPE_CARE_PART_A_FORM));
		install(form("SGBV PRC Psychological Assessment 363B Form", "SGBV PRC Psychological Assessment 363B Form", _EncounterType.SGBV_PSYCHOLOGICAL_ASSESSMENT_PART_B_ENCOUNTER, "1", _Form.SGBV_PSYCHOLOGICAL_ASSESSMENT_PART_B_FORM));

	}
}
