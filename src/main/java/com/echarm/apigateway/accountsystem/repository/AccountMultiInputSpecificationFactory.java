package com.echarm.apigateway.accountsystem.repository;

import java.util.ArrayList;

import com.echarm.apigateway.accountsystem.model.Account;

public class AccountMultiInputSpecificationFactory {

	public AccountMultiInputSpecificationFactory() {
	}
	
	static AccountMultiInputSpecification getFindAccountByAccountIdMultiInputSpecification(ArrayList<Account> arr) {
		return new FindAccountByAccountIdMultiInputSpecification(arr);
	}

}
