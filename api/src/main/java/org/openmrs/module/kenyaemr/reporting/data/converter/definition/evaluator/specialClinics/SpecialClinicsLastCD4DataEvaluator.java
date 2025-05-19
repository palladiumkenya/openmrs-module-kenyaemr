/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.specialClinics;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsHFAZScoreDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.SpecialClinicsLastCD4DataDefinition;
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
     * Evaluates Last CD4
 */
@Handler(supports= SpecialClinicsLastCD4DataDefinition.class, order=50)
public class SpecialClinicsLastCD4DataEvaluator implements EncounterDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedEncounterData evaluate(EncounterDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedEncounterData c = new EvaluatedEncounterData(definition, context);

        SpecialClinicsLastCD4DataDefinition cohortDefinition = (SpecialClinicsLastCD4DataDefinition) definition;
        String specialClinic = cohortDefinition.getSpecialClinic();

        String qry = "select s.encounter_id,\n" +
                "                           mid(max(concat(coalesce(date(x.date_test_requested), date(x.visit_date)),\n" +
                "                                          if(x.lab_test = 5497, x.test_result, if(x.lab_test = 167718 and x.test_result = 1254, '>200',\n" +
                "                                                                                  if(x.lab_test = 167718 and x.test_result = 167717,\n" +
                "                                                                                     '<=200',\n" +
                "                                                                                     if(x.lab_test = 730, concat(x.test_result, '%'), '')))),\n" +
                "                                          '')),\n" +
                "                               11) as cd4\n" +
                "                    from kenyaemr_etl.etl_special_clinics s\n" +
                "                             inner join kenyaemr_etl.etl_laboratory_extract x on s.patient_id = x.patient_id\n" +
                "                    where s.visit_date between date(:startDate) and date(:endDate)\n" +
                "                      and date(x.visit_date) <= date(:endDate)\n" +
                "                      and lab_test in (167718, 5497, 730)\n" +
                "                    and s.special_clinic_form_uuid = '"+specialClinic+"'\n" +
                "                    GROUP BY x.encounter_id;";

        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        queryBuilder.append(qry);
        Date startDate = (Date)context.getParameterValue("startDate");
        Date endDate = (Date)context.getParameterValue("endDate");
        queryBuilder.addParameter("endDate", endDate);
        queryBuilder.addParameter("startDate", startDate);
        queryBuilder.addParameter("specialClinic", specialClinic);
        Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
        c.setData(data);
        return c;
    }
}
