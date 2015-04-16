package com.echarm.apigateway.accountsystem.util;

public enum AccountType {
    FACEBOOK,
    GOOGLE,
    ECHARM;

    public static AccountType isAccountTypeExist(String accountType) {
        for (AccountType a : AccountType.values()) {
            if (a.name().equalsIgnoreCase(accountType)) {
                return a;
            }
        }
        return null;
    }
}
