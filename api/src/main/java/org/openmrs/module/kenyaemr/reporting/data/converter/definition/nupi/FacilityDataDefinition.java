/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter.definition.nupi;

import org.openmrs.PersonAttributeType;
import org.openmrs.module.reporting.data.BaseDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;

public class FacilityDataDefinition extends BaseDataDefinition implements PersonDataDefinition {

    @ConfigurationProperty
	private PersonAttributeType personAttributeType;

    public FacilityDataDefinition() {
    }

    public FacilityDataDefinition(String name, PersonAttributeType personAttributeType) {
        super.setName(name);
        this.personAttributeType = personAttributeType;
    }

    @Override
    public Class<?> getDataType() {
        return String.class;
    }

    public PersonAttributeType getPersonAttributeType() {
        return personAttributeType;
    }

    public void setPersonAttributeType(PersonAttributeType personAttributeType) {
        this.personAttributeType = personAttributeType;
    }
}
