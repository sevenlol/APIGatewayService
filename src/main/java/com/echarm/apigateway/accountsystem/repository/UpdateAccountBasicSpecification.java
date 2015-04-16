package com.echarm.apigateway.accountsystem.repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.echarm.apigateway.accountsystem.error.AccountNotExistErrorBody;
import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.UserInfo;

public class UpdateAccountBasicSpecification extends AccountBasicSpecification {

	public UpdateAccountBasicSpecification(Account account) {
		super(account);
		// TODO Auto-generated constructor stub
	}

	@Override
	Account doActions(MongoTemplate mongoTemplate)
			throws ResourceNotExistException, NoContentException {

		String typeName = account.getUserTypeString();
		String accountId = account.getAccountId();

		if(accountId == null || typeName == null ) {
			throw new ServerSideProblemException("Input article model is not complete!!! Missing field: `article_id` or `category`");
		}
		else if(accountId.equals("") || typeName.equals("")) {
			throw new ServerSideProblemException("Input article model is not complete!!! Missing field: `article_id` or `category`");
		}

		String[] fieldNameArr = account.getNonNullCapitalCamelName();
		Update updateParam = new Update();
		Method method;
		String fieldName;
		String innerFieldName;
		UserInfo tempInfo = null;
		if(fieldNameArr == null) {
			throw new NoContentException();
		}
		for(int index = 0; index < fieldNameArr.length; index++) {
			fieldName = fieldNameArr[index];
			try {
				method = account.getClass().getMethod("get"+ fieldName);
				if(!fieldName.equals("UserInfo")) {
				    updateParam.set(fieldName.substring(0,1).toLowerCase() + fieldName.substring(1), method.invoke(account));
				}
				else {
					String[] infoFieldNameArr = account.getUserInfo().getNonNullCapitalCamelName();
					for(int innerIndex = 0; innerIndex < infoFieldNameArr.length; innerIndex++) {
						innerFieldName = infoFieldNameArr[innerIndex];
						tempInfo = (UserInfo) method.invoke(account);
						updateParam.set("userInfo." + innerFieldName.substring(0,1).toLowerCase() + innerFieldName.substring(1), tempInfo.getClass().getMethod("get" + innerFieldName).invoke(tempInfo));
					}
				}
			} catch (NoSuchMethodException e) {
				throw new ServerSideProblemException("No such attribute to update!!!");
				//e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Query findQuery = new Query();
		findQuery.addCriteria(Criteria.where("_id").is(accountId));
		Account tempAccount = mongoTemplate.findAndModify(findQuery, updateParam, new FindAndModifyOptions().returnNew(true), Account.class, typeName);
		if(tempAccount == null) {
			ResourceNotExistException e = new ResourceNotExistException("Requested article with id = \"" + accountId + "\" doesnot exists");
			e.setErrorBody(new AccountNotExistErrorBody(accountId, account.getUserType()));
			throw e;
		}

		return tempAccount;
	}

}
