package com.echarm.apigateway.accountsystem.util;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.echarm.apigateway.accountsystem.model.Account;


public class AccountQueryBuilder {
    private Account account;
    private Query query;

    public AccountQueryBuilder(Account account, Query query) {
        this.account = account;
        this.query   = query;
    }

    public AccountQueryBuilder addIdCriteria() {
        if (account != null && account.getAccountId() != null &&
            query != null) {
            query.addCriteria(Criteria.where("_id").is(account.getAccountId()));
        }

        return this;
    }

    public AccountQueryBuilder addUserCriteria() {
        if (account != null && account.getUserName() != null &&
            query != null) {
            query.addCriteria(Criteria.where("userName").is(account.getUserName()));
        }

        return this;
    }

    public AccountQueryBuilder addEmailCriteria() {
        if (account != null && account.getEmail() != null &&
            query != null) {
            query.addCriteria(Criteria.where("email").is(account.getEmail()));
        }

        return this;
    }

    public AccountQueryBuilder addEmailOrUserCriteria() {
        if (account != null && account.getEmail() != null &&
            query != null) {
            query.addCriteria(Criteria.where("email").exists(true).orOperator(Criteria.where("email").is(account.getEmail()), Criteria.where("userName").is(account.getUserName())));
        }
        return this;
    }

    /*
     * TODO Add other criteria
     */

    public Query getQuery() {
        return query;
    }

}
