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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.MOH740.Moh740IndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;

/**
 * MOH 755 report
 */
@Component
@Builds({"kenyaemr.ehrReports.report.moh740"})
public class Moh740ReportBuilder extends AbstractReportBuilder {
    protected static final Log log = LogFactory.getLog(Moh740ReportBuilder.class);
    static final int NEW_VISIT = 164180, RE_VISIT= 160530;
    static final String DIABETIC_CLINICAl = CommonMetadata._Form.DIABETIC_CLINICAL_FORM;
    @Autowired
    private CommonDimensionLibrary commonDimensions;
    @Autowired
    private Moh740IndicatorLibrary Moh740Indicator;
    @Override
    protected List<Parameter> getParameters(ReportDescriptor descriptor) {
        return Arrays.asList(
                new Parameter("startDate", "Start Date", Date.class),
                new Parameter("endDate", "End Date", Date.class),
                new Parameter("dateBasedReporting", "", String.class)
        );
    }
    String indParams = "startDate=${startDate},endDate=${endDate}";
    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
        return Arrays.asList(
                ReportUtils.map(createMoh740SummaryDataSet(), indParams)
        );
    }

    ColumnParameters f0_5 = new ColumnParameters(null, "0-5 years, Female", "gender=F|age=0-5");
    ColumnParameters m0_5 = new ColumnParameters(null, "0-5 years, Male", "gender=M|age=0-5");
    ColumnParameters f0_18 = new ColumnParameters(null, "0-18 years, Female", "gender=F|age=0-18");
    ColumnParameters m0_18 = new ColumnParameters(null, "0-18 years, Male", "gender=M|age=0-18");
    ColumnParameters f6_18 = new ColumnParameters(null, "6-18 years, Female", "gender=F|age=6-18");
    ColumnParameters m6_18 = new ColumnParameters(null, "6-18 years, Male", "gender=M|age=6-18");
    ColumnParameters f19_35 = new ColumnParameters(null, "19-35 years, Female", "gender=F|age=19-35");
    ColumnParameters m19_35 = new ColumnParameters(null, "19-35 years, Male", "gender=M|age=19-35");
    ColumnParameters f_all36AndAbove = new ColumnParameters(null, "36+ years, Female", "gender=F|age=36+");
    ColumnParameters m_all36AndAbove = new ColumnParameters(null, "36+ years, Male", "gender=M|age=36+");
    ColumnParameters m36_60 = new ColumnParameters(null, "36-60 years, Male", "gender=M|age=36-60");
    ColumnParameters f36_60 = new ColumnParameters(null, "36-60 years, Female", "gender=F|age=36-60");
    ColumnParameters m_all60AndAbove = new ColumnParameters(null, "60+ years, Male", "gender=M|age=60+");
    ColumnParameters f_all60AndAbove = new ColumnParameters(null, "60+ years, Female", "gender=F|age=60+");
    ColumnParameters colTotal = new ColumnParameters(null, "Total", "");

    // for all gender
    ColumnParameters male = new ColumnParameters(null, "Male", "gender=M");
    ColumnParameters female = new ColumnParameters(null, "Female", "gender=F");
    ColumnParameters allTotal = new ColumnParameters(null, "Total", "");

    List<ColumnParameters> type_1_AgeDisaggregations = Arrays.asList(m0_5, f0_5, m6_18, f6_18, m19_35, f19_35, f_all36AndAbove, m_all36AndAbove, colTotal);
    List<ColumnParameters> type_2_AgeDisaggregations = Arrays.asList(m0_18, f0_18, m19_35, f19_35, m36_60, f36_60, m_all60AndAbove, f_all60AndAbove, colTotal);
    List<ColumnParameters> hypertension_AgeDisaggregations = Arrays.asList(m0_18, f0_18, m19_35, f19_35, m36_60, f36_60, m_all60AndAbove, f_all60AndAbove, colTotal);

    List<ColumnParameters> all_indicators = Arrays.asList(male, female, allTotal);


    private DataSetDefinition createMoh740SummaryDataSet() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName("MOH740");
        dsd.setDescription("MOH740 MONTHLY SUMMARY");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addDimension("age", map(commonDimensions.moh740AgeGroups(), "onDate=${endDate}"));
        dsd.addDimension("gender", map(commonDimensions.gender()));

        EmrReportingUtils.addRow(dsd,"Cumulative no. of diabetes patients in care", "Cumulative no. of diabetes patients in care", ReportUtils.map(Moh740Indicator.cumulativeDiabetes(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"Cumulative no. of hypertension patients in care", "Cumulative no. of hypertension patients in care", ReportUtils.map(Moh740Indicator.cumulativeHypertension(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"Cumulative no. of co-morbid DM+HTN patients in care", "Cumulative no. of co-morbid DM+HTN patients in care", ReportUtils.map(Moh740Indicator.cumulativeDMAndHTN(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. of newly diagnosed diabetes cases", "No. of newly diagnosed diabetes cases", ReportUtils.map(Moh740Indicator.newDiagnosedDiabetes(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. of newly diagnosed hypertension cases", "No. of newly diagnosed hypertension cases", ReportUtils.map(Moh740Indicator.newDiagnosedHypertension(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"First visit to clinic", "First visit to clinic", ReportUtils.map(Moh740Indicator.firstVisitToClinic(NEW_VISIT), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"Pre-existing DM", "Pre-existing DM", ReportUtils.map(Moh740Indicator.preExistingDM(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"Pre-Existing HTN", "Pre-Existing HTN", ReportUtils.map(Moh740Indicator.preExistingHTN(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"Pre-Existing DM and HTN", "Pre-Existing DM and HTN", ReportUtils.map(Moh740Indicator.preExistingDMandHTN(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
//        dsd.addColumn("Pre-existing HTN", "", ReportUtils.map(Moh740Indicator.preExistingHTN(), indParams), "");

        EmrReportingUtils.addRow(dsd, "TypeOne", "Total no. with Type 1 Diabetes", ReportUtils.map(Moh740Indicator.diabetesByTypeOne(), indParams), type_1_AgeDisaggregations, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
        EmrReportingUtils.addRow(dsd, "TypeTwo", "Total no. with Type 2 Diabetes ", ReportUtils.map(Moh740Indicator.diabetesByTypeTwo(), indParams), type_2_AgeDisaggregations, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));

        EmrReportingUtils.addRow(dsd,"No. of Diabetes secondary to other causes", "No. of Diabetes secondary to other causes", ReportUtils.map(Moh740Indicator.diabetesSecondaryToOther(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. of patients on insulin", "No. of patients on insulin", ReportUtils.map(Moh740Indicator.patientOnInsulin(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. of patients on OGLAs", "No. of patients on OGLAs", ReportUtils.map(Moh740Indicator.patientOnOGLAs(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. of patients on both (Insulin and OGLAs)", "No. of patients on both (Insulin and OGLAs)", ReportUtils.map(Moh740Indicator.patientOnInsulinAndOGLAs(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. of patients on diet and exercise only (DM and HTN)", "No. of patients on diet and exercise only (DM and HTN)", ReportUtils.map(Moh740Indicator.patientOnDietAndExercise(), indParams), all_indicators, Arrays.asList("01", "02", "03"));

        // dsd.addColumn("No. of patients done HbA1c", "", ReportUtils.map(Moh740Indicator.patientDoneHbA1c(), indParams), "");
        EmrReportingUtils.addRow(dsd,"No. of patients done HbA1c", "No. of patients done HbA1c", ReportUtils.map(Moh740Indicator.patientDoneHbA1c(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. that met HbA1c target (< 7%)", "No. that met HbA1c target (< 7%)", ReportUtils.map(Moh740Indicator.patientMetHbA1cTarget(), indParams), all_indicators, Arrays.asList("01", "02", "03"));

        EmrReportingUtils.addRow(dsd, "Hypertension", "No. with hypertension", ReportUtils.map(Moh740Indicator.totalHypertension(), indParams), hypertension_AgeDisaggregations, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));

        EmrReportingUtils.addRow(dsd,"No. of patients on antihypertensives", "No. of patients on antihypertensives", ReportUtils.map(Moh740Indicator.patientOnAntihypertensives(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. with a BP (≥140/90) at clinic visit", "No. with a BP (≥140/90) at clinic visit", ReportUtils.map(Moh740Indicator.patientWithHighBP(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"Total no. of patients with CVD (new diagnosis) Stroke", "Total no. of patients with CVD (new diagnosis) Stroke", ReportUtils.map(Moh740Indicator.newDiagnosedStroke(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"Total no. of patients with CVD (new diagnosis) Ischemic heart disease", "Total no. of patients with CVD (new diagnosis) Ischemic heart disease", ReportUtils.map(Moh740Indicator.newDiagnosedHeartDisease(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"Total no. of patients with CVD (new diagnosis) Peripheral vascular/artery disease", "Total no. of patients with CVD (new diagnosis) Peripheral vascular/artery disease", ReportUtils.map(Moh740Indicator.newDiagnosedPeripheralDisease(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"Total no. of patients with CVD (new diagnosis) Heart failure", "Total no. of patients with CVD (new diagnosis) Heart failure", ReportUtils.map(Moh740Indicator.newDiagnosedHeartFailure(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. of Patients with neuropathies (new diagnosis)", "No. of Patients with neuropathies (new diagnosis)", ReportUtils.map(Moh740Indicator.newDiagnosedWithNeuropathies(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. of patients screened for diabetic foot", "No. of patients screened for diabetic foot", ReportUtils.map(Moh740Indicator.patientScreenedForDiabeticFoot(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. of patients with diabetic foot ulcer (new diagnosis)", "No. of patients with diabetic foot ulcer (new diagnosis)", ReportUtils.map(Moh740Indicator.newDiagnosedWithFootUlcer(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. of feet saved through treatment", "No. of feet saved through treatment", ReportUtils.map(Moh740Indicator.noFeetSavedThroughTreatment(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. of amputation due to diabetic foot", "No. of amputation due to diabetic foot", ReportUtils.map(Moh740Indicator.noAmputatedDiabeticFoot(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. with kidney complications (new diagnosis)", "No. with kidney complications (new diagnosis)", ReportUtils.map(Moh740Indicator.newDiagnosedWithKidneyComplication(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. with diabetic retinopathy (new diagnosis)", "No. with diabetic retinopathy (new diagnosis)", ReportUtils.map(Moh740Indicator.newDiagnosedWithDiabeticRetinopathy(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. Screened for Tuberculosis", "No. Screened for Tuberculosis", ReportUtils.map(Moh740Indicator.patientScreenedForTuberculosis(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"No. Screened Positive for Tuberculosis Total", "No. Screened Positive for Tuberculosis Total", ReportUtils.map(Moh740Indicator.patientScreenedPositiveTuberculosis(), indParams), all_indicators, Arrays.asList("01", "02", "03"));

        EmrReportingUtils.addRow(dsd,"No. enrolled with SHA", "No. enrolled with SHA", ReportUtils.map(Moh740Indicator.noEnrolledSHA(), indParams), all_indicators, Arrays.asList("01", "02", "03"));
        return dsd;
    }


}