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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.fp.FPClientsRecievingPostpartumDefinition;
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
@Handler(supports= FPClientsRecievingPostpartumDefinition.class, order=50)
public class FPClientsRecievingPostpartumEvaluator implements EncounterDataEvaluator {

    //receiving_postpartum_fp
    @Autowired
    private EvaluationService evaluationService;

    @Override
    public EvaluatedEncounterData evaluate(EncounterDataDefinition encounterDataDefinition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(encounterDataDefinition, context);

        String qry = "SELECT v.encounter_id,  cn.name AS receiving_postpartum_fp " +
                "FROM kenyaemr_etl.etl_family_planning v LEFT JOIN openmrs.concept c ON c.concept_id = v.receiving_postpartum_fp " +
                "LEFT JOIN openmrs.concept_name cn  ON cn.concept_id = c.concept_id  AND cn.locale = 'en' " +
                " AND cn.concept_name_type = 'FULLY_SPECIFIED' WHERE DATE(v.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate);";


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
