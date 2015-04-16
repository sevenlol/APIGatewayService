package com.echarm.apigateway.security.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.util.UserType;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Account account;

    private List<SimpleGrantedAuthority> authorities = new LinkedList<>();

    public UserDetailsImpl(Account account) {
        super();
        this.account = account;

        if (account.getUserType() == UserType.USER)
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        else if (account.getUserType() == UserType.DOCTOR)
            authorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
        else if (account.getUserType() == UserType.ADMIN)
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return getAccount().getPassword();
    }

    @Override
    public String getUsername() {
        return getAccount().getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Account getAccount() {
        return account;
    }

}
