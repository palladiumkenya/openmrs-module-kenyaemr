/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.calculation.library.sari;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.*;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.metadata.SARIMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Calculates the eligibility for SARI/ILI patients
 */


        public class EnrolForSariCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
        protected static final Log log = LogFactory.getLog(EnrolForSariCalculation.class);
     public static final EncounterType sariEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.SARI_SCREENING);
      public static final Form sariScreeningForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.SARI_SCREENING);

    @Override
        public String getFlagMessage() {
        return "Enroll to SARI";
        }

        /**
         * Evaluates the calculation
         */

        @Override
        public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {

            Set<Integer> alive = Filters.alive(cohort, context);
            PatientService patientService = Context.getPatientService();
            CalculationResultMap ret = new CalculationResultMap();


        for (Integer ptId :alive) {
            boolean result = false;
            Patient patient = patientService.getPatient(ptId);
            Encounter lastSariEnc = EmrUtils.lastEncounter(patient, sariEncType, sariScreeningForm);
            Obs abnormalBreath= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(113316), cohort, context), ptId);
            Obs wheezing= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(122496), cohort, context), ptId);
            Obs soreThroat= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(158843), cohort, context), ptId);
            Obs difficultyBreathing= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(122496), cohort, context), ptId);
            Obs chestPain= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(120749), cohort, context), ptId);
            Obs rhinorrhea= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(165501), cohort, context), ptId);
            Obs soreMuscles= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(133632), cohort, context), ptId);
            Obs haemoptysis= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(138905), cohort, context), ptId);
            Obs chills= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(871), cohort, context), ptId);
            Obs diarrhea= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(142412), cohort, context), ptId);
            Obs vomiting= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(122983), cohort, context), ptId);
            Obs earPain= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(131602), cohort, context), ptId);
            Obs skinRash= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(512), cohort, context), ptId);
            Obs lackOfAppetite= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(163484), cohort, context), ptId);
            Obs conjunctivitis= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(119905), cohort, context), ptId);
            Obs convulsions= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(113054), cohort, context), ptId);
            Obs rigors= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(127361), cohort, context), ptId);
            Obs pneumoniaClinicalDiagnosis= EmrCalculationUtils.obsResultForPatient(Calculations.lastObs(Context.getConceptService().getConcept(114100), cohort, context), ptId);

                if ( (abnormalBreath != null && abnormalBreath.getValueCoded() != null && (abnormalBreath.getValueCoded().getConceptId().equals(1065) || abnormalBreath.getValueCoded().getConceptId().equals(1066))) ||
                        (wheezing != null && wheezing.getValueCoded() != null && (wheezing.getValueCoded().getConceptId().equals(1065) || wheezing.getValueCoded().getConceptId().equals(1066))) ||
                        (soreThroat != null && soreThroat.getValueCoded() != null && (soreThroat.getValueCoded().getConceptId().equals(1065) || soreThroat.getValueCoded().getConceptId().equals(1066))) ||
                        (difficultyBreathing != null && difficultyBreathing.getValueCoded() != null && (difficultyBreathing.getValueCoded().getConceptId().equals(1065) || difficultyBreathing.getValueCoded().getConceptId().equals(1066))) ||
                        (chestPain != null && chestPain.getValueCoded() != null && (chestPain.getValueCoded().getConceptId().equals(1065) || chestPain.getValueCoded().getConceptId().equals(1066))) ||
                        (rhinorrhea != null && rhinorrhea.getValueCoded() != null && (rhinorrhea.getValueCoded().getConceptId().equals(1065) || rhinorrhea.getValueCoded().getConceptId().equals(1066))) ||
                        (soreMuscles != null && soreMuscles.getValueCoded() != null && (soreMuscles.getValueCoded().getConceptId().equals(1065) || soreMuscles.getValueCoded().getConceptId().equals(1066))) ||
                        (haemoptysis != null && haemoptysis.getValueCoded() != null && (haemoptysis.getValueCoded().getConceptId().equals(1065) || haemoptysis.getValueCoded().getConceptId().equals(1066))) ||
                        (chills != null && chills.getValueCoded() != null && (chills.getValueCoded().getConceptId().equals(1065) || chills.getValueCoded().getConceptId().equals(1066))) ||
                        (diarrhea != null && diarrhea.getValueCoded() != null && (diarrhea.getValueCoded().getConceptId().equals(1065) || diarrhea.getValueCoded().getConceptId().equals(1066))) ||
                        (vomiting != null && vomiting.getValueCoded() != null && (vomiting.getValueCoded().getConceptId().equals(1065) || vomiting.getValueCoded().getConceptId().equals(1066))) ||
                        (earPain != null && earPain.getValueCoded() != null && (earPain.getValueCoded().getConceptId().equals(1065) || earPain.getValueCoded().getConceptId().equals(1066))) ||
                        (skinRash != null && skinRash.getValueCoded() != null && (skinRash.getValueCoded().getConceptId().equals(1065) || skinRash.getValueCoded().getConceptId().equals(1066))) ||
                        (lackOfAppetite != null && lackOfAppetite.getValueCoded() != null && (lackOfAppetite.getValueCoded().getConceptId().equals(1065) || lackOfAppetite.getValueCoded().getConceptId().equals(1066))) ||
                        (conjunctivitis != null && conjunctivitis.getValueCoded() != null && (conjunctivitis.getValueCoded().getConceptId().equals(1065) || conjunctivitis.getValueCoded().getConceptId().equals(1066))) ||
                        (convulsions != null && convulsions.getValueCoded() != null && (convulsions.getValueCoded().getConceptId().equals(1065) || convulsions.getValueCoded().getConceptId().equals(1066))) ||
                        (rigors != null && rigors.getValueCoded() != null && (rigors.getValueCoded().getConceptId().equals(1065) || rigors.getValueCoded().getConceptId().equals(1066))) ||
                        (pneumoniaClinicalDiagnosis != null && pneumoniaClinicalDiagnosis.getValueCoded() != null && (pneumoniaClinicalDiagnosis.getValueCoded().getConceptId().equals(1065) || pneumoniaClinicalDiagnosis.getValueCoded().getConceptId().equals(1066)))) {

                    Obs[] symptomsRecorded = {abnormalBreath,wheezing , convulsions,soreThroat,difficultyBreathing,chestPain,rhinorrhea,soreMuscles,haemoptysis,chills,diarrhea,vomiting,earPain,skinRash,lackOfAppetite,conjunctivitis,convulsions,rigors,pneumoniaClinicalDiagnosis};
                    int count = 0;
                    for (Obs s : symptomsRecorded) {
                        if (s != null) {
                            count++;
                        }
                    }
                    if(lastSariEnc != null && !DateUtils.isSameDay(new Date(), lastSariEnc.getEncounterDatetime())) {
                        result = false;
                    }else{
                        if (count >= 5) {
                            result = true;
                        }else {
                            result = false;
                        }
                    }


                }

            ret.put(ptId, new BooleanResult(result, this));
            }

            return ret;
        }
}
