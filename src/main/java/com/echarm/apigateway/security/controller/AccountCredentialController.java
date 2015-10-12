package com.echarm.apigateway.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.accountsystem.model.Account;

@RestController
public class AccountCredentialController {

    @RequestMapping(value = "/me/reset_password", method = RequestMethod.POST)
    public Account resetMyPassword() {
        return null;
    }

    @RequestMapping(value = "/me/password", method = RequestMethod.PUT)
    public Account changeMyPassword() {
        return null;
    }

    @RequestMapping(value = "/me/email", method = RequestMethod.PUT)
    public Account changeMyEmail() {
        return null;
    }
}
