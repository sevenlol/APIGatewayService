package com.echarm.apigateway.popular.util;

import com.echarm.apigateway.util.ValidateRule;
import com.echarm.apigateway.util.ValidateType;

public class PopularDoctorValidatorFactory {

    public static PopularDoctorValidator getAllFieldNotNullValidator() {
        return new PopularDoctorValidatorBuilder()
                    .add(PopularDoctorField.DOCTOR_CATEGORY, ValidateType.NON_NULL)
                    .add(PopularDoctorField.DOCTOR_ID, ValidateType.NON_NULL)
                    .add(PopularDoctorField.DOCTOR_NAME, ValidateType.NON_NULL)
                    .add(PopularDoctorField.DOCTOR_STICKER_URL, ValidateType.NON_NULL)
                    .getValidator(ValidateRule.ALL_PASS);
    }

    public static PopularDoctorValidator getAllFieldNonEmptyValidator() {
        return new PopularDoctorValidatorBuilder()
                    .add(PopularDoctorField.DOCTOR_CATEGORY, ValidateType.NON_EMPTY)
                    .add(PopularDoctorField.DOCTOR_ID, ValidateType.NON_EMPTY)
                    .add(PopularDoctorField.DOCTOR_NAME, ValidateType.NON_EMPTY)
                    .add(PopularDoctorField.DOCTOR_STICKER_URL, ValidateType.NON_EMPTY)
                    .getValidator(ValidateRule.ALL_PASS);
    }
}
