/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.ortho;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.ortho.OrthoAmountPaidDataDefinition;
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
 *
 */
@Handler(supports= OrthoAmountPaidDataDefinition.class, order=50)
public class OrthoAmountPaidDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry = "SELECT v.encounter_id, tp.amount AS amount\n" +
                "FROM kenyaemr_etl.etl_orthopaedic_clinic_visit v\n" +
                "LEFT JOIN openmrs.cashier_bill t \n" +
                "    ON v.patient_id = t.patient_id \n" +
                "    AND DATE(t.date_created) between date(:startDate) and date(:endDate)\n" +
                "LEFT JOIN openmrs.cashier_bill_payment tp \n" +
                "    ON t.bill_id = tp.bill_id \n" +
                "    AND DATE(tp.date_created) between date(:startDate) and date(:endDate)\n" +
                "LEFT JOIN openmrs.cashier_payment_mode m \n" +
                "    ON tp.payment_mode_id = m.payment_mode_id \n" +
                "    AND DATE(m.date_created) between date(:startDate) and date(:endDate)\n" +
                "WHERE DATE(v.visit_date) between date(:startDate) and date(:endDate);";

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
