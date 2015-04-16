package com.echarm.apigateway.accountsystem.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.DoctorInfo;
import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.accountsystem.util.UserType;


public class DeleteTypedAccountSpecification extends AccountSpecification {

	public DeleteTypedAccountSpecification(Account account) {
		super(account);
		// TODO Auto-generated constructor stub
	}

	@Override
	List<Account> doActions(MongoTemplate mongoTemplate)
			throws ResourceNotExistException, NoContentException, ResourceExistException {
		/*
		 * 1. Find all accounts that need to be deleted
		 * 2. Delete those accounts
		 */

		AccountSpecification accSpec = AccountSpecificationFactory.getFindAllTypedSpecification(account);
		List<Account> resultList = accSpec.doActions(mongoTemplate);

		String colName = account.getUserTypeString();
		Query findQuery = new Query();
        findQuery.addCriteria(Criteria.where("isDeleted").is(false));
        if(account.getUserType() == UserType.DOCTOR) {
        	DoctorInfo info = (DoctorInfo) account.getUserInfo();
        	if(info.getCategory() != Category.Arbitrary) {
        		findQuery.addCriteria(Criteria.where("userInfo.category").is(info.getCategoryString()));
        	}
        }

        Update update = new Update();
        update.set("isDeleted", true);
        mongoTemplate.updateMulti(findQuery, update, Account.class, colName);

		return resultList;
	}

}
