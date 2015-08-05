package com.echarm.apigateway.favorite.util;

import java.util.HashMap;
import java.util.Map;

import com.echarm.apigateway.util.ValidateRule;
import com.echarm.apigateway.util.ValidateType;

public class FavoriteArticleValidatorBuilder {

    private Map<FavoriteArticleField, ValidateType> map;

    public FavoriteArticleValidatorBuilder() { map = new HashMap<FavoriteArticleField, ValidateType>(); }

    public FavoriteArticleValidatorBuilder add(FavoriteArticleField field, ValidateType type) {
        if (field != null && type != null) map.put(field, type);

        return this;
    }

    public FavoriteArticleValidator getValidator(ValidateRule rule) {
        return new FavoriteArticleValidator(rule, map);
    }
}
