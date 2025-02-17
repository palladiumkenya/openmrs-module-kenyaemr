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
import org.openmrs.module.kenyaemr.reporting.library.moh711.Moh711IndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.kenyaemr.reporting.library.specialClinics.SpecialClinicsIndicatorLibrary;
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
@Builds({"kenyaemr.ehrReports.report.moh755"})
public class Moh755ReportBuilder extends AbstractReportBuilder {

    protected static final Log log = LogFactory.getLog(Moh755ReportBuilder.class);

    static final String[] HPV_TEST_SCREENING = {"HPV Test","HPV"};
    static final String[] PAP_SMEAR_SCREENING = {"Pap Smear"};

    static final int NEW_VISIT = 164180,
            RE_VISIT= 160530,REFERALS= 160563, INTERVENTION = 165001, REFERRAL_IN =160563, REFERRAL_OUT = 159492;
    static final String SPECIAL_CLINIC = CommonMetadata._Form.OCCUPATIONAL_THERAPY_CLINICAL_FORM;
    @Autowired
    private CommonDimensionLibrary commonDimensions;

    @Autowired
    private SpecialClinicsIndicatorLibrary specialClinicsIndicators;

    @Autowired
    private Moh711IndicatorLibrary moh711Indicators;
    /**
     * @see AbstractReportBuilder#getParameters(ReportDescriptor)
     */
    @Override
    protected List<Parameter> getParameters(ReportDescriptor descriptor) {
        return Arrays.asList(
                new Parameter("startDate", "Start Date", Date.class),
                new Parameter("endDate", "End Date", Date.class),
                new Parameter("dateBasedReporting", "", String.class)
        );
    }

    String indParams = "startDate=${startDate},endDate=${endDate}";

    /**
     * @see AbstractReportBuilder#buildDataSets(ReportDescriptor, ReportDefinition)
     */
    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
        return Arrays.asList(
                ReportUtils.map(createOphthalmicServiceSummaryDataSet(), indParams)

        );
    }

   ColumnParameters fUnder1Month = new ColumnParameters(null, "<1 months", "gender=F|age=<1");
    ColumnParameters mUnder1Month = new ColumnParameters(null, "<1 months", "gender=M|age=<1");
    ColumnParameters f1To12Months = new ColumnParameters(null, "1-12 months", "gender=F|age=1-12");

    ColumnParameters m1To12Months = new ColumnParameters(null, "1-12 months", "gender=M|age=1-12");
    ColumnParameters f1_5 = new ColumnParameters(null, "1-5 years", "gender=F|age=1-5");
    ColumnParameters m1_5 = new ColumnParameters(null, "1-5 years", "gender=M|age=1-5");
    ColumnParameters f5_9 = new ColumnParameters(null, "5-9 years", "gender=F|age=5-9");
    ColumnParameters m5_9 = new ColumnParameters(null, "5-9 years", "gender=M|age=5-9");
    ColumnParameters f10_14 = new ColumnParameters(null, "10-14 years", "gender=F|age=10-14");
    ColumnParameters m10_14 = new ColumnParameters(null, "10-14 years", "gender=M|age=10-14");
    ColumnParameters colTotal = new ColumnParameters(null, "Total", "");

    List<ColumnParameters> therapeuticAgeDisaggregations = Arrays.asList(mUnder1Month, fUnder1Month,m1To12Months, f1To12Months,m1_5, f1_5,m5_9, f5_9,
            m10_14, f10_14, colTotal);

    private DataSetDefinition createOphthalmicServiceSummaryDataSet() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName("OPHTHALMIC_SERVICE_SUMMARY");
        dsd.setDescription("OPHTHALMIC SERVICE SUMMARY");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addDimension("age", map(commonDimensions.specialClinicsAgeGroups(), "onDate=${endDate}"));
        dsd.addDimension("gender", map(commonDimensions.gender()));

        dsd.addColumn("No. of interventions", "", ReportUtils.map(specialClinicsIndicators.otInterventionVisitType(NEW_VISIT, INTERVENTION, SPECIAL_CLINIC), indParams), "");
        dsd.addColumn("No. of referrals In", "", ReportUtils.map(specialClinicsIndicators.otInterventionVisitType(NEW_VISIT, REFERRAL_IN, SPECIAL_CLINIC), indParams), "");
        dsd.addColumn("No. of referrals Out", "", ReportUtils.map(specialClinicsIndicators.otInterventionVisitType(NEW_VISIT, REFERRAL_OUT, SPECIAL_CLINIC), indParams), "");
        dsd.addColumn("No. of interventions(Re-Visit)", "", ReportUtils.map(specialClinicsIndicators.otInterventionVisitType(RE_VISIT, INTERVENTION, SPECIAL_CLINIC), indParams), "");
        dsd.addColumn("No. of referrals In(Re-Visit)", "", ReportUtils.map(specialClinicsIndicators.otInterventionVisitType(RE_VISIT, REFERRAL_IN, SPECIAL_CLINIC), indParams), "");
        dsd.addColumn("No. of referrals Out(Re-Visit)", "", ReportUtils.map(specialClinicsIndicators.otInterventionVisitType(RE_VISIT, REFERRAL_OUT, SPECIAL_CLINIC), indParams), "");
        dsd.addColumn("Total referrals In (New & Re-Visit)", "", ReportUtils.map(specialClinicsIndicators.totalNoOfOtInterventions(NEW_VISIT, RE_VISIT, REFERRAL_IN, SPECIAL_CLINIC), indParams), "");
        dsd.addColumn("Total referrals Out(New & Re-Visit)", "", ReportUtils.map(specialClinicsIndicators.totalNoOfOtInterventions(NEW_VISIT, RE_VISIT, REFERRAL_OUT, SPECIAL_CLINIC), indParams), "");
        dsd.addColumn("Total of Interventions(New & Re-Visit)", "", ReportUtils.map(specialClinicsIndicators.totalNoOfOtInterventions(NEW_VISIT, RE_VISIT, INTERVENTION, SPECIAL_CLINIC), indParams), "");
        dsd.addColumn("Total No. Of ATs Dispensed New Visits", "", ReportUtils.map(specialClinicsIndicators.totalNumberOfATsDispensed(INTERVENTION, REFERRAL_IN, REFERRAL_OUT, NEW_VISIT, SPECIAL_CLINIC), indParams), "");
        dsd.addColumn("Total No. Of ATs Dispensed Re-Visits", "", ReportUtils.map(specialClinicsIndicators.totalNumberOfATsDispensed(INTERVENTION, REFERRAL_IN, REFERRAL_OUT, RE_VISIT, SPECIAL_CLINIC), indParams), "");
        EmrReportingUtils.addRow(dsd, "learningFindings", "Learning Findings", ReportUtils.map(specialClinicsIndicators.learningFindings(SPECIAL_CLINIC), indParams), therapeuticAgeDisaggregations, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"));
        EmrReportingUtils.addRow(dsd, "neurodevelopmental", "Neuron Developmental", ReportUtils.map(specialClinicsIndicators.neurodevelopmental(SPECIAL_CLINIC), indParams), therapeuticAgeDisaggregations, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"));
        EmrReportingUtils.addRow(dsd, "neurodiversityConditions", "Neurodiversity Conditions", ReportUtils.map(specialClinicsIndicators.neurodiversityConditions(SPECIAL_CLINIC), indParams), therapeuticAgeDisaggregations, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"));
        EmrReportingUtils.addRow(dsd, "childrenWithIntellectualDisabilities", "Children With Intellectual Disabilities", ReportUtils.map(specialClinicsIndicators.childrenWithIntellectualDisabilities(SPECIAL_CLINIC), indParams), therapeuticAgeDisaggregations, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"));
        EmrReportingUtils.addRow(dsd, "delayedDevelopmentalMilestones", "Delayed Developmental Milestones", ReportUtils.map(specialClinicsIndicators.delayedDevelopmentalMilestones(SPECIAL_CLINIC), indParams), therapeuticAgeDisaggregations, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"));
        EmrReportingUtils.addRow(dsd, "childrenTrainedOnAT", "No. of children  identified on assistive technology play device", ReportUtils.map(specialClinicsIndicators.childrenTrainedOnAT(SPECIAL_CLINIC), indParams), therapeuticAgeDisaggregations, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"));
        return dsd;
    }


}