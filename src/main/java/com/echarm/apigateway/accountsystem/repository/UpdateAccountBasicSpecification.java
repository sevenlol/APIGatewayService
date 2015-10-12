package com.echarm.apigateway.accountsystem.repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.echarm.apigateway.accountsystem.error.AccountExistErrorBody;
import com.echarm.apigateway.accountsystem.error.AccountNotExistErrorBody;
import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.UserInfo;
import com.echarm.apigateway.accountsystem.util.UserType;

public class UpdateAccountBasicSpecification extends AccountBasicSpecification {

	public UpdateAccountBasicSpecification(Account account) {
		super(account);
		// TODO Auto-generated constructor stub
	}

	@Override
	Account doActions(MongoTemplate mongoTemplate)
			throws ResourceNotExistException, NoContentException, ResourceExistException {

		String typeName = account.getUserTypeString();
		String accountId = account.getAccountId();

		if(accountId == null || typeName == null ) {
			throw new ServerSideProblemException("Input article model is not complete!!! Missing field: `article_id` or `category`");
		}
		else if(accountId.equals("") || typeName.equals("")) {
			throw new ServerSideProblemException("Input article model is not complete!!! Missing field: `article_id` or `category`");
		}

		// attempt to update username or email
		// check if the to be updated username or email already exists
		// TODO consider allowing to update to the same email/username (?)
		if (account.getUserName() != null || account.getEmail() != null) {

		    Query searchQuery = new Query();
		    searchQuery.addCriteria(Criteria.where("isDeleted").is(false));
		    if (account.getUserName() != null && account.getEmail() != null) {
		        searchQuery.addCriteria(Criteria.where("email").exists(true).orOperator(
		                Criteria.where("userName").is(account.getUserName()),
		                Criteria.where("email").is(account.getEmail())));
		    } else {
		        if (account.getUserName() != null) {
	                searchQuery.addCriteria(Criteria.where("userName").is(account.getUserName()));
	            }
	            if (account.getEmail() != null) {
	                searchQuery.addCriteria(Criteria.where("email").is(account.getEmail()));
	            }
		    }

		    boolean isExist = false;
		    UserType[] typeArr = UserType.values();
		    for (UserType type : typeArr) {
		        if (mongoTemplate.exists(searchQuery, Account.class, type.name())) {
		            isExist = true;
		            break;
		        }
		    }
		    if (isExist) {
		        AccountExistErrorBody eb = new AccountExistErrorBody(account.getUserName(), account.getEmail());
	            ResourceExistException e = new ResourceExistException("Username or email already exists!");
	            e.setErrorBody(eb);
	            throw e;
		    }
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
