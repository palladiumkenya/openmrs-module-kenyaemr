/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.builder.hiv;

import org.openmrs.PatientIdentifierType;
import org.openmrs.module.kenyacore.report.HybridReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractHybridReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
import org.openmrs.module.kenyaemr.calculation.library.hiv.*;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.DateOfEnrollmentArtCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.DateArtStartDateConverter;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.IPTOutcomeDataConverter;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.SimpleResultDateConverter;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.ActivePatientsSnapshotCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.HivConsultationDuringPeriodCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.ActiveInProgramConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.BooleanResultsConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.ActivePatientsPopulationTypeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.MFLCodeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.*;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
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
@Builds({"kenyaemr.hiv.report.appointmentsTracker"})
public class AppointmentsTrackerReportBuilder extends AbstractHybridReportBuilder {
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


    protected Mapped<CohortDefinition> patientsWithHivVisitsCohort() {
        CohortDefinition cd = new HivConsultationDuringPeriodCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setName("Patients Visits");
        return ReportUtils.map(cd, "startDate=${startDate},endDate=${endDate}");
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {

        PatientDataSetDefinition allVisits = patientsWithVisitsDataSetDefinition("visitedPatients");
        allVisits.addRowFilter(patientsWithHivVisitsCohort());
        DataSetDefinition patientsDSD = allVisits;

        return Arrays.asList(
                ReportUtils.map(patientsDSD, "startDate=${startDate},endDate=${endDate}")
        );
    }

    protected PatientDataSetDefinition patientsWithVisitsDataSetDefinition(String datasetName) {

        PatientDataSetDefinition dsd = new PatientDataSetDefinition(datasetName);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String defParam = "startDate=${startDate},endDate=${endDate}";

        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);      
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(upn.getName(), upn), identifierFormatter);
      
      	PatientAppointmentScheduledDateDataDefinition patientAppointmentScheduledDateDataDefinition = new PatientAppointmentScheduledDateDataDefinition();
		patientAppointmentScheduledDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		patientAppointmentScheduledDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		
		PatientAppointmentTCADateDataDefinition patientAppointmentTCADateDataDefinition = new PatientAppointmentTCADateDataDefinition();
		patientAppointmentTCADateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		patientAppointmentTCADateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
       
        DataConverter formatter = new ObjectFormatter("{familyName}, {givenName}");
		DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), formatter);

		dsd.addColumn("id", new PatientIdDataDefinition(), "");
		dsd.addColumn("Name", nameDef, "");
		dsd.addColumn("Date of Birth", new BirthdateDataDefinition(), "", new BirthdateConverter(DATE_FORMAT));
		dsd.addColumn("Age", new AgeDataDefinition(), "");
		dsd.addColumn("Sex", new GenderDataDefinition(), "");
        dsd.addColumn("CCC No", identifierDef, "");       
        dsd.addColumn("Date Appointment Scheduled", patientAppointmentScheduledDateDataDefinition,"startDate=${startDate},endDate=${endDate}", new DateConverter(DATE_FORMAT));
        dsd.addColumn("Next Appointment Date", patientAppointmentTCADateDataDefinition, "startDate=${startDate},endDate=${endDate}", new DateConverter(DATE_FORMAT));
        return dsd;
    }
}
