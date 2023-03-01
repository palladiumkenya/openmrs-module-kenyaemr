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
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.data.converter.IdentifierConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLLastVLDateForMLDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLLastVLResultForMLDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLPredictionCategoryDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.art.ETLPredictionScoreDataDefinition;
import org.openmrs.module.metadatadeploy.MetadataUtils;
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
                PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
                DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(upn.getName(), upn), new IdentifierConverter());
                
                addStandardColumns(report, dsd);
                dsd.addColumn("UPN", identifierDef, "");
                dsd.addColumn("ML Prediction Score", new ETLPredictionScoreDataDefinition(), "");
                dsd.addColumn("ML Prediction Category", new ETLPredictionCategoryDataDefinition(), "");
                ETLLastVLDateForMLDataDefinition lastVlDateDataDefinition = new ETLLastVLDateForMLDataDefinition();
                ETLLastVLResultForMLDataDefinition lastVlResultDataDefinition = new ETLLastVLResultForMLDataDefinition();
                dsd.addColumn("Date Of Test", lastVlDateDataDefinition, "", new DateConverter(DATE_FORMAT));
                dsd.addColumn("Actual Test Results", lastVlResultDataDefinition, "");
        }
}
