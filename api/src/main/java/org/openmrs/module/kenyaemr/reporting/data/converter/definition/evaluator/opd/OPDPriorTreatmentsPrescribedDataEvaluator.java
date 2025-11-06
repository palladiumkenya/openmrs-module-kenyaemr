/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.opd;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDPriorTreatmentsPrescribedDataDefinition;
import org.openmrs.module.reporting.data.obs.definition.ObsDataDefinition;
import org.openmrs.module.reporting.data.obs.EvaluatedObsData;
import org.openmrs.module.reporting.data.obs.evaluator.ObsDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Evaluates prior lab treatment prescribed
 * OPD Register
 */
@Handler(supports= OPDPriorTreatmentsPrescribedDataDefinition.class, order=50)
public class OPDPriorTreatmentsPrescribedDataEvaluator implements ObsDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedObsData evaluate(ObsDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedObsData c = new EvaluatedObsData(definition, context);

        String qry = "select le.obs_id,\n" +
                "       d.drug_name\n" +
                "from kenyaemr_etl.etl_laboratory_extract le\n" +
                "         LEFT JOIN (select d.enc_name, d.patient_id, GROUP_CONCAT(DISTINCT(d.drug_name) separator '|') as drug_name\n" +
                "                    from kenyaemr_etl.etl_drug_order d\n" +
                "                    where date(d.visit_date) between date(:startDate) and date(:endDate)\n" +
                "                    group by visit_date,patient_id) d ON le.patient_id = d.patient_id\n" +
                "    and d.enc_name = 'Drug Order'\n" +
                "where date(le.visit_date) between date(:startDate) and date(:endDate);";

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
