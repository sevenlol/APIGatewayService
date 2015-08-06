package com.echarm.apigateway.popular.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.popular.model.PopularArticle;
import com.echarm.apigateway.popular.model.PopularArticleList;
import com.echarm.apigateway.popular.model.PopularDoctor;
import com.echarm.apigateway.popular.model.PopularDoctorList;
import com.echarm.apigateway.popular.model.PopularQA;
import com.echarm.apigateway.popular.model.PopularQAList;
import com.echarm.apigateway.popular.repository.PopularArticleListRepository;
import com.echarm.apigateway.popular.repository.PopularDoctorListRepository;
import com.echarm.apigateway.popular.repository.PopularQAListRepository;
import com.echarm.apigateway.popular.request.PopularArticleRequestWrapper;
import com.echarm.apigateway.popular.request.PopularDoctorRequestWrapper;
import com.echarm.apigateway.popular.request.PopularQARequestWrapper;
import com.echarm.apigateway.popular.response.IdStatusListResponse;
import com.echarm.apigateway.popular.response.PopularArticleListResponseFactory;
import com.echarm.apigateway.popular.response.PopularDoctorListResponseFactory;
import com.echarm.apigateway.popular.response.PopularQAListResponseFactory;
import com.echarm.apigateway.popular.util.PopularArticleValidator;
import com.echarm.apigateway.popular.util.PopularArticleValidatorFactory;
import com.echarm.apigateway.popular.util.PopularDoctorValidator;
import com.echarm.apigateway.popular.util.PopularDoctorValidatorFactory;
import com.echarm.apigateway.popular.util.PopularListDocumentId;
import com.echarm.apigateway.popular.util.PopularQAValidator;
import com.echarm.apigateway.popular.util.PopularQAValidatorFactory;
import com.echarm.apigateway.security.error.CustomExceptionFactory;

@RestController
public class UpdatePopularListController {

    @Autowired
    private PopularArticleListRepository articleListRepository;

    @Autowired
    private PopularDoctorListRepository doctorListRepository;

    @Autowired
    private PopularQAListRepository qaListRepository;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/articles/{category}", method = RequestMethod.PUT)
    public IdStatusListResponse updatePopularArticleList(
            @RequestBody(required=false) List<PopularArticleRequestWrapper> articleList,
            @PathVariable String category) throws Exception {
        if (articleListRepository == null) {
            throw CustomExceptionFactory.getServerProblemException("Popular article list repository should not be null!");
        }

        // validate JSON body
        if (category == null || category.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: category", "Path", "Query parameter category should not be null or empty");
        }

        if (articleList == null) {
            throw CustomExceptionFactory.getMissingParamException(
                    "JSON: popular article list", "Body", "JSON body should not be null");
        }
        if (articleList.size() == 0) {
            throw CustomExceptionFactory.getEmptyParamException(
                    "JSON: popular article list", "Body", "JSON body should not be empty");
        }

        // validate popular article fields
        PopularArticleValidator nonNullValidator = PopularArticleValidatorFactory.getAllFieldNotNullValidator();
        PopularArticleValidator nonEmptyValidator = PopularArticleValidatorFactory.getAllFieldNonEmptyValidator();
        PopularArticleList updateList = new PopularArticleList()
                                                .setListCategory(category)
                                                .setListId(PopularListDocumentId.getPopularArticleListId(category));
        Map<String, PopularArticle> updateMap = new HashMap<String, PopularArticle>();
        for (PopularArticleRequestWrapper wrapper : articleList) {
            if (wrapper == null || wrapper.getPopularArticle() == null) {
                throw CustomExceptionFactory.getMissingParamException(
                        "JSON Object: popular article",
                        "JSON Array in Body",
                        "Popular article object in body should not be null!");
            }

            PopularArticle article = wrapper.getPopularArticle();
            if (!nonNullValidator.validate(article)) {
                throw CustomExceptionFactory.getMissingParamException(
                        "JSON Object: popular article",
                        "JSON Array in Body",
                        "Popular article object in body contains null fields!");
            }

            if (!nonEmptyValidator.validate(article)) {
                throw CustomExceptionFactory.getEmptyParamException(
                        "JSON Object: popular article",
                        "JSON Array in Body",
                        "Popular article object in body contains empty fields!");
            }
            updateMap.put(article.getArticleId(), article);
        }
        updateList.setArticleMap(updateMap);

        // validation done, create
        PopularArticleList result = articleListRepository.updatePopularArticleList(updateList);

        if (result == null || result.getArticleMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result popular article list repository should not be null or have null map!");
        }

        // create response
        return PopularArticleListResponseFactory.getIdStatusListResponse(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/doctors/{category}", method = RequestMethod.PUT)
    public IdStatusListResponse updatePopularDoctorList(
            @RequestBody(required=false) List<PopularDoctorRequestWrapper> doctorList,
            @PathVariable String category) throws Exception {

        if (doctorListRepository == null) {
            throw CustomExceptionFactory.getServerProblemException("Popular doctor list repository should not be null!");
        }

        // validate JSON body
        if (category == null || category.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: category", "Path", "Query parameter category should not be null or empty");
        }

        if (doctorList == null) {
            throw CustomExceptionFactory.getMissingParamException(
                    "JSON: popular doctor list", "Body", "JSON body should not be null");
        }
        if (doctorList.size() == 0) {
            throw CustomExceptionFactory.getEmptyParamException(
                    "JSON: popular doctor list", "Body", "JSON body should not be empty");
        }

        // validate popular doctor fields
        PopularDoctorValidator nonNullValidator = PopularDoctorValidatorFactory.getAllFieldNotNullValidator();
        PopularDoctorValidator nonEmptyValidator = PopularDoctorValidatorFactory.getAllFieldNonEmptyValidator();
        Map<String, PopularDoctor> updateMap = new HashMap<String, PopularDoctor>();
        for (PopularDoctorRequestWrapper wrapper : doctorList) {
            if (wrapper == null || wrapper.getPopularDoctor() == null) {
                throw CustomExceptionFactory.getMissingParamException(
                        "JSON Object: popular doctor",
                        "JSON Array in Body",
                        "Popular doctor object in body should not be null!");
            }

            PopularDoctor doctor = wrapper.getPopularDoctor();
            if (!nonNullValidator.validate(doctor)) {
                throw CustomExceptionFactory.getMissingParamException(
                        "JSON Object: popular doctor",
                        "JSON Array in Body",
                        "Popular doctor object in body contains null fields!");
            }

            if (!nonEmptyValidator.validate(doctor)) {
                throw CustomExceptionFactory.getEmptyParamException(
                        "JSON Object: popular doctor",
                        "JSON Array in Body",
                        "Popular doctor object in body contains empty fields!");
            }
            updateMap.put(doctor.getDoctorId(), doctor);
        }
        PopularDoctorList updateList = new PopularDoctorList()
                                                .setListCategory(category)
                                                .setListId(PopularListDocumentId.getPopularDoctorListId(category))
                                                .setDoctorMap(updateMap);

        // validation done, update
        PopularDoctorList result = doctorListRepository.updateDoctorList(updateList);

        if (result == null || result.getDoctorMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result popular doctor list should not be null or have null map!");
        }

        return PopularDoctorListResponseFactory.getIdStatusListResponse(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/popular/qas/{category}", method = RequestMethod.PUT)
    public IdStatusListResponse updatePopularQAList(
            @RequestBody(required=false) List<PopularQARequestWrapper> qaList,
            @PathVariable String category) throws Exception {

        if (qaListRepository == null) {
            throw CustomExceptionFactory.getServerProblemException("Popular qa list repository should not be null!");
        }

        // validate JSON body
        if (category == null || category.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: category", "Path", "Query parameter category should not be null or empty");
        }

        if (qaList == null) {
            throw CustomExceptionFactory.getMissingParamException(
                    "JSON: popular qa list", "Body", "JSON body should not be null");
        }
        if (qaList.size() == 0) {
            throw CustomExceptionFactory.getEmptyParamException(
                    "JSON: popular qa list", "Body", "JSON body should not be empty");
        }

        // validate popular qa fields
        PopularQAValidator nonNullValidator = PopularQAValidatorFactory.getAllFieldNotNullValidator();
        PopularQAValidator nonEmptyValidator = PopularQAValidatorFactory.getAllFieldNonEmptyValidator();
        Map<String, PopularQA> updateMap = new HashMap<String, PopularQA>();
        for (PopularQARequestWrapper wrapper : qaList) {
            if (wrapper == null || wrapper.getPopularQA() == null) {
                throw CustomExceptionFactory.getMissingParamException(
                        "JSON Object: popular qa",
                        "JSON Array in Body",
                        "Popular qa object in body should not be null!");
            }

            PopularQA qa = wrapper.getPopularQA();
            if (!nonNullValidator.validate(qa)) {
                throw CustomExceptionFactory.getMissingParamException(
                        "JSON Object: popular qa",
                        "JSON Array in Body",
                        "Popular qa object in body contains null fields!");
            }

            if (!nonEmptyValidator.validate(qa)) {
                throw CustomExceptionFactory.getEmptyParamException(
                        "JSON Object: popular qa",
                        "JSON Array in Body",
                        "Popular qa object in body contains empty fields!");
            }
            updateMap.put(qa.getQuestionId(), qa);
        }
        PopularQAList updateList = new PopularQAList()
                                                .setListCategory(category)
                                                .setListId(PopularListDocumentId.getPopularQAListId(category))
                                                .setQaMap(updateMap);

        // validation done, update
        PopularQAList result = qaListRepository.updateQAList(updateList);

        if (result == null || result.getQaMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result popular qa list should not be null or have null map!");
        }

        return PopularQAListResponseFactory.getIdStatusListResponse(result);
    }
}
