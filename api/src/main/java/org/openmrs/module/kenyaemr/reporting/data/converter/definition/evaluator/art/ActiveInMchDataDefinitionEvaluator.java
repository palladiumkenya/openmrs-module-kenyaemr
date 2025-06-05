/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.art;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ActiveInMchDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLLastVLDateDataDefinition;
import org.openmrs.module.reporting.data.person.EvaluatedPersonData;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.data.person.evaluator.PersonDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Evaluates Active in MCH Data Definition
 */
@Handler(supports= ActiveInMchDataDefinition.class, order=50)
public class ActiveInMchDataDefinitionEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "SELECT \n" +
                "    a.patient_id,\n" +
                "    CASE \n" +
                "        WHEN a.bf = 1065 THEN 'Breastfeeding'\n" +
                "        WHEN a.pg = 1065 OR a.anc_client IS NOT NULL THEN 'Pregnant'\n" +
                "        ELSE 'No'\n" +
                "    END AS active_in_pmtct\n" +
                "FROM (\n" +
                "    SELECT \n" +
                "        f.patient_id,\n" +
                "        MID(MAX(CONCAT(f.visit_date, f.breastfeeding)), 11) AS bf,\n" +
                "        MID(MAX(CONCAT(f.visit_date, f.pregnancy_status)), 11) AS pg,\n" +
                "        MAX(f.visit_date) AS hiv_fup_date,\n" +
                "        anc.patient_id AS anc_client,\n" +
                "        anc.visit_date AS anc_visit_date\n" +
                "    FROM kenyaemr_etl.etl_patient_hiv_followup f\n" +
                "    INNER JOIN kenyaemr_etl.etl_patient_demographics d \n" +
                "        ON f.patient_id = d.patient_id AND d.gender = 'F'\n" +
                "    LEFT JOIN (\n" +
                "        SELECT \n" +
                "            anc.patient_id,\n" +
                "            anc.visit_date \n" +
                "        FROM kenyaemr_etl.etl_mch_antenatal_visit anc \n" +
                "        WHERE anc.visit_date BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
                "    ) anc \n" +
                "        ON f.patient_id = anc.patient_id\n" +
                "    WHERE f.person_present = 978 \n" +
                "        AND DATE(f.visit_date) <= DATE(:endDate)\n" +
                "    GROUP BY f.patient_id\n" +
                ") a;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Date startDate = (Date)context.getParameterValue("startDate");
        Date endDate = (Date)context.getParameterValue("endDate");
        queryBuilder.addParameter("startDate", startDate);
        queryBuilder.addParameter("endDate", endDate);

        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}