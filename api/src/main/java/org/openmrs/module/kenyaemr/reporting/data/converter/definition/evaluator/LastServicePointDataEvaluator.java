/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.LastServicePointDataDefinition;
import org.openmrs.module.reporting.data.encounter.EvaluatedEncounterData;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDataDefinition;
import org.openmrs.module.reporting.data.encounter.evaluator.EncounterDataEvaluator;
import org.openmrs.module.reporting.data.person.EvaluatedPersonData;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.data.person.evaluator.PersonDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * ANC Prophylaxis Given column
 */
@Handler(supports= LastServicePointDataDefinition.class, order=50)
public class LastServicePointDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "select\n" +
                "  e.patient_id,\n" +
                " (case when pv.visit_date <=  v.visit_date or ld.visit_date <=  v.visit_date then \"ANC\" \n" +
                " when v.visit_date <=  pv.visit_date or ld.visit_date <=  pv.visit_date THEN \"PNC\"\n" +
                " when pv.visit_date <=  ld.visit_date or v.visit_date <=  ld.visit_date THEN \"Maternity\" \n" +
                "else \"ANC\"\n" +
                "end) as visit_date\n"+
                "from kenyaemr_etl.etl_mch_enrollment e\n" +
                "inner join kenyaemr_etl.etl_mch_antenatal_visit v on v.patient_id = e.patient_id\n" +
                " left join kenyaemr_etl.etl_mch_postnatal_visit pv on e.patient_id = pv.patient_id\n" +
                "left join kenyaemr_etl.etl_mchs_delivery ld  on e.patient_id = ld.patient_id\n" +
                "GROUP BY e.patient_id;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
