package com.echarm.apigateway.accountsystem.repository;

import com.echarm.apigateway.accountsystem.model.Account;


public class AccountSpecificationFactory {
    public static AccountSpecification getDeleteAllAccountSpecification(Account account) {
        return new DeleteAllAccountSpecification(account) ;
    }

    public static AccountSpecification getDeleteTypedAccountSpecification(Account account) {
        return new DeleteTypedAccountSpecification(account);
    }

    public static AccountSpecification getFindAccountByIdSpecification(Account account) {
        return new FindAccountByIdSpecification(account);
    }

    public static AccountSpecification getFindAllAccountSpecification(Account account) {
        return new FindAllAccountSpecification(account);
    }

    public static AccountSpecification getFindAllTypedSpecification(Account account) {
        return new FindAllTypedSpecification(account);
    }
}
