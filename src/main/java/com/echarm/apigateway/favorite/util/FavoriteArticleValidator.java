package com.echarm.apigateway.favorite.util;

import java.util.Map;
import java.util.Set;

import com.echarm.apigateway.favorite.model.FavoriteArticle;
import com.echarm.apigateway.util.ValidateRule;
import com.echarm.apigateway.util.ValidateType;

public class FavoriteArticleValidator {

    private ValidateRule rule;
    private Map<FavoriteArticleField, ValidateType> map;
    private StringBuilder builder;

    public FavoriteArticleValidator(ValidateRule rule, Map<FavoriteArticleField, ValidateType> map) {
        this.rule = rule;
        this.map = map;
        builder = new StringBuilder();
    }

    public boolean validate(FavoriteArticle article) {
        if (article == null) return false;
        if (rule == null || map == null) return false;
        if (map.size() == 0) return true;

        // clear string builder
        builder.setLength(0);
        builder.append("Rule: " + rule);

        Set<FavoriteArticleField> fieldSet = map.keySet();
        for (FavoriteArticleField field : fieldSet) {
            boolean isFieldValidated = validateField(article, field, map.get(field));

            if (rule == ValidateRule.ALL_PASS && !isFieldValidated) {
                builder.append(" => Failed!\nField: " + field + ", Type: " + map.get(field) + "\n");
                return false;
            } else if (rule == ValidateRule.NOT_ALL_FAIL && isFieldValidated) {
                return true;
            }
        }

        if (rule == ValidateRule.ALL_PASS)  return true;
        else if (rule == ValidateRule.NOT_ALL_FAIL) {
            builder.append(" => Failed!\n");
            return false;
        }

        return false;
    }

    public String getValidateStr() {
        if (builder != null)
            return builder.toString();
        else
            return null;
    }

    private boolean validateField(FavoriteArticle article, FavoriteArticleField field, ValidateType type) {
        if (article == null || field == null || type == null) return false;

        switch(field) {
            case ARTICLE_TITLE:
                return validate(article.getArticleTitle(), type);
            case ARTICLE_ID:
                return validate(article.getArticleId(), type);
            case ARTICLE_CATEGORY:
                return validate(article.getArticleCategory(), type);
            case ARTICLE_CREATED_AT:
                return validate(article.getArticleCreatedAt(), type);
            case AUTHOR_ID:
                return validate(article.getAuthorId(), type);
            case AUTHOR_NAME:
                return validate(article.getAuthorName(), type);
            case AUTHOR_CATEGORY:
                return validate(article.getAuthorCategory(), type);
            case FAVORITE_AT:
                return validate(article.getFavoriteAt(), type);
            default:
        }

        return false;
    }

    private boolean validate(Object obj, ValidateType type) {
        if (type == null) return false;

        switch(type) {
            case NA:
                return true;
            case NON_EMPTY:
                // allow obj to be null
                if (obj instanceof String){
                    return !"".equals(obj);
                } else {
                    // this type only works on string
                    return true;
                }
            case NON_NULL:
                return obj != null;
            case NON_NULL_AND_EMPTY:
                if (obj instanceof String) {
                    return obj != null && !"".equals(obj);
                } else {
                    return obj != null;
                }
            default:
        }

        return false;
    }
}
