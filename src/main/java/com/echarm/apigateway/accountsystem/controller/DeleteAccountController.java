package com.echarm.apigateway.accountsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class DeleteAccountController {

    @Autowired
    private AccountRepositoryService repository;

    @RequestMapping(value = "/accounts/users/{accountId}", method = RequestMethod.DELETE)
    public Account deleteUserAccount(@PathVariable String accountId) throws Exception {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // set account id and userType
        UserAccount account = new UserAccount();
        account.setAccountId(accountId);

        // delete Account with {accountId}
        // when Account with {accountId} doesn't exist, ResourceNotExistException will be thrown with AccountNotExistErrorBody
        Account result = repository.deleteAccount(account);

        // query result should not be null, server error
        if (result == null) {
            throw new ServerSideProblemException("The result (Account) from repository should not be null");
        }

        // type not correct, server error
        if (!(result instanceof UserAccount)) {
            throw new ServerSideProblemException("The result (Account) from repository should be a UserAccount object");
        }

        // TODO check account fields

        return result;
    }

    @RequestMapping(value = "/accounts/doctors/{category}/{accountId}", method = RequestMethod.DELETE)
    public Account deleteDoctorAccount(@PathVariable String category, @PathVariable String accountId)
                                       throws Exception {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // check Category
        ControllerUtil.checkRequestInput(false, false, null, true, false, category);

        // set account id and userType
        DoctorInfo info = new DoctorInfo();
        info.setCategory(Category.valueOf(category));
        DoctorAccount account = new DoctorAccount();
        account.setAccountId(accountId);
        account.setUserInfo(info);

        // delete DoctorAccount with {accountId}
        // when DoctorAccount with {accountId} under {category} doesn't exist, ResourceNotExistException will be thrown with AccountNotExistErrorBody
        Account result = repository.deleteAccount(account);

        // result null, server error
        if (result == null) {
            throw new ServerSideProblemException("The result (Account) from repository should not be null");
        }

        // type not correct, server error
        if (!(result instanceof UserAccount)) {
            throw new ServerSideProblemException("The result (Account) from repository should be a DoctorAccount object");
        }

        // TODO check account fields

        return result;
    }

    @RequestMapping(value = "/accounts/admins/{accountId}", method = RequestMethod.DELETE)
    public Account deleteAdminAccount(@PathVariable String accountId) throws Exception {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // set account id and userType
        AdminAccount account = new AdminAccount();
        account.setAccountId(accountId);

        // delete Account with {accountId}
        // when Account with {accountId} doesn't exist, ResourceNotExistException will be thrown with AccountNotExistErrorBody
        Account result = repository.deleteAccount(account);

        // result null, server error
        if (result == null) {
            throw new ServerSideProblemException("The result (Account) from repository should not be null");
        }

        // type not correct, server error
        if (!(result instanceof AdminAccount)) {
            throw new ServerSideProblemException("The result (Account) from repository should be a AdminAccount object");
        }

        // TODO account fields check

        return result;
    }
}
