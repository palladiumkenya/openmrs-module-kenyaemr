/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.builder.hiv.art;

import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.module.kenyacore.report.HybridReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractHybridReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.NeedsViralLoadTestCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.AgeAtReportingDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLArtStartDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLLastVLDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLLastVLJustificationDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLLastVLResultDataDefinition;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.data.person.definition.ConvertedPersonDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonAttributeDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonIdDataDefinition;
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

/**
 * Created by codehub on 10/28/15.
 * This report documents all possible elements associated with viral load
 */
@Component
@Builds({"kenyaemr.hiv.report.needsViralLoad"})
public class NeedsViralLoadTestReportBuilder extends AbstractHybridReportBuilder {
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(
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

    protected Mapped<CohortDefinition> allPatientsDueForVLCohort() {
        CohortDefinition cd = new NeedsViralLoadTestCohortDefinition();
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setName("Active Patients Due for Viral Load Tests");
        return ReportUtils.map(cd, "endDate=${endDate}");
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {

        PatientDataSetDefinition patientsDatasetDefinition = activePatientsDueForVlDataSetDefinition("activePatientsDueForVl");
        patientsDatasetDefinition.addRowFilter(allPatientsDueForVLCohort());
        DataSetDefinition vlEligiblePatientsDsd = patientsDatasetDefinition;

        return Arrays.asList(ReportUtils.map(vlEligiblePatientsDsd, "endDate=${endDate}")
        );
    }

    protected PatientDataSetDefinition activePatientsDueForVlDataSetDefinition(String datasetName) {

        PatientDataSetDefinition dsd = new PatientDataSetDefinition(datasetName);
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String defParam = "endDate=${endDate}";

        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        PatientIdentifierType sha = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.SHA_UNIQUE_IDENTIFICATION_NUMBER);

        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(upn.getName(), upn), identifierFormatter);
        DataDefinition shaDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(sha.getName(), sha), identifierFormatter);
        AgeAtReportingDataDefinition ageAtReportingDataDefinition = new AgeAtReportingDataDefinition();
        ageAtReportingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        ETLLastVLResultDataDefinition lastVlResultDataDefinition = new ETLLastVLResultDataDefinition();
        lastVlResultDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        ETLLastVLDateDataDefinition lastVLDateDataDefinition = new ETLLastVLDateDataDefinition();
        lastVLDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        ETLLastVLJustificationDataDefinition eTLLastVLJustificationDataDefinition = new ETLLastVLJustificationDataDefinition();
        eTLLastVLJustificationDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        DataConverter formatter = new ObjectFormatter("{familyName}, {givenName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), formatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);

        dsd.addColumn("id", new PersonIdDataDefinition(), "");
        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("CCC No", identifierDef, "");
        dsd.addColumn("SHA No", shaDef, "");
        dsd.addColumn("DOB", new BirthdateDataDefinition(), "", new BirthdateConverter(DATE_FORMAT));
        dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
        dsd.addColumn("Age at reporting", ageAtReportingDataDefinition, defParam);
        dsd.addColumn("Phone number", new PersonAttributeDataDefinition(phoneNumber), "");
        dsd.addColumn("Art Start Date", new ETLArtStartDateDataDefinition(), "", new DateConverter(DATE_FORMAT));
        dsd.addColumn("Last VL Date", lastVLDateDataDefinition, defParam, new DateConverter(DATE_FORMAT));
        dsd.addColumn("Last VL Justification", eTLLastVLJustificationDataDefinition, defParam);
        dsd.addColumn("Last VL Result", lastVlResultDataDefinition, defParam);

        return dsd;
    }
}
