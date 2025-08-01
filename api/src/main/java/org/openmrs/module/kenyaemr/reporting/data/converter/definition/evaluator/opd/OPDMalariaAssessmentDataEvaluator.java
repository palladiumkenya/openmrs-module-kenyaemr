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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDComplaintDurationDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDMalariaAssessmentDataDefinition;
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
 * Evaluates Malaria assessment  
 * OPD Register
 */
@Handler(supports= OPDMalariaAssessmentDataDefinition.class, order=50)
public class OPDMalariaAssessmentDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry = "select\n" +
			"    v.encounter_id,\n" +
			"       if(ed.diagnosis_coded = 166623 and x.lab_test not in (1643,2017901,1366), 1,\n" +
			"         if(x.lab_test = 1643 and x.test_result = 664, 2,\n" +
			"           if(x.lab_test in (2017917) and x.test_result = 2017936, 3,\n" +
			"            if(x.lab_test in (1643) and x.test_result = 703, 4,\n" +
			"               if(x.lab_test in (2017917) and x.test_result = 703, 5,''))))) as malaria\n" +
			"from kenyaemr_etl.etl_clinical_encounter v\n" +
			"         INNER JOIN kenyaemr_etl.etl_laboratory_extract x ON v.patient_id = x.patient_id AND date(x.visit_date) between date(:startDate) and date(:endDate)\n" +
			"         LEFT JOIN openmrs.encounter_diagnosis ed ON v.patient_id = ed.patient_id and date(ed.date_created)between date(:startDate) and date(:endDate)\n" +
			"where date(v.visit_date) between date(:startDate) and date(:endDate);";

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
