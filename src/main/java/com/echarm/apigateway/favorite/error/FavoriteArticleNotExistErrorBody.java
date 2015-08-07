package com.echarm.apigateway.favorite.error;

import com.echarm.apigateway.accountsystem.error.ErrorBody;

public class FavoriteArticleNotExistErrorBody extends ErrorBody {

    public static final int CODE = 1011;
    public static final String MESSAGE = "Favorite article does not exist in list";

    public FavoriteArticleNotExistErrorBody(String listId, String articleId) {
        super(CODE, MESSAGE, generateDescription(listId, articleId));
    }

    public static String generateDescription(String listId, String articleId) {
        if (listId == null || articleId == null)
            return null;

        return String.format("Favorite article with ID: %s does not exist in list with ID: %s!", articleId, listId);
    }
}
