package com.echarm.apigateway.accountsystem.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.util.UserType;

public class FindAccountByAccountIdMultiInputSpecification extends
		AccountMultiInputSpecification {

	public FindAccountByAccountIdMultiInputSpecification(
			ArrayList<Account> accountArr) {
		super(accountArr);
	}

	@Override
	List<Account> doActions(MongoTemplate mongoTemplate)
			throws ResourceNotExistException, NoContentException,
			ResourceExistException {
		
		ArrayList<String> accountIdArr = new ArrayList<String>();
		ArrayList<Account> resultArr = new ArrayList<Account>();
		ArrayList<Account> tempArr = null;
		
		for(int index = 0; index < accountArr.size(); index++) {
			if(accountArr.get(index).getAccountId() != null)
				accountIdArr.add(accountArr.get(index).getAccountId());
		}
		
		Criteria criteria = Criteria.where("accountId").in(accountIdArr);
		Query query = new Query();
		query.addCriteria(criteria);
		query.addCriteria(Criteria.where("isDeleted").is(false));
		
		UserType[] userTypeArr = UserType.class.getEnumConstants();
		for(int index = 0; index < userTypeArr.length; index++) {
			tempArr = (ArrayList<Account>) mongoTemplate.find(query, Account.class, userTypeArr[index].toString());
			if(tempArr != null) {
				if(tempArr.size() != 0) {
					resultArr.addAll(tempArr);
				}
			}
		}
		if(resultArr.size() == 0) {
			throw new NoContentException();
		}
		else
			return resultArr;
	}

}
