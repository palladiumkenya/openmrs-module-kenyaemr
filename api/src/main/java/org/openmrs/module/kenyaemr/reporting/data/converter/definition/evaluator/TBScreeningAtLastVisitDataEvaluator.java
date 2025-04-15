/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.TBScreeningAtLastVisitDataDefinition;
import org.openmrs.module.reporting.data.person.EvaluatedPersonData;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.data.person.evaluator.PersonDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Evaluates a patient's tb screening at last visit
 */
@Handler(supports=TBScreeningAtLastVisitDataDefinition.class, order=50)
public class TBScreeningAtLastVisitDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "SELECT v.patient_id,\n" +
                "       CASE v.lastTBStatus\n" +
                "           WHEN 1660 THEN 'No TB Signs'\n" +
                "           WHEN 142177 THEN 'Presumed TB'\n" +
                "           WHEN 1662 THEN 'TB Confirmed'\n" +
                "           WHEN 160737 THEN 'TB Screening Not Done' END as lastTBStatus\n" +
                "from (SELECT f.patient_id,\n" +
                "             f.visit_date,\n" +
                "             MID(MAX(CONCAT(DATE(s.visit_date), f.tb_status)), 11) AS lastTBStatus\n" +
                "      FROM kenyaemr_etl.etl_tb_screening s\n" +
                "               INNER JOIN kenyaemr_etl.etl_patient_hiv_followup f\n" +
                "                          ON s.patient_id = f.patient_id AND s.visit_date = f.visit_date\n" +
                "      GROUP BY s.patient_id) v\n" +
                "GROUP BY patient_id;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}