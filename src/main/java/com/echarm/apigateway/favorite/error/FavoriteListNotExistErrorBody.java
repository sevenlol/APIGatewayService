package com.echarm.apigateway.favorite.error;

import com.echarm.apigateway.accountsystem.error.ErrorBody;

public class FavoriteListNotExistErrorBody extends ErrorBody {

    public static final int CODE = 1010;
    public static final String MESSAGE = "Favorite list does not exist";

    public FavoriteListNotExistErrorBody(String listId) {
        super(CODE, MESSAGE, generateDescription(listId));
    }

    public static String generateDescription(String listId) {
        if (listId == null)
            return null;

        return String.format("Favorite list with ID: %s does not exist!", listId);
    }
}
