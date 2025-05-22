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
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.ObsValueDatetimeConverter;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.RDQACalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.specialClinics.SpecialClinicsRegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDDiagnosisDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDOrderingClinicianDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDOutcomeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.*;
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
	private static final String SPECIAL_CLINIC_HEARING_FORM_UUID = CommonMetadata._Form.ORTHOPAEDIC_CLINICAL_FORM;
    private static final String COMPARATIVE_OPERATION = ">=";
    private static final Integer AGE = 0;

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
        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        EncounterDataSetDefinition dsd = new EncounterDataSetDefinition();
        dsd.setName("MOH420");
        dsd.setDescription("OPD Visit information");
        dsd.addSortCriteria("Visit Date", SortCriteria.SortDirection.ASC);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
        PatientIdentifierType pcn = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.PATIENT_CLINIC_NUMBER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition patientClinicNo = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(pcn.getName(), pcn), identifierFormatter);
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(upn.getName(), upn), identifierFormatter);

        SpecialClinicsReferredToDataDefinition specialClinicsReferredToDataDefinition = new SpecialClinicsReferredToDataDefinition();
		specialClinicsReferredToDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		specialClinicsReferredToDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		specialClinicsReferredToDataDefinition.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);

		SpecialClinicsReferredFromDataDefinition specialClinicsReferredFromDataDefinition = new SpecialClinicsReferredFromDataDefinition();
		specialClinicsReferredFromDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		specialClinicsReferredFromDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		specialClinicsReferredFromDataDefinition.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);

		SpecialClinicsVisitTypeDataDefinition specialClinicsVisitTypeDataDefinition = new SpecialClinicsVisitTypeDataDefinition();
		specialClinicsVisitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		specialClinicsVisitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		specialClinicsVisitTypeDataDefinition.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);

        SpecialClinicsPaymentMethodsDataDefinition paymentMethodsDataDefinition = new SpecialClinicsPaymentMethodsDataDefinition();
        paymentMethodsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        paymentMethodsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        paymentMethodsDataDefinition.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);

        SpecialClinicsReceiptNumberDataDefinition receiptNumberDataDefinition = new SpecialClinicsReceiptNumberDataDefinition();
        receiptNumberDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        receiptNumberDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        receiptNumberDataDefinition.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);

        SpecialClinicsDiagnosisDataDefinition diagnosisDataDefinition = new SpecialClinicsDiagnosisDataDefinition();
        diagnosisDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        diagnosisDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        diagnosisDataDefinition.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);

        SpecialClinicsAmountPaidDataDefinition amountPaidDataDefinition = new SpecialClinicsAmountPaidDataDefinition();
        amountPaidDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        amountPaidDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        amountPaidDataDefinition.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);

        SpecialClinicsAssistiveTechnologyDataDefinition assistiveTechnologyDataDefinition = new SpecialClinicsAssistiveTechnologyDataDefinition();
        assistiveTechnologyDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        assistiveTechnologyDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        assistiveTechnologyDataDefinition.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);

        SpecialClinicsProcedureOrderDataDefinition procedureOrderDataDefinition = new SpecialClinicsProcedureOrderDataDefinition();
        procedureOrderDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        procedureOrderDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        procedureOrderDataDefinition.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);

        SpecialClinicsTreatmentInterventionDataDefinition treatmentInterventionDataDefinition = new SpecialClinicsTreatmentInterventionDataDefinition();
        treatmentInterventionDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        treatmentInterventionDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        treatmentInterventionDataDefinition.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);

        SpecialClinicsAreaOfServiceDataDefinition siteOfScreeningDataDefinition = new SpecialClinicsAreaOfServiceDataDefinition();
        siteOfScreeningDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        siteOfScreeningDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        siteOfScreeningDataDefinition.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);

        SpecialClinicsProviderDataDefinition providerDataDefinition = new SpecialClinicsProviderDataDefinition();
        providerDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        providerDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        providerDataDefinition.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);

		PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);

        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("id", new PatientIdDataDefinition(), "");
        dsd.addColumn("Serial Number", new PersonIdDataDefinition(), "");
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));
        dsd.addColumn("Age", new AgeDataDefinition(), "");
        dsd.addColumn("Sex", new GenderDataDefinition(), "");
        dsd.addColumn("Parent/Caregiver Telephone No", new PersonAttributeDataDefinition(phoneNumber), "");   
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));
        dsd.addColumn("OP/IP Number", identifierDef, "");
		dsd.addColumn("OPD Number (New)", patientClinicNo, "");
		dsd.addColumn("County",new CalculationDataDefinition("County", new CountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Sub County", new CalculationDataDefinition("Subcounty", new SubCountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Village", new CalculationDataDefinition("Village/Estate/Landmark", new PersonAddressCalculation()), "",new RDQACalculationResultConverter());
        dsd.addColumn("Occupation", new ObsForPersonDataDefinition("Occupation", TimeQualifier.LAST, Dictionary.getConcept(Dictionary.OCCUPATION), null, null), "", new ObsValueConverter());
        dsd.addColumn("Diagnosis",diagnosisDataDefinition, paramMapping);  //TODO: Add all diagnosis
        dsd.addColumn("Assistive Technology", assistiveTechnologyDataDefinition, paramMapping);
        dsd.addColumn("Treatment Intervention", treatmentInterventionDataDefinition, paramMapping);
        dsd.addColumn("Site of Screening",siteOfScreeningDataDefinition, paramMapping);
		dsd.addColumn("Referred to", specialClinicsReferredToDataDefinition, paramMapping);
		dsd.addColumn("Referred from", specialClinicsReferredFromDataDefinition, paramMapping);
		dsd.addColumn("Visit Type", specialClinicsVisitTypeDataDefinition, paramMapping);
        dsd.addColumn("Payment Method", paymentMethodsDataDefinition, paramMapping);
        dsd.addColumn("Receipt Number", receiptNumberDataDefinition, paramMapping);
        dsd.addColumn("Underlying Condition", procedureOrderDataDefinition, paramMapping);
        dsd.addColumn("Amount Paid", amountPaidDataDefinition, paramMapping);
        dsd.addColumn("Next Appointment Date", new ObsForPersonDataDefinition("Next Appointment Date", TimeQualifier.LAST, Dictionary.getConcept(Dictionary.RETURN_VISIT_DATE), null, null), "", new ObsValueDatetimeConverter());
        dsd.addColumn("Name of Officer",providerDataDefinition, paramMapping);
        SpecialClinicsRegisterCohortDefinition cd = new SpecialClinicsRegisterCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);
        cd.setComparisonOperator(COMPARATIVE_OPERATION);
        cd.setAge(AGE);
		dsd.addRowFilter(cd, paramMapping);
		return dsd;

    }
}
