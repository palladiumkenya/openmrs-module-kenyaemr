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

        String qry = "SELECT\n" +
                "    t.patient_id, CASE\n" +
                "        -- 1. If everything is null but there is a request, it's the 1st VL pending results\n" +
                "        WHEN t.vl_result IS NULL\n" +
                "            AND t.date_test_result_received IS NULL\n" +
                "            AND t.previous_test_result IS NULL\n" +
                "            AND t.previous_date_test_requested IS NULL\n" +
                "            AND t.date_test_requested IS NOT NULL\n" +
                "            THEN '1st vl pending results'\n" +
                "        -- 2. Validity Logic using effective (substituted) values\n" +
                "        WHEN (\n" +
                "            (TIMESTAMPDIFF(MONTH, t.date_started_art, :endDate) >= 3 AND t.base_viral_load_test_result IS NULL) -- First VL new on ART\n" +
                "                OR\n" +
                "            ((t.pregnancy_status = 1065 OR t.breastfeeding_status = 1065)\n" +
                "                AND TIMESTAMPDIFF(MONTH, t.date_started_art, :endDate) >= 3\n" +
                "                AND (t.effective_vl_result IS NOT NULL AND t.effective_date_requested < :endDate)\n" +
                "                AND (t.order_reason NOT IN (159882, 1434, 2001237, 163718))\n" +
                "                ) -- Immediate for PG & BF\n" +
                "                OR\n" +
                "            (t.lab_test = 856 AND t.effective_vl_result >= 200\n" +
                "                AND TIMESTAMPDIFF(MONTH, t.effective_date_requested, :endDate) >= 3\n" +
                "                ) -- Unsuppressed VL\n" +
                "                OR\n" +
                "            (((t.lab_test = 1305 AND t.effective_vl_result = 1302) OR t.effective_vl_result < 200)\n" +
                "                AND TIMESTAMPDIFF(MONTH, t.effective_date_requested, :endDate) >= 6\n" +
                "                AND TIMESTAMPDIFF(YEAR, t.DOB, t.effective_date_requested) BETWEEN 0 AND 24\n" +
                "                ) -- 0-24 with last suppressed VL\n" +
                "                OR\n" +
                "            (((t.lab_test = 1305 AND t.effective_vl_result = 1302) OR t.effective_vl_result < 200)\n" +
                "                AND TIMESTAMPDIFF(MONTH, t.effective_date_requested, :endDate) >= 12\n" +
                "                AND TIMESTAMPDIFF(YEAR, t.DOB, t.effective_date_requested) > 24\n" +
                "                ) -- > 24 with last suppressed VL\n" +
                "                OR\n" +
                "            ((t.pregnancy_status = 1065 OR t.breastfeeding_status = 1065)\n" +
                "                AND TIMESTAMPDIFF(MONTH, t.date_started_art, :endDate) >= 3\n" +
                "                AND (t.order_reason IN (159882, 1434, 2001237, 163718) AND TIMESTAMPDIFF(MONTH, t.effective_date_requested, :endDate) >= 6)\n" +
                "                AND ((t.lab_test = 1305 AND t.effective_vl_result = 1302) OR (t.effective_vl_result < 200))\n" +
                "                ) -- PG & BF after baseline < 200\n" +
                "            ) THEN 'Invalid'\n" +
                "        ELSE 'Valid'\n" +
                "        END AS vl_status\n" +
                "FROM (\n" +
                "         SELECT\n" +
                "             v.*,\n" +
                "             d.DOB,\n" +
                "             -- Substitution Logic: If current is null and requested is later than base/previous, use previous\n" +
                "             IF(v.vl_result IS NULL\n" +
                "                    AND v.date_test_result_received IS NULL\n" +
                "                    AND v.date_test_requested > GREATEST(COALESCE(v.base_viral_load_test_date, '1900-01-01'), COALESCE(v.previous_date_test_requested, '1900-01-01')),\n" +
                "                v.previous_test_result,\n" +
                "                v.vl_result\n" +
                "             ) AS effective_vl_result,\n" +
                "             IF(v.vl_result IS NULL\n" +
                "                    AND v.date_test_result_received IS NULL\n" +
                "                    AND v.date_test_requested > GREATEST(COALESCE(v.base_viral_load_test_date, '1900-01-01'), COALESCE(v.previous_date_test_requested, '1900-01-01')),\n" +
                "                v.previous_date_test_requested,\n" +
                "                v.date_test_requested\n" +
                "             ) AS effective_date_requested\n" +
                "         FROM kenyaemr_etl.etl_viral_load_validity_tracker v\n" +
                "                  INNER JOIN kenyaemr_etl.etl_patient_demographics d ON v.patient_id = d.patient_id\n" +
                "         WHERE v.date_test_requested <= date(:endDate)\n" +
                "     ) t\n" +
                "ORDER BY t.patient_id ASC;";

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
