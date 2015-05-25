package com.echarm.apigateway.security.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.accountsystem.model.DoctorAccount;

@RestController
public class DoctorController {

	@RequestMapping(value = "/doctors", method = RequestMethod.GET)
    public List<DoctorAccount> getDoctors(@RequestParam(value = "id_list", required = false) String accountIdListStr) {
    	// TODO finish this
    	return null;
    }

}
