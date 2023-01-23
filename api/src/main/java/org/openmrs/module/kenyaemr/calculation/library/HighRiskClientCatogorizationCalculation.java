/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.calculation.library;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.ListResult;
import org.openmrs.module.kenyacore.calculation.*;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.calculation.library.hiv.DateOfEnrollmentHivCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.TransferInDateCalculation;
import org.openmrs.module.kenyaemr.calculation.library.models.Cd4ValueAndDate;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.metadata.OVCMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.common.DurationUnit;

import java.util.*;

/**
 * There is a need to categorize high-risk PMTCT Client
 */

        public class HighRiskClientCatogorizationCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
        protected static final Log log = LogFactory.getLog(HighRiskClientCatogorizationCalculation.class);


        @Override
        public String getFlagMessage() {
        return "High Risk Client";
        }

        /**
         * Evaluates the calculation
         * Allow new HV positive irrespective of time identified
         *All infected AGYW < 19 including OVC & DREAM girls
         * All clients with detectable VL > 200 copies/ml at baseline for known positive or anytime in the PMTCT followUP period
         * Tranfer Ins or Transist clients
         */

        @Override
        public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {

            Set<Integer> alive =Filters.alive(cohort, context);
            CalculationResultMap hivPositive = Calculations.allObs(Dictionary.getConcept(Dictionary.HIV_POSITIVE), cohort, context);
            CalculationResultMap hivEnrollmentDate = calculate(new DateOfEnrollmentHivCalculation(), cohort, context);
            Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
            Program ovcDreamProgram = MetadataUtils.existing(Program.class, OVCMetadata._Program.OVC);
            PatientService patientService = Context.getPatientService();

            CalculationResultMap transferInDate = calculate(new TransferInDateCalculation(), cohort, context);
            Set<Integer> inHivProgram = Filters.inProgram(hivProgram, alive, context);
            Set<Integer> inOvcDreamProgram = Filters.inProgram(ovcDreamProgram, alive, context);
            CalculationResultMap ret = new CalculationResultMap();

            for (Integer ptId :alive) {
                    boolean result = false;
                    Cd4ValueAndDate vlValue = null;
                    ListResult listResult = (ListResult) hivPositive.get(ptId);
                    Date enrollmentDate = EmrCalculationUtils.datetimeResultForPatient(hivEnrollmentDate, ptId);
                    List<Obs> allObsAsList = CalculationUtils.extractResultValues(listResult);

                    Patient patient = patientService.getPatient(ptId);

                    Date transferInDateValue = EmrCalculationUtils.datetimeResultForPatient(transferInDate, ptId);

                    for(Obs obs:allObsAsList){
                        Date obsDate = obs.getObsDatetime();
                        if(enrollmentDate != null && obsDate.before(dateLimit(enrollmentDate, 31))) {
                            result = true;
                        }
                     }

                     if(patient.getGender().equals("F") && patient.getAge() >= 10 && patient.getAge() <= 19 && inOvcDreamProgram.contains(ptId)) {
                         result = true;
                      }
                     if (vlValue != null && vlValue.getCd4Value() >= 200) {
                         result = true;
                     }
                     if (inHivProgram.contains(ptId) && transferInDateValue != null ) {
                         result = true;
                     }
                ret.put(ptId, new BooleanResult(result, this));
            }

            return ret;
        }
        private  Date dateLimit(Date date1, Integer days) {

            return DateUtil.adjustDate(date1, days, DurationUnit.DAYS);
         }}
