package com.echarm.apigateway.favorite.util;

import java.util.HashMap;
import java.util.Map;

import com.echarm.apigateway.util.ValidateRule;
import com.echarm.apigateway.util.ValidateType;

public class FavoriteQAValidatorBuilder {

    private Map<FavoriteQAField, ValidateType> map;

    public FavoriteQAValidatorBuilder() { map = new HashMap<FavoriteQAField, ValidateType>(); }

    public FavoriteQAValidatorBuilder add(FavoriteQAField field, ValidateType type) {
        if (field != null && type != null) map.put(field, type);

        return this;
    }

    public FavoriteQAValidator getValidator(ValidateRule rule) {
        return new FavoriteQAValidator(rule, map);
    }
}
