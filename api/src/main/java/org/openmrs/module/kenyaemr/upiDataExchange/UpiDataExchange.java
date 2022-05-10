/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.upiDataExchange;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAddress;
import org.openmrs.Program;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.PersonService;
import org.openmrs.api.ProgramWorkflowService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.kenyaemr.wrapper.PersonWrapper;
import org.openmrs.module.kenyaemrorderentry.util.Utils;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.util.PrivilegeConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.openmrs.module.kenyaemr.util.EmrUtils.getJsonNodeFactory;

public class UpiDataExchange {

	private Log log = LogFactory.getLog(UpiDataExchange.class);
	public static ConceptService conceptService = Context.getConceptService();
	/**
	 * Returns a single object details for patients
	 *
	 * @return
	 */
	public ObjectNode generatePayloadForUpi(Patient patient) {
		Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		ObjectNode payload = getJsonNodeFactory().objectNode();
		PersonService personService = Context.getPersonService();
		PatientWrapper patientWrapper = new PatientWrapper(patient);

		String dob = patient.getBirthdate() != null ? Utils.getSimpleDateFormat("yyyy-MM-dd").format(patient.getBirthdate()) : "";
		//get marital status
		Obs savedMaritalStatus = EmrUtils.getLatestObs(patient, Dictionary.CIVIL_STATUS);
		String maritalStatus = "";
		if (savedMaritalStatus != null) {
			maritalStatus = maritalStatusConverter(savedMaritalStatus.getValueCoded());
		}
		//get occupation
		Obs savedOccupation = EmrUtils.getLatestObs(patient, Dictionary.OCCUPATION);
		String occupation = "";
		if (savedMaritalStatus != null) {
			occupation = occupationTypeConverter(savedOccupation.getValueCoded());
		}
		//get highest level of education
		Obs savedEducationLevel = EmrUtils.getLatestObs(patient, Dictionary.EDUCATION);
		String education = "";
		if (savedEducationLevel != null) {
			education = educationLevelConverter(savedEducationLevel.getValueCoded());
		}
		ObjectNode address = getPatientAddress(patient);
		ObjectNode identifiers = getPatientIdentifiers(patient);
		ObjectNode contact = getPatientContactInformation(patient);
		ObjectNode nextOfKin = getPatientNextOfKins(patient);

			payload.put("clientNumber", patientWrapper.getUPINumber() != null ? patientWrapper.getUPINumber() : "");
	    	payload.put("firstName", patient.getFamilyName()!= null ? patient.getFamilyName() : "");
		    payload.put("middleName", patient.getMiddleName() != null ? patient.getMiddleName() : "");
		    payload.put("lastName", patient.getGivenName()!= null ? patient.getGivenName() : "");
		    payload.put("dateOfBirth", dob);
		    payload.put("gender", patient.getGender());
		    payload.put("maritalStatus", maritalStatus);
		    payload.put("occupation", occupation);
		    payload.put("religion", "");
		    payload.put("educationLevel", education);
              //Addresses
		    payload.put("country", "");
		    payload.put("countyOfBirth", "");
		    payload.put("county", address.get("county").textValue());
		    payload.put("subCounty", address.get("subCounty").textValue());
		    payload.put("ward", address.get("ward").textValue());
		    payload.put("village", address.get("village").textValue());
		    payload.put("landMark", address.get("landMark").textValue());
		    payload.put("address", address.get("address").textValue());
              //Identifiers
		    payload.put("identificationType", "National ID");
		    payload.put("identificationNumber:", identifiers.get("nationalId").textValue());
		    payload.put("identificationType", "Birth Certificate Number");
		    payload.put("identificationType", identifiers.get("birthCertificate").textValue());
		       //Contact infromation
		    payload.put("primaryPhone", contact.get("primaryPhone").textValue());
		    payload.put("secondaryPhone", contact.get("secondaryPhone").textValue());
		    payload.put("emailAddress:", contact.get("emailAddress").textValue());
		        //Get Next of kin
		    payload.put("name", nextOfKin.get("emailAddress").textValue());
		    payload.put("relationship", nextOfKin.get("nextOfKinRelationship").textValue());
		    payload.put("residence", nextOfKin.get("nextOfKinContact").textValue());


		return payload;
	}

	/**
	 * Returns a patient's address
	 *
	 * @param patient
	 * @return
	 */
	public static ObjectNode getPatientAddress(Patient patient) {
		Set<PersonAddress> addresses = patient.getAddresses();
		//patient address
		ObjectNode patientAddressNode = getJsonNodeFactory().objectNode();
		String county = "";
		String sub_county = "";
		String ward = "";
		String village = "";
		String landMark = "";
		String postAddress = "";
		for (PersonAddress address : addresses) {
			if (address.getCountyDistrict() != null) {
				county = address.getCountyDistrict() != null ? address.getCountyDistrict() : "";
			}
			if (address.getStateProvince() != null) {
				sub_county = address.getStateProvince() != null ? address.getStateProvince() : "";
			}
			if (address.getAddress4() != null) {
				ward = address.getAddress4() != null ? address.getAddress4() : "";
			}
			if (address.getCityVillage() != null) {
				village = address.getCityVillage() != null ? address.getCityVillage() : "";
			}
			if (address.getAddress2() != null) {
				landMark = address.getAddress2() != null ? address.getAddress2() : "";
			}
			if (address.getAddress1() != null) {
				postAddress = address.getAddress1() != null ? address.getAddress1() : "";
			}
		}

		patientAddressNode.put("county", county);
		patientAddressNode.put("subCounty", sub_county);
		patientAddressNode.put("ward", ward);
		patientAddressNode.put("village", village);
		patientAddressNode.put("landMark", landMark);
		patientAddressNode.put("address", postAddress);
		return patientAddressNode;
	}

	/**
	 * Returns a patient's identifiers
	 *
	 * @param patient
	 * @return
	 */
	public static ObjectNode getPatientIdentifiers(Patient patient) {
		Set<PatientIdentifier> identifiers = patient.getIdentifiers();
		//patient identifers
		ObjectNode patientIdentifierNode = getJsonNodeFactory().objectNode();
		PatientWrapper patientWrapper = new PatientWrapper(patient);
		PatientIdentifierType nationalIdType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.NATIONAL_ID);
		PatientIdentifierType passportIdType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.PASSPORT_NUMBER);
		PatientIdentifierType hudumaNumberIdType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.HUDUMA_NUMBER);
		PatientIdentifierType birthCertificateIdType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.BIRTH_CERTIFICATE_NUMBER);
		PatientIdentifierType alienIdType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.ALIEN_ID_NUMBER);
		PatientIdentifierType drivingLicenseIdType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.DRIVING_LICENSE);
		PatientIdentifierType patientClinicNumberType = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.PATIENT_CLINIC_NUMBER);

		String nationalId = "";
		String passportNumber = "";
		String hudumaNumber = "";
		String birthCertificate = "";
		String alienId = "";
		String drivingLicense = "";
		String patientClinicNumber = "";
		for (PatientIdentifier identifier : identifiers) {
			if (identifier.getIdentifierType().equals(nationalIdType)) {
				nationalId = identifier.getIdentifierType().getName();
			}
			if (identifier.getIdentifierType().equals(passportIdType)) {
				passportNumber = identifier.getIdentifierType().getName();
			}
			if (identifier.getIdentifierType().equals(hudumaNumberIdType)) {
				hudumaNumber = identifier.getIdentifierType().getName();
			}
			if (identifier.getIdentifierType().equals(birthCertificateIdType)) {
				birthCertificate = identifier.getIdentifierType().getName();
			}
			if (identifier.getIdentifierType().equals(alienIdType)) {
				alienId = identifier.getIdentifierType().getName();
			}
			if (identifier.getIdentifierType().equals(drivingLicenseIdType)) {
				drivingLicense = identifier.getIdentifierType().getName();
			}
			if (identifier.getIdentifierType().equals(patientClinicNumberType)) {
				patientClinicNumber = identifier.getIdentifierType().getName();
			}
		}
		patientIdentifierNode.put("nationalId", nationalId);
		patientIdentifierNode.put("passportNumber", passportNumber);
		patientIdentifierNode.put("hudumaNumber", hudumaNumber);
		patientIdentifierNode.put("birthCertificate", birthCertificate);
		patientIdentifierNode.put("alienId", alienId);
		patientIdentifierNode.put("drivingLicense", drivingLicense);
		patientIdentifierNode.put("patientClinicNumber", patientClinicNumber);

		return patientIdentifierNode;
	}

	/**
	 * Returns a patient's contacts
	 *
	 * @param patient
	 * @return
	 */
	public static ObjectNode getPatientContactInformation(Patient patient) {
		//patient contact details
		ObjectNode patientContactsNode = getJsonNodeFactory().objectNode();
		PersonWrapper personWrapper = new PersonWrapper(patient);
		String phoneNumber = personWrapper.getTelephoneContact() != null ? personWrapper.getTelephoneContact() : "";
		String emailAddress = personWrapper.getEmailAddress() != null ? personWrapper.getEmailAddress() : "";

		patientContactsNode.put("primaryPhone", phoneNumber);
		patientContactsNode.put("secondaryPhone", "");
		patientContactsNode.put("emailAddress", emailAddress);

		return patientContactsNode;
	}

	/**
	 * Returns a patient's next of kins
	 *
	 * @param patient
	 * @return
	 */
	public static ObjectNode getPatientNextOfKins(Patient patient) {
		//patient next of kins details
		ObjectNode patientNextOfKinNode = getJsonNodeFactory().objectNode();
		PatientWrapper patientWrapper = new PatientWrapper(patient);
		String nextOfKinName = patientWrapper.getNextOfKinName() != null ? patientWrapper.getNextOfKinName() : "";
		String nextOfKinRelationship = patientWrapper.getNextOfKinRelationship() != null ? patientWrapper.getNextOfKinRelationship() : "";
		String nextOfKinContact = patientWrapper.getNextOfKinContact() != null ? patientWrapper.getNextOfKinContact() : "";


		patientNextOfKinNode.put("nextOfKinName", nextOfKinName);
		patientNextOfKinNode.put("nextOfKinRelationship", nextOfKinRelationship);
		patientNextOfKinNode.put("nextOfKinContact", nextOfKinContact);

		return patientNextOfKinNode;
	}
	/*Maps a list of marital status answers by concepts  */
	static String maritalStatusConverter(Concept key) {
		Map<Concept, String> maritalStatusTypeList = new HashMap<Concept, String>();
		maritalStatusTypeList.put(conceptService.getConcept(159715), "Married polygamous");
		maritalStatusTypeList.put(conceptService.getConcept(5555), "Married monogamous");
		maritalStatusTypeList.put(conceptService.getConcept(1058), "Devorced");
		maritalStatusTypeList.put(conceptService.getConcept(1059), "Widowed");
		maritalStatusTypeList.put(conceptService.getConcept(1060), "Living with partner");
		maritalStatusTypeList.put(conceptService.getConcept(1057), "Never married");
		return maritalStatusTypeList.get(key);
	}

	/*Maps a list of occupations answers by concepts  */
	static String occupationTypeConverter(Concept key) {
		Map<Concept, String> occupationTypeList = new HashMap<Concept, String>();
		occupationTypeList.put(conceptService.getConcept(1538), "Farmer");
		occupationTypeList.put(conceptService.getConcept(1539), "Trader");
		occupationTypeList.put(conceptService.getConcept(1540), "Employee");
		occupationTypeList.put(conceptService.getConcept(159465), "Student");
		occupationTypeList.put(conceptService.getConcept(159466), "Driver");
		occupationTypeList.put(conceptService.getConcept(1107), "None");
		occupationTypeList.put(conceptService.getConcept(5622), "Other");
		return occupationTypeList.get(key);
	}
	/*Maps a list of highest level of education answers by concepts  */
	static String educationLevelConverter(Concept key) {
		Map<Concept, String> educationTypeList = new HashMap<Concept, String>();
		educationTypeList.put(conceptService.getConcept(1107), "None");
		educationTypeList.put(conceptService.getConcept(1713), "Primary");
		educationTypeList.put(conceptService.getConcept(1714), "Secondary");
		educationTypeList.put(conceptService.getConcept(159785), "College");
		educationTypeList.put(conceptService.getConcept(6522), "Other");
	  return educationTypeList.get(key);
	}

}
