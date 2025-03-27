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

import org.openmrs.Form;
import org.openmrs.PatientIdentifierType.LocationBehavior;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.PersonAttributeType;
import org.openmrs.customdatatype.datatype.DateDatatype;
import org.openmrs.module.idgen.validator.LuhnMod25IdentifierValidator;
import org.openmrs.module.kenyaemr.EmrConstants;
import org.openmrs.module.kenyaemr.Metadata;
import org.openmrs.module.kenyaemr.datatype.FormDatatype;
import org.openmrs.module.kenyaemr.datatype.LocationDatatype;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.stereotype.Component;

import org.openmrs.customdatatype.datatype.FreeTextDatatype;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.encounterType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.form;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.globalProperty;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.patientIdentifierType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.personAttributeType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.providerAttributeType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.relationshipType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.visitAttributeType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.visitType;

/**
 * Common metadata bundle
 */
@Component
public class CommonMetadata extends AbstractMetadataBundle {

	public static final String GP_CLIENT_VERIFICATION_USE_EMR_PROXY = "kenyaemr.client.registry.use.emr.proxy";
	public static final String GP_CLIENT_VERIFICATION_EMR_VERIFICATION_PROXY_URL = "kenyaemr.client.registry.emr.verification.proxy.url";
	public static final String GP_CLIENT_VERIFICATION_GET_END_POINT = "kenyaemr.client.registry.get.api";
	public static final String GP_SHA_CLIENT_VERIFICATION_GET_END_POINT = "kenyaemr.sha.registry.get.api";
	public static final String GP_HIE_BASE_END_POINT_URL = "kenyaemr.sha.facilityregistry.get.api";
	public static final String GP_HIE_API_USER = "kenyaemr.sha.facilityregistry.get.api.user";
	public static final String GP_SHA_FACILITY_VERIFICATION_GET_API_SECRET = "kenyaemr.sha.facilityregistry.get.api.secret";
	public static final String GP_SHA_HEALTH_WORKER_VERIFICATION_GET_END_POINT = "kenyaemr.sha.healthworker.registry.get.api";
	public static final String GP_SHA_HEALTH_WORKER_VERIFICATION_GET_API_USER = "kenyaemr.sha.healthworker.get.api.user";
	public static final String GP_SHA_HEALTH_WORKER_VERIFICATION_GET_API_SECRET = "kenyaemr.sha.healthworker.get.api.secret";
	public static final String GP_SHA_CLIENT_VERIFICATION_GET_API_USER = "kenyaemr.sha.registry.get.api.user";
	public static final String GP_SHA_CLIENT_VERIFICATION_GET_API_SECRET = "kenyaemr.sha.registry.get.api.secret";
	public static final String GP_CLIENT_VERIFICATION_POST_END_POINT = "kenyaemr.client.registry.post.api";
	public static final String GP_CLIENT_VERIFICATION_API_TOKEN = "kenyaemr.client.registry.api.token";
	public static final String GP_CLIENT_VERIFICATION_TOKEN_URL = "kenyaemr.client.registry.token.url";
	public static final String GP_CLIENT_VERIFICATION_OAUTH2_CLIENT_ID = "kenyaemr.client.registry.oath2.client.id";
	public static final String GP_CLIENT_VERIFICATION_OAUTH2_CLIENT_SECRET = "kenyaemr.client.registry.oath2.client.secret";
	public static final String GP_CLIENT_VERIFICATION_OAUTH2_SCOPE = "kenyaemr.client.registry.oath2.scope";
	public static final String GP_CLIENT_VERIFICATION_QUERY_UPI_END_POINT = "kenyaemr.client.registry.query.upi.api";
	public static final String GP_CLIENT_VERIFICATION_QUERY_CCC_END_POINT = "kenyaemr.client.registry.query.ccc.api";
	public static final String GP_CLIENT_VERIFICATION_UPDATE_END_POINT = "kenyaemr.client.registry.update.api";
	public static final String GP_KENYAEMR_VERSION = "kenyaemr.version";
	public static final String HIV_CONSULTATION_SERVICE = "885b4ad3-fd4c-4a16-8ed3-08813e6b01fa";
	public static final String PREP_MONTHLY_REFILL_SERVICE = "b8c3efd9-e106-4409-ae0e-b9c651484a20";
	public static final String DRUG_REFILL_SERVICE = "a96921a1-b89e-4dd2-b6b4-7310f13bbabe";
	public static final String PREP_FOLLOWUP_SERVICE = "6f9b19f6-ac25-41f9-a75c-b8b125dec3da";
	public static final String PREP_INITIAL_SERVICE = "242f74b9-b0a3-4ba6-9be3-8f57591e3dff";
	public static final String GP_SHA_CLIENT_VERIFICATION_JWT_GET_END_POINT = "kenyaemr.sha.clientregistry.jwt.get.api";
	public static final String GP_SHA_HEALTH_WORKER_VERIFICATION_JWT_GET_END_POINT = "kenyaemr.sha.healthworker.jwt.get.api";
	public static final String GP_SHA_FACILITY_VERIFICATION_JWT_GET_END_POINT = "kenyaemr.sha.facilityregistry.jwt.get.api";
	public static final String GP_SHA_JWT_TOKEN_GET_END_POINT = "kenyaemr.sha.token.jwt.get.api";
	public static final String GP_SHA_JWT_TOKEN_USERNAME = "kenyaemr.sha.jwt.token.username";
	public static final String GP_SHA_JWT_TOKEN_PASSWORD = "kenyaemr.sha.jwt.token.password";
	public static final String GP_SHA_INTERVENTIONS = "kenyaemr.sha.interventions";
	public static final String GP_SHA_FACILITY_REGISTRATION_NUMBER = "kenyaemr.sha.registration.number";
	public static final String GP_CLINICAL_ACTION_PERCENTAGE_THRESHOLD = "kenyaemr.clinical.action.percentage.threshold";
	public static final String GP_CLINICAL_ACTION_HEI_ABSOLUTE_THRESHOLD = "kenyaemr.clinical.action.hei.absolute.threshold";


	public static final class _EncounterType {
		public static final String CONSULTATION = "465a92f2-baf8-42e9-9612-53064be868e8";
		public static final String LAB_RESULTS = "17a381d1-7e29-406a-b782-aa903b963c28";
		public static final String REGISTRATION = "de1f9d67-b73e-4e1b-90d0-036166fc6995";
		public static final String TRIAGE = "d1059fb9-a079-4feb-a749-eedd709ae542";
		public static final String HTS = "9c0a7a57-62ff-4f75-babe-5835b0e921b7";
		public static final String DRUG_REGIMEN_EDITOR = "7dffc392-13e7-11e9-ab14-d663bd873d93";
		public static final String CACX_SCREENING = "3fefa230-ea10-45c7-b62b-b3b8eb7274bb";
		public static final String ONCOLOGY_SCREENING = "e24209cc-0a1d-11eb-8f2a-bb245320c623";
		public static final String HIV_SELF_TEST = "8b706d42-b4ae-4b3b-bd83-b14f15294362";
		public static final String VMMC_PROCEDURE = "35c6fcc2-960b-11ec-b909-0242ac120002";
		public static final String GAD_7 = "899d64ad-be13-4071-a879-2153847206b7";
		public static final String MAT_CLINICAL_ENCOUNTER = "c3518485-ee22-4a47-b6d4-6d0e8f297b02";
		public static final String ILI_SURVEILLANCE = "f60910c7-2edd-4d93-813c-0e57095f892f";
		public static final String SARI_SURVEILLANCE = "76d55715-88cc-4851-b5e0-09136426fd46";
		public static final String PROCEDURE_RESULTS = "99a7a6ba-59f4-484e-880d-01cbeaead62f";
		public static final String NUTRITION = "160fcc03-4ff5-413f-b582-7e944a770bed";
		public static final String AUDIOLOGY = "49da00fd-5b62-437a-a2d4-a28b3d22fa27";
		public static final String PSYCHIATRIC = "7671cc06-b852-46e6-a279-afc8e2343a04";
		public static final String ONCOLOGY = "70a0158e-98f3-400b-9c90-a13c84b72065";
		public static final String PHYSIOTHERAPY = "a0ee267f-4555-48d7-9b1b-6d0dadee8506";
		public static final String GOPC = "92be533c-35f0-4505-bfbd-95724bea0208";
		public static final String MOPC = "4c629037-c0cd-4094-84d7-0737ab7b1bd0";
		public static final String SOPC = "d14dde5b-95dc-40a1-8ff0-acad34fb58b2";
		public static final String POPC = "6f8e49f2-3bff-4aff-909b-20568c316625";
		public static final String MAXILLOFACIAL = "92999f52-f352-415a-9e0d-87872e5b2c8d";
		public static final String SPEECHANDLANGUAGE = "5d0b6d85-5b88-410c-9f0f-4dab3db7ceb2";
		public static final String FAMILY_PLANNING = "85b019dc-18ec-4315-b661-5f7037e7ce38";
		public static final String DIABETICCONSULTATION = "70dc0091-064d-4428-ade8-119f142a93a2";
		public static final String ADVERSEDRUGREACTION = "7a185fe4-c56f-4195-b682-d3f5afa9d9c2";
		public static final String DERMATOLOGY = "e6eb6328-3f24-43f8-9f75-92daccb6ac48";
		public static final String UROLOGY = "1b1d8425-49e0-4cc6-8a66-6a598b5ac0a5";
		public static final String HEARING_SCREENING = "9c015ff8-8c5d-42bc-8ebd-dc7ea01e19d3";
		public static final String NEUROLOGY = "14d2b0fb-7fac-49df-bcf5-a07463fa3433";
		public static final String POST_MORTEM = "32b61a73-4971-4fc0-b20b-9a30176317e2";
		public static final String MORGUE_ADMISSION = "3d2df845-6f3c-45e7-b91a-d828a1f9c2e8";
		public static final String MORGUE_DISCHARGE = "3d618f40b-b5a3-4f17-81c8-2f04e2aad58e";
		public static final String INFECTIOUS_DISEASE = "a2cac281-81a8-4f35-9bc5-62493c8ee7df";
		public static final String IPD_PROCEDURE = "68634d60-86de-485e-99f9-76622fc5856b";
		public static final String NURSING_CARDEX = "b6569074-3b8c-43ba-bd4a-98c445405035";
		public static final String DOCTORS_NOTE = "14b36860-5033-4765-b91b-ace856ab64c2";
		public static final String POST_OPERATION = "13beea61-7d3d-4860-abe3-d5b874f736fb";
		public static final String PRE_OPERATION_CHECKLIST = "9023e0cd-78ef-44af-ba54-47f30f739b4a";
		public static final String NEW_BORN_ADMISSION = "454db697-7bc4-49e7-a9fa-097c19f1c9ec";
		public static final String PLASTIC_SURGERY = "5748bd8a-06bf-41a0-88df-16eed290db21";
		public static final String IIT_SCORE = "1dab4593-b09d-4c5b-83fe-f041092145d3";
	}

	public static final class _Form {
		public static final String CLINICAL_ENCOUNTER = Metadata.Form.CLINICAL_ENCOUNTER;
		public static final String LAB_RESULTS = Metadata.Form.LAB_RESULTS;
		public static final String OBSTETRIC_HISTORY = Metadata.Form.OBSTETRIC_HISTORY;
		public static final String OTHER_MEDICATIONS = Metadata.Form.OTHER_MEDICATIONS;
		public static final String PROGRESS_NOTE = Metadata.Form.PROGRESS_NOTE;
		public static final String SURGICAL_AND_MEDICAL_HISTORY = Metadata.Form.SURGICAL_AND_MEDICAL_HISTORY;
		public static final String TRIAGE = Metadata.Form.TRIAGE;
		public static final String GAD_7 = Metadata.Form.GAD_7;
		public static final String HTS_INITIAL_TEST = "402dc5d7-46da-42d4-b2be-f43ea4ad87b0";
		public static final String HTS_CONFIRMATORY_TEST = "b08471f6-0892-4bf7-ab2b-bf79797b8ea4";
		public static final String HTS_LINKAGE = "050a7f12-5c52-4cad-8834-863695af335d";
		public static final String CONTACT_LISTING = "d4493a7c-49fc-11e8-842f-0ed5f89f718b";
		public static final String BASIC_REGISTRATION = "add7abdc-59d1-11e8-9c2d-fa7ae01bbebc";
		public static final String DRUG_REGIMEN_EDITOR = "da687480-e197-11e8-9f32-f2801f1b9fd1";
		public static final String HTS_CLIENT_TRACING = "15ed03d2-c972-11e9-a32f-2a2ae2dbcce4";
		public static final String HTS_REFERRAL = "9284828e-ce55-11e9-a32f-2a2ae2dbcce4";
		public static final String CACX_SCREENING_FORM = "0c93b93c-bfef-4d2a-9fbe-16b59ee366e7";
		public static final String CACX_ASSESSMENT_FORM = "48f2235ca-cc77-49cb-83e6-f526d5a5f174";
		public static final String ONCOLOGY_SCREENING_FORM = "be5c5602-0a1d-11eb-9e20-37d2e56925ee";
		public static final String HIV_SELF_TESTING = "810fc592-f5f8-467a-846e-e177ba48a4e5";
		public static final String VMMC_PROCEDURE_FORM = "5ee93f48-960b-11ec-b909-0242ac120002";

		public static final String ILI_SURVEILLANCE_FORM = "05bcb369-5d50-4130-9a15-19c77a80314a";
		public static final String SARI_SURVEILLANCE_FORM = "be0f79d3-9e9a-414b-a1ca-6a2974110bc4";
		public static final String NUTRITION = "b8357314-0f6a-4fc9-a5b7-339f47095d62";
		public static final String ONCOLOGY_FORM = "31a371c6-3cfe-431f-94db-4acadad8d209";
		public static final String PSYCHIATRIC_FORM = "1fbd26f1-0478-437c-be1e-b8468bd03ffa";
		public static final String AUDIOLOGY_FORM = "d9f74419-e179-426e-9aff-ec97f334a075";
		public static final String PHYSIOTHERAPY_FORM = "18c209ac-0787-4b51-b9aa-aa8b1581239c";
		public static final String GOPC_FORM = "35ab0825-33af-49e7-ac01-bb0b05753732";
		public static final String MOPC_FORM = "00aa7662-e3fd-44a5-8f3a-f73eb7afa437";
		public static final String SOPC_FORM = "da1f7e74-5371-4997-8a02-b7b9303ddb61";
		public static final String POPC_FORM = "d95e44dd-e389-42ae-a9b6-1160d8eeebc4";
		public static final String MAXILLOFACIAL_CLINICAL_FORM = "b40d369c-31d0-4c1d-a80a-7e4b7f73bea0";
		public static final String SPEECH_AND_LANGAUGE_THERAPY_CLINICAL_FORM = "67f98072-1518-4beb-8a30-aa8a319ee3df";
		public static final String FAMILY_PLANNING = "a52c57d4-110f-4879-82ae-907b0d90add6";
		public static final String DIABETIC_CLINICAL_FORM = "9f6543e4-0821-4f9c-9264-94e45dc35e17";
		public static final String ADVERSE_DRUG_REACTION_FORM = "461e1b45-b3f2-4899-b3e9-d3b110b6ed9c";
		public static final String DERMATOLOGY_CLINICAL_FORM = "efa2f992-44af-487e-aaa7-c92813a34612";
		public static final String UROLOGY_CLINICAL_FORM = "57df8a60-7585-4fc0-b51b-e10e568cf53c";
		public static final String HEARING_SCREENING_CLINICAL_FORM = "270f388f-439f-476a-8919-8211f850d366";
		public static final String NEUROLOGY_CLINICAL_FORM = "f97f2bf3-c26b-4adf-aacd-e09d720a14cd";
		public static final String POST_MORTEM_CLINICAL_FORM = "016beec1-edff-4293-b3ed-817c7dddaa93";
		public static final String EAR_NOSE_THROAT_CLINICAL_FORM = "c5055956-c3bb-45f2-956f-82e114c57aa7";
		public static final String ORTHOPAEDIC_CLINICAL_FORM = "beec83df-6606-4019-8223-05a54a52f2b0";
		public static final String OCCUPATIONAL_THERAPY_CLINICAL_FORM = "062a24b5-728b-4639-8176-197e8f458490";
		public static final String OBSTETRIC_HISTORY_FORM = "d81e8157-317c-4041-9498-9d2318a1f2ed";
		public static final String OPHTHAMOLOGY_CLINICAL_FORM = "235900ff-4d4a-4575-9759-96f325f5e291";
		public static final String FERTILITY_CLINICAL_FORM = "32e43fc9-6de3-48e3-aafe-3b92f167753d";
		public static final String CARDIOLOGY_CLINICAL_FORM = "998be6de-bd13-4136-ba0d-3f772139895f";
		public static final String GASTROENTEROLOGY_CLINICAL_FORM = "6b4fa553-f2b3-47d0-a4c5-fc11f38b0b24";
		public static final String DENTAL_CLINICAL_FORM = "a3c01460-c346-4f3d-a627-5c7de9494ba0";
		public static final String INFECTIOUS_DISEASE_CLINICAL_FORM = "8f2fbcca-126f-439b-95b5-bcbc62647328";
		public static final String RENAL_CLINICAL_FORM = "6d0be8bd-5320-45a0-9463-60c9ee2b1338";
		public static final String IPD_PROCEDURE_FORM = "2b9c2b94-0b03-416a-b312-eef49b42f72c";
		public static final String NURSING_CARDEX_FORM = "1f81d5e2-3569-40cf-bbb9-361a53ba409b";
		public static final String DOCTORS_NOTE_FORM = "87379b0a-738b-4799-9736-cdac614cee2a";
		public static final String POST_OPERATION_FORM = "ac043326-c5f0-4d11-9e6f-f7266b3f3859";
		public static final String PRE_OPERATION_CHECKLIST_FORM = "a14824ca-29af-4d37-b9da-6bdee39f8808";
		public static final String NEW_BORN_ADMISSION_FORM = "5b0a08f5-87c1-40cc-8c09-09c33b44523d";
		public static final String PLASTIC_SURGERY_FORM = "05365b29-0bf1-4351-a2fb-524ec72130e8";
	}

	public static final class _OrderType {
		public static final String DRUG = "131168f4-15f5-102d-96e4-000c29c2a5d7";
	}

	public static final class _PatientIdentifierType {
		public static final String NATIONAL_ID = Metadata.IdentifierType.NATIONAL_ID;
		public static final String OLD_ID = Metadata.IdentifierType.OLD;
		public static final String OPENMRS_ID = Metadata.IdentifierType.MEDICAL_RECORD_NUMBER;
		public static final String PATIENT_CLINIC_NUMBER = Metadata.IdentifierType.PATIENT_CLINIC_NUMBER;
		public static final String NATIONAL_UNIQUE_PATIENT_IDENTIFIER = Metadata.IdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER;
		public static final String CWC_NUMBER = Metadata.IdentifierType.CWC_NUMBER;
		public static final String KDoD_SERVICE_NUMBER = Metadata.IdentifierType.KDoD_SERVICE_NUMBER;
		public static final String CLIENT_NUMBER = Metadata.IdentifierType.CLIENT_NUMBER;
		public static final String HUDUMA_NUMBER = Metadata.IdentifierType.HUDUMA_NUMBER;
		public static final String PASSPORT_NUMBER = Metadata.IdentifierType.PASSPORT_NUMBER;
		public static final String BIRTH_CERTIFICATE_NUMBER = Metadata.IdentifierType.BIRTH_CERTIFICATE_NUMBER;
		public static final String ALIEN_ID_NUMBER = Metadata.IdentifierType.ALIEN_ID_NUMBER;
		public static final String DRIVING_LICENSE = Metadata.IdentifierType.DRIVING_LICENSE;
		public static final String RECENCY_TESTING_ID = Metadata.IdentifierType.RECENCY_TESTING_ID;
		public static final String SOCIAL_HEALTH_INSURANCE_NUMBER = Metadata.IdentifierType.SOCIAL_HEALTH_INSURANCE_NUMBER;
		public static final String SHA_UNIQUE_IDENTIFICATION_NUMBER = Metadata.IdentifierType.SHA_UNIQUE_IDENTIFICATION_NUMBER;
		public static final String KDOD_PUBLICATION_NUMBER = Metadata.IdentifierType.KDOD_PUBLICATION_NUMBER;
	}

	public static final class _PersonAttributeType {
		public static final String NEXT_OF_KIN_ADDRESS = "7cf22bec-d90a-46ad-9f48-035952261294";
		public static final String NEXT_OF_KIN_CONTACT = "342a1d39-c541-4b29-8818-930916f4c2dc";
		public static final String NEXT_OF_KIN_NAME = "830bef6d-b01f-449d-9f8d-ac0fede8dbd3";
		public static final String NEXT_OF_KIN_RELATIONSHIP = "d0aa9fd1-2ac5-45d8-9c5e-4317c622c8f5";
		public static final String SUBCHIEF_NAME = "40fa0c9c-7415-43ff-a4eb-c7c73d7b1a7a";
		public static final String TELEPHONE_CONTACT = "b2c38640-2603-4629-aebd-3b54f33f1e3a";
		public static final String EMAIL_ADDRESS = "b8d0b331-1d2d-4a9a-b741-1816f498bdb6";
		public static final String ALTERNATE_PHONE_CONTACT = "94614350-84c8-41e0-ac29-86bc107069be";
		public static final String NEAREST_HEALTH_CENTER = "27573398-4651-4ce5-89d8-abec5998165c";
		public static final String GUARDIAN_FIRST_NAME = "8caf6d06-9070-49a5-b715-98b45e5d427b";
		public static final String GUARDIAN_LAST_NAME = "0803abbd-2be4-4091-80b3-80c6940303df";
		public static final String CHT_USERNAME = "1aaead2d-0e88-40b2-abcd-6bc3d20fa43c";
		public static final String KDOD_CADRE = "96a99acd-2f11-45bb-89f7-648dbcac5ddf";
		public static final String KDOD_RANK = "9f1f8254-20ea-4be4-a14d-19201fe217bf";
		public static final String KDOD_UNIT = "848f5688-41c6-464c-b078-ea6524a3e971";
		public static final String KDOD_CIVILIAN_RANK = "457463c8-dddb-4d35-bb5c-eb365f6d1790";
		public static final String KDOD_SERVICE = "cdcda371-3b8a-4ce1-b753-76eaac0ab3cb";
		public static final String VERIFICATION_STATUS_WITH_NATIONAL_REGISTRY = "869f623a-f78e-4ace-9202-0bed481822f5";
		public static final String VERIFICATION_MESSAGE_WITH_NATIONAL_REGISTRY = "752a0331-5293-4aa5-bf46-4d51aaf2cdc5";
		public static final String CCC_SYNC_STATUS_WITH_NATIONAL_REGISTRY = "4dfa195f-8420-424d-8275-d60cf115303d";
		public static final String CCC_SYNC_MESSAGE_WITH_NATIONAL_REGISTRY = "9bc43f7e-ff05-4afb-8dc4-710d245a927c";
		public static final String VERIFICATION_DESCRIPTION_FOR_IPRS_ERROR = "a45d0a45-4e7a-4d3e-a550-2d482628d930";
		public static final String DUPLICATE_NUPI_STATUS_WITH_NATIONAL_REGISTRY = "5897ac2d-de2f-4b58-aa92-c9adc9aedc66";
		public static final String DUPLICATE_NUPI_FACILITY_WITH_NATIONAL_REGISTRY = "39673db2-c229-441c-bd84-f30edf1cc1a7";
		public static final String DUPLICATE_NUPI_SITES_WITH_NATIONAL_REGISTRY = "876816a4-fa3e-4f93-b1f1-4443cebd9f30";
		public static final String DUPLICATE_NUPI_TOTALSITES_WITH_NATIONAL_REGISTRY = "2816180c-46a2-49d7-b15f-e44fd81b5057";
		public static final String PNS_APPROACH = "59d1b886-90c8-4f7f-9212-08b20a9ee8cf";
		public static final String PNS_PATIENT_CONTACT_BASELINE_HIV_STATUS = "3ca03c84-632d-4e53-95ad-91f1bd9d96d6";
		public static final String PNS_PATIENT_CONTACT_LIVING_WITH_PATIENT = "35a08d84-9f80-4991-92b4-c4ae5903536e";
		public static final String PNS_PATIENT_CONTACT_REGISTRATION_SOURCE = "7c94bd35-fba7-4ef7-96f5-29c89a318fcf";
		public static final String PNS_PATIENT_CONTACT_IPV_OUTCOME = "49c543c2-a72a-4b0a-8cca-39c375c0726f";

	}

	public static final class _Provider {
		public static final String UNKNOWN = "ae01b8ff-a4cc-4012-bcf7-72359e852e14";
	}

	public static final class _ProviderAttributeType {
		public static final String PRIMARY_FACILITY = "5a53dddd-b382-4245-9bf1-03bce973f24b";
		public static final String LICENSE_NUMBER = "bcaaa67b-cc72-4662-90c2-e1e992ceda66";
		public static final String LICENSE_EXPIRY_DATE = "00539959-a1c7-4848-a5ed-8941e9d5e835";
		public static final String LICENSE_BODY = "ba18bb97-d17c-4640-80d2-58e7df90ca4c";
		public static final String NATIONAL_ID = "3d152c97-2293-4a2b-802e-e0f1009b7b15";
		public static final String PROVIDER_HIE_FHIR_REFERENCE = "67b94e8e-4d61-4810-b0f1-d86497f6e553";
		public static final String PROVIDER_QUALIFICATION = "43f99413-6e7f-4812-bc60-066bb1d43f94";
		public static final String PROVIDER_ADDRESS = "033ff604-ecf7-464f-b623-5b77c733667f";
		public static final String PROVIDER_TELEPHONE = "37daed7f-1f4e-4e62-8e83-6048ade18a87";
		public static final String PROVIDER_PASSPORT_NUMBER = "5b4b88e8-9db3-41e6-a175-5e39f2c8a9a5";

	}

	public static final class _RelationshipType {
		public static final String SPOUSE = "d6895098-5d8d-11e3-94ee-b35a4132a5e3";
		public static final String GUARDIAN_DEPENDANT = "5f115f62-68b7-11e3-94ee-6bef9086de92";
		public static final String PARTNER = "007b765f-6725-4ae9-afee-9966302bace4";
		public static final String CO_WIFE = "2ac0d501-eadc-4624-b982-563c70035d46";
		public static final String SNS = "76edc1fe-c5ce-4608-b326-c8ecd1020a73";
		public static final String CASE_MANAGER = "9065e3c6-b2f5-4f99-9cbf-f67fd9f82ec5";
		public static final String CARE_GIVER = "3667e52f-8653-40e1-b227-a7278d474020";
	}

	public static final class _VisitAttributeType {
		public static final String SOURCE_FORM = "8bfab185-6947-4958-b7ab-dfafae1a3e3d";
		public static final String VISIT_QUEUE_NUMBER = "c61ce16f-272a-41e7-9924-4c555d0932c5";
		public static final String PATIENT_TYPE_UUID = "3b9dfac8-9e4d-11ee-8c90-0242ac120002";
		public static final String PAYMENT_METHOD_UUID = "e6cb0c3b-04b0-4117-9bc6-ce24adbda802";
		public static final String POLICY_NUMBER = "0f4f3306-f01b-43c6-af5b-fdb60015cb02";
		public static final String INSURANCE_SCHEME = "2d0fa959-6780-41f1-85b1-402045935068";
		public static final String SHA_BENEFITS_PACKAGE = "338725fa-3790-4679-98b9-be623214ee29";
	}

	public static final class _VisitType {
		public static final String OUTPATIENT = "3371a4d4-f66f-4454-a86d-92c7b3da990c";
		public static final String INPATIENT = "a73e2ac6-263b-47fc-99fc-e0f2c09fc914";
		public static final String MORGUE = "6307dbe2-f336-4c11-a393-50c2769f455a";
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {
		install(encounterType("Consultation", "Collection of clinical data during the main consultation",
				_EncounterType.CONSULTATION));
		install(encounterType("Lab Results", "Collection of laboratory results", _EncounterType.LAB_RESULTS));
		install(encounterType("Registration", "Initial data collection for a patient, not specific to any program",
				_EncounterType.REGISTRATION));
		install(encounterType("Triage", "Collection of limited data prior to a more thorough examination",
				_EncounterType.TRIAGE));
		install(encounterType("Generalized Anxiety Disorder Assessment",
				"Anxiety Screening using Generalized Anxiety Disorder Assessment (GAD-7)", _EncounterType.GAD_7));
		install(encounterType("HTS", "HTS Services", _EncounterType.HTS));
		install(encounterType("Drug Regimen Editor", "Handles patient regimen events",
				_EncounterType.DRUG_REGIMEN_EDITOR));
		install(encounterType("Cervical cancer screening", "Cervical cancer screening", _EncounterType.CACX_SCREENING));
		install(encounterType("HIV self testing", "Self testing screening", _EncounterType.HIV_SELF_TEST));
		install(encounterType("Oncology screening", "Oncology screening encounter type",
				_EncounterType.ONCOLOGY_SCREENING));
		install(encounterType("MAT Clinical Encounter", "MAT Clinical Encounter",
				_EncounterType.MAT_CLINICAL_ENCOUNTER));
		install(encounterType("ILI Surveillance", "ILI Surveillance encounter type", _EncounterType.ILI_SURVEILLANCE));
		install(encounterType("SARI Surveillance", "SARI Surveillance encounter type",
				_EncounterType.SARI_SURVEILLANCE));
		install(encounterType("Procedure Results", "Procedure outcome encounter type",
				_EncounterType.PROCEDURE_RESULTS));
		install(encounterType("Nutrition", "Nutrition encounter type", _EncounterType.NUTRITION));
		install(encounterType("Audiology", "Audiology encounter type", _EncounterType.AUDIOLOGY));
		install(encounterType("Psychiatric", "Psychiatric encounter type", _EncounterType.PSYCHIATRIC));
		install(encounterType("Oncology", "Oncology encounter type", _EncounterType.ONCOLOGY));
		install(encounterType("Physiotherapy", "Physiotherapy encounter type", _EncounterType.PHYSIOTHERAPY));
		install(encounterType("GOPC", "GOPC encounter type", _EncounterType.GOPC));
		install(encounterType("MOPC", "MOPC encounter type", _EncounterType.MOPC));
		install(encounterType("SOPC", "SOPC encounter type", _EncounterType.SOPC));
		install(encounterType("POPC", "POPC encounter type", _EncounterType.POPC));
		install(encounterType("MAXILLOFACIAL", "Maxillofacial encounter type", _EncounterType.MAXILLOFACIAL));
		install(encounterType("Speech and Language", "Speech and Language encounter type",
				_EncounterType.SPEECHANDLANGUAGE));
		install(encounterType("Family Planning", "Family Planning encounter type", _EncounterType.FAMILY_PLANNING));
		install(encounterType("Diabetic Clinic", "Diabetic Clinic encounter type", _EncounterType.DIABETICCONSULTATION));
		install(encounterType("Adverse Drug Reaction", "Adverse Drug Reaction encounter type", _EncounterType.ADVERSEDRUGREACTION));
		install(encounterType("Dermatology Clinic", "Dermatology clinical encounter type", _EncounterType.DERMATOLOGY));
		install(encounterType("Urology Clinic", "Urology clinical encounter type", _EncounterType.UROLOGY));
		install(encounterType("Hearing Screening Clinic", "Hearing Screening clinical encounter type", _EncounterType.HEARING_SCREENING));
		install(encounterType("Neurology Clinic", "Neurology clinical encounter type", _EncounterType.NEUROLOGY));
		install(encounterType("Post-Mortem", "Post-Mortem clinical encounter type", _EncounterType.POST_MORTEM));
		install(encounterType("Morgue Admission", "Morgue admission clinical encounter type", _EncounterType.MORGUE_ADMISSION));
		install(encounterType("Morgue Discharge", "Morgue discharge clinical encounter type", _EncounterType.MORGUE_DISCHARGE));
		install(encounterType("Infectious Disease", "Infectious disease clinical encounter type", _EncounterType.INFECTIOUS_DISEASE));
		install(encounterType("IPD Procedure", "Inpatient procedure clinical encounter type", _EncounterType.IPD_PROCEDURE));
		install(encounterType("Nursing Cardex", "Nursing cardex clinical encounter type", _EncounterType.NURSING_CARDEX));
		install(encounterType("Doctor's Note", "Doctor's note clinical encounter type", _EncounterType.DOCTORS_NOTE));
		install(encounterType("Post Operation", "Post operation clinical encounter type", _EncounterType.POST_OPERATION));
		install(encounterType("Pre-Operation Checklist", "Pre-Operation checklist clinical encounter type", _EncounterType.PRE_OPERATION_CHECKLIST));
		install(encounterType("New born admission", "New born admission clinical encounter type", _EncounterType.NEW_BORN_ADMISSION));
		install(encounterType("Plastic Surgery Clinic", "Plastic surgery clinical encounter type", _EncounterType.PLASTIC_SURGERY));
		install(encounterType("IIT Score", "IIT Score clinical encounter type", _EncounterType.IIT_SCORE));

		install(form("Clinical Encounter", null, _EncounterType.CONSULTATION, "1", _Form.CLINICAL_ENCOUNTER));
		install(form("Lab Results", null, _EncounterType.LAB_RESULTS, "1", _Form.LAB_RESULTS));
		install(form("Obstetric History", null, _EncounterType.REGISTRATION, "1", _Form.OBSTETRIC_HISTORY));
		install(form("Medications", "Recording of non-regimen medications", _EncounterType.CONSULTATION, "1",
				_Form.OTHER_MEDICATIONS));
		install(form("Progress Note", "For additional information - mostly complaints and examination findings.",
				_EncounterType.CONSULTATION, "1", _Form.PROGRESS_NOTE));
		install(form("Surgical and Medical History", null, _EncounterType.REGISTRATION, "1",
				_Form.SURGICAL_AND_MEDICAL_HISTORY));
		install(form("Triage", null, _EncounterType.TRIAGE, "1", _Form.TRIAGE));
		install(form("Generalized Anxiety Disorder Assessment",
				"Anxiety Screening using Generalized Anxiety Disorder Assessment (GAD-7)", _EncounterType.GAD_7, "1",
				_Form.GAD_7));
		install(form("HTS Initial Form", "Form for HTS testing services ", _EncounterType.HTS, "1",
				_Form.HTS_INITIAL_TEST));
		install(form("HTS Retest Form", "Form for HTS retest Services", _EncounterType.HTS, "1",
				_Form.HTS_CONFIRMATORY_TEST));
		install(form("HTS Linkage Form", "Form for HTS linkage", _EncounterType.HTS, "1", _Form.HTS_LINKAGE));
		install(form("Contact Listing Form", "Lists all contacts for a patient", _EncounterType.HTS, "1",
				_Form.CONTACT_LISTING));
		install(form("Registration Form", "Initial data collection for a patient/client, not specific to any program",
				_EncounterType.REGISTRATION, "1", _Form.BASIC_REGISTRATION));
		install(form("Drug Regimen Editor", null, _EncounterType.DRUG_REGIMEN_EDITOR, "1", _Form.DRUG_REGIMEN_EDITOR));
		install(form("HTS Client Tracing Form", "Form for tracing hts clients", _EncounterType.HTS, "1",
				_Form.HTS_CLIENT_TRACING));
		install(form("HTS Client Referral Form", "Form for HTS linkage referral", _EncounterType.HTS, "1",
				_Form.HTS_REFERRAL));
		install(form("Cervical Cancer Screening Form", "Form for Cervical Cancer Screening",
				_EncounterType.CACX_SCREENING, "1", _Form.CACX_SCREENING_FORM));
		install(form("Cervical Cancer Assessment Form", "Form for Cervical Cancer Assessment",
				_EncounterType.CACX_SCREENING, "1", _Form.CACX_ASSESSMENT_FORM));
		install(form("Cancer Screening and early diagnosis", "Form Cancer Screening and early diagnosis",
				_EncounterType.ONCOLOGY_SCREENING, "1", _Form.ONCOLOGY_SCREENING_FORM));
		install(form("HIV Self Test Form", "Form for HIV self testing services ", _EncounterType.HIV_SELF_TEST, "1",
				_Form.HIV_SELF_TESTING));
		install(form("ILI Surveillance Form", "Form for ILI Surveillance", _EncounterType.ILI_SURVEILLANCE, "1",
				_Form.ILI_SURVEILLANCE_FORM));
		install(form("SARI Surveillance Form", "Form for SARI Surveillance", _EncounterType.SARI_SURVEILLANCE, "1",
				_Form.SARI_SURVEILLANCE_FORM));
		install(form("Nutrition Form", "Form for Nutrition", _EncounterType.NUTRITION, "1", _Form.NUTRITION));
		install(form("Audiology", "Form for Audiology", _EncounterType.AUDIOLOGY, "1", _Form.AUDIOLOGY_FORM));
		install(form("Psychiatric Form", "Form for Psychiatric", _EncounterType.PSYCHIATRIC, "1",
				_Form.PSYCHIATRIC_FORM));
		install(form("Oncology Form", "Form for Oncology", _EncounterType.ONCOLOGY, "1", _Form.ONCOLOGY_FORM));
		install(form("Physiotherapy Form", "Form for Physiotherapy", _EncounterType.PHYSIOTHERAPY, "1",
				_Form.PHYSIOTHERAPY_FORM));
		install(form("GOPC Form", "Form for Oncology", _EncounterType.GOPC, "1", _Form.GOPC_FORM));
		install(form("MOPC Form", "Form for Oncology", _EncounterType.MOPC, "1", _Form.MOPC_FORM));
		install(form("SOPC Form", "Form for Oncology", _EncounterType.SOPC, "1", _Form.SOPC_FORM));
		install(form("Family Planning", "Form for family planning", _EncounterType.FAMILY_PLANNING, "1",
				_Form.FAMILY_PLANNING));
		install(form("POPC Form", "Form for Oncology", _EncounterType.POPC, "1", _Form.POPC_FORM));
		install(form("Maxillofacial Clinical Form", "Form for Maxillofacial clinical encounter",
				_EncounterType.MAXILLOFACIAL, "1", _Form.MAXILLOFACIAL_CLINICAL_FORM));
		install(form("Speech and Language Therapy Clinical Form",
				"Form for Speech and Language Therapy clinical encounter", _EncounterType.SPEECHANDLANGUAGE, "1",
				_Form.SPEECH_AND_LANGAUGE_THERAPY_CLINICAL_FORM));
		install(form("Diabetic Clinical Form", "Form for Diabetic Consultattion clinical encounter", _EncounterType.DIABETICCONSULTATION, "1", 
				_Form.DIABETIC_CLINICAL_FORM));
		install(form("Adverse Drug Reaction Clinical Form", "Form for Adverse Drug Reaction and Pharmacovigilance clinical encounter", _EncounterType.ADVERSEDRUGREACTION, "1", _Form.ADVERSE_DRUG_REACTION_FORM));
		install(form("Dermatology Clinical Form", "Form for Dermatology clinical encounter", _EncounterType.DERMATOLOGY, "1", _Form.DERMATOLOGY_CLINICAL_FORM));
		install(form("Urology Clinical Form", "Form for Urology clinical encounter", _EncounterType.UROLOGY, "1", _Form.UROLOGY_CLINICAL_FORM));
		install(form("Hearing Screening Clinical Form", "Form for Hearing screening clinical encounter", _EncounterType.HEARING_SCREENING, "1", _Form.HEARING_SCREENING_CLINICAL_FORM));
		install(form("Neurology Clinical Form", "Form for Neurology clinical encounter", _EncounterType.NEUROLOGY, "1", _Form.NEUROLOGY_CLINICAL_FORM));
		install(form("Post-Mortem Clinical Form", "Form for Morgue clinical encounter", _EncounterType.POST_MORTEM, "1", _Form.POST_MORTEM_CLINICAL_FORM));
		install(form("ENT Clinical Form", "Form for ENT clinical encounter", _EncounterType.CONSULTATION, "1", _Form.EAR_NOSE_THROAT_CLINICAL_FORM));
		install(form("Orthopaedic Clinical Form", "Form for Orthopaedic clinical encounter", _EncounterType.CONSULTATION, "1", _Form.ORTHOPAEDIC_CLINICAL_FORM));
		install(form("Occupational Therapy Clinical Form", "Form for Occupational therapy encounter", _EncounterType.CONSULTATION, "1", _Form.OCCUPATIONAL_THERAPY_CLINICAL_FORM));
		install(form("Obstetric History Form", "Form for Obstetric History", _EncounterType.CONSULTATION, "1", _Form.OBSTETRIC_HISTORY_FORM));
		install(form("Ophthamology Clinical Form", "Form for Ophthamology encounter ", _EncounterType.CONSULTATION, "1", _Form.OPHTHAMOLOGY_CLINICAL_FORM));
		install(form("Gastroenterology Clinical Form", "Form for Gastroenterology encounter ", _EncounterType.CONSULTATION, "1", _Form.GASTROENTEROLOGY_CLINICAL_FORM));
		install(form("Fertility Clinical Form", "Form for Fertility encounter ", _EncounterType.CONSULTATION, "1", _Form.FERTILITY_CLINICAL_FORM));
		install(form("Cardiology Clinical Form", "Form for Cardiology encounter ", _EncounterType.CONSULTATION, "1", _Form.CARDIOLOGY_CLINICAL_FORM));
		install(form("Dental Clinical Form", "Form for Dental encounter ", _EncounterType.CONSULTATION, "1", _Form.DENTAL_CLINICAL_FORM));
		install(form("Infectious Disease Clinical Form", "Form for Infectious disease clinical encounter", _EncounterType.INFECTIOUS_DISEASE, "1", _Form.INFECTIOUS_DISEASE_CLINICAL_FORM));
		install(form("IPD Procedure Clinical Form", "Form for Inpatient procedure clinical encounter", _EncounterType.IPD_PROCEDURE, "1", _Form.IPD_PROCEDURE_FORM));
		install(form("Doctor's Note Clinical Form", "Form for Infectious disease clinical encounter", _EncounterType.DOCTORS_NOTE, "1", _Form.DOCTORS_NOTE_FORM));
		install(form("Nursing Cardex Clinical Form", "Form for Cardex plan clinical encounter", _EncounterType.NURSING_CARDEX, "1", _Form.NURSING_CARDEX_FORM));
		install(form("Pre-Operation Checklist Clinical Form", "Form for Pre-procedure checklist clinical encounter", _EncounterType.PRE_OPERATION_CHECKLIST, "1", _Form.PRE_OPERATION_CHECKLIST_FORM));
		install(form("Post Operation Clinical Form", "Form for post operation clinical encounter", _EncounterType.POST_OPERATION, "1", _Form.POST_OPERATION_FORM));
		install(form("Newbord Admission Clinical Form", "Form for Newbord admission clinical encounter", _EncounterType.NEW_BORN_ADMISSION, "1", _Form.NEW_BORN_ADMISSION_FORM));
		install(form("Plastic Surgery Clinical Form", "Form for Plastic surgery clinical encounter", _EncounterType.PLASTIC_SURGERY, "1", _Form.PLASTIC_SURGERY_FORM));

		install(globalProperty(EmrConstants.GP_DEFAULT_LOCATION,
				"The facility for which this installation is configured",
				LocationDatatype.class, null, null));

		String adxMappingString = "[{\"reportName\":\"Revised MOH 731\",\"prefix\":\"Y23_\",\"datasets\":[{\"name\":\"1\",\"dhisName\":\"OaIjkmzEeNO\"},{\"name\":\"2\",\"dhisName\":\"L7hIEAumRnQ\"},{\"name\":\"3\",\"dhisName\":\"gtDFQTNBp7y\"},{\"name\":\"4\",\"dhisName\":\"SoiOUE3lOJd\"}]}]";
		//3pm Adx string
		String adx3pmMappingString = "[{\"reportName\":\"Monthly report\",\"prefix\":\"\",\"datasets\":[{\"name\":\"1\",\"3pmName\":\"qzJqoxdfXJn\"}]}]";

		install(globalProperty(EmrConstants.GP_DHIS2_DATASET_MAPPING, "ADX Mapping for KenyaEMR and DHIS2 datasets", adxMappingString));
		install(globalProperty(EmrConstants.GP_3PM_DATASET_MAPPING, "ADX Mapping for KenyaEMR and 3PM datasets", adx3pmMappingString));
		install(globalProperty(EmrConstants.GP_DHIS_USERNAME, "Username for DHIS server", ""));
		install(globalProperty(EmrConstants.GP_DHIS_PASSWORD, "Password for DHIS server","" ));

		install(globalProperty("order.drugDosingUnitsConceptUuid", "Drug dosing units concept",
				"162384AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
		install(globalProperty("client_number_label", "Label for Client Number", "Client Number"));
		install(globalProperty("clientNumber.enabled", "Switch to show client number", "false"));

		AdministrationService administrationService = Context.getAdministrationService();
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_CLIENT_VERIFICATION_USE_EMR_PROXY) == null) {
			install(globalProperty(GP_CLIENT_VERIFICATION_USE_EMR_PROXY, "Use the EMR backend to proxy NUPI requests (true or false)", "false"));
		}    
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_CLIENT_VERIFICATION_EMR_VERIFICATION_PROXY_URL) == null) {
			install(globalProperty(GP_CLIENT_VERIFICATION_EMR_VERIFICATION_PROXY_URL, "The local EMR URL to proxy NUPI verification requests", "http://127.0.0.1:8080/openmrs/ws/rest/v1/kenyaemr/verifynupi"));
		} 
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_CLIENT_VERIFICATION_GET_END_POINT) == null) {
			install(globalProperty(GP_CLIENT_VERIFICATION_GET_END_POINT, "A GET API for getting client information at the client registry", "https://afyakenyaapi.health.go.ke/partners/registry/search"));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_SHA_CLIENT_VERIFICATION_GET_END_POINT) == null) {
			install(globalProperty(GP_SHA_CLIENT_VERIFICATION_GET_END_POINT, "A GET API for getting SHA client information from the client registry", "http://127.0.0.1:9342/api/shaPatientResource"));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_HIE_BASE_END_POINT_URL) == null) {
			install(globalProperty(GP_HIE_BASE_END_POINT_URL, "A GET API for HIE registry", ""));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_SHA_HEALTH_WORKER_VERIFICATION_GET_END_POINT) == null) {
			install(globalProperty(GP_SHA_HEALTH_WORKER_VERIFICATION_GET_END_POINT, "A GET API for getting SHA Health Worker information from Healthcare Worker registry", ""));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_SHA_HEALTH_WORKER_VERIFICATION_GET_API_USER) == null) {
			install(globalProperty(GP_SHA_HEALTH_WORKER_VERIFICATION_GET_API_USER, "API user for for connecting to the SHA provider registry", ""));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_SHA_HEALTH_WORKER_VERIFICATION_GET_API_SECRET) == null) {
			install(globalProperty(GP_SHA_HEALTH_WORKER_VERIFICATION_GET_API_SECRET, "API secret token for for connecting to the SHA provider registry", ""));
		}

		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_SHA_CLIENT_VERIFICATION_GET_API_USER) == null) {
			install(globalProperty(GP_SHA_CLIENT_VERIFICATION_GET_API_USER, "API user for for connecting to the SHA client registry", ""));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_SHA_CLIENT_VERIFICATION_GET_API_SECRET) == null) {
			install(globalProperty(GP_SHA_CLIENT_VERIFICATION_GET_API_SECRET, "API secret token for for connecting to the SHA client registry", ""));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_HIE_API_USER) == null) {
			install(globalProperty(GP_HIE_API_USER, "API user for for connecting to the SHA Facility registry", ""));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_SHA_FACILITY_VERIFICATION_GET_API_SECRET) == null) {
			install(globalProperty(GP_SHA_FACILITY_VERIFICATION_GET_API_SECRET, "API secret token for for connecting to the SHA facility registry", ""));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_CLIENT_VERIFICATION_POST_END_POINT) == null) {
			install(globalProperty(GP_CLIENT_VERIFICATION_POST_END_POINT, "A POST API for posting client information to the client registry", "https://afyakenyaapi.health.go.ke/partners/registry"));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_CLIENT_VERIFICATION_API_TOKEN) == null) {
			install(globalProperty(GP_CLIENT_VERIFICATION_API_TOKEN, "API token for connecting to the client registry", ""));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_CLIENT_VERIFICATION_TOKEN_URL) == null) {
			install(globalProperty(GP_CLIENT_VERIFICATION_TOKEN_URL, "client registry authorization token URL", "https://afyakenyaidentityapi.health.go.ke/connect/token"));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_CLIENT_VERIFICATION_OAUTH2_CLIENT_ID) == null) {
			install(globalProperty(GP_CLIENT_VERIFICATION_OAUTH2_CLIENT_ID, "client registry authorization client ID", "palladium.partner.client"));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_CLIENT_VERIFICATION_OAUTH2_CLIENT_SECRET) == null) {
			install(globalProperty(GP_CLIENT_VERIFICATION_OAUTH2_CLIENT_SECRET, "client registry authorization client secret", "28f95b2a"));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_CLIENT_VERIFICATION_OAUTH2_SCOPE) == null) {
			install(globalProperty(GP_CLIENT_VERIFICATION_OAUTH2_SCOPE, "client registry authorization scope", "DHP.Gateway DHP.Partners"));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_CLIENT_VERIFICATION_QUERY_UPI_END_POINT) == null) {
			install(globalProperty(GP_CLIENT_VERIFICATION_QUERY_UPI_END_POINT, "A GET API for getting client information at the client registry using NUPI number", "https://afyakenyaapi.health.go.ke/partners/registry/search/upi"));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_CLIENT_VERIFICATION_QUERY_CCC_END_POINT) == null) {
			install(globalProperty(GP_CLIENT_VERIFICATION_QUERY_CCC_END_POINT, "A GET API for getting client information at the client registry using CCC number", "https://afyakenyaapi.health.go.ke/partners/registry/search/ccc"));
		}
		if(administrationService.getGlobalPropertyObject(CommonMetadata.GP_CLIENT_VERIFICATION_UPDATE_END_POINT) == null) {
			install(globalProperty(GP_CLIENT_VERIFICATION_UPDATE_END_POINT, "A PUT API for updating client information at the client registry", "https://dhpstagingapi.health.go.ke/partners/registry"));
		}
		if(Context.getAdministrationService().getGlobalPropertyObject(CommonMetadata.GP_SHA_CLIENT_VERIFICATION_JWT_GET_END_POINT) == null) {
			install(globalProperty(GP_SHA_CLIENT_VERIFICATION_JWT_GET_END_POINT, "A GET API for connecting to the SHA JWT Authenticated client registry", ""));
		}
		if(Context.getAdministrationService().getGlobalPropertyObject(CommonMetadata.GP_SHA_HEALTH_WORKER_VERIFICATION_JWT_GET_END_POINT) == null) {
			install(globalProperty(GP_SHA_HEALTH_WORKER_VERIFICATION_JWT_GET_END_POINT, "A GET API for connecting to the SHA JWT Authenticated health worker registry", ""));
		}
		if(Context.getAdministrationService().getGlobalPropertyObject(CommonMetadata.GP_SHA_FACILITY_VERIFICATION_JWT_GET_END_POINT) == null) {
			install(globalProperty(GP_SHA_FACILITY_VERIFICATION_JWT_GET_END_POINT, "A GET API for connecting to the SHA JWT Authenticated facility registry", ""));
		}
		if(Context.getAdministrationService().getGlobalPropertyObject(CommonMetadata.GP_SHA_JWT_TOKEN_GET_END_POINT) == null) {
			install(globalProperty(GP_SHA_JWT_TOKEN_GET_END_POINT, "SHA JWT Authentication token url", ""));
		}
		if(Context.getAdministrationService().getGlobalPropertyObject(CommonMetadata.GP_SHA_JWT_TOKEN_USERNAME) == null) {
			install(globalProperty(GP_SHA_JWT_TOKEN_USERNAME, "SHA JWT Authentication token username", ""));
		}
		if(Context.getAdministrationService().getGlobalPropertyObject(CommonMetadata.GP_SHA_JWT_TOKEN_PASSWORD) == null) {
			install(globalProperty(GP_SHA_JWT_TOKEN_PASSWORD, "SHA JWT Authentication token password", ""));
		}
		if(Context.getAdministrationService().getGlobalPropertyObject(CommonMetadata.GP_SHA_INTERVENTIONS) == null) {
			install(globalProperty(GP_SHA_INTERVENTIONS, "SHA Interventions url", ""));
		}
		if(Context.getAdministrationService().getGlobalPropertyObject(CommonMetadata.GP_SHA_FACILITY_REGISTRATION_NUMBER) == null) {
			install(globalProperty(GP_SHA_FACILITY_REGISTRATION_NUMBER, "SHA facility registration number", ""));
		}
		if(Context.getAdministrationService().getGlobalPropertyObject(CommonMetadata.GP_CLINICAL_ACTION_PERCENTAGE_THRESHOLD) == null) {
			install(globalProperty(GP_CLINICAL_ACTION_PERCENTAGE_THRESHOLD, "General percentage threshold for clinical action", ""));
		}
		if(Context.getAdministrationService().getGlobalPropertyObject(CommonMetadata.GP_CLINICAL_ACTION_HEI_ABSOLUTE_THRESHOLD) == null) {
			install(globalProperty(GP_CLINICAL_ACTION_HEI_ABSOLUTE_THRESHOLD, "Absolute threshold for clinical action for HEI", ""));
		}

		install(patientIdentifierType("Old Identification Number", "Identifier given out prior to OpenMRS",
				null, null, null,
				LocationBehavior.NOT_USED, false, _PatientIdentifierType.OLD_ID));
		install(patientIdentifierType("OpenMRS ID", "Medical Record Number generated by OpenMRS for every patient",
				null, null, LuhnMod25IdentifierValidator.class,
				LocationBehavior.REQUIRED, true, _PatientIdentifierType.OPENMRS_ID));
		install(patientIdentifierType("Patient Clinic Number",
				"Assigned to the patient at a clinic service (not globally unique)",
				".{1,15}", "At most 15 characters long", null,
				LocationBehavior.NOT_USED, false, _PatientIdentifierType.PATIENT_CLINIC_NUMBER));
		install(patientIdentifierType("National ID", "Kenyan national identity card number",
				"\\d{5,18}", "Between 5 and 18 consecutive digits", null,
				LocationBehavior.NOT_USED, false, _PatientIdentifierType.NATIONAL_ID));
		install(patientIdentifierType("National Unique patient identifier", "National Unique patient identifier",
				".{1,14}", "At most 14 characters long", null,
				LocationBehavior.NOT_USED, false, _PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER));
		install(patientIdentifierType("CWC Number",
				"Assigned to a child patient when enrolling into the Child Welfare Clinic (CWC)",
				".{1,14}", "Should take the format (CWC-MFL code-serial number) e.g CWC-15007-00001", null,
				LocationBehavior.NOT_USED, false, _PatientIdentifierType.CWC_NUMBER));
		install(patientIdentifierType("Service number", "Unique Id for KDoD service men",
				"^[0-9]{5,6}$|^[0-9]{5,6}\\/[0-9]{2}$",
				"Must be a 5-6 digit number (for principal) or 5-6 digit number followed by / and 2 digits (for dependant)",
				null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.KDoD_SERVICE_NUMBER));

		install(patientIdentifierType("Client Number", "A partner specific identification for clients", "", "",
				null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.CLIENT_NUMBER));
		install(patientIdentifierType("Huduma Number", "Kenyan huduma number", "^[a-zA-Z0-9]+$",
				"Allows for alphanumeric format",
				null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.HUDUMA_NUMBER));
		install(patientIdentifierType("Passport Number", "Passport number", "^[a-zA-Z0-9]+$",
				"Allows for alphanumeric format",
				null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.PASSPORT_NUMBER));
		install(patientIdentifierType("Birth Certificate Number", "Birth certificate number for client",
				"^[a-zA-Z0-9]+$", "Allows for alphanumeric format",
				null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.BIRTH_CERTIFICATE_NUMBER));
		install(patientIdentifierType("Alien ID Number", "Alien ID number for client", "^[a-zA-Z0-9]+$",
				"Allows for alphanumeric format",
				null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.ALIEN_ID_NUMBER));
		install(patientIdentifierType("Driving License Number", "Driving License number for client", "^[a-zA-Z0-9]+$",
				"Allows for alphanumeric format",
				null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.DRIVING_LICENSE));
		install(patientIdentifierType("Recency Testing ID", "Recency Testing ID", "", "Allows for alphanumeric format",
				null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.RECENCY_TESTING_ID));
		install(patientIdentifierType("Social Health Insurance Number", "Social Health Insurance Number", "",
				"Allows for alphanumeric format",
				null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.SOCIAL_HEALTH_INSURANCE_NUMBER));
		install(patientIdentifierType("Social Health Authority Identification Number",
				"Social Health Authority Unique Identification Number", "", "Allows for alphanumeric format",
				null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.SHA_UNIQUE_IDENTIFICATION_NUMBER));
		install(patientIdentifierType("Publication Number",
				"Uniquely identifies military dependents, aiding in organizing and linking them to service members", "",
				"Allows for alphanumeric format",
				null, LocationBehavior.NOT_USED, false, _PatientIdentifierType.KDOD_PUBLICATION_NUMBER));

		install(personAttributeType("Telephone contact", "Telephone contact number",
				String.class, null, true, 1.0, _PersonAttributeType.TELEPHONE_CONTACT));
		install(personAttributeType("Email address", "Email address of person",
				String.class, null, false, 2.0, _PersonAttributeType.EMAIL_ADDRESS));
		install(personAttributeType("CHT username", "CHT username reference",
				String.class, null, false, 4.4, _PersonAttributeType.CHT_USERNAME));

		// Patient only person attributes..
		install(personAttributeType("Subchief name", "Name of subchief or chief of patient's area",
				String.class, null, false, 3.0, _PersonAttributeType.SUBCHIEF_NAME));
		install(personAttributeType("Next of kin name", "Name of patient's next of kin",
				String.class, null, false, 4.0, _PersonAttributeType.NEXT_OF_KIN_NAME));
		install(personAttributeType("Next of kin relationship", "Next of kin relationship to the patient",
				String.class, null, false, 4.1, _PersonAttributeType.NEXT_OF_KIN_RELATIONSHIP));
		install(personAttributeType("Next of kin contact", "Telephone contact of patient's next of kin",
				String.class, null, false, 4.2, _PersonAttributeType.NEXT_OF_KIN_CONTACT));
		install(personAttributeType("Next of kin address", "Address of patient's next of kin",
				String.class, null, false, 4.3, _PersonAttributeType.NEXT_OF_KIN_ADDRESS));
		install(personAttributeType("Alternate Phone Number", "Patient's alternate phone number",
				String.class, null, false, 4.3, _PersonAttributeType.ALTERNATE_PHONE_CONTACT));
		install(personAttributeType("Nearest Health Facility", "Patient's nearest Health Facility",
				String.class, null, false, 4.3, _PersonAttributeType.NEAREST_HEALTH_CENTER));
		// guardian properties
		install(personAttributeType("Guardian First Name", "Guardian's first name",
				String.class, null, false, 4.3, _PersonAttributeType.GUARDIAN_FIRST_NAME));
		install(personAttributeType("Guardian Last Name", "Guardian's last name",
				String.class, null, false, 4.3, _PersonAttributeType.GUARDIAN_LAST_NAME));
		// KDoD properties
		install(personAttributeType("KDoD cadre", "Cadre in KDoD",
				String.class, null, false, 4.5, _PersonAttributeType.KDOD_CADRE));
		install(personAttributeType("KDoD rank", "Rank in KDoD",
				String.class, null, false, 4.5, _PersonAttributeType.KDOD_RANK));
		install(personAttributeType("KDoD unit", "KDoD passout unit",
				String.class, null, false, 4.5, _PersonAttributeType.KDOD_UNIT));
		install(personAttributeType("KDoD civilian rank", "KDOD classification for civilian staff",
				String.class, null, false, 4.5, _PersonAttributeType.KDOD_CIVILIAN_RANK));
		install(personAttributeType("KDoD Service", "Service in KDoD e.g Army, Navy, Airforce",
				String.class, null, false, 4.5, _PersonAttributeType.KDOD_SERVICE));

		// Client Registry Properties
		install(personAttributeType("cr verification status", "Verification status with national registry",
				String.class, null, false, 4.5, _PersonAttributeType.VERIFICATION_STATUS_WITH_NATIONAL_REGISTRY));

		install(personAttributeType("cr verification message", "Verification message with national registry",
				String.class, null, false, 4.5, _PersonAttributeType.VERIFICATION_MESSAGE_WITH_NATIONAL_REGISTRY));

		install(personAttributeType("cr ccc sync status", "CCC Sync status with national registry",
				String.class, null, false, 4.5, _PersonAttributeType.CCC_SYNC_STATUS_WITH_NATIONAL_REGISTRY));

		install(personAttributeType("cr ccc sync message", "CCC Sync message with national registry",
				String.class, null, false, 4.5, _PersonAttributeType.CCC_SYNC_MESSAGE_WITH_NATIONAL_REGISTRY));

		install(personAttributeType("cr verification iprs error description ",
				"CR Verification error description from IPRS",
				String.class, null, false, 4.5, _PersonAttributeType.VERIFICATION_DESCRIPTION_FOR_IPRS_ERROR));

		install(personAttributeType("nupi duplication status", "NUPI Duplication status with national registry",
				String.class, null, false, 4.5, _PersonAttributeType.DUPLICATE_NUPI_STATUS_WITH_NATIONAL_REGISTRY));

		install(personAttributeType("nupi duplication facility", "NUPI Duplication facility with national registry",
				String.class, null, false, 4.5, _PersonAttributeType.DUPLICATE_NUPI_FACILITY_WITH_NATIONAL_REGISTRY));

		install(personAttributeType("nupi duplication sites", "NUPI Duplication site names with national registry",
				String.class, null, false, 4.5, _PersonAttributeType.DUPLICATE_NUPI_SITES_WITH_NATIONAL_REGISTRY));

		install(personAttributeType("nupi duplication total sites",
				"NUPI Duplication total number of sites with national registry",
				String.class, null, false, 4.5, _PersonAttributeType.DUPLICATE_NUPI_TOTALSITES_WITH_NATIONAL_REGISTRY));
		install(personAttributeType("PNS Approach", "PNS Approach",
				String.class, null, false, 4.5, _PersonAttributeType.PNS_APPROACH));
		install(personAttributeType("PNS Baseline HIV Status", "PNS Baseline HIV Status",
				String.class, null, false, 4.5, _PersonAttributeType.PNS_PATIENT_CONTACT_BASELINE_HIV_STATUS));
		install(personAttributeType("Contact living with patient", "Contact living  with patient",
				String.class, null, false, 4.5, _PersonAttributeType.PNS_PATIENT_CONTACT_LIVING_WITH_PATIENT));
		install(personAttributeType("Contact registration from PNS", "Contact registration source from PNS",
				String.class, null, false, 4.5, _PersonAttributeType.PNS_PATIENT_CONTACT_REGISTRATION_SOURCE));
		install(personAttributeType("Contact IPV Outcome", "Contact IPV Outcome",
				String.class, null, false, 4.5, _PersonAttributeType.PNS_PATIENT_CONTACT_IPV_OUTCOME));

		// Provider attribute types.
		install(providerAttributeType("Primary Facility", "Default facility for a provider", LocationDatatype.class, "",
				0, 9999, _ProviderAttributeType.PRIMARY_FACILITY));
		install(providerAttributeType("Practising License Number", "Provider Practising License Number",
				FreeTextDatatype.class, "", 0, 9999, _ProviderAttributeType.LICENSE_NUMBER));
		install(providerAttributeType("License Expiry Date", "Provider Practising License Expiry Date",
				DateDatatype.class, "", 0, 9999, _ProviderAttributeType.LICENSE_EXPIRY_DATE));
		install(providerAttributeType("Provider National Id Number", "Provider National Id Number",
				FreeTextDatatype.class, "", 0, 9999, _ProviderAttributeType.NATIONAL_ID));
		install(providerAttributeType("License Body", "", FreeTextDatatype.class, "", 0, 9999,
				_ProviderAttributeType.LICENSE_BODY));
		install(providerAttributeType("Provider HIE FHIR Reference", "", FreeTextDatatype.class, "", 0, 9999,
				_ProviderAttributeType.PROVIDER_HIE_FHIR_REFERENCE));
		install(providerAttributeType("Provider Qualification", "", FreeTextDatatype.class, "", 0, 9999,
			_ProviderAttributeType.PROVIDER_QUALIFICATION));
		install(providerAttributeType("Provider Address", "", FreeTextDatatype.class, "", 0, 9999,
			_ProviderAttributeType.PROVIDER_ADDRESS));
		install(providerAttributeType("Provider Telephone", "", FreeTextDatatype.class, "", 0, 9999,
			_ProviderAttributeType.PROVIDER_TELEPHONE));
		install(providerAttributeType("Provider passport number", "Provider passport number", FreeTextDatatype.class, "", 0, 9999,
			_ProviderAttributeType.PROVIDER_PASSPORT_NUMBER));

		install(relationshipType("Guardian", "Dependant", "One that guards, watches over, or protects",
				_RelationshipType.GUARDIAN_DEPENDANT));
		install(relationshipType("Spouse", "Spouse",
				"A spouse is a partner in a marriage, civil union, domestic partnership or common-law marriage a male spouse is a husband and a female spouse is a wife",
				_RelationshipType.SPOUSE));
		install(relationshipType("Partner", "Partner",
				"Someone I had sex with for fun without commitment to a relationship", _RelationshipType.PARTNER));
		install(relationshipType("Co-wife", "Co-wife", "Female member spouse in a polygamist household",
				_RelationshipType.CO_WIFE));
		install(relationshipType("SNS", "SNS", "Social Network Strategy", _RelationshipType.SNS));
		install(relationshipType("Case manager", "Client", "Case manager", _RelationshipType.CASE_MANAGER));
		install(relationshipType("Primary caregiver", "Primary caregiver", "Primary caregiver",
				_RelationshipType.CARE_GIVER));

		install(visitAttributeType("Source form", "The form whose submission created the visit",
				FormDatatype.class, null, 0, 1, _VisitAttributeType.SOURCE_FORM));

		install(visitAttributeType("Visit queue number",
				"The visit queue number assigned to a visit when they are added to the queue", FreeTextDatatype.class,
				null, 0, 1, _VisitAttributeType.VISIT_QUEUE_NUMBER));
		install(visitAttributeType("Patient Type", "To indicate whether the patient is paying for a service",
				FreeTextDatatype.class, null, 0, 1, _VisitAttributeType.PATIENT_TYPE_UUID));
		install(visitAttributeType("Payment Method", "The payment method used by the patient to settle payment",
				FreeTextDatatype.class, null, 0, 1, _VisitAttributeType.PAYMENT_METHOD_UUID));
		install(visitAttributeType("Policy Number", "The insurance policy number or member number",
				FreeTextDatatype.class, null, 0, 1, _VisitAttributeType.POLICY_NUMBER));
		install(visitAttributeType("Insurance scheme",
				"The insurance scheme the patient is using to settle payment for services e.g. NHIF, Old mutual.",
				FreeTextDatatype.class, null, 0, 1, _VisitAttributeType.INSURANCE_SCHEME));
		install(visitAttributeType("SHA Benefits Package", "SHA benefits package the patient is entitled to",
				FreeTextDatatype.class, null, 0, 1, _VisitAttributeType.SHA_BENEFITS_PACKAGE));

		install(visitType("Outpatient", "Visit where the patient is not admitted to the hospital",
				_VisitType.OUTPATIENT));
		install(visitType("Inpatient", "Visit where the patient is admitted to the hospital", _VisitType.INPATIENT));
		uninstall(possible(PersonAttributeType.class, "73d34479-2f9e-4de3-a5e6-1f79a17459bb"),
				"Became patient identifier"); // National ID attribute type

		//Retiring Lab results form
		uninstall(possible(Form.class, "7e603909-9ed5-4d0c-a688-26ecb05d8b6e"), "Form deprecated with introduction of Lab orders");

		install(globalProperty(GP_KENYAEMR_VERSION, "The version of the installed KenyaEMR",
				null));
	}
}
