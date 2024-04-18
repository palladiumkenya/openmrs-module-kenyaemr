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

import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.report.HybridReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractHybridReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
import org.openmrs.module.kenyaemr.Metadata;
import org.openmrs.module.kenyaemr.calculation.library.TelephoneNumberCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.EncounterDatetimeConverter;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.TimeQualifier;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.converter.ObsValueConverter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.EncountersForPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.SqlPatientDataDefinition;
import org.openmrs.module.reporting.data.person.definition.AgeDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.data.person.definition.ConvertedPersonDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.data.person.definition.ObsForPersonDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonAttributeDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonIdDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PreferredNameDataDefinition;
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
@Builds({ "kenyaemr.ehrReports.report.240" })
public class SetupMOH240LabReportBuilder extends AbstractHybridReportBuilder {

    @Override
    protected Mapped<CohortDefinition> buildCohort(HybridReportDescriptor hybridReportDescriptor,
                                                   PatientDataSetDefinition patientDataSetDefinition) {
        return null;
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
        PatientDataSetDefinition dsd = LabRegister();
        dsd.setName("lrr");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        report.setBaseCohortDefinition(ReportUtils.map(getLabOrderEncounter(), "startDate=${startDate},endDate=${endDate}"));

        return Arrays.asList(ReportUtils.map((DataSetDefinition) dsd, "startDate=${startDate},endDate=${endDate}"));
    }

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
                Date.class), new Parameter("dateBasedReporting", "", String.class));
    }

    private PatientDataSetDefinition LabRegister() {
        PatientDataSetDefinition dsd = new PatientDataSetDefinition();
        dsd.setName("lrr");
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addRowFilter(getLabOrderEncounter(), "startDate=${startDate},endDate=${endDate+23h}");
        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName}, {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);

        PersonAttributeType personAttributeType = Context.getPersonService().getPersonAttributeTypeByUuid(CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);

        PatientIdentifierType openmrsID = Context.getPatientService().getPatientIdentifierTypeByUuid("dfacd928-0370-4315-99d7-6ec1c9f7ae76");
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
                openmrsID.getName(), openmrsID), identifierFormatter);

        dsd.addColumn("id", new PersonIdDataDefinition(), "");
        dsd.addColumn("identifier", identifierDef, "");
        dsd.addColumn("Date", getEncounterDate(), "onOrAfter=${startDate},onOrBefore=${endDate+23h}",
                new EncounterDatetimeConverter());
        dsd.addColumn("Name", nameDef, "");
        dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
        dsd.addColumn("DOB", new BirthdateDataDefinition(), "", new BirthdateConverter("yyyy-MM-dd"));
        dsd.addColumn("age", new AgeDataDefinition(), "");
        dsd.addColumn("village", getAddress(), "", null);
        dsd.addColumn("telephone", new PersonAttributeDataDefinition(personAttributeType), "",
                null);
        return dsd;

    }


    private DataDefinition getEncounterDate() {
        EncounterType labEncounter = Context.getEncounterService().getEncounterTypeByUuid(
                "17a381d1-7e29-406a-b782-aa903b963c28");
        EncountersForPatientDataDefinition dsd = new EncountersForPatientDataDefinition();
        dsd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
        dsd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
        dsd.setTypes(Arrays.asList(labEncounter));
        dsd.setWhich(TimeQualifier.LAST);
        return dsd;
    }

    private CohortDefinition getLabOrderEncounter() {
        EncounterType labEncounter = Context.getEncounterService().getEncounterTypeByUuid(
                "17a381d1-7e29-406a-b782-aa903b963c28");
        SqlCohortDefinition sqlEncounterQuery = new SqlCohortDefinition();
        sqlEncounterQuery.setName("Get unique lab encounter types");
        sqlEncounterQuery.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sqlEncounterQuery.addParameter(new Parameter("endDate", "End Date", Date.class));
        sqlEncounterQuery
                .setQuery("SELECT p.patient_id FROM patient p INNER JOIN  encounter e ON p.patient_id=e.patient_id "
                        + " WHERE e.encounter_datetime BETWEEN :startDate AND :endDate AND e.voided=0 AND p.voided = 0 "
                        + " AND e.encounter_type IN(" + labEncounter.getEncounterTypeId() + ")");
        return sqlEncounterQuery;
    }

    public static DataDefinition getAddress() {
        SqlPatientDataDefinition sqlPdfn = new SqlPatientDataDefinition();
        sqlPdfn.setName("Address");
        sqlPdfn.setQuery("SELECT p.person_id, REPLACE(CONCAT_WS(' ',pa.address1,pa.address2,pa.city_village,pa.address3,pa.address5,pa.address6), ',','') AS address FROM person p "
                + " INNER JOIN person_address pa ON p.person_id=pa.person_id ");
        return sqlPdfn;
    }
}