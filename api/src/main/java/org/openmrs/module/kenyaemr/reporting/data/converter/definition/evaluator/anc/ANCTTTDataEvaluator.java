/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.anc;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.anc.ANCTTTDataDefinition;
import org.openmrs.module.reporting.data.encounter.EvaluatedEncounterData;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDataDefinition;
import org.openmrs.module.reporting.data.encounter.evaluator.EncounterDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Evaluates  Data Definition to ANC TTT Dose
 */
@Handler(supports=ANCTTTDataDefinition.class, order=50)
public class ANCTTTDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry = "\n" +
                "SELECT v.encounter_id,\n" +
                "       CASE\n" +
                "           WHEN tetanus_taxoid_1 IS NOT NULL THEN '1st Dose'\n" +
                "           WHEN tetanus_taxoid_1 IS NOT NULL THEN '2nd Dose'\n" +
                "           WHEN tetanus_taxoid_1 IS NOT NULL THEN '3rd Dose'\n" +
                "           WHEN tetanus_taxoid_1 IS NOT NULL THEN '4th Dose'\n" +
                "           WHEN tetanus_taxoid_1 IS NOT NULL THEN '5th Dose'\n" +
                "           END AS TT\n" +
                "FROM kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "         LEFT JOIN kenyaemr_etl.etl_preventive_services s\n" +
                "                   ON v.patient_id = s.patient_id AND v.encounter_id = s.encounter_id\n" +
                "WHERE DATE(v.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate);";

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
