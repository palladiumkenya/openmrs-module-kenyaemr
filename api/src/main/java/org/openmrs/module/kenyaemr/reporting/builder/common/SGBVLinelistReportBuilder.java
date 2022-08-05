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

import org.openmrs.module.kenyacore.report.HybridReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractHybridReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.SGBVLinelistCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.sgbv.*;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.AgeAtReportingDataDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.person.definition.*;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Builds({"kenyaemr.common.report.sgblinelist"})
public class SGBVLinelistReportBuilder extends AbstractHybridReportBuilder {
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(
                new Parameter("startDate", "Start Date", Date.class),
                new Parameter("endDate", "End Date", Date.class),
                new Parameter("dateBasedReporting", "", String.class)
        );
    }

    @Override
    protected void addColumns(HybridReportDescriptor report, PatientDataSetDefinition dsd) {
    }

    @Override
    protected Mapped<CohortDefinition> buildCohort(HybridReportDescriptor descriptor, PatientDataSetDefinition dsd) {
        return null;
    }


    protected Mapped<CohortDefinition> allPatientsCohort() {
        CohortDefinition cd = new SGBVLinelistCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setName("SGBV Patients Linelist");
        return ReportUtils.map(cd, "startDate=${startDate},endDate=${endDate}");
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {

        PatientDataSetDefinition allVisits = sgbvPatientsLinelistDataSetDefinition("sgbvLinelistPatients");
        allVisits.addRowFilter(allPatientsCohort());
        DataSetDefinition allPatientsDSD = allVisits;

        return Arrays.asList(
                ReportUtils.map(allPatientsDSD, "startDate=${startDate},endDate=${endDate}")
        );
    }

    protected PatientDataSetDefinition sgbvPatientsLinelistDataSetDefinition(String datasetName) {

        PatientDataSetDefinition dsd = new PatientDataSetDefinition(datasetName);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String defParam = "startDate=${startDate},endDate=${endDate}";

        PtnAgeDataDefinition currentAgeDataDefinition = new PtnAgeDataDefinition();
	currentAgeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        
        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
	ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        
        dsd.addColumn("id", new PtnIdDataDefinition(), "");
        dsd.addColumn("Name", new PtnNameDataDefinition(), "");//
        dsd.addColumn("Patient Clinic Number", new PcnDataDefinition(), "");//
        dsd.addColumn("Age", currentAgeDataDefinition, "endDate=${endDate}");
        dsd.addColumn("Sex", new PtnSexDataDefinition(), "");//
        dsd.addColumn("Telephone", new TelephoneDataDefinition(), "");//
        dsd.addColumn("Date Screened", new ArrivalDateDataDefinition(), "", new DateConverter(DATE_FORMAT));//
        dsd.addColumn("Last Visit", new ArrivalDateDataDefinition(), "", new DateConverter(DATE_FORMAT));//
        dsd.addColumn("TCA", new TCADataDefinition(), "", new DateConverter(DATE_FORMAT));//

        SGBVLinelistCohortDefinition cd = new SGBVLinelistCohortDefinition();
	cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
	dsd.addRowFilter(cd, defParam);
        return dsd;
    }
}
