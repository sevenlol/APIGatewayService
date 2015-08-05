package com.echarm.apigateway.popular.error;

import com.echarm.apigateway.accountsystem.error.ErrorBody;

public class PopularQAListNotExistErrorBody extends ErrorBody {
    public static final int CODE = 1009;
    public static final String MESSAGE = "Popular qa list does not exist";

    public PopularQAListNotExistErrorBody(String listId) {
        super(CODE, MESSAGE, generateDescription(listId));
    }

    public static String generateDescription(String listId) {
        if (listId == null)
            return null;

        return String.format("Popular qa list with ID: %s does not exist!", listId);
    }
}
