/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.pnc;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.pnc.PNCTestedForHIVAtPNCDataDefinition;
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
 * PNC tested for HIV at PNC column
 */
@Handler(supports= PNCTestedForHIVAtPNCDataDefinition.class, order=50)
public class PNCTestedForHIVAtPNCDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry = "select a.encounter_id,\n" +
			" if(a.tested_at_pnc is not null, 'Yes',\n" +
			"   if(a.mch_enr_hiv_status = 703 || a.hts_hiv_final_status ='Positive' || a.anc_final_hiv_status = 'Positive', 'Known positive','No')) as tested_at_pnc\n" +
			" from (select v.patient_id,\n" +
			"              v.encounter_id,\n" +
			"             coalesce(nullif(v.final_test_result,''), nullif(t.final_test_result,'')) as tested_at_pnc,\n" +
			"              enr.hiv_status as mch_enr_hiv_status,\n" +
			"              t.final_test_result as hts_hiv_final_status,\n" +
			"              anc.final_test_result as anc_final_hiv_status\n" +
			"       from kenyaemr_etl.etl_mch_postnatal_visit v\n" +
			"                left join (select enr.encounter_id, enr.patient_id, enr.visit_date, enr.hiv_status\n" +
			"                           from kenyaemr_etl.etl_mch_enrollment enr\n" +
			"                             where date(enr.visit_date) between date(:startDate) and date(:endDate)) enr\n" +
			"                          on v.patient_id = enr.patient_id\n" +
			"                left join (select anc.encounter_id, anc.patient_id, anc.visit_date, anc.final_test_result\n" +
			"                           from kenyaemr_etl.etl_mch_antenatal_visit anc\n" +
			"                           where date(anc.visit_date) between date(:startDate) and date(:endDate)) anc\n" +
			"                          on v.patient_id = anc.patient_id\n" +
			"                left join (select ld.encounter_id, ld.patient_id, ld.visit_date, ld.final_test_result\n" +
			"                           from kenyaemr_etl.etl_mchs_delivery ld\n" +
			"                           where date(ld.visit_date) between date(:startDate) and date(:endDate)) ld\n" +
			"                          on v.patient_id = ld.patient_id\n" +
			"                left join (select t.encounter_id, t.patient_id, t.visit_date, t.hts_entry_point, t.final_test_result as final_test_result\n" +
			"                           from kenyaemr_etl.etl_hts_test t\n" +
			"                           where date(t.visit_date) between date(:startDate) and date(:endDate)) t\n" +
			"                          on v.patient_id = t.patient_id and v.visit_date = t.visit_date\n" +
			"       where date(v.visit_date) between date(:startDate) and date(:endDate)) a;";

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
