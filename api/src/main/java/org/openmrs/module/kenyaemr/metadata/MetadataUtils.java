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

import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for metadata operations
 */
public class MetadataUtils {

	private static final Logger logger = LoggerFactory.getLogger(MetadataUtils.class);
	
	/**
	 * Global property key for enabling/disabling form installation
	 */
	public static final String GP_ENABLE_FORMS = "kenyaemr.enable.forms";

	/**
	 * Private constructor to prevent instantiation of utility class
	 */
	private MetadataUtils() {
		// Utility class - no instantiation
	}

	/**
	 * Checks if forms should be installed based on the global property.
	 * If the global property doesn't exist, defaults to true for backward compatibility.
	 * 
	 * @return true if forms should be installed, false otherwise
	 */
	public static boolean shouldInstallForms() {
		logger.info("=== shouldInstallForms() method called ===");
		
		try {
			AdministrationService administrationService = Context.getAdministrationService();
			if (administrationService == null) {
				logger.error("AdministrationService is null! Cannot check GP_ENABLE_FORMS. Defaulting to true.");
				return true;
			}
			
			logger.info("AdministrationService retrieved successfully. Checking global property: {}", GP_ENABLE_FORMS);
			String propertyValue = administrationService.getGlobalProperty(GP_ENABLE_FORMS);
			
			logger.info("Checking GP_ENABLE_FORMS ({}): raw value = '{}'", GP_ENABLE_FORMS, propertyValue);
			
			if (propertyValue == null || propertyValue.trim().isEmpty()) {
				// Default to true if property doesn't exist (backward compatibility)
				logger.info("GP_ENABLE_FORMS is null or empty, defaulting to true (backward compatibility)");
				return true;
			}
			
			String trimmedValue = propertyValue.trim();
			boolean shouldInstall = trimmedValue.equalsIgnoreCase("true");
			
			logger.info("GP_ENABLE_FORMS trimmed value = '{}', shouldInstallForms = {}", trimmedValue, shouldInstall);
			
			return shouldInstall;
		} catch (Exception e) {
			logger.error("Exception occurred while checking GP_ENABLE_FORMS: {}", e.getMessage(), e);
			logger.error("Defaulting to true due to exception");
			return true;
		}
	}
}
