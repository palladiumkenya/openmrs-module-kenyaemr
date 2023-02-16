/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.util;

import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;

public class HtsConstants {
    public static final Integer HTS_FINAL_TEST_CONCEPT_ID = 159427;
    public static final Integer HTS_POSITIVE_RESULT_CONCEPT_ID = 703;
    public static final Integer HTS_NEGATIVE_RESULT_CONCEPT_ID = 664;
    public static final Integer HTSLINKAGE_QUESTION_CONCEPT_ID = 159811;
    public static final Integer HTS_UPN_QUESTION_CONCEPT_ID = 162053;
    public static final Integer SUCCESSFUL_LINKAGE_CONCEPT_ID = 1065;
    public static final Integer HTS_TRACING_OUTCOME_QUESTION_CONCEPT_ID = 159811;
    public static final Integer HTS_SUCCESSFULL_TRACING_OUTCOME_CONCEPT_ID = 1065;
    public static final Integer IN_PATIENT_DEPARTMENT_CONCEPT_ID = 5485;
    public static final Integer OUT_PATIENT_DEPARTMENT_CONCEPT_ID = 160542;
    public static final Integer PEDIATRIC_CLINIC_CONCEPT_ID = 162181;
    public static final Integer MALNUTRITION_CLINIC_CONCEPT_ID = 160552;
    public static final Integer MNCH_ANC_CLINIC_CONCEPT_ID = 160538;
    public static final Integer MNCH_MAT_CLINIC_CONCEPT_ID = 160456;
    public static final Integer MNCH_PNC_CLINIC_CONCEPT_ID = 1623;
    public static final Integer TB_CLINIC_CONCEPT_ID = 160541;
    public static final Integer VCT_CLINIC_CONCEPT_ID = 159940;
    public static final Integer VMMC_CLINIC_CONCEPT_ID = 162223;
    public static final Integer STI_CLINIC_CONCEPT_ID = 160546;
    public static final Integer EMERGENCY_DEPARTMENT_CONCEPT_ID = 160522;
    public static final Integer COMMUNITY_TESTING_CONCEPT_ID = 163096;
    public static final Integer HOME_BASED_TESTING_CONCEPT_ID = 159938;
    public static final Integer MOBILE_OUTREACH_TESTING_CONCEPT_ID = 159939;
    public static final Integer OTHER_TESTING_CONCEPT_ID = 5622;
    public static final Form htsInitialForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.HTS_INITIAL_TEST);
    public static final Form htsRetestForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.HTS_CONFIRMATORY_TEST);
    public static final Form htsLinkageForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.HTS_LINKAGE);
    public static final Form htsTracingForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.HTS_CLIENT_TRACING);
    public static final Form htsReferralForm = MetadataUtils.existing(Form.class, CommonMetadata._Form.HTS_REFERRAL);
    public static final EncounterType htsEncType = MetadataUtils.existing(EncounterType.class, CommonMetadata._EncounterType.HTS);
}
