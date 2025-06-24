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

import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.moh717.Moh717CohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.moh717.Moh717IndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.openmrs.module.kenyaemr.EmrConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;

/**
 * Report builder for MOH717
 * Work load
 */

@Component
@Builds({ "kenyaemr.ehrReports.report.717" })
public class MOH717ReportBuilder extends AbstractReportBuilder {
    private final Moh717CohortLibrary moh717CohortLibrary;

    private final CommonDimensionLibrary commonDimensionLibrary;

    private final Moh717IndicatorLibrary moh717IndicatorLibrary;

    static final int NEW_VISIT = 164180, RE_ATT= 160530, FP_RE_ATT = 164142;

    static final ArrayList<String> otherSpecialClinics = new ArrayList<>(Arrays.asList(
            CommonMetadata._Form.NEUROLOGY_CLINICAL_FORM,
            CommonMetadata._Form.DERMATOLOGY_CLINICAL_FORM,
            CommonMetadata._Form.AUDIOLOGY_FORM,
            CommonMetadata._Form.SPEECH_AND_LANGAUGE_THERAPY_CLINICAL_FORM,
            CommonMetadata._Form.DIABETIC_CLINICAL_FORM,
            CommonMetadata._Form.MAXILLOFACIAL_CLINICAL_FORM,
            CommonMetadata._Form.GASTROENTEROLOGY_CLINICAL_FORM,
            CommonMetadata._Form.CARDIOLOGY_CLINICAL_FORM,
            CommonMetadata._Form.DENTAL_CLINICAL_FORM,
            CommonMetadata._Form.FERTILITY_CLINICAL_FORM,
            CommonMetadata._Form.UROLOGY_CLINICAL_FORM
    ));
    static final String OCCUPATIONAL_THERAPY_FORM = CommonMetadata._Form.OCCUPATIONAL_THERAPY_CLINICAL_FORM;
    static final String PHYSIOTHERAPY_FORM = CommonMetadata._Form.PHYSIOTHERAPY_FORM;
    static final String dentalFillingList = String.join(",", new ArrayList<>(Arrays.asList(
            EmrConstants.TEMP_FILLING_PER_TOOTH, EmrConstants.COMPOSITE_FILLING, EmrConstants.AMALGAM_FILLING, EmrConstants.GlASS_LONOMER_FILLING
    )));
    static final String dentalExtractionList = String.join(",",new ArrayList<>(Arrays.asList(
            EmrConstants.SIMPLE_TOOTH_EXTRACTION, EmrConstants.SURGICAL_TOOTH_EXTRACTION, EmrConstants.DENTAL_EXTRACTION_UNDER_GA, EmrConstants.EXTRA_TOOTH_EXTRACTION
    )));
    static final String stitchesRemovalList = String.join(",", new ArrayList<>(Arrays.asList(
            EmrConstants.REMOVAL_OF_CORNEAL_CONJUCTIVAL_SUTURES_LA, EmrConstants.REMOVAL_OF_CORNEAL_CONJUCTIVAL_SUTURES_GA, EmrConstants.SURGICAL_REMOVAL_OF_STICHES_MINOR_THEATRE, EmrConstants.REMOVAL_OF_CORNEAL_STITCHES,
            EmrConstants.ENT_REMOVAL_OF_STITCHES
    )));
    static final String injectionsList = String.join(",",new ArrayList<>(Arrays.asList(
            EmrConstants.INJECTION, EmrConstants.INJECTION_OF_SCLEROSING_AGENT_IN_VEIN, EmrConstants.INJECTION_OF_SUBSTANCE_INTO_VENTRICULAR_SHUNT, EmrConstants.SUB_CONJUNCTIVAL_INJECTION,
            EmrConstants.INTRA_ARTERIAL_INJECTION,EmrConstants.SUB_CONJUCTIRAL_SUB_TENON_INJECTION
    )));
    static final String stitchingList = String.join(",",new ArrayList<>(Arrays.asList(
            EmrConstants.STITCHING, EmrConstants.DENTAL_STITCHING, EmrConstants.NOSE_EARS_STITCHING, EmrConstants.STITCHING_OPD,
            EmrConstants.STITCHING_MINOR_THEATER,EmrConstants.SATURING_ESOPHAGEAL_LACERATION_TRANSABDOMINAL_APPROACH,
            EmrConstants.SATURING_ESOPHAGEAL_LACERATION_TRANSTHORACIC_APPROACH,EmrConstants.ENT_SURGICAL_TOILET_STITCHING_UNDER_GA
    )));
    static final String dressingList = String.join(",",new ArrayList<>(Arrays.asList(
            EmrConstants.CLEAN_AND_DRESSING, EmrConstants.BURN_DRESSING, EmrConstants.ENT_DRESSING, EmrConstants.CASUALTY_DRESSING,
            EmrConstants.ENT_CHANGE_OF_DRESSINGS,EmrConstants.DRESSING_MINOR,
            EmrConstants.DRESSING_MINOR_OPD,EmrConstants.EYE_DRESSING,EmrConstants.EXTENSIVE_DRESSING,EmrConstants.DRESSING_LARGE_SPECIALISED,
            EmrConstants.DRESSING_SMALL_BURN,EmrConstants.SATURE_WOUND_WITH_DRESSING,EmrConstants.WOUND_DRESSING,EmrConstants.WOUND_DRESSING_ENT
    )));
    public static final String plainXRayList = String.join(",", new ArrayList<>(Arrays.asList(
            EmrConstants.XRAY_CHEST,
            EmrConstants.XRAY_ABDOMEN,
            EmrConstants.XRAY_OTHER,
            EmrConstants.XRAY_ARM,
            EmrConstants.XRAY_LEG,
            EmrConstants.XRAY_HAND,
            EmrConstants.XRAY_FOOT,
            EmrConstants.XRAY_SKULL,
            EmrConstants.XRAY_SPINE,
            EmrConstants.XRAY_PELVIS,
            EmrConstants.XRAY_SHOULDER,
            EmrConstants.XRAY_UPPER_GASTROINTESTINAL_SERIES,
            EmrConstants.DUAL_ENERGY_XRAY_PHOTON_ABSORPTIOMETRY,
            EmrConstants.DIAGNOSTIC_RADIOGRAPHY_OF_CHEST_COMBINED_PA_AND_LATERAL,
            EmrConstants.XRAY_OF_ABDOMEN_1_VIEW,
            EmrConstants.XRAY_OF_ABDOMEN_2_VIEWS_AP_SUPINE_AND_LATERAL_DECUBITUS,
            EmrConstants.XRAY_OF_BILATERAL_ACROMIOCLAVICULAR_JOINTS,
            EmrConstants.XRAY_OF_LEFT_ANKLE_2_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_ANKLE_2_VIEWS,
            EmrConstants.XRAY_OF_BILATERAL_ANKLES_2_VIEWS,
            EmrConstants.XRAY_OF_LEFT_CALCANEUS_2_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_CALCANEUS_2_VIEWS,
            EmrConstants.XRAY_OF_BILATERAL_CALCANEUS_2_VIEWS,
            EmrConstants.XRAY_OF_CHEST_4_VIEWS_PA_LAT_WITH_RIGHT_AND_LEFT_OBLIQUE,
            EmrConstants.XRAY_OF_CHEST_1_VIEW_AP,
            EmrConstants.XRAY_OF_CHEST_BILATERAL_OBLIQUE,
            EmrConstants.XRAY_OF_CHEST_2_VIEWS_AND_APICAL_LORDOTIC,
            EmrConstants.XRAY_OF_CHEST_APICAL_LORDOTIC,
            EmrConstants.XRAY_OF_CHEST_4_VIEWS,
            EmrConstants.XRAY_OF_LEFT_CLAVICLE_2_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_CLAVICLE_2_VIEWS,
            EmrConstants.XRAY_OF_BILATERAL_CLAVICLES_2_VIEWS,
            EmrConstants.XRAY_OF_CERVICAL_SPINE_2_OR_3_VIEWS,
            EmrConstants.XRAY_OF_LEFT_ELBOW_2_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_ELBOW_2_VIEWS,
            EmrConstants.XRAY_OF_BILATERAL_ELBOWS_2_VIEWS,
            EmrConstants.XRAY_OF_FACIAL_BONES_3_VIEWS,
            EmrConstants.XRAY_OF_LEFT_FEMUR_2_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_FEMUR_2_VIEWS,
            EmrConstants.XRAY_OF_BILATERAL_FEMURS_2_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_WRIST_2_VIEWS,
            EmrConstants.XRAY_OF_LEFT_WRIST_2_VIEWS,
            EmrConstants.XRAY_OF_THORACIC_SPINE_2_VIEWS,
            EmrConstants.XRAY_OF_LEFT_FOOT_2_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_FOOT_2_VIEWS,
            EmrConstants.XRAY_OF_BILATERAL_FEET_2_VIEWS,
            EmrConstants.XRAY_OF_LEFT_FOREARM_2_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_FOREARM_2_VIEWS,
            EmrConstants.XRAY_OF_BILATERAL_FOREARMS_2_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_LOWER_LEG_2_VIEWS,
            EmrConstants.XRAY_OF_LEFT_HAND_2_VIEWS,
            EmrConstants.XRAY_OF_LEFT_LOWER_LEG_2_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_HAND_2_VIEWS,
            EmrConstants.XRAY_OF_BILATERAL_HANDS_2_VIEWS,
            EmrConstants.XRAY_OF_LEFT_HIP_2_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_HIP_2_VIEWS,
            EmrConstants.XRAY_OF_BILATERAL_HIPS_2_VIEWS,
            EmrConstants.XRAY_OF_THORACIC_AND_LUMBAR_SPINE_2_VIEWS,
            EmrConstants.XRAY_OF_SPINE_1_VIEW,
            EmrConstants.XRAY_OF_LEFT_UPPER_ARM_2_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_UPPER_ARM_2_VIEWS,
            EmrConstants.XRAY_OF_SKULL_4_VIEWS,
            EmrConstants.XRAY_OF_BILATERAL_UPPER_ARMS_2_VIEWS,
            EmrConstants.XRAY_OF_SINUSES_3_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_SHOULDER_2_VIEWS,
            EmrConstants.XRAY_OF_LEFT_SHOULDER_2_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_RIBS_2_VIEWS,
            EmrConstants.XRAY_OF_LEFT_RIBS_2_VIEWS,
            EmrConstants.XRAY_OF_PELVIS_1_VIEW,
            EmrConstants.XRAY_OF_MANDIBLE_PANOREX_,
            EmrConstants.XRAY_OF_LUMBAR_SPINE_2_OR_3_VIEWS,
            EmrConstants.XRAY_OF_RIGHT_KNEE_1_OR_2_VIEWS,
            EmrConstants.XRAY_OF_LEFT_KNEE_1_OR_2_VIEWS,
            EmrConstants.XRAY_OF_BILATERAL_RIBS_2_VIEWS,
            EmrConstants.XRAY_OF_BILATERAL_KNEES_2_VIEWS,
            EmrConstants.XRAY_OF_CEREBROSPINAL_FLUID_SHUNT,
            EmrConstants.XRAY_OF_BOTH_SACROILIAC_JOINTS,
            EmrConstants.XRAY_OF_LEFT_STERNOCLAVICULAR_JOINT,
            EmrConstants.XRAY_OF_RIGHT_STERNOCLAVICULAR_JOINT,
            EmrConstants.PLAIN_XRAY_OF_LEFT_SACROILIAC_JOINT,
            EmrConstants.PLAIN_XRAY_OF_RIGHT_SACROILIAC_JOINT,
            EmrConstants.XRAY_OF_BILATERAL_WRISTS,
            EmrConstants.ABDOMINAL_ERECT_XRAY,
            EmrConstants.ORTHOPANTOMOGRAM,
            EmrConstants.TIBIA_FIBULA_ANTEROPOSTERIOR_VIEW,
            EmrConstants.XRAY_SCAPULA,
            EmrConstants.XRAY_CHEST_PA_VIEW,
            EmrConstants.XRAY_CHEST_LATERAL_VIEW,
            EmrConstants.VENOGRAM_XRAY,
            EmrConstants.THIGH_XRAY_LEFT,
            EmrConstants.THIGH_XRAY_RIGHT,
            EmrConstants.SELLA_XRAY,
            EmrConstants.SACRAL_ILLIAC_JOINT_XRAY,
            EmrConstants.POINTING_FINGER_X_RAY_LEFT,
            EmrConstants.POINTING_FINGER_XRAY_RIGHT,
            EmrConstants.OPTIC_FORAMEN_XRAY,
            EmrConstants.ORBITS_XRAY,
            EmrConstants.CENTRAL_SPINE_XRAY,
            EmrConstants.ATHROGRAM_XRAY,
            EmrConstants.ENEMA_XRAY,
            EmrConstants.MYELOGRAM_XRAY,
            EmrConstants.MASTOIDS_XRAY,
            EmrConstants.MAXILLA_XRAY,
            EmrConstants.LIMB_XRAY,
            EmrConstants.INTRAVENOUS_UROGRAPHY_IVU_XRAY,
            EmrConstants.BARIUM_MEAL_XRAY,
            EmrConstants.DENTAL_XRAY,
            EmrConstants.SACRUM_XRAY,
            EmrConstants.COCCYX_XRAY,
            EmrConstants.MICTURATING_CYSTOURETHROGRAM_MCU_XRAY,
            EmrConstants.ELBOW_JOINT_XRAY_LEFT,
            EmrConstants.ELBOW_JOINT_XRAY_RIGHT,
            EmrConstants.TIBIA__FIBULA_LEFT_XRAY,
            EmrConstants.CHEST_LATERAL_XRAY,
            EmrConstants.CHEST_RIBS_XRAY,
            EmrConstants.WRIST_JOINT_XRAY_LEFT,
            EmrConstants.WRIST_JOINT_XRAY_RIGHT,
            EmrConstants.WRIST_XRAY_BOTH_HANDS,
            EmrConstants.HAND_WRIST_XRAY_LEFT,
            EmrConstants.HAND_WRIST_XRAY_RIGHT,
            EmrConstants.FINGER_XRAY,
            EmrConstants.HUMEROUS_SHOULDER_XRAY,
            EmrConstants.HIP_LATERAL_XRAY,
            EmrConstants.LUMBAR_SACRAL_XRAY,
            EmrConstants.THORACOLUMBAR_SPINE_XRAY,
            EmrConstants.FEMUR_HIP_XRAY,
            EmrConstants.TIBIA__FIBULA_RIGHT_XRAY,
            EmrConstants.FOOT_ANKLE_XRAY,
            EmrConstants.TOE_XRAY,
            EmrConstants.POST_NASAL_SPACE_XRAY,
            EmrConstants.ABDOMEN_DEBUBIT_XRAY,
            EmrConstants.ABDOMEN_LATERAL_XRAY,
            EmrConstants.KIDNEY_URETER_AND_BLADDER_KUB_XRAY,
            EmrConstants.THUMB_XRAY,
            EmrConstants.TEMPOROMANDIBULAR_JOINT_TMJ_XRAY,
            EmrConstants.SCAPULA_XRAY,
            EmrConstants.NASAL_BONES_XRAY,
            EmrConstants.ORTHOPANTOMOGRAM_OPG_XRAY,
            EmrConstants.KNEE_JOINT_XRAY_RIGHT,
            EmrConstants.KNEE_JOINT_XRAY_LEFT,
            EmrConstants.RIGHT_JAW_XRAY,
            EmrConstants.BILATERAL_BITE_WINGS_BBW,
            EmrConstants.XRAY_POST_NASAL_SPACE
    )));
    public static final String generalUltrasoundList = String.join(",",new ArrayList<>(Arrays.asList(
            EmrConstants.ULTRASOUND_ABDOMEN,
            EmrConstants.ULTRASOUND_HEPATIC,
            EmrConstants.ECHOCARDIOGRAM,
            EmrConstants.ULTRASONOGRAPHY_BY_TRANSRECTAL_APPROACH,
            EmrConstants.ULTRASOUND_OF_BILATERAL_LOWER_EXTREMITY_VEINS,
            EmrConstants.ULTRASOUND_OF_RIGHT_LOWER_EXTREMITY_VEIN,
            EmrConstants.ULTRASOUND_OF_LEFT_LOWER_EXTREMITY_VEIN,
            EmrConstants.ULTRASOUND_OF_EXTREMITY,
            EmrConstants.ULTRASOUND_OF_PELVIS_TRANSVAGINAL,
            EmrConstants.ULTRASOUND_OF_SCROTUM_AND_TESTICLE,
            EmrConstants.ULTRASOUND_OF_ABDOMEN_AND_RETROPERITONEUM_LIMITED,
            EmrConstants.ULTRASOUND_OF_ABDOMEN_AND_RETROPERITONEUM,
            EmrConstants.ULTRASOUND_OF_PELVIS_LIMITED,
            EmrConstants.ULTRASOUND_OF_PELVIS,
            EmrConstants.ULTRASOUND_GUIDED_PARACENTESIS,
            EmrConstants.ULTRASOUND_OF_HEAD_AND_NECK,
            EmrConstants.ULTRASOUND_GUIDANCE_FOR_ASPIRATION_OF_PERICARDIAL_SPACE,
            EmrConstants.ULTRASOUND_GUIDANCE_FOR_PERCUTANEOUS_DRAINAGE_OF_CAVITY,
            EmrConstants.ULTRASOUND_OF_CHEST,
            EmrConstants.ULTRASOUND_OF_LEFT_BREAST,
            EmrConstants.ULTRASOUND_OF_RIGHT_BREAST,
            EmrConstants.ULTRASOUND_OF_ABDOMEN_LIMITED,
            EmrConstants.TRANSTHORACIC_ECHOCARDIOGRAM,
            EmrConstants.ULTRASOUND_SCAN_OF_HEAD,
            EmrConstants.FINE_NEEDLE_ASPIRATION_BIOPSY_OF_THYROID,
            EmrConstants.ULTRASOUND_OF_THYROID,
            EmrConstants.ULTRASOUND_OF_PROSTATE,
            EmrConstants.ENDORECTAL_ULTRASOUND,
            EmrConstants.DOPPLER_ULTRASOUND_OF_RENAL_ARTERY,
            EmrConstants.DOPPLER_ULTRASOUND,
            EmrConstants.RENAL_ULTRA_SOUND,
            EmrConstants.FINGER_X__RAY_4TH_METATARSAL_RT_HAND,
            EmrConstants.ULTRASOUND_LEFT_HAND_FINGER,
            EmrConstants.NECK_ULTRA_SOUND,
            EmrConstants.THYROID_ULTRA_SOUND,
            EmrConstants.SCROTAL_ULTRA_SOUND,
            EmrConstants.PROSTATE_ULTRA_SOUND,
            EmrConstants.TESTICULAR_ULTRA_SOUND,
            EmrConstants.DOPPLER_UPPER_LIMB_ARTERIAL,
            EmrConstants.DOPPLER_LOWER_LIMB_ARTERIAL,
            EmrConstants.DOPPLER_CAROTID,
            EmrConstants.KNEE_ULTRA_SOUND,
            EmrConstants.REGIONAL_ULTRASOUND,
            EmrConstants.ULTRASOUND_GUIDED_FNA,
            EmrConstants.ULTRASOUND_GUIDED_BIOPSY_SUPERFICIAL,
            EmrConstants.ULTRASOUND_GUIDED_DEEP_BIOPSY,
            EmrConstants.FAST_ULTRASOUND,
            EmrConstants.INGUINAL_SCROTAL_ULTRASOUND,
            EmrConstants.INGUINAL_SCAN,
            EmrConstants.AXILLA_ULTRA_SOUND,
            EmrConstants.KUB_PROSTATE_ULTRASOUND,
            EmrConstants.KUB_ULTRASOUND,
            EmrConstants.IMAGE_GUIDED_CHEMOPORT_INSERTION_ADULT,
            EmrConstants.IMAGE_GUIDED_CHEMOPORT_INSERTION_PEADIATRIC,
            EmrConstants.IMAGE_GUIDED_CVC_INSERTION,
            EmrConstants.DOPPLER_UPPER_LIMB_VENOUS_LEFT,
            EmrConstants.DOPPLER_LOWER_LIMB_VENOUS_RIGHT,
            EmrConstants.ECHOGRAM,
            EmrConstants.US_GUIDED_PIGTAIL_DRAINAGE_OF_A_CYST_LIVER_RENAL_OR_MESENTERIC,
            EmrConstants.US_GUIDED_ASPIRATION_OF_A_CYST_OR_FLUID,
            EmrConstants.US_GUIDED_SIMPLE_CYST_DRAINAGE_RENAL_LIVER_SPLEEN,
            EmrConstants.US_GUIDED_TUMOUR_LOCALIZATION,
            EmrConstants.MUSCULOSKELETAL_ULTRASOUND,
            EmrConstants.DOPPLER_LOWER_LIMB_VENOUS_LEFT,
            EmrConstants.CRANIAL_ULTRA_SOUND,
            EmrConstants.ULTRASOUND_OF_BOTH_BREAST
    )));
    public static final String contrastEnhancedExaminationList = String.join(",",new ArrayList<>(Arrays.asList(
            EmrConstants.FLUOROSCOPY_ESOPHAGUS,
            EmrConstants.NUCLEAR_SCAN_TECHNETIUM_99MTC_SESTAMIBI,
            EmrConstants.RETROGRADE_PYELOGRAM,
            EmrConstants.FLUOROSCOPY,
            EmrConstants.ANGIOGRAM,
            EmrConstants.CHOLANGIOGRAM,
            EmrConstants.VOIDING_URETHROCYSTOGRAPHY,
            EmrConstants.INSERTION_OF_TUNNELED_DIALYSIS_CATHETER_USING_FLUOROSCOPIC_GUIDANCE,
            EmrConstants.PERINEOGRAM,
            EmrConstants.URETERAL_REFLUX_STUDY,
            EmrConstants.CENTRAL_VENOUS_LINE_PROCEDURE_USING_FLUOROSCOPIC_GUIDANCE,
            EmrConstants.INJECTION_OF_BLOOD_VESSEL_USING_FLUOROSCOPIC_GUIDANCE,
            EmrConstants.LAPAROSCOPY_WITH_GUIDED_TRANSHEPATIC_CHOLANGIOGRAPHY,
            EmrConstants.LAPAROSCOPY_WITH_GUIDED_TRANSHEPATIC_CHOLANGIOGRAPHY_AND_BIOPSY,
            EmrConstants.NEPHROSTOMY_USING_FLUOROSCOPIC_GUIDANCE,
            EmrConstants.RADIOGRAPHIC_IMAGING_NEPHROSTOMY,
            EmrConstants.REPLACEMENT_OF_DIALYSIS_CATHETER_USING_FLUOROSCOPIC_GUIDANCE,
            EmrConstants.REPLACEMENT_OF_NEPHROSTOMY_TUBE_USING_FLUOROSCOPIC_GUIDANCE,
            EmrConstants.CAROTID_ARTERIOGRAPHY_UNILATERAL,
            EmrConstants.CAROTID_ARTERIOGRAPHY_BILATERAL,
            EmrConstants.CEREBRAL_FOUR_VESSEL_ANGIOGRAM,
            EmrConstants.BARIUM_ENEMA,
            EmrConstants.ANGIOGRAPHYAORTA,
            EmrConstants.CONTRAST_MEDIA,
            EmrConstants.ANGIOGRAPHYBRAIN,
            EmrConstants.ANGIOGRAPHYEXTREMETIES,
            EmrConstants.ANGIOGRAPHYPULMONARY_CHEST,
            EmrConstants.HYSTEROSALPINGOGRAPHY,
            EmrConstants.IMAGE_GUIDED_DIALYSIS_CATHETER_INSERTION,
            EmrConstants.LOWER_UPPER_LIMB_ARTERIOGRAM_BILATERAL,
            EmrConstants.LOWER_LIMB_UPPER_LIMB_ARTERIOGRAM_UNILATERAL,
            EmrConstants.FLUSH_AORTOGRAM_RENAL_ARTERY_HEPATIC_WITH_EMBOLIZATION_MATERIAL_AND_MICROCATHET,
            EmrConstants.INTERVENTIONAL_RADIOLOGY_IR_REVIEW_OF_TUBES,
            EmrConstants.PERCUTANEOUS_TRANSHEPATIC_CHOLANGIOGRAPHY_PTC_WITH_BILIARY_DRAINAGE_TWO_TUBES,
            EmrConstants.INTERNALIZATION_OF_BILIARY_TUBE_NO_TUBES_AVAILABLE,
            EmrConstants.NEPHROSTOGRAM,
            EmrConstants.GASTROGRAFIN_SWALLOW_XRAY,
            EmrConstants.SINOGRAM,
            EmrConstants.HYSTEROSALPINGOGRAM_HSG,
            EmrConstants.MICTURATING_CYSTOURETHROGRAM_MCU
    )));
    public static final String magneticResonanceImagingList = String.join(",",new ArrayList<>(Arrays.asList(
            EmrConstants.MAGNETIC_RESONANCE_SPECTROSCOPY,
            EmrConstants.MAGNETIC_RESONANCE_IMAGING_OF_BRAIN_FUNCTIONAL,
            EmrConstants.MAGNETIC_RESONANCE_IMAGING_OF_ABDOMEN_WITHOUT_CONTRAST,
            EmrConstants.MAGNETIC_RESONANCE_IMAGING_OF_ABDOMEN_WITH_CONTRAST,
            EmrConstants.MAGNETIC_RESONANCE_IMAGING_MRI_MODALITY,
            EmrConstants.MAGNETIC_RESONANCE_IMAGING_OF_THORACOLUMBAR_SPINE,
            EmrConstants.MAGNETIC_RESONANCE_IMAGING_OF_LUMBOSACRAL_SPINE,
            EmrConstants.MAGNETIC_RESONANCE_IMAGING_OF_CERVICAL_SPINE,
            EmrConstants.MAGNETIC_RESONANCE_IMAGING_OF_BREAST,
            EmrConstants.MRI_HEAD_BRAIN_WR,
            EmrConstants.MRI_HEAD_BRAIN_WITHOUT_R,
            EmrConstants.MRI_KNEE_WR,
            EmrConstants.MRI_CERVICAL_SPINE_WR,
            EmrConstants.MRI_THORACIC_SPINE_WR,
            EmrConstants.MRI_LUMBAR_SPINE_WR
    )));
    public static final String computerizedTomographyList = String.join(",",new ArrayList<>(Arrays.asList(
            EmrConstants.COMPUTED_TOMOGRAPHY_SCAN_HEAD,
            EmrConstants.POSITRON_EMISSION_TOMOGRAPHY_SCAN,
            EmrConstants.ICTAL_BRAIN_SINGLE_PHOTON_EMISSION_COMPUTED_TOMOGRAPHY_SPECT_,
            EmrConstants.INTERICTAL_BRAIN_SINGLE_PHOTON_EMISSION_COMPUTED_TOMOGRAPHY_SPECT_,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_ABDOMEN_AND_PELVIS_WITHOUT_CONTRAST,
            EmrConstants.CT_SCAN_PELVIS,
            EmrConstants.CT_SCAN_CHEST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_BILATERAL_LOWER_EXTREMITIES_WITHOUT_CONTRAST,
            EmrConstants.COMPUTERIZED_TOMOGRAPHY_OF_RIGHT_UPPER_EXTREMITY_WITHOUT_CONTRAST,
            EmrConstants.COMPUTERIZED_TOMOGRAPHY_OF_LEFT_UPPER_EXTREMITY_WITHOUT_CONTRAST,
            EmrConstants.COMPUTERIZED_TOMOGRAPHY_OF_RIGHT_UPPER_EXTREMITY_WITH_IV_CONTRAST,
            EmrConstants.COMPUTERIZED_TOMOGRAPHY_OF_LEFT_UPPER_EXTREMITY_WITH_IV_CONTRAST,
            EmrConstants.COMPUTERIZED_TOMOGRAPHY_OF_FACIAL_BONES_AND_MAXILLA_WITHOUT_CONTRAST,
            EmrConstants.COMPUTERIZED_TOMOGRAPHY_OF_RIGHT_LOWER_EXTREMITY_WITHOUT_CONTRAST,
            EmrConstants.COMPUTERIZED_TOMOGRAPHY_OF_LEFT_LOWER_EXTREMITY_WITHOUT_CONTRAST,
            EmrConstants.COMPUTERIZED_TOMOGRAPHY_OF_LUMBAR_SPINE_WITHOUT_CONTRAST,
            EmrConstants.COMPUTERIZED_TOMOGRAPHY_OF_CERVICAL_SPINE_WITHOUT_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_BILATERAL_LOWER_EXTREMITIES_WITH_IV_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_CERVICAL_SPINE_WITH_INTRAVENOUS_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_CHEST_WITH_INTRAVENOUS_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_HEAD_WITH_INTRAVENOUS_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_LUMBAR_SPINE_WITH_INTRAVENOUS_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_RIGHT_LOWER_EXTREMITY_WITH_INTRAVENOUS_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_LEFT_LOWER_EXTREMITY_WITH_INTRAVENOUS_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_FACIAL_BONES_AND_MAXILLA_WITH_INTRAVENOUS_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_NECK_WITH_INTRAVENOUS_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_THORACIC_SPINE_WITH_INTRAVENOUS_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_ABDOMEN_AND_PELVIS_WITH_INTRAVENOUS_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_ABDOMEN_WITH_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_ABDOMEN_WITHOUT_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_CHEST_AND_ABDOMEN_WITHOUT_IV_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_CHEST_ABDOMEN_AND_PELVIS_WITHOUT_IV_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_CHEST_AND_ABDOMEN_WITH_IV_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_CHEST_ABDOMEN_AND_PELVIS_WITH_IV_CONTRAST,
            EmrConstants.NEPHROSTOMY_USING_COMPUTED_TOMOGRAPHY_GUIDANCE,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_HEART,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_KNEE,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_FEMUR,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_HIP,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_ORBITS_WITH_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_ORBITS_WITHOUT_CONTRAST,
            EmrConstants.CT_SCANS_WITHOUT_CONTRAST_CHEST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_BRAIN_WITHOUT_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_CHEST_WITHOUT_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_IVU_WITHOUT_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_OF_NECK_WITHOUT_CONTRAST,
            EmrConstants.COMPUTED_TOMOGRAPHY_WITHOUT_CONTRAST_PELVIMETRY,
            EmrConstants.CT_PARANASAL_SINUSES,
            EmrConstants.CT_ANGIOGRAM_WR,
            EmrConstants.CT_BRAIN_ANGIOGRAM_WR,
            EmrConstants.CT_EXTREMITIES_WR,
            EmrConstants.CT_GUIDED_ABCESS_DRAINAGE_WR,
            EmrConstants.CT_GUIDED_BIOPSY_LUNG_BIOPSY_WR,
            EmrConstants.CT_GUIDED_BONE_BIOPSY_WR,
            EmrConstants.CT_SCAN_ANKLE_WR,
            EmrConstants.CT_SCAN_CERVICAL_SPINE_WR,
            EmrConstants.CT_SCAN_CHEST_WITH_CONTRAST_WR,
            EmrConstants.CT_SCAN_HEAD_BRAIN_WR,
            EmrConstants.CT_SCAN_HIP_WR,
            EmrConstants.CT_SCAN_KNEE_WR,
            EmrConstants.CT_SCAN_LUMBER_SPINE_WR,
            EmrConstants.CT_SCAN_NECK_WITH_CONTRAST_WR,
            EmrConstants.CT_SCAN_PELVIC_TRAUMA_WR,
            EmrConstants.CT_SCAN_PELVIC_WITH_CONTRAST_WR,
            EmrConstants.CT_SCAN_PSN_WR,
            EmrConstants.CT_SCAN_SHOULDER_WR,
            EmrConstants.CT_SCAN_THORACIC_SPINE_WR,
            EmrConstants.CT_SCAN_UROGRAM_WR,
            EmrConstants.CT_SCAN_WRIST_WR,
            EmrConstants.HIGH_RESOLUTION_COMPUTED_TOMOGRAPHY_HRCT_CHEST_SCAN
    )));
    public static final String mammographyList = String.join(",",new ArrayList<>(Arrays.asList(
            EmrConstants.MAMMOGRAM,EmrConstants.MAMMARY_DUCTOGRAM
    )));
    public static final String obstetricUltrasoundList = String.join(",",new ArrayList<>(Arrays.asList(
            EmrConstants.OBSTETRIC_ULTRASOUND,EmrConstants.ULTRASOUND_OBSTETRIC_DOPPLER
    )));
    ColumnParameters femaleChildrenUnder5 = new ColumnParameters(null, "<5", "age=<5|gender=F");
    ColumnParameters maleChildrenUnder5 = new ColumnParameters(null, "<5", "age=<5|gender=M");
    ColumnParameters females5To59 = new ColumnParameters(null, "5-59, Female", "gender=F|age=5-59");
    ColumnParameters males5To59 = new ColumnParameters(null, "5-59, Male", "gender=M|age=5-59");
    ColumnParameters over60 = new ColumnParameters(null, ">60", "age=60+");

    ColumnParameters colTotal = new ColumnParameters(null, "Total", "");

    List<ColumnParameters> moh717Disaggregations = Arrays.asList(males5To59, females5To59,
            maleChildrenUnder5, femaleChildrenUnder5, over60, colTotal);

    @Autowired
    public MOH717ReportBuilder(Moh717CohortLibrary moh717CohortLibrary, CommonDimensionLibrary commonDimensionLibrary, Moh717IndicatorLibrary moh717IndicatorLibrary) {
        this.moh717CohortLibrary = moh717CohortLibrary;
        this.commonDimensionLibrary = commonDimensionLibrary;
        this.moh717IndicatorLibrary = moh717IndicatorLibrary;
    }

    @Override
    protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
        return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
                Date.class), new Parameter("dateBasedReporting", "", String.class));
    }

    @Override
    protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,ReportDefinition reportDefinition) {
        return Arrays.asList(ReportUtils.map(moh717DatasetDefinition(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(totalAmountCollectedDatasetDefinition(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(totalAmountReceivedDatasetDefinition(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(clientsWaivedDatasetDefinition(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(totalAmountWaivedDatasetDefinition(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(clientsExemptedDatasetDefinition(), "startDate=${startDate},endDate=${endDate}"),
                ReportUtils.map(totalAmountExemptedDatasetDefinition(), "startDate=${startDate},endDate=${endDate}")
                );
    }

    private DataSetDefinition moh717DatasetDefinition() {
        String indParams = "startDate=${startDate},endDate=${endDate}";

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        dsd.setName("MOH717");
        dsd.setDescription("MOH 717 Report");

        dsd.addDimension("age", map(commonDimensionLibrary.standardAgeGroups(), "onDate=${endDate}"));
        dsd.addDimension("gender", map(commonDimensionLibrary.gender(), ""));
        dsd.addDimension("state", map(commonDimensionLibrary.newOrRevisits(), "startDate=${startDate},endDate=${endDate}"));

        EmrReportingUtils.addRow(dsd, "General OP New", "",
                ReportUtils.map(moh717IndicatorLibrary.getPatientsWithNewClinicalEncounterWithinReportingPeriod(), indParams), moh717Disaggregations, Arrays.asList("01", "02", "03", "04", "05","06"));

        EmrReportingUtils.addRow(dsd, "General OP RE-ATT", "",
                ReportUtils.map(moh717IndicatorLibrary.getPatientsWithReturnClinicalEncounterWithinReportingPeriod(), indParams), moh717Disaggregations, Arrays.asList("01", "02", "03", "04", "05","06"));

        dsd.addColumn("New CWC Visits", "",
                ReportUtils.map(moh717IndicatorLibrary.newCWCVisits(), indParams), "");
        dsd.addColumn( "CWC RE-ATT", "",
                ReportUtils.map(moh717IndicatorLibrary.cwcRevisits(), indParams), "");
        dsd.addColumn( "New ANC Visits", "",
                ReportUtils.map(moh717IndicatorLibrary.newANCVisits(), indParams), "");
        dsd.addColumn( "ANC RE-ATT", "",
                ReportUtils.map(moh717IndicatorLibrary.ancRevisits(), indParams),"");
        dsd.addColumn("New PNC Visits", "",
                ReportUtils.map(moh717IndicatorLibrary.newPNCVisits(), indParams), "");
        dsd.addColumn( "PNC RE-ATT", "",
                ReportUtils.map(moh717IndicatorLibrary.pncReVisits(), indParams), "");
        dsd.addColumn( "FP NEW", "",
                ReportUtils.map(moh717IndicatorLibrary.fpVisit(NEW_VISIT), indParams), "");
        dsd.addColumn( "FP RE-ATT", "",
                ReportUtils.map(moh717IndicatorLibrary.fpVisit(FP_RE_ATT), indParams), "");
        // Maternity Services
        dsd.addColumn( "Normal Deliveries", "", ReportUtils.map(moh717IndicatorLibrary.normalDeliveries(), indParams), "");
        dsd.addColumn( "Caesarean sections", "", ReportUtils.map(moh717IndicatorLibrary.caesareanSections(), indParams), "");
        dsd.addColumn( "Breech Deliveries", "", ReportUtils.map(moh717IndicatorLibrary.breechDeliveries(), indParams), "");
        dsd.addColumn( "Assisted Vaginal Delivery", "", ReportUtils.map(moh717IndicatorLibrary.assistedVaginalDelivery(), indParams), "");
        dsd.addColumn( "Born before arrival", "", ReportUtils.map(moh717IndicatorLibrary.bornBeforeArrival(), indParams), "");
        dsd.addColumn( "Maternal deaths", "", ReportUtils.map(moh717IndicatorLibrary.maternalDeaths(), indParams), "");
        dsd.addColumn( "Maternal deaths Audited", "", ReportUtils.map(moh717IndicatorLibrary.maternalDeathsAudited(), indParams), "");
        dsd.addColumn( "Live births", "", ReportUtils.map(moh717IndicatorLibrary.liveBirths(), indParams), "");
        dsd.addColumn( "Still births", "", ReportUtils.map(moh717IndicatorLibrary.stillBirths(), indParams), "");
        dsd.addColumn( "Neonatal deaths", "", ReportUtils.map(moh717IndicatorLibrary.neonatalDeaths(), indParams), "");
        dsd.addColumn( "Low Birth weight babies", "", ReportUtils.map(moh717IndicatorLibrary.lowBirthWeightBabies(), indParams), "");
        dsd.addColumn( "Total Discharges (new born)", "", ReportUtils.map(moh717IndicatorLibrary.totalDischarges(), indParams), "");

        dsd.addColumn( "Number of Laboratory tests", "", ReportUtils.map(moh717IndicatorLibrary.laboratoryTests(), indParams), "");

        // Special Clinics
        dsd.addColumn( "ENT Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.EAR_NOSE_THROAT_CLINICAL_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "ENT Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.EAR_NOSE_THROAT_CLINICAL_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Eye Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.OPHTHAMOLOGY_CLINICAL_FORM,NEW_VISIT), indParams), "");
        dsd.addColumn( "Eye Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.OPHTHAMOLOGY_CLINICAL_FORM,RE_ATT), indParams), "");
        dsd.addColumn( "TB and Leprosy Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.newOnTBClinic(), indParams), "");
        dsd.addColumn( "TB and Leprosy Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.revisitTBClinic(), indParams), "");
        dsd.addColumn( "CCC Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.newOnCCClinic(), indParams), "");
        dsd.addColumn( "CCC Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.revisitCCClinic(), indParams), "");
        dsd.addColumn( "Psychiatry Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.PSYCHIATRIC_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "Psychiatry Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.PSYCHIATRIC_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Orthopaedic Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.ORTHOPAEDIC_CLINICAL_FORM,NEW_VISIT), indParams), "");
        dsd.addColumn( "Orthopaedic Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.ORTHOPAEDIC_CLINICAL_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Occupational Therapy Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.OCCUPATIONAL_THERAPY_CLINICAL_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "Occupational Therapy Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.OCCUPATIONAL_THERAPY_CLINICAL_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Physiotherapy Therapy Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.PHYSIOTHERAPY_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "Physiotherapy Therapy Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.PHYSIOTHERAPY_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Medical Clinics (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.MOPC_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "Medical Clinics (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.MOPC_FORM,RE_ATT), indParams), "");
        dsd.addColumn( "Surgical Clinics (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.SOPC_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "Surgical Clinics (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.SOPC_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Paediatrics (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.POPC_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "Paediatrics (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.POPC_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Obstetrics and Gynaecology (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.GOPC_FORM,NEW_VISIT), indParams), "");
        dsd.addColumn( "Obstetrics and Gynaecology (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.GOPC_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Nutrition Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.NUTRITION, NEW_VISIT), indParams), "");
        dsd.addColumn( "Nutrition Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.NUTRITION, RE_ATT), indParams), "");
        dsd.addColumn( "Oncology Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.ONCOLOGY_FORM, NEW_VISIT), indParams), "");
        dsd.addColumn( "Oncology Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.ONCOLOGY_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "Renal Clinic (New)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.RENAL_CLINICAL_FORM,NEW_VISIT), indParams), "");
        dsd.addColumn( "Renal Clinic (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.specialClinic(CommonMetadata._Form.RENAL_CLINICAL_FORM, RE_ATT), indParams), "");
        dsd.addColumn( "All other special Clinics (New)", "", ReportUtils.map(moh717IndicatorLibrary.otherSpecialClinics(EmrUtils.formatListWithQuotes(otherSpecialClinics),NEW_VISIT), indParams), "");
        dsd.addColumn( "All other special Clinics (Re_Att)", "", ReportUtils.map(moh717IndicatorLibrary.otherSpecialClinics(EmrUtils.formatListWithQuotes(otherSpecialClinics),RE_ATT), indParams), "");

        dsd.addColumn( "Dental Attendances (New)", "", ReportUtils.map(moh717IndicatorLibrary.dentalAttendances(dentalExtractionList, dentalFillingList,NEW_VISIT), indParams), "");
        dsd.addColumn( "Dental Attendances (RE_ATT)", "", ReportUtils.map(moh717IndicatorLibrary.dentalAttendances(dentalExtractionList,dentalFillingList,RE_ATT), indParams), "");
        dsd.addColumn( "Dental Fillings (New)", "", ReportUtils.map(moh717IndicatorLibrary.dentalFillings(dentalFillingList,NEW_VISIT), indParams), "");
        dsd.addColumn( "Dental Fillings (RE_ATT)", "", ReportUtils.map(moh717IndicatorLibrary.dentalFillings(dentalFillingList,RE_ATT), indParams), "");
        dsd.addColumn( "Dental Extractions (New)", "", ReportUtils.map(moh717IndicatorLibrary.dentalExtractions(dentalExtractionList,NEW_VISIT), indParams), "");
        dsd.addColumn( "Dental Extractions (RE_ATT)", "", ReportUtils.map(moh717IndicatorLibrary.dentalExtractions(dentalExtractionList,RE_ATT), indParams), "");
        dsd.addColumn( "Dressings", "", ReportUtils.map(moh717IndicatorLibrary.dressings(dressingList), indParams), "");
        dsd.addColumn( "Removal of Stitches", "", ReportUtils.map(moh717IndicatorLibrary.removalOfStitches(stitchesRemovalList), indParams), "");
        dsd.addColumn( "Injections", "", ReportUtils.map(moh717IndicatorLibrary.injections(injectionsList), indParams), "");
        dsd.addColumn( "Stitching", "", ReportUtils.map(moh717IndicatorLibrary.stitching(stitchingList), indParams), "");
        dsd.addColumn( "Physiotherapy Treatments", "", ReportUtils.map(moh717IndicatorLibrary.specialClinics(PHYSIOTHERAPY_FORM), indParams), "");
        dsd.addColumn( "Occupational Therapy Treatments", "", ReportUtils.map(moh717IndicatorLibrary.specialClinics(OCCUPATIONAL_THERAPY_FORM), indParams), "");
        dsd.addColumn( "Plain XRay without contrast enhancement", "", ReportUtils.map(moh717IndicatorLibrary.xrayAndImaging(plainXRayList), indParams), "");
        dsd.addColumn( "General ultrasound", "", ReportUtils.map(moh717IndicatorLibrary.xrayAndImaging(generalUltrasoundList), indParams), "");
        dsd.addColumn( "Contrast enhanced examinations", "", ReportUtils.map(moh717IndicatorLibrary.xrayAndImaging(contrastEnhancedExaminationList), indParams), "");
        dsd.addColumn( "Magnetic Resonance Imaging", "", ReportUtils.map(moh717IndicatorLibrary.xrayAndImaging(magneticResonanceImagingList), indParams), "");
        dsd.addColumn( "Computerized Tomography", "", ReportUtils.map(moh717IndicatorLibrary.xrayAndImaging(computerizedTomographyList), indParams), "");
        dsd.addColumn( "Mammography", "", ReportUtils.map(moh717IndicatorLibrary.xrayAndImaging(mammographyList), indParams), "");
        dsd.addColumn( "Obstetric ultrasound", "", ReportUtils.map(moh717IndicatorLibrary.xrayAndImaging(obstetricUltrasoundList), indParams), "");

        return dsd;
    }
    private DataSetDefinition totalAmountCollectedDatasetDefinition(){
        SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
        sqlDataSetDefinition.setName("AmountCollected");
        sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        sqlDataSetDefinition.setSqlQuery("select CAST(IFNULL(sum(ifnull(r.total_sales, 0)), 0) AS SIGNED) as total_amount_collected\n" +
                "from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sqlDataSetDefinition;
    }
    private DataSetDefinition totalAmountReceivedDatasetDefinition(){
        SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
        sqlDataSetDefinition.setName("AmountReceived");
        sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        sqlDataSetDefinition.setSqlQuery("SELECT CAST(\n" +
                "               IFNULL(\n" +
                "                       SUM(\n" +
                "                               IFNULL(r.cash_receipts_cash_from_daily_services, 0) +\n" +
                "                               IFNULL(r.cash_receipt_nhif_receipt, 0) +\n" +
                "                               IFNULL(r.cash_receipt_other_debtors_receipt, 0)\n" +
                "                       ),\n" +
                "                       0) AS SIGNED\n" +
                "       ) AS total_amount_received\n" +
                "FROM kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "WHERE DATE(transaction_date) BETWEEN DATE(:startDate) AND DATE(:endDate);");
        return sqlDataSetDefinition;
    }
    private DataSetDefinition clientsWaivedDatasetDefinition(){
        SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
        sqlDataSetDefinition.setName("clientsWaived");
        sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        sqlDataSetDefinition.setSqlQuery("select CAST(IFNULL(count(ifnull(r.revenue_not_collected_patient_not_yet_paid_waivers, 0)),\n" +
                "                   0) AS SIGNED) as clients_waived\n" +
                "from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sqlDataSetDefinition;
    }
    private DataSetDefinition totalAmountWaivedDatasetDefinition(){
        SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
        sqlDataSetDefinition.setName("AmountWaived");
        sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        sqlDataSetDefinition.setSqlQuery("select CAST(ifnull(sum(ifnull(r.revenue_not_collected_patient_not_yet_paid_waivers, 0)), 0) AS SIGNED) as total_waived\n" +
                "from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sqlDataSetDefinition;
    }
    private DataSetDefinition clientsExemptedDatasetDefinition(){
        SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
        sqlDataSetDefinition.setName("clientsExempted");
        sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        sqlDataSetDefinition.setSqlQuery("select CAST(ifnull(count(ifnull(r.revenue_not_collected_write_offs_exemptions, 0)), 0) AS SIGNED) as clients_exempted\n" +
                "from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sqlDataSetDefinition;
    }
    private DataSetDefinition totalAmountExemptedDatasetDefinition(){
        SqlDataSetDefinition sqlDataSetDefinition = new SqlDataSetDefinition();
        sqlDataSetDefinition.setName("AmountExempted");
        sqlDataSetDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
        sqlDataSetDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
        sqlDataSetDefinition.setSqlQuery("select CAST(IFNULL(sum(ifnull(r.revenue_not_collected_write_offs_exemptions, 0)), 0) AS SIGNED) as total_exempted\n" +
                "from kenyaemr_etl.etl_daily_revenue_summary r\n" +
                "where date(transaction_date) between date(:startDate) and date(:endDate);");
        return sqlDataSetDefinition;
    }
    
}
