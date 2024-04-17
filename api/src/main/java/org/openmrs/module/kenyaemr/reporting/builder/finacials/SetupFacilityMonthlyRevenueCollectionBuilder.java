/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.builder.finacials;

import org.openmrs.module.kenyacore.report.HybridReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractHybridReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.finacials.FacilityRevenueSummaries;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Builds({ "kenyaemr.common.report.facility.monthly.collection" })
public class SetupFacilityMonthlyRevenueCollectionBuilder extends AbstractHybridReportBuilder {

    @Override
    protected Mapped<CohortDefinition> buildCohort(HybridReportDescriptor hybridReportDescriptor,
                                                   PatientDataSetDefinition patientDataSetDefinition) {
        return null;
    }

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
                Date.class), new Parameter("dateBasedReporting", "", String.class));
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
        SqlDataSetDefinition dsdM1 = new SqlDataSetDefinition();
        dsdM1.setName("M1");
        dsdM1.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsdM1.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsdM1.setSqlQuery(FacilityRevenueSummaries.getMonthlySummaryQueryM1());

        SqlDataSetDefinition dsdM2 = new SqlDataSetDefinition();
        dsdM2.setName("M2");
        dsdM2.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsdM2.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsdM2.setSqlQuery(FacilityRevenueSummaries.getMonthlySummaryQueryM2());

        return Arrays.asList(ReportUtils.map((DataSetDefinition) dsdM1, "startDate=${startDate},endDate=${endDate+23h}"),
                ReportUtils.map((DataSetDefinition) dsdM2, "startDate=${startDate},endDate=${endDate+23h}"));
    }
}
