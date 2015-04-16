package com.echarm.apigateway.accountsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.repository.AccountRepositoryService;
import com.echarm.apigateway.accountsystem.repository.AccountSpecification;
import com.echarm.apigateway.accountsystem.repository.AccountSpecificationFactory;

@RestController
public class DeleteAllAccountController {

    @Autowired
    private AccountRepositoryService repository;

    @RequestMapping(value = "/accounts", method = RequestMethod.DELETE)
    public List<Account> deleteAllAccount() throws ResourceNotExistException, NoContentException, ResourceExistException {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        // delete all accounts
        // when no accounts found, NoContentException is thrown
        AccountSpecification specification = AccountSpecificationFactory.getDeleteAllAccountSpecification(null);
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
