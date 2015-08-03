package com.echarm.apigateway.popular.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.popular.response.PopularArticleResponseWrapper;
import com.echarm.apigateway.popular.response.PopularDoctorResponseWrapper;
import com.echarm.apigateway.popular.response.PopularQAResponseWrapper;

@RestController
public class ReadPopularListController {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/articles/{category}", method = RequestMethod.GET)
    public List<PopularArticleResponseWrapper> updatePopularArticleList(@PathVariable String category) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/doctors/{category}", method = RequestMethod.GET)
    public List<PopularDoctorResponseWrapper> updatePopularDoctorList(@PathVariable String category) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/qas/{category}", method = RequestMethod.GET)
    public List<PopularQAResponseWrapper> updatePopularQAList(@PathVariable String category) {
        return null;
    }
}
