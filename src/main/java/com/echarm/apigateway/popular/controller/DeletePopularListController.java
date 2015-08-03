package com.echarm.apigateway.popular.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.popular.response.PopularArticleResponseWrapper;
import com.echarm.apigateway.popular.response.PopularDoctorResponseWrapper;
import com.echarm.apigateway.popular.response.PopularQAResponseWrapper;

@RestController
public class DeletePopularListController {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/articles/{category}", method = RequestMethod.DELETE)
    public List<PopularArticleResponseWrapper> updatePopularArticleList(
            @PathVariable String category,
            @RequestParam(value = "id_list", required = false) String idList) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/doctors/{category}", method = RequestMethod.DELETE)
    public List<PopularDoctorResponseWrapper> updatePopularDoctorList(
            @PathVariable String category,
            @RequestParam(value = "id_list", required = false) String idList) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/qas/{category}", method = RequestMethod.DELETE)
    public List<PopularQAResponseWrapper> updatePopularQAList(
            @PathVariable String category,
            @RequestParam(value = "id_list", required = false) String idList) {
        return null;
    }
}
