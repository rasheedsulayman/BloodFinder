package com.r4sh33d.iblood.models;

public class BloodDonationCenter {
    public String name;
    public String description;
    public String googleMapName;
    public MiniLocation miniLocation;

    public BloodDonationCenter(String name, String description, String googleMapName, MiniLocation miniLocation) {
        this.name = name;
        this.description = description;
        this.googleMapName = googleMapName;
        this.miniLocation = miniLocation;
    }
}
