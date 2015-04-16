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
import com.echarm.apigateway.accountsystem.model.DoctorAccount;
import com.echarm.apigateway.accountsystem.model.DoctorInfo;
import com.echarm.apigateway.accountsystem.model.UserAccount;
import com.echarm.apigateway.accountsystem.repository.AccountRepositoryService;
import com.echarm.apigateway.accountsystem.repository.AccountSpecification;
import com.echarm.apigateway.accountsystem.repository.AccountSpecificationFactory;
import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.accountsystem.util.ControllerUtil;

@RestController
public class DeleteAllTypedAccountController {

    @Autowired
    private AccountRepositoryService repository;

    @RequestMapping(value = "/accounts/users", method = RequestMethod.DELETE)
    public List<Account> deleteAllUserAccount() throws ResourceNotExistException, NoContentException, ResourceExistException {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // set account id and userType
        UserAccount account = new UserAccount();

        // delete all UserAccounts
        // when no accounts are found, NoContentException is thrown
        AccountSpecification specification = AccountSpecificationFactory.getDeleteTypedAccountSpecification(account);
        List<Account> results = repository.query(specification);

        // query result should not be null, server error
        if (results == null) {
            throw new ServerSideProblemException("The result (List<Account>) from the repository should not be null");
        }

        // query result should have at least one element
        // if not, server error
        if (results.size() <= 0) {
            throw new ServerSideProblemException("The result (List<Account>) size should be at least 1");
        }

        return results;
    }

    @RequestMapping(value = "/accounts/doctors", method = RequestMethod.DELETE)
    public List<Account> deleteAllDoctorAccount() throws ResourceNotExistException, NoContentException, ResourceExistException {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // set account id and userType
        DoctorInfo info = new DoctorInfo();
        info.setCategory(Category.Arbitrary);
        DoctorAccount account = new DoctorAccount();
        account.setUserInfo(info);

        // delete all UserAccounts
        // when no accounts are found, NoContentException is thrown
        AccountSpecification specification = AccountSpecificationFactory.getDeleteTypedAccountSpecification(account);
        List<Account> results = repository.query(specification);

        // query result should not be null, server error
        if (results == null) {
            throw new ServerSideProblemException("The result (List<Account>) from the repository should not be null");
        }

        // query result should have at least one element
        // if not, server error
        if (results.size() <= 0) {
            throw new ServerSideProblemException("The result (List<Account>) size should be at least 1");
        }

        return results;
    }

    @RequestMapping(value = "/accounts/doctors/{category}", method = RequestMethod.DELETE)
    public List<Account> deleteAllDoctorInCategoryAccount(@PathVariable String category)
                                                          throws ResourceNotExistException, NoContentException, InvalidParameterException, ResourceExistException {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        ControllerUtil.checkRequestInput(false, false, null, true, false, category);

        // set account id and userType
        DoctorInfo info = new DoctorInfo();
        info.setCategory(Category.valueOf(category));
        DoctorAccount account = new DoctorAccount();
        account.setUserInfo(info);

        // delete all UserAccounts
        // when no accounts are found, NoContentException is thrown
        AccountSpecification specification = AccountSpecificationFactory.getDeleteTypedAccountSpecification(account);
        List<Account> results = repository.query(specification);

        // query result should not be null, server error
        if (results == null) {
            throw new ServerSideProblemException("The result (List<Account>) from the repository should not be null");
        }

        // query result should have at least one element
        // if not, server error
        if (results.size() <= 0) {
            throw new ServerSideProblemException("The result (List<Account>) size should be at least 1");
        }

        return results;
    }

    @RequestMapping(value = "/accounts/admins", method = RequestMethod.DELETE)
    public List<Account> deleteAllAdminAccount() throws ResourceNotExistException, NoContentException, ResourceExistException {
        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // set account id and userType
        AdminAccount account = new AdminAccount();

        // delete all UserAccounts
        // when no accounts are found, NoContentException is thrown
        AccountSpecification specification = AccountSpecificationFactory.getDeleteTypedAccountSpecification(account);
        List<Account> results = repository.query(specification);

        // query result should not be null, server error
        if (results == null) {
            throw new ServerSideProblemException("The result (List<Account>) from the repository should not be null");
        }

        // query result should have at least one element
        // if not, server error
        if (results.size() <= 0) {
            throw new ServerSideProblemException("The result (List<Account>) size should be at least 1");
        }

        return results;
    }
}
