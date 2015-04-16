package com.echarm.apigateway.accountsystem.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.model.Account;

public class FindAccountByIdSpecification extends AccountSpecification {

	public FindAccountByIdSpecification(Account account) {
		super(account);
		// TODO Auto-generated constructor stub
	}

	@Override
	List<Account> doActions(MongoTemplate mongoTemplate)
			throws ResourceNotExistException, NoContentException, ResourceExistException {

		List<Account> resultArr = new ArrayList<Account>();

		AccountBasicSpecification accSpec = AccountBasicSpecificationFactory.getFindAccountBasicSpecification(account);
		resultArr.add(accSpec.doActions(mongoTemplate));

		return resultArr;
	}

}
