/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.calculation.library.hiv;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Program;
import org.openmrs.api.ConceptService;
import org.openmrs.api.OrderService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.*;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.calculation.library.*;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.InitialArtStartDateCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.LastViralLoadResultCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.OnArtCalculation;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.metadata.MchMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.kenyaemr.util.HtsConstants;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.parameter.OrderSearchCriteria;
import org.openmrs.ui.framework.SimpleObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils.daysSince;
import static org.openmrs.module.kenyaemrorderentry.util.Utils.getLatestObs;

public class NeedsViralLoadTestCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {
    protected static final Log log = LogFactory.getLog(StablePatientsCalculation.class);
    String flagMessage = null;
    public static final Integer HTS_PMTCT_ANC_ENTRY_POINT_CONCEPT_ID = 160538;
    public static final Integer HTS_PMTCT_MAT_ENTRY_POINT_CONCEPT_ID = 160456;
    public static final Integer HTS_PMTCT_PNC_ENTRY_POINT_CONCEPT_ID = 1623;
    public static final Integer HTSENTRYPOINT_QUESTION_CONCEPT_ID = 160540;




    /**
     * Needs vl test calculation criteria: New EMR guidelines March 2023
     * -----------------------------------------------------------------
     * Immediately = Pregnant + Breastfeeding mothers On ART
     * After 3 months = All unsuppressed + All Newly on ART (Including Pregnant and Breastfeeding mothers)
     * After 6 months = Children (0-24) with suppressed VL or Pregnant_Breastfeeding with suppressed initial VL after Pregnancy/BF status is recorded
     * After 12 months = Adults aged 25+ years with suppressed VL (upto 200 cps/ml)
     *
     * @see org.openmrs.module.kenyacore.calculation.PatientFlagCalculation#getFlagMessage()
     */
    @Override
    public String getFlagMessage() {
        return flagMessage;
    }

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
        Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
        PatientService patientService = Context.getPatientService();
        ConceptService cs = Context.getConceptService();
        OrderService orderService = Context.getOrderService();
        EncounterType mchEncType = MetadataUtils.existing(EncounterType.class, MchMetadata._EncounterType.MCHCS_CONSULTATION);
        Form mchAncForm = MetadataUtils.existing(Form.class, MchMetadata._Form.MCHMS_ANTENATAL_VISIT);
        Form mchDeliveryForm = MetadataUtils.existing(Form.class, MchMetadata._Form.MCHMS_DELIVERY);
        Form mchPostnatalForm = MetadataUtils.existing(Form.class, MchMetadata._Form.MCHMS_POSTNATAL_VISIT);



        Set<Integer> alive = Filters.alive(cohort, context);
        Set<Integer> inHivProgram = Filters.inProgram(hivProgram, alive, context);
        //Cohorts to consider
        //  Pregnant
        Set<Integer> pregnant = CalculationUtils.patientsThatPass(calculate(new IsPregnantCalculation(), cohort, context));
        // Breastfeeding
        Set<Integer> breastFeeding = CalculationUtils.patientsThatPass(calculate(new IsBreastFeedingCalculation(), cohort, context));
        // All on ART already
        Set<Integer> allOnArt = CalculationUtils.patientsThatPass(calculate(new OnArtCalculation(), cohort, context));
        // Patients with pending vl results
        Set<Integer> pendingVlResults = CalculationUtils.patientsThatPass(calculate(new PendingViralLoadResultCalculation(), cohort, context));
        //check for last pregnancy start date
        CalculationResultMap pregnancyStartDate = calculate(new PregnancyStartDateCalculation(), cohort, context);
        //check for last breastfeeding start date
        CalculationResultMap breastFeedingStarDate = calculate(new BreastFeedingStartDateCalculation(), cohort, context);
        //get the initial art start date
        CalculationResultMap dateInitiatedART = calculate(new InitialArtStartDateCalculation(), cohort, context);
        //check for last vl and date
        LastViralLoadResultCalculation lastVlResultCalculation = new LastViralLoadResultCalculation();
        CalculationResultMap lastVlResults = lastVlResultCalculation.evaluate(cohort, null, context);
        //Checks for ltfu
        Set<Integer> ltfu = CalculationUtils.patientsThatPass(calculate(new LostToFollowUpCalculation(), cohort, context));
        //Returns active in MCH clients
        Set<Integer> activeInMCH = CalculationUtils.patientsThatPass(calculate(new ActiveInMCHProgramCalculation(), cohort, context));

        CalculationResultMap ret = new CalculationResultMap();
        for (Integer ptId : cohort) {
            Patient patient = patientService.getPatient(ptId);
            boolean needsViralLoadTest = false;
            String lastVlResult = null;
            String lastVlResultLDL = null;
            Double lastVlResultValue = null;
            Date lastVLResultDate = null;
            Date artStartDate = EmrCalculationUtils.datetimeResultForPatient(dateInitiatedART, ptId);
            Date lastPregStartDate = EmrCalculationUtils.datetimeResultForPatient(pregnancyStartDate, ptId);
            Date lastBFStartDate = EmrCalculationUtils.datetimeResultForPatient(breastFeedingStarDate, ptId);
            

            //Check for latest vl and if it exists (vl is only valid if its for the last 12 months)
            CalculationResult lastvlresult = lastVlResults.get(ptId);
            if (lastvlresult != null && lastvlresult.getValue() != null) {
                Object lastVl = lastvlresult.getValue();
                SimpleObject res = (SimpleObject) lastVl;
                lastVlResult = res.get("lastVl").toString();
                lastVLResultDate = (Date) res.get("lastVlDate");
                // Differentiate between LDL and values for Viral load results
                if (lastVlResult == "LDL") {
                    lastVlResultLDL = "LDL";
                } else {
                    lastVlResultValue = Double.parseDouble(lastVlResult);
                }
            }
            // Confirm that patient is on hiv and there are no pending vls
            if (inHivProgram.contains(ptId) && !pendingVlResults.contains(ptId) && allOnArt.contains(ptId) && !ltfu.contains(ptId)) {

                Obs savedPregnancyStatus = getLatestObs(patient, Dictionary.PREGNANCY_STATUS);
                Obs savedBFStatus = getLatestObs(patient, Dictionary.CURRENTLY_BREASTFEEDING);
                Concept YES = Dictionary.getConcept(Dictionary.YES);

                Date obsPregStatusDate = savedPregnancyStatus != null && savedPregnancyStatus.getValueCoded().equals(YES) ? savedPregnancyStatus.getObsDatetime() : null;
                Date obsBFStatusDate = savedBFStatus != null && savedBFStatus.getValueCoded().equals(YES) ? savedBFStatus.getObsDatetime() : null;

                //Immediate: Pregnant or breastfeeding On ART
                if (artStartDate != null && daysSince(artStartDate, context) >= 92 && (
                        (activeInMCH.contains(ptId) && lastVLResultDate != null && ((lastPregStartDate != null && lastPregStartDate.after(lastVLResultDate)) || (lastBFStartDate != null && lastBFStartDate.after(lastVLResultDate))))
                                || (obsPregStatusDate != null && lastVLResultDate != null && obsPregStatusDate.after(lastVLResultDate)) || (obsBFStatusDate != null && lastVLResultDate != null && obsBFStatusDate.after(lastVLResultDate)))) {

                    needsViralLoadTest = true;
                    flagMessage = "Due for Viral Load";
                }
                //After 3 months: All with unsuppressed VL (>200 cps/ml)
                else if (lastVlResultValue != null && lastVLResultDate != null && daysSince(lastVLResultDate, context) >= 92 && lastVlResultValue > 200) {
                    needsViralLoadTest = true;
                    flagMessage = "Due for Viral Load";
                }
                //After 3 Months: New positives with no previous VL
                else if (artStartDate != null && daysSince(artStartDate, context) >= 92 && lastVLResultDate == null) {
                    needsViralLoadTest = true;
                }
                //After 6 months:
                // Pregnant and BF with a suppressed VL pregnancy test or during BF.
                //0-24 years old with a suppressed or LDL previous VL
                else if (((lastPregStartDate != null && lastVLResultDate != null && lastPregStartDate.before(lastVLResultDate)) || (lastBFStartDate != null && lastVLResultDate != null && lastBFStartDate.before(lastVLResultDate)) || (obsPregStatusDate != null && lastVLResultDate != null && obsPregStatusDate.before(lastVLResultDate)) || (obsBFStatusDate != null && lastVLResultDate != null && obsBFStatusDate.before(lastVLResultDate))
                        || patient.getAge() <= 24) && (lastVLResultDate != null && daysSince(lastVLResultDate, context) >= 183 && (lastVlResultLDL != null || (lastVlResultValue != null && lastVlResultValue < 200)))) {
                    needsViralLoadTest = true;
                    flagMessage = "Due for Viral Load";
                }
                //After 12 Months: > 25 years old with suppressed VL or LDL
                else if (lastVLResultDate != null && daysSince(lastVLResultDate, context) >= 365 && patient.getAge() >= 25) {
                    needsViralLoadTest = true;
                    flagMessage = "Due for Viral Load";
                }

                
                ret.put(ptId, new BooleanResult(needsViralLoadTest, this));
            }

            /*Due for Pre-ART viral load test flag
             * Criteria: In MCH program, Not on art and no previous vl test/Results,confirmed positive while in MCH
             * 
             * 
             */
            if(activeInMCH.contains(ptId)) {
                Encounter lastHtsInitialEnc = EmrUtils.lastEncounter(patient, HtsConstants.htsEncType, HtsConstants.htsInitialForm);
                Encounter lastHtsRetestEnc = EmrUtils.lastEncounter(patient, HtsConstants.htsEncType, HtsConstants.htsRetestForm);
                Encounter lastAncEnc = EmrUtils.lastEncounter(patient, mchEncType, mchAncForm);
                Encounter lastMchDeliveryEnc = EmrUtils.lastEncounter(patient, mchEncType, mchDeliveryForm);
                Encounter lastMchPostnatalEnc = EmrUtils.lastEncounter(patient, mchEncType, mchPostnatalForm);
                Concept htsFinalTestQuestion = cs.getConcept(HtsConstants.HTS_FINAL_TEST_CONCEPT_ID);
                Concept htsPositiveResult = cs.getConcept(HtsConstants.HTS_POSITIVE_RESULT_CONCEPT_ID);
                Concept htsEntryPointQuestion = cs.getConcept(HTSENTRYPOINT_QUESTION_CONCEPT_ID);
                Concept htsPmtctAncEntryPoint = cs.getConcept(HTS_PMTCT_ANC_ENTRY_POINT_CONCEPT_ID);
                Concept htsPmtctMatEntryPoint = cs.getConcept(HTS_PMTCT_MAT_ENTRY_POINT_CONCEPT_ID);
                Concept htsPmtctPncEntryPoint = cs.getConcept(HTS_PMTCT_PNC_ENTRY_POINT_CONCEPT_ID);
                Concept dateOfDelivery = cs.getConcept(Dictionary.DATE_OF_CONFINEMENT); //Though named date of confinement, same concept used for delivery date
                Encounter lastHtsEnc = null; 
                Date dateOfDeliveryValue = null;
                Date dateTestedPositiveAtHts = null; 
                List<Concept> vlOrderConcepts = Arrays.asList(cs.getConceptByUuid(Dictionary.HIV_VIRAL_LOAD), cs.getConceptByUuid(Dictionary.HIV_VIRAL_LOAD_QUALITATIVE));
                OrderSearchCriteria OrderSearchCriteria = new OrderSearchCriteria( patient, null, vlOrderConcepts, null, null, null, 
                null, null, false, null, null, null, null, true, true, false, false); 
                List<Order> vlOrders = orderService.getOrders(OrderSearchCriteria);                

                if (lastHtsInitialEnc != null && lastHtsRetestEnc == null) {
                    lastHtsEnc = lastHtsInitialEnc;
                } else if (lastHtsInitialEnc == null && lastHtsRetestEnc != null) {
                    lastHtsEnc = lastHtsRetestEnc;
                } else if (lastHtsInitialEnc != null && lastHtsRetestEnc != null) {
                    if (lastHtsInitialEnc.getEncounterDatetime().after(lastHtsRetestEnc.getEncounterDatetime())) {
                        lastHtsEnc = lastHtsInitialEnc;
                    } else {
                        lastHtsEnc = lastHtsRetestEnc;
                    }
                }
                boolean isConfirmedPositiveAtAnc = lastAncEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastAncEnc, htsFinalTestQuestion, htsPositiveResult) : false;
                boolean isConfirmedPositiveAtPostnatal = lastMchPostnatalEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastMchPostnatalEnc, htsFinalTestQuestion, htsPositiveResult) : false;
                boolean isConfirmedPositiveAtDelivery = lastMchDeliveryEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastMchDeliveryEnc, htsFinalTestQuestion, htsPositiveResult) : false;
                boolean testedPositiveWithin24MonthsInPostnatal = false;
                
                if(lastMchPostnatalEnc != null) {
                    
                    Date dateTestedPositivePnc = null;
                    for (Obs obs : lastMchPostnatalEnc.getObs()) {
                        if(obs.getConcept().equals(dateOfDelivery)) {
                            dateOfDeliveryValue = obs.getValueDate();
                        } else if(obs.getConcept().equals(htsFinalTestQuestion) && obs.getValueCoded().equals(htsPositiveResult)) {
                            dateTestedPositivePnc = obs.getObsDatetime();
                        }
                    }
                    if(dateOfDeliveryValue != null && dateTestedPositivePnc != null) {
                        testedPositiveWithin24MonthsInPostnatal = testedPositiveWithin24MonthsInPnc(dateOfDeliveryValue, dateTestedPositivePnc);
                    }
                }

                if ( vlOrders.size() == 0 && artStartDate == null &&
                (isConfirmedPositiveAtAnc || (isConfirmedPositiveAtPostnatal && testedPositiveWithin24MonthsInPostnatal) || isConfirmedPositiveAtDelivery)) {
                    needsViralLoadTest = true;
                    flagMessage = "Due for Pre-ART Viral Load";
                }

                if(lastHtsEnc != null) {
                    boolean isPositiveTestResult = lastHtsEnc != null ? EmrUtils.encounterThatPassCodedAnswer(lastHtsEnc, htsFinalTestQuestion, htsPositiveResult) : false;
                    if(isPositiveTestResult) {
                        dateTestedPositiveAtHts = lastHtsEnc.getEncounterDatetime();
                    }
                    if(isPositiveTestResult && vlOrders.size() == 0 && artStartDate == null) {
                        for (Obs obs : lastHtsEnc.getObs()) { 
                            if(obs.getConcept().equals(htsEntryPointQuestion) && (obs.getValueCoded().equals(htsPmtctAncEntryPoint) || 
                            obs.getValueCoded().equals(htsPmtctMatEntryPoint))) {
                                needsViralLoadTest = true;
                                flagMessage = "Due for Pre-ART Viral Load";
                            }
                        }
                    }
                }
                // check if confirmed positive using hts form and still within 24 months at postnatal
                if(dateTestedPositiveAtHts != null && dateOfDeliveryValue != null && dateTestedPositiveAtHts.after(dateOfDeliveryValue) && vlOrders.size() == 0 && artStartDate == null) {
                    testedPositiveWithin24MonthsInPostnatal = testedPositiveWithin24MonthsInPnc(dateOfDeliveryValue, dateTestedPositiveAtHts);
                    if(testedPositiveWithin24MonthsInPostnatal) {
                        needsViralLoadTest = true;
                        flagMessage = "Due for Pre-ART Viral Load";
                    }
                }

                ret.put(ptId, new BooleanResult(needsViralLoadTest, this));
            }
        }
        return ret;
    }
    private boolean testedPositiveWithin24MonthsInPnc(Date deliveryDate ,Date dateTestedPositive) {
        boolean isTestedPositiveWithin24Months = false;
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(deliveryDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(dateTestedPositive);
        int yearDiff = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int monthDiff = yearDiff * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        if (monthDiff <= 24) {
            isTestedPositiveWithin24Months = true;
        }
        return isTestedPositiveWithin24Months;
    }
}
