package com.echarm.apigateway.accountsystem.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.model.Account;


public abstract class AccountSpecification {
    protected Account account;

    public AccountSpecification(Account account) {
        this.account = account;
    }

    abstract List<Account> doActions(MongoTemplate mongoTemplate) throws ResourceNotExistException, NoContentException, ResourceExistException;
}
