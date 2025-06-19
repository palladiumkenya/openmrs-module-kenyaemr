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
	// Plain X-Ray without contrast enhancement
	public static final String XRAY_CHEST = "12";
	public static final String XRAY_ABDOMEN = "101";
	public static final String XRAY_OTHER = "309";
	public static final String XRAY_ARM = "377";
	public static final String XRAY_LEG = "380";
	public static final String XRAY_HAND = "382";
	public static final String XRAY_FOOT = "384";
	public static final String XRAY_SKULL = "386";
	public static final String XRAY_SPINE = "390";
	public static final String XRAY_PELVIS = "392";
	public static final String XRAY_SHOULDER = "394";
	public static final String XRAY_UPPER_GASTROINTESTINAL_SERIES = "159600";
	public static final String DUAL_ENERGY_XRAY_PHOTON_ABSORPTIOMETRY = "160630";
	public static final String DIAGNOSTIC_RADIOGRAPHY_OF_CHEST_COMBINED_PA_AND_LATERAL = "161251";
	public static final String XRAY_OF_ABDOMEN_1_VIEW = "161263";
	public static final String XRAY_OF_ABDOMEN_2_VIEWS_AP_SUPINE_AND_LATERAL_DECUBITUS = "161264";
	public static final String XRAY_OF_BILATERAL_ACROMIOCLAVICULAR_JOINTS = "161265";
	public static final String XRAY_OF_LEFT_ANKLE_2_VIEWS = "161266";
	public static final String XRAY_OF_RIGHT_ANKLE_2_VIEWS = "161267";
	public static final String XRAY_OF_BILATERAL_ANKLES_2_VIEWS = "161268";
	public static final String XRAY_OF_LEFT_CALCANEUS_2_VIEWS = "161269";
	public static final String XRAY_OF_RIGHT_CALCANEUS_2_VIEWS = "161270";
	public static final String XRAY_OF_BILATERAL_CALCANEUS_2_VIEWS = "161271";
	public static final String XRAY_OF_CHEST_4_VIEWS_PA_LAT_WITH_RIGHT_AND_LEFT_OBLIQUE = "161272";
	public static final String XRAY_OF_CHEST_1_VIEW_AP = "161273";
	public static final String XRAY_OF_CHEST_BILATERAL_OBLIQUE = "161294";
	public static final String XRAY_OF_CHEST_2_VIEWS_AND_APICAL_LORDOTIC = "161295";
	public static final String XRAY_OF_CHEST_APICAL_LORDOTIC = "161296";
	public static final String XRAY_OF_CHEST_4_VIEWS = "161297";
	public static final String XRAY_OF_LEFT_CLAVICLE_2_VIEWS = "161298";
	public static final String XRAY_OF_RIGHT_CLAVICLE_2_VIEWS = "161299";
	public static final String XRAY_OF_BILATERAL_CLAVICLES_2_VIEWS = "161300";
	public static final String XRAY_OF_CERVICAL_SPINE_2_OR_3_VIEWS = "161301";
	public static final String XRAY_OF_LEFT_ELBOW_2_VIEWS = "161302";
	public static final String XRAY_OF_RIGHT_ELBOW_2_VIEWS = "161303";
	public static final String XRAY_OF_BILATERAL_ELBOWS_2_VIEWS = "161304";
	public static final String XRAY_OF_FACIAL_BONES_3_VIEWS = "161305";
	public static final String XRAY_OF_LEFT_FEMUR_2_VIEWS = "161306";
	public static final String XRAY_OF_RIGHT_FEMUR_2_VIEWS = "161307";
	public static final String XRAY_OF_BILATERAL_FEMURS_2_VIEWS = "161308";
	public static final String XRAY_OF_RIGHT_WRIST_2_VIEWS = "161317";
	public static final String XRAY_OF_LEFT_WRIST_2_VIEWS = "161318";
	public static final String XRAY_OF_THORACIC_SPINE_2_VIEWS = "161320";
	public static final String XRAY_OF_LEFT_FOOT_2_VIEWS = "161321";
	public static final String XRAY_OF_RIGHT_FOOT_2_VIEWS = "161322";
	public static final String XRAY_OF_BILATERAL_FEET_2_VIEWS = "161323";
	public static final String XRAY_OF_LEFT_FOREARM_2_VIEWS = "161324";
	public static final String XRAY_OF_RIGHT_FOREARM_2_VIEWS = "161325";
	public static final String XRAY_OF_BILATERAL_FOREARMS_2_VIEWS = "161326";
	public static final String XRAY_OF_RIGHT_LOWER_LEG_2_VIEWS = "161327";
	public static final String XRAY_OF_LEFT_HAND_2_VIEWS = "161328";
	public static final String XRAY_OF_LEFT_LOWER_LEG_2_VIEWS = "161329";
	public static final String XRAY_OF_RIGHT_HAND_2_VIEWS = "161330";
	public static final String XRAY_OF_BILATERAL_HANDS_2_VIEWS = "161331";
	public static final String XRAY_OF_LEFT_HIP_2_VIEWS = "161332";
	public static final String XRAY_OF_RIGHT_HIP_2_VIEWS = "161333";
	public static final String XRAY_OF_BILATERAL_HIPS_2_VIEWS = "161334";
	public static final String XRAY_OF_THORACIC_AND_LUMBAR_SPINE_2_VIEWS = "161335";
	public static final String XRAY_OF_SPINE_1_VIEW = "161336";
	public static final String XRAY_OF_LEFT_UPPER_ARM_2_VIEWS = "161337";
	public static final String XRAY_OF_RIGHT_UPPER_ARM_2_VIEWS = "161338";
	public static final String XRAY_OF_SKULL_4_VIEWS = "161339";
	public static final String XRAY_OF_BILATERAL_UPPER_ARMS_2_VIEWS = "161340";
	public static final String XRAY_OF_SINUSES_3_VIEWS = "161341";
	public static final String XRAY_OF_RIGHT_SHOULDER_2_VIEWS = "161342";
	public static final String XRAY_OF_LEFT_SHOULDER_2_VIEWS = "161343";
	public static final String XRAY_OF_RIGHT_RIBS_2_VIEWS = "161344";
	public static final String XRAY_OF_LEFT_RIBS_2_VIEWS = "161345";
	public static final String XRAY_OF_PELVIS_1_VIEW = "161346";
	public static final String XRAY_OF_MANDIBLE_PANOREX_ = "161347";
	public static final String XRAY_OF_LUMBAR_SPINE_2_OR_3_VIEWS = "161348";
	public static final String XRAY_OF_RIGHT_KNEE_1_OR_2_VIEWS = "161349";
	public static final String XRAY_OF_LEFT_KNEE_1_OR_2_VIEWS = "161350";
	public static final String XRAY_OF_BILATERAL_RIBS_2_VIEWS = "161351";
	public static final String XRAY_OF_BILATERAL_KNEES_2_VIEWS = "161352";
	public static final String XRAY_OF_CEREBROSPINAL_FLUID_SHUNT = "164317";
	public static final String XRAY_OF_BOTH_SACROILIAC_JOINTS = "167895";
	public static final String XRAY_OF_LEFT_STERNOCLAVICULAR_JOINT = "167898";
	public static final String XRAY_OF_RIGHT_STERNOCLAVICULAR_JOINT = "167899";
	public static final String PLAIN_XRAY_OF_LEFT_SACROILIAC_JOINT = "167900";
	public static final String PLAIN_XRAY_OF_RIGHT_SACROILIAC_JOINT = "167901";
	public static final String XRAY_OF_BILATERAL_WRISTS = "167902";
	public static final String ABDOMINAL_ERECT_XRAY = "1000330";
	public static final String ORTHOPANTOMOGRAM = "1000406";
	public static final String TIBIA_FIBULA_ANTEROPOSTERIOR_VIEW = "1000407";
	public static final String XRAY_SCAPULA = "1000425";
	public static final String XRAY_CHEST_PA_VIEW = "1000427";
	public static final String XRAY_CHEST_LATERAL_VIEW = "1000428";
	public static final String VENOGRAM_XRAY = "2001314";
	public static final String THIGH_XRAY_LEFT = "2001315";
	public static final String THIGH_XRAY_RIGHT = "2001316";
	public static final String SELLA_XRAY = "2001317";
	public static final String SACRAL_ILLIAC_JOINT_XRAY = "2001318";
	public static final String POINTING_FINGER_X_RAY_LEFT = "2001319";
	public static final String POINTING_FINGER_XRAY_RIGHT = "2001320";
	public static final String OPTIC_FORAMEN_XRAY = "2001321";
	public static final String ORBITS_XRAY = "2001322";
	public static final String CENTRAL_SPINE_XRAY = "2001323";
	public static final String ATHROGRAM_XRAY = "2001324";
	public static final String ENEMA_XRAY = "2001325";
	public static final String MYELOGRAM_XRAY = "2001326";
	public static final String MASTOIDS_XRAY = "2001327";
	public static final String MAXILLA_XRAY = "2001328";
	public static final String LIMB_XRAY = "2001329";
	public static final String INTRAVENOUS_UROGRAPHY_IVU_XRAY = "2001330";
	public static final String BARIUM_MEAL_XRAY = "2001332";
	public static final String DENTAL_XRAY = "2001333";
	public static final String SACRUM_XRAY = "2001336";
	public static final String COCCYX_XRAY = "2001337";
	public static final String MICTURATING_CYSTOURETHROGRAM_MCU_XRAY = "2001338";
	public static final String ELBOW_JOINT_XRAY_LEFT = "2001339";
	public static final String ELBOW_JOINT_XRAY_RIGHT = "2001340";
	public static final String TIBIA__FIBULA_LEFT_XRAY = "2001341";
	public static final String CHEST_LATERAL_XRAY = "2001342";
	public static final String CHEST_RIBS_XRAY = "2001343";
	public static final String WRIST_JOINT_XRAY_LEFT = "2001344";
	public static final String WRIST_JOINT_XRAY_RIGHT = "2001345";
	public static final String WRIST_XRAY_BOTH_HANDS = "2001346";
	public static final String HAND_WRIST_XRAY_LEFT = "2001347";
	public static final String HAND_WRIST_XRAY_RIGHT = "2001348";
	public static final String FINGER_XRAY = "2001350";
	public static final String HUMEROUS_SHOULDER_XRAY = "2001351";
	public static final String HIP_LATERAL_XRAY = "2001352";
	public static final String LUMBAR_SACRAL_XRAY = "2001353";
	public static final String THORACOLUMBAR_SPINE_XRAY = "2001354";
	public static final String FEMUR_HIP_XRAY = "2001355";
	public static final String TIBIA__FIBULA_RIGHT_XRAY = "2001356";
	public static final String FOOT_ANKLE_XRAY = "2001357";
	public static final String TOE_XRAY = "2001358";
	public static final String POST_NASAL_SPACE_XRAY = "2001359";
	public static final String ABDOMEN_DEBUBIT_XRAY = "2001360";
	public static final String ABDOMEN_LATERAL_XRAY = "2001361";
	public static final String KIDNEY_URETER_AND_BLADDER_KUB_XRAY = "2001364";
	public static final String THUMB_XRAY = "2001379";
	public static final String TEMPOROMANDIBULAR_JOINT_TMJ_XRAY = "2001381";
	public static final String SCAPULA_XRAY = "2001382";
	public static final String NASAL_BONES_XRAY = "2001384";
	public static final String ORTHOPANTOMOGRAM_OPG_XRAY = "2001390";
	public static final String KNEE_JOINT_XRAY_RIGHT = "2001413";
	public static final String KNEE_JOINT_XRAY_LEFT = "2001414";
	public static final String RIGHT_JAW_XRAY = "2001526";
	public static final String BILATERAL_BITE_WINGS_BBW = "2001527";
	public static final String XRAY_POST_NASAL_SPACE = "2001998";

	// General ultrasound
	public static final String ULTRASOUND_ABDOMEN = "845";
	public static final String ULTRASOUND_HEPATIC = "852";
	public static final String ECHOCARDIOGRAM = "159567";
	public static final String ULTRASONOGRAPHY_BY_TRANSRECTAL_APPROACH = "161164";
	public static final String ULTRASOUND_OF_BILATERAL_LOWER_EXTREMITY_VEINS = "161274";
	public static final String ULTRASOUND_OF_RIGHT_LOWER_EXTREMITY_VEIN = "161275";
	public static final String ULTRASOUND_OF_LEFT_LOWER_EXTREMITY_VEIN = "161276";
	public static final String ULTRASOUND_OF_EXTREMITY = "161277";
	public static final String ULTRASOUND_OF_PELVIS_TRANSVAGINAL = "161278";
	public static final String ULTRASOUND_OF_SCROTUM_AND_TESTICLE = "161279";
	public static final String ULTRASOUND_OF_ABDOMEN_AND_RETROPERITONEUM_LIMITED = "161280";
	public static final String ULTRASOUND_OF_ABDOMEN_AND_RETROPERITONEUM = "161281";
	public static final String ULTRASOUND_OF_PELVIS_LIMITED = "161282";
	public static final String ULTRASOUND_OF_PELVIS = "161283";
	public static final String ULTRASOUND_GUIDED_PARACENTESIS = "161284";
	public static final String ULTRASOUND_OF_HEAD_AND_NECK = "161285";
	public static final String ULTRASOUND_GUIDANCE_FOR_ASPIRATION_OF_PERICARDIAL_SPACE = "161286";
	public static final String ULTRASOUND_GUIDANCE_FOR_PERCUTANEOUS_DRAINAGE_OF_CAVITY = "161287";
	public static final String ULTRASOUND_OF_CHEST = "161288";
	public static final String ULTRASOUND_OF_LEFT_BREAST = "161289";
	public static final String ULTRASOUND_OF_RIGHT_BREAST = "161290";
	public static final String ULTRASOUND_OF_ABDOMEN_LIMITED = "161291";
	public static final String TRANSTHORACIC_ECHOCARDIOGRAM = "163041";
	public static final String ULTRASOUND_SCAN_OF_HEAD = "164323";
	public static final String FINE_NEEDLE_ASPIRATION_BIOPSY_OF_THYROID = "164912";
	public static final String ULTRASOUND_OF_THYROID = "167891";
	public static final String ULTRASOUND_OF_PROSTATE = "167892";
	public static final String ENDORECTAL_ULTRASOUND = "167893";
	public static final String DOPPLER_ULTRASOUND_OF_RENAL_ARTERY = "167896";
	public static final String DOPPLER_ULTRASOUND = "1000408";
	public static final String RENAL_ULTRA_SOUND = "2001331";
	public static final String FINGER_X__RAY_4TH_METATARSAL_RT_HAND = "2001349";
	public static final String ULTRASOUND_LEFT_HAND_FINGER = "2001362";
	public static final String NECK_ULTRA_SOUND = "2001363";
	public static final String THYROID_ULTRA_SOUND = "2001365";
	public static final String SCROTAL_ULTRA_SOUND = "2001366";
	public static final String PROSTATE_ULTRA_SOUND = "2001367";
	public static final String TESTICULAR_ULTRA_SOUND = "2001368";
	public static final String DOPPLER_UPPER_LIMB_ARTERIAL = "2001369";
	public static final String DOPPLER_LOWER_LIMB_ARTERIAL = "2001370";
	public static final String DOPPLER_CAROTID = "2001371";
	public static final String KNEE_ULTRA_SOUND = "2001372";
	public static final String REGIONAL_ULTRASOUND = "2001373";
	public static final String ULTRASOUND_GUIDED_FNA = "2001374";
	public static final String ULTRASOUND_GUIDED_BIOPSY_SUPERFICIAL = "2001375";
	public static final String ULTRASOUND_GUIDED_DEEP_BIOPSY = "2001376";
	public static final String FAST_ULTRASOUND = "2001377";
	public static final String INGUINAL_SCROTAL_ULTRASOUND = "2001378";
	public static final String INGUINAL_SCAN = "2001380";
	public static final String AXILLA_ULTRA_SOUND = "2001383";
	public static final String KUB_PROSTATE_ULTRASOUND = "2001388";
	public static final String KUB_ULTRASOUND = "2001389";
	public static final String IMAGE_GUIDED_CHEMOPORT_INSERTION_ADULT = "2001392";
	public static final String IMAGE_GUIDED_CHEMOPORT_INSERTION_PEADIATRIC = "2001393";
	public static final String IMAGE_GUIDED_CVC_INSERTION = "2001394";
	public static final String DOPPLER_UPPER_LIMB_VENOUS_LEFT = "2001415";
	public static final String DOPPLER_LOWER_LIMB_VENOUS_RIGHT = "2001416";
	public static final String ECHOGRAM = "2001417";
	public static final String US_GUIDED_PIGTAIL_DRAINAGE_OF_A_CYST_LIVER_RENAL_OR_MESENTERIC = "2001418";
	public static final String US_GUIDED_ASPIRATION_OF_A_CYST_OR_FLUID = "2001419";
	public static final String US_GUIDED_SIMPLE_CYST_DRAINAGE_RENAL_LIVER_SPLEEN = "2001420";
	public static final String US_GUIDED_TUMOUR_LOCALIZATION = "2001421";
	public static final String MUSCULOSKELETAL_ULTRASOUND = "2001428";
	public static final String DOPPLER_LOWER_LIMB_VENOUS_LEFT = "2001429";
	public static final String CRANIAL_ULTRA_SOUND = "2001534";
	public static final String ULTRASOUND_OF_BOTH_BREAST = "2002335";

	//Contrast enhanced examination
	public static final String FLUOROSCOPY_ESOPHAGUS = "159601";
	public static final String NUCLEAR_SCAN_TECHNETIUM_99MTC_SESTAMIBI = "161170";
	public static final String RETROGRADE_PYELOGRAM = "161319";
	public static final String FLUOROSCOPY = "163958";
	public static final String ANGIOGRAM = "164012";
	public static final String CHOLANGIOGRAM = "164024";
	public static final String VOIDING_URETHROCYSTOGRAPHY = "164552";
	public static final String INSERTION_OF_TUNNELED_DIALYSIS_CATHETER_USING_FLUOROSCOPIC_GUIDANCE = "164606";
	public static final String PERINEOGRAM = "164615";
	public static final String URETERAL_REFLUX_STUDY = "164645";
	public static final String CENTRAL_VENOUS_LINE_PROCEDURE_USING_FLUOROSCOPIC_GUIDANCE = "164655";
	public static final String INJECTION_OF_BLOOD_VESSEL_USING_FLUOROSCOPIC_GUIDANCE = "164674";
	public static final String LAPAROSCOPY_WITH_GUIDED_TRANSHEPATIC_CHOLANGIOGRAPHY = "164688";
	public static final String LAPAROSCOPY_WITH_GUIDED_TRANSHEPATIC_CHOLANGIOGRAPHY_AND_BIOPSY = "164689";
	public static final String NEPHROSTOMY_USING_FLUOROSCOPIC_GUIDANCE = "164704";
	public static final String RADIOGRAPHIC_IMAGING_NEPHROSTOMY = "164712";
	public static final String REPLACEMENT_OF_DIALYSIS_CATHETER_USING_FLUOROSCOPIC_GUIDANCE = "164723";
	public static final String REPLACEMENT_OF_NEPHROSTOMY_TUBE_USING_FLUOROSCOPIC_GUIDANCE = "164725";
	public static final String CAROTID_ARTERIOGRAPHY_UNILATERAL = "167889";
	public static final String CAROTID_ARTERIOGRAPHY_BILATERAL = "167890";
	public static final String CEREBRAL_FOUR_VESSEL_ANGIOGRAM = "167904";
	public static final String BARIUM_ENEMA = "1000222";
	public static final String ANGIOGRAPHYAORTA = "1000280";
	public static final String CONTRAST_MEDIA = "1000328";
	public static final String ANGIOGRAPHYBRAIN = "1000345";
	public static final String ANGIOGRAPHYEXTREMETIES = "1000346";
	public static final String ANGIOGRAPHYPULMONARY_CHEST = "1000347";
	public static final String HYSTEROSALPINGOGRAPHY = "1000405";
	public static final String IMAGE_GUIDED_DIALYSIS_CATHETER_INSERTION = "2001395";
	public static final String LOWER_UPPER_LIMB_ARTERIOGRAM_BILATERAL = "2001406";
	public static final String LOWER_LIMB_UPPER_LIMB_ARTERIOGRAM_UNILATERAL = "2001407";
	public static final String FLUSH_AORTOGRAM_RENAL_ARTERY_HEPATIC_WITH_EMBOLIZATION_MATERIAL_AND_MICROCATHET = "2001408";
	public static final String INTERVENTIONAL_RADIOLOGY_IR_REVIEW_OF_TUBES = "2001422";
	public static final String PERCUTANEOUS_TRANSHEPATIC_CHOLANGIOGRAPHY_PTC_WITH_BILIARY_DRAINAGE_TWO_TUBES = "2001423";
	public static final String INTERNALIZATION_OF_BILIARY_TUBE_NO_TUBES_AVAILABLE = "2001424";
	public static final String NEPHROSTOGRAM = "2001426";
	public static final String GASTROGRAFIN_SWALLOW_XRAY = "2001528";
	public static final String SINOGRAM = "2001529";
	public static final String HYSTEROSALPINGOGRAM_HSG = "2001530";
	public static final String MICTURATING_CYSTOURETHROGRAM_MCU = "2001531";

	// Magnetic Resonance Imaging
	public static final String MAGNETIC_RESONANCE_SPECTROSCOPY = "161052";
	public static final String MAGNETIC_RESONANCE_IMAGING_OF_BRAIN_FUNCTIONAL = "161129";
	public static final String MAGNETIC_RESONANCE_IMAGING_OF_ABDOMEN_WITHOUT_CONTRAST = "163030";
	public static final String MAGNETIC_RESONANCE_IMAGING_OF_ABDOMEN_WITH_CONTRAST = "163031";
	public static final String MAGNETIC_RESONANCE_IMAGING_MRI_MODALITY = "168138";
	public static final String MAGNETIC_RESONANCE_IMAGING_OF_THORACOLUMBAR_SPINE = "168202";
	public static final String MAGNETIC_RESONANCE_IMAGING_OF_LUMBOSACRAL_SPINE = "168203";
	public static final String MAGNETIC_RESONANCE_IMAGING_OF_CERVICAL_SPINE = "168204";
	public static final String MAGNETIC_RESONANCE_IMAGING_OF_BREAST = "168651";
	public static final String MRI_HEAD_BRAIN_WR = "2001385";
	public static final String MRI_HEAD_BRAIN_WITHOUT_R = "2001386";
	public static final String MRI_KNEE_WR = "2001387";
	public static final String MRI_CERVICAL_SPINE_WR = "2001451";
	public static final String MRI_THORACIC_SPINE_WR = "2001453";
	public static final String MRI_LUMBAR_SPINE_WR = "2001454";

	// Computerized Tomography (CT)
	public static final String COMPUTED_TOMOGRAPHY_SCAN_HEAD = "846";
	public static final String POSITRON_EMISSION_TOMOGRAPHY_SCAN = "161051";
	public static final String ICTAL_BRAIN_SINGLE_PHOTON_EMISSION_COMPUTED_TOMOGRAPHY_SPECT_ = "161130";
	public static final String INTERICTAL_BRAIN_SINGLE_PHOTON_EMISSION_COMPUTED_TOMOGRAPHY_SPECT_ = "161131";
	public static final String COMPUTED_TOMOGRAPHY_OF_ABDOMEN_AND_PELVIS_WITHOUT_CONTRAST = "161162";
	public static final String CT_SCAN_PELVIS = "161163";
	public static final String CT_SCAN_CHEST = "161165";
	public static final String COMPUTED_TOMOGRAPHY_OF_BILATERAL_LOWER_EXTREMITIES_WITHOUT_CONTRAST = "161292";
	public static final String COMPUTERIZED_TOMOGRAPHY_OF_RIGHT_UPPER_EXTREMITY_WITHOUT_CONTRAST = "161293";
	public static final String COMPUTERIZED_TOMOGRAPHY_OF_LEFT_UPPER_EXTREMITY_WITHOUT_CONTRAST = "161309";
	public static final String COMPUTERIZED_TOMOGRAPHY_OF_RIGHT_UPPER_EXTREMITY_WITH_IV_CONTRAST = "161310";
	public static final String COMPUTERIZED_TOMOGRAPHY_OF_LEFT_UPPER_EXTREMITY_WITH_IV_CONTRAST = "161311";
	public static final String COMPUTERIZED_TOMOGRAPHY_OF_FACIAL_BONES_AND_MAXILLA_WITHOUT_CONTRAST = "161312";
	public static final String COMPUTERIZED_TOMOGRAPHY_OF_RIGHT_LOWER_EXTREMITY_WITHOUT_CONTRAST = "161313";
	public static final String COMPUTERIZED_TOMOGRAPHY_OF_LEFT_LOWER_EXTREMITY_WITHOUT_CONTRAST = "161314";
	public static final String COMPUTERIZED_TOMOGRAPHY_OF_LUMBAR_SPINE_WITHOUT_CONTRAST = "161315";
	public static final String COMPUTERIZED_TOMOGRAPHY_OF_CERVICAL_SPINE_WITHOUT_CONTRAST = "161316";
	public static final String COMPUTED_TOMOGRAPHY_OF_BILATERAL_LOWER_EXTREMITIES_WITH_IV_CONTRAST = "161353";
	public static final String COMPUTED_TOMOGRAPHY_OF_CERVICAL_SPINE_WITH_INTRAVENOUS_CONTRAST = "162286";
	public static final String COMPUTED_TOMOGRAPHY_OF_CHEST_WITH_INTRAVENOUS_CONTRAST = "162287";
	public static final String COMPUTED_TOMOGRAPHY_OF_HEAD_WITH_INTRAVENOUS_CONTRAST = "162288";
	public static final String COMPUTED_TOMOGRAPHY_OF_LUMBAR_SPINE_WITH_INTRAVENOUS_CONTRAST = "162289";
	public static final String COMPUTED_TOMOGRAPHY_OF_RIGHT_LOWER_EXTREMITY_WITH_INTRAVENOUS_CONTRAST = "162290";
	public static final String COMPUTED_TOMOGRAPHY_OF_LEFT_LOWER_EXTREMITY_WITH_INTRAVENOUS_CONTRAST = "162291";
	public static final String COMPUTED_TOMOGRAPHY_OF_FACIAL_BONES_AND_MAXILLA_WITH_INTRAVENOUS_CONTRAST = "162292";
	public static final String COMPUTED_TOMOGRAPHY_OF_NECK_WITH_INTRAVENOUS_CONTRAST = "162293";
	public static final String COMPUTED_TOMOGRAPHY_OF_THORACIC_SPINE_WITH_INTRAVENOUS_CONTRAST = "162294";
	public static final String COMPUTED_TOMOGRAPHY_OF_ABDOMEN_AND_PELVIS_WITH_INTRAVENOUS_CONTRAST = "163003";
	public static final String COMPUTED_TOMOGRAPHY_OF_ABDOMEN_WITH_CONTRAST = "163004";
	public static final String COMPUTED_TOMOGRAPHY_OF_ABDOMEN_WITHOUT_CONTRAST = "163005";
	public static final String COMPUTED_TOMOGRAPHY_OF_CHEST_AND_ABDOMEN_WITHOUT_IV_CONTRAST = "163037";
	public static final String COMPUTED_TOMOGRAPHY_OF_CHEST_ABDOMEN_AND_PELVIS_WITHOUT_IV_CONTRAST = "163038";
	public static final String COMPUTED_TOMOGRAPHY_OF_CHEST_AND_ABDOMEN_WITH_IV_CONTRAST = "163039";
	public static final String COMPUTED_TOMOGRAPHY_OF_CHEST_ABDOMEN_AND_PELVIS_WITH_IV_CONTRAST = "163040";
	public static final String NEPHROSTOMY_USING_COMPUTED_TOMOGRAPHY_GUIDANCE = "164705";
	public static final String COMPUTED_TOMOGRAPHY_OF_HEART = "167879";
	public static final String COMPUTED_TOMOGRAPHY_OF_KNEE = "168199";
	public static final String COMPUTED_TOMOGRAPHY_OF_FEMUR = "168200";
	public static final String COMPUTED_TOMOGRAPHY_OF_HIP = "168201";
	public static final String COMPUTED_TOMOGRAPHY_OF_ORBITS_WITH_CONTRAST = "168575";
	public static final String COMPUTED_TOMOGRAPHY_OF_ORBITS_WITHOUT_CONTRAST = "168576";
	public static final String CT_SCANS_WITHOUT_CONTRAST_CHEST = "1000284";
	public static final String COMPUTED_TOMOGRAPHY_OF_BRAIN_WITHOUT_CONTRAST = "1000348";
	public static final String COMPUTED_TOMOGRAPHY_OF_CHEST_WITHOUT_CONTRAST = "1000349";
	public static final String COMPUTED_TOMOGRAPHY_IVU_WITHOUT_CONTRAST = "1000350";
	public static final String COMPUTED_TOMOGRAPHY_OF_NECK_WITHOUT_CONTRAST = "1000351";
	public static final String COMPUTED_TOMOGRAPHY_WITHOUT_CONTRAST_PELVIMETRY = "1000352";
	public static final String CT_PARANASAL_SINUSES = "1000426";
	public static final String CT_ANGIOGRAM_WR = "2001430";
	public static final String CT_BRAIN_ANGIOGRAM_WR = "2001431";
	public static final String CT_EXTREMITIES_WR = "2001432";
	public static final String CT_GUIDED_ABCESS_DRAINAGE_WR = "2001433";
	public static final String CT_GUIDED_BIOPSY_LUNG_BIOPSY_WR = "2001434";
	public static final String CT_GUIDED_BONE_BIOPSY_WR = "2001435";
	public static final String CT_SCAN_ANKLE_WR = "2001436";
	public static final String CT_SCAN_CERVICAL_SPINE_WR = "2001437";
	public static final String CT_SCAN_CHEST_WITH_CONTRAST_WR = "2001438";
	public static final String CT_SCAN_HEAD_BRAIN_WR = "2001439";
	public static final String CT_SCAN_HIP_WR = "2001440";
	public static final String CT_SCAN_KNEE_WR = "2001441";
	public static final String CT_SCAN_LUMBER_SPINE_WR = "2001442";
	public static final String CT_SCAN_NECK_WITH_CONTRAST_WR = "2001443";
	public static final String CT_SCAN_PELVIC_TRAUMA_WR = "2001444";
	public static final String CT_SCAN_PELVIC_WITH_CONTRAST_WR = "2001445";
	public static final String CT_SCAN_PSN_WR = "2001446";
	public static final String CT_SCAN_SHOULDER_WR = "2001447";
	public static final String CT_SCAN_THORACIC_SPINE_WR = "2001448";
	public static final String CT_SCAN_UROGRAM_WR = "2001449";
	public static final String CT_SCAN_WRIST_WR = "2001450";
	public static final String HIGH_RESOLUTION_COMPUTED_TOMOGRAPHY_HRCT_CHEST_SCAN = "2001452";

	// Mammography
	public static final String MAMMOGRAM = "163591";
	public static final String MAMMARY_DUCTOGRAM = "167903";

	//Obstetric ultrasound
	public static final String OBSTETRIC_ULTRASOUND = "159618";
	public static final String ULTRASOUND_OBSTETRIC_DOPPLER = "167897";
}
