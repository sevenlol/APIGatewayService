package com.echarm.apigateway.accountsystem.util;

import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.security.util.RandomStringGenerator;

public class IdGenerationService {

    public static final String ACCOUNT_PREFIX = "ACC";
    public static final int RAND_STR_LENGTH = 8;

    public static String generateAccountId(Account account) {
        if (account == null) return null;

        String userType = account.getUserInfo() == null ? UserType.USER.toString() : account.getUserType().toString();
        return String.format("%s:%s:%s:%d",
                ACCOUNT_PREFIX,
                userType,
                RandomStringGenerator.getString(RAND_STR_LENGTH),
                System.currentTimeMillis());
    }
}
