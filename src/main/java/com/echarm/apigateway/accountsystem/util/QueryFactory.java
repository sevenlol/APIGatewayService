package com.echarm.apigateway.accountsystem.util;

import org.springframework.data.mongodb.core.query.Query;

import com.echarm.apigateway.accountsystem.model.Account;


public class QueryFactory {
    public static Query getBasicFilterQuery(Query query, Account account) {
        if (query == null || account == null)
            return null;

        AccountQueryBuilder builder = new AccountQueryBuilder(account, query);
        /*
         * chain all needed criteria here, if the field is non-null, the field will be used as a filter criterion
         */
        return builder.addUserCriteria().addEmailCriteria().getQuery();
    }
    public static Query getUserNameFilterQuery(Query query, Account account) {
        if (query == null || account == null)
            return null;

        AccountQueryBuilder builder = new AccountQueryBuilder(account, query);
        /*
         * chain all needed criteria here, if the field is non-null, the field will be used as a filter criterion
         */
        return builder.addUserCriteria().getQuery();
    }

    public static Query getEmailOrUserNameFilterQuery(Query query, Account account) {
        if (query == null || account == null)
            return null;

        AccountQueryBuilder builder = new AccountQueryBuilder(account, query);
        /*
         * chain all needed criteria here, if the field is non-null, the field will be used as a filter criterion
         */
        return builder.addEmailOrUserCriteria().getQuery();
    }
}
