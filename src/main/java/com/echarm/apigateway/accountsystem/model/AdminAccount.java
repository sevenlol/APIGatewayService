package com.echarm.apigateway.accountsystem.model;

import com.echarm.apigateway.accountsystem.util.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AdminAccount extends Account{

    public AdminAccount() {
        super();
        super.setUserType(UserType.ADMIN);
    }

    @JsonIgnore
    @Override
    public void setUserType(UserType userType) {
        // do nothing
    }
}
