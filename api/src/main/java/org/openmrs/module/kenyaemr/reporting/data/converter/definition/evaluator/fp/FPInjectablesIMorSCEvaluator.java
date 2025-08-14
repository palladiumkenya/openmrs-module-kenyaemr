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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.fp.FPInjectablesIMorSCDefinition;
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
@Handler(supports= FPInjectablesIMorSCDefinition.class, order=50)
public class FPInjectablesIMorSCEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    @Override
    public EvaluatedEncounterData evaluate(EncounterDataDefinition encounterDataDefinition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(encounterDataDefinition, context);
        String qry = "SELECT \n" +
                "    fp.encounter_id,\n" +
                "    CASE \n" +
                "        WHEN fp.contraceptive_dispensed = (SELECT concept_id FROM openmrs.concept WHERE uuid = '907AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA') THEN 'IM'\n" +
                "        WHEN fp.contraceptive_dispensed = (SELECT concept_id FROM openmrs.concept WHERE uuid = '79494AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA') THEN 'SC'\n" +
                "        ELSE NULL \n" +
                "    END AS injection_type\n" +
                "\n" +
                "FROM \n" +
                "    kenyaemr_etl.etl_family_planning fp\n" +
                "JOIN \n" +
                "    openmrs.patient p ON fp.patient_id = p.patient_id\n" +
                "LEFT JOIN \n" +
                "    openmrs.concept_name cn_visit ON fp.type_of_visit_for_method = cn_visit.concept_id \n" +
                "    AND cn_visit.locale = 'en' \n" +
                "    AND cn_visit.concept_name_type = 'FULLY_SPECIFIED'\n" +
                "WHERE \n" +
                "    fp.contraceptive_dispensed IN (\n" +
                "        (SELECT concept_id FROM openmrs.concept WHERE uuid = '907AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'),  -- DMPA-IM\n" +
                "        (SELECT concept_id FROM openmrs.concept WHERE uuid = '79494AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA')   -- DMPA-SC\n" +
                "    )\n" +
                "    AND DATE(fp.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n" +
                "    AND fp.voided = 0;";
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
