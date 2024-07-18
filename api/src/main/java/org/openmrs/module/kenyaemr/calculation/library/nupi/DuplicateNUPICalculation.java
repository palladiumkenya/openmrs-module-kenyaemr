/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.kenyaemr.calculation.library.nupi;

import java.util.Collection;
import java.util.Map;

import org.openmrs.Patient;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;

/**
 * Calculate whether a patient has high IIT risk score based on data pulled from NDWH
 */
public class DuplicateNUPICalculation extends AbstractPatientCalculation {
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(Collection, Map,
	 *      PatientCalculationContext)
	 * @should determine whether a patient has high IIT risk
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
		
		PersonAttributeType duplicateStatusPA = Context.getPersonService().getPersonAttributeTypeByUuid(CommonMetadata._PersonAttributeType.DUPLICATE_NUPI_STATUS_WITH_NATIONAL_REGISTRY);
		PatientService patientService = Context.getPatientService();
		CalculationResultMap ret = new CalculationResultMap();

		try {
			for(Integer ptId: cohort){
				Patient patient = patientService.getPatient(ptId);
				try {
					if (patient.getAttribute(duplicateStatusPA) != null) {
						// Has a duplicate?
						if (patient.getAttribute(duplicateStatusPA).getValue().trim().equalsIgnoreCase("true")) {
							ret.put(patient.getId(), new BooleanResult(true, this, context));
						}
					}
				} catch(Exception em) {
					System.err.println("Duplicate NUPI Error reporting: " + em.getMessage());
					em.printStackTrace();
				}
			}
		} catch(Exception ex) {
			System.err.println("Duplicate NUPI Error reporting: " + ex.getMessage());
			ex.printStackTrace();
		}
		
		return ret;
	}
	
}

