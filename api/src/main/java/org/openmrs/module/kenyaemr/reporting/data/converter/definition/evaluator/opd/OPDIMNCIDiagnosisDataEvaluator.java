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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDMNCIDiagnosisDataDefinition;
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
 * Evaluates MNCI Diagnosis  
 * OPD Register
 */
@Handler(supports= OPDMNCIDiagnosisDataDefinition.class, order=50)
public class OPDIMNCIDiagnosisDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry = "select\n" +
			"    v.encounter_id,\n" +
			"    (case ed.diagnosis_coded\n" +
			"         when 168737 then 'DIARRHOEA_WITH_DEHYDRATION'\n" +
			"         when 166569 then 'DIARRHOEA_WITH_SOME_DEHYDRATION'\n" +
			"         when 168736 then 'DIARRHOEA_WITH_SEVERE_DEHYDRATION'\n" +
			"         when 145622 then 'CHOLERA'\n" +
			"         when 152 then 'DYSENTRY'\n" +
			"         when 117889 then 'GASTROENTERITIS'\n" +
			"         when 114100 then 'PNEUMONIA'\n" +
			"         when 168738 then 'SEVERE_PNEUMONIA'\n" +
			"         when 123093 then 'UPPER_RESPIRATORY_TRACT_INFECTIONS'\n" +
			"         when 135556 then 'LOWER_RESPIRATORY_TRACT_INFECTIONS'\n" +
			"         when 121375 then 'ASTHMA'\n" +
			"         when 168739 then 'PRESUMED_TUBERCULOSIS'\n" +
			"         when 166623 then 'SUSPECTED_MALARIA'\n" +
			"         when 168740 then 'TESTED_FOR_MALARIA'\n" +
			"         when 160148 then 'CONFIRMED_MALARIA' end) as mnci_diagnosis\n" +
			"from kenyaemr_etl.etl_clinical_encounter v\n" +
			"         LEFT JOIN encounter_diagnosis ed ON v.patient_id = ed.patient_id and date(ed.date_created)between date(:startDate) and date(:endDate)\n" +
			"         LEFT JOIN kenyaemr_etl.etl_laboratory_extract x ON v.patient_id = x.patient_id AND date(v.visit_date) between date(:startDate) and date(:endDate)\n" +
			"where date(v.visit_date) between date(:startDate) and date(:endDate);\n";

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
