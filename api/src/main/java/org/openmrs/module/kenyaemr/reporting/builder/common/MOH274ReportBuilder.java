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
import org.openmrs.module.kenyaemr.reporting.calculation.converter.RDQACalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.specialClinics.SpecialClinicsRegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.OPDNumber5AndAboveRevisitDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.specialClinics.*;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.kenyaemr.reporting.library.specialClinics.SpecialClinicsIndicatorLibrary;
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
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.EncounterDataSetDefinition;
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
@Builds({"kenyaemr.ehrReports.report.moh274"})
public class MOH274ReportBuilder extends AbstractReportBuilder {
    public static final String ENC_DATE_FORMAT = "yyyy/MM/dd";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
	//private  final String SPECIAL_CLINIC_HEARING_FORM_UUID = CommonMetadata._Form.ORTHOPAEDIC_CLINICAL_FORM;
    static final String SPECIAL_CLINIC_HEARING_FORM_UUID = CommonMetadata._Form.ORTHOPAEDIC_CLINICAL_FORM;
    private static final String COMPARATIVE_OPERATION = ">=";
    static final int NEW_VISIT = 164180,RE_VISIT= 160530, CREPE_BANDAGES = 2002094, ARMSLINGS = 169054 ;
    static final String CASH = "Cash", INSURANCE = "Insurance", MOBILE = "Mobile Money", WAIVER = "Waiver",FRACTURE= "Fracture",DISLOCATION = "Dislocation";
    @Autowired
    private CommonDimensionLibrary commonDimensions;
    private static final Integer AGE = 0;
    String indParams = "startDate=${startDate},endDate=${endDate}";
    @Autowired
    private SpecialClinicsIndicatorLibrary specialClinicsIndicators;
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
                ReportUtils.map(datasetColumns(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(orthopaedicSummaryAggregateDataSet(), "startDate=${startDate},endDate=${endDate}")
        );
    }

    protected DataSetDefinition datasetColumns() {
        EncounterDataSetDefinition dsd = new EncounterDataSetDefinition();
        dsd.setName("MOH274");
        dsd.setDescription("OPD Visit information");
        dsd.addSortCriteria("Visit Date", SortCriteria.SortDirection.ASC);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
       // dsd.addDimension("gender", map(commonDimensions.gender()));

        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
        PatientIdentifierType pcn = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.PATIENT_CLINIC_NUMBER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition patientClinicNo = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(pcn.getName(), pcn), identifierFormatter);

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

        SpecialClinicsSymptomsDataDefinition symptomsDataDefinition = new SpecialClinicsSymptomsDataDefinition();
        symptomsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        symptomsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        symptomsDataDefinition.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);

        OPDNumber5AndAboveRevisitDataDefinition opdNumber5AndAboveRevisitDataDefinition = new OPDNumber5AndAboveRevisitDataDefinition();
        opdNumber5AndAboveRevisitDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        opdNumber5AndAboveRevisitDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        SpecialClinicsDrugOrderDataDefinition drugOrderDataDefinition = new SpecialClinicsDrugOrderDataDefinition();
        drugOrderDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        drugOrderDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        drugOrderDataDefinition.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);

        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);
        PersonAttributeType f_name = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.GUARDIAN_FIRST_NAME);

        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("id", new PatientIdDataDefinition(), "");
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));
        dsd.addColumn("Serial Number", new PersonIdDataDefinition(), "");
        dsd.addColumn("Age", new AgeDataDefinition(), "");
        dsd.addColumn("Sex", new GenderDataDefinition(), "");
        dsd.addColumn("Parent/Caregiver name", new PersonAttributeDataDefinition(f_name), "");
        dsd.addColumn("Parent/Caregiver Telephone No", new PersonAttributeDataDefinition(phoneNumber), "");
        dsd.addColumn("Occupation", new ObsForPersonDataDefinition("Occupation", TimeQualifier.LAST, Dictionary.getConcept(Dictionary.OCCUPATION), null, null), "", new ObsValueConverter());
        dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));
		dsd.addColumn("OPD Number (New)", patientClinicNo, "");
        dsd.addColumn("OPD Number (Revisit)", opdNumber5AndAboveRevisitDataDefinition, paramMapping);
		dsd.addColumn("County",new CalculationDataDefinition("County", new CountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Sub County", new CalculationDataDefinition("Subcounty", new SubCountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Village", new CalculationDataDefinition("Village/Estate/Landmark", new PersonAddressCalculation()), "",new RDQACalculationResultConverter());
		dsd.addColumn("Cause",symptomsDataDefinition, paramMapping);
		dsd.addColumn("Diagnosis",diagnosisDataDefinition, paramMapping);  //TODO: Add all diagnosis
        dsd.addColumn("Treatment", drugOrderDataDefinition, paramMapping);
		dsd.addColumn("Referred to", specialClinicsReferredToDataDefinition, paramMapping);
		dsd.addColumn("Referred from", specialClinicsReferredFromDataDefinition, paramMapping);
		dsd.addColumn("Visit Type", specialClinicsVisitTypeDataDefinition, paramMapping);
        dsd.addColumn("Payment Method", paymentMethodsDataDefinition, paramMapping);
        dsd.addColumn("Receipt Number", receiptNumberDataDefinition, paramMapping);
        dsd.addColumn("Amount Paid", amountPaidDataDefinition, paramMapping);

		SpecialClinicsRegisterCohortDefinition cd = new SpecialClinicsRegisterCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setSpecialClinic(SPECIAL_CLINIC_HEARING_FORM_UUID);
        cd.setComparisonOperator(COMPARATIVE_OPERATION);
        cd.setAge(AGE);
		dsd.addRowFilter(cd, paramMapping);
		return dsd;

    }
    protected DataSetDefinition orthopaedicSummaryAggregateDataSet() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName("ORTHOPAEDIC_SERVICE_SUMMARY");
        dsd.setDescription("ORTHOPAEDIC TRAUMA SERVICE SUMMARY");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addDimension("age", map(commonDimensions.specialClinicsAgeGroups(), "onDate=${endDate}"));
        dsd.addDimension("gender", map(commonDimensions.gender()));
        dsd.addColumn("No. of new visits", "", ReportUtils.map(specialClinicsIndicators.visitType(NEW_VISIT, SPECIAL_CLINIC_HEARING_FORM_UUID), indParams), "");
        dsd.addColumn("No. of Re-visits", "", ReportUtils.map(specialClinicsIndicators.visitType(RE_VISIT, SPECIAL_CLINIC_HEARING_FORM_UUID), indParams), "");
        dsd.addColumn("Total No. of Visits", "", ReportUtils.map(specialClinicsIndicators.totalCountVisits(NEW_VISIT,RE_VISIT, SPECIAL_CLINIC_HEARING_FORM_UUID), indParams), "");
        dsd.addColumn("Cash payment", "", ReportUtils.map(specialClinicsIndicators.paymentType(SPECIAL_CLINIC_HEARING_FORM_UUID,CASH), indParams), "");
        dsd.addColumn("Insurance payment", "", ReportUtils.map(specialClinicsIndicators.paymentType(SPECIAL_CLINIC_HEARING_FORM_UUID,INSURANCE), indParams), "");
        dsd.addColumn("Mobile payment", "", ReportUtils.map(specialClinicsIndicators.paymentType(SPECIAL_CLINIC_HEARING_FORM_UUID,MOBILE), indParams), "");
        dsd.addColumn("Waiver", "", ReportUtils.map(specialClinicsIndicators.paymentType(SPECIAL_CLINIC_HEARING_FORM_UUID,WAIVER), indParams), "");
        dsd.addColumn("Total Cash Paid", "", ReportUtils.map(specialClinicsIndicators.totalAmountPaid(SPECIAL_CLINIC_HEARING_FORM_UUID, CASH), indParams), "");
        dsd.addColumn("Total Insurance Paid", "", ReportUtils.map(specialClinicsIndicators.totalAmountPaid(SPECIAL_CLINIC_HEARING_FORM_UUID, INSURANCE), indParams), "");
        dsd.addColumn("Total Mobile Paid", "", ReportUtils.map(specialClinicsIndicators.totalAmountPaid(SPECIAL_CLINIC_HEARING_FORM_UUID, MOBILE), indParams), "");
        dsd.addColumn("Total Waived", "", ReportUtils.map(specialClinicsIndicators.totalAmountPaid(SPECIAL_CLINIC_HEARING_FORM_UUID, WAIVER), indParams), "");
        dsd.addColumn("Crepe bandages", "", ReportUtils.map(specialClinicsIndicators.procedureDone(CREPE_BANDAGES,SPECIAL_CLINIC_HEARING_FORM_UUID), indParams), "");
        dsd.addColumn("Armslings", "", ReportUtils.map(specialClinicsIndicators.procedureDone(ARMSLINGS,SPECIAL_CLINIC_HEARING_FORM_UUID), indParams), "");
        dsd.addColumn("Other Procedures", "", ReportUtils.map(specialClinicsIndicators.procedureOtherDone(CREPE_BANDAGES,ARMSLINGS,SPECIAL_CLINIC_HEARING_FORM_UUID), indParams), "");
        dsd.addColumn("Facility To", "", ReportUtils.map(specialClinicsIndicators.referredTo(SPECIAL_CLINIC_HEARING_FORM_UUID), indParams), "");
        dsd.addColumn("Facility From", "", ReportUtils.map(specialClinicsIndicators.referredFrom(SPECIAL_CLINIC_HEARING_FORM_UUID), indParams), "");
        dsd.addColumn("Fracture", "", ReportUtils.map(specialClinicsIndicators.closedReduction(FRACTURE, SPECIAL_CLINIC_HEARING_FORM_UUID), indParams), "");
        dsd.addColumn("Dislocation", "", ReportUtils.map(specialClinicsIndicators.closedReduction(DISLOCATION, SPECIAL_CLINIC_HEARING_FORM_UUID), indParams), "");
        dsd.addColumn("Fracture and Dislocation", "", ReportUtils.map(specialClinicsIndicators.fractureAndDislocation(SPECIAL_CLINIC_HEARING_FORM_UUID), indParams), "");
        return dsd;
    }


}
