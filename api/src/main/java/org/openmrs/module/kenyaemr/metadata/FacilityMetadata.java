/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.metadata;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Location;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.customdatatype.datatype.FreeTextDatatype;
import org.openmrs.customdatatype.datatype.RegexValidatedTextDatatype;
import org.openmrs.module.kenyaemr.EmrConstants;
import org.openmrs.module.kenyaemr.metadata.sync.LocationMflCsvSource;
import org.openmrs.module.kenyaemr.metadata.sync.LocationMflSynchronization;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.metadatadeploy.bundle.Requires;
import org.openmrs.module.metadatadeploy.source.ObjectSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.locationAttributeType;

/**
 * Locations metadata bundle
 */
@Component
@Requires({ CommonMetadata.class })
public class FacilityMetadata extends AbstractMetadataBundle {

	@Autowired
	private LocationMflSynchronization mflSynchronization;

	public static final class _Location {
		public static final String UNKNOWN = "8d6c993e-c2cc-11de-8d13-0010c6dffd0f";
	}

	public static final class _LocationAttributeType {
		public static final String MASTER_FACILITY_CODE = "8a845a89-6aa5-4111-81d3-0af31c45c002";
		public static final String TELEPHONE_LANDLINE = "4ecb5b3f-1518-4056-a266-c4da1def45f5";
		public static final String TELEPHONE_MOBILE = "8760f471-b2bb-4ded-8970-badf95d3bb44";
		public static final String TELEPHONE_FAX = "29e1e758-d03e-4e84-a55e-288fa63d533a";
		public static final String SHA_ACCREDITATION = "7dbbfe5d-8a5a-4b24-897d-0cc5299c3dbb";
		public static final String SHA_CONTRACTED_FACILITY = "68d9200e-a469-482f-8cc8-9d0953a3c917";
	}

	/**
	 * @see org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle#install()
	 */
	@Override
	public void install() throws Exception {
		AdministrationService administrationService = Context.getAdministrationService();
		final String refreshConfig = (administrationService.getGlobalProperty(EmrConstants.GP_CONFIGURE_FACILITY_LIST_REFRESH_ON_STARTUP));

		if (StringUtils.isNotEmpty(refreshConfig) && refreshConfig.equalsIgnoreCase("true")) {
			install(true);
		} else {
			System.out.println("Skipping refreshing of the facility list ...");
		}
		install(locationAttributeType(
				"SHA Facility Operational Status", "SHA accredited verification status",
				FreeTextDatatype.class, "", 0, 1,
				_LocationAttributeType.SHA_ACCREDITATION
		));

		install(locationAttributeType(
				"Contracted by SHA", "Facility contracted by SHA",
				FreeTextDatatype.class, "", 0, 1,
				_LocationAttributeType.SHA_CONTRACTED_FACILITY
		));
	}

	/**
	 * Provides an install method we can use from unit tests when we don't want to sync the entire facility list
	 * @param full whether or not to run the facility sync
	 * @throws Exception
	 */
	public void install(boolean full) throws Exception {
		install(locationAttributeType(
				"Master Facility Code", "Unique facility code allocated by the Ministry of Health",
				RegexValidatedTextDatatype.class, "\\d{5}", 0, 1,
				_LocationAttributeType.MASTER_FACILITY_CODE
		));

		install(locationAttributeType(
				"Official Landline", "Landline telephone contact number",
				FreeTextDatatype.class, "", 0, 1,
				_LocationAttributeType.TELEPHONE_LANDLINE
		));

		install(locationAttributeType(
				"Official Mobile", "Mobile telephone contact number",
				FreeTextDatatype.class, "", 0, 1,
				_LocationAttributeType.TELEPHONE_MOBILE
		));

		install(locationAttributeType(
				"Official Fax", "Fax telephone number",
				FreeTextDatatype.class, "", 0, 1,
				_LocationAttributeType.TELEPHONE_FAX
		));

		if (full) {
			ObjectSource<Location> source = new LocationMflCsvSource("metadata/mfl_2014-05-12.csv");
			sync(source, mflSynchronization);
		}
	}
}