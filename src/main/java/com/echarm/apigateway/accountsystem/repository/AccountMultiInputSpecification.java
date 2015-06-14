package com.echarm.apigateway.accountsystem.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.model.Account;

public abstract class AccountMultiInputSpecification {
	protected ArrayList<Account> accountArr;
	
	public AccountMultiInputSpecification(ArrayList<Account> accountArr) {
		this.accountArr = accountArr;
	}
	
	abstract List<Account> doActions(MongoTemplate mongoTemplate) throws ResourceNotExistException, NoContentException, ResourceExistException;
}
