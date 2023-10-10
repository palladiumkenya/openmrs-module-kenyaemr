/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.sari;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.sari.TemperatureDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.sari.VomittingDataDefinition;
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
 * Evaluates TemperatureDataDefinition
 */
@Handler(supports= VomittingDataDefinition.class, order=50)
public class VomittingDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
//        String qry = "select t.patient_id,\n" +
//                "          mid(max(concat(t.visit_date,t.complaint)),11) as Vomiting from kenyaemr_etl.etl_allergy_chronic_illness t \n" +
//                "          where date(t.visit_date) between date(:startDate) and date(:endDate) AND t.complaint = 122983 \n" +
//                "           GROUP BY t.patient_id;";
        String qry = "SELECT t.patient_id,\n" +
                "CONCAT( MAX(CASE WHEN t.complaint = 122983 THEN CONCAT('Duration ', timestampdiff(DAY, date(t.complaint_date), date(:endDate))) END)) AS Vomiting\n" +
                "FROM kenyaemr_etl.etl_allergy_chronic_illness t \n" +
                "WHERE date(t.visit_date) between date(:startDate) and date(:endDate) AND t.complaint IN (122983)\n" +
                "GROUP BY t.patient_id;";

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
