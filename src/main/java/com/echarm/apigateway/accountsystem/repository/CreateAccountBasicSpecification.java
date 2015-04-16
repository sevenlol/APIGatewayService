package com.echarm.apigateway.accountsystem.repository;

import java.util.Arrays;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.echarm.apigateway.accountsystem.error.AccountExistErrorBody;
import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.util.QueryFactory;
import com.echarm.apigateway.accountsystem.util.Time;
import com.echarm.apigateway.accountsystem.util.UserType;

public class CreateAccountBasicSpecification extends AccountBasicSpecification {

	public CreateAccountBasicSpecification(Account account) {
		super(account);
		// TODO Auto-generated constructor stub
	}

	@Override
	Account doActions(MongoTemplate mongoTemplate)
			throws ResourceNotExistException, NoContentException, ServerSideProblemException, ResourceExistException {
		/**
		 * 1. Check if all the required fields are filled
		 * 2. Insert the account to the db
		 */

		String typeName = account.getUserTypeString();
		String[] missingArr = account.getNullRequiredInputFieldName();
		if(missingArr != null) {
			throw new ServerSideProblemException("Input article model is not complete!!! Missing field: " + Arrays.toString(missingArr));
		}
		//String colName = article.getCategory().toString();
		if(!mongoTemplate.collectionExists(typeName))
			mongoTemplate.createCollection(typeName);

		Query query = new Query();
		query = QueryFactory.getEmailOrUserNameFilterQuery(query, account);
		query.addCriteria(Criteria.where("isDeleted").is(false));

		//System.out.println(query.toString());
		UserType[] typeArr = UserType.values();
		boolean isUsernameOrEmailConflict = false;
		if (typeArr != null && typeArr.length > 0) {
		    for (int i = 0; i < typeArr.length; i++)
		        isUsernameOrEmailConflict = isUsernameOrEmailConflict || mongoTemplate.exists(query, Account.class, typeArr[i].name());
		}

		if(isUsernameOrEmailConflict) {
			AccountExistErrorBody eb = new AccountExistErrorBody(account.getUserName(), account.getEmail());
			ResourceExistException e = new ResourceExistException("Username or email already exists!");
			e.setErrorBody(eb);
			throw e;
		}

		account.setAccountId(Time.getCurrentTimeMillisStr());
		account.setIsDeleted(false);;
		mongoTemplate.save(account, typeName);
		/*
		Account acc = null;
		Query findQuery = new Query();
		findQuery.addCriteria(Criteria.where("_id").is(account.getAccountId()));
		if(typeName.equals(UserType.ADMIN.toString()))
			acc = mongoTemplate.findOne(findQuery, AdminAccount.class, typeName);
		else if(typeName.equals(UserType.DOCTOR.toString()))
			acc = mongoTemplate.findOne(findQuery, DoctorAccount.class, typeName);
		else if(typeName.equals(UserType.USER.toString()))
			acc = mongoTemplate.findOne(findQuery, UserAccount.class, typeName);
		else
			return null;
		*/
		return account;
	}

}
