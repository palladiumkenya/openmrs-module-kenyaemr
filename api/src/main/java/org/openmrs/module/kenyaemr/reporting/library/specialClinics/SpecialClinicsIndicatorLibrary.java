/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.specialClinics;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

/**
 * Indicators specific to the Special Clinics report
 */
@Component
public class SpecialClinicsIndicatorLibrary {

	@Autowired
	private SpecialClinicsCohortLibrary specialClinicsCohortLibrary;


	public CohortIndicator otInterventionVisitType(int visitType, String otIntervention,String specialClinic){
		return cohortIndicator("OT Intervention Visit Type", ReportUtils.map(specialClinicsCohortLibrary.noOtIntervention(visitType, otIntervention, specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator visitType(int visitType, String specialClinic){
		return cohortIndicator("OT Intervention Visit Type", ReportUtils.map(specialClinicsCohortLibrary.visitType(visitType,specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}
		public CohortIndicator totalNoOfOtInterventions(int newVisit, int reVisit,String interventionType,  String specialClinic){
		return cohortIndicator("OT Intervention Visit Type", ReportUtils.map(specialClinicsCohortLibrary.totalNoOfOtInterventions(newVisit,reVisit,interventionType,specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator totalNumberOfATsDispensed(String intervention, String referredIn,String referredOut,  int visitType, String specialClinic){
		return cohortIndicator("OT Intervention Visit Type", ReportUtils.map(specialClinicsCohortLibrary.totalNumberOfATsDispensed(intervention,referredIn,referredOut,visitType,specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator neurodevelopmental(String specialClinic){
		return cohortIndicator("Neurodevelopmental", ReportUtils.map(specialClinicsCohortLibrary.neurodevelopmental(specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator learningFindings(String specialClinic){
		return cohortIndicator("Learning Findings", ReportUtils.map(specialClinicsCohortLibrary.learningFindings(specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator neurodiversityConditions(String specialClinic){
		return cohortIndicator("Neurodiversity Conditions", ReportUtils.map(specialClinicsCohortLibrary.neurodiversityConditions(specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator childrenWithIntellectualDisabilities(String specialClinic){
		return cohortIndicator("Children With Intellectual Disabilities", ReportUtils.map(specialClinicsCohortLibrary.childrenWithIntellectualDisabilities(specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator delayedDevelopmentalMilestones(String specialClinic){
		return cohortIndicator("Delayed Developmental Milestones", ReportUtils.map(specialClinicsCohortLibrary.delayedDevelopmentalMilestones(specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator childrenTrainedOnAT(String specialClinic){
		return cohortIndicator("No. of children  identified on assistive technology play device", ReportUtils.map(specialClinicsCohortLibrary.childrenTrainedOnAT(specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator totalCountVisits(int newVisits, int revists, String specialClinic){
		return cohortIndicator("No. total visits", ReportUtils.map(specialClinicsCohortLibrary.totalCountVisits(newVisits,revists,specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}
	public  CohortIndicator totalNumberOfATsNewAndRevisitDispensed(String intervention, String referredIn, String referredOut, int newVisit, int reVisit, String specialClinic) {
		return cohortIndicator("No. total of new and Revisit Dispensed", ReportUtils.map(specialClinicsCohortLibrary.totalNumberOfATsNewAndRevisitDispensed(intervention,referredIn,referredOut,newVisit,reVisit,specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator paymentType(String paymentType, String specialClinic) {
		return cohortIndicator("Payment Type", ReportUtils.map(specialClinicsCohortLibrary.paymentType(paymentType,specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator totalAmountPaid(String specialClinic, String paymentType) {
		return cohortIndicator("Total Amount Paid", ReportUtils.map(specialClinicsCohortLibrary.totalAmountPaid(specialClinic,paymentType), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator procedureDone(Integer procedureType, String specialClinic) {
		return cohortIndicator("Procedure done", ReportUtils.map(specialClinicsCohortLibrary.procedureDone(procedureType,specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator procedureOtherDone(Integer crepeBandages,Integer armsling, String specialClinic) {
		return cohortIndicator("Other Procedure done", ReportUtils.map(specialClinicsCohortLibrary.procedureOtherDone(crepeBandages,armsling,specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator referredTo( String specialClinic) {
		return cohortIndicator("Referred To", ReportUtils.map(specialClinicsCohortLibrary.facilityTo(specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator referredFrom( String specialClinic) {
		return cohortIndicator("Referred From", ReportUtils.map(specialClinicsCohortLibrary.facilityFrom(specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator closedReduction(String reduction, String specialClinic) {
		return cohortIndicator("Closed Reduction", ReportUtils.map(specialClinicsCohortLibrary.closedReduction(reduction,specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator fractureAndDislocation(String specialClinic) {
		return cohortIndicator("Fracture and Dislocation", ReportUtils.map(specialClinicsCohortLibrary.fractureAndDislocation(specialClinic), "startDate=${startDate},endDate=${endDate}"));
	}


}