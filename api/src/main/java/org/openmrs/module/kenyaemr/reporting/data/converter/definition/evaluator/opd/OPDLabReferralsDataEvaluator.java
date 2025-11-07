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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDLabReferralsDataDefinition;
import org.openmrs.module.reporting.data.obs.EvaluatedObsData;
import org.openmrs.module.reporting.data.obs.definition.ObsDataDefinition;
import org.openmrs.module.reporting.data.obs.evaluator.ObsDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Evaluates Lab referrals
 * MOH 240 Lab Register 
 */
@Handler(supports= OPDLabReferralsDataDefinition.class, order=50)
public class OPDLabReferralsDataEvaluator implements ObsDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedObsData evaluate(ObsDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedObsData c = new EvaluatedObsData(definition, context);

		String qry = "select le.obs_id,\n" +
                "       v.referred_to\n" +
                "from kenyaemr_etl.etl_laboratory_extract le\n" +
                "         left join (select v.patient_id,\n" +
                "                           (case v.referral_to\n" +
                "                                when 'This health facility' then 1\n" +
                "                                when 'Other health facility' then 2\n" +
                "                                when 'Community Unit' then 3\n" +
                "                                else '' end) as referred_to\n" +
                "                    from kenyaemr_etl.etl_clinical_encounter v\n" +
                "                    where date(v.visit_date) between date(:startDate) and date(:endDate)) v\n" +
                "                   on v.patient_id = le.patient_id\n" +
                "where date(le.visit_date) BETWEEN date(:startDate) AND date(:endDate)\n" +
                "and obs_id is not null;";

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
