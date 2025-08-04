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
		public static final Integer DWSOD_1 = 142412;
		public static final Integer DWSOD_2 = 142630;

		//Diarrhoea with severe dehydration
		public static final Integer DWSD_1 = 2007117;
		public static final Integer DWSD_2 = 142630;

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
		public static final Integer URC_1 = 106;
		public static final Integer URC_2 = 121832;
		public static final Integer URC_3 = 2011090;
		public static final Integer URC_4 = 2011091;
		public static final Integer URC_5 = 2011092;
		public static final Integer URC_6 = 2011093;
		public static final Integer URC_7 = 125815;
		public static final Integer URC_8 = 2011095;
		public static final Integer URC_9 = 2011096;
		public static final Integer URC_10 = 149713;
		public static final Integer URC_11 = 149716;
		public static final Integer URC_12 = 149495;
		public static final Integer URC_13 = 2011097;
		public static final Integer URC_14 = 149714;
		public static final Integer URC_15 = 149825;
		public static final Integer URC_16 = 2011099;
		public static final Integer URC_17 = 2011101;
		public static final Integer URC_18 = 149477;
		public static final Integer URC_19 = 121691;
		public static final Integer URC_20 = 2011103;
		public static final Integer URC_21 = 2011104;
		public static final Integer URC_22 = 2011105;
		public static final Integer URC_23 = 2011106;
		public static final Integer URC_24 = 2011108;
		public static final Integer URC_25 = 2011109;
		public static final Integer URC_26 = 2011110;
		public static final Integer URC_27 = 157808;
		public static final Integer URC_28 = 123280;
		public static final Integer URC_29 = 145175;
		public static final Integer URC_30 = 145276;
		public static final Integer URC_31 = 145214;
		public static final Integer URC_32 = 2011115;
		public static final Integer URC_33 = 2011116;
		public static final Integer URC_34 = 2011117;
		public static final Integer URC_35 = 2011118;
		public static final Integer URC_36 = 2011119;
		public static final Integer URC_37 = 142493;
		public static final Integer URC_38 = 138130;
		public static final Integer URC_39 = 138120;
		public static final Integer URC_40 = 138144;
		public static final Integer URC_41 = 2011121;
		public static final Integer URC_42 = 2011122;
		public static final Integer URC_43 = 2011123;
		public static final Integer URC_44 = 2011124;
		public static final Integer URC_45 = 130868;
		public static final Integer URC_46 = 129522;
		public static final Integer URC_47 = 2011126;
		public static final Integer URC_48 = 141479;
		public static final Integer URC_49 = 116400;
		public static final Integer URC_50 = 125914;
		public static final Integer URC_51 = 2011127;
		public static final Integer URC_52 = 2011128;
		public static final Integer URC_53 = 129518;
		public static final Integer URC_54 = 2011129;
		public static final Integer URC_55 = 2011130;
		public static final Integer URC_56 = 2011132;
		public static final Integer URC_57 = 130385;
		public static final Integer URC_58 = 2011133;
		public static final Integer URC_59 = 2011134;
		public static final Integer URC_60 = 2011135;
		public static final Integer URC_61 = 2011136;


		//Lower Respiratory Tract Infections
		public static final Integer OTRA_1 = 2017815;
		public static final Integer OTRA_2 = 2005873;
		public static final Integer OTRA_3 = 2005875;
		public static final Integer OTRA_4 = 2006035;
		public static final Integer OTRA_5 = 2006036;
		public static final Integer OTRA_6 = 2006037;
		public static final Integer OTRA_7 = 144677;
		public static final Integer OTRA_8 = 2006038;
		public static final Integer OTRA_9 = 152293;
		public static final Integer OTRA_10 = 2006039;
		public static final Integer OTRA_11 = 147941;
		public static final Integer OTRA_12 = 147419;
		public static final Integer OTRA_13 = 139262;
		public static final Integer OTRA_14 = 2006040;
		public static final Integer OTRA_15 = 125965;
		public static final Integer OTRA_16 = 2006041;
		public static final Integer OTRA_17 = 2006042;
		public static final Integer OTRA_18 = 140517;
		public static final Integer OTRA_19 = 147997;
		public static final Integer OTRA_20 = 147282;
		public static final Integer OTRA_21 = 125627;
		public static final Integer OTRA_22 = 134707;
		public static final Integer OTRA_23 = 134679;
		public static final Integer OTRA_24 = 2006044;
		public static final Integer OTRA_25 = 2006045;
		public static final Integer OTRA_26 = 115463;
		public static final Integer OTRA_27 = 2006046;
		public static final Integer OTRA_28 = 129996;
		public static final Integer OTRA_29 = 2006048;
		public static final Integer OTRA_30 = 2006049;
		public static final Integer OTRA_31 = 135757;
		public static final Integer OTRA_32 = 2006050;
		public static final Integer OTRA_33 = 2006051;
		public static final Integer OTRA_34 = 134387;
		public static final Integer OTRA_35 = 2006052;
		public static final Integer OTRA_36 = 2006053;
		public static final Integer OTRA_37 = 165843;
		public static final Integer OTRA_38 = 2006140;
		public static final Integer OTRA_39 = 2006141;
		public static final Integer OTRA_40 = 2006142;
		public static final Integer OTRA_41 = 2006143;
		public static final Integer OTRA_42 = 2006144;
		public static final Integer OTRA_43 = 2006145;
		public static final Integer OTRA_44 = 2006147;
		public static final Integer OTRA_45 = 2006148;
		public static final Integer OTRA_46 = 2006149;
		public static final Integer OTRA_47 = 121011;
		public static final Integer OTRA_48 = 2005837;
		public static final Integer OTRA_49 = 2005838;
		public static final Integer OTRA_50 = 2005839;
		public static final Integer OTRA_51 = 2005840;
		public static final Integer OTRA_52 = 145266;
		public static final Integer OTRA_53 = 2005842;
		public static final Integer OTRA_54 = 2005843;
		public static final Integer OTRA_55 = 2005844;
		public static final Integer OTRA_56 = 2005845;
		public static final Integer OTRA_57 = 2005846;
		public static final Integer OTRA_58 = 2005847;
		public static final Integer OTRA_59 = 2005870;
		public static final Integer OTRA_60 = 2005873;
		public static final Integer OTRA_61 = 2005875;
		public static final Integer OTRA_62 = 149884;
		public static final Integer OTRA_63 = 2006019;
		public static final Integer OTRA_64 = 2006129;
		public static final Integer OTRA_65 = 2006020;
		public static final Integer OTRA_66 = 154946;
		public static final Integer OTRA_67 = 154945;
		public static final Integer OTRA_68 = 2006021;
		public static final Integer OTRA_69 = 2006022;
		public static final Integer OTRA_70 = 154943;
		public static final Integer OTRA_71 = 154941;
		public static final Integer OTRA_72 = 2006023;
		public static final Integer OTRA_73 = 2006024;
		public static final Integer OTRA_74 = 2006026;
		public static final Integer OTRA_75 = 150584;
		public static final Integer OTRA_76 = 2006027;
		public static final Integer OTRA_77 = 150582;
		public static final Integer OTRA_78 = 2006028;
		public static final Integer OTRA_79 = 2006029;
		public static final Integer OTRA_80 = 2006030;
		public static final Integer OTRA_81 = 2006031;
		public static final Integer OTRA_82 = 2006032;
		public static final Integer OTRA_83 = 2006033;
		public static final Integer OTRA_84 = 2006055;
		public static final Integer OTRA_85 = 2006056;
		public static final Integer OTRA_86 = 2006057;
		public static final Integer OTRA_87 = 2006058;
		public static final Integer OTRA_88 = 2006059;
		public static final Integer OTRA_89 = 2006061;
		public static final Integer OTRA_90 = 2006062;
		public static final Integer OTRA_91 = 2006063;
		public static final Integer OTRA_92 = 2006067;
		public static final Integer OTRA_93 = 2006064;
		public static final Integer OTRA_94 = 2006065;
		public static final Integer OTRA_95 = 2006068;
		public static final Integer OTRA_96 = 152002;
		public static final Integer OTRA_97 = 152057;
		public static final Integer OTRA_98 = 2006069;
		public static final Integer OTRA_99 = 2006070;
		public static final Integer OTRA_100 = 2006071;
		public static final Integer OTRA_101 = 2006073;
		public static final Integer OTRA_102 = 2006072;
		public static final Integer OTRA_103 = 113506;
		public static final Integer OTRA_104 = 2006074;
		public static final Integer OTRA_105 = 2006076;
		public static final Integer OTRA_106 = 2006077;
		public static final Integer OTRA_107 = 2006078;
		public static final Integer OTRA_108 = 2006079;
		public static final Integer OTRA_109 = 124089;
		public static final Integer OTRA_110 = 2006080;
		public static final Integer OTRA_111 = 2006081;
		public static final Integer OTRA_112 = 2006083;
		public static final Integer OTRA_113 = 2006084;
		public static final Integer OTRA_114 = 2006085;
		public static final Integer OTRA_115 = 2006086;
		public static final Integer OTRA_116 = 2006087;
		public static final Integer OTRA_117 = 2006088;
		public static final Integer OTRA_118 = 2006089;
		public static final Integer OTRA_119 = 2006090;
		public static final Integer OTRA_120 = 2006092;
		public static final Integer OTRA_121 = 135458;
		public static final Integer OTRA_122 = 2006093;
		public static final Integer OTRA_123 = 2006094;
		public static final Integer OTRA_124 = 117297;
		public static final Integer OTRA_125 = 128156;
		public static final Integer OTRA_126 = 2006096;
		public static final Integer OTRA_127 = 2006097;
		public static final Integer OTRA_128 = 2006098;
		public static final Integer OTRA_129 = 2006099;
		public static final Integer OTRA_130 = 2006100;
		public static final Integer OTRA_131 = 2006101;
		public static final Integer OTRA_132 = 2006102;
		public static final Integer OTRA_133 = 2006103;
		public static final Integer OTRA_134 = 2006105;
		public static final Integer OTRA_135 = 2006106;
		public static final Integer OTRA_136 = 2006107;
		public static final Integer OTRA_137 = 2006108;
		public static final Integer OTRA_138 = 2006110;
		public static final Integer OTRA_139 = 2006111;
		public static final Integer OTRA_140 = 2006112;
		public static final Integer OTRA_141 = 2006113;
		public static final Integer OTRA_142 = 2006114;
		public static final Integer OTRA_143 = 2006115;
		public static final Integer OTRA_144 = 2006116;
		public static final Integer OTRA_145 = 128157;
		public static final Integer OTRA_146 = 2006118;
		public static final Integer OTRA_147 = 2006119;
		public static final Integer OTRA_148 = 2006120;
		public static final Integer OTRA_149 = 2006121;
		public static final Integer OTRA_150 = 2006122;
		public static final Integer OTRA_151 = 158381;
		public static final Integer OTRA_152 = 2006123;
		public static final Integer OTRA_153 = 126067;
		public static final Integer OTRA_154 = 2006125;
		public static final Integer OTRA_155 = 2006126;
		public static final Integer OTRA_156 = 2006127;
		public static final Integer OTRA_157 = 2006129;
		public static final Integer OTRA_158 = 2006130;
		public static final Integer OTRA_159 = 2006131;
		public static final Integer OTRA_160 = 142098;
		public static final Integer OTRA_161 = 2006132;
		public static final Integer OTRA_162 = 2006133;
		public static final Integer OTRA_163 = 2006134;
		public static final Integer OTRA_164 = 2006135;
		public static final Integer OTRA_165 = 2006137;
		public static final Integer OTRA_166 = 2006138;
		public static final Integer OTRA_167 = 114108;
		public static final Integer OTRA_168 = 138845;
		public static final Integer OTRA_169 = 148246;
		public static final Integer OTRA_170 = 136721;
		public static final Integer OTRA_171 = 144532;
		public static final Integer OTRA_172 = 2006139;
		public static final Integer OTRA_173 = 2006151;
		public static final Integer OTRA_174 = 155583;
		public static final Integer OTRA_175 = 152302;
		public static final Integer OTRA_176 = 2006349;
		public static final Integer OTRA_177 = 110738;
		public static final Integer OTRA_178 = 2006350;


		//Presumed Tuberculosis
		public static final Integer TCP_1 = 2002912;
		public static final Integer TCP_2 = 2002913;


		//Other Menignitis A
		public static final Integer OMC_1 = 111967;
		public static final Integer OMC_2 = 125820;
		public static final Integer OMC_3 = 125958;
		public static final Integer OMC_4 = 130095;
		public static final Integer OMC_5 = 134359;
		public static final Integer OMC_6 = 2002523;
		public static final Integer OMC_7 = 141201;
		public static final Integer OMC_8 = 2002524;
		public static final Integer OMC_9 = 2002525;
		public static final Integer OMC_10 = 2002526;
		public static final Integer OMC_11 = 135474;
		public static final Integer OMC_12 = 2002540;
		public static final Integer OMC_13 = 121255;
		public static final Integer OMC_14 = 2002541;
		public static final Integer OMC_15 = 2002542;
		public static final Integer OMC_16 = 2002543;
		public static final Integer OMC_17 = 139736;
		public static final Integer OMC_18 = 2002544;
		public static final Integer OMC_19 = 121211;
		public static final Integer OMC_20 = 2002545;
		public static final Integer OMC_21 = 2002546;
		public static final Integer OMC_22 = 133667;
		public static final Integer OMC_23 = 2002946;
		public static final Integer OMC_24 = 138698;
		public static final Integer OMC_25 = 152206;
		public static final Integer OMC_26 = 146525;
		public static final Integer OMC_27 = 144646;
		public static final Integer OMC_28 = 1294;
		public static final Integer OMC_29 = 2002660;
		public static final Integer OMC_30 = 124076;
		public static final Integer OMC_31 = 2002676;
		public static final Integer OMC_32 = 2002733;
		public static final Integer OMC_33 = 2002778;
		public static final Integer OMC_34 = 2006553;
		public static final Integer OMC_35 = 145722;
		public static final Integer OMC_36 = 2006554;
		public static final Integer OMC_37 = 114406;
		public static final Integer OMC_38 = 2006557;
		public static final Integer OMC_39 = 2006558;
		public static final Integer OMC_40 = 2006559;
		public static final Integer OMC_41 = 2006560;
		public static final Integer OMC_42 = 2006624;
		public static final Integer OMC_43 = 2009405;
		public static final Integer OMC_44 = 2014610;

		//Poliomyelitis
		public static final Integer PMC_1 = 2002520;
		public static final Integer PMC_2 = 158786;
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
		public static final Integer AM_6 = 169524;
		public static final Integer AM_7 = 2002422;
		public static final Integer AM_8 = 149000;
		public static final Integer AM_9 = 149001;

		//Mumps
		public static final Integer MPC_1 = 133671;
		public static final Integer MPC_2 = 152238;
		public static final Integer MPC_3 = 2002518;
		public static final Integer MPC_4 = 2015563;
		public static final Integer MPC_5 = 133662;
		public static final Integer MPC_6 = 133667;
		public static final Integer MPC_7 = 133669;
		public static final Integer MPC_8 = 133661;

		//Typhoid fever
		public static final Integer TYC_1 = 141;
		public static final Integer TYC_2 = 2002395;
		public static final Integer TYC_3 = 2002396;
		public static final Integer TYC_4 = 130811;
		public static final Integer TYC_5 = 2002394;

		//Bilhazia
		public static final Integer BLC_1 = 117152;
		public static final Integer BLC_2 = 2002798;
		public static final Integer BLC_3 = 2002799;
		public static final Integer BLC_4 = 2002800;
		public static final Integer BLC_5 = 2002804;
		public static final Integer BLC_6 = 2002801;
		public static final Integer BLC_7 = 2002802;
		public static final Integer BLC_8 = 2002803;

		//Interstinal Worms

		public static final Integer IWC_1 = 2002807;
		public static final Integer IWC_2 = 2002810;
		public static final Integer IWC_3 = 2002811;
		public static final Integer IWC_4 = 134042;
		public static final Integer IWC_5 = 2002808;
		public static final Integer IWC_6 = 2002809;
		public static final Integer IWC_7 = 2002733;
		public static final Integer IWC_8 = 151976;
		public static final Integer IWC_9 = 2002734;
		public static final Integer IWC_10 = 2002735;
		public static final Integer IWC_11 = 2002736;
		public static final Integer IWC_12 = 148353;
		public static final Integer IWC_13 = 2002737;
		public static final Integer IWC_14 = 2002738;
		public static final Integer IWC_15 = 2002739;
		public static final Integer IWC_16 = 141216;
		public static final Integer IWC_17 = 2002746;
		public static final Integer IWC_18 = 140993;
		public static final Integer IWC_19 = 2002751;
		public static final Integer IWC_20 = 2002752;
		public static final Integer IWC_21 = 132466;
		public static final Integer IWC_22 = 2002753;
		public static final Integer IWC_23 = 124130;
		public static final Integer IWC_24 = 124129;
		public static final Integer IWC_25 = 2002754;
		public static final Integer IWC_26 = 2002755;
		public static final Integer IWC_27 = 2002756;
		public static final Integer IWC_28 = 2002778;
		public static final Integer IWC_29 = 2002779;
		public static final Integer IWC_30 = 155839;
		public static final Integer IWC_31 = 2002780;
		public static final Integer IWC_32 = 2002781;
		public static final Integer IWC_33 = 142267;
		public static final Integer IWC_34 = 2002782;
		public static final Integer IWC_35 = 138346;
		public static final Integer IWC_36 = 117148;
		public static final Integer IWC_37 = 2002789;
		public static final Integer IWC_38 = 2002790;
		public static final Integer IWC_39 = 2002791;
		public static final Integer IWC_40 = 2002792;
		public static final Integer IWC_41 = 2002793;
		public static final Integer IWC_42 = 145042;
		public static final Integer IWC_43 = 142388;
		public static final Integer IWC_44 = 2002795;
		public static final Integer IWC_45 = 2002796;
		public static final Integer IWC_46 = 131773;
		public static final Integer IWC_47 = 2002797;
		public static final Integer IWC_48 = 2002805;
		public static final Integer IWC_49 = 2002806;


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
		public static final Integer MDC_101 = 2005420;
		public static final Integer MDC_102 = 2005421;
		public static final Integer MDC_103 = 2005422;
		public static final Integer MDC_104 = 2005423;
		public static final Integer MDC_105 = 127839;
		public static final Integer MDC_106 = 2005424;
		public static final Integer MDC_107 = 2005425;
		public static final Integer MDC_108 = 2005426;
		public static final Integer MDC_109 = 2005427;
		public static final Integer MDC_110 = 2005428;
		public static final Integer MDC_111 = 2005429;
		public static final Integer MDC_112 = 2005430;
		public static final Integer MDC_113 = 2005431;
		public static final Integer MDC_114 = 2005433;
		public static final Integer MDC_115 = 118779;
		public static final Integer MDC_116 = 134079;
		public static final Integer MDC_117 = 2005434;
		public static final Integer MDC_118 = 2005435;
		public static final Integer MDC_119 = 2005439;
		public static final Integer MDC_120 = 2005444;
		public static final Integer MDC_121 = 139545;
		public static final Integer MDC_122 = 130966;
		public static final Integer MDC_123 = 149209;
		public static final Integer MDC_124 = 2004831;
		public static final Integer MDC_125 = 2004832;
		public static final Integer MDC_126 = 2004833;
		public static final Integer MDC_127 = 2004833;
		public static final Integer MDC_128 = 2004834;
		public static final Integer MDC_129 = 2004835;
		public static final Integer MDC_130 = 2004835;
		public static final Integer MDC_131 = 113881;
		public static final Integer MDC_132 = 2004872;
		public static final Integer MDC_133 = 2004717;
		public static final Integer MDC_134 = 2004719;
		public static final Integer MDC_135 = 2004720;
		public static final Integer MDC_136 = 2004722;
		public static final Integer MDC_137 = 2004723;
		public static final Integer MDC_138 = 2004724;
		public static final Integer MDC_139 = 155205;
		public static final Integer MDC_140 = 121320;
		public static final Integer MDC_141 = 148142;
		public static final Integer MDC_142 = 2004768;
		public static final Integer MDC_143 = 2004770;
		public static final Integer MDC_144 = 2004727;
		public static final Integer MDC_145 = 2004729;
		public static final Integer MDC_146 = 2004731;
		public static final Integer MDC_147 = 2004733;
		public static final Integer MDC_148 = 2004734;
		public static final Integer MDC_149 = 2004736;
		public static final Integer MDC_150 = 2004738;
		public static final Integer MDC_151 = 2004739;
		public static final Integer MDC_152 = 2004754;
		public static final Integer MDC_153 = 2004755;
		public static final Integer MDC_154 = 2004756;
		public static final Integer MDC_155 = 2004758;
		public static final Integer MDC_156 = 2004760;
		public static final Integer MDC_157 = 2004761;
		public static final Integer MDC_158 = 2004773;
		public static final Integer MDC_159 = 125901;
		public static final Integer MDC_160 = 2004774;
		public static final Integer MDC_161 = 2004776;
		public static final Integer MDC_162 = 2004777;
		public static final Integer MDC_163 = 2005367;
		public static final Integer MDC_164 = 2005368;
		public static final Integer MDC_165 = 2005369;
		public static final Integer MDC_166 = 2005370;
		public static final Integer MDC_167 = 2005371;
		public static final Integer MDC_168 = 2005372;
		public static final Integer MDC_169 = 2005373;
		public static final Integer MDC_170 = 118678;
		public static final Integer MDC_171 = 2004836;
		public static final Integer MDC_172 = 2004841;
		public static final Integer MDC_173 = 2004842;
		public static final Integer MDC_174 = 2004844;
		public static final Integer MDC_175 = 2004845;
		public static final Integer MDC_176 = 2004846;
		public static final Integer MDC_177 = 2004848;
		public static final Integer MDC_178 = 2004854;
		public static final Integer MDC_179 = 2004855;
		public static final Integer MDC_180 = 2004857;
		public static final Integer MDC_181 = 2004859;
		public static final Integer MDC_182 = 2004860;
		public static final Integer MDC_183 = 2004861;
		public static final Integer MDC_184 = 2004863;
		public static final Integer MDC_185 = 2004864;
		public static final Integer MDC_186 = 112196;
		public static final Integer MDC_187 = 2004866;
		public static final Integer MDC_188 = 2004867;
		public static final Integer MDC_189 = 2004868;
		public static final Integer MDC_190 = 2004870;
		public static final Integer MDC_191 = 2004871;
		public static final Integer MDC_192 = 2004873;
		public static final Integer MDC_193 = 2004875;
		public static final Integer MDC_194 = 127891;
		public static final Integer MDC_195 = 2004883;
		public static final Integer MDC_196 = 2004885;
		public static final Integer MDC_197 = 2004887;
		public static final Integer MDC_198 = 2005138;
		public static final Integer MDC_199 = 2005140;
		public static final Integer MDC_200 = 2005141;
		public static final Integer MDC_201 = 2005142;
		public static final Integer MDC_202 = 2005143;
		public static final Integer MDC_203 = 2005144;
		public static final Integer MDC_204 = 2005145;
		public static final Integer MDC_205 = 2005146;
		public static final Integer MDC_206 = 2005147;
		public static final Integer MDC_207 = 2005148;
		public static final Integer MDC_208 = 121722;
		public static final Integer MDC_209 = 123823;
		public static final Integer MDC_210 = 2005154;
		public static final Integer MDC_211 = 110093;
		public static final Integer MDC_212 = 2005155;
		public static final Integer MDC_213 = 2005156;
		public static final Integer MDC_214 = 2005157;
		public static final Integer MDC_215 = 2005158;
		public static final Integer MDC_216 = 2005159;
		public static final Integer MDC_217 = 2005160;
		public static final Integer MDC_218 = 2005161;
		public static final Integer MDC_219 = 149146;
		public static final Integer MDC_220 = 149147;
		public static final Integer MDC_221 = 2005163;
		public static final Integer MDC_222 = 2005164;
		public static final Integer MDC_223 = 2005166;
		public static final Integer MDC_224 = 2005168;
		public static final Integer MDC_225 = 2005169;
		public static final Integer MDC_226 = 2005170;
		public static final Integer MDC_227 = 2005171;
		public static final Integer MDC_228 = 2005172;
		public static final Integer MDC_229 = 2005173;
		public static final Integer MDC_230 = 2005174;
		public static final Integer MDC_231 = 2005175;
		public static final Integer MDC_232 = 146497;
		public static final Integer MDC_233 = 169528;
		public static final Integer MDC_234 = 2005176;
		public static final Integer MDC_235 = 146491;
		public static final Integer MDC_236 = 2005178;
		public static final Integer MDC_237 = 146493;
		public static final Integer MDC_238 = 2005179;
		public static final Integer MDC_239 = 2005180;
		public static final Integer MDC_240 = 2005182;
		public static final Integer MDC_241 = 2005184;
		public static final Integer MDC_242 = 2005185;
		public static final Integer MDC_243 = 2005186;
		public static final Integer MDC_244 = 2005187;
		public static final Integer MDC_245 = 2005189;
		public static final Integer MDC_246 = 2005195;
		public static final Integer MDC_247 = 2005197;
		public static final Integer MDC_248 = 2005199;
		public static final Integer MDC_249 = 2005201;
		public static final Integer MDC_250 = 2005203;
		public static final Integer MDC_251 = 2005205;
		public static final Integer MDC_252 = 2005209;
		public static final Integer MDC_253 = 2005210;
		public static final Integer MDC_254 = 2005211;
		public static final Integer MDC_255 = 2005212;
		public static final Integer MDC_256 = 2005214;
		public static final Integer MDC_257 = 2005216;
		public static final Integer MDC_258 = 2005218;
		public static final Integer MDC_259 = 2005222;
		public static final Integer MDC_260 = 2005224;
		public static final Integer MDC_261 = 2005226;
		public static final Integer MDC_262 = 2005228;
		public static final Integer MDC_263 = 2004920;
		public static final Integer MDC_264 = 2004990;
		public static final Integer MDC_265 = 2004916;
		public static final Integer MDC_266 = 2004991;
		public static final Integer MDC_267 = 2004992;
		public static final Integer MDC_268 = 131781;
		public static final Integer MDC_269 = 131779;
		public static final Integer MDC_270 = 2004993;
		public static final Integer MDC_271 = 2004995;
		public static final Integer MDC_272 = 2004996;
		public static final Integer MDC_273 = 131778;
		public static final Integer MDC_274 = 2004998;
		public static final Integer MDC_275 = 2004999;
		public static final Integer MDC_276 = 2005001;
		public static final Integer MDC_277 = 2005070;
		public static final Integer MDC_278 = 2005071;
		public static final Integer MDC_279 = 2005072;
		public static final Integer MDC_280 = 2005074;
		public static final Integer MDC_281 = 2005075;
		public static final Integer MDC_282 = 2005076;
		public static final Integer MDC_283 = 2005077;
		public static final Integer MDC_284 = 2005078;
		public static final Integer MDC_285 = 2005078;
		public static final Integer MDC_286 = 2005080;
		public static final Integer MDC_287 = 2005081;
		public static final Integer MDC_288 = 2005083;
		public static final Integer MDC_289 = 2005084;
		public static final Integer MDC_290 = 2005085;
		public static final Integer MDC_291 = 2005086;
		public static final Integer MDC_292 = 2005088;
		public static final Integer MDC_293 = 2005089;
		public static final Integer MDC_294 = 2005090;
		public static final Integer MDC_295 = 2005091;
		public static final Integer MDC_296 = 2005092;
		public static final Integer MDC_297 = 2005093;
		public static final Integer MDC_298 = 2005004;
		public static final Integer MDC_299 = 2005006;
		public static final Integer MDC_300 = 2005007;
		public static final Integer MDC_301 = 2005008;
		public static final Integer MDC_302 = 2005011;
		public static final Integer MDC_303 = 2005012;
		public static final Integer MDC_304 = 2005013;
		public static final Integer MDC_305 = 2005014;
		public static final Integer MDC_306 = 2005015;
		public static final Integer MDC_307 = 2005021;
		public static final Integer MDC_308 = 2005025;
		public static final Integer MDC_309 = 2005026;
		public static final Integer MDC_310 = 2005027;
		public static final Integer MDC_311 = 2005028;
		public static final Integer MDC_312 = 2005030;
		public static final Integer MDC_313 = 2005031;
		public static final Integer MDC_314 = 2005032;
		public static final Integer MDC_315 = 126831;
		public static final Integer MDC_316 = 126832;
		public static final Integer MDC_317 = 2005036;
		public static final Integer MDC_318 = 2005045;
		public static final Integer MDC_319 = 2005038;
		public static final Integer MDC_320 = 2005042;
		public static final Integer MDC_321 = 2005047;
		public static final Integer MDC_322 = 2005049;
		public static final Integer MDC_323 = 144657;
		public static final Integer MDC_324 = 144658;
		public static final Integer MDC_325 = 2005052;
		public static final Integer MDC_326 = 2005053;
		public static final Integer MDC_327 = 2005055;
		public static final Integer MDC_328 = 120332;
		public static final Integer MDC_329 = 144655;
		public static final Integer MDC_330 = 2005056;
		public static final Integer MDC_331 = 144650;
		public static final Integer MDC_332 = 2005059;
		public static final Integer MDC_333 = 2005060;
		public static final Integer MDC_334 = 2005061;
		public static final Integer MDC_335 = 144652;
		public static final Integer MDC_336 = 144653;
		public static final Integer MDC_337 = 2005063;
		public static final Integer MDC_338 = 2005064;
		public static final Integer MDC_339 = 2005065;
		public static final Integer MDC_340 = 2005066;
		public static final Integer MDC_341 = 2005095;
		public static final Integer MDC_342 = 2005097;
		public static final Integer MDC_343 = 2005098;
		public static final Integer MDC_344 = 2005099;
		public static final Integer MDC_345 = 2005100;
		public static final Integer MDC_346 = 2005102;
		public static final Integer MDC_347 = 2005105;
		public static final Integer MDC_348 = 2005106;
		public static final Integer MDC_349 = 2005107;
		public static final Integer MDC_350 = 2005108;
		public static final Integer MDC_351 = 2005109;
		public static final Integer MDC_352 = 2005110;
		public static final Integer MDC_353 = 2005111;
		public static final Integer MDC_354 = 2005111;
		public static final Integer MDC_355 = 2005112;
		public static final Integer MDC_356 = 2005114;
		public static final Integer MDC_357 = 2005115;
		public static final Integer MDC_358 = 2005116;
		public static final Integer MDC_359 = 2005117;
		public static final Integer MDC_360 = 2005119;
		public static final Integer MDC_361 = 2005120;
		public static final Integer MDC_362 = 2005121;
		public static final Integer MDC_363 = 2005122;
		public static final Integer MDC_364 = 2005123;
		public static final Integer MDC_365 = 2005124;
		public static final Integer MDC_366 = 2005126;
		public static final Integer MDC_367 = 2005128;
		public static final Integer MDC_368 = 2005129;
		public static final Integer MDC_369 = 2005130;
		public static final Integer MDC_370 = 120968;
		public static final Integer MDC_371 = 169527;
		public static final Integer MDC_372 = 146594;
		public static final Integer MDC_373 = 2005132;
		public static final Integer MDC_374 = 2005133;
		public static final Integer MDC_375 = 2005135;
		public static final Integer MDC_376 = 2005150;
		public static final Integer MDC_377 = 2005152;
		public static final Integer MDC_378 = 2005190;
		public static final Integer MDC_379 = 2005191;
		public static final Integer MDC_380 = 2005192;
		public static final Integer MDC_381 = 2005193;
		public static final Integer MDC_382 = 2005194;
		public static final Integer MDC_383 = 117713;
		public static final Integer MDC_384 = 2005196;
		public static final Integer MDC_385 = 2005198;
		public static final Integer MDC_386 = 2005202;
		public static final Integer MDC_387 = 139134;
		public static final Integer MDC_388 = 2005204;
		public static final Integer MDC_389 = 2005206;
		public static final Integer MDC_390 = 2005213;
		public static final Integer MDC_391 = 2005217;
		public static final Integer MDC_392 = 2005219;
		public static final Integer MDC_393 = 2005221;
		public static final Integer MDC_394 = 2005225;
		public static final Integer MDC_395 = 2005227;
		public static final Integer MDC_396 = 2005229;
		public static final Integer MDC_397 = 2005230;
		public static final Integer MDC_398 = 2005232;
		public static final Integer MDC_399 = 2005233;
		public static final Integer MDC_400 = 133041;
		public static final Integer MDC_401 = 2005234;
		public static final Integer MDC_402 = 2005235;
		public static final Integer MDC_403 = 2005238;
		public static final Integer MDC_404 = 2005241;
		public static final Integer MDC_405 = 2005242;
		public static final Integer MDC_406 = 2005244;
		public static final Integer MDC_407 = 2005246;
		public static final Integer MDC_408 = 2005247;
		public static final Integer MDC_409 = 2005248;
		public static final Integer MDC_410 = 2005249;
		public static final Integer MDC_411 = 2005250;
		public static final Integer MDC_412 = 2005251;
		public static final Integer MDC_413 = 2005252;
		public static final Integer MDC_414 = 2005253;
		public static final Integer MDC_415 = 2005254;
		public static final Integer MDC_416 = 2005256;
		public static final Integer MDC_417 = 2005257;
		public static final Integer MDC_418 = 2005258;
		public static final Integer MDC_419 = 2005259;
		public static final Integer MDC_420 = 2005261;
		public static final Integer MDC_421 = 2005263;
		public static final Integer MDC_422 = 2005264;
		public static final Integer MDC_423 = 2005265;
		public static final Integer MDC_424 = 2005267;
		public static final Integer MDC_425 = 2005268;
		public static final Integer MDC_426 = 2005269;
		public static final Integer MDC_427 = 2005270;
		public static final Integer MDC_428 = 2005271;
		public static final Integer MDC_429 = 2005103;
		public static final Integer MDC_430 = 2005446;
		public static final Integer MDC_431 = 2005447;
		public static final Integer MDC_432 = 2005449;
		public static final Integer MDC_433 = 2005450;
		public static final Integer MDC_434 = 2005451;
		public static final Integer MDC_435 = 2005452;
		public static final Integer MDC_436 = 2005454;
		public static final Integer MDC_437 = 2005456;
		public static final Integer MDC_438 = 2005457;
		public static final Integer MDC_439 = 2005458;
		public static final Integer MDC_440 = 2005460;
		public static final Integer MDC_441 = 2005461;
		public static final Integer MDC_442 = 2005462;
		public static final Integer MDC_443 = 2005463;
		public static final Integer MDC_444 = 2005464;
		public static final Integer MDC_445 = 2005465;
		public static final Integer MDC_446 = 2005466;
		public static final Integer MDC_447 = 2005467;
		public static final Integer MDC_448 = 2005469;
		public static final Integer MDC_449 = 2005470;
		public static final Integer MDC_450 = 2005471;
		public static final Integer MDC_451 = 2005472;
		public static final Integer MDC_452 = 2005474;
		public static final Integer MDC_453 = 2005476;
		public static final Integer MDC_454 = 2005477;
		public static final Integer MDC_455 = 2005478;
		public static final Integer MDC_456 = 2005480;
		public static final Integer MDC_457 = 2005481;
		public static final Integer MDC_458 = 2005483;
		public static final Integer MDC_459 = 2005484;
		public static final Integer MDC_460 = 2005485;
		public static final Integer MDC_461 = 2005487;
		public static final Integer MDC_462 = 2005488;
		public static final Integer MDC_463 = 2005489;
		public static final Integer MDC_464 = 2005490;
		public static final Integer MDC_465 = 2005491;
		public static final Integer MDC_466 = 2005492;
		public static final Integer MDC_467 = 2005493;
		public static final Integer MDC_468 = 2005495;
		public static final Integer MDC_469 = 2005496;
		public static final Integer MDC_470 = 2005497;
		public static final Integer MDC_471 = 2005498;
		public static final Integer MDC_472 = 2005499;
		public static final Integer MDC_473 = 2005500;
		public static final Integer MDC_474 = 2005502;
		public static final Integer MDC_475 = 2005504;
		public static final Integer MDC_476 = 2005505;
		public static final Integer MDC_477 = 2005506;
		public static final Integer MDC_478 = 2005508;
		public static final Integer MDC_479 = 2005509;
		public static final Integer MDC_480 = 2005510;
		public static final Integer MDC_481 = 2005512;
		public static final Integer MDC_482 = 2005513;
		public static final Integer MDC_483 = 2005515;
		public static final Integer MDC_484 = 2005516;
		public static final Integer MDC_485 = 2005517;
		public static final Integer MDC_486 = 2005518;
		public static final Integer MDC_487 = 2005519;
		public static final Integer MDC_488 = 2005520;
		public static final Integer MDC_489 = 2005521;
		public static final Integer MDC_490 = 2005522;
		public static final Integer MDC_491 = 2005524;
		public static final Integer MDC_492 = 2005525;
		public static final Integer MDC_493 = 2005526;
		public static final Integer MDC_494 = 2005527;
		public static final Integer MDC_495 = 2005528;
		public static final Integer MDC_496 = 2005529;
		public static final Integer MDC_497 = 2005531;
		public static final Integer MDC_498 = 2005533;
		public static final Integer MDC_499 = 2005534;
		public static final Integer MDC_500 = 2005535;
		public static final Integer MDC_501 = 2005537;
		public static final Integer MDC_502 = 2005538;
		public static final Integer MDC_503 = 2005539;
		public static final Integer MDC_504 = 2005540;
		public static final Integer MDC_505 = 2005541;
		public static final Integer MDC_506 = 2005542;
		public static final Integer MDC_507 = 2005243;
		public static final Integer MDC_508 = 2005236;
		public static final Integer MDC_509 = 2007370;
		public static final Integer MDC_510 = 2007371;
		public static final Integer MDC_511 = 2007372;
		public static final Integer MDC_512 = 2007373;
		public static final Integer MDC_513 = 2007375;
		public static final Integer MDC_514 = 2007376;
		public static final Integer MDC_515 = 2007378;
		public static final Integer MDC_516 = 2007379;
		public static final Integer MDC_517 = 2007380;
		public static final Integer MDC_518 = 2007382;
		public static final Integer MDC_519 = 2007382;
		public static final Integer MDC_520 = 2007985;
		public static final Integer MDC_521 = 2007986;
		public static final Integer MDC_522 = 2007987;
		public static final Integer MDC_523 = 2007988;
		public static final Integer MDC_524 = 2007989;
		public static final Integer MDC_525 = 2007990;
		public static final Integer MDC_526 = 2007991;
		public static final Integer MDC_527 = 2005279;
		public static final Integer MDC_528 = 2005280;
		public static final Integer MDC_529 = 2005281;
		public static final Integer MDC_530 = 2005282;
		public static final Integer MDC_531 = 2005283;
		public static final Integer MDC_532 = 2005284;
		public static final Integer MDC_533 = 2005285;
		public static final Integer MDC_534 = 117958;
		public static final Integer MDC_535 = 2005287;
		public static final Integer MDC_536 = 2005288;
		public static final Integer MDC_537 = 2005289;
		public static final Integer MDC_538 = 2005290;
		public static final Integer MDC_539 = 2005292;
		public static final Integer MDC_540 = 2005293;
		public static final Integer MDC_541 = 2005294;
		public static final Integer MDC_542 = 2005295;
		public static final Integer MDC_543 = 119569;
		public static final Integer MDC_544 = 2005295;
		public static final Integer MDC_545 = 2005296;
		public static final Integer MDC_546 = 2005297;
		public static final Integer MDC_547 = 2005298;
		public static final Integer MDC_548 = 2005299;
		public static final Integer MDC_549 = 2005300;
		public static final Integer MDC_550 = 2005300;
		public static final Integer MDC_551 = 2005301;
		public static final Integer MDC_552 = 2005303;
		public static final Integer MDC_553 = 2005304;
		public static final Integer MDC_554 = 2005305;
		public static final Integer MDC_555 = 2005306;
		public static final Integer MDC_556 = 2005307;
		public static final Integer MDC_557 = 2005308;
		public static final Integer MDC_558 = 2005309;
		public static final Integer MDC_559 = 2005310;
		public static final Integer MDC_560 = 2005311;
		public static final Integer MDC_561 = 2005312;
		public static final Integer MDC_562 = 2005316;
		public static final Integer MDC_563 = 2005317;
		public static final Integer MDC_564 = 2005318;
		public static final Integer MDC_565 = 2005319;
		public static final Integer MDC_566 = 2005375;
		public static final Integer MDC_567 = 2005376;
		public static final Integer MDC_568 = 2005437;
		public static final Integer MDC_569 = 2004896;
		public static final Integer MDC_570 = 2004897;
		public static final Integer MDC_571 = 2004896;
		public static final Integer MDC_572 = 2004900;
		public static final Integer MDC_573 = 2004901;
		public static final Integer MDC_574 = 2004902;
		public static final Integer MDC_575 = 2004903;
		public static final Integer MDC_576 = 2004904;
		public static final Integer MDC_577 = 2004905;
		public static final Integer MDC_578 = 2004908;
		public static final Integer MDC_579 = 2004910;
		public static final Integer MDC_580 = 2004912;
		public static final Integer MDC_581 = 2004913;
		public static final Integer MDC_582 = 2004915;
		public static final Integer MDC_583 = 2004918;
		public static final Integer MDC_584 = 2004919;
		public static final Integer MDC_585 = 2004922;
		public static final Integer MDC_586 = 2004923;
		public static final Integer MDC_587 = 2004925;
		public static final Integer MDC_588 = 2004926;
		public static final Integer MDC_589 = 2004930;
		public static final Integer MDC_590 = 2004931;
		public static final Integer MDC_591 = 2004934;
		public static final Integer MDC_592 = 2004936;
		public static final Integer MDC_593 = 2004939;
		public static final Integer MDC_594 = 2004940;
		public static final Integer MDC_595 = 2004943;
		public static final Integer MDC_596 = 2004945;
		public static final Integer MDC_597 = 2004947;
		public static final Integer MDC_598 = 2004948;
		public static final Integer MDC_599 = 2004957;
		public static final Integer MDC_600 = 2004958;
		public static final Integer MDC_601 = 2004961;
		public static final Integer MDC_602 = 2004966;
		public static final Integer MDC_603 = 2004968;
		public static final Integer MDC_604 = 2004969;
		public static final Integer MDC_605 = 2004971;
		public static final Integer MDC_606 = 2004972;
		public static final Integer MDC_607 = 2004975;
		public static final Integer MDC_608 = 146847;
		public static final Integer MDC_609 = 169453;
		public static final Integer MDC_610 = 2004977;
		public static final Integer MDC_611 = 130220;
		public static final Integer MDC_612 = 2004986;
		public static final Integer MDC_613 = 2004985;
		public static final Integer MDC_614 = 2004987;
		public static final Integer MDC_615 = 158534;
		public static final Integer MDC_616 = 141854;
		public static final Integer MDC_617 = 133005;
		public static final Integer MDC_618 = 2004911;
		public static final Integer MDC_619 = 2004933;
		public static final Integer MDC_620 = 2004937;
		public static final Integer MDC_621 = 2004942;
		public static final Integer MDC_622 = 2004946;
		public static final Integer MDC_623 = 2004952;
		public static final Integer MDC_624 = 2004955;
		public static final Integer MDC_625 = 2004959;
		public static final Integer MDC_626 = 2004964;
		public static final Integer MDC_627 = 2004967;
		public static final Integer MDC_628 = 2004970;
		public static final Integer MDC_629 = 2004974;
		public static final Integer MDC_630 = 2007993;
		public static final Integer MDC_631 = 2007994;
		public static final Integer MDC_632 = 2004995;
		public static final Integer MDC_633 = 2007997;
		public static final Integer MDC_634 = 2007998;
		public static final Integer MDC_635 = 2007999;
		public static final Integer MDC_636 = 2008000;
		public static final Integer MDC_637 = 2008001;
		public static final Integer MDC_638 = 127984;
		public static final Integer MDC_639 = 2008002;
		public static final Integer MDC_640 = 136746;
		public static final Integer MDC_641 = 2008003;
		public static final Integer MDC_642 = 2008004;
		public static final Integer MDC_643 = 2008008;
		public static final Integer MDC_644 = 2008011;
		public static final Integer MDC_645 = 2008014;
		public static final Integer MDC_646 = 2008019;
		public static final Integer MDC_647 = 2008021;
		public static final Integer MDC_648 = 2008022;
		public static final Integer MDC_649 = 2008023;
		public static final Integer MDC_650 = 2008008;
		public static final Integer MDC_651 = 2008011;
		public static final Integer MDC_652 = 2008032;
		public static final Integer MDC_653 = 2008033;
		public static final Integer MDC_654 = 2008034;
		public static final Integer MDC_655 = 2008035;
		public static final Integer MDC_656 = 2008036;
		public static final Integer MDC_657 = 2008037;
		public static final Integer MDC_658 = 2008038;
		public static final Integer MDC_659 = 2008039;
		public static final Integer MDC_660 = 2008040;
		public static final Integer MDC_661 = 2008041;
		public static final Integer MDC_662 = 2008043;
		public static final Integer MDC_663 = 2008044;
		public static final Integer MDC_664 = 2008045;
		public static final Integer MDC_665 = 2008046;
		public static final Integer MDC_666 = 2008047;
		public static final Integer MDC_667 = 2008048;
		public static final Integer MDC_668 = 2008049;
		public static final Integer MDC_669 = 2008050;
		public static final Integer MDC_670 = 2008051;
		public static final Integer MDC_671 = 2008052;
		public static final Integer MDC_672 = 2008053;
		public static final Integer MDC_673 = 2008054;
		public static final Integer MDC_674 = 2008055;
		public static final Integer MDC_675 = 2008056;
		public static final Integer MDC_676 = 2008057;
		public static final Integer MDC_677 = 2008058;
		public static final Integer MDC_678 = 2008059;
		public static final Integer MDC_679 = 2008060;
		public static final Integer MDC_680 = 2008061;
		public static final Integer MDC_681 = 2008062;
		public static final Integer MDC_682 = 2008063;
		public static final Integer MDC_683 = 2008064;
		public static final Integer MDC_684 = 2008065;
		public static final Integer MDC_685 = 2008066;
		public static final Integer MDC_686 = 2005272;
		public static final Integer MDC_687 = 2005273;
		public static final Integer MDC_688 = 2005274;
		public static final Integer MDC_689 = 2005275;
		public static final Integer MDC_690 = 2005276;
		public static final Integer MDC_691 = 2005277;
		public static final Integer MDC_692 = 2005314;
		public static final Integer MDC_693 = 2005315;
		public static final Integer MDC_694 = 2005320;
		public static final Integer MDC_695 = 2005321;
		public static final Integer MDC_696 = 2005322;
		public static final Integer MDC_697 = 2005323;
		public static final Integer MDC_698 = 2005324;
		public static final Integer MDC_699 = 2005325;
		public static final Integer MDC_700 = 2005326;
		public static final Integer MDC_701 = 2005328;
		public static final Integer MDC_702 = 2005329;
		public static final Integer MDC_703 = 2005330;
		public static final Integer MDC_704 = 2005332;
		public static final Integer MDC_705 = 2005333;
		public static final Integer MDC_706 = 2005334;
		public static final Integer MDC_707 = 2005335;
		public static final Integer MDC_708 = 2005337;
		public static final Integer MDC_709 = 2005338;
		public static final Integer MDC_710 = 2005339;
		public static final Integer MDC_711 = 2005340;
		public static final Integer MDC_712 = 2005341;
		public static final Integer MDC_713 = 2005342;
		public static final Integer MDC_714 = 2005343;
		public static final Integer MDC_715 = 2005344;
		public static final Integer MDC_716 = 2005345;
		public static final Integer MDC_717 = 2005346;
		public static final Integer MDC_718 = 2005347;
		public static final Integer MDC_719 = 2005348;
		public static final Integer MDC_720 = 2005349;
		public static final Integer MDC_721 = 2005350;
		public static final Integer MDC_722 = 2005351;
		public static final Integer MDC_723 = 2005438;
		public static final Integer MDC_724 = 2005439;
		public static final Integer MDC_725 = 2005440;
		public static final Integer MDC_726 = 2005441;
		public static final Integer MDC_727 = 2005442;
		public static final Integer MDC_728 = 2005443;
		public static final Integer MDC_729 = 2005444;
		public static final Integer MDC_730 = 2005438;
		public static final Integer MDC_731 = 2005438;
		public static final Integer MDC_732 = 2005438;

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
		public static final Integer DDC_51 = 119558;
		public static final Integer DDC_52 = 2006361;
		public static final Integer DDC_53 = 5337;
		public static final Integer DDC_54 = 2006362;
		public static final Integer DDC_55 = 2006363;
		public static final Integer DDC_56 = 2006365;
		public static final Integer DDC_57 = 2006366;
		public static final Integer DDC_58 = 2006367;
		public static final Integer DDC_59 = 2006368;
		public static final Integer DDC_60 = 2006369;
		public static final Integer DDC_61 = 2006370;
		public static final Integer DDC_62 = 2006371;
		public static final Integer DDC_63 = 2006374;
		public static final Integer DDC_64 = 2006375;
		public static final Integer DDC_65 = 2006377;
		public static final Integer DDC_66 = 2006378;
		public static final Integer DDC_67 = 2006379;
		public static final Integer DDC_68 = 2006380;
		public static final Integer DDC_69 = 2006381;
		public static final Integer DDC_70 = 2006382;
		public static final Integer DDC_71 = 2006383;
		public static final Integer DDC_72 = 2006384;
		public static final Integer DDC_73 = 139407;
		public static final Integer DDC_74 = 39495;
		public static final Integer DDC_75 = 148147;
		public static final Integer DDC_76 = 2006393;
		public static final Integer DDC_77 = 138121;
		public static final Integer DDC_78 = 118614;
		public static final Integer DDC_79 = 2006394;
		public static final Integer DDC_80 = 2006395;
		public static final Integer DDC_81 = 148154;
		public static final Integer DDC_82 = 138127;
		public static final Integer DDC_83 = 126523;
		public static final Integer DDC_84 = 150560;
		public static final Integer DDC_85 = 126519;
		public static final Integer DDC_86 = 126519;
		public static final Integer DDC_87 = 133824;
		public static final Integer DDC_88 = 141862;
		public static final Integer DDC_89 = 2006396;
		public static final Integer DDC_90 = 2006397;
		public static final Integer DDC_91 = 2006398;
		public static final Integer DDC_92 = 2006399;
		public static final Integer DDC_93 = 145740;
		public static final Integer DDC_94 = 2006354;
		public static final Integer DDC_95 = 2006355;
		public static final Integer DDC_96 = 2006356;
		public static final Integer DDC_97 = 2006357;
		public static final Integer DDC_98 = 2006358;
		public static final Integer DDC_99 = 2006386;
		public static final Integer DDC_100 = 2006387;
		public static final Integer DDC_101 = 2006388;
		public static final Integer DDC_102 = 2006411;
		public static final Integer DDC_103 = 2006412;
		public static final Integer DDC_104 = 2006413;
		public static final Integer DDC_105 = 2006420;
		public static final Integer DDC_106 = 2006421;
		public static final Integer DDC_107 = 2006422;
		public static final Integer DDC_108 = 128096;
		public static final Integer DDC_109 = 133448;
		public static final Integer DDC_110 = 113480;
		public static final Integer DDC_111 = 2006428;
		public static final Integer DDC_112 = 128098;
		public static final Integer DDC_113 = 150841;
		public static final Integer DDC_114 = 2006429;
		public static final Integer DDC_115 = 114258;
		public static final Integer DDC_116 = 2006430;
		public static final Integer DDC_117 = 114259;
		public static final Integer DDC_118 = 2006431;
		public static final Integer DDC_119 = 149907;
		public static final Integer DDC_120 = 145491;
		public static final Integer DDC_121 = 2006432;
		public static final Integer DDC_122 = 127934;
		public static final Integer DDC_123 = 2006434;
		public static final Integer DDC_124 = 2006435;
		public static final Integer DDC_125 = 148162;
		public static final Integer DDC_126 = 127608;
		public static final Integer DDC_127 = 2006441;
		public static final Integer DDC_128 = 2006442;
		public static final Integer DDC_129 = 2006443;
		public static final Integer DDC_130 = 2006444;
		public static final Integer DDC_131 = 2006445;
		public static final Integer DDC_132 = 2006446;
		public static final Integer DDC_133 = 2006447;
		public static final Integer DDC_134 = 2006448;
		public static final Integer DDC_135 = 2006444;
		public static final Integer DDC_136 = 114250;
		public static final Integer DDC_137 = 121873;
		public static final Integer DDC_138 = 2006449;
		public static final Integer DDC_139 = 2006451;
		public static final Integer DDC_140 = 2006452;
		public static final Integer DDC_141 = 2006453;
		public static final Integer DDC_142 = 2006454;
		public static final Integer DDC_143 = 2006455;
		public static final Integer DDC_144 = 159344;
		public static final Integer DDC_145 = 2006456;
		public static final Integer DDC_146 = 117796;
		public static final Integer DDC_147 = 139443;
		public static final Integer DDC_148 = 2006458;
		public static final Integer DDC_149 = 2006459;
		public static final Integer DDC_150 = 2006460;
		public static final Integer DDC_151 = 2006461;
		public static final Integer DDC_152 = 2006462;
		public static final Integer DDC_153 = 2006465;
		public static final Integer DDC_154 = 2006466;
		public static final Integer DDC_155 = 2006467;
		public static final Integer DDC_156 = 2006468;
		public static final Integer DDC_157 = 2006469;
		public static final Integer DDC_158 = 2006470;
		public static final Integer DDC_159 = 2006471;
		public static final Integer DDC_160 = 2006472;
		public static final Integer DDC_161 = 2006472;
		public static final Integer DDC_162 = 2006473;
		public static final Integer DDC_163 = 2006474;
		public static final Integer DDC_164 = 2006475;
		public static final Integer DDC_165 = 134715;
		public static final Integer DDC_166 = 2006476;
		public static final Integer DDC_167 = 2006477;
		public static final Integer DDC_168 = 2006478;
		public static final Integer DDC_169 = 112454;
		public static final Integer DDC_170 = 112454;
		public static final Integer DDC_171 = 112454;
		public static final Integer DDC_172 = 112454;

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
		public static final Integer BC_44 = 2021060;
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
		public static final Integer BC_101 = 2027370;
		public static final Integer BC_102 = 2027373;
		public static final Integer BC_103 = 2027375;
		public static final Integer BC_104 = 2027378;
		public static final Integer BC_105 = 2027380;
		//snake bites
		public static final Integer SBC_1 = 2028125;
		public static final Integer SBC_2 = 2028128;
		public static final Integer SBC_3 = 2028130;
		public static final Integer SBC_4 = 2030392;
		public static final Integer SBC_5 = 2029073;
		public static final Integer SBC_6 = 2023720;
		public static final Integer SBC_7 = 2021568;
		public static final Integer SBC_8 = 2012002;
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
		public static final Integer DTC_20 = 2008922;
		public static final Integer DTC_21 = 2004325;


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
		public static final Integer EPC_11 = 2005700;
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
		public static final Integer CRPC_11 = 2006007;
		public static final Integer CRPC_12 = 2006008;


		//Autism
		public static final Integer ATC_1 = 2004741;
		public static final Integer ATC_2 = 2004742;
		public static final Integer ATC_3 = 2004743;
		public static final Integer ATC_4 = 2004745;
		public static final Integer ATC_5 = 2004747;
		public static final Integer ATC_6 = 2004748;
		public static final Integer ATC_7 = 2004749;
		public static final Integer ATC_8 = 2004751;


		//Trypanosomiasis

		public static final Integer TRC_1 = 149241;
		public static final Integer TRC_2 = 2002658;
		public static final Integer TRC_3 = 2002660;
		public static final Integer TRC_4 = 2002662;
		public static final Integer TRC_5 = 2002664;
		public static final Integer TRC_6 = 137342;
		public static final Integer TRC_7 = 2002668;
		public static final Integer TRC_8 = 2002669;
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
		public static final Integer SCC_99 = 2003489;
		public static final Integer SCC_100 = 2003490;
		public static final Integer SCC_101 = 149650;
		public static final Integer SCC_102 = 149651;
		public static final Integer SCC_103 = 149649;
		public static final Integer SCC_104 = 2003493;
		public static final Integer SCC_105 = 2003495;
		public static final Integer SCC_106 = 149665;
		public static final Integer SCC_107 = 2003497;
		public static final Integer SCC_108 = 149606;
		public static final Integer SCC_109 = 133576;
		public static final Integer SCC_110 = 2003502;
		public static final Integer SCC_111 = 2003509;
		public static final Integer SCC_112 = 2003511;
		public static final Integer SCC_113 = 2003513;
		public static final Integer SCC_114 = 2003514;
		public static final Integer SCC_115 = 2003516;
		public static final Integer SCC_116 = 2003517;
		public static final Integer SCC_117 = 2003519;
		public static final Integer SCC_118 = 2003521;
		public static final Integer SCC_119 = 2003522;
		public static final Integer SCC_120 = 2003523;
		public static final Integer SCC_121 = 2003524;
		public static final Integer SCC_122 = 2003525;
		public static final Integer SCC_123 = 2003526;
		public static final Integer SCC_124 = 2003527;
		public static final Integer SCC_125 = 2003529;
		public static final Integer SCC_126 = 2003530;
		public static final Integer SCC_127 = 140008;
		public static final Integer SCC_128 = 2003531;
		public static final Integer SCC_129 = 2003533;
		public static final Integer SCC_130 = 2003534;
		public static final Integer SCC_131 = 2003536;
		public static final Integer SCC_132 = 2003537;
		public static final Integer SCC_133 = 2003539;
		public static final Integer SCC_134 = 2003542;
		public static final Integer SCC_135 = 2003543;
		public static final Integer SCC_136 = 2003545;
		public static final Integer SCC_137 = 2003547;
		public static final Integer SCC_138 = 2003548;
		public static final Integer SCC_139 = 2003549;
		public static final Integer SCC_140 = 2003550;
		public static final Integer SCC_141 = 2003552;
		public static final Integer SCC_142 = 2003553;
		public static final Integer SCC_143 = 2003555;
		public static final Integer SCC_144 = 2003556;
		public static final Integer SCC_145 = 2003558;
		public static final Integer SCC_146 = 2003559;
		public static final Integer SCC_147 = 2003560;
		public static final Integer SCC_148 = 2002930;
		public static final Integer SCC_149 = 2003491;
		public static final Integer SCC_150 = 2003492;
		public static final Integer SCC_151 = 2003494;
		public static final Integer SCC_152 = 2003496;
		public static final Integer SCC_153 = 2003498;
		public static final Integer SCC_154 = 2003499;
		public static final Integer SCC_155 = 2003500;
		public static final Integer SCC_156 = 2003501;
		public static final Integer SCC_157 = 2003503;
		public static final Integer SCC_158 = 2003505;
		public static final Integer SCC_159 = 125024;
		public static final Integer SCC_160 = 2003510;
		public static final Integer SCC_161 = 2003512;
		public static final Integer SCC_162 = 2003515;
		public static final Integer SCC_163 = 130083;
		public static final Integer SCC_164 = 2003518;
		public static final Integer SCC_165 = 2003520;
		public static final Integer SCC_166 = 126289;
		public static final Integer SCC_167 = 2003528;
		public static final Integer SCC_168 = 130085;
		public static final Integer SCC_169 = 2003532;
		public static final Integer SCC_170 = 2003535;
		public static final Integer SCC_171 = 2003538;
		public static final Integer SCC_172 = 135794;
		public static final Integer SCC_173 = 2003541;
		public static final Integer SCC_174 = 2003544;
		public static final Integer SCC_175 = 2003546;
		public static final Integer SCC_176 = 149056;
		public static final Integer SCC_177 = 139661;
		public static final Integer SCC_178 = 133831;
		public static final Integer SCC_179 = 2003551;
		public static final Integer SCC_180 = 2003554;
		public static final Integer SCC_181 = 2003557;
		public static final Integer SCC_182 = 2003561;
		public static final Integer SCC_183 = 2003562;
		public static final Integer SCC_184 = 2003564;
		public static final Integer SCC_185 = 2003568;
		public static final Integer SCC_186 = 2003571;
		public static final Integer SCC_187 = 147936;
		public static final Integer SCC_188 = 2003578;
		public static final Integer SCC_189 = 2003582;
		public static final Integer SCC_190 = 2003585;
		public static final Integer SCC_191 = 2003587;
		public static final Integer SCC_192 = 2003593;
		public static final Integer SCC_193 = 2003597;
		public static final Integer SCC_194 = 2003602;
		public static final Integer SCC_195 = 2003607;
		public static final Integer SCC_196 = 2003610;
		public static final Integer SCC_197 = 2003619;
		public static final Integer SCC_198 = 2003621;
		public static final Integer SCC_199 = 2003627;
		public static final Integer SCC_200 = 2003628;
		public static final Integer SCC_201 = 2003630;
		public static final Integer SCC_202 = 2003631;
		public static final Integer SCC_203 = 2003634;
		public static final Integer SCC_204 = 2003645;
		public static final Integer SCC_205 = 2003647;
		public static final Integer SCC_206 = 2003650;
		public static final Integer SCC_207 = 153438;
		public static final Integer SCC_208 = 2003655;
		public static final Integer SCC_209 = 2003658;
		public static final Integer SCC_210 = 2003659;


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
		public static final Integer TBA_11 = 2002881;
		public static final Integer TBA_12 = 2002883;
		public static final Integer TBA_13 = 112066;
		public static final Integer TBA_14 = 112087;
		public static final Integer TBA_15 = 2002884;
		public static final Integer TBA_16 = 2002885;
		public static final Integer TBA_17 = 154164;
		public static final Integer TBA_18 = 2002887;
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
		public static final Integer MOA_1 = 2002523;
		public static final Integer MOA_2 = 141201;
		public static final Integer MOA_3 = 2002524;
		public static final Integer MOA_4 = 2002525;
		public static final Integer MOA_5 = 2002526;
		public static final Integer MOA_6 = 135474;
		public static final Integer MOA_7 = 2002540;
		public static final Integer MOA_8 = 121255;
		public static final Integer MOA_9 = 2002542;
		public static final Integer MOA_10 = 2002543;
		public static final Integer MOA_11 = 2002541;
		public static final Integer MOA_12 = 139736;
		public static final Integer MOA_13 = 2002544;
		public static final Integer MOA_14 = 121211;
		public static final Integer MOA_15 = 2002545;
		public static final Integer MOA_16 = 2002546;
		public static final Integer MOA_17 = 133667;
		public static final Integer MOA_18 = 2002946;
		public static final Integer MOA_19 = 138698;
		public static final Integer MOA_20 = 152206;
		public static final Integer MOA_21 = 146525;
		public static final Integer MOA_22 = 144646;
		public static final Integer MOA_23 = 1294;
		public static final Integer MOA_24 = 2002660;
		public static final Integer MOA_25 = 124076;
		public static final Integer MOA_26 = 2002676;
		public static final Integer MOA_27 = 2002733;
		public static final Integer MOA_28 = 2002778;
		public static final Integer MOA_29 = 2006553;
		public static final Integer MOA_30 = 145722;
		public static final Integer MOA_31 = 2006554;
		public static final Integer MOA_32 = 114406;
		public static final Integer MOA_33 = 2006557;
		public static final Integer MOA_34 = 2006558;
		public static final Integer MOA_35 = 2006559;
		public static final Integer MOA_36 = 2006560;
		public static final Integer MOA_37 = 2006624;
		public static final Integer MOA_38 = 2009405;
		public static final Integer MOA_39 = 2014610;
		public static final Integer MOA_40 = 111967;
		public static final Integer MOA_41 = 125820;
		public static final Integer MOA_42 = 125958;
		public static final Integer MOA_43 = 130095;
		public static final Integer MOA_44 = 134359;
		public static final Integer MOA_45 = 134369;

		//Tetanus
		public static final Integer NTA_1 = 2013066;
		public static final Integer NTA_2 = 2013089;
		public static final Integer NTA_3 = 2013090;
		public static final Integer NTA_4 = 2013091;
		public static final Integer NTA_5 = 2025660;
		public static final Integer NTA_6 = 2025663;
		public static final Integer NTA_7 = 84877;
		public static final Integer NTA_8 = 2025690;
		public static final Integer NTA_9 = 2025973;
		public static final Integer NTA_10 = 2026005;
		public static final Integer NTA_11 = 2026006;
		public static final Integer NTA_12 = 2026007;
		public static final Integer NTA_13 = 2026008;
		public static final Integer NTA_14 = 2026010;
		public static final Integer NTA_15 = 2026011;
		public static final Integer NTA_16 = 2026015;
		public static final Integer NTA_17 = 2026016;
		public static final Integer NTA_18 = 2026017;
		public static final Integer NTA_19 = 2025843;
		public static final Integer NTA_20 = 2025844;
		public static final Integer NTA_21 = 2025845;
		public static final Integer NTA_22 = 2025826;
		public static final Integer NTA_23 = 2025827;
		public static final Integer NTA_24 = 2025828;
		public static final Integer NTA_25 = 2025831;
		public static final Integer NTA_26 = 2025832;
		public static final Integer NTA_27 = 124957;
		public static final Integer NTA_28 = 152276;
		public static final Integer NTA_29 = 124954;


		//Poliomyelitis B
		public static final Integer PMA_1 = 2013072;
		public static final Integer PMA_2 = 2025860;
		public static final Integer PMA_3 = 2025868;
		public static final Integer PMA_4 = 2025869;
		public static final Integer PMA_5 = 2025871;
		public static final Integer PMA_6 = 2025873;
		public static final Integer PMA_7 = 2002520;
		public static final Integer PMA_8 = 158786;

		//CHICKENPOX B
		public static final Integer CPA_1 = 160435;
		public static final Integer CPA_2 = 86110;
		public static final Integer CPA_3 = 2025851;
		public static final Integer CPA_4 = 2025852;
		public static final Integer CPA_5 = 892;
		public static final Integer CPA_6 = 2002945;
		public static final Integer CPA_7 = 2002947;
		public static final Integer CPA_8 = 2002949;
		public static final Integer CPA_9 = 2002948;
		public static final Integer CPA_10 = 2009385;

		//MEASLES B
		public static final Integer MEA_1 = 2015560;
		public static final Integer MEA_2 = 2025938;
		public static final Integer MEA_3 = 159703;
		public static final Integer MEA_4 = 2025886;
		public static final Integer MEA_5 = 134561;
		public static final Integer MEA_6 = 152209;
		public static final Integer MEA_7 = 129469;
		public static final Integer MEA_8 = 152206;
		public static final Integer MEA_9 = 115885;

		//HEPATITIS
		public static final Integer HEA_1 = 142964;
		public static final Integer HEA_2 = 149471;
		public static final Integer HEA_3 = 2002670;
		public static final Integer HEA_4 = 2002673;
		public static final Integer HEA_5 = 149743;
		public static final Integer HEA_6 = 2002675;
		public static final Integer HEA_7 = 152665;
		public static final Integer HEA_8 = 2002839;
		public static final Integer HEA_9 = 2002841;
		public static final Integer HEA_10 = 145131;
		public static final Integer HEA_11 = 2002843;
		public static final Integer HEA_12 = 2002844;
		public static final Integer HEA_13 = 2002846;
		public static final Integer HEA_14 = 2002850;
		public static final Integer HEA_15 = 145347;
		public static final Integer HEA_16 = 2002852;
		public static final Integer HEA_17 = 2002867;
		public static final Integer HEA_18 = 2002868;
		public static final Integer HEA_19 = 2002870;
		public static final Integer HEA_20 = 2002871;
		public static final Integer HEA_21 = 124412;
		public static final Integer HEA_22 = 149153;
		public static final Integer HEA_23 = 2007676;
		public static final Integer HEA_24 = 2007677;
		public static final Integer HEA_25 = 2007678;
		public static final Integer HEA_26 = 2007751;
		public static final Integer HEA_27 = 2007762;
		public static final Integer HEA_28 = 2007764;
		public static final Integer HEA_29 = 2009196;
		public static final Integer HEA_30 = 152080;
		public static final Integer HEA_31 = 2014359;
		public static final Integer HEA_32 = 2014362;
		public static final Integer HEA_33 = 2014316;
		public static final Integer HEA_34 = 2014319;
		public static final Integer HEA_35 = 2014321;
		public static final Integer HEA_36 = 2014325;
		public static final Integer HEA_37 = 2014327;


		//Mumps   MPSA
		public static final Integer MPSA_1 = 2015563;
		public static final Integer MPSA_2 = 133671;
		public static final Integer MPSA_3 = 152238;
		public static final Integer MPSA_4 = 2002518;
		public static final Integer MPSA_5 = 133667;
		public static final Integer MPSA_6 = 133669;
		public static final Integer MPSA_7 = 133661;

		//Tested malaria TMC
		public static final Integer TMC_1 = 152295;
		public static final Integer TMC_2 = 2002634;
		public static final Integer TMC_3 = 2002636;
		public static final Integer TMC_4 = 158379;
		public static final Integer TMC_5 = 2002639;
		public static final Integer TMC_6 = 2002641;
		public static final Integer TMC_7 = 152296;
		public static final Integer TMC_8 = 2002644;
		public static final Integer TMC_9 = 2002646;
		public static final Integer TMC_10 = 116128;
		public static final Integer TMC_11 = 155686;
		public static final Integer TMC_12 = 2017917;

		//Suspected malaria SMC
		public static final Integer SMC_1 = 152295;
		public static final Integer SMC_2 = 2002634;
		public static final Integer SMC_3 = 2002636;
		public static final Integer SMC_4 = 158379;
		public static final Integer SMC_5 = 2002639;
		public static final Integer SMC_6 = 2002641;
		public static final Integer SMC_7 = 2002644;
		public static final Integer SMC_8 = 2002646;
		public static final Integer SMC_9 = 2002649;
		public static final Integer SMC_10 = 2002650;
		public static final Integer SMC_11 = 2002652;
		public static final Integer SMC_12 = 116128;

		//Confirmed Malaria positive  COA

		public static final Integer COA_1 = 152295;
		public static final Integer COA_2 = 2002634;
		public static final Integer COA_3 = 2002636;
		public static final Integer COA_4 = 158379;
		public static final Integer COA_5 = 2002639;
		public static final Integer COA_6 = 2002641;
		public static final Integer COA_7 = 152296;
		public static final Integer COA_8 = 2002644;
		public static final Integer COA_9 = 2002646;
		public static final Integer COA_10 = 2002649;
		public static final Integer COA_11 = 2002650;
		public static final Integer COA_12 = 2002652;
		public static final Integer COA_13 = 116128;

		//Malnutrition

		public static final Integer MALA_1 = 2004982;
		public static final Integer MALA_2 = 2004983;
		public static final Integer MALA_3 = 2004984;
		public static final Integer MALA_4 = 2004988;
		public static final Integer MALA_5 = 2004989;
		public static final Integer MALA_6 = 123056;
		public static final Integer MALA_7 = 2004994;
		public static final Integer MALA_8 = 123054;
		public static final Integer MALA_9 = 2004997;
		public static final Integer MALA_10 = 2005000;
		public static final Integer MALA_11 = 2005002;
		public static final Integer MALA_12 = 2005009;
		public static final Integer MALA_13 = 2005016;
		public static final Integer MALA_14 = 2005017;
		public static final Integer MALA_15 = 2005018;
		public static final Integer MALA_16 = 2005019;
		public static final Integer MALA_17 = 2005020;
		public static final Integer MALA_18 = 2005022;
		public static final Integer MALA_19 = 2005024;
		public static final Integer MALA_20 = 123029;
		public static final Integer MALA_21 = 141696;
		public static final Integer MALA_22 = 122868;
		public static final Integer MALA_23 = 2005033;
		public static final Integer MALA_24 = 2005035;
		public static final Integer MALA_25 = 2005037;
		public static final Integer MALA_26 = 2005039;
		public static final Integer MALA_27 = 2005041;
		public static final Integer MALA_28 = 2005044;
		public static final Integer MALA_29 = 2005046;
		public static final Integer MALA_30 = 2005048;
		public static final Integer MALA_31 = 130654;
		public static final Integer MALA_32 = 2005051;
		public static final Integer MALA_33 = 2005054;
		public static final Integer MALA_34 = 123041;
		public static final Integer MALA_35 = 140018;
		public static final Integer MALA_36 = 2005057;
		public static final Integer MALA_37 = 147315;
		public static final Integer MALA_38 = 2004406;
		public static final Integer MALA_39 = 145602;
		public static final Integer MALA_40 = 136537;
		public static final Integer MALA_41 = 2004408;
		public static final Integer MALA_42 = 2004410;
		public static final Integer MALA_43 = 2004409;
		public static final Integer MALA_44 = 122745;
		public static final Integer MALA_45 = 136556;
		public static final Integer MALA_46 = 2004411;
		public static final Integer MALA_47 = 2004412;
		public static final Integer MALA_48 = 155758;
		public static final Integer MALA_49 = 126810;
		public static final Integer MALA_50 = 145525;
		public static final Integer MALA_51 = 134702;
		public static final Integer MALA_52 = 152426;
		public static final Integer MALA_53 = 2004413;
		public static final Integer MALA_54 = 2004414;
		public static final Integer MALA_55 = 152346;
		public static final Integer MALA_56 = 152351;
		public static final Integer MALA_57 = 152352;
		public static final Integer MALA_58 = 2004416;
		public static final Integer MALA_59 = 2004417;
		public static final Integer MALA_60 = 2004418;
		public static final Integer MALA_61 = 157648;
		public static final Integer MALA_62 = 2009285;
		public static final Integer MALA_63 = 2014336;

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
		public static final Integer STIA_10 = 136112;
		public static final Integer STIA_11 = 143671;
		public static final Integer STIA_12 = 113655;
		public static final Integer STIA_13 = 129119;
		public static final Integer STIA_14 = 129165;
		public static final Integer STIA_15 = 128482;
		public static final Integer STIA_16 = 123595;
		public static final Integer STIA_17 = 136020;
		public static final Integer STIA_18 = 113068;
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
		public static final Integer STIA_70 = 2002832;
		public static final Integer STIA_57 = 145762;
		public static final Integer STIA_58 = 139277;
		public static final Integer STIA_59 = 117146;
		public static final Integer STIA_60 = 112992;
		public static final Integer STIA_61 = 2002845;
		public static final Integer STIA_62 = 2002847;
		public static final Integer STIA_63 = 2002849;
		public static final Integer STIA_64 = 155080;
		public static final Integer STIA_65 = 139505;
		public static final Integer STIA_66 = 2002853;
		public static final Integer STIA_67 = 2002854;
		public static final Integer STIA_68 = 2002856;
		public static final Integer STIA_69 = 2002857;


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
		public static final Integer INA_9 = 2002733;
		public static final Integer INA_10 = 151976;
		public static final Integer INA_11 = 2002734;
		public static final Integer INA_12 = 2002735;
		public static final Integer INA_13 = 2002736;
		public static final Integer INA_14 = 148353;
		public static final Integer INA_15 = 2002737;
		public static final Integer INA_16 = 2002738;
		public static final Integer INA_17 = 2002739;
		public static final Integer INA_18 = 141216;
		public static final Integer INA_19 = 2002746;
		public static final Integer INA_20 = 140993;
		public static final Integer INA_21 = 2002751;
		public static final Integer INA_22 = 2002752;
		public static final Integer INA_23 = 132466;
		public static final Integer INA_24 = 2002753;
		public static final Integer INA_25 = 124130;
		public static final Integer INA_26 = 124129;
		public static final Integer INA_27 = 2002754;
		public static final Integer INA_28 = 2002755;
		public static final Integer INA_29 = 2002756;
		public static final Integer INA_30 = 2002778;
		public static final Integer INA_31 = 2002779;
		public static final Integer INA_32 = 155839;
		public static final Integer INA_33 = 2002780;
		public static final Integer INA_34 = 2002781;
		public static final Integer INA_35 = 2002782;
		public static final Integer INA_36 = 2002789;
		public static final Integer INA_37 = 2002790;
		public static final Integer INA_38 = 2002791;
		public static final Integer INA_39 = 2002792;
		public static final Integer INA_40 = 2002793;
		public static final Integer INA_41 = 145042;
		public static final Integer INA_42 = 145042;
		public static final Integer INA_43 = 142388;
		public static final Integer INA_44 = 2002796;
		public static final Integer INA_45 = 131773;
		public static final Integer INA_46 = 2002797;
		public static final Integer INA_47 = 2002805;
		public static final Integer INA_48 = 2002806;
		public static final Integer INA_49 = 134042;
		public static final Integer INA_50 = 2002808;
		public static final Integer INA_51 = 2002810;
		public static final Integer INA_52 = 2002809;
		public static final Integer INA_53 = 2002811;

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
		public static final Integer EYA_123 = 111183;
		public static final Integer EYA_124 = 118440;
		public static final Integer EYA_125 = 2006648;
		public static final Integer EYA_126 = 2006649;
		public static final Integer EYA_127 = 2006650;
		public static final Integer EYA_128 = 156134;
		public static final Integer EYA_129 = 2006955;
		public static final Integer EYA_130 = 2006957;
		public static final Integer EYA_131 = 143337;
		public static final Integer EYA_132 = 2006977;
		public static final Integer EYA_133 = 149414;
		public static final Integer EYA_134 = 2006979;
		public static final Integer EYA_135 = 2006980;
		public static final Integer EYA_136 = 143349;
		public static final Integer EYA_137 = 2006982;
		public static final Integer EYA_138 = 146835;
		public static final Integer EYA_139 = 126990;
		public static final Integer EYA_140 = 2006990;
		public static final Integer EYA_141 = 2006992;
		public static final Integer EYA_142 = 2006993;
		public static final Integer EYA_143 = 119827;
		public static final Integer EYA_144 = 136296;
		public static final Integer EYA_145 = 143338;
		public static final Integer EYA_146 = 2006997;
		public static final Integer EYA_147 = 2006998;
		public static final Integer EYA_148 = 43365;
		public static final Integer EYA_149 = 143353;
		public static final Integer EYA_150 = 127822;
		public static final Integer EYA_151 = 136298;
		public static final Integer EYA_152 = 2007018;
		public static final Integer EYA_153 = 2007019;
		public static final Integer EYA_154 = 2007076;
		public static final Integer EYA_155 = 2007079;
		public static final Integer EYA_156 = 2007081;
		public static final Integer EYA_157 = 120936;
		public static final Integer EYA_158 = 2007159;
		public static final Integer EYA_159 = 2007161;
		public static final Integer EYA_160 = 136344;
		public static final Integer EYA_161 = 2007162;
		public static final Integer EYA_162 = 2007163;
		public static final Integer EYA_163 = 2007164;
		public static final Integer EYA_164 = 126667;
		public static final Integer EYA_165 = 2007165;
		public static final Integer EYA_166 = 2007166;
		public static final Integer EYA_167 = 2007167;
		public static final Integer EYA_168 = 139423;
		public static final Integer EYA_169 = 2007241;
		public static final Integer EYA_170 = 2007242;
		public static final Integer EYA_171 = 2007244;
		public static final Integer EYA_172 = 2007245;
		public static final Integer EYA_173 = 129164;
		public static final Integer EYA_174 = 2007246;
		public static final Integer EYA_175 = 2007247;
		public static final Integer EYA_176 = 2007248;
		public static final Integer EYA_177 = 2007249;
		public static final Integer EYA_178 = 2007250;
		public static final Integer EYA_179 = 2007251;
		public static final Integer EYA_180 = 2007252;
		public static final Integer EYA_181 = 2007253;
		public static final Integer EYA_182 = 114148;
		public static final Integer EYA_183 = 2007254;
		public static final Integer EYA_184 = 2007255;
		public static final Integer EYA_185 = 2007256;
		public static final Integer EYA_186 = 139421;
		public static final Integer EYA_187 = 2007257;
		public static final Integer EYA_188 = 2007258;
		public static final Integer EYA_189 = 2007259;
		public static final Integer EYA_190 = 2007260;
		public static final Integer EYA_191 = 2007261;
		public static final Integer EYA_192 = 12007262;
		public static final Integer EYA_193 = 2007263;
		public static final Integer EYA_194 = 2007264;
		public static final Integer EYA_195 = 2007265;
		public static final Integer EYA_196 = 126996;
		public static final Integer EYA_197 = 2007266;
		public static final Integer EYA_198 = 2007267;
		public static final Integer EYA_199 = 2007268;
		public static final Integer EYA_200 = 2007269;
		public static final Integer EYA_201 = 145104;
		public static final Integer EYA_202 = 2007270;
		public static final Integer EYA_203 = 2007271;
		public static final Integer EYA_204 = 2007272;
		public static final Integer EYA_205 = 2007273;
		public static final Integer EYA_206 = 2007274;
		public static final Integer EYA_207 = 2007275;
		public static final Integer EYA_208 = 2007276;
		public static final Integer EYA_209 = 2007277;
		public static final Integer EYA_210 = 2007278;
		public static final Integer EYA_211 = 2007279;
		public static final Integer EYA_212 = 2007280;
		public static final Integer EYA_213 = 2007281;
		public static final Integer EYA_214 = 118506;
		public static final Integer EYA_215 = 140914;
		public static final Integer EYA_216 = 2007283;
		public static final Integer EYA_217 = 2007285;
		public static final Integer EYA_218 = 2007286;
		public static final Integer EYA_219 = 2007287;
		public static final Integer EYA_220 = 2007288;
		public static final Integer EYA_221 = 2007289;
		public static final Integer EYA_222 = 115868;
		public static final Integer EYA_223 = 2007290;
		public static final Integer EYA_224 = 2007291;
		public static final Integer EYA_225 = 2007383;
		public static final Integer EYA_226 = 2007384;
		public static final Integer EYA_227 = 139910;
		public static final Integer EYA_228 = 2007385;
		public static final Integer EYA_229 = 2007386;
		public static final Integer EYA_230 = 146072;
		public static final Integer EYA_231 = 2007387;
		public static final Integer EYA_232 = 2007388;
		public static final Integer EYA_233 = 113625;
		public static final Integer EYA_234 = 2007390;
		public static final Integer EYA_235 = 2007391;
		public static final Integer EYA_236 = 2007392;
		public static final Integer EYA_237 = 132460;
		public static final Integer EYA_238 = 2007393;
		public static final Integer EYA_239 = 2007394;
		public static final Integer EYA_240 = 2007396;
		public static final Integer EYA_241 = 2007397;
		public static final Integer EYA_242 = 2007398;
		public static final Integer EYA_243 = 2007399;
		public static final Integer EYA_244 = 2007439;
		public static final Integer EYA_245 = 2007440;
		public static final Integer EYA_246 = 2007441;
		public static final Integer EYA_247 = 2007442;
		public static final Integer EYA_248 = 132461;
		public static final Integer EYA_249 = 2007443;
		public static final Integer EYA_250 = 2007444;
		public static final Integer EYA_251 = 119840;
		public static final Integer EYA_252 = 143392;
		public static final Integer EYA_253 = 2007445;
		public static final Integer EYA_254 = 116717;
		public static final Integer EYA_255 = 153325;
		public static final Integer EYA_256 = 2007447;
		public static final Integer EYA_257 = 2007448;
		public static final Integer EYA_258 = 2007449;
		public static final Integer EYA_259 = 112942;
		public static final Integer EYA_260 = 2007450;
		public static final Integer EYA_261 = 2007451;
		public static final Integer EYA_262 = 2007452;
		public static final Integer EYA_263 = 2007453;
		public static final Integer EYA_264 = 2007454;
		public static final Integer EYA_265 = 2007455;
		public static final Integer EYA_266 = 2007456;
		public static final Integer EYA_267 = 2008409;
		public static final Integer EYA_268 = 2008410;
		public static final Integer EYA_269 = 2008544;
		public static final Integer EYA_270 = 2008568;
		public static final Integer EYA_271 = 2008671;
		public static final Integer EYA_272 = 2008672;
		public static final Integer EYA_273 = 2008673;
		public static final Integer EYA_274 = 2008674;
		public static final Integer EYA_275 = 2008675;
		public static final Integer EYA_276 = 2008676;
		public static final Integer EYA_277 = 111465;
		public static final Integer EYA_278 = 2008678;
		public static final Integer EYA_279 = 2008679;
		public static final Integer EYA_280 = 2008680;
		public static final Integer EYA_281 = 2008681;
		public static final Integer EYA_282 = 2008682;
		public static final Integer EYA_283 = 2008683;
		public static final Integer EYA_284 = 123632;
		public static final Integer EYA_285 = 2008686;
		public static final Integer EYA_286 = 2008687;
		public static final Integer EYA_287 = 2008688;
		public static final Integer EYA_288 = 2008689;
		public static final Integer EYA_289 = 2008690;
		public static final Integer EYA_290 = 2009438;
		public static final Integer EYA_291 = 2009439;
		public static final Integer EYA_292 = 2009440;
		public static final Integer EYA_293 = 2009441;
		public static final Integer EYA_294 = 2009442;
		public static final Integer EYA_295 = 2009443;
		public static final Integer EYA_296 = 133540;
		public static final Integer EYA_297 = 138261;
		public static final Integer EYA_298 = 148271;
		public static final Integer EYA_299 = 129197;
		public static final Integer EYA_300 = 148737;
		public static final Integer EYA_301 = 148742;
		public static final Integer EYA_302 = 124298;
		public static final Integer EYA_303 = 2009444;
		public static final Integer EYA_304 = 2009445;
		public static final Integer EYA_305 = 116720;
		public static final Integer EYA_306 = 119709;
		public static final Integer EYA_307 = 2009447;
		public static final Integer EYA_308 = 2009448;
		public static final Integer EYA_309 = 2009449;
		public static final Integer EYA_310 = 2009450;
		public static final Integer EYA_311 = 2009451;
		public static final Integer EYA_312 = 2009523;
		public static final Integer EYA_313 = 2009524;
		public static final Integer EYA_314 = 2009525;
		public static final Integer EYA_315 = 147215;
		public static final Integer EYA_316 = 2009526;
		public static final Integer EYA_317 = 2009457;
		public static final Integer EYA_318 = 141233;
		public static final Integer EYA_319 = 121503;
		public static final Integer EYA_320 = 2009464;
		public static final Integer EYA_321 = 127348;
		public static final Integer EYA_322 = 2009466;
		public static final Integer EYA_323 = 2009468;
		public static final Integer EYA_324 = 2009469;
		public static final Integer EYA_325 = 2009470;
		public static final Integer EYA_326 = 2009471;
		public static final Integer EYA_327 = 114363;
		public static final Integer EYA_328 = 2009473;
		public static final Integer EYA_329 = 2009474;
		public static final Integer EYA_330 = 2009475;
		public static final Integer EYA_331 = 2009476;
		public static final Integer EYA_332 = 2009478;
		public static final Integer EYA_333 = 2009479;
		public static final Integer EYA_334 = 2009480;
		public static final Integer EYA_335 = 2009481;
		public static final Integer EYA_336 = 2009484;
		public static final Integer EYA_337 = 2009485;
		public static final Integer EYA_338 = 2009486;
		public static final Integer EYA_339 = 2009487;
		public static final Integer EYA_340 = 2009488;
		public static final Integer EYA_341 = 2009489;
		public static final Integer EYA_342 = 123075;
		public static final Integer EYA_343 = 112268;
		public static final Integer EYA_344 = 2009490;
		public static final Integer EYA_345 = 2009492;
		public static final Integer EYA_346 = 2009493;
		public static final Integer EYA_347 = 2009494;
		public static final Integer EYA_348 = 2009495;
		public static final Integer EYA_349 = 2009496;
		public static final Integer EYA_350 = 2009497;
		public static final Integer EYA_351 = 2009498;
		public static final Integer EYA_352 = 2009499;
		public static final Integer EYA_353 = 2009527;
		public static final Integer EYA_354 = 2009529;
		public static final Integer EYA_355 = 2009531;
		public static final Integer EYA_356 = 2010486;
		public static final Integer EYA_357 = 2010487;
		public static final Integer EYA_358 = 2010488;
		public static final Integer EYA_359 = 2010489;
		public static final Integer EYA_360 = 2006644;
		public static final Integer EYA_361 = 2006659;
		public static final Integer EYA_362 = 2006661;
		public static final Integer EYA_363 = 2006666;
		public static final Integer EYA_364 = 2006670;
		public static final Integer EYA_365 = 2006671;
		public static final Integer EYA_366 = 145113;
		public static final Integer EYA_367 = 134503;
		public static final Integer EYA_368 = 130866;
		public static final Integer EYA_369 = 2006686;
		public static final Integer EYA_370 = 2006687;
		public static final Integer EYA_371 = 2006688;
		public static final Integer EYA_372 = 2006689;
		public static final Integer EYA_373 = 2006690;
		public static final Integer EYA_374 = 124145;
		public static final Integer EYA_375 = 2006692;
		public static final Integer EYA_376 = 2006693;
		public static final Integer EYA_377 = 2006694;
		public static final Integer EYA_378 = 2006696;
		public static final Integer EYA_379 = 2006697;
		public static final Integer EYA_380 = 2006698;
		public static final Integer EYA_381 = 2006699;
		public static final Integer EYA_382 = 2006701;
		public static final Integer EYA_383 = 2006702;
		public static final Integer EYA_384 = 2006703;
		public static final Integer EYA_385 = 2006708;
		public static final Integer EYA_386 = 2006709;
		public static final Integer EYA_387 = 2006710;
		public static final Integer EYA_388 = 2006711;
		public static final Integer EYA_389 = 2006712;
		public static final Integer EYA_390 = 147223;
		public static final Integer EYA_391 = 2006713;
		public static final Integer EYA_392 = 2006714;
		public static final Integer EYA_393 = 2006715;
		public static final Integer EYA_394 = 2006706;
		public static final Integer EYA_395 = 2006717;
		public static final Integer EYA_396 = 2006718;
		public static final Integer EYA_397 = 2006719;
		public static final Integer EYA_398 = 2006720;
		public static final Integer EYA_399 = 2006721;
		public static final Integer EYA_400 = 2006722;
		public static final Integer EYA_401 = 2006723;
		public static final Integer EYA_402 = 140955;
		public static final Integer EYA_403 = 2006725;
		public static final Integer EYA_404 = 142958;
		public static final Integer EYA_405 = 143591;
		public static final Integer EYA_406 = 2006726;
		public static final Integer EYA_407 = 2006727;
		public static final Integer EYA_408 = 142957;
		public static final Integer EYA_409 = 2006728;
		public static final Integer EYA_410 = 110472;
		public static final Integer EYA_411 = 2006729;
		public static final Integer EYA_412 = 2006730;
		public static final Integer EYA_413 = 2006731;
		public static final Integer EYA_414 = 2006732;
		public static final Integer EYA_415 = 2006735;
		public static final Integer EYA_416 = 2006736;
		public static final Integer EYA_417 = 2006737;
		public static final Integer EYA_418 = 2006738;
		public static final Integer EYA_419 = 2006739;
		public static final Integer EYA_420 = 2006742;
		public static final Integer EYA_421 = 2006744;
		public static final Integer EYA_422 = 131740;
		public static final Integer EYA_423 = 2006747;
		public static final Integer EYA_424 = 2006749;
		public static final Integer EYA_425 = 131726;
		public static final Integer EYA_426 = 2006751;
		public static final Integer EYA_427 = 2006768;
		public static final Integer EYA_428 = 2006770;
		public static final Integer EYA_429 = 2006772;
		public static final Integer EYA_430 = 2006774;
		public static final Integer EYA_431 = 2006776;
		public static final Integer EYA_432 = 2006779;
		public static final Integer EYA_433 = 2006781;
		public static final Integer EYA_434 = 2006783;
		public static final Integer EYA_435 = 2006786;
		public static final Integer EYA_436 = 2006787;
		public static final Integer EYA_437 = 2006789;
		public static final Integer EYA_438 = 140916;
		public static final Integer EYA_439 = 2006790;
		public static final Integer EYA_440 = 2006791;
		public static final Integer EYA_441 = 2006793;
		public static final Integer EYA_442 = 2006794;
		public static final Integer EYA_443 = 2006795;
		public static final Integer EYA_444 = 2006797;
		public static final Integer EYA_445 = 2006798;
		public static final Integer EYA_446 = 2006799;
		public static final Integer EYA_447 = 131724;
		public static final Integer EYA_448 = 131730;
		public static final Integer EYA_449 = 2006796;
		public static final Integer EYA_450 = 2006800;
		public static final Integer EYA_451 = 2006801;
		public static final Integer EYA_452 = 2006802;
		public static final Integer EYA_453 = 2006803;
		public static final Integer EYA_454 = 130187;
		public static final Integer EYA_455 = 128184;
		public static final Integer EYA_456 = 2006870;
		public static final Integer EYA_457 = 2006871;
		public static final Integer EYA_458 = 2006873;
		public static final Integer EYA_459 = 2006874;
		public static final Integer EYA_460 = 2006875;
		public static final Integer EYA_461 = 2006876;
		public static final Integer EYA_462 = 2006877;
		public static final Integer EYA_463 = 2006878;
		public static final Integer EYA_464 = 2006880;
		public static final Integer EYA_465 = 138097;
		public static final Integer EYA_466 = 2007020;
		public static final Integer EYA_467 = 2007021;
		public static final Integer EYA_468 = 2007022;
		public static final Integer EYA_469 = 2007023;
		public static final Integer EYA_470 = 2007024;
		public static final Integer EYA_471 = 2007026;
		public static final Integer EYA_472 = 2007027;
		public static final Integer EYA_473 = 2007028;
		public static final Integer EYA_474 = 2007029;
		public static final Integer EYA_475 = 2007030;
		public static final Integer EYA_476 = 2007031;
		public static final Integer EYA_477 = 2007032;
		public static final Integer EYA_478 = 2007033;
		public static final Integer EYA_479 = 2007036;
		public static final Integer EYA_480 = 2007035;
		public static final Integer EYA_481 = 153459;
		public static final Integer EYA_482 = 2007038;
		public static final Integer EYA_483 = 2007039;
		public static final Integer EYA_484 = 2007040;
		public static final Integer EYA_485 = 2007041;
		public static final Integer EYA_486 = 2007042;
		public static final Integer EYA_487 = 2007043;
		public static final Integer EYA_488 = 2007044;
		public static final Integer EYA_489 = 2007045;
		public static final Integer EYA_490 = 122660;
		public static final Integer EYA_491 = 2007047;
		public static final Integer EYA_492 = 2007048;
		public static final Integer EYA_493 = 2007049;
		public static final Integer EYA_494 = 2007050;
		public static final Integer EYA_495 = 2007051;
		public static final Integer EYA_496 = 130228;
		public static final Integer EYA_497 = 2007053;
		public static final Integer EYA_498 = 2007054;
		public static final Integer EYA_499 = 2007055;
		public static final Integer EYA_500 = 2007056;
		public static final Integer EYA_501 = 2007057;
		public static final Integer EYA_502 = 2007058;
		public static final Integer EYA_503 = 2007059;
		public static final Integer EYA_504 = 2007060;
		public static final Integer EYA_505 = 2007061;
		public static final Integer EYA_506 = 148477;
		public static final Integer EYA_507 = 2007063;
		public static final Integer EYA_508 = 2007064;
		public static final Integer EYA_509 = 2007065;
		public static final Integer EYA_510 = 2007066;
		public static final Integer EYA_511 = 2007067;
		public static final Integer EYA_512 = 2007068;
		public static final Integer EYA_513 = 148536;
		public static final Integer EYA_514 = 142151;
		public static final Integer EYA_515 = 2007094;
		public static final Integer EYA_516 = 2007096;
		public static final Integer EYA_517 = 2007097;
		public static final Integer EYA_518 = 2007099;
		public static final Integer EYA_519 = 141110;
		public static final Integer EYA_520 = 113132;
		public static final Integer EYA_521 = 127099;
		public static final Integer EYA_522 = 2007102;
		public static final Integer EYA_523 = 2007104;
		public static final Integer EYA_524 = 145536;
		public static final Integer EYA_525 = 2007107;
		public static final Integer EYA_526 = 2007109;
		public static final Integer EYA_527 = 2007110;
		public static final Integer EYA_528 = 145535;
		public static final Integer EYA_529 = 2007113;
		public static final Integer EYA_530 = 2007115;
		public static final Integer EYA_531 = 2007118;
		public static final Integer EYA_532 = 2007120;
		public static final Integer EYA_533 = 2007122;
		public static final Integer EYA_534 = 2007124;
		public static final Integer EYA_535 = 2007126;
		public static final Integer EYA_536 = 2007127;
		public static final Integer EYA_537 = 2007129;
		public static final Integer EYA_538 = 115223;
		public static final Integer EYA_539 = 128401;
		public static final Integer EYA_540 = 142444;
		public static final Integer EYA_541 = 2007131;
		public static final Integer EYA_542 = 138180;
		public static final Integer EYA_543 = 158621;
		public static final Integer EYA_544 = 127525;
		public static final Integer EYA_545 = 2007136;
		public static final Integer EYA_546 = 2007138;
		public static final Integer EYA_547 = 2007140;
		public static final Integer EYA_548 = 2007142;
		public static final Integer EYA_549 = 2007144;
		public static final Integer EYA_550 = 2007146;
		public static final Integer EYA_551 = 142965;
		public static final Integer EYA_552 = 2007151;
		public static final Integer EYA_553 = 2007152;
		public static final Integer EYA_554 = 2007153;
		public static final Integer EYA_555 = 2007154;
		public static final Integer EYA_556 = 2007156;
		public static final Integer EYA_557 = 127564;
		public static final Integer EYA_558 = 2007168;
		public static final Integer EYA_559 = 2007169;
		public static final Integer EYA_560 = 2007170;
		public static final Integer EYA_561 = 2007171;
		public static final Integer EYA_562 = 2007173;
		public static final Integer EYA_563 = 2007174;
		public static final Integer EYA_564 = 2007175;
		public static final Integer EYA_565 = 2007176;
		public static final Integer EYA_566 = 2007177;
		public static final Integer EYA_567 = 2007178;
		public static final Integer EYA_568 = 2007179;
		public static final Integer EYA_569 = 145945;
		public static final Integer EYA_570 = 2007180;
		public static final Integer EYA_571 = 2007181;
		public static final Integer EYA_572 = 2007182;
		public static final Integer EYA_573 = 2007183;
		public static final Integer EYA_574 = 141588;
		public static final Integer EYA_575 = 2007185;
		public static final Integer EYA_576 = 113259;
		public static final Integer EYA_577 = 140834;
		public static final Integer EYA_578 = 127534;
		public static final Integer EYA_579 = 127537;
		public static final Integer EYA_580 = 2007187;
		public static final Integer EYA_581 = 2007188;
		public static final Integer EYA_582 = 2007190;
		public static final Integer EYA_583 = 2007191;
		public static final Integer EYA_584 = 2007192;
		public static final Integer EYA_585 = 130412;
		public static final Integer EYA_590 = 127551;
		public static final Integer EYA_591 = 126670;
		public static final Integer EYA_592 = 138869;
		public static final Integer EYA_593 = 2007194;
		public static final Integer EYA_594 = 2007195;
		public static final Integer EYA_595 = 127554;
		public static final Integer EYA_596 = 127550;
		public static final Integer EYA_597 = 2007196;
		public static final Integer EYA_598 = 2007197;
		public static final Integer EYA_599 = 2007198;
		public static final Integer EYA_600 = 2007199;
		public static final Integer EYA_601 = 129421;
		public static final Integer EYA_602 = 123011;
		public static final Integer EYA_603 = 123015;
		public static final Integer EYA_604 = 2007200;
		public static final Integer EYA_605 = 2007201;
		public static final Integer EYA_606 = 2007202;
		public static final Integer EYA_607 = 2007203;
		public static final Integer EYA_608 = 2007204;
		public static final Integer EYA_609 = 2007205;
		public static final Integer EYA_610 = 2007206;
		public static final Integer EYA_611 = 128058;
		public static final Integer EYA_612 = 2007207;
		public static final Integer EYA_613 = 2007208;
		public static final Integer EYA_614 = 125188;
		public static final Integer EYA_615 = 2007209;
		public static final Integer EYA_616 = 2007210;
		public static final Integer EYA_617 = 2007212;
		public static final Integer EYA_618 = 130231;
		public static final Integer EYA_619 = 2007213;
		public static final Integer EYA_620 = 2007214;
		public static final Integer EYA_621 = 2007215;
		public static final Integer EYA_622 = 2007216;
		public static final Integer EYA_623 = 2007217;
		public static final Integer EYA_624 = 2007218;
		public static final Integer EYA_625 = 113252;
		public static final Integer EYA_626 = 2007219;
		public static final Integer EYA_627 = 2007220;
		public static final Integer EYA_628 = 2007221;
		public static final Integer EYA_629 = 148618;
		public static final Integer EYA_630 = 129428;
		public static final Integer EYA_631 = 2007222;
		public static final Integer EYA_632 = 2007223;
		public static final Integer EYA_633 = 2007225;
		public static final Integer EYA_634 = 2007226;
		public static final Integer EYA_635 = 2007227;
		public static final Integer EYA_636 = 124182;
		public static final Integer EYA_637 = 114367;
		public static final Integer EYA_638 = 2007229;
		public static final Integer EYA_639 = 2007230;
		public static final Integer EYA_640 = 2007231;
		public static final Integer EYA_641 = 2007232;
		public static final Integer EYA_642 = 2007233;
		public static final Integer EYA_643 = 2007234;
		public static final Integer EYA_644 = 2007235;
		public static final Integer EYA_645 = 2007236;
		public static final Integer EYA_646 = 123629;
		public static final Integer EYA_647 = 131761;
		public static final Integer EYA_648 = 2007237;
		public static final Integer EYA_649 = 2007238;
		public static final Integer EYA_650 = 2007239;
		public static final Integer EYA_651 = 157426;
		public static final Integer EYA_652 = 2009452;
		public static final Integer EYA_653 = 2009453;
		public static final Integer EYA_654 = 2009454;
		public static final Integer EYA_655 = 2009455;
		public static final Integer EYA_656 = 2009456;
		public static final Integer EYA_657 = 2010490;
		public static final Integer EYA_658 = 2010491;

		//     Ear Infection EIA  
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
		public static final Integer EIA_66 = 130689;
		public static final Integer EIA_67 = 114179;
		public static final Integer EIA_68 = 145611;
		public static final Integer EIA_69 = 145950;
		public static final Integer EIA_70 = 148141;
		public static final Integer EIA_71 = 2010527;
		public static final Integer EIA_72 = 2010528;
		public static final Integer EIA_73 = 2010529;
		public static final Integer EIA_74 = 121934;
		public static final Integer EIA_75 = 2010530;
		public static final Integer EIA_76 = 111761;
		public static final Integer EIA_77 = 121796;
		public static final Integer EIA_78 = 2010531;
		public static final Integer EIA_79 = 2010532;
		public static final Integer EIA_80 = 2010533;
		public static final Integer EIA_81 = 2010534;
		public static final Integer EIA_82 = 2010535;
		public static final Integer EIA_83 = 2010536;
		public static final Integer EIA_84 = 2010537;
		public static final Integer EIA_85 = 2010538;
		public static final Integer EIA_86 = 2010586;
		public static final Integer EIA_87 = 2010575;
		public static final Integer EIA_88 = 2010576;
		public static final Integer EIA_89 = 2010577;
		public static final Integer EIA_90 = 2010578;
		public static final Integer EIA_91 = 2010579;
		public static final Integer EIA_92 = 2010581;
		public static final Integer EIA_93 = 2010582;
		public static final Integer EIA_94 = 2010583;
		public static final Integer EIA_95 = 2010584;
		public static final Integer EIA_96 = 2010585;
		public static final Integer EIA_97 = 129199;
		public static final Integer EIA_98 = 2010589;
		public static final Integer EIA_99 = 2010588;
		public static final Integer EIA_100 = 2010590;
		public static final Integer EIA_101 = 2010591;
		public static final Integer EIA_102 = 2010592;
		public static final Integer EIA_103 = 20105;
		public static final Integer EIA_104 = 2010587;
		public static final Integer EIA_105 = 146078;
		public static final Integer EIA_106 = 2010540;
		public static final Integer EIA_107 = 136194;
		public static final Integer EIA_108 = 2010541;
		public static final Integer EIA_109 = 2010542;
		public static final Integer EIA_110 = 2010544;
		public static final Integer EIA_111 = 2010545;
		public static final Integer EIA_112 = 2010550;
		public static final Integer EIA_113 = 2010552;
		public static final Integer EIA_114 = 2010553;
		public static final Integer EIA_115 = 2010554;
		public static final Integer EIA_116 = 2010555;
		public static final Integer EIA_117 = 2010556;
		public static final Integer EIA_118 = 2010557;
		public static final Integer EIA_119 = 2010559;
		public static final Integer EIA_120 = 2010561;
		public static final Integer EIA_121 = 2010570;
		public static final Integer EIA_122 = 115226;
		public static final Integer EIA_123 = 151702;
		public static final Integer EIA_124 = 2010594;
		public static final Integer EIA_125 = 131602;
		public static final Integer EIA_126 = 2010596;
		public static final Integer EIA_127 = 2010595;
		public static final Integer EIA_128 = 150122;
		public static final Integer EIA_129 = 2010597;
		public static final Integer EIA_130 = 2010598;
		public static final Integer EIA_131 = 2010599;
		public static final Integer EIA_132 = 127837;
		public static final Integer EIA_133 = 133800;
		public static final Integer EIA_134 = 139285;
		public static final Integer EIA_135 = 145320;
		public static final Integer EIA_136 = 2010600;
		public static final Integer EIA_137 = 2010601;

		// PNEUMONIA 

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

		//ABORTION 

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
		public static final Integer ABA_46 = 150904;
		public static final Integer ABA_47 = 127238;
		public static final Integer ABA_48 = 14423;
		public static final Integer ABA_49 = 2008859;
		public static final Integer ABA_50 = 2008860;
		public static final Integer ABA_51 = 144515;
		public static final Integer ABA_52 = 2008861;
		public static final Integer ABA_53 = 2008862;
		public static final Integer ABA_54 = 2008863;
		public static final Integer ABA_55 = 134100;
		public static final Integer ABA_56 = 2008864;
		public static final Integer ABA_57 = 2008866;
		public static final Integer ABA_58 = 142615;
		public static final Integer ABA_59 = 2008867;
		public static final Integer ABA_60 = 2008868;
		public static final Integer ABA_61 = 2008869;
		public static final Integer ABA_62 = 2008870;
		public static final Integer ABA_63 = 119671;
		public static final Integer ABA_64 = 2008871;
		public static final Integer ABA_65 = 2008872;
		public static final Integer ABA_66 = 2008873;
		public static final Integer ABA_67 = 2008874;
		public static final Integer ABA_68 = 156220;
		public static final Integer ABA_69 = 156220;


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
		public static final Integer HYA_36 = 117386;
		public static final Integer HYA_37 = 2010607;

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

	
		public static final Integer ENT = 160455; //160455


		public static final Integer STI = 160546; //160546

		
		public static final String SC = "db1ba5ed-1da0-430d-9744-4dcc006f98b0"; //1000047

	

		public static final Integer MALARIA = 123;




		//malaria confirmed
		public static final Integer POSITIVE = 703;

		//Malaria suspected
		public static final Integer NEGATIVE = 664;

		public static final Integer INDETERMINATE = 1138;

	

		//Diagnosis concepts
		public static final String PROBLEM_ADDED = "6042AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

		

		//Asthma 	
		public static final Integer ASTHMA1 = 2005833;
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
		public static final Integer ASTHMA59 = 4;
		public static final Integer ASTHMA60 = 2005821;
		public static final Integer ASTHMA61 = 2005822;
		public static final Integer ASTHMA62 = 2005824;
		public static final Integer ASTHMA63 = 2005825;
		public static final Integer ASTHMA64 = 2005829;
		public static final Integer ASTHMA65 = 2005831;
		public static final Integer ASTHMA66 = 2005832;
		public static final Integer ASTHMA67 = 143261;
		public static final Integer ASTHMA68 = 2005834;
		public static final Integer ASTHMA69 = 2005835;
		public static final Integer ASTHMA70 = 2005836;
		public static final Integer ASTHMA71 = 2006074;
		public static final Integer ASTHMA72 = 121375;
		public static final Integer ASTHMA73 = 121699;
		public static final Integer ASTHMA74 = 2005823;
		public static final Integer ASTHMA75 = 2005830;


		
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

	}


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
			} catch (Exception e) {
			}
		}
		if (c == null) {
			try {
				c = Context.getConceptService().getConcept(Integer.parseInt(lookup));
			} catch (Exception e) {
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

		Integer DYSENTERY = 2014827;
		Integer CHOLERA = 2002391;
		Integer ANAEMIA = 1226;
		Integer MENINGOCOCCAL_MENINGITIS = 134369;
		Integer NEONATAL_SEPSIS = 226;
		Integer NEONATAL_TETANUS = 124954;
		Integer JIGGERS_INFESTATION = 166567;
		Integer DISEASES_OF_SKIN = 2009210;
		Integer DOWN_SYNDROME = 144481;
		Integer POISONING = 2027745;
		Integer BRUCELLOSIS = 121005;
		Integer VIRAL_HAEMORRHAGIC_FEVER = 2002647;
		Integer CUTANEOUS_LEISHMANIASIS = 143074;
		Integer SUSPECTED_ANTHRAX = 121555;
		Integer HYPOXAEMIA = 2006141;
		List<Integer> allNonListedDiagnosisForUnder5 = new ArrayList<Integer>();

		allNonListedDiagnosisForUnder5.add(DYSENTERY);
		allNonListedDiagnosisForUnder5.add(CHOLERA);
		allNonListedDiagnosisForUnder5.add(ANAEMIA);
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
		return allNonListedDiagnosisForUnder5;
	}

	/*
	 * Provides a list of diagnosis which are not mapped as a list for above 5
	 *
	 * */
	public static List<Integer> allNonListedDiagnosisForAbove5() {
		Integer MENINGOCOCCAL_MENINGITIS = 134369;
		Integer MALARIA_IN_PREGNANCY = 135361;
		Integer JIGGERS_INFESTATION = 166567;
		Integer BRUCELLOCIS = 121005;
		Integer PHYSICAL_DISABILITY = 2014582;
		Integer VIRAL_HAEMORRHAGIC_FEVER = 2002647;
		Integer CUTANEOUS_LEISHMANIASIS = 143074;
		Integer SUSPECTED_ANTHRAX = 121555;

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
