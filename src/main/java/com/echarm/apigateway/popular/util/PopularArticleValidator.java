package com.echarm.apigateway.popular.util;

import java.util.Map;
import java.util.Set;

import com.echarm.apigateway.popular.model.PopularArticle;
import com.echarm.apigateway.util.ValidateRule;
import com.echarm.apigateway.util.ValidateType;

public class PopularArticleValidator {

    private ValidateRule rule;
    private Map<PopularArticleField, ValidateType> map;
    private StringBuilder builder;

    public PopularArticleValidator(ValidateRule rule, Map<PopularArticleField, ValidateType> map) {
        this.rule = rule;
        this.map = map;
        builder = new StringBuilder();
    }

    public boolean validate(PopularArticle article) {
        if (article == null) return false;
        if (rule == null || map == null) return false;
        if (map.size() == 0) return true;

        // clear string builder
        builder.setLength(0);
        builder.append("Rule: " + rule);

        Set<PopularArticleField> fieldSet = map.keySet();
        for (PopularArticleField field : fieldSet) {
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

    private boolean validateField(PopularArticle article, PopularArticleField field, ValidateType type) {
        if (article == null || field == null || type == null) return false;

        switch(field) {
            case ARTICLE_TITLE:
                return validate(article.getArticleTitle(), type);
            case ARTICLE_ID:
                return validate(article.getArticleId(), type);
            case ARTICLE_CATEGORY:
                return validate(article.getArticleCategory(), type);
            case AUTHOR_ID:
                return validate(article.getAuthorId(), type);
            case AUTHOR_NAME:
                return validate(article.getAuthorName(), type);
            case AUTHOR_CATEGORY:
                return validate(article.getAuthorCategory(), type);
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
