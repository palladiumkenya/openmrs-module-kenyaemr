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

import org.openmrs.PatientIdentifierType;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.ClinicianWorkloadCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.KenyaEMROpenMRSNumberDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.AgeAtReportingDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.clinicianWorkload.CheckinDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.clinicianWorkload.CheckinProviderDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.clinicianWorkload.ClinicalEncounterDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.clinicianWorkload.ClinicalEncounterProviderDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.clinicianWorkload.PrescriptionDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.clinicianWorkload.PrescriptionProviderDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.clinicianWorkload.TriageDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.clinicianWorkload.TriageProviderDataDefinition;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.ConvertedPersonDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PreferredNameDataDefinition;
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
@Builds({"kenyaemr.ehrReports.report.clinicianWorkloadReport"})
public class ClinicianWorkloadReportBuilder extends AbstractReportBuilder {

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
        return Arrays.asList(
                ReportUtils.map(datasetColumns(), "startDate=${startDate},endDate=${endDate}")
        );
    }

    protected DataSetDefinition datasetColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("providerWorkload");
        dsd.setDescription("Provider workload information");
        dsd.addSortCriteria("Visit Date", SortCriteria.SortDirection.ASC);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        PatientIdentifierType sha = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.SHA_UNIQUE_IDENTIFICATION_NUMBER);

        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition shaDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(sha.getName(), sha), identifierFormatter);

        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);

        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
        ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        CheckinDateDataDefinition checkinDateDataDefinition = new CheckinDateDataDefinition();
        checkinDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        checkinDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        CheckinProviderDataDefinition checkinProviderDataDefinition = new CheckinProviderDataDefinition();
        checkinProviderDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        checkinProviderDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        TriageDateDataDefinition triageDateDataDefinition = new TriageDateDataDefinition();
        triageDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        triageDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        TriageProviderDataDefinition triageProviderDataDefinition = new TriageProviderDataDefinition();
        triageProviderDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        triageProviderDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        ClinicalEncounterDateDataDefinition clinicalEncounterDateDataDefinition = new ClinicalEncounterDateDataDefinition();
        clinicalEncounterDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        clinicalEncounterDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        ClinicalEncounterProviderDataDefinition clinicalEncounterProviderDataDefinition = new ClinicalEncounterProviderDataDefinition();
        clinicalEncounterProviderDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        clinicalEncounterProviderDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        PrescriptionDateDataDefinition prescriptionDateDataDefinition = new PrescriptionDateDataDefinition();
        prescriptionDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        prescriptionDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        PrescriptionProviderDataDefinition prescriptionProviderDataDefinition = new PrescriptionProviderDataDefinition();
        prescriptionProviderDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        prescriptionProviderDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        dsd.addColumn("id", new PatientIdDataDefinition(), "");
        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("OpenMRS Id", new KenyaEMROpenMRSNumberDataDefinition(), "");
        dsd.addColumn("SHA No", shaDef, "");
        dsd.addColumn("Age", ageAtReportingDataDefinition, "endDate=${endDate}");
        dsd.addColumn("Sex", new GenderDataDefinition(), "");
        dsd.addColumn("Checkin Date", checkinDateDataDefinition, paramMapping);
        dsd.addColumn("Checkin Provider", checkinProviderDataDefinition, paramMapping);
        dsd.addColumn("Triage Date", triageDateDataDefinition, paramMapping);
        dsd.addColumn("Triage Provider", triageProviderDataDefinition, paramMapping);
        dsd.addColumn("Clinical Encounter Date", clinicalEncounterDateDataDefinition, paramMapping);
        dsd.addColumn("Clinical Encounter Provider", clinicalEncounterProviderDataDefinition, paramMapping);
        dsd.addColumn("Prescription Date", prescriptionDateDataDefinition, paramMapping);
        dsd.addColumn("Prescription Provider", prescriptionProviderDataDefinition, paramMapping);

        ClinicianWorkloadCohortDefinition cd = new ClinicianWorkloadCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addRowFilter(cd, paramMapping);
        return dsd;

    }
}
