/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.ncd;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.ncd.NCDDiagnosisDataDefinition;
import org.openmrs.module.reporting.data.encounter.EvaluatedEncounterData;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDataDefinition;
import org.openmrs.module.reporting.data.encounter.evaluator.EncounterDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;


@Component
@Handler(supports= NCDDiagnosisDataDefinition.class, order=50)
public class NCDDiagnosisDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    @Override
    public EvaluatedEncounterData evaluate(EncounterDataDefinition encounterDataDefinition, EvaluationContext evaluationContext)
            throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(encounterDataDefinition, evaluationContext);
        //diagnosis not there

//        String qry ="select ncd.encounter_id, dl.name as diagnosis from kenyaemr_etl.etl_ncd_enrollment ncd\n" +
//                "inner join (select cn.name, ed.patient_id from openmrs.encounter_diagnosis ed inner join openmrs.concept_name cn on cn.concept_id = ed.diagnosis_coded and cn.locale = 'en') dl on ncd.patient_id = dl.patient_id;";

        String qry ="SELECT ncd.encounter_id, cn.name AS diagnosis\n" +
                "FROM kenyaemr_etl.etl_ncd_enrollment ncd\n" +
                "INNER JOIN openmrs.encounter_diagnosis ed ON ed.encounter_id = ncd.encounter_id\n" +
                "INNER JOIN openmrs.concept_name cn \n" +
                "    ON cn.concept_id = ed.diagnosis_coded \n" +
                "    AND cn.locale = 'en'\n" +
                "WHERE DATE(ncd.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate);\n";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Date startDate = (Date) evaluationContext.getParameterValue("startDate");
        Date endDate = (Date) evaluationContext.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);
        queryBuilder.addParameter("startDate", startDate);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, evaluationContext);
        c.setData(data);

        return c;
    }
}
