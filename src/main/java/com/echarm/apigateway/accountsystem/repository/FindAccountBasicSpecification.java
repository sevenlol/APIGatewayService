package com.echarm.apigateway.accountsystem.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.echarm.apigateway.accountsystem.error.AccountNotExistErrorBody;
import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.DoctorInfo;
import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.accountsystem.util.UserType;


/*
 *  This specification implements both finding the accounts by its id or its user name.
 */

public class FindAccountBasicSpecification extends AccountBasicSpecification {

	public FindAccountBasicSpecification(Account account) {
		super(account);
		// TODO Auto-generated constructor stub
	}

	@Override
	Account doActions(MongoTemplate mongoTemplate)
			throws ResourceNotExistException, NoContentException {
		Account resultAcc = null;

		String accId = account.getAccountId();
		String userName = account.getUserName();
		String userTypeStr = account.getUserTypeString();

		if((userName == null && accId == null) || userTypeStr == null) {
			throw new ServerSideProblemException("Input article model is not complete!!! Missing field: `account_id`, `username`, or `user_type`");
		}
		Query findQuery = new Query();
		if(accId != null)
			findQuery.addCriteria(Criteria.where("_id").is(accId));
		else if(userName != null)
			findQuery.addCriteria(Criteria.where("userName").is(userName));

		findQuery.addCriteria(Criteria.where("isDeleted").is(false));

		if(!userTypeStr.equals(UserType.ARBITRARY.toString())) {
			if(userTypeStr.equals(UserType.DOCTOR.toString())) {
				DoctorInfo info = (DoctorInfo) account.getUserInfo();
				if(info.getCategory() != Category.Arbitrary) {
					findQuery.addCriteria(Criteria.where("userInfo.category").is(info.getCategoryString()));
					//System.out.println("*1");
				}
			}

			resultAcc = mongoTemplate.findOne(findQuery, Account.class, userTypeStr);
		}
		else {
			UserType[] userTypeArr = UserType.class.getEnumConstants();
			for(int index = 0; index < userTypeArr.length; index++) {
				resultAcc = mongoTemplate.findOne(findQuery, Account.class, userTypeArr[index].toString());
				if(resultAcc != null)
					break;
			}
		}

		if(resultAcc == null) {
			ResourceNotExistException e = new ResourceNotExistException("Requested account with id = \"" + accId + "\" or username = \"" + userName + "\" does not exists");
			e.setErrorBody(new AccountNotExistErrorBody(accId, account.getUserType()));
			throw e;
		}
		else {
			//System.out.println("*2 " + resultAcc.getUserName());
			return resultAcc;
		}

		/*
		Article ar = mongoTemplate.findOne(findQuery, Article.class, category);
		if(ar == null) {
			ResourceNotExistException e = new ResourceNotExistException("Requested article with id = \"" + articleId + "\" doesnot exists");
			e.setErrorBody(new ArticleNotExistErrorBody(articleId));
			throw e;
		}
		else
			resultArr.add(ar);
		*/
	}

}
