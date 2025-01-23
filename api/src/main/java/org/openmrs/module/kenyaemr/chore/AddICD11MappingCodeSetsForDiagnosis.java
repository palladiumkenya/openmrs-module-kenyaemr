/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.chore;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.chore.AbstractChore;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Update ICD 11 Mappings for Diagnosis
 * Uses DiagnosisICD10ToICD11Mapper
 */
@Component("kenyaemr.chore.AddICD11MappingCodeSetsForDiagnosis")
public class AddICD11MappingCodeSetsForDiagnosis extends AbstractChore {

	/**
	 * @see AbstractChore#perform(PrintWriter)
	 */

	@Override
	public void perform(PrintWriter out) {

		ObjectMapper mapper = new ObjectMapper();
		ArrayNode conf = null;
		try {
			System.out.println("Retrieving ICD code mapper");
			conf = (ArrayNode) mapper.readTree(icd11DiagnosisCodeJson());
			if (conf != null) {
				System.out.println("ICD code mapper found");
				for (Iterator<JsonNode> it = conf.iterator(); it.hasNext(); ) {
					ObjectNode node = (ObjectNode) it.next();

					String conceptId = node.get("ConceptId").asText();
					String icd11code = node.get("ICD11Code").asText();

					String updateConceptSql = "Insert into concept_reference_map (\n" +
						"    concept_map_id,\n" +
						"    creator,\n" +
						"    date_created,\n" +
						"    concept_id,\n" +
						"    uuid,\n" +
						"    concept_reference_term_id,\n" +
						"    concept_map_type_id,\n" +
						"    changed_by,\n" +
						"    date_changed)\n" +
						"select null,\n" +
						"       1,\n" +
						"       CURDATE(),\n" +
						"      " + conceptId + ",\n" +
						"       UUID(),\n" +
						"       rt.concept_reference_term_id,\n" +
						"       1,\n" +
						"       null,\n" +
						"       null\n" +
						"from concept_reference_term rt\n" +
						"left join openmrs.concept_reference_map crm on rt.concept_reference_term_id = crm.concept_reference_term_id\n" +
						"where rt.code = '" + icd11code + "' and crm.concept_reference_term_id is null;";

					Context.getAdministrationService().executeSQL(updateConceptSql, false);

				}
			}
		} catch (IOException e) {
			out.println("Failed to update ICD 11 codes");
			throw new RuntimeException(e);
		}
		out.println("Successfully updated ICD 11 codes");
	}

	private static String icd11DiagnosisCodeJson() {
		String json = "[\n" +
			"    {\n" +
			"        \"DiagnosisName\": \"Headache, not elsewhere classified\",\n" +
			"        \"ConceptId\": \"139084\",\n" +			
			"        \"ICD11Code\": \"8A8Z\"\n" +
			"    },\n" +
			"    {\n" +
			"        \"DiagnosisName\": \"Malaria due to Plasmodium falciparum\",\n" +
			"        \"ConceptId\": \"116128\",\n" +		
			"        \"ICD11Code\": \"1F40\"\n" +
			"    },\n" +
			"    {\n" +
			"        \"DiagnosisName\": \"Typhoid fever\",\n" +
			"        \"ConceptId\": \"141\",\n" +			
			"        \"ICD11Code\": \"1A07.Z\"\n" +
			"    },\n" +
			"    {\n" +
			"        \"DiagnosisName\": \"Pneumonia, organism unspecified\",\n" +
			"        \"ConceptId\": \"114100\",\n" +			
			"        \"ICD11Code\": \"CA40.Z\"\n" +
			"    },\n" +
			"    {\n" +
			"        \"DiagnosisName\": \"Fever of other or unknown origin\",\n" +
			"        \"ConceptId\": \"140238\",\n" +		
			"        \"ICD11Code\": \"MG26\"\n" +
			"    }\n" +
			"]";
		return json;
	}


}
