package org.openmrs.module.kenyaemr.calculation.library;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.ConceptService;
import org.openmrs.api.EncounterService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.*;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.LastViralLoadResultCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.TransferInDateCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.metadata.MchMetadata;
import org.openmrs.module.kenyaemr.metadata.OVCMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class HighRiskClientCategorization extends AbstractPatientCalculation implements PatientFlagCalculation {
    protected static final Log log = LogFactory.getLog(HighRiskClientCategorization.class);
    @Autowired
    private EncounterService encounterService;
    @Override
    public String getFlagMessage() {
        return "High risk client categorization";
    }

    /**
     * Evaluates the calculation
     * @should calculate if the patient is in HIV program
     * @should calculate Adolecent Girls in OVC/DREAM
     * @should calculate patient in VL is >= 200
     * @should calculate patient in transfer in
     */

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
        Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
        Program ovcDreamProgram = MetadataUtils.existing(Program.class, OVCMetadata._Program.OVC);

        Set<Integer> alive = Filters.alive(cohort, context);
        PatientService patientService = Context.getPatientService();
        LastViralLoadResultCalculation lastVlResultCalculation = new LastViralLoadResultCalculation();
        CalculationResultMap lastVlResults = lastVlResultCalculation.evaluate(cohort, null, context);
        CalculationResultMap transferInDate = calculate(new TransferInDateCalculation(), cohort, context);

        CalculationResultMap ret = new CalculationResultMap();
        Set<Integer> inHivProgram = Filters.inProgram(hivProgram, alive, context);
        Set<Integer> inOvcDreamProgram = Filters.inProgram(ovcDreamProgram, alive, context);

        for (Integer ptId :alive) {
            boolean result = false;
            Patient patient = patientService.getPatient(ptId);
            Double lastVlResultValue = null;
            CalculationResult lastvlresult = lastVlResults.get(ptId);
            Date transferInDateValue = EmrCalculationUtils.datetimeResultForPatient(transferInDate, ptId);
            EncounterType mchEnrollment = MetadataUtils.existing(EncounterType.class, MchMetadata._EncounterType.MCHMS_ENROLLMENT);
            Form antenatalVisitForm = MetadataUtils.existing(Form.class, MchMetadata._Form.MCHMS_ANTENATAL_VISIT);
            Encounter mchMsEnc = EmrUtils.lastEncounter(patient,mchEnrollment,antenatalVisitForm );

            if (inHivProgram.contains(ptId)) {
                result = true;
            }
            if(patient.getGender().equals("F") && patient.getAge() >= 10 && patient.getAge() <= 19 && inOvcDreamProgram.contains(ptId)) {
                result = true;
            }
            if (lastvlresult != null && lastvlresult.getValue() != null && lastVlResultValue >=200) {
                    result = true;
            }
            if (inHivProgram.contains(ptId) && transferInDateValue != null ) {
                result = true;
            }
            if(mchMsEnc != null){
                result  = true;
            }

            ret.put(ptId, new BooleanResult(result, this));
        }

        return ret;
    }
}
