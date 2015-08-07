package com.echarm.apigateway.security.util;

import org.springframework.security.core.Authentication;

import com.echarm.apigateway.security.error.CustomExceptionFactory;
import com.echarm.apigateway.security.service.UserDetailsImpl;

public class SecurityUtilities {

    public static void checkAuthenticationObject(Authentication auth) throws Exception {
        String errorMsg = null;

        if (auth == null) {
            errorMsg = "Authentication object should not be null";
        } else if (auth.getPrincipal() == null) {
            errorMsg = "Principal object should not be null";
        } else if (!(auth.getPrincipal() instanceof UserDetailsImpl)) {
            errorMsg = "Principal object should be type UserDetailsImpl";
        } else if (((UserDetailsImpl) auth.getPrincipal()).getAccount() == null) {
            errorMsg = "Principal object should contain an Account object";
        } else if (((UserDetailsImpl) auth.getPrincipal()).getAccount().getAccountId() == null) {
            errorMsg = "Account object should have non-null account id";
        }

        if (errorMsg != null) {
            throw CustomExceptionFactory.getServerProblemException(errorMsg);
        }
    }
}
