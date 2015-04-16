package com.echarm.apigateway.accountsystem.model;

import com.echarm.apigateway.accountsystem.util.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class ArbitraryAccount extends Account {

    @JsonIgnore
    public ArbitraryAccount() {
        super();
        super.setUserType(UserType.ARBITRARY);
    }

    @JsonIgnore
    @Override
    public void setUserType(UserType userType) {
        // do nothing
    }

    @JsonIgnore
    @Override
    public void setUserInfo(UserInfo userInfo) {
        //do nothing
    }

    @JsonProperty("user_info")
    public void setUserInfo(DoctorInfo doctorInfo) {
        super.setUserInfo(doctorInfo);
    }

    @Override
    @JsonProperty("user_info")
    public DoctorInfo getUserInfo() {
        if (super.getUserInfo() instanceof DoctorInfo)
            return (DoctorInfo) super.getUserInfo();
        else
            return null;
    }
}
