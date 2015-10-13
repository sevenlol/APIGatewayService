package com.echarm.apigateway.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.accountsystem.error.InvalidParameterException;
import com.echarm.apigateway.accountsystem.error.MissingParameterErrorBody;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.AdminAccount;
import com.echarm.apigateway.accountsystem.model.DoctorAccount;
import com.echarm.apigateway.accountsystem.model.DoctorInfo;
import com.echarm.apigateway.accountsystem.model.UserAccount;
import com.echarm.apigateway.accountsystem.repository.AccountRepositoryService;
import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.security.service.UserDetailsImpl;

@RestController
public class AccountCredentialController {

    @Autowired
    private AccountRepositoryService repository;

    @RequestMapping(value = "/me/reset_password", method = RequestMethod.POST)
    public Account resetMyPassword(Authentication auth) {
        return null;
    }

    @RequestMapping(value = "/me/password", method = RequestMethod.PUT)
    public Account changeMyPassword(
            Authentication auth,
            @RequestBody(required = false) Account account) throws Exception {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }
        if (auth == null) {
            throw new ServerSideProblemException("auth null");
        }

        Object user = auth.getPrincipal();
        if (user == null || !(user instanceof UserDetailsImpl)) {
            throw new ServerSideProblemException("Authentication object should be nonnull and has type UserDetailsImpl");
        }

        Account currUserAccount = ((UserDetailsImpl) user).getAccount();
        if (currUserAccount == null || currUserAccount.getAccountId() == null) {
            throw new ServerSideProblemException("Authentication object should contain a valid account object");
        }

        if (account == null || account.getPassword() == null) {
            MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("Json Object: Account", "Body"));
            InvalidParameterException exception = new InvalidParameterException("No Json Body in the Request!");
            exception.setErrorBody(body);
            throw exception;
        }

        Account updateAccount = getUpdateAccount(currUserAccount);
        updateAccount.setAccountId(currUserAccount.getAccountId());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        updateAccount.setPassword(encoder.encode(account.getPassword()));

        Account result = repository.updateAccount(account);

        // result null, server error
        if (result == null) {
            throw new ServerSideProblemException("The result from repository should not be null");
        }

        return result;
    }

    @RequestMapping(value = "/me/email", method = RequestMethod.PUT)
    public Account changeMyEmail(
            Authentication auth,
            @RequestBody(required = false) Account account) throws Exception {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }
        if (auth == null) {
            throw new ServerSideProblemException("auth null");
        }

        Object user = auth.getPrincipal();
        if (user == null || !(user instanceof UserDetailsImpl)) {
            throw new ServerSideProblemException("Authentication object should be nonnull and has type UserDetailsImpl");
        }

        Account currUserAccount = ((UserDetailsImpl) user).getAccount();
        if (currUserAccount == null || currUserAccount.getAccountId() == null) {
            throw new ServerSideProblemException("Authentication object should contain a valid account object");
        }

        if (account == null || account.getEmail() == null) {
            MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("Json Object: Account", "Body"));
            InvalidParameterException exception = new InvalidParameterException("No Json Body in the Request!");
            exception.setErrorBody(body);
            throw exception;
        }

        Account updateAccount = getUpdateAccount(currUserAccount);
        updateAccount.setAccountId(currUserAccount.getAccountId());
        updateAccount.setEmail(account.getEmail());

        Account result = repository.updateAccount(account);

        // result null, server error
        if (result == null) {
            throw new ServerSideProblemException("The result from repository should not be null");
        }

        return result;
    }

    private Account getUpdateAccount(Account authUserAccount) throws Exception {
        Account updateAccount = null;
        if (authUserAccount instanceof UserAccount) {
            updateAccount = new UserAccount();
        } else if (authUserAccount instanceof DoctorAccount) {
            updateAccount = new DoctorAccount();

            Category category = null;
            if (authUserAccount.getUserInfo() instanceof DoctorInfo) {
                category = ((DoctorInfo) authUserAccount.getUserInfo()).getCategory();
            }

            DoctorInfo info = new DoctorInfo();
            info.setCategory(category);
            updateAccount.setUserInfo(info);
        } else if (authUserAccount instanceof AdminAccount) {
            updateAccount = new AdminAccount();
        } else {
            throw new ServerSideProblemException("Authentication object should contain a account object with valid type");
        }
        return updateAccount;
    }
}
