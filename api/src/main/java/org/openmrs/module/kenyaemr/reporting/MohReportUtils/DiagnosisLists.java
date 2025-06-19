/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.MohReportUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openmrs.Concept;

import static org.openmrs.module.kenyaemr.reporting.MohReportUtils.DiagnosisConcepts.getConcept;


public class DiagnosisLists {





	public static List<Integer> getDiarrheaWithNoDehydrationDiagnosisList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.DWND_1,
			DiagnosisConcepts._DiagnosisConcepts.DWND_2);

	}

	public static List<Integer> getGastroenteritisList() {
		return Arrays
			.asList(DiagnosisConcepts._DiagnosisConcepts.GAS_1,
				DiagnosisConcepts._DiagnosisConcepts.GAS_2,
				DiagnosisConcepts._DiagnosisConcepts.GAS_3,
				DiagnosisConcepts._DiagnosisConcepts.GAS_4,
				DiagnosisConcepts._DiagnosisConcepts.GAS_5,
				DiagnosisConcepts._DiagnosisConcepts.GAS_6,
				DiagnosisConcepts._DiagnosisConcepts.GAS_7,
				DiagnosisConcepts._DiagnosisConcepts.GAS_8,
				DiagnosisConcepts._DiagnosisConcepts.GAS_9,
				DiagnosisConcepts._DiagnosisConcepts.GAS_10,
				DiagnosisConcepts._DiagnosisConcepts.GAS_11,
				DiagnosisConcepts._DiagnosisConcepts.GAS_12,
				DiagnosisConcepts._DiagnosisConcepts.GAS_13,
				DiagnosisConcepts._DiagnosisConcepts.GAS_14,
				DiagnosisConcepts._DiagnosisConcepts.GAS_15,
				DiagnosisConcepts._DiagnosisConcepts.GAS_16,
				DiagnosisConcepts._DiagnosisConcepts.GAS_17,
				DiagnosisConcepts._DiagnosisConcepts.GAS_18,
				DiagnosisConcepts._DiagnosisConcepts.GAS_19,
				DiagnosisConcepts._DiagnosisConcepts.GAS_20,
				DiagnosisConcepts._DiagnosisConcepts.GAS_21,
				DiagnosisConcepts._DiagnosisConcepts.GAS_22,
				DiagnosisConcepts._DiagnosisConcepts.GAS_23,
				DiagnosisConcepts._DiagnosisConcepts.GAS_24,
				DiagnosisConcepts._DiagnosisConcepts.GAS_25,
				DiagnosisConcepts._DiagnosisConcepts.GAS_26

			);
	}

	public static List<Integer> getSeverePneumoniaList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.SP_1,
			DiagnosisConcepts._DiagnosisConcepts.SP_2,
			DiagnosisConcepts._DiagnosisConcepts.SP_3,
			DiagnosisConcepts._DiagnosisConcepts.SP_4,
			DiagnosisConcepts._DiagnosisConcepts.SP_5,
			DiagnosisConcepts._DiagnosisConcepts.SP_6,
			DiagnosisConcepts._DiagnosisConcepts.SP_7,
			DiagnosisConcepts._DiagnosisConcepts.SP_8,
			DiagnosisConcepts._DiagnosisConcepts.SP_9,
			DiagnosisConcepts._DiagnosisConcepts.SP_10
		);
	}

	public static List<Integer> getLowerTractInfectionList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.LTI_1,
			DiagnosisConcepts._DiagnosisConcepts.LTI_2,
			DiagnosisConcepts._DiagnosisConcepts.LTI_3
		);
	}

	public static List<Integer> getUpperRespiratoryTractInfectionsList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.URC_1,
			DiagnosisConcepts._DiagnosisConcepts.URC_2,
			DiagnosisConcepts._DiagnosisConcepts.URC_3,
			DiagnosisConcepts._DiagnosisConcepts.URC_4,
			DiagnosisConcepts._DiagnosisConcepts.URC_5,
			DiagnosisConcepts._DiagnosisConcepts.URC_6
		);
	}

	public static List<Integer> getPresumedTuberculosisList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.TCP_1,
			DiagnosisConcepts._DiagnosisConcepts.TCP_2

		);
	}

	public static List<Integer> getMalariaList() {
		return Arrays
			.asList(
				DiagnosisConcepts._DiagnosisConcepts.CMC_1,
				DiagnosisConcepts._DiagnosisConcepts.CMC_2,
				DiagnosisConcepts._DiagnosisConcepts.CMC_3,
				DiagnosisConcepts._DiagnosisConcepts.CMC_4
			);
	}

	public static List<Integer> getEarInfectionsConditionsList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.ERA1, DiagnosisConcepts._DiagnosisConcepts.ERA2,
			DiagnosisConcepts._DiagnosisConcepts.ERA3, DiagnosisConcepts._DiagnosisConcepts.ERA4,
			DiagnosisConcepts._DiagnosisConcepts.ERA5, DiagnosisConcepts._DiagnosisConcepts.ERA6,
			DiagnosisConcepts._DiagnosisConcepts.ERA7);
	}


	public static List<Integer> getOtherMenigitisList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.OMC_1,
			DiagnosisConcepts._DiagnosisConcepts.OMC_2,
			DiagnosisConcepts._DiagnosisConcepts.OMC_3,
			DiagnosisConcepts._DiagnosisConcepts.OMC_4,
			DiagnosisConcepts._DiagnosisConcepts.OMC_5,
			DiagnosisConcepts._DiagnosisConcepts.OMC_6,
			DiagnosisConcepts._DiagnosisConcepts.OMC_7,
			DiagnosisConcepts._DiagnosisConcepts.OMC_8,
			DiagnosisConcepts._DiagnosisConcepts.OMC_9,
			DiagnosisConcepts._DiagnosisConcepts.OMC_10,
			DiagnosisConcepts._DiagnosisConcepts.OMC_11,
			DiagnosisConcepts._DiagnosisConcepts.OMC_12,
			DiagnosisConcepts._DiagnosisConcepts.OMC_13,
			DiagnosisConcepts._DiagnosisConcepts.OMC_14,
			DiagnosisConcepts._DiagnosisConcepts.OMC_15,
			DiagnosisConcepts._DiagnosisConcepts.OMC_16,
			DiagnosisConcepts._DiagnosisConcepts.OMC_17,
			DiagnosisConcepts._DiagnosisConcepts.OMC_18,
			DiagnosisConcepts._DiagnosisConcepts.OMC_19,
			DiagnosisConcepts._DiagnosisConcepts.OMC_20,
			DiagnosisConcepts._DiagnosisConcepts.OMC_21,
			DiagnosisConcepts._DiagnosisConcepts.OMC_22,
			DiagnosisConcepts._DiagnosisConcepts.OMC_23,
			DiagnosisConcepts._DiagnosisConcepts.OMC_24,
			DiagnosisConcepts._DiagnosisConcepts.OMC_25,
			DiagnosisConcepts._DiagnosisConcepts.OMC_26,
			DiagnosisConcepts._DiagnosisConcepts.OMC_27,
			DiagnosisConcepts._DiagnosisConcepts.OMC_28,
			DiagnosisConcepts._DiagnosisConcepts.OMC_29,
			DiagnosisConcepts._DiagnosisConcepts.OMC_30,
			DiagnosisConcepts._DiagnosisConcepts.OMC_31,
			DiagnosisConcepts._DiagnosisConcepts.OMC_32,
			DiagnosisConcepts._DiagnosisConcepts.OMC_33,
			DiagnosisConcepts._DiagnosisConcepts.OMC_34,
			DiagnosisConcepts._DiagnosisConcepts.OMC_35,
			DiagnosisConcepts._DiagnosisConcepts.OMC_36,
			DiagnosisConcepts._DiagnosisConcepts.OMC_37,
			DiagnosisConcepts._DiagnosisConcepts.OMC_38,
			DiagnosisConcepts._DiagnosisConcepts.OMC_39,
			DiagnosisConcepts._DiagnosisConcepts.OMC_40,
			DiagnosisConcepts._DiagnosisConcepts.OMC_41,
			DiagnosisConcepts._DiagnosisConcepts.OMC_42,
			DiagnosisConcepts._DiagnosisConcepts.OMC_43,
			DiagnosisConcepts._DiagnosisConcepts.OMC_44

		);
	}

	public static List<Integer> getPoliomyelitisList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.PMC_1,
			DiagnosisConcepts._DiagnosisConcepts.PMC_2

		);
	}
	public static List<Integer> getChickenPoxList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.CPC_1, 
			DiagnosisConcepts._DiagnosisConcepts.CPC_2,
			DiagnosisConcepts._DiagnosisConcepts.CPC_3,
			DiagnosisConcepts._DiagnosisConcepts.CPC_4,
			DiagnosisConcepts._DiagnosisConcepts.CPC_5, 
			DiagnosisConcepts._DiagnosisConcepts.CPC_6,
			DiagnosisConcepts._DiagnosisConcepts.CPC_7, 
			DiagnosisConcepts._DiagnosisConcepts.CPC_8);
	}

	public static List<Integer> getMeaslesList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.MSC_1, 
			DiagnosisConcepts._DiagnosisConcepts.MSC_2,
			DiagnosisConcepts._DiagnosisConcepts.MSC_3, 
			DiagnosisConcepts._DiagnosisConcepts.MSC_4,
			DiagnosisConcepts._DiagnosisConcepts.MSC_5, 
			DiagnosisConcepts._DiagnosisConcepts.MSC_6

		);
	}

	public static List<Integer> getHepatitisList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.HPC_1, 
			DiagnosisConcepts._DiagnosisConcepts.HPC_2,
			DiagnosisConcepts._DiagnosisConcepts.HPC_3,
			DiagnosisConcepts._DiagnosisConcepts.HPC_4,
			DiagnosisConcepts._DiagnosisConcepts.HPC_5, 
			DiagnosisConcepts._DiagnosisConcepts.HPC_6,
			DiagnosisConcepts._DiagnosisConcepts.HPC_7, 
			DiagnosisConcepts._DiagnosisConcepts.HPC_8,
			DiagnosisConcepts._DiagnosisConcepts.HPC_9, 
			DiagnosisConcepts._DiagnosisConcepts.HPC_10,
			DiagnosisConcepts._DiagnosisConcepts.HPC_11,
			DiagnosisConcepts._DiagnosisConcepts.HPC_12,
			DiagnosisConcepts._DiagnosisConcepts.HPC_13,
			DiagnosisConcepts._DiagnosisConcepts.HPC_14,
			DiagnosisConcepts._DiagnosisConcepts.HPC_15,
			DiagnosisConcepts._DiagnosisConcepts.HPC_16,
			DiagnosisConcepts._DiagnosisConcepts.HPC_17, 
			DiagnosisConcepts._DiagnosisConcepts.HPC_18,
			DiagnosisConcepts._DiagnosisConcepts.HPC_19, 
			DiagnosisConcepts._DiagnosisConcepts.HPC_20,
			DiagnosisConcepts._DiagnosisConcepts.HPC_21, 
			DiagnosisConcepts._DiagnosisConcepts.HPC_22,
			DiagnosisConcepts._DiagnosisConcepts.HPC_23, 
			DiagnosisConcepts._DiagnosisConcepts.HPC_24,
			DiagnosisConcepts._DiagnosisConcepts.HPC_25, 
			DiagnosisConcepts._DiagnosisConcepts.HPC_26,
			DiagnosisConcepts._DiagnosisConcepts.HPC_27, 
			DiagnosisConcepts._DiagnosisConcepts.HPC_28,
			DiagnosisConcepts._DiagnosisConcepts.HPC_29, 
			DiagnosisConcepts._DiagnosisConcepts.HPC_30,
			DiagnosisConcepts._DiagnosisConcepts.HPC_31, 
			DiagnosisConcepts._DiagnosisConcepts.HPC_32,
			DiagnosisConcepts._DiagnosisConcepts.HPC_33, 
			DiagnosisConcepts._DiagnosisConcepts.HPC_34

		);
	}

	public static List<Integer> getAmoebiasis() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.AM_1,
			DiagnosisConcepts._DiagnosisConcepts.AM_2,
			DiagnosisConcepts._DiagnosisConcepts.AM_3,
			DiagnosisConcepts._DiagnosisConcepts.AM_4,
			DiagnosisConcepts._DiagnosisConcepts.AM_5

		);
	}

	public static List<Integer> getMumpsList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.MPC_1,
			DiagnosisConcepts._DiagnosisConcepts.MPC_2,
			DiagnosisConcepts._DiagnosisConcepts.MPC_3,
			DiagnosisConcepts._DiagnosisConcepts.MPC_4
		);
	}

	public static List<Integer> getTyphoidList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.TYC_1,
			DiagnosisConcepts._DiagnosisConcepts.TYC_2,
			DiagnosisConcepts._DiagnosisConcepts.TYC_3, 
			DiagnosisConcepts._DiagnosisConcepts.TYC_4
		);
	}

	public static List<Integer> getBilharziaList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.BLC_1, 
			DiagnosisConcepts._DiagnosisConcepts.BLC_2,
			DiagnosisConcepts._DiagnosisConcepts.BLC_3, 
			DiagnosisConcepts._DiagnosisConcepts.BLC_4,
			DiagnosisConcepts._DiagnosisConcepts.BLC_5, 
			DiagnosisConcepts._DiagnosisConcepts.BLC_6
		);
	}

	public static List<Integer> getInterstinalwormsList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.IWC_1, 
			DiagnosisConcepts._DiagnosisConcepts.IWC_2,
			DiagnosisConcepts._DiagnosisConcepts.IWC_3, 
			DiagnosisConcepts._DiagnosisConcepts.IWC_4,
			DiagnosisConcepts._DiagnosisConcepts.IWC_5, 
			DiagnosisConcepts._DiagnosisConcepts.IWC_6
		);
	}

	public static List<Integer> getEyeInfectionsList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.EC_1, 
			DiagnosisConcepts._DiagnosisConcepts.EC_2,
			DiagnosisConcepts._DiagnosisConcepts.EC_3, 
			DiagnosisConcepts._DiagnosisConcepts.EC_4,
			DiagnosisConcepts._DiagnosisConcepts.EC_5, 
			DiagnosisConcepts._DiagnosisConcepts.EC_6,
			DiagnosisConcepts._DiagnosisConcepts.EC_7, 
			DiagnosisConcepts._DiagnosisConcepts.EC_8,
			DiagnosisConcepts._DiagnosisConcepts.EC_9, 
			DiagnosisConcepts._DiagnosisConcepts.EC_10,
			DiagnosisConcepts._DiagnosisConcepts.EC_11, 
			DiagnosisConcepts._DiagnosisConcepts.EC_12,
			DiagnosisConcepts._DiagnosisConcepts.EC_13, 
			DiagnosisConcepts._DiagnosisConcepts.EC_14,
			DiagnosisConcepts._DiagnosisConcepts.EC_15, 
			DiagnosisConcepts._DiagnosisConcepts.EC_16,
			DiagnosisConcepts._DiagnosisConcepts.EC_17, 
			DiagnosisConcepts._DiagnosisConcepts.EC_18,
			DiagnosisConcepts._DiagnosisConcepts.EC_19, 
			DiagnosisConcepts._DiagnosisConcepts.EC_20,
			DiagnosisConcepts._DiagnosisConcepts.EC_21,
			DiagnosisConcepts._DiagnosisConcepts.EC_22,
			DiagnosisConcepts._DiagnosisConcepts.EC_23, 
			DiagnosisConcepts._DiagnosisConcepts.EC_24,
			DiagnosisConcepts._DiagnosisConcepts.EC_25, 
			DiagnosisConcepts._DiagnosisConcepts.EC_26,
			DiagnosisConcepts._DiagnosisConcepts.EC_27, 
			DiagnosisConcepts._DiagnosisConcepts.EC_28,
			DiagnosisConcepts._DiagnosisConcepts.EC_29, 
			DiagnosisConcepts._DiagnosisConcepts.EC_30,
			DiagnosisConcepts._DiagnosisConcepts.EC_31, 
			DiagnosisConcepts._DiagnosisConcepts.EC_32,
			DiagnosisConcepts._DiagnosisConcepts.EC_33, 
			DiagnosisConcepts._DiagnosisConcepts.EC_34,
			DiagnosisConcepts._DiagnosisConcepts.EC_35, 
			DiagnosisConcepts._DiagnosisConcepts.EC_36,
			DiagnosisConcepts._DiagnosisConcepts.EC_37, 
			DiagnosisConcepts._DiagnosisConcepts.EC_38,
			DiagnosisConcepts._DiagnosisConcepts.EC_39, 
			DiagnosisConcepts._DiagnosisConcepts.EC_40,
			DiagnosisConcepts._DiagnosisConcepts.EC_41, 
			DiagnosisConcepts._DiagnosisConcepts.EC_42,
			DiagnosisConcepts._DiagnosisConcepts.EC_43, 
			DiagnosisConcepts._DiagnosisConcepts.EC_44,
			DiagnosisConcepts._DiagnosisConcepts.EC_45, 
			DiagnosisConcepts._DiagnosisConcepts.EC_46,
			DiagnosisConcepts._DiagnosisConcepts.EC_47, 
			DiagnosisConcepts._DiagnosisConcepts.EC_48,
			DiagnosisConcepts._DiagnosisConcepts.EC_49, 
			DiagnosisConcepts._DiagnosisConcepts.EC_50,
			DiagnosisConcepts._DiagnosisConcepts.EC_51, 
			DiagnosisConcepts._DiagnosisConcepts.EC_52,
			DiagnosisConcepts._DiagnosisConcepts.EC_53
		);
	}



	public static List<Integer> getTonsilitiesList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.TSC_1, 
			DiagnosisConcepts._DiagnosisConcepts.TSC_2,
			DiagnosisConcepts._DiagnosisConcepts.TSC_3,
			DiagnosisConcepts._DiagnosisConcepts.TSC_4,
			DiagnosisConcepts._DiagnosisConcepts.TSC_5, 
			DiagnosisConcepts._DiagnosisConcepts.TSC_6,
			DiagnosisConcepts._DiagnosisConcepts.TSC_7, 
			DiagnosisConcepts._DiagnosisConcepts.TSC_8,
			DiagnosisConcepts._DiagnosisConcepts.TSC_9, 
			DiagnosisConcepts._DiagnosisConcepts.TSC_10
		);
	}

	public static List<Integer> getUrinaryTractInfectionList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.UTI1, 
			DiagnosisConcepts._DiagnosisConcepts.UTI2,
			DiagnosisConcepts._DiagnosisConcepts.UTI3, 
			DiagnosisConcepts._DiagnosisConcepts.UTI4,
			DiagnosisConcepts._DiagnosisConcepts.UTI5, 
			DiagnosisConcepts._DiagnosisConcepts.UTI6,
			DiagnosisConcepts._DiagnosisConcepts.UTI6

		);
	}

	public static List<Integer> getMentalDisordersList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.MDC_1,
			DiagnosisConcepts._DiagnosisConcepts.MDC_2, DiagnosisConcepts._DiagnosisConcepts.MDC_3,
			DiagnosisConcepts._DiagnosisConcepts.MDC_4, DiagnosisConcepts._DiagnosisConcepts.MDC_5,
			DiagnosisConcepts._DiagnosisConcepts.MDC_6, DiagnosisConcepts._DiagnosisConcepts.MDC_7,
			DiagnosisConcepts._DiagnosisConcepts.MDC_8, DiagnosisConcepts._DiagnosisConcepts.MDC_9,
			DiagnosisConcepts._DiagnosisConcepts.MDC_10, DiagnosisConcepts._DiagnosisConcepts.MDC_11,
			DiagnosisConcepts._DiagnosisConcepts.MDC_12, DiagnosisConcepts._DiagnosisConcepts.MDC_13,
			DiagnosisConcepts._DiagnosisConcepts.MDC_14, DiagnosisConcepts._DiagnosisConcepts.MDC_15,
			DiagnosisConcepts._DiagnosisConcepts.MDC_16, DiagnosisConcepts._DiagnosisConcepts.MDC_17,
			DiagnosisConcepts._DiagnosisConcepts.MDC_18, DiagnosisConcepts._DiagnosisConcepts.MDC_19,
			DiagnosisConcepts._DiagnosisConcepts.MDC_20, DiagnosisConcepts._DiagnosisConcepts.MDC_21,
			DiagnosisConcepts._DiagnosisConcepts.MDC_22, DiagnosisConcepts._DiagnosisConcepts.MDC_23,
			DiagnosisConcepts._DiagnosisConcepts.MDC_24, DiagnosisConcepts._DiagnosisConcepts.MDC_25,
			DiagnosisConcepts._DiagnosisConcepts.MDC_26, DiagnosisConcepts._DiagnosisConcepts.MDC_27,
			DiagnosisConcepts._DiagnosisConcepts.MDC_28, DiagnosisConcepts._DiagnosisConcepts.MDC_29,
			DiagnosisConcepts._DiagnosisConcepts.MDC_30, DiagnosisConcepts._DiagnosisConcepts.MDC_31,
			DiagnosisConcepts._DiagnosisConcepts.MDC_32, DiagnosisConcepts._DiagnosisConcepts.MDC_33,
			DiagnosisConcepts._DiagnosisConcepts.MDC_34, DiagnosisConcepts._DiagnosisConcepts.MDC_35,
			DiagnosisConcepts._DiagnosisConcepts.MDC_36, DiagnosisConcepts._DiagnosisConcepts.MDC_37,
			DiagnosisConcepts._DiagnosisConcepts.MDC_38, DiagnosisConcepts._DiagnosisConcepts.MDC_39,
			DiagnosisConcepts._DiagnosisConcepts.MDC_40, DiagnosisConcepts._DiagnosisConcepts.MDC_41,
			DiagnosisConcepts._DiagnosisConcepts.MDC_42, DiagnosisConcepts._DiagnosisConcepts.MDC_43,
			DiagnosisConcepts._DiagnosisConcepts.MDC_44, DiagnosisConcepts._DiagnosisConcepts.MDC_45,
			DiagnosisConcepts._DiagnosisConcepts.MDC_46, DiagnosisConcepts._DiagnosisConcepts.MDC_47,
			DiagnosisConcepts._DiagnosisConcepts.MDC_48, DiagnosisConcepts._DiagnosisConcepts.MDC_49,
			DiagnosisConcepts._DiagnosisConcepts.MDC_50, DiagnosisConcepts._DiagnosisConcepts.MDC_51,
			DiagnosisConcepts._DiagnosisConcepts.MDC_52, DiagnosisConcepts._DiagnosisConcepts.MDC_53,
			DiagnosisConcepts._DiagnosisConcepts.MDC_54, DiagnosisConcepts._DiagnosisConcepts.MDC_55,
			DiagnosisConcepts._DiagnosisConcepts.MDC_56, DiagnosisConcepts._DiagnosisConcepts.MDC_57,
			DiagnosisConcepts._DiagnosisConcepts.MDC_58, DiagnosisConcepts._DiagnosisConcepts.MDC_59,
			DiagnosisConcepts._DiagnosisConcepts.MDC_60, DiagnosisConcepts._DiagnosisConcepts.MDC_61,
			DiagnosisConcepts._DiagnosisConcepts.MDC_62, DiagnosisConcepts._DiagnosisConcepts.MDC_63,
			DiagnosisConcepts._DiagnosisConcepts.MDC_64, DiagnosisConcepts._DiagnosisConcepts.MDC_65,
			DiagnosisConcepts._DiagnosisConcepts.MDC_66, DiagnosisConcepts._DiagnosisConcepts.MDC_67,
			DiagnosisConcepts._DiagnosisConcepts.MDC_68, DiagnosisConcepts._DiagnosisConcepts.MDC_69,
			DiagnosisConcepts._DiagnosisConcepts.MDC_70, DiagnosisConcepts._DiagnosisConcepts.MDC_71,
			DiagnosisConcepts._DiagnosisConcepts.MDC_72, DiagnosisConcepts._DiagnosisConcepts.MDC_73,
			DiagnosisConcepts._DiagnosisConcepts.MDC_74, DiagnosisConcepts._DiagnosisConcepts.MDC_75,
			DiagnosisConcepts._DiagnosisConcepts.MDC_76, DiagnosisConcepts._DiagnosisConcepts.MDC_77,
			DiagnosisConcepts._DiagnosisConcepts.MDC_78, DiagnosisConcepts._DiagnosisConcepts.MDC_79,
			DiagnosisConcepts._DiagnosisConcepts.MDC_80, DiagnosisConcepts._DiagnosisConcepts.MDC_81,
			DiagnosisConcepts._DiagnosisConcepts.MDC_82, DiagnosisConcepts._DiagnosisConcepts.MDC_83,
			DiagnosisConcepts._DiagnosisConcepts.MDC_84, DiagnosisConcepts._DiagnosisConcepts.MDC_85,
			DiagnosisConcepts._DiagnosisConcepts.MDC_86, DiagnosisConcepts._DiagnosisConcepts.MDC_87,
			DiagnosisConcepts._DiagnosisConcepts.MDC_88, DiagnosisConcepts._DiagnosisConcepts.MDC_89,
			DiagnosisConcepts._DiagnosisConcepts.MDC_90, DiagnosisConcepts._DiagnosisConcepts.MDC_91,
			DiagnosisConcepts._DiagnosisConcepts.MDC_92, DiagnosisConcepts._DiagnosisConcepts.MDC_93,
			DiagnosisConcepts._DiagnosisConcepts.MDC_94, DiagnosisConcepts._DiagnosisConcepts.MDC_95,
			DiagnosisConcepts._DiagnosisConcepts.MDC_96, DiagnosisConcepts._DiagnosisConcepts.MDC_97,
			DiagnosisConcepts._DiagnosisConcepts.MDC_98, DiagnosisConcepts._DiagnosisConcepts.MDC_99,
			DiagnosisConcepts._DiagnosisConcepts.MDC_100, DiagnosisConcepts._DiagnosisConcepts.MDC_101,
			DiagnosisConcepts._DiagnosisConcepts.MDC_102, DiagnosisConcepts._DiagnosisConcepts.MDC_103,
			DiagnosisConcepts._DiagnosisConcepts.MDC_104, DiagnosisConcepts._DiagnosisConcepts.MDC_105,
			DiagnosisConcepts._DiagnosisConcepts.MDC_106, DiagnosisConcepts._DiagnosisConcepts.MDC_107,
			DiagnosisConcepts._DiagnosisConcepts.MDC_108, DiagnosisConcepts._DiagnosisConcepts.MDC_109,
			DiagnosisConcepts._DiagnosisConcepts.MDC_110, DiagnosisConcepts._DiagnosisConcepts.MDC_111,
			DiagnosisConcepts._DiagnosisConcepts.MDC_112, DiagnosisConcepts._DiagnosisConcepts.MDC_113,
			DiagnosisConcepts._DiagnosisConcepts.MDC_114, DiagnosisConcepts._DiagnosisConcepts.MDC_115,
			DiagnosisConcepts._DiagnosisConcepts.MDC_116, DiagnosisConcepts._DiagnosisConcepts.MDC_117,
			DiagnosisConcepts._DiagnosisConcepts.MDC_118, DiagnosisConcepts._DiagnosisConcepts.MDC_119,
			DiagnosisConcepts._DiagnosisConcepts.MDC_120, DiagnosisConcepts._DiagnosisConcepts.MDC_121,
			DiagnosisConcepts._DiagnosisConcepts.MDC_122, DiagnosisConcepts._DiagnosisConcepts.MDC_123,
			DiagnosisConcepts._DiagnosisConcepts.MDC_124, DiagnosisConcepts._DiagnosisConcepts.MDC_125,
			DiagnosisConcepts._DiagnosisConcepts.MDC_126, DiagnosisConcepts._DiagnosisConcepts.MDC_127,
			DiagnosisConcepts._DiagnosisConcepts.MDC_128, DiagnosisConcepts._DiagnosisConcepts.MDC_129,
			DiagnosisConcepts._DiagnosisConcepts.MDC_130, DiagnosisConcepts._DiagnosisConcepts.MDC_131,
			DiagnosisConcepts._DiagnosisConcepts.MDC_132
		);

	}


	public static List<Integer> getDentalDisordersList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.DDC_1, DiagnosisConcepts._DiagnosisConcepts.DDC_2,
			DiagnosisConcepts._DiagnosisConcepts.DDC_3, DiagnosisConcepts._DiagnosisConcepts.DDC_4,
			DiagnosisConcepts._DiagnosisConcepts.DDC_5, DiagnosisConcepts._DiagnosisConcepts.DDC_6,
			DiagnosisConcepts._DiagnosisConcepts.DDC_7, DiagnosisConcepts._DiagnosisConcepts.DDC_8,
			DiagnosisConcepts._DiagnosisConcepts.DDC_9, DiagnosisConcepts._DiagnosisConcepts.DDC_10,
			DiagnosisConcepts._DiagnosisConcepts.DDC_11, DiagnosisConcepts._DiagnosisConcepts.DDC_12,
			DiagnosisConcepts._DiagnosisConcepts.DDC_13, DiagnosisConcepts._DiagnosisConcepts.DDC_14,
			DiagnosisConcepts._DiagnosisConcepts.DDC_15, DiagnosisConcepts._DiagnosisConcepts.DDC_16,
			DiagnosisConcepts._DiagnosisConcepts.DDC_17, DiagnosisConcepts._DiagnosisConcepts.DDC_18,
			DiagnosisConcepts._DiagnosisConcepts.DDC_19, DiagnosisConcepts._DiagnosisConcepts.DDC_20,
			DiagnosisConcepts._DiagnosisConcepts.DDC_21, DiagnosisConcepts._DiagnosisConcepts.DDC_22,
			DiagnosisConcepts._DiagnosisConcepts.DDC_23, DiagnosisConcepts._DiagnosisConcepts.DDC_24,
			DiagnosisConcepts._DiagnosisConcepts.DDC_25, DiagnosisConcepts._DiagnosisConcepts.DDC_26,
			DiagnosisConcepts._DiagnosisConcepts.DDC_27, DiagnosisConcepts._DiagnosisConcepts.DDC_28,
			DiagnosisConcepts._DiagnosisConcepts.DDC_29, DiagnosisConcepts._DiagnosisConcepts.DDC_30,
			DiagnosisConcepts._DiagnosisConcepts.DDC_31, DiagnosisConcepts._DiagnosisConcepts.DDC_32,
			DiagnosisConcepts._DiagnosisConcepts.DDC_33, DiagnosisConcepts._DiagnosisConcepts.DDC_34,
			DiagnosisConcepts._DiagnosisConcepts.DDC_35, DiagnosisConcepts._DiagnosisConcepts.DDC_36,
			DiagnosisConcepts._DiagnosisConcepts.DDC_37, DiagnosisConcepts._DiagnosisConcepts.DDC_38,
			DiagnosisConcepts._DiagnosisConcepts.DDC_39, DiagnosisConcepts._DiagnosisConcepts.DDC_40,
			DiagnosisConcepts._DiagnosisConcepts.DDC_41, DiagnosisConcepts._DiagnosisConcepts.DDC_42,
			DiagnosisConcepts._DiagnosisConcepts.DDC_43, DiagnosisConcepts._DiagnosisConcepts.DDC_44,
			DiagnosisConcepts._DiagnosisConcepts.DDC_45, DiagnosisConcepts._DiagnosisConcepts.DDC_46,
			DiagnosisConcepts._DiagnosisConcepts.DDC_47, DiagnosisConcepts._DiagnosisConcepts.DDC_48,
			DiagnosisConcepts._DiagnosisConcepts.DDC_49, DiagnosisConcepts._DiagnosisConcepts.DDC_50

		);

	}

	public static List<Integer> getRoadTrafficInjuriesList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.RTC_1, DiagnosisConcepts._DiagnosisConcepts.RTC_2,
			DiagnosisConcepts._DiagnosisConcepts.RTC_3,DiagnosisConcepts._DiagnosisConcepts.RTC_4,
			DiagnosisConcepts._DiagnosisConcepts.RTC_5,DiagnosisConcepts._DiagnosisConcepts.RTC_6,
			DiagnosisConcepts._DiagnosisConcepts.RTC_7,DiagnosisConcepts._DiagnosisConcepts.RTC_8,
			DiagnosisConcepts._DiagnosisConcepts.RTC_9,DiagnosisConcepts._DiagnosisConcepts.RTC_10,
			DiagnosisConcepts._DiagnosisConcepts.RTC_11,DiagnosisConcepts._DiagnosisConcepts.RTC_12,
			DiagnosisConcepts._DiagnosisConcepts.RTC_13,DiagnosisConcepts._DiagnosisConcepts.RTC_14,
			DiagnosisConcepts._DiagnosisConcepts.RTC_15,DiagnosisConcepts._DiagnosisConcepts.RTC_16,
			DiagnosisConcepts._DiagnosisConcepts.RTC_17,DiagnosisConcepts._DiagnosisConcepts.RTC_18,
			DiagnosisConcepts._DiagnosisConcepts.RTC_19,DiagnosisConcepts._DiagnosisConcepts.RTC_20,
			DiagnosisConcepts._DiagnosisConcepts.RTC_21,DiagnosisConcepts._DiagnosisConcepts.RTC_22,
			DiagnosisConcepts._DiagnosisConcepts.RTC_23
		);
	}

	public static List<Integer> getViolenceRelatedInjuriesList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.VRC_1, DiagnosisConcepts._DiagnosisConcepts.VRC_2,
			DiagnosisConcepts._DiagnosisConcepts.VRC_3, DiagnosisConcepts._DiagnosisConcepts.VRC_4,
			DiagnosisConcepts._DiagnosisConcepts.VRC_5, DiagnosisConcepts._DiagnosisConcepts.VRC_6,
			DiagnosisConcepts._DiagnosisConcepts.VRC_7

		);
	}

	public static List<Integer> getOtherInjuriesList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.OIC_1, DiagnosisConcepts._DiagnosisConcepts.OIC_2,
			DiagnosisConcepts._DiagnosisConcepts.OIC_3, DiagnosisConcepts._DiagnosisConcepts.OIC_4,
			DiagnosisConcepts._DiagnosisConcepts.OIC_5, DiagnosisConcepts._DiagnosisConcepts.OIC_6,
			DiagnosisConcepts._DiagnosisConcepts.OIC_7, DiagnosisConcepts._DiagnosisConcepts.OIC_8,
			DiagnosisConcepts._DiagnosisConcepts.OIC_9, DiagnosisConcepts._DiagnosisConcepts.OIC_10,
			DiagnosisConcepts._DiagnosisConcepts.OIC_11, DiagnosisConcepts._DiagnosisConcepts.OIC_12
		);

	}

	public static List<Integer> getSexualAssaultList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.SAC_1, 
			DiagnosisConcepts._DiagnosisConcepts.SAC_2,
//			DiagnosisConcepts._DiagnosisConcepts.SAC_3 TODO:a icd11 mapping XE85Q was shared instead of the concept ID,
			DiagnosisConcepts._DiagnosisConcepts.SAC_4,
			DiagnosisConcepts._DiagnosisConcepts.SAC_5

		);
	}
	public static List<Integer> getBurnsList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.BC_1, DiagnosisConcepts._DiagnosisConcepts.BC_2,
			DiagnosisConcepts._DiagnosisConcepts.BC_3, DiagnosisConcepts._DiagnosisConcepts.BC_4,
			DiagnosisConcepts._DiagnosisConcepts.BC_5, DiagnosisConcepts._DiagnosisConcepts.BC_6,
			DiagnosisConcepts._DiagnosisConcepts.BC_7, DiagnosisConcepts._DiagnosisConcepts.BC_8,
			DiagnosisConcepts._DiagnosisConcepts.BC_9, DiagnosisConcepts._DiagnosisConcepts.BC_10,
			DiagnosisConcepts._DiagnosisConcepts.BC_11, DiagnosisConcepts._DiagnosisConcepts.BC_12,
			DiagnosisConcepts._DiagnosisConcepts.BC_13, DiagnosisConcepts._DiagnosisConcepts.BC_13,
			DiagnosisConcepts._DiagnosisConcepts.BC_14, DiagnosisConcepts._DiagnosisConcepts.BC_15,
			DiagnosisConcepts._DiagnosisConcepts.BC_16, DiagnosisConcepts._DiagnosisConcepts.BC_17,
			DiagnosisConcepts._DiagnosisConcepts.BC_18, DiagnosisConcepts._DiagnosisConcepts.BC_19,
			DiagnosisConcepts._DiagnosisConcepts.BC_20, DiagnosisConcepts._DiagnosisConcepts.BC_21,
			DiagnosisConcepts._DiagnosisConcepts.BC_22, DiagnosisConcepts._DiagnosisConcepts.BC_23,
			DiagnosisConcepts._DiagnosisConcepts.BC_24, DiagnosisConcepts._DiagnosisConcepts.BC_25,
			DiagnosisConcepts._DiagnosisConcepts.BC_26, DiagnosisConcepts._DiagnosisConcepts.BC_27,
			DiagnosisConcepts._DiagnosisConcepts.BC_28, DiagnosisConcepts._DiagnosisConcepts.BC_29,
			DiagnosisConcepts._DiagnosisConcepts.BC_30, DiagnosisConcepts._DiagnosisConcepts.BC_31,
			DiagnosisConcepts._DiagnosisConcepts.BC_32, DiagnosisConcepts._DiagnosisConcepts.BC_33,
			DiagnosisConcepts._DiagnosisConcepts.BC_34, DiagnosisConcepts._DiagnosisConcepts.BC_35,
			DiagnosisConcepts._DiagnosisConcepts.BC_36, DiagnosisConcepts._DiagnosisConcepts.BC_37,
			DiagnosisConcepts._DiagnosisConcepts.BC_38, DiagnosisConcepts._DiagnosisConcepts.BC_39,
			DiagnosisConcepts._DiagnosisConcepts.BC_40, DiagnosisConcepts._DiagnosisConcepts.BC_41,
			DiagnosisConcepts._DiagnosisConcepts.BC_42, DiagnosisConcepts._DiagnosisConcepts.BC_43,
			DiagnosisConcepts._DiagnosisConcepts.BC_44, DiagnosisConcepts._DiagnosisConcepts.BC_45,
			DiagnosisConcepts._DiagnosisConcepts.BC_46, DiagnosisConcepts._DiagnosisConcepts.BC_47,
			DiagnosisConcepts._DiagnosisConcepts.BC_48, DiagnosisConcepts._DiagnosisConcepts.BC_49,
			DiagnosisConcepts._DiagnosisConcepts.BC_50, DiagnosisConcepts._DiagnosisConcepts.BC_51,
			DiagnosisConcepts._DiagnosisConcepts.BC_52, DiagnosisConcepts._DiagnosisConcepts.BC_53,
			DiagnosisConcepts._DiagnosisConcepts.BC_54, DiagnosisConcepts._DiagnosisConcepts.BC_55,
			DiagnosisConcepts._DiagnosisConcepts.BC_56, DiagnosisConcepts._DiagnosisConcepts.BC_57,
			DiagnosisConcepts._DiagnosisConcepts.BC_58, DiagnosisConcepts._DiagnosisConcepts.BC_59,
			DiagnosisConcepts._DiagnosisConcepts.BC_60, DiagnosisConcepts._DiagnosisConcepts.BC_61,
			DiagnosisConcepts._DiagnosisConcepts.BC_61, DiagnosisConcepts._DiagnosisConcepts.BC_62,
			DiagnosisConcepts._DiagnosisConcepts.BC_63, DiagnosisConcepts._DiagnosisConcepts.BC_64,
			DiagnosisConcepts._DiagnosisConcepts.BC_65, DiagnosisConcepts._DiagnosisConcepts.BC_66,
			DiagnosisConcepts._DiagnosisConcepts.BC_67, DiagnosisConcepts._DiagnosisConcepts.BC_68,
			DiagnosisConcepts._DiagnosisConcepts.BC_69, DiagnosisConcepts._DiagnosisConcepts.BC_70,
			DiagnosisConcepts._DiagnosisConcepts.BC_71, DiagnosisConcepts._DiagnosisConcepts.BC_72,
			DiagnosisConcepts._DiagnosisConcepts.BC_73, DiagnosisConcepts._DiagnosisConcepts.BC_74,
			DiagnosisConcepts._DiagnosisConcepts.BC_75, DiagnosisConcepts._DiagnosisConcepts.BC_76,
			DiagnosisConcepts._DiagnosisConcepts.BC_77, DiagnosisConcepts._DiagnosisConcepts.BC_78,
			DiagnosisConcepts._DiagnosisConcepts.BC_79, DiagnosisConcepts._DiagnosisConcepts.BC_80,
			DiagnosisConcepts._DiagnosisConcepts.BC_81, DiagnosisConcepts._DiagnosisConcepts.BC_82,
			DiagnosisConcepts._DiagnosisConcepts.BC_83, DiagnosisConcepts._DiagnosisConcepts.BC_84,
			DiagnosisConcepts._DiagnosisConcepts.BC_85, DiagnosisConcepts._DiagnosisConcepts.BC_86,
			DiagnosisConcepts._DiagnosisConcepts.BC_87, DiagnosisConcepts._DiagnosisConcepts.BC_88,
			DiagnosisConcepts._DiagnosisConcepts.BC_89, DiagnosisConcepts._DiagnosisConcepts.BC_90,
			DiagnosisConcepts._DiagnosisConcepts.BC_91, DiagnosisConcepts._DiagnosisConcepts.BC_92,
			DiagnosisConcepts._DiagnosisConcepts.BC_93, DiagnosisConcepts._DiagnosisConcepts.BC_94,
			DiagnosisConcepts._DiagnosisConcepts.BC_95, DiagnosisConcepts._DiagnosisConcepts.BC_96,
			DiagnosisConcepts._DiagnosisConcepts.BC_97, DiagnosisConcepts._DiagnosisConcepts.BC_98,
			DiagnosisConcepts._DiagnosisConcepts.BC_99, DiagnosisConcepts._DiagnosisConcepts.BC_100,
			DiagnosisConcepts._DiagnosisConcepts.BC_101, DiagnosisConcepts._DiagnosisConcepts.BC_102,
			DiagnosisConcepts._DiagnosisConcepts.BC_103, DiagnosisConcepts._DiagnosisConcepts.BC_104,
			DiagnosisConcepts._DiagnosisConcepts.BC_105
		);
	}


	public static List<Integer> getSnakeBitesList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.SBC_1,
			DiagnosisConcepts._DiagnosisConcepts.SBC_2, DiagnosisConcepts._DiagnosisConcepts.SBC_3,
			DiagnosisConcepts._DiagnosisConcepts.SBC_4, DiagnosisConcepts._DiagnosisConcepts.SBC_5,
			DiagnosisConcepts._DiagnosisConcepts.SBC_6
		);
	}

	public static List<Integer> getDogBitesList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.DBC_1, DiagnosisConcepts._DiagnosisConcepts.DBC_2,
			DiagnosisConcepts._DiagnosisConcepts.DBC_3, DiagnosisConcepts._DiagnosisConcepts.DBC_4
		);

	}
	public static List<Integer> getOtherBitesList() {

		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.OBC_1, DiagnosisConcepts._DiagnosisConcepts.OBC_2,
			DiagnosisConcepts._DiagnosisConcepts.OBC_3, DiagnosisConcepts._DiagnosisConcepts.OBC_4,
			DiagnosisConcepts._DiagnosisConcepts.OBC_5, DiagnosisConcepts._DiagnosisConcepts.OBC_6,
			DiagnosisConcepts._DiagnosisConcepts.OBC_7
		);

	}

	public static List<Integer> getDiabetesList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.DTC_1, DiagnosisConcepts._DiagnosisConcepts.DTC_2,
			DiagnosisConcepts._DiagnosisConcepts.DTC_3, DiagnosisConcepts._DiagnosisConcepts.DTC_4,
			DiagnosisConcepts._DiagnosisConcepts.DTC_5, DiagnosisConcepts._DiagnosisConcepts.DTC_6,
			DiagnosisConcepts._DiagnosisConcepts.DTC_7, DiagnosisConcepts._DiagnosisConcepts.DTC_8,
			DiagnosisConcepts._DiagnosisConcepts.DTC_9,	DiagnosisConcepts._DiagnosisConcepts.DTC_10,
			DiagnosisConcepts._DiagnosisConcepts.DTC_11,DiagnosisConcepts._DiagnosisConcepts.DTC_12,
			DiagnosisConcepts._DiagnosisConcepts.DTC_13, DiagnosisConcepts._DiagnosisConcepts.DTC_14,
			DiagnosisConcepts._DiagnosisConcepts.DTC_15, DiagnosisConcepts._DiagnosisConcepts.DTC_16,
			DiagnosisConcepts._DiagnosisConcepts.DTC_17, DiagnosisConcepts._DiagnosisConcepts.DTC_18,
			DiagnosisConcepts._DiagnosisConcepts.DTC_19, DiagnosisConcepts._DiagnosisConcepts.DTC_20,
			DiagnosisConcepts._DiagnosisConcepts.DTC_21
		);
	}

	public static List<Integer> getEpilepsyList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.EPC_1,
			DiagnosisConcepts._DiagnosisConcepts.EPC_2, DiagnosisConcepts._DiagnosisConcepts.EPC_3,
			DiagnosisConcepts._DiagnosisConcepts.EPC_4, DiagnosisConcepts._DiagnosisConcepts.EPC_5,
			DiagnosisConcepts._DiagnosisConcepts.EPC_6, DiagnosisConcepts._DiagnosisConcepts.EPC_7,
			DiagnosisConcepts._DiagnosisConcepts.EPC_8, DiagnosisConcepts._DiagnosisConcepts.EPC_9,
			DiagnosisConcepts._DiagnosisConcepts.EPC_10, DiagnosisConcepts._DiagnosisConcepts.EPC_11,
			DiagnosisConcepts._DiagnosisConcepts.EPC_12, DiagnosisConcepts._DiagnosisConcepts.EPC_13,
			DiagnosisConcepts._DiagnosisConcepts.EPC_14, DiagnosisConcepts._DiagnosisConcepts.EPC_15,
			DiagnosisConcepts._DiagnosisConcepts.EPC_16, DiagnosisConcepts._DiagnosisConcepts.EPC_17,
			DiagnosisConcepts._DiagnosisConcepts.EPC_18, DiagnosisConcepts._DiagnosisConcepts.EPC_19,
			DiagnosisConcepts._DiagnosisConcepts.EPC_20, DiagnosisConcepts._DiagnosisConcepts.EPC_21,
			DiagnosisConcepts._DiagnosisConcepts.EPC_22, DiagnosisConcepts._DiagnosisConcepts.EPC_23,
			DiagnosisConcepts._DiagnosisConcepts.EPC_24
		);
	}

	public static List<Integer> getOtherConvulsiveDisordersList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.OCDC_1,
			DiagnosisConcepts._DiagnosisConcepts.OCDC_2,
			DiagnosisConcepts._DiagnosisConcepts.OCDC_3
		);
	}

	public static List<Integer> getReumonicFeverList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.RF_1,
			DiagnosisConcepts._DiagnosisConcepts.RF_2, DiagnosisConcepts._DiagnosisConcepts.RF_3,
			DiagnosisConcepts._DiagnosisConcepts.RF_4

		);
	}

	public static List<Integer> getRicketsList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.RKC_1, DiagnosisConcepts._DiagnosisConcepts.RKC_2,
			DiagnosisConcepts._DiagnosisConcepts.RKC_3, DiagnosisConcepts._DiagnosisConcepts.RKC_4,
			DiagnosisConcepts._DiagnosisConcepts.RKC_5
		);
	}

	public static List<Integer> getCerebralPalsyList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.CRPC_1, DiagnosisConcepts._DiagnosisConcepts.CRPC_2,
			DiagnosisConcepts._DiagnosisConcepts.CRPC_3, DiagnosisConcepts._DiagnosisConcepts.CRPC_4,
			DiagnosisConcepts._DiagnosisConcepts.CRPC_5, DiagnosisConcepts._DiagnosisConcepts.CRPC_6,
			DiagnosisConcepts._DiagnosisConcepts.CRPC_7, DiagnosisConcepts._DiagnosisConcepts.CRPC_8,
			DiagnosisConcepts._DiagnosisConcepts.CRPC_9, DiagnosisConcepts._DiagnosisConcepts.CRPC_10,
			DiagnosisConcepts._DiagnosisConcepts.CRPC_11,DiagnosisConcepts._DiagnosisConcepts.CRPC_12

		);
	}

	public static List<Integer> getAutismList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.ATC_1,
			DiagnosisConcepts._DiagnosisConcepts.ATC_2, DiagnosisConcepts._DiagnosisConcepts.ATC_3,
			DiagnosisConcepts._DiagnosisConcepts.ATC_4, DiagnosisConcepts._DiagnosisConcepts.ATC_5,
			DiagnosisConcepts._DiagnosisConcepts.ATC_6, DiagnosisConcepts._DiagnosisConcepts.ATC_7,
			DiagnosisConcepts._DiagnosisConcepts.ATC_8
		);
	}

	public static List<Integer> getTryponomiasisList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.TRC_1,
			DiagnosisConcepts._DiagnosisConcepts.TRC_2, DiagnosisConcepts._DiagnosisConcepts.TRC_3,
			DiagnosisConcepts._DiagnosisConcepts.TRC_4, DiagnosisConcepts._DiagnosisConcepts.TRC_5,
			DiagnosisConcepts._DiagnosisConcepts.TRC_6, DiagnosisConcepts._DiagnosisConcepts.TRC_7,
			DiagnosisConcepts._DiagnosisConcepts.TRC_8, DiagnosisConcepts._DiagnosisConcepts.TRC_9,
			DiagnosisConcepts._DiagnosisConcepts.TRC_10


		);
	}
	public static List<Integer> getYellowFeverList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.YFC_1,
			DiagnosisConcepts._DiagnosisConcepts.YFC_2
		);
	}

	public static List<Integer> getRiftValleyFeverList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.RVF_1,
			DiagnosisConcepts._DiagnosisConcepts.RVF_2
		);
	}

	public static List<Integer> getChikungunyaFeverList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.CKU_1,
			DiagnosisConcepts._DiagnosisConcepts.CKU_2);
	}

	public static List<Integer> getDengueFeverList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.DENF_1,
			DiagnosisConcepts._DiagnosisConcepts.DENF_2, DiagnosisConcepts._DiagnosisConcepts.DENF_3,
			DiagnosisConcepts._DiagnosisConcepts.DENF_4, DiagnosisConcepts._DiagnosisConcepts.DENF_5,
			DiagnosisConcepts._DiagnosisConcepts.DENF_6

		);
	}

	public static List<Integer> getKalazarLeishmaniasisList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.KLC_1,
			DiagnosisConcepts._DiagnosisConcepts.KLC_2,
			DiagnosisConcepts._DiagnosisConcepts.KLC_3,
			DiagnosisConcepts._DiagnosisConcepts.KLC_4
		);
	}

	public static List<Integer> getChildHoodCancerist() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.SCC_1, DiagnosisConcepts._DiagnosisConcepts.SCC_2,
			DiagnosisConcepts._DiagnosisConcepts.SCC_3, DiagnosisConcepts._DiagnosisConcepts.SCC_4,
			DiagnosisConcepts._DiagnosisConcepts.SCC_5, DiagnosisConcepts._DiagnosisConcepts.SCC_6,
			DiagnosisConcepts._DiagnosisConcepts.SCC_7, DiagnosisConcepts._DiagnosisConcepts.SCC_8,
			DiagnosisConcepts._DiagnosisConcepts.SCC_9, DiagnosisConcepts._DiagnosisConcepts.SCC_10,
			DiagnosisConcepts._DiagnosisConcepts.SCC_11, DiagnosisConcepts._DiagnosisConcepts.SCC_12,
			DiagnosisConcepts._DiagnosisConcepts.SCC_13, DiagnosisConcepts._DiagnosisConcepts.SCC_14,
			DiagnosisConcepts._DiagnosisConcepts.SCC_15, DiagnosisConcepts._DiagnosisConcepts.SCC_16,
			DiagnosisConcepts._DiagnosisConcepts.SCC_17, DiagnosisConcepts._DiagnosisConcepts.SCC_18,
			DiagnosisConcepts._DiagnosisConcepts.SCC_19, DiagnosisConcepts._DiagnosisConcepts.SCC_20,
			DiagnosisConcepts._DiagnosisConcepts.SCC_21, DiagnosisConcepts._DiagnosisConcepts.SCC_22,
			DiagnosisConcepts._DiagnosisConcepts.SCC_23, DiagnosisConcepts._DiagnosisConcepts.SCC_24,
			DiagnosisConcepts._DiagnosisConcepts.SCC_25, DiagnosisConcepts._DiagnosisConcepts.SCC_26,
			DiagnosisConcepts._DiagnosisConcepts.SCC_27, DiagnosisConcepts._DiagnosisConcepts.SCC_28,
			DiagnosisConcepts._DiagnosisConcepts.SCC_29, DiagnosisConcepts._DiagnosisConcepts.SCC_30,
			DiagnosisConcepts._DiagnosisConcepts.SCC_31, DiagnosisConcepts._DiagnosisConcepts.SCC_32,
			DiagnosisConcepts._DiagnosisConcepts.SCC_33, DiagnosisConcepts._DiagnosisConcepts.SCC_34,
			DiagnosisConcepts._DiagnosisConcepts.SCC_35, DiagnosisConcepts._DiagnosisConcepts.SCC_36,
			DiagnosisConcepts._DiagnosisConcepts.SCC_37, DiagnosisConcepts._DiagnosisConcepts.SCC_38,
			DiagnosisConcepts._DiagnosisConcepts.SCC_39, DiagnosisConcepts._DiagnosisConcepts.SCC_40,
			DiagnosisConcepts._DiagnosisConcepts.SCC_41, DiagnosisConcepts._DiagnosisConcepts.SCC_42,
			DiagnosisConcepts._DiagnosisConcepts.SCC_43, DiagnosisConcepts._DiagnosisConcepts.SCC_44,
			DiagnosisConcepts._DiagnosisConcepts.SCC_45, DiagnosisConcepts._DiagnosisConcepts.SCC_46,
			DiagnosisConcepts._DiagnosisConcepts.SCC_47, DiagnosisConcepts._DiagnosisConcepts.SCC_48,
			DiagnosisConcepts._DiagnosisConcepts.SCC_49, DiagnosisConcepts._DiagnosisConcepts.SCC_50,
			DiagnosisConcepts._DiagnosisConcepts.SCC_51, DiagnosisConcepts._DiagnosisConcepts.SCC_52,
			DiagnosisConcepts._DiagnosisConcepts.SCC_53, DiagnosisConcepts._DiagnosisConcepts.SCC_54,
			DiagnosisConcepts._DiagnosisConcepts.SCC_55, DiagnosisConcepts._DiagnosisConcepts.SCC_56,
			DiagnosisConcepts._DiagnosisConcepts.SCC_57, DiagnosisConcepts._DiagnosisConcepts.SCC_58,
			DiagnosisConcepts._DiagnosisConcepts.SCC_59, DiagnosisConcepts._DiagnosisConcepts.SCC_60,
			DiagnosisConcepts._DiagnosisConcepts.SCC_61, DiagnosisConcepts._DiagnosisConcepts.SCC_62,
			DiagnosisConcepts._DiagnosisConcepts.SCC_63, DiagnosisConcepts._DiagnosisConcepts.SCC_64,
			DiagnosisConcepts._DiagnosisConcepts.SCC_65, DiagnosisConcepts._DiagnosisConcepts.SCC_66,
			DiagnosisConcepts._DiagnosisConcepts.SCC_67, DiagnosisConcepts._DiagnosisConcepts.SCC_68,
			DiagnosisConcepts._DiagnosisConcepts.SCC_69, DiagnosisConcepts._DiagnosisConcepts.SCC_70,
			DiagnosisConcepts._DiagnosisConcepts.SCC_71, DiagnosisConcepts._DiagnosisConcepts.SCC_72,
			DiagnosisConcepts._DiagnosisConcepts.SCC_73, DiagnosisConcepts._DiagnosisConcepts.SCC_74,
			DiagnosisConcepts._DiagnosisConcepts.SCC_75, DiagnosisConcepts._DiagnosisConcepts.SCC_76,
			DiagnosisConcepts._DiagnosisConcepts.SCC_77, DiagnosisConcepts._DiagnosisConcepts.SCC_78,
			DiagnosisConcepts._DiagnosisConcepts.SCC_79, DiagnosisConcepts._DiagnosisConcepts.SCC_80,
			DiagnosisConcepts._DiagnosisConcepts.SCC_81, DiagnosisConcepts._DiagnosisConcepts.SCC_82,
			DiagnosisConcepts._DiagnosisConcepts.SCC_83, DiagnosisConcepts._DiagnosisConcepts.SCC_84,
			DiagnosisConcepts._DiagnosisConcepts.SCC_85, DiagnosisConcepts._DiagnosisConcepts.SCC_86,
			DiagnosisConcepts._DiagnosisConcepts.SCC_87, DiagnosisConcepts._DiagnosisConcepts.SCC_88,
			DiagnosisConcepts._DiagnosisConcepts.SCC_89, DiagnosisConcepts._DiagnosisConcepts.SCC_90,
			DiagnosisConcepts._DiagnosisConcepts.SCC_91, DiagnosisConcepts._DiagnosisConcepts.SCC_92,
			DiagnosisConcepts._DiagnosisConcepts.SCC_93, DiagnosisConcepts._DiagnosisConcepts.SCC_94,
			DiagnosisConcepts._DiagnosisConcepts.SCC_95, DiagnosisConcepts._DiagnosisConcepts.SCC_96,
			DiagnosisConcepts._DiagnosisConcepts.SCC_97, DiagnosisConcepts._DiagnosisConcepts.SCC_98,
			DiagnosisConcepts._DiagnosisConcepts.SCC_99, DiagnosisConcepts._DiagnosisConcepts.SCC_100,
			DiagnosisConcepts._DiagnosisConcepts.SCC_101, DiagnosisConcepts._DiagnosisConcepts.SCC_102,
			DiagnosisConcepts._DiagnosisConcepts.SCC_103, DiagnosisConcepts._DiagnosisConcepts.SCC_104,
			DiagnosisConcepts._DiagnosisConcepts.SCC_105, DiagnosisConcepts._DiagnosisConcepts.SCC_106,
			DiagnosisConcepts._DiagnosisConcepts.SCC_107, DiagnosisConcepts._DiagnosisConcepts.SCC_108,
			DiagnosisConcepts._DiagnosisConcepts.SCC_109, DiagnosisConcepts._DiagnosisConcepts.SCC_110,
			DiagnosisConcepts._DiagnosisConcepts.SCC_111, DiagnosisConcepts._DiagnosisConcepts.SCC_112,
			DiagnosisConcepts._DiagnosisConcepts.SCC_113, DiagnosisConcepts._DiagnosisConcepts.SCC_114,
			DiagnosisConcepts._DiagnosisConcepts.SCC_115, DiagnosisConcepts._DiagnosisConcepts.SCC_116,
			DiagnosisConcepts._DiagnosisConcepts.SCC_117, DiagnosisConcepts._DiagnosisConcepts.SCC_118,
			DiagnosisConcepts._DiagnosisConcepts.SCC_119, DiagnosisConcepts._DiagnosisConcepts.SCC_120,
			DiagnosisConcepts._DiagnosisConcepts.SCC_121, DiagnosisConcepts._DiagnosisConcepts.SCC_122,
			DiagnosisConcepts._DiagnosisConcepts.SCC_123, DiagnosisConcepts._DiagnosisConcepts.SCC_124,
			DiagnosisConcepts._DiagnosisConcepts.SCC_125, DiagnosisConcepts._DiagnosisConcepts.SCC_126,
			DiagnosisConcepts._DiagnosisConcepts.SCC_127, DiagnosisConcepts._DiagnosisConcepts.SCC_128,
			DiagnosisConcepts._DiagnosisConcepts.SCC_129, DiagnosisConcepts._DiagnosisConcepts.SCC_130,
			DiagnosisConcepts._DiagnosisConcepts.SCC_131, DiagnosisConcepts._DiagnosisConcepts.SCC_132,
			DiagnosisConcepts._DiagnosisConcepts.SCC_133, DiagnosisConcepts._DiagnosisConcepts.SCC_134,
			DiagnosisConcepts._DiagnosisConcepts.SCC_135, DiagnosisConcepts._DiagnosisConcepts.SCC_136,
			DiagnosisConcepts._DiagnosisConcepts.SCC_137, DiagnosisConcepts._DiagnosisConcepts.SCC_138,
			DiagnosisConcepts._DiagnosisConcepts.SCC_139, DiagnosisConcepts._DiagnosisConcepts.SCC_140,
			DiagnosisConcepts._DiagnosisConcepts.SCC_141, DiagnosisConcepts._DiagnosisConcepts.SCC_142,
			DiagnosisConcepts._DiagnosisConcepts.SCC_143, DiagnosisConcepts._DiagnosisConcepts.SCC_144,
			DiagnosisConcepts._DiagnosisConcepts.SCC_145, DiagnosisConcepts._DiagnosisConcepts.SCC_146,
			DiagnosisConcepts._DiagnosisConcepts.SCC_147, DiagnosisConcepts._DiagnosisConcepts.SCC_148,
			DiagnosisConcepts._DiagnosisConcepts.SCC_149, DiagnosisConcepts._DiagnosisConcepts.SCC_150,
			DiagnosisConcepts._DiagnosisConcepts.SCC_151, DiagnosisConcepts._DiagnosisConcepts.SCC_152,
			DiagnosisConcepts._DiagnosisConcepts.SCC_153, DiagnosisConcepts._DiagnosisConcepts.SCC_154,
			DiagnosisConcepts._DiagnosisConcepts.SCC_155, DiagnosisConcepts._DiagnosisConcepts.SCC_156,
			DiagnosisConcepts._DiagnosisConcepts.SCC_157, DiagnosisConcepts._DiagnosisConcepts.SCC_158,
			DiagnosisConcepts._DiagnosisConcepts.SCC_159, DiagnosisConcepts._DiagnosisConcepts.SCC_160,
			DiagnosisConcepts._DiagnosisConcepts.SCC_161, DiagnosisConcepts._DiagnosisConcepts.SCC_162,
			DiagnosisConcepts._DiagnosisConcepts.SCC_163, DiagnosisConcepts._DiagnosisConcepts.SCC_164,
			DiagnosisConcepts._DiagnosisConcepts.SCC_165, DiagnosisConcepts._DiagnosisConcepts.SCC_166,
			DiagnosisConcepts._DiagnosisConcepts.SCC_167, DiagnosisConcepts._DiagnosisConcepts.SCC_168,
			DiagnosisConcepts._DiagnosisConcepts.SCC_169, DiagnosisConcepts._DiagnosisConcepts.SCC_170,
			DiagnosisConcepts._DiagnosisConcepts.SCC_171, DiagnosisConcepts._DiagnosisConcepts.SCC_172,
			DiagnosisConcepts._DiagnosisConcepts.SCC_173, DiagnosisConcepts._DiagnosisConcepts.SCC_174,
			DiagnosisConcepts._DiagnosisConcepts.SCC_175, DiagnosisConcepts._DiagnosisConcepts.SCC_176,
			DiagnosisConcepts._DiagnosisConcepts.SCC_177, DiagnosisConcepts._DiagnosisConcepts.SCC_178,
			DiagnosisConcepts._DiagnosisConcepts.SCC_179, DiagnosisConcepts._DiagnosisConcepts.SCC_180,
			DiagnosisConcepts._DiagnosisConcepts.SCC_181, DiagnosisConcepts._DiagnosisConcepts.SCC_182,
			DiagnosisConcepts._DiagnosisConcepts.SCC_183, DiagnosisConcepts._DiagnosisConcepts.SCC_184,
			DiagnosisConcepts._DiagnosisConcepts.SCC_185, DiagnosisConcepts._DiagnosisConcepts.SCC_186,
			DiagnosisConcepts._DiagnosisConcepts.SCC_187, DiagnosisConcepts._DiagnosisConcepts.SCC_188,
			DiagnosisConcepts._DiagnosisConcepts.SCC_189, DiagnosisConcepts._DiagnosisConcepts.SCC_190,
			DiagnosisConcepts._DiagnosisConcepts.SCC_191, DiagnosisConcepts._DiagnosisConcepts.SCC_192,
			DiagnosisConcepts._DiagnosisConcepts.SCC_193, DiagnosisConcepts._DiagnosisConcepts.SCC_194,
			DiagnosisConcepts._DiagnosisConcepts.SCC_195, DiagnosisConcepts._DiagnosisConcepts.SCC_196,
			DiagnosisConcepts._DiagnosisConcepts.SCC_197, DiagnosisConcepts._DiagnosisConcepts.SCC_198,
			DiagnosisConcepts._DiagnosisConcepts.SCC_199, DiagnosisConcepts._DiagnosisConcepts.SCC_200,
			DiagnosisConcepts._DiagnosisConcepts.SCC_201, DiagnosisConcepts._DiagnosisConcepts.SCC_202,
			DiagnosisConcepts._DiagnosisConcepts.SCC_203, DiagnosisConcepts._DiagnosisConcepts.SCC_204,
			DiagnosisConcepts._DiagnosisConcepts.SCC_205, DiagnosisConcepts._DiagnosisConcepts.SCC_206,
			DiagnosisConcepts._DiagnosisConcepts.SCC_207, DiagnosisConcepts._DiagnosisConcepts.SCC_208,
			DiagnosisConcepts._DiagnosisConcepts.SCC_209, DiagnosisConcepts._DiagnosisConcepts.SCC_210
		);
	}

	//moh705B

	public static List<Integer> getDiarrheaDiagnosisList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.DA_1,
			DiagnosisConcepts._DiagnosisConcepts.DA_2,
			DiagnosisConcepts._DiagnosisConcepts.DA_3,
			DiagnosisConcepts._DiagnosisConcepts.DA_4, 
			DiagnosisConcepts._DiagnosisConcepts.DA_5
		);
	}

	public static List<Integer> getTuberculosisDiagnosisList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.TBA_1,
			DiagnosisConcepts._DiagnosisConcepts.TBA_2,
			DiagnosisConcepts._DiagnosisConcepts.TBA_3,
			DiagnosisConcepts._DiagnosisConcepts.TBA_4,
			DiagnosisConcepts._DiagnosisConcepts.TBA_5,
			DiagnosisConcepts._DiagnosisConcepts.TBA_6,
			DiagnosisConcepts._DiagnosisConcepts.TBA_7,
			DiagnosisConcepts._DiagnosisConcepts.TBA_8, 
			DiagnosisConcepts._DiagnosisConcepts.TBA_9,
			DiagnosisConcepts._DiagnosisConcepts.TBA_10, 
			DiagnosisConcepts._DiagnosisConcepts.TBA_11,
			DiagnosisConcepts._DiagnosisConcepts.TBA_12,
			DiagnosisConcepts._DiagnosisConcepts.TBA_13,
			DiagnosisConcepts._DiagnosisConcepts.TBA_14,
			DiagnosisConcepts._DiagnosisConcepts.TBA_15,
			DiagnosisConcepts._DiagnosisConcepts.TBA_16,
			DiagnosisConcepts._DiagnosisConcepts.TBA_17,
			DiagnosisConcepts._DiagnosisConcepts.TBA_18,
			DiagnosisConcepts._DiagnosisConcepts.TBA_19,
			DiagnosisConcepts._DiagnosisConcepts.TBA_20,
			DiagnosisConcepts._DiagnosisConcepts.TBA_21, 
			DiagnosisConcepts._DiagnosisConcepts.TBA_22,
			DiagnosisConcepts._DiagnosisConcepts.TBA_23, 
			DiagnosisConcepts._DiagnosisConcepts.TBA_24,
			DiagnosisConcepts._DiagnosisConcepts.TBA_25, 
			DiagnosisConcepts._DiagnosisConcepts.TBA_26,
			DiagnosisConcepts._DiagnosisConcepts.TBA_27, 
			DiagnosisConcepts._DiagnosisConcepts.TBA_28,
			DiagnosisConcepts._DiagnosisConcepts.TBA_29, 
			DiagnosisConcepts._DiagnosisConcepts.TBA_30,
			DiagnosisConcepts._DiagnosisConcepts.TBA_31, 
			DiagnosisConcepts._DiagnosisConcepts.TBA_32,
			DiagnosisConcepts._DiagnosisConcepts.TBA_33, 
			DiagnosisConcepts._DiagnosisConcepts.TBA_34,
			DiagnosisConcepts._DiagnosisConcepts.TBA_35, 
			DiagnosisConcepts._DiagnosisConcepts.TBA_36,
			DiagnosisConcepts._DiagnosisConcepts.TBA_37, 
			DiagnosisConcepts._DiagnosisConcepts.TBA_38,
			DiagnosisConcepts._DiagnosisConcepts.TBA_39, 
			DiagnosisConcepts._DiagnosisConcepts.TBA_40,
			DiagnosisConcepts._DiagnosisConcepts.TBA_41
		);
	}

	public static List<Integer> getDysenteryList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.DYA_1,
			DiagnosisConcepts._DiagnosisConcepts.DYA_2);
	}

	public static List<Integer> getCholeraList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.CLA_1,
			DiagnosisConcepts._DiagnosisConcepts.CLA_2,
			DiagnosisConcepts._DiagnosisConcepts.CLA_3,
			DiagnosisConcepts._DiagnosisConcepts.CLA_4,
			DiagnosisConcepts._DiagnosisConcepts.CLA_5,
			DiagnosisConcepts._DiagnosisConcepts.CLA_6,
			DiagnosisConcepts._DiagnosisConcepts.CLA_7

		);
	}

	public static List<Integer> getMeningococcalMeningitisList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.MENINGOCOCCAL_MENINGITIS

		);
	}

	public static List<Integer> getOtherMenigitisListB() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.MOA_1,
			DiagnosisConcepts._DiagnosisConcepts.MOA_2, DiagnosisConcepts._DiagnosisConcepts.MOA_3,
			DiagnosisConcepts._DiagnosisConcepts.MOA_4, DiagnosisConcepts._DiagnosisConcepts.MOA_5,
			DiagnosisConcepts._DiagnosisConcepts.MOA_6, DiagnosisConcepts._DiagnosisConcepts.MOA_7,
			DiagnosisConcepts._DiagnosisConcepts.MOA_8, DiagnosisConcepts._DiagnosisConcepts.MOA_9,
			DiagnosisConcepts._DiagnosisConcepts.MOA_10, DiagnosisConcepts._DiagnosisConcepts.MOA_11,
			DiagnosisConcepts._DiagnosisConcepts.MOA_12, DiagnosisConcepts._DiagnosisConcepts.MOA_13,
			DiagnosisConcepts._DiagnosisConcepts.MOA_14, DiagnosisConcepts._DiagnosisConcepts.MOA_15,
			DiagnosisConcepts._DiagnosisConcepts.MOA_16, DiagnosisConcepts._DiagnosisConcepts.MOA_17,
			DiagnosisConcepts._DiagnosisConcepts.MOA_18, DiagnosisConcepts._DiagnosisConcepts.MOA_19,
			DiagnosisConcepts._DiagnosisConcepts.MOA_20, DiagnosisConcepts._DiagnosisConcepts.MOA_21,
			DiagnosisConcepts._DiagnosisConcepts.MOA_22, DiagnosisConcepts._DiagnosisConcepts.MOA_23,
			DiagnosisConcepts._DiagnosisConcepts.MOA_24, DiagnosisConcepts._DiagnosisConcepts.MOA_25,
			DiagnosisConcepts._DiagnosisConcepts.MOA_26, DiagnosisConcepts._DiagnosisConcepts.MOA_27,
			DiagnosisConcepts._DiagnosisConcepts.MOA_28, DiagnosisConcepts._DiagnosisConcepts.MOA_29,
			DiagnosisConcepts._DiagnosisConcepts.MOA_30, DiagnosisConcepts._DiagnosisConcepts.MOA_31,
			DiagnosisConcepts._DiagnosisConcepts.MOA_32, DiagnosisConcepts._DiagnosisConcepts.MOA_33,
			DiagnosisConcepts._DiagnosisConcepts.MOA_34, DiagnosisConcepts._DiagnosisConcepts.MOA_35,
			DiagnosisConcepts._DiagnosisConcepts.MOA_36, DiagnosisConcepts._DiagnosisConcepts.MOA_37,
			DiagnosisConcepts._DiagnosisConcepts.MOA_38, DiagnosisConcepts._DiagnosisConcepts.MOA_39,
			DiagnosisConcepts._DiagnosisConcepts.MOA_40, DiagnosisConcepts._DiagnosisConcepts.MOA_41,
			DiagnosisConcepts._DiagnosisConcepts.MOA_42, DiagnosisConcepts._DiagnosisConcepts.MOA_43,
			DiagnosisConcepts._DiagnosisConcepts.MOA_44, DiagnosisConcepts._DiagnosisConcepts.MOA_45

		);
	}

	public static List<Integer> getTetanusList() {
		return Arrays.asList(
			DiagnosisConcepts._DiagnosisConcepts.NTA_1, DiagnosisConcepts._DiagnosisConcepts.NTA_2,
			DiagnosisConcepts._DiagnosisConcepts.NTA_3, DiagnosisConcepts._DiagnosisConcepts.NTA_4,
			DiagnosisConcepts._DiagnosisConcepts.NTA_5, DiagnosisConcepts._DiagnosisConcepts.NTA_6,
			DiagnosisConcepts._DiagnosisConcepts.NTA_7, DiagnosisConcepts._DiagnosisConcepts.NTA_8,
			DiagnosisConcepts._DiagnosisConcepts.NTA_9, DiagnosisConcepts._DiagnosisConcepts.NTA_10,
			DiagnosisConcepts._DiagnosisConcepts.NTA_11, DiagnosisConcepts._DiagnosisConcepts.NTA_12,
			DiagnosisConcepts._DiagnosisConcepts.NTA_13, DiagnosisConcepts._DiagnosisConcepts.NTA_14,
			DiagnosisConcepts._DiagnosisConcepts.NTA_15, DiagnosisConcepts._DiagnosisConcepts.NTA_16,
			DiagnosisConcepts._DiagnosisConcepts.NTA_17, DiagnosisConcepts._DiagnosisConcepts.NTA_18,
			DiagnosisConcepts._DiagnosisConcepts.NTA_19, DiagnosisConcepts._DiagnosisConcepts.NTA_20,
			DiagnosisConcepts._DiagnosisConcepts.NTA_21, DiagnosisConcepts._DiagnosisConcepts.NTA_22,
			DiagnosisConcepts._DiagnosisConcepts.NTA_23, DiagnosisConcepts._DiagnosisConcepts.NTA_24,
			DiagnosisConcepts._DiagnosisConcepts.NTA_25, DiagnosisConcepts._DiagnosisConcepts.NTA_26,
			DiagnosisConcepts._DiagnosisConcepts.NTA_27, DiagnosisConcepts._DiagnosisConcepts.NTA_28,
			DiagnosisConcepts._DiagnosisConcepts.NTA_29

		);
	}


	public static List<Integer> getPoliomyelitisListB() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.PMA_1,
			DiagnosisConcepts._DiagnosisConcepts.PMA_2,
			DiagnosisConcepts._DiagnosisConcepts.PMA_3,
			DiagnosisConcepts._DiagnosisConcepts.PMA_4,
			DiagnosisConcepts._DiagnosisConcepts.PMA_5,
			DiagnosisConcepts._DiagnosisConcepts.PMA_6,
			DiagnosisConcepts._DiagnosisConcepts.PMA_7,
			DiagnosisConcepts._DiagnosisConcepts.PMA_8
		);
	}

	public static List<Integer> getChickenPoxListB() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.CPA_1, DiagnosisConcepts._DiagnosisConcepts.CPA_2,
			DiagnosisConcepts._DiagnosisConcepts.CPA_3, DiagnosisConcepts._DiagnosisConcepts.CPA_4,
			DiagnosisConcepts._DiagnosisConcepts.CPA_5, DiagnosisConcepts._DiagnosisConcepts.CPA_6,
			DiagnosisConcepts._DiagnosisConcepts.CPA_7, DiagnosisConcepts._DiagnosisConcepts.CPA_8,
			DiagnosisConcepts._DiagnosisConcepts.CPA_9, DiagnosisConcepts._DiagnosisConcepts.CPA_10

		);
	}


	public static List<Integer> getMeaslesListB() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.MEA_1, DiagnosisConcepts._DiagnosisConcepts.MEA_2,
			DiagnosisConcepts._DiagnosisConcepts.MEA_3, DiagnosisConcepts._DiagnosisConcepts.MEA_4,
			DiagnosisConcepts._DiagnosisConcepts.MEA_5, DiagnosisConcepts._DiagnosisConcepts.MEA_6,
			DiagnosisConcepts._DiagnosisConcepts.MEA_7, DiagnosisConcepts._DiagnosisConcepts.MEA_8,
			DiagnosisConcepts._DiagnosisConcepts.MEA_9

		);
	}


	public static List<Integer> getHepatitisListB() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.HEA_1, DiagnosisConcepts._DiagnosisConcepts.HEA_2,
			DiagnosisConcepts._DiagnosisConcepts.HEA_3, DiagnosisConcepts._DiagnosisConcepts.HEA_4,
			DiagnosisConcepts._DiagnosisConcepts.HEA_5, DiagnosisConcepts._DiagnosisConcepts.HEA_6,
			DiagnosisConcepts._DiagnosisConcepts.HEA_7, DiagnosisConcepts._DiagnosisConcepts.HEA_8,
			DiagnosisConcepts._DiagnosisConcepts.HEA_9, DiagnosisConcepts._DiagnosisConcepts.HEA_10,
			DiagnosisConcepts._DiagnosisConcepts.HEA_11, DiagnosisConcepts._DiagnosisConcepts.HEA_12,
			DiagnosisConcepts._DiagnosisConcepts.HEA_13, DiagnosisConcepts._DiagnosisConcepts.HEA_14,
			DiagnosisConcepts._DiagnosisConcepts.HEA_15, DiagnosisConcepts._DiagnosisConcepts.HEA_16,
			DiagnosisConcepts._DiagnosisConcepts.HEA_17, DiagnosisConcepts._DiagnosisConcepts.HEA_18,
			DiagnosisConcepts._DiagnosisConcepts.HEA_19, DiagnosisConcepts._DiagnosisConcepts.HEA_20,
			DiagnosisConcepts._DiagnosisConcepts.HEA_21, DiagnosisConcepts._DiagnosisConcepts.HEA_22,
			DiagnosisConcepts._DiagnosisConcepts.HEA_23, DiagnosisConcepts._DiagnosisConcepts.HEA_24,
			DiagnosisConcepts._DiagnosisConcepts.HEA_25, DiagnosisConcepts._DiagnosisConcepts.HEA_26,
			DiagnosisConcepts._DiagnosisConcepts.HEA_27, DiagnosisConcepts._DiagnosisConcepts.HEA_28,
			DiagnosisConcepts._DiagnosisConcepts.HEA_29, DiagnosisConcepts._DiagnosisConcepts.HEA_30,
			DiagnosisConcepts._DiagnosisConcepts.HEA_31, DiagnosisConcepts._DiagnosisConcepts.HEA_32,
			DiagnosisConcepts._DiagnosisConcepts.HEA_33, DiagnosisConcepts._DiagnosisConcepts.HEA_34,
			DiagnosisConcepts._DiagnosisConcepts.HEA_35, DiagnosisConcepts._DiagnosisConcepts.HEA_36,
			DiagnosisConcepts._DiagnosisConcepts.HEA_37

		);
	}


	public static List<Integer> getMumpsListB() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.MPSA_1, DiagnosisConcepts._DiagnosisConcepts.MPSA_2,
			DiagnosisConcepts._DiagnosisConcepts.MPSA_3, DiagnosisConcepts._DiagnosisConcepts.MPSA_4
		);
	}
	public static List<Integer> getTestedMalariaListB() {
		return Arrays
			.asList(
				DiagnosisConcepts._DiagnosisConcepts.TMC_1,
				DiagnosisConcepts._DiagnosisConcepts.TMC_2,
				DiagnosisConcepts._DiagnosisConcepts.TMC_3,
				DiagnosisConcepts._DiagnosisConcepts.TMC_4,
				DiagnosisConcepts._DiagnosisConcepts.TMC_5,
				DiagnosisConcepts._DiagnosisConcepts.TMC_6

			);
	}


	public static List<Integer> getMalariaListB() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.COA_1, DiagnosisConcepts._DiagnosisConcepts.COA_2,
			DiagnosisConcepts._DiagnosisConcepts.COA_3, DiagnosisConcepts._DiagnosisConcepts.COA_4,
			DiagnosisConcepts._DiagnosisConcepts.COA_5, DiagnosisConcepts._DiagnosisConcepts.COA_6,
			DiagnosisConcepts._DiagnosisConcepts.COA_7, DiagnosisConcepts._DiagnosisConcepts.COA_8,
			DiagnosisConcepts._DiagnosisConcepts.COA_9, DiagnosisConcepts._DiagnosisConcepts.COA_10,
			DiagnosisConcepts._DiagnosisConcepts.COA_11, DiagnosisConcepts._DiagnosisConcepts.COA_12,
			DiagnosisConcepts._DiagnosisConcepts.COA_13, DiagnosisConcepts._DiagnosisConcepts.COA_14,
			DiagnosisConcepts._DiagnosisConcepts.COA_15, DiagnosisConcepts._DiagnosisConcepts.COA_16,
			DiagnosisConcepts._DiagnosisConcepts.COA_17
		);
	}


	public static List<Integer> getSexuallyTransmittedInfectionsList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.STIA_1, DiagnosisConcepts._DiagnosisConcepts.STIA_2,
			DiagnosisConcepts._DiagnosisConcepts.STIA_3, DiagnosisConcepts._DiagnosisConcepts.STIA_4,
			DiagnosisConcepts._DiagnosisConcepts.STIA_5, DiagnosisConcepts._DiagnosisConcepts.STIA_6,
			DiagnosisConcepts._DiagnosisConcepts.STIA_7, DiagnosisConcepts._DiagnosisConcepts.STIA_8,
			DiagnosisConcepts._DiagnosisConcepts.STIA_9, DiagnosisConcepts._DiagnosisConcepts.STIA_10,
			DiagnosisConcepts._DiagnosisConcepts.STIA_11, DiagnosisConcepts._DiagnosisConcepts.STIA_12,
			DiagnosisConcepts._DiagnosisConcepts.STIA_13, DiagnosisConcepts._DiagnosisConcepts.STIA_14,
			DiagnosisConcepts._DiagnosisConcepts.STIA_15, DiagnosisConcepts._DiagnosisConcepts.STIA_16,
			DiagnosisConcepts._DiagnosisConcepts.STIA_17, DiagnosisConcepts._DiagnosisConcepts.STIA_18,
			DiagnosisConcepts._DiagnosisConcepts.STIA_19, DiagnosisConcepts._DiagnosisConcepts.STIA_20,
			DiagnosisConcepts._DiagnosisConcepts.STIA_21, DiagnosisConcepts._DiagnosisConcepts.STIA_22,
			DiagnosisConcepts._DiagnosisConcepts.STIA_23, DiagnosisConcepts._DiagnosisConcepts.STIA_24,
			DiagnosisConcepts._DiagnosisConcepts.STIA_25, DiagnosisConcepts._DiagnosisConcepts.STIA_26,
			DiagnosisConcepts._DiagnosisConcepts.STIA_27, DiagnosisConcepts._DiagnosisConcepts.STIA_28,
			DiagnosisConcepts._DiagnosisConcepts.STIA_29, DiagnosisConcepts._DiagnosisConcepts.STIA_30,
			DiagnosisConcepts._DiagnosisConcepts.STIA_31, DiagnosisConcepts._DiagnosisConcepts.STIA_32,
			DiagnosisConcepts._DiagnosisConcepts.STIA_33, DiagnosisConcepts._DiagnosisConcepts.STIA_34,
			DiagnosisConcepts._DiagnosisConcepts.STIA_35, DiagnosisConcepts._DiagnosisConcepts.STIA_36,
			DiagnosisConcepts._DiagnosisConcepts.STIA_37, DiagnosisConcepts._DiagnosisConcepts.STIA_38,
			DiagnosisConcepts._DiagnosisConcepts.STIA_39, DiagnosisConcepts._DiagnosisConcepts.STIA_40,
			DiagnosisConcepts._DiagnosisConcepts.STIA_41, DiagnosisConcepts._DiagnosisConcepts.STIA_42,
			DiagnosisConcepts._DiagnosisConcepts.STIA_43, DiagnosisConcepts._DiagnosisConcepts.STIA_44,
			DiagnosisConcepts._DiagnosisConcepts.STIA_45, DiagnosisConcepts._DiagnosisConcepts.STIA_46,
			DiagnosisConcepts._DiagnosisConcepts.STIA_47, DiagnosisConcepts._DiagnosisConcepts.STIA_48,
			DiagnosisConcepts._DiagnosisConcepts.STIA_49, DiagnosisConcepts._DiagnosisConcepts.STIA_50,
			DiagnosisConcepts._DiagnosisConcepts.STIA_51, DiagnosisConcepts._DiagnosisConcepts.STIA_52,
			DiagnosisConcepts._DiagnosisConcepts.STIA_53, DiagnosisConcepts._DiagnosisConcepts.STIA_54,
			DiagnosisConcepts._DiagnosisConcepts.STIA_55, DiagnosisConcepts._DiagnosisConcepts.STIA_56
		);
	}


	public static List<Integer> getUrinaryTractInfectionListB() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.URA_1, DiagnosisConcepts._DiagnosisConcepts.URA_2,
			DiagnosisConcepts._DiagnosisConcepts.URA_3, DiagnosisConcepts._DiagnosisConcepts.URA_4,
			DiagnosisConcepts._DiagnosisConcepts.URA_5, DiagnosisConcepts._DiagnosisConcepts.URA_6,
			DiagnosisConcepts._DiagnosisConcepts.URA_6, DiagnosisConcepts._DiagnosisConcepts.URA_8


		);
	}

	public static List<Integer> getInterstinalwormsListB() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.INA_1, DiagnosisConcepts._DiagnosisConcepts.INA_2,
			DiagnosisConcepts._DiagnosisConcepts.INA_3, DiagnosisConcepts._DiagnosisConcepts.INA_4,
			DiagnosisConcepts._DiagnosisConcepts.INA_5, DiagnosisConcepts._DiagnosisConcepts.INA_6,
			DiagnosisConcepts._DiagnosisConcepts.INA_7, DiagnosisConcepts._DiagnosisConcepts.INA_8
		);
	}

	public static List<Integer> getMalnutritionListB() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.MLA_1, DiagnosisConcepts._DiagnosisConcepts.MLA_2,
			DiagnosisConcepts._DiagnosisConcepts.MLA_3, DiagnosisConcepts._DiagnosisConcepts.MLA_4,
			DiagnosisConcepts._DiagnosisConcepts.MLA_5, DiagnosisConcepts._DiagnosisConcepts.MLA_6,
			DiagnosisConcepts._DiagnosisConcepts.MLA_7


		);
	}

	public static List<Integer> getAnaemiaListB() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.ANEA_1, DiagnosisConcepts._DiagnosisConcepts.ANEA_2,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_3, DiagnosisConcepts._DiagnosisConcepts.ANEA_4,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_5, DiagnosisConcepts._DiagnosisConcepts.ANEA_6,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_7,DiagnosisConcepts._DiagnosisConcepts.ANEA_8,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_9,DiagnosisConcepts._DiagnosisConcepts.ANEA_10,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_11,DiagnosisConcepts._DiagnosisConcepts.ANEA_12,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_13,DiagnosisConcepts._DiagnosisConcepts.ANEA_14,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_15,DiagnosisConcepts._DiagnosisConcepts.ANEA_16,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_17,DiagnosisConcepts._DiagnosisConcepts.ANEA_18,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_19,DiagnosisConcepts._DiagnosisConcepts.ANEA_20,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_21,DiagnosisConcepts._DiagnosisConcepts.ANEA_22,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_23,DiagnosisConcepts._DiagnosisConcepts.ANEA_24,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_25,DiagnosisConcepts._DiagnosisConcepts.ANEA_26,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_27,DiagnosisConcepts._DiagnosisConcepts.ANEA_28,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_29,DiagnosisConcepts._DiagnosisConcepts.ANEA_30,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_31,DiagnosisConcepts._DiagnosisConcepts.ANEA_32,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_33,DiagnosisConcepts._DiagnosisConcepts.ANEA_34,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_35,DiagnosisConcepts._DiagnosisConcepts.ANEA_36,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_37,DiagnosisConcepts._DiagnosisConcepts.ANEA_38,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_39,DiagnosisConcepts._DiagnosisConcepts.ANEA_40,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_41,DiagnosisConcepts._DiagnosisConcepts.ANEA_42,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_43,DiagnosisConcepts._DiagnosisConcepts.ANEA_44,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_45,DiagnosisConcepts._DiagnosisConcepts.ANEA_46,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_47,DiagnosisConcepts._DiagnosisConcepts.ANEA_48,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_49,DiagnosisConcepts._DiagnosisConcepts.ANEA_50,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_51,DiagnosisConcepts._DiagnosisConcepts.ANEA_52,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_53,DiagnosisConcepts._DiagnosisConcepts.ANEA_54,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_55,DiagnosisConcepts._DiagnosisConcepts.ANEA_56,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_57,DiagnosisConcepts._DiagnosisConcepts.ANEA_58,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_59,DiagnosisConcepts._DiagnosisConcepts.ANEA_60,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_61,DiagnosisConcepts._DiagnosisConcepts.ANEA_62,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_63,DiagnosisConcepts._DiagnosisConcepts.ANEA_64,
			DiagnosisConcepts._DiagnosisConcepts.ANEA_65



		);
	}

	//EYE INFECTION B

	public static List<Integer> getEyeInfectionsListB() {
		return Arrays.asList(
			DiagnosisConcepts._DiagnosisConcepts.EYA_1,
			DiagnosisConcepts._DiagnosisConcepts.EYA_2,
			DiagnosisConcepts._DiagnosisConcepts.EYA_3,
			DiagnosisConcepts._DiagnosisConcepts.EYA_4,
			DiagnosisConcepts._DiagnosisConcepts.EYA_5,
			DiagnosisConcepts._DiagnosisConcepts.EYA_6,
			DiagnosisConcepts._DiagnosisConcepts.EYA_7,
			DiagnosisConcepts._DiagnosisConcepts.EYA_8,
			DiagnosisConcepts._DiagnosisConcepts.EYA_9,
			DiagnosisConcepts._DiagnosisConcepts.EYA_10,
			DiagnosisConcepts._DiagnosisConcepts.EYA_11,
			DiagnosisConcepts._DiagnosisConcepts.EYA_12,
			DiagnosisConcepts._DiagnosisConcepts.EYA_13,
			DiagnosisConcepts._DiagnosisConcepts.EYA_14,
			DiagnosisConcepts._DiagnosisConcepts.EYA_15,
			DiagnosisConcepts._DiagnosisConcepts.EYA_16,
			DiagnosisConcepts._DiagnosisConcepts.EYA_17,
			DiagnosisConcepts._DiagnosisConcepts.EYA_18,
			DiagnosisConcepts._DiagnosisConcepts.EYA_19,
			DiagnosisConcepts._DiagnosisConcepts.EYA_20,
			DiagnosisConcepts._DiagnosisConcepts.EYA_21,
			DiagnosisConcepts._DiagnosisConcepts.EYA_22,
			DiagnosisConcepts._DiagnosisConcepts.EYA_23,
			DiagnosisConcepts._DiagnosisConcepts.EYA_24,
			DiagnosisConcepts._DiagnosisConcepts.EYA_25,
			DiagnosisConcepts._DiagnosisConcepts.EYA_26,
			DiagnosisConcepts._DiagnosisConcepts.EYA_27,
			DiagnosisConcepts._DiagnosisConcepts.EYA_28,
			DiagnosisConcepts._DiagnosisConcepts.EYA_29,
			DiagnosisConcepts._DiagnosisConcepts.EYA_30,
			DiagnosisConcepts._DiagnosisConcepts.EYA_31,
			DiagnosisConcepts._DiagnosisConcepts.EYA_32,
			DiagnosisConcepts._DiagnosisConcepts.EYA_33,
			DiagnosisConcepts._DiagnosisConcepts.EYA_34,
			DiagnosisConcepts._DiagnosisConcepts.EYA_35,
			DiagnosisConcepts._DiagnosisConcepts.EYA_36,
			DiagnosisConcepts._DiagnosisConcepts.EYA_37,
			DiagnosisConcepts._DiagnosisConcepts.EYA_38,
			DiagnosisConcepts._DiagnosisConcepts.EYA_39,
			DiagnosisConcepts._DiagnosisConcepts.EYA_40,
			DiagnosisConcepts._DiagnosisConcepts.EYA_41,
			DiagnosisConcepts._DiagnosisConcepts.EYA_42,
			DiagnosisConcepts._DiagnosisConcepts.EYA_43,
			DiagnosisConcepts._DiagnosisConcepts.EYA_44,
			DiagnosisConcepts._DiagnosisConcepts.EYA_45,
			DiagnosisConcepts._DiagnosisConcepts.EYA_46,
			DiagnosisConcepts._DiagnosisConcepts.EYA_47,
			DiagnosisConcepts._DiagnosisConcepts.EYA_48,
			DiagnosisConcepts._DiagnosisConcepts.EYA_49,
			DiagnosisConcepts._DiagnosisConcepts.EYA_50,
			DiagnosisConcepts._DiagnosisConcepts.EYA_51,
			DiagnosisConcepts._DiagnosisConcepts.EYA_52,
			DiagnosisConcepts._DiagnosisConcepts.EYA_53,
			DiagnosisConcepts._DiagnosisConcepts.EYA_54,
			DiagnosisConcepts._DiagnosisConcepts.EYA_55,
			DiagnosisConcepts._DiagnosisConcepts.EYA_56,
			DiagnosisConcepts._DiagnosisConcepts.EYA_57,
			DiagnosisConcepts._DiagnosisConcepts.EYA_58,
			DiagnosisConcepts._DiagnosisConcepts.EYA_59,
			DiagnosisConcepts._DiagnosisConcepts.EYA_60,
			DiagnosisConcepts._DiagnosisConcepts.EYA_61,
			DiagnosisConcepts._DiagnosisConcepts.EYA_62,
			DiagnosisConcepts._DiagnosisConcepts.EYA_63,
			DiagnosisConcepts._DiagnosisConcepts.EYA_64,
			DiagnosisConcepts._DiagnosisConcepts.EYA_65,
			DiagnosisConcepts._DiagnosisConcepts.EYA_66,
			DiagnosisConcepts._DiagnosisConcepts.EYA_67,
			DiagnosisConcepts._DiagnosisConcepts.EYA_68,
			DiagnosisConcepts._DiagnosisConcepts.EYA_69,
			DiagnosisConcepts._DiagnosisConcepts.EYA_70,
			DiagnosisConcepts._DiagnosisConcepts.EYA_71,
			DiagnosisConcepts._DiagnosisConcepts.EYA_72,
			DiagnosisConcepts._DiagnosisConcepts.EYA_73,
			DiagnosisConcepts._DiagnosisConcepts.EYA_74,
			DiagnosisConcepts._DiagnosisConcepts.EYA_75,
			DiagnosisConcepts._DiagnosisConcepts.EYA_76,
			DiagnosisConcepts._DiagnosisConcepts.EYA_77,
			DiagnosisConcepts._DiagnosisConcepts.EYA_78,
			DiagnosisConcepts._DiagnosisConcepts.EYA_79,
			DiagnosisConcepts._DiagnosisConcepts.EYA_80,
			DiagnosisConcepts._DiagnosisConcepts.EYA_81,
			DiagnosisConcepts._DiagnosisConcepts.EYA_82,
			DiagnosisConcepts._DiagnosisConcepts.EYA_83,
			DiagnosisConcepts._DiagnosisConcepts.EYA_84,
			DiagnosisConcepts._DiagnosisConcepts.EYA_85,
			DiagnosisConcepts._DiagnosisConcepts.EYA_86,
			DiagnosisConcepts._DiagnosisConcepts.EYA_87,
			DiagnosisConcepts._DiagnosisConcepts.EYA_88,
			DiagnosisConcepts._DiagnosisConcepts.EYA_89,
			DiagnosisConcepts._DiagnosisConcepts.EYA_90,
			DiagnosisConcepts._DiagnosisConcepts.EYA_91,
			DiagnosisConcepts._DiagnosisConcepts.EYA_92,
			DiagnosisConcepts._DiagnosisConcepts.EYA_93,
			DiagnosisConcepts._DiagnosisConcepts.EYA_94,
			DiagnosisConcepts._DiagnosisConcepts.EYA_95,
			DiagnosisConcepts._DiagnosisConcepts.EYA_96,
			DiagnosisConcepts._DiagnosisConcepts.EYA_97,
			DiagnosisConcepts._DiagnosisConcepts.EYA_98,
			DiagnosisConcepts._DiagnosisConcepts.EYA_99,
			DiagnosisConcepts._DiagnosisConcepts.EYA_100,
			DiagnosisConcepts._DiagnosisConcepts.EYA_101,
			DiagnosisConcepts._DiagnosisConcepts.EYA_102,
			DiagnosisConcepts._DiagnosisConcepts.EYA_103,
			DiagnosisConcepts._DiagnosisConcepts.EYA_104,
			DiagnosisConcepts._DiagnosisConcepts.EYA_105,
			DiagnosisConcepts._DiagnosisConcepts.EYA_106,
			DiagnosisConcepts._DiagnosisConcepts.EYA_107,
			DiagnosisConcepts._DiagnosisConcepts.EYA_108,
			DiagnosisConcepts._DiagnosisConcepts.EYA_109,
			DiagnosisConcepts._DiagnosisConcepts.EYA_110,
			DiagnosisConcepts._DiagnosisConcepts.EYA_111,
			DiagnosisConcepts._DiagnosisConcepts.EYA_112,
			DiagnosisConcepts._DiagnosisConcepts.EYA_113,
			DiagnosisConcepts._DiagnosisConcepts.EYA_114,
			DiagnosisConcepts._DiagnosisConcepts.EYA_115,
			DiagnosisConcepts._DiagnosisConcepts.EYA_116,
			DiagnosisConcepts._DiagnosisConcepts.EYA_117,
			DiagnosisConcepts._DiagnosisConcepts.EYA_118,
			DiagnosisConcepts._DiagnosisConcepts.EYA_119,
			DiagnosisConcepts._DiagnosisConcepts.EYA_120,
			DiagnosisConcepts._DiagnosisConcepts.EYA_121,
			DiagnosisConcepts._DiagnosisConcepts.EYA_122
		);
	}


//Ear Infections

	public static List<Integer> getEarInfectionConditionsList() {
		return Arrays.asList(
			DiagnosisConcepts._DiagnosisConcepts.EIA_1,
			DiagnosisConcepts._DiagnosisConcepts.EIA_2,
			DiagnosisConcepts._DiagnosisConcepts.EIA_3,
			DiagnosisConcepts._DiagnosisConcepts.EIA_4,
			DiagnosisConcepts._DiagnosisConcepts.EIA_5,
			DiagnosisConcepts._DiagnosisConcepts.EIA_6,
			DiagnosisConcepts._DiagnosisConcepts.EIA_7,
			DiagnosisConcepts._DiagnosisConcepts.EIA_8,
			DiagnosisConcepts._DiagnosisConcepts.EIA_9,
			DiagnosisConcepts._DiagnosisConcepts.EIA_10,
			DiagnosisConcepts._DiagnosisConcepts.EIA_11,
			DiagnosisConcepts._DiagnosisConcepts.EIA_12,
			DiagnosisConcepts._DiagnosisConcepts.EIA_13,
			DiagnosisConcepts._DiagnosisConcepts.EIA_14,
			DiagnosisConcepts._DiagnosisConcepts.EIA_15,
			DiagnosisConcepts._DiagnosisConcepts.EIA_16,
			DiagnosisConcepts._DiagnosisConcepts.EIA_17,
			DiagnosisConcepts._DiagnosisConcepts.EIA_18,
			DiagnosisConcepts._DiagnosisConcepts.EIA_19,
			DiagnosisConcepts._DiagnosisConcepts.EIA_20,
			DiagnosisConcepts._DiagnosisConcepts.EIA_21,
			DiagnosisConcepts._DiagnosisConcepts.EIA_22,
			DiagnosisConcepts._DiagnosisConcepts.EIA_23,
			DiagnosisConcepts._DiagnosisConcepts.EIA_24,
			DiagnosisConcepts._DiagnosisConcepts.EIA_25,
			DiagnosisConcepts._DiagnosisConcepts.EIA_26,
			DiagnosisConcepts._DiagnosisConcepts.EIA_27,
			DiagnosisConcepts._DiagnosisConcepts.EIA_28,
			DiagnosisConcepts._DiagnosisConcepts.EIA_29,
			DiagnosisConcepts._DiagnosisConcepts.EIA_30,
			DiagnosisConcepts._DiagnosisConcepts.EIA_31,
			DiagnosisConcepts._DiagnosisConcepts.EIA_32,
			DiagnosisConcepts._DiagnosisConcepts.EIA_33,
			DiagnosisConcepts._DiagnosisConcepts.EIA_34,
			DiagnosisConcepts._DiagnosisConcepts.EIA_35,
			DiagnosisConcepts._DiagnosisConcepts.EIA_36,
			DiagnosisConcepts._DiagnosisConcepts.EIA_37,
			DiagnosisConcepts._DiagnosisConcepts.EIA_38,
			DiagnosisConcepts._DiagnosisConcepts.EIA_39,
			DiagnosisConcepts._DiagnosisConcepts.EIA_40,
			DiagnosisConcepts._DiagnosisConcepts.EIA_41,
			DiagnosisConcepts._DiagnosisConcepts.EIA_42,
			DiagnosisConcepts._DiagnosisConcepts.EIA_43,
			DiagnosisConcepts._DiagnosisConcepts.EIA_44,
			DiagnosisConcepts._DiagnosisConcepts.EIA_45,
			DiagnosisConcepts._DiagnosisConcepts.EIA_46,
			DiagnosisConcepts._DiagnosisConcepts.EIA_47,
			DiagnosisConcepts._DiagnosisConcepts.EIA_48,
			DiagnosisConcepts._DiagnosisConcepts.EIA_49,
			DiagnosisConcepts._DiagnosisConcepts.EIA_50,
			DiagnosisConcepts._DiagnosisConcepts.EIA_51,
			DiagnosisConcepts._DiagnosisConcepts.EIA_52,
			DiagnosisConcepts._DiagnosisConcepts.EIA_53,
			DiagnosisConcepts._DiagnosisConcepts.EIA_54,
			DiagnosisConcepts._DiagnosisConcepts.EIA_55,
			DiagnosisConcepts._DiagnosisConcepts.EIA_56,
			DiagnosisConcepts._DiagnosisConcepts.EIA_57,
			DiagnosisConcepts._DiagnosisConcepts.EIA_58,
			DiagnosisConcepts._DiagnosisConcepts.EIA_59,
			DiagnosisConcepts._DiagnosisConcepts.EIA_60,
			DiagnosisConcepts._DiagnosisConcepts.EIA_61,
			DiagnosisConcepts._DiagnosisConcepts.EIA_62, 
			DiagnosisConcepts._DiagnosisConcepts.EIA_63,
			DiagnosisConcepts._DiagnosisConcepts.EIA_64, 
			DiagnosisConcepts._DiagnosisConcepts.EIA_65
		);
	}

	public static List<Integer> getUpperRespiratoryTractInfectionsListB() {
		return Arrays.asList(
			DiagnosisConcepts._DiagnosisConcepts.UPA_1,
			DiagnosisConcepts._DiagnosisConcepts.UPA_2,
			DiagnosisConcepts._DiagnosisConcepts.UPA_3,
			DiagnosisConcepts._DiagnosisConcepts.UPA_4,
			DiagnosisConcepts._DiagnosisConcepts.UPA_5,
			DiagnosisConcepts._DiagnosisConcepts.UPA_6,
			DiagnosisConcepts._DiagnosisConcepts.UPA_7,
			DiagnosisConcepts._DiagnosisConcepts.UPA_8,
			DiagnosisConcepts._DiagnosisConcepts.UPA_9,
			DiagnosisConcepts._DiagnosisConcepts.UPA_10,
			DiagnosisConcepts._DiagnosisConcepts.UPA_11,
			DiagnosisConcepts._DiagnosisConcepts.UPA_12,
			DiagnosisConcepts._DiagnosisConcepts.UPA_13,
			DiagnosisConcepts._DiagnosisConcepts.UPA_14,
			DiagnosisConcepts._DiagnosisConcepts.UPA_15,
			DiagnosisConcepts._DiagnosisConcepts.UPA_16,
			DiagnosisConcepts._DiagnosisConcepts.UPA_17,
			DiagnosisConcepts._DiagnosisConcepts.UPA_18,
			DiagnosisConcepts._DiagnosisConcepts.UPA_19,
			DiagnosisConcepts._DiagnosisConcepts.UPA_20,
			DiagnosisConcepts._DiagnosisConcepts.UPA_21,
			DiagnosisConcepts._DiagnosisConcepts.UPA_22,
			DiagnosisConcepts._DiagnosisConcepts.UPA_23,
			DiagnosisConcepts._DiagnosisConcepts.UPA_24,
			DiagnosisConcepts._DiagnosisConcepts.UPA_25,
			DiagnosisConcepts._DiagnosisConcepts.UPA_26,
			DiagnosisConcepts._DiagnosisConcepts.UPA_27,
			DiagnosisConcepts._DiagnosisConcepts.UPA_28,
			DiagnosisConcepts._DiagnosisConcepts.UPA_29,
			DiagnosisConcepts._DiagnosisConcepts.UPA_30,
			DiagnosisConcepts._DiagnosisConcepts.UPA_31,
			DiagnosisConcepts._DiagnosisConcepts.UPA_32,
			DiagnosisConcepts._DiagnosisConcepts.UPA_33,
			DiagnosisConcepts._DiagnosisConcepts.UPA_34,
			DiagnosisConcepts._DiagnosisConcepts.UPA_35,
			DiagnosisConcepts._DiagnosisConcepts.UPA_36,
			DiagnosisConcepts._DiagnosisConcepts.UPA_37,
			DiagnosisConcepts._DiagnosisConcepts.UPA_38,
			DiagnosisConcepts._DiagnosisConcepts.UPA_39,
			DiagnosisConcepts._DiagnosisConcepts.UPA_40,
			DiagnosisConcepts._DiagnosisConcepts.UPA_41,
			DiagnosisConcepts._DiagnosisConcepts.UPA_42,
			DiagnosisConcepts._DiagnosisConcepts.UPA_43,
			DiagnosisConcepts._DiagnosisConcepts.UPA_44,
			DiagnosisConcepts._DiagnosisConcepts.UPA_45,
			DiagnosisConcepts._DiagnosisConcepts.UPA_46,
			DiagnosisConcepts._DiagnosisConcepts.UPA_47,
			DiagnosisConcepts._DiagnosisConcepts.UPA_48,
			DiagnosisConcepts._DiagnosisConcepts.UPA_49,
			DiagnosisConcepts._DiagnosisConcepts.UPA_50,
			DiagnosisConcepts._DiagnosisConcepts.UPA_51,
			DiagnosisConcepts._DiagnosisConcepts.UPA_52,
			DiagnosisConcepts._DiagnosisConcepts.UPA_53,
			DiagnosisConcepts._DiagnosisConcepts.UPA_54,
			DiagnosisConcepts._DiagnosisConcepts.UPA_55,
			DiagnosisConcepts._DiagnosisConcepts.UPA_56
		);
	}


	//Asthma
	public static List<Integer> getAsthmaListB() {
		return Arrays.asList(
			DiagnosisConcepts._DiagnosisConcepts.ASA_1,
			DiagnosisConcepts._DiagnosisConcepts.ASA_2,
			DiagnosisConcepts._DiagnosisConcepts.ASA_3,
			DiagnosisConcepts._DiagnosisConcepts.ASA_4,
			DiagnosisConcepts._DiagnosisConcepts.ASA_5,
			DiagnosisConcepts._DiagnosisConcepts.ASA_6,
			DiagnosisConcepts._DiagnosisConcepts.ASA_7,
			DiagnosisConcepts._DiagnosisConcepts.ASA_8,
			DiagnosisConcepts._DiagnosisConcepts.ASA_9,
			DiagnosisConcepts._DiagnosisConcepts.ASA_10,
			DiagnosisConcepts._DiagnosisConcepts.ASA_11,
			DiagnosisConcepts._DiagnosisConcepts.ASA_12,
			DiagnosisConcepts._DiagnosisConcepts.ASA_13,
			DiagnosisConcepts._DiagnosisConcepts.ASA_14,
			DiagnosisConcepts._DiagnosisConcepts.ASA_15,
			DiagnosisConcepts._DiagnosisConcepts.ASA_16,
			DiagnosisConcepts._DiagnosisConcepts.ASA_17
		);
	}

	public static List<Integer> getPneumoniaListB() {
		return Arrays.asList(
			DiagnosisConcepts._DiagnosisConcepts.PNA_1,
			DiagnosisConcepts._DiagnosisConcepts.PNA_2,
			DiagnosisConcepts._DiagnosisConcepts.PNA_3,
			DiagnosisConcepts._DiagnosisConcepts.PNA_4,
			DiagnosisConcepts._DiagnosisConcepts.PNA_5,
			DiagnosisConcepts._DiagnosisConcepts.PNA_6,
			DiagnosisConcepts._DiagnosisConcepts.PNA_7,
			DiagnosisConcepts._DiagnosisConcepts.PNA_8,
			DiagnosisConcepts._DiagnosisConcepts.PNA_9,
			DiagnosisConcepts._DiagnosisConcepts.PNA_10,
			DiagnosisConcepts._DiagnosisConcepts.PNA_11,
			DiagnosisConcepts._DiagnosisConcepts.PNA_12,
			DiagnosisConcepts._DiagnosisConcepts.PNA_13,
			DiagnosisConcepts._DiagnosisConcepts.PNA_14,
			DiagnosisConcepts._DiagnosisConcepts.PNA_15,
			DiagnosisConcepts._DiagnosisConcepts.PNA_16,
			DiagnosisConcepts._DiagnosisConcepts.PNA_17,
			DiagnosisConcepts._DiagnosisConcepts.PNA_18,
			DiagnosisConcepts._DiagnosisConcepts.PNA_19,
			DiagnosisConcepts._DiagnosisConcepts.PNA_20,
			DiagnosisConcepts._DiagnosisConcepts.PNA_21,
			DiagnosisConcepts._DiagnosisConcepts.PNA_22,
			DiagnosisConcepts._DiagnosisConcepts.PNA_23,
			DiagnosisConcepts._DiagnosisConcepts.PNA_24,
			DiagnosisConcepts._DiagnosisConcepts.PNA_25,
			DiagnosisConcepts._DiagnosisConcepts.PNA_26
		);
	}
	// Method to get all skin disease conditions
	public static List<Integer> getDiseasesOfSkin() {
		return Arrays.asList(
			DiagnosisConcepts._DiagnosisConcepts.DS_1,
			DiagnosisConcepts._DiagnosisConcepts.DS_2,
			DiagnosisConcepts._DiagnosisConcepts.DS_3,
			DiagnosisConcepts._DiagnosisConcepts.DS_4,
			DiagnosisConcepts._DiagnosisConcepts.DS_5,
			DiagnosisConcepts._DiagnosisConcepts.DS_6,
			DiagnosisConcepts._DiagnosisConcepts.DS_7,
			DiagnosisConcepts._DiagnosisConcepts.DS_8,
			DiagnosisConcepts._DiagnosisConcepts.DS_9,
			DiagnosisConcepts._DiagnosisConcepts.DS_10,
			DiagnosisConcepts._DiagnosisConcepts.DS_11,
			DiagnosisConcepts._DiagnosisConcepts.DS_12,
			DiagnosisConcepts._DiagnosisConcepts.DS_13,
			DiagnosisConcepts._DiagnosisConcepts.DS_14,
			DiagnosisConcepts._DiagnosisConcepts.DS_15,
			DiagnosisConcepts._DiagnosisConcepts.DS_16,
			DiagnosisConcepts._DiagnosisConcepts.DS_17,
			DiagnosisConcepts._DiagnosisConcepts.DS_18,
			DiagnosisConcepts._DiagnosisConcepts.DS_19,
			DiagnosisConcepts._DiagnosisConcepts.DS_20,
			DiagnosisConcepts._DiagnosisConcepts.DS_21,
			DiagnosisConcepts._DiagnosisConcepts.DS_22,
			DiagnosisConcepts._DiagnosisConcepts.DS_23,
			DiagnosisConcepts._DiagnosisConcepts.DS_24,
			DiagnosisConcepts._DiagnosisConcepts.DS_25,
			DiagnosisConcepts._DiagnosisConcepts.DS_26,
			DiagnosisConcepts._DiagnosisConcepts.DS_27,
			DiagnosisConcepts._DiagnosisConcepts.DS_28,
			DiagnosisConcepts._DiagnosisConcepts.DS_29,
			DiagnosisConcepts._DiagnosisConcepts.DS_30,
			DiagnosisConcepts._DiagnosisConcepts.DS_31,
			DiagnosisConcepts._DiagnosisConcepts.DS_32,
			DiagnosisConcepts._DiagnosisConcepts.DS_33,
			DiagnosisConcepts._DiagnosisConcepts.DS_34,
			DiagnosisConcepts._DiagnosisConcepts.DS_35,
			DiagnosisConcepts._DiagnosisConcepts.DS_36,
			DiagnosisConcepts._DiagnosisConcepts.DS_37,
			DiagnosisConcepts._DiagnosisConcepts.DS_38,
			DiagnosisConcepts._DiagnosisConcepts.DS_39,
			DiagnosisConcepts._DiagnosisConcepts.DS_40,
			DiagnosisConcepts._DiagnosisConcepts.DS_41,
			DiagnosisConcepts._DiagnosisConcepts.DS_42,
			DiagnosisConcepts._DiagnosisConcepts.DS_43,
			DiagnosisConcepts._DiagnosisConcepts.DS_44,
			DiagnosisConcepts._DiagnosisConcepts.DS_45,
			DiagnosisConcepts._DiagnosisConcepts.DS_46,
			DiagnosisConcepts._DiagnosisConcepts.DS_47,
			DiagnosisConcepts._DiagnosisConcepts.DS_48,
			DiagnosisConcepts._DiagnosisConcepts.DS_49,
			DiagnosisConcepts._DiagnosisConcepts.DS_50,
			DiagnosisConcepts._DiagnosisConcepts.DS_51,
			DiagnosisConcepts._DiagnosisConcepts.DS_52,
			DiagnosisConcepts._DiagnosisConcepts.DS_53,
			DiagnosisConcepts._DiagnosisConcepts.DS_54,
			DiagnosisConcepts._DiagnosisConcepts.DS_55,
			DiagnosisConcepts._DiagnosisConcepts.DS_56,
			DiagnosisConcepts._DiagnosisConcepts.DS_57,
			DiagnosisConcepts._DiagnosisConcepts.DS_58,
			DiagnosisConcepts._DiagnosisConcepts.DS_59,
			DiagnosisConcepts._DiagnosisConcepts.DS_60,
			DiagnosisConcepts._DiagnosisConcepts.DS_61,
			DiagnosisConcepts._DiagnosisConcepts.DS_62,
			DiagnosisConcepts._DiagnosisConcepts.DS_63,
			DiagnosisConcepts._DiagnosisConcepts.DS_64,
			DiagnosisConcepts._DiagnosisConcepts.DS_65,
			DiagnosisConcepts._DiagnosisConcepts.DS_66,
			DiagnosisConcepts._DiagnosisConcepts.DS_67,
			DiagnosisConcepts._DiagnosisConcepts.DS_68,
			DiagnosisConcepts._DiagnosisConcepts.DS_69,
			DiagnosisConcepts._DiagnosisConcepts.DS_70,
			DiagnosisConcepts._DiagnosisConcepts.DS_71,
			DiagnosisConcepts._DiagnosisConcepts.DS_72,
			DiagnosisConcepts._DiagnosisConcepts.DS_73,
			DiagnosisConcepts._DiagnosisConcepts.DS_74,
			DiagnosisConcepts._DiagnosisConcepts.DS_75,
			DiagnosisConcepts._DiagnosisConcepts.DS_76,
			DiagnosisConcepts._DiagnosisConcepts.DS_77
		);
	}


	public static List<Integer> getOtherDisOfRespiratorySystemList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.OTRA_1,
			DiagnosisConcepts._DiagnosisConcepts.OTRA_2

		);
	}


	public static List<Integer> getAbortionList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.ABA_1,
			DiagnosisConcepts._DiagnosisConcepts.ABA_2,
			DiagnosisConcepts._DiagnosisConcepts.ABA_3,
			DiagnosisConcepts._DiagnosisConcepts.ABA_4,
			DiagnosisConcepts._DiagnosisConcepts.ABA_5,
			DiagnosisConcepts._DiagnosisConcepts.ABA_6,
			DiagnosisConcepts._DiagnosisConcepts.ABA_7,
			DiagnosisConcepts._DiagnosisConcepts.ABA_8,
			DiagnosisConcepts._DiagnosisConcepts.ABA_9,
			DiagnosisConcepts._DiagnosisConcepts.ABA_10,
			DiagnosisConcepts._DiagnosisConcepts.ABA_11,
			DiagnosisConcepts._DiagnosisConcepts.ABA_12,
			DiagnosisConcepts._DiagnosisConcepts.ABA_13,
			DiagnosisConcepts._DiagnosisConcepts.ABA_14,
			DiagnosisConcepts._DiagnosisConcepts.ABA_15,
			DiagnosisConcepts._DiagnosisConcepts.ABA_16,
			DiagnosisConcepts._DiagnosisConcepts.ABA_17,
			DiagnosisConcepts._DiagnosisConcepts.ABA_18,
			DiagnosisConcepts._DiagnosisConcepts.ABA_19,
			DiagnosisConcepts._DiagnosisConcepts.ABA_20,
			DiagnosisConcepts._DiagnosisConcepts.ABA_21,
			DiagnosisConcepts._DiagnosisConcepts.ABA_22,
			DiagnosisConcepts._DiagnosisConcepts.ABA_23,
			DiagnosisConcepts._DiagnosisConcepts.ABA_24,
			DiagnosisConcepts._DiagnosisConcepts.ABA_25,
			DiagnosisConcepts._DiagnosisConcepts.ABA_26,
			DiagnosisConcepts._DiagnosisConcepts.ABA_27,
			DiagnosisConcepts._DiagnosisConcepts.ABA_28,
			DiagnosisConcepts._DiagnosisConcepts.ABA_29,
			DiagnosisConcepts._DiagnosisConcepts.ABA_30,
			DiagnosisConcepts._DiagnosisConcepts.ABA_31,
			DiagnosisConcepts._DiagnosisConcepts.ABA_32,
			DiagnosisConcepts._DiagnosisConcepts.ABA_33,
			DiagnosisConcepts._DiagnosisConcepts.ABA_34,
			DiagnosisConcepts._DiagnosisConcepts.ABA_35,
			DiagnosisConcepts._DiagnosisConcepts.ABA_36,
			DiagnosisConcepts._DiagnosisConcepts.ABA_37,
			DiagnosisConcepts._DiagnosisConcepts.ABA_38,
			DiagnosisConcepts._DiagnosisConcepts.ABA_39,
			DiagnosisConcepts._DiagnosisConcepts.ABA_40,
			DiagnosisConcepts._DiagnosisConcepts.ABA_41,
			DiagnosisConcepts._DiagnosisConcepts.ABA_42,
			DiagnosisConcepts._DiagnosisConcepts.ABA_43,
			DiagnosisConcepts._DiagnosisConcepts.ABA_44,
			DiagnosisConcepts._DiagnosisConcepts.ABA_45

		);
	}



//Dis. of Puerperium & Childbirth


	public static List<Integer> getDisOfPuerperiumChildbathList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.DPA_1,
			DiagnosisConcepts._DiagnosisConcepts.DPA_2,
			DiagnosisConcepts._DiagnosisConcepts.DPA_3,
			DiagnosisConcepts._DiagnosisConcepts.DPA_4,
			DiagnosisConcepts._DiagnosisConcepts.DPA_5,
			DiagnosisConcepts._DiagnosisConcepts.DPA_6,
			DiagnosisConcepts._DiagnosisConcepts.DPA_7,
			DiagnosisConcepts._DiagnosisConcepts.DPA_8,
			DiagnosisConcepts._DiagnosisConcepts.DPA_9,
			DiagnosisConcepts._DiagnosisConcepts.DPA_10,
			DiagnosisConcepts._DiagnosisConcepts.DPA_11,
			DiagnosisConcepts._DiagnosisConcepts.DPA_12,
			DiagnosisConcepts._DiagnosisConcepts.DPA_13,
			DiagnosisConcepts._DiagnosisConcepts.DPA_14,
			DiagnosisConcepts._DiagnosisConcepts.DPA_15,
			DiagnosisConcepts._DiagnosisConcepts.DPA_16,
			DiagnosisConcepts._DiagnosisConcepts.DPA_17,
			DiagnosisConcepts._DiagnosisConcepts.DPA_18,
			DiagnosisConcepts._DiagnosisConcepts.DPA_19,
			DiagnosisConcepts._DiagnosisConcepts.DPA_20


		);
	}

	public static List<Integer> getHypertensionList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.HYA_1,
			DiagnosisConcepts._DiagnosisConcepts.HYA_2,
			DiagnosisConcepts._DiagnosisConcepts.HYA_3,
			DiagnosisConcepts._DiagnosisConcepts.HYA_4,
			DiagnosisConcepts._DiagnosisConcepts.HYA_5,
			DiagnosisConcepts._DiagnosisConcepts.HYA_6,
			DiagnosisConcepts._DiagnosisConcepts.HYA_7,
			DiagnosisConcepts._DiagnosisConcepts.HYA_8,
			DiagnosisConcepts._DiagnosisConcepts.HYA_9,
			DiagnosisConcepts._DiagnosisConcepts.HYA_10,
			DiagnosisConcepts._DiagnosisConcepts.HYA_11,
			DiagnosisConcepts._DiagnosisConcepts.HYA_12,
			DiagnosisConcepts._DiagnosisConcepts.HYA_13,
			DiagnosisConcepts._DiagnosisConcepts.HYA_14,
			DiagnosisConcepts._DiagnosisConcepts.HYA_15,
			DiagnosisConcepts._DiagnosisConcepts.HYA_16,
			DiagnosisConcepts._DiagnosisConcepts.HYA_17,
			DiagnosisConcepts._DiagnosisConcepts.HYA_18,
			DiagnosisConcepts._DiagnosisConcepts.HYA_19,
			DiagnosisConcepts._DiagnosisConcepts.HYA_20,
			DiagnosisConcepts._DiagnosisConcepts.HYA_21,
			DiagnosisConcepts._DiagnosisConcepts.HYA_22,
			DiagnosisConcepts._DiagnosisConcepts.HYA_23,
			DiagnosisConcepts._DiagnosisConcepts.HYA_24,
			DiagnosisConcepts._DiagnosisConcepts.HYA_25,
			DiagnosisConcepts._DiagnosisConcepts.HYA_26,
			DiagnosisConcepts._DiagnosisConcepts.HYA_27,
			DiagnosisConcepts._DiagnosisConcepts.HYA_28,
			DiagnosisConcepts._DiagnosisConcepts.HYA_29,
			DiagnosisConcepts._DiagnosisConcepts.HYA_30,
			DiagnosisConcepts._DiagnosisConcepts.HYA_31,
			DiagnosisConcepts._DiagnosisConcepts.HYA_32,
			DiagnosisConcepts._DiagnosisConcepts.HYA_33,
			DiagnosisConcepts._DiagnosisConcepts.HYA_34,
			DiagnosisConcepts._DiagnosisConcepts.HYA_35

		);
	}
	
	public static List<Integer> getDentalDisordersListB() {
		return Arrays.asList(
			DiagnosisConcepts._DiagnosisConcepts.DEA_1,
			DiagnosisConcepts._DiagnosisConcepts.DEA_2,
			DiagnosisConcepts._DiagnosisConcepts.DEA_3,
			DiagnosisConcepts._DiagnosisConcepts.DEA_4,
			DiagnosisConcepts._DiagnosisConcepts.DEA_5,
			DiagnosisConcepts._DiagnosisConcepts.DEA_6,
			DiagnosisConcepts._DiagnosisConcepts.DEA_7,
			DiagnosisConcepts._DiagnosisConcepts.DEA_8,
			DiagnosisConcepts._DiagnosisConcepts.DEA_9,
			DiagnosisConcepts._DiagnosisConcepts.DEA_10,
			DiagnosisConcepts._DiagnosisConcepts.DEA_11,
			DiagnosisConcepts._DiagnosisConcepts.DEA_12,
			DiagnosisConcepts._DiagnosisConcepts.DEA_13,
			DiagnosisConcepts._DiagnosisConcepts.DEA_14,
			DiagnosisConcepts._DiagnosisConcepts.DEA_15,
			DiagnosisConcepts._DiagnosisConcepts.DEA_16,
			DiagnosisConcepts._DiagnosisConcepts.DEA_17,
			DiagnosisConcepts._DiagnosisConcepts.DEA_18,
			DiagnosisConcepts._DiagnosisConcepts.DEA_19,
			DiagnosisConcepts._DiagnosisConcepts.DEA_20,
			DiagnosisConcepts._DiagnosisConcepts.DEA_21,
			DiagnosisConcepts._DiagnosisConcepts.DEA_22,
			DiagnosisConcepts._DiagnosisConcepts.DEA_23,
			DiagnosisConcepts._DiagnosisConcepts.DEA_24,
			DiagnosisConcepts._DiagnosisConcepts.DEA_25,
			DiagnosisConcepts._DiagnosisConcepts.DEA_26,
			DiagnosisConcepts._DiagnosisConcepts.DEA_27,
			DiagnosisConcepts._DiagnosisConcepts.DEA_28,
			DiagnosisConcepts._DiagnosisConcepts.DEA_29,
			DiagnosisConcepts._DiagnosisConcepts.DEA_30,
			DiagnosisConcepts._DiagnosisConcepts.DEA_31,
			DiagnosisConcepts._DiagnosisConcepts.DEA_32,
			DiagnosisConcepts._DiagnosisConcepts.DEA_33,
			DiagnosisConcepts._DiagnosisConcepts.DEA_34,
			DiagnosisConcepts._DiagnosisConcepts.DEA_35,
			DiagnosisConcepts._DiagnosisConcepts.DEA_36,
			DiagnosisConcepts._DiagnosisConcepts.DEA_37,
			DiagnosisConcepts._DiagnosisConcepts.DEA_38,
			DiagnosisConcepts._DiagnosisConcepts.DEA_39,
			DiagnosisConcepts._DiagnosisConcepts.DEA_40,
			DiagnosisConcepts._DiagnosisConcepts.DEA_41,
			DiagnosisConcepts._DiagnosisConcepts.DEA_42,
			DiagnosisConcepts._DiagnosisConcepts.DEA_43,
			DiagnosisConcepts._DiagnosisConcepts.DEA_44,
			DiagnosisConcepts._DiagnosisConcepts.DEA_45,
			DiagnosisConcepts._DiagnosisConcepts.DEA_46,
			DiagnosisConcepts._DiagnosisConcepts.DEA_47,
			DiagnosisConcepts._DiagnosisConcepts.DEA_48,
			DiagnosisConcepts._DiagnosisConcepts.DEA_49,
			DiagnosisConcepts._DiagnosisConcepts.DEA_50,
			DiagnosisConcepts._DiagnosisConcepts.DEA_51,
			DiagnosisConcepts._DiagnosisConcepts.DEA_52,
			DiagnosisConcepts._DiagnosisConcepts.DEA_53,
			DiagnosisConcepts._DiagnosisConcepts.DEA_54,
			DiagnosisConcepts._DiagnosisConcepts.DEA_55,
			DiagnosisConcepts._DiagnosisConcepts.DEA_56,
			DiagnosisConcepts._DiagnosisConcepts.DEA_57,
			DiagnosisConcepts._DiagnosisConcepts.DEA_58
		);
	}
	public static List<Integer> getAnthritisJointPainsList() {
		return Arrays.asList(
			DiagnosisConcepts._DiagnosisConcepts.AJPA_1,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_2,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_3,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_4,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_5,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_6,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_7,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_8,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_9,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_10,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_11,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_12,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_13,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_14,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_15,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_16,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_17,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_18,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_19,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_20,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_21,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_22,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_23,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_24,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_25,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_26,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_27,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_28,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_29,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_30,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_31,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_32,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_33,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_34,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_35,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_36,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_37,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_38,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_39,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_40,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_41,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_42,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_43,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_44,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_45,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_46,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_47,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_48,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_49,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_50,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_51,
			DiagnosisConcepts._DiagnosisConcepts.AJPA_52
		);
	}

	public static List<Integer> getOtherCentralNervousSystemConditionsList() {
		return Arrays.asList(
			DiagnosisConcepts._DiagnosisConcepts.CNSA_1,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_2,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_3,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_4,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_5,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_6,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_7,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_8,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_9,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_10,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_11,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_12,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_13,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_14,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_15,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_16,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_17,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_18,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_19,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_20,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_21,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_22,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_23,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_24,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_25,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_26,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_27,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_28,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_29,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_30,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_31,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_32,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_33,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_34,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_35,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_36,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_37,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_38,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_39,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_40,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_41,
			DiagnosisConcepts._DiagnosisConcepts.CNSA_42
		);
	}


	public static List<Integer> getOverweightList() {
		return Arrays.asList(
			DiagnosisConcepts._DiagnosisConcepts.OVA_1,
			DiagnosisConcepts._DiagnosisConcepts.OVA_2,
			DiagnosisConcepts._DiagnosisConcepts.OVA_3,
			DiagnosisConcepts._DiagnosisConcepts.OVA_4,
			DiagnosisConcepts._DiagnosisConcepts.OVA_5,
			DiagnosisConcepts._DiagnosisConcepts.OVA_6
		);
	}

	public static List<Integer> getDiseaseOfTheSkinList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.SKIN_1, DiagnosisConcepts._DiagnosisConcepts.SKIN_2,
			DiagnosisConcepts._DiagnosisConcepts.SKIN_3, DiagnosisConcepts._DiagnosisConcepts.SKIN_4,
			DiagnosisConcepts._DiagnosisConcepts.SKIN_5

		);
	}



	public static List<Integer> getPoisoningList() {
		return Arrays.asList( DiagnosisConcepts._DiagnosisConcepts.POA_1,
			DiagnosisConcepts._DiagnosisConcepts.POA_2,
			DiagnosisConcepts._DiagnosisConcepts.POA_3,
			DiagnosisConcepts._DiagnosisConcepts.POA_4,
			DiagnosisConcepts._DiagnosisConcepts.POA_5,
			DiagnosisConcepts._DiagnosisConcepts.POA_6,
			DiagnosisConcepts._DiagnosisConcepts.POA_7,
			DiagnosisConcepts._DiagnosisConcepts.POA_8,
			DiagnosisConcepts._DiagnosisConcepts.POA_9,
			DiagnosisConcepts._DiagnosisConcepts.POA_10

		);
	}


	public static List<Integer> getCardiovascularConditionsList() {
		return Arrays.asList( DiagnosisConcepts._DiagnosisConcepts.CAA_1,
			DiagnosisConcepts._DiagnosisConcepts.CAA_2,
			DiagnosisConcepts._DiagnosisConcepts.CAA_3,
			DiagnosisConcepts._DiagnosisConcepts.CAA_4

		);
	}

//End concept mapping Refactoring for 705A and 705B









	public static List<Integer> getMalnutritionList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.MALN1, DiagnosisConcepts._DiagnosisConcepts.MALN2,
			DiagnosisConcepts._DiagnosisConcepts.MALN3, DiagnosisConcepts._DiagnosisConcepts.MALN4,
			DiagnosisConcepts._DiagnosisConcepts.MALN5, DiagnosisConcepts._DiagnosisConcepts.MALN6,
			DiagnosisConcepts._DiagnosisConcepts.MALN7, DiagnosisConcepts._DiagnosisConcepts.MALN8,
			DiagnosisConcepts._DiagnosisConcepts.MALN9, DiagnosisConcepts._DiagnosisConcepts.MALN10,
			DiagnosisConcepts._DiagnosisConcepts.MALN11, DiagnosisConcepts._DiagnosisConcepts.MALN12,
			DiagnosisConcepts._DiagnosisConcepts.MALN13, DiagnosisConcepts._DiagnosisConcepts.MALN14,
			DiagnosisConcepts._DiagnosisConcepts.MALN15, DiagnosisConcepts._DiagnosisConcepts.MALN16,
			DiagnosisConcepts._DiagnosisConcepts.MALN17, DiagnosisConcepts._DiagnosisConcepts.MALN18,
			DiagnosisConcepts._DiagnosisConcepts.MALN19, DiagnosisConcepts._DiagnosisConcepts.MALN20,
			DiagnosisConcepts._DiagnosisConcepts.MALN21, DiagnosisConcepts._DiagnosisConcepts.MALN22,
			DiagnosisConcepts._DiagnosisConcepts.MALN23, DiagnosisConcepts._DiagnosisConcepts.MALN24,
			DiagnosisConcepts._DiagnosisConcepts.MALN25, DiagnosisConcepts._DiagnosisConcepts.MALN26,
			DiagnosisConcepts._DiagnosisConcepts.MALN27, DiagnosisConcepts._DiagnosisConcepts.MALN28,
			DiagnosisConcepts._DiagnosisConcepts.MALN29, DiagnosisConcepts._DiagnosisConcepts.MALN30

		);
	}

	public static List<Integer> getAnaemiaList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.ANEMIA1, DiagnosisConcepts._DiagnosisConcepts.ANEMIA2,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA3, DiagnosisConcepts._DiagnosisConcepts.ANEMIA4,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA5, DiagnosisConcepts._DiagnosisConcepts.ANEMIA6,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA7, DiagnosisConcepts._DiagnosisConcepts.ANEMIA8,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA9, DiagnosisConcepts._DiagnosisConcepts.ANEMIA10,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA11, DiagnosisConcepts._DiagnosisConcepts.ANEMIA12,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA13, DiagnosisConcepts._DiagnosisConcepts.ANEMIA14,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA15, DiagnosisConcepts._DiagnosisConcepts.ANEMIA16,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA17, DiagnosisConcepts._DiagnosisConcepts.ANEMIA18,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA19, DiagnosisConcepts._DiagnosisConcepts.ANEMIA20,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA21, DiagnosisConcepts._DiagnosisConcepts.ANEMIA22,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA23, DiagnosisConcepts._DiagnosisConcepts.ANEMIA24,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA25, DiagnosisConcepts._DiagnosisConcepts.ANEMIA26,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA27, DiagnosisConcepts._DiagnosisConcepts.ANEMIA28,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA29, DiagnosisConcepts._DiagnosisConcepts.ANEMIA30,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA31, DiagnosisConcepts._DiagnosisConcepts.ANEMIA32,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA33, DiagnosisConcepts._DiagnosisConcepts.ANEMIA34,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA35, DiagnosisConcepts._DiagnosisConcepts.ANEMIA36,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA37, DiagnosisConcepts._DiagnosisConcepts.ANEMIA38,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA39, DiagnosisConcepts._DiagnosisConcepts.ANEMIA40,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA41, DiagnosisConcepts._DiagnosisConcepts.ANEMIA42,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA43, DiagnosisConcepts._DiagnosisConcepts.ANEMIA44,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA45, DiagnosisConcepts._DiagnosisConcepts.ANEMIA46,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA47, DiagnosisConcepts._DiagnosisConcepts.ANEMIA48,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA49, DiagnosisConcepts._DiagnosisConcepts.ANEMIA50,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA51, DiagnosisConcepts._DiagnosisConcepts.ANEMIA52,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA53, DiagnosisConcepts._DiagnosisConcepts.ANEMIA54,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA55, DiagnosisConcepts._DiagnosisConcepts.ANEMIA56,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA57, DiagnosisConcepts._DiagnosisConcepts.ANEMIA58,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA59, DiagnosisConcepts._DiagnosisConcepts.ANEMIA60,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA61, DiagnosisConcepts._DiagnosisConcepts.ANEMIA62,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA63, DiagnosisConcepts._DiagnosisConcepts.ANEMIA64,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA65, DiagnosisConcepts._DiagnosisConcepts.ANEMIA66,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA67, DiagnosisConcepts._DiagnosisConcepts.ANEMIA68,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA69, DiagnosisConcepts._DiagnosisConcepts.ANEMIA70,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA71, DiagnosisConcepts._DiagnosisConcepts.ANEMIA72,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA73, DiagnosisConcepts._DiagnosisConcepts.ANEMIA74,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA75, DiagnosisConcepts._DiagnosisConcepts.ANEMIA76,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA77, DiagnosisConcepts._DiagnosisConcepts.ANEMIA78,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA79, DiagnosisConcepts._DiagnosisConcepts.ANEMIA80,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA81, DiagnosisConcepts._DiagnosisConcepts.ANEMIA82,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA83, DiagnosisConcepts._DiagnosisConcepts.ANEMIA84,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA85, DiagnosisConcepts._DiagnosisConcepts.ANEMIA86,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA87, DiagnosisConcepts._DiagnosisConcepts.ANEMIA88,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA89, DiagnosisConcepts._DiagnosisConcepts.ANEMIA90,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA91, DiagnosisConcepts._DiagnosisConcepts.ANEMIA92,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA93, DiagnosisConcepts._DiagnosisConcepts.ANEMIA94,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA95, DiagnosisConcepts._DiagnosisConcepts.ANEMIA96,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA97, DiagnosisConcepts._DiagnosisConcepts.ANEMIA98,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA99, DiagnosisConcepts._DiagnosisConcepts.ANEMIA100,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA101, DiagnosisConcepts._DiagnosisConcepts.ANEMIA102,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA103, DiagnosisConcepts._DiagnosisConcepts.ANEMIA104,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA105, DiagnosisConcepts._DiagnosisConcepts.ANEMIA106,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA107, DiagnosisConcepts._DiagnosisConcepts.ANEMIA108,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA109, DiagnosisConcepts._DiagnosisConcepts.ANEMIA110,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA111, DiagnosisConcepts._DiagnosisConcepts.ANEMIA112,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA113, DiagnosisConcepts._DiagnosisConcepts.ANEMIA114,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA115, DiagnosisConcepts._DiagnosisConcepts.ANEMIA116,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA117, DiagnosisConcepts._DiagnosisConcepts.ANEMIA118,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA119, DiagnosisConcepts._DiagnosisConcepts.ANEMIA120,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA121, DiagnosisConcepts._DiagnosisConcepts.ANEMIA122,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA123, DiagnosisConcepts._DiagnosisConcepts.ANEMIA124,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA125, DiagnosisConcepts._DiagnosisConcepts.ANEMIA126,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA127, DiagnosisConcepts._DiagnosisConcepts.ANEMIA128,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA129, DiagnosisConcepts._DiagnosisConcepts.ANEMIA130,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA131, DiagnosisConcepts._DiagnosisConcepts.ANEMIA132,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA133, DiagnosisConcepts._DiagnosisConcepts.ANEMIA134,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA135, DiagnosisConcepts._DiagnosisConcepts.ANEMIA136,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA137, DiagnosisConcepts._DiagnosisConcepts.ANEMIA138,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA139, DiagnosisConcepts._DiagnosisConcepts.ANEMIA140,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA141, DiagnosisConcepts._DiagnosisConcepts.ANEMIA142,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA143, DiagnosisConcepts._DiagnosisConcepts.ANEMIA144,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA145, DiagnosisConcepts._DiagnosisConcepts.ANEMIA146,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA147, DiagnosisConcepts._DiagnosisConcepts.ANEMIA148,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA149, DiagnosisConcepts._DiagnosisConcepts.ANEMIA150,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA151, DiagnosisConcepts._DiagnosisConcepts.ANEMIA152,
			DiagnosisConcepts._DiagnosisConcepts.ANEMIA153

		);
	}







	public static List<Integer> getAsthmaList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.ASTHMA1, DiagnosisConcepts._DiagnosisConcepts.ASTHMA2,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA3, DiagnosisConcepts._DiagnosisConcepts.ASTHMA4,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA5, DiagnosisConcepts._DiagnosisConcepts.ASTHMA6,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA6, DiagnosisConcepts._DiagnosisConcepts.ASTHMA7,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA8, DiagnosisConcepts._DiagnosisConcepts.ASTHMA9,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA10, DiagnosisConcepts._DiagnosisConcepts.ASTHMA11,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA12, DiagnosisConcepts._DiagnosisConcepts.ASTHMA13,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA14, DiagnosisConcepts._DiagnosisConcepts.ASTHMA15,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA16, DiagnosisConcepts._DiagnosisConcepts.ASTHMA17,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA18, DiagnosisConcepts._DiagnosisConcepts.ASTHMA19,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA20, DiagnosisConcepts._DiagnosisConcepts.ASTHMA21,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA22, DiagnosisConcepts._DiagnosisConcepts.ASTHMA23,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA24, DiagnosisConcepts._DiagnosisConcepts.ASTHMA25,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA26, DiagnosisConcepts._DiagnosisConcepts.ASTHMA27,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA28, DiagnosisConcepts._DiagnosisConcepts.ASTHMA29,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA30, DiagnosisConcepts._DiagnosisConcepts.ASTHMA31,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA32, DiagnosisConcepts._DiagnosisConcepts.ASTHMA33,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA34, DiagnosisConcepts._DiagnosisConcepts.ASTHMA35,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA36, DiagnosisConcepts._DiagnosisConcepts.ASTHMA37,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA38, DiagnosisConcepts._DiagnosisConcepts.ASTHMA39,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA40, DiagnosisConcepts._DiagnosisConcepts.ASTHMA41,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA42, DiagnosisConcepts._DiagnosisConcepts.ASTHMA43,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA44, DiagnosisConcepts._DiagnosisConcepts.ASTHMA45,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA46, DiagnosisConcepts._DiagnosisConcepts.ASTHMA47,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA48, DiagnosisConcepts._DiagnosisConcepts.ASTHMA49,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA50, DiagnosisConcepts._DiagnosisConcepts.ASTHMA51,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA52, DiagnosisConcepts._DiagnosisConcepts.ASTHMA53,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA54, DiagnosisConcepts._DiagnosisConcepts.ASTHMA55,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA56, DiagnosisConcepts._DiagnosisConcepts.ASTHMA57,
			DiagnosisConcepts._DiagnosisConcepts.ASTHMA58

		);
	}



	public static List<Integer> getChromosomalAbnormalitiesList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.CHRAB1, DiagnosisConcepts._DiagnosisConcepts.CHRAB2,
			DiagnosisConcepts._DiagnosisConcepts.CHRAB3, DiagnosisConcepts._DiagnosisConcepts.CHRAB4,
			DiagnosisConcepts._DiagnosisConcepts.CHRAB5, DiagnosisConcepts._DiagnosisConcepts.CHRAB6,
			DiagnosisConcepts._DiagnosisConcepts.CHRAB7, DiagnosisConcepts._DiagnosisConcepts.CHRAB8,
			DiagnosisConcepts._DiagnosisConcepts.CHRAB9, DiagnosisConcepts._DiagnosisConcepts.CHRAB10

		);
	}

	public static List<Integer> getCongenitalAnomaliesList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.COAN1, DiagnosisConcepts._DiagnosisConcepts.COAN2,
			DiagnosisConcepts._DiagnosisConcepts.COAN3, DiagnosisConcepts._DiagnosisConcepts.COAN4,
			DiagnosisConcepts._DiagnosisConcepts.COAN5, DiagnosisConcepts._DiagnosisConcepts.COAN7,
			DiagnosisConcepts._DiagnosisConcepts.COAN8, DiagnosisConcepts._DiagnosisConcepts.COAN9,
			DiagnosisConcepts._DiagnosisConcepts.COAN10, DiagnosisConcepts._DiagnosisConcepts.COAN11,
			DiagnosisConcepts._DiagnosisConcepts.COAN12, DiagnosisConcepts._DiagnosisConcepts.COAN13,
			DiagnosisConcepts._DiagnosisConcepts.COAN14, DiagnosisConcepts._DiagnosisConcepts.COAN15,
			DiagnosisConcepts._DiagnosisConcepts.COAN16, DiagnosisConcepts._DiagnosisConcepts.COAN17,
			DiagnosisConcepts._DiagnosisConcepts.COAN18, DiagnosisConcepts._DiagnosisConcepts.COAN19,
			DiagnosisConcepts._DiagnosisConcepts.COAN20, DiagnosisConcepts._DiagnosisConcepts.COAN21,
			DiagnosisConcepts._DiagnosisConcepts.COAN22, DiagnosisConcepts._DiagnosisConcepts.COAN23,
			DiagnosisConcepts._DiagnosisConcepts.COAN24, DiagnosisConcepts._DiagnosisConcepts.COAN25,
			DiagnosisConcepts._DiagnosisConcepts.COAN26, DiagnosisConcepts._DiagnosisConcepts.COAN27,
			DiagnosisConcepts._DiagnosisConcepts.COAN28, DiagnosisConcepts._DiagnosisConcepts.COAN29,
			DiagnosisConcepts._DiagnosisConcepts.COAN30, DiagnosisConcepts._DiagnosisConcepts.COAN31,
			DiagnosisConcepts._DiagnosisConcepts.COAN32, DiagnosisConcepts._DiagnosisConcepts.COAN33,
			DiagnosisConcepts._DiagnosisConcepts.COAN34, DiagnosisConcepts._DiagnosisConcepts.COAN35,
			DiagnosisConcepts._DiagnosisConcepts.COAN36, DiagnosisConcepts._DiagnosisConcepts.COAN37,
			DiagnosisConcepts._DiagnosisConcepts.COAN38, DiagnosisConcepts._DiagnosisConcepts.COAN39,
			DiagnosisConcepts._DiagnosisConcepts.COAN40, DiagnosisConcepts._DiagnosisConcepts.COAN41,
			DiagnosisConcepts._DiagnosisConcepts.COAN42, DiagnosisConcepts._DiagnosisConcepts.COAN43,
			DiagnosisConcepts._DiagnosisConcepts.COAN44, DiagnosisConcepts._DiagnosisConcepts.COAN45,
			DiagnosisConcepts._DiagnosisConcepts.COAN46, DiagnosisConcepts._DiagnosisConcepts.COAN47,
			DiagnosisConcepts._DiagnosisConcepts.COAN48, DiagnosisConcepts._DiagnosisConcepts.COAN49,
			DiagnosisConcepts._DiagnosisConcepts.COAN50, DiagnosisConcepts._DiagnosisConcepts.COAN51,
			DiagnosisConcepts._DiagnosisConcepts.COAN52, DiagnosisConcepts._DiagnosisConcepts.COAN53,
			DiagnosisConcepts._DiagnosisConcepts.COAN54, DiagnosisConcepts._DiagnosisConcepts.COAN55,
			DiagnosisConcepts._DiagnosisConcepts.COAN56, DiagnosisConcepts._DiagnosisConcepts.COAN57,
			DiagnosisConcepts._DiagnosisConcepts.COAN58, DiagnosisConcepts._DiagnosisConcepts.COAN59,
			DiagnosisConcepts._DiagnosisConcepts.COAN60, DiagnosisConcepts._DiagnosisConcepts.COAN61,
			DiagnosisConcepts._DiagnosisConcepts.COAN62, DiagnosisConcepts._DiagnosisConcepts.COAN63,
			DiagnosisConcepts._DiagnosisConcepts.COAN64, DiagnosisConcepts._DiagnosisConcepts.COAN65,
			DiagnosisConcepts._DiagnosisConcepts.COAN66, DiagnosisConcepts._DiagnosisConcepts.COAN67,
			DiagnosisConcepts._DiagnosisConcepts.COAN68, DiagnosisConcepts._DiagnosisConcepts.COAN69,
			DiagnosisConcepts._DiagnosisConcepts.COAN70, DiagnosisConcepts._DiagnosisConcepts.COAN71,
			DiagnosisConcepts._DiagnosisConcepts.COAN72, DiagnosisConcepts._DiagnosisConcepts.COAN73,
			DiagnosisConcepts._DiagnosisConcepts.COAN74, DiagnosisConcepts._DiagnosisConcepts.COAN75,
			DiagnosisConcepts._DiagnosisConcepts.COAN76, DiagnosisConcepts._DiagnosisConcepts.COAN77,
			DiagnosisConcepts._DiagnosisConcepts.COAN78, DiagnosisConcepts._DiagnosisConcepts.COAN79,
			DiagnosisConcepts._DiagnosisConcepts.COAN80, DiagnosisConcepts._DiagnosisConcepts.COAN81,
			DiagnosisConcepts._DiagnosisConcepts.COAN82, DiagnosisConcepts._DiagnosisConcepts.COAN83,
			DiagnosisConcepts._DiagnosisConcepts.COAN84, DiagnosisConcepts._DiagnosisConcepts.COAN85,
			DiagnosisConcepts._DiagnosisConcepts.COAN86, DiagnosisConcepts._DiagnosisConcepts.COAN87,
			DiagnosisConcepts._DiagnosisConcepts.COAN88, DiagnosisConcepts._DiagnosisConcepts.COAN89,
			DiagnosisConcepts._DiagnosisConcepts.COAN90, DiagnosisConcepts._DiagnosisConcepts.COAN91,
			DiagnosisConcepts._DiagnosisConcepts.COAN92, DiagnosisConcepts._DiagnosisConcepts.COAN93,
			DiagnosisConcepts._DiagnosisConcepts.COAN94, DiagnosisConcepts._DiagnosisConcepts.COAN95,
			DiagnosisConcepts._DiagnosisConcepts.COAN96, DiagnosisConcepts._DiagnosisConcepts.COAN97,
			DiagnosisConcepts._DiagnosisConcepts.COAN98, DiagnosisConcepts._DiagnosisConcepts.COAN99,
			DiagnosisConcepts._DiagnosisConcepts.COAN100, DiagnosisConcepts._DiagnosisConcepts.COAN101,
			DiagnosisConcepts._DiagnosisConcepts.COAN121, DiagnosisConcepts._DiagnosisConcepts.COAN120,
			DiagnosisConcepts._DiagnosisConcepts.COAN122, DiagnosisConcepts._DiagnosisConcepts.COAN123,
			DiagnosisConcepts._DiagnosisConcepts.COAN124, DiagnosisConcepts._DiagnosisConcepts.COAN125,
			DiagnosisConcepts._DiagnosisConcepts.COAN126, DiagnosisConcepts._DiagnosisConcepts.COAN127,
			DiagnosisConcepts._DiagnosisConcepts.COAN128, DiagnosisConcepts._DiagnosisConcepts.COAN129,
			DiagnosisConcepts._DiagnosisConcepts.COAN130, DiagnosisConcepts._DiagnosisConcepts.COAN131,
			DiagnosisConcepts._DiagnosisConcepts.COAN132, DiagnosisConcepts._DiagnosisConcepts.COAN133,
			DiagnosisConcepts._DiagnosisConcepts.COAN134, DiagnosisConcepts._DiagnosisConcepts.COAN135,
			DiagnosisConcepts._DiagnosisConcepts.COAN136, DiagnosisConcepts._DiagnosisConcepts.COAN137,
			DiagnosisConcepts._DiagnosisConcepts.COAN138, DiagnosisConcepts._DiagnosisConcepts.COAN139,
			DiagnosisConcepts._DiagnosisConcepts.COAN140, DiagnosisConcepts._DiagnosisConcepts.COAN141,
			DiagnosisConcepts._DiagnosisConcepts.COAN142, DiagnosisConcepts._DiagnosisConcepts.COAN143,
			DiagnosisConcepts._DiagnosisConcepts.COAN144, DiagnosisConcepts._DiagnosisConcepts.COAN145,
			DiagnosisConcepts._DiagnosisConcepts.COAN146, DiagnosisConcepts._DiagnosisConcepts.COAN147,
			DiagnosisConcepts._DiagnosisConcepts.COAN148, DiagnosisConcepts._DiagnosisConcepts.COAN149,
			DiagnosisConcepts._DiagnosisConcepts.COAN150, DiagnosisConcepts._DiagnosisConcepts.COAN151,
			DiagnosisConcepts._DiagnosisConcepts.COAN152, DiagnosisConcepts._DiagnosisConcepts.COAN153,
			DiagnosisConcepts._DiagnosisConcepts.COAN154, DiagnosisConcepts._DiagnosisConcepts.COAN155,
			DiagnosisConcepts._DiagnosisConcepts.COAN156, DiagnosisConcepts._DiagnosisConcepts.COAN157,
			DiagnosisConcepts._DiagnosisConcepts.COAN158, DiagnosisConcepts._DiagnosisConcepts.COAN159,
			DiagnosisConcepts._DiagnosisConcepts.COAN160, DiagnosisConcepts._DiagnosisConcepts.COAN161);
	}

	public static List<Integer> getPneumoniaList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_1,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_2, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_3,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_4, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_6,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_7, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_8,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_9, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_11,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_12, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_13,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_14, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_15,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_16, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_17,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_18, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_19,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_20, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_21,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_23, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_24,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_25, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_26,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_27, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_28,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_29, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_30,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_31, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_32,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_33, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_34,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_35, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_36,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_37, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_38,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_39, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_40,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_41, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_42,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_43, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_44,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_45, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_46,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_47, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_48,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_49, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_50,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_51, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_52,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_53, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_58,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_55, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_56,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_57, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_59,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_60, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_61,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_62, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_63,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_64, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_65,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_66, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_67,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_68, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_69,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_70, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_71,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_72, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_73,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_74, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_75,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_76, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_77,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_78, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_79,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_80, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_81,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_82, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_83,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_84, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_85,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_86, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_87,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_88, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_89,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_90, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_91,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_92, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_93,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_94, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_95,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_96, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_97,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_98, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_99,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_100, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_101,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_102, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_103,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_107, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_108,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_109, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_110,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_111, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_112,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_113, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_114,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_115, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_117,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_118, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_119,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_120, DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_121,
			DiagnosisConcepts._DiagnosisConcepts.PNEUMONIA_122);
	}




	public static List<Concept> getDiabetesListConcepts() {
		return Arrays.asList(

			getConcept(DiabetesDiagnosisConstants.DIABETIC1), getConcept(DiabetesDiagnosisConstants.DIABETIC2),
			getConcept(DiabetesDiagnosisConstants.DIABETIC3), getConcept(DiabetesDiagnosisConstants.DIABETIC4),
			getConcept(DiabetesDiagnosisConstants.DIABETIC5), getConcept(DiabetesDiagnosisConstants.DIABETIC6),
			getConcept(DiabetesDiagnosisConstants.DIABETIC7), getConcept(DiabetesDiagnosisConstants.DIABETIC8),
			getConcept(DiabetesDiagnosisConstants.DIABETIC9), getConcept(DiabetesDiagnosisConstants.DIABETIC10),
			getConcept(DiabetesDiagnosisConstants.DIABETIC11), getConcept(DiabetesDiagnosisConstants.DIABETIC12),
			getConcept(DiabetesDiagnosisConstants.DIABETIC13), getConcept(DiabetesDiagnosisConstants.DIABETIC14),
			getConcept(DiabetesDiagnosisConstants.DIABETIC15), getConcept(DiabetesDiagnosisConstants.DIABETIC16),
			getConcept(DiabetesDiagnosisConstants.DIABETIC17), getConcept(DiabetesDiagnosisConstants.DIABETIC18),
			getConcept(DiabetesDiagnosisConstants.DIABETIC19), getConcept(DiabetesDiagnosisConstants.DIABETIC20),
			getConcept(DiabetesDiagnosisConstants.DIABETIC21), getConcept(DiabetesDiagnosisConstants.DIABETIC22),
			getConcept(DiabetesDiagnosisConstants.DIABETIC23), getConcept(DiabetesDiagnosisConstants.DIABETIC24),
			getConcept(DiabetesDiagnosisConstants.DIABETIC25), getConcept(DiabetesDiagnosisConstants.DIABETIC26),
			getConcept(DiabetesDiagnosisConstants.DIABETIC27), getConcept(DiabetesDiagnosisConstants.DIABETIC28),
			getConcept(DiabetesDiagnosisConstants.DIABETIC29), getConcept(DiabetesDiagnosisConstants.DIABETIC30),
			getConcept(DiabetesDiagnosisConstants.DIABETIC31), getConcept(DiabetesDiagnosisConstants.DIABETIC32),
			getConcept(DiabetesDiagnosisConstants.DIABETIC33), getConcept(DiabetesDiagnosisConstants.DIABETIC34),
			getConcept(DiabetesDiagnosisConstants.DIABETIC35), getConcept(DiabetesDiagnosisConstants.DIABETIC36),
			getConcept(DiabetesDiagnosisConstants.DIABETIC37), getConcept(DiabetesDiagnosisConstants.DIABETIC38),
			getConcept(DiabetesDiagnosisConstants.DIABETIC39), getConcept(DiabetesDiagnosisConstants.DIABETIC40),
			getConcept(DiabetesDiagnosisConstants.DIABETIC41), getConcept(DiabetesDiagnosisConstants.DIABETIC42),
			getConcept(DiabetesDiagnosisConstants.DIABETIC43), getConcept(DiabetesDiagnosisConstants.DIABETIC44),
			getConcept(DiabetesDiagnosisConstants.DIABETIC45), getConcept(DiabetesDiagnosisConstants.DIABETIC46),
			getConcept(DiabetesDiagnosisConstants.DIABETIC47), getConcept(DiabetesDiagnosisConstants.DIABETIC48),
			getConcept(DiabetesDiagnosisConstants.DIABETIC49), getConcept(DiabetesDiagnosisConstants.DIABETIC50),
			getConcept(DiabetesDiagnosisConstants.DIABETIC51), getConcept(DiabetesDiagnosisConstants.DIABETIC52),
			getConcept(DiabetesDiagnosisConstants.DIABETIC53), getConcept(DiabetesDiagnosisConstants.DIABETIC54),
			getConcept(DiabetesDiagnosisConstants.DIABETIC55), getConcept(DiabetesDiagnosisConstants.DIABETIC56),
			getConcept(DiabetesDiagnosisConstants.DIABETIC57), getConcept(DiabetesDiagnosisConstants.DIABETIC58),
			getConcept(DiabetesDiagnosisConstants.DIABETIC59), getConcept(DiabetesDiagnosisConstants.DIABETIC60),
			getConcept(DiabetesDiagnosisConstants.DIABETIC61), getConcept(DiabetesDiagnosisConstants.DIABETIC62),
			getConcept(DiabetesDiagnosisConstants.DIABETIC63), getConcept(DiabetesDiagnosisConstants.DIABETIC64),
			getConcept(DiabetesDiagnosisConstants.DIABETIC65), getConcept(DiabetesDiagnosisConstants.DIABETIC66),
			getConcept(DiabetesDiagnosisConstants.DIABETIC67), getConcept(DiabetesDiagnosisConstants.DIABETIC68),
			getConcept(DiabetesDiagnosisConstants.DIABETIC69), getConcept(DiabetesDiagnosisConstants.DIABETIC70),
			getConcept(DiabetesDiagnosisConstants.DIABETIC71), getConcept(DiabetesDiagnosisConstants.DIABETIC72),
			getConcept(DiabetesDiagnosisConstants.DIABETIC73), getConcept(DiabetesDiagnosisConstants.DIABETIC74),
			getConcept(DiabetesDiagnosisConstants.DIABETIC75), getConcept(DiabetesDiagnosisConstants.DIABETIC76),
			getConcept(DiabetesDiagnosisConstants.DIABETIC77), getConcept(DiabetesDiagnosisConstants.DIABETIC78),
			getConcept(DiabetesDiagnosisConstants.DIABETIC79), getConcept(DiabetesDiagnosisConstants.DIABETIC80),
			getConcept(DiabetesDiagnosisConstants.DIABETIC81), getConcept(DiabetesDiagnosisConstants.DIABETIC82),
			getConcept(DiabetesDiagnosisConstants.DIABETIC83), getConcept(DiabetesDiagnosisConstants.DIABETIC84),
			getConcept(DiabetesDiagnosisConstants.DIABETIC85), getConcept(DiabetesDiagnosisConstants.DIABETIC86),
			getConcept(DiabetesDiagnosisConstants.DIABETIC87), getConcept(DiabetesDiagnosisConstants.DIABETIC88),
			getConcept(DiabetesDiagnosisConstants.DIABETIC89), getConcept(DiabetesDiagnosisConstants.DIABETIC90),
			getConcept(DiabetesDiagnosisConstants.DIABETIC91), getConcept(DiabetesDiagnosisConstants.DIABETIC92),
			getConcept(DiabetesDiagnosisConstants.DIABETIC93), getConcept(DiabetesDiagnosisConstants.DIABETIC94),
			getConcept(DiabetesDiagnosisConstants.DIABETIC95), getConcept(DiabetesDiagnosisConstants.DIABETIC96),
			getConcept(DiabetesDiagnosisConstants.DIABETIC97), getConcept(DiabetesDiagnosisConstants.DIABETIC98),
			getConcept(DiabetesDiagnosisConstants.DIABETIC99), getConcept(DiabetesDiagnosisConstants.DIABETIC100),
			getConcept(DiabetesDiagnosisConstants.DIABETIC101), getConcept(DiabetesDiagnosisConstants.DIABETIC102),
			getConcept(DiabetesDiagnosisConstants.DIABETIC105), getConcept(DiabetesDiagnosisConstants.DIABETIC106),
			getConcept(DiabetesDiagnosisConstants.DIABETIC107), getConcept(DiabetesDiagnosisConstants.DIABETIC108),
			getConcept(DiabetesDiagnosisConstants.DIABETIC109), getConcept(DiabetesDiagnosisConstants.DIABETIC110),
			getConcept(DiabetesDiagnosisConstants.DIABETIC111), getConcept(DiabetesDiagnosisConstants.DIABETIC112),
			getConcept(DiabetesDiagnosisConstants.DIABETIC113), getConcept(DiabetesDiagnosisConstants.DIABETIC114),
			getConcept(DiabetesDiagnosisConstants.DIABETIC115), getConcept(DiabetesDiagnosisConstants.DIABETIC116),
			getConcept(DiabetesDiagnosisConstants.DIABETIC117), getConcept(DiabetesDiagnosisConstants.DIABETIC118),
			getConcept(DiabetesDiagnosisConstants.DIABETIC119), getConcept(DiabetesDiagnosisConstants.DIABETIC120),
			getConcept(DiabetesDiagnosisConstants.DIABETIC121), getConcept(DiabetesDiagnosisConstants.DIABETIC122),
			getConcept(DiabetesDiagnosisConstants.DIABETIC123), getConcept(DiabetesDiagnosisConstants.DIABETIC124),
			getConcept(DiabetesDiagnosisConstants.DIABETIC125), getConcept(DiabetesDiagnosisConstants.DIABETIC126),
			getConcept(DiabetesDiagnosisConstants.DIABETIC127), getConcept(DiabetesDiagnosisConstants.DIABETIC128),
			getConcept(DiabetesDiagnosisConstants.DIABETIC129), getConcept(DiabetesDiagnosisConstants.DIABETIC130),
			getConcept(DiabetesDiagnosisConstants.DIABETIC131), getConcept(DiabetesDiagnosisConstants.DIABETIC132),
			getConcept(DiabetesDiagnosisConstants.DIABETIC133), getConcept(DiabetesDiagnosisConstants.DIABETIC134),
			getConcept(DiabetesDiagnosisConstants.DIABETIC135), getConcept(DiabetesDiagnosisConstants.DIABETIC136),
			getConcept(DiabetesDiagnosisConstants.DIABETIC137), getConcept(DiabetesDiagnosisConstants.DIABETIC138),
			getConcept(DiabetesDiagnosisConstants.DIABETIC139), getConcept(DiabetesDiagnosisConstants.DIABETIC140),
			getConcept(DiabetesDiagnosisConstants.DIABETIC141), getConcept(DiabetesDiagnosisConstants.DIABETIC142),
			getConcept(DiabetesDiagnosisConstants.DIABETIC143), getConcept(DiabetesDiagnosisConstants.DIABETIC144),
			getConcept(DiabetesDiagnosisConstants.DIABETIC145), getConcept(DiabetesDiagnosisConstants.DIABETIC146),
			getConcept(DiabetesDiagnosisConstants.DIABETIC147), getConcept(DiabetesDiagnosisConstants.DIABETIC148),
			getConcept(DiabetesDiagnosisConstants.DIABETIC149), getConcept(DiabetesDiagnosisConstants.DIABETIC150),
			getConcept(DiabetesDiagnosisConstants.DIABETIC151), getConcept(DiabetesDiagnosisConstants.DIABETIC152),
			getConcept(DiabetesDiagnosisConstants.DIABETIC153), getConcept(DiabetesDiagnosisConstants.DIABETIC154),
			getConcept(DiabetesDiagnosisConstants.DIABETIC155), getConcept(DiabetesDiagnosisConstants.DIABETIC156),
			getConcept(DiabetesDiagnosisConstants.DIABETIC157), getConcept(DiabetesDiagnosisConstants.DIABETIC158),
			getConcept(DiabetesDiagnosisConstants.DIABETIC159), getConcept(DiabetesDiagnosisConstants.DIABETIC160),
			getConcept(DiabetesDiagnosisConstants.DIABETIC161), getConcept(DiabetesDiagnosisConstants.DIABETIC162),
			getConcept(DiabetesDiagnosisConstants.DIABETIC163), getConcept(DiabetesDiagnosisConstants.DIABETIC164),
			getConcept(DiabetesDiagnosisConstants.DIABETIC165), getConcept(DiabetesDiagnosisConstants.DIABETIC166),
			getConcept(DiabetesDiagnosisConstants.DIABETIC167), getConcept(DiabetesDiagnosisConstants.DIABETIC168),
			getConcept(DiabetesDiagnosisConstants.DIABETIC169), getConcept(DiabetesDiagnosisConstants.DIABETIC170),
			getConcept(DiabetesDiagnosisConstants.DIABETIC171), getConcept(DiabetesDiagnosisConstants.DIABETIC172),
			getConcept(DiabetesDiagnosisConstants.DIABETIC173), getConcept(DiabetesDiagnosisConstants.DIABETIC174),
			getConcept(DiabetesDiagnosisConstants.DIABETIC175), getConcept(DiabetesDiagnosisConstants.DIABETIC176),
			getConcept(DiabetesDiagnosisConstants.DIABETIC177), getConcept(DiabetesDiagnosisConstants.DIABETIC178),
			getConcept(DiabetesDiagnosisConstants.DIABETIC179), getConcept(DiabetesDiagnosisConstants.DIABETIC180),
			getConcept(DiabetesDiagnosisConstants.DIABETIC181), getConcept(DiabetesDiagnosisConstants.DIABETIC182),
			getConcept(DiabetesDiagnosisConstants.DIABETIC183), getConcept(DiabetesDiagnosisConstants.DIABETIC184),
			getConcept(DiabetesDiagnosisConstants.DIABETIC185), getConcept(DiabetesDiagnosisConstants.DIABETIC186),
			getConcept(DiabetesDiagnosisConstants.DIABETIC187), getConcept(DiabetesDiagnosisConstants.DIABETIC188),
			getConcept(DiabetesDiagnosisConstants.DIABETIC189), getConcept(DiabetesDiagnosisConstants.DIABETIC190),
			getConcept(DiabetesDiagnosisConstants.DIABETIC191), getConcept(DiabetesDiagnosisConstants.DIABETIC192),
			getConcept(DiabetesDiagnosisConstants.DIABETIC193), getConcept(DiabetesDiagnosisConstants.DIABETIC194),
			getConcept(DiabetesDiagnosisConstants.DIABETIC195), getConcept(DiabetesDiagnosisConstants.DIABETIC196),
			getConcept(DiabetesDiagnosisConstants.DIABETIC197), getConcept(DiabetesDiagnosisConstants.DIABETIC198),
			getConcept(DiabetesDiagnosisConstants.DIABETIC200), getConcept(DiabetesDiagnosisConstants.DIABETIC201),
			getConcept(DiabetesDiagnosisConstants.DIABETIC202), getConcept(DiabetesDiagnosisConstants.DIABETIC203),
			getConcept(DiabetesDiagnosisConstants.DIABETIC204), getConcept(DiabetesDiagnosisConstants.DIABETIC205),
			getConcept(DiabetesDiagnosisConstants.DIABETIC206), getConcept(DiabetesDiagnosisConstants.DIABETIC207),
			getConcept(DiabetesDiagnosisConstants.DIABETIC208), getConcept(DiabetesDiagnosisConstants.DIABETIC209),
			getConcept(DiabetesDiagnosisConstants.DIABETIC210), getConcept(DiabetesDiagnosisConstants.DIABETIC211),
			getConcept(DiabetesDiagnosisConstants.DIABETIC212), getConcept(DiabetesDiagnosisConstants.DIABETIC213),
			getConcept(DiabetesDiagnosisConstants.DIABETIC214)

		);
	}





	public static List<Integer> getBrucellosisList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.BRUCELOSIS1,
			DiagnosisConcepts._DiagnosisConcepts.BRUCELOSIS2, DiagnosisConcepts._DiagnosisConcepts.BRUCELOSIS3,
			DiagnosisConcepts._DiagnosisConcepts.BRUCELOSIS4, DiagnosisConcepts._DiagnosisConcepts.BRUCELOSIS5,
			DiagnosisConcepts._DiagnosisConcepts.BRUCELOSIS6, DiagnosisConcepts._DiagnosisConcepts.BRUCELOSIS7,
			DiagnosisConcepts._DiagnosisConcepts.BRUCELLOSIS);
	}





	public static List<Integer> getMuscularSkeletalConditionsList() {
		return Arrays.asList(MascularDiagnosisConstants.Muscular1, MascularDiagnosisConstants.Muscular2,
			MascularDiagnosisConstants.Muscular3, MascularDiagnosisConstants.Muscular4,
			MascularDiagnosisConstants.Muscular5, MascularDiagnosisConstants.Muscular6,
			MascularDiagnosisConstants.Muscular7, MascularDiagnosisConstants.Muscular8,
			MascularDiagnosisConstants.Muscular9, MascularDiagnosisConstants.Muscular10,
			MascularDiagnosisConstants.Muscular11, MascularDiagnosisConstants.Muscular12,
			MascularDiagnosisConstants.Muscular13, MascularDiagnosisConstants.Muscular14,
			MascularDiagnosisConstants.Muscular15, MascularDiagnosisConstants.Muscular16,
			MascularDiagnosisConstants.Muscular17, MascularDiagnosisConstants.Muscular18,
			MascularDiagnosisConstants.Muscular19, MascularDiagnosisConstants.Muscular20,
			MascularDiagnosisConstants.Muscular21, MascularDiagnosisConstants.Muscular22,
			MascularDiagnosisConstants.Muscular23, MascularDiagnosisConstants.Muscular24,
			MascularDiagnosisConstants.Muscular25, MascularDiagnosisConstants.Muscular26,
			MascularDiagnosisConstants.Muscular27, MascularDiagnosisConstants.Muscular28,
			MascularDiagnosisConstants.Muscular29, MascularDiagnosisConstants.Muscular30,
			MascularDiagnosisConstants.Muscular31, MascularDiagnosisConstants.Muscular32,
			MascularDiagnosisConstants.Muscular33, MascularDiagnosisConstants.Muscular34,
			MascularDiagnosisConstants.Muscular35, MascularDiagnosisConstants.Muscular36,
			MascularDiagnosisConstants.Muscular37, MascularDiagnosisConstants.Muscular38,
			MascularDiagnosisConstants.Muscular39, MascularDiagnosisConstants.Muscular40,
			MascularDiagnosisConstants.Muscular41, MascularDiagnosisConstants.Muscular42,
			MascularDiagnosisConstants.Muscular43, MascularDiagnosisConstants.Muscular44,
			MascularDiagnosisConstants.Muscular45, MascularDiagnosisConstants.Muscular46,
			MascularDiagnosisConstants.Muscular47, MascularDiagnosisConstants.Muscular48,
			MascularDiagnosisConstants.Muscular49, MascularDiagnosisConstants.Muscular50,
			MascularDiagnosisConstants.Muscular51, MascularDiagnosisConstants.Muscular52

		);
	}

	public static List<Integer> getFistulaBirthRelatedList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.FISTULA1, DiagnosisConcepts._DiagnosisConcepts.FISTULA2,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA3, DiagnosisConcepts._DiagnosisConcepts.FISTULA4,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA5, DiagnosisConcepts._DiagnosisConcepts.FISTULA6,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA7, DiagnosisConcepts._DiagnosisConcepts.FISTULA8,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA9, DiagnosisConcepts._DiagnosisConcepts.FISTULA10,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA11, DiagnosisConcepts._DiagnosisConcepts.FISTULA12,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA13, DiagnosisConcepts._DiagnosisConcepts.FISTULA14,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA15, DiagnosisConcepts._DiagnosisConcepts.FISTULA16,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA17, DiagnosisConcepts._DiagnosisConcepts.FISTULA18,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA19, DiagnosisConcepts._DiagnosisConcepts.FISTULA20,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA21, DiagnosisConcepts._DiagnosisConcepts.FISTULA22,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA23, DiagnosisConcepts._DiagnosisConcepts.FISTULA24,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA25, DiagnosisConcepts._DiagnosisConcepts.FISTULA26,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA27, DiagnosisConcepts._DiagnosisConcepts.FISTULA28,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA29, DiagnosisConcepts._DiagnosisConcepts.FISTULA30,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA31, DiagnosisConcepts._DiagnosisConcepts.FISTULA32,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA33, DiagnosisConcepts._DiagnosisConcepts.FISTULA34,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA35, DiagnosisConcepts._DiagnosisConcepts.FISTULA36,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA37, DiagnosisConcepts._DiagnosisConcepts.FISTULA38,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA39, DiagnosisConcepts._DiagnosisConcepts.FISTULA40,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA41, DiagnosisConcepts._DiagnosisConcepts.FISTULA42,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA43, DiagnosisConcepts._DiagnosisConcepts.FISTULA44,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA45, DiagnosisConcepts._DiagnosisConcepts.FISTULA46,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA47, DiagnosisConcepts._DiagnosisConcepts.FISTULA48,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA49, DiagnosisConcepts._DiagnosisConcepts.FISTULA50,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA51, DiagnosisConcepts._DiagnosisConcepts.FISTULA52,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA53, DiagnosisConcepts._DiagnosisConcepts.FISTULA54,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA55, DiagnosisConcepts._DiagnosisConcepts.FISTULA56,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA57, DiagnosisConcepts._DiagnosisConcepts.FISTULA58,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA59, DiagnosisConcepts._DiagnosisConcepts.FISTULA60,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA61, DiagnosisConcepts._DiagnosisConcepts.FISTULA62,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA63, DiagnosisConcepts._DiagnosisConcepts.FISTULA64,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA65, DiagnosisConcepts._DiagnosisConcepts.FISTULA66,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA67, DiagnosisConcepts._DiagnosisConcepts.FISTULA68,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA69, DiagnosisConcepts._DiagnosisConcepts.FISTULA70,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA71, DiagnosisConcepts._DiagnosisConcepts.FISTULA72,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA73, DiagnosisConcepts._DiagnosisConcepts.FISTULA74,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA75, DiagnosisConcepts._DiagnosisConcepts.FISTULA76,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA77, DiagnosisConcepts._DiagnosisConcepts.FISTULA78,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA79, DiagnosisConcepts._DiagnosisConcepts.FISTULA80,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA81, DiagnosisConcepts._DiagnosisConcepts.FISTULA82,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA83, DiagnosisConcepts._DiagnosisConcepts.FISTULA84,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA85, DiagnosisConcepts._DiagnosisConcepts.FISTULA86,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA87, DiagnosisConcepts._DiagnosisConcepts.FISTULA88,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA89, DiagnosisConcepts._DiagnosisConcepts.FISTULA90,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA91, DiagnosisConcepts._DiagnosisConcepts.FISTULA92,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA93, DiagnosisConcepts._DiagnosisConcepts.FISTULA94,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA95, DiagnosisConcepts._DiagnosisConcepts.FISTULA96,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA97, DiagnosisConcepts._DiagnosisConcepts.FISTULA98,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA99, DiagnosisConcepts._DiagnosisConcepts.FISTULA100,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA101, DiagnosisConcepts._DiagnosisConcepts.FISTULA102,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA103, DiagnosisConcepts._DiagnosisConcepts.FISTULA104,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA105, DiagnosisConcepts._DiagnosisConcepts.FISTULA106,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA107, DiagnosisConcepts._DiagnosisConcepts.FISTULA108,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA109, DiagnosisConcepts._DiagnosisConcepts.FISTULA110,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA111, DiagnosisConcepts._DiagnosisConcepts.FISTULA112,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA113, DiagnosisConcepts._DiagnosisConcepts.FISTULA114,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA115, DiagnosisConcepts._DiagnosisConcepts.FISTULA116,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA117, DiagnosisConcepts._DiagnosisConcepts.FISTULA118,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA119, DiagnosisConcepts._DiagnosisConcepts.FISTULA120,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA121, DiagnosisConcepts._DiagnosisConcepts.FISTULA122,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA123, DiagnosisConcepts._DiagnosisConcepts.FISTULA124,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA125, DiagnosisConcepts._DiagnosisConcepts.FISTULA126,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA127, DiagnosisConcepts._DiagnosisConcepts.FISTULA128,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA129, DiagnosisConcepts._DiagnosisConcepts.FISTULA130,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA131, DiagnosisConcepts._DiagnosisConcepts.FISTULA132,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA133, DiagnosisConcepts._DiagnosisConcepts.FISTULA134,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA135, DiagnosisConcepts._DiagnosisConcepts.FISTULA136,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA137, DiagnosisConcepts._DiagnosisConcepts.FISTULA138,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA139, DiagnosisConcepts._DiagnosisConcepts.FISTULA140,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA141, DiagnosisConcepts._DiagnosisConcepts.FISTULA142,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA143, DiagnosisConcepts._DiagnosisConcepts.FISTULA144,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA145, DiagnosisConcepts._DiagnosisConcepts.FISTULA146,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA147, DiagnosisConcepts._DiagnosisConcepts.FISTULA148,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA149, DiagnosisConcepts._DiagnosisConcepts.FISTULA150,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA151, DiagnosisConcepts._DiagnosisConcepts.FISTULA152,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA153, DiagnosisConcepts._DiagnosisConcepts.FISTULA154,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA155, DiagnosisConcepts._DiagnosisConcepts.FISTULA156,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA157, DiagnosisConcepts._DiagnosisConcepts.FISTULA158,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA159, DiagnosisConcepts._DiagnosisConcepts.FISTULA160,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA161, DiagnosisConcepts._DiagnosisConcepts.FISTULA162,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA163, DiagnosisConcepts._DiagnosisConcepts.FISTULA164,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA165, DiagnosisConcepts._DiagnosisConcepts.FISTULA166,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA167, DiagnosisConcepts._DiagnosisConcepts.FISTULA168,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA169, DiagnosisConcepts._DiagnosisConcepts.FISTULA170,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA171, DiagnosisConcepts._DiagnosisConcepts.FISTULA172,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA173, DiagnosisConcepts._DiagnosisConcepts.FISTULA174,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA175, DiagnosisConcepts._DiagnosisConcepts.FISTULA176,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA177, DiagnosisConcepts._DiagnosisConcepts.FISTULA178,
			DiagnosisConcepts._DiagnosisConcepts.FISTULA179, DiagnosisConcepts._DiagnosisConcepts.FISTULA180

		);
	}

	public static List<Integer> getNeoplamsList() {
		return Arrays.asList(OncologyDiagnosisConstants.CANCER_1, OncologyDiagnosisConstants.CANCER_2,
			OncologyDiagnosisConstants.CANCER_3, OncologyDiagnosisConstants.CANCER_4, OncologyDiagnosisConstants.CANCER_5,
			OncologyDiagnosisConstants.CANCER_6, OncologyDiagnosisConstants.CANCER_7, OncologyDiagnosisConstants.CANCER_8,
			OncologyDiagnosisConstants.CANCER_9, OncologyDiagnosisConstants.CANCER_10, OncologyDiagnosisConstants.CANCER_11,
			OncologyDiagnosisConstants.CANCER_12, OncologyDiagnosisConstants.CANCER_13,
			OncologyDiagnosisConstants.CANCER_14, OncologyDiagnosisConstants.CANCER_15,
			OncologyDiagnosisConstants.CANCER_16, OncologyDiagnosisConstants.CANCER_17,
			OncologyDiagnosisConstants.CANCER_18, OncologyDiagnosisConstants.CANCER_19,
			OncologyDiagnosisConstants.CANCER_20, OncologyDiagnosisConstants.CANCER_21,
			OncologyDiagnosisConstants.CANCER_22, OncologyDiagnosisConstants.CANCER_23,
			OncologyDiagnosisConstants.CANCER_24, OncologyDiagnosisConstants.CANCER_25,
			OncologyDiagnosisConstants.CANCER_26, OncologyDiagnosisConstants.CANCER_27,
			OncologyDiagnosisConstants.CANCER_28, OncologyDiagnosisConstants.CANCER_29,
			OncologyDiagnosisConstants.CANCER_30, OncologyDiagnosisConstants.CANCER_31,
			OncologyDiagnosisConstants.CANCER_32, OncologyDiagnosisConstants.CANCER_33,
			OncologyDiagnosisConstants.CANCER_34, OncologyDiagnosisConstants.CANCER_35,
			OncologyDiagnosisConstants.CANCER_36, OncologyDiagnosisConstants.CANCER_37,
			OncologyDiagnosisConstants.CANCER_38, OncologyDiagnosisConstants.CANCER_39,
			OncologyDiagnosisConstants.CANCER_40, OncologyDiagnosisConstants.CANCER_41,
			OncologyDiagnosisConstants.CANCER_42, OncologyDiagnosisConstants.CANCER_43,
			OncologyDiagnosisConstants.CANCER_44, OncologyDiagnosisConstants.CANCER_45,
			OncologyDiagnosisConstants.CANCER_46, OncologyDiagnosisConstants.CANCER_47,
			OncologyDiagnosisConstants.CANCER_48, OncologyDiagnosisConstants.CANCER_49,
			OncologyDiagnosisConstants.CANCER_50, OncologyDiagnosisConstants.CANCER_51,
			OncologyDiagnosisConstants.CANCER_52, OncologyDiagnosisConstants.CANCER_53,
			OncologyDiagnosisConstants.CANCER_54, OncologyDiagnosisConstants.CANCER_55,
			OncologyDiagnosisConstants.CANCER_56, OncologyDiagnosisConstants.CANCER_57,
			OncologyDiagnosisConstants.CANCER_58, OncologyDiagnosisConstants.CANCER_59,
			OncologyDiagnosisConstants.CANCER_60, OncologyDiagnosisConstants.CANCER_61,
			OncologyDiagnosisConstants.CANCER_62, OncologyDiagnosisConstants.CANCER_63,
			OncologyDiagnosisConstants.CANCER_64, OncologyDiagnosisConstants.CANCER_65,
			OncologyDiagnosisConstants.CANCER_66, OncologyDiagnosisConstants.CANCER_67,
			OncologyDiagnosisConstants.CANCER_68, OncologyDiagnosisConstants.CANCER_69,
			OncologyDiagnosisConstants.CANCER_70, OncologyDiagnosisConstants.CANCER_71,
			OncologyDiagnosisConstants.CANCER_72, OncologyDiagnosisConstants.CANCER_73,
			OncologyDiagnosisConstants.CANCER_74, OncologyDiagnosisConstants.CANCER_75,
			OncologyDiagnosisConstants.CANCER_76, OncologyDiagnosisConstants.CANCER_77,
			OncologyDiagnosisConstants.CANCER_78, OncologyDiagnosisConstants.CANCER_79,
			OncologyDiagnosisConstants.CANCER_80, OncologyDiagnosisConstants.CANCER_81,
			OncologyDiagnosisConstants.CANCER_82, OncologyDiagnosisConstants.CANCER_83,
			OncologyDiagnosisConstants.CANCER_84, OncologyDiagnosisConstants.CANCER_85,
			OncologyDiagnosisConstants.CANCER_86, OncologyDiagnosisConstants.CANCER_87,
			OncologyDiagnosisConstants.CANCER_88, OncologyDiagnosisConstants.CANCER_89,
			OncologyDiagnosisConstants.CANCER_90, OncologyDiagnosisConstants.CANCER_91,
			OncologyDiagnosisConstants.CANCER_92, OncologyDiagnosisConstants.CANCER_93,
			OncologyDiagnosisConstants.CANCER_94, OncologyDiagnosisConstants.CANCER_95,
			OncologyDiagnosisConstants.CANCER_96, OncologyDiagnosisConstants.CANCER_97,
			OncologyDiagnosisConstants.CANCER_98, OncologyDiagnosisConstants.CANCER_99,
			OncologyDiagnosisConstants.CANCER_100, OncologyDiagnosisConstants.CANCER_101,
			OncologyDiagnosisConstants.CANCER_102, OncologyDiagnosisConstants.CANCER_103,
			OncologyDiagnosisConstants.CANCER_104, OncologyDiagnosisConstants.CANCER_105,
			OncologyDiagnosisConstants.CANCER_106, OncologyDiagnosisConstants.CANCER_107,
			OncologyDiagnosisConstants.CANCER_108, OncologyDiagnosisConstants.CANCER_109,
			OncologyDiagnosisConstants.CANCER_110, OncologyDiagnosisConstants.CANCER_111,
			OncologyDiagnosisConstants.CANCER_112, OncologyDiagnosisConstants.CANCER_113,
			OncologyDiagnosisConstants.CANCER_114, OncologyDiagnosisConstants.CANCER_115,
			OncologyDiagnosisConstants.CANCER_116, OncologyDiagnosisConstants.CANCER_117,
			OncologyDiagnosisConstants.CANCER_118, OncologyDiagnosisConstants.CANCER_119,
			OncologyDiagnosisConstants.CANCER_120, OncologyDiagnosisConstants.CANCER_121,
			OncologyDiagnosisConstants.CANCER_122, OncologyDiagnosisConstants.CANCER_123,
			OncologyDiagnosisConstants.CANCER_124, OncologyDiagnosisConstants.CANCER_125,
			OncologyDiagnosisConstants.CANCER_126, OncologyDiagnosisConstants.CANCER_127,
			OncologyDiagnosisConstants.CANCER_128, OncologyDiagnosisConstants.CANCER_129,
			OncologyDiagnosisConstants.CANCER_130, OncologyDiagnosisConstants.CANCER_131,
			OncologyDiagnosisConstants.CANCER_132, OncologyDiagnosisConstants.CANCER_133,
			OncologyDiagnosisConstants.CANCER_134, OncologyDiagnosisConstants.CANCER_135,
			OncologyDiagnosisConstants.CANCER_136, OncologyDiagnosisConstants.CANCER_137,
			OncologyDiagnosisConstants.CANCER_138, OncologyDiagnosisConstants.CANCER_139,
			OncologyDiagnosisConstants.CANCER_140, OncologyDiagnosisConstants.CANCER_141,
			OncologyDiagnosisConstants.CANCER_142, OncologyDiagnosisConstants.CANCER_143,
			OncologyDiagnosisConstants.CANCER_144, OncologyDiagnosisConstants.CANCER_145,
			OncologyDiagnosisConstants.CANCER_146, OncologyDiagnosisConstants.CANCER_147,
			OncologyDiagnosisConstants.CANCER_148, OncologyDiagnosisConstants.CANCER_149,
			OncologyDiagnosisConstants.CANCER_150, OncologyDiagnosisConstants.CANCER_151,
			OncologyDiagnosisConstants.CANCER_152, OncologyDiagnosisConstants.CANCER_153,
			OncologyDiagnosisConstants.CANCER_154, OncologyDiagnosisConstants.CANCER_155,
			OncologyDiagnosisConstants.CANCER_156, OncologyDiagnosisConstants.CANCER_157,
			OncologyDiagnosisConstants.CANCER_158, OncologyDiagnosisConstants.CANCER_159,
			OncologyDiagnosisConstants.CANCER_160, OncologyDiagnosisConstants.CANCER_161,
			OncologyDiagnosisConstants.CANCER_162, OncologyDiagnosisConstants.CANCER_163,
			OncologyDiagnosisConstants.CANCER_164, OncologyDiagnosisConstants.CANCER_165,
			OncologyDiagnosisConstants.CANCER_166, OncologyDiagnosisConstants.CANCER_167,
			OncologyDiagnosisConstants.CANCER_168, OncologyDiagnosisConstants.CANCER_169,
			OncologyDiagnosisConstants.CANCER_170, OncologyDiagnosisConstants.CANCER_171,
			OncologyDiagnosisConstants.CANCER_172, OncologyDiagnosisConstants.CANCER_173,
			OncologyDiagnosisConstants.CANCER_174, OncologyDiagnosisConstants.CANCER_175,
			OncologyDiagnosisConstants.CANCER_176, OncologyDiagnosisConstants.CANCER_177,
			OncologyDiagnosisConstants.CANCER_178, OncologyDiagnosisConstants.CANCER_179,
			OncologyDiagnosisConstants.CANCER_180, OncologyDiagnosisConstants.CANCER_181,
			OncologyDiagnosisConstants.CANCER_182, OncologyDiagnosisConstants.CANCER_183,
			OncologyDiagnosisConstants.CANCER_184, OncologyDiagnosisConstants.CANCER_185,
			OncologyDiagnosisConstants.CANCER_186, OncologyDiagnosisConstants.CANCER_187,
			OncologyDiagnosisConstants.CANCER_188, OncologyDiagnosisConstants.CANCER_189,
			OncologyDiagnosisConstants.CANCER_190, OncologyDiagnosisConstants.CANCER_191,
			OncologyDiagnosisConstants.CANCER_192, OncologyDiagnosisConstants.CANCER_193,
			OncologyDiagnosisConstants.CANCER_194, OncologyDiagnosisConstants.CANCER_195,
			OncologyDiagnosisConstants.CANCER_196, OncologyDiagnosisConstants.CANCER_197,
			OncologyDiagnosisConstants.CANCER_198, OncologyDiagnosisConstants.CANCER_199,
			OncologyDiagnosisConstants.CANCER_200, OncologyDiagnosisConstants.CANCER_201,
			OncologyDiagnosisConstants.CANCER_202, OncologyDiagnosisConstants.CANCER_203,
			OncologyDiagnosisConstants.CANCER_204, OncologyDiagnosisConstants.CANCER_205,
			OncologyDiagnosisConstants.CANCER_206, OncologyDiagnosisConstants.CANCER_207,
			OncologyDiagnosisConstants.CANCER_208, OncologyDiagnosisConstants.CANCER_209,
			OncologyDiagnosisConstants.CANCER_210, OncologyDiagnosisConstants.CANCER_211,
			OncologyDiagnosisConstants.CANCER_212, OncologyDiagnosisConstants.CANCER_213,
			OncologyDiagnosisConstants.CANCER_214, OncologyDiagnosisConstants.CANCER_215,
			OncologyDiagnosisConstants.CANCER_216, OncologyDiagnosisConstants.CANCER_217,
			OncologyDiagnosisConstants.CANCER_218, OncologyDiagnosisConstants.CANCER_219,
			OncologyDiagnosisConstants.CANCER_220, OncologyDiagnosisConstants.CANCER_221,
			OncologyDiagnosisConstants.CANCER_222, OncologyDiagnosisConstants.CANCER_223,
			OncologyDiagnosisConstants.CANCER_224, OncologyDiagnosisConstants.CANCER_225,
			OncologyDiagnosisConstants.CANCER_226, OncologyDiagnosisConstants.CANCER_227,
			OncologyDiagnosisConstants.CANCER_228, OncologyDiagnosisConstants.CANCER_229,
			OncologyDiagnosisConstants.CANCER_230, OncologyDiagnosisConstants.CANCER_231,
			OncologyDiagnosisConstants.CANCER_232, OncologyDiagnosisConstants.CANCER_233,
			OncologyDiagnosisConstants.CANCER_234, OncologyDiagnosisConstants.CANCER_235,
			OncologyDiagnosisConstants.CANCER_236, OncologyDiagnosisConstants.CANCER_237,
			OncologyDiagnosisConstants.CANCER_238, OncologyDiagnosisConstants.CANCER_239,
			OncologyDiagnosisConstants.CANCER_240, OncologyDiagnosisConstants.CANCER_241,
			OncologyDiagnosisConstants.CANCER_242, OncologyDiagnosisConstants.CANCER_243,
			OncologyDiagnosisConstants.CANCER_244, OncologyDiagnosisConstants.CANCER_245,
			OncologyDiagnosisConstants.CANCER_246, OncologyDiagnosisConstants.CANCER_247,
			OncologyDiagnosisConstants.CANCER_248, OncologyDiagnosisConstants.CANCER_249,
			OncologyDiagnosisConstants.CANCER_250, OncologyDiagnosisConstants.CANCER_251,
			OncologyDiagnosisConstants.CANCER_252, OncologyDiagnosisConstants.CANCER_253,
			OncologyDiagnosisConstants.CANCER_254, OncologyDiagnosisConstants.CANCER_255,
			OncologyDiagnosisConstants.CANCER_256, OncologyDiagnosisConstants.CANCER_257,
			OncologyDiagnosisConstants.CANCER_258, OncologyDiagnosisConstants.CANCER_259,
			OncologyDiagnosisConstants.CANCER_260, OncologyDiagnosisConstants.CANCER_261,
			OncologyDiagnosisConstants.CANCER_262, OncologyDiagnosisConstants.CANCER_263,
			OncologyDiagnosisConstants.CANCER_264, OncologyDiagnosisConstants.CANCER_265,
			OncologyDiagnosisConstants.CANCER_266, OncologyDiagnosisConstants.CANCER_267,
			OncologyDiagnosisConstants.CANCER_268, OncologyDiagnosisConstants.CANCER_269,
			OncologyDiagnosisConstants.CANCER_270, OncologyDiagnosisConstants.CANCER_271,
			OncologyDiagnosisConstants.CANCER_272, OncologyDiagnosisConstants.CANCER_273,
			OncologyDiagnosisConstants.CANCER_274, OncologyDiagnosisConstants.CANCER_275,
			OncologyDiagnosisConstants.CANCER_276, OncologyDiagnosisConstants.CANCER_277,
			OncologyDiagnosisConstants.CANCER_278, OncologyDiagnosisConstants.CANCER_279,
			OncologyDiagnosisConstants.CANCER_280, OncologyDiagnosisConstants.CANCER_281,
			OncologyDiagnosisConstants.CANCER_282, OncologyDiagnosisConstants.CANCER_283,
			OncologyDiagnosisConstants.CANCER_284, OncologyDiagnosisConstants.CANCER_285,
			OncologyDiagnosisConstants.CANCER_286, OncologyDiagnosisConstants.CANCER_287,
			OncologyDiagnosisConstants.CANCER_288, OncologyDiagnosisConstants.CANCER_289,
			OncologyDiagnosisConstants.CANCER_290, OncologyDiagnosisConstants.CANCER_291,
			OncologyDiagnosisConstants.CANCER_292, OncologyDiagnosisConstants.CANCER_293,
			OncologyDiagnosisConstants.CANCER_294, OncologyDiagnosisConstants.CANCER_295,
			OncologyDiagnosisConstants.CANCER_296, OncologyDiagnosisConstants.CANCER_297,
			OncologyDiagnosisConstants.CANCER_298, OncologyDiagnosisConstants.CANCER_299,
			OncologyDiagnosisConstants.CANCER_300, OncologyDiagnosisConstants.CANCER_301,
			OncologyDiagnosisConstants.CANCER_302, OncologyDiagnosisConstants.CANCER_303,
			OncologyDiagnosisConstants.CANCER_304, OncologyDiagnosisConstants.CANCER_305,
			OncologyDiagnosisConstants.CANCER_306, OncologyDiagnosisConstants.CANCER_307,
			OncologyDiagnosisConstants.CANCER_308, OncologyDiagnosisConstants.CANCER_309,
			OncologyDiagnosisConstants.CANCER_310, OncologyDiagnosisConstants.CANCER_311,
			OncologyDiagnosisConstants.CANCER_312, OncologyDiagnosisConstants.CANCER_313,
			OncologyDiagnosisConstants.CANCER_314, OncologyDiagnosisConstants.CANCER_315,
			OncologyDiagnosisConstants.CANCER_316, OncologyDiagnosisConstants.CANCER_317,
			OncologyDiagnosisConstants.CANCER_318, OncologyDiagnosisConstants.CANCER_319,
			OncologyDiagnosisConstants.CANCER_320, OncologyDiagnosisConstants.CANCER_321,
			OncologyDiagnosisConstants.CANCER_322, OncologyDiagnosisConstants.CANCER_323,
			OncologyDiagnosisConstants.CANCER_324, OncologyDiagnosisConstants.CANCER_325,
			OncologyDiagnosisConstants.CANCER_326, OncologyDiagnosisConstants.CANCER_327,
			OncologyDiagnosisConstants.CANCER_328, OncologyDiagnosisConstants.CANCER_329,
			OncologyDiagnosisConstants.CANCER_330, OncologyDiagnosisConstants.CANCER_331,
			OncologyDiagnosisConstants.CANCER_332, OncologyDiagnosisConstants.CANCER_333,
			OncologyDiagnosisConstants.CANCER_334, OncologyDiagnosisConstants.CANCER_335,
			OncologyDiagnosisConstants.CANCER_336, OncologyDiagnosisConstants.CANCER_337,
			OncologyDiagnosisConstants.CANCER_338, OncologyDiagnosisConstants.CANCER_339,
			OncologyDiagnosisConstants.CANCER_340, OncologyDiagnosisConstants.CANCER_341,
			OncologyDiagnosisConstants.CANCER_342, OncologyDiagnosisConstants.CANCER_343,
			OncologyDiagnosisConstants.CANCER_344, OncologyDiagnosisConstants.CANCER_345,
			OncologyDiagnosisConstants.CANCER_346, OncologyDiagnosisConstants.CANCER_347,
			OncologyDiagnosisConstants.CANCER_348, OncologyDiagnosisConstants.CANCER_349,
			OncologyDiagnosisConstants.CANCER_350, OncologyDiagnosisConstants.CANCER_351,
			OncologyDiagnosisConstants.CANCER_352, OncologyDiagnosisConstants.CANCER_353,
			OncologyDiagnosisConstants.CANCER_354, OncologyDiagnosisConstants.CANCER_355,
			OncologyDiagnosisConstants.CANCER_356, OncologyDiagnosisConstants.CANCER_357,
			OncologyDiagnosisConstants.CANCER_358, OncologyDiagnosisConstants.CANCER_359,
			OncologyDiagnosisConstants.CANCER_360, OncologyDiagnosisConstants.CANCER_361,
			OncologyDiagnosisConstants.CANCER_362, OncologyDiagnosisConstants.CANCER_363,
			OncologyDiagnosisConstants.CANCER_364, OncologyDiagnosisConstants.CANCER_365,
			OncologyDiagnosisConstants.CANCER_366, OncologyDiagnosisConstants.CANCER_367,
			OncologyDiagnosisConstants.CANCER_368, OncologyDiagnosisConstants.CANCER_369,
			OncologyDiagnosisConstants.CANCER_370, OncologyDiagnosisConstants.CANCER_371,
			OncologyDiagnosisConstants.CANCER_372, OncologyDiagnosisConstants.CANCER_373,
			OncologyDiagnosisConstants.CANCER_374, OncologyDiagnosisConstants.CANCER_375,
			OncologyDiagnosisConstants.CANCER_376, OncologyDiagnosisConstants.CANCER_377,
			OncologyDiagnosisConstants.CANCER_378, OncologyDiagnosisConstants.CANCER_379,
			OncologyDiagnosisConstants.CANCER_380, OncologyDiagnosisConstants.CANCER_381,
			OncologyDiagnosisConstants.CANCER_382, OncologyDiagnosisConstants.CANCER_383,
			OncologyDiagnosisConstants.CANCER_384, OncologyDiagnosisConstants.CANCER_385,
			OncologyDiagnosisConstants.CANCER_386, OncologyDiagnosisConstants.CANCER_387,
			OncologyDiagnosisConstants.CANCER_388, OncologyDiagnosisConstants.CANCER_389,
			OncologyDiagnosisConstants.CANCER_390, OncologyDiagnosisConstants.CANCER_391,
			OncologyDiagnosisConstants.CANCER_392, OncologyDiagnosisConstants.CANCER_393,
			OncologyDiagnosisConstants.CANCER_394, OncologyDiagnosisConstants.CANCER_395,
			OncologyDiagnosisConstants.CANCER_396, OncologyDiagnosisConstants.CANCER_397,
			OncologyDiagnosisConstants.CANCER_398, OncologyDiagnosisConstants.CANCER_399,
			OncologyDiagnosisConstants.CANCER_400, OncologyDiagnosisConstants.CANCER_401,
			OncologyDiagnosisConstants.CANCER_402, OncologyDiagnosisConstants.CANCER_403,
			OncologyDiagnosisConstants.CANCER_404, OncologyDiagnosisConstants.CANCER_405,
			OncologyDiagnosisConstants.CANCER_406, OncologyDiagnosisConstants.CANCER_407,
			OncologyDiagnosisConstants.CANCER_408, OncologyDiagnosisConstants.CANCER_409,
			OncologyDiagnosisConstants.CANCER_410, OncologyDiagnosisConstants.CANCER_411,
			OncologyDiagnosisConstants.CANCER_412, OncologyDiagnosisConstants.CANCER_413,
			OncologyDiagnosisConstants.CANCER_414, OncologyDiagnosisConstants.CANCER_415,
			OncologyDiagnosisConstants.CANCER_416, OncologyDiagnosisConstants.CANCER_417,
			OncologyDiagnosisConstants.CANCER_418, OncologyDiagnosisConstants.CANCER_419,
			OncologyDiagnosisConstants.CANCER_420, OncologyDiagnosisConstants.CANCER_421,
			OncologyDiagnosisConstants.CANCER_422, OncologyDiagnosisConstants.CANCER_423,
			OncologyDiagnosisConstants.CANCER_424, OncologyDiagnosisConstants.CANCER_425,
			OncologyDiagnosisConstants.CANCER_426, OncologyDiagnosisConstants.CANCER_427,
			OncologyDiagnosisConstants.CANCER_428, OncologyDiagnosisConstants.CANCER_429,
			OncologyDiagnosisConstants.CANCER_430, OncologyDiagnosisConstants.CANCER_431,
			OncologyDiagnosisConstants.CANCER_432, OncologyDiagnosisConstants.CANCER_433,
			OncologyDiagnosisConstants.CANCER_434, OncologyDiagnosisConstants.CANCER_435,
			OncologyDiagnosisConstants.CANCER_436, OncologyDiagnosisConstants.CANCER_437,
			OncologyDiagnosisConstants.CANCER_438, OncologyDiagnosisConstants.CANCER_439,
			OncologyDiagnosisConstants.CANCER_440, OncologyDiagnosisConstants.CANCER_441,
			OncologyDiagnosisConstants.CANCER_442, OncologyDiagnosisConstants.CANCER_443,
			OncologyDiagnosisConstants.CANCER_444, OncologyDiagnosisConstants.CANCER_445,
			OncologyDiagnosisConstants.CANCER_446, OncologyDiagnosisConstants.CANCER_447,
			OncologyDiagnosisConstants.CANCER_448, OncologyDiagnosisConstants.CANCER_449,
			OncologyDiagnosisConstants.CANCER_450, OncologyDiagnosisConstants.CANCER_451,
			OncologyDiagnosisConstants.CANCER_452, OncologyDiagnosisConstants.CANCER_453,
			OncologyDiagnosisConstants.CANCER_454, OncologyDiagnosisConstants.CANCER_455,
			OncologyDiagnosisConstants.CANCER_456, OncologyDiagnosisConstants.CANCER_457,
			OncologyDiagnosisConstants.CANCER_458, OncologyDiagnosisConstants.CANCER_459,
			OncologyDiagnosisConstants.CANCER_460, OncologyDiagnosisConstants.CANCER_461,
			OncologyDiagnosisConstants.CANCER_462, OncologyDiagnosisConstants.CANCER_463,
			OncologyDiagnosisConstants.CANCER_464, OncologyDiagnosisConstants.CANCER_465,
			OncologyDiagnosisConstants.CANCER_466, OncologyDiagnosisConstants.CANCER_467,
			OncologyDiagnosisConstants.CANCER_468, OncologyDiagnosisConstants.CANCER_469,
			OncologyDiagnosisConstants.CANCER_470, OncologyDiagnosisConstants.CANCER_471,
			OncologyDiagnosisConstants.CANCER_472, OncologyDiagnosisConstants.CANCER_473,
			OncologyDiagnosisConstants.CANCER_474, OncologyDiagnosisConstants.CANCER_475,
			OncologyDiagnosisConstants.CANCER_476, OncologyDiagnosisConstants.CANCER_477,
			OncologyDiagnosisConstants.CANCER_478, OncologyDiagnosisConstants.CANCER_479,
			OncologyDiagnosisConstants.CANCER_480, OncologyDiagnosisConstants.CANCER_481,
			OncologyDiagnosisConstants.CANCER_482, OncologyDiagnosisConstants.CANCER_483,
			OncologyDiagnosisConstants.CANCER_484, OncologyDiagnosisConstants.CANCER_485,
			OncologyDiagnosisConstants.CANCER_486, OncologyDiagnosisConstants.CANCER_487,
			OncologyDiagnosisConstants.CANCER_488, OncologyDiagnosisConstants.CANCER_489,
			OncologyDiagnosisConstants.CANCER_490, OncologyDiagnosisConstants.CANCER_491,
			OncologyDiagnosisConstants.CANCER_492, OncologyDiagnosisConstants.CANCER_493,
			OncologyDiagnosisConstants.CANCER_494, OncologyDiagnosisConstants.CANCER_495,
			OncologyDiagnosisConstants.CANCER_496, OncologyDiagnosisConstants.CANCER_497,
			OncologyDiagnosisConstants.CANCER_498, OncologyDiagnosisConstants.CANCER_499,
			OncologyDiagnosisConstants.CANCER_500, OncologyDiagnosisConstants.CANCER_501,
			OncologyDiagnosisConstants.CANCER_502, OncologyDiagnosisConstants.CANCER_503,
			OncologyDiagnosisConstants.CANCER_504, OncologyDiagnosisConstants.CANCER_505,
			OncologyDiagnosisConstants.CANCER_506, OncologyDiagnosisConstants.CANCER_507,
			OncologyDiagnosisConstants.CANCER_508, OncologyDiagnosisConstants.CANCER_509,
			OncologyDiagnosisConstants.CANCER_510, OncologyDiagnosisConstants.CANCER_511,
			OncologyDiagnosisConstants.CANCER_512, OncologyDiagnosisConstants.CANCER_513,
			OncologyDiagnosisConstants.CANCER_514, OncologyDiagnosisConstants.CANCER_515,
			OncologyDiagnosisConstants.CANCER_516, OncologyDiagnosisConstants.CANCER_517,
			OncologyDiagnosisConstants.CANCER_518, OncologyDiagnosisConstants.CANCER_519,
			OncologyDiagnosisConstants.CANCER_520, OncologyDiagnosisConstants.CANCER_521,
			OncologyDiagnosisConstants.CANCER_522, OncologyDiagnosisConstants.CANCER_523,
			OncologyDiagnosisConstants.CANCER_524, OncologyDiagnosisConstants.CANCER_525,
			OncologyDiagnosisConstants.CANCER_526, OncologyDiagnosisConstants.CANCER_527,
			OncologyDiagnosisConstants.CANCER_528, OncologyDiagnosisConstants.CANCER_529,
			OncologyDiagnosisConstants.CANCER_530, OncologyDiagnosisConstants.CANCER_531,
			OncologyDiagnosisConstants.CANCER_532, OncologyDiagnosisConstants.CANCER_533,
			OncologyDiagnosisConstants.CANCER_534, OncologyDiagnosisConstants.CANCER_535,
			OncologyDiagnosisConstants.CANCER_536, OncologyDiagnosisConstants.CANCER_537,
			OncologyDiagnosisConstants.CANCER_538, OncologyDiagnosisConstants.CANCER_539,
			OncologyDiagnosisConstants.CANCER_540, OncologyDiagnosisConstants.CANCER_541,
			OncologyDiagnosisConstants.CANCER_542, OncologyDiagnosisConstants.CANCER_543,
			OncologyDiagnosisConstants.CANCER_544, OncologyDiagnosisConstants.CANCER_545,
			OncologyDiagnosisConstants.CANCER_546, OncologyDiagnosisConstants.CANCER_547,
			OncologyDiagnosisConstants.CANCER_548, OncologyDiagnosisConstants.CANCER_549,
			OncologyDiagnosisConstants.CANCER_550, OncologyDiagnosisConstants.CANCER_551,
			OncologyDiagnosisConstants.CANCER_552, OncologyDiagnosisConstants.CANCER_553,
			OncologyDiagnosisConstants.CANCER_554, OncologyDiagnosisConstants.CANCER_555,
			OncologyDiagnosisConstants.CANCER_556, OncologyDiagnosisConstants.CANCER_557,
			OncologyDiagnosisConstants.CANCER_558, OncologyDiagnosisConstants.CANCER_559,
			OncologyDiagnosisConstants.CANCER_560, OncologyDiagnosisConstants.CANCER_561,
			OncologyDiagnosisConstants.CANCER_562, OncologyDiagnosisConstants.CANCER_563,
			OncologyDiagnosisConstants.CANCER_564, OncologyDiagnosisConstants.CANCER_565,
			OncologyDiagnosisConstants.CANCER_566, OncologyDiagnosisConstants.CANCER_567,
			OncologyDiagnosisConstants.CANCER_568, OncologyDiagnosisConstants.CANCER_569,
			OncologyDiagnosisConstants.CANCER_570, OncologyDiagnosisConstants.CANCER_571,
			OncologyDiagnosisConstants.CANCER_572, OncologyDiagnosisConstants.CANCER_573,
			OncologyDiagnosisConstants.CANCER_574, OncologyDiagnosisConstants.CANCER_575,
			OncologyDiagnosisConstants.CANCER_576, OncologyDiagnosisConstants.CANCER_577,
			OncologyDiagnosisConstants.CANCER_578, OncologyDiagnosisConstants.CANCER_579,
			OncologyDiagnosisConstants.CANCER_580, OncologyDiagnosisConstants.CANCER_581,
			OncologyDiagnosisConstants.CANCER_582, OncologyDiagnosisConstants.CANCER_583,
			OncologyDiagnosisConstants.CANCER_584, OncologyDiagnosisConstants.CANCER_585,
			OncologyDiagnosisConstants.CANCER_586, OncologyDiagnosisConstants.CANCER_587,
			OncologyDiagnosisConstants.CANCER_588, OncologyDiagnosisConstants.CANCER_589,
			OncologyDiagnosisConstants.CANCER_590, OncologyDiagnosisConstants.CANCER_591,
			OncologyDiagnosisConstants.CANCER_592, OncologyDiagnosisConstants.CANCER_593,
			OncologyDiagnosisConstants.CANCER_594, OncologyDiagnosisConstants.CANCER_595,
			OncologyDiagnosisConstants.CANCER_596, OncologyDiagnosisConstants.CANCER_597,
			OncologyDiagnosisConstants.CANCER_598, OncologyDiagnosisConstants.CANCER_599,
			OncologyDiagnosisConstants.CANCER_600, OncologyDiagnosisConstants.CANCER_601,
			OncologyDiagnosisConstants.CANCER_602, OncologyDiagnosisConstants.CANCER_603,
			OncologyDiagnosisConstants.CANCER_604, OncologyDiagnosisConstants.CANCER_605,
			OncologyDiagnosisConstants.CANCER_606, OncologyDiagnosisConstants.CANCER_607,
			OncologyDiagnosisConstants.CANCER_608, OncologyDiagnosisConstants.CANCER_609,
			OncologyDiagnosisConstants.CANCER_610, OncologyDiagnosisConstants.CANCER_611,
			OncologyDiagnosisConstants.CANCER_612, OncologyDiagnosisConstants.CANCER_613,
			OncologyDiagnosisConstants.CANCER_614, OncologyDiagnosisConstants.CANCER_615,
			OncologyDiagnosisConstants.CANCER_616, OncologyDiagnosisConstants.CANCER_617,
			OncologyDiagnosisConstants.CANCER_618, OncologyDiagnosisConstants.CANCER_619,
			OncologyDiagnosisConstants.CANCER_620, OncologyDiagnosisConstants.CANCER_621,
			OncologyDiagnosisConstants.CANCER_622, OncologyDiagnosisConstants.CANCER_623,
			OncologyDiagnosisConstants.CANCER_624, OncologyDiagnosisConstants.CANCER_625,
			OncologyDiagnosisConstants.CANCER_626, OncologyDiagnosisConstants.CANCER_627,
			OncologyDiagnosisConstants.CANCER_628, OncologyDiagnosisConstants.CANCER_629,
			OncologyDiagnosisConstants.CANCER_630, OncologyDiagnosisConstants.CANCER_631,
			OncologyDiagnosisConstants.CANCER_632, OncologyDiagnosisConstants.CANCER_633,
			OncologyDiagnosisConstants.CANCER_634, OncologyDiagnosisConstants.CANCER_635,
			OncologyDiagnosisConstants.CANCER_636, OncologyDiagnosisConstants.CANCER_637,
			OncologyDiagnosisConstants.CANCER_638, OncologyDiagnosisConstants.CANCER_639,
			OncologyDiagnosisConstants.CANCER_640, OncologyDiagnosisConstants.CANCER_641,
			OncologyDiagnosisConstants.CANCER_642, OncologyDiagnosisConstants.CANCER_643,
			OncologyDiagnosisConstants.CANCER_644, OncologyDiagnosisConstants.CANCER_645,
			OncologyDiagnosisConstants.CANCER_646, OncologyDiagnosisConstants.CANCER_647,
			OncologyDiagnosisConstants.CANCER_648, OncologyDiagnosisConstants.CANCER_649,
			OncologyDiagnosisConstants.CANCER_650, OncologyDiagnosisConstants.CANCER_651,
			OncologyDiagnosisConstants.CANCER_652, OncologyDiagnosisConstants.CANCER_653,
			OncologyDiagnosisConstants.CANCER_654, OncologyDiagnosisConstants.CANCER_655,
			OncologyDiagnosisConstants.CANCER_656, OncologyDiagnosisConstants.CANCER_657,
			OncologyDiagnosisConstants.CANCER_658, OncologyDiagnosisConstants.CANCER_659,
			OncologyDiagnosisConstants.CANCER_660, OncologyDiagnosisConstants.CANCER_661,
			OncologyDiagnosisConstants.CANCER_662, OncologyDiagnosisConstants.CANCER_663,
			OncologyDiagnosisConstants.CANCER_664, OncologyDiagnosisConstants.CANCER_665,
			OncologyDiagnosisConstants.CANCER_666, OncologyDiagnosisConstants.CANCER_667,
			OncologyDiagnosisConstants.CANCER_668, OncologyDiagnosisConstants.CANCER_669,
			OncologyDiagnosisConstants.CANCER_670, OncologyDiagnosisConstants.CANCER_671,
			OncologyDiagnosisConstants.CANCER_672, OncologyDiagnosisConstants.CANCER_673,
			OncologyDiagnosisConstants.CANCER_674, OncologyDiagnosisConstants.CANCER_675,
			OncologyDiagnosisConstants.CANCER_676, OncologyDiagnosisConstants.CANCER_677,
			OncologyDiagnosisConstants.CANCER_678, OncologyDiagnosisConstants.CANCER_679,
			OncologyDiagnosisConstants.CANCER_680, OncologyDiagnosisConstants.CANCER_681,
			OncologyDiagnosisConstants.CANCER_682, OncologyDiagnosisConstants.CANCER_683,
			OncologyDiagnosisConstants.CANCER_684, OncologyDiagnosisConstants.CANCER_685,
			OncologyDiagnosisConstants.CANCER_686, OncologyDiagnosisConstants.CANCER_687,
			OncologyDiagnosisConstants.CANCER_688, OncologyDiagnosisConstants.CANCER_689,
			OncologyDiagnosisConstants.CANCER_690, OncologyDiagnosisConstants.CANCER_691,
			OncologyDiagnosisConstants.CANCER_692, OncologyDiagnosisConstants.CANCER_693,
			OncologyDiagnosisConstants.CANCER_694, OncologyDiagnosisConstants.CANCER_695,
			OncologyDiagnosisConstants.CANCER_696, OncologyDiagnosisConstants.CANCER_697,
			OncologyDiagnosisConstants.CANCER_698, OncologyDiagnosisConstants.CANCER_699,
			OncologyDiagnosisConstants.CANCER_700, OncologyDiagnosisConstants.CANCER_701,
			OncologyDiagnosisConstants.CANCER_702, OncologyDiagnosisConstants.CANCER_703,
			OncologyDiagnosisConstants.CANCER_704, OncologyDiagnosisConstants.CANCER_705,
			OncologyDiagnosisConstants.CANCER_706, OncologyDiagnosisConstants.CANCER_707,
			OncologyDiagnosisConstants.CANCER_708, OncologyDiagnosisConstants.CANCER_709,
			OncologyDiagnosisConstants.CANCER_710, OncologyDiagnosisConstants.CANCER_711,
			OncologyDiagnosisConstants.CANCER_712, OncologyDiagnosisConstants.CANCER_713,
			OncologyDiagnosisConstants.CANCER_714, OncologyDiagnosisConstants.CANCER_715,
			OncologyDiagnosisConstants.CANCER_716, OncologyDiagnosisConstants.CANCER_717,
			OncologyDiagnosisConstants.CANCER_718, OncologyDiagnosisConstants.CANCER_719,
			OncologyDiagnosisConstants.CANCER_720, OncologyDiagnosisConstants.CANCER_721,
			OncologyDiagnosisConstants.CANCER_722, OncologyDiagnosisConstants.CANCER_723,
			OncologyDiagnosisConstants.CANCER_724, OncologyDiagnosisConstants.CANCER_725,
			OncologyDiagnosisConstants.CANCER_726, OncologyDiagnosisConstants.CANCER_727,
			OncologyDiagnosisConstants.CANCER_728, OncologyDiagnosisConstants.CANCER_729,
			OncologyDiagnosisConstants.CANCER_730, OncologyDiagnosisConstants.CANCER_731,
			OncologyDiagnosisConstants.CANCER_732, OncologyDiagnosisConstants.CANCER_733,
			OncologyDiagnosisConstants.CANCER_734, OncologyDiagnosisConstants.CANCER_735,
			OncologyDiagnosisConstants.CANCER_736, OncologyDiagnosisConstants.CANCER_737,
			OncologyDiagnosisConstants.CANCER_738, OncologyDiagnosisConstants.CANCER_739,
			OncologyDiagnosisConstants.CANCER_740, OncologyDiagnosisConstants.CANCER_741,
			OncologyDiagnosisConstants.CANCER_742, OncologyDiagnosisConstants.CANCER_743,
			OncologyDiagnosisConstants.CANCER_744, OncologyDiagnosisConstants.CANCER_745,
			OncologyDiagnosisConstants.CANCER_746, OncologyDiagnosisConstants.CANCER_747,
			OncologyDiagnosisConstants.CANCER_748, OncologyDiagnosisConstants.CANCER_749,
			OncologyDiagnosisConstants.CANCER_750, OncologyDiagnosisConstants.CANCER_751,
			OncologyDiagnosisConstants.CANCER_752, OncologyDiagnosisConstants.CANCER_753,
			OncologyDiagnosisConstants.CANCER_754, OncologyDiagnosisConstants.CANCER_755,
			OncologyDiagnosisConstants.CANCER_756, OncologyDiagnosisConstants.CANCER_757,
			OncologyDiagnosisConstants.CANCER_758, OncologyDiagnosisConstants.CANCER_759,
			OncologyDiagnosisConstants.CANCER_760, OncologyDiagnosisConstants.CANCER_761,
			OncologyDiagnosisConstants.CANCER_762, OncologyDiagnosisConstants.CANCER_763,
			OncologyDiagnosisConstants.CANCER_764, OncologyDiagnosisConstants.CANCER_765,
			OncologyDiagnosisConstants.CANCER_766, OncologyDiagnosisConstants.CANCER_767,
			OncologyDiagnosisConstants.CANCER_768, OncologyDiagnosisConstants.CANCER_769,
			OncologyDiagnosisConstants.CANCER_770, OncologyDiagnosisConstants.CANCER_771,
			OncologyDiagnosisConstants.CANCER_772, OncologyDiagnosisConstants.CANCER_773,
			OncologyDiagnosisConstants.CANCER_774, OncologyDiagnosisConstants.CANCER_775,
			OncologyDiagnosisConstants.CANCER_776, OncologyDiagnosisConstants.CANCER_777,
			OncologyDiagnosisConstants.CANCER_778, OncologyDiagnosisConstants.CANCER_779,
			OncologyDiagnosisConstants.CANCER_780, OncologyDiagnosisConstants.CANCER_781,
			OncologyDiagnosisConstants.CANCER_782, OncologyDiagnosisConstants.CANCER_783,
			OncologyDiagnosisConstants.CANCER_784, OncologyDiagnosisConstants.CANCER_785,
			OncologyDiagnosisConstants.CANCER_786, OncologyDiagnosisConstants.CANCER_787,
			OncologyDiagnosisConstants.CANCER_788, OncologyDiagnosisConstants.CANCER_789,
			OncologyDiagnosisConstants.CANCER_790, OncologyDiagnosisConstants.CANCER_791,
			OncologyDiagnosisConstants.CANCER_792, OncologyDiagnosisConstants.CANCER_793,
			OncologyDiagnosisConstants.CANCER_794, OncologyDiagnosisConstants.CANCER_795,
			OncologyDiagnosisConstants.CANCER_796, OncologyDiagnosisConstants.CANCER_797,
			OncologyDiagnosisConstants.CANCER_798, OncologyDiagnosisConstants.CANCER_799,
			OncologyDiagnosisConstants.CANCER_800, OncologyDiagnosisConstants.CANCER_801,
			OncologyDiagnosisConstants.CANCER_802, OncologyDiagnosisConstants.CANCER_803,
			OncologyDiagnosisConstants.CANCER_804, OncologyDiagnosisConstants.CANCER_805,
			OncologyDiagnosisConstants.CANCER_806, OncologyDiagnosisConstants.CANCER_807,
			OncologyDiagnosisConstants.CANCER_808, OncologyDiagnosisConstants.CANCER_809,
			OncologyDiagnosisConstants.CANCER_810, OncologyDiagnosisConstants.CANCER_811,
			OncologyDiagnosisConstants.CANCER_812, OncologyDiagnosisConstants.CANCER_813,
			OncologyDiagnosisConstants.CANCER_814, OncologyDiagnosisConstants.CANCER_815,
			OncologyDiagnosisConstants.CANCER_816, OncologyDiagnosisConstants.CANCER_817,
			OncologyDiagnosisConstants.CANCER_818, OncologyDiagnosisConstants.CANCER_819,
			OncologyDiagnosisConstants.CANCER_820, OncologyDiagnosisConstants.CANCER_821,
			OncologyDiagnosisConstants.CANCER_822, OncologyDiagnosisConstants.CANCER_823,
			OncologyDiagnosisConstants.CANCER_824, OncologyDiagnosisConstants.CANCER_825,
			OncologyDiagnosisConstants.CANCER_826, OncologyDiagnosisConstants.CANCER_827,
			OncologyDiagnosisConstants.CANCER_828, OncologyDiagnosisConstants.CANCER_829,
			OncologyDiagnosisConstants.CANCER_830, OncologyDiagnosisConstants.CANCER_831,
			OncologyDiagnosisConstants.CANCER_832, OncologyDiagnosisConstants.CANCER_833,
			OncologyDiagnosisConstants.CANCER_834, OncologyDiagnosisConstants.CANCER_835,
			OncologyDiagnosisConstants.CANCER_836, OncologyDiagnosisConstants.CANCER_837,
			OncologyDiagnosisConstants.CANCER_838, OncologyDiagnosisConstants.CANCER_839,
			OncologyDiagnosisConstants.CANCER_840, OncologyDiagnosisConstants.CANCER_841,
			OncologyDiagnosisConstants.CANCER_842, OncologyDiagnosisConstants.CANCER_843,
			OncologyDiagnosisConstants.CANCER_844, OncologyDiagnosisConstants.CANCER_845,
			OncologyDiagnosisConstants.CANCER_846, OncologyDiagnosisConstants.CANCER_847,
			OncologyDiagnosisConstants.CANCER_848, OncologyDiagnosisConstants.CANCER_849,
			OncologyDiagnosisConstants.CANCER_850, OncologyDiagnosisConstants.CANCER_851,
			OncologyDiagnosisConstants.CANCER_852, OncologyDiagnosisConstants.CANCER_853,
			OncologyDiagnosisConstants.CANCER_854, OncologyDiagnosisConstants.CANCER_855,
			OncologyDiagnosisConstants.CANCER_856, OncologyDiagnosisConstants.CANCER_857,
			OncologyDiagnosisConstants.CANCER_858, OncologyDiagnosisConstants.CANCER_859,
			OncologyDiagnosisConstants.CANCER_860, OncologyDiagnosisConstants.CANCER_861,
			OncologyDiagnosisConstants.CANCER_862, OncologyDiagnosisConstants.CANCER_863,
			OncologyDiagnosisConstants.CANCER_864, OncologyDiagnosisConstants.CANCER_865,
			OncologyDiagnosisConstants.CANCER_866, OncologyDiagnosisConstants.CANCER_867,
			OncologyDiagnosisConstants.CANCER_868, OncologyDiagnosisConstants.CANCER_869,
			OncologyDiagnosisConstants.CANCER_870, OncologyDiagnosisConstants.CANCER_871,
			OncologyDiagnosisConstants.CANCER_872, OncologyDiagnosisConstants.CANCER_873,
			OncologyDiagnosisConstants.CANCER_874, OncologyDiagnosisConstants.CANCER_875,
			OncologyDiagnosisConstants.CANCER_876, OncologyDiagnosisConstants.CANCER_877,
			OncologyDiagnosisConstants.CANCER_878, OncologyDiagnosisConstants.CANCER_879,
			OncologyDiagnosisConstants.CANCER_880, OncologyDiagnosisConstants.CANCER_881,
			OncologyDiagnosisConstants.CANCER_882, OncologyDiagnosisConstants.CANCER_883,
			OncologyDiagnosisConstants.CANCER_884, OncologyDiagnosisConstants.CANCER_885,
			OncologyDiagnosisConstants.CANCER_886, OncologyDiagnosisConstants.CANCER_887,
			OncologyDiagnosisConstants.CANCER_888, OncologyDiagnosisConstants.CANCER_889,
			OncologyDiagnosisConstants.CANCER_890, OncologyDiagnosisConstants.CANCER_891,
			OncologyDiagnosisConstants.CANCER_892, OncologyDiagnosisConstants.CANCER_893,
			OncologyDiagnosisConstants.CANCER_894, OncologyDiagnosisConstants.CANCER_895,
			OncologyDiagnosisConstants.CANCER_896, OncologyDiagnosisConstants.CANCER_897,
			OncologyDiagnosisConstants.CANCER_898, OncologyDiagnosisConstants.CANCER_899,
			OncologyDiagnosisConstants.CANCER_900, OncologyDiagnosisConstants.CANCER_901,
			OncologyDiagnosisConstants.CANCER_902, OncologyDiagnosisConstants.CANCER_903,
			OncologyDiagnosisConstants.CANCER_904, OncologyDiagnosisConstants.CANCER_905,
			OncologyDiagnosisConstants.CANCER_906, OncologyDiagnosisConstants.CANCER_907,
			OncologyDiagnosisConstants.CANCER_908, OncologyDiagnosisConstants.CANCER_909,
			OncologyDiagnosisConstants.CANCER_910, OncologyDiagnosisConstants.CANCER_911,
			OncologyDiagnosisConstants.CANCER_912, OncologyDiagnosisConstants.CANCER_913,
			OncologyDiagnosisConstants.CANCER_914, OncologyDiagnosisConstants.CANCER_915,
			OncologyDiagnosisConstants.CANCER_916, OncologyDiagnosisConstants.CANCER_917,
			OncologyDiagnosisConstants.CANCER_918, OncologyDiagnosisConstants.CANCER_919,
			OncologyDiagnosisConstants.CANCER_920, OncologyDiagnosisConstants.CANCER_921,
			OncologyDiagnosisConstants.CANCER_922, OncologyDiagnosisConstants.CANCER_923,
			OncologyDiagnosisConstants.CANCER_924, OncologyDiagnosisConstants.CANCER_925,
			OncologyDiagnosisConstants.CANCER_926, OncologyDiagnosisConstants.CANCER_927,
			OncologyDiagnosisConstants.CANCER_928, OncologyDiagnosisConstants.CANCER_929,
			OncologyDiagnosisConstants.CANCER_930, OncologyDiagnosisConstants.CANCER_931,
			OncologyDiagnosisConstants.CANCER_932, OncologyDiagnosisConstants.CANCER_933,
			OncologyDiagnosisConstants.CANCER_934, OncologyDiagnosisConstants.CANCER_935,
			OncologyDiagnosisConstants.CANCER_936, OncologyDiagnosisConstants.CANCER_937,
			OncologyDiagnosisConstants.CANCER_938, OncologyDiagnosisConstants.CANCER_939,
			OncologyDiagnosisConstants.CANCER_940, OncologyDiagnosisConstants.CANCER_941,
			OncologyDiagnosisConstants.CANCER_942, OncologyDiagnosisConstants.CANCER_943,
			OncologyDiagnosisConstants.CANCER_944, OncologyDiagnosisConstants.CANCER_945,
			OncologyDiagnosisConstants.CANCER_946, OncologyDiagnosisConstants.CANCER_947,
			OncologyDiagnosisConstants.CANCER_948, OncologyDiagnosisConstants.CANCER_949,
			OncologyDiagnosisConstants.CANCER_950, OncologyDiagnosisConstants.CANCER_951,
			OncologyDiagnosisConstants.CANCER_952, OncologyDiagnosisConstants.CANCER_953,
			OncologyDiagnosisConstants.CANCER_954, OncologyDiagnosisConstants.CANCER_955,
			OncologyDiagnosisConstants.CANCER_956, OncologyDiagnosisConstants.CANCER_957,
			OncologyDiagnosisConstants.CANCER_958, OncologyDiagnosisConstants.CANCER_959,
			OncologyDiagnosisConstants.CANCER_960, OncologyDiagnosisConstants.CANCER_961,
			OncologyDiagnosisConstants.CANCER_962, OncologyDiagnosisConstants.CANCER_963,
			OncologyDiagnosisConstants.CANCER_964, OncologyDiagnosisConstants.CANCER_965,
			OncologyDiagnosisConstants.CANCER_966, OncologyDiagnosisConstants.CANCER_967,
			OncologyDiagnosisConstants.CANCER_968, OncologyDiagnosisConstants.CANCER_969,
			OncologyDiagnosisConstants.CANCER_970, OncologyDiagnosisConstants.CANCER_971,
			OncologyDiagnosisConstants.CANCER_972, OncologyDiagnosisConstants.CANCER_973,
			OncologyDiagnosisConstants.CANCER_974, OncologyDiagnosisConstants.CANCER_975,
			OncologyDiagnosisConstants.CANCER_976, OncologyDiagnosisConstants.CANCER_977,
			OncologyDiagnosisConstants.CANCER_978, OncologyDiagnosisConstants.CANCER_979,
			OncologyDiagnosisConstants.CANCER_980, OncologyDiagnosisConstants.CANCER_981,
			OncologyDiagnosisConstants.CANCER_982, OncologyDiagnosisConstants.CANCER_983,
			OncologyDiagnosisConstants.CANCER_984, OncologyDiagnosisConstants.CANCER_985,
			OncologyDiagnosisConstants.CANCER_986, OncologyDiagnosisConstants.CANCER_987,
			OncologyDiagnosisConstants.CANCER_988, OncologyDiagnosisConstants.CANCER_989,
			OncologyDiagnosisConstants.CANCER_990, OncologyDiagnosisConstants.CANCER_991,
			OncologyDiagnosisConstants.CANCER_992, OncologyDiagnosisConstants.CANCER_993,
			OncologyDiagnosisConstants.CANCER_994, OncologyDiagnosisConstants.CANCER_995,
			OncologyDiagnosisConstants.CANCER_996, OncologyDiagnosisConstants.CANCER_997,
			OncologyDiagnosisConstants.CANCER_998, OncologyDiagnosisConstants.CANCER_999,
			OncologyDiagnosisConstants.CANCER_1000, OncologyDiagnosisConstants.CANCER_1001,
			OncologyDiagnosisConstants.CANCER_1002, OncologyDiagnosisConstants.CANCER_1003,
			OncologyDiagnosisConstants.CANCER_1004, OncologyDiagnosisConstants.CANCER_1005,
			OncologyDiagnosisConstants.CANCER_1006, OncologyDiagnosisConstants.CANCER_1007,
			OncologyDiagnosisConstants.CANCER_1008, OncologyDiagnosisConstants.CANCER_1009,
			OncologyDiagnosisConstants.CANCER_1010, OncologyDiagnosisConstants.CANCER_1011,
			OncologyDiagnosisConstants.CANCER_1012, OncologyDiagnosisConstants.CANCER_1013,
			OncologyDiagnosisConstants.CANCER_1014, OncologyDiagnosisConstants.CANCER_1015,
			OncologyDiagnosisConstants.CANCER_1016, OncologyDiagnosisConstants.CANCER_1017,
			OncologyDiagnosisConstants.CANCER_1018, OncologyDiagnosisConstants.CANCER_1019,
			OncologyDiagnosisConstants.CANCER_1020, OncologyDiagnosisConstants.CANCER_1021,
			OncologyDiagnosisConstants.CANCER_1022, OncologyDiagnosisConstants.CANCER_1023,
			OncologyDiagnosisConstants.CANCER_1024, OncologyDiagnosisConstants.CANCER_1025,
			OncologyDiagnosisConstants.CANCER_1026, OncologyDiagnosisConstants.CANCER_1027,
			OncologyDiagnosisConstants.CANCER_1028, OncologyDiagnosisConstants.CANCER_1029,
			OncologyDiagnosisConstants.CANCER_1030, OncologyDiagnosisConstants.CANCER_1031,
			OncologyDiagnosisConstants.CANCER_1032, OncologyDiagnosisConstants.CANCER_1033,
			OncologyDiagnosisConstants.CANCER_1034, OncologyDiagnosisConstants.CANCER_1035,
			OncologyDiagnosisConstants.CANCER_1036, OncologyDiagnosisConstants.CANCER_1037,
			OncologyDiagnosisConstants.CANCER_1038, OncologyDiagnosisConstants.CANCER_1039,
			OncologyDiagnosisConstants.CANCER_1040, OncologyDiagnosisConstants.CANCER_1041,
			OncologyDiagnosisConstants.CANCER_1042, OncologyDiagnosisConstants.CANCER_1043,
			OncologyDiagnosisConstants.CANCER_1044, OncologyDiagnosisConstants.CANCER_1045,
			OncologyDiagnosisConstants.CANCER_1046, OncologyDiagnosisConstants.CANCER_1047,
			OncologyDiagnosisConstants.CANCER_1048, OncologyDiagnosisConstants.CANCER_1049,
			OncologyDiagnosisConstants.CANCER_1050, OncologyDiagnosisConstants.CANCER_1051,
			OncologyDiagnosisConstants.CANCER_1052, OncologyDiagnosisConstants.CANCER_1053,
			OncologyDiagnosisConstants.CANCER_1054, OncologyDiagnosisConstants.CANCER_1055,
			OncologyDiagnosisConstants.CANCER_1056, OncologyDiagnosisConstants.CANCER_1057,
			OncologyDiagnosisConstants.CANCER_1058, OncologyDiagnosisConstants.CANCER_1059,
			OncologyDiagnosisConstants.CANCER_1060, OncologyDiagnosisConstants.CANCER_1061,
			OncologyDiagnosisConstants.CANCER_1062, OncologyDiagnosisConstants.CANCER_1063,
			OncologyDiagnosisConstants.CANCER_1064, OncologyDiagnosisConstants.CANCER_1065,
			OncologyDiagnosisConstants.CANCER_1066, OncologyDiagnosisConstants.CANCER_1067,
			OncologyDiagnosisConstants.CANCER_1068, OncologyDiagnosisConstants.CANCER_1069,
			OncologyDiagnosisConstants.CANCER_1070, OncologyDiagnosisConstants.CANCER_1071,
			OncologyDiagnosisConstants.CANCER_1072, OncologyDiagnosisConstants.CANCER_1073,
			OncologyDiagnosisConstants.CANCER_1074, OncologyDiagnosisConstants.CANCER_1075,
			OncologyDiagnosisConstants.CANCER_1076, OncologyDiagnosisConstants.CANCER_1077,
			OncologyDiagnosisConstants.CANCER_1078, OncologyDiagnosisConstants.CANCER_1079,
			OncologyDiagnosisConstants.CANCER_1080, OncologyDiagnosisConstants.CANCER_1081,
			OncologyDiagnosisConstants.CANCER_1082, OncologyDiagnosisConstants.CANCER_1083,
			OncologyDiagnosisConstants.CANCER_1084, OncologyDiagnosisConstants.CANCER_1085,
			OncologyDiagnosisConstants.CANCER_1086, OncologyDiagnosisConstants.CANCER_1087,
			OncologyDiagnosisConstants.CANCER_1088, OncologyDiagnosisConstants.CANCER_1089,
			OncologyDiagnosisConstants.CANCER_1090, OncologyDiagnosisConstants.CANCER_1091,
			OncologyDiagnosisConstants.CANCER_1092, OncologyDiagnosisConstants.CANCER_1093,
			OncologyDiagnosisConstants.CANCER_1094, OncologyDiagnosisConstants.CANCER_1095,
			OncologyDiagnosisConstants.CANCER_1096, OncologyDiagnosisConstants.CANCER_1097,
			OncologyDiagnosisConstants.CANCER_1098, OncologyDiagnosisConstants.CANCER_1099,
			OncologyDiagnosisConstants.CANCER_1100, OncologyDiagnosisConstants.CANCER_1101,
			OncologyDiagnosisConstants.CANCER_1102, OncologyDiagnosisConstants.CANCER_1103,
			OncologyDiagnosisConstants.CANCER_1104, OncologyDiagnosisConstants.CANCER_1105,
			OncologyDiagnosisConstants.CANCER_1106, OncologyDiagnosisConstants.CANCER_1107,
			OncologyDiagnosisConstants.CANCER_1108, OncologyDiagnosisConstants.CANCER_1109,
			OncologyDiagnosisConstants.CANCER_1110, OncologyDiagnosisConstants.CANCER_1111,
			OncologyDiagnosisConstants.CANCER_1112, OncologyDiagnosisConstants.CANCER_1113,
			OncologyDiagnosisConstants.CANCER_1114, OncologyDiagnosisConstants.CANCER_1115,
			OncologyDiagnosisConstants.CANCER_1116, OncologyDiagnosisConstants.CANCER_1117,
			OncologyDiagnosisConstants.CANCER_1118, OncologyDiagnosisConstants.CANCER_1119,
			OncologyDiagnosisConstants.CANCER_1120, OncologyDiagnosisConstants.CANCER_1121,
			OncologyDiagnosisConstants.CANCER_1122, OncologyDiagnosisConstants.CANCER_1123,
			OncologyDiagnosisConstants.CANCER_1124, OncologyDiagnosisConstants.CANCER_1125,
			OncologyDiagnosisConstants.CANCER_1126, OncologyDiagnosisConstants.CANCER_1127,
			OncologyDiagnosisConstants.CANCER_1128, OncologyDiagnosisConstants.CANCER_1129,
			OncologyDiagnosisConstants.CANCER_1130, OncologyDiagnosisConstants.CANCER_1131,
			OncologyDiagnosisConstants.CANCER_1132, OncologyDiagnosisConstants.CANCER_1133,
			OncologyDiagnosisConstants.CANCER_1134, OncologyDiagnosisConstants.CANCER_1135,
			OncologyDiagnosisConstants.CANCER_1136, OncologyDiagnosisConstants.CANCER_1137,
			OncologyDiagnosisConstants.CANCER_1138, OncologyDiagnosisConstants.CANCER_1139,
			OncologyDiagnosisConstants.CANCER_1140, OncologyDiagnosisConstants.CANCER_1141,
			OncologyDiagnosisConstants.CANCER_1142, OncologyDiagnosisConstants.CANCER_1143,
			OncologyDiagnosisConstants.CANCER_1144, OncologyDiagnosisConstants.CANCER_1145,
			OncologyDiagnosisConstants.CANCER_1146, OncologyDiagnosisConstants.CANCER_1147,
			OncologyDiagnosisConstants.CANCER_1148, OncologyDiagnosisConstants.CANCER_1149,
			OncologyDiagnosisConstants.CANCER_1150, OncologyDiagnosisConstants.CANCER_1151,
			OncologyDiagnosisConstants.CANCER_1152, OncologyDiagnosisConstants.CANCER_1153,
			OncologyDiagnosisConstants.CANCER_1154, OncologyDiagnosisConstants.CANCER_1155,
			OncologyDiagnosisConstants.CANCER_1156, OncologyDiagnosisConstants.CANCER_1157,
			OncologyDiagnosisConstants.CANCER_1158, OncologyDiagnosisConstants.CANCER_1159,
			OncologyDiagnosisConstants.CANCER_1160, OncologyDiagnosisConstants.CANCER_1161,
			OncologyDiagnosisConstants.CANCER_1162, OncologyDiagnosisConstants.CANCER_1163,
			OncologyDiagnosisConstants.CANCER_1164, OncologyDiagnosisConstants.CANCER_1165,
			OncologyDiagnosisConstants.CANCER_1166, OncologyDiagnosisConstants.CANCER_1167,
			OncologyDiagnosisConstants.CANCER_1168, OncologyDiagnosisConstants.CANCER_1169,
			OncologyDiagnosisConstants.CANCER_1170, OncologyDiagnosisConstants.CANCER_1171,
			OncologyDiagnosisConstants.CANCER_1172, OncologyDiagnosisConstants.CANCER_1173,
			OncologyDiagnosisConstants.CANCER_1174, OncologyDiagnosisConstants.CANCER_1175,
			OncologyDiagnosisConstants.CANCER_1176, OncologyDiagnosisConstants.CANCER_1177,
			OncologyDiagnosisConstants.CANCER_1178, OncologyDiagnosisConstants.CANCER_1179,
			OncologyDiagnosisConstants.CANCER_1180, OncologyDiagnosisConstants.CANCER_1181,
			OncologyDiagnosisConstants.CANCER_1182, OncologyDiagnosisConstants.CANCER_1183,
			OncologyDiagnosisConstants.CANCER_1184, OncologyDiagnosisConstants.CANCER_1185,
			OncologyDiagnosisConstants.CANCER_1186, OncologyDiagnosisConstants.CANCER_1187,
			OncologyDiagnosisConstants.CANCER_1188, OncologyDiagnosisConstants.CANCER_1189,
			OncologyDiagnosisConstants.CANCER_1190, OncologyDiagnosisConstants.CANCER_1191,
			OncologyDiagnosisConstants.CANCER_1192, OncologyDiagnosisConstants.CANCER_1193,
			OncologyDiagnosisConstants.CANCER_1194, OncologyDiagnosisConstants.CANCER_1195,
			OncologyDiagnosisConstants.CANCER_1196, OncologyDiagnosisConstants.CANCER_1197,
			OncologyDiagnosisConstants.CANCER_1198, OncologyDiagnosisConstants.CANCER_1199,
			OncologyDiagnosisConstants.CANCER_1200, OncologyDiagnosisConstants.CANCER_1201,
			OncologyDiagnosisConstants.CANCER_1202, OncologyDiagnosisConstants.CANCER_1203,
			OncologyDiagnosisConstants.CANCER_1204, OncologyDiagnosisConstants.CANCER_1205,
			OncologyDiagnosisConstants.CANCER_1206, OncologyDiagnosisConstants.CANCER_1207,
			OncologyDiagnosisConstants.CANCER_1208, OncologyDiagnosisConstants.CANCER_1209,
			OncologyDiagnosisConstants.CANCER_1210, OncologyDiagnosisConstants.CANCER_1211,
			OncologyDiagnosisConstants.CANCER_1212, OncologyDiagnosisConstants.CANCER_1213,
			OncologyDiagnosisConstants.CANCER_1214, OncologyDiagnosisConstants.CANCER_1215,
			OncologyDiagnosisConstants.CANCER_1216, OncologyDiagnosisConstants.CANCER_1217,
			OncologyDiagnosisConstants.CANCER_1218, OncologyDiagnosisConstants.CANCER_1219);
	}

	public static List<Integer> getPhysicalDisabilityList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.PHYSICAL_DISABILITY2,
			DiagnosisConcepts._DiagnosisConcepts.PHYSICAL_DISABILITY3,
			DiagnosisConcepts._DiagnosisConcepts.PHYSICAL_DISABILITY4,
			DiagnosisConcepts._DiagnosisConcepts.PHYSICAL_DISABILITY5,
			DiagnosisConcepts._DiagnosisConcepts.PHYSICAL_DISABILITY6,
			DiagnosisConcepts._DiagnosisConcepts.PHYSICAL_DISABILITY7

		);
	}







	public static List<Integer> getViralHaemorrhagicFeverList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.HAEMORRHAGICFEVER_1,
			DiagnosisConcepts._DiagnosisConcepts.HAEMORRHAGICFEVER_2);
	}

	public static List<Integer> getMalariaInPregnancyList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.Malaria_in_pregnancy,

			DiagnosisConcepts._DiagnosisConcepts.Malaria_in_Mother_Complicating_Pregnancy_Childbirth_and_or_Puerperium,
			DiagnosisConcepts._DiagnosisConcepts.Malaria_during_pregnancy

		);
	}











	public static List<Integer> getConfirmedMalariaResults() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.POSITIVE,
			DiagnosisConcepts._DiagnosisConcepts.Positive_for_Plasmodium_falciparum,
			DiagnosisConcepts._DiagnosisConcepts.Positive_for_Plasmodium_vivax,
			DiagnosisConcepts._DiagnosisConcepts.Positive_for_both_Plasmodium_falciparum_and_Plasmodium_vivax);
	}

	public static List<Integer> getSuspectedMalariaResults() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.NEGATIVE,
			DiagnosisConcepts._DiagnosisConcepts.INDETERMINATE,

			DiagnosisConcepts._DiagnosisConcepts.Test_not_performed_due_to_lack_of_availability_of_test_materials,
			DiagnosisConcepts._DiagnosisConcepts.POSITIVE, DiagnosisConcepts._DiagnosisConcepts.Procedure_performed);
	}







//	public static List<Integer> getJiggersInfestationList() {
//		return Arrays.asList(123964);
//	}



	public static List<Integer> getFeversList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.FEVER1, DiagnosisConcepts._DiagnosisConcepts.FEVER2,
			DiagnosisConcepts._DiagnosisConcepts.FEVER3, DiagnosisConcepts._DiagnosisConcepts.FEVER4,
			DiagnosisConcepts._DiagnosisConcepts.FEVER5, DiagnosisConcepts._DiagnosisConcepts.FEVER6,
			DiagnosisConcepts._DiagnosisConcepts.FEVER7, DiagnosisConcepts._DiagnosisConcepts.FEVER8,
			DiagnosisConcepts._DiagnosisConcepts.RIFT_VALLEY_FEVER, DiagnosisConcepts._DiagnosisConcepts.FEVER10,
			DiagnosisConcepts._DiagnosisConcepts.FEVER11, DiagnosisConcepts._DiagnosisConcepts.FEVER12,
			DiagnosisConcepts._DiagnosisConcepts.FEVER13, DiagnosisConcepts._DiagnosisConcepts.FEVER14,
			DiagnosisConcepts._DiagnosisConcepts.FEVER15, DiagnosisConcepts._DiagnosisConcepts.FEVER16,
			DiagnosisConcepts._DiagnosisConcepts.FEVER17, DiagnosisConcepts._DiagnosisConcepts.FEVER18,
			DiagnosisConcepts._DiagnosisConcepts.Chikungunya_Fever,
			DiagnosisConcepts._DiagnosisConcepts.Chikungunya_Haemorrhagic_Fever,
			DiagnosisConcepts._DiagnosisConcepts.FEVER21, DiagnosisConcepts._DiagnosisConcepts.FEVER22,
			DiagnosisConcepts._DiagnosisConcepts.FEVER23, DiagnosisConcepts._DiagnosisConcepts.FEVER24,
			DiagnosisConcepts._DiagnosisConcepts.FEVER25, DiagnosisConcepts._DiagnosisConcepts.FEVER26,
			DiagnosisConcepts._DiagnosisConcepts.FEVER27, DiagnosisConcepts._DiagnosisConcepts.FEVER28,
			DiagnosisConcepts._DiagnosisConcepts.FEVER29, DiagnosisConcepts._DiagnosisConcepts.FEVER30,
			DiagnosisConcepts._DiagnosisConcepts.FEVER31, DiagnosisConcepts._DiagnosisConcepts.FEVER32,
			DiagnosisConcepts._DiagnosisConcepts.FEVER33, DiagnosisConcepts._DiagnosisConcepts.FEVER34,
			DiagnosisConcepts._DiagnosisConcepts.FEVER35, DiagnosisConcepts._DiagnosisConcepts.FEVER36,
			DiagnosisConcepts._DiagnosisConcepts.FEVER37, DiagnosisConcepts._DiagnosisConcepts.FEVER38,
			DiagnosisConcepts._DiagnosisConcepts.FEVER39, DiagnosisConcepts._DiagnosisConcepts.FEVER40,
			DiagnosisConcepts._DiagnosisConcepts.FEVER41, DiagnosisConcepts._DiagnosisConcepts.FEVER42,
			DiagnosisConcepts._DiagnosisConcepts.FEVER43, DiagnosisConcepts._DiagnosisConcepts.FEVER44,
			DiagnosisConcepts._DiagnosisConcepts.FEVER45, DiagnosisConcepts._DiagnosisConcepts.FEVER46,
			DiagnosisConcepts._DiagnosisConcepts.FEVER47, DiagnosisConcepts._DiagnosisConcepts.FEVER48,
			DiagnosisConcepts._DiagnosisConcepts.FEVER49, DiagnosisConcepts._DiagnosisConcepts.FEVER50,
			DiagnosisConcepts._DiagnosisConcepts.FEVER51, DiagnosisConcepts._DiagnosisConcepts.FEVER52,
			DiagnosisConcepts._DiagnosisConcepts.FEVER53, DiagnosisConcepts._DiagnosisConcepts.FEVER54,
			DiagnosisConcepts._DiagnosisConcepts.FEVER55, DiagnosisConcepts._DiagnosisConcepts.FEVER56,
			DiagnosisConcepts._DiagnosisConcepts.FEVER57, DiagnosisConcepts._DiagnosisConcepts.FEVER58,
			DiagnosisConcepts._DiagnosisConcepts.FEVER59, DiagnosisConcepts._DiagnosisConcepts.FEVER60,
			DiagnosisConcepts._DiagnosisConcepts.FEVER61, DiagnosisConcepts._DiagnosisConcepts.FEVER62,
			DiagnosisConcepts._DiagnosisConcepts.FEVER63, DiagnosisConcepts._DiagnosisConcepts.FEVER64,
			DiagnosisConcepts._DiagnosisConcepts.FEVER65, DiagnosisConcepts._DiagnosisConcepts.FEVER66,
			DiagnosisConcepts._DiagnosisConcepts.FEVER67, DiagnosisConcepts._DiagnosisConcepts.FEVER68,
			DiagnosisConcepts._DiagnosisConcepts.FEVER69, DiagnosisConcepts._DiagnosisConcepts.FEVER70,
			DiagnosisConcepts._DiagnosisConcepts.FEVER71, DiagnosisConcepts._DiagnosisConcepts.FEVER72,
			DiagnosisConcepts._DiagnosisConcepts.FEVER73, DiagnosisConcepts._DiagnosisConcepts.FEVER74,
			DiagnosisConcepts._DiagnosisConcepts.FEVER75, DiagnosisConcepts._DiagnosisConcepts.FEVER76,
			DiagnosisConcepts._DiagnosisConcepts.FEVER77, DiagnosisConcepts._DiagnosisConcepts.FEVER78,
			DiagnosisConcepts._DiagnosisConcepts.FEVER79, DiagnosisConcepts._DiagnosisConcepts.FEVER80,
			DiagnosisConcepts._DiagnosisConcepts.FEVER81, DiagnosisConcepts._DiagnosisConcepts.FEVER82,
			DiagnosisConcepts._DiagnosisConcepts.FEVER83, DiagnosisConcepts._DiagnosisConcepts.FEVER84,
			DiagnosisConcepts._DiagnosisConcepts.FEVER85, DiagnosisConcepts._DiagnosisConcepts.FEVER86,
			DiagnosisConcepts._DiagnosisConcepts.FEVER87, DiagnosisConcepts._DiagnosisConcepts.FEVER88,
			DiagnosisConcepts._DiagnosisConcepts.FEVER89, DiagnosisConcepts._DiagnosisConcepts.FEVER90,
			DiagnosisConcepts._DiagnosisConcepts.FEVER91, DiagnosisConcepts._DiagnosisConcepts.FEVER92,
			DiagnosisConcepts._DiagnosisConcepts.FEVER93, DiagnosisConcepts._DiagnosisConcepts.FEVER94,
			DiagnosisConcepts._DiagnosisConcepts.FEVER95, DiagnosisConcepts._DiagnosisConcepts.FEVER96,
			DiagnosisConcepts._DiagnosisConcepts.FEVER97, DiagnosisConcepts._DiagnosisConcepts.FEVER98,
			DiagnosisConcepts._DiagnosisConcepts.FEVER99, DiagnosisConcepts._DiagnosisConcepts.FEVER100,
			DiagnosisConcepts._DiagnosisConcepts.FEVER101, DiagnosisConcepts._DiagnosisConcepts.FEVER102,
			DiagnosisConcepts._DiagnosisConcepts.FEVER103, DiagnosisConcepts._DiagnosisConcepts.FEVER103,
			DiagnosisConcepts._DiagnosisConcepts.FEVER104, DiagnosisConcepts._DiagnosisConcepts.FEVER105,
			DiagnosisConcepts._DiagnosisConcepts.FEVER106, DiagnosisConcepts._DiagnosisConcepts.Dengue_haemorrhagic_fever,
			DiagnosisConcepts._DiagnosisConcepts.Dengue_fever, DiagnosisConcepts._DiagnosisConcepts.FEVER109,
			DiagnosisConcepts._DiagnosisConcepts.FEVER110, DiagnosisConcepts._DiagnosisConcepts.FEVER111,
			DiagnosisConcepts._DiagnosisConcepts.FEVER112, DiagnosisConcepts._DiagnosisConcepts.FEVER113,
			DiagnosisConcepts._DiagnosisConcepts.FEVER114, DiagnosisConcepts._DiagnosisConcepts.FEVER115,
			DiagnosisConcepts._DiagnosisConcepts.FEVER116, DiagnosisConcepts._DiagnosisConcepts.FEVER117,
			DiagnosisConcepts._DiagnosisConcepts.FEVER118, DiagnosisConcepts._DiagnosisConcepts.FEVER119,
			DiagnosisConcepts._DiagnosisConcepts.FEVER120, DiagnosisConcepts._DiagnosisConcepts.FEVER121,
			DiagnosisConcepts._DiagnosisConcepts.FEVER122, DiagnosisConcepts._DiagnosisConcepts.FEVER123,
			DiagnosisConcepts._DiagnosisConcepts.FEVER124, DiagnosisConcepts._DiagnosisConcepts.FEVER125,
			DiagnosisConcepts._DiagnosisConcepts.FEVER126, DiagnosisConcepts._DiagnosisConcepts.FEVER127,
			DiagnosisConcepts._DiagnosisConcepts.FEVER128, DiagnosisConcepts._DiagnosisConcepts.FEVER129,
			DiagnosisConcepts._DiagnosisConcepts.FEVER130, DiagnosisConcepts._DiagnosisConcepts.FEVER131,
			DiagnosisConcepts._DiagnosisConcepts.FEVER132, DiagnosisConcepts._DiagnosisConcepts.FEVER133,
			DiagnosisConcepts._DiagnosisConcepts.FEVER134, DiagnosisConcepts._DiagnosisConcepts.FEVER135,
			DiagnosisConcepts._DiagnosisConcepts.FEVER136, DiagnosisConcepts._DiagnosisConcepts.FEVER137,
			DiagnosisConcepts._DiagnosisConcepts.FEVER138, DiagnosisConcepts._DiagnosisConcepts.FEVER139,
			DiagnosisConcepts._DiagnosisConcepts.FEVER140, DiagnosisConcepts._DiagnosisConcepts.FEVER141

		);
	}







	public static List<Integer> mildDehydration() {
		return Arrays
			.asList(DiagnosisConcepts._DiagnosisConcepts.DEHYDRATION,
				DiagnosisConcepts._DiagnosisConcepts.Neonatal_dehydration,
				DiagnosisConcepts._DiagnosisConcepts.Dehydration,
				DiagnosisConcepts._DiagnosisConcepts.Moderate_dehydration,
				DiagnosisConcepts._DiagnosisConcepts.Mild_dehydration);
	}

	public static List<Integer> getCutaneousLeishmaniasisList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.Dry_Form_of_Cutaneous_Leishmaniasis,
			DiagnosisConcepts._DiagnosisConcepts.Zoonotic_Form_of_Cutaneous_Leishmaniasis,
			DiagnosisConcepts._DiagnosisConcepts.Wet_Form_of_Cutaneous_Leishmaniasis,
			DiagnosisConcepts._DiagnosisConcepts.Ulcerating_Cutaneous_Leishmaniasis,
			DiagnosisConcepts._DiagnosisConcepts.Recurrent_Cutaneous_Leishmaniasis,
			DiagnosisConcepts._DiagnosisConcepts.Late_Cutaneous_Leishmaniasis,
			DiagnosisConcepts._DiagnosisConcepts.Disseminated_Mucocutaneous_Leishmaniasis,
			DiagnosisConcepts._DiagnosisConcepts.Diffuse_Cutaneous_Leishmaniasis,
			DiagnosisConcepts._DiagnosisConcepts.Cutaneous_Leishmaniasis,
			DiagnosisConcepts._DiagnosisConcepts.Asian_Desert_Cutaneous_Leishmaniasis,
			DiagnosisConcepts._DiagnosisConcepts.American_Cutaneous_Mucocutaneous_Leishmaniasis,
			DiagnosisConcepts._DiagnosisConcepts.Acute_Necrotising_Cutaneous_Leishmaniasis);
	}

	public static List<Integer> getAnthraxList() {
		return Arrays
			.asList(DiagnosisConcepts._DiagnosisConcepts.Pulmonary_Anthrax,
				DiagnosisConcepts._DiagnosisConcepts.Contact_with_or_Exposure_to_Anthrax,
				DiagnosisConcepts._DiagnosisConcepts.Anthrax,
				DiagnosisConcepts._DiagnosisConcepts.Gastrointestinal_Anthrax,
				DiagnosisConcepts._DiagnosisConcepts.Cutaneous_Anthrax,
				DiagnosisConcepts._DiagnosisConcepts.Anthrax_Septicaemia,
				DiagnosisConcepts._DiagnosisConcepts.Anthrax_Pneumonia);
	}

	public static List<Integer> getNeutalSepsisList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.NEUTAL_SEPISIS);
	}

	public static List<Integer> getDownSyndromeList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.DOWN_SYNDROME);
	}
	
	public static List<Integer> getHypoxaemiaList() {
		return Arrays.asList(DiagnosisConcepts._DiagnosisConcepts.Hypoxaemia);
	}

	public static List<Integer> getJiggersInfestationList() {
		return Arrays.asList(123964);
	}

	

	public static List<Integer> getAllOtherUnder5DiseasesList() {			
		List<Integer> allListedDiagnosisForUnder5 = new ArrayList<Integer>();
		allListedDiagnosisForUnder5.addAll(DiagnosisConcepts.allNonListedDiagnosisForUnder5());		
		allListedDiagnosisForUnder5.addAll(getDiarrheaWithNoDehydrationDiagnosisList());		
		allListedDiagnosisForUnder5.addAll(getGastroenteritisList());
		allListedDiagnosisForUnder5.addAll(getSeverePneumoniaList());		
		allListedDiagnosisForUnder5.addAll(getUpperRespiratoryTractInfectionsList());
		allListedDiagnosisForUnder5.addAll(getLowerTractInfectionList());		
		allListedDiagnosisForUnder5.addAll(getPresumedTuberculosisList());
		allListedDiagnosisForUnder5.addAll(getMalariaList());
		allListedDiagnosisForUnder5.addAll(getOtherMenigitisList());
		allListedDiagnosisForUnder5.addAll(getPoliomyelitisList());
		allListedDiagnosisForUnder5.addAll(getChickenPoxList());
		allListedDiagnosisForUnder5.addAll(getMeaslesList());
		allListedDiagnosisForUnder5.addAll(getHepatitisList());
		allListedDiagnosisForUnder5.addAll(getAmoebiasis());
		allListedDiagnosisForUnder5.addAll(getMumpsList());
		allListedDiagnosisForUnder5.addAll(getTyphoidList());
		allListedDiagnosisForUnder5.addAll(getBilharziaList());
		allListedDiagnosisForUnder5.addAll(getInterstinalwormsList());
		allListedDiagnosisForUnder5.addAll(getEyeInfectionsList());
		allListedDiagnosisForUnder5.addAll(getTonsilitiesList());
		allListedDiagnosisForUnder5.addAll(getPoliomyelitisList());
		allListedDiagnosisForUnder5.addAll(getChickenPoxList());
		allListedDiagnosisForUnder5.addAll(getMumpsList());
		allListedDiagnosisForUnder5.addAll(getTyphoidList());
		allListedDiagnosisForUnder5.addAll(getBilharziaList());
		allListedDiagnosisForUnder5.addAll(getInterstinalwormsList());
		allListedDiagnosisForUnder5.addAll(getEyeInfectionsList());
		allListedDiagnosisForUnder5.addAll(getTonsilitiesList());
		allListedDiagnosisForUnder5.addAll(getUrinaryTractInfectionList());
		allListedDiagnosisForUnder5.addAll(getMentalDisordersList());
		allListedDiagnosisForUnder5.addAll(getDentalDisordersList());
		allListedDiagnosisForUnder5.addAll(getViolenceRelatedInjuriesList());
		allListedDiagnosisForUnder5.addAll(getOtherInjuriesList());
		allListedDiagnosisForUnder5.addAll(getSexualAssaultList());
		allListedDiagnosisForUnder5.addAll(getBurnsList());
		allListedDiagnosisForUnder5.addAll(getSnakeBitesList());
		allListedDiagnosisForUnder5.addAll(getDogBitesList());
		allListedDiagnosisForUnder5.addAll(getOtherBitesList());
		allListedDiagnosisForUnder5.addAll(getDiabetesList());
		allListedDiagnosisForUnder5.addAll(getEpilepsyList());
		allListedDiagnosisForUnder5.addAll(getOtherConvulsiveDisordersList());
		allListedDiagnosisForUnder5.addAll(getReumonicFeverList());
		allListedDiagnosisForUnder5.addAll(getRicketsList());
		allListedDiagnosisForUnder5.addAll(getCerebralPalsyList());
		allListedDiagnosisForUnder5.addAll(getAutismList());
		allListedDiagnosisForUnder5.addAll(getTryponomiasisList());
		allListedDiagnosisForUnder5.addAll(getYellowFeverList());
		allListedDiagnosisForUnder5.addAll(getRiftValleyFeverList());
		allListedDiagnosisForUnder5.addAll(getChikungunyaFeverList());
		allListedDiagnosisForUnder5.addAll(getDengueFeverList());
		allListedDiagnosisForUnder5.addAll(getKalazarLeishmaniasisList());
		allListedDiagnosisForUnder5.addAll(getChildHoodCancerist());
		allListedDiagnosisForUnder5.addAll(getTuberculosisDiagnosisList());
		allListedDiagnosisForUnder5.addAll(getOverweightList());
		allListedDiagnosisForUnder5.addAll(getRoadTrafficInjuriesList());
		return allListedDiagnosisForUnder5;
	}
	public static List<Integer> getAllOtherAbove5DiseasesList() {
		List<Integer> allListedDiagnosisForAbove5 = new ArrayList<Integer>();
		allListedDiagnosisForAbove5.addAll(DiagnosisConcepts.allNonListedDiagnosisForAbove5());
		allListedDiagnosisForAbove5.addAll(getDiarrheaDiagnosisList());
		allListedDiagnosisForAbove5.addAll(getTuberculosisDiagnosisList());
		allListedDiagnosisForAbove5.addAll(getCholeraList());
		allListedDiagnosisForAbove5.addAll(getDysenteryList());
		allListedDiagnosisForAbove5.addAll(getOtherMenigitisListB());
		allListedDiagnosisForAbove5.addAll(getPoliomyelitisListB());
		allListedDiagnosisForAbove5.addAll(getChickenPoxListB());
		allListedDiagnosisForAbove5.addAll(getMeaslesListB());
		allListedDiagnosisForAbove5.addAll(getHepatitisListB());
		allListedDiagnosisForAbove5.addAll(getMumpsListB());
		allListedDiagnosisForAbove5.addAll(getTestedMalariaListB());
		allListedDiagnosisForAbove5.addAll(getMalariaListB());
		allListedDiagnosisForAbove5.addAll(getUrinaryTractInfectionListB());
		allListedDiagnosisForAbove5.addAll(getInterstinalwormsListB());
		allListedDiagnosisForAbove5.addAll(getMalnutritionListB());
		allListedDiagnosisForAbove5.addAll(getAnaemiaListB());
		allListedDiagnosisForAbove5.addAll(getEyeInfectionsListB());
		allListedDiagnosisForAbove5.addAll(getUpperRespiratoryTractInfectionsListB());
		allListedDiagnosisForAbove5.addAll(getAsthmaListB());
		allListedDiagnosisForAbove5.addAll(getPneumoniaListB());
		allListedDiagnosisForAbove5.addAll(getDiseasesOfSkin());
		allListedDiagnosisForAbove5.addAll(getOtherDisOfRespiratorySystemList());
		allListedDiagnosisForAbove5.addAll(getMentalDisordersList());
		allListedDiagnosisForAbove5.addAll(getDentalDisordersListB());
		allListedDiagnosisForAbove5.addAll(getTetanusList());
		allListedDiagnosisForAbove5.addAll(getTyphoidList());
		allListedDiagnosisForAbove5.addAll(getAmoebiasis());
		allListedDiagnosisForAbove5.addAll(getSexuallyTransmittedInfectionsList());
		allListedDiagnosisForAbove5.addAll(getBilharziaList());
		allListedDiagnosisForAbove5.addAll(getAbortionList());
		allListedDiagnosisForAbove5.addAll(getDisOfPuerperiumChildbathList());
		allListedDiagnosisForAbove5.addAll(getHypertensionList());
		allListedDiagnosisForAbove5.addAll(getMentalDisordersList());
		allListedDiagnosisForAbove5.addAll(getAnthritisJointPainsList());
		allListedDiagnosisForAbove5.addAll(getPoisoningList());
		allListedDiagnosisForAbove5.addAll(getRoadTrafficInjuriesList());
		allListedDiagnosisForAbove5.addAll(getOtherInjuriesList());
		allListedDiagnosisForAbove5.addAll(getViolenceRelatedInjuriesList());
		allListedDiagnosisForAbove5.addAll(getSexualAssaultList());
		allListedDiagnosisForAbove5.addAll(getBurnsList());
		allListedDiagnosisForAbove5.addAll(getSnakeBitesList());
		allListedDiagnosisForAbove5.addAll(getDogBitesList());
		allListedDiagnosisForAbove5.addAll(getOtherBitesList());
		allListedDiagnosisForAbove5.addAll(getDiabetesList());
		allListedDiagnosisForAbove5.addAll(getEpilepsyList());
		allListedDiagnosisForAbove5.addAll(getCardiovascularConditionsList());
		allListedDiagnosisForAbove5.addAll(getOtherCentralNervousSystemConditionsList());
		allListedDiagnosisForAbove5.addAll(getOverweightList());
		allListedDiagnosisForAbove5.addAll(getTryponomiasisList());
		allListedDiagnosisForAbove5.addAll(getYellowFeverList());
		allListedDiagnosisForAbove5.addAll(getViralHaemorrhagicFeverList());
		allListedDiagnosisForAbove5.addAll(getRiftValleyFeverList());
		allListedDiagnosisForAbove5.addAll(getChikungunyaFeverList());
		allListedDiagnosisForAbove5.addAll(getDengueFeverList());
		allListedDiagnosisForAbove5.addAll(getKalazarLeishmaniasisList());
		allListedDiagnosisForAbove5.addAll(getMuscularSkeletalConditionsList());
		allListedDiagnosisForAbove5.addAll(getFistulaBirthRelatedList());
		allListedDiagnosisForAbove5.addAll(getNeoplamsList());
	
		return allListedDiagnosisForAbove5;
	}
}
