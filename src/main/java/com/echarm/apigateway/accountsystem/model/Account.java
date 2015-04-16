package com.echarm.apigateway.accountsystem.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.echarm.apigateway.accountsystem.util.AccountType;
import com.echarm.apigateway.accountsystem.util.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
@Document
@JsonInclude(Include.NON_NULL)
public class Account {
	@Id
    private String accountId;
    private UserType userType;
    private AccountType accountType;
    private String email;
    private String userName;
    private String salt;
    private String password;
    private String createdAt;
    private UserInfo userInfo;
    private Boolean isDeleted;

    public Account() {

    }

    /*
     * setter
     */

    @JsonIgnore
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @JsonIgnore
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @JsonIgnore
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @JsonProperty("account_type")
    public void setAccountTypeWithString(String accountType) {
        if (accountType == null || AccountType.isAccountTypeExist(accountType) == null)
            this.accountType = null;
        else
            this.accountType = AccountType.valueOf(accountType);
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("salt")
    public void setSalt(String salt) {
        this.salt = salt;
    }

    @JsonProperty("username")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("user_info")
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
    	this.createdAt = createdAt;
    }

    @JsonIgnore
    public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}


    /*
     * getter
     */

    @JsonProperty("account_id")
    public String getAccountId() {
        return accountId;
    }

    @JsonIgnore
    public UserType getUserType() {
        return userType;
    }

    @JsonIgnore
    public AccountType getAccountType() {
        return accountType;
    }

    @JsonProperty("user_type")
    public String getUserTypeString() {
        if (userType == null)
            return null;
        else
            return userType.name();
    }

    @JsonProperty("account_type")
    public String getAccountTypeString() {
        if (accountType == null)
            return null;
        else
            return accountType.name();
    }

    @JsonProperty("username")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("salt")
    public String getSalt() {
        return salt;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("user_info")
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
    	return createdAt;
    }

    @JsonIgnore
    public Boolean getIsDeleted() {
		return isDeleted;
	}

    /*
     * Json Input Validation Helper Functions
     */

    @JsonIgnore
    public boolean isAllJsonInputFieldNonNull() {
    	return this.accountType != null && this.email != null && this.password != null &&
    		   this.userInfo != null && this.userName != null && this.createdAt != null &&
    		   this.salt != null;
    }

    @JsonIgnore
    public boolean isAllJsonInputFieldNonEmpty() {
    	return (this.email == null || !this.email.equals("")) &&
    	       (this.password == null || !this.password.equals("")) &&
    		   (this.userName == null || !this.userName.equals("")) &&
    		   (this.createdAt == null || !this.createdAt.equals("")) &&
    		   (this.salt == null || !this.salt.equals(""));
    }

    @JsonIgnore
    public boolean isAllJsonInputFieldNull() {
    	return this.accountType == null && this.email == null && this.password == null &&
    		   this.userInfo == null && this.userName == null && this.createdAt == null &&
    		   this.salt == null;
    }

    @JsonIgnore
    public String[] getNullJsonInputFieldName() {
    	List<String> nullFieldNameList = new ArrayList<String>();

    	if (accountType == null)
    		nullFieldNameList.add("account_type");

    	if (email == null)
    		nullFieldNameList.add("email");

    	if (salt == null)
            nullFieldNameList.add("salt");

    	if (password == null)
    		nullFieldNameList.add("password");

    	if (userName == null)
    		nullFieldNameList.add("username");

    	if (userInfo == null)
    		nullFieldNameList.add("user_info");

    	if (createdAt == null)
    		nullFieldNameList.add("created_at");

    	if (nullFieldNameList.size() > 0)
            return nullFieldNameList.toArray(new String[nullFieldNameList.size()]);

    	return null;
    }

    @JsonIgnore
    public String[] getEmptyJsonInputFieldName() {
    	List<String> emptyFieldNameList = new ArrayList<String>();

    	if (email != null && email.equals(""))
    		emptyFieldNameList.add("email");

    	if (password != null && password.equals(""))
    		emptyFieldNameList.add("password");

    	if (userName != null && userName.equals(""))
    		emptyFieldNameList.add("username");

    	if (salt != null && salt.equals(""))
            emptyFieldNameList.add("salt");

    	if (createdAt != null && createdAt.equals(""))
    		emptyFieldNameList.add("created_at");

    	if (emptyFieldNameList.size() > 0)
            return emptyFieldNameList.toArray(new String[emptyFieldNameList.size()]);

    	return null;
    }

    @JsonIgnore
    public String[] getNullRequiredInputFieldName() {
    	String[] nullInputFieldArr = this.getEmptyJsonInputFieldName();
    	List<String> nullList = null;
    	if(nullInputFieldArr != null)
    		nullList = new ArrayList<String>(Arrays.asList(nullInputFieldArr));
    	else
    		nullList = new ArrayList<String>();
    	nullInputFieldArr = userInfo.getNullRequiredInputFieldName();
    	if(nullInputFieldArr != null)
    		nullList.addAll(Arrays.asList(nullInputFieldArr));
    	if(!nullList.isEmpty())
    		return nullList.toArray(new String[nullList.size()]);
    	else
    		return null;
    }

    @JsonIgnore
    public String[] getNonNullCapitalCamelName() {
    	List<String> fieldNameList = new ArrayList<String>();

    	if (accountType != null)
    		fieldNameList.add("AccountType");

    	if (email != null)
    		fieldNameList.add("Email");

    	if (password != null)
    		fieldNameList.add("Password");

    	if (userName != null)
    		fieldNameList.add("UserName");

    	if (salt != null)
            fieldNameList.add("Salt");

    	if (userInfo != null)
    		fieldNameList.add("UserInfo");

    	if (createdAt != null)
    		fieldNameList.add("CreatedAt");

    	if(isDeleted != null)
    		fieldNameList.add("IsDeleted");

    	if (fieldNameList.size() > 0)
            return fieldNameList.toArray(new String[fieldNameList.size()]);

    	return null;
    }

}
