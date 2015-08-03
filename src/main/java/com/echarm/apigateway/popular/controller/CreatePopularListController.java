package com.echarm.apigateway.popular.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.popular.request.PopularArticleRequestWrapper;
import com.echarm.apigateway.popular.request.PopularDoctorRequestWrapper;
import com.echarm.apigateway.popular.request.PopularQARequestWrapper;
import com.echarm.apigateway.popular.response.IdStatusListResponse;

@RestController
public class CreatePopularListController {

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/popular/articles/{category}", method = RequestMethod.POST)
    public List<IdStatusListResponse> createPopularArticleList(
            @RequestBody(required=false) List<PopularArticleRequestWrapper> articleList,
            @PathVariable String category) {
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/popular/doctors/{category}", method = RequestMethod.POST)
    public List<IdStatusListResponse> createPopularDoctorList(
            @RequestBody(required=false) List<PopularDoctorRequestWrapper> doctorList,
            @PathVariable String category) {
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/popular/qas/{category}", method = RequestMethod.POST)
    public List<IdStatusListResponse> createPopularQAList(
            @RequestBody(required=false) List<PopularQARequestWrapper> qaList,
            @PathVariable String category) {
        return null;
    }
}
