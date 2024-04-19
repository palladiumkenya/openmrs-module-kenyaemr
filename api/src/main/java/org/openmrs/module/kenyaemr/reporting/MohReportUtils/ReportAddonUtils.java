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



import java.util.Arrays;
import java.util.List;

public class ReportAddonUtils {

	public static List<ColumnParameters> getAdultChildrenColumns() {
		ColumnParameters day1 = new ColumnParameters("day1", "Day 1", "day=1", "01");
		ColumnParameters day2 = new ColumnParameters("day2", "Day 2", "day=2", "02");
		ColumnParameters day3 = new ColumnParameters("day3", "Day 3", "day=3", "03");
		ColumnParameters day4 = new ColumnParameters("day4", "Day 4", "day=4", "04");
		ColumnParameters day5 = new ColumnParameters("day5", "Day 5", "day=5", "05");
		ColumnParameters day6 = new ColumnParameters("day6", "Day 6", "day=6", "06");
		ColumnParameters day7 = new ColumnParameters("day7", "Day 7", "day=7", "07");
		ColumnParameters day8 = new ColumnParameters("day8", "Day 8", "day=8", "08");
		ColumnParameters day9 = new ColumnParameters("day9", "Day 9", "day=9", "09");
		ColumnParameters day10 = new ColumnParameters("day10", "Day 10", "day=10", "10");
		ColumnParameters day11 = new ColumnParameters("day11", "Day 11", "day=11", "11");
		ColumnParameters day12 = new ColumnParameters("day12", "Day 12", "day=12", "12");
		ColumnParameters day13 = new ColumnParameters("day13", "Day 13", "day=13", "13");
		ColumnParameters day14 = new ColumnParameters("day14", "Day 14", "day=14", "14");
		ColumnParameters day15 = new ColumnParameters("day15", "Day 15", "day=15", "15");
		ColumnParameters day16 = new ColumnParameters("day16", "Day 16", "day=16", "16");
		ColumnParameters day17 = new ColumnParameters("day17", "Day 17", "day=17", "17");
		ColumnParameters day18 = new ColumnParameters("day18", "Day 18", "day=18", "18");
		ColumnParameters day19 = new ColumnParameters("day19", "Day 19", "day=19", "19");
		ColumnParameters day20 = new ColumnParameters("day20", "Day 20", "day=20", "20");
		ColumnParameters day21 = new ColumnParameters("day21", "Day 21", "day=21", "21");
		ColumnParameters day22 = new ColumnParameters("day22", "Day 22", "day=22", "22");
		ColumnParameters day23 = new ColumnParameters("day23", "Day 23", "day=23", "23");
		ColumnParameters day24 = new ColumnParameters("day24", "Day 24", "day=24", "24");
		ColumnParameters day25 = new ColumnParameters("day25", "Day 25", "day=25", "25");
		ColumnParameters day26 = new ColumnParameters("day26", "Day 26", "day=26", "26");
		ColumnParameters day27 = new ColumnParameters("day27", "Day 27", "day=27", "27");
		ColumnParameters day28 = new ColumnParameters("day28", "Day 28", "day=28", "28");
		ColumnParameters day29 = new ColumnParameters("day29", "Day 29", "day=29", "29");
		ColumnParameters day30 = new ColumnParameters("day30", "Day 30", "day=30", "30");
		ColumnParameters day31 = new ColumnParameters("day31", "Day 31", "day=31", "31");
		ColumnParameters dayTotal = new ColumnParameters("dayTotal", "Total", "", "32");
		return Arrays.asList(day1, day2, day3, day4, day5, day6, day7, day8, day9, day10, day11, day12, day13, day14, day15,
				day16, day17, day18, day19, day20, day21, day22, day23, day24, day25, day26, day27, day28, day29, day30, day31,
				dayTotal);
	}
	
	public static List<ColumnParameters> getGeneralOutPatientFilters() {
		
		ColumnParameters under5MaleNew = new ColumnParameters("under5MaleNew", "below 5 and new", "age=<5|gender=M", "01");
		ColumnParameters under5MaleRevisit = new ColumnParameters("over5MaleRevisit", "below 5 and revisit",
		        "state=RVT|age=<5|gender=M", "02");
		ColumnParameters under5FemaleNew = new ColumnParameters("under5FemaleNew", "below 5 and new",
		        "state=NEW|age=<5|gender=F", "03");
		ColumnParameters under5FemaleRevisit = new ColumnParameters("over5FemaleRevisit", "below 5 and revisit",
		        "state=RVT|age=<5|gender=F", "04");
		ColumnParameters over5MaleNew = new ColumnParameters("over5MaleNew", "above 5 and new", "state=NEW|age=5+|gender=M",
		        "05");
		ColumnParameters over5MaleRevisit = new ColumnParameters("over5MaleRevisit", "above 5 and revisit",
		        "state=RVT|age=5+|gender=M", "06");
		ColumnParameters over5FemaleNew = new ColumnParameters("over5MaleNew", "above 5 and new",
		        "state=NEW|age=5+|gender=F", "07");
		ColumnParameters over5FemaleRevisit = new ColumnParameters("over5MaleRevisit", "above 5 and revisit",
		        "state=RVT|age=5+|gender=F", "08");
		ColumnParameters over60New = new ColumnParameters("over60New", "above 60 and new", "state=NEW|age=60+", "09");
		ColumnParameters over60Revisit = new ColumnParameters("over60Revisit", "above 60 and revisit", "state=RVT|age=60+",
		        "10");
		
		return Arrays.asList(under5MaleNew, under5MaleRevisit, under5FemaleNew, under5FemaleRevisit, over5MaleNew,
		    over5MaleRevisit, over5FemaleNew, over5FemaleRevisit, over5MaleRevisit, over60New, over60Revisit);
		
	}
	
	public static List<ColumnParameters> getSpecialClinicPatientFilters() {
		
		ColumnParameters entNew = new ColumnParameters("ENTNew", "ENT Clinic", "state=NEW|clinic=ENT", "01");
		ColumnParameters entRevisit = new ColumnParameters("ENTRevisit", "ENT Clinic", "state=RVT|clinic=ENT", "02");
		
		ColumnParameters eyeClinicNew = new ColumnParameters("eyeClinicNew", "Eye Clinic", "state=NEW|clinic=EYE", "03");
		ColumnParameters eyeClinicRevisit = new ColumnParameters("eyeClinicRevisit", "Eye Clinic", "state=RVT|clinic=EYE",
		        "04");
		
		ColumnParameters tbAndLeprosyNew = new ColumnParameters("tbAndLeprosyNew", "TB and Leprosy", "state=NEW|clinic=TBL",
		        "05");
		ColumnParameters tbAndLeprosyRevisit = new ColumnParameters("tbAndLeprosyRevisit", "TB and Leprosy",
		        "state=RVT|clinic=TBL", "06");
		
		ColumnParameters sexuallyTransmittedNew = new ColumnParameters("sexuallyTransmittedNew",
		        "Sexually Transmitted infection", "state=NEW|clinic=STI", "07");
		ColumnParameters sexuallyTransmittedRevisit = new ColumnParameters("sexuallyTransmittedRevisit",
		        "Sexually Transmitted infection", "state=RVT|clinic=STI", "08");
		
		ColumnParameters cccNew = new ColumnParameters("cccNew", "Comprehensive Care Clinic (CCC)", "state=NEW|clinic=CCC",
		        "09");
		ColumnParameters cccRevisit = new ColumnParameters("cccRevisit", "Comprehensive Care Clinic (CCC)",
		        "state=RVT|clinic=CCC", "10");
		
		ColumnParameters psychiatryNew = new ColumnParameters("psychiatryNew", "Psychiatry", "state=NEW|clinic=PSY", "11");
		ColumnParameters psychiatryRevisit = new ColumnParameters("psychiatryRevisit", "Psychiatry", "state=RVT|clinic=PSY",
		        "12");
		
		ColumnParameters orthopaedicNew = new ColumnParameters("orthopaedicNew", "Orthopaedic Clinic",
		        "state=NEW|clinic=ORT", "13");
		ColumnParameters orthopaedicRevisit = new ColumnParameters("orthopaedicRevisit", "Orthopaedic Clinic",
		        "state=RVT|clinic=ORT", "14");
		
		ColumnParameters occupationalTherapyNew = new ColumnParameters("occupationalTherapyNew",
		        "Occupational Therapy Clinic", "state=NEW|clinc=OCP", "15");
		ColumnParameters occupationalTherapyRevisit = new ColumnParameters("occupationalTherapyRevisit",
		        "Occupational Therapy Clinic", "state=RVT|clinic=OCP", "16");
		
		ColumnParameters physiotherapyNew = new ColumnParameters("physiotherapyNew", "Physiotherapy Clinic",
		        "state=NEW|clinic=PHYS", "17");
		ColumnParameters physiotherapyRevisit = new ColumnParameters("physiotherapyRevisit", "Physiotherapy Clinic",
		        "state=RVT|clinic=PHYS", "18");
		
		ColumnParameters medicalClinicsNew = new ColumnParameters("medicalClinicsNew", "Medical Clinics",
		        "state=NEW|clinic=MC", "19");
		ColumnParameters medicalClinicsRevisit = new ColumnParameters("medicalClinicsRevisit", "Medical Clinics",
		        "state=RVT|clinic=MC", "20");
		
		ColumnParameters surgicalClinicsNew = new ColumnParameters("surgicalClinicsNew", "Surgical Clinics",
		        "state=NEW|clinic=SC", "21");
		ColumnParameters surgicalClinicsRevisit = new ColumnParameters("surgicalClinicsRevisit", "Surgical Clinics",
		        "state=RVT|clinic=SC", "22");
		
		ColumnParameters paediatricsNew = new ColumnParameters("paediatricsNew", "Paediatrics", "state=NEW|clinic=PAED",
		        "23");
		ColumnParameters paediatricsRevisit = new ColumnParameters("paediatricsRevisit", "Paediatrics",
		        "state=RVT|clinic=PAED", "24");
		
		ColumnParameters obstetricsGynaecologyNew = new ColumnParameters("obstetricsGynaecologyNew",
		        "Obstetrics/Gynaecology", "state=NEW|clinic=OG", "25");
		ColumnParameters obstetricsGynaecologyRevisit = new ColumnParameters("obstetricsGynaecologyRevisit",
		        "Obstetrics/Gynaecology", "state=RVT|clinic=OG", "26");
		
		return Arrays.asList(entNew, entRevisit, entNew, eyeClinicNew, eyeClinicRevisit, tbAndLeprosyNew,
		    tbAndLeprosyRevisit, sexuallyTransmittedNew, sexuallyTransmittedRevisit, cccNew, cccRevisit, psychiatryNew,
		    psychiatryRevisit, orthopaedicNew, orthopaedicRevisit, occupationalTherapyNew, occupationalTherapyRevisit,
		    psychiatryNew, physiotherapyNew, physiotherapyRevisit, medicalClinicsNew, medicalClinicsRevisit,
		    surgicalClinicsNew, surgicalClinicsRevisit, paediatricsNew, paediatricsRevisit, obstetricsGynaecologyNew,
		    obstetricsGynaecologyRevisit);
		
	}
	
	public static List<ColumnParameters> getAgeUnderOver5Columns() {
		ColumnParameters malariaTotalUnder5 = new ColumnParameters("malariaTotalUnder5", "Total Exam - under 5", "age=<5",
		        "01");
		ColumnParameters malariaTotalOver5 = new ColumnParameters("malariaTotalOver5", "Total Exam - over 5", "age=>5", "02");
		return Arrays.asList(malariaTotalUnder5, malariaTotalOver5);
	}
	
	public static List<ColumnParameters> getAdultChildrenWithColumns() {
		ColumnParameters day1Male = new ColumnParameters("day1Male", "Day 1 Male", "day=1|gender=M", "01");
		ColumnParameters day2Male = new ColumnParameters("day2Male", "Day 2 male", "day=2|gender=M", "02");
		ColumnParameters day3Male = new ColumnParameters("day3Male", "Day 3 male", "day=3|gender=M", "03");
		ColumnParameters day4Male = new ColumnParameters("day4Male", "Day 4 male", "day=4|gender=M", "04");
		ColumnParameters day5Male = new ColumnParameters("day5Male", "Day 5 male", "day=5|gender=M", "05");
		ColumnParameters day6Male = new ColumnParameters("day6Male", "Day 6 male", "day=6|gender=M", "06");
		ColumnParameters day7Male = new ColumnParameters("day7Male", "Day 7 male", "day=7|gender=M", "07");
		ColumnParameters day8Male = new ColumnParameters("day8Male", "Day 8 male", "day=8|gender=M", "08");
		ColumnParameters day9Male = new ColumnParameters("day9Male", "Day 9 male", "day=9|gender=M", "09");
		ColumnParameters day10Male = new ColumnParameters("day10Male", "Day 10 male", "day=10|gender=M", "10");
		ColumnParameters day11Male = new ColumnParameters("day11Male", "Day 11 male", "day=11|gender=M", "11");
		ColumnParameters day12Male = new ColumnParameters("day12Male", "Day 12 male", "day=12|gender=M", "12");
		ColumnParameters day13Male = new ColumnParameters("day13Male", "Day 13 male", "day=13|gender=M", "13");
		ColumnParameters day14Male = new ColumnParameters("day14Male", "Day 14 male", "day=14|gender=M", "14");
		ColumnParameters day15Male = new ColumnParameters("day15Male", "Day 15 male", "day=15|gender=M", "15");
		ColumnParameters day16Male = new ColumnParameters("day16Male", "Day 16 male", "day=16|gender=M", "16");
		ColumnParameters day17Male = new ColumnParameters("day17Male", "Day 17 male", "day=17|gender=M", "17");
		ColumnParameters day18Male = new ColumnParameters("day18Male", "Day 18 male", "day=18|gender=M", "18");
		ColumnParameters day19Male = new ColumnParameters("day19Male", "Day 19 male", "day=19|gender=M", "19");
		ColumnParameters day20Male = new ColumnParameters("day20Male", "Day 20 male", "day=20|gender=M", "20");
		ColumnParameters day21Male = new ColumnParameters("day21Male", "Day 21 male", "day=21|gender=M", "21");
		ColumnParameters day22Male = new ColumnParameters("day22Male", "Day 22 male", "day=22|gender=M", "22");
		ColumnParameters day23Male = new ColumnParameters("day23Male", "Day 23 male", "day=23|gender=M", "23");
		ColumnParameters day24Male = new ColumnParameters("day24Male", "Day 24 male", "day=24|gender=M", "24");
		ColumnParameters day25Male = new ColumnParameters("day25Male", "Day 25 male", "day=25|gender=M", "25");
		ColumnParameters day26Male = new ColumnParameters("day26Male", "Day 26 male", "day=26|gender=M", "26");
		ColumnParameters day27Male = new ColumnParameters("day27Male", "Day 27 male", "day=27|gender=M", "27");
		ColumnParameters day28Male = new ColumnParameters("day28Male", "Day 28 male", "day=28|gender=M", "28");
		ColumnParameters day29Male = new ColumnParameters("day29Male", "Day 29 male", "day=29|gender=M", "29");
		ColumnParameters day30Male = new ColumnParameters("day30Male", "Day 30 male", "day=30|gender=M", "30");
		ColumnParameters day31Male = new ColumnParameters("day31Male", "Day 31 male", "day=31|gender=M", "31");
		ColumnParameters dayTotalMale = new ColumnParameters("dayTotalMale", "Days total male", "gender=M", "32");
		
		ColumnParameters day1Female = new ColumnParameters("day1Female", "Day 1 Female", "day=1|gender=F", "33");
		ColumnParameters day2Female = new ColumnParameters("day2Female", "Day 2 female", "day=2|gender=F", "34");
		ColumnParameters day3Female = new ColumnParameters("day3Female", "Day 3 female", "day=3|gender=F", "35");
		ColumnParameters day4Female = new ColumnParameters("day4Female", "Day 4 female", "day=4|gender=F", "36");
		ColumnParameters day5Female = new ColumnParameters("day5Female", "Day 5 female", "day=5|gender=F", "37");
		ColumnParameters day6Female = new ColumnParameters("day6Female", "Day 6 female", "day=6|gender=F", "38");
		ColumnParameters day7Female = new ColumnParameters("day7Female", "Day 7 female", "day=7|gender=F", "39");
		ColumnParameters day8Female = new ColumnParameters("day8Female", "Day 8 female", "day=8|gender=F", "40");
		ColumnParameters day9Female = new ColumnParameters("day9Female", "Day 9 female", "day=9|gender=F", "41");
		ColumnParameters day10Female = new ColumnParameters("day10Female", "Day 10 female", "day=10|gender=F", "42");
		ColumnParameters day11Female = new ColumnParameters("day11Female", "Day 11 female", "day=11|gender=F", "43");
		ColumnParameters day12Female = new ColumnParameters("day12Female", "Day 12 female", "day=12|gender=F", "44");
		ColumnParameters day13Female = new ColumnParameters("day13Female", "Day 13 female", "day=13|gender=F", "45");
		ColumnParameters day14Female = new ColumnParameters("day14Female", "Day 14 female", "day=14|gender=F", "46");
		ColumnParameters day15Female = new ColumnParameters("day15Female", "Day 15 female", "day=15|gender=F", "47");
		ColumnParameters day16Female = new ColumnParameters("day16Female", "Day 16 female", "day=16|gender=F", "48");
		ColumnParameters day17Female = new ColumnParameters("day17Female", "Day 17 female", "day=17|gender=F", "49");
		ColumnParameters day18Female = new ColumnParameters("day18Female", "Day 18 female", "day=18|gender=F", "50");
		ColumnParameters day19Female = new ColumnParameters("day19Female", "Day 19 female", "day=19|gender=F", "51");
		ColumnParameters day20Female = new ColumnParameters("day20Female", "Day 20 female", "day=20|gender=F", "52");
		ColumnParameters day21Female = new ColumnParameters("day21Female", "Day 21 female", "day=21|gender=F", "53");
		ColumnParameters day22Female = new ColumnParameters("day22Female", "Day 22 female", "day=22|gender=F", "54");
		ColumnParameters day23Female = new ColumnParameters("day23Female", "Day 23 female", "day=23|gender=F", "55");
		ColumnParameters day24Female = new ColumnParameters("day24Female", "Day 24 female", "day=24|gender=F", "56");
		ColumnParameters day25Female = new ColumnParameters("day25Female", "Day 25 female", "day=25|gender=F", "57");
		ColumnParameters day26Female = new ColumnParameters("day26Female", "Day 26 female", "day=26|gender=F", "58");
		ColumnParameters day27Female = new ColumnParameters("day27Female", "Day 27 female", "day=27|gender=F", "59");
		ColumnParameters day28Female = new ColumnParameters("day28Female", "Day 28 female", "day=28|gender=F", "60");
		ColumnParameters day29Female = new ColumnParameters("day29Female", "Day 29 female", "day=29|gender=F", "61");
		ColumnParameters day30Female = new ColumnParameters("day30Female", "Day 30 female", "day=30|gender=F", "62");
		ColumnParameters day31Female = new ColumnParameters("day31Female", "Day 31 female", "day=31|gender=F", "63");
		ColumnParameters dayTotalFemale = new ColumnParameters("dayTotalFemale", "Day total female", "gender=F", "64");
		
		return Arrays.asList(day1Male, day2Male, day3Male, day4Male, day5Male, day6Male, day7Male, day8Male, day9Male,
		    day10Male, day11Male, day12Male, day13Male, day14Male, day15Male, day16Male, day17Male, day18Male, day19Male,
		    day20Male, day21Male, day22Male, day22Male, day23Male, day24Male, day25Male, day26Male, day27Male, day28Male,
		    day29Male, day30Male, day31Male, day1Female, day2Female, day3Female, day4Female, day5Female, day6Female,
		    day7Female, day8Female, day9Female, day10Female, day11Female, day12Female, day13Female, day14Female,
		    day15Female, day16Female, day17Female, day18Female, day19Female, day20Female, day21Female, day22Female,
		    day23Female, day24Female, day25Female, day26Female, day27Female, day28Female, day29Female, day30Female,
		    day31Female, dayTotalMale, dayTotalFemale);
	}
	
}
