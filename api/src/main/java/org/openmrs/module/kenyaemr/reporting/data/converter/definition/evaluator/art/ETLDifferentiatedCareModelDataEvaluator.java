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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLDifferentiatedCareModelDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLStabilityDataDefinition;
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
 * Evaluates Differentiated care model Data Definition
 */
@Handler(supports= ETLDifferentiatedCareModelDataDefinition.class, order=50)
public class ETLDifferentiatedCareModelDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry="select fup.patient_id,\n" +
                "       (case fup.differentiated_care\n" +
                "            when 164942 then \"Standard Care\"\n" +
                "            when 164943 then \"Fast Track\"\n" +
                "            when 166443 then \"Health care worker Led facility ART group(HFAG)\"\n" +
                "            when 166444 then \"Peer Led Facility ART Group(PFAG)\"\n" +
                "            when 1555 then \"Health care worker Led Community ART group(HCAG)\"\n" +
                "            when 164945 then \"Peer Led Community ART Group(PCAG)\"\n" +
                "            when 1000478 then \"Community Pharmacy(CP)\"\n" +
                "            when 164944 then \"Community ART Distribution Points(CAPD)\"\n" +
                "            when 166583 then \"Individual patient ART Community Distribution(IACD)\"\n" +
                "            when 164946 then \"Facility ART Distribution Group\"\n" +
                "            else \"\" end) as differentiated_care_model\n" +
                "from (SELECT f.patient_id,\n" +
                "             MID(MAX(CONCAT(f.visit_date, f.differentiated_care)), 11) AS differentiated_care\n" +
                "      FROM kenyaemr_etl.etl_patient_hiv_followup f\n" +
                "               INNER JOIN\n" +
                "           kenyaemr_etl.etl_patient_demographics d\n" +
                "           ON f.patient_id = d.patient_id\n" +
                "      WHERE f.stability IS NOT NULL\n" +
                "        AND f.person_present = 978\n" +
                "        AND DATE(f.visit_date) <= DATE(:endDate)\n" +
                "        AND f.voided = 0\n" +
                "      GROUP BY f.patient_id) fup;";

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
