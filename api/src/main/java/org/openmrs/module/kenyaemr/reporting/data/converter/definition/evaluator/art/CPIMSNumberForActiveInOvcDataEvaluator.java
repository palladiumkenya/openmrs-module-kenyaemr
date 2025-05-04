/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.art;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.CPIMSNumberForActiveInOVCDataDefinition;
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
 * Evaluates Active in Ovc Data Definition
 */
@Handler(supports= CPIMSNumberForActiveInOVCDataDefinition.class, order=50)
public class CPIMSNumberForActiveInOvcDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "SELECT pp.patient_id,\n" +
                "    CASE \n" +
                "        WHEN p.name IS NOT NULL THEN d.CPIMS_unique_identifier\n" +
                "        WHEN TIMESTAMPDIFF(YEAR, d.DOB, DATE(:endDate)) <= 17 THEN 'No'\n" +
                "        ELSE ''\n" +
                "    END AS active_in_ovc\n" +
                "FROM patient_program pp\n" +
                "INNER JOIN program p ON p.program_id = pp.program_id\n" +
                "LEFT JOIN kenyaemr_etl.etl_patient_demographics d ON d.patient_id = pp.patient_id\n" +
                "WHERE DATE(pp.date_completed) IS NULL \n" +
                "  AND p.name = 'OVC'\n" +
                "GROUP BY pp.patient_id\n" +
                "HAVING MAX(DATE(pp.date_enrolled)) <= DATE(:endDate);";
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

