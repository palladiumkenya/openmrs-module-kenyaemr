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
import org.openmrs.module.kenyaemr.metadata.SARIMetadata;
import org.openmrs.module.kenyaemr.metadata.VMMCMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;

public class SariConstants {
    public static final EncounterType EncType = MetadataUtils.existing(EncounterType.class, SARIMetadata._EncounterType.SARI_ENROLLMENT);
    public static final Form sariExitForm = MetadataUtils.existing(Form.class, SARIMetadata._Form.SARI_EXIT_FORM);
    public static final EncounterType sariExitEncType = MetadataUtils.existing(EncounterType.class, SARIMetadata._EncounterType.SARI_EXIT);
    public static final Form sariFollowUpForm = MetadataUtils.existing(Form.class, SARIMetadata._Form.SARI_FOLLOW_UP_FORM);
    public static final EncounterType sariFollowUpEncType = MetadataUtils.existing(EncounterType.class, SARIMetadata._EncounterType.SARI_FOLLOW_UP);
    public static final Form sariliScreenngForm = MetadataUtils.existing(Form.class, SARIMetadata._Form.SARI_ILI_SCREENING_FORM);
    public static final EncounterType sariliScreeningEncType = MetadataUtils.existing(EncounterType.class, SARIMetadata._EncounterType.SARI_ILI_SCREENING);

}
