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

import org.openmrs.module.kenyacore.report.HybridReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractHybridReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.DailyBedReturnPatientDischargeRegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.DailyBedReturnRegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.KenyaEMROpenMRSNumberDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.bed.*;
import org.openmrs.module.kenyaemr.reporting.library.bed.DailyBedReturnIndicatorLibrary;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.PatientIdDataDefinition;
import org.openmrs.module.reporting.data.person.definition.*;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Component
@Builds({"kenyaemr.ehrReports.report.dailyBedReturn"})
public class DailyBedReturnReportBuilder extends AbstractHybridReportBuilder {
    public static final String ENC_DATE_FORMAT = "yyyy/MM/dd";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String ADMISSION_UUID = "e22e39fd-7db2-45e7-80f1-60fa0d5a4378";
    public static final String TRANSFER_UUID = "d3b07384-8d1c-4e6b-9b8e-2f3b8e4a1c9f";
    public static final String OCCUPIED = "OCCUPIED";
    public static final String AVAILABLE = "AVAILABLE";
    public static final Integer CURED = 162677;
    public static final Integer ABSCONDED = 160431;
    public static final Integer LEFT_AGAINST_MEDICAL_ADVICE = 1694;
    public static final Integer DECEASED = 159 ;
    public static final Integer REFERRED_TO_ANOTHER_FACILITY = 164165;



    @Autowired
    private DailyBedReturnIndicatorLibrary dailyBedReturnCohortLibrary;
    String indParams = "startDate=${startDate},endDate=${endDate}";

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(
                new Parameter("startDate", "Start Date", Date.class),
                new Parameter("endDate", "End Date", Date.class),
                new Parameter("dateBasedReporting", "", String.class)
        );
    }
    @Override
    protected Mapped<CohortDefinition> buildCohort(HybridReportDescriptor descriptor, PatientDataSetDefinition dsd) {
        return null;
    }
    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor, ReportDefinition reportDefinition) {
        return Arrays.asList(
                ReportUtils.map(datasetColumns(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(deathAndDischargeDatasetColumns(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(interWardTransferDatasetColumns(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(summaryDailyBedReturnDataSet(), indParams),
                ReportUtils.map(indicatorsDataSet(), indParams)


        );
    }
    private DataSetDefinition summaryDailyBedReturnDataSet() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName("DBR");
        dsd.setDescription("Bed Occupation Status");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addColumn("Current Bed Occupation", "",ReportUtils.map(dailyBedReturnCohortLibrary.patientCurrentBedOccupationStatus(OCCUPIED), indParams), "");
        dsd.addColumn("Current Bed vacant Occupation", "", ReportUtils.map(dailyBedReturnCohortLibrary.patientCurrentBedOccupationStatus(AVAILABLE), indParams), "");
        dsd.addColumn("Previous Bed Occupation", "", ReportUtils.map(dailyBedReturnCohortLibrary.patientPreviousBedOccupationStatus(OCCUPIED), indParams), "");
        dsd.addColumn("Previous Bed vacant Occupation", "", ReportUtils.map(dailyBedReturnCohortLibrary.patientPreviousBedOccupationStatus(AVAILABLE), indParams), "");
        dsd.addColumn("Current Total Beds", "", ReportUtils.map(dailyBedReturnCohortLibrary.currentTotalBeds(OCCUPIED,AVAILABLE), indParams), "");
        dsd.addColumn("Previous Total Beds", "", ReportUtils.map(dailyBedReturnCohortLibrary.previousTotalBeds(OCCUPIED,AVAILABLE), indParams), "");
        dsd.addColumn("Patient Admissions Today", "", ReportUtils.map(dailyBedReturnCohortLibrary.patientsAdmittedByEndOfToday(ADMISSION_UUID), indParams), "");
        dsd.addColumn("Total Patients by End of Reporting Period", "",ReportUtils.map(dailyBedReturnCohortLibrary.totalPatientsByEndOfReportingPeriod(ADMISSION_UUID,OCCUPIED), indParams), "");
        dsd.addColumn("Total Patients Discharged today", "",ReportUtils.map(dailyBedReturnCohortLibrary.patientsDischargedByEndOfToday(), indParams), "");
        dsd.addColumn("Total Patients in Ward", "",ReportUtils.map(dailyBedReturnCohortLibrary.totalPatientsRemainingInWardByEndOfReportingPeriod(ADMISSION_UUID), indParams), "");

        return dsd;
    }
    private DataSetDefinition indicatorsDataSet() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName("Indicators");
        dsd.setDescription("Indicators");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addColumn("Admission", "",ReportUtils.map(dailyBedReturnCohortLibrary.patientAdmissionStatus(ADMISSION_UUID), indParams), "");
        dsd.addColumn("Home", "",ReportUtils.map(dailyBedReturnCohortLibrary.patientDischargedStatus(CURED), indParams), "");
        dsd.addColumn("Abscondee", "",ReportUtils.map(dailyBedReturnCohortLibrary.patientDischargedStatus(ABSCONDED), indParams), "");
        dsd.addColumn("Left", "",ReportUtils.map(dailyBedReturnCohortLibrary.patientDischargedStatus(LEFT_AGAINST_MEDICAL_ADVICE), indParams), "");
        dsd.addColumn("Died", "",ReportUtils.map(dailyBedReturnCohortLibrary.patientDischargedStatus(DECEASED), indParams), "");
        dsd.addColumn("Referred", "",ReportUtils.map(dailyBedReturnCohortLibrary.patientDischargedStatus(REFERRED_TO_ANOTHER_FACILITY), indParams), "");
        dsd.addColumn("Discharge transfer", "",ReportUtils.map(dailyBedReturnCohortLibrary.totalInterWardTransferDischargeReportingPeriod(TRANSFER_UUID), indParams), "");
        dsd.addColumn("Admission transfer", "",ReportUtils.map(dailyBedReturnCohortLibrary.totalInterWardAdmissionTransferReportingPeriod(ADMISSION_UUID), indParams), "");
        dsd.addColumn("Total Discharged", "",ReportUtils.map(dailyBedReturnCohortLibrary.totalPatientDischargedStatus(), indParams), "");
        return dsd;
    }

    protected DataSetDefinition datasetColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("dailyBedReturn");
        dsd.setDescription("IPD Visit information");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);

        KenyaEMROpenMRSNumberDataDefinition openMRSNumberDataDefinition = new KenyaEMROpenMRSNumberDataDefinition();
        openMRSNumberDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        openMRSNumberDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        PatientDischargedTypeDataDefinition patientDischargedTypeDataDefinition = new PatientDischargedTypeDataDefinition();
        patientDischargedTypeDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        patientDischargedTypeDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        PatientAdmittedDateDataDefinition patientAdmittedDateDataDefinition = new PatientAdmittedDateDataDefinition();
        patientAdmittedDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        patientAdmittedDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        patientAdmittedDateDataDefinition.setEncounterType(ADMISSION_UUID);

        PatientAdmissionWardDataDefinition wardDataDefinition = new PatientAdmissionWardDataDefinition();
        wardDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        wardDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        wardDataDefinition.setEncounterType(ADMISSION_UUID);

        PatientAdmissionWardDataDefinition wardTransferDataDefinition = new PatientAdmissionWardDataDefinition();
        wardTransferDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        wardTransferDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        wardTransferDataDefinition.setEncounterType(TRANSFER_UUID);

        dsd.addColumn("id", new PatientIdDataDefinition(), "");
        dsd.addColumn("Date", patientAdmittedDateDataDefinition, paramMapping);
        dsd.addColumn("OpenMRS Number", openMRSNumberDataDefinition, paramMapping);
        dsd.addColumn("Ward", wardDataDefinition, paramMapping);
        dsd.addColumn("Transfer Ward", wardTransferDataDefinition, paramMapping);
        dsd.addColumn("Discharge Type", patientDischargedTypeDataDefinition, paramMapping);
        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("Age", new AgeDataDefinition(), "");
        dsd.addColumn("Sex", new GenderDataDefinition(), "");

        DailyBedReturnRegisterCohortDefinition cd = new DailyBedReturnRegisterCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setEncounterType(ADMISSION_UUID);
        dsd.addRowFilter(cd, paramMapping);
        return dsd;
    }

    protected DataSetDefinition deathAndDischargeDatasetColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("discharge");
        dsd.setDescription("IPD Discharge");
        dsd.addSortCriteria("Visit Date", SortCriteria.SortDirection.ASC);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);

        PatientDischargedDateDataDefinition patientDischargedDateDataDefinition = new PatientDischargedDateDataDefinition();
        patientDischargedDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        patientDischargedDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        PatientDischargedLocationDataDefinition patientDischargedLocationDataDefinition = new PatientDischargedLocationDataDefinition();
        patientDischargedLocationDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        patientDischargedLocationDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        KenyaEMROpenMRSNumberDataDefinition openMRSNumberDataDefinition = new KenyaEMROpenMRSNumberDataDefinition();
        openMRSNumberDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        openMRSNumberDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("id", new PatientIdDataDefinition(), "");
        dsd.addColumn("Date", patientDischargedDateDataDefinition, paramMapping);
        dsd.addColumn("OpenMRS Number", openMRSNumberDataDefinition, paramMapping);
        dsd.addColumn("Ward", patientDischargedLocationDataDefinition, paramMapping);

        DailyBedReturnPatientDischargeRegisterCohortDefinition cd = new DailyBedReturnPatientDischargeRegisterCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addRowFilter(cd, paramMapping);
        return dsd;

    }
    protected DataSetDefinition interWardTransferDatasetColumns() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("interWardTransfer");
        dsd.setDescription("IPD Discharge");
        dsd.addSortCriteria("Visit Date", SortCriteria.SortDirection.ASC);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        String paramMapping = "startDate=${startDate},endDate=${endDate}";

        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);

        PatientAdmittedDateDataDefinition patientAdmittedDateDataDefinition = new PatientAdmittedDateDataDefinition();
        patientAdmittedDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        patientAdmittedDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        patientAdmittedDateDataDefinition.setEncounterType(TRANSFER_UUID);

        PatientAdmissionWardDataDefinition admittedDefinition = new PatientAdmissionWardDataDefinition();
        admittedDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        admittedDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        admittedDefinition.setEncounterType(ADMISSION_UUID);

        PatientAdmissionWardDataDefinition transferredDefinition = new PatientAdmissionWardDataDefinition();
        transferredDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        transferredDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        transferredDefinition.setEncounterType(TRANSFER_UUID);

        KenyaEMROpenMRSNumberDataDefinition openMRSNumberDataDefinition = new KenyaEMROpenMRSNumberDataDefinition();
        openMRSNumberDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        openMRSNumberDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));

        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("id", new PatientIdDataDefinition(), "");
        dsd.addColumn("Date", patientAdmittedDateDataDefinition, paramMapping);
        dsd.addColumn("OpenMRS Number", openMRSNumberDataDefinition, paramMapping);
        dsd.addColumn("Ward Admitted", admittedDefinition, paramMapping);
        dsd.addColumn("Ward Transferred", transferredDefinition, paramMapping);

        DailyBedReturnRegisterCohortDefinition cd = new DailyBedReturnRegisterCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cd.setEncounterType(TRANSFER_UUID);
        dsd.addRowFilter(cd, paramMapping);
        return dsd;

    }

}
