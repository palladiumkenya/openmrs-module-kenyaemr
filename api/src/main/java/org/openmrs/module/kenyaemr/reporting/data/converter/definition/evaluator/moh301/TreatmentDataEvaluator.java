/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.moh301;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.TreatmentDataDefinition;
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
 * Evaluates a TreatmentDataDefinition
 */
@Handler(supports = TreatmentDataDefinition.class, order = 50)
public class TreatmentDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "SELECT do.patient_id,\n" +
                "                  GROUP_CONCAT(DISTINCT CONCAT_WS(' ', do.drug_name, do.quantity, case do.quantity_units\n" +
                "                           when 162396 then 'Box(es)'\n" +
                "                           when 162353 then 'Bottle(s)'\n" +
                "                           when 1513 then 'Tablet(s)'\n" +
                "                           when 162382 then 'Vial'\n" +
                "                           when 162380 then 'Tube(s)'\n" +
                "                           when 1518 then 'Suppository'\n" +
                "                           when 1608 then 'Capsule(s)'\n" +
                "                           when 162335 then 'Ampule(s)'\n" +
                "                           when 162353 then 'Bottle(s)'\n" +
                "                           when 1514 then 'Packets'\n" +
                "                           when 2000635 then 'Piece(s)'\n" +
                "                           when 2000636 then 'Roll(s)'\n" +
                "                           when 2000637 then 'Dozen(s)'\n" +
                "                           when 2000638 then 'Pair(s)'\n" +
                "                           when 2000639 then 'Set(s)'\n" +
                "                           when 2000640 then 'Pack(s)'\n" +
                "                           when 162401 then 'Kit(s)'\n" +
                "                           when 162381 then 'Unit(s)'\n" +
                "                           when 162448 then 'Transdermal patch(es)'\n" +
                "                           when 162456 then 'Rectal gel'\n" +
                "                           when 162452 then 'Powder'\n" +
                "                           when 1291 then 'Tin(s)'\n" +
                "                           when 2002379 then 'Sachet(s)' end) ORDER BY\n" +
                "                               do.drug_name SEPARATOR\n" +
                "                               ', ') AS treatement\n" +
                "                FROM kenyaemr_etl.etl_drug_order do\n" +
                "                WHERE do.visit_date BETWEEN :startDate AND :endDate\n" +
                "group by do.patient_id;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        Date startDate = (Date) context.getParameterValue("startDate");
        Date endDate = (Date) context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);
        queryBuilder.addParameter("startDate", startDate);
        queryBuilder.append(qry);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}