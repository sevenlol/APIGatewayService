package com.echarm.apigateway.popular.error;

import com.echarm.apigateway.accountsystem.error.ErrorBody;

public class PopularDoctorListNotExistErrorBody extends ErrorBody {
    public static final int CODE = 1008;
    public static final String MESSAGE = "Popular doctor list does not exist";

    public PopularDoctorListNotExistErrorBody(String listId) {
        super(CODE, MESSAGE, generateDescription(listId));
    }

    public static String generateDescription(String listId) {
        if (listId == null)
            return null;

        return String.format("Popular doctor list with ID: %s does not exist!", listId);
    }
}
