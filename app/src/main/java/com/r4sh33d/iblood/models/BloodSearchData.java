package com.r4sh33d.iblood.models;

public class BloodSearchData {
    public String bloodType;
    public String donationType;
    public boolean considerReligion;
    public String religion;
    public UserLocation seekersLocation;
    public String preferredDonorState;
    public String preferredDonorCity;
    public String preferedRangeKm;

    public BloodSearchData(String bloodType, String donationType, boolean considerReligion,
                           String religion, String preferredDonorState,
                           String preferredDonorCity) {
        this.bloodType = bloodType;
        this.donationType = donationType;
        this.considerReligion = considerReligion;
        this.religion = religion;
        this.seekersLocation = seekersLocation;
        this.preferredDonorState = preferredDonorState;
        this.preferredDonorCity = preferredDonorCity;
    }

    public BloodSearchData(String bloodType, String donationType, boolean considerReligion,
                           String religion, String preferedRangeKm) {
        this.bloodType = bloodType;
        this.donationType = donationType;
        this.considerReligion = considerReligion;
        this.religion = religion;
        this.preferedRangeKm = preferedRangeKm;
    }
}

