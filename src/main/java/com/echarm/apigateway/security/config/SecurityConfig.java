package com.echarm.apigateway.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.echarm.apigateway.security.service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/resources/**");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {

	    http.csrf().disable()
	        .httpBasic()
	    .and()
	        .authorizeRequests().antMatchers("/index.html", "/home.html", "/login.html", "/").permitAll()
	    .and().authorizeRequests().antMatchers(HttpMethod.POST, "/accounts").permitAll()
        .and().authorizeRequests().antMatchers(HttpMethod.GET, "/articles", "/articles/*", "/articles/*/*").permitAll()
        .and().authorizeRequests().antMatchers(HttpMethod.POST, "/articles", "/articles/*", "/articles/*/*").hasRole("ADMIN")
        .and().authorizeRequests().antMatchers(HttpMethod.PUT, "/articles", "/articles/*", "/articles/*/*").hasRole("ADMIN")
        .and().authorizeRequests().antMatchers(HttpMethod.DELETE, "/articles", "/articles/*", "/articles/*/*").hasRole("ADMIN")
        .and().authorizeRequests().antMatchers(HttpMethod.GET, "/articles/*/*/comments/**").permitAll()
        .and().authorizeRequests().antMatchers(HttpMethod.POST, "/articles/*/*/comments").hasAnyRole("USER","ADMIN")
        .and().authorizeRequests().antMatchers(HttpMethod.PUT, "/articles/*/*/comments/*").hasRole("ADMIN")
        .and().authorizeRequests().antMatchers(HttpMethod.DELETE, "/articles/*/*/comments/**").hasRole("ADMIN")
	    .and()
	        .authorizeRequests().antMatchers(HttpMethod.DELETE, "/accounts/**").permitAll()
	    .and()
            .authorizeRequests().antMatchers(HttpMethod.POST, "/accounts/**").permitAll()
        .and()
            .authorizeRequests().antMatchers(HttpMethod.GET, "/accounts/**").permitAll()
        .and()
            .authorizeRequests().antMatchers(HttpMethod.PUT, "/accounts/**").permitAll()
        .and()
            .authorizeRequests().antMatchers(HttpMethod.PATCH, "/accounts/**").permitAll()
	    .and()
	        .authorizeRequests().antMatchers("/user/**").authenticated();
	}

	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(new UserDetailServiceImpl());
	    /*auth.inMemoryAuthentication()
	            .withUser("user").password("1234").roles("USER")
	        .and()
	            .withUser("admin").password("12345678").roles("ADMIN");*/
	}
}
