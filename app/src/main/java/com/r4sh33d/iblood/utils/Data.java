package com.r4sh33d.iblood.utils;

import java.util.HashMap;

public class Data {
    //Painful to write ☹️☹️☹️☹️
    public static HashMap<String, String[]> bloodTypeCompatibilityMapping = new HashMap<>();
    static {
        bloodTypeCompatibilityMapping.put("AB+", new String[]{"O-", "O+", "B-", "B+", "A-", "A+", "AB-", "AB+"});
        bloodTypeCompatibilityMapping.put("AB-", new String[]{"O-", "B-", "A-", "AB-"});
        bloodTypeCompatibilityMapping.put("A+", new String[]{"O-", "O+", "A-", "A+"});
        bloodTypeCompatibilityMapping.put("A-", new String[]{"O-", "A-"});
        bloodTypeCompatibilityMapping.put("B+", new String[]{"O-", "O+", "B-", "B+"});
        bloodTypeCompatibilityMapping.put("B-", new String[]{"O-", "B-"});
        bloodTypeCompatibilityMapping.put("O+", new String[]{"O-", "O+"});
        bloodTypeCompatibilityMapping.put("O-", new String[]{"O-"});
    }
}
