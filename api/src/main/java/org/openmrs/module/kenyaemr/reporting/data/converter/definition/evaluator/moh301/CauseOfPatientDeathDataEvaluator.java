/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.moh301;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.CauseOfPatientDeathDataDefinition;
import org.openmrs.module.reporting.data.person.EvaluatedPersonData;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.data.person.evaluator.PersonDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Evaluates a CauseOfDeathDataDefinition
 */
@Handler(supports = CauseOfPatientDeathDataDefinition.class, order = 50)
public class CauseOfPatientDeathDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "select d.patient_id, case d.cause_of_death\n" +
                "when 160158 then 'Human immunodeficiency virus disease resulting in other specified diseases'\n" +
                "when 133478 then 'Natural Death with Proved Cause'\n" +
                "when 123812 then 'Undetermined Manner of Death, Accidental Cause Suspected'\n" +
                "when 163324 then 'Co-infection with human immunodeficiency virus and tuberculosis'\n" +
                "when 116030 then 'Neoplasm/cancer'\n" +
                "when 142917 then 'Death of Unknown Cause'\n" +
                "when 160159 then 'HIV resulting in infectious or parasitic disease'\n" +
                "when 156673 then 'HIV disease resulting in mycobacterial infection'\n" +
                "when 156667 then 'HIV disease resulting in Burkitts lymphoma'\n" +
                "when 115195 then 'Malignant lymphoma, Non-Hodgkins'\n" +
                "when 116031 then 'Malignant Neoplasm'\n" +
                "when 156672 then 'HIV disease resulting in multiple malignant neoplasms'\n" +
                "when 155010 then 'AIDS with Kaposi sarcoma'\n" +
                "when 159988 then 'HIV disease resulting in malignant neoplasm'\n" +
                "when 157593 then 'malignant neoplasm of lymphoid, haematopoietic and related tissue, unspecified'\n" +
                "when 123122 then 'Viral Infection'\n" +
                "when 5333 then 'HIV STAGING - SEVERE BACTERIAL INFECTION'\n" +
                "when 5350 then 'HIV STAGING - DISSEMINATED ENDEMIC MYCOSIS'\n" +
                "when 882 then 'Pneumocystis carinii pneumonia'\n" +
                "when 156668 then 'HIV disease resulting in candidiasis'\n" +
                "when 156671 then 'HIV disease resulting in multiple infections'\n" +
                "when 156669 then 'HIV disease resulting in cytomegaloviral disease'\n" +
                "when 171 then 'Fungal infection'\n" +
                "when 165609 then 'Infection due to severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)' else 'N/A' end\n" +
                "from kenyaemr_etl.etl_patient_demographics d where d.death_date between date(:startDate) and date(:endDate);";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        Date startDate = (Date) context.getParameterValue("startDate");
        Date endDate = (Date) context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);
        queryBuilder.addParameter("startDate", startDate);
        queryBuilder.append(qry);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}