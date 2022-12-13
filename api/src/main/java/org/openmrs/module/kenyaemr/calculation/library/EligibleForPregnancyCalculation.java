package org.openmrs.module.kenyaemr.calculation.library;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.*;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.metadata.MchMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Calculates the recorded pregnancy status of patients
 */
public class EligibleForPregnancyCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
    protected static final Log log = LogFactory.getLog(EligibleForPregnancyCalculation.class);

    public static final EncounterType triageEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.TRIAGE);
    public static final Form triageScreeningForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.TRIAGE);

    @Override
    public String getFlagMessage() {
        return "Eligible for Pregnancy Test";
    }
    Integer SEXUAL_ABSTAINED = 160109;
    Integer LMP = 48;
    Integer FAMILY_PLANNING = 160653;
    Integer MISCARIAGE = 1657;
    Integer NEGATIVE = 1066;


    /**
     * Evaluates the calculation
     * @should calculate null for deceased patients
     * @should calculate null for patients with no recorded status
     * @should calculate last recorded pregnancy status for all patients
     */

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
        Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
        Program mchcsProgram = MetadataUtils.existing(Program.class, MchMetadata._Program.MCHCS);
        Set<Integer> aliveAndFemale = Filters.female(Filters.alive(cohort, context), context);
        PatientService patientService = Context.getPatientService();
        Set<Integer> alive = Filters.alive(cohort, context);
        CalculationResultMap ret = new CalculationResultMap();
        Set<Integer> inHivProgram = Filters.inProgram(hivProgram, alive, context);
        Set<Integer> inMchcsProgram = Filters.inProgram(mchcsProgram, alive, context);

        EncounterType mchEnrollment = MetadataUtils.existing(EncounterType.class, MchMetadata._EncounterType.MCHMS_ENROLLMENT);
        EncounterType mchDiscontinuation = MetadataUtils.existing(EncounterType.class, MchMetadata._EncounterType.MCHMS_DISCONTINUATION);

        CalculationResultMap enrollmentMap = Calculations.lastEncounter(mchEnrollment, aliveAndFemale, context);
        CalculationResultMap discontinuation = Calculations.lastEncounter(mchDiscontinuation, aliveAndFemale, context);

        for (Integer ptId :aliveAndFemale) {
            boolean result = false;
            Patient patient = patientService.getPatient(ptId);
            Form antenatalVisitForm = MetadataUtils.existing(Form.class, MchMetadata._Form.MCHMS_ANTENATAL_VISIT);
            Form matVisitForm = MetadataUtils.existing(Form.class, MchMetadata._Form.MCHMS_DELIVERY);
            Form pncVisitForm = MetadataUtils.existing(Form.class, MchMetadata._Form.MCHMS_POSTNATAL_VISIT);
            EncounterType mchConsultationEncounterType = MetadataUtils.existing(EncounterType.class, MchMetadata._EncounterType.MCHMS_CONSULTATION);
            Encounter lastANCHtsEnc = EmrUtils.lastEncounter(patient,mchConsultationEncounterType,antenatalVisitForm );
            Encounter lastMatHtsEnc = EmrUtils.lastEncounter(patient,mchConsultationEncounterType,matVisitForm );
            Encounter lastPNCHtsEnc = EmrUtils.lastEncounter(patient,mchConsultationEncounterType,pncVisitForm );
            Encounter lastTriageTestingEnc = EmrUtils.lastEncounter(patient, triageEncType, triageScreeningForm);
            ConceptService cs = Context.getConceptService();
            Concept sexualAbstainedResult = cs.getConcept(SEXUAL_ABSTAINED);
            Concept lmpResult = cs.getConcept(LMP);
            Concept familyPlanningResult = cs.getConcept(FAMILY_PLANNING);
            Concept miscariageResult = cs.getConcept(MISCARIAGE);
            Concept negative = cs.getConcept(NEGATIVE);
            boolean patientSexualAbstainedTestResult = lastTriageTestingEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageTestingEnc, sexualAbstainedResult, negative) : false;
            boolean pantientLmpResult = lastTriageTestingEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageTestingEnc, lmpResult, negative) : false;
            boolean patientFamilyPlanningResult = lastTriageTestingEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageTestingEnc, familyPlanningResult, negative) : false;
            boolean patientMiscariageResultTestResult = lastTriageTestingEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastTriageTestingEnc, miscariageResult, negative) : false;
            Encounter encounter = EmrCalculationUtils.encounterResultForPatient(enrollmentMap, ptId);
            Encounter encounterDiscontinuation = EmrCalculationUtils.encounterResultForPatient(discontinuation, ptId);
            if(lastTriageTestingEnc != null && patientSexualAbstainedTestResult){
                result  = true;
            }
            if(lastTriageTestingEnc != null && pantientLmpResult){
                result  = true;
            }
            if(lastTriageTestingEnc != null && patientFamilyPlanningResult){
                result  = true;
            }
            if(lastTriageTestingEnc != null && patientMiscariageResultTestResult){
                result  = true;
            }
            if (inHivProgram.contains(ptId)) {
                result = true;
            }
            if(inMchcsProgram.contains(ptId) && lastPNCHtsEnc == null && lastMatHtsEnc == null && lastANCHtsEnc == null ){
                result = true;
            }
            if(encounter != null && encounterDiscontinuation != null && encounterDiscontinuation.getEncounterDatetime().after(encounter.getEncounterDatetime())){
                result = true;
            }
            ret.put(ptId, new BooleanResult(result, this));
        }

        return ret;
    }
}
