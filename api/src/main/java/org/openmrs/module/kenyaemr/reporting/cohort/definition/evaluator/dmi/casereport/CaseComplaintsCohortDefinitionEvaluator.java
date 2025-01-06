/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.cohort.definition.evaluator.dmi.casereport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.dmi.casereport.CaseComplaintsCohortDefinition;
import org.openmrs.module.reporting.common.ObjectUtil;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.openmrs.module.reporting.query.visit.VisitQueryResult;
import org.openmrs.module.reporting.query.visit.definition.VisitQuery;
import org.openmrs.module.reporting.query.visit.evaluator.VisitQueryEvaluator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Evaluator for case complaints Cohort
 */
@Handler(supports = {CaseComplaintsCohortDefinition.class})
public class CaseComplaintsCohortDefinitionEvaluator implements VisitQueryEvaluator {

    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    EvaluationService evaluationService;

    @Override
    public VisitQueryResult evaluate(VisitQuery visitQuery, EvaluationContext context) throws EvaluationException {
        context = ObjectUtil.nvl(context, new EvaluationContext());
        VisitQueryResult queryResult = new VisitQueryResult(visitQuery, context);

        String qry = "select i.visit_id\n" +
                "from kenyaemr_etl.etl_allergy_chronic_illness i\n" +
                "where i.visit_date between date(:startDate) and date(:endDate)\n" +
                "  and i.complaint is not null\n" +
                "group by visit_id;";

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.append(qry);
        Date startDate = (Date) context.getParameterValue("startDate");
        Date endDate = (Date) context.getParameterValue("endDate");
        builder.addParameter("endDate", endDate);
        builder.addParameter("startDate", startDate);

        List<Integer> results = evaluationService.evaluateToList(builder, Integer.class, context);
        queryResult.getMemberIds().addAll(results);
        return queryResult;
    }
}
