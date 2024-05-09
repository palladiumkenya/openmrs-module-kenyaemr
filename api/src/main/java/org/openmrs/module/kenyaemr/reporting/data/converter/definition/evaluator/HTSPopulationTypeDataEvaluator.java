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
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.HTSPopulationTypeDataDefinition;
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
 * Evaluates a HTSTestSettingDataEvaluator to produce a VisitData
 */
@Handler(supports= HTSPopulationTypeDataDefinition.class, order=50)
public class HTSPopulationTypeDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry = "select\n" +
                "fup.patient_id,\n" +
                "  CONCAT_WS('\\r\\n',fup.people_in_prison,fup.priority_population_type,fup.couple_discordant) as Population_Type\n" +
                "from  (SELECT patient_id,\n" +
                "         mid(max(concat(visit_date,(case people_in_prison\n" +
                "                                    when 163488 then 'Community'\n" +
                "                                    when 1142 then 'Staff'\n" +
                "                                    when 163488 then 'Inmates' else '' end), '' )),11) as people_in_prison,\n" +
                "         mid(max(concat(visit_date,(case couple_discordant\n" +
                "                                    when 1065 then 'Couple Discordant' else '' end), '' )),11) as couple_discordant,\n" +
                "         mid(max(concat(visit_date,(case key_population_type\n" +
                "                                   when 159674 then 'Fisher folk'\n" +
                "                                    when 162198 then 'Truck driver'\n" +
                "                                    when 162277 then 'Prisoner' else '' end), '' )),11) as priority_population_type\n" +
                "        FROM kenyaemr_etl.etl_hts_test GROUP BY patient_id) fup\n" +
                "GROUP BY fup.patient_id;";
        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
