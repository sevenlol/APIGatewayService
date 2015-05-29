package com.echarm.apigateway.security.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.accountsystem.error.InvalidParameterException;
import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.accountsystem.model.DoctorAccount;
import com.echarm.apigateway.accountsystem.model.DoctorInfo;
import com.echarm.apigateway.accountsystem.util.AccountFieldChecker;
import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.accountsystem.util.DoctorInfoFieldChecker;
import com.echarm.apigateway.security.service.UserDetailsImpl;
import com.echarm.apigateway.security.util.CommaDelimitedStringParser;

@RestController
public class DoctorController {

	@RequestMapping(value = "/members/doctors", method = RequestMethod.GET)
    public List<DoctorAccount> getAllDoctors(@RequestParam(value = "id_list", required = false) String accountIdListStr) throws InvalidParameterException {
		if (accountIdListStr != null) {
			// not null => parse str
			String[] accountIdList = CommaDelimitedStringParser.parse(accountIdListStr);
			// TODO get user list here
		}
    	return null;
    }

	@RequestMapping(value = "/members/doctors/{categoryStr}", method = RequestMethod.GET)
    public List<DoctorAccount> getDoctorsInCategory(@RequestParam(value = "id_list", required = false) String accountIdListStr,
    												@PathVariable String categoryStr) throws InvalidParameterException {
		Category category = null;

		if (categoryStr != null) {
			category = Category.isCategoryExist(categoryStr);
		}

		if (accountIdListStr != null) {
			// not null => parse str
			String[] accountIdList = CommaDelimitedStringParser.parse(accountIdListStr);
			// TODO get user list here
		}
    	return null;
    }

	@RequestMapping(value = "/members/doctors", method = RequestMethod.POST)
	public DoctorAccount registerDoctor(@RequestBody(required=false) DoctorAccount account) {

		// Check Input Account
		AccountFieldChecker accountChecker = new AccountFieldChecker(AccountFieldChecker.ConnectType.ALL_PASS);
		accountChecker
			.setChecker(AccountFieldChecker.CheckField.email, AccountFieldChecker.CheckType.BOTH)
			.setChecker(AccountFieldChecker.CheckField.userInfo, AccountFieldChecker.CheckType.NON_NULL);
		if (!accountChecker.check(account)) {
			// TODO check type fail, throw error
		}

		// if userInfo is attached, check fields
		DoctorInfo info = account.getUserInfo();
		if (info != null) {
			// Check DoctorInfo
			DoctorInfoFieldChecker infoChecker = new DoctorInfoFieldChecker(DoctorInfoFieldChecker.ConnectType.ALL_PASS);
			infoChecker
				.setChecker(DoctorInfoFieldChecker.CheckField.name, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.gender, DoctorInfoFieldChecker.CheckType.NON_NULL)
				.setChecker(DoctorInfoFieldChecker.CheckField.phoneNumber, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.address, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.category, DoctorInfoFieldChecker.CheckType.NON_NULL)
				.setChecker(DoctorInfoFieldChecker.CheckField.currentHospital, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.college, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.title, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.specialty, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.availableTime, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.facebookAccount, DoctorInfoFieldChecker.CheckType.NON_EMPTY)
				.setChecker(DoctorInfoFieldChecker.CheckField.blogUrl, DoctorInfoFieldChecker.CheckType.NON_EMPTY);

			if(!infoChecker.check(info)) {
				// doctor info check failed, throw error
			}
		}

		// TODO send a mail to E-Charm's email account

		return null;
	}

	@RequestMapping(value = "/members/doctors", method = RequestMethod.PUT)
	public DoctorAccount updateDoctor(@RequestBody(required=false) DoctorAccount account, Authentication auth) {

		if (auth == null) {
			// TODO throw error
		}

		Object user = auth.getPrincipal();
		if (!(user instanceof UserDetailsImpl)) {
			// TODO throw error
		}

		Account authAccount = ((UserDetailsImpl) user).getAccount();
		if (authAccount == null) {
			// TODO throw error
		}

		// TODO check userType

		// Check Input Account
		AccountFieldChecker accountChecker = new AccountFieldChecker(AccountFieldChecker.ConnectType.NOT_ALL_FAIL);
		accountChecker
			.setChecker(AccountFieldChecker.CheckField.userInfo, AccountFieldChecker.CheckType.NON_NULL);
		if (!accountChecker.check(account)) {
			// TODO check type fail, throw error
		}

		// if userInfo is attached, check fields
		DoctorInfo info = account.getUserInfo();
		if (info != null) {
			// Check DoctorInfo
			DoctorInfoFieldChecker infoChecker = new DoctorInfoFieldChecker(DoctorInfoFieldChecker.ConnectType.NOT_ALL_FAIL);
			infoChecker
				.setChecker(DoctorInfoFieldChecker.CheckField.name, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.gender, DoctorInfoFieldChecker.CheckType.NON_NULL)
				.setChecker(DoctorInfoFieldChecker.CheckField.phoneNumber, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.address, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.category, DoctorInfoFieldChecker.CheckType.NON_NULL)
				.setChecker(DoctorInfoFieldChecker.CheckField.currentHospital, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.college, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.title, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.specialty, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.availableTime, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.facebookAccount, DoctorInfoFieldChecker.CheckType.BOTH)
				.setChecker(DoctorInfoFieldChecker.CheckField.blogUrl, DoctorInfoFieldChecker.CheckType.BOTH);

			if(!infoChecker.check(info)) {
				// doctor info check failed, throw error
			}
		}

		// TODO set un-updateable fields to null

		// TODO update my account

		return null;
	}
}
