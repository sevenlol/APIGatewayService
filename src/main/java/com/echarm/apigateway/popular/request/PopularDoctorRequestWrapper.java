package com.echarm.apigateway.popular.request;

import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.popular.model.PopularDoctor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class PopularDoctorRequestWrapper {

    private PopularDoctor doctor;

    public PopularDoctorRequestWrapper() { this.doctor = new PopularDoctor(); }

    /* getter methods */

    public PopularDoctor getPopularDoctor() { return doctor; };

    /* setter methods */

    @JsonProperty("doctor_name") public void setDoctorName(String doctorName) { doctor.setDoctorName(doctorName); }
    @JsonProperty("doctor_id") public void setDoctorId(String doctorId) { doctor.setDoctorId(doctorId); }
    @JsonProperty("doctor_sticker_url") public void setDoctorStickerUrl(String doctorStickerUrl) { doctor.setDoctorStickerUrl(doctorStickerUrl); }

    @JsonProperty("doctor_category")
    public void setAuthorCategory(String doctorCategoryStr) {
        doctor.setDoctorCategory(Category.isCategoryExist(doctorCategoryStr));
    }
}
