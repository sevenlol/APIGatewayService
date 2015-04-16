package com.echarm.apigateway.accountsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.AdminAccount;
import com.echarm.apigateway.accountsystem.model.DoctorAccount;
import com.echarm.apigateway.accountsystem.model.UserAccount;
import com.echarm.apigateway.accountsystem.repository.AccountRepositoryService;
import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.accountsystem.util.ControllerUtil;

@RestController
public class PartiallyUpdateAccountController {

    @Autowired
    private AccountRepositoryService repository;

    @RequestMapping(value = "/accounts/users/{accountId}", method = RequestMethod.PATCH)
    public Account partiallyUpdateUserAccount(@PathVariable String accountId, @RequestBody(required=false) UserAccount account)
                                              throws Exception {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // check inputs from the http request
        ControllerUtil.checkRequestInput(true, false, account, false, false, null);

        // set accountId
        account.setAccountId(accountId);

        // partially update the user account
        // when Account with {accountId} doesn't exist, ResourceNotExistException will be thrown with AccountNotExistErrorBody
        // when the updated Account is exactly the same as the input Account, NoContentException is thrown
        Account result = repository.updateAccount(account);

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

    @RequestMapping(value = "/accounts/doctors/{category}/{accountId}", method = RequestMethod.PATCH)
    public Account partiallyUpdateDoctorAccount(@PathVariable String accountId, @PathVariable String category, @RequestBody(required=false) DoctorAccount account)
                                                throws Exception {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // check inputs from the http request
        ControllerUtil.checkRequestInput(true, false, account, true, false, category);

        // set category and accountId
        account.getUserInfo().setCategory(Category.valueOf(category));
        account.setAccountId(accountId);

        // partially update the user account
        // when Account with {accountId} doesn't exist, ResourceNotExistException will be thrown with AccountNotExistErrorBody
        // when the updated Account is exactly the same as the input Account, NoContentException is thrown
        Account result = repository.updateAccount(account);

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

    @RequestMapping(value = "/accounts/admins/{accountId}", method = RequestMethod.PATCH)
    public Account partiallyUpdateAdminAccount(@PathVariable String accountId, @RequestBody(required=false) AdminAccount account)
                                               throws Exception {
        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // check inputs from the http request
        ControllerUtil.checkRequestInput(true, false, account, false, false, null);

        // set accountId
        account.setAccountId(accountId);

        // partially update the user account
        // when Account with {accountId} doesn't exist, ResourceNotExistException will be thrown with AccountNotExistErrorBody
        // when the updated Account is exactly the same as the input Account, NoContentException is thrown
        Account result = repository.updateAccount(account);

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
