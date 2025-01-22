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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.NCDStatusDataDefinition;
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
 * Evaluates NCDs status date DataDefinition
 */
@Handler(supports = NCDStatusDataDefinition.class, order = 50)
public class NCDStatusDataEvaluator implements PersonDataEvaluator {
    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "SELECT ci.patient_id,\n" +
                "       GROUP_CONCAT(CASE ci.is_chronic_illness_controlled\n" +
                "            WHEN 1065 THEN 'Controlled'\n" +
                "            WHEN 1066 THEN 'Not controlled'\n" +
                "        END) AS controlled_status\n" +
                "FROM kenyaemr_etl.etl_allergy_chronic_illness ci\n" +
                "JOIN (\n" +
                "    SELECT patient_id, MAX(visit_date) AS latest_visit_date\n" +
                "    FROM kenyaemr_etl.etl_allergy_chronic_illness\n" +
                "    WHERE visit_date <= DATE(:endDate)\n" +
                "    GROUP BY patient_id\n" +
                ") latest ON ci.patient_id = latest.patient_id AND ci.visit_date = latest.latest_visit_date\n" +
                "GROUP BY ci.patient_id;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Date endDate = (Date) context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);

        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
