/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.openmrs.CareSetting;
import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.GlobalProperty;
import org.openmrs.Location;
import org.openmrs.LocationAttributeType;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.Provider;
import org.openmrs.User;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.util.PrivilegeConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Relationship;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

/**
 * Miscellaneous utility methods
 */
public class EmrUtils {
	protected static final Log log = LogFactory.getLog(EmrUtils.class);
	public static String GP_2X_FORMS_WHITELIST = "kenyaemr.2.x.forms.whitelist";
	private static final String DEFAULT_GLOBAL_PROPERTY_KEY = "facility.mflcode";
	private static final AdministrationService administrationService = Context.getAdministrationService();
	public static final Integer LOCATION_ID = Integer.parseInt(administrationService.getGlobalProperty("kenyaemr.defaultLocation"));
	/**
	 * Checks whether a date has any time value
	 * @param date the date
	 * @return true if the date has time
	 * @should return true only if date has time
	 */
	public static boolean dateHasTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR) != 0 || cal.get(Calendar.MINUTE) != 0 || cal.get(Calendar.SECOND) != 0 || cal.get(Calendar.MILLISECOND) != 0;
	}

	/**
	 * Checks if a given date is today
	 * @param date the date
	 * @return true if date is today
	 */
	public static boolean isToday(Date date) {
		return DateUtils.isSameDay(date, new Date());
	}

	/**
	 * Converts a WHO stage concept to a WHO stage number
	 * @param c the WHO stage concept
	 * @return the WHO stage number (null if the concept isn't a WHO stage)
	 */
	public static Integer whoStage(Concept c) {
		if (c != null) {
			if (c.equals(Dictionary.getConcept(Dictionary.WHO_STAGE_1_ADULT)) || c.equals(Dictionary.getConcept(Dictionary.WHO_STAGE_1_PEDS))) {
				return 1;
			}
			if (c.equals(Dictionary.getConcept(Dictionary.WHO_STAGE_2_ADULT)) || c.equals(Dictionary.getConcept(Dictionary.WHO_STAGE_2_PEDS))) {
				return 2;
			}
			if (c.equals(Dictionary.getConcept(Dictionary.WHO_STAGE_3_ADULT)) || c.equals(Dictionary.getConcept(Dictionary.WHO_STAGE_3_PEDS))) {
				return 3;
			}
			if (c.equals(Dictionary.getConcept(Dictionary.WHO_STAGE_4_ADULT)) || c.equals(Dictionary.getConcept(Dictionary.WHO_STAGE_4_PEDS))) {
				return 4;
			}
		}
		return null;
	}

	/**
	 * Parses a CSV list of strings, returning all trimmed non-empty values
	 * @param csv the CSV string
	 * @return the concepts
	 */
	public static List<String> parseCsv(String csv) {
		List<String> values = new ArrayList<String>();

		for (String token : csv.split(",")) {
			token = token.trim();

			if (!StringUtils.isEmpty(token)) {
				values.add(token);
			}
		}
		return values;
	}

	/**
	 * Parses a CSV list of concept ids, UUIDs or mappings
	 * @param csv the CSV string
	 * @return the concepts
	 */
	public static List<Concept> parseConcepts(String csv) {
		List<String> identifiers = parseCsv(csv);
		List<Concept> concepts = new ArrayList<Concept>();

		for (String identifier : identifiers) {
			if (StringUtils.isNumeric(identifier)) {
				concepts.add(Context.getConceptService().getConcept(Integer.valueOf(identifier)));
			}
			else {
				concepts.add(Dictionary.getConcept(identifier));
			}
		}
		return concepts;
	}

	/**
	 * Unlike in OpenMRS core, a user can only be one provider in KenyaEMR
	 * @param user the user
	 * @return the provider or null
	 */
	public static Provider getProvider(User user) {
		Person person = user.getPerson();
		Collection<Provider> providers = Context.getProviderService().getProvidersByPerson(person);
		return providers.size() > 0 ? providers.iterator().next() : null;
	}

	/**
	 * Finds the last encounter during the program enrollment with the given encounter type
	 *
	 * @param type the encounter type
	 *
	 * @return the encounter
	 */
	public static Encounter lastEncounter(Patient patient, EncounterType type) {
		List<Encounter> encounters = Context.getEncounterService().getEncounters(patient, null, null, null, null, Collections.singleton(type), null, null, null, false);
		return encounters.size() > 0 ? encounters.get(encounters.size() - 1) : null;
	}

	public static Encounter lastEncounter(Patient patient, EncounterType type, Form form) {
		List<Encounter> encounters = Context.getEncounterService().getEncounters(patient, null, null, null, Collections.singleton(form), Collections.singleton(type), null, null, null, false);
		return encounters.size() > 0 ? encounters.get(encounters.size() - 1) : null;
	}

	public static Encounter lastEncounter(Patient patient, EncounterType type, List<Form> forms) {
		List<Encounter> encounters = Context.getEncounterService().getEncounters(patient, null, null, null, forms, Collections.singleton(type), null, null, null, false);
		return encounters.size() > 0 ? encounters.get(encounters.size() - 1) : null;
	}

	public static List<Encounter> AllEncounters(Patient patient, EncounterType type, Form form) {
		List<Encounter> encounters = Context.getEncounterService().getEncounters(patient, null, null, null, Collections.singleton(form), Collections.singleton(type), null, null, null, false);
		return encounters;
	}

	/**
	 * Finds the first encounter during the program enrollment with the given encounter type
	 *
	 * @param type the encounter type
	 *
	 * @return the encounter
	 */
	public static Encounter firstEncounter(Patient patient, EncounterType type) {
		List<Encounter> encounters = Context.getEncounterService().getEncounters(patient, null, null, null, null, Collections.singleton(type), null, null, null, false);
		return encounters.size() > 0 ? encounters.get(0) : null;
	}

	/**
	 * Finds the last encounter of a given type entered via a given form.
	 *
	 * @param encounterType the type of encounter
	 * @param form          the form through which the encounter was entered.
	 */
	public static Encounter encounterByForm(Patient patient, EncounterType encounterType, Form form) {
		List<Form> forms = null;
		if (form != null) {
			forms = new ArrayList<Form>();
			forms.add(form);
		}
		EncounterService encounterService = Context.getEncounterService();
		List<Encounter> encounters = encounterService.getEncounters
				(
						patient,
						null,
						null,
						null,
						forms,
						Collections.singleton(encounterType),
						null,
						null,
						null,
						false
				);
		return encounters.size() > 0 ? encounters.get(encounters.size() - 1) : null;
	}

	/**
	 *
	 * @param patient
	 * @param careSetting
	 * @return
	 */
	public static List<DrugOrder> drugOrdersFromOrders(Patient patient, CareSetting careSetting) {

		OrderType drugOrderType = Context.getOrderService().getOrderTypeByUuid(OrderType.DRUG_ORDER_TYPE_UUID);
		List<Order> allDrugOrders = null;
		if (careSetting == null) {
			allDrugOrders = Context.getOrderService().getAllOrdersByPatient(patient);
		} else {
			allDrugOrders = Context.getOrderService().getOrders(patient, careSetting, drugOrderType, false);
		}

		List<DrugOrder> drugOrdersOnly = new ArrayList<DrugOrder>();
		for (Order o:allDrugOrders) {
			DrugOrder order = null;
			if (o.getOrderType().equals(drugOrderType)) {
				order = (DrugOrder)o;
				drugOrdersOnly.add(order);
			}

		}
		return drugOrdersOnly;
	}

	public static ObjectNode getDatasetMappingForReport(String reportName, String mappingString) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		ArrayNode conf = (ArrayNode) mapper.readTree(mappingString);

		for (Iterator<JsonNode> it = conf.iterator(); it.hasNext(); ) {
			ObjectNode node = (ObjectNode) it.next();
			if (node.get("reportName").asText().equals(reportName)) {
				return node;
			}
		}

		return null;
	}

	public static boolean encounterThatPassCodedAnswer(Encounter enc, Concept question, Concept answer) {
		boolean passed = false;
		for (Obs obs : enc.getAllObs()) {
			if (obs.getConcept().getConceptId().intValue() == question.getConceptId().intValue()
					&& obs.getValueCoded().getConceptId().intValue() == answer.getConceptId().intValue()) {
				passed = true;
				break;
			}
		}
		return passed;
	}

	/**
	 * a helper method that checks if an encounter has obs for a concept
	 * @param enc
	 * @param question
	 * @return
	 */
	public static boolean encounterHasObsForConcept(Encounter enc, Concept question) {
		boolean passed = false;
		for (Obs obs : enc.getAllObs()) {
			if (obs.getConcept().getConceptId().intValue() == question.getConceptId().intValue()) {
				passed = true;
				break;
			}
		}
		return passed;
	}

	public static List<String> getSubCountyList() {
		List<String> subCountyList = new ArrayList<String>();
		Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		StringBuilder q = new StringBuilder();
		q.append("Select distinct implementation_subcounty ");
		q.append("from kenyaemr_etl.etl_contact;");

		List<List<Object>> subCounties = Context.getAdministrationService().executeSQL(q.toString(), true);
		if (!subCounties.isEmpty()) {
			for (List<Object> res : subCounties) {
				String location = (String) res.get(0);
				subCountyList.add(location);
			}
		}
		Context.removeProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		return subCountyList;
	}

	public static List<Person> getPersonChildren(Patient patient) {
		List<Person> people = new ArrayList<Person>();
		for (Relationship relationship : Context.getPersonService().getRelationshipsByPerson(patient)) {
			if (relationship.getRelationshipType().getbIsToA().equals("Child")) {
				people.add(relationship.getPersonB());
			}
		}
		return people;
	}

	/**
	 * A temporary solution for whitelisting forms to show in 2.x
	 * TODO: retire this once all forms are fully moved to o3
	 * @return
	 */
	public static List<String> getFormsToShowInLegacyUI() {
		GlobalProperty gpFormsWhitelist = Context.getAdministrationService().getGlobalPropertyObject(GP_2X_FORMS_WHITELIST);

		String formsWhiteList = "";
		List<String> formsList = new ArrayList<String>();
		if (gpFormsWhitelist != null) {
			formsWhiteList = gpFormsWhitelist.getPropertyValue();
			if (StringUtils.isNotBlank(formsWhiteList)) {
				formsList = Arrays.asList(formsWhiteList.split(","));
			}
		}
		return formsList;

	}
	public static Date getLatestAppointmentDateForService (Integer patientId, Integer appointmentServiceId) {
		Context.addProxyPrivilege(PrivilegeConstants.SQL_LEVEL_ACCESS);
		Date appointmentDate = null;
		String sql = "SELECT MAX(start_date_time) AS appointment_date FROM patient_appointment WHERE" + " patient_id =" + patientId + " AND appointment_service_id =" +appointmentServiceId;
		String lastAppointmentDate = null;
		try {
			List<List<Object>> result = Context.getAdministrationService().executeSQL(sql, true);
			if (result != null && !result.isEmpty() && result.get(0) != null && result.get(0).get(0) != null) {
				lastAppointmentDate = result.get(0).get(0).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(lastAppointmentDate != null) {
			LocalDateTime localDateTime = LocalDateTime.parse(lastAppointmentDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			appointmentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

		}


		return appointmentDate;
	}

	/**
	 * Helper method that receives a List of Strings and returns a String
	 * of comma-separated and single-quoted input list elements
	 * @param list
	 * @return
	 */
	public static String formatListWithQuotes(ArrayList<String> list) {
		StringJoiner joiner = new StringJoiner(", ");
		for (String value : list) {
			joiner.add("'" + value + "'");
		}
		return joiner.toString();
	}

	/**
	 * Helper method for getting mfl code for a facility
	 * @return
	 */
	public static String getMFLCode() {

		KenyaEmrService kenyaEmrService = Context.getService(KenyaEmrService.class);
		String defaultMflCode = null;
		String globalPropertyMflCode = null;

		try {
			// Fetch the default MFL code from the KenyaEmrService
			defaultMflCode = kenyaEmrService != null ? kenyaEmrService.getDefaultLocationMflCode() : null;
			if (defaultMflCode != null) {
				defaultMflCode = defaultMflCode.trim();
			}

			// Fetch the global property for the MFL code
			globalPropertyMflCode = Context.getAdministrationService().getGlobalProperty(DEFAULT_GLOBAL_PROPERTY_KEY);
			if (globalPropertyMflCode != null) {
				globalPropertyMflCode = globalPropertyMflCode.trim();
			}
		} catch (Exception e) {
			log.error("Error retrieving MFL Code: {}", e);
		}

		// Return the global property MFL code if available, otherwise fallback to default
		return globalPropertyMflCode != null ? globalPropertyMflCode : defaultMflCode;
	}

	public static String getGlobalPropertyValue(String value) {
		GlobalProperty result = administrationService.getGlobalPropertyObject(value);
		return result.getPropertyValue() != null ? result.getPropertyValue().trim() : "";
	}

	/**
	 * Get the current location
	 *
	 * @return
	 */
	public static Location getDefaultLocation() {
		Location var2;
		try {
			Context.addProxyPrivilege(PrivilegeConstants.GET_LOCATIONS);
			Context.addProxyPrivilege(PrivilegeConstants.GET_GLOBAL_PROPERTIES);
			Context.addProxyPrivilege(PrivilegeConstants.MANAGE_LOCATIONS);
			String GP_DEFAULT_LOCATION = "kenyaemr.defaultLocation";
			GlobalProperty gp = Context.getAdministrationService().getGlobalPropertyObject(GP_DEFAULT_LOCATION);
			var2 = gp != null ? (Location) gp.getValue() : null;
		} finally {
			Context.removeProxyPrivilege(PrivilegeConstants.GET_LOCATIONS);
			Context.removeProxyPrivilege(PrivilegeConstants.GET_GLOBAL_PROPERTIES);
			Context.removeProxyPrivilege(PrivilegeConstants.MANAGE_LOCATIONS);
		}
		return var2;
	}

}