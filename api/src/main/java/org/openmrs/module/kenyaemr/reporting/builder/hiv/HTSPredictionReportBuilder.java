/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.builder.hiv;
import org.openmrs.PatientIdentifierType;
import org.openmrs.module.kenyacore.report.CohortReportDescriptor;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyacore.report.builder.CalculationReportBuilder;
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.DateOfEnrollmentArtCalculation;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.DateArtStartDateConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.IdentifierConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLArtStartDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLCurrentRegLineDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLCurrentRegimenDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLFirstRegimenDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLLastVisitDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLNextAppointmentDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLStabilityDataDefinition;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.springframework.stereotype.Component;

/**
 * Report builder for ETL MOH 731
 */
@Component
@Builds({"kenyaemr.etl.common.report.htsprediction"})
// public class HTSPredictionReportBuilder extends AbstractReportBuilder {
public class HTSPredictionReportBuilder extends CalculationReportBuilder {
        public static final String DATE_FORMAT = "dd/MM/yyyy";

        @Override
        protected void addColumns(CohortReportDescriptor report, PatientDataSetDefinition dsd) {
                PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
                        HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
                DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                        upn.getName(), upn), new IdentifierConverter());
                
                addStandardColumns(report, dsd);
                dsd.addColumn("UPN", identifierDef, "");
                // dsd.addColumn("Last risk score", new CalculationDataDefinition("Last risk score (%)", new LastRiskScoreCalculation()), "", null);
                // dsd.addColumn("Evaluation Date", new CalculationDataDefinition("Evaluation Date", new LastRiskScoreEvaluationDateCalculation()), "", new CalculationResultConverter());
                dsd.addColumn("Enrollment Date", new CalculationDataDefinition("Enrollment Date", new DateOfEnrollmentArtCalculation()), "", new DateArtStartDateConverter());
                dsd.addColumn("Art Start Date", new ETLArtStartDateDataDefinition(), "", new DateConverter(DATE_FORMAT));
                dsd.addColumn("First Regimen", new ETLFirstRegimenDataDefinition(), "");
                dsd.addColumn("Current Regimen", new ETLCurrentRegimenDataDefinition(), "");
                dsd.addColumn("Current Regimen Line", new ETLCurrentRegLineDataDefinition(), "");
                dsd.addColumn("Stability", new ETLStabilityDataDefinition(), "");
                dsd.addColumn("Last Visit Date", new ETLLastVisitDateDataDefinition(), "", new DateConverter(DATE_FORMAT));
                dsd.addColumn("Next Appointment Date", new ETLNextAppointmentDateDataDefinition(), "",
                        new DateConverter(DATE_FORMAT));
                dsd.addSortCriteria("Last risk score", SortCriteria.SortDirection.DESC);
                
        }
}
