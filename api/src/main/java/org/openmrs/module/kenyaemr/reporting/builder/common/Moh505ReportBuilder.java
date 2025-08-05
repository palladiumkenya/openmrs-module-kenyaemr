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
    static final int GUINEA_WORM_DISEASE = 137458;
    static final int MEASLES = 134561;
    static final int SUSPECTED_MALARIA = 166623;
    static final int MENINGOCOCCAL_MENINGITIS = 134369;
    static final int NEONATAL_TETANUS = 124954;
    static final int NEONATAL_DEATH = 154223;
    static final int PLAGUE = 114120;
    static final int RABIES = 160146;
    static final int RIFT_VALLEY_FEVER = 113217;
    static final int SUSPECTED_MDR = 160039;
    static final int TYPHOID = 141;
    static final int YELLOW_FEVER = 122759;
    static final int RAPID_TEST_FOR_MALARIA = 1643;
    static final int TYPHOID_TEST = 165562;
    static final int TB_TEST = 5475;
    static final int STOOL_CULTURE = 163603;
    static final int VIRAL_HAEMORRHAGIC_FEVER = 123112;
    static final int INFECTION_FOLLOWING_IMMUNIZATION = 151956;
    static final int DECEASED = 159;
    static final int BACTERIAL_MENINGITIS = 121255;
    static final int OTHER_TEST_PERFORMED = 165398; //Other test performed
    static final int CEREBROSPINAL_FLUID_MICROSCOPY = 159649; //cerebrospinal fluid microscopy
    static final int CEREBROSPINAL_FLUID_BACTERIAL_CULTURE = 159648; //cerebrospinal fluid bacterial culture


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
    ColumnParameters colTotal = new ColumnParameters(null, "Total", "");

    List<ColumnParameters> all_epidemic = Arrays.asList(a0_5, a5plus, colTotal);

    private DataSetDefinition createMoh505DataSet() {
        log.info("Initializing MOH 505 DataSetDefinition");
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName("MOH505");
        dsd.setDescription("MOH 505 IDSR Weekly Epidemic Monitoring");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addDimension("age", map(commonDimensions.moh505AgeGroups(), "onDate=${endDate}"));

        EmrReportingUtils.addRow(dsd,"AEFI", "No of AEFI Cases", ReportUtils.map(Moh505Indicator.diagnosis(INFECTION_FOLLOWING_IMMUNIZATION), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"AEFI Deaths", "No of AEFI Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(INFECTION_FOLLOWING_IMMUNIZATION, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Acute Jaundice", "No of Acute Jaundice Cases", ReportUtils.map(Moh505Indicator.acuteJaundiceCase(), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Acute Jaundice Deaths", "No of Acute Jaundice Deaths", ReportUtils.map(Moh505Indicator.acuteJaundicePatientDeceased(DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Acute Malnutrition", "No of Acute Malnutrition Cases", ReportUtils.map(Moh505Indicator.diagnosis(ACUTE_MALNUTRITION), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Acute Malnutrition Deaths", "No of Acute Malnutrition Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(ACUTE_MALNUTRITION, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"AFP (Poliomyelitis)", "No of AFP (Poliomyelitis) Cases", ReportUtils.map(Moh505Indicator.diagnosis(POLIOMYELITIS), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"AFP (Poliomyelitis) Deaths", "No of AFP (Poliomyelitis) Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(POLIOMYELITIS, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Anthrax", "No of Anthrax Cases", ReportUtils.map(Moh505Indicator.diagnosis(ANTHRAX), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Anthrax Deaths", "No of Anthrax Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(ANTHRAX, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Cholera", "No of Cholera Cases", ReportUtils.map(Moh505Indicator.diagnosis(CHOLERA), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Cholera Deaths", "No of Cholera Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(CHOLERA, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Dengue", "No of Dengue Cases", ReportUtils.map(Moh505Indicator.diagnosis(DENGUE), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Dengue Deaths", "No of Dengue Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(DENGUE, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Dysentery (Bacillary)", "No of Dysentery (Bacillary) Cases", ReportUtils.map(Moh505Indicator.diagnosis(DYSENTERY), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Dysentery (Bacillary) Deaths", "No of Dysentery (Bacillary) Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(DYSENTERY, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Guinea Worm Disease", "No of Guinea Worm Disease Cases", ReportUtils.map(Moh505Indicator.diagnosis(GUINEA_WORM_DISEASE), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Guinea Worm Disease Deaths", "No of Guinea Worm Disease Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(GUINEA_WORM_DISEASE, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Measles", "No of Measles Cases", ReportUtils.map(Moh505Indicator.diagnosis(MEASLES), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Measles Deaths", "No of Measles Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(MEASLES, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Suspected Malaria", "No of Suspected Malaria Cases", ReportUtils.map(Moh505Indicator.diagnosis(SUSPECTED_MALARIA), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Suspected Malaria Deaths", "No of Suspected Malaria Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(SUSPECTED_MALARIA, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Deaths due to Malaria", "No of Deaths due to Malaria Cases", ReportUtils.map(Moh505Indicator.acuteJaundiceCase(), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Maternal deaths", "No of Maternal deaths", ReportUtils.map(Moh505Indicator.maternalDeathCase(), indParams), all_epidemic, Arrays.asList("02"));
        EmrReportingUtils.addRow(dsd,"Meningococcal Meningitis", "No of Meningococcal Meningitis cases", ReportUtils.map(Moh505Indicator.diagnosis(MENINGOCOCCAL_MENINGITIS), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Meningococcal Meningitis Deaths", "No of Meningococcal Meningitis Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(MENINGOCOCCAL_MENINGITIS, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Neonatal deaths", "No of Neonatal deaths cases", ReportUtils.map(Moh505Indicator.diagnosis(NEONATAL_DEATH), indParams), all_epidemic, Arrays.asList("01"));
        EmrReportingUtils.addRow(dsd,"Neonatal Tetanus", "No of Neonatal Tetanus cases", ReportUtils.map(Moh505Indicator.diagnosis(NEONATAL_TETANUS), indParams), all_epidemic, Arrays.asList("01"));
        EmrReportingUtils.addRow(dsd,"Neonatal Tetanus Deaths", "No of Neonatal Tetanus Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(NEONATAL_TETANUS, DECEASED), indParams), all_epidemic, Arrays.asList("01"));
        EmrReportingUtils.addRow(dsd,"Plague", "No of Plague cases", ReportUtils.map(Moh505Indicator.diagnosis(PLAGUE), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Plague Deaths", "No of Plague Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(PLAGUE, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Rabies", "No of Rabies cases", ReportUtils.map(Moh505Indicator.diagnosis(RABIES), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Rabies Deaths", "No of Rabies Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(RABIES, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Rift Valley Fever", "No of Rift Valley Fever cases", ReportUtils.map(Moh505Indicator.diagnosis(RIFT_VALLEY_FEVER), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Rift Valley Fever Deaths", "No of Rift Valley Fever Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(RIFT_VALLEY_FEVER, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"SARI (Cluster ≥3 cases)", "No of SARI (Cluster ≥3 cases) cases", ReportUtils.map(Moh505Indicator.sariPatientCases(), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"SARI (Cluster ≥3 deaths)", "No of SARI (Cluster ≥3 deaths) deaths", ReportUtils.map(Moh505Indicator.sariPatientDeceased(DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Suspected MDR/XDR TB", "No of Suspected MDR/XDR TB cases", ReportUtils.map(Moh505Indicator.diagnosis(SUSPECTED_MDR), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Suspected MDR/XDR TB Deaths", "No of Suspected MDR/XDR TB Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(SUSPECTED_MDR, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Typhoid", "No of Typhoid cases", ReportUtils.map(Moh505Indicator.diagnosis(TYPHOID), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Typhoid Deaths", "No of Typhoid Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(TYPHOID, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"VHF", "No of VHF cases", ReportUtils.map(Moh505Indicator.diagnosis(VIRAL_HAEMORRHAGIC_FEVER), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"VHF Deaths", "No of VHF Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(VIRAL_HAEMORRHAGIC_FEVER, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Yellow Fever", "No of Yellow Fever cases", ReportUtils.map(Moh505Indicator.diagnosis(YELLOW_FEVER), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Yellow Fever Deaths", "No of Yellow Fever Deaths", ReportUtils.map(Moh505Indicator.patientDiagnosedAndDeceased(YELLOW_FEVER, DECEASED), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Others (Specify)", "No of Others (Specify) cases", ReportUtils.map(Moh505Indicator.acuteJaundiceCase(), indParams), all_epidemic, Arrays.asList("01", "02"));

        EmrReportingUtils.addRow(dsd,"Microscopy Tested", "Malaria Tested (Microscopy)", ReportUtils.map(Moh505Indicator.malariaTested(SUSPECTED_MALARIA, CEREBROSPINAL_FLUID_MICROSCOPY), indParams), all_epidemic, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"Microscopy Positive", "Malaria Positive (Microscopy)", ReportUtils.map(Moh505Indicator.malariaTestedPositive(SUSPECTED_MALARIA, CEREBROSPINAL_FLUID_MICROSCOPY), indParams), all_epidemic, Arrays.asList("01", "02", "03"));
        EmrReportingUtils.addRow(dsd,"mRDT Tested", "Malaria Tested (mRDT)", ReportUtils.map(Moh505Indicator.malariaTested(SUSPECTED_MALARIA, RAPID_TEST_FOR_MALARIA), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"mRDT Positive", "Malaria Positive (mRDT)", ReportUtils.map(Moh505Indicator.malariaTestedPositive(SUSPECTED_MALARIA, RAPID_TEST_FOR_MALARIA), indParams), all_epidemic, Arrays.asList("01", "02"));

        EmrReportingUtils.addRow(dsd,"Tubercullosis Tested", "Tubercullosis Tested", ReportUtils.map(Moh505Indicator.tbTested(TB_TEST), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Tubercullosis Positive", "Tubercullosis Positive", ReportUtils.map(Moh505Indicator.tbTestedPositive(TB_TEST), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Typhoid Tested", "Typhoid Tested", ReportUtils.map(Moh505Indicator.typhoidTested(TYPHOID_TEST), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Typhoid Positive", "Typhoid Positive", ReportUtils.map(Moh505Indicator.typhoidTestedPositive(TYPHOID_TEST), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Shigella Dysentry Tested", "Shigella Dysentry Tested", ReportUtils.map(Moh505Indicator.dysentryTested(STOOL_CULTURE), indParams), all_epidemic, Arrays.asList("01", "02"));
        EmrReportingUtils.addRow(dsd,"Shigella Dysentry Positive", "Shigella Dysentry Positive", ReportUtils.map(Moh505Indicator.dysentryTestedPositive(STOOL_CULTURE), indParams), all_epidemic, Arrays.asList("01", "02"));

        EmrReportingUtils.addRow(dsd,"No CSF", "Bacterial Meningitis (No CSF)", ReportUtils.map(Moh505Indicator.bacterialTested(CEREBROSPINAL_FLUID_BACTERIAL_CULTURE), indParams), all_epidemic, Arrays.asList());
        EmrReportingUtils.addRow(dsd,"No contaminated", "Bacterial Meningitis (No contaminated)", ReportUtils.map(Moh505Indicator.bacterialTested(CEREBROSPINAL_FLUID_MICROSCOPY), indParams), all_epidemic, Arrays.asList());
        EmrReportingUtils.addRow(dsd,"No Tested", "Bacterial Meningitis (No Tested)", ReportUtils.map(Moh505Indicator.bacterialTested(OTHER_TEST_PERFORMED), indParams), all_epidemic, Arrays.asList());

        EmrReportingUtils.addRow(dsd,"No +ve Nm", "Bacterial Meningitis (No +ve Nm)", ReportUtils.map(Moh505Indicator.bacteriaTestedPositiveNM(CEREBROSPINAL_FLUID_MICROSCOPY, CEREBROSPINAL_FLUID_BACTERIAL_CULTURE), indParams), all_epidemic, Arrays.asList());
        EmrReportingUtils.addRow(dsd,"No +ve Sp", "Bacterial Meningitis (No +ve Sp)", ReportUtils.map(Moh505Indicator.bacteriaTestedPositiveSp(CEREBROSPINAL_FLUID_MICROSCOPY, CEREBROSPINAL_FLUID_BACTERIAL_CULTURE), indParams), all_epidemic, Arrays.asList());
        EmrReportingUtils.addRow(dsd,"No +ve H influenza", "Bacterial Meningitis (No +ve H influenza)", ReportUtils.map(Moh505Indicator.bacteriaTestedPositiveHInfluenza(CEREBROSPINAL_FLUID_BACTERIAL_CULTURE), indParams), all_epidemic, Arrays.asList());

        return dsd;
    }


}