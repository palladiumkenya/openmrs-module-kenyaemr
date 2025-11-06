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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDClinicalDiagnosisDataDefinition;
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
 * Evaluates Clinical Diagnosis  
 * MOH 240 Lab Register Register
 */
@Handler(supports= OPDClinicalDiagnosisDataDefinition.class, order=50)
public class OPDClinicalDiagnosisDataEvaluator implements ObsDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedObsData evaluate(ObsDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedObsData c = new EvaluatedObsData(definition, context);

		String qry = "select\n" +
			"   le.encounter_id,\n" +
			"   dl.name as clinical_diagnosis\n" +
			" from kenyaemr_etl.etl_laboratory_extract le\n" +
			"        inner join (select\n" +
			"                        cn.name, ed.date_created, ed.dx_rank ,ed.patient_id\n" +
			"                    from openmrs.encounter_diagnosis ed\n" +
			"                             inner join openmrs.concept_name cn on cn.concept_id = ed.diagnosis_coded and cn.locale = 'en' and ed.dx_rank = 1\n" +
			"                                       ) dl on le.patient_id = dl.patient_id and date(dl.date_created) = date(le.visit_date)\n" +
			"                                       and date(le.visit_Date) between date(:startDate) and date(:endDate);";

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
