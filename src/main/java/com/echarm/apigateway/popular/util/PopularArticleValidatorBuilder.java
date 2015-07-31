package com.echarm.apigateway.popular.util;

import java.util.HashMap;
import java.util.Map;

import com.echarm.apigateway.util.ValidateRule;
import com.echarm.apigateway.util.ValidateType;

public class PopularArticleValidatorBuilder {

    private Map<PopularArticleField, ValidateType> map;

    public PopularArticleValidatorBuilder() { map = new HashMap<PopularArticleField, ValidateType>(); }

    public PopularArticleValidatorBuilder add(PopularArticleField field, ValidateType type) {
        if (field != null && type != null) map.put(field, type);

        return this;
    }

    public PopularArticleValidator getValidator(ValidateRule rule) {
        return new PopularArticleValidator(rule, map);
    }
}
