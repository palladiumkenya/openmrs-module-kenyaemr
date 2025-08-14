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
import org.openmrs.module.kenyaemr.reporting.cohort.definition.MoH301CohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.KenyaEMROpenMRSNumberDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.AgeAtReportingDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.ipt.PhoneNumberDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.AdmissionReferralDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.CauseOfPatientDeathDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.CountyAndSubCountyOfResidenceDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.DateOfAdmissionDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.DateOfDischargeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.DiagnosisDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.DischargeOutcomeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.DischargeReferralDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.DischargeRemarksDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.HIVInterventionDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.HIVStatusIPDDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.NutritionSupportDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.TreatmentDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.moh301.VillageEstateAndLandmarkDatadefinition;
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
@Builds({"kenyaemr.ehrReports.report.moh301InpatientRegister"})
public class MoH301InpatientReportBuilder extends AbstractReportBuilder {

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
        dsd.setName("admittedPatients");
        dsd.setDescription("Admitted Patients Report");
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

        DateOfAdmissionDataDefinition dateOfAdmissionDataDefinition = new DateOfAdmissionDataDefinition();
        dateOfAdmissionDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        dateOfAdmissionDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        HIVInterventionDataDefinition hivInterventionDataDefinition = new HIVInterventionDataDefinition();
        hivInterventionDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        hivInterventionDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        HIVStatusIPDDataDefinition hivStatusDataDefinition = new HIVStatusIPDDataDefinition();
        hivStatusDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        hivStatusDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        DiagnosisDataDefinition diagnosisDataDefinition = new DiagnosisDataDefinition();
        diagnosisDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        diagnosisDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        TreatmentDataDefinition treatmentDataDefinition = new TreatmentDataDefinition();
        treatmentDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        treatmentDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        NutritionSupportDataDefinition nutritionSupportDataDefinition = new NutritionSupportDataDefinition();
        nutritionSupportDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        nutritionSupportDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        DateOfDischargeDataDefinition dateOfDischargeDataDefinition = new DateOfDischargeDataDefinition();
        dateOfDischargeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        dateOfDischargeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        DischargeOutcomeDataDefinition dischargeOutcomeDataDefinition = new DischargeOutcomeDataDefinition();
        dischargeOutcomeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        dischargeOutcomeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        CauseOfPatientDeathDataDefinition causeOfDeathDataDefinition = new CauseOfPatientDeathDataDefinition();
        causeOfDeathDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        causeOfDeathDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        AdmissionReferralDataDefinition admissionReferralDataDefinition = new AdmissionReferralDataDefinition();
        admissionReferralDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        admissionReferralDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        DischargeReferralDataDefinition dischargeReferralDataDefinition = new DischargeReferralDataDefinition();
        dischargeReferralDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        dischargeReferralDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        DischargeRemarksDataDefinition dischargeRemarksDataDefinition = new DischargeRemarksDataDefinition();
        dischargeRemarksDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        dischargeRemarksDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        AdmissionReferralDataDefinition referredFromDataDefinition = new AdmissionReferralDataDefinition();
        referredFromDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        referredFromDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        DischargeReferralDataDefinition referredToDataDefinition = new DischargeReferralDataDefinition();
        referredToDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        referredToDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        dsd.addColumn("id", new PatientIdDataDefinition(), "");
        dsd.addColumn("Admission Date", dateOfAdmissionDataDefinition, paramMapping);
        dsd.addColumn("Inpatient Number", new KenyaEMROpenMRSNumberDataDefinition(), "");
        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("Age", ageAtReportingDataDefinition, "endDate=${endDate}");
        dsd.addColumn("Sex", new GenderDataDefinition(), "");
        dsd.addColumn("County and Sub-county", new CountyAndSubCountyOfResidenceDataDefinition(), "");
        dsd.addColumn("Village Estate and LandMark", new VillageEstateAndLandmarkDatadefinition(), "");
        dsd.addColumn("Telephone Number", new PhoneNumberDataDefinition(), "");
        dsd.addColumn("HIV Intervention", hivInterventionDataDefinition, paramMapping);
        dsd.addColumn("HIV Status", hivStatusDataDefinition, paramMapping);
        dsd.addColumn("Diagnosis", diagnosisDataDefinition, paramMapping);
        dsd.addColumn("Treatment", treatmentDataDefinition, paramMapping);
        dsd.addColumn("Nutrition Support", nutritionSupportDataDefinition, paramMapping);
        dsd.addColumn("Discharge Date", dateOfDischargeDataDefinition, paramMapping);
        dsd.addColumn("Outcome", dischargeOutcomeDataDefinition, paramMapping);
        dsd.addColumn("Cause of Death", causeOfDeathDataDefinition, paramMapping);
        dsd.addColumn("Referred From", referredFromDataDefinition, paramMapping);
        dsd.addColumn("Referred To", referredToDataDefinition, paramMapping);
        dsd.addColumn("Remarks", dischargeRemarksDataDefinition, paramMapping);

        MoH301CohortDefinition cd = new MoH301CohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addRowFilter(cd, paramMapping);
        return dsd;

    }
}
