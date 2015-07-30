package com.echarm.apigateway.popular.model;

import com.echarm.apigateway.accountsystem.util.Category;

public class PopularDoctor {

    private Category doctorCategory;
    private String doctorName;
    private String doctorId;
    private String doctorStickerUrl;

    public PopularDoctor() {}

    /* getter methods */

    public Category getDoctorCategory() { return doctorCategory; }
    public String getDoctorName() { return doctorName; }
    public String getDoctorId() { return doctorId; }
    public String getDoctorStickerUrl() { return doctorStickerUrl; }

    /* setter methods */

    public PopularDoctor setDoctorCategory(Category doctorCategory) {
        this.doctorCategory = doctorCategory; return this;
    }

    public PopularDoctor setDoctorName(String doctorName) {
        this.doctorName = doctorName; return this;
    }

    public PopularDoctor setDoctorId(String doctorId) {
        this.doctorId = doctorId; return this;
    }

    public PopularDoctor setDoctorStickerUrl(String doctorStickerUrl) {
        this.doctorStickerUrl = doctorStickerUrl; return this;
    }
}
