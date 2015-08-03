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
public class UpdatePopularListController {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/articles/{category}", method = RequestMethod.PUT)
    public List<IdStatusListResponse> updatePopularArticleList(
            @RequestBody(required=false) List<PopularArticleRequestWrapper> articleList,
            @PathVariable String category) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/doctors/{category}", method = RequestMethod.PUT)
    public List<IdStatusListResponse> updatePopularDoctorList(
            @RequestBody(required=false) List<PopularDoctorRequestWrapper> doctorList,
            @PathVariable String category) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/qas/{category}", method = RequestMethod.PUT)
    public List<IdStatusListResponse> updatePopularQAList(
            @RequestBody(required=false) List<PopularQARequestWrapper> qaList,
            @PathVariable String category) {
        return null;
    }
}
