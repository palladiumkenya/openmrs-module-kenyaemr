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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.HIVStatusIPDDataDefinition;
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
 * Evaluates a HIVStatusIPDDataDefinition
 */
@Handler(supports = HIVStatusIPDDataDefinition.class, order = 50)
public class HIVStatusIPDDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "select a.patient_id,\n" +
                "       if((t.test_results = 'Positive' and t.test_date < a.admission_date) or\n" +
                "          (e.patient_id is not null and e.date_confirmed_hiv_pos < a.admission_date), 'Known Positive',\n" +
                "          if((t.test_results = 'Positive' and t.test_date between a.admission_date and date(:endDate)) or\n" +
                "             (e.patient_id is not null and e.date_confirmed_hiv_pos between a.admission_date and date(:endDate)),\n" +
                "             'Positive this Visit', t.test_results)) as hiv_status\n" +
                "from kenyaemr_etl.etl_inpatient_admission a\n" +
                "         left join (select t.patient_id,\n" +
                "                           max(t.visit_date)                                       as test_date,\n" +
                "                           mid(max(concat(t.visit_date, t.final_test_result)), 11) as test_results\n" +
                "                    from kenyaemr_etl.etl_hts_test t\n" +
                "                    where date(t.visit_date) <= date(:endDate)\n" +
                "                    group by t.patient_id) t on a.patient_id = t.patient_id\n" +
                "         left join (select e.patient_id,\n" +
                "                           max(e.visit_date)                                                 as enrollment_date,\n" +
                "                           mid(max(concat(e.visit_date, e.date_confirmed_hiv_positive)), 11) as date_confirmed_hiv_pos\n" +
                "                    from kenyaemr_etl.etl_hiv_enrollment e\n" +
                "                    where e.visit_date <= date(:endDate)\n" +
                "                    group by e.patient_id) e on a.patient_id = e.patient_id\n" +
                "where date(a.admission_date) between date(:startDate) and date(:endDate);";

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