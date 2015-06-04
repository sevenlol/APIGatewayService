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
import com.echarm.apigateway.accountsystem.repository.AccountRepositoryService;
import com.echarm.apigateway.accountsystem.repository.AccountSpecification;
import com.echarm.apigateway.accountsystem.repository.AccountSpecificationFactory;
import com.echarm.apigateway.security.service.UserDetailsImpl;

@RestController
public class AuthController {

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
        		Account reqAccount = new Account();
        		reqAccount.setAccountId(account.getAccountId());

        		// get account
        		AccountSpecification specification = AccountSpecificationFactory.getFindAccountByIdSpecification(reqAccount);
                List<Account> results = repository.query(specification);

                if (results.size() != 1) {
                    throw new ServerSideProblemException("The result (List<Account>) size should be 1");
                }

                Account resultAccount = results.get(0);

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

        if (connection != null) {
            UserProfile socialMediaProfile = connection.fetchUserProfile();

            List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
            gas.add(new SimpleGrantedAuthority("ROLE_USER"));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("user", null, gas));

            try {
                // redirect url
                httpServletResponse.sendRedirect("/");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
