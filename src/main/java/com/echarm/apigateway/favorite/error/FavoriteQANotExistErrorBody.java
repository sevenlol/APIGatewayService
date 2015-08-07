package com.echarm.apigateway.favorite.error;

import com.echarm.apigateway.accountsystem.error.ErrorBody;

public class FavoriteQANotExistErrorBody extends ErrorBody {

    public static final int CODE = 1011;
    public static final String MESSAGE = "Favorite qa does not exist in list";

    public FavoriteQANotExistErrorBody(String listId, String questionId) {
        super(CODE, MESSAGE, generateDescription(listId, questionId));
    }

    public static String generateDescription(String listId, String questionId) {
        if (listId == null || questionId == null)
            return null;

        return String.format("Favorite qa with ID: %s does not exist in list with ID: %s!", questionId, listId);
    }
}
