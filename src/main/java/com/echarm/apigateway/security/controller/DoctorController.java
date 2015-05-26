package com.echarm.apigateway.security.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.accountsystem.model.DoctorAccount;

@RestController
public class DoctorController {

	@RequestMapping(value = "/members/doctors", method = RequestMethod.GET)
    public List<DoctorAccount> getDoctors(@RequestParam(value = "id_list", required = false) String accountIdListStr) {
    	// TODO finish this
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
