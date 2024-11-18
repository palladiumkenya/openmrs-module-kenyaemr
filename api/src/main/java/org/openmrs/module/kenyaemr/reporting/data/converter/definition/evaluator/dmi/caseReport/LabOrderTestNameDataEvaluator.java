/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.dmi.caseReport;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.LabOrderTestNameDataDefinition;
import org.openmrs.module.reporting.data.visit.EvaluatedVisitData;
import org.openmrs.module.reporting.data.visit.definition.VisitDataDefinition;
import org.openmrs.module.reporting.data.visit.evaluator.VisitDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Evaluates Visit type Data Definition
 */

@Handler(supports = LabOrderTestNameDataDefinition.class, order = 50)
public class LabOrderTestNameDataEvaluator implements VisitDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedVisitData evaluate(VisitDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedVisitData c = new EvaluatedVisitData(definition, context);

        String qry = "WITH FilteredOrders AS (SELECT patient_id,\n" +
                "       encounter_id,\n" +
                "       order_id,\n" +
                "       concept_id\n" +
                "FROM openmrs.orders\n" +
                "WHERE order_type_id = 3\n" +
                "  AND voided = 0\n" +
                "  AND DATE(date_activated) >= date(:startDate)\n" +
                "GROUP BY patient_id,concept_id),\n" +
                "LabOrderConcepts AS (SELECT cs.concept_set_id AS set_id,\n" +
                "         cs.concept_id     AS member_concept_id,\n" +
                "         c.datatype_id     AS member_datatype,\n" +
                "         c.class_id        AS member_class,\n" +
                "         n.name\n" +
                "  FROM concept_set cs\n" +
                "           INNER JOIN concept c ON cs.concept_id = c.concept_id\n" +
                "           INNER JOIN concept_name n ON c.concept_id = n.concept_id\n" +
                "      AND n.locale = 'en'\n" +
                "      AND n.concept_name_type = 'FULLY_SPECIFIED'\n" +
                "  WHERE cs.concept_set = 1000628)\n" +
                "SELECT v.visit_id,\n" +
                "GROUP_CONCAT(CASE\n" +
                "WHEN o.concept_id IS NOT NULL\n" +
                "    THEN CONCAT(COALESCE(o.order_id, '-'), ':', lc.name) END ORDER BY\n" +
                "lc.member_concept_id SEPARATOR ', ') as Lab_test\n" +
                "FROM visit v\n" +
                "INNER JOIN encounter e ON e.visit_id = v.visit_id\n" +
                "INNER JOIN FilteredOrders o ON o.encounter_id = e.encounter_id\n" +
                "INNER JOIN LabOrderConcepts lc ON o.concept_id = lc.member_concept_id\n" +
                "WHERE DATE(v.date_started) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
                "group by v.visit_id;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Date startDate = (Date) context.getParameterValue("startDate");
        Date endDate = (Date) context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);
        queryBuilder.addParameter("startDate", startDate);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
