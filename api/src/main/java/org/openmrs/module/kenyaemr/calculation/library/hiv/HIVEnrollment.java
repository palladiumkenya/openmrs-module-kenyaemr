package org.openmrs.module.kenyaemr.calculation.library.hiv;import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Program;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Calculations to Checks if a patient is enrolled in HIV program
 */
public class HIVEnrollment extends AbstractPatientCalculation {
    protected static final Log log = LogFactory.getLog(HIVEnrollment.class);
    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
        Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
        Set<Integer> alive = Filters.alive(cohort, context);
        Set<Integer> inHivProgram = Filters.inProgram(hivProgram, alive, context);
        CalculationResultMap ret = new CalculationResultMap();
        boolean inProgram = false;
        for (Integer ptId : cohort) {
            if (!inHivProgram.contains(ptId)) {
                inProgram = true;
            }
            ret.put(ptId, new BooleanResult(inProgram, this));
        }
        return ret;
    }

}

