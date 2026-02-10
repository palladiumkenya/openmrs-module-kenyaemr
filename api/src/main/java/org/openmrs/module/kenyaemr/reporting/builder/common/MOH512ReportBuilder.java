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

import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAttributeType;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
import org.openmrs.module.kenyaemr.calculation.library.hiv.CountyAddressCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.SubCountyAddressCalculation;
import org.openmrs.module.kenyaemr.calculation.library.mchcs.PersonAddressCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.RDQACalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.FPRegisterCohortDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.CalculationResultConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.MFLCodeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.fp.*;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDatetimeDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.*;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.EncounterDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Builds({"kenyaemr.ehrReports.report.moh512"})
public class MOH512ReportBuilder extends AbstractReportBuilder {


    public static final String ENC_DATE_FORMAT = "yyyy/MM/dd";
    public static final String DATE_FORMAT ="dd/MM/yyyy";
    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(
                new Parameter("startDate", "Start Date", Date.class),
                new Parameter("endDate", "End Date", Date.class),
                new Parameter("dateBasedReporting", "", String.class)
        );
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor, ReportDefinition reportDefinition) {
        return Arrays.asList(ReportUtils.map(moh512Dataset(), "startDate=${startDate},endDate=${endDate}"));
    }
    protected DataSetDefinition moh512Dataset() {
        EncounterDataSetDefinition dsd = new EncounterDataSetDefinition();
        dsd.setName("MOH512");
        dsd.setDescription("MOH 512 Family Planning Register");
        dsd.addSortCriteria("Visit Date", SortCriteria.SortDirection.ASC);
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));

        String paramMapping = "startDate=${startDate},endDate=${endDate}";
        //Data definitions
        DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName} {middleName}");
        DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
        PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, HivMetadata._PatientIdentifierType.UNIQUE_PATIENT_NUMBER);
        PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
        DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
        DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(upn.getName(), upn), identifierFormatter);
        DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(nupi.getName(), nupi), identifierFormatter);
        PersonAttributeType phoneNumber = MetadataUtils.existing(PersonAttributeType.class, CommonMetadata._PersonAttributeType.TELEPHONE_CONTACT);

        ClientTypeDefinition clientTypeDefinition = new ClientTypeDefinition();
        clientTypeDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        clientTypeDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FirstEverUserOfContraceptiveDefinition firstEverUserOfContraceptiveDefinition =new FirstEverUserOfContraceptiveDefinition();
        firstEverUserOfContraceptiveDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        firstEverUserOfContraceptiveDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPCervicalCancerScreeningResultsDefinition fpCervicalCancerScreeningResultsDefinition =new FPCervicalCancerScreeningResultsDefinition();
        fpCervicalCancerScreeningResultsDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpCervicalCancerScreeningResultsDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPCervicalCancerScreeningYNDefinition fpCervicalCancerScreeningYNDefinition=new FPCervicalCancerScreeningYNDefinition();
        fpCervicalCancerScreeningYNDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpCervicalCancerScreeningYNDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPClientsRecievingPostpartumDefinition fpClientsRecievingPostpartumDefinition =new FPClientsRecievingPostpartumDefinition();
        fpClientsRecievingPostpartumDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpClientsRecievingPostpartumDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPEmergencyContraceptiveQtyDispDefinition fpEmergencyContraceptiveQtyDispDefinition =new FPEmergencyContraceptiveQtyDispDefinition();
        fpEmergencyContraceptiveQtyDispDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpEmergencyContraceptiveQtyDispDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPEmergencyContraceptivesGivenDefinition fpEmergencyContraceptivesGivenDefinition = new FPEmergencyContraceptivesGivenDefinition();
        fpEmergencyContraceptivesGivenDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpEmergencyContraceptivesGivenDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPFemalesCondomsQtyDispDefinition fpFemalesCondomsQtyDispDefinition =new FPFemalesCondomsQtyDispDefinition();
        fpFemalesCondomsQtyDispDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpFemalesCondomsQtyDispDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPHIVStatusCounseledDefinition fphivStatusCounseledDefinition =new FPHIVStatusCounseledDefinition();
        fphivStatusCounseledDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fphivStatusCounseledDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPHIVStatusResultsDefinition fphivStatusResultsDefinition = new FPHIVStatusResultsDefinition();
        fphivStatusResultsDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fphivStatusResultsDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FpImplantsQtyDispDefinition fpImplantsQtyDispDefinition =new FpImplantsQtyDispDefinition();
        fpImplantsQtyDispDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpImplantsQtyDispDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPImplantsRodsDefinition fpImplantsRodsDefinition = new FPImplantsRodsDefinition();
        fpImplantsRodsDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpImplantsRodsDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPImplantsTypeOfVisitDefinition fpImplantsTypeOfVisitDefinition =new FPImplantsTypeOfVisitDefinition();
        fpImplantsTypeOfVisitDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpImplantsTypeOfVisitDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPInjectablesIMorSCDefinition fpInjectablesIMorSCDefinition =new FPInjectablesIMorSCDefinition();
        fpInjectablesIMorSCDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpInjectablesIMorSCDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPInjectablesQtyDispDefinition fpInjectablesQtyDispDefinition = new FPInjectablesQtyDispDefinition();
        fpInjectablesQtyDispDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpInjectablesQtyDispDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPInjectablesTypeOfVisitDefinition fpInjectablesTypeOfVisitDefinition =new FPInjectablesTypeOfVisitDefinition();
        fpInjectablesTypeOfVisitDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpInjectablesTypeOfVisitDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPIntimatePartnerViolenceDefinition fpIntimatePartnerViolenceDefinition=new FPIntimatePartnerViolenceDefinition();
        fpIntimatePartnerViolenceDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpIntimatePartnerViolenceDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPIUCDNonHomornalDefinition fpiucdNonHomornalDefinition = new FPIUCDNonHomornalDefinition();
        fpiucdNonHomornalDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpiucdNonHomornalDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPIUCDQtyDispDefinition fpiucdQtyDispDefinition =new FPIUCDQtyDispDefinition();
        fpiucdQtyDispDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpiucdQtyDispDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPIUCDTypeOfVisitDefinition fpiucdTypeOfVisitDefinition=new FPIUCDTypeOfVisitDefinition();
        fpiucdTypeOfVisitDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpiucdTypeOfVisitDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPMalesCondomsQtyDispDefinition fpMalesCondomsQtyDispDefinition=new FPMalesCondomsQtyDispDefinition();
        fpMalesCondomsQtyDispDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpMalesCondomsQtyDispDefinition.addParameter(new Parameter("endDate", "end Date", Date.class));

        FPNaturalMethodCounselledDefinition fpNaturalMethodCounselledDefinition =new FPNaturalMethodCounselledDefinition();
        fpNaturalMethodCounselledDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpNaturalMethodCounselledDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPProgestinQtyDispDefinition fpProgestinQtyDispDefinition =new FPProgestinQtyDispDefinition();
        fpProgestinQtyDispDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpProgestinQtyDispDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPProgestinTypeOfVisitDefinition fpProgestinTypeOfVisitDefinition =new FPProgestinTypeOfVisitDefinition();
        fpProgestinTypeOfVisitDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpProgestinTypeOfVisitDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPQtyDispDefinition fpQtyDispDefinition=new FPQtyDispDefinition();
        fpQtyDispDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpQtyDispDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPReasonsForReferralDefinition fpReasonsForReferralDefinition =new FPReasonsForReferralDefinition();
        fpReasonsForReferralDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpReasonsForReferralDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPReferralToDefinition fpReferralToDefinition=new FPReferralToDefinition();
        fpReferralToDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpReferralToDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPRefferalFromDefinition fpRefferalFromDefinition =new FPRefferalFromDefinition();
        fpRefferalFromDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpRefferalFromDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPRemarksDefinition fpRemarksDefinition =new FPRemarksDefinition();
        fpRemarksDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpRemarksDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPTBScreeningDefinition fptbScreeningDefinition=new FPTBScreeningDefinition();
        fptbScreeningDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fptbScreeningDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPTypeOfVisitOralContraceptiveDefinition fpTypeOfVisitOralContraceptiveDefinition=new FPTypeOfVisitOralContraceptiveDefinition();
        fpTypeOfVisitOralContraceptiveDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpTypeOfVisitOralContraceptiveDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPVSCDefinition fpvscDefinition =new FPVSCDefinition();
        fpvscDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpvscDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPCondomsforFPClientsDefinition fpCondomsforFPClientsDefinition =new FPCondomsforFPClientsDefinition();
        fpCondomsforFPClientsDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpCondomsforFPClientsDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPCycleBeadsGivenDefinition fpCycleBeadsGivenDefinition =new FPCycleBeadsGivenDefinition();
        fpCycleBeadsGivenDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpCycleBeadsGivenDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        FPReasonsforLARCDefinition fpReasonsforLARCDefinition =new FPReasonsforLARCDefinition();
        fpReasonsforLARCDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        fpReasonsforLARCDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

        dsd.addColumn("Date", new EncounterDatetimeDataDefinition(), "", new DateConverter(ENC_DATE_FORMAT));
        dsd.addColumn("ClientNumber", identifierDef, null);
        dsd.addColumn("Nameofclient", nameDef, "");
        dsd.addColumn("ClientType", clientTypeDefinition, paramMapping);
        dsd.addColumn("1steveruserofcontraceptiveYorN", firstEverUserOfContraceptiveDefinition, paramMapping);
        dsd.addColumn("Age", new AgeDataDefinition(), "");
        dsd.addColumn("Sex", new GenderDataDefinition(), "");
        dsd.addColumn("TelephoneNo", new PersonAttributeDataDefinition(phoneNumber), "");
        dsd.addColumn("County",new CalculationDataDefinition("County", new CountyAddressCalculation()), "",new CalculationResultConverter());
        //dsd.addColumn("Sub County", new CalculationDataDefinition("Subcounty", new SubCountyAddressCalculation()), "",new CalculationResultConverter());
        dsd.addColumn("VillageEstateLandmark", new CalculationDataDefinition("Village", new PersonAddressCalculation()), "",new RDQACalculationResultConverter());

        dsd.addColumn("TypeofvisitformethodOral", fpTypeOfVisitOralContraceptiveDefinition,paramMapping);
        dsd.addColumn("QtyDisporal", fpQtyDispDefinition, paramMapping);
        //dsd.addColumn("Type of visit for method:NR", fpTypeOfVisitOralContraceptiveDefinition, paramMapping);
        //dsd.addColumn("Qty Disp.oral", fpQtyDispDefinition, paramMapping);
        dsd.addColumn("Typeovisitformethodprogestin", fpProgestinTypeOfVisitDefinition, paramMapping);
        dsd.addColumn("QtyDispprogestin", fpProgestinQtyDispDefinition, paramMapping);
        dsd.addColumn("TypeofvisitformethodInjectables", fpInjectablesTypeOfVisitDefinition, paramMapping);
        dsd.addColumn("IMorSCInjectables", fpInjectablesIMorSCDefinition, paramMapping);
        dsd.addColumn("QtyDispInjectables", fpInjectablesQtyDispDefinition, paramMapping);
        dsd.addColumn("TypeofvisitformethodImplants", fpImplantsTypeOfVisitDefinition, paramMapping);
        dsd.addColumn("RodsImplants", fpImplantsRodsDefinition, paramMapping);
        dsd.addColumn("QtyDispImplants", fpImplantsQtyDispDefinition, paramMapping);
        dsd.addColumn("EmergencyContraceptivesGivenYorN", fpEmergencyContraceptivesGivenDefinition, paramMapping);
        dsd.addColumn("EmergencyContraceptivesQtyDisp", fpEmergencyContraceptiveQtyDispDefinition, paramMapping);
        dsd.addColumn("TypeofvisitformethodIUCDs", fpiucdTypeOfVisitDefinition, paramMapping);;
        dsd.addColumn("NonHormonalIUCDs", fpiucdNonHomornalDefinition, paramMapping);
        dsd.addColumn("QtyDispIUCDs", fpiucdQtyDispDefinition, paramMapping);
        dsd.addColumn("CondomsforFPclients", fpCondomsforFPClientsDefinition, paramMapping);
        dsd.addColumn("MalecondomsQty", fpMalesCondomsQtyDispDefinition, paramMapping);
        dsd.addColumn("FemalescondomsQty", fpFemalesCondomsQtyDispDefinition, paramMapping);
        dsd.addColumn("VSCvasectomy", fpvscDefinition, paramMapping);
        dsd.addColumn("NaturalFPcounselled", fpNaturalMethodCounselledDefinition, paramMapping);
        dsd.addColumn("CycleBeadsGivenYorN", fpCycleBeadsGivenDefinition, paramMapping);
        dsd.addColumn("ClientsrecievingPostpartumFP", fpClientsRecievingPostpartumDefinition, paramMapping);
        dsd.addColumn("TBScreening", fptbScreeningDefinition, paramMapping);
        dsd.addColumn("HIVStatuscounseled", fphivStatusCounseledDefinition, paramMapping);
        dsd.addColumn("HIVStatusResults", fphivStatusResultsDefinition, paramMapping);
        dsd.addColumn("ReasonsforLARC", fpReasonsforLARCDefinition, paramMapping);
        dsd.addColumn("IntimateParterViolenceReprocuctive", fpIntimatePartnerViolenceDefinition, paramMapping);
        dsd.addColumn("CervicalCancerScreening", fpCervicalCancerScreeningYNDefinition, paramMapping);
        dsd.addColumn("CervicalCancerScreeningResults", fpCervicalCancerScreeningResultsDefinition, paramMapping);
        dsd.addColumn("Referralsfrom", fpRefferalFromDefinition, paramMapping);
        dsd.addColumn("Referralsto", fpReferralToDefinition, paramMapping);
        dsd.addColumn("Referralreasons", fpReasonsForReferralDefinition, paramMapping);
        dsd.addColumn("Remarks", fpRemarksDefinition, paramMapping);




        // Add row filter for FP
        FPRegisterCohortDefinition cd = new FPRegisterCohortDefinition();
        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.addRowFilter(cd, paramMapping);

        return dsd;
    }
}
