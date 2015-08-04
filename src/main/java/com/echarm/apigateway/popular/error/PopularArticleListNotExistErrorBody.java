package com.echarm.apigateway.popular.error;

import com.echarm.apigateway.accountsystem.error.ErrorBody;


public class PopularArticleListNotExistErrorBody extends ErrorBody {
    public static final int CODE = 1007;
    public static final String MESSAGE = "Popular article list does not exist";

    public PopularArticleListNotExistErrorBody(String listId) {
        super(CODE, MESSAGE, generateDescription(listId));
    }

    public static String generateDescription(String listId) {
        if (listId == null)
            return null;

        return String.format("Popular article list with ID: %s does not exist!", listId);
    }
}
