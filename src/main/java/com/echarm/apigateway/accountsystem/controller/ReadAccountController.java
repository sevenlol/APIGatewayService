package com.echarm.apigateway.accountsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.accountsystem.error.InvalidParameterException;
import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.AdminAccount;
import com.echarm.apigateway.accountsystem.model.ArbitraryAccount;
import com.echarm.apigateway.accountsystem.model.DoctorAccount;
import com.echarm.apigateway.accountsystem.model.DoctorInfo;
import com.echarm.apigateway.accountsystem.model.UserAccount;
import com.echarm.apigateway.accountsystem.repository.AccountRepositoryService;
import com.echarm.apigateway.accountsystem.repository.AccountSpecification;
import com.echarm.apigateway.accountsystem.repository.AccountSpecificationFactory;
import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.accountsystem.util.ControllerUtil;

@RestController
public class ReadAccountController {

    @Autowired
    private AccountRepositoryService repository;

    @RequestMapping(value = "/accounts/users/{accountId}", method = RequestMethod.GET)
    public Account readUserAccount(@PathVariable String accountId) throws ResourceNotExistException, NoContentException, ResourceExistException {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // set account id and userType
        UserAccount account = new UserAccount();
        account.setAccountId(accountId);

        // get the UserAccount with {accountId}
        // when no accounts found, ResourceNotExistException is thrown with AccountNotExistErrorBody
        AccountSpecification specification = AccountSpecificationFactory.getFindAccountByIdSpecification(account);
        List<Account> results = repository.query(specification);

        // query result should not be null, server error
        if (results == null) {
            throw new ServerSideProblemException("The result (List<Account>) from the repository should not be null");
        }

        // query result should have at least one element
        // if not, server error
        if (results.size() != 1) {
            throw new ServerSideProblemException("The result (List<Account>) size should be 1");
        }

        // wrong account type
        if (!(results.get(0) instanceof UserAccount)) {
            throw new ServerSideProblemException("The result (List<Account>.get(0)) should be a UserAccount");
        }

        return results.get(0);
    }

    @RequestMapping(value = "/accounts/doctors/{category}/{accountId}", method = RequestMethod.GET)
    public Account readDoctorAccount(@PathVariable String category, @PathVariable String accountId)
                                     throws ResourceNotExistException, InvalidParameterException, NoContentException, ResourceExistException {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // check Category
        ControllerUtil.checkRequestInput(false, false, null, true, true, category);

        // set account id, category and userType
        DoctorInfo info = new DoctorInfo();
        info.setCategory(Category.valueOf(category));
        DoctorAccount account = new DoctorAccount();
        account.setAccountId(accountId);
        account.setUserInfo(info);

        // get the DoctorAccount with {accountId}
        // when no accounts found, ResourceNotExistException is thrown with AccountNotExistErrorBody
        AccountSpecification specification = AccountSpecificationFactory.getFindAccountByIdSpecification(account);
        List<Account> results = repository.query(specification);

        // query result should not be null, server error
        if (results == null) {
            throw new ServerSideProblemException("The result (List<Account>) from the repository should not be null");
        }

        // query result should have at least one element
        // if not, server error
        if (results.size() != 1) {
            throw new ServerSideProblemException("The result (List<Account>) size should be 1");
        }

        // wrong account type
        if (!(results.get(0) instanceof DoctorAccount)) {
            throw new ServerSideProblemException("The result (List<Account>.get(0)) should be a DoctorAccount");
        }

        return results.get(0);
    }

    @RequestMapping(value = "/accounts/admins/{accountId}", method = RequestMethod.GET)
    public Account readAdminAccount(@PathVariable String accountId) throws ResourceNotExistException, NoContentException, ResourceExistException {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // set account id and userType
        AdminAccount account = new AdminAccount();
        account.setAccountId(accountId);

        // get the AdminAccount with {accountId}
        // when no accounts found, ResourceNotExistException is thrown with AccountNotExistErrorBody
        AccountSpecification specification = AccountSpecificationFactory.getFindAccountByIdSpecification(account);
        List<Account> results = repository.query(specification);

        // query result should not be null, server error
        if (results == null) {
            throw new ServerSideProblemException("The result (List<Account>) from the repository should not be null");
        }

        // query result should have at least one element
        // if not, server error
        if (results.size() != 1) {
            throw new ServerSideProblemException("The result (List<Account>) size should be 1");
        }

        // wrong account type
        if (!(results.get(0) instanceof AdminAccount)) {
            throw new ServerSideProblemException("The result (List<Account>.get(0)) should be a AdminAccount");
        }

        return results.get(0);
    }

    @RequestMapping(value = "/accounts/arbitrarys/{accountId}", method = RequestMethod.GET)
    public Account readArbitraryAccount(@PathVariable String accountId) throws ResourceNotExistException, NoContentException, ResourceExistException {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // set account id and userType
        ArbitraryAccount account = new ArbitraryAccount();
        account.setAccountId(accountId);

        // get the Account with {accountId}
        // when no accounts found, ResourceNotExistException is thrown with AccountNotExistErrorBody
        AccountSpecification specification = AccountSpecificationFactory.getFindAccountByIdSpecification(account);
        List<Account> results = repository.query(specification);

        // query result should not be null, server error
        if (results == null) {
            throw new ServerSideProblemException("The result (List<Account>) from the repository should not be null");
        }

        // query result should have at least one element
        // if not, server error
        if (results.size() != 1) {
            throw new ServerSideProblemException("The result (List<Account>) size should be 1");
        }

        return results.get(0);
    }
}
