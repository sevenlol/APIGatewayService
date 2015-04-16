package com.echarm.apigateway.accountsystem.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.DoctorInfo;
import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.accountsystem.util.UserType;

public class DeleteAllAccountSpecification extends AccountSpecification {

	public DeleteAllAccountSpecification(Account account) {
		super(account);
		// TODO Auto-generated constructor stub
	}

	@Override
	List<Account> doActions(MongoTemplate mongoTemplate)
			throws ResourceNotExistException, NoContentException, ResourceExistException {
		Account tempAcc = new Account();
		DoctorInfo docInfo = new DoctorInfo();
		UserType[] userTypeArr = UserType.class.getEnumConstants();
		List<Account> tempList = null;
		List<Account> resultList = new ArrayList<Account>();

		docInfo.setCategory(Category.Arbitrary);
		tempAcc.setUserInfo(docInfo);
		for(int index = 0; index < userTypeArr.length; index++) {
			tempAcc.setUserType(userTypeArr[index]);
			//System.out.println(userTypeArr[index].toString());
			AccountSpecification accSpec = AccountSpecificationFactory.getDeleteTypedAccountSpecification(tempAcc);
			try {
				tempList = accSpec.doActions(mongoTemplate);
			} catch(NoContentException e) {
				tempList = null;
			}
			if(tempList != null)
				resultList.addAll(tempList);
		}
		if(resultList.size() == 0)
			throw new NoContentException();
		else
			return resultList;
	}

}
