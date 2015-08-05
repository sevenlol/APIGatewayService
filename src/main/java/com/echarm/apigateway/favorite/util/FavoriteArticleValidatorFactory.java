package com.echarm.apigateway.favorite.util;

import com.echarm.apigateway.util.ValidateRule;
import com.echarm.apigateway.util.ValidateType;

public class FavoriteArticleValidatorFactory {

    public static FavoriteArticleValidator getAllFieldNotNullValidator() {
        return new FavoriteArticleValidatorBuilder()
            .add(FavoriteArticleField.ARTICLE_CATEGORY, ValidateType.NON_NULL)
            .add(FavoriteArticleField.ARTICLE_ID, ValidateType.NON_NULL)
            .add(FavoriteArticleField.ARTICLE_TITLE, ValidateType.NON_NULL)
            .add(FavoriteArticleField.ARTICLE_CREATED_AT, ValidateType.NON_NULL)
            .add(FavoriteArticleField.AUTHOR_CATEGORY, ValidateType.NON_NULL)
            .add(FavoriteArticleField.AUTHOR_ID, ValidateType.NON_NULL)
            .add(FavoriteArticleField.AUTHOR_NAME, ValidateType.NON_NULL)
            .add(FavoriteArticleField.FAVORITE_AT, ValidateType.NON_NULL)
            .getValidator(ValidateRule.ALL_PASS);
    }

    public static FavoriteArticleValidator getAllFieldNonEmptyValidator() {
        return new FavoriteArticleValidatorBuilder()
            .add(FavoriteArticleField.ARTICLE_CATEGORY, ValidateType.NON_EMPTY)
            .add(FavoriteArticleField.ARTICLE_ID, ValidateType.NON_EMPTY)
            .add(FavoriteArticleField.ARTICLE_TITLE, ValidateType.NON_EMPTY)
            .add(FavoriteArticleField.ARTICLE_CREATED_AT, ValidateType.NON_EMPTY)
            .add(FavoriteArticleField.AUTHOR_CATEGORY, ValidateType.NON_EMPTY)
            .add(FavoriteArticleField.AUTHOR_ID, ValidateType.NON_EMPTY)
            .add(FavoriteArticleField.AUTHOR_NAME, ValidateType.NON_EMPTY)
            .add(FavoriteArticleField.FAVORITE_AT, ValidateType.NON_EMPTY)
            .getValidator(ValidateRule.ALL_PASS);
    }
}
