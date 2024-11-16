/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.dmi.caseReport;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.dmi.casereport.ComplaintDataDefinition;
import org.openmrs.module.reporting.data.visit.EvaluatedVisitData;
import org.openmrs.module.reporting.data.visit.definition.VisitDataDefinition;
import org.openmrs.module.reporting.data.visit.evaluator.VisitDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Evaluates Visit type Data Definition
 */

@Handler(supports = ComplaintDataDefinition.class, order = 50)
public class ComplaintDataEvaluator implements VisitDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedVisitData evaluate(VisitDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedVisitData c = new EvaluatedVisitData(definition, context);

        String qry = "SELECT\n" +
                "    i.visit_id,\n" +
                "    GROUP_CONCAT(\n" +
                "            DISTINCT CASE\n" +
                "                         WHEN i.complaint IS NOT NULL THEN\n" +
                "                             CONCAT(i.complaint, ':', COALESCE(\n" +
                "                                     case i.complaint\n" +
                "                                         when 151 then \"Abdominal pain\"\n" +
                "                                         when 141631 then \"Abnormal Uterine Bleeding\"\n" +
                "                                         when 121543 then \"Anxiety\"\n" +
                "                                         when 148035 then \"Back pain\"\n" +
                "                                         when 840 then \"Bloody Urine\"\n" +
                "                                         when 117671 then \"Blood in stool\"\n" +
                "                                         when 131021 then \"Breast Pain\"\n" +
                "                                         when 120749 then \"Chest Pain\"\n" +
                "                                         when 871 then \"Cold and Chills\"\n" +
                "                                         when 120345 then \"Confusion\"\n" +
                "                                         when 143264 then \"Cough\"\n" +
                "                                         when 113054 then \"Convulsions\"\n" +
                "                                         when 144576 then \"Coma\"\n" +
                "                                         when 106 then \"Coryza\"\n" +
                "                                         when 143129 then \"Crying Infant\"\n" +
                "                                         when 119574 then \"Delirium\"\n" +
                "                                         when 119537 then \"Depression\"\n" +
                "                                         when 142412 then \"Diarrhoea\"\n" +
                "                                         when 122496 then \"Difficult in breathing\"\n" +
                "                                         when 118789 then \"Difficulty in swallowing\"\n" +
                "                                         when 142247 then \"Discharge from Penis\"\n" +
                "                                         when 141830 then \"Dizziness\"\n" +
                "                                         when 141585 then \"Ear Pain\"\n" +
                "                                         when 141128 then \"Epigastric Pain\"\n" +
                "                                         when 131040 then \"Eye Pain\"\n" +
                "                                         when 140941 then \"Excessive Sweating\"\n" +
                "                                         when 114399 then \"Facial Pain\"\n" +
                "                                         when 162626 then \"Fatigue/weakness\"\n" +
                "                                         when 140070 then \"Flank Pain\"\n" +
                "                                         when 140238 then \"Fever\"\n" +
                "                                         when 135367 then \" General Body Malaise\"\n" +
                "                                         when 135462 then \"Genital Ulcer\"\n" +
                "                                         when 139084 then \"Headache\"\n" +
                "                                         when 117698 then \"Hearing Loss\"\n" +
                "                                         when 116214 then \"Hypotension\"\n" +
                "                                         when 879 then \"Itchiness/Pruritus\"\n" +
                "                                         when 116558 then \"Joint Pain\"\n" +
                "                                         when 114395 then \"Leg Pain\"\n" +
                "                                         when 116334 then \"Lethargy\"\n" +
                "                                         when 135595 then \"Loss of Appetite\"\n" +
                "                                         when 135488 then \"Lymphadenopathy\"\n" +
                "                                         when 121657 then \"Memory Loss\"\n" +
                "                                         when 111721 then \"Mouth Ulceration\"\n" +
                "                                         when 131015 then \"Mouth Pain\"\n" +
                "                                         when 133028 then \"Muscle cramps\"\n" +
                "                                         when 133632 then \"Muscle Pain\"\n" +
                "                                         when 121 then \"Mylagia\"\n" +
                "                                         when 5978 then \"Nausea\"\n" +
                "                                         when 112721 then \"Neck Stiffness\"\n" +
                "                                         when 133469 then \"Neck Pain\"\n" +
                "                                         when 133027 then \"Night sweats\"\n" +
                "                                         when 132653 then \"Numbness\"\n" +
                "                                         when 162628 then \"Unexplained bleeding\"\n" +
                "                                         when 125225 then \"Pain when Swallowing\"\n" +
                "                                         when 131034 then \"Pelvic Pain\"\n" +
                "                                         when 5953 then \"Poor Vision\"\n" +
                "                                         when 512 then \"Rash\"\n" +
                "                                         when 127777 then \"Red Eye/ Conjuctivitis\"\n" +
                "                                         when 6017 then \"Refusal to feed\"\n" +
                "                                         when 113224 then \"Running/Blocked nose\"\n" +
                "                                         when 131032 then \"Scrotal Pain\"\n" +
                "                                         when 206 then \"Seizure\"\n" +
                "                                         when 126535 then \"Shoulder Pain\"\n" +
                "                                         when 112989 then \"Shock\"\n" +
                "                                         when 158843 then \"Sore Throat\"\n" +
                "                                         when 141597 then \"Sleep Disturbance\"\n" +
                "                                         when 125198 then \"Swollen Legs\"\n" +
                "                                         when 112200 then \"Tremors\"\n" +
                "                                         when 160208 then \"Urinary Symptoms\"\n" +
                "                                         when 161887 then \"Watery Diarrhoea\"\n" +
                "                                         when 157498 then \"Weakness of Limbs\"\n" +
                "                                         when 832 then \"Weight Loss\"\n" +
                "                                         when 150802 then \"Vaginal Bleeding\"\n" +
                "                                         when 123396 then \"Vaginal Discharge\"\n" +
                "                                         when 122983 then \"Vomiting\"\n" +
                "                                         when 111525 then \"Vertigo\"\n" +
                "                                         when 5622 then \"Other\" end, '-')\n" +
                "\n" +
                "                             )\n" +
                "                ELSE '-'\n" +
                "        END\n" +
                "            ORDER BY i.complaint\n" +
                "            SEPARATOR ', '\n" +
                "    ) AS complaint_onset_date\n" +
                "FROM\n" +
                "    kenyaemr_etl.etl_allergy_chronic_illness i\n" +
                "WHERE\n" +
                "    i.visit_date BETWEEN date(:startDate) AND date(:endDate)\n" +
                "GROUP BY\n" +
                "    i.visit_id;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Date startDate = (Date) context.getParameterValue("startDate");
        Date endDate = (Date) context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);
        queryBuilder.addParameter("startDate", startDate);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
