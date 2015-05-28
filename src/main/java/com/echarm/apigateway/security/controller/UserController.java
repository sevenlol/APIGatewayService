package com.echarm.apigateway.security.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.accountsystem.error.InvalidParameterException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.UserAccount;
import com.echarm.apigateway.accountsystem.util.AccountFieldChecker;
import com.echarm.apigateway.security.service.UserDetailsImpl;
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

		if (auth == null) {
			// TODO throw error
		}

		Object user = auth.getPrincipal();
		if (!(user instanceof UserDetailsImpl)) {
			// TODO throw error
		}

		Account authAccount = ((UserDetailsImpl) user).getAccount();
		if (authAccount == null) {
			// TODO throw error
		}

		// TODO Check Input Account
		AccountFieldChecker checker = new AccountFieldChecker(AccountFieldChecker.ConnectType.NOT_ALL_FAIL);
		checker
			.setChecker(AccountFieldChecker.CheckField.email, AccountFieldChecker.CheckType.BOTH)
			.setChecker(AccountFieldChecker.CheckField.userInfo, AccountFieldChecker.CheckType.NON_NULL);
		if (!checker.check(account)) {
			// TODO check type fail, throw error
		}

		// TODO set un-updateable fields to null

		// TODO update my account

		return null;
	}
}
