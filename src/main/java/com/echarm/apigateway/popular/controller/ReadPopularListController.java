package com.echarm.apigateway.popular.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.popular.model.PopularArticleList;
import com.echarm.apigateway.popular.model.PopularDoctorList;
import com.echarm.apigateway.popular.model.PopularQAList;
import com.echarm.apigateway.popular.repository.PopularArticleListRepository;
import com.echarm.apigateway.popular.repository.PopularDoctorListRepository;
import com.echarm.apigateway.popular.repository.PopularQAListRepository;
import com.echarm.apigateway.popular.response.PopularArticleListResponseFactory;
import com.echarm.apigateway.popular.response.PopularArticleResponseWrapper;
import com.echarm.apigateway.popular.response.PopularDoctorListResponseFactory;
import com.echarm.apigateway.popular.response.PopularDoctorResponseWrapper;
import com.echarm.apigateway.popular.response.PopularQAListResponseFactory;
import com.echarm.apigateway.popular.response.PopularQAResponseWrapper;
import com.echarm.apigateway.popular.util.PopularListDocumentId;
import com.echarm.apigateway.security.error.CustomExceptionFactory;

@RestController
public class ReadPopularListController {

    @Autowired
    private PopularArticleListRepository articleListRepository;

    @Autowired
    private PopularDoctorListRepository doctorListRepository;

    @Autowired
    private PopularQAListRepository qaListRepository;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/articles/{category}", method = RequestMethod.GET)
    public List<PopularArticleResponseWrapper> readPopularArticleList(@PathVariable String category) throws Exception {

        if (articleListRepository == null) {
            throw CustomExceptionFactory.getServerProblemException("Popular article list repository should not be null!");
        }

        if (category == null || category.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: category", "Path", "Query parameter category should not be null or empty");
        }

        PopularArticleList queryList = new PopularArticleList()
                                                .setListCategory(category)
                                                .setListId(PopularListDocumentId.getPopularArticleListId(category));

        PopularArticleList result = articleListRepository.readPopularArticleList(queryList);

        if (result == null || result.getArticleMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result popular article list should not be null or have null map!");
        }

        return PopularArticleListResponseFactory.getPopularArticleListResponse(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/doctors/{category}", method = RequestMethod.GET)
    public List<PopularDoctorResponseWrapper> readPopularDoctorList(@PathVariable String category) throws Exception {

        if (doctorListRepository == null) {
            throw CustomExceptionFactory.getServerProblemException("Popular doctor list repository should not be null!");
        }

        if (category == null || category.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: category", "Path", "Query parameter category should not be null or empty");
        }

        PopularDoctorList queryList = new PopularDoctorList()
                                                .setListCategory(category)
                                                .setListId(PopularListDocumentId.getPopularDoctorListId(category));

        PopularDoctorList result = doctorListRepository.readDoctorList(queryList);

        if (result == null || result.getDoctorMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result popular doctor list should not be null or have null map!");
        }

        return PopularDoctorListResponseFactory.getPopularDoctorListResponse(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/qas/{category}", method = RequestMethod.GET)
    public List<PopularQAResponseWrapper> readPopularQAList(@PathVariable String category) throws Exception {

        if (qaListRepository == null) {
            throw CustomExceptionFactory.getServerProblemException("Popular qa list repository should not be null!");
        }

        if (category == null || category.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: category", "Path", "Query parameter category should not be null or empty");
        }

        PopularQAList queryList = new PopularQAList()
                                                .setListCategory(category)
                                                .setListId(PopularListDocumentId.getPopularQAListId(category));

        PopularQAList result = qaListRepository.readQAList(queryList);

        if (result == null || result.getQaMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result popular qa list should not be null or have null map!");
        }

        return PopularQAListResponseFactory.getPopularQAListResponse(result);
    }
}
