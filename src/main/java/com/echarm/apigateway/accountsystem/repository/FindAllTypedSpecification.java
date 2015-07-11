package com.echarm.apigateway.accountsystem.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.DoctorInfo;
import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.accountsystem.util.QueryFactory;
import com.echarm.apigateway.accountsystem.util.UserType;


public class FindAllTypedSpecification extends AccountSpecification {

	public FindAllTypedSpecification(Account account) {
		super(account);
		// TODO Auto-generated constructor stub
	}

	@Override
	List<Account> doActions(MongoTemplate mongoTemplate)
			throws ResourceNotExistException, NoContentException {

		List<Account> list = null;
		String colName = account.getUserTypeString();
		//System.out.println("In find type " + colName);
		if(colName == null) {
			throw new ServerSideProblemException("Need to specify the user type!!!");
		}
		/*
		list = mongoTemplate.findAll(Account.class, colName);
		*/
		Query query = new Query();
		list = mongoTemplate.find(QueryFactory.getUserNameFilterQuery(query, account), Account.class, colName);

		if(list.size() == 0) {
			throw new NoContentException();
		}
		//
		List<Account> endList = new ArrayList<Account>();
		DoctorInfo docInfo = null;
		DoctorInfo tempInfo = null;

		if(account.getUserType() != UserType.DOCTOR) {
			for(Account acc : list){
				if(!acc.getIsDeleted()){
					endList.add(acc);
				}
			}
		}
		else {
			docInfo = (DoctorInfo) account.getUserInfo();
			if(docInfo.getCategory() == Category.Arbitrary) {
				for(Account acc : list){
					if(!acc.getIsDeleted()){
						endList.add(acc);
					}
				}
			}
			else {
				for(Account acc : list){
				    if (!(acc.getUserInfo() instanceof DoctorInfo)) {
				        continue;
				    }

					tempInfo = (DoctorInfo) acc.getUserInfo();
					if(tempInfo.getCategory() == docInfo.getCategory()) {
						if(!acc.getIsDeleted()){
							endList.add(acc);
						}
					}
				}
			}
		}
		if(endList.size() == 0)
			throw new NoContentException();
		else
			return endList;
	}

}
