package com.echarm.apigateway.popular.util;

import java.util.HashMap;
import java.util.Map;

import com.echarm.apigateway.util.ValidateRule;
import com.echarm.apigateway.util.ValidateType;

public class PopularDoctorValidatorBuilder {

    private Map<PopularDoctorField, ValidateType> map;

    public PopularDoctorValidatorBuilder() { map = new HashMap<PopularDoctorField, ValidateType>(); }

    public PopularDoctorValidatorBuilder add(PopularDoctorField field, ValidateType type) {
        if (field != null && type != null) map.put(field, type);

        return this;
    }

    public PopularDoctorValidator getValidator(ValidateRule rule) {
        return new PopularDoctorValidator(rule, map);
    }
}
