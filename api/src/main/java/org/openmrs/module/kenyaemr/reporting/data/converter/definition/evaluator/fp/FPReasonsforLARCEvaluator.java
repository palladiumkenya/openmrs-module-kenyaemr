/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.fp;


import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.fp.FPReasonsforLARCDefinition;
import org.openmrs.module.reporting.data.encounter.EvaluatedEncounterData;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDataDefinition;
import org.openmrs.module.reporting.data.encounter.evaluator.EncounterDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@Handler(supports= FPReasonsforLARCDefinition.class, order=50)
public class FPReasonsforLARCEvaluator implements EncounterDataEvaluator {
    @Autowired
    private EvaluationService evaluationService;


    @Override
    public EvaluatedEncounterData evaluate(EncounterDataDefinition encounterDataDefinition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(encounterDataDefinition, context);
        String qry = "SELECT v.encounter_id, cn.name AS reasons_for_larc_removal " +
                "FROM kenyaemr_etl.etl_family_planning v " +
                "JOIN openmrs.concept_name cn " +
                "    ON cn.concept_id = v.reasons_for_larc_removal " +
                "    AND cn.locale = 'en' " +
                "    AND cn.voided = 0 " +
                "WHERE v.reasons_for_larc_removal IS NOT NULL " +
                "  AND DATE(v.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)";



//        String qry =
//                "SELECT v.encounter_id, " +
//                        "       CASE v.reasons_for_larc_removal " +
//                        "           WHEN (SELECT concept_id FROM openmrs.concept WHERE uuid = '161638AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA') THEN 'Maturity' " +
//                        "           WHEN (SELECT concept_id FROM openmrs.concept WHERE uuid = '160571AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA') THEN 'Desire to get pregnant' " +
//                        "           WHEN (SELECT concept_id FROM openmrs.concept WHERE uuid = '164154AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA') THEN 'Health concerns/Side effects' " +
//                        "           WHEN (SELECT concept_id FROM openmrs.concept WHERE uuid = '163494AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA') THEN 'Method Switch' " +
//                        "           WHEN (SELECT concept_id FROM openmrs.concept WHERE uuid = '160693AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA') THEN 'Method Failure' " +
//                        "           WHEN (SELECT concept_id FROM openmrs.concept WHERE uuid = '5622AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA') THEN 'Others (Specify)' " +
//                        "           ELSE NULL " +
//                        "       END AS reasons_for_larc_removal_name " +
//                        "FROM kenyaemr_etl.etl_family_planning v " +
//                        "WHERE v.reasons_for_larc_removal IN ( " +
//                        "    (SELECT concept_id FROM openmrs.concept WHERE uuid = '161638AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), " +
//                        "    (SELECT concept_id FROM openmrs.concept WHERE uuid = '160571AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), " +
//                        "    (SELECT concept_id FROM openmrs.concept WHERE uuid = '164154AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), " +
//                        "    (SELECT concept_id FROM openmrs.concept WHERE uuid = '163494AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), " +
//                        "    (SELECT concept_id FROM openmrs.concept WHERE uuid = '160693AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'), " +
//                        "    (SELECT concept_id FROM openmrs.concept WHERE uuid = '5622AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA') " +
//                        ") " +
//                        "AND DATE(v.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate);";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Date startDate = (Date)context.getParameterValue("startDate");
        Date endDate = (Date)context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);
        queryBuilder.addParameter("startDate", startDate);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
