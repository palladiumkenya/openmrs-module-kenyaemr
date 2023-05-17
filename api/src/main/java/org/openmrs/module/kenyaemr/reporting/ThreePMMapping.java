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
import java.util.List;
import java.util.Map;

public class ThreePMMapping {

    private final String indicatorName;

    private final List<String> emrDisaggregations;

    private final String duid;

    private final String dName;

    private final List<String> cuids;

    private final List<String> cuidDisaggregations;

    public ThreePMMapping(String indicatorName, List<String> emrDisaggregations, String duid, String dName, List<String> cuids, List<String> cuidDisaggregations) {
        this.indicatorName = indicatorName;
        this.emrDisaggregations = emrDisaggregations;
        this.duid = duid;
        this.dName = dName;
        this.cuids = cuids;
        this.cuidDisaggregations = cuidDisaggregations;

    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public List<String> getEmrDisaggregations() {
        return emrDisaggregations;
    }

    public String getDuid() {
        return duid;
    }

    public String getdName() {
        return dName;
    }

    public List<String> getCuids() {
        return cuids;
    }

    public List<String> getCuidDisaggregations() {
        return cuidDisaggregations;
    }

    public static String get3PMIndicatorId(String indicatorDisaggr) {

        HashMap<String, String> map = new HashMap<String, String>();

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

        return map.get(indicatorDisaggr);
    }

}
