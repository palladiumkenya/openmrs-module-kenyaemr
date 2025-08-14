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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.fp.FPImplantsTypeOfVisitDefinition;
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
@Handler(supports= FPImplantsTypeOfVisitDefinition.class, order=50)
public class FPImplantsTypeOfVisitEvaluator implements EncounterDataEvaluator {
    @Autowired
    private EvaluationService evaluationService;

    @Override
    public EvaluatedEncounterData evaluate(EncounterDataDefinition encounterDataDefinition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(encounterDataDefinition, context);
        String qry = "SELECT \n" +
                "    f.encounter_id,\n" +
                "    CASE f.type_of_visit_for_method\n" +
                "        WHEN 164180 THEN 'New'                     -- From typeOfVisitForMethod1\n" +
                "        WHEN 164142 THEN 'Re-visit'                 -- From typeOfVisitForMethod1\n" +
                "        WHEN 164180 THEN '1st time insertion'       -- From typeOfVisitForMethod2 (note: same concept ID reused)\n" +
                "        WHEN 164161 THEN 'Removal'\n" +
                "        WHEN 'd5ea1533-7346-4e0b-8626-9bff6cd183b2' THEN 'Re-insertion'\n" +
                "        WHEN '356f0bfc-a3e1-44a3-8a15-efe2845cb18f' THEN 'Check-up'\n" +
                "        ELSE 'Unknown'\n" +
                "    END AS type_of_visit_label\n" +
                "FROM kenyaemr_etl.etl_family_planning f\n" +
                "WHERE DATE(f.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate);\n;";

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
