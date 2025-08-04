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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDDiagnosisDataDefinition;
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
 * Evaluates Diagnosis  
 * OPD Register
 */
@Handler(supports= OPDDiagnosisDataDefinition.class, order=50)
public class OPDDiagnosisDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

		String qry = "select\n" +
			"  v.encounter_id,\n" +
			"  con.name as mnci_diagnosis\n" +
			"from kenyaemr_etl.etl_clinical_encounter v\n" +
			"            inner join (select\n" +
			"                            cn.name, cn.date_created, ed.patient_id,ed.encounter_id\n" +
			"                        from openmrs.encounter_diagnosis ed\n" +
			"                                 inner join openmrs.concept_name cn on cn.concept_id = ed.diagnosis_coded and cn.locale = 'en'\n" +
			"                                     and date(ed.date_created) between date(:startDate) and date(:endDate)\n" +
			") con on v.encounter_id = con.encounter_id and date(v.visit_Date) between date(:startDate) and date(:endDate)\n" +
			"UNION\n" +
			"select\n" +
			"   sc.encounter_id,\n" +
			"    con.name as mnci_diagnosis\n" +
			"from kenyaemr_etl.etl_special_clinics sc\n" +
			"         inner join (select\n" +
			"                         cn.name, cn.date_created, ed.patient_id,ed.encounter_id\n" +
			"                     from openmrs.encounter_diagnosis ed\n" +
			"                              inner join openmrs.concept_name cn on cn.concept_id = ed.diagnosis_coded and cn.locale = 'en'\n" +
			"                         and date(ed.date_created) between date(:startDate) and date(:endDate)\n" +
			") con on sc.encounter_id = con.encounter_id and date(sc.visit_Date) between date(:startDate) and date(:endDate);";

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
