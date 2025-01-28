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
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.RDQACalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.MOH204ARegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.HTSRemarksDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.opd.*;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
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
@Builds({"kenyaemr.ehrReports.report.moh204A"})
public class MOH204AReportBuilder extends AbstractReportBuilder {
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
        dsd.setName("MOH204A");
        dsd.setDescription("OPD Visit information");
        dsd.addSortCriteria("Visit Date", SortCriteria.SortDirection.ASC);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
		OPDNumberUnder5NewVisitDataDefinition opdNumberUnder5NewVisitDataDefinition =new OPDNumberUnder5NewVisitDataDefinition();
		opdNumberUnder5NewVisitDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdNumberUnder5NewVisitDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

		OPDNumberUnder5RevisitDataDefinition opdNumberUnder5RevisitDataDefinition = new OPDNumberUnder5RevisitDataDefinition();
		opdNumberUnder5RevisitDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdNumberUnder5RevisitDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

		OPDHeightDataDefinition  opdHeightDataDefinition = new OPDHeightDataDefinition();
		opdHeightDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdHeightDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDWeightDataDefinition opdWeightDataDefinition = new OPDWeightDataDefinition();
		opdWeightDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdWeightDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDMuacDataDefinition opdMuacDataDefinition = new OPDMuacDataDefinition();
		opdMuacDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdMuacDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDTemperatureDataDefinition opdTemperatureDataDefinition = new OPDTemperatureDataDefinition();
		opdTemperatureDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdTemperatureDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDRespiratoryRateDataDefinition opdRespiratoryRateDataDefinition = new OPDRespiratoryRateDataDefinition();
		opdRespiratoryRateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdRespiratoryRateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDOxygenSaturationDataDefinition opdOxygenSaturationDataDefinition = new OPDOxygenSaturationDataDefinition();
		opdOxygenSaturationDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdOxygenSaturationDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDPulseRateDataDefinition opdPulseRateDataDefinition = new OPDPulseRateDataDefinition();
		opdPulseRateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdPulseRateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDDangerSignsDataDefinition opdDangerSignsDataDefinition = new OPDDangerSignsDataDefinition();
		opdDangerSignsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdDangerSignsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDComplaintDurationDataDefinition opdComplaintDurationDataDefinition = new OPDComplaintDurationDataDefinition();
		opdComplaintDurationDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdComplaintDurationDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDMalariaAssessmentDataDefinition opdMalariaAssessmentDataDefinition = new OPDMalariaAssessmentDataDefinition();
		opdMalariaAssessmentDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdMalariaAssessmentDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDDiagnosisDataDefinition opdDiagnosisDataDefinition = new OPDDiagnosisDataDefinition();
		opdDiagnosisDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdDiagnosisDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDTracerDrugsPrescribedDataDefinition opdTracerDrugsPrescribedDataDefinition = new OPDTracerDrugsPrescribedDataDefinition();
		opdTracerDrugsPrescribedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdTracerDrugsPrescribedDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDNonTracerDrugsPrescribedDataDefinition opdNonTracerDrugsPrescribedDataDefinition = new OPDNonTracerDrugsPrescribedDataDefinition();
		opdNonTracerDrugsPrescribedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdNonTracerDrugsPrescribedDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDImmunizationStatusDataDefinition opdImmunizationStatusDataDefinition = new OPDImmunizationStatusDataDefinition();
		opdImmunizationStatusDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdImmunizationStatusDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDTbScreeningDataDefinition opdTbScreeningDataDefinition = new OPDTbScreeningDataDefinition();
		opdTbScreeningDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdTbScreeningDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDNutritionalInterventionsDataDefinition opdNutritionalInterventionsDataDefinition = new OPDNutritionalInterventionsDataDefinition();
		opdNutritionalInterventionsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdNutritionalInterventionsDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDReferredToDataDefinition opdReferredToDataDefinition = new OPDReferredToDataDefinition();
		opdReferredToDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdReferredToDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDReferredFromDataDefinition opdReferredFromDataDefinition = new OPDReferredFromDataDefinition();
		opdReferredFromDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdReferredFromDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		OPDOutcomeDataDefinition opdOutcomeDataDefinition = new OPDOutcomeDataDefinition();
		opdOutcomeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		opdOutcomeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		
		PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);

        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("id", new PatientIdDataDefinition(), "");
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));
        dsd.addColumn("Age", new AgeDataDefinition(), "");
        dsd.addColumn("Sex", new GenderDataDefinition(), "");
        dsd.addColumn("Parent/Caregiver Telephone No", new PersonAttributeDataDefinition(phoneNumber), "");   
		dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));         
		dsd.addColumn("OPD Number (New)", opdNumberUnder5NewVisitDataDefinition, paramMapping);
		dsd.addColumn("OPD Number (Revisit)", opdNumberUnder5RevisitDataDefinition, paramMapping);   //TODO:Determine revisit vs new visit
        dsd.addColumn("Referred From", opdReferredFromDataDefinition, paramMapping);
		dsd.addColumn("County",new CalculationDataDefinition("County", new CountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Sub County", new CalculationDataDefinition("Subcounty", new SubCountyAddressCalculation()), "",new CalculationResultConverter());
		dsd.addColumn("Village", new CalculationDataDefinition("Village/Estate/Landmark", new PersonAddressCalculation()), "",new RDQACalculationResultConverter());
		dsd.addColumn("Weight", opdWeightDataDefinition, paramMapping);
		dsd.addColumn("Height", opdHeightDataDefinition, paramMapping);	
		dsd.addColumn("Muac", opdMuacDataDefinition, paramMapping);	
		dsd.addColumn("Temperature",opdTemperatureDataDefinition, paramMapping);
		dsd.addColumn("Respiratory Rate",opdRespiratoryRateDataDefinition, paramMapping);
		dsd.addColumn("Oxygen Saturation (SPO2)",opdOxygenSaturationDataDefinition, paramMapping);
		dsd.addColumn("Pulse Rate",opdPulseRateDataDefinition, paramMapping);
		dsd.addColumn("Danger signs",opdDangerSignsDataDefinition, paramMapping);
		dsd.addColumn("Duration",opdComplaintDurationDataDefinition, paramMapping);
		dsd.addColumn("Malaria",opdMalariaAssessmentDataDefinition, paramMapping);
		dsd.addColumn("Clinical Diagnosis",opdDiagnosisDataDefinition, paramMapping);
		dsd.addColumn("Tracer Drugs Prescribed",opdTracerDrugsPrescribedDataDefinition, paramMapping);
		dsd.addColumn("Other Treatments Prescribed",opdNonTracerDrugsPrescribedDataDefinition, paramMapping);
		dsd.addColumn("Immunization Status Up to Date",opdImmunizationStatusDataDefinition, paramMapping);
		dsd.addColumn("TB Screening",opdTbScreeningDataDefinition, paramMapping);
		dsd.addColumn("Nutrition Interventions",opdNutritionalInterventionsDataDefinition, paramMapping);  //TODO: missing  2=Nutrition therapeutic supplements and 3=Diatetics
		dsd.addColumn("Referred to",opdNutritionalInterventionsDataDefinition, paramMapping);  //TODO: missing  1=Community Unit
		dsd.addColumn("Remarks/Outcome",opdOutcomeDataDefinition, paramMapping); 
		
        MOH204ARegisterCohortDefinition cd = new MOH204ARegisterCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		dsd.addRowFilter(cd, paramMapping);
		return dsd;

    }
}
