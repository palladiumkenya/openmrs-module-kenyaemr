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
import org.openmrs.module.kenyaemr.reporting.library.NCDs.NCDIndicatorLibrary;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;



@Component
@Builds({"kenyaemr.ehrReports.report.moh270"})
public class MOH270ReportBuilder extends AbstractReportBuilder {
    @Autowired
    private CommonDimensionLibrary commonDimensions;
    @Autowired
    private NCDIndicatorLibrary ncdIndicators;

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Collections.emptyList();
    }
    String indParams = "startDate=${startDate},endDate=${endDate}";
    public static final String ENC_DATE_FORMAT = "yyyy/MM/dd";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    static final String NCD_FORM_UUID = CommonMetadata._Form.NCD_FORM;

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor, ReportDefinition reportDefinition) {
        return Arrays.asList(
                ReportUtils.map(createMOH270DataSet(), indParams)
        );
    }




    private DataSetDefinition createMOH270DataSet() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        SpecialClinicsDiagnosisDataDefinition diagnosisDataDefinition = new SpecialClinicsDiagnosisDataDefinition();
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        dsd.setName("Permanent Register MOH270");
        dsd.setDescription("Patients linelist for NCD - MOH 270");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addColumn("Serial Number", "", ReportUtils.map(ncdIndicators.serialNumber(), indParams), "");
        dsd.addColumn("Visit Date", "", ReportUtils.map(ncdIndicators.visitDate(), indParams), "");
        dsd.addColumn("County Code", "", ReportUtils.map(ncdIndicators.countyCode(), indParams), "");
        dsd.addColumn("MFL Code", "", ReportUtils.map(ncdIndicators.mflCode(), indParams), "");
        dsd.addColumn("Patient No", "", ReportUtils.map(ncdIndicators.patientNumber(), indParams), "");
        dsd.addColumn("Name", "", ReportUtils.map(ncdIndicators.patientName(), indParams), "");
        dsd.addColumn("DOB", "", ReportUtils.map(ncdIndicators.DOB(), indParams), "");
        dsd.addColumn("Sex", "", ReportUtils.map(ncdIndicators.sex(), indParams), "");
        dsd.addColumn("Sub county", "", ReportUtils.map(ncdIndicators.subcounty(), indParams), "");
        dsd.addColumn("Telephone", "", ReportUtils.map(ncdIndicators.telephone(), indParams), "");
        dsd.addColumn("Village/Estate", "", ReportUtils.map(ncdIndicators.vilage(), indParams), "");
        dsd.addColumn("Landmark", "", ReportUtils.map(ncdIndicators.landmark(), indParams), "");
        dsd.addColumn("Treatment Supporter", "", ReportUtils.map(ncdIndicators.treatmentSupporter(), indParams), "");
        dsd.addColumn("Diagnosis", "", ReportUtils.map(ncdIndicators.diagnosis(), indParams), "");



        dsd.addColumn("Year of Diagnosis", "", ReportUtils.map(ncdIndicators.yearOfDiagnosis(), indParams), "");
        dsd.addColumn("Complications", "", ReportUtils.map(ncdIndicators.complications(), indParams), "");
        dsd.addColumn("treatment", "", ReportUtils.map(ncdIndicators.treatment(), indParams), "");
        dsd.addColumn("Patient Status", "", ReportUtils.map(ncdIndicators.patiententStatus(), indParams), "");
        dsd.addColumn("SHIF", "", ReportUtils.map(ncdIndicators.SHIF(), indParams), "");
        dsd.addColumn("Remarks", "", ReportUtils.map(ncdIndicators.Remarks(), indParams), "");

        return dsd;
    }

}
