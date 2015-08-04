package com.echarm.apigateway.popular.model;

import org.springframework.data.mongodb.core.mapping.Field;

import com.echarm.apigateway.accountsystem.util.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PopularDoctor {

    @Field("doctor_category") private Category doctorCategory;
    @Field("doctor_name") private String doctorName;
    @Field("doctor_id") private String doctorId;
    @Field("doctor_sticker_url") private String doctorStickerUrl;

    public PopularDoctor() {}

    /* getter methods */

    @JsonIgnore public Category getDoctorCategory() { return doctorCategory; }
    @JsonIgnore public String getDoctorName() { return doctorName; }
    @JsonIgnore public String getDoctorId() { return doctorId; }
    @JsonIgnore public String getDoctorStickerUrl() { return doctorStickerUrl; }

    /* setter methods */

    @JsonIgnore
    public PopularDoctor setDoctorCategory(Category doctorCategory) {
        this.doctorCategory = doctorCategory; return this;
    }

    @JsonIgnore
    public PopularDoctor setDoctorName(String doctorName) {
        this.doctorName = doctorName; return this;
    }

    @JsonIgnore
    public PopularDoctor setDoctorId(String doctorId) {
        this.doctorId = doctorId; return this;
    }

    @JsonIgnore
    public PopularDoctor setDoctorStickerUrl(String doctorStickerUrl) {
        this.doctorStickerUrl = doctorStickerUrl; return this;
    }
}
