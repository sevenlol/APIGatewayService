package com.echarm.apigateway.accountsystem.model;

import com.echarm.apigateway.accountsystem.util.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserAccount extends Account{

    public UserAccount() {
        super();
        super.setUserType(UserType.USER);
    }

    @JsonIgnore
    @Override
    public void setUserType(UserType userType) {
        // do nothing
    }
}
