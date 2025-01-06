/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.cohort.definition.dmi.casereport;

import org.openmrs.Visit;
import org.openmrs.module.reporting.common.Localized;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;
import org.openmrs.module.reporting.definition.configuration.ConfigurationPropertyCachingStrategy;
import org.openmrs.module.reporting.evaluation.caching.Caching;
import org.openmrs.module.reporting.query.BaseQuery;
import org.openmrs.module.reporting.query.visit.definition.VisitQuery;

import java.util.Date;

/**
 * Surveillance cases cohort definition
 */

@Caching(strategy = ConfigurationPropertyCachingStrategy.class)
@Localized("reporting.CaseDiagnosisCohortDefinition")
public class CaseDiagnosisCohortDefinition extends BaseQuery<Visit> implements VisitQuery {
    @ConfigurationProperty
    private Date asOfDate;

    public CaseDiagnosisCohortDefinition() {
    }

    public Date getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Date asOfDate) {
        this.asOfDate = asOfDate;
    }

}