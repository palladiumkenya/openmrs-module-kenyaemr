/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.builder.common;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.library.shared.hiv.art.FmapIndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.openmrs.api.db.hibernate.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Query;
import org.openmrs.Drug;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimCohortLibrary;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

// import org.openmrs.module.reporting.cohort.definition.*;
// import org.openmrs.module.reporting.common.*;
// import org.openmrs.module.reporting.data.converter.*;
// import org.openmrs.module.reporting.dataset.definition.*;
// import org.openmrs.module.reporting.dataset.definition.evaluator.CohortIndicatorDataSetEvaluator;
// import org.openmrs.module.reporting.dataset.definition.evaluator.DataSetDefinitionEvaluator;
// import org.openmrs.module.reporting.evaluation.EvaluationContext;
// import org.openmrs.module.reporting.indicator.CohortIndicator;
// import org.openmrs.module.reporting.indicator.CohortIndicatorResult;
// import org.openmrs.module.reporting.indicator.Indicator;
// import org.openmrs.module.reporting.indicator.service.IndicatorService;
// import org.openmrs.module.reporting.indicator.service.IndicatorServiceImpl;


/**
 * FMAP MOH-729 Report
 */
@Component
@Builds({"kenyaemr.etl.common.report.fcdrr"})
public class ReportBuilder {

    private DataSetDefinition dataSetDefinition;
    private EvaluationContext evaluationContext;
    private IndicatorService indicatorService;

    public ReportBuilder() {
        this.dataSetDefinition = new CohortIndicatorDataSetDefinition();
        this.evaluationContext = new EvaluationContext();
        this.indicatorService = new IndicatorServiceImpl();
    }

    public void buildReport() {
        // Set up your dataset definition
        CohortIndicatorDataSetDefinition dataSetDefinition = new CohortIndicatorDataSetDefinition();

        // Define your dataset columns and indicators
        CohortIndicator openingStockIndicator = new CohortIndicator();
        openingStockIndicator.setName("Opening Stock");
        openingStockIndicator.setSqlQuery("SELECT COUNT(*) FROM stockmgmt_stock_item WHERE date_craeted < :startDate");
        openingStockIndicator.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dataSetDefinition.addColumn("openingStock", openingStockIndicator, "");

        CohortIndicator closingStockIndicator = new CohortIndicator();
        closingStockIndicator.setName("Closing Stock");
        closingStockIndicator.setSqlQuery("SELECT COUNT(*) FROM stockmgmt_stock_item WHERE date_created <= :endDate");
        closingStockIndicator.addParameter(new Parameter("endDate", "End Date", Date.class));
        dataSetDefinition.addColumn("closingStock", closingStockIndicator, "");

        // Set the dataset definition
        this.dataSetDefinition = dataSetDefinition;

        // Set up your evaluation context
        Date startDate = "2024-01-01";
        Date endDate = "2024-01-31";
        evaluationContext.addParameterValue("startDate", startDate);
        evaluationContext.addParameterValue("endDate", endDate);

        // Evaluate dataset definition
        DataSetDefinitionEvaluator dataSetDefinitionEvaluator = new CohortIndicatorDataSetEvaluator();
        dataSetDefinitionEvaluator.evaluate(dataSetDefinition, evaluationContext);

        // Retrieve indicators
        List<Indicator> indicators = dataSetDefinition.getMetaData().getIndicators();

        // Display results
        for (Indicator indicator : indicators) {
            CohortIndicatorResult result = indicatorService.evaluate(new CohortIndicator(indicator), evaluationContext);
            System.out.println(indicator.getName() + ": " + result.getValue());
        }
    }
}