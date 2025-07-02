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
import org.openmrs.Concept;
import org.openmrs.api.context.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiagnosisConcepts {
	public static final class _DiagnosisConcepts {
		//MOH705A

		//Diarrhoea with no dehydration
		public static final Integer DWND_1 = 2019918;
		public static final Integer DWND_2 = 139753;

		//Gastroenteritis
		public static final Integer GAS_1 = 2006748;
		public static final Integer GAS_2 = 2007422;
		public static final Integer GAS_3 = 2007423;
		public static final Integer GAS_4 = 147325;
		public static final Integer GAS_5 = 2009734;
		public static final Integer GAS_6 = 2009806;
		public static final Integer GAS_7 = 2009842;
		public static final Integer GAS_8 = 2009843;
		public static final Integer GAS_9 = 2009844;
		public static final Integer GAS_10 = 2009845;
		public static final Integer GAS_11 = 2009847;
		public static final Integer GAS_12 = 2012736;
		public static final Integer GAS_13 = 2012739;
		public static final Integer GAS_14 = 2012744;
		public static final Integer GAS_15 = 2012747;
		public static final Integer GAS_16 = 2012751;
		public static final Integer GAS_17 = 2012756;
		public static final Integer GAS_18 = 2018645;
		public static final Integer GAS_19 = 2018690;
		public static final Integer GAS_20 = 2019691;
		public static final Integer GAS_21 = 2019731;
		public static final Integer GAS_22 = 2019732;
		public static final Integer GAS_23 = 2026983;
		public static final Integer GAS_24 = 2023096;
		public static final Integer GAS_25 = 2023097;
		public static final Integer GAS_26 = 2025206;

		//Severe pneumonia
		public static final Integer SP_1 = 2005950;
		public static final Integer SP_2 = 2005951;
		public static final Integer SP_3 = 112744;
		public static final Integer SP_4 = 114106;
		public static final Integer SP_5 = 155715;
		public static final Integer SP_6 = 2005950;
		public static final Integer SP_7 = 2005952;
		public static final Integer SP_8 = 150584;
		public static final Integer SP_9 = 2005970;
		public static final Integer SP_10 = 143772;

		//Upper Respiratory Tract Infections
		public static final Integer URC_1 = 2011131;
		public static final Integer URC_2 = 2011133;
		public static final Integer URC_3 = 2011134;
		public static final Integer URC_4 = 2011135;
		public static final Integer URC_5 = 2011136;
		public static final Integer URC_6 = 2017557;

		//Lower Respiratory Tract Infections
		public static final Integer LTI_1 = 2005873;
		public static final Integer LTI_2 = 2005875;
		public static final Integer LTI_3 = 2017815;

		//Presumed Tuberculosis
		public static final Integer TCP_1 = 2002912;
		public static final Integer TCP_2 = 2002913;

		
		//Other Menignitis A
		public static final Integer OMC_1= 111967;
		public static final Integer OMC_2= 125820;
		public static final Integer OMC_3= 125958;
		public static final Integer OMC_4= 130095;
		public static final Integer OMC_5= 134359;
		public static final Integer OMC_6= 2002523;
		public static final Integer OMC_7= 141201;
		public static final Integer OMC_8= 2002524;
		public static final Integer OMC_9= 2002525;
		public static final Integer OMC_10= 2002526;
		public static final Integer OMC_11= 135474;
		public static final Integer OMC_12= 2002540;
		public static final Integer OMC_13= 121255;
		public static final Integer OMC_14= 2002541;
		public static final Integer OMC_15= 2002542;
		public static final Integer OMC_16= 2002543;
		public static final Integer OMC_17= 139736;
		public static final Integer OMC_18= 2002544;
		public static final Integer OMC_19= 121211;
		public static final Integer OMC_20= 2002545;
		public static final Integer OMC_21= 2002546;
		public static final Integer OMC_22= 133667;
		public static final Integer OMC_23= 2002946;
		public static final Integer OMC_24= 138698;
		public static final Integer OMC_25= 152206;
		public static final Integer OMC_26= 146525;
		public static final Integer OMC_27= 144646;
		public static final Integer OMC_28= 1294;
		public static final Integer OMC_29= 2002660;
		public static final Integer OMC_30= 124076;
		public static final Integer OMC_31= 2002676;
		public static final Integer OMC_32= 2002733;
		public static final Integer OMC_33= 2002778;
		public static final Integer OMC_34= 2006553;
		public static final Integer OMC_35= 145722;
		public static final Integer OMC_36= 2006554;
		public static final Integer OMC_37= 114406;
		public static final Integer OMC_38= 2006557;
		public static final Integer OMC_39= 2006558;
		public static final Integer OMC_40= 2006559;
		public static final Integer OMC_41= 2006560;
		public static final Integer OMC_42= 2006624;
		public static final Integer OMC_43= 2009405;
		public static final Integer OMC_44= 2014610;

		//Poliomyelitis
		public static final Integer PMC_1= 2002520;
		public static final Integer PMC_2= 158786;
		// DS Variables (Diseases of Skin)
		public static final Integer DS_1 = 2009210;
		public static final Integer DS_2 = 2002802;
		public static final Integer DS_3 = 2004289;
		public static final Integer DS_4 = 150118;
		public static final Integer DS_5 = 2006707;
		public static final Integer DS_6 = 145568;
		public static final Integer DS_7 = 2007476;
		public static final Integer DS_8 = 2007477;
		public static final Integer DS_9 = 113112;
		public static final Integer DS_10 = 2007478;
		public static final Integer DS_11 = 2007479;
		public static final Integer DS_12 = 2007480;
		public static final Integer DS_13 = 2007493;
		public static final Integer DS_14 = 2007495;
		public static final Integer DS_15 = 2007496;
		public static final Integer DS_16 = 145350;
		public static final Integer DS_17 = 2007499;
		public static final Integer DS_18 = 2007500;
		public static final Integer DS_19 = 2007501;
		public static final Integer DS_20 = 2007502;
		public static final Integer DS_21 = 2007503;
		public static final Integer DS_22 = 2007504;
		public static final Integer DS_23 = 2007528;
		public static final Integer DS_24 = 2007526;
		public static final Integer DS_25 = 2007527;
		public static final Integer DS_26 = 2007524;
		public static final Integer DS_27 = 2007522;
		public static final Integer DS_28 = 2007470;
		public static final Integer DS_29 = 2007472;
		public static final Integer DS_30 = 2007473;
		public static final Integer DS_31 = 2007474;
		public static final Integer DS_32 = 2007475;
		public static final Integer DS_33 = 2008159;
		public static final Integer DS_34 = 2007969;
		public static final Integer DS_35 = 128249;
		public static final Integer DS_36 = 130088;
		public static final Integer DS_37 = 151939;
		public static final Integer DS_38 = 158725;
		public static final Integer DS_39 = 158585;
		public static final Integer DS_40 = 2007537;
		public static final Integer DS_41 = 2003135;
		public static final Integer DS_42 = 156716;
		public static final Integer DS_43 = 2004288;
		public static final Integer DS_44 = 2007554;
		public static final Integer DS_45 = 2007555;
		public static final Integer DS_46 = 145136;
		public static final Integer DS_47 = 2007556;
		public static final Integer DS_48 = 2007557;
		public static final Integer DS_49 = 123467;
		public static final Integer DS_50 = 2007559;
		public static final Integer DS_51 = 143503;
		public static final Integer DS_52 = 2007560;
		public static final Integer DS_53 = 2007561;
		public static final Integer DS_54 = 2007562;
		public static final Integer DS_55 = 2007563;
		public static final Integer DS_56 = 2007565;
		public static final Integer DS_57 = 2007566;
		public static final Integer DS_58 = 2007567;
		public static final Integer DS_59 = 2008151;
		public static final Integer DS_60 = 2008411;
		public static final Integer DS_61 = 2008413;
		public static final Integer DS_62 = 2008414;
		public static final Integer DS_63 = 2008415;
		public static final Integer DS_64 = 141056;
		public static final Integer DS_65 = 116195;
		public static final Integer DS_66 = 112476;
		public static final Integer DS_67 = 2004118;
		public static final Integer DS_68 = 2004119;
		public static final Integer DS_69 = 2004120;
		public static final Integer DS_70 = 141717;
		public static final Integer DS_71 = 2004121;
		public static final Integer DS_72 = 2004122;
		public static final Integer DS_73 = 2006368;
		public static final Integer DS_74 = 2007436;
		public static final Integer DS_75 = 152161;
		public static final Integer DS_76 = 141053;
		public static final Integer DS_77 = 2007569;
		// CHICKEN POX
		public static final Integer CPC_1 = 160435;
		public static final Integer CPC_2 = 892;
		public static final Integer CPC_3 = 2002945;
		public static final Integer CPC_4 = 2002946;
		public static final Integer CPC_5 = 2002947;
		public static final Integer CPC_6 = 2002948;
		public static final Integer CPC_7 = 2002949;
		public static final Integer CPC_8 = 2009385;

		// MEASLES
		public static final Integer MSC_1 = 2015560;
		public static final Integer MSC_2 = 134561;
		public static final Integer MSC_3 = 152209;
		public static final Integer MSC_4 = 129469;
		public static final Integer MSC_5 = 152206;
		public static final Integer MSC_6 = 115885;
		// HEPATITIS
		public static final Integer HPC_1 = 142964;
		public static final Integer HPC_2 = 149471;
		public static final Integer HPC_3 = 2002670;
		public static final Integer HPC_4 = 2002673;
		public static final Integer HPC_5 = 149743;
		public static final Integer HPC_6 = 2002675;
		public static final Integer HPC_7 = 152665;
		public static final Integer HPC_8 = 2002839;
		public static final Integer HPC_9 = 2002841;
		public static final Integer HPC_10 = 145131;
		public static final Integer HPC_11 = 2002843;
		public static final Integer HPC_12 = 2002844;
		public static final Integer HPC_13 = 2002846;
		public static final Integer HPC_14 = 2002850;
		public static final Integer HPC_15 = 145347;
		public static final Integer HPC_16 = 2002852;
		public static final Integer HPC_17 = 2002867;
		public static final Integer HPC_18 = 2002868;
		public static final Integer HPC_19 = 2002870;
		public static final Integer HPC_20 = 2002871;
		public static final Integer HPC_21 = 124412;
		public static final Integer HPC_22 = 115215;
		public static final Integer HPC_23 = 2007751;
		public static final Integer HPC_24 = 2007762;
		public static final Integer HPC_25 = 2007764;
		public static final Integer HPC_26 = 2009196;
		public static final Integer HPC_27 = 152080;
		public static final Integer HPC_28 = 2014359;
		public static final Integer HPC_29 = 2014362;
		public static final Integer HPC_30 = 2014316;
		public static final Integer HPC_31 = 2014319;
		public static final Integer HPC_32 = 2014321;
		public static final Integer HPC_33 = 2014325;
		public static final Integer HPC_34 = 2014327;

		//AMOEBIASIS
		public static final Integer AM_1 = 124;
		public static final Integer AM_2 = 122594;
		public static final Integer AM_3 = 149009;
		public static final Integer AM_4 = 2002426;
		public static final Integer AM_5 = 2002656;

		//Mumps
		public static final Integer MPC_1 = 133671;
		public static final Integer MPC_2 = 152238;
		public static final Integer MPC_3 = 2002518;
		public static final Integer MPC_4 = 2015563;

		//Typhoid fever
		public static final Integer TYC_1 = 141;
		public static final Integer TYC_2 = 2002395;
		public static final Integer TYC_3 = 2002396;
		public static final Integer TYC_4 = 130811;

		//Bilhazia
		public static final Integer BLC_1 = 117152;
		public static final Integer BLC_2 = 2002798;
		public static final Integer BLC_3 = 2002799;
		public static final Integer BLC_4 = 2002800;
		public static final Integer BLC_5 = 2002804;
		public static final Integer BLC_6 = 2002801;

		//Worms
		public static final Integer IWC_1 = 2002807;
		public static final Integer IWC_2 = 2002810;
		public static final Integer IWC_3 = 2002811;
		public static final Integer IWC_4 = 134042;
		public static final Integer IWC_5 = 2002808;
		public static final Integer IWC_6 = 2002809;

		//EYE INFECTIONS
		public static final Integer EC_1 = 2006660;
		public static final Integer EC_2 = 2006710;
		public static final Integer EC_3 = 119905;
		public static final Integer EC_4 = 2006804;
		public static final Integer EC_5 = 139460;
		public static final Integer EC_6 = 149898;
		public static final Integer EC_7 = 995;
		public static final Integer EC_8 = 2006805;
		public static final Integer EC_9 = 2006806;
		public static final Integer EC_10 = 140013;
		public static final Integer EC_11 = 2006807;
		public static final Integer EC_12 = 133804;
		public static final Integer EC_13 = 143593;
		public static final Integer EC_14 = 149827;
		public static final Integer EC_15 = 2006865;
		public static final Integer EC_16 = 2006866;
		public static final Integer EC_17 = 147222;
		public static final Integer EC_18 = 123233;
		public static final Integer EC_19 = 126671;
		public static final Integer EC_20 = 2006867;
		public static final Integer EC_21 = 2006868;
		public static final Integer EC_22 = 136298;
		public static final Integer EC_23 = 2008376;
		public static final Integer EC_24 = 2009403;
		public static final Integer EC_25 = 145667;
		public static final Integer EC_26 = 123123;
		public static final Integer EC_27 = 143595;
		public static final Integer EC_28 = 2002632;
		public static final Integer EC_29 = 2002633;
		public static final Integer EC_30 = 2002635;
		public static final Integer EC_31 = 2006959;
		public static final Integer EC_32 = 2006961;
		public static final Integer EC_33 = 2006971;
		public static final Integer EC_34 = 2006973;
		public static final Integer EC_35 = 2006974;
		public static final Integer EC_36 = 127090;
		public static final Integer EC_37 = 138700;
		public static final Integer EC_38 = 2002565;
		public static final Integer EC_39 = 138703;
		public static final Integer EC_40= 2002567;
		public static final Integer EC_41 = 2002568;
		public static final Integer EC_42 = 143596;
		public static final Integer EC_43 = 2002747;
		public static final Integer EC_44 = 155839;
		public static final Integer EC_45 = 2004256;
		public static final Integer EC_46 = 2006647;
		public static final Integer EC_47 = 150610;
		public static final Integer EC_48 = 2006652;
		public static final Integer EC_49 = 2006651;
		public static final Integer EC_50 = 2006653;
		public static final Integer EC_51 = 2006654;
		public static final Integer EC_52 = 2006662;
		public static final Integer EC_53 = 2006663;
	
		//Tonsililites
		public static final Integer TSC_1 = 149496;
		public static final Integer TSC_2 = 125815;
		public static final Integer TSC_3 = 2011095;
		public static final Integer TSC_4 = 2011096;
		public static final Integer TSC_5 = 2013724;
		public static final Integer TSC_6 = 2011120;
		public static final Integer TSC_7 = 138120;
		public static final Integer TSC_8 = 2011121;
		public static final Integer TSC_9 = 2011122;
		public static final Integer TSC_10 = 2011123;


		//UTIs
		public static final Integer UTI1 = 2007888;
		public static final Integer UTI2 = 156175;
		public static final Integer UTI3 = 2007889;
		public static final Integer UTI4 = 2007890;
		public static final Integer UTI5 = 2007891;
		public static final Integer UTI6 = 2007892;
		public static final Integer UTI7 = 159255;

		//MENTAL DISORDER
		public static final Integer MDC_1 = 113155;
		public static final Integer MDC_2 = 2004781;
		public static final Integer MDC_3 = 2004783;
		public static final Integer MDC_4 = 2004784;
		public static final Integer MDC_5 = 2004786;
		public static final Integer MDC_6 = 2004792;
		public static final Integer MDC_7 = 2004788;
		public static final Integer MDC_8 = 2004789;
		public static final Integer MDC_9 = 2004791;
		public static final Integer MDC_10 = 2004794;
		public static final Integer MDC_11 = 2004795;
		public static final Integer MDC_12 = 2004796;
		public static final Integer MDC_13 = 2004797;
		public static final Integer MDC_14 = 2004798;
		public static final Integer MDC_15 = 2004799;
		public static final Integer MDC_16 = 2004800;
		public static final Integer MDC_17 = 2004801;
		public static final Integer MDC_18 = 2004802;
		public static final Integer MDC_19 = 127132;
		public static final Integer MDC_20 = 2004806;
		public static final Integer MDC_21 = 2004808;
		public static final Integer MDC_22 = 2004809;
		public static final Integer MDC_23 = 2004810;
		public static final Integer MDC_24 = 2004811;
		public static final Integer MDC_25 = 2004812;
		public static final Integer MDC_26 = 2004813;
		public static final Integer MDC_27 = 2004814;
		public static final Integer MDC_28 = 2004815;
		public static final Integer MDC_29 = 2004815;
		public static final Integer MDC_30 = 2004816;
		public static final Integer MDC_31 = 2004817;
		public static final Integer MDC_32 = 2004818;
		public static final Integer MDC_33 = 2004819;
		public static final Integer MDC_34 = 2004820;
		public static final Integer MDC_35 = 2004821;
		public static final Integer MDC_36 = 2004822;
		public static final Integer MDC_37 = 2004823;
		public static final Integer MDC_38 = 113139;
		public static final Integer MDC_39 = 154937;
		public static final Integer MDC_40 = 2004825;
		public static final Integer MDC_41 = 2004824;
		public static final Integer MDC_42 = 2004826;
		public static final Integer MDC_43 = 2004827;
		public static final Integer MDC_44 = 2004828;
		public static final Integer MDC_45 = 2004829;
		public static final Integer MDC_46 = 2004830;
		public static final Integer MDC_47 = 2004803;
		public static final Integer MDC_48 = 2004804;
		public static final Integer MDC_49 = 2004805;
		public static final Integer MDC_50 = 2004807;
		public static final Integer MDC_51 = 2005361;
		public static final Integer MDC_52 = 119570;
		public static final Integer MDC_53 = 2005362;
		public static final Integer MDC_54 = 2005363;
		public static final Integer MDC_55 = 2005364;
		public static final Integer MDC_56 = 2005365;
		public static final Integer MDC_57 = 2005374;
		public static final Integer MDC_58 = 2005378;
		public static final Integer MDC_59 = 2005379;
		public static final Integer MDC_60 = 2005380;
		public static final Integer MDC_61 = 2005381;
		public static final Integer MDC_62 = 2005382;
		public static final Integer MDC_63 = 2005383;
		public static final Integer MDC_64 = 2005384;
		public static final Integer MDC_65 = 2005385;
		public static final Integer MDC_66 = 2005386;
		public static final Integer MDC_67 = 2005387;
		public static final Integer MDC_68 = 2005388;
		public static final Integer MDC_69 = 2005389;
		public static final Integer MDC_70 = 2005390;
		public static final Integer MDC_71 = 2005391;
		public static final Integer MDC_72 = 2005392;
		public static final Integer MDC_73 = 2005393;
		public static final Integer MDC_74 = 2005394;
		public static final Integer MDC_75 = 2005395;
		public static final Integer MDC_76 = 2005396;
		public static final Integer MDC_77 = 2005397;
		public static final Integer MDC_78 = 2005398;
		public static final Integer MDC_79 = 2005399;
		public static final Integer MDC_80 = 2005400;
		public static final Integer MDC_81 = 2005401;
		public static final Integer MDC_82 = 2005402;
		public static final Integer MDC_83 = 2005403;
		public static final Integer MDC_84 = 2005404;
		public static final Integer MDC_85 = 2005405;
		public static final Integer MDC_86 = 2005406;
		public static final Integer MDC_87 = 2005407;
		public static final Integer MDC_88 = 2005408;
		public static final Integer MDC_89 = 2005409;
		public static final Integer MDC_90 = 2005410;
		public static final Integer MDC_91 = 119706;
		public static final Integer MDC_92 = 2005411;
		public static final Integer MDC_93 = 2005412;
		public static final Integer MDC_94 = 2005413;
		public static final Integer MDC_95 = 2005414;
		public static final Integer MDC_96 = 2005415;
		public static final Integer MDC_97 = 2005416;
		public static final Integer MDC_98 = 2005417;
		public static final Integer MDC_99 = 2005418;
		public static final Integer MDC_100 = 2005419;
		public static final Integer MDC_101= 2005420;
		public static final Integer MDC_102= 2005421;
		public static final Integer MDC_103= 2005422;
		public static final Integer MDC_104= 2005423;
		public static final Integer MDC_105 = 127839;
		public static final Integer MDC_106= 2005424;
		public static final Integer MDC_107= 2005425;
		public static final Integer MDC_108= 2005426;
		public static final Integer MDC_109= 2005427;
		public static final Integer MDC_110= 2005428;
		public static final Integer MDC_111= 2005429;
		public static final Integer MDC_112= 2005430;
		public static final Integer MDC_113 = 2005431;
		public static final Integer MDC_114= 2005433;
		public static final Integer MDC_115= 118779;
		public static final Integer MDC_116= 134079;
		public static final Integer MDC_117= 2005434;
		public static final Integer MDC_118= 2005435;
		public static final Integer MDC_119= 2005439;
		public static final Integer MDC_120= 2005444;
		public static final Integer MDC_121= 139545;
		public static final Integer MDC_122= 130966;
		public static final Integer MDC_123= 149209;
		public static final Integer MDC_124= 2004831;
		public static final Integer MDC_125= 2004832;
		public static final Integer MDC_126= 2004833;
		public static final Integer MDC_127= 2004833;
		public static final Integer MDC_128= 2004834;
		public static final Integer MDC_129= 2004835;
		public static final Integer MDC_130= 2004835;
		public static final Integer MDC_131= 113881;
		public static final Integer MDC_132= 2004872;

		//Dental disorders
		public static final Integer DDC_1 = 2006483;
		public static final Integer DDC_2 = 2006479;
		public static final Integer DDC_3 = 2006480;
		public static final Integer DDC_4 = 2006481;
		public static final Integer DDC_5 = 2006482;
		public static final Integer DDC_6 = 2020274;
		public static final Integer DDC_7 = 2020282;
		public static final Integer DDC_8 = 2020285;
		public static final Integer DDC_9 = 2020286;
		public static final Integer DDC_10 = 2020332;
		public static final Integer DDC_11 = 2009707;
		public static final Integer DDC_12 = 2012373;
		public static final Integer DDC_13 = 2012448;
		public static final Integer DDC_14 = 2012450;
		public static final Integer DDC_15 = 125015;
		public static final Integer DDC_16 = 2006414;
		public static final Integer DDC_17 = 141388;
		public static final Integer DDC_18 = 137758;
		public static final Integer DDC_19 = 119183;
		public static final Integer DDC_20 = 2006419;
		public static final Integer DDC_21 = 118490;
		public static final Integer DDC_22 = 150652;
		public static final Integer DDC_23 = 118548;
		public static final Integer DDC_24 = 114307;
		public static final Integer DDC_25 = 2006424;
		public static final Integer DDC_26 = 2006425;
		public static final Integer DDC_27 = 2006426;
		public static final Integer DDC_28 = 2006436;
		public static final Integer DDC_29 = 2006437;
		public static final Integer DDC_30 = 2006438;
		public static final Integer DDC_31 = 2006438;
		public static final Integer DDC_32 = 2006439;
		public static final Integer DDC_33 = 2006440;
		public static final Integer DDC_34 = 2020284;
		public static final Integer DDC_35 = 2020327;
		public static final Integer DDC_36 = 2020329;
		public static final Integer DDC_37 = 2012428;
		public static final Integer DDC_38 = 2012432;
		public static final Integer DDC_39 = 2012435;
		public static final Integer DDC_40 = 2012437;
		public static final Integer DDC_41 = 120292;
		public static final Integer DDC_42 = 2006408;
		public static final Integer DDC_43 = 2006411;
		public static final Integer DDC_44 = 112347;
		public static final Integer DDC_45 = 2006415;
		public static final Integer DDC_46 = 2006416;
		public static final Integer DDC_47 = 2006417;
		public static final Integer DDC_48 = 2006418;
		public static final Integer DDC_49 = 2006423;
		public static final Integer DDC_50 = 2006470;

		//ROAD TRAFFIC INJURIES
		public static final Integer RTC_1 = 2011801;
		public static final Integer RTC_2 = 2012092;
		public static final Integer RTC_3 = 2011800;
		public static final Integer RTC_4 = 2011801;
		public static final Integer RTC_5 = 2011897;
		public static final Integer RTC_6 = 2012091;
		public static final Integer RTC_7 = 2012092;
		public static final Integer RTC_8 = 2012094;
		public static final Integer RTC_9 = 2012095;
		public static final Integer RTC_10 = 2012097;
		public static final Integer RTC_11 = 2012098;
		public static final Integer RTC_12 = 2012100;
		public static final Integer RTC_13 = 2012101;
		public static final Integer RTC_14 = 2012103;
		public static final Integer RTC_15 = 2012104;
		public static final Integer RTC_16 = 2012105;
		public static final Integer RTC_17 = 2012106;
		public static final Integer RTC_18 = 2012108;
		public static final Integer RTC_19 = 2012110;
		public static final Integer RTC_20 = 2012113;
		public static final Integer RTC_21 = 2012115;
		public static final Integer RTC_22 = 2012116;
		public static final Integer RTC_23 = 2012118;
		//DEATH DUE TO ROAD TRAFFIC INJURIES
		public static final Integer DRTC_1 = 2011801;
		public static final Integer DRTC_2 = 2012092;
		public static final Integer DRTC_3 = 2011800;
		public static final Integer DRTC_4 = 2011801;
		public static final Integer DRTC_5 = 2011897;
		public static final Integer DRTC_6 = 2012091;
		public static final Integer DRTC_7 = 2012092;
		public static final Integer DRTC_8 = 2012094;
		public static final Integer DRTC_9 = 2012095;
		public static final Integer DRTC_10 = 2012097;
		public static final Integer DRTC_11 = 2012098;
		public static final Integer DRTC_12= 2012100;
		public static final Integer DRTC_13 = 2012101;
		public static final Integer DRTC_14 = 2012103;
		public static final Integer DRTC_15 = 2012104;
		public static final Integer DRTC_16 = 2012105;
		public static final Integer DRTC_17= 2012106;
		public static final Integer DRTC_18= 2012108;
		public static final Integer DRTC_19= 2012110;
		public static final Integer DRTC_20= 2012113;
		public static final Integer DRTC_21= 2012115;
		public static final Integer DRTC_22= 2012116;
		public static final Integer DRTC_23= 2012118;
		//VIOLENCE RELATED INJURIES
		public static final Integer VRC_1 = 2012029;
		public static final Integer VRC_2 = 2012033;
		public static final Integer VRC_3 = 2012249;
		public static final Integer VRC_4 = 2012040;
		public static final Integer VRC_5 = 2012041;
		public static final Integer VRC_6 = 2012042;
		public static final Integer VRC_7 = 2012044;
		//OTHER INJURIES
		public static final Integer OIC_1 = 2020592;
		public static final Integer OIC_2 = 2020608;
		public static final Integer OIC_3 = 2020629;
		public static final Integer OIC_4 = 2020648;
		public static final Integer OIC_5 = 2020658;
		public static final Integer OIC_6 = 2011900;
		public static final Integer OIC_7 = 2011902;
		public static final Integer OIC_8 = 2011906;
		public static final Integer OIC_9 = 2011908;
		public static final Integer OIC_10 = 2011912;
		public static final Integer OIC_11 = 2011914;
		public static final Integer OIC_12 = 2011915;
		//SEXUAL VIOLENCE
		public static final Integer SAC_1 = 2022452;
		public static final Integer SAC_2 = 2022453;
		public static final Integer SAC_4 = 2022455;
		public static final Integer SAC_5 = 2022456;
		//BURNS
		public static final Integer BC_1 = 2020933;
		public static final Integer BC_2 = 2020936;
		public static final Integer BC_3 = 2020937;
		public static final Integer BC_4 = 2020941;
		public static final Integer BC_5 = 2020946;
		public static final Integer BC_6 = 2020949;
		public static final Integer BC_7 = 2020951;
		public static final Integer BC_8 = 2020954;
		public static final Integer BC_9 = 2020958;
		public static final Integer BC_10 = 2020960;
		public static final Integer BC_11 = 2020964;
		public static final Integer BC_12 = 2020966;
		public static final Integer BC_13 = 2020968;
		public static final Integer BC_14 = 2020972;
		public static final Integer BC_15 = 2020976;
		public static final Integer BC_16 = 2020980;
		public static final Integer BC_17 = 2020984;
		public static final Integer BC_18 = 2020987;
		public static final Integer BC_19 = 2020990;
		public static final Integer BC_20 = 2020993;
		public static final Integer BC_21 = 2020995;
		public static final Integer BC_22 = 2020998;
		public static final Integer BC_23 = 2020999;
		public static final Integer BC_24 = 2021001;
		public static final Integer BC_25 = 2021004;
		public static final Integer BC_26 = 2021006;
		public static final Integer BC_27 = 2021008;
		public static final Integer BC_28 = 2021009;
		public static final Integer BC_29 = 2021014;
		public static final Integer BC_30 = 2021016;
		public static final Integer BC_31 = 2021019;
		public static final Integer BC_32 = 2021021;
		public static final Integer BC_33 = 2021024;
		public static final Integer BC_34 = 2021026;
		public static final Integer BC_35 = 2021029;
		public static final Integer BC_36 = 2021031;
		public static final Integer BC_37 = 2021033;
		public static final Integer BC_38 = 2021034;
		public static final Integer BC_39 = 2021040;
		public static final Integer BC_40 = 2021047;
		public static final Integer BC_41 = 2021050;
		public static final Integer BC_42 = 2021052;
		public static final Integer BC_43 = 2021058;
		public static final Integer BC_44= 2021060;
		public static final Integer BC_45 = 2021063;
		public static final Integer BC_46 = 2021064;
		public static final Integer BC_47 = 2021066;
		public static final Integer BC_48 = 2021069;
		public static final Integer BC_49 = 2021071;
		public static final Integer BC_50 = 2021074;
		public static final Integer BC_51 = 2021077;
		public static final Integer BC_52 = 2021079;
		public static final Integer BC_53 = 2021082;
		public static final Integer BC_54 = 2021083;
		public static final Integer BC_55 = 2021085;
		public static final Integer BC_56 = 2021087;
		public static final Integer BC_57 = 2021089;
		public static final Integer BC_58 = 2021092;
		public static final Integer BC_59 = 2021094;
		public static final Integer BC_60 = 2021096;
		public static final Integer BC_61 = 2021098;
		public static final Integer BC_62 = 146703;
		public static final Integer BC_63 = 2021100;
		public static final Integer BC_64 = 2021104;
		public static final Integer BC_65 = 2021105;
		public static final Integer BC_66 = 2021106;
		public static final Integer BC_67 = 2021109;
		public static final Integer BC_68 = 2021111;
		public static final Integer BC_69 = 2021117;
		public static final Integer BC_70 = 2021115;
		public static final Integer BC_71 = 2021119;
		public static final Integer BC_72 = 2021124;
		public static final Integer BC_73 = 155348;
		public static final Integer BC_74 = 2021127;
		public static final Integer BC_75 = 2021130;
		public static final Integer BC_76 = 2021134;
		public static final Integer BC_77 = 2021136;
		public static final Integer BC_78 = 2021137;
		public static final Integer BC_79 = 2027334;
		public static final Integer BC_80 = 2027335;
		public static final Integer BC_81 = 2027347;
		public static final Integer BC_82 = 155357;
		public static final Integer BC_83 = 155358;
		public static final Integer BC_84 = 155359;
		public static final Integer BC_85 = 155360;
		public static final Integer BC_86 = 155361;
		public static final Integer BC_87 = 155362;
		public static final Integer BC_88 = 155363;
		public static final Integer BC_89 = 155364;
		public static final Integer BC_90 = 2027352;
		public static final Integer BC_91 = 2027355;
		public static final Integer BC_92 = 2027356;
		public static final Integer BC_93 = 2027357;
		public static final Integer BC_94 = 2027358;
		public static final Integer BC_95 = 2027360;
		public static final Integer BC_96 = 2027361;
		public static final Integer BC_97 = 2027362;
		public static final Integer BC_98 = 2027366;
		public static final Integer BC_99 = 2027367;
		public static final Integer BC_100 = 2027368;
		public static final Integer BC_101= 2027370;
		public static final Integer BC_102 = 2027373;
		public static final Integer BC_103 = 2027375;
		public static final Integer BC_104= 2027378;
		public static final Integer BC_105= 2027380;
		//snake bites
		public static final Integer SBC_1 = 2028125;
		public static final Integer SBC_2 = 2028128;
		public static final Integer SBC_3 = 2028130;
		public static final Integer SBC_4 = 2030392;
		public static final Integer SBC_5 = 2029073;
		public static final Integer SBC_6 = 2023720;
		//Dog bites
		public static final Integer DBC_1 = 160146;
		public static final Integer DBC_2 = 2013469;
		public static final Integer DBC_3 = 2015528;
		public static final Integer DBC_4 = 2015528;

		//Other bites refer to
		public static final Integer OBC_1 = 127902;
		public static final Integer OBC_2 = 2008423;
		public static final Integer OBC_3 = 2008425;
		public static final Integer OBC_4 = 2008427;
		public static final Integer OBC_5 = 2008428;
		public static final Integer OBC_6 = 2008430;
		public static final Integer OBC_7 = 2011999;


		//Diabetes
		public static final Integer DTC_1 = 142474;
		public static final Integer DTC_2 = 2004524;
		public static final Integer DTC_3 = 134721;
		public static final Integer DTC_4 = 2004525;
		public static final Integer DTC_5 = 2004526;
		public static final Integer DTC_6 = 2004527;
		public static final Integer DTC_7 = 2004326;
		public static final Integer DTC_8 = 2004327;
		public static final Integer DTC_9 = 2004329;
		public static final Integer DTC_10 = 2004330;
		public static final Integer DTC_11 = 2004331;
		public static final Integer DTC_12 = 2004332;
		public static final Integer DTC_13 = 2004335;
		public static final Integer DTC_14 = 2004350;
		public static final Integer DTC_15 = 2004395;
		public static final Integer DTC_16 = 119477;
		public static final Integer DTC_17 = 2008919;
		public static final Integer DTC_18 = 1449;
		public static final Integer DTC_19 = 2008921;
		public static final Integer DTC_20= 2008922;
		public static final Integer DTC_21= 2004325;


		//EPILEPSY
		public static final Integer EPC_1 = 2005689;
		public static final Integer EPC_2 = 2005690;
		public static final Integer EPC_3 = 2005691;
		public static final Integer EPC_4 = 2005693;
		public static final Integer EPC_5 = 2005694;
		public static final Integer EPC_6 = 2005695;
		public static final Integer EPC_7 = 2005696;
		public static final Integer EPC_8 = 2005697;
		public static final Integer EPC_9 = 2005698;
		public static final Integer EPC_10 = 2005699;
		public static final Integer EPC_11= 2005700;
		public static final Integer EPC_12 = 2005701;
		public static final Integer EPC_13 = 2005702;
		public static final Integer EPC_14 = 2005703;
		public static final Integer EPC_15 = 2005704;
		public static final Integer EPC_16 = 2005705;
		public static final Integer EPC_17 = 2005706;
		public static final Integer EPC_18 = 2005707;
		public static final Integer EPC_19 = 2005710;
		public static final Integer EPC_20 = 133555;
		public static final Integer EPC_21 = 2005724;
		public static final Integer EPC_22 = 128433;
		public static final Integer EPC_23 = 2005761;
		public static final Integer EPC_24 = 2005762;

		//Convulsive disorders

		public static final Integer OCDC_1 = 2013847;
		public static final Integer OCDC_2 = 2014318;
		public static final Integer OCDC_3 = 2014323;
		//Reumotic fever
		public static final Integer RF_1 = 113229;
		public static final Integer RF_2 = 113230;
		public static final Integer RF_3 = 2002814;
		public static final Integer RF_4 = 121843;


		//Rickets
		public static final Integer RKC_1 = 2005019;
		public static final Integer RKC_2 = 2004416;
		public static final Integer RKC_3 = 2004605;
		public static final Integer RKC_4 = 2004606;
		public static final Integer RKC_5 = 2004607;
		//cerebral mental Palsy
		public static final Integer CRPC_1 = 126258;
		public static final Integer CRPC_2 = 2006001;
		public static final Integer CRPC_3 = 2006002;
		public static final Integer CRPC_4 = 158847;
		public static final Integer CRPC_5 = 155483;
		public static final Integer CRPC_6 = 2006003;
		public static final Integer CRPC_7 = 2006004;
		public static final Integer CRPC_8 = 2006005;
		public static final Integer CRPC_9 = 141625;
		public static final Integer CRPC_10 = 152034;
		public static final Integer CRPC_11= 2006007;
		public static final Integer CRPC_12= 2006008;


		//Autism
		public static final Integer ATC_1 = 2004741;
		public static final Integer ATC_2 = 2004742;
		public static final Integer ATC_3 = 2004743;
		public static final Integer ATC_4= 2004745;
		public static final Integer ATC_5 = 2004747;
		public static final Integer ATC_6 = 2004748;
		public static final Integer ATC_7 = 2004749;
		public static final Integer ATC_8= 2004751;



		//Trypanosomiasis

		public static final Integer TRC_1 = 149241;
		public static final Integer TRC_2 = 2002658;
		public static final Integer TRC_3 = 2002660;
		public static final Integer TRC_4 = 2002662;
		public static final Integer TRC_5 = 2002664;
		public static final Integer TRC_6 = 137342;
		public static final Integer TRC_7 = 2002668;
		public static final Integer TRC_8= 2002669;
		public static final Integer TRC_9 = 2002671;
		public static final Integer TRC_10 = 2002672;

		//Yellow Fever
		public static final Integer YFC_1 = 86562;
		public static final Integer YFC_2 = 122759;

		//Rift valley Fever
		public static final Integer RVF_1 = 2014470;
		public static final Integer RVF_2 = 113217;

		//Chikungunya fever
		public static final Integer CKU_1 = 120742;
		public static final Integer CKU_2 = 2014713;

		//Dengue fever
		public static final Integer DENF_1 = 142592;
		public static final Integer DENF_2 = 2014378;
		public static final Integer DENF_3 = 2014380;
		public static final Integer DENF_4 = 2014381;
		public static final Integer DENF_5 = 2014383;
		public static final Integer DENF_6 = 2014387;

		//Leshmaniasis kalazaar
		public static final Integer KLC_1 = 116350;
		public static final Integer KLC_2 = 123084;
		public static final Integer KLC_3 = 148988;
		public static final Integer KLC_4 = 2002677;

		//Suspected CHILDHOOD CANCERS
		public static final Integer SCC_1 = 156509;
		public static final Integer SCC_2 = 139418;
		public static final Integer SCC_3 = 2002855;
		public static final Integer SCC_4 = 163849;
		public static final Integer SCC_5 = 2002860;
		public static final Integer SCC_6 = 163822;
		public static final Integer SCC_7 = 2002863;
		public static final Integer SCC_8 = 2002864;
		public static final Integer SCC_9 = 2002865;
		public static final Integer SCC_10 = 2002866;
		public static final Integer SCC_11 = 133313;
		public static final Integer SCC_12 = 2002869;
		public static final Integer SCC_13 = 134872;
		public static final Integer SCC_14 = 2002873;
		public static final Integer SCC_15 = 2002877;
		public static final Integer SCC_16 = 2002878;
		public static final Integer SCC_17 = 2002880;
		public static final Integer SCC_18 = 128877;
		public static final Integer SCC_19 = 2002891;
		public static final Integer SCC_20 = 121197;
		public static final Integer SCC_21 = 2002893;
		public static final Integer SCC_22 = 2002895;
		public static final Integer SCC_23 = 2002896;
		public static final Integer SCC_24 = 2002897;
		public static final Integer SCC_25 = 2002898;
		public static final Integer SCC_26 = 2002899;
		public static final Integer SCC_27 = 2002901;
		public static final Integer SCC_28 = 2002903;
		public static final Integer SCC_29 = 2002905;
		public static final Integer SCC_30 = 2002907;
		public static final Integer SCC_31 = 2002908;
		public static final Integer SCC_32 = 2002909;
		public static final Integer SCC_33 = 128850;
		public static final Integer SCC_34 = 2002910;
		public static final Integer SCC_35 = 2002911;
		public static final Integer SCC_36 = 164666;
		public static final Integer SCC_37 = 147812;
		public static final Integer SCC_38 = 147623;
		public static final Integer SCC_39 = 2002917;
		public static final Integer SCC_40 = 2002919;
		public static final Integer SCC_41 = 2003008;
		public static final Integer SCC_42 = 2003009;
		public static final Integer SCC_43 = 2003010;
		public static final Integer SCC_44 = 121978;
		public static final Integer SCC_45 = 2003011;
		public static final Integer SCC_46 = 2003012;
		public static final Integer SCC_47 = 155576;
		public static final Integer SCC_48 = 110627;
		public static final Integer SCC_49 = 2003013;
		public static final Integer SCC_50 = 113987;
		public static final Integer SCC_51 = 2003014;
		public static final Integer SCC_52 = 2003015;
		public static final Integer SCC_53 = 2003016;
		public static final Integer SCC_54 = 111622;
		public static final Integer SCC_55 = 2003131;
		public static final Integer SCC_56 = 134628;
		public static final Integer SCC_57 = 2003132;
		public static final Integer SCC_58 = 2003133;
		public static final Integer SCC_59 = 2003134;
		public static final Integer SCC_60 = 2003135;
		public static final Integer SCC_61 = 2003136;
		public static final Integer SCC_62 = 2003137;
		public static final Integer SCC_63 = 157673;
		public static final Integer SCC_64 = 2003138;
		public static final Integer SCC_65 = 2003139;
		public static final Integer SCC_66 = 2003140;
		public static final Integer SCC_67 = 2003141;
		public static final Integer SCC_68 = 113372;
		public static final Integer SCC_69 = 2003142;
		public static final Integer SCC_70 = 2003143;
		public static final Integer SCC_71 = 2003144;
		public static final Integer SCC_72 = 2003145;
		public static final Integer SCC_73 = 133578;
		public static final Integer SCC_74 = 2003146; //
		public static final Integer SCC_75 = 2003147;
		public static final Integer SCC_76 = 2003148;
		public static final Integer SCC_77 = 2003149;
		public static final Integer SCC_78 = 145279;
		public static final Integer SCC_79 = 2003150;
		public static final Integer SCC_80 = 2003151;
		public static final Integer SCC_81 = 2003152;
		public static final Integer SCC_82 = 2003153;
		public static final Integer SCC_83 = 2003154;
		public static final Integer SCC_84 = 2003155;
		public static final Integer SCC_85 = 2003156;
		public static final Integer SCC_86 = 2003157;
		public static final Integer SCC_87 = 2003158;
		public static final Integer SCC_88 = 2003159;
		public static final Integer SCC_89 = 2003160;
		public static final Integer SCC_90 = 2003161;
		public static final Integer SCC_91 = 2003162;
		public static final Integer SCC_92 = 2003163;
		public static final Integer SCC_93 = 2003164;
		public static final Integer SCC_94 = 2003483;
		public static final Integer SCC_95 = 2003484;
		public static final Integer SCC_96 = 2003485;
		public static final Integer SCC_97 = 2003486;
		public static final Integer SCC_98 = 2003488;
		public static final Integer SCC_99= 2003489;
		public static final Integer SCC_100= 2003490;
		public static final Integer SCC_101= 149650;
		public static final Integer SCC_102= 149651;
		public static final Integer SCC_103= 149649;
		public static final Integer SCC_104 = 2003493;
		public static final Integer SCC_105 = 2003495;
		public static final Integer SCC_106= 149665;
		public static final Integer SCC_107= 2003497;
		public static final Integer SCC_108= 149606;
		public static final Integer SCC_109= 133576;
		public static final Integer SCC_110= 2003502;
		public static final Integer SCC_111= 2003509;
		public static final Integer SCC_112= 2003511;
		public static final Integer SCC_113= 2003513;
		public static final Integer SCC_114= 2003514;
		public static final Integer SCC_115= 2003516;
		public static final Integer SCC_116= 2003517;
		public static final Integer SCC_117= 2003519;
		public static final Integer SCC_118 = 2003521;
		public static final Integer SCC_119= 2003522;
		public static final Integer SCC_120= 2003523;
		public static final Integer SCC_121= 2003524;
		public static final Integer SCC_122= 2003525;
		public static final Integer SCC_123= 2003526;
		public static final Integer SCC_124= 2003527;
		public static final Integer SCC_125= 2003529;
		public static final Integer SCC_126= 2003530;
		public static final Integer SCC_127= 140008;
		public static final Integer SCC_128= 2003531;
		public static final Integer SCC_129= 2003533;
		public static final Integer SCC_130= 2003534;
		public static final Integer SCC_131= 2003536;
		public static final Integer SCC_132= 2003537;
		public static final Integer SCC_133= 2003539;
		public static final Integer SCC_134= 2003542;
		public static final Integer SCC_135= 2003543;
		public static final Integer SCC_136= 2003545;
		public static final Integer SCC_137= 2003547;
		public static final Integer SCC_138= 2003548;
		public static final Integer SCC_139= 2003549;
		public static final Integer SCC_140= 2003550;
		public static final Integer SCC_141= 2003552;
		public static final Integer SCC_142= 2003553;
		public static final Integer SCC_143= 2003555;
		public static final Integer SCC_144= 2003556;
		public static final Integer SCC_145= 2003558;
		public static final Integer SCC_146= 2003559;
		public static final Integer SCC_147= 2003560;
		public static final Integer SCC_148= 2002930;
		public static final Integer SCC_149= 2003491;
		public static final Integer SCC_150= 2003492;
		public static final Integer SCC_151= 2003494;
		public static final Integer SCC_152= 2003496;
		public static final Integer SCC_153= 2003498;
		public static final Integer SCC_154= 2003499;
		public static final Integer SCC_155= 2003500;
		public static final Integer SCC_156= 2003501;
		public static final Integer SCC_157= 2003503;
		public static final Integer SCC_158= 2003505;
		public static final Integer SCC_159= 125024;
		public static final Integer SCC_160= 2003510;
		public static final Integer SCC_161= 2003512;
		public static final Integer SCC_162= 2003515;
		public static final Integer SCC_163= 130083;
		public static final Integer SCC_164= 2003518;
		public static final Integer SCC_165= 2003520;
		public static final Integer SCC_166= 126289;
		public static final Integer SCC_167= 2003528;
		public static final Integer SCC_168= 130085;
		public static final Integer SCC_169= 2003532;
		public static final Integer SCC_170= 2003535;
		public static final Integer SCC_171= 2003538;
		public static final Integer SCC_172= 135794;
		public static final Integer SCC_173= 2003541;
		public static final Integer SCC_174= 2003544;
		public static final Integer SCC_175= 2003546;
		public static final Integer SCC_176= 149056;
		public static final Integer SCC_177= 139661;
		public static final Integer SCC_178= 133831;
		public static final Integer SCC_179= 2003551;
		public static final Integer SCC_180= 2003554;
		public static final Integer SCC_181= 2003557;
		public static final Integer SCC_182= 2003561;
		public static final Integer SCC_183= 2003562;
		public static final Integer SCC_184= 2003564;
		public static final Integer SCC_185= 2003568;
		public static final Integer SCC_186= 2003571;
		public static final Integer SCC_187= 147936;
		public static final Integer SCC_188= 2003578;
		public static final Integer SCC_189= 2003582;
		public static final Integer SCC_190= 2003585;
		public static final Integer SCC_191= 2003587;
		public static final Integer SCC_192= 2003593;
		public static final Integer SCC_193= 2003597;
		public static final Integer SCC_194= 2003602;
		public static final Integer SCC_195= 2003607;
		public static final Integer SCC_196= 2003610;
		public static final Integer SCC_197= 2003619;
		public static final Integer SCC_198= 2003621;
		public static final Integer SCC_199= 2003627;
		public static final Integer SCC_200= 2003628;
		public static final Integer SCC_201= 2003630;
		public static final Integer SCC_202= 2003631;
		public static final Integer SCC_203= 2003634;
		public static final Integer SCC_204= 2003645;
		public static final Integer SCC_205= 2003647;
		public static final Integer SCC_206= 2003650;
		public static final Integer SCC_207= 153438;
		public static final Integer SCC_208= 2003655;
		public static final Integer SCC_209= 2003658;
		public static final Integer SCC_210= 2003659;


		//moh705B

		//Diarrhoea
		public static final Integer DA_1 = 2007117;
		public static final Integer DA_2 = 2007401;
		public static final Integer DA_3 = 139753;
		public static final Integer DA_4 = 142412;
		public static final Integer DA_5 = 2014248;

		//Tuberculosis
		public static final Integer TBA_1 = 160007;
		public static final Integer TBA_2 = 2014797;
		public static final Integer TBA_3 = 113303;
		public static final Integer TBA_4 = 2002858;
		public static final Integer TBA_5 = 2002859;
		public static final Integer TBA_6 = 2002861;
		public static final Integer TBA_7 = 2002862;
		public static final Integer TBA_8 = 2002876;
		public static final Integer TBA_9 = 2002879;
		public static final Integer TBA_10 = 111967;
		public static final Integer TBA_11= 2002881;
		public static final Integer TBA_12= 2002883;
		public static final Integer TBA_13= 112066;
		public static final Integer TBA_14= 112087;
		public static final Integer TBA_15= 2002884;
		public static final Integer TBA_16 = 2002885;
		public static final Integer TBA_17 = 154164;
		public static final Integer TBA_18= 2002887;
		public static final Integer TBA_19 = 2002886;
		public static final Integer TBA_20 = 2002888;
		public static final Integer TBA_21 = 2002889;
		public static final Integer TBA_22 = 159174;
		public static final Integer TBA_23 = 2002890;
		public static final Integer TBA_24 = 143064;
		public static final Integer TBA_25 = 2002892;
		public static final Integer TBA_26 = 115753;
		public static final Integer TBA_27 = 2002894;
		public static final Integer TBA_28 = 152012;
		public static final Integer TBA_29 = 2002900;
		public static final Integer TBA_30 = 2002902;
		public static final Integer TBA_31 = 2002904;
		public static final Integer TBA_32 = 2002906;
		public static final Integer TBA_33 = 2002912;
		public static final Integer TBA_34 = 2002913;
		public static final Integer TBA_35 = 2002837;
		public static final Integer TBA_36 = 2009191;
		public static final Integer TBA_37 = 2009193;
		public static final Integer TBA_38 = 2009194;
		public static final Integer TBA_39 = 143645;
		public static final Integer TBA_40 = 2010314;
		public static final Integer TBA_41 = 2010286;

		//Dysentry
		public static final Integer DYA_1 = 2014827;
		public static final Integer DYA_2 = 137284;

		//Cholera CLA
		public static final Integer CLA_1 = 2013430;
		public static final Integer CLA_2 = 2015050;
		public static final Integer CLA_3 = 2015189;
		public static final Integer CLA_4 = 2015191;
		public static final Integer CLA_5 = 2015193;
		public static final Integer CLA_6 = 2015195;
		public static final Integer CLA_7 = 2002391;
		
		//Other meningitisB
		public static final Integer MOA_1= 2002523;
		public static final Integer MOA_2= 141201;
		public static final Integer MOA_3= 2002524;
		public static final Integer MOA_4= 2002525;
		public static final Integer MOA_5= 2002526;
		public static final Integer MOA_6= 135474;
		public static final Integer MOA_7= 2002540;
		public static final Integer MOA_8= 121255;
		public static final Integer MOA_9= 2002542;
		public static final Integer MOA_10= 2002543;
		public static final Integer MOA_11= 2002541;
		public static final Integer MOA_12= 139736;
		public static final Integer MOA_13= 2002544;
		public static final Integer MOA_14= 121211;
		public static final Integer MOA_15= 2002545;
		public static final Integer MOA_16= 2002546;
		public static final Integer MOA_17= 133667;
		public static final Integer MOA_18= 2002946;
		public static final Integer MOA_19= 138698;
		public static final Integer MOA_20= 152206;
		public static final Integer MOA_21= 146525;
		public static final Integer MOA_22= 144646;
		public static final Integer MOA_23= 1294;
		public static final Integer MOA_24= 2002660;
		public static final Integer MOA_25= 124076;
		public static final Integer MOA_26= 2002676;
		public static final Integer MOA_27= 2002733;
		public static final Integer MOA_28= 2002778;
		public static final Integer MOA_29= 2006553;
		public static final Integer MOA_30= 145722;
		public static final Integer MOA_31= 2006554;
		public static final Integer MOA_32= 114406;
		public static final Integer MOA_33= 2006557;
		public static final Integer MOA_34= 2006558;
		public static final Integer MOA_35= 2006559;
		public static final Integer MOA_36= 2006560;
		public static final Integer MOA_37= 2006624;
		public static final Integer MOA_38= 2009405;
		public static final Integer MOA_39= 2014610;
		public static final Integer MOA_40= 111967;
		public static final Integer MOA_41= 125820;
		public static final Integer MOA_42= 125958;
		public static final Integer MOA_43= 130095;
		public static final Integer MOA_44= 134359;
		public static final Integer MOA_45= 134369;

		//Tetanus
		public static final Integer NTA_1= 2013066;
		public static final Integer NTA_2= 2013089;
		public static final Integer NTA_3= 2013090;
		public static final Integer NTA_4= 2013091;
		public static final Integer NTA_5= 2025660;
		public static final Integer NTA_6= 2025663;
		public static final Integer NTA_7= 84877;
		public static final Integer NTA_8= 2025690;
		public static final Integer NTA_9= 2025973;
		public static final Integer NTA_10= 2026005;
		public static final Integer NTA_11= 2026006;
		public static final Integer NTA_12= 2026007;
		public static final Integer NTA_13= 2026008;
		public static final Integer NTA_14= 2026010;
		public static final Integer NTA_15= 2026011;
		public static final Integer NTA_16= 2026015;
		public static final Integer NTA_17= 2026016;
		public static final Integer NTA_18= 2026017;
		public static final Integer NTA_19= 2025843;
		public static final Integer NTA_20= 2025844;
		public static final Integer NTA_21= 2025845;
		public static final Integer NTA_22= 2025826;
		public static final Integer NTA_23= 2025827;
		public static final Integer NTA_24= 2025828;
		public static final Integer NTA_25= 2025831;
		public static final Integer NTA_26= 2025832;
		public static final Integer NTA_27= 124957;
		public static final Integer NTA_28= 152276;
		public static final Integer NTA_29= 124954;


		//Poliomyelitis B
		public static final Integer PMA_1= 2013072;
		public static final Integer PMA_2= 2025860;
		public static final Integer PMA_3= 2025868;
		public static final Integer PMA_4= 2025869;
		public static final Integer PMA_5= 2025871;
		public static final Integer PMA_6= 2025873;
		public static final Integer PMA_7= 2002520;
		public static final Integer PMA_8= 158786;

		//CHICKENPOX B
		public static final Integer CPA_1= 160435;
		public static final Integer CPA_2= 86110;
		public static final Integer CPA_3= 2025851;
		public static final Integer CPA_4= 2025852;
		public static final Integer CPA_5= 892;
		public static final Integer CPA_6= 2002945;
		public static final Integer CPA_7= 2002947;
		public static final Integer CPA_8= 2002949;
		public static final Integer CPA_9= 2002948;
		public static final Integer CPA_10= 2009385;

		//MEASLES B
		public static final Integer MEA_1= 2015560;
		public static final Integer MEA_2= 2025938;
		public static final Integer MEA_3= 159703;
		public static final Integer MEA_4= 2025886;
		public static final Integer MEA_5= 134561;
		public static final Integer MEA_6= 152209;
		public static final Integer MEA_7= 129469;
		public static final Integer MEA_8= 152206;
		public static final Integer MEA_9= 115885;

		//HEPATITIS
		public static final Integer HEA_1= 142964;
		public static final Integer HEA_2= 149471;
		public static final Integer HEA_3= 2002670;
		public static final Integer HEA_4= 2002673;
		public static final Integer HEA_5= 149743;
		public static final Integer HEA_6= 2002675;
		public static final Integer HEA_7= 152665;
		public static final Integer HEA_8= 2002839;
		public static final Integer HEA_9= 2002841;
		public static final Integer HEA_10= 145131;
		public static final Integer HEA_11= 2002843;
		public static final Integer HEA_12= 2002844;
		public static final Integer HEA_13= 2002846;
		public static final Integer HEA_14= 2002850;
		public static final Integer HEA_15= 145347;
		public static final Integer HEA_16= 2002852;
		public static final Integer HEA_17= 2002867;
		public static final Integer HEA_18= 2002868;
		public static final Integer HEA_19= 2002870;
		public static final Integer HEA_20= 2002871;
		public static final Integer HEA_21= 124412;
		public static final Integer HEA_22= 149153;
		public static final Integer HEA_23= 2007676;
		public static final Integer HEA_24= 2007677;
		public static final Integer HEA_25= 2007678;
		public static final Integer HEA_26= 2007751;
		public static final Integer HEA_27= 2007762;
		public static final Integer HEA_28= 2007764;
		public static final Integer HEA_29= 2009196;
		public static final Integer HEA_30= 152080;
		public static final Integer HEA_31= 2014359;
		public static final Integer HEA_32= 2014362;
		public static final Integer HEA_33= 2014316;
		public static final Integer HEA_34= 2014319;
		public static final Integer HEA_35= 2014321;
		public static final Integer HEA_36= 2014325;
		public static final Integer HEA_37= 2014327;


		//Mumps   MPSA
		public static final Integer MPSA_1= 2015563;
		public static final Integer MPSA_2= 133671;
		public static final Integer MPSA_3= 152238;
		public static final Integer MPSA_4= 2002518;

		//Tested malaria TMC
		public static final Integer TMC_1= 152295;
		public static final Integer TMC_2= 2002634;
		public static final Integer TMC_3= 2002636;
		public static final Integer TMC_4= 158379;
		public static final Integer TMC_5= 2002639;
		public static final Integer TMC_6= 2002641;
		public static final Integer TMC_7= 152296;
		public static final Integer TMC_8= 2002644;
		public static final Integer TMC_9= 2002646;
		public static final Integer TMC_10= 116128;
		public static final Integer TMC_11= 155686;
		public static final Integer TMC_12= 2017917;
	
		//Suspected malaria SMC
		public static final Integer SMC_1= 2002652;
		public static final Integer SMC_2= 116128;

		//Confirmed Malaria positive  COA
		public static final Integer COA_1= 2002631;
		public static final Integer COA_2= 2002637;
		public static final Integer COA_3= 2002642;
		public static final Integer COA_4= 2002649;
		public static final Integer COA_5= 2002650;

		//STI	
		public static final Integer STIA_1 = 2002856;
		public static final Integer STIA_2 = 2002857;
		public static final Integer STIA_3 = 132764;
		public static final Integer STIA_4 = 112491;
		public static final Integer STIA_5 = 143672;
		public static final Integer STIA_6 = 141580;
		public static final Integer STIA_7 = 141581;
		public static final Integer STIA_8 = 157457;
		public static final Integer STIA_9 = 116386;
		public static final Integer STIA_10	 = 136112;
		public static final Integer STIA_11 = 143671;
		public static final Integer STIA_12 = 113655;
		public static final Integer STIA_13 = 129119;
		public static final Integer STIA_14 = 129165;
		public static final Integer STIA_15= 128482;
		public static final Integer STIA_16= 123595;
		public static final Integer STIA_17 = 136020;
		public static final Integer STIA_18= 113068;
		public static final Integer STIA_19 = 113069;
		public static final Integer STIA_20 = 116369;
		public static final Integer STIA_21 = 2002442;
		public static final Integer STIA_22 = 2002461;
		public static final Integer STIA_23 = 2002763;
		public static final Integer STIA_24 = 2002765;
		public static final Integer STIA_25 = 2002766;
		public static final Integer STIA_26 = 2002767;
		public static final Integer STIA_27 = 2002768;
		public static final Integer STIA_28 = 2002769;
		public static final Integer STIA_29 = 2002771;
		public static final Integer STIA_30 = 2002772;
		public static final Integer STIA_31 = 2002773;
		public static final Integer STIA_32 = 139342;
		public static final Integer STIA_33 = 2002777;
		public static final Integer STIA_34 = 139317;
		public static final Integer STIA_35 = 2010396;
		public static final Integer STIA_36 = 2010397;
		public static final Integer STIA_37 = 2010398;
		public static final Integer STIA_38 = 2010399;
		public static final Integer STIA_39 = 2010400;
		public static final Integer STIA_40 = 2010401;
		public static final Integer STIA_41 = 2010402;
		public static final Integer STIA_42 = 2014112;
		public static final Integer STIA_43 = 120733;
		public static final Integer STIA_44 = 2014868;
		public static final Integer STIA_45 = 2002829;
		public static final Integer STIA_46 = 2002830;
		public static final Integer STIA_47 = 145665;
		public static final Integer STIA_48 = 2002831;
		public static final Integer STIA_49 = 2002833;
		public static final Integer STIA_50 = 2002834;
		public static final Integer STIA_51 = 2002835;
		public static final Integer STIA_52 = 145667;
		public static final Integer STIA_53 = 2002725;
		public static final Integer STIA_54 = 120730;
		public static final Integer STIA_55 = 155117;
		public static final Integer STIA_56 = 2010396;



		//Urinary Tract Infection URA
		public static final Integer URA_1 = 2007888;
		public static final Integer URA_2 = 156175;
		public static final Integer URA_3 = 2007889;
		public static final Integer URA_4 = 2007890;
		public static final Integer URA_5 = 2007891;
		public static final Integer URA_6 = 2007892;
		public static final Integer URA_7 = 159255;
		public static final Integer URA_8 = 133367;

		//Intestinal worm B
		public static final Integer INA_1 = 2002807;
		public static final Integer INA_2 = 134042;
		public static final Integer INA_3 = 2002808;
		public static final Integer INA_4 = 2002809;
		public static final Integer INA_5 = 2002810;
		public static final Integer INA_6 = 2002811;
		public static final Integer INA_7 = 2002812;
		public static final Integer INA_8 = 2002813;

		//MALNUTRITION B
		public static final Integer MLA_1 = 2004984;
		public static final Integer MLA_2 = 152346;
		public static final Integer MLA_3 = 2004417;
		public static final Integer MLA_4 = 2004418;
		public static final Integer MLA_5 = 157648;
		public static final Integer MLA_6 = 2009285;
		public static final Integer MLA_7 = 2014336;

		//AnaemiaB



		public static final Integer ANEA_1 = 113372;
		public static final Integer ANEA_2 = 2003144;
		public static final Integer ANEA_3 = 133578;
		public static final Integer ANEA_4 = 2003155;
		public static final Integer ANEA_5 = 1226;
		public static final Integer ANEA_6 = 2003971;
		public static final Integer ANEA_7 = 2003972;
		public static final Integer ANEA_8 = 136535;
		public static final Integer ANEA_9 = 2003973;
		public static final Integer ANEA_10 = 2003974;
		public static final Integer ANEA_11 = 2003975;
		public static final Integer ANEA_12 = 136536;
		public static final Integer ANEA_13 = 123664;
		public static final Integer ANEA_14 = 115848;
		public static final Integer ANEA_15 = 2003976;
		public static final Integer ANEA_16 = 2003977;
		public static final Integer ANEA_17 = 2003978;
		public static final Integer ANEA_18 = 2003979;
		public static final Integer ANEA_19 = 130377;
		public static final Integer ANEA_20 = 2003980;
		public static final Integer ANEA_21 = 2003981;
		public static final Integer ANEA_22 = 2003982;
		public static final Integer ANEA_23 = 2003983;
		public static final Integer ANEA_24 = 2003984;
		public static final Integer ANEA_25 = 2003985;
		public static final Integer ANEA_26 = 2003986;
		public static final Integer ANEA_27 = 2003987;
		public static final Integer ANEA_28 = 2003988;
		public static final Integer ANEA_29 = 2003989;
		public static final Integer ANEA_30 = 140020;
		public static final Integer ANEA_31 = 2003990;
		public static final Integer ANEA_32 = 2003991;
		public static final Integer ANEA_33 = 2003992;
		public static final Integer ANEA_34 = 138750;
		public static final Integer ANEA_35 = 128344;
		public static final Integer ANEA_36 = 2003993;
		public static final Integer ANEA_37 = 148871;
		public static final Integer ANEA_38 = 2003994;
		public static final Integer ANEA_39 = 2003995;
		public static final Integer ANEA_40 = 2003996;
		public static final Integer ANEA_41 = 2003997;
		public static final Integer ANEA_42 = 2003998;
		public static final Integer ANEA_43 = 2003999;
		public static final Integer ANEA_44 = 2004000;
		public static final Integer ANEA_45 = 2004001;
		public static final Integer ANEA_46 = 117576;
		public static final Integer ANEA_47 = 2004002;
		public static final Integer ANEA_48 = 2004003;
		public static final Integer ANEA_49 = 2004004;
		public static final Integer ANEA_50 = 2004005;
		public static final Integer ANEA_51 = 2004006;
		public static final Integer ANEA_52 = 2004008;
		public static final Integer ANEA_53 = 2004009;
		public static final Integer ANEA_54 = 2004010;
		public static final Integer ANEA_55 = 2004187;
		public static final Integer ANEA_56 = 2004188;
		public static final Integer ANEA_57 = 2004313;
		public static final Integer ANEA_58 = 2004314;
		public static final Integer ANEA_59 = 2004315;
		public static final Integer ANEA_60 = 2004316;
		public static final Integer ANEA_61 = 2004317;
		public static final Integer ANEA_62 = 115791;
		public static final Integer ANEA_63 = 2004323;
		public static final Integer ANEA_64 = 2004324;
		public static final Integer ANEA_65 = 2004328;

		//EYE INFECTIONS B

		public static final Integer EYA_1 = 117770;
		public static final Integer EYA_2 = 112066;
		public static final Integer EYA_3 = 2002565;
		public static final Integer EYA_4 = 138700;
		public static final Integer EYA_5 = 138703;
		public static final Integer EYA_6 = 2002567;
		public static final Integer EYA_7 = 2002568;
		public static final Integer EYA_8 = 143596;
		public static final Integer EYA_9 = 2002747;
		public static final Integer EYA_10 = 155839;
		public static final Integer EYA_11 = 2003586;
		public static final Integer EYA_12 = 2003588;
		public static final Integer EYA_13 = 2003798;
		public static final Integer EYA_14 = 2003799;
		public static final Integer EYA_15 = 2003800;
		public static final Integer EYA_16 = 2003801;
		public static final Integer EYA_17 = 2003904;
		public static final Integer EYA_18 = 147829;
		public static final Integer EYA_19 = 2003906;
		public static final Integer EYA_20 = 147828;
		public static final Integer EYA_21 = 2003913;
		public static final Integer EYA_22 = 2003917;
		public static final Integer EYA_23 = 2003923;
		public static final Integer EYA_24 = 2003924;
		public static final Integer EYA_25 = 2004849;
		public static final Integer EYA_26 = 2003944;
		public static final Integer EYA_27 = 2004256;
		public static final Integer EYA_28 = 2006642;
		public static final Integer EYA_29 = 2006645;
		public static final Integer EYA_30 = 2006646;
		public static final Integer EYA_31 = 2006647;
		public static final Integer EYA_32 = 150610;
		public static final Integer EYA_33 = 2006643;
		public static final Integer EYA_34 = 2006651;
		public static final Integer EYA_35 = 2006652;
		public static final Integer EYA_36 = 2006653;
		public static final Integer EYA_37 = 2006654;
		public static final Integer EYA_38 = 145768;
		public static final Integer EYA_39 = 2006655;
		public static final Integer EYA_40 = 2006656;
		public static final Integer EYA_41 = 2006657;
		public static final Integer EYA_42 = 2006658;
		public static final Integer EYA_43 = 2006660;
		public static final Integer EYA_44 = 2006662;
		public static final Integer EYA_45 = 2006663;
		public static final Integer EYA_46 = 2006664;
		public static final Integer EYA_47 = 2006667;
		public static final Integer EYA_48 = 2006668;
		public static final Integer EYA_49 = 2006669;
		public static final Integer EYA_50 = 2006672;
		public static final Integer EYA_51 = 2006673;
		public static final Integer EYA_52 = 2006674;
		public static final Integer EYA_53 = 2006675;
		public static final Integer EYA_54 = 2006676;
		public static final Integer EYA_55 = 2006677;
		public static final Integer EYA_56 = 2006678;
		public static final Integer EYA_57 = 2006679;
		public static final Integer EYA_58 = 2006680;
		public static final Integer EYA_59 = 2006681;
		public static final Integer EYA_60 = 2006682;
		public static final Integer EYA_61 = 2006683;
		public static final Integer EYA_62 = 140053;
		public static final Integer EYA_63 = 2006684;
		public static final Integer EYA_64 = 2006685;
		public static final Integer EYA_65 = 127513;
		public static final Integer EYA_66 = 122813;
		public static final Integer EYA_67 = 2006704;
		public static final Integer EYA_68 = 123123;
		public static final Integer EYA_69 = 143595;
		public static final Integer EYA_70 = 2002632;
		public static final Integer EYA_71 = 2002633;
		public static final Integer EYA_72 = 2002635;
		public static final Integer EYA_73 = 119905;
		public static final Integer EYA_74 = 2006804;
		public static final Integer EYA_75 = 139460;
		public static final Integer EYA_76 = 995;
		public static final Integer EYA_77 = 149898;
		public static final Integer EYA_78 = 2006805;
		public static final Integer EYA_79 = 2006806;
		public static final Integer EYA_80 = 140013;
		public static final Integer EYA_81 = 2006807;
		public static final Integer EYA_82 = 133804;
		public static final Integer EYA_83 = 2006808;
		public static final Integer EYA_84 = 2006809;
		public static final Integer EYA_85 = 143593;
		public static final Integer EYA_86 = 149827;
		public static final Integer EYA_87 = 2006865;
		public static final Integer EYA_88 = 2006866;
		public static final Integer EYA_89 = 147222;
		public static final Integer EYA_90 = 123233;
		public static final Integer EYA_91 = 126671;
		public static final Integer EYA_92 = 2006867;
		public static final Integer EYA_93 = 2006868;
		public static final Integer EYA_94 = 2006869;
		public static final Integer EYA_95 = 2006951;
		public static final Integer EYA_96 = 2006879;
		public static final Integer EYA_97 = 2006878;
		public static final Integer EYA_98 = 2006876;
		public static final Integer EYA_99 = 2006875;
		public static final Integer EYA_100 = 2006959;
		public static final Integer EYA_101 = 2006961;
		public static final Integer EYA_102 = 2006971;
		public static final Integer EYA_103 = 2006973;
		public static final Integer EYA_104 = 2006974;
		public static final Integer EYA_105 = 120860;
		public static final Integer EYA_106 = 2007069;
		public static final Integer EYA_107 = 2007070;
		public static final Integer EYA_108 = 2007071;
		public static final Integer EYA_109 = 2007072;
		public static final Integer EYA_110 = 2007073;
		public static final Integer EYA_111 = 2007074;
		public static final Integer EYA_112 = 2007075;
		public static final Integer EYA_113 = 2007083;
		public static final Integer EYA_114 = 124206;
		public static final Integer EYA_115 = 142455;
		public static final Integer EYA_116 = 125688;
		public static final Integer EYA_117 = 2007087;
		public static final Integer EYA_118 = 2007089;
		public static final Integer EYA_119 = 122119;
		public static final Integer EYA_120 = 2007038;
		public static final Integer EYA_121 = 2007039;
		public static final Integer EYA_122 = 2007040;

		//     Ear Infection EIA  B

		public static final Integer EIA_1 = 134917;
		public static final Integer EIA_2 = 2010497;
		public static final Integer EIA_3 = 2010496;
		public static final Integer EIA_4 = 145251;
		public static final Integer EIA_5 = 2010500;
		public static final Integer EIA_6 = 2010501;
		public static final Integer EIA_7 = 2010502;
		public static final Integer EIA_8 = 151986;
		public static final Integer EIA_9 = 110161;
		public static final Integer EIA_10 = 114428;
		public static final Integer EIA_11 = 2010493;
		public static final Integer EIA_12 = 2010494;
		public static final Integer EIA_13 = 2010495;
		public static final Integer EIA_14 = 145568;
		public static final Integer EIA_15 = 2010498;
		public static final Integer EIA_16 = 2010499;
		public static final Integer EIA_17 = 118467;
		public static final Integer EIA_18 = 2010503;
		public static final Integer EIA_19 = 2010504;
		public static final Integer EIA_20 = 2010505;
		public static final Integer EIA_21 = 150073;
		public static final Integer EIA_22 = 2010506;
		public static final Integer EIA_23 = 530;
		public static final Integer EIA_24 = 2010507;
		public static final Integer EIA_25 = 2010508;
		public static final Integer EIA_26 = 2010509;
		public static final Integer EIA_27 = 2010510;
		public static final Integer EIA_28 = 2010511;
		public static final Integer EIA_29 = 2010512;
		public static final Integer EIA_30 = 2010513;
		public static final Integer EIA_31 = 2010514;
		public static final Integer EIA_32 = 121818;
		public static final Integer EIA_33 = 2010515;
		public static final Integer EIA_34 = 145151;
		public static final Integer EIA_35 = 145484;
		public static final Integer EIA_36 = 2010516;
		public static final Integer EIA_37 = 2010517;
		public static final Integer EIA_38 = 2010518;
		public static final Integer EIA_39 = 149609;
		public static final Integer EIA_40 = 120595;
		public static final Integer EIA_41 = 2010519;
		public static final Integer EIA_42 = 2010520;
		public static final Integer EIA_43 = 118498;
		public static final Integer EIA_44 = 2010521;
		public static final Integer EIA_45 = 118496;
		public static final Integer EIA_46 = 132528;
		public static final Integer EIA_47 = 2010522;
		public static final Integer EIA_48 = 2010523;
		public static final Integer EIA_49 = 149669;
		public static final Integer EIA_50 = 145290;
		public static final Integer EIA_51 = 2010525;
		public static final Integer EIA_52 = 2010526;
		public static final Integer EIA_53 = 155978;
		public static final Integer EIA_54 = 2010573;
		public static final Integer EIA_55 = 116464;
		public static final Integer EIA_56 = 136197;
		public static final Integer EIA_57 = 2010572;
		public static final Integer EIA_58 = 2010571;
		public static final Integer EIA_59 = 2010560;
		public static final Integer EIA_60 = 114424;
		public static final Integer EIA_61 = 2010558;
		public static final Integer EIA_62 = 2010549;
		public static final Integer EIA_63 = 2010547;
		public static final Integer EIA_64 = 2010548;
		public static final Integer EIA_65 = 123212;

		//Upper Respiratory Tract Infections B
		public static final Integer UPA_1 = 106;
		public static final Integer UPA_2 = 121832;
		public static final Integer UPA_3 = 149579;
		public static final Integer UPA_4 = 2011090;
		public static final Integer UPA_5 = 154989;
		public static final Integer UPA_6 = 2011091;
		public static final Integer UPA_7 = 2011092;
		public static final Integer UPA_8 = 2011093;
		public static final Integer UPA_9 = 2022699;
		public static final Integer UPA_10 = 2011094;
		public static final Integer UPA_11 = 149496;
		public static final Integer UPA_12 = 125815;
		public static final Integer UPA_13 = 2011095;
		public static final Integer UPA_14 = 2011096;
		public static final Integer UPA_15 = 149713;
		public static final Integer UPA_16 = 149715;
		public static final Integer UPA_17 = 149716;
		public static final Integer UPA_18 = 149495;
		public static final Integer UPA_19 = 2011097;
		public static final Integer UPA_20 = 2011098;
		public static final Integer UPA_21 = 149714;
		public static final Integer UPA_22 = 149825;
		public static final Integer UPA_23 = 2011099;
		public static final Integer UPA_24 = 2011100;
		public static final Integer UPA_25 = 2011101;
		public static final Integer UPA_26 = 149477;
		public static final Integer UPA_27 = 2011102;
		public static final Integer UPA_28 = 121692;
		public static final Integer UPA_29 = 121691;
		public static final Integer UPA_30 = 2011103;
		public static final Integer UPA_31 = 2011104;
		public static final Integer UPA_32 = 2011105;
		public static final Integer UPA_33 = 2011106;
		public static final Integer UPA_34 = 2011107;
		public static final Integer UPA_35 = 2011108;
		public static final Integer UPA_36 = 2011109;
		public static final Integer UPA_37 = 2011110;
		public static final Integer UPA_38 = 157808;
		public static final Integer UPA_39 = 123280;
		public static final Integer UPA_40 = 2011111;
		public static final Integer UPA_41 = 2011112;
		public static final Integer UPA_42 = 2011113;
		public static final Integer UPA_43 = 145175;
		public static final Integer UPA_44 = 145276;
		public static final Integer UPA_45 = 145214;
		public static final Integer UPA_46 = 2011114;
		public static final Integer UPA_47 = 2011116;
		public static final Integer UPA_48 = 2011136;
		public static final Integer UPA_49 = 2011135;
		public static final Integer UPA_50 = 2011134;
		public static final Integer UPA_51 = 2011133;
		public static final Integer UPA_52 = 130385;
		public static final Integer UPA_53 = 2011132;
		public static final Integer UPA_54 = 2011117;
		public static final Integer UPA_55 = 2013715;
		public static final Integer UPA_56 = 2013711;

		//Asthama B
		public static final Integer ASA_1 = 2006074;
		public static final Integer ASA_2 = 121375;
		public static final Integer ASA_3 = 121699;
		public static final Integer ASA_4 = 4;
		public static final Integer ASA_5 = 2005821;
		public static final Integer ASA_6 = 2005822;
		public static final Integer ASA_7 = 2005823;
		public static final Integer ASA_8 = 2005824;
		public static final Integer ASA_9 = 2005825;
		public static final Integer ASA_10 = 2005829;
		public static final Integer ASA_11 = 2005830;
		public static final Integer ASA_12 = 2005831;
		public static final Integer ASA_13 = 143261;
		public static final Integer ASA_14 = 2005833;
		public static final Integer ASA_15 = 2005834;
		public static final Integer ASA_16 = 2005835;
		public static final Integer ASA_17 = 2005836;

		// PNEUMONIA B
		public static final Integer PNA_1 = 114100;
		public static final Integer PNA_2 = 121252;
		public static final Integer PNA_3 = 2005877;
		public static final Integer PNA_4 = 155715;
		public static final Integer PNA_5 = 2005950;
		public static final Integer PNA_6 = 114099;
		public static final Integer PNA_7 = 115447;
		public static final Integer PNA_8 = 2005951;
		public static final Integer PNA_9 = 112744;
		public static final Integer PNA_10 = 114106;
		public static final Integer PNA_11 = 2005952;
		public static final Integer PNA_12 = 2005953;
		public static final Integer PNA_13 = 2005954;
		public static final Integer PNA_14 = 123098;
		public static final Integer PNA_15 = 2005957;
		public static final Integer PNA_16 = 130013;
		public static final Integer PNA_17 = 2005963;
		public static final Integer PNA_18 = 2005965;
		public static final Integer PNA_19 = 2005967;
		public static final Integer PNA_20 = 2005968;
		public static final Integer PNA_21 = 2005970;
		public static final Integer PNA_22 = 2005974;
		public static final Integer PNA_23 = 2006015;
		public static final Integer PNA_24 = 2006016;
		public static final Integer PNA_25 = 2006017;
		public static final Integer PNA_26 = 2006018;

		//Other Lower tract respiratory infection
		public static final Integer OTRA_1 = 2005873;
		public static final Integer OTRA_2 = 2005875;

		//ABORTION B
		public static final Integer ABA_1 = 2008752;
		public static final Integer ABA_2 = 126127;
		public static final Integer ABA_3 = 2008753;
		public static final Integer ABA_4 = 112796;
		public static final Integer ABA_5 = 2008754;
		public static final Integer ABA_6 = 2008755;
		public static final Integer ABA_7 = 117212;
		public static final Integer ABA_8 = 2008825;
		public static final Integer ABA_9 = 2008826;
		public static final Integer ABA_10 = 2008827;
		public static final Integer ABA_11 = 2008828;
		public static final Integer ABA_12 = 2008829;
		public static final Integer ABA_13 = 2008830;
		public static final Integer ABA_14 = 2008831;
		public static final Integer ABA_15 = 2008832;
		public static final Integer ABA_16 = 2008833;
		public static final Integer ABA_17 = 2008834;
		public static final Integer ABA_18 = 2008835;
		public static final Integer ABA_19 = 2008836;
		public static final Integer ABA_20 = 2008837;
		public static final Integer ABA_21 = 2008838;
		public static final Integer ABA_22 = 2008839;
		public static final Integer ABA_23 = 2008840;
		public static final Integer ABA_24 = 2008841;
		public static final Integer ABA_25 = 2008842;
		public static final Integer ABA_26 = 2008843;
		public static final Integer ABA_27 = 2008844;
		public static final Integer ABA_28 = 2008845;
		public static final Integer ABA_29 = 2008846;
		public static final Integer ABA_30 = 2008847;
		public static final Integer ABA_31 = 2008848;
		public static final Integer ABA_32 = 2008849;
		public static final Integer ABA_33 = 2008850;
		public static final Integer ABA_34 = 2008851;
		public static final Integer ABA_35 = 140790;
		public static final Integer ABA_36 = 156220;
		public static final Integer ABA_37 = 156218;
		public static final Integer ABA_38 = 156219;
		public static final Integer ABA_39 = 2008852;
		public static final Integer ABA_40 = 2008853;
		public static final Integer ABA_41 = 2008854;
		public static final Integer ABA_42 = 2008855;
		public static final Integer ABA_43 = 2008856;
		public static final Integer ABA_44 = 2008857;
		public static final Integer ABA_45 = 2008858;

		//Dis Of Puerperium & Childbath
		public static final Integer DPA_1 = 2008875;
		public static final Integer DPA_2 = 2008876;
		public static final Integer DPA_3 = 2008877;
		public static final Integer DPA_4 = 2008878;
		public static final Integer DPA_5 = 2008879;
		public static final Integer DPA_6 = 2008880;
		public static final Integer DPA_7 = 2008881;
		public static final Integer DPA_8 = 2008882;
		public static final Integer DPA_9 = 2008892;
		public static final Integer DPA_10 = 141538;
		public static final Integer DPA_11 = 2008924;
		public static final Integer DPA_12 = 2008931;
		public static final Integer DPA_13 = 2009152;
		public static final Integer DPA_14 = 2009154;
		public static final Integer DPA_15 = 2009155;
		public static final Integer DPA_16 = 2009156;
		public static final Integer DPA_17 = 158999;
		public static final Integer DPA_18 = 2009157;
		public static final Integer DPA_19 = 156585;
		public static final Integer DPA_20 = 155485;

		//HYPERTENSION
		public static final Integer HYA_1 = 132472;
		public static final Integer HYA_2 = 140987;
		public static final Integer HYA_3 = 2010602;
		public static final Integer HYA_4 = 2010603;
		public static final Integer HYA_5 = 2010604;
		public static final Integer HYA_6 = 2010605;
		public static final Integer HYA_7 = 2010606;
		public static final Integer HYA_8 = 161644;
		public static final Integer HYA_9 = 113087;
		public static final Integer HYA_10 = 2010608;
		public static final Integer HYA_11 = 2010609;
		public static final Integer HYA_12 = 2010610;
		public static final Integer HYA_13 = 2010611;
		public static final Integer HYA_14 = 2010612;
		public static final Integer HYA_15 = 128125;
		public static final Integer HYA_16 = 2010666;
		public static final Integer HYA_17 = 2010667;
		public static final Integer HYA_18 = 2010668;
		public static final Integer HYA_19 = 2010669;
		public static final Integer HYA_20 = 2010670;
		public static final Integer HYA_21 = 2010671;
		public static final Integer HYA_22 = 2011008;
		public static final Integer HYA_23 = 129484;
		public static final Integer HYA_24 = 2007835;
		public static final Integer HYA_25 = 2007842;
		public static final Integer HYA_26 = 2007845;
		public static final Integer HYA_27 = 2007847;
		public static final Integer HYA_28 = 2007866;
		public static final Integer HYA_29 = 2008875;
		public static final Integer HYA_30 = 2008876;
		public static final Integer HYA_31 = 2008880;
		public static final Integer HYA_32 = 2008881;
		public static final Integer HYA_33 = 2008882;
		public static final Integer HYA_34 = 113859;
		public static final Integer HYA_35 = 136205;

		//menta disorder b
		public static final Integer MDA_1 = 113155;
		public static final Integer MDA_2 = 2004781;
		public static final Integer MDA_3 = 2004783;
		public static final Integer MDA_4 = 2004784;
		public static final Integer MDA_5 = 2004786;
		public static final Integer MDA_6 = 2004792;
		public static final Integer MDA_7 = 2004788;
		public static final Integer MDA_8 = 2004789;
		public static final Integer MDA_9 = 2004791;
		public static final Integer MDA_10 = 2004794;
		public static final Integer MDA_11 = 2004795;
		public static final Integer MDA_12 = 2004796;
		public static final Integer MDA_13 = 2004797;
		public static final Integer MDA_14 = 2004798;
		public static final Integer MDA_15 = 2004799;
		public static final Integer MDA_16 = 2004800;
		public static final Integer MDA_17 = 2004801;
		public static final Integer MDA_18 = 2004802;
		public static final Integer MDA_19 = 127132;
		public static final Integer MDA_20 = 2004806;
		public static final Integer MDA_21 = 2004808;
		public static final Integer MDA_22 = 2004809;
		public static final Integer MDA_23 = 2004810;
		public static final Integer MDA_24 = 2004811;
		public static final Integer MDA_25 = 2004812;
		public static final Integer MDA_26 = 2004813;
		public static final Integer MDA_27 = 2004814;
		public static final Integer MDA_28 = 2004815;
		public static final Integer MDA_29 = 2004816;
		public static final Integer MDA_30 = 2004817;
		public static final Integer MDA_31 = 2004818;
		public static final Integer MDA_32 = 2004819;
		public static final Integer MDA_33 = 2004820;
		public static final Integer MDA_34 = 2004821;
		public static final Integer MDA_35 = 2004822;
		public static final Integer MDA_36 = 2004823;
		public static final Integer MDA_37 = 113139;
		public static final Integer MDA_38 = 154937;
		public static final Integer MDA_39 = 2004825;
		public static final Integer MDA_40 = 2004824;
		public static final Integer MDA_41 = 2004826;
		public static final Integer MDA_42 = 2004827;
		public static final Integer MDA_43 = 2004828;
		public static final Integer MDA_44 = 2004829;
		public static final Integer MDA_45 = 2004830;
		public static final Integer MDA_46 = 2004803;
		public static final Integer MDA_47 = 2004804;
		public static final Integer MDA_48 = 2004805;
		public static final Integer MDA_49 = 2004807;
		public static final Integer MDA_50 = 2005361;
		public static final Integer MDA_51 = 119570;
		public static final Integer MDA_52 = 2005362;
		public static final Integer MDA_53 = 2005363;
		public static final Integer MDA_54 = 2005364;
		public static final Integer MDA_55 = 2005365;
		public static final Integer MDA_56 = 2005374;
		public static final Integer MDA_57 = 2005378;
		public static final Integer MDA_58 = 2005379;
		public static final Integer MDA_59 = 2005380;
		public static final Integer MDA_60 = 2005381;
		public static final Integer MDA_61 = 2005382;
		public static final Integer MDA_62 = 2005383;
		public static final Integer MDA_63 = 2005384;
		public static final Integer MDA_64 = 2005385;
		public static final Integer MDA_65 = 2005386;
		public static final Integer MDA_66 = 2005403;
		public static final Integer MDA_67 = 2005404;
		public static final Integer MDA_68 = 2005405;
		public static final Integer MDA_69 = 2005406;
		public static final Integer MDA_70 = 2005407;
		public static final Integer MDA_71 = 2005408;
		public static final Integer MDA_72 = 2005409;
		public static final Integer MDA_73 = 2005410;
		public static final Integer MDA_74 = 119706;
		public static final Integer MDA_75 = 2005411;
		public static final Integer MDA_76 = 2005412;
		public static final Integer MDA_77 = 2005413;
		public static final Integer MDA_78 = 2005414;
		public static final Integer MDA_79 = 2005415;
		public static final Integer MDA_80 = 2005416;
		public static final Integer MDA_81 = 2005417;
		public static final Integer MDA_82 = 2005418;
		public static final Integer MDA_83 = 2005419;
		public static final Integer MDA_84 = 2005420;
		public static final Integer MDA_85 = 2005421;
		public static final Integer MDA_86 = 2005422;
		public static final Integer MDA_87 = 2005423;
		public static final Integer MDA_88 = 127839;
		public static final Integer MDA_89 = 2005424;
		public static final Integer MDA_90 = 2005425;
		public static final Integer MDA_91 = 2005426;
		public static final Integer MDA_92 = 2005427;
		public static final Integer MDA_93 = 2005428;
		public static final Integer MDA_94 = 2005429;
		public static final Integer MDA_95 = 2005430;
		public static final Integer MDA_96 = 2005431;
		public static final Integer MDA_97 = 2005432;
		public static final Integer MDA_98 = 2005387;
		public static final Integer MDA_99 = 2005388;
		public static final Integer MDA_100 = 2005389;
		public static final Integer MDA_101 = 2005390;
		public static final Integer MDA_102 = 2005391;
		public static final Integer MDA_103 = 2005392;
		public static final Integer MDA_104 = 2005393;
		public static final Integer MDA_105 = 2005394;
		public static final Integer MDA_106 = 2005395;
		public static final Integer MDA_107 = 2005396;
		public static final Integer MDA_108 = 2005397;
		public static final Integer MDA_109 = 2005398;
		public static final Integer MDA_110 = 2005399;
		public static final Integer MDA_111 = 2005400;
		public static final Integer MDA_112 = 2005401;
		public static final Integer MDA_113 = 2005402;
		public static final Integer MDA_114 = 2005433;
		public static final Integer MDA_115 = 118779;
		public static final Integer MDA_116 = 134079;
		public static final Integer MDA_117 = 2005434;
		public static final Integer MDA_118 = 2005435;
		public static final Integer MDA_119 = 2005439;
		public static final Integer MDA_120 = 2005444;
		// Dental Disorder

		public static final Integer DEA_1 = 119558;
		public static final Integer DEA_2 = 2006421;
		public static final Integer DEA_3 = 2006422;
		public static final Integer DEA_4 = 127608;
		public static final Integer DEA_5 = 125015;
		public static final Integer DEA_6 = 2006414;
		public static final Integer DEA_7 = 141388;
		public static final Integer DEA_8 = 137758;
		public static final Integer DEA_9 = 2006418;
		public static final Integer DEA_10 = 119183;
		public static final Integer DEA_11 = 2006419;
		public static final Integer DEA_12 = 118490;
		public static final Integer DEA_13 = 2006423;
		public static final Integer DEA_14 = 2006426;
		public static final Integer DEA_15 = 133448;
		public static final Integer DEA_16 = 113480;
		public static final Integer DEA_17 = 128098;
		public static final Integer DEA_18 = 150841;
		public static final Integer DEA_19 = 130572;
		public static final Integer DEA_20 = 2006429;
		public static final Integer DEA_21 = 114258;
		public static final Integer DEA_22 = 114259;
		public static final Integer DEA_23 = 2006430;
		public static final Integer DEA_24 = 2006431;
		public static final Integer DEA_25 = 121523;
		public static final Integer DEA_26 = 149907;
		public static final Integer DEA_27 = 145491;
		public static final Integer DEA_28 = 2006432;
		public static final Integer DEA_29 = 2006433;
		public static final Integer DEA_30 = 2006440;
		public static final Integer DEA_31 = 2006439;
		public static final Integer DEA_32 = 148162;
		public static final Integer DEA_33 = 2006438;
		public static final Integer DEA_34 = 117798;
		public static final Integer DEA_35 = 2006441;
		public static final Integer DEA_36 = 2006442;
		public static final Integer DEA_37 = 2006443;
		public static final Integer DEA_38 = 2006444;
		public static final Integer DEA_39 = 2006445;
		public static final Integer DEA_40 = 2006446;
		public static final Integer DEA_41 = 114250;
		public static final Integer DEA_42 = 2006447;
		public static final Integer DEA_43 = 2006448;
		public static final Integer DEA_44 = 114240;
		public static final Integer DEA_45 = 121873;
		public static final Integer DEA_46 = 2006449;
		public static final Integer DEA_47 = 130463;
		public static final Integer DEA_48 = 2006450;
		public static final Integer DEA_49 = 2006451;
		public static final Integer DEA_50 = 2006453;
		public static final Integer DEA_51 = 2006454;
		public static final Integer DEA_52 = 2006455;
		public static final Integer DEA_53 = 159344;
		public static final Integer DEA_54 = 2006456;
		public static final Integer DEA_55 = 2006457;
		public static final Integer DEA_56 = 117796;
		public static final Integer DEA_57 = 139443;
		public static final Integer DEA_58 = 2006458;

		//AJPA Arthritis
		public static final Integer AJPA_1 = 149534;
		public static final Integer AJPA_2 = 116194;
		public static final Integer AJPA_3 = 127315;
		public static final Integer AJPA_4 = 158693;
		public static final Integer AJPA_5 = 2008508;
		public static final Integer AJPA_6 = 2008509;
		public static final Integer AJPA_7 = 2008510;
		public static final Integer AJPA_8 = 2008511;
		public static final Integer AJPA_9 = 131651;
		public static final Integer AJPA_10 = 2008512;
		public static final Integer AJPA_11 = 2008513;
		public static final Integer AJPA_12 = 2008514;
		public static final Integer AJPA_13 = 2008545;
		public static final Integer AJPA_14 = 2008546;
		public static final Integer AJPA_15 = 2008547;
		public static final Integer AJPA_16 = 2008548;
		public static final Integer AJPA_17 = 2008549;
		public static final Integer AJPA_18 = 2008550;
		public static final Integer AJPA_19 = 2008551;
		public static final Integer AJPA_20 = 2008552;
		public static final Integer AJPA_21 = 2008553;
		public static final Integer AJPA_22 = 2008554;
		public static final Integer AJPA_23 = 2008556;
		public static final Integer AJPA_24 = 2008557;
		public static final Integer AJPA_25 = 2008558;
		public static final Integer AJPA_26 = 127417;
		public static final Integer AJPA_27 = 2008580;
		public static final Integer AJPA_28 = 152339;
		public static final Integer AJPA_29 = 2008581;
		public static final Integer AJPA_30 = 2008582;
		public static final Integer AJPA_31 = 2008583;
		public static final Integer AJPA_32 = 2008584;
		public static final Integer AJPA_33 = 2008585;
		public static final Integer AJPA_34 = 2008577;
		public static final Integer AJPA_35 = 2011311;
		public static final Integer AJPA_36 = 2011312;
		public static final Integer AJPA_37 = 2011317;
		public static final Integer AJPA_38 = 2011318;
		public static final Integer AJPA_39 = 2011319;
		public static final Integer AJPA_40 = 2011321;
		public static final Integer AJPA_41 = 130567;
		public static final Integer AJPA_42 = 136373;
		public static final Integer AJPA_43 = 2008586;
		public static final Integer AJPA_44 = 2008587;
		public static final Integer AJPA_45 = 2008588;
		public static final Integer AJPA_46 = 2008589;
		public static final Integer AJPA_47 = 2008590;
		public static final Integer AJPA_48 = 2008591;
		public static final Integer AJPA_49 = 2008592;
		public static final Integer AJPA_50 = 2008593;
		public static final Integer AJPA_51 = 2008594;
		public static final Integer AJPA_52 = 2008595;

		//POISONING B
		public static final Integer POA_1 = 2006173;
		public static final Integer POA_2 = 2011892;
		public static final Integer POA_3 = 150118;
		public static final Integer POA_4 = 2006707;
		public static final Integer POA_5 = 2006709;
		public static final Integer POA_6 = 2006711;
		public static final Integer POA_7 = 2006712;
		public static final Integer POA_8 = 145568;
		public static final Integer POA_9 = 2007476;
		public static final Integer POA_10 = 2027745;

		//ROAD TRAFFIC INJURIES
		public static final Integer ROA_1 = 2011801;
		public static final Integer ROA_2 = 2012092;
		public static final Integer ROA_3 = 2011800;
		public static final Integer ROA_4 = 2011897;
		public static final Integer ROA_5 = 2012091;
		public static final Integer ROA_6 = 2012094;
		public static final Integer ROA_7 = 2012095;
		public static final Integer ROA_8 = 2012097;
		public static final Integer ROA_9 = 2012098;
		public static final Integer ROA_10 = 2012100;
		public static final Integer ROA_11 = 2012101;
		public static final Integer ROA_12 = 2012103;
		public static final Integer ROA_13 = 2012104;
		public static final Integer ROA_14 = 2012105;
		public static final Integer ROA_15 = 2012106;
		public static final Integer ROA_16 = 2012108;
		public static final Integer ROA_17 = 2012110;
		public static final Integer ROA_18 = 2012113;
		public static final Integer ROA_19 = 2012115;
		public static final Integer ROA_20 = 2012116;
		public static final Integer ROA_21 = 2012118;


		//cardiovascular
		// CAA Variables
		public static final Integer CAA_1 = 2010039;
		public static final Integer CAA_2 = 2010040;
		public static final Integer CAA_3 = 2009654;
		public static final Integer CAA_4 = 2016518;

		//CENTRAL NERVOUS SYSTEM
		// CNSA Variables (Central Nervous System Abnormalities)
		public static final Integer CNSA_1 = 2002779;
		public static final Integer CNSA_2 = 2002786;
		public static final Integer CNSA_3 = 2002898;
		public static final Integer CNSA_4 = 2002899;
		public static final Integer CNSA_5 = 2002901;
		public static final Integer CNSA_6 = 2002903;
		public static final Integer CNSA_7 = 2002905;
		public static final Integer CNSA_8 = 2002917;
		public static final Integer CNSA_9 = 2003553;
		public static final Integer CNSA_10 = 2003613;
		public static final Integer CNSA_11 = 2003639;
		public static final Integer CNSA_12 = 2004153;
		public static final Integer CNSA_13 = 2005595;
		public static final Integer CNSA_14 = 2005643;
		public static final Integer CNSA_15 = 2005645;
		public static final Integer CNSA_16 = 2005646;
		public static final Integer CNSA_17 = 2006601;
		public static final Integer CNSA_18 = 2008939;
		public static final Integer CNSA_19 = 2009119;
		public static final Integer CNSA_20 = 155467;
		public static final Integer CNSA_21 = 2009326;
		public static final Integer CNSA_22 = 2009337;
		public static final Integer CNSA_23 = 2009338;
		public static final Integer CNSA_24 = 2011232;
		public static final Integer CNSA_25 = 2011240;
		public static final Integer CNSA_26 = 2011241;
		public static final Integer CNSA_27 = 2015740;
		public static final Integer CNSA_28 = 2025532;
		public static final Integer CNSA_29 = 2002532;
		public static final Integer CNSA_30 = 2002533;
		public static final Integer CNSA_31 = 155324;
		public static final Integer CNSA_32 = 2002470;
		public static final Integer CNSA_33 = 2002471;
		public static final Integer CNSA_34 = 2002472;
		public static final Integer CNSA_35 = 2002482;
		public static final Integer CNSA_36 = 2002483;
		public static final Integer CNSA_37 = 2002484;
		public static final Integer CNSA_38 = 2002487;
		public static final Integer CNSA_39 = 2002488;
		public static final Integer CNSA_40 = 2002489;
		public static final Integer CNSA_41 = 2002490;
		public static final Integer CNSA_42 = 155838;



		//OVERWEIGHT
		public static final Integer OVA_1 = 2004421;
		public static final Integer OVA_2 = 114413;
		public static final Integer OVA_3 = 2004423;
		public static final Integer OVA_4 = 2004424;
		public static final Integer OVA_5 = 2004443;
		public static final Integer OVA_6 = 2004444;


		// Meningococcal
		public static final Integer MENINGOCOCCAL_MENINGITIS = 134369; // 134369





		public static final Integer OM24 = 138594;


		// Tetanus
		public static final Integer TETANUS = 124957; // 124957

		public static final Integer TETANUS_NEONATORUM = 124954; // 124954

		public static final Integer OBSTETRICAL_TETANUS = 152276;









		//public static final Integer Bacterial_Gastroenteritis = 148023;


		public static final Integer DIARRHOEA = 16;



		//ViralHaemorrhagicFever
		public static final Integer HAEMORRHAGICFEVER_1 = 123112;

		public static final Integer HAEMORRHAGICFEVER_2 = 148483;

		// TUBERCULORSIS
		public static final Integer TUBERCULOSIS = 112141; //


		public static final Integer EPTB = 5042;













		public static final Integer PNEUMONIA = 114100;

		public static final Integer BRUCELLOSIS = 121005;


		public static final Integer ENT = 160455; //160455

		public static final String TBL = "d0ed935d-0c32-451f-b9f4-f5743e267bbe"; //1000044

		public static final Integer STI = 160546; //160546

		public static final Integer ORT = 160465; //160465

		public static final String OCP = "c2f85aed-695f-49b1-973e-002c37cbdebf"; //1000209

		public static final String PHYS = "c2f85aed-695f-49b1-973e-002c37cbdebf"; //1000209 to create

		public static final String SC = "db1ba5ed-1da0-430d-9744-4dcc006f98b0"; //1000047

		public static final Integer PAED = 160544; //160544

		public static final Integer OG = 160456; //160456

		public static final String MOPC = "66710a6d-5894-4f7d-a874-b449df77314d"; //1000042




		public static final Integer MALARIA = 123;



		public static final Integer Malaria_in_pregnancy = 134594;

		public static final Integer Malarial_Nephrosis = 135359;

		public static final Integer Malarial_Hepatitis = 135360;

		public static final Integer Malaria_in_Mother_Complicating_Pregnancy_Childbirth_and_or_Puerperium = 135361;

		public static final Integer Malaria_during_pregnancy = 135361;




		//malaria confirmed
		public static final Integer POSITIVE = 703;

		public static final Integer Positive_for_Plasmodium_falciparum = 161246;

		public static final Integer Positive_for_Plasmodium_vivax = 161247;

		public static final Integer Positive_for_both_Plasmodium_falciparum_and_Plasmodium_vivax = 161248;

		//Malaria suspected
		public static final Integer NEGATIVE = 664;

		public static final Integer INDETERMINATE = 1138;

		public static final Integer Test_not_performed_due_to_lack_of_availability_of_test_materials = 160352;

		public static final Integer Procedure_performed = 1651;

		//Poliomyelitis (AFP)
		public static final Integer PLY1 = 5258;

		public static final Integer PLY2 = 114004;

		public static final Integer PLY3 = 114663;

		public static final Integer PLY4 = 114664;

		public static final Integer PLY5 = 121870;

		public static final Integer PLY6 = 121892;

		public static final Integer PLY7 = 121922;

		public static final Integer PLY8 = 121925;

		public static final Integer PLY9 = 121926;

		public static final Integer PLY10 = 127663;

		public static final Integer PLY11 = 129466;

		public static final Integer PLY12 = 129578;

		public static final Integer PLY13 = 129579;

		public static final Integer PLY14 = 129580;

		public static final Integer PLY15 = 129581;

		public static final Integer PLY16 = 129582;

		public static final Integer PLY17 = 129583;

		public static final Integer PLY18 = 129584;

		public static final Integer PLY19 = 129585;

		public static final Integer PLY20 = 129586;

		public static final Integer PLY21 = 129692;

		public static final Integer PLY22 = 136046;

		public static final Integer PLY23 = 139188;

		public static final Integer PLY24 = 141160;

		public static final Integer PLY25 = 148628;

		public static final Integer PLY26 = 149568;

		public static final Integer PLY27 = 152009;

		public static final Integer PLY28 = 152010;

		public static final Integer PLY29 = 152011;

		public static final Integer PLY30 = 154938;

		public static final Integer PLY31 = 154970;

		public static final Integer PLY32 = 158143;

		public static final Integer PLY33 = 158786;

		public static final Integer PLY34 = 160195;

		//fevers
		public static final Integer FEVER1 = 130;

		public static final Integer FEVER2 = 141;

		public static final Integer FEVER3 = 1494;

		public static final Integer FEVER4 = 1870;

		public static final Integer FEVER5 = 1892;

		public static final Integer FEVER6 = 112372;

		public static final Integer FEVER7 = 112373;

		public static final Integer FEVER8 = 113213;

		public static final Integer RIFT_VALLEY_FEVER = 113217;

		public static final Integer FEVER10 = 113228;

		public static final Integer FEVER11 = 113229;

		public static final Integer FEVER12 = 113230;

		public static final Integer FEVER13 = 113511;

		public static final Integer FEVER14 = 115681;

		public static final Integer FEVER15 = 115794;

		public static final Integer FEVER16 = 116125;

		public static final Integer FEVER17 = 118491;

		public static final Integer FEVER18 = 119353;

		public static final Integer Chikungunya_Fever = 120742;

		public static final Integer Chikungunya_Haemorrhagic_Fever = 120743;

		public static final Integer FEVER21 = 121005;

		public static final Integer FEVER22 = 121039;

		public static final Integer FEVER23 = 121040;

		public static final Integer FEVER24 = 121508;

		public static final Integer FEVER25 = 121690;

		public static final Integer FEVER26 = 121843;

		public static final Integer FEVER27 = 122759;

		public static final Integer FEVER28 = 122873;

		public static final Integer FEVER29 = 123112;

		public static final Integer FEVER30 = 123272;

		public static final Integer FEVER31 = 123557;

		public static final Integer FEVER32 = 123922;

		public static final Integer FEVER33 = 123936;

		public static final Integer FEVER34 = 124151;

		public static final Integer FEVER35 = 124291;

		public static final Integer FEVER36 = 125053;

		public static final Integer FEVER37 = 125220;

		public static final Integer FEVER38 = 125825;

		public static final Integer FEVER39 = 126057;

		public static final Integer FEVER40 = 126147;

		public static final Integer FEVER41 = 126171;

		public static final Integer FEVER42 = 126223;

		public static final Integer FEVER43 = 126272;

		public static final Integer FEVER44 = 126273;

		public static final Integer FEVER45 = 126455;

		public static final Integer FEVER46 = 126787;

		public static final Integer FEVER47 = 127144;

		public static final Integer FEVER48 = 127189;

		public static final Integer FEVER49 = 127328;

		public static final Integer FEVER50 = 127345;

		public static final Integer FEVER51 = 127447;

		public static final Integer FEVER52 = 127664;

		public static final Integer FEVER53 = 127719;

		public static final Integer FEVER54 = 127730;

		public static final Integer FEVER55 = 127731;

		public static final Integer FEVER56 = 127732;

		public static final Integer FEVER57 = 127733;

		public static final Integer FEVER58 = 127734;

		public static final Integer FEVER59 = 127735;

		public static final Integer FEVER60 = 127736;

		public static final Integer FEVER61 = 127737;

		public static final Integer FEVER62 = 127738;

		public static final Integer FEVER63 = 127739;

		public static final Integer FEVER64 = 127740;

		public static final Integer FEVER65 = 127902;

		public static final Integer FEVER66 = 127972;

		public static final Integer FEVER67 = 127981;

		public static final Integer FEVER68 = 127990;

		public static final Integer FEVER69 = 128392;

		public static final Integer FEVER70 = 129324;

		public static final Integer FEVER71 = 129354;

		public static final Integer FEVER72 = 129554;

		public static final Integer FEVER73 = 129599;

		public static final Integer FEVER74 = 130008;

		public static final Integer FEVER75 = 130010;

		public static final Integer FEVER76 = 130147;

		public static final Integer FEVER77 = 130558;

		public static final Integer FEVER78 = 130811;

		public static final Integer FEVER79 = 130812;

		public static final Integer FEVER80 = 130813;

		public static final Integer FEVER81 = 130814;

		public static final Integer FEVER82 = 131690;

		public static final Integer FEVER83 = 132381;

		public static final Integer FEVER84 = 132400;

		public static final Integer FEVER85 = 133472;

		public static final Integer FEVER86 = 133830;

		public static final Integer FEVER87 = 133937;

		public static final Integer FEVER88 = 134096;

		public static final Integer FEVER89 = 134124;

		public static final Integer FEVER90 = 134372;

		public static final Integer FEVER91 = 134380;

		public static final Integer FEVER92 = 134567;

		public static final Integer FEVER93 = 135579;

		public static final Integer FEVER94 = 136116;

		public static final Integer FEVER95 = 136221;

		public static final Integer FEVER96 = 136391;

		public static final Integer FEVER97 = 136451;

		public static final Integer FEVER98 = 139094;

		public static final Integer FEVER99 = 139297;

		public static final Integer FEVER100 = 139917;

		public static final Integer FEVER101 = 140237;

		public static final Integer FEVER102 = 140238;

		public static final Integer FEVER103 = 140471;

		public static final Integer FEVER104 = 140562;

		public static final Integer FEVER105 = 141205;

		public static final Integer FEVER106 = 141564;

		public static final Integer Dengue_haemorrhagic_fever = 142591;

		public static final Integer Dengue_fever = 142592;

		public static final Integer FEVER109 = 142629;

		public static final Integer FEVER110 = 143384;

		public static final Integer FEVER111 = 143621;

		public static final Integer FEVER112 = 144579;

		public static final Integer FEVER113 = 145182;

		public static final Integer FEVER114 = 146087;

		public static final Integer FEVER115 = 146943;

		public static final Integer FEVER116 = 146948;

		public static final Integer FEVER117 = 147043;

		public static final Integer FEVER118 = 147079;

		public static final Integer FEVER119 = 147260;

		public static final Integer FEVER120 = 147928;

		public static final Integer FEVER121 = 147950;

		public static final Integer FEVER122 = 148481;

		public static final Integer FEVER123 = 148483;

		public static final Integer FEVER124 = 148922;

		public static final Integer FEVER125 = 149253;

		public static final Integer FEVER126 = 149533;

		public static final Integer FEVER127 = 149939;

		public static final Integer FEVER128 = 152161;

		public static final Integer FEVER129 = 152456;

		public static final Integer FEVER130 = 153869;

		public static final Integer FEVER131 = 154892;

		public static final Integer FEVER132 = 155879;

		public static final Integer FEVER133 = 156083;

		public static final Integer FEVER134 = 156659;

		public static final Integer FEVER135 = 157324;

		public static final Integer FEVER136 = 157827;

		public static final Integer FEVER137 = 158019;

		public static final Integer FEVER138 = 158615;

		public static final Integer FEVER139 = 158796;

		public static final Integer FEVER140 = 158867;

		public static final Integer FEVER141 = 159336;






		//Malnutritions
		public static final Integer MALN1 = 113960;

		public static final Integer MALN2 = 115122;

		public static final Integer MALN3 = 116304;

		public static final Integer MALN4 = 116314;

		public static final Integer MALN5 = 119614;

		public static final Integer MALN6 = 126358;

		public static final Integer MALN7 = 126598;

		public static final Integer MALN8 = 134721;

		public static final Integer MALN9 = 134722;

		public static final Integer MALN10 = 134723;

		public static final Integer MALN11 = 134724;

		public static final Integer MALN12 = 134725;

		public static final Integer MALN13 = 141591;

		public static final Integer MALN14 = 148471;

		public static final Integer MALN15 = 152022;

		public static final Integer MALN16 = 152346;

		public static final Integer MALN17 = 157648;

		public static final Integer MALN18 = 157649;

		public static final Integer MALN19 = 157650;

		public static final Integer MALN20 = 157651;

		public static final Integer MALN21 = 157652;

		public static final Integer MALN22 = 157653;

		public static final Integer MALN23 = 157793;

		public static final Integer MALN24 = 157812;

		public static final Integer MALN25 = 158480;

		public static final Integer MALN26 = 160205;

		public static final Integer MALN27 = 162330;

		public static final Integer MALN28 = 162331;

		public static final Integer MALN29 = 163302;

		public static final Integer MALN30 = 163303;

		//Diagnosis concepts
		public static final String PROBLEM_ADDED = "6042AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

		public static final String PROVISIONAL_DIAGNOSIS = "160249AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

		public static final String FINA_DIAGNOSIS = "160250AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

		//eye infections
		public static final Integer EI1 = 140832;

		public static final Integer EI2 = 160517;

		//Anaemia
		public static final Integer ANEMIA1 = 110128;
		public static final Integer ANEMIA2 = 110833;
		public static final Integer ANEMIA3 = 110835;
		public static final Integer ANEMIA4 = 113053;
		public static final Integer ANEMIA5 = 113372;
		public static final Integer ANEMIA6 = 115128;
		public static final Integer ANEMIA7 = 115212;

		public static final Integer ANEMIA8 = 115791;

		public static final Integer ANEMIA9 = 115849;

		public static final Integer ANEMIA10 = 115851;

		public static final Integer ANEMIA11 = 115852;

		public static final Integer ANEMIA12 = 117576;

		public static final Integer ANEMIA13 = 117627;

		public static final Integer ANEMIA14 = 117628;

		public static final Integer ANEMIA15 = 119618;

		public static final Integer ANEMIA16 = 119909;

		public static final Integer ANEMIA17 = 121618;

		public static final Integer ANEMIA18 = 121624;

		public static final Integer ANEMIA19 = 121627;

		public static final Integer ANEMIA20 = 121629;

		public static final Integer ANEMIA21 = 122180;

		public static final Integer ANEMIA22 = 122819;

		public static final Integer ANEMIA23 = 122912;

		public static final Integer ANEMIA24 = 122978;

		public static final Integer ANEMIA25 = 123042;

		public static final Integer ANEMIA26 = 123043;

		public static final Integer ANEMIA27 = 123578;

		public static final Integer ANEMIA28 = 124444;

		public static final Integer ANEMIA29 = 124914;

		public static final Integer ANEMIA30 = 126058;

		public static final Integer ANEMIA31 = 126509;

		public static final Integer ANEMIA32 = 127011;

		public static final Integer ANEMIA33 = 127724;

		public static final Integer ANEMIA34 = 127753;

		public static final Integer ANEMIA35 = 127987;

		public static final Integer ANEMIA36 = 128344;

		public static final Integer ANEMIA37 = 130556;

		public static final Integer ANEMIA38 = 132673;

		public static final Integer ANEMIA39 = 132969;

		public static final Integer ANEMIA40 = 134206;

		public static final Integer ANEMIA41 = 134208;

		public static final Integer ANEMIA42 = 134429;

		public static final Integer ANEMIA43 = 134431;

		public static final Integer ANEMIA44 = 134433;

		public static final Integer ANEMIA45 = 134434;

		public static final Integer ANEMIA46 = 134438;

		public static final Integer ANEMIA47 = 134439;

		public static final Integer ANEMIA48 = 134440;

		public static final Integer ANEMIA49 = 134442;

		public static final Integer ANEMIA50 = 134443;

		public static final Integer ANEMIA51 = 134444;

		public static final Integer ANEMIA52 = 134445;

		public static final Integer ANEMIA53 = 134449;

		public static final Integer ANEMIA54 = 134450;

		public static final Integer ANEMIA55 = 134451;

		public static final Integer ANEMIA56 = 135425;

		public static final Integer ANEMIA57 = 136114;

		public static final Integer ANEMIA58 = 138565;

		public static final Integer ANEMIA59 = 138736;

		public static final Integer ANEMIA60 = 138923;

		public static final Integer ANEMIA61 = 138924;

		public static final Integer ANEMIA62 = 138926;

		public static final Integer ANEMIA63 = 138927;

		public static final Integer ANEMIA64 = 138928;

		public static final Integer ANEMIA65 = 138929;

		public static final Integer ANEMIA66 = 138930;

		public static final Integer ANEMIA67 = 138931;

		public static final Integer ANEMIA68 = 138932;

		public static final Integer ANEMIA69 = 139109;

		public static final Integer ANEMIA70 = 139110;

		public static final Integer ANEMIA71 = 139679;

		public static final Integer ANEMIA72 = 139680;

		public static final Integer ANEMIA73 = 139681;

		public static final Integer ANEMIA74 = 139682;

		public static final Integer ANEMIA75 = 139683;

		public static final Integer ANEMIA76 = 140561;

		public static final Integer ANEMIA77 = 141086;

		public static final Integer ANEMIA78 = 141726;

		public static final Integer ANEMIA79 = 141738;

		public static final Integer ANEMIA80 = 142277;

		public static final Integer ANEMIA81 = 143579;

		public static final Integer ANEMIA82 = 143989;

		public static final Integer ANEMIA83 = 143990;

		public static final Integer ANEMIA84 = 144271;

		public static final Integer ANEMIA85 = 144571;

		public static final Integer ANEMIA86 = 145510;

		public static final Integer ANEMIA87 = 146060;

		public static final Integer ANEMIA88 = 148078;

		public static final Integer ANEMIA89 = 148110;

		public static final Integer ANEMIA90 = 148111;

		public static final Integer ANEMIA91 = 148112;

		public static final Integer ANEMIA92 = 148113;

		public static final Integer ANEMIA93 = 148114;

		public static final Integer ANEMIA94 = 148115;

		public static final Integer ANEMIA95 = 148116;

		public static final Integer ANEMIA96 = 148524;

		public static final Integer ANEMIA97 = 148525;

		public static final Integer ANEMIA98 = 148832;

		public static final Integer ANEMIA99 = 148833;

		public static final Integer ANEMIA100 = 148835;

		public static final Integer ANEMIA101 = 148836;

		public static final Integer ANEMIA102 = 148837;

		public static final Integer ANEMIA103 = 148838;

		public static final Integer ANEMIA104 = 148839;

		public static final Integer ANEMIA105 = 148840;

		public static final Integer ANEMIA106 = 148842;

		public static final Integer ANEMIA107 = 148843;

		public static final Integer ANEMIA108 = 148844;

		public static final Integer ANEMIA109 = 148845;

		public static final Integer ANEMIA110 = 148846;

		public static final Integer ANEMIA111 = 148848;

		public static final Integer ANEMIA112 = 148849;

		public static final Integer ANEMIA113 = 148850;

		public static final Integer ANEMIA114 = 148851;

		public static final Integer ANEMIA115 = 148852;

		public static final Integer ANEMIA116 = 148854;

		public static final Integer ANEMIA117 = 148855;

		public static final Integer ANEMIA118 = 148856;

		public static final Integer ANEMIA119 = 148857;

		public static final Integer ANEMIA120 = 148858;

		public static final Integer ANEMIA121 = 148859;

		public static final Integer ANEMIA122 = 148860;

		public static final Integer ANEMIA123 = 148861;

		public static final Integer ANEMIA124 = 148862;

		public static final Integer ANEMIA125 = 148863;

		public static final Integer ANEMIA126 = 148864;

		public static final Integer ANEMIA127 = 148865;

		public static final Integer ANEMIA128 = 148866;

		public static final Integer ANEMIA129 = 148867;

		public static final Integer ANEMIA130 = 148868;

		public static final Integer ANEMIA131 = 148869;

		public static final Integer ANEMIA132 = 148870;

		public static final Integer ANEMIA133 = 148871;

		public static final Integer ANEMIA134 = 148872;

		public static final Integer ANEMIA135 = 148874;

		public static final Integer ANEMIA136 = 148875;

		public static final Integer ANEMIA137 = 148876;

		public static final Integer ANEMIA138 = 149566;

		public static final Integer ANEMIA139 = 149660;

		public static final Integer ANEMIA140 = 149661;

		public static final Integer ANEMIA141 = 149662;

		public static final Integer ANEMIA142 = 149663;

		public static final Integer ANEMIA143 = 149664;

		public static final Integer ANEMIA144 = 150037;

		public static final Integer ANEMIA145 = 150038;

		public static final Integer ANEMIA146 = 151529;

		public static final Integer ANEMIA147 = 152029;

		public static final Integer ANEMIA148 = 152334;

		public static final Integer ANEMIA149 = 152335;

		public static final Integer ANEMIA150 = 158812;

		public static final Integer ANEMIA151 = 162042;

		public static final Integer ANEMIA152 = 162043;

		public static final Integer ANEMIA153 = 162044;



		//Ear infections
		public static final Integer ERA1 = 110189;

		public static final Integer ERA2 = 110889;

		public static final Integer ERA3 = 117086;

		public static final Integer ERA4 = 117087;

		public static final Integer ERA5 = 123119;

		public static final Integer ERA6 = 139740;

		public static final Integer ERA7 = 156628;

		//Atshima cases

		public static final Integer ASTHMA1 = 4;

		public static final Integer ASTHMA2 = 110826;

		public static final Integer ASTHMA3 = 113017;

		public static final Integer ASTHMA4 = 113018;

		public static final Integer ASTHMA5 = 116632;

		public static final Integer ASTHMA6 = 116633;

		public static final Integer ASTHMA7 = 116634;

		public static final Integer ASTHMA8 = 116635;

		public static final Integer ASTHMA9 = 118494;

		public static final Integer ASTHMA10 = 119500;

		public static final Integer ASTHMA11 = 120675;

		public static final Integer ASTHMA12 = 120676;

		public static final Integer ASTHMA13 = 121366;

		public static final Integer ASTHMA14 = 121367;

		public static final Integer ASTHMA15 = 121369;

		public static final Integer ASTHMA16 = 121372;

		public static final Integer ASTHMA17 = 121373;

		public static final Integer ASTHMA18 = 121375;

		public static final Integer ASTHMA19 = 121699;

		public static final Integer ASTHMA20 = 122838;

		public static final Integer ASTHMA21 = 125023;

		public static final Integer ASTHMA22 = 128465;

		public static final Integer ASTHMA23 = 129952;

		public static final Integer ASTHMA24 = 130070;

		public static final Integer ASTHMA25 = 132491;

		public static final Integer ASTHMA26 = 134026;

		public static final Integer ASTHMA27 = 134121;

		public static final Integer ASTHMA28 = 134161;

		public static final Integer ASTHMA29 = 134557;

		public static final Integer ASTHMA30 = 136032;

		public static final Integer ASTHMA31 = 136577;

		public static final Integer ASTHMA32 = 136578;

		public static final Integer ASTHMA33 = 139094;

		public static final Integer ASTHMA34 = 139212;

		public static final Integer ASTHMA35 = 140234;

		public static final Integer ASTHMA36 = 140848;

		public static final Integer ASTHMA37 = 140849;

		public static final Integer ASTHMA38 = 140850;

		public static final Integer ASTHMA39 = 140926;

		public static final Integer ASTHMA40 = 143261;

		public static final Integer ASTHMA41 = 145683;

		public static final Integer ASTHMA42 = 145742;

		public static final Integer ASTHMA43 = 146186;

		public static final Integer ASTHMA44 = 147994;

		public static final Integer ASTHMA45 = 148272;

		public static final Integer ASTHMA46 = 148273;

		public static final Integer ASTHMA47 = 148307;

		public static final Integer ASTHMA48 = 149075;

		public static final Integer ASTHMA49 = 149319;

		public static final Integer ASTHMA50 = 149899;

		public static final Integer ASTHMA51 = 153753;

		public static final Integer ASTHMA52 = 153754;

		public static final Integer ASTHMA53 = 155022;

		public static final Integer ASTHMA54 = 155180;

		public static final Integer ASTHMA55 = 155181;

		public static final Integer ASTHMA56 = 157788;

		public static final Integer ASTHMA57 = 157792;

		public static final Integer ASTHMA58 = 157802;


		//Chromosomes abnomarlities
		public static final Integer CHRAB1 = 116496;

		public static final Integer CHRAB2 = 116497;

		public static final Integer CHRAB3 = 116498;

		public static final Integer CHRAB4 = 120684;

		public static final Integer CHRAB5 = 120685;

		public static final Integer CHRAB6 = 140250;

		public static final Integer CHRAB7 = 140251;

		public static final Integer CHRAB8 = 145521;

		public static final Integer CHRAB9 = 154401;

		public static final Integer CHRAB10 = 157676;

		//congenal anoamalies
		public static final Integer COAN1 = 119927;

		public static final Integer COAN2 = 119946;

		public static final Integer COAN3 = 119947;

		public static final Integer COAN4 = 119956;

		public static final Integer COAN5 = 120020;

		public static final Integer COAN7 = 120024;

		public static final Integer COAN8 = 120025;

		public static final Integer COAN9 = 120026;

		public static final Integer COAN10 = 120028;

		public static final Integer COAN11 = 120031;

		public static final Integer COAN12 = 120032;

		public static final Integer COAN13 = 120036;

		public static final Integer COAN14 = 120043;

		public static final Integer COAN15 = 120045;

		public static final Integer COAN16 = 120048;

		public static final Integer COAN17 = 120049;

		public static final Integer COAN18 = 120051;

		public static final Integer COAN19 = 120053;

		public static final Integer COAN20 = 120055;

		public static final Integer COAN21 = 120058;

		public static final Integer COAN22 = 120060;

		public static final Integer COAN23 = 120064;

		public static final Integer COAN24 = 120066;

		public static final Integer COAN25 = 120067;

		public static final Integer COAN26 = 120070;

		public static final Integer COAN27 = 120071;

		public static final Integer COAN28 = 120073;

		public static final Integer COAN29 = 120076;

		public static final Integer COAN30 = 120077;

		public static final Integer COAN31 = 120080;

		public static final Integer COAN32 = 120082;

		public static final Integer COAN33 = 120085;

		public static final Integer COAN34 = 120088;

		public static final Integer COAN35 = 120090;

		public static final Integer COAN36 = 120091;

		public static final Integer COAN37 = 120094;

		public static final Integer COAN38 = 120095;

		public static final Integer COAN39 = 120096;

		public static final Integer COAN40 = 120099;

		public static final Integer COAN41 = 120101;

		public static final Integer COAN42 = 120102;

		public static final Integer COAN43 = 120105;

		public static final Integer COAN44 = 120107;

		public static final Integer COAN45 = 120108;

		public static final Integer COAN46 = 120111;

		public static final Integer COAN47 = 120113;

		public static final Integer COAN48 = 120115;

		public static final Integer COAN49 = 120117;

		public static final Integer COAN50 = 120119;

		public static final Integer COAN51 = 120122;

		public static final Integer COAN52 = 120125;

		public static final Integer COAN53 = 120126;

		public static final Integer COAN54 = 120141;

		public static final Integer COAN55 = 120143;

		public static final Integer COAN56 = 120144;

		public static final Integer COAN57 = 120765;

		public static final Integer COAN58 = 120766;

		public static final Integer COAN59 = 121595;

		public static final Integer COAN60 = 140449;

		public static final Integer COAN61 = 140450;

		public static final Integer COAN62 = 140453;

		public static final Integer COAN63 = 140456;

		public static final Integer COAN64 = 144159;

		public static final Integer COAN65 = 144160;

		public static final Integer COAN66 = 144161;

		public static final Integer COAN67 = 144162;

		public static final Integer COAN68 = 144163;

		public static final Integer COAN69 = 144164;

		public static final Integer COAN70 = 144165;

		public static final Integer COAN71 = 144166;

		public static final Integer COAN72 = 144167;

		public static final Integer COAN73 = 144168;

		public static final Integer COAN74 = 144169;

		public static final Integer COAN75 = 144171;

		public static final Integer COAN76 = 144172;

		public static final Integer COAN77 = 144173;

		public static final Integer COAN78 = 144174;

		public static final Integer COAN79 = 144177;

		public static final Integer COAN80 = 144178;

		public static final Integer COAN81 = 144179;

		public static final Integer COAN82 = 144180;

		public static final Integer COAN83 = 144181;

		public static final Integer COAN84 = 144182;

		public static final Integer COAN85 = 144183;

		public static final Integer COAN86 = 144184;

		public static final Integer COAN87 = 144185;

		public static final Integer COAN88 = 144187;

		public static final Integer COAN89 = 144188;

		public static final Integer COAN90 = 144189;

		public static final Integer COAN91 = 144191;

		public static final Integer COAN92 = 144192;

		public static final Integer COAN93 = 144193;

		public static final Integer COAN94 = 144194;

		public static final Integer COAN95 = 144195;

		public static final Integer COAN96 = 144196;

		public static final Integer COAN97 = 144197;

		public static final Integer COAN98 = 144198;

		public static final Integer COAN99 = 144199;

		public static final Integer COAN100 = 144200;

		public static final Integer COAN101 = 144202;

		public static final Integer COAN120 = 144222;

		public static final Integer COAN121 = 144223;

		public static final Integer COAN122 = 144224;

		public static final Integer COAN123 = 144225;

		public static final Integer COAN124 = 144226;

		public static final Integer COAN125 = 144227;

		public static final Integer COAN126 = 144228;

		public static final Integer COAN127 = 144230;

		public static final Integer COAN128 = 144231;

		public static final Integer COAN129 = 144232;

		public static final Integer COAN130 = 144233;

		public static final Integer COAN131 = 144237;

		public static final Integer COAN132 = 144238;

		public static final Integer COAN133 = 144239;

		public static final Integer COAN134 = 144240;

		public static final Integer COAN135 = 144242;

		public static final Integer COAN136 = 144243;

		public static final Integer COAN137 = 144244;

		public static final Integer COAN138 = 144245;

		public static final Integer COAN139 = 144246;

		public static final Integer COAN140 = 144247;

		public static final Integer COAN141 = 144248;

		public static final Integer COAN142 = 144249;

		public static final Integer COAN143 = 144250;

		public static final Integer COAN144 = 144251;

		public static final Integer COAN145 = 144252;

		public static final Integer COAN146 = 144253;

		public static final Integer COAN147 = 144254;

		public static final Integer COAN148 = 144255;

		public static final Integer COAN149 = 144256;

		public static final Integer COAN150 = 144257;

		public static final Integer COAN151 = 144258;

		public static final Integer COAN152 = 144259;

		public static final Integer COAN153 = 144260;

		public static final Integer COAN154 = 144261;

		public static final Integer COAN155 = 151524;

		public static final Integer COAN156 = 151686;

		public static final Integer COAN157 = 152443;

		public static final Integer COAN158 = 155681;

		public static final Integer COAN159 = 155682;

		public static final Integer COAN160 = 155683;

		public static final Integer COAN161 = 163390;

		//pneumonia
		public static final Integer PNEUMONIA_1 = 42;

		public static final Integer PNEUMONIA_2 = 482;

		public static final Integer PNEUMONIA_3 = 882;

		public static final Integer PNEUMONIA_4 = 1215;

		public static final Integer PNEUMONIA_6 = 5024;

		public static final Integer PNEUMONIA_7 = 110131;

		public static final Integer PNEUMONIA_8 = 110290;

		public static final Integer PNEUMONIA_9 = 110839;

		public static final Integer PNEUMONIA_11 = 111802;

		public static final Integer PNEUMONIA_12 = 112744;

		public static final Integer PNEUMONIA_13 = 113020;

		public static final Integer PNEUMONIA_14 = 113809;

		public static final Integer PNEUMONIA_15 = 114096;

		public static final Integer PNEUMONIA_16 = 114099;

		public static final Integer PNEUMONIA_17 = 114100;

		public static final Integer PNEUMONIA_18 = 114105;

		public static final Integer PNEUMONIA_19 = 114106;

		public static final Integer PNEUMONIA_20 = 115379;

		public static final Integer PNEUMONIA_21 = 115447;

		public static final Integer PNEUMONIA_23 = 116709;

		public static final Integer PNEUMONIA_24 = 119679;

		public static final Integer PNEUMONIA_25 = 119680;

		public static final Integer PNEUMONIA_26 = 120724;

		public static final Integer PNEUMONIA_27 = 120725;

		public static final Integer PNEUMONIA_28 = 121252;

		public static final Integer PNEUMONIA_29 = 121254;

		public static final Integer PNEUMONIA_30 = 121268;

		public static final Integer PNEUMONIA_31 = 121269;

		public static final Integer PNEUMONIA_32 = 121392;

		public static final Integer PNEUMONIA_33 = 123098;

		public static final Integer PNEUMONIA_34 = 123742;

		public static final Integer PNEUMONIA_35 = 123743;

		public static final Integer PNEUMONIA_36 = 124979;

		public static final Integer PNEUMONIA_37 = 127199;

		public static final Integer PNEUMONIA_38 = 127429;

		public static final Integer PNEUMONIA_39 = 128334;

		public static final Integer PNEUMONIA_40 = 128429;

		public static final Integer PNEUMONIA_41 = 129467;

		public static final Integer PNEUMONIA_42 = 130007;

		public static final Integer PNEUMONIA_43 = 130008;

		public static final Integer PNEUMONIA_44 = 130009;

		public static final Integer PNEUMONIA_45 = 130010;

		public static final Integer PNEUMONIA_46 = 130011;

		public static final Integer PNEUMONIA_47 = 130012;

		public static final Integer PNEUMONIA_48 = 130013;

		public static final Integer PNEUMONIA_49 = 130014;

		public static final Integer PNEUMONIA_50 = 130015;

		public static final Integer PNEUMONIA_51 = 130018;

		public static final Integer PNEUMONIA_52 = 130019;

		public static final Integer PNEUMONIA_53 = 130020;

		public static final Integer PNEUMONIA_55 = 130021;

		public static final Integer PNEUMONIA_56 = 130036;

		public static final Integer PNEUMONIA_57 = 130050;

		public static final Integer PNEUMONIA_58 = 130164;

		public static final Integer PNEUMONIA_59 = 130880;

		public static final Integer PNEUMONIA_60 = 130885;

		public static final Integer PNEUMONIA_61 = 131697;

		public static final Integer PNEUMONIA_62 = 131709;

		public static final Integer PNEUMONIA_63 = 135458;

		public static final Integer PNEUMONIA_64 = 135695;

		public static final Integer PNEUMONIA_65 = 135701;

		public static final Integer PNEUMONIA_66 = 137164;

		public static final Integer PNEUMONIA_67 = 137166;

		public static final Integer PNEUMONIA_68 = 137167;

		public static final Integer PNEUMONIA_69 = 137255;

		public static final Integer PNEUMONIA_70 = 137256;

		public static final Integer PNEUMONIA_71 = 138005;

		public static final Integer PNEUMONIA_72 = 138006;

		public static final Integer PNEUMONIA_73 = 138584;

		public static final Integer PNEUMONIA_74 = 138592;

		public static final Integer PNEUMONIA_75 = 138876;

		public static final Integer PNEUMONIA_76 = 139077;

		public static final Integer PNEUMONIA_77 = 139160;

		public static final Integer PNEUMONIA_78 = 139243;

		public static final Integer PNEUMONIA_79 = 139468;

		public static final Integer PNEUMONIA_80 = 139639;

		public static final Integer PNEUMONIA_81 = 139933;

		public static final Integer PNEUMONIA_82 = 141182;

		public static final Integer PNEUMONIA_83 = 141278;

		public static final Integer PNEUMONIA_84 = 142515;

		public static final Integer PNEUMONIA_85 = 143772;

		public static final Integer PNEUMONIA_86 = 144541;

		public static final Integer PNEUMONIA_87 = 144625;

		public static final Integer PNEUMONIA_88 = 145698;

		public static final Integer PNEUMONIA_89 = 146885;

		public static final Integer PNEUMONIA_90 = 147378;

		public static final Integer PNEUMONIA_91 = 148309;

		public static final Integer PNEUMONIA_92 = 148310;

		public static final Integer PNEUMONIA_93 = 148311;

		public static final Integer PNEUMONIA_94 = 148312;

		public static final Integer PNEUMONIA_95 = 148313;

		public static final Integer PNEUMONIA_96 = 148314;

		public static final Integer PNEUMONIA_97 = 148601;

		public static final Integer PNEUMONIA_98 = 148986;

		public static final Integer PNEUMONIA_99 = 149089;

		public static final Integer PNEUMONIA_100 = 149417;

		public static final Integer PNEUMONIA_101 = 149425;

		public static final Integer PNEUMONIA_102 = 150155;

		public static final Integer PNEUMONIA_103 = 150584;

		public static final Integer PNEUMONIA_107 = 155714;

		public static final Integer PNEUMONIA_108 = 155715;

		public static final Integer PNEUMONIA_109 = 155716;

		public static final Integer PNEUMONIA_110 = 155717;

		public static final Integer PNEUMONIA_111 = 155718;

		public static final Integer PNEUMONIA_112 = 155719;

		public static final Integer PNEUMONIA_113 = 156818;

		public static final Integer PNEUMONIA_114 = 157468;

		public static final Integer PNEUMONIA_115 = 157470;

		public static final Integer PNEUMONIA_117 = 158384;

		public static final Integer PNEUMONIA_118 = 158444;

		public static final Integer PNEUMONIA_119 = 158647;

		public static final Integer PNEUMONIA_120 = 158700;

		public static final Integer PNEUMONIA_121 = 158702;

		public static final Integer PNEUMONIA_122 = 159277;

		//respiratory disorders
		public static final Integer OTHER_REP_1 = 148393;

		public static final Integer OTHER_REP_2 = 158451;




		//Skin disease
		public static final Integer SKIN_1 = 119022;

		public static final Integer SKIN_2 = 125954;

		public static final Integer SKIN_3 = 128208;

		public static final Integer SKIN_4 = 142634;

		public static final Integer SKIN_5 = 160229;


		//New and revisit clients
		public static final Integer NEW_PATIENT = 164144;

		public static final String REVISIT_PATIENT = "d5ea1533-7346-4e0b-8626-9bff6cd183b2";

		//Additional special clinic concepts
		public static final Integer NUTRITION_PROGRAM = 160552;

		public static final Integer RENAL_CLINIC = 160475;

		public static final Integer ONCOLOGY_CLINIC = 116030;










		//Diabetics

		//fistula
		public static final Integer FISTULA1 = 49;

		public static final Integer FISTULA2 = 110168;

		public static final Integer FISTULA3 = 110267;

		public static final Integer FISTULA4 = 110268;

		public static final Integer FISTULA5 = 111645;

		public static final Integer FISTULA6 = 113876;

		public static final Integer FISTULA7 = 113877;

		public static final Integer FISTULA8 = 114601;

		public static final Integer FISTULA9 = 117885;

		public static final Integer FISTULA10 = 118145;

		public static final Integer FISTULA11 = 118808;

		public static final Integer FISTULA12 = 118809;

		public static final Integer FISTULA13 = 123201;

		public static final Integer FISTULA14 = 123202;

		public static final Integer FISTULA15 = 123203;

		public static final Integer FISTULA16 = 123204;

		public static final Integer FISTULA17 = 123205;

		public static final Integer FISTULA18 = 123289;

		public static final Integer FISTULA19 = 123310;

		public static final Integer FISTULA20 = 123377;

		public static final Integer FISTULA21 = 123423;

		public static final Integer FISTULA22 = 123439;

		public static final Integer FISTULA23 = 123442;

		public static final Integer FISTULA24 = 123443;

		public static final Integer FISTULA25 = 123497;

		public static final Integer FISTULA26 = 123509;

		public static final Integer FISTULA27 = 123510;

		public static final Integer FISTULA28 = 123511;

		public static final Integer FISTULA29 = 123513;

		public static final Integer FISTULA30 = 123514;

		public static final Integer FISTULA31 = 123535;

		public static final Integer FISTULA32 = 123541;

		public static final Integer FISTULA33 = 123659;

		public static final Integer FISTULA34 = 124267;

		public static final Integer FISTULA35 = 124402;

		public static final Integer FISTULA36 = 124403;

		public static final Integer FISTULA37 = 125865;

		public static final Integer FISTULA38 = 126503;

		public static final Integer FISTULA39 = 126794;

		public static final Integer FISTULA40 = 127319;

		public static final Integer FISTULA41 = 127844;

		public static final Integer FISTULA42 = 127846;

		public static final Integer FISTULA43 = 127847;

		public static final Integer FISTULA44 = 127855;

		public static final Integer FISTULA45 = 129265;

		public static final Integer FISTULA46 = 129460;

		public static final Integer FISTULA47 = 130056;

		public static final Integer FISTULA48 = 130345;

		public static final Integer FISTULA49 = 130573;

		public static final Integer FISTULA50 = 130974;

		public static final Integer FISTULA51 = 131097;

		public static final Integer FISTULA52 = 131696;

		public static final Integer FISTULA53 = 131748;

		public static final Integer FISTULA54 = 132475;

		public static final Integer FISTULA55 = 134295;

		public static final Integer FISTULA56 = 134306;

		public static final Integer FISTULA57 = 134540;

		public static final Integer FISTULA58 = 134541;

		public static final Integer FISTULA59 = 134705;

		public static final Integer FISTULA60 = 135494;

		public static final Integer FISTULA61 = 135551;

		public static final Integer FISTULA62 = 136170;

		public static final Integer FISTULA63 = 136196;

		public static final Integer FISTULA64 = 136197;

		public static final Integer FISTULA65 = 136492;

		public static final Integer FISTULA66 = 136671;

		public static final Integer FISTULA67 = 136672;

		public static final Integer FISTULA68 = 136673;

		public static final Integer FISTULA69 = 136731;

		public static final Integer FISTULA70 = 136732;

		public static final Integer FISTULA71 = 137857;

		public static final Integer FISTULA72 = 138413;

		public static final Integer FISTULA73 = 138642;

		public static final Integer FISTULA74 = 139570;

		public static final Integer FISTULA75 = 139592;

		public static final Integer FISTULA76 = 139595;

		public static final Integer FISTULA77 = 139615;

		public static final Integer FISTULA78 = 140083;

		public static final Integer FISTULA79 = 140084;

		public static final Integer FISTULA80 = 140085;

		public static final Integer FISTULA81 = 140086;

		public static final Integer FISTULA82 = 140087;

		public static final Integer FISTULA83 = 140088;

		public static final Integer FISTULA84 = 140089;

		public static final Integer FISTULA85 = 140090;

		public static final Integer FISTULA86 = 140091;

		public static final Integer FISTULA87 = 140092;

		public static final Integer FISTULA88 = 140093;

		public static final Integer FISTULA89 = 140094;

		public static final Integer FISTULA90 = 140095;

		public static final Integer FISTULA91 = 140096;

		public static final Integer FISTULA92 = 140097;

		public static final Integer FISTULA93 = 140098;

		public static final Integer FISTULA94 = 140099;

		public static final Integer FISTULA95 = 140100;

		public static final Integer FISTULA96 = 140102;

		public static final Integer FISTULA97 = 140428;

		public static final Integer FISTULA98 = 140464;

		public static final Integer FISTULA99 = 140465;

		public static final Integer FISTULA100 = 140889;

		public static final Integer FISTULA101 = 140892;

		public static final Integer FISTULA102 = 140996;

		public static final Integer FISTULA103 = 141001;

		public static final Integer FISTULA104 = 141006;

		public static final Integer FISTULA105 = 141214;

		public static final Integer FISTULA106 = 141215;

		public static final Integer FISTULA107 = 141327;

		public static final Integer FISTULA108 = 141328;

		public static final Integer FISTULA109 = 141329;

		public static final Integer FISTULA110 = 141330;

		public static final Integer FISTULA111 = 141331;

		public static final Integer FISTULA112 = 141662;

		public static final Integer FISTULA113 = 141663;

		public static final Integer FISTULA114 = 141677;

		public static final Integer FISTULA115 = 143642;

		public static final Integer FISTULA116 = 143737;

		public static final Integer FISTULA117 = 143743;

		public static final Integer FISTULA118 = 143744;

		public static final Integer FISTULA119 = 143745;

		public static final Integer FISTULA120 = 143746;

		public static final Integer FISTULA121 = 143747;

		public static final Integer FISTULA122 = 143960;

		public static final Integer FISTULA123 = 143961;

		public static final Integer FISTULA124 = 143970;

		public static final Integer FISTULA125 = 143971;

		public static final Integer FISTULA126 = 144070;

		public static final Integer FISTULA127 = 144090;

		public static final Integer FISTULA128 = 144156;

		public static final Integer FISTULA129 = 144157;

		public static final Integer FISTULA130 = 145626;

		public static final Integer FISTULA131 = 145628;

		public static final Integer FISTULA132 = 145629;

		public static final Integer FISTULA133 = 145778;

		public static final Integer FISTULA134 = 145783;

		public static final Integer FISTULA135 = 146144;

		public static final Integer FISTULA136 = 146875;

		public static final Integer FISTULA137 = 146879;

		public static final Integer FISTULA138 = 146880;

		public static final Integer FISTULA139 = 146890;

		public static final Integer FISTULA140 = 146950;

		public static final Integer FISTULA141 = 147251;

		public static final Integer FISTULA142 = 148441;

		public static final Integer FISTULA143 = 148454;

		public static final Integer FISTULA144 = 148505;

		public static final Integer FISTULA145 = 148541;

		public static final Integer FISTULA146 = 148543;

		public static final Integer FISTULA147 = 148544;

		public static final Integer FISTULA148 = 148652;

		public static final Integer FISTULA149 = 148903;

		public static final Integer FISTULA150 = 150103;

		public static final Integer FISTULA151 = 150104;

		public static final Integer FISTULA152 = 150111;

		public static final Integer FISTULA153 = 150112;

		public static final Integer FISTULA154 = 150899;

		public static final Integer FISTULA155 = 151765;

		public static final Integer FISTULA156 = 152085;

		public static final Integer FISTULA157 = 152086;

		public static final Integer FISTULA158 = 152300;

		public static final Integer FISTULA159 = 152671;

		public static final Integer FISTULA160 = 155120;

		public static final Integer FISTULA161 = 155197;

		public static final Integer FISTULA162 = 155684;

		public static final Integer FISTULA163 = 155761;

		public static final Integer FISTULA164 = 156140;

		public static final Integer FISTULA165 = 156313;

		public static final Integer FISTULA166 = 156315;

		public static final Integer FISTULA167 = 156316;

		public static final Integer FISTULA168 = 156463;

		public static final Integer FISTULA169 = 156616;

		public static final Integer FISTULA170 = 157329;

		public static final Integer FISTULA171 = 157402;

		public static final Integer FISTULA172 = 157974;

		public static final Integer FISTULA173 = 157980;

		public static final Integer FISTULA174 = 158603;

		public static final Integer FISTULA175 = 158635;

		public static final Integer FISTULA176 = 159919;

		public static final Integer FISTULA177 = 163848;

		public static final Integer FISTULA178 = 163870;

		public static final Integer FISTULA179 = 163871;

		public static final Integer FISTULA180 = 163872;




		// start CARDIOVASCULAR Consts
		public static final Integer CARDIOVASCULAR_1 = 110462;

		public static final Integer CARDIOVASCULAR_2 = 117397;

		public static final Integer CARDIOVASCULAR_3 = 119270;

		public static final Integer CARDIOVASCULAR_4 = 120001;

		public static final Integer CARDIOVASCULAR_5 = 120002;

		public static final Integer CARDIOVASCULAR_6 = 120003;

		public static final Integer CARDIOVASCULAR_7 = 120004;

		public static final Integer CARDIOVASCULAR_8 = 120005;

		public static final Integer CARDIOVASCULAR_9 = 120115;

		public static final Integer CARDIOVASCULAR_10 = 120874;

		public static final Integer CARDIOVASCULAR_11 = 120875;

		public static final Integer CARDIOVASCULAR_12 = 121486;

		public static final Integer CARDIOVASCULAR_13 = 122227;

		public static final Integer CARDIOVASCULAR_14 = 122432;

		public static final Integer CARDIOVASCULAR_15 = 122727;

		public static final Integer CARDIOVASCULAR_16 = 129903;

		public static final Integer CARDIOVASCULAR_17 = 140231;

		public static final Integer CARDIOVASCULAR_18 = 141036;

		public static final Integer CARDIOVASCULAR_19 = 141752;

		public static final Integer CARDIOVASCULAR_20 = 144098;

		public static final Integer CARDIOVASCULAR_21 = 144099;

		public static final Integer CARDIOVASCULAR_22 = 144100;

		public static final Integer CARDIOVASCULAR_23 = 144101;

		public static final Integer CARDIOVASCULAR_24 = 146153;

		public static final Integer CARDIOVASCULAR_25 = 146154;

		public static final Integer CARDIOVASCULAR_26 = 146155;

		public static final Integer CARDIOVASCULAR_27 = 150812;

		public static final Integer CARDIOVASCULAR_28 = 155434;

		public static final Integer CARDIOVASCULAR_29 = 155687;

		//Overweight
		public static final Integer OVERWEIGHT1 = 114413;

		public static final Integer OVERWEIGHT2 = 158204;


		//Brucelosis
		public static final Integer BRUCELOSIS1 = 121005;

		public static final Integer BRUCELOSIS2 = 121042;

		public static final Integer BRUCELOSIS3 = 129500;

		public static final Integer BRUCELOSIS4 = 138391;

		public static final Integer BRUCELOSIS5 = 146480;

		public static final Integer BRUCELOSIS6 = 146866;

		public static final Integer BRUCELOSIS7 = 155336;

		public static final Integer EAR_INFECTION_0 = 110189;

		public static final Integer EAR_INFECTION_1 = 110889;

		public static final Integer EAR_INFECTION_2 = 117086;

		public static final Integer EAR_INFECTION_3 = 117086;

		public static final Integer EAR_INFECTION_4 = 117087;

		public static final Integer EAR_INFECTION_5 = 123119;

		public static final Integer EAR_INFECTION_6 = 139740;

		public static final Integer EAR_INFECTION_7 = 156628;

		//Physical Disability
		public static final Integer PHYSICAL_DISABILITY1 = 110104;

		public static final Integer PHYSICAL_DISABILITY2 = 119485;

		public static final Integer PHYSICAL_DISABILITY3 = 122936;

		public static final Integer PHYSICAL_DISABILITY4 = 152464;

		public static final Integer PHYSICAL_DISABILITY5 = 153343;

		public static final Integer PHYSICAL_DISABILITY6 = 156923;

		public static final Integer PHYSICAL_DISABILITY7 = 164538;



		//Kalazar
		public static final Integer KALAZAR = 129386;

		//Plague
		public static final Integer PLAGUE1 = 114093;

		public static final Integer PLAGUE2 = 114120;

		public static final Integer PLAGUE3 = 126687;

		public static final Integer PLAGUE4 = 126882;

		public static final Integer PLAGUE5 = 128493;

		public static final Integer PLAGUE6 = 129694;

		public static final Integer PLAGUE7 = 130003;

		public static final Integer PLAGUE8 = 130095;

		public static final Integer PLAGUE9 = 130096;

		public static final Integer PLAGUE10 = 145971;

		public static final Integer PLAGUE11 = 146858;

		public static final Integer PLAGUE12 = 149270;

		public static final Integer PLAGUE13 = 150738;

		public static final Integer PLAGUE14 = 154809;

		public static final Integer PLAGUE15 = 157238;

		public static final Integer PLAGUE16 = 158375;

		public static final Integer PLAGUE17 = 158376;



		public static final String MOPC_TRAIGE = "98f596cc-5ad1-4c58-91e8-d1ea0329c89d";

		public static final String DENTAL_OPD = "548bf465-fdfb-43f1-836e-76d0416789bf";

		public static final String DENTAL_SPECIAL_CLINIC = "30aff715-92de-4662-aa33-fa6b6179fed0";

		//Dangue fever
		public static final Integer Dengue_Shock_Syndrome = 142590;

		public static final Integer Dry_Form_of_Cutaneous_Leishmaniasis = 118825;

		public static final Integer Zoonotic_Form_of_Cutaneous_Leishmaniasis = 122736;

		public static final Integer Wet_Form_of_Cutaneous_Leishmaniasis = 122866;

		public static final Integer Ulcerating_Cutaneous_Leishmaniasis = 123882;

		public static final Integer Recurrent_Cutaneous_Leishmaniasis = 127835;

		public static final Integer Late_Cutaneous_Leishmaniasis = 136110;

		public static final Integer Disseminated_Mucocutaneous_Leishmaniasis = 141897;

		public static final Integer Diffuse_Cutaneous_Leishmaniasis = 142362;

		public static final Integer Cutaneous_Leishmaniasis = 143074;

		public static final Integer Asian_Desert_Cutaneous_Leishmaniasis = 148329;

		public static final Integer American_Cutaneous_Mucocutaneous_Leishmaniasis = 148988;

		public static final Integer Acute_Necrotising_Cutaneous_Leishmaniasis = 149635;

		//Anthrax
		public static final Integer Pulmonary_Anthrax = 116937;

		public static final Integer Contact_with_or_Exposure_to_Anthrax = 121554;

		public static final Integer Anthrax = 121555;

		public static final Integer Gastrointestinal_Anthrax = 139588;

		public static final Integer Cutaneous_Anthrax = 143086;

		public static final Integer Anthrax_Septicaemia = 148600;

		public static final Integer Anthrax_Pneumonia = 148601;

		//Dehydration
		public static final Integer DEHYDRATION = 991;

		public static final Integer Neonatal_dehydration = 133410;

		public static final Integer Dehydration = 142630;

		public static final Integer severe_dehydration = 154015;

		public static final Integer Moderate_dehydration = 154016;

		public static final Integer Mild_dehydration = 154017;

		//Gastroenteritis
		public static final Integer Gastroenteritis_1 = 197;

		public static final Integer Gastroenteritis_2 = 428;

		public static final Integer Gastroenteritis_3 = 900;

		public static final Integer Gastroenteritis_4 = 112749;

		public static final Integer Gastroenteritis_5 = 113172;

		public static final Integer Gastroenteritis_6 = 117050;

		public static final Integer Gastroenteritis_7 = 117889;

		public static final Integer Gastroenteritis_8 = 118586;

		public static final Integer Gastroenteritis_9 = 123113;

		public static final Integer Gastroenteritis_10 = 123114;

		public static final Integer Gastroenteritis_11 = 124445;

		public static final Integer Gastroenteritis_13 = 127943;

		public static final Integer Gastroenteritis_14 = 132860;

		public static final Integer Gastroenteritis_15 = 132941;

		public static final Integer Gastroenteritis_16 = 137284;

		public static final Integer Gastroenteritis_17 = 139591;

		public static final Integer Gastroenteritis_18 = 142379;

		public static final Integer Gastroenteritis_19 = 148023;

		public static final Integer Gastroenteritis_20 = 149097;

		public static final Integer Gastroenteritis_21 = 149098;

		public static final Integer Gastroenteritis_22 = 149725;

		public static final Integer Gastroenteritis_23 = 149727;

		public static final Integer Gastroenteritis_24 = 149779;

		public static final Integer Gastroenteritis_25 = 151875;

		public static final Integer Gastroenteritis_26 = 151876;

		public static final Integer Gastroenteritis_27 = 151877;

		//severe pneumonia
		public static final Integer acute_bacterial_pneumonia = 1463;

		public static final Integer Acute_Interstitial_Pneumonia = 111028;

		public static final Integer Chronic_Interstitial_Pneumonia = 116708;

		public static final Integer acute_bronchitis_due_to_Mycoplasma_pneumoniae = 154944;

		public static final Integer acute_mucous_pneumonia = 154964;

		public static final Integer bilateral_pneumonia = 155296;

		public static final Integer neonatal_pneumonia = 157907;

		public static final Integer Acute_bacterial_pneumonia_presumed = 160437;

		public static final Integer Sub_acute_pneumonia_presumed_PCP = 160438;

		//lower tract infection
		public static final Integer Lower_Respiratory_Tract_Infection = 135556;

		public static final Integer Chronic_Obstructive_Pulmonary_Disease_with_Acute_Lower_Respiratory_Infection = 152062;

		public static final Integer unspecified_acute_lower_respiratory_infection = 159222;

		public static final Integer RESPIRATORY_TRACT_INFECTION_LOWER = 998;

		public static final Integer RESPIRATORY_TRACT_COVID = 165868;

		//upeer tract infection
		public static final Integer UPPER_RESPIRATORY_TRACT_INFECTION = 895;

		public static final Integer WHO_HIV_RECURRENT_UPPER_RESPIRATORY_INFECTIONS = 5331;

		public static final Integer Congenital_Anomaly_of_Upper_Respiratory_System = 120022;

		public static final Integer Congenital_Abnormality_of_Upper_Respiratory_Tract = 120023;

		public static final Integer Benign_Neoplasm_of_Upper_Respiratory_Tract = 121172;

		public static final Integer Viral_Upper_Respiratory_Tract_Infection = 123093;

		public static final Integer Upper_Respiratory_Tract_Obstruction = 123561;

		public static final Integer Upper_Respiratory_Tract_Hypersensitivity_Reaction = 123562;

		public static final Integer Upper_Respiratory_Inflammation_due_to_Fumes_and_or_Vapors = 123563;

		public static final Integer Upper_Respiratory_Inflammation_due_to_Chemical_Fumes = 123564;

		public static final Integer Upper_Respiratory_Infection = 123565;

		public static final Integer Recurrent_Upper_Respiratory_Tract_Infection = 127784;

		public static final Integer Influenzal_Acute_Upper_Respiratory_Infection = 137165;

		public static final Integer Disorder_of_Upper_Respiratory_System = 141964;

		public static final Integer Acute_Upper_Respiratory_Infection_of_Multiple_Sites = 149477;

		public static final Integer Acute_Upper_Respiratory_Infection = 149478;

		public static final Integer COVID = 165633;

		//Neutal Sepsis
		public static final Integer NEUTAL_SEPISIS = 226;

		//Down's syndrome
		public static final Integer DOWN_SYNDROME = 144481;


		//
		public static final Integer Hypoxaemia = 117312;


		//filling concepts ids
		public static final Integer Temporary_Filling_Per_Tooth = 1000342;

		public static final Integer Amalgam_filling = 1000300;

		public static final Integer Composite_Filling = 1000306;

		public static final Integer Glass_Lonomer_Filling = 1000317;

		//extraction concept ids
		public static final Integer Extra_Tooth_extraction = 1000315;

		public static final Integer Tooth_Extraction_simple = 1000344;

		public static final Integer Excision_of_Tooth = 1000314;

		//Medical examination
		public static final Integer MEDICAL_EXAMINATION_ROUTINE = 432;

		public static final Integer Student_Medical_Assessment = 1000446;

		//Dressing
		public static final Integer CLEAN_AND_DRESSING = 441;

		public static final Integer Suture_wound_with_dressing = 1934;

		public static final Integer Wound_Dressing = 1935;

		public static final Integer Change_of_Dressing = 151596;

		public static final Integer Burn_dressing = 161668;

		public static final Integer Dressing_change_under_anesthesia = 161945;

		public static final Integer Dressing_change_for_open_wound_of_breast = 162210;

		public static final Integer Cleaning_and_Dressing = 1000242;

		public static final Integer Casualty_Dressing = 1000245;

		public static final Integer ENT_Dressing = 1000356;

		public static final Integer Removal_Of_Ear_Dressing = 1000363;

		public static final Integer Wound_Dressing_ENT = 1000368;

		public static final Integer Dressing_Per_Session_Female_Medical_Ward = 1000424;

		//Removal of stitches
		public static final Integer Removal_Of_Stitches = 1000365;

		public static final Integer Removal_Of_Corneal_Stitches = 1000397;

		//injection
		public static final Integer INJECTION = 1000251;

		public static final Integer Sub_Conjuctiral_Sub_Tenon_Injections = 1000399;

		public static final Integer Intra_arterial_injection = 164677;

		public static final Integer Intralesional_injection = 164000;

		//Stitching
		public static final Integer STITCHING = 1000251;

		public static final Integer Stitching_Casualty = 1000260;

		public static final Integer Stitching_In_Minor_Theatre = 1000261;

		public static final Integer Dental_stitching = 1000340;

		public static final Integer Diarrhoea_with_no_dehydration = 168737;

		public static final Integer Diarrhoea_with_some_dehydration = 166569;

		public static final Integer Diarrhoea_with_severe_dehydration = 168736;

	}

//		public static final Concept Diarrhoea_with_no_dehydration_concept = getConcept(__DiagnosisConcepts.Diarrhoea_with_no_dehydration);
//
//		public static final Concept Diarrhoea_with_some_dehydration_concept = getConcept(__DiagnosisConcepts.Diarrhoea_with_some_dehydration);
//
//		public static final Concept Diarrhoea_with_severe_dehydration_concept = getConcept(__DiagnosisConcepts.Diarrhoea_with_severe_dehydration);

	/**
	 * @return the Concept that matches the passed uuid, name, source:code mapping, or primary key
	 *         id
	 */
	public static Concept getConcept(String lookup) {
		Concept c = Context.getConceptService().getConceptByUuid(lookup);
		if (c == null) {
			c = Context.getConceptService().getConceptByName(lookup);
		}
		if (c == null) {
			try {
				String[] split = lookup.split("\\:");
				if (split.length == 2) {
					c = Context.getConceptService().getConceptByMapping(split[1], split[0]);
				}
			}
			catch (Exception e) {}
		}
		if (c == null) {
			try {
				c = Context.getConceptService().getConcept(Integer.parseInt(lookup));
			}
			catch (Exception e) {
				throw new RuntimeException(e.toString());
			}
		}
		return c;
	}

	public static Concept getConcept(Integer lookup) {

		return Context.getConceptService().getConcept(lookup);
	}
	
/*
* Provides a list of diagnosis which are not mapped as a list for under 5
*  
* */
	public static List<Integer> allNonListedDiagnosisForUnder5() {
		Integer DIARRHOEA_WITH_SOME_DEHYDRATION = 2007117;
		Integer DIARRHOEA_WITH_SEVERE_DEHYDRATION = 2014248 ;
		Integer DYSENTERY = 2014827;
		Integer PNEUMONIA = 114100;
		Integer ASTHMA = 121375;
		Integer CHOLERA = 2002391;		
		Integer MALNUTRITION = 2004984;
		Integer ANAEMIA = 1226;
		Integer MENINGOCOCCAL_MENINGITIS = 134369;
		Integer NEONATAL_SEPSIS = 226;
		Integer NEONATAL_TETANUS = 124954;
		Integer JIGGERS_INFESTATION = 166567;
		Integer DISEASES_OF_SKIN = 2009210;
		Integer DOWN_SYNDROME = 144481;
		Integer POISONING= 2027745;
		Integer BRUCELLOSIS= 121005;
		Integer VIRAL_HAEMORRHAGIC_FEVER= 2002647;
		Integer CUTANEOUS_LEISHMANIASIS= 143074;
		Integer SUSPECTED_ANTHRAX= 121555;
		Integer HYPOXAEMIA= 2006141;
		Integer EAR_INFECTION_PEAD= 149609;
		List<Integer> allNonListedDiagnosisForUnder5 = new ArrayList<Integer>();

		allNonListedDiagnosisForUnder5.add(DIARRHOEA_WITH_SOME_DEHYDRATION);
		allNonListedDiagnosisForUnder5.add(DIARRHOEA_WITH_SEVERE_DEHYDRATION);
		allNonListedDiagnosisForUnder5.add(DYSENTERY);
		allNonListedDiagnosisForUnder5.add(PNEUMONIA);
		allNonListedDiagnosisForUnder5.add(ASTHMA);
		allNonListedDiagnosisForUnder5.add(CHOLERA);		
		allNonListedDiagnosisForUnder5.add(ANAEMIA);
		allNonListedDiagnosisForUnder5.add(MALNUTRITION);
		allNonListedDiagnosisForUnder5.add(MENINGOCOCCAL_MENINGITIS);
		allNonListedDiagnosisForUnder5.add(NEONATAL_SEPSIS);
		allNonListedDiagnosisForUnder5.add(NEONATAL_TETANUS);
		allNonListedDiagnosisForUnder5.add(JIGGERS_INFESTATION);
		allNonListedDiagnosisForUnder5.add(DISEASES_OF_SKIN);
		allNonListedDiagnosisForUnder5.add(DOWN_SYNDROME);
		allNonListedDiagnosisForUnder5.add(POISONING);
		allNonListedDiagnosisForUnder5.add(BRUCELLOSIS);
		allNonListedDiagnosisForUnder5.add(VIRAL_HAEMORRHAGIC_FEVER);
		allNonListedDiagnosisForUnder5.add(CUTANEOUS_LEISHMANIASIS);
		allNonListedDiagnosisForUnder5.add(SUSPECTED_ANTHRAX);
		allNonListedDiagnosisForUnder5.add(HYPOXAEMIA);
		allNonListedDiagnosisForUnder5.add(EAR_INFECTION_PEAD);
		return allNonListedDiagnosisForUnder5;
	}
	/*
	 * Provides a list of diagnosis which are not mapped as a list for above 5
	 *
	 * */
	public static List<Integer> allNonListedDiagnosisForAbove5() {
		Integer MENINGOCOCCAL_MENINGITIS = 134369;
		Integer MALARIA_IN_PREGNANCY =135361;
		Integer JIGGERS_INFESTATION =166567;
		Integer BRUCELLOCIS=121005;
		Integer PHYSICAL_DISABILITY=2014582;
		Integer VIRAL_HAEMORRHAGIC_FEVER=2002647;
		Integer CUTANEOUS_LEISHMANIASIS=143074;
		Integer SUSPECTED_ANTHRAX=121555;

		List<Integer> allNonListedDiagnosisForAbove5 = new ArrayList<Integer>();

		allNonListedDiagnosisForAbove5.add(MENINGOCOCCAL_MENINGITIS);
		allNonListedDiagnosisForAbove5.add(MALARIA_IN_PREGNANCY);
		allNonListedDiagnosisForAbove5.add(JIGGERS_INFESTATION);
		allNonListedDiagnosisForAbove5.add(BRUCELLOCIS);
		allNonListedDiagnosisForAbove5.add(PHYSICAL_DISABILITY);
		allNonListedDiagnosisForAbove5.add(VIRAL_HAEMORRHAGIC_FEVER);
		allNonListedDiagnosisForAbove5.add(CUTANEOUS_LEISHMANIASIS);
		allNonListedDiagnosisForAbove5.add(SUSPECTED_ANTHRAX);
		return allNonListedDiagnosisForAbove5;
	}
	
	
}
