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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.anc.ANCOtherIllnessesDataDefinition;
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
 * Evaluates  Data Definition to Other Illnesses
 */
@Handler(supports=ANCOtherIllnessesDataDefinition.class, order=50)
public class ANCOtherIllnessesDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);
        String qry ="SELECT v.encounter_id, (case v.has_other_illnes  when 1065 then\n" +
                "  concat('Yes (',\n" +
                "              concat_ws(',',max(if(o.value_coded=149019,'1',null)),max(if(o.value_coded=148432,'2',null)),max(if(o.value_coded=153754,'3',null)),\n" +
                "              max(if(o.value_coded=159351,'4',null)),max(if(o.value_coded=119270,'5',null)),max(if(o.value_coded=120637,'6',null)),\n" +
                "              max(if(o.value_coded=145438,'7',null)),max(if(o.value_coded=1295,'8',null)),max(if(o.value_coded=120576,'9',null)),\n" +
                "              max(if(o.value_coded=119692,'10',null)),max(if(o.value_coded=120291,'11',null)),max(if(o.value_coded=119481,'12',null)),\n" +
                "              max(if(o.value_coded=118631,'13',null)),max(if(o.value_coded=117855,'14',null)),max(if(o.value_coded=117789,'15',null)),\n" +
                "              max(if(o.value_coded=139071,'16',null)),max(if(o.value_coded=115728,'17',null)),max(if(o.value_coded=117399,'18',null)),\n" +
                "              max(if(o.value_coded=117321,'19',null)),max(if(o.value_coded=151342,'20',null)),max(if(o.value_coded=133687,'21',null)),\n" +
                "              max(if(o.value_coded=115115,'22',null)),max(if(o.value_coded=114662,'23',null)),max(if(o.value_coded=117703,'24',null)),\n" +
                "              max(if(o.value_coded=118976,'25',null)),\n" +
                "              ')')\n" +
                ") when 1066 then \"No\" else \"\" end) as other_illness\n" +
                "from kenyaemr_etl.etl_mch_antenatal_visit v\n" +
                "left join openmrs.obs o on o.encounter_id=v.encounter_id and o.concept_id=1284\n" +
                "GROUP BY v.encounter_id;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
