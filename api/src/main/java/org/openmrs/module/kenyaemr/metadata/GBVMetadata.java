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

		public static final String GBV_TREATMENT = "e41c3d74-37c7-4001-9f19-ef9e35224b70";
	}

	public static final class _Concept {

		public static final String GBV_TREATMENT = "124824AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	}
	public static final class _EncounterType {
		public static final String GBV_ENROLLMENT_ENCOUNTER = "b3f8c498-7f17-44c7-993e-6cd981a5f420";
		public static final String GBV_TRAUMA_COUNSELLING_ENCOUNTER = "16048e65-24c6-4833-a342-29000143298f";
		public static final String GBV_DISCONTINUATION_ENCOUNTER = "ab3031fe-d67c-4ae4-9317-f6f5b920b776";
		public static final String GBV_COMMUNITY_LINKAGE_ENCOUNTER = "b38c73ee-6949-4f5f-8013-a89e36474d72";
		public static final String GBV_LEGAL_ENCOUNTER = "8941dc56-afda-4155-866f-e9fc0e71f5fd";
		public static final String GBV_PERPETRATOR_DETAILS_ENCOUNTER = "189e128f-3212-47ca-9d89-ee1876c394a9";
		public static final String GBV_CONSENT_ENCOUNTER = "3809b3fc-bcdd-46fe-a461-60a341a842d0";
		public static final String PEP_MANAGEMENT_NON_OCN_ENCOUNTER = "4f718f68-b414-4e27-803e-b4fbbc959d89";
		public static final String PEP_MANAGEMENT_OCN_ENCOUNTER = "5c1ecaf1-ec25-46b7-9b5e-ee7fe44f03cf";
		public static final String PEP_MANAGEMENT_SURVIVOR_ENCOUNTER = "133c8398-1fdc-437a-a74a-e73b1254c1d6";
		public static final String SGBV_POST_RAPE_CARE_PART_A_ENCOUNTER = "e571c807-8fcc-4bc3-bc64-4ed372b348e4";
		public static final String SGBV_PSYCHOLOGICAL_ASSESSMENT_PART_B_ENCOUNTER = "b5ab0a6b-9425-44da-b0d9-242f609f1605";
		public static final String GBV_PHYSICAL_EMOTIONAL_ABUSE_ENCOUNTER = "39d9350b-6eba-4653-a0cf-c554f44f6e91";
		public static final String GBV_PEP_FOLLOWUP_ENCOUNTER = "24c11d80-986d-4f13-9086-d0a01a84dae3";

	}

	public static final class _Form {
		public static final String GBV_ENROLLMENT_FORM = "9ba1d4aa-57d7-48f9-a635-a23508e8136c";
		public static final String GBV_TRAUMA_COUNSELLING_FORM = "e983d758-5adf-4917-8172-0f4be4d8116a";
		public static final String GBV_DISCONTINUATION_FORM = "8fed3d06-f8a1-4cb8-b853-cd93394bab79";
		public static final String GBV_COMMUNITY_LINKAGE_FORM = "f760e38c-3d2f-4a5d-aa3d-e9682576efa8";
		public static final String GBV_LEGAL_FORM = "d0c36426-4503-4236-ab5d-39bff77f2b50";
		public static final String GBV_PERPETRATOR_DETAILS_FORM = "f37d7e0e-95e8-430d-96a3-8e22664f74d6";
		public static final String GBV_CONSENT_FORM = "d720a8b3-52cc-41e2-9a75-3fd0d67744e5";
		public static final String PEP_MANAGEMENT_NON_OCN_FORM = "92de9269-6bb4-4c24-8ec9-870aa2c64b5a";
		public static final String PEP_MANAGEMENT_OCN_FORM = "556718da-7564-4286-a081-c50f8e4b6504";
		public static final String PEP_MANAGEMENT_SURVIVOR_FORM = "f44b2405-226b-47c4-b98f-b826ea4725ae";
		public static final String SGBV_POST_RAPE_CARE_PART_A_FORM = "c46aa4fd-8a5a-4675-90a7-a6f2119f61d8";
		public static final String SGBV_PSYCHOLOGICAL_ASSESSMENT_PART_B_FORM = "9d21275a-7657-433a-b305-a736423cc496";
		public static final String GBV_PHYSICAL_EMOTIONAL_ABUSE_FORM = "a0943862-f0fe-483d-9f11-44f62abae063";
		public static final String GBV_PEP_FOLLOWUP_FORM = "155ccbe2-a33f-4a58-8ce6-57a7372071ee";
		public static final String GBV_INITIAL_FORM = "e182d25f-d824-4cc7-8e0c-188519c300aa";
	}
	/**
	 * @see AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {

		install(program("Violence screening", "Violence Treatment Program", _Concept.GBV_TREATMENT,
			_Program.GBV_TREATMENT));
		install(encounterType("Violence Enrollment Encounter", "Violence Enrollment Encounter", _EncounterType.GBV_ENROLLMENT_ENCOUNTER));
		install(encounterType("Violence Trauma Counselling Encounter", "Violence Trauma Counselling Encounter", _EncounterType.GBV_TRAUMA_COUNSELLING_ENCOUNTER));
		install(encounterType("Violence Discontinuation Encounter", "Violence Discontinuation Encounter", _EncounterType.GBV_DISCONTINUATION_ENCOUNTER));
		install(encounterType("Violence Community Linkage Encounter", "Violence Community Linkage Encounter", _EncounterType.GBV_COMMUNITY_LINKAGE_ENCOUNTER));
		install(encounterType("Violence Legal Encounter", "Violence Legal Encounter", _EncounterType.GBV_LEGAL_ENCOUNTER));
		install(encounterType("Violence Perpetrator Details Encounter", "Violence Perpetrator Details Encounter", _EncounterType.GBV_PERPETRATOR_DETAILS_ENCOUNTER));
		install(encounterType("Violence Consent Encounter", "Violence Consent Encounter", _EncounterType.GBV_CONSENT_ENCOUNTER));
		install(encounterType("PEP Management Non OCN Encounter", "PEP Management Non OCN Encounter", _EncounterType.PEP_MANAGEMENT_NON_OCN_ENCOUNTER));
		install(encounterType("PEP Management OCN Encounter", "PEP Management OCN Encounter", _EncounterType.PEP_MANAGEMENT_OCN_ENCOUNTER));
		install(encounterType("PEP Management Survivor Encounter", "PEP Management Survivor Encounter", _EncounterType.PEP_MANAGEMENT_SURVIVOR_ENCOUNTER));
		install(encounterType("Sexual violence Post Rape Care 363A", "Sexual violence Post Rape Care 363A Encounter", _EncounterType.SGBV_POST_RAPE_CARE_PART_A_ENCOUNTER));
		install(encounterType("Sexual violence PRC Psychological Assessment 363B", "Sexual violence PRC Psychological Assessment 363B Encounter", _EncounterType.SGBV_PSYCHOLOGICAL_ASSESSMENT_PART_B_ENCOUNTER));
		install(encounterType("Violence Physical and Emotional Abuse", "Violence Physical and Emotional Abuse Encounter", _EncounterType.GBV_PHYSICAL_EMOTIONAL_ABUSE_ENCOUNTER));
		install(encounterType("Violence PEP Follow up Encounter", "Violence PEP Follow up Encounter", _EncounterType.GBV_PEP_FOLLOWUP_ENCOUNTER));

		install(form("Violence Enrollment Form", "Gender Based Violence Enrollment Form", _EncounterType.GBV_ENROLLMENT_ENCOUNTER, "1", _Form.GBV_ENROLLMENT_FORM));
		install(form("Violence Trauma Counselling Form", "Gender Based Violence Trauma Counselling Form", _EncounterType.GBV_TRAUMA_COUNSELLING_ENCOUNTER, "1", _Form.GBV_TRAUMA_COUNSELLING_FORM));
		install(form("Violence Discontinuation Form", "Gender Based Violence Discontinuation Form", _EncounterType.GBV_DISCONTINUATION_ENCOUNTER, "1", _Form.GBV_DISCONTINUATION_FORM));
		install(form("Violence Community Linkage Form", "Gender Based Violence Community Linkage Form", _EncounterType.GBV_COMMUNITY_LINKAGE_ENCOUNTER, "1", _Form.GBV_COMMUNITY_LINKAGE_FORM));
		install(form("Violence Legal Form", "Gender Based Violence Legal Form", _EncounterType.GBV_LEGAL_ENCOUNTER, "1", _Form.GBV_LEGAL_FORM));
		install(form("Violence Perpetrator Details Form", "Gender Based Violence Perpetrator Details Form", _EncounterType.GBV_PERPETRATOR_DETAILS_ENCOUNTER, "1", _Form.GBV_PERPETRATOR_DETAILS_FORM));
		install(form("Violence Consent Form", "Gender Based Violence Consent Form", _EncounterType.GBV_CONSENT_ENCOUNTER, "1", _Form.GBV_CONSENT_FORM));
		install(form("PEP Management Non OCN Form", "Gender Based Violence PEP Management Non OCN Form", _EncounterType.PEP_MANAGEMENT_NON_OCN_ENCOUNTER, "1", _Form.PEP_MANAGEMENT_NON_OCN_FORM));
		install(form("PEP Management OCN Form", "Post Exposure Prophylaxis Management OCN Form", _EncounterType.PEP_MANAGEMENT_OCN_ENCOUNTER, "1", _Form.PEP_MANAGEMENT_OCN_FORM));
		install(form("PEP Management Survivor Form", "Post Exposure Prophylaxis Management Survivor Form", _EncounterType.PEP_MANAGEMENT_SURVIVOR_ENCOUNTER, "1", _Form.PEP_MANAGEMENT_SURVIVOR_FORM));
		install(form("Sexual violence Post Rape Care 363A Form", "Sexual violence Post Rape Care 363A Form", _EncounterType.SGBV_POST_RAPE_CARE_PART_A_ENCOUNTER, "1", _Form.SGBV_POST_RAPE_CARE_PART_A_FORM));
		install(form("Sexual violence PRC Psychological Assessment 363B Form", "Sexual violence PRC Psychological Assessment 363B Form", _EncounterType.SGBV_PSYCHOLOGICAL_ASSESSMENT_PART_B_ENCOUNTER, "1", _Form.SGBV_PSYCHOLOGICAL_ASSESSMENT_PART_B_FORM));
		install(form("Violence Physical and Emotional Abuse Form", "Violence Physical and Emotional Abuse Form", _EncounterType.GBV_PHYSICAL_EMOTIONAL_ABUSE_ENCOUNTER, "1", _Form.GBV_PHYSICAL_EMOTIONAL_ABUSE_FORM));
		install(form("Violence PEP Follow up Form", "Violence PEP Follow up Form", _EncounterType.GBV_PEP_FOLLOWUP_ENCOUNTER, "1", _Form.GBV_PEP_FOLLOWUP_FORM));
		install(form("Violence Initial Form", "Violence Initial Form", _EncounterType.GBV_ENROLLMENT_ENCOUNTER, "1", _Form.GBV_INITIAL_FORM));

	}
}
