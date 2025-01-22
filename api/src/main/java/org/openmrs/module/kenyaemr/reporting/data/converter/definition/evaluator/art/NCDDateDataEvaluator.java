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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.NCDDateDataDefinition;
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
 * Evaluates NCDs onset date DataDefinition
 */
@Handler(supports = NCDDateDataDefinition.class, order = 50)
public class NCDDateDataEvaluator implements PersonDataEvaluator {
    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry ="WITH LatestIllnessDates AS (\n" +
                "  SELECT\n" +
                "    patient_id,\n" +
                "    MAX(chronic_illness_onset_date) AS latest_onset_date,\n" +
                "    COUNT(*) AS date_count\n" +
                "  FROM\n" +
                "    kenyaemr_etl.etl_allergy_chronic_illness\n" +
                "  WHERE\n" +
                "    visit_date <= DATE(:endDate)\n" +
                "  GROUP BY\n" +
                "    patient_id\n" +
                ")\n" +
                "SELECT\n" +
                "  ci.patient_id,\n" +
                "  CASE WHEN lid.date_count > 1 THEN\n" +
                "    GROUP_CONCAT(ci.chronic_illness_onset_date SEPARATOR ', ')\n" +
                "  ELSE\n" +
                "    MAX(ci.chronic_illness_onset_date)\n" +
                "  END AS ChronicIllness_onset\n" +
                "FROM\n" +
                "  kenyaemr_etl.etl_allergy_chronic_illness ci\n" +
                "INNER JOIN LatestIllnessDates lid ON ci.patient_id = lid.patient_id\n" +
                "WHERE\n" +
                "  ci.visit_date <= DATE(:endDate)\n" +
                "GROUP BY\n" +
                "  ci.patient_id;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Date endDate = (Date) context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);

        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
