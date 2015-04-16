package com.echarm.apigateway.accountsystem.repository;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.model.Account;

public abstract class AccountBasicSpecification {
    protected Account account;

    public AccountBasicSpecification(Account account) {
        this.account = account;
    }

    abstract Account doActions(MongoTemplate mongoTemplate) throws ResourceNotExistException, NoContentException, ResourceExistException;
}
