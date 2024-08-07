/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.dmi;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.VisitTypeWithComplaintsDataDefinition;
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
 * Evaluates Visit type Data Definition
 */
@Handler(supports= VisitTypeWithComplaintsDataDefinition.class, order=50)
public class VisitTypeWithComplaintsDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "select a.patient_id, if(\n" +
                "e.patient_outcome in (1693,160429) or v.visit_type_id = 1, 'OPD', if(e.patient_outcome = 1654 or v.visit_type_id = 2, 'IPD','N/A')) as visit_type\n" +
                "from (select patient_id, c.complaint as complaint, c.complaint_date as complaint_date, c.visit_date\n" +
                "      from kenyaemr_etl.etl_allergy_chronic_illness c\n" +
                "      where date(c.visit_date) between date(:startDate) and date(:endDate)\n" +
                "      group by patient_id) a\n" +
                "         join openmrs.visit v\n" +
                "              on a.patient_id = v.patient_id and date(a.visit_date) = date(v.date_started)\n" +
                "         join kenyaemr_etl.etl_patient_triage t\n" +
                "              on a.patient_id = t.patient_id and date(t.visit_date) = date(v.date_started)\n" +
                "join kenyaemr_etl.etl_clinical_encounter e\n" +
                "on a.patient_id = e.patient_id and date(a.visit_date) = date(e.visit_date);";

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