package com.echarm.apigateway.popular.util;

public enum PopularDoctorField {

    DOCTOR_ID(0), DOCTOR_CATEGORY(1), DOCTOR_NAME(2),
    DOCTOR_STICKER_URL(3);

    public final int id;

    private PopularDoctorField(int id) { this.id = id; }

    public static PopularDoctorField getPopularDoctorField(int id) {
        for (PopularDoctorField popularDoctorField : PopularDoctorField.values()) {
            if (popularDoctorField.id == id) {
                return popularDoctorField;
            }
        }
        return null;
    }
}
