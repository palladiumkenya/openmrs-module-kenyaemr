/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.builder.IDSRSuspectedCaseList;


import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
import org.openmrs.module.kenyaemr.calculation.library.mchcs.PersonAddressCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.SARICohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.sari.*;
import org.openmrs.module.metadatadeploy.MetadataUtils;
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
@Builds({"kenyaemr.common.report.IDSRSuspectedCaseList"})
public class IDSRSuspectedCaseListReportBuilder extends AbstractReportBuilder {
    public static final String DATE_FORMAT = "yyyy/MM/dd";
    public static final String ENC_DATE_FORMAT = "dd/MM/yyyy";

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
        dsd.setName("IDSR Suspected case list");
        dsd.setDescription("Linkage register");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String paramMapping = "startDate=${startDate},endDate=${endDate}";
        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);

        TemperatureDataDefinition temperatureDataDefinition = new TemperatureDataDefinition();
        temperatureDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        temperatureDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        SariVisitTypeDataDefinition visitTypeDataDefinition = new SariVisitTypeDataDefinition();
        visitTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        visitTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        AdmissionDateDataDefinition admissionDateDataDefinition = new AdmissionDateDataDefinition();
        admissionDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        admissionDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        CoughDataDefinition coughDataDefinition = new CoughDataDefinition();
        coughDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        coughDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        DifficultInBreathingDataDefinition difficultInBreathingDataDefinition = new DifficultInBreathingDataDefinition();
        difficultInBreathingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        difficultInBreathingDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        VomittingDataDefinition vomittingDataDefinition = new VomittingDataDefinition();
        vomittingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        vomittingDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        DiarrhoeaDataDefinition diarrhoeaDataDefinition = new DiarrhoeaDataDefinition();
        diarrhoeaDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        diarrhoeaDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        BloodInStoolDataDefinition bloodInStoolDataDefinition = new BloodInStoolDataDefinition();
        bloodInStoolDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        bloodInStoolDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        SuspectedDataDefinition suspectedDataDefinition = new SuspectedDataDefinition();
        suspectedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        suspectedDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(upn.getName(), upn), identifierFormatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);
        dsd.addColumn("Unique Patient No", identifierDef, "");
        dsd.addColumn("Visit Type", visitTypeDataDefinition,  paramMapping);
        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("Sex", new GenderDataDefinition(), "");
        dsd.addColumn("id", new PatientIdDataDefinition(), "");
        dsd.addColumn("Date of Birth", new BirthdateDataDefinition(), "", new BirthdateConverter(DATE_FORMAT));
        dsd.addColumn("Age", new AgeDataDefinition(), "");
        dsd.addColumn("County", new CalculationDataDefinition("County", new PersonAddressCalculation("countyDistrict")), "");
        dsd.addColumn("Sub county", new CalculationDataDefinition("Sub county", new PersonAddressCalculation("stateProvince")), "");
        dsd.addColumn("Village_Estate_Landmark", new CalculationDataDefinition("Village/Estate/Landmark", new PersonAddressCalculation()), "");
        dsd.addColumn("Visit Date", new EncounterDatetimeDataDefinition(),"", new DateConverter(ENC_DATE_FORMAT));
        dsd.addColumn("Temperature", temperatureDataDefinition,  paramMapping);
        dsd.addColumn("Admission Date", admissionDateDataDefinition,  paramMapping);
        dsd.addColumn("Cough", coughDataDefinition,  paramMapping);
        dsd.addColumn("Difficult in breathing", difficultInBreathingDataDefinition,  paramMapping);
        dsd.addColumn("Vomiting", vomittingDataDefinition,  paramMapping);
        dsd.addColumn("Diarrhea", diarrhoeaDataDefinition,  paramMapping);
        dsd.addColumn("Blood in stool", bloodInStoolDataDefinition,  paramMapping);
        dsd.addColumn("Suspected Case", suspectedDataDefinition,  paramMapping);

        SARICohortDefinition cd = new SARICohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addRowFilter(cd, paramMapping);
        return dsd;

    }
}