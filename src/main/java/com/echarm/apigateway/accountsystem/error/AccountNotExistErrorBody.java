package com.echarm.apigateway.accountsystem.error;

import com.echarm.apigateway.accountsystem.util.UserType;


public class AccountNotExistErrorBody extends ErrorBody{
	public static final int CODE = 1005;
	public static final String MESSAGE = "Account does not exist";

	public AccountNotExistErrorBody(String accountId, UserType userType) {
		super(CODE, MESSAGE, generateDescription(accountId, userType));
	}

	public static String generateDescription(String accountId, UserType userType) {
	    if (accountId == null || userType == null)
	        return null;

		return String.format("Account with ID: \"%s\", UserType: \"%s\" does not exist!", accountId, userType.name());
	}
}
