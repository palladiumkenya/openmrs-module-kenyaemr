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
import org.openmrs.module.kenyaemr.calculation.library.hiv.CountyAddressCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.SubCountyAddressCalculation;
import org.openmrs.module.kenyaemr.calculation.library.mchcs.PersonAddressCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.RDQACalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.specialClinics.SpecialClinicsRegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.*;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
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
@Builds({"kenyaemr.specialClinics.report.moh417"})
public class MOH417ReportBuilder extends AbstractReportBuilder {
    public static final String ENC_DATE_FORMAT = "yyyy/MM/dd";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String SPECIAL_CLINIC = CommonMetadata._Form.OCCUPATIONAL_THERAPY_CLINICAL_FORM;
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
        EncounterDataSetDefinition dsd = new EncounterDataSetDefinition();
        dsd.setName("MOH417");
        dsd.setDescription("MOH 417 Visit information");
        dsd.addSortCriteria("Visit Date", SortCriteria.SortDirection.ASC);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
        PatientIdentifierType pcn = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.PATIENT_CLINIC_NUMBER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition patientClinicNo = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(pcn.getName(), pcn), identifierFormatter);

		SpecialClinicsReferredToDataDefinition specialClinicsReferredToDataDefinition = new SpecialClinicsReferredToDataDefinition();
		specialClinicsReferredToDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		specialClinicsReferredToDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		specialClinicsReferredToDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsReferredFromDataDefinition specialClinicsReferredFromDataDefinition = new SpecialClinicsReferredFromDataDefinition();
		specialClinicsReferredFromDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		specialClinicsReferredFromDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		specialClinicsReferredFromDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

		SpecialClinicsVisitTypeDataDefinition specialClinicsVisitTypeDataDefinition = new SpecialClinicsVisitTypeDataDefinition();
		specialClinicsVisitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		specialClinicsVisitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		specialClinicsVisitTypeDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

        SpecialClinicsPaymentMethodsDataDefinition paymentMethodsDataDefinition = new SpecialClinicsPaymentMethodsDataDefinition();
        paymentMethodsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        paymentMethodsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        paymentMethodsDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

        SpecialClinicsReceiptNumberDataDefinition receiptNumberDataDefinition = new SpecialClinicsReceiptNumberDataDefinition();
        receiptNumberDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        receiptNumberDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        receiptNumberDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

        SpecialClinicsOTInterventionDataDefinition oTInterventionDataDefinition = new SpecialClinicsOTInterventionDataDefinition();
        oTInterventionDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        oTInterventionDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        oTInterventionDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

        SpecialClinicsAssistiveTechnologyDataDefinition assistiveTechnologyDataDefinition = new SpecialClinicsAssistiveTechnologyDataDefinition();
        assistiveTechnologyDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        assistiveTechnologyDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        assistiveTechnologyDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

        SpecialClinicsAmountPaidDataDefinition amountPaidDataDefinition = new SpecialClinicsAmountPaidDataDefinition();
        amountPaidDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        amountPaidDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        amountPaidDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

        SpecialClinicsPatientInSchoolDataDefinition patientInSchoolDataDefinition = new SpecialClinicsPatientInSchoolDataDefinition();
        patientInSchoolDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        patientInSchoolDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        patientInSchoolDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

        SpecialClinicsPatientWithDisabilityDataDefinition patientWithDisabilityDataDefinition = new SpecialClinicsPatientWithDisabilityDataDefinition();
        patientWithDisabilityDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        patientWithDisabilityDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        patientWithDisabilityDataDefinition.setSpecialClinic(SPECIAL_CLINIC);

        SpecialClinicsDiagnosisDataDefinition diagnosisDataDefinition = new SpecialClinicsDiagnosisDataDefinition();
		diagnosisDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		diagnosisDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		
		PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);

        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("id", new PatientIdDataDefinition(), "");
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));
        dsd.addColumn("Age", new AgeDataDefinition(), "");
        dsd.addColumn("Sex", new GenderDataDefinition(), "");
        dsd.addColumn("Parent/Caregiver Telephone No", new PersonAttributeDataDefinition(phoneNumber), "");   
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));         
		dsd.addColumn("OPD Number (New)", patientClinicNo, "");
		dsd.addColumn("County",new CalculationDataDefinition("County", new CountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Sub County", new CalculationDataDefinition("Subcounty", new SubCountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Village", new CalculationDataDefinition("Village/Estate/Landmark", new PersonAddressCalculation()), "",new RDQACalculationResultConverter());
		dsd.addColumn("Diagnosis",diagnosisDataDefinition, paramMapping);  //TODO: Add all diagnosis
        dsd.addColumn("Patient in School", patientInSchoolDataDefinition, paramMapping);
        dsd.addColumn("Patient with Disability", patientWithDisabilityDataDefinition, paramMapping);
		dsd.addColumn("Referred to", specialClinicsReferredToDataDefinition, paramMapping);
		dsd.addColumn("Referred from", specialClinicsReferredFromDataDefinition, paramMapping);
        dsd.addColumn("OT Interventions", oTInterventionDataDefinition, paramMapping);
        dsd.addColumn("Assistive Technology", assistiveTechnologyDataDefinition, paramMapping);
        dsd.addColumn("Visit Type", specialClinicsVisitTypeDataDefinition, paramMapping);
        dsd.addColumn("Payment Method", paymentMethodsDataDefinition, paramMapping);
        dsd.addColumn("Receipt Number", receiptNumberDataDefinition, paramMapping);
        dsd.addColumn("Amount Paid", amountPaidDataDefinition, paramMapping);

		SpecialClinicsRegisterCohortDefinition cd = new SpecialClinicsRegisterCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setSpecialClinic(SPECIAL_CLINIC);
        cd.setAge(AGE);
        cd.setComparisonOperator(COMPARATIVE_OPERATION);
		dsd.addRowFilter(cd, paramMapping);
		return dsd;

    }
}
