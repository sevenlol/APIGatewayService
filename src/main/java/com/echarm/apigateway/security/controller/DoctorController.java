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
import com.echarm.apigateway.accountsystem.model.DoctorAccount;
import com.echarm.apigateway.accountsystem.util.Category;
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
		return null;
	}

	@RequestMapping(value = "/members/doctors", method = RequestMethod.PUT)
	public DoctorAccount updateDoctor(@RequestBody(required=false) DoctorAccount account, Authentication auth) {
		return null;
	}
}
