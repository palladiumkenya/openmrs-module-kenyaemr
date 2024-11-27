/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.ortho;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.ortho.OrthoAssistiveTechnologyProductDataDefinition;
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
 *
 */
@Handler(supports= OrthoAssistiveTechnologyProductDataDefinition.class, order=50)
public class OrthoAssistiveTechnologyProductDataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        String qry = "select v.encounter_id,\n" +
                "(case v.procedure_done when 1107 then 'None'\n" +
                "            when 1000133 then 'Cryotherapy'\n" +
                "            when 127642 then 'Transverse friction massage'\n" +
                "            when 162158 then 'Compression bandaging'\n" +
                "            when 527 then 'Splinting'\n" +
                "            when 165390 then 'Pulsed electromagnetic field therapy(PEMF)'\n" +
                "            when 5981 then 'Thermotherapy'\n" +
                "            when 160925 then 'Limb Elevation'\n" +
                "            when 166403 then 'Plaster of Paris(POP) application'\n" +
                "            when 108 then 'Isometric exercises'\n" +
                "            when 137971 then 'ROM exercises'\n" +
                "            when 5622 then 'Joint Aspiration'\n" +
                "            when 5108 then 'Gait Training'\n" +
                "            when 1933 then 'TENS,SWD'\n" +
                "            when 127642 then 'Traction and counter traction'\n" +
                "            when 162158 then 'Closed Reduction by traction(Gallows/Dunlops/Smiths traction)'\n" +
                "            when 166939 then 'Corrective osteotomy'\n" +
                "            when 165390 then 'Cuff and collar sling'\n" +
                "            when 168650 then 'Stripping'\n" +
                "            when 160925 then 'Intramedullary fixation with  K-wires'\n" +
                "            when 136117 then 'Rigid plate and screw fixation'\n" +
                "            when 108 then 'Closed Reduction(Hippocrates/Stimson/Kocher method)'\n" +
                "            when 137971 then 'Open reduction and internal fixation'\n" +
                "            when 5622 then 'DCP plating'\n" +
                "            when 166737 then 'Arm Sling'\n" +
                "            when 1933 then 'Cast fixed'\n" +
                "            when 127642 then 'Percutaneous fixation'\n" +
                "            when 162158 then 'Excision'\n" +
                "            when 123498 then 'External fixation'\n" +
                "            when 165390 then 'Anthroplasty'\n" +
                "            when 166105 then 'Arthrodesis'\n" +
                "            when 160925 then 'Buddy taping'\n" +
                "            when 164009 then 'Amputation'\n" +
                "            when 163949 then 'Debridement'\n" +
                "            when 137971 then 'Tenoctomy'\n" +
                "            when 5622 then 'Tenodes'\n" +
                "            when 162926 then 'Removal of Plaster' else '' end) as procedure_done\n" +
                "from kenyaemr_etl.etl_orthopaedic_clinic_visit v\n" +
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
