/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.cohort.definition.specialClinics;

import org.openmrs.Encounter;
import org.openmrs.module.reporting.common.Localized;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;
import org.openmrs.module.reporting.definition.configuration.ConfigurationPropertyCachingStrategy;
import org.openmrs.module.reporting.evaluation.caching.Caching;
import org.openmrs.module.reporting.query.BaseQuery;
import org.openmrs.module.reporting.query.encounter.definition.EncounterQuery;

import java.util.Date;

/**
 */
@Caching(strategy = ConfigurationPropertyCachingStrategy.class)
@Localized("reporting.SpecialClinicsRegisterCohortDefinition")
public class SpecialClinicsRegisterCohortDefinition extends BaseQuery<Encounter> implements EncounterQuery {
	@ConfigurationProperty
	private Date asOfDate;
	@ConfigurationProperty
	private String specialClinic;
	@ConfigurationProperty
	private String comparisonOperator;
	@ConfigurationProperty
	private Integer age;



	public SpecialClinicsRegisterCohortDefinition() {
	}

	public Date getAsOfDate() {
		return asOfDate;
	}

	public void setAsOfDate(Date asOfDate) {
		this.asOfDate = asOfDate;
	}
	public String getSpecialClinic() {
		return specialClinic;
	}

	public void setSpecialClinic(String specialClinic) {
		this.specialClinic = specialClinic;
	}

	public String getComparisonOperator() {
		return comparisonOperator;
	}

	public void setComparisonOperator(String comparisonOperator) {
		this.comparisonOperator = comparisonOperator;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
