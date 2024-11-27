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
import org.openmrs.PersonAttributeType;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.library.hiv.CountyAddressCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.SubCountyAddressCalculation;
import org.openmrs.module.kenyaemr.calculation.library.mchcs.PersonAddressCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.ObsValueDatetimeConverter;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.RDQACalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.MOH420RegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.*;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.ortho.*;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.common.TimeQualifier;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.converter.ObsValueConverter;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDatetimeDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.*;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.EncounterDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Builds({"kenyaemr.ehrReports.report.moh420"})
public class MOH420ReportBuilder extends AbstractReportBuilder {
    public static final String ENC_DATE_FORMAT = "yyyy/MM/dd";
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
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor, ReportDefinition reportDefinition) {
        return Arrays.asList(
                ReportUtils.map(datasetColumns(), "startDate=${startDate},endDate=${endDate}")
        );
    }

    protected DataSetDefinition datasetColumns() {
        EncounterDataSetDefinition dsd = new EncounterDataSetDefinition();
        dsd.setName("MOH420");
        dsd.setDescription("Orthopaedic Technology Visit information");
        dsd.addSortCriteria("Visit Date", SortCriteria.SortDirection.ASC);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
        PatientIdentifierType pcn = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.PATIENT_CLINIC_NUMBER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition patientClinicNo = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(pcn.getName(), pcn), identifierFormatter);






		OPDDiagnosisDataDefinition opdDiagnosisDataDefinition = new OPDDiagnosisDataDefinition();
		opdDiagnosisDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdDiagnosisDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        OrthoReferralFormDataDefinition orthoReferralFormDataDefinition = new OrthoReferralFormDataDefinition();
        orthoReferralFormDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        orthoReferralFormDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

		OrthoVisitTypeDataDefinition orthoVisitTypeDataDefinition = new OrthoVisitTypeDataDefinition();
		orthoVisitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		orthoVisitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        OrthoUnderlyingConditionDataDefinition orthoUnderlyingConditionDataDefinition = new OrthoUnderlyingConditionDataDefinition();
        orthoUnderlyingConditionDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        orthoUnderlyingConditionDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        OrthoInterventionGivenDataDefinition orthoInterventionGivenDataDefinition = new OrthoInterventionGivenDataDefinition();
        orthoInterventionGivenDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        orthoInterventionGivenDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        OrthoPatientReferralDataDefinition orthoPatientReferralDataDefinition = new OrthoPatientReferralDataDefinition();
        orthoPatientReferralDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        orthoPatientReferralDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        OrthoAssistiveTechnologyProductDataDefinition orthoAssistiveTechnologyProductDataDefinition = new OrthoAssistiveTechnologyProductDataDefinition();
        orthoAssistiveTechnologyProductDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        orthoAssistiveTechnologyProductDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        OrthoReceiptNumberDataDefinition orthoReceiptNumberDataDefinition = new OrthoReceiptNumberDataDefinition();
        orthoReceiptNumberDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        orthoReceiptNumberDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        OrthoAmountPaidDataDefinition orthoAmountPaidDataDefinition = new OrthoAmountPaidDataDefinition();
        orthoAmountPaidDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        orthoAmountPaidDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        OrthoPaymentModeDataDefinition orthoPaymentModeDataDefinition = new OrthoPaymentModeDataDefinition();
        orthoPaymentModeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        orthoPaymentModeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        OrthoServiceAreaDataDefinition orthoServiceAreaDataDefinition = new OrthoServiceAreaDataDefinition();
        orthoServiceAreaDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        orthoServiceAreaDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

		PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);

        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("id", new PatientIdDataDefinition(), "");
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));
        dsd.addColumn("Age", new AgeDataDefinition(), "");
        dsd.addColumn("Sex", new GenderDataDefinition(), "");
        dsd.addColumn("Parent/Caregiver Telephone No", new PersonAttributeDataDefinition(phoneNumber), "");   
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));         
		dsd.addColumn("OPD Number (New)", patientClinicNo, "");
        dsd.addColumn("Occupation", new ObsForPersonDataDefinition("Occupation", TimeQualifier.LAST, Dictionary.getConcept(Dictionary.OCCUPATION), null, null), "", new ObsValueConverter());
		//dsd.addColumn("OPD Number (Revisit)", patientClinicNo, "");   //TODO:Determine revisit vs new visit
		dsd.addColumn("County",new CalculationDataDefinition("County", new CountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Sub County", new CalculationDataDefinition("Subcounty", new SubCountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Village", new CalculationDataDefinition("Village/Estate/Landmark", new PersonAddressCalculation()), "",new RDQACalculationResultConverter());
		dsd.addColumn("Visit Type", orthoVisitTypeDataDefinition, paramMapping);
        dsd.addColumn("Referral Form", orthoReferralFormDataDefinition, paramMapping);
        dsd.addColumn("Underlying Condition", orthoUnderlyingConditionDataDefinition, paramMapping);
        dsd.addColumn("Intervention Given", orthoInterventionGivenDataDefinition, paramMapping);
        dsd.addColumn("Patient Referral", orthoPatientReferralDataDefinition, paramMapping);
        dsd.addColumn("Assistive Technology Product", orthoAssistiveTechnologyProductDataDefinition, paramMapping);
        dsd.addColumn("Service Area", orthoServiceAreaDataDefinition, paramMapping);
        dsd.addColumn("Receipt Number", orthoReceiptNumberDataDefinition, paramMapping);
        dsd.addColumn("Amount Paid", orthoAmountPaidDataDefinition, paramMapping);
        dsd.addColumn("Payment Mode", orthoPaymentModeDataDefinition, paramMapping);
		dsd.addColumn("Diagnosis",opdDiagnosisDataDefinition, paramMapping);  //TODO: Add all diagnosis
        dsd.addColumn("Next Appointment Date", new ObsForPersonDataDefinition("Next Appointment Date", TimeQualifier.LAST, Dictionary.getConcept(Dictionary.RETURN_VISIT_DATE), null, null), "", new ObsValueDatetimeConverter());


		MOH420RegisterCohortDefinition cd = new MOH420RegisterCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		dsd.addRowFilter(cd, paramMapping);
		return dsd;

    }
}
