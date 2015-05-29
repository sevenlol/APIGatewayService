package com.echarm.apigateway.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.accountsystem.error.InvalidParameterException;
import com.echarm.apigateway.accountsystem.error.MissingParameterErrorBody;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.UserAccount;
import com.echarm.apigateway.accountsystem.model.UserInfo;
import com.echarm.apigateway.accountsystem.repository.AccountRepositoryService;
import com.echarm.apigateway.accountsystem.util.AccountFieldChecker;
import com.echarm.apigateway.accountsystem.util.UserInfoFieldChecker;
import com.echarm.apigateway.accountsystem.util.UserType;
import com.echarm.apigateway.security.service.UserDetailsImpl;
import com.echarm.apigateway.security.util.CommaDelimitedStringParser;

@RestController
public class UserController {

	@Autowired
    private AccountRepositoryService repository;

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
	public Account updateUser(@RequestBody(required=false) UserAccount account, Authentication auth) throws Exception {

		if (auth == null) {
			throw new ServerSideProblemException("Authentication Object is NULL!");
		}

		Object user = auth.getPrincipal();
		if (!(user instanceof UserDetailsImpl)) {
			throw new ServerSideProblemException("Principal Object has a wrong type!");
		}

		Account authAccount = ((UserDetailsImpl) user).getAccount();
		if (authAccount == null) {
			throw new ServerSideProblemException("Account Object in Principal is NULL!");
		}

		// check userType
		if (authAccount.getUserType() == null || authAccount.getUserType() != UserType.USER) {
			throw new ServerSideProblemException("UserType should be USER!");
		}

		// Check Input Account
		AccountFieldChecker accountChecker = new AccountFieldChecker(AccountFieldChecker.ConnectType.NOT_ALL_FAIL);
		accountChecker
			.setChecker(AccountFieldChecker.CheckField.userInfo, AccountFieldChecker.CheckType.NON_NULL);
		if (!accountChecker.check(account)) {
			// check type fail, throw error
			MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("Object: user_info", "Body"));
            InvalidParameterException exception = new InvalidParameterException("Invalid JSON Body: user_info field missing!");
            exception.setErrorBody(body);
            throw exception;
		}

		// if userInfo is attached, check fields
		UserInfo info = account.getUserInfo();
		if (info != null) {
			// Check UserInfo
			UserInfoFieldChecker infoChecker = new UserInfoFieldChecker(UserInfoFieldChecker.ConnectType.NOT_ALL_FAIL);
			infoChecker
				.setChecker(UserInfoFieldChecker.CheckField.name, UserInfoFieldChecker.CheckType.BOTH)
				.setChecker(UserInfoFieldChecker.CheckField.gender, UserInfoFieldChecker.CheckType.NON_NULL)
				.setChecker(UserInfoFieldChecker.CheckField.phoneNumber, UserInfoFieldChecker.CheckType.BOTH)
				.setChecker(UserInfoFieldChecker.CheckField.address, UserInfoFieldChecker.CheckType.BOTH);

			if(!infoChecker.check(info)) {
				// user info check failed, throw error
				MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("Some Fields missing", "user_info in Body"));
	            InvalidParameterException exception = new InvalidParameterException("Some fields in user_info in JSON body missing!");
	            exception.setErrorBody(body);
	            throw exception;
			}
		}

		// set un-updateable fields to null
		nullifyStaticAccountField(account);

		// set account id
		account.setAccountId(authAccount.getAccountId());

		// update my account
		Account result = repository.updateAccount(account);

		// type not correct, server error
		// TODO Change findAllTypedAccountSpec to generate UserAccount Object instead of Account
        /*if (!(result instanceof UserAccount)) {
            throw new ServerSideProblemException("The result from repository should be a UserAccount object");
        }*/
		if (result.getUserType() != UserType.USER) {
			throw new ServerSideProblemException("The result from repository should have userType USER");
		}

        // TODO account fields check

		return result;
	}

	private void nullifyStaticAccountField(Account account) {
		if (account == null)
			return;

		account.setAccountType(null);
		account.setCreatedAt(null);
		account.setEmail(null);
		account.setPassword(null);
		account.setSalt(null);
		account.setUserType(null);
		account.setUserName(null);
	}
}
