package com.echarm.apigateway.accountsystem.repository;

import java.util.ArrayList;

import com.echarm.apigateway.accountsystem.model.Account;

public abstract class AccountMultiInputSpecification extends AccountSpecification {
	protected ArrayList<Account> accountArr;

	public AccountMultiInputSpecification(ArrayList<Account> accountArr) {
	    super(null);
	    this.accountArr = accountArr;
	}
}
