package com.echarm.apigateway.accountsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.AdminAccount;
import com.echarm.apigateway.accountsystem.model.DoctorAccount;
import com.echarm.apigateway.accountsystem.model.DoctorInfo;
import com.echarm.apigateway.accountsystem.model.UserAccount;
import com.echarm.apigateway.accountsystem.repository.AccountRepositoryService;
import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.accountsystem.util.ControllerUtil;

@RestController
public class CreateAccountController {

    @Autowired
    private AccountRepositoryService repository;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/accounts/users", method = RequestMethod.POST)
    public Account createUserAccount(@RequestBody(required=false) UserAccount account)
                                     throws Exception {

    	// repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // check inputs from the http request
        ControllerUtil.checkRequestInput(true, true, account, false, false, null);

        // encode password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        account.setPassword(encoder.encode(account.getPassword()));

        Account result = null;

		result = repository.createAccount(account);


        // result null, server error
        if (result == null) {
        	throw new ServerSideProblemException("The result from repository should not be null");
        }

        // type not correct, server error
        if (!(result instanceof UserAccount)) {
        	throw new ServerSideProblemException("The result from repository should be a UserAccount object");
        }

        // TODO account fields check

        return result;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/accounts/doctors/{category}", method = RequestMethod.POST)
    public Account createDoctorAccount(@RequestBody(required=false) DoctorAccount account, @PathVariable String category)
    		                           throws Exception {

    	// repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // check inputs from the http request
        ControllerUtil.checkRequestInput(true, true, account, true, false, category);

        // encode password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        account.setPassword(encoder.encode(account.getPassword()));

        // set category
        DoctorInfo info = account.getUserInfo();
        if (info == null) {
        	throw new ServerSideProblemException("UserInfo in Account should not be null");
        }
        info.setCategory(Category.valueOf(category));
        account.setUserInfo(info);

        // create doctor account
        Account result = null;

		result = repository.createAccount(account);


        // result null, server error
        if (result == null) {
        	throw new ServerSideProblemException("The result from repository should not be null");
        }

        // type not correct, server error
        if (!(result instanceof DoctorAccount)) {
        	throw new ServerSideProblemException("The result from repository should be a DoctorAccount object");
        }

        // TODO account fields check

        return result;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/accounts/admins", method = RequestMethod.POST)
    public Account createAdminAccount(@RequestBody(required=false) AdminAccount account)
    		                          throws Exception {

    	// repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // check inputs from the http request
        ControllerUtil.checkRequestInput(true, true, account, false, false, null);

        // encode password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        account.setPassword(encoder.encode(account.getPassword()));

        Account result = null;

		result = repository.createAccount(account);


        // result null, server error
        if (result == null) {
        	throw new ServerSideProblemException("The result from repository should not be null");
        }

        // type not correct, server error
        if (!(result instanceof AdminAccount)) {
        	throw new ServerSideProblemException("The result from repository should be a AdminAccount object");
        }

        // TODO account fields check

        return result;
    }
}
