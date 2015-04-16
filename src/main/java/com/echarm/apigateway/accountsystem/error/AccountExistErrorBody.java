package com.echarm.apigateway.accountsystem.error;


public class AccountExistErrorBody extends ErrorBody {
    public static final int CODE = 4001;
    public static final String MESSAGE = "Account already exists";

    public AccountExistErrorBody(String username, String email) {
        super(CODE, MESSAGE, generateDescription(username, email));
    }

    public static String generateDescription(String username, String email) {
        if (username == null && email == null)
            return null;

        return String.format("Account with %s %s already exists!",
                             username == null ? "" : String.format("Username: '%s'", username),
                             email    == null ? "" : String.format("Email: '%s'", email));
    }
}
