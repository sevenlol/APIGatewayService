package com.echarm.apigateway.accountsystem.repository;

import java.util.List;

import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.model.Account;

public interface AccountRepositoryService {
    public Account createAccount(Account account) throws Exception;
    public Account updateAccount(Account account) throws Exception;
    public Account deleteAccount(Account account) throws Exception;
    public List<Account> query(AccountSpecification specification) throws ResourceNotExistException, NoContentException, ResourceExistException;
}
