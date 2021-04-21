/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.anc;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.anc.ANCCounselledDoneDataDefinition;
import org.openmrs.module.reporting.data.encounter.EvaluatedEncounterData;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDataDefinition;
import org.openmrs.module.reporting.data.encounter.evaluator.EncounterDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Evaluates a Visit Number Data Definition to produce a Visit Number
 */
@Handler(supports=ANCCounselledDoneDataDefinition.class, order=50)
public class ANCCounselledDoneDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry ="SELECT v.encounter_id, (case v.counselled when 1065 then\n" +
                "                  concat('Yes \\n(',\n" +
                "                              concat_ws(',',max(if(o.value_coded=159758,'1',null)),max(if(o.value_coded=159857,'2',null)),max(if(o.value_coded=156277,'3',null)),\n" +
                "                              max(if(o.value_coded=1914,'4',null)),max(if(o.value_coded=159854,'5',null)),max(if(o.value_coded=159856,'6',null)),\n" +
                "                              max(if(o.value_coded=161651,'7',null)),max(if(o.value_coded=1381,'8',null)),\n" +
                "                              ')')\n" +
                "                ) when 1066 then \"No\" else \"\" end) as counselled\n" +
                "                from kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "                left join openmrs.obs o on o.encounter_id=v.encounter_id and o.concept_id=159853\n" +
                "                GROUP BY v.encounter_id;";
//                "select\n" +
//                "v.encounter_id,\n" +
//                "(case v.counselled when 1065 then \"Yes\" when 1066 then \"No\" else \"\" end) as counselled\n" +
//                "from kenyaemr_etl.etl_mch_antenatal_visit v;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
