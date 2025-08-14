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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.fp.FPCycleBeadsGivenDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.fp.FPEmergencyContraceptiveQtyDispDefinition;
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
@Handler(supports= FPCycleBeadsGivenDefinition.class, order=50)
public class FPCycleBeadsGivenEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;
    @Override
    public EvaluatedEncounterData evaluate(EncounterDataDefinition encounterDataDefinition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(encounterDataDefinition, context);
        //String qry = "select v.encounter_id, v.circle_beads_given from kenyaemr_etl.etl_family_planning v where date(v.visit_date) between date(:startDate) and date(:endDate);";

        String qry = "SELECT \n" +
                "    v.encounter_id,\n" +
                "    CASE \n" +
                "        WHEN v.circle_beads_given = (SELECT concept_id FROM openmrs.concept WHERE uuid = '1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA') THEN 'Y'\n" +
                "        WHEN v.circle_beads_given = (SELECT concept_id FROM openmrs.concept WHERE uuid = '1066AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA') THEN 'N'\n" +
                "        ELSE NULL\n" +
                "    END AS circle_beads_given\n" +
                "FROM \n" +
                "    kenyaemr_etl.etl_family_planning v\n" +
                "WHERE \n" +
                "    v.circle_beads_given IN (\n" +
                "        (SELECT concept_id FROM openmrs.concept WHERE uuid = '1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA'),\n" +
                "        (SELECT concept_id FROM openmrs.concept WHERE uuid = '1066AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA')\n" +
                "    )\n" +
                "    AND DATE(v.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate);";


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
