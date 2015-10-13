package com.echarm.apigateway.security.controller;

import java.util.List;

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
import com.echarm.apigateway.accountsystem.repository.AccountSpecification;
import com.echarm.apigateway.accountsystem.repository.FindAccountByUserNameSpecification;
import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.accountsystem.util.UserType;
import com.echarm.apigateway.security.service.UserDetailsImpl;
import com.echarm.apigateway.security.util.RandomStringGenerator;

@RestController
public class AccountCredentialController {

    @Autowired
    private AccountRepositoryService repository;

    @RequestMapping(value = "/me/reset_password", method = RequestMethod.POST)
    public Account resetMyPassword(
            @RequestBody(required = false) Account account) throws Exception {

        // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

        if (account == null) {
            MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("Json Object: Account", "body"));
            InvalidParameterException exception = new InvalidParameterException("No Json Body in the Request!");
            exception.setErrorBody(body);
            throw exception;
        }

        if (account.getUserName() == null ||
            account.getEmail() == null) {
            MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("Username or Email", "Json Object: Account"));
            InvalidParameterException exception = new InvalidParameterException("No Username or Email in Json Body!");
            exception.setErrorBody(body);
            throw exception;
        }

        Account searchAccount = new Account();
        searchAccount.setUserName(account.getUserName());
        searchAccount.setUserType(UserType.ARBITRARY);

        AccountSpecification spec = new FindAccountByUserNameSpecification(searchAccount);

        List<Account> results = repository.query(spec);

        if (results == null) {
            throw new ServerSideProblemException("results should not be null");
        }
        if (results.size() != 1) {
            throw new ServerSideProblemException("results should have size 1");
        }
        if (results.get(0) == null || results.get(0).getAccountId() == null) {
            throw new ServerSideProblemException("result account should not be null or have null id");
        }

        String newPassword = RandomStringGenerator.getString(12);

        // TODO send a email with the new password

        Account updateAccount = getUpdateAccount(results.get(0));

        updateAccount.setAccountId(results.get(0).getAccountId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        updateAccount.setPassword(encoder.encode(newPassword));

        Account result = repository.updateAccount(updateAccount);

        // result null, server error
        if (result == null) {
            throw new ServerSideProblemException("The result from repository should not be null");
        }

        System.out.println("New password: " + newPassword);

        // only respond with accountId
        Account resAccount = new Account();
        resAccount.setAccountId(result.getAccountId());
        return resAccount;
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

        Account result = repository.updateAccount(updateAccount);

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

        Account result = repository.updateAccount(updateAccount);

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
