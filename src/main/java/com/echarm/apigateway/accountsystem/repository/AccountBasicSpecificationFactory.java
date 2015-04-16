package com.echarm.apigateway.accountsystem.repository;

import com.echarm.apigateway.accountsystem.model.Account;


public class AccountBasicSpecificationFactory {
    public static AccountBasicSpecification getCreateAccountBasicSpecification(Account account) {
        return new CreateAccountBasicSpecification(account) ;
    }
    public static AccountBasicSpecification getUpdateAccountBasicSpecification(Account account) {
        return new UpdateAccountBasicSpecification(account) ;
    }
    public static AccountBasicSpecification getFindAccountBasicSpecification(Account account) {
        return new FindAccountBasicSpecification(account) ;
    }
}
