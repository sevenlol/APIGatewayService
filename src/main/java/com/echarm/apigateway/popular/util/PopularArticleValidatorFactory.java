package com.echarm.apigateway.popular.util;

import com.echarm.apigateway.util.ValidateRule;
import com.echarm.apigateway.util.ValidateType;

public class PopularArticleValidatorFactory {

    public static PopularArticleValidator getAllFieldNotNullValidator() {
        return new PopularArticleValidatorBuilder()
            .add(PopularArticleField.ARTICLE_CATEGORY, ValidateType.NON_NULL)
            .add(PopularArticleField.ARTICLE_ID, ValidateType.NON_NULL)
            .add(PopularArticleField.ARTICLE_TITLE, ValidateType.NON_NULL)
            .add(PopularArticleField.AUTHOR_CATEGORY, ValidateType.NON_NULL)
            .add(PopularArticleField.AUTHOR_ID, ValidateType.NON_NULL)
            .add(PopularArticleField.AUTHOR_NAME, ValidateType.NON_NULL)
            .getValidator(ValidateRule.ALL_PASS);
    }

    public static PopularArticleValidator getAllFieldNonEmptyValidator() {
        return new PopularArticleValidatorBuilder()
            .add(PopularArticleField.ARTICLE_CATEGORY, ValidateType.NON_EMPTY)
            .add(PopularArticleField.ARTICLE_ID, ValidateType.NON_EMPTY)
            .add(PopularArticleField.ARTICLE_TITLE, ValidateType.NON_EMPTY)
            .add(PopularArticleField.AUTHOR_CATEGORY, ValidateType.NON_EMPTY)
            .add(PopularArticleField.AUTHOR_ID, ValidateType.NON_EMPTY)
            .add(PopularArticleField.AUTHOR_NAME, ValidateType.NON_EMPTY)
            .getValidator(ValidateRule.ALL_PASS);
    }
}
