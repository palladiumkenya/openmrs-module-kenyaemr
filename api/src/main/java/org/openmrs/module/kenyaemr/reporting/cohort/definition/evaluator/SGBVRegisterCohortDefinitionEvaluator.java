/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.cohort.definition.evaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.SGBVRegisterCohortDefinition;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.evaluator.CohortDefinitionEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Evaluator for ActivePatientsSnapshotCohortDefinition
 * Includes patients who are active on ART.
 * Provides a snapshot of a patient with regard to the last visit
 */
@Handler(supports = {SGBVRegisterCohortDefinition.class})
public class SGBVRegisterCohortDefinitionEvaluator implements CohortDefinitionEvaluator {

    private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	EvaluationService evaluationService;

    @Override
    public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

		SGBVRegisterCohortDefinition definition = (SGBVRegisterCohortDefinition) cohortDefinition;

        if (definition == null)
            return null;

		Cohort newCohort = new Cohort();

		String qry="SELECT t.encounter_id FROM (\n" +
                                "SELECT a.encounter_id, a.patient_id, b.`patient_clinic_number`, a.`visit_date`, \n" +
                                "REPLACE(CONCAT(IFNULL(b.`family_name`,''), ' ',IFNULL(b.`given_name`,''), ' ', IFNULL(b.`middle_name`,'')), ',', '') AS patient_name,\n" +
                                "IFNULL(c.`address2`,c.`address5`) AS sublocation_landmark, b.`phone_number`,\n" +
                                "TIMESTAMPDIFF(YEAR,b.`DOB`,@stopdate) AS age, b.`Gender`, b.`marital_status`,\n" +
                                "IF(ovc.`program` = 'OVC', 'Yes', 'No') AS active_on_ovc,\n" +
                                "IF(a.`emotional_ipv`=1066, 'No', IF(a.`emotional_ipv`=118688, 'Yes', NULL)) emotional_ipv, \n" +
                                "IF(a.`physical_ipv`=1066, 'No', IF(a.`physical_ipv`=158358, 'Yes', NULL)) physical_ipv, \n" +
                                "IF(a.`sexual_ipv`=1066, 'No', IF(a.`sexual_ipv`=152370, 'Yes', NULL)) sexual_ipv,\n" +
                                "IF(prc.action_taken=127910, prc.date_created, NULL) prc_fill_date,\n" +
                                "IF(pth.action_taken=165228, pth.date_created, NULL) p3_fill_date,\n" +
                                "IF(hiv.`program` = 'HIV', 'Known Positive',\n" +
                                "IF(hts.final_test_result='Positive', 'Positive',\n" +
                                "IF(hts.final_test_result='Negative', 'Negative', 'Not Done'))) AS hiv_test,\n" +
                                "IF(ecp.action_taken=160570, 'Yes', 'No') given_ecp,\n" +
                                "IF(pep.action_taken=165171, 'Yes', 'No') given_pep,\n" +
                                "IF(sti.action_taken=165200, 'Yes', 'No') sti_treatment,\n" +
                                "IF(tra.action_taken=165184, 'Yes', 'No') trauma_counselling,\n" +
                                "fup.`next_appointment_date`\n" +
                                "FROM kenyaemr_etl.`etl_gbv_screening` a\n" +
                                "LEFT JOIN kenyaemr_etl.`etl_patient_demographics` b ON a.`patient_id`=b.`patient_id`\n" +
                                "LEFT JOIN openmrs.`person_address` c ON a.`patient_id`=c.`person_id`\n" +
                                "LEFT JOIN (SELECT patient_id, pg.program\n" +
                                "		FROM kenyaemr_etl.`etl_patient_program` pg WHERE pg.`program`='OVC' AND pg.`date_completed` IS NULL) ovc ON a.patient_id = ovc.patient_id\n" +
                                "LEFT JOIN (SELECT patient_id, pg.program\n" +
                                "		FROM kenyaemr_etl.`etl_patient_program` pg WHERE pg.`program`='HIV' AND pg.`date_completed` IS NULL) hiv ON a.patient_id = hiv.patient_id\n" +
                                "LEFT JOIN (SELECT gsa.`visit_id`, gsa.`action_taken`, DATE(gsa.`date_created`) date_created\n" +
                                "		FROM kenyaemr_etl.`etl_gbv_screening_action` gsa \n" +
                                "		WHERE gsa.`action_taken` = 127910 AND gsa.`visit_date` BETWEEN date(:startDate) AND date(:endDate)) prc ON a.`visit_id` = prc.visit_id\n" +
                                "LEFT JOIN (SELECT gsa.`visit_id`, gsa.`action_taken`, DATE(gsa.`date_created`) date_created\n" +
                                "		FROM kenyaemr_etl.`etl_gbv_screening_action` gsa \n" +
                                "		WHERE gsa.`action_taken` = 165228 AND gsa.`visit_date` BETWEEN date(:startDate) AND date(:endDate)) pth ON a.`visit_id` = pth.visit_id\n" +
                                "LEFT JOIN (SELECT gsa.`visit_id`, gsa.`action_taken`, DATE(gsa.`date_created`) date_created\n" +
                                "		FROM kenyaemr_etl.`etl_gbv_screening_action` gsa \n" +
                                "		WHERE gsa.`action_taken` = 160570 AND gsa.`visit_date` BETWEEN date(:startDate) AND date(:endDate)) ecp ON a.`visit_id` = ecp.visit_id\n" +
                                "LEFT JOIN (SELECT gsa.`visit_id`, gsa.`action_taken`, DATE(gsa.`date_created`) date_created\n" +
                                "		FROM kenyaemr_etl.`etl_gbv_screening_action` gsa \n" +
                                "		WHERE gsa.`action_taken` = 165171 AND gsa.`visit_date` BETWEEN date(:startDate) AND date(:endDate)) pep ON a.`visit_id` = pep.visit_id\n" +
                                "LEFT JOIN (SELECT gsa.`visit_id`, gsa.`action_taken`, DATE(gsa.`date_created`) date_created\n" +
                                "		FROM kenyaemr_etl.`etl_gbv_screening_action` gsa \n" +
                                "		WHERE gsa.`action_taken` = 165200 AND gsa.`visit_date` BETWEEN date(:startDate) AND date(:endDate)) sti ON a.`visit_id` = sti.visit_id\n" +
                                "LEFT JOIN (SELECT gsa.`visit_id`, gsa.`action_taken`, DATE(gsa.`date_created`) date_created\n" +
                                "		FROM kenyaemr_etl.`etl_gbv_screening_action` gsa \n" +
                                "		WHERE gsa.`action_taken` = 165184 AND gsa.`visit_date` BETWEEN date(:startDate) AND date(:endDate)) tra ON a.`visit_id` = tra.visit_id\n" +
                                "LEFT JOIN (SELECT gsa.`visit_id`, gsa.`next_appointment_date`\n" +
                                "		FROM kenyaemr_etl.`etl_patient_hiv_followup` gsa) fup ON a.`visit_id` = fup.visit_id\n" +
                                "LEFT JOIN (SELECT ht.patient_id, ht.`final_test_result` FROM kenyaemr_etl.`etl_hts_test` ht WHERE ht.`test_type`=1) hts ON a.`patient_id`=hts.patient_id\n" +
                                "\n" +
                                "WHERE a.`visit_date` BETWEEN date(:startDate) AND date(:endDate)\n" +
                                "AND (a.`physical_ipv`= 158358 OR a.`sexual_ipv`=152370 OR a.`emotional_ipv`=118688)\n" +
                                "GROUP BY a.patient_id, a.`visit_id`) t;";

		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.append(qry);
		Date startDate = (Date)context.getParameterValue("startDate");
		Date endDate = (Date)context.getParameterValue("endDate");
		builder.addParameter("startDate", startDate);
		builder.addParameter("endDate", endDate);

		List<Integer> ptIds = evaluationService.evaluateToList(builder, Integer.class, context);
		newCohort.setMemberIds(new HashSet<Integer>(ptIds));
		return new EvaluatedCohort(newCohort, definition, context);
    }
}
