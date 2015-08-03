package com.echarm.apigateway.popular.response;

import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.popular.model.PopularDoctor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class PopularDoctorResponseWrapper {

    private PopularDoctor doctor;

    public PopularDoctorResponseWrapper(PopularDoctor doctor) { this.doctor = doctor; }

    /* getter methods */

    @JsonProperty("doctor_name")
    public String getDoctorName() {
        if (this.doctor != null) {
            return this.doctor.getDoctorName();
        }
        return null;
    }

    @JsonProperty("doctor_id")
    public String getDoctorId() {
        if (this.doctor != null) {
            return this.doctor.getDoctorId();
        }
        return null;
    }

    @JsonProperty("doctor_sticker_url")
    public String getDoctorStickerUrl() {
        if (this.doctor != null) {
            return this.doctor.getDoctorStickerUrl();
        }
        return null;
    }

    @JsonProperty("doctor_category")
    public Category getDoctorCategory() {
        if (this.doctor != null) {
            return this.doctor.getDoctorCategory();
        }
        return null;
    }

}
