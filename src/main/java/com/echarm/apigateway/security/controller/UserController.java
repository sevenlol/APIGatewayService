package com.echarm.apigateway.security.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.accountsystem.error.InvalidParameterException;
import com.echarm.apigateway.accountsystem.model.UserAccount;
import com.echarm.apigateway.security.util.CommaDelimitedStringParser;

@RestController
public class UserController {

	@RequestMapping(value = "/members/users", method = RequestMethod.GET)
    public List<UserAccount> getUsers(@RequestParam(value = "id_list", required = false) String accountIdListStr) throws InvalidParameterException {

		if (accountIdListStr != null) {
			// not null => parse str
			String[] accountIdList = CommaDelimitedStringParser.parse(accountIdListStr);
			// TODO get user list here
		}

		// TODO get all user here

    	return null;
    }

	@RequestMapping(value = "/members/users", method = RequestMethod.PUT)
	public UserAccount updateUser(@RequestBody(required=false) UserAccount account, Authentication auth) {
		// TODO finish this
		return null;
	}
}
