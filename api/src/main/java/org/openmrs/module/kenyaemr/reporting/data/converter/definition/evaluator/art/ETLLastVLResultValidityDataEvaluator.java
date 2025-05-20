/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.evaluator.art;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLLastVLResultValidityDataDefinition;
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
 * Evaluates Last VL result validity Data Definition
 */
@Handler(supports= ETLLastVLResultValidityDataDefinition.class, order=50)
public class ETLLastVLResultValidityDataEvaluator implements PersonDataEvaluator {

    @Autowired
    private EvaluationService evaluationService;

    public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context) throws EvaluationException {
        EvaluatedPersonData c = new EvaluatedPersonData(definition, context);

        String qry = "select vt.patient_id,\n" +
                "       if((vt.vl_result >= 200 and timestampdiff(MONTH, date(vt.date_test_requested), date(:endDate)) >= 3)\n" +
                "              OR (timestampdiff(YEAR, d.DOB, date(vt.date_test_requested)) between 0 and 24 and\n" +
                "                  ((vt.lab_test = 856 and vt.vl_result < 200) OR (vt.lab_test = 1305 and vt.vl_result = 1302)) and\n" +
                "                  timestampdiff(MONTH, date(vt.date_test_requested), date(:endDate)) >= 6)\n" +
                "              OR (timestampdiff(MONTH, vt.date_started_art, date(:endDate)) >= 3 and vt.vl_result is null and\n" +
                "                  vt.previous_test_result is null and vt.base_viral_load_test_result is null)\n" +
                "              OR (TIMESTAMPDIFF(MONTH, date(vt.date_started_art), date(:endDate)) >= 3 and\n" +
                "                  (vt.breastfeeding_status = 1065 or vt.pregnancy_status = 1065) and\n" +
                "                  (vt.latest_hiv_followup_visit > vt.date_test_requested OR vt.date_test_requested is null))\n" +
                "              OR (timestampdiff(MONTH, vt.date_test_requested, date(:endDate)) >= 6 and\n" +
                "                  timestampdiff(YEAR, d.DOB, date(vt.date_test_requested)) between 0 and 24 and\n" +
                "                  timestampdiff(MONTH, vt.date_started_art, date(:endDate)) >= 3 and\n" +
                "                  ((vt.lab_test = 856 and vt.vl_result < 200) OR (vt.lab_test = 1305 and vt.vl_result = 1302)))\n" +
                "              OR (timestampdiff(YEAR, d.DOB, date(vt.date_test_requested)) > 24 and\n" +
                "                  TIMESTAMPDIFF(MONTH, date(vt.date_started_art), date(:endDate)) >= 3 and\n" +
                "                  ((vt.lab_test = 856 and vt.vl_result < 200) OR (vt.lab_test = 1305 and vt.vl_result = 1302)) and\n" +
                "                  timestampdiff(MONTH, date(vt.date_test_requested), date(:endDate)) >= 12)\n" +
                "              OR (vt.pregnancy_status = 1065 or vt.breastfeeding_status = 1065 and\n" +
                "                                                ((vt.lab_test = 856 and vt.vl_result < 200) OR\n" +
                "                                                 (vt.lab_test = 1305 and vt.vl_result = 1302)) and\n" +
                "                                                vt.previous_order_reason in (1434, 159882))\n" +
                "              and timestampdiff(MONTH, date(vt.date_test_requested), date(:endDate)) >= 6, 'Invalid',\n" +
                "          'Valid') as vl_status\n" +
                "from kenyaemr_etl.etl_viral_load_validity_tracker vt\n" +
                "         inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = vt.patient_id\n" +
                "group by vt.patient_id;";

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
