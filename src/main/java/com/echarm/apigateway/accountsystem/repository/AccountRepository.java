package com.echarm.apigateway.accountsystem.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Repository;

import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.util.DatabaseParameters;
import com.mongodb.MongoClient;

@Repository
public class AccountRepository implements AccountRepositoryService{

	private MongoTemplate mongoTemplate = null;

	public AccountRepository() {
		try {
			connectToDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new ServerSideProblemException("Cannot connect to the article database!!!");
		}
	}

    @Override
    public Account createAccount(Account account) throws ResourceNotExistException, NoContentException, ResourceExistException {
        // TODO Auto-generated method stub
    	Account acc = null;
    	AccountBasicSpecification crtAccSpc = AccountBasicSpecificationFactory.getCreateAccountBasicSpecification(account);
    	acc = crtAccSpc.doActions(mongoTemplate);
        return acc;
    }

    @Override
    public Account updateAccount(Account account) throws ResourceNotExistException, NoContentException, ResourceExistException {
    	Account acc = null;
    	AccountBasicSpecification upAccSpc = AccountBasicSpecificationFactory.getUpdateAccountBasicSpecification(account);
    	acc = upAccSpc.doActions(mongoTemplate);
        return acc;
    }

    @Override
    public Account deleteAccount(Account account) throws ResourceNotExistException, NoContentException, ResourceExistException {
    	Account acc = null;
    	account.setIsDeleted(true);
    	AccountBasicSpecification upAccSpc = AccountBasicSpecificationFactory.getUpdateAccountBasicSpecification(account);
    	acc = upAccSpc.doActions(mongoTemplate);
        return acc;
    }

    @Override
    public List<Account> query(AccountSpecification specification) throws ResourceNotExistException, NoContentException, ResourceExistException {
    	return specification.doActions(mongoTemplate);
    }

    private void connectToDB() throws Exception {
		MongoClient mongoClient = new MongoClient();
		mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(mongoClient, DatabaseParameters.DB_NAME_DEFAULT));
	}

}
