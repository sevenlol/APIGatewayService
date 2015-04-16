package com.echarm.apigateway.accountsystem.util;

public enum UserType {
    USER,
    DOCTOR,
    ADMIN,
    ARBITRARY;

    public static UserType isUserTypeExist(String userType) {
        for (UserType u : UserType.values()) {
            if (u.name().equalsIgnoreCase(userType)) {
                return u;
            }
        }
        return null;
    }
}
