package com.echarm.apigateway.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.accountsystem.error.CategoryNotExistErrorBody;
import com.echarm.apigateway.accountsystem.error.InvalidParameterException;
import com.echarm.apigateway.accountsystem.error.MissingParameterErrorBody;
import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceExistException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.DoctorAccount;
import com.echarm.apigateway.accountsystem.model.DoctorInfo;
import com.echarm.apigateway.accountsystem.model.UserInfo;
import com.echarm.apigateway.accountsystem.repository.AccountMultiInputSpecificationFactory;
import com.echarm.apigateway.accountsystem.repository.AccountRepositoryService;
import com.echarm.apigateway.accountsystem.repository.AccountSpecification;
import com.echarm.apigateway.accountsystem.repository.AccountSpecificationFactory;
import com.echarm.apigateway.accountsystem.util.AccountFieldChecker;
import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.accountsystem.util.DoctorInfoFieldChecker;
import com.echarm.apigateway.accountsystem.util.UserType;
import com.echarm.apigateway.security.service.UserDetailsImpl;
import com.echarm.apigateway.security.util.CommaDelimitedStringParser;

@RestController
public class DoctorController {

	@Autowired
    private AccountRepositoryService repository;

	@RequestMapping(value = "/members/doctors", method = RequestMethod.GET)
    public List<Account> getAllDoctors(@RequestParam(value = "id_list", required = false) String accountIdListStr) throws InvalidParameterException, ResourceNotExistException, NoContentException, ResourceExistException {

	    // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

	    if (accountIdListStr != null) {
			// not null => parse str
			String[] accountIdList = CommaDelimitedStringParser.parse(accountIdListStr);

			if (accountIdList == null) {
                throw new ServerSideProblemException("AccountIdList should not be null");
            }

			ArrayList<Account> accountList = new ArrayList<Account>();
            for (int i = 0; i < accountIdList.length; i++) {
                DoctorAccount tmpAccount = new DoctorAccount();
                tmpAccount.setAccountId(accountIdList[i]);
                DoctorInfo info = new DoctorInfo();
                info.setCategory(Category.Arbitrary);
                tmpAccount.setUserInfo(info);
                accountList.add(tmpAccount);
            }

            AccountSpecification multiIdSpec = AccountMultiInputSpecificationFactory.getFindAccountByAccountIdMultiInputSpecification(accountList);
            List<Account> multiIdResults = repository.query(multiIdSpec);

            // query result should not be null, server error
            if (multiIdResults == null) {
                throw new ServerSideProblemException("The result (List<Account>) from the repository should not be null");
            }

            // query result should have at least one element
            // if not, server error
            if (multiIdResults.size() <= 0) {
                throw new ServerSideProblemException("The result (List<Account>) size should be at least 1");
            }

            for (Account acc : multiIdResults) {
                acc.setPassword(null);
                acc.setSalt(null);
            }

            return multiIdResults;
		}

	    // set UserType
        DoctorInfo info = new DoctorInfo();
        info.setCategory(Category.Arbitrary);
        DoctorAccount account = new DoctorAccount();
        account.setUserInfo(info);

        // get all UserAccounts in the database
        // when no accounts found in the database, NoContentException is thrown
        AccountSpecification specification = AccountSpecificationFactory.getFindAllTypedSpecification(account);
        List<Account> results = repository.query(specification);

        // query result should not be null, server error
        if (results == null) {
            throw new ServerSideProblemException("The result (List<Account>) from the repository should not be null");
        }

        // query result should have at least one element
        // if not, server error
        if (results.size() <= 0) {
            throw new ServerSideProblemException("The result (List<Account>) size should be at least 1");
        }

    	return results;
    }

	@RequestMapping(value = "/members/doctors/{categoryStr}", method = RequestMethod.GET)
    public List<Account> getDoctorsInCategory(@RequestParam(value = "id_list", required = false) String accountIdListStr,
    												@PathVariable String categoryStr) throws InvalidParameterException, ResourceNotExistException, NoContentException, ResourceExistException {

	    // repository null, server error
        if (repository == null) {
            throw new ServerSideProblemException("repository null");
        }

	    Category category = null;

		if (categoryStr != null) {
			category = Category.isCategoryExist(categoryStr);
		}

		if (category == null) {
		    ResourceNotExistException exception = new ResourceNotExistException("Category "+category.toString()+" Not Exist!");
            exception.setErrorBody(new CategoryNotExistErrorBody(category.toString()));
            throw exception;
		}

		if (accountIdListStr != null) {
			// not null => parse str
			String[] accountIdList = CommaDelimitedStringParser.parse(accountIdListStr);

			if (accountIdList == null) {
                throw new ServerSideProblemException("AccountIdList should not be null");
            }

            ArrayList<Account> accountList = new ArrayList<Account>();
            for (int i = 0; i < accountIdList.length; i++) {
                DoctorAccount tmpAccount = new DoctorAccount();
                tmpAccount.setAccountId(accountIdList[i]);
                DoctorInfo info = new DoctorInfo();
                info.setCategory(category);
                tmpAccount.setUserInfo(info);
                accountList.add(tmpAccount);
            }

            AccountSpecification multiIdSpec = AccountMultiInputSpecificationFactory.getFindAccountByAccountIdMultiInputSpecification(accountList);
            List<Account> multiIdResults = repository.query(multiIdSpec);

            // query result should not be null, server error
            if (multiIdResults == null) {
                throw new ServerSideProblemException("The result (List<Account>) from the repository should not be null");
            }

            // query result should have at least one element
            // if not, server error
            if (multiIdResults.size() <= 0) {
                throw new ServerSideProblemException("The result (List<Account>) size should be at least 1");
            }

            for (Account acc : multiIdResults) {
                acc.setPassword(null);
                acc.setSalt(null);
            }

            return multiIdResults;
		}

		// set UserType
        DoctorInfo info = new DoctorInfo();
        info.setCategory(category);
        DoctorAccount account = new DoctorAccount();
        account.setUserInfo(info);

        // get all DoctorAccounts in the database
        // when no accounts found in the database, NoContentException is thrown
        AccountSpecification specification = AccountSpecificationFactory.getFindAllTypedSpecification(account);
        List<Account> results = repository.query(specification);

        // query result should not be null, server error
        if (results == null) {
            throw new ServerSideProblemException("The result (List<Account>) from the repository should not be null");
        }

        // query result should have at least one element
        // if not, server error
        if (results.size() <= 0) {
            throw new ServerSideProblemException("The result (List<Account>) size should be at least 1");
        }

    	return results;
    }

	@RequestMapping(value = "/members/doctors", method = RequestMethod.POST)
	public DoctorAccount registerDoctor(@RequestBody(required=false) DoctorAccount account) throws InvalidParameterException {

		// Check Input Account
		AccountFieldChecker accountChecker = new AccountFieldChecker(AccountFieldChecker.ConnectType.ALL_PASS);
		accountChecker
			.setChecker(AccountFieldChecker.CheckField.username, AccountFieldChecker.CheckType.BOTH)
			.setChecker(AccountFieldChecker.CheckField.password, AccountFieldChecker.CheckType.BOTH)
			.setChecker(AccountFieldChecker.CheckField.email, AccountFieldChecker.CheckType.BOTH)
			.setChecker(AccountFieldChecker.CheckField.userInfo, AccountFieldChecker.CheckType.NON_NULL);
		if (!accountChecker.check(account)) {
			// check type fail, throw error
			MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("String: email or Object user_info", "Body"));
            InvalidParameterException exception = new InvalidParameterException("Invalid JSON Body: email or user_info field missing!");
            exception.setErrorBody(body);
            throw exception;
		}

		// if userInfo is attached, check fields
		DoctorInfo info = account.getUserInfo();
		if (info != null) {
			// Check DoctorInfo
			DoctorInfoFieldChecker infoChecker = new DoctorInfoFieldChecker(DoctorInfoFieldChecker.ConnectType.ALL_PASS);
			infoChecker
				.setChecker(DoctorInfoFieldChecker.CheckField.name, DoctorInfoFieldChecker.CheckType.BOTH)
				//.setChecker(DoctorInfoFieldChecker.CheckField.gender, DoctorInfoFieldChecker.CheckType.NON_NULL)
//				.setChecker(DoctorInfoFieldChecker.CheckField.phoneNumber, DoctorInfoFieldChecker.CheckType.BOTH)
//				.setChecker(DoctorInfoFieldChecker.CheckField.address, DoctorInfoFieldChecker.CheckType.BOTH)
				// NOTE category is combined in title field in the web
//				.setChecker(DoctorInfoFieldChecker.CheckField.category, DoctorInfoFieldChecker.CheckType.NON_NULL)
				.setChecker(DoctorInfoFieldChecker.CheckField.currentHospital, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.college, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.title, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.specialty, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.availableTime, DoctorInfoFieldChecker.CheckType.BOTH)
				/*.setChecker(DoctorInfoFieldChecker.CheckField.facebookAccount, DoctorInfoFieldChecker.CheckType.NON_EMPTY)
				.setChecker(DoctorInfoFieldChecker.CheckField.blogUrl, DoctorInfoFieldChecker.CheckType.NON_EMPTY)*/;

			if(!infoChecker.check(info)) {
				MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("Some Fields missing", "user_info in Body"));
	            InvalidParameterException exception = new InvalidParameterException("Some fields in user_info in JSON body missing!");
	            exception.setErrorBody(body);
	            throw exception;
			}
		}

		// TODO send a mail to E-Charm's email account

		return null;
	}

	@RequestMapping(value = "/members/doctors", method = RequestMethod.PUT)
	public Account updateDoctor(@RequestBody(required=false) DoctorAccount account, Authentication auth) throws Exception {

		if (auth == null) {
			throw new ServerSideProblemException("Authentication Object is NULL!");
		}

		Object user = auth.getPrincipal();
		if (!(user instanceof UserDetailsImpl)) {
			throw new ServerSideProblemException("Principal Object has a wrong type!");
		}

		Account authAccount = ((UserDetailsImpl) user).getAccount();
		if (authAccount == null) {
			throw new ServerSideProblemException("Account Object in Principal is NULL!");
		}

		// check userType
		if (authAccount.getUserType() == null || authAccount.getUserType() != UserType.DOCTOR) {
			throw new ServerSideProblemException("UserType should be DOCTOR!");
		}

		// Check Input Account
		AccountFieldChecker accountChecker = new AccountFieldChecker(AccountFieldChecker.ConnectType.NOT_ALL_FAIL);
		accountChecker
			.setChecker(AccountFieldChecker.CheckField.userInfo, AccountFieldChecker.CheckType.NON_NULL);
		if (!accountChecker.check(account)) {
			// check type fail, throw error
			MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("Object: user_info", "Body"));
            InvalidParameterException exception = new InvalidParameterException("Invalid JSON Body: user_info field missing!");
            exception.setErrorBody(body);
            throw exception;
		}

		// TODO modify gender mapping bug
		// if userInfo is attached, check fields
		DoctorInfo info = account.getUserInfo();
		if (info != null) {
			// Check DoctorInfo
			DoctorInfoFieldChecker infoChecker = new DoctorInfoFieldChecker(DoctorInfoFieldChecker.ConnectType.NOT_ALL_FAIL);
			infoChecker
				.setChecker(DoctorInfoFieldChecker.CheckField.name, DoctorInfoFieldChecker.CheckType.BOTH)
				//.setChecker(DoctorInfoFieldChecker.CheckField.gender, DoctorInfoFieldChecker.CheckType.NON_NULL)
				.setChecker(DoctorInfoFieldChecker.CheckField.phoneNumber, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.address, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.category, DoctorInfoFieldChecker.CheckType.NON_NULL)
				.setChecker(DoctorInfoFieldChecker.CheckField.currentHospital, DoctorInfoFieldChecker.CheckType.BOTH)
				//.setChecker(DoctorInfoFieldChecker.CheckField.college, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.title, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.specialty, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.availableTime, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.facebookAccount, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.blogUrl, DoctorInfoFieldChecker.CheckType.BOTH);

			if(!infoChecker.check(info)) {
				// doctor info check failed, throw error
				MissingParameterErrorBody body = new MissingParameterErrorBody(MissingParameterErrorBody.generateDescription("Some Fields missing", "user_info in Body"));
	            InvalidParameterException exception = new InvalidParameterException("Some fields in user_info in JSON body missing!");
	            exception.setErrorBody(body);
	            throw exception;
			}
		}

		// set un-updateable fields to null
		nullifyStaticAccountField(account);

		// set account id and category
		account.setAccountId(authAccount.getAccountId());
		UserInfo accountInfo = authAccount.getUserInfo();
		if (!(accountInfo instanceof DoctorInfo)) {
			throw new ServerSideProblemException("The userInfo in authAccount should be a DoctorInfo");
		}
		account.getUserInfo().setCategory(((DoctorInfo)accountInfo).getCategory());

		// update my account
		Account result = repository.updateAccount(account);

		// type not correct, server error
		// TODO Change findAllTypedAccountSpec to generate DoctorAccount Object instead of Account
		/*if (!(result instanceof DoctorAccount)) {
			throw new ServerSideProblemException("The result from repository should be a DoctorAccount object");
		}*/
		if (result.getUserType() != UserType.DOCTOR) {
			throw new ServerSideProblemException("The result from repository should have userType DOCTOR");
		}

		// set password/salt to null
		result.setPassword(null);
		result.setSalt(null);

		// TODO account fields check

		return result;
	}

	private void nullifyStaticAccountField(Account account) {
		if (account == null)
			return;

		account.setAccountType(null);
		account.setCreatedAt(null);
		account.setEmail(null);
		account.setPassword(null);
		account.setSalt(null);
		account.setUserType(null);
		account.setUserName(null);
	}
}
