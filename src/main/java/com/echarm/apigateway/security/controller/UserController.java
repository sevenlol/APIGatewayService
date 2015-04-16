package com.echarm.apigateway.security.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.security.service.UserDetailsImpl;

@RestController
public class UserController {

    /*
     * if the /user resource is reachable, it returns the currently authenticated user (an Authentication)
     * else, an 401 response is returned
     */
    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/user/me")
    public Account getUserAccount(Principal user) {
        if (user instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) user).getAccount();
        }

        return null;
    }
}
