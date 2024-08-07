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
import org.openmrs.module.kenyaemr.reporting.MohReportUtils.ReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.moh717.Moh717CohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.moh717.Moh717IndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
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
import static org.openmrs.module.kenyaemr.reporting.MohReportUtils.ReportAddonUtils.getGeneralOutPatientFilters;

/**
 * Report builder for MOH717
 * Work load
 */

@Component
@Builds({ "kenyaemr.ehrReports.report.717" })
public class MOH717ReportBuilder extends AbstractReportBuilder {
    private final Moh717CohortLibrary moh717CohortLibrary;

    private final CommonDimensionLibrary commonDimensionLibrary;

    private final Moh717IndicatorLibrary moh717IndicatorLibrary;

    @Autowired
    public MOH717ReportBuilder(Moh717CohortLibrary moh717CohortLibrary, CommonDimensionLibrary commonDimensionLibrary, Moh717IndicatorLibrary moh717IndicatorLibrary) {
        this.moh717CohortLibrary = moh717CohortLibrary;
        this.commonDimensionLibrary = commonDimensionLibrary;
        this.moh717IndicatorLibrary = moh717IndicatorLibrary;
    }

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
                Date.class), new Parameter("dateBasedReporting", "", String.class));
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,
                                                            ReportDefinition reportDefinition) {
        return Arrays.asList(ReportUtils.map(moh717DatasetDefinition(), "startDate=${startDate},endDate=${endDate}"));
    }

    private DataSetDefinition moh717DatasetDefinition() {
        String indParams = "startDate=${startDate},endDate=${endDate}";

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.setName("MOH717");
        dsd.setDescription("MOH 717 Report");

        dsd.addDimension("age", map(commonDimensionLibrary.standardAgeGroups(), "onDate=${endDate}"));
        dsd.addDimension("gender", map(commonDimensionLibrary.gender(), ""));
        dsd.addDimension("state", map(commonDimensionLibrary.newOrRevisits(), "startDate=${startDate},endDate=${endDate}"));



        ReportingUtils.addRow(dsd, "OSN", "OUTPATIENT SERVICES NEW PATIENTS",
                ReportUtils.map(moh717IndicatorLibrary.getAllPatientsWithEncountersWithinReportingPeriod(), indParams), getGeneralOutPatientFilters());

        ReportingUtils.addRow(dsd, "OSR", "OUTPATIENT SERVICES REVIST PATIENTS",
                ReportUtils.map(moh717IndicatorLibrary.getAllPatientsWithEncountersWithinReportingPeriod(), indParams), getGeneralOutPatientFilters());
        return dsd;
    }
}
