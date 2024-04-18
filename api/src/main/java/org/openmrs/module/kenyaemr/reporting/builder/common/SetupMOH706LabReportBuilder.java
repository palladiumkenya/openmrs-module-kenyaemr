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
import org.openmrs.module.kenyaemr.reporting.library.MOH706.Moh706LabCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.MOH706.Moh706IndicatorLibrary;
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

import static org.openmrs.module.kenyacore.report.ReportUtils.map;

@Component
@Builds({ "kenyaemr.ehrReports.706.report" })
public class SetupMOH706LabReportBuilder extends AbstractReportBuilder {


    private final Moh706IndicatorLibrary moh706IndicatorLibrary;

    @Autowired
    public SetupMOH706LabReportBuilder(Moh706LabCohortLibrary moh706CohortLibrary, Moh706IndicatorLibrary moh706IndicatorLibrary) {
        this.moh706IndicatorLibrary = moh706IndicatorLibrary;
    }

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
                Date.class), new Parameter("dateBasedReporting", "", String.class));
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,
                                                            ReportDefinition reportDefinition) {
        return Arrays.asList(map(getMoh706LabDatasetDefinition(),
                "startDate=${startDate},endDate=${endDate+23h}"));
    }

    private DataSetDefinition getMoh706LabDatasetDefinition() {
        CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
        cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cohortDsd.setName("MOH706");
        cohortDsd.setDescription("MOH 706 for the lab");

        //URINE ANALYSIS
        cohortDsd.addColumn("UAGL", "1.2 Glucose",
                ReportUtils.map(moh706IndicatorLibrary.getAllUrineAnalysisGlucoseTestsPositives(), "startDate=${startDate},endDate=${endDate}"), "");

        return cohortDsd;
    }

}

