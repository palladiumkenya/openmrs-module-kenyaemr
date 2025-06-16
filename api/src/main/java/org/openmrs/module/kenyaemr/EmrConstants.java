/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * KenyaEMR specific constants
 */
public class EmrConstants {

	/**
	 * Module ID
	 */
	public static final String MODULE_ID = "kenyaemr";
	public static final String KP_MODULE_ID = "kenyakeypop";
	public static final String FACILITY_REPORTING_MODULE_ID = "facilityreporting";
	/**
	 * Application IDs
	 */
	public static final String APP_REGISTRATION = MODULE_ID + ".registration";
	public static final String APP_INTAKE = MODULE_ID + ".intake";
	public static final String APP_CLINICIAN = MODULE_ID + ".medicalEncounter";
	public static final String APP_CHART = MODULE_ID + ".medicalChart";
	public static final String APP_REPORTS = MODULE_ID + ".reports";
	public static final String APP_DIRECTORY = MODULE_ID + ".directory";
	public static final String APP_FACILITIES = MODULE_ID + ".facilities";
	public static final String APP_ADMIN = MODULE_ID + ".admin";
	public static final String APP_DEVELOPER = MODULE_ID + ".developer";
	public static final String APP_FACILITY_DASHBOARD = MODULE_ID + ".facilityDashboard";
	public static final String APP_DRUG_ORDER = MODULE_ID + ".drugorder";
	public static final String APP_LAB_ORDER = MODULE_ID + ".laborder";
	public static final String APP_DEFAULTER_TRACING = MODULE_ID + ".defaultertracing";
	public static final String APP_HIV_TESTING = MODULE_ID + ".hivtesting";
	public static final String APP_PREP = MODULE_ID + ".prep";
	public static final String APP_COVID = "covid.app.home";
	public static final String APP_KP = KP_MODULE_ID + ".keypopulation.provider";
	public static final String APP_AIR = FACILITY_REPORTING_MODULE_ID + ".facilityReporting.air";
	public static final String APP_LAB_MANIFEST = MODULE_ID + ".labmanifest";
	public static final String APP_ADHERENCE_COUNSELOR = MODULE_ID + ".counselling";
	public static final String APP_NUPI_VERIFICATION = MODULE_ID + ".nupiVerification";
	public static final String APP_O3_SHORTCUT = MODULE_ID + ".openmrs3Shortcut";
	public static final String APP_O3_QUEUES_SHORTCUT = MODULE_ID + ".openmrs3ServiceQueueShortcut";
	public static final String APP_O3_APPOINTMENTS_SHORTCUT = MODULE_ID + ".openmrs3AppointmentsShortcut";
	public static final String APP_REFERRALS = MODULE_ID + ".referral.home";

	/**
	 * Global property names
	 */
	public static final String GP_DEFAULT_LOCATION = MODULE_ID + ".defaultLocation";
	public static final String GP_CONTROLLER_WHITELIST = MODULE_ID + ".controllerWhitelist";
	public static final String GP_SUPPORT_PHONE_NUMBER = MODULE_ID + ".supportPhoneNumber";
	public static final String GP_SUPPORT_EMAIL_ADDRESS = MODULE_ID + ".supportEmailAddress";
	public static final String GP_EXTERNAL_HELP_URL = MODULE_ID + ".externalHelpUrl";
	public static final String GP_DHIS2_DATASET_MAPPING = MODULE_ID + ".adxDatasetMapping";
	public static final String GP_3PM_DATASET_MAPPING = KP_MODULE_ID + ".adx3pmDatasetMapping";
	public static final String GP_DATA_TOOL_URL = "kenyaemr.web.datatool.url";
	public static final String GP_DHIS_USERNAME = "dhis.username";
	public static final String GP_DHIS_PASSWORD = "dhis.password";

	/**
	 * Default global property values
	 */
	public static final String DEFAULT_SUPPORT_PHONE_NUMBER = "0800 722 440";
	public static final String DEFAULT_SUPPORT_EMAIL_ADDRESS = "help@palladiumgroup.on.spiceworks.com";
	public static final String DEFAULT_EXTERNAL_HELP_URL = "/help";

	public static final String GP_CONFIGURE_FACILITY_LIST_REFRESH_ON_STARTUP = "kenyaemr.refresh.facility.metadata";
	//Dental filling
	public static final String TEMP_FILLING_PER_TOOTH = "1000342";
	public static final String COMPOSITE_FILLING = "1000306";
	public static final String AMALGAM_FILLING = "1000300";
	public static final String GlASS_LONOMER_FILLING = "1000317";

	//Dental extraction
	public static final String SIMPLE_TOOTH_EXTRACTION = "1000344";
	public static final String SURGICAL_TOOTH_EXTRACTION = "166836";
	public static final String DENTAL_EXTRACTION_UNDER_GA = "2002209";
	public static final String EXTRA_TOOTH_EXTRACTION = "1000315";

	//Removal of Stitches
	public static final String REMOVAL_OF_CORNEAL_CONJUCTIVAL_SUTURES_LA = "2002097";
	public static final String REMOVAL_OF_CORNEAL_CONJUCTIVAL_SUTURES_GA = "2002096";
	public static final String SURGICAL_REMOVAL_OF_STICHES_MINOR_THEATRE = "2002265";
	public static final String REMOVAL_OF_CORNEAL_STITCHES = "1000397";
	public static final String ENT_REMOVAL_OF_STITCHES = "1000365";

	//Injections
	public static final String INJECTION = "1000251";
	public static final String INJECTION_OF_SCLEROSING_AGENT_IN_VEIN = "164553";
	public static final String INJECTION_OF_SUBSTANCE_INTO_VENTRICULAR_SHUNT = "164298";
	public static final String SUB_CONJUNCTIVAL_INJECTION = "168190";
	public static final String INTRA_ARTERIAL_INJECTION = "164677";
	public static final String SUB_CONJUCTIRAL_SUB_TENON_INJECTION = "1000399";

	//Stitching
	public static final String STITCHING = "1000447";
	public static final String DENTAL_STITCHING = "1000340";
	public static final String NOSE_EARS_STITCHING = "2001937";
	public static final String STITCHING_OPD = "2002263";
	public static final String STITCHING_MINOR_THEATER = "2002266";
	public static final String SATURING_ESOPHAGEAL_LACERATION_TRANSABDOMINAL_APPROACH = "164736";
	public static final String SATURING_ESOPHAGEAL_LACERATION_TRANSTHORACIC_APPROACH = "164737";
	public static final String ENT_SURGICAL_TOILET_STITCHING_UNDER_GA = "2001951";

	// Dressings
	public static final String CLEAN_AND_DRESSING = "441";
	public static final String BURN_DRESSING = "161668";
	public static final String ENT_DRESSING = "1000356";
	public static final String CASUALTY_DRESSING = "1000245";
	public static final String ENT_CHANGE_OF_DRESSINGS = "2001956";
	public static final String DRESSING_MINOR = "2002081";
	public static final String DRESSING_MINOR_OPD = "2002275";
	public static final String EYE_DRESSING = "2002111";
	public static final String EXTENSIVE_DRESSING = "2002215";
	public static final String DRESSING_LARGE_SPECIALISED = "2002276";
	public static final String DRESSING_SMALL_BURN = "2002278";
	public static final String SATURE_WOUND_WITH_DRESSING = "1934";
	public static final String WOUND_DRESSING = "1935";
	public static final String WOUND_DRESSING_ENT = "1000368";

	//Outpatient to Inpatient Checking On AdmissionRequest file
	public static final String INPATIENT_ADMISSION_REQUEST_QUESTION_CONCEPT = "160433AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	public static final String INPATIENT_ADMISSION_ANSWER_CONCEPT = "1654AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	public static final Set<Integer> CONDITIONS_CONCEPTS = new HashSet<>(Arrays.asList(114662,115728,149019,117789,117855,2005642,127417, 2008582, 136373,145438, 119692,118631,126513,116004, 128653, 128825, 115968, 113701, 115986, 2003295, 128788, 113744, 116017, 116041, 113689,
			128988, 2003169, 129069, 113763, 128781,2003211, 116049, 116066, 128955, 113712, 2003278, 157622, 126901,1295,2010623,115115,142474, 2004524, 134721, 2004525, 2004526,2004527, 2004325, 2004326, 2004327, 2004329,2004330, 2004331, 2004332,2004335,121610, 2010615, 125971,
			2010616,  2010617, 121960, 2010621, 2010622 ,143320, 2010623, 115001, 2010618, 121918, 2010619, 158974, 2010620, 2010624, 2010625, 2010626, 2010627, 119821, 2010628, 2010629,2010630,2010631, 2010632, 2010633, 2010634, 2010635, 2010636,145438,117321,
			143893, 2004493, 2004494, 2004495, 2004496, 2004497, 2004498, 2004499, 2004500, 2004501,145131,140987, 113087,2010574, 2010580, 2010586,121375,2004715,2010437,2002858,2002837,2002460,2004420,2007861,126513,2005019));

}
