package com.echarm.apigateway.security.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.security.service.UserDetailsImpl;

@RestController
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /*
     * if the /user resource is reachable, it returns the currently authenticated user (an Authentication)
     * else, an 401 response is returned
     */
    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/user/me")
    public Account getUserAccount() {
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        if (user instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) user).getAccount();
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
