/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.data.converter;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.module.reporting.data.converter.DataConverter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EncounterDatetimeConverter implements DataConverter {

    public Object convert(Object original) {
        Encounter encounter = (Encounter) original;

        if (encounter == null) {
            return null;
        } else if (encounter.getDateCreated() != null) {
            return formatDates(encounter.getDateCreated(), "dd-MM-yyyy HH:mm:ss");
        }
        return null;
    }

    public Class<?> getInputDataType() {
        return Encounter.class;
    }

    public Class<?> getDataType() {
        return Object.class;
    }

    public static String formatDates(Date date, String format) {
        Format formatter = new SimpleDateFormat(format);
        String s = formatter.format(date);
        return s;
    }
}
