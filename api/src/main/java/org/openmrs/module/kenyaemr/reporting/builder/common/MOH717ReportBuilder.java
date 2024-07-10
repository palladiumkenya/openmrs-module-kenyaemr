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
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.moh717.Moh717CohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.moh717.Moh717IndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.openmrs.module.reporting.indicator.CohortIndicator;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.reporting.MohReportUtils.ReportAddonUtils.getGeneralOutPatientFilters;

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
        dsd.addColumn( "Low Birth weight babies", "", ReportUtils.map(moh717IndicatorLibrary.lowBirthWeightBabies(), indParams), "");
        dsd.addColumn( "Total Discharges (new born)", "", ReportUtils.map(moh717IndicatorLibrary.totalDischarges(), indParams), "");

        dsd.addColumn( "Number of Laboratory tests", "", ReportUtils.map(moh717IndicatorLibrary.laboratoryTests(), indParams), "");
        dsd.addColumn("Number of Examinations (XRay & Imaging)", "", ReportUtils.map(moh717IndicatorLibrary.xrayAndImaging(), indParams), "");
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
