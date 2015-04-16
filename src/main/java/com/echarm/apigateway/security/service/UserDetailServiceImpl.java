package com.echarm.apigateway.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.repository.AccountRepository;
import com.echarm.apigateway.accountsystem.repository.AccountSpecification;
import com.echarm.apigateway.accountsystem.repository.AccountSpecificationFactory;

public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        if (username == null)
            return null;

        Account account = new Account();
        account.setUserName(username);

        // get all Accounts in the database
        // when no accounts found in the database, NoContentException is thrown
        AccountSpecification specification = AccountSpecificationFactory.getFindAllAccountSpecification(account);
        List<Account> results = null;
        try {
            results = repository.query(specification);
        } catch (ResourceNotExistException e) {
            return null;
        } catch (NoContentException e) {
            return null;
        } catch (ResourceExistException e) {
            return null;
        } finally {
            if (results != null && results.size() == 1) {
                return new UserDetailsImpl(results.get(0));
            }
        }

        return null;
    }
}
