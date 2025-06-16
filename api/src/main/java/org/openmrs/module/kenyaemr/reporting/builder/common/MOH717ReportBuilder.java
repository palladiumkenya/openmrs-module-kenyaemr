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

import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.moh717.Moh717CohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.moh717.Moh717IndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.openmrs.module.kenyaemr.EmrConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;

/**
 * Report builder for MOH717
 * Work load
 */

@Component
@Builds({ "kenyaemr.ehrReports.report.717" })
public class MOH717ReportBuilder extends AbstractReportBuilder {
    private final Moh717CohortLibrary moh717CohortLibrary;

    private final CommonDimensionLibrary commonDimensionLibrary;

    private final Moh717IndicatorLibrary moh717IndicatorLibrary;

    static final int NEW_VISIT = 164180, RE_ATT= 160530;

    static final ArrayList<String> otherSpecialClinics = new ArrayList<>(Arrays.asList(
            CommonMetadata._Form.NEUROLOGY_CLINICAL_FORM,
            CommonMetadata._Form.DERMATOLOGY_CLINICAL_FORM,
            CommonMetadata._Form.AUDIOLOGY_FORM,
            CommonMetadata._Form.SPEECH_AND_LANGAUGE_THERAPY_CLINICAL_FORM,
            CommonMetadata._Form.DIABETIC_CLINICAL_FORM,
            CommonMetadata._Form.MAXILLOFACIAL_CLINICAL_FORM,
            CommonMetadata._Form.GASTROENTEROLOGY_CLINICAL_FORM,
            CommonMetadata._Form.CARDIOLOGY_CLINICAL_FORM,
            CommonMetadata._Form.DENTAL_CLINICAL_FORM,
            CommonMetadata._Form.FERTILITY_CLINICAL_FORM,
            CommonMetadata._Form.UROLOGY_CLINICAL_FORM
    ));
    static final String OCCUPATIONAL_THERAPY_FORM = CommonMetadata._Form.OCCUPATIONAL_THERAPY_CLINICAL_FORM;
    static final String PHYSIOTHERAPY_FORM = CommonMetadata._Form.PHYSIOTHERAPY_FORM;
    static final String dentalFillingList = String.join(",", new ArrayList<>(Arrays.asList(
            EmrConstants.TEMP_FILLING_PER_TOOTH, EmrConstants.COMPOSITE_FILLING, EmrConstants.AMALGAM_FILLING, EmrConstants.GlASS_LONOMER_FILLING
    )));
    static final String dentalExtractionList = String.join(",",new ArrayList<>(Arrays.asList(
            EmrConstants.SIMPLE_TOOTH_EXTRACTION, EmrConstants.SURGICAL_TOOTH_EXTRACTION, EmrConstants.DENTAL_EXTRACTION_UNDER_GA, EmrConstants.EXTRA_TOOTH_EXTRACTION
    )));
    static final String stitchesRemovalList = String.join(",", new ArrayList<>(Arrays.asList(
            EmrConstants.REMOVAL_OF_CORNEAL_CONJUCTIVAL_SUTURES_LA, EmrConstants.REMOVAL_OF_CORNEAL_CONJUCTIVAL_SUTURES_GA, EmrConstants.SURGICAL_REMOVAL_OF_STICHES_MINOR_THEATRE, EmrConstants.REMOVAL_OF_CORNEAL_STITCHES,
            EmrConstants.ENT_REMOVAL_OF_STITCHES
    )));
    static final String injectionsList = String.join(",",new ArrayList<>(Arrays.asList(
            EmrConstants.INJECTION, EmrConstants.INJECTION_OF_SCLEROSING_AGENT_IN_VEIN, EmrConstants.INJECTION_OF_SUBSTANCE_INTO_VENTRICULAR_SHUNT, EmrConstants.SUB_CONJUNCTIVAL_INJECTION,
            EmrConstants.INTRA_ARTERIAL_INJECTION,EmrConstants.SUB_CONJUCTIRAL_SUB_TENON_INJECTION
    )));
    static final String stitchingList = String.join(",",new ArrayList<>(Arrays.asList(
            EmrConstants.STITCHING, EmrConstants.DENTAL_STITCHING, EmrConstants.NOSE_EARS_STITCHING, EmrConstants.STITCHING_OPD,
            EmrConstants.STITCHING_MINOR_THEATER,EmrConstants.SATURING_ESOPHAGEAL_LACERATION_TRANSABDOMINAL_APPROACH,
            EmrConstants.SATURING_ESOPHAGEAL_LACERATION_TRANSTHORACIC_APPROACH,EmrConstants.ENT_SURGICAL_TOILET_STITCHING_UNDER_GA
    )));
    static final String dressingList = String.join(",",new ArrayList<>(Arrays.asList(
            EmrConstants.CLEAN_AND_DRESSING, EmrConstants.BURN_DRESSING, EmrConstants.ENT_DRESSING, EmrConstants.CASUALTY_DRESSING,
            EmrConstants.ENT_CHANGE_OF_DRESSINGS,EmrConstants.DRESSING_MINOR,
            EmrConstants.DRESSING_MINOR_OPD,EmrConstants.EYE_DRESSING,EmrConstants.EXTENSIVE_DRESSING,EmrConstants.DRESSING_LARGE_SPECIALISED,
            EmrConstants.DRESSING_SMALL_BURN,EmrConstants.SATURE_WOUND_WITH_DRESSING,EmrConstants.WOUND_DRESSING,EmrConstants.WOUND_DRESSING_ENT
    )));

    ColumnParameters femaleChildrenUnder5 = new ColumnParameters(null, "<5", "age=<5|gender=F");
    ColumnParameters maleChildrenUnder5 = new ColumnParameters(null, "<5", "age=<5|gender=M");
    ColumnParameters females5To59 = new ColumnParameters(null, "5-59, Female", "gender=F|age=5-59");
    ColumnParameters males5To59 = new ColumnParameters(null, "5-59, Male", "gender=M|age=5-59");
    ColumnParameters over60 = new ColumnParameters(null, ">60", "age=60+");

    ColumnParameters colTotal = new ColumnParameters(null, "Total", "");

    List<ColumnParameters> moh717Disaggregations = Arrays.asList(males5To59, females5To59,
            maleChildrenUnder5, femaleChildrenUnder5, over60, colTotal);

    @Autowired
    public MOH717ReportBuilder(Moh717CohortLibrary moh717CohortLibrary, CommonDimensionLibrary commonDimensionLibrary, Moh717IndicatorLibrary moh717IndicatorLibrary) {
        this.moh717CohortLibrary = moh717CohortLibrary;
        this.commonDimensionLibrary = commonDimensionLibrary;
        this.moh717IndicatorLibrary = moh717IndicatorLibrary;
    }

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
                Date.class), new Parameter("dateBasedReporting", "", String.class));
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,ReportDefinition reportDefinition) {
        return Arrays.asList(ReportUtils.map(moh717DatasetDefinition(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(totalAmountCollectedDatasetDefinition(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(totalAmountReceivedDatasetDefinition(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(clientsWaivedDatasetDefinition(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(totalAmountWaivedDatasetDefinition(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(clientsExemptedDatasetDefinition(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(totalAmountExemptedDatasetDefinition(), "startDate=${startDate},endDate=${endDate}")
                );
    }

    private DataSetDefinition moh717DatasetDefinition() {
        String indParams = "startDate=${startDate},endDate=${endDate}";

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.setName("MOH717");
        dsd.setDescription("MOH 717 Report");

        dsd.addDimension("age", map(commonDimensionLibrary.standardAgeGroups(), "onDate=${endDate}"));
        dsd.addDimension("gender", map(commonDimensionLibrary.gender(), ""));
        dsd.addDimension("state", map(commonDimensionLibrary.newOrRevisits(), "startDate=${startDate},endDate=${endDate}"));

        EmrReportingUtils.addRow(dsd, "General OP New", "",
                ReportUtils.map(moh717IndicatorLibrary.getPatientsWithNewClinicalEncounterWithinReportingPeriod(), indParams), moh717Disaggregations, Arrays.asList("01", "02", "03", "04", "05","06"));

        EmrReportingUtils.addRow(dsd, "General OP RE-ATT", "",
                ReportUtils.map(moh717IndicatorLibrary.getPatientsWithReturnClinicalEncounterWithinReportingPeriod(), indParams), moh717Disaggregations, Arrays.asList("01", "02", "03", "04", "05","06"));

        dsd.addColumn("New CWC Visits", "",
                ReportUtils.map(moh717IndicatorLibrary.newCWCVisits(), indParams), "");
        dsd.addColumn( "CWC RE-ATT", "",
                ReportUtils.map(moh717IndicatorLibrary.cwcRevisits(), indParams), "");
        dsd.addColumn( "New ANC Visits", "",
                ReportUtils.map(moh717IndicatorLibrary.newANCVisits(), indParams), "");
        dsd.addColumn( "ANC RE-ATT", "",
                ReportUtils.map(moh717IndicatorLibrary.ancRevisits(), indParams),"");
        dsd.addColumn("New PNC Visits", "",
                ReportUtils.map(moh717IndicatorLibrary.newPNCVisits(), indParams), "");
        dsd.addColumn( "PNC RE-ATT", "",
                ReportUtils.map(moh717IndicatorLibrary.pncReVisits(), indParams), "");
        dsd.addColumn( "FP NEW", "",
                ReportUtils.map(moh717IndicatorLibrary.fpVisit(NEW_VISIT), indParams), "");
        dsd.addColumn( "FP RE-ATT", "",
                ReportUtils.map(moh717IndicatorLibrary.fpVisit(RE_ATT), indParams), "");
        // Maternity Services
        dsd.addColumn( "Normal Deliveries", "", ReportUtils.map(moh717IndicatorLibrary.normalDeliveries(), indParams), "");
        dsd.addColumn( "Caesarean sections", "", ReportUtils.map(moh717IndicatorLibrary.caesareanSections(), indParams), "");
        dsd.addColumn( "Breech Deliveries", "", ReportUtils.map(moh717IndicatorLibrary.breechDeliveries(), indParams), "");
        dsd.addColumn( "Assisted Vaginal Delivery", "", ReportUtils.map(moh717IndicatorLibrary.assistedVaginalDelivery(), indParams), "");
        dsd.addColumn( "Born before arrival", "", ReportUtils.map(moh717IndicatorLibrary.bornBeforeArrival(), indParams), "");
        dsd.addColumn( "Maternal deaths", "", ReportUtils.map(moh717IndicatorLibrary.maternalDeaths(), indParams), "");
        dsd.addColumn( "Maternal deaths Audited", "", ReportUtils.map(moh717IndicatorLibrary.maternalDeathsAudited(), indParams), "");
        dsd.addColumn( "Live births", "", ReportUtils.map(moh717IndicatorLibrary.liveBirths(), indParams), "");
        dsd.addColumn( "Still births", "", ReportUtils.map(moh717IndicatorLibrary.stillBirths(), indParams), "");
        dsd.addColumn( "Neonatal deaths", "", ReportUtils.map(moh717IndicatorLibrary.neonatalDeaths(), indParams), "");
        dsd.addColumn( "Low Birth weight babies", "", ReportUtils.map(moh717IndicatorLibrary.lowBirthWeightBabies(), indParams), "");
        dsd.addColumn( "Total Discharges (new born)", "", ReportUtils.map(moh717IndicatorLibrary.totalDischarges(), indParams), "");

        dsd.addColumn( "Number of Laboratory tests", "", ReportUtils.map(moh717IndicatorLibrary.laboratoryTests(), indParams), "");
        dsd.addColumn("Number of Examinations (XRay & Imaging)", "", ReportUtils.map(moh717IndicatorLibrary.xrayAndImaging(), indParams), "");

        // Special Clinics
        dsd.addColumn( "ENT Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.EAR_NOSE_THROAT_CLINICAL_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "ENT Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.EAR_NOSE_THROAT_CLINICAL_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Eye Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.OPHTHAMOLOGY_CLINICAL_FORM,NEW_VISIT), indParams), "");
        dsd.addColumn( "Eye Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.OPHTHAMOLOGY_CLINICAL_FORM,RE_ATT), indParams), "");
        dsd.addColumn( "TB and Leprosy Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.newOnTBClinic(), indParams), "");
        dsd.addColumn( "TB and Leprosy Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.revisitTBClinic(), indParams), "");
        dsd.addColumn( "CCC Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.newOnCCClinic(), indParams), "");
        dsd.addColumn( "CCC Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.revisitCCClinic(), indParams), "");
        dsd.addColumn( "Psychiatry Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.PSYCHIATRIC_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "Psychiatry Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.PSYCHIATRIC_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Orthopaedic Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.ORTHOPAEDIC_CLINICAL_FORM,NEW_VISIT), indParams), "");
        dsd.addColumn( "Orthopaedic Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.ORTHOPAEDIC_CLINICAL_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Occupational Therapy Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.OCCUPATIONAL_THERAPY_CLINICAL_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "Occupational Therapy Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.OCCUPATIONAL_THERAPY_CLINICAL_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Physiotherapy Therapy Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.PHYSIOTHERAPY_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "Physiotherapy Therapy Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.PHYSIOTHERAPY_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Medical Clinics (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.MOPC_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "Medical Clinics (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.MOPC_FORM,RE_ATT), indParams), "");
        dsd.addColumn( "Surgical Clinics (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.SOPC_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "Surgical Clinics (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.SOPC_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Paediatrics (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.POPC_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "Paediatrics (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.POPC_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Obstetrics and Gynaecology (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.GOPC_FORM,NEW_VISIT), indParams), "");
        dsd.addColumn( "Obstetrics and Gynaecology (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.GOPC_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Nutrition Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.NUTRITION, NEW_VISIT), indParams), "");
        dsd.addColumn( "Nutrition Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.NUTRITION, RE_ATT), indParams), "");
        dsd.addColumn( "Oncology Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.ONCOLOGY_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "Oncology Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.ONCOLOGY_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Renal Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.RENAL_CLINICAL_FORM,NEW_VISIT), indParams), "");
        dsd.addColumn( "Renal Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.RENAL_CLINICAL_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "All other special Clinics (New)", "", ReportUtils.map(moh717IndicatorLibrary.otherSpecialClinics(EmrUtils.formatListWithQuotes(otherSpecialClinics),NEW_VISIT), indParams), "");
        dsd.addColumn( "All other special Clinics (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.otherSpecialClinics(EmrUtils.formatListWithQuotes(otherSpecialClinics),RE_ATT), indParams), "");

        dsd.addColumn( "Dental Attendances (New)", "", ReportUtils.map(moh717IndicatorLibrary.dentalAttendances(dentalExtractionList, dentalFillingList,NEW_VISIT), indParams), "");
        dsd.addColumn( "Dental Attendances (RE_ATT)", "", ReportUtils.map(moh717IndicatorLibrary.dentalAttendances(dentalExtractionList,dentalFillingList,RE_ATT), indParams), "");
        dsd.addColumn( "Dental Fillings (New)", "", ReportUtils.map(moh717IndicatorLibrary.dentalFillings(dentalFillingList,NEW_VISIT), indParams), "");
        dsd.addColumn( "Dental Fillings (RE_ATT)", "", ReportUtils.map(moh717IndicatorLibrary.dentalFillings(dentalFillingList,RE_ATT), indParams), "");
        dsd.addColumn( "Dental Extractions (New)", "", ReportUtils.map(moh717IndicatorLibrary.dentalExtractions(dentalExtractionList,NEW_VISIT), indParams), "");
        dsd.addColumn( "Dental Extractions (RE_ATT)", "", ReportUtils.map(moh717IndicatorLibrary.dentalExtractions(dentalExtractionList,RE_ATT), indParams), "");
        dsd.addColumn( "Dressings", "", ReportUtils.map(moh717IndicatorLibrary.dressings(dressingList), indParams), "");
        dsd.addColumn( "Removal of Stitches", "", ReportUtils.map(moh717IndicatorLibrary.removalOfStitches(stitchesRemovalList), indParams), "");
        dsd.addColumn( "Injections", "", ReportUtils.map(moh717IndicatorLibrary.injections(injectionsList), indParams), "");
        dsd.addColumn( "Stitching", "", ReportUtils.map(moh717IndicatorLibrary.stitching(stitchingList), indParams), "");
        dsd.addColumn( "Physiotherapy Treatments", "", ReportUtils.map(moh717IndicatorLibrary.specialClinics(PHYSIOTHERAPY_FORM), indParams), "");
        dsd.addColumn( "Occupational Therapy Treatments", "", ReportUtils.map(moh717IndicatorLibrary.specialClinics(OCCUPATIONAL_THERAPY_FORM), indParams), "");

        return dsd;
    }
    private DataSetDefinition totalAmountCollectedDatasetDefinition(){
        SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
        sqlDataSetDefinition.setName("AmountCollected");
        sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        sqlDataSetDefinition.setSqlQuery("select CAST(IFNULL(sum(ifnull(r.total_sales, 0)), 0) AS SIGNED) as total_amount_collected\n" +
                "from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sqlDataSetDefinition;
    }
    private DataSetDefinition totalAmountReceivedDatasetDefinition(){
        SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
        sqlDataSetDefinition.setName("AmountReceived");
        sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        sqlDataSetDefinition.setSqlQuery("SELECT CAST(\n" +
                "               IFNULL(\n" +
                "                       SUM(\n" +
                "                               IFNULL(r.cash_receipts_cash_from_daily_services, 0) +\n" +
                "                               IFNULL(r.cash_receipt_nhif_receipt, 0) +\n" +
                "                               IFNULL(r.cash_receipt_other_debtors_receipt, 0)\n" +
                "                       ),\n" +
                "                       0) AS SIGNED\n" +
                "       ) AS total_amount_received\n" +
                "FROM kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "WHERE DATE(transaction_date) BETWEEN DATE(:startDate) AND DATE(:endDate);");
        return sqlDataSetDefinition;
    }
    private DataSetDefinition clientsWaivedDatasetDefinition(){
        SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
        sqlDataSetDefinition.setName("clientsWaived");
        sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        sqlDataSetDefinition.setSqlQuery("select CAST(IFNULL(count(ifnull(r.revenue_not_collected_patient_not_yet_paid_waivers, 0)),\n" +
                "                   0) AS SIGNED) as clients_waived\n" +
                "from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sqlDataSetDefinition;
    }
    private DataSetDefinition totalAmountWaivedDatasetDefinition(){
        SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
        sqlDataSetDefinition.setName("AmountWaived");
        sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        sqlDataSetDefinition.setSqlQuery("select CAST(ifnull(sum(ifnull(r.revenue_not_collected_patient_not_yet_paid_waivers, 0)), 0) AS SIGNED) as total_waived\n" +
                "from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sqlDataSetDefinition;
    }
    private DataSetDefinition clientsExemptedDatasetDefinition(){
        SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
        sqlDataSetDefinition.setName("clientsExempted");
        sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        sqlDataSetDefinition.setSqlQuery("select CAST(ifnull(count(ifnull(r.revenue_not_collected_write_offs_exemptions, 0)), 0) AS SIGNED) as clients_exempted\n" +
                "from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sqlDataSetDefinition;
    }
    private DataSetDefinition totalAmountExemptedDatasetDefinition(){
        SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
        sqlDataSetDefinition.setName("AmountExempted");
        sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        sqlDataSetDefinition.setSqlQuery("select CAST(IFNULL(sum(ifnull(r.revenue_not_collected_write_offs_exemptions, 0)), 0) AS SIGNED) as total_exempted\n" +
                "from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sqlDataSetDefinition;
    }
    
}
