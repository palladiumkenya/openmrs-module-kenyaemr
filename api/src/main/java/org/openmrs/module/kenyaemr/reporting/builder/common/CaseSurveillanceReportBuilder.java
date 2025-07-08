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

import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.publicHealthActionReport.PublicHealthActionIndicatorLibrary;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Report builder for Case surveillance report
 */
@Component
@Builds({"kenyaemr.etl.common.report.caseSurveillance"})
public class CaseSurveillanceReportBuilder extends AbstractReportBuilder {

    @Autowired
    private PublicHealthActionIndicatorLibrary publicHealthActionIndicatorLibrary;

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(
                new Parameter("startDate", "Start Date", Date.class),
                new Parameter("endDate", "End Date", Date.class),
                new Parameter("dateBasedReporting", "", String.class)
        );
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor, ReportDefinition reportDefinition) {
        return Arrays.asList(ReportUtils.map(caseSurveillance(), "startDate=${startDate},endDate=${endDate}")
        );
    }

    protected DataSetDefinition caseSurveillance() {
        CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
        cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cohortDsd.setName("caseSurveillance");
        cohortDsd.setDescription("Case Surveillance Report");

        cohortDsd.addColumn("HIV+ and NOT Linked", "", ReportUtils.map(publicHealthActionIndicatorLibrary.notLinked(), "startDate=${startDate},endDate=${endDate}"), "");
        cohortDsd.addColumn("Pregnant and postpartum women at high risk (ML-based) not linked to PrEP", "(Tested within past 7 days)", ReportUtils.map(publicHealthActionIndicatorLibrary.pregnantPostPartumNotLinkedToPrep(), ""), "");
        cohortDsd.addColumn("HEI (6-8 weeks) without DNA PCR results", "(Turning 6-8 weeks within past 7 days", ReportUtils.map(publicHealthActionIndicatorLibrary.heiSixToEightWeeksMissingPCRTests(), ""), "");
        cohortDsd.addColumn("HEI (24 months) without a final documented outcome", "(Turning 24 months within past 7 days)", ReportUtils.map(publicHealthActionIndicatorLibrary.hei24MonthsUndocumentedOutcome(), ""), "");
        cohortDsd.addColumn("Delayed viral load testing (Visited facility were eligible for vl but sample not taken)", "", ReportUtils.map(publicHealthActionIndicatorLibrary.delayedVLTestingZeroGracePeriod(), "startDate=${startDate},endDate=${endDate}"), "");
        cohortDsd.addColumn("Virally Unsuppressed without Enhanced Adherence Counselling", "(No EAC past 2 weeks)", ReportUtils.map(publicHealthActionIndicatorLibrary.txCUrrUnsuppressedWithoutEAC(), "startDate=${startDate},endDate=${endDate}"), "");

        return cohortDsd;

    }
}
