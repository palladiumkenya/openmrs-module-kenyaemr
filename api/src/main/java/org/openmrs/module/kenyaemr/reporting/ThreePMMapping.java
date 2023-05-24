/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting;

import java.util.HashMap;

public class ThreePMMapping {
    public static String get3PMIndicatorId(String indicatorDisaggr) {

        HashMap<String, String> map = new HashMap<String, String>();

       // Care & Treatment
        map.put("CTV3: Current on treatment-01", "FEyLrThIhS3-slGBtK9NsjZ");
        map.put("CTV3: Current on treatment-02", "FEyLrThIhS3-RL2LAAV8h9r");
        map.put("CTV3: Current on treatment-03", "FEyLrThIhS3-ja1pGb0xZqC");
        map.put("CTV3: Current on treatment-04", "FEyLrThIhS3-Aoyz53eT06v");
        map.put("CTV3: Current on treatment-05", "FEyLrThIhS3-B49DkRhRjFU");
        map.put("CTV3: Current on treatment-06", "FEyLrThIhS3-jWGYebQ1pCn");
        map.put("CTV3: Current on treatment-07", "FEyLrThIhS3-haYMHQSFfFi");
        map.put("CTV3: Current on treatment-08", "FEyLrThIhS3-ZhI3TLG2Iea");

        map.put("CTV3: On DTG-based regimen-01","NMk500a98zP-slGBtK9NsjZ");
        map.put("CTV3: On DTG-based regimen-02","NMk500a98zP-RL2LAAV8h9r");
        map.put("CTV3: On DTG-based regimen-03","NMk500a98zP-ja1pGb0xZqC");
        map.put("CTV3: On DTG-based regimen-04","NMk500a98zP-Aoyz53eT06v");
        map.put("CTV3: On DTG-based regimen-05","NMk500a98zP-B49DkRhRjFU");
        map.put("CTV3: On DTG-based regimen-06","NMk500a98zP-jWGYebQ1pCn");
        map.put("CTV3: On DTG-based regimen-07","NMk500a98zP-haYMHQSFfFi");
        map.put("CTV3: On DTG-based regimen-08","NMk500a98zP-ZhI3TLG2Iea");

        map.put("CTV3: Stopped treatment-01","a93ZnTk0bBs-slGBtK9NsjZ");
        map.put("CTV3: Stopped treatment-02","a93ZnTk0bBs-RL2LAAV8h9r");
        map.put("CTV3: Stopped treatment-03","a93ZnTk0bBs-ja1pGb0xZqC");
        map.put("CTV3: Stopped treatment-04","a93ZnTk0bBs-Aoyz53eT06v");
        map.put("CTV3: Stopped treatment-05","a93ZnTk0bBs-B49DkRhRjFU");
        map.put("CTV3: Stopped treatment-06","a93ZnTk0bBs-jWGYebQ1pCn");
        map.put("CTV3: Stopped treatment-07","a93ZnTk0bBs-haYMHQSFfFi");
        map.put("CTV3: Stopped treatment-08","a93ZnTk0bBs-ZhI3TLG2Iea");

        map.put("CTV3: Lost (LTFU) (> 30 days)-01","AceTOuNrxjX-slGBtK9NsjZ");
        map.put("CTV3: Lost (LTFU) (> 30 days)-02","AceTOuNrxjX-RL2LAAV8h9r");
        map.put("CTV3: Lost (LTFU) (> 30 days)-03","AceTOuNrxjX-ja1pGb0xZqC");
        map.put("CTV3: Lost (LTFU) (> 30 days)-04","AceTOuNrxjX-Aoyz53eT06v");
        map.put("CTV3: Lost (LTFU) (> 30 days)-05","AceTOuNrxjX-B49DkRhRjFU");
        map.put("CTV3: Lost (LTFU) (> 30 days)-06","AceTOuNrxjX-jWGYebQ1pCn");
        map.put("CTV3: Lost (LTFU) (> 30 days)-07","AceTOuNrxjX-haYMHQSFfFi");
        map.put("CTV3: Lost (LTFU) (> 30 days)-08","AceTOuNrxjX-ZhI3TLG2Iea");

        map.put("CTV3: Current on treatment - (Number with Hypertension)-01","kw898hEHmOr-slGBtK9NsjZ");
        map.put("CTV3: Current on treatment - (Number with Hypertension)-02","kw898hEHmOr-RL2LAAV8h9r");
        map.put("CTV3: Current on treatment - (Number with Hypertension)-03","kw898hEHmOr-ja1pGb0xZqC");
        map.put("CTV3: Current on treatment - (Number with Hypertension)-04","kw898hEHmOr-Aoyz53eT06v");
        map.put("CTV3: Current on treatment - (Number with Hypertension)-05","kw898hEHmOr-B49DkRhRjFU");
        map.put("CTV3: Current on treatment - (Number with Hypertension)-06","kw898hEHmOr-jWGYebQ1pCn");
        map.put("CTV3: Current on treatment - (Number with Hypertension)-07","kw898hEHmOr-haYMHQSFfFi");
        map.put("CTV3: Current on treatment - (Number with Hypertension)-08","kw898hEHmOr-ZhI3TLG2Iea");

        map.put("CTV3: Current on treatment - (Number with Hypertension, well controlled)-01","Ay7s58hdbxW-slGBtK9NsjZ");
        map.put("CTV3: Current on treatment - (Number with Hypertension, well controlled)-02","Ay7s58hdbxW-RL2LAAV8h9r");
        map.put("CTV3: Current on treatment - (Number with Hypertension, well controlled)-03","Ay7s58hdbxW-ja1pGb0xZqC");
        map.put("CTV3: Current on treatment - (Number with Hypertension, well controlled)-04","Ay7s58hdbxW-Aoyz53eT06v");
        map.put("CTV3: Current on treatment - (Number with Hypertension, well controlled)-05","Ay7s58hdbxW-B49DkRhRjFU");
        map.put("CTV3: Current on treatment - (Number with Hypertension, well controlled)-06","Ay7s58hdbxW-jWGYebQ1pCn");
        map.put("CTV3: Current on treatment - (Number with Hypertension, well controlled)-07","Ay7s58hdbxW-haYMHQSFfFi");
        map.put("CTV3: Current on treatment - (Number with Hypertension, well controlled)-08","Ay7s58hdbxW-ZhI3TLG2Iea");

        map.put("CTV3: Eligible for routine vl-01","Ag9cqaruIkv-slGBtK9NsjZ");
        map.put("CTV3: Eligible for routine vl-02","Ag9cqaruIkv-RL2LAAV8h9r");
        map.put("CTV3: Eligible for routine vl-03","Ag9cqaruIkv-ja1pGb0xZqC");
        map.put("CTV3: Eligible for routine vl-04","Ag9cqaruIkv-Aoyz53eT06v");
        map.put("CTV3: Eligible for routine vl-05","Ag9cqaruIkv-B49DkRhRjFU");
        map.put("CTV3: Eligible for routine vl-06","Ag9cqaruIkv-jWGYebQ1pCn");
        map.put("CTV3: Eligible for routine vl-07","Ag9cqaruIkv-haYMHQSFfFi");
        map.put("CTV3: Eligible for routine vl-08","Ag9cqaruIkv-ZhI3TLG2Iea");

        map.put("CTV3: Transfer outs-01","C1Vh1xi4yZw-slGBtK9NsjZ");
        map.put("CTV3: Transfer outs-02","C1Vh1xi4yZw-RL2LAAV8h9r");
        map.put("CTV3: Transfer outs-03","C1Vh1xi4yZw-ja1pGb0xZqC");
        map.put("CTV3: Transfer outs-04","C1Vh1xi4yZw-Aoyz53eT06v");
        map.put("CTV3: Transfer outs-05","C1Vh1xi4yZw-B49DkRhRjFU");
        map.put("CTV3: Transfer outs-06","C1Vh1xi4yZw-jWGYebQ1pCn");
        map.put("CTV3: Transfer outs-07","C1Vh1xi4yZw-haYMHQSFfFi");
        map.put("CTV3: Transfer outs-08","C1Vh1xi4yZw-ZhI3TLG2Iea");

        map.put("CTV3: Current on treatment - (Number with Diabetes, well controlled)-01","ENMPiMCwLdy-slGBtK9NsjZ");
        map.put("CTV3: Current on treatment - (Number with Diabetes, well controlled)-02","ENMPiMCwLdy-RL2LAAV8h9r");
        map.put("CTV3: Current on treatment - (Number with Diabetes, well controlled)-03","ENMPiMCwLdy-ja1pGb0xZqC");
        map.put("CTV3: Current on treatment - (Number with Diabetes, well controlled)-04","ENMPiMCwLdy-Aoyz53eT06v");
        map.put("CTV3: Current on treatment - (Number with Diabetes, well controlled)-05","ENMPiMCwLdy-B49DkRhRjFU");
        map.put("CTV3: Current on treatment - (Number with Diabetes, well controlled)-06","ENMPiMCwLdy-jWGYebQ1pCn");
        map.put("CTV3: Current on treatment - (Number with Diabetes, well controlled)-07","ENMPiMCwLdy-haYMHQSFfFi");
        map.put("CTV3: Current on treatment - (Number with Diabetes, well controlled)-08","ENMPiMCwLdy-ZhI3TLG2Iea");

        map.put("CTV3: With viral load results 200 - 999cp/ml-01","f7XdPzmNt5A-slGBtK9NsjZ");
        map.put("CTV3: With viral load results 200 - 999cp/ml-02","f7XdPzmNt5A-RL2LAAV8h9r");
        map.put("CTV3: With viral load results 200 - 999cp/ml-03","f7XdPzmNt5A-ja1pGb0xZqC");
        map.put("CTV3: With viral load results 200 - 999cp/ml-04","f7XdPzmNt5A-Aoyz53eT06v");
        map.put("CTV3: With viral load results 200 - 999cp/ml-05","f7XdPzmNt5A-B49DkRhRjFU");
        map.put("CTV3: With viral load results 200 - 999cp/ml-06","f7XdPzmNt5A-jWGYebQ1pCn");
        map.put("CTV3: With viral load results 200 - 999cp/ml-07","f7XdPzmNt5A-haYMHQSFfFi");
        map.put("CTV3: With viral load results 200 - 999cp/ml-08","f7XdPzmNt5A-ZhI3TLG2Iea");

        map.put("CTV3: On MMS/MMD/DSD-01","g2URVG9t2Zt-slGBtK9NsjZ");
        map.put("CTV3: On MMS/MMD/DSD-02","g2URVG9t2Zt-RL2LAAV8h9r");
        map.put("CTV3: On MMS/MMD/DSD-03","g2URVG9t2Zt-ja1pGb0xZqC");
        map.put("CTV3: On MMS/MMD/DSD-04","g2URVG9t2Zt-Aoyz53eT06v");
        map.put("CTV3: On MMS/MMD/DSD-05","g2URVG9t2Zt-B49DkRhRjFU");
        map.put("CTV3: On MMS/MMD/DSD-06","g2URVG9t2Zt-jWGYebQ1pCn");
        map.put("CTV3: On MMS/MMD/DSD-07","g2URVG9t2Zt-haYMHQSFfFi");
        map.put("CTV3: On MMS/MMD/DSD-08","g2URVG9t2Zt-ZhI3TLG2Iea");

        map.put("CTV3: New on treatment-01","jW4YS9IZ5xy-slGBtK9NsjZ");
        map.put("CTV3: New on treatment-02","jW4YS9IZ5xy-RL2LAAV8h9r");
        map.put("CTV3: New on treatment-03","jW4YS9IZ5xy-ja1pGb0xZqC");
        map.put("CTV3: New on treatment-04","jW4YS9IZ5xy-Aoyz53eT06v");
        map.put("CTV3: New on treatment-05","jW4YS9IZ5xy-B49DkRhRjFU");
        map.put("CTV3: New on treatment-06","jW4YS9IZ5xy-jWGYebQ1pCn");
        map.put("CTV3: New on treatment-07","jW4YS9IZ5xy-haYMHQSFfFi");
        map.put("CTV3: New on treatment-08","jW4YS9IZ5xy-ZhI3TLG2Iea");

        map.put("CTV3: Previously lost but returned to treatment (Tx_RTT)-01","qRey76zo22U-slGBtK9NsjZ");
        map.put("CTV3: Previously lost but returned to treatment (Tx_RTT)-02","qRey76zo22U-RL2LAAV8h9r");
        map.put("CTV3: Previously lost but returned to treatment (Tx_RTT)-03","qRey76zo22U-ja1pGb0xZqC");
        map.put("CTV3: Previously lost but returned to treatment (Tx_RTT)-04","qRey76zo22U-Aoyz53eT06v");
        map.put("CTV3: Previously lost but returned to treatment (Tx_RTT)-05","qRey76zo22U-B49DkRhRjFU");
        map.put("CTV3: Previously lost but returned to treatment (Tx_RTT)-06","qRey76zo22U-jWGYebQ1pCn");
        map.put("CTV3: Previously lost but returned to treatment (Tx_RTT)-07","qRey76zo22U-haYMHQSFfFi");
        map.put("CTV3: Previously lost but returned to treatment (Tx_RTT)-08","qRey76zo22U-ZhI3TLG2Iea");

        map.put("CTV3: With vl results < 400cp/ml (includes LDL)-01","Pa2rsZAcORE-slGBtK9NsjZ");
        map.put("CTV3: With vl results < 400cp/ml (includes LDL)-02","Pa2rsZAcORE-RL2LAAV8h9r");
        map.put("CTV3: With vl results < 400cp/ml (includes LDL)-03","Pa2rsZAcORE-ja1pGb0xZqC");
        map.put("CTV3: With vl results < 400cp/ml (includes LDL)-04","Pa2rsZAcORE-Aoyz53eT06v");
        map.put("CTV3: With vl results < 400cp/ml (includes LDL)-05","Pa2rsZAcORE-B49DkRhRjFU");
        map.put("CTV3: With vl results < 400cp/ml (includes LDL)-06","Pa2rsZAcORE-jWGYebQ1pCn");
        map.put("CTV3: With vl results < 400cp/ml (includes LDL)-07","Pa2rsZAcORE-haYMHQSFfFi");
        map.put("CTV3: With vl results < 400cp/ml (includes LDL)-08","Pa2rsZAcORE-ZhI3TLG2Iea");

        map.put("CTV3: Number switched to 3rd line-01","ZdrEgOTb0vy-slGBtK9NsjZ");
        map.put("CTV3: Number switched to 3rd line-02","ZdrEgOTb0vy-RL2LAAV8h9r");
        map.put("CTV3: Number switched to 3rd line-03","ZdrEgOTb0vy-ja1pGb0xZqC");
        map.put("CTV3: Number switched to 3rd line-04","ZdrEgOTb0vy-Aoyz53eT06v");
        map.put("CTV3: Number switched to 3rd line-05","ZdrEgOTb0vy-B49DkRhRjFU");
        map.put("CTV3: Number switched to 3rd line-06","ZdrEgOTb0vy-jWGYebQ1pCn");
        map.put("CTV3: Number switched to 3rd line-07","ZdrEgOTb0vy-haYMHQSFfFi");
        map.put("CTV3: Number switched to 3rd line-08","ZdrEgOTb0vy-ZhI3TLG2Iea");

        map.put("CTV3: With vl results >= 1000cp/ml-01","mCdYS5pHbVH-slGBtK9NsjZ");
        map.put("CTV3: With vl results >= 1000cp/ml-02","mCdYS5pHbVH-RL2LAAV8h9r");
        map.put("CTV3: With vl results >= 1000cp/ml-03","mCdYS5pHbVH-ja1pGb0xZqC");
        map.put("CTV3: With vl results >= 1000cp/ml-04","mCdYS5pHbVH-Aoyz53eT06v");
        map.put("CTV3: With vl results >= 1000cp/ml-05","mCdYS5pHbVH-B49DkRhRjFU");
        map.put("CTV3: With vl results >= 1000cp/ml-06","mCdYS5pHbVH-jWGYebQ1pCn");
        map.put("CTV3: With vl results >= 1000cp/ml-07","mCdYS5pHbVH-haYMHQSFfFi");
        map.put("CTV3: With vl results >= 1000cp/ml-08","mCdYS5pHbVH-ZhI3TLG2Iea");

        map.put("CTV3: Current on treatment - (Number with NCD well controlled)-01","ueetqhbPA3s-slGBtK9NsjZ");
        map.put("CTV3: Current on treatment - (Number with NCD well controlled)-02","ueetqhbPA3s-RL2LAAV8h9r");
        map.put("CTV3: Current on treatment - (Number with NCD well controlled)-03","ueetqhbPA3s-ja1pGb0xZqC");
        map.put("CTV3: Current on treatment - (Number with NCD well controlled)-04","ueetqhbPA3s-Aoyz53eT06v");
        map.put("CTV3: Current on treatment - (Number with NCD well controlled)-05","ueetqhbPA3s-B49DkRhRjFU");
        map.put("CTV3: Current on treatment - (Number with NCD well controlled)-06","ueetqhbPA3s-jWGYebQ1pCn");
        map.put("CTV3: Current on treatment - (Number with NCD well controlled)-07","ueetqhbPA3s-haYMHQSFfFi");
        map.put("CTV3: Current on treatment - (Number with NCD well controlled)-08","ueetqhbPA3s-ZhI3TLG2Iea");

        map.put("CTV3: Number switched to 2nd line-01","qubwkZK8CxM-slGBtK9NsjZ");
        map.put("CTV3: Number switched to 2nd line-02","qubwkZK8CxM-RL2LAAV8h9r");
        map.put("CTV3: Number switched to 2nd line-03","qubwkZK8CxM-ja1pGb0xZqC");
        map.put("CTV3: Number switched to 2nd line-04","qubwkZK8CxM-Aoyz53eT06v");
        map.put("CTV3: Number switched to 2nd line-05","qubwkZK8CxM-B49DkRhRjFU");
        map.put("CTV3: Number switched to 2nd line-06","qubwkZK8CxM-jWGYebQ1pCn");
        map.put("CTV3: Number switched to 2nd line-07","qubwkZK8CxM-haYMHQSFfFi");
        map.put("CTV3: Number switched to 2nd line-08","qubwkZK8CxM-ZhI3TLG2Iea");

        map.put("CTV3: Transfer ins-01","PtWHwRBbvov-slGBtK9NsjZ");
        map.put("CTV3: Transfer ins-02","PtWHwRBbvov-RL2LAAV8h9r");
        map.put("CTV3: Transfer ins-03","PtWHwRBbvov-ja1pGb0xZqC");
        map.put("CTV3: Transfer ins-04","PtWHwRBbvov-Aoyz53eT06v");
        map.put("CTV3: Transfer ins-05","PtWHwRBbvov-B49DkRhRjFU");
        map.put("CTV3: Transfer ins-06","PtWHwRBbvov-jWGYebQ1pCn");
        map.put("CTV3: Transfer ins-07","PtWHwRBbvov-haYMHQSFfFi");
        map.put("CTV3: Transfer ins-08","PtWHwRBbvov-ZhI3TLG2Iea");

        map.put("CTV3: Current on treatment - (Number with Diabetes)-01","ysonYfgb8uf-slGBtK9NsjZ");
        map.put("CTV3: Current on treatment - (Number with Diabetes)-02","ysonYfgb8uf-RL2LAAV8h9r");
        map.put("CTV3: Current on treatment - (Number with Diabetes)-03","ysonYfgb8uf-ja1pGb0xZqC");
        map.put("CTV3: Current on treatment - (Number with Diabetes)-04","ysonYfgb8uf-Aoyz53eT06v");
        map.put("CTV3: Current on treatment - (Number with Diabetes)-05","ysonYfgb8uf-B49DkRhRjFU");
        map.put("CTV3: Current on treatment - (Number with Diabetes)-06","ysonYfgb8uf-jWGYebQ1pCn");
        map.put("CTV3: Current on treatment - (Number with Diabetes)-07","ysonYfgb8uf-haYMHQSFfFi");
        map.put("CTV3: Current on treatment - (Number with Diabetes)-08","ysonYfgb8uf-ZhI3TLG2Iea");

        map.put("CTV3: Died-01","p3Sa9IVj3CA-slGBtK9NsjZ");
        map.put("CTV3: Died-02","p3Sa9IVj3CA-RL2LAAV8h9r");
        map.put("CTV3: Died-03","p3Sa9IVj3CA-ja1pGb0xZqC");
        map.put("CTV3: Died-04","p3Sa9IVj3CA-Aoyz53eT06v");
        map.put("CTV3: Died-05","p3Sa9IVj3CA-B49DkRhRjFU");
        map.put("CTV3: Died-06","p3Sa9IVj3CA-jWGYebQ1pCn");
        map.put("CTV3: Died-07","p3Sa9IVj3CA-haYMHQSFfFi");
        map.put("CTV3: Died-08","p3Sa9IVj3CA-ZhI3TLG2Iea");

        map.put("CTV3: With viral load results < 200cp/ml (includes LDL)-01","zLhsbMB7rtv-slGBtK9NsjZ");
        map.put("CTV3: With viral load results < 200cp/ml (includes LDL)-02","zLhsbMB7rtv-RL2LAAV8h9r");
        map.put("CTV3: With viral load results < 200cp/ml (includes LDL)-03","zLhsbMB7rtv-ja1pGb0xZqC");
        map.put("CTV3: With viral load results < 200cp/ml (includes LDL)-04","zLhsbMB7rtv-Aoyz53eT06v");
        map.put("CTV3: With viral load results < 200cp/ml (includes LDL)-05","zLhsbMB7rtv-B49DkRhRjFU");
        map.put("CTV3: With viral load results < 200cp/ml (includes LDL)-06","zLhsbMB7rtv-jWGYebQ1pCn");
        map.put("CTV3: With viral load results < 200cp/ml (includes LDL)-07","zLhsbMB7rtv-haYMHQSFfFi");
        map.put("CTV3: With viral load results < 200cp/ml (includes LDL)-08","zLhsbMB7rtv-ZhI3TLG2Iea");

        map.put("CTV3: With vl results 400 - 999cp/ml-01","lfQdvUNwJiZ-slGBtK9NsjZ");
        map.put("CTV3: With vl results 400 - 999cp/ml-02","lfQdvUNwJiZ-RL2LAAV8h9r");
        map.put("CTV3: With vl results 400 - 999cp/ml-03","lfQdvUNwJiZ-ja1pGb0xZqC");
        map.put("CTV3: With vl results 400 - 999cp/ml-04","lfQdvUNwJiZ-Aoyz53eT06v");
        map.put("CTV3: With vl results 400 - 999cp/ml-05","lfQdvUNwJiZ-B49DkRhRjFU");
        map.put("CTV3: With vl results 400 - 999cp/ml-06","lfQdvUNwJiZ-jWGYebQ1pCn");
        map.put("CTV3: With vl results 400 - 999cp/ml-07","lfQdvUNwJiZ-haYMHQSFfFi");
        map.put("CTV3: With vl results 400 - 999cp/ml-08","lfQdvUNwJiZ-ZhI3TLG2Iea");

        //HTS - IPD
        map.put("HTSV3: IPD HP - eligible-01","Eovs26khBBu-slGBtK9NsjZ");
        map.put("HTSV3: IPD HP - eligible-02","Eovs26khBBu-RL2LAAV8h9r");
        map.put("HTSV3: IPD HP - eligible-03","Eovs26khBBu-ja1pGb0xZqC");
        map.put("HTSV3: IPD HP - eligible-04","Eovs26khBBu-Aoyz53eT06v");
        map.put("HTSV3: IPD HP - eligible-05","Eovs26khBBu-B49DkRhRjFU");
        map.put("HTSV3: IPD HP - eligible-06","Eovs26khBBu-jWGYebQ1pCn");
        map.put("HTSV3: IPD HP - eligible-07","Eovs26khBBu-haYMHQSFfFi");
        map.put("HTSV3: IPD HP - eligible-08","Eovs26khBBu-ZhI3TLG2Iea");

        map.put("HTSV3: IPD HP - known positive-01","bmBqZJh5Vj0-slGBtK9NsjZ");
        map.put("HTSV3: IPD HP - known positive-02","bmBqZJh5Vj0-RL2LAAV8h9r");
        map.put("HTSV3: IPD HP - known positive-03","bmBqZJh5Vj0-ja1pGb0xZqC");
        map.put("HTSV3: IPD HP - known positive-04","bmBqZJh5Vj0-Aoyz53eT06v");
        map.put("HTSV3: IPD HP - known positive-05","bmBqZJh5Vj0-B49DkRhRjFU");
        map.put("HTSV3: IPD HP - known positive-06","bmBqZJh5Vj0-jWGYebQ1pCn");
        map.put("HTSV3: IPD HP - known positive-07","bmBqZJh5Vj0-haYMHQSFfFi");
        map.put("HTSV3: IPD HP - known positive-08","bmBqZJh5Vj0-ZhI3TLG2Iea");

        map.put("HTSV3: IPD HP - linked-01","dD1WI0qgZv7-slGBtK9NsjZ");
        map.put("HTSV3: IPD HP - linked-02","dD1WI0qgZv7-RL2LAAV8h9r");
        map.put("HTSV3: IPD HP - linked-03","dD1WI0qgZv7-ja1pGb0xZqC");
        map.put("HTSV3: IPD HP - linked-04","dD1WI0qgZv7-Aoyz53eT06v");
        map.put("HTSV3: IPD HP - linked-05","dD1WI0qgZv7-B49DkRhRjFU");
        map.put("HTSV3: IPD HP - linked-06","dD1WI0qgZv7-jWGYebQ1pCn");
        map.put("HTSV3: IPD HP - linked-07","dD1WI0qgZv7-haYMHQSFfFi");
        map.put("HTSV3: IPD HP - linked-08","dD1WI0qgZv7-ZhI3TLG2Iea");

        map.put("HTSV3: IPD HP - newly identified positive-01","R1oPKzLXFYF-slGBtK9NsjZ");
        map.put("HTSV3: IPD HP - newly identified positive-02","R1oPKzLXFYF-RL2LAAV8h9r");
        map.put("HTSV3: IPD HP - newly identified positive-03","R1oPKzLXFYF-ja1pGb0xZqC");
        map.put("HTSV3: IPD HP - newly identified positive-04","R1oPKzLXFYF-Aoyz53eT06v");
        map.put("HTSV3: IPD HP - newly identified positive-05","R1oPKzLXFYF-B49DkRhRjFU");
        map.put("HTSV3: IPD HP - newly identified positive-06","R1oPKzLXFYF-jWGYebQ1pCn");
        map.put("HTSV3: IPD HP - newly identified positive-07","R1oPKzLXFYF-haYMHQSFfFi");
        map.put("HTSV3: IPD HP - newly identified positive-08","R1oPKzLXFYF-ZhI3TLG2Iea");

        map.put("HTSV3: IPD HP - screened-01","P7llYRP3JRR-slGBtK9NsjZ");
        map.put("HTSV3: IPD HP - screened-02","P7llYRP3JRR-RL2LAAV8h9r");
        map.put("HTSV3: IPD HP - screened-03","P7llYRP3JRR-ja1pGb0xZqC");
        map.put("HTSV3: IPD HP - screened-04","P7llYRP3JRR-Aoyz53eT06v");
        map.put("HTSV3: IPD HP - screened-05","P7llYRP3JRR-B49DkRhRjFU");
        map.put("HTSV3: IPD HP - screened-06","P7llYRP3JRR-jWGYebQ1pCn");
        map.put("HTSV3: IPD HP - screened-07","P7llYRP3JRR-haYMHQSFfFi");
        map.put("HTSV3: IPD HP - screened-08","P7llYRP3JRR-ZhI3TLG2Iea");

        map.put("HTSV3: IPD HP - tested-01","Y8gygWUrdIP-slGBtK9NsjZ");
        map.put("HTSV3: IPD HP - tested-02","Y8gygWUrdIP-RL2LAAV8h9r");
        map.put("HTSV3: IPD HP - tested-03","Y8gygWUrdIP-ja1pGb0xZqC");
        map.put("HTSV3: IPD HP - tested-04","Y8gygWUrdIP-Aoyz53eT06v");
        map.put("HTSV3: IPD HP - tested-05","Y8gygWUrdIP-B49DkRhRjFU");
        map.put("HTSV3: IPD HP - tested-06","Y8gygWUrdIP-jWGYebQ1pCn");
        map.put("HTSV3: IPD HP - tested-07","Y8gygWUrdIP-haYMHQSFfFi");
        map.put("HTSV3: IPD HP - tested-08","Y8gygWUrdIP-ZhI3TLG2Iea");
        //map.put("HTSV3: IPD HP - workload","fpqOh9xjohD-HllvX50cXC0");

        map.put("HTSV3: IPD NP - eligible-01","ABPJyymxDbV-slGBtK9NsjZ");
        map.put("HTSV3: IPD NP - eligible-02","ABPJyymxDbV-RL2LAAV8h9r");
        map.put("HTSV3: IPD NP - eligible-03","ABPJyymxDbV-ja1pGb0xZqC");
        map.put("HTSV3: IPD NP - eligible-04","ABPJyymxDbV-Aoyz53eT06v");
        map.put("HTSV3: IPD NP - eligible-05","ABPJyymxDbV-B49DkRhRjFU");
        map.put("HTSV3: IPD NP - eligible-06","ABPJyymxDbV-jWGYebQ1pCn");
        map.put("HTSV3: IPD NP - eligible-07","ABPJyymxDbV-haYMHQSFfFi");
        map.put("HTSV3: IPD NP - eligible-08","ABPJyymxDbV-ZhI3TLG2Iea");

        map.put("HTSV3: IPD NP - known positive-01","f53Yb4QHC5J-slGBtK9NsjZ");
        map.put("HTSV3: IPD NP - known positive-02","f53Yb4QHC5J-RL2LAAV8h9r");
        map.put("HTSV3: IPD NP - known positive-03","f53Yb4QHC5J-ja1pGb0xZqC");
        map.put("HTSV3: IPD NP - known positive-04","f53Yb4QHC5J-Aoyz53eT06v");
        map.put("HTSV3: IPD NP - known positive-05","f53Yb4QHC5J-B49DkRhRjFU");
        map.put("HTSV3: IPD NP - known positive-06","f53Yb4QHC5J-jWGYebQ1pCn");
        map.put("HTSV3: IPD NP - known positive-07","f53Yb4QHC5J-haYMHQSFfFi");
        map.put("HTSV3: IPD NP - known positive-08","f53Yb4QHC5J-ZhI3TLG2Iea");

        map.put("HTSV3: IPD NP - linked-01","YYjM4Z32q79-slGBtK9NsjZ");
        map.put("HTSV3: IPD NP - linked-02","YYjM4Z32q79-RL2LAAV8h9r");
        map.put("HTSV3: IPD NP - linked-03","YYjM4Z32q79-ja1pGb0xZqC");
        map.put("HTSV3: IPD NP - linked-04","YYjM4Z32q79-Aoyz53eT06v");
        map.put("HTSV3: IPD NP - linked-05","YYjM4Z32q79-B49DkRhRjFU");
        map.put("HTSV3: IPD NP - linked-06","YYjM4Z32q79-jWGYebQ1pCn");
        map.put("HTSV3: IPD NP - linked-07","YYjM4Z32q79-haYMHQSFfFi");
        map.put("HTSV3: IPD NP - linked-08","YYjM4Z32q79-ZhI3TLG2Iea");

        map.put("HTSV3: IPD NP - newly identified positive-01","Pz1p4oFoNWa-slGBtK9NsjZ");
        map.put("HTSV3: IPD NP - newly identified positive-02","Pz1p4oFoNWa-RL2LAAV8h9r");
        map.put("HTSV3: IPD NP - newly identified positive-03","Pz1p4oFoNWa-ja1pGb0xZqC");
        map.put("HTSV3: IPD NP - newly identified positive-04","Pz1p4oFoNWa-Aoyz53eT06v");
        map.put("HTSV3: IPD NP - newly identified positive-05","Pz1p4oFoNWa-B49DkRhRjFU");
        map.put("HTSV3: IPD NP - newly identified positive-06","Pz1p4oFoNWa-jWGYebQ1pCn");
        map.put("HTSV3: IPD NP - newly identified positive-07","Pz1p4oFoNWa-haYMHQSFfFi");
        map.put("HTSV3: IPD NP - newly identified positive-08","Pz1p4oFoNWa-ZhI3TLG2Iea");

        map.put("HTSV3: IPD NP - screened-01","b8Vwuvc5jha-slGBtK9NsjZ");
        map.put("HTSV3: IPD NP - screened-02","b8Vwuvc5jha-RL2LAAV8h9r");
        map.put("HTSV3: IPD NP - screened-03","b8Vwuvc5jha-ja1pGb0xZqC");
        map.put("HTSV3: IPD NP - screened-04","b8Vwuvc5jha-Aoyz53eT06v");
        map.put("HTSV3: IPD NP - screened-05","b8Vwuvc5jha-B49DkRhRjFU");
        map.put("HTSV3: IPD NP - screened-06","b8Vwuvc5jha-jWGYebQ1pCn");
        map.put("HTSV3: IPD NP - screened-07","b8Vwuvc5jha-haYMHQSFfFi");
        map.put("HTSV3: IPD NP - screened-08","b8Vwuvc5jha-ZhI3TLG2Iea");

        map.put("HTSV3: IPD NP - tested-01","snzfN6tm396-slGBtK9NsjZ");
        map.put("HTSV3: IPD NP - tested-02","snzfN6tm396-RL2LAAV8h9r");
        map.put("HTSV3: IPD NP - tested-03","snzfN6tm396-ja1pGb0xZqC");
        map.put("HTSV3: IPD NP - tested-04","snzfN6tm396-Aoyz53eT06v");
        map.put("HTSV3: IPD NP - tested-05","snzfN6tm396-B49DkRhRjFU");
        map.put("HTSV3: IPD NP - tested-06","snzfN6tm396-jWGYebQ1pCn");
        map.put("HTSV3: IPD NP - tested-07","snzfN6tm396-haYMHQSFfFi");
        map.put("HTSV3: IPD NP - tested-08","snzfN6tm396-ZhI3TLG2Iea");
        //map.put("HTSV3: IPD NP - workload","WiPU5c2SedB-HllvX50cXC0");

        //HTS - OPD
        map.put("HTSV3: OPD HP - eligible-01","Pmd0uEoGsuJ-slGBtK9NsjZ");
        map.put("HTSV3: OPD HP - eligible-02","Pmd0uEoGsuJ-RL2LAAV8h9r");
        map.put("HTSV3: OPD HP - eligible-03","Pmd0uEoGsuJ-ja1pGb0xZqC");
        map.put("HTSV3: OPD HP - eligible-04","Pmd0uEoGsuJ-Aoyz53eT06v");
        map.put("HTSV3: OPD HP - eligible-05","Pmd0uEoGsuJ-B49DkRhRjFU");
        map.put("HTSV3: OPD HP - eligible-06","Pmd0uEoGsuJ-jWGYebQ1pCn");
        map.put("HTSV3: OPD HP - eligible-07","Pmd0uEoGsuJ-haYMHQSFfFi");
        map.put("HTSV3: OPD HP - eligible-08","Pmd0uEoGsuJ-ZhI3TLG2Iea");

        map.put("HTSV3: OPD HP - known positive-01","j2G0lQSDNwY-slGBtK9NsjZ");
        map.put("HTSV3: OPD HP - known positive-02","j2G0lQSDNwY-RL2LAAV8h9r");
        map.put("HTSV3: OPD HP - known positive-03","j2G0lQSDNwY-ja1pGb0xZqC");
        map.put("HTSV3: OPD HP - known positive-04","j2G0lQSDNwY-Aoyz53eT06v");
        map.put("HTSV3: OPD HP - known positive-05","j2G0lQSDNwY-B49DkRhRjFU");
        map.put("HTSV3: OPD HP - known positive-06","j2G0lQSDNwY-jWGYebQ1pCn");
        map.put("HTSV3: OPD HP - known positive-07","j2G0lQSDNwY-haYMHQSFfFi");
        map.put("HTSV3: OPD HP - known positive-08","j2G0lQSDNwY-ZhI3TLG2Iea");

        map.put("HTSV3: OPD HP - linked-01","DE7b7KkSTqv-slGBtK9NsjZ");
        map.put("HTSV3: OPD HP - linked-02","DE7b7KkSTqv-RL2LAAV8h9r");
        map.put("HTSV3: OPD HP - linked-03","DE7b7KkSTqv-ja1pGb0xZqC");
        map.put("HTSV3: OPD HP - linked-04","DE7b7KkSTqv-Aoyz53eT06v");
        map.put("HTSV3: OPD HP - linked-05","DE7b7KkSTqv-B49DkRhRjFU");
        map.put("HTSV3: OPD HP - linked-06","DE7b7KkSTqv-jWGYebQ1pCn");
        map.put("HTSV3: OPD HP - linked-07","DE7b7KkSTqv-haYMHQSFfFi");
        map.put("HTSV3: OPD HP - linked-08","DE7b7KkSTqv-ZhI3TLG2Iea");

        map.put("HTSV3: OPD HP - newly identified positive-01","bhURcpGJGom-slGBtK9NsjZ");
        map.put("HTSV3: OPD HP - newly identified positive-02","bhURcpGJGom-RL2LAAV8h9r");
        map.put("HTSV3: OPD HP - newly identified positive-03","bhURcpGJGom-ja1pGb0xZqC");
        map.put("HTSV3: OPD HP - newly identified positive-04","bhURcpGJGom-Aoyz53eT06v");
        map.put("HTSV3: OPD HP - newly identified positive-05","bhURcpGJGom-B49DkRhRjFU");
        map.put("HTSV3: OPD HP - newly identified positive-06","bhURcpGJGom-jWGYebQ1pCn");
        map.put("HTSV3: OPD HP - newly identified positive-07","bhURcpGJGom-haYMHQSFfFi");
        map.put("HTSV3: OPD HP - newly identified positive-08","bhURcpGJGom-ZhI3TLG2Iea");

        map.put("HTSV3: OPD HP - screened-01","ss7ILGAdkHU-slGBtK9NsjZ");
        map.put("HTSV3: OPD HP - screened-02","ss7ILGAdkHU-RL2LAAV8h9r");
        map.put("HTSV3: OPD HP - screened-03","ss7ILGAdkHU-ja1pGb0xZqC");
        map.put("HTSV3: OPD HP - screened-04","ss7ILGAdkHU-Aoyz53eT06v");
        map.put("HTSV3: OPD HP - screened-05","ss7ILGAdkHU-B49DkRhRjFU");
        map.put("HTSV3: OPD HP - screened-06","ss7ILGAdkHU-jWGYebQ1pCn");
        map.put("HTSV3: OPD HP - screened-07","ss7ILGAdkHU-haYMHQSFfFi");
        map.put("HTSV3: OPD HP - screened-08","ss7ILGAdkHU-ZhI3TLG2Iea");

        map.put("HTSV3: OPD HP - tested-01","OoItp68AYPU-slGBtK9NsjZ");
        map.put("HTSV3: OPD HP - tested-02","OoItp68AYPU-RL2LAAV8h9r");
        map.put("HTSV3: OPD HP - tested-03","OoItp68AYPU-ja1pGb0xZqC");
        map.put("HTSV3: OPD HP - tested-04","OoItp68AYPU-Aoyz53eT06v");
        map.put("HTSV3: OPD HP - tested-05","OoItp68AYPU-B49DkRhRjFU");
        map.put("HTSV3: OPD HP - tested-06","OoItp68AYPU-jWGYebQ1pCn");
        map.put("HTSV3: OPD HP - tested-07","OoItp68AYPU-haYMHQSFfFi");
        map.put("HTSV3: OPD HP - tested-08","OoItp68AYPU-ZhI3TLG2Iea");

        map.put("HTSV3: OPD NP - eligible-01","JXz1rKH6PCb-slGBtK9NsjZ");
        map.put("HTSV3: OPD NP - eligible-02","JXz1rKH6PCb-RL2LAAV8h9r");
        map.put("HTSV3: OPD NP - eligible-03","JXz1rKH6PCb-ja1pGb0xZqC");
        map.put("HTSV3: OPD NP - eligible-04","JXz1rKH6PCb-Aoyz53eT06v");
        map.put("HTSV3: OPD NP - eligible-05","JXz1rKH6PCb-B49DkRhRjFU");
        map.put("HTSV3: OPD NP - eligible-06","JXz1rKH6PCb-jWGYebQ1pCn");
        map.put("HTSV3: OPD NP - eligible-07","JXz1rKH6PCb-haYMHQSFfFi");
        map.put("HTSV3: OPD NP - eligible-08","JXz1rKH6PCb-ZhI3TLG2Iea");

        map.put("HTSV3: OPD NP - known positive-01","hriOIHtb4Tz-slGBtK9NsjZ");
        map.put("HTSV3: OPD NP - known positive-02","hriOIHtb4Tz-RL2LAAV8h9r");
        map.put("HTSV3: OPD NP - known positive-03","hriOIHtb4Tz-ja1pGb0xZqC");
        map.put("HTSV3: OPD NP - known positive-04","hriOIHtb4Tz-Aoyz53eT06v");
        map.put("HTSV3: OPD NP - known positive-05","hriOIHtb4Tz-B49DkRhRjFU");
        map.put("HTSV3: OPD NP - known positive-06","hriOIHtb4Tz-jWGYebQ1pCn");
        map.put("HTSV3: OPD NP - known positive-07","hriOIHtb4Tz-haYMHQSFfFi");
        map.put("HTSV3: OPD NP - known positive-08","hriOIHtb4Tz-ZhI3TLG2Iea");

        map.put("HTSV3: OPD NP - linked-01","aNpMbgH354J-slGBtK9NsjZ");
        map.put("HTSV3: OPD NP - linked-02","aNpMbgH354J-RL2LAAV8h9r");
        map.put("HTSV3: OPD NP - linked-03","aNpMbgH354J-ja1pGb0xZqC");
        map.put("HTSV3: OPD NP - linked-04","aNpMbgH354J-Aoyz53eT06v");
        map.put("HTSV3: OPD NP - linked-05","aNpMbgH354J-B49DkRhRjFU");
        map.put("HTSV3: OPD NP - linked-06","aNpMbgH354J-jWGYebQ1pCn");
        map.put("HTSV3: OPD NP - linked-07","aNpMbgH354J-haYMHQSFfFi");
        map.put("HTSV3: OPD NP - linked-08","aNpMbgH354J-ZhI3TLG2Iea");

        map.put("HTSV3: OPD NP - newly identified positive-01","v6sFj11PpN6-slGBtK9NsjZ");
        map.put("HTSV3: OPD NP - newly identified positive-02","v6sFj11PpN6-RL2LAAV8h9r");
        map.put("HTSV3: OPD NP - newly identified positive-03","v6sFj11PpN6-ja1pGb0xZqC");
        map.put("HTSV3: OPD NP - newly identified positive-04","v6sFj11PpN6-Aoyz53eT06v");
        map.put("HTSV3: OPD NP - newly identified positive-05","v6sFj11PpN6-B49DkRhRjFU");
        map.put("HTSV3: OPD NP - newly identified positive-06","v6sFj11PpN6-jWGYebQ1pCn");
        map.put("HTSV3: OPD NP - newly identified positive-07","v6sFj11PpN6-haYMHQSFfFi");
        map.put("HTSV3: OPD NP - newly identified positive-08","v6sFj11PpN6-ZhI3TLG2Iea");

        map.put("HTSV3: OPD NP - screened-01","bxwwUG86Cs5-slGBtK9NsjZ");
        map.put("HTSV3: OPD NP - screened-02","bxwwUG86Cs5-RL2LAAV8h9r");
        map.put("HTSV3: OPD NP - screened-03","bxwwUG86Cs5-ja1pGb0xZqC");
        map.put("HTSV3: OPD NP - screened-04","bxwwUG86Cs5-Aoyz53eT06v");
        map.put("HTSV3: OPD NP - screened-05","bxwwUG86Cs5-B49DkRhRjFU");
        map.put("HTSV3: OPD NP - screened-06","bxwwUG86Cs5-jWGYebQ1pCn");
        map.put("HTSV3: OPD NP - screened-07","bxwwUG86Cs5-haYMHQSFfFi");
        map.put("HTSV3: OPD NP - screened-08","bxwwUG86Cs5-ZhI3TLG2Iea");

        map.put("HTSV3: OPD NP - tested-01","VGPv7IYRhW4-slGBtK9NsjZ");
        map.put("HTSV3: OPD NP - tested-02","VGPv7IYRhW4-RL2LAAV8h9r");
        map.put("HTSV3: OPD NP - tested-03","VGPv7IYRhW4-ja1pGb0xZqC");
        map.put("HTSV3: OPD NP - tested-04","VGPv7IYRhW4-Aoyz53eT06v");
        map.put("HTSV3: OPD NP - tested-05","VGPv7IYRhW4-B49DkRhRjFU");
        map.put("HTSV3: OPD NP - tested-06","VGPv7IYRhW4-jWGYebQ1pCn");
        map.put("HTSV3: OPD NP - tested-07","VGPv7IYRhW4-haYMHQSFfFi");
        map.put("HTSV3: OPD NP - tested-08","VGPv7IYRhW4-ZhI3TLG2Iea");

        return map.get(indicatorDisaggr);
    }

}
