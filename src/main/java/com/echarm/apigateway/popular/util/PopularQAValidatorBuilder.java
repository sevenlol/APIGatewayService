package com.echarm.apigateway.popular.util;

import java.util.HashMap;
import java.util.Map;

import com.echarm.apigateway.util.ValidateRule;
import com.echarm.apigateway.util.ValidateType;

public class PopularQAValidatorBuilder {

    private Map<PopularQAField, ValidateType> map;

    public PopularQAValidatorBuilder() { map = new HashMap<PopularQAField, ValidateType>(); }

    public PopularQAValidatorBuilder add(PopularQAField field, ValidateType type) {
        if (field != null && type != null) map.put(field, type);

        return this;
    }

    public PopularQAValidator getValidator(ValidateRule rule) {
        return new PopularQAValidator(rule, map);
    }
}
