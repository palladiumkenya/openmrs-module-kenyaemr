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
import org.openmrs.module.kenyaemr.reporting.cohort.definition.NCDRegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
//import org.openmrs.module.kenyaemr.reporting.data.converter.definition.ncd.*;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.MFLCodeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.ncd.*;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDatetimeDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
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
@Builds({"kenyaemr.ehrReports.report.moh270"})
public class MOH270ReportBuilder extends AbstractReportBuilder {
    public static final String ENC_DATE_FORMAT = "yyyy/MM/dd";
    public static final String DATE_FORMAT ="dd/MM/yyyy";


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
        return Arrays.asList(ReportUtils.map(moh270Dataset(), "startDate=${startDate},endDate=${endDate}"));
    }

    protected DataSetDefinition moh270Dataset() {
        EncounterDataSetDefinition dsd = new EncounterDataSetDefinition();
        dsd.setName("MOH270");
        dsd.setDescription("MOH 270 Permanent Register");
        dsd.addSortCriteria("Visit Date", SortCriteria.SortDirection.ASC);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String paramMapping = "startDate=${startDate},endDate=${endDate}";
        //Data definitions
        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(upn.getName(), upn), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(nupi.getName(), nupi), identifierFormatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);


        dsd.addColumn("Serial Number", new PersonIdDataDefinition(), "");
        dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(), "", new DateConverter(ENC_DATE_FORMAT));
        dsd.addColumn("County",new CalculationDataDefinition("County", new CountyAddressCalculation()), "",new CalculationResultConverter());
        dsd.addColumn("MFL Code", new MFLCodeDataDefinition(), "");
        dsd.addColumn("Patient No", identifierDef, null);
        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("DOB", new BirthdateDataDefinition(), "", new BirthdateConverter(DATE_FORMAT));
        dsd.addColumn("Sex", new GenderDataDefinition(), "");
        dsd.addColumn("Telephone No", new PersonAttributeDataDefinition(phoneNumber), "");
        dsd.addColumn("Sub County", new CalculationDataDefinition("Subcounty", new SubCountyAddressCalculation()), "",new CalculationResultConverter());
        dsd.addColumn("Village/Estate/Landmark", new CalculationDataDefinition("Village", new PersonAddressCalculation()), "",new RDQACalculationResultConverter());
        //dsd.addColumn("Landmark", new CalculationDataDefinition("Landmark", new PersonAddressCalculation()), "");


        // Instantiate and add startDate/endDate to each data definition
        NCDTreatmentSupporterDataDefinition treatmentSupporter = new NCDTreatmentSupporterDataDefinition();
        treatmentSupporter.addParameter(new Parameter("startDate", "Start Date", Date.class));
        treatmentSupporter.addParameter(new Parameter("endDate", "End Date", Date.class));

        NCDDiagnosisDataDefinition diagnosis = new NCDDiagnosisDataDefinition();
        diagnosis.addParameter(new Parameter("startDate", "Start Date", Date.class));
        diagnosis.addParameter(new Parameter("endDate", "End Date", Date.class));

        NCDYearOfDiagnosisDataDefinition yearOfDiagnosis = new NCDYearOfDiagnosisDataDefinition();
        yearOfDiagnosis.addParameter(new Parameter("startDate", "Start Date", Date.class));
        yearOfDiagnosis.addParameter(new Parameter("endDate", "End Date", Date.class));

        NCDComplicationsDataDefinition complications = new NCDComplicationsDataDefinition();
        complications.addParameter(new Parameter("startDate", "Start Date", Date.class));
        complications.addParameter(new Parameter("endDate", "End Date", Date.class));

        NCDMedicationsDataDefinition medications = new NCDMedicationsDataDefinition();
        medications.addParameter(new Parameter("startDate", "Start Date", Date.class));
        medications.addParameter(new Parameter("endDate", "End Date", Date.class));

        NCDPatientStatusDataDefinition patientStatus = new NCDPatientStatusDataDefinition();
        patientStatus.addParameter(new Parameter("startDate", "Start Date", Date.class));
        patientStatus.addParameter(new Parameter("endDate", "End Date", Date.class));

        NCDSHIFStatusDataDefinition shifStatus = new NCDSHIFStatusDataDefinition();
        shifStatus.addParameter(new Parameter("startDate", "Start Date", Date.class));
        shifStatus.addParameter(new Parameter("endDate", "End Date", Date.class));

        NCDClinicalNotesDataDefinition clinicalNotes = new NCDClinicalNotesDataDefinition();
        clinicalNotes.addParameter(new Parameter("startDate", "Start Date", Date.class));
        clinicalNotes.addParameter(new Parameter("endDate", "End Date", Date.class));

        // Add them to the dataset
        dsd.addColumn("Treatment Supporter Contact", treatmentSupporter, paramMapping);
        dsd.addColumn("Diagnosis", diagnosis, paramMapping);
       dsd.addColumn("Year of Diagnosis", yearOfDiagnosis, paramMapping);
        dsd.addColumn("Complications", complications, paramMapping);
        dsd.addColumn("Treatment", medications, paramMapping);
       // dsd.addColumn("Patient Status", patientStatus, paramMapping);
        dsd.addColumn("SHIF", shifStatus, paramMapping);
        dsd.addColumn("Remarks", clinicalNotes, paramMapping);



        // Add row filter for NCD patients
        NCDRegisterCohortDefinition cd = new NCDRegisterCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addRowFilter(cd, paramMapping);

        return dsd;
    }
}
