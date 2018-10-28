package com.r4sh33d.iblood.models;

public class BloodSearchData {
    public String bloodType;
    public String donationType;
    public boolean considerReligion;
    public String religion;
    public UserLocation seekersLocation;

    public BloodSearchData(String bloodType, String donationType,
                           boolean considerReligion, String religion) {
        this.bloodType = bloodType;
        this.donationType = donationType;
        this.considerReligion = considerReligion;
        this.religion = religion;
    }
}
