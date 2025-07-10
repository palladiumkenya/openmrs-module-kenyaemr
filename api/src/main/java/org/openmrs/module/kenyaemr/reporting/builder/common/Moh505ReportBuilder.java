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
import org.openmrs.module.kenyaemr.reporting.library.MOH505.Moh505IndicatorLibrary;
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
 * MOH 505 report
 */
@Component
@Builds({"kenyaemr.ehrReports.report.moh505"})
public class Moh505ReportBuilder extends AbstractReportBuilder {
    protected static final Log log = LogFactory.getLog(Moh505ReportBuilder.class);
    static final int ACUTE_MALNUTRITION = 160205;
    static final int POLIOMYELITIS = 5258;
    static final int ANTHRAX = 121555;
    static final int CHOLERA = 2002391;
    static final int DENGUE = 142592;
    static final int DYSENTERY = 2014827;
    static final int GUINEA_WORM_DISEASE = 164180;
    static final int MEASLES = 134561;
    static final int SUSPECTED_MALARIA = 166623;
    static final int MENINGOCOCCAL_MENINGITIS = 134369;
    static final int NEONATAL_TETANUS = 124954;
    static final int PLAGUE = 114120;
    static final int RABIES = 160146;
    static final int RIFT_VALLEY_FEVER = 113217;
    static final int SUSPECTED_MDR = 160039;
    static final int TYPHOID = 141;
    static final int YELLOW_FEVER = 122759;

    @Autowired
    private CommonDimensionLibrary commonDimensions;
    @Autowired
    private Moh505IndicatorLibrary Moh505Indicator;
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
                ReportUtils.map(createMoh505DataSet(), indParams)
        );
    }


    ColumnParameters a0_5 = new ColumnParameters(null, "0-5 years", "age=0-5");
    ColumnParameters a5plus = new ColumnParameters(null, "5+ years", "age=5+");

    List<ColumnParameters> all_epidemic = Arrays.asList(a0_5, a5plus);

    private DataSetDefinition createMoh505DataSet() {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName("MOH505");
        dsd.setDescription("MOH 505 IDSR Weekly Epidemic Monitoring");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addDimension("age", map(commonDimensions.moh505AgeGroups(), "onDate=${endDate}"));

        EmrReportingUtils.addRow(dsd,"AEFI", "No of AEFI Cases", ReportUtils.map(Moh505Indicator.acuteJaundiceCase(), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Acute Jaundice", "No of Acute Jaundice Cases", ReportUtils.map(Moh505Indicator.acuteJaundiceCase(), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Acute Malnutrition", "No of Acute Malnutrition Cases", ReportUtils.map(Moh505Indicator.acuteMalnutritionCase(ACUTE_MALNUTRITION), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"AFP (Poliomyelitis)", "No of AFP (Poliomyelitis) Cases", ReportUtils.map(Moh505Indicator.poliomyelitisCase(POLIOMYELITIS), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Anthrax", "No of Anthrax Cases", ReportUtils.map(Moh505Indicator.anthraxCase(ANTHRAX), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Cholera", "No of Cholera Cases", ReportUtils.map(Moh505Indicator.choleraCase(CHOLERA), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Dengue", "No of Dengue Cases", ReportUtils.map(Moh505Indicator.dengueCase(DENGUE), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Dysentery (Bacillary)", "No of Dysentery (Bacillary) Cases", ReportUtils.map(Moh505Indicator.dysenteryCase(DYSENTERY), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Guinea Worm Disease", "No of Guinea Worm Disease Cases", ReportUtils.map(Moh505Indicator.guineaWormDiseaseCase(GUINEA_WORM_DISEASE), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Measles", "No of Measles Cases", ReportUtils.map(Moh505Indicator.measlesCase(MEASLES), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Suspected Malaria", "No of Suspected Malaria Cases", ReportUtils.map(Moh505Indicator.suspectedMalariaCase(SUSPECTED_MALARIA), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Deaths due to Malaria", "No of Deaths due to Malaria Cases", ReportUtils.map(Moh505Indicator.acuteJaundiceCase(), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Maternal deaths", "No of Maternal deaths", ReportUtils.map(Moh505Indicator.acuteJaundiceCase(), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Meningococcal Meningitis", "No of Meningococcal Meningitis cases", ReportUtils.map(Moh505Indicator.meningococcalMeningitisCase(MENINGOCOCCAL_MENINGITIS), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Neonatal deaths", "No of Neonatal deaths cases", ReportUtils.map(Moh505Indicator.acuteJaundiceCase(), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Neonatal Tetanus", "No of Neonatal Tetanus cases", ReportUtils.map(Moh505Indicator.neonatalTetanusCase(NEONATAL_TETANUS), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Plague", "No of Plague cases", ReportUtils.map(Moh505Indicator.plagueCase(PLAGUE), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Rabies", "No of Rabies cases", ReportUtils.map(Moh505Indicator.rabiesCase(RABIES), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Rift Valley Fever", "No of Rift Valley Fever cases", ReportUtils.map(Moh505Indicator.riftValleyFeverCase(RIFT_VALLEY_FEVER), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"SARI (Cluster ≥3 cases)", "No of SARI (Cluster ≥3 cases) cases", ReportUtils.map(Moh505Indicator.acuteJaundiceCase(), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Suspected MDR/XDR TB", "No of Suspected MDR/XDR TB cases", ReportUtils.map(Moh505Indicator.suspectedTBCase(SUSPECTED_MDR), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Typhoid ", "No of Typhoid cases", ReportUtils.map(Moh505Indicator.typhoidCase(TYPHOID), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"VHF", "No of VHF cases", ReportUtils.map(Moh505Indicator.acuteJaundiceCase(), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Yellow Fever", "No of Yellow Fever cases", ReportUtils.map(Moh505Indicator.yellowFeverCase(YELLOW_FEVER), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Others (Specify)", "No of Others (Specify) cases", ReportUtils.map(Moh505Indicator.acuteJaundiceCase(), indParams), all_epidemic, Arrays.asList("01", "02"));

        return dsd;
    }


}