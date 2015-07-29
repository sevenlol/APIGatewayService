package com.echarm.apigateway.security.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.AdminAccount;
import com.echarm.apigateway.accountsystem.model.DoctorAccount;
import com.echarm.apigateway.accountsystem.model.DoctorInfo;
import com.echarm.apigateway.accountsystem.model.UserAccount;
import com.echarm.apigateway.accountsystem.model.UserInfo;
import com.echarm.apigateway.accountsystem.repository.AccountRepositoryService;
import com.echarm.apigateway.accountsystem.repository.AccountSpecification;
import com.echarm.apigateway.accountsystem.repository.AccountSpecificationFactory;
import com.echarm.apigateway.accountsystem.util.AccountType;
import com.echarm.apigateway.accountsystem.util.Time;
import com.echarm.apigateway.accountsystem.util.UserType;
import com.echarm.apigateway.security.service.UserDetailsImpl;
import com.echarm.apigateway.security.util.RandomStringGenerator;

@RestController
public class AuthController {

    private static final String CONN_TYPE_FACEBOOK = "facebook";

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private AccountRepositoryService repository;

    /*
     * if the /user resource is reachable, it returns the currently authenticated user (an Authentication)
     * else, an 401 response is returned
     */
    @RequestMapping("/user")
    public Account user(Authentication auth) throws ResourceNotExistException, NoContentException, ResourceExistException {
    	if (auth == null)
    		return null;

    	// repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

    	Object user = auth.getPrincipal();
    	if (user instanceof UserDetailsImpl) {
        	Account account = ((UserDetailsImpl) user).getAccount();
        	if (account != null) {
        		Account reqAccount;

        		if (account.getUserType() == UserType.USER) {
        			reqAccount = new UserAccount();
        		} else if (account.getUserType() == UserType.DOCTOR) {
        			reqAccount = new DoctorAccount();
        			// TODO add defensive check here
        			DoctorInfo info = new DoctorInfo();
        			info.setCategory(((DoctorInfo) account.getUserInfo()).getCategory());
        			((DoctorAccount)reqAccount).setUserInfo(info);
        		} else if (account.getUserType() == UserType.ADMIN) {
        			reqAccount = new AdminAccount();
        		} else {
        			reqAccount = new Account();
        		}

        		reqAccount.setAccountId(account.getAccountId());

        		// get account
        		AccountSpecification specification = AccountSpecificationFactory.getFindAccountByIdSpecification(reqAccount);
                List<Account> results = repository.query(specification);

                if (results.size() != 1) {
                    throw new ServerSideProblemException("The result (List<Account>) size should be 1");
                }

                Account resultAccount = results.get(0);

                System.out.println(resultAccount.getUserInfo().getAddress());

                resultAccount.setPassword(null);
                resultAccount.setSalt(null);

                return resultAccount;
        	}
        }

        return null;
    }

    @RequestMapping("/signup")
    public void signup(Principal user, WebRequest request, HttpServletResponse httpServletResponse) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);

        if (connection == null) {
            // TODO redirect
            return;
        }

        // only support facebook atm
        if (connection.getKey() == null || !CONN_TYPE_FACEBOOK.equalsIgnoreCase(connection.getKey().getProviderId()) ||
            connection.getKey().getProviderUserId() == null) {
            // TODO redirect
            return;
        }

        String accountId = String.format("%s:%s", connection.getKey().getProviderId(), connection.getKey().getProviderUserId());
        System.out.println("Account ID: " + accountId);

        Account searchAcc = new Account();
        searchAcc.setAccountId(accountId);
        searchAcc.setUserType(UserType.USER);
        AccountSpecification spec = AccountSpecificationFactory.getFindAccountByIdSpecification(searchAcc);

        List<Account> accList = null;
        try {
            accList = repository.query(spec);
        } catch (ResourceNotExistException e1) {
            // Account not exist, set to null just in case
            accList = null;
        } catch (Exception e2) {
            // Other exception
            // TODO redirect
            System.out.println(e2.getMessage());
            return;
        }

        Account account = null;

        // implicit signup for the user
        if (accList == null) {
            System.out.println("Implicit sign up start!");

            UserAccount implicitSignUpAcc = new UserAccount();
            implicitSignUpAcc.setAccountId(accountId);
            implicitSignUpAcc.setAccountType(AccountType.FACEBOOK);

            UserProfile socialMediaProfile = connection.fetchUserProfile();
            if (socialMediaProfile != null) {
                UserInfo info = new UserInfo();
                if (socialMediaProfile.getEmail() != null) {
                    implicitSignUpAcc.setEmail(socialMediaProfile.getEmail());
                }

                if (socialMediaProfile.getName() != null) {
                    info.setName(socialMediaProfile.getName());
                    implicitSignUpAcc.setUserInfo(info);
                }

                if (socialMediaProfile.getUsername() != null) {
                    implicitSignUpAcc.setUserName(socialMediaProfile.getUsername());
                }
            }

            // set random username, password, email, salt and createdAt
            if (implicitSignUpAcc.getUserName() == null) {
                String randStr = RandomStringGenerator.getString(10);
                String randUsername = String.format("%s:%s", accountId, randStr);
                implicitSignUpAcc.setUserName(randUsername);
            }
            if (implicitSignUpAcc.getEmail() == null) {
                String randStr = RandomStringGenerator.getString(10);
                String randEmail = String.format("%s:%s@echarm.com", accountId, randStr);
                implicitSignUpAcc.setEmail(randEmail);
            }
            String randStr = RandomStringGenerator.getString(20);
            // encode password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            implicitSignUpAcc.setPassword(encoder.encode(randStr));
            implicitSignUpAcc.setSalt(randStr);
            implicitSignUpAcc.setCreatedAt(Time.getCurrentTimeStr());

            // retry if username/email exists
            int retryCount = 3;
            boolean isSuccess = true;
            while(retryCount >= 0) {
                retryCount--;
                try {
                    repository.createAccount(implicitSignUpAcc);
                } catch (Exception e) {
                    isSuccess = false;
                }

                if (isSuccess) {
                    break;
                } else {
                    // re-generate username/email
                    randStr = RandomStringGenerator.getString(10);
                    String randUsername = String.format("%s:%s", accountId, randStr);
                    implicitSignUpAcc.setUserName(randUsername);
                    randStr = RandomStringGenerator.getString(10);
                    String randEmail = String.format("%s:%s@echarm.com", accountId, randStr);
                    implicitSignUpAcc.setEmail(randEmail);
                }
            }

            if (!isSuccess) {
                // create account fail
                // TODO redirect
                return;
            } else {
                // assign the signed up account
                System.out.println("Implicit sign up succeeded! ID: " + implicitSignUpAcc.getAccountId());
                account = implicitSignUpAcc;
            }
        } else {
            if (accList.size() != 1) {
                // some unexpected error occurred
                // TODO redirect
                return;
            }

            System.out.println("Account already registered! No need for implicit sign up! ID: " + accountId);
            account = accList.get(0);
        }

        List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
        gas.add(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl principal = new UserDetailsImpl(account);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, null, gas));

        try {
            // redirect url
            httpServletResponse.sendRedirect("/");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
