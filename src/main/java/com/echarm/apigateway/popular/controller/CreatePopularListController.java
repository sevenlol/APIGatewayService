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

import com.echarm.apigateway.popular.error.PopularListExceptionFactory;
import com.echarm.apigateway.popular.model.PopularArticle;
import com.echarm.apigateway.popular.model.PopularArticleList;
import com.echarm.apigateway.popular.repository.PopularArticleListRepository;
import com.echarm.apigateway.popular.request.PopularArticleRequestWrapper;
import com.echarm.apigateway.popular.request.PopularDoctorRequestWrapper;
import com.echarm.apigateway.popular.request.PopularQARequestWrapper;
import com.echarm.apigateway.popular.response.IdStatusListResponse;
import com.echarm.apigateway.popular.response.PopularArticleListResponseFactory;
import com.echarm.apigateway.popular.util.PopularArticleValidator;
import com.echarm.apigateway.popular.util.PopularArticleValidatorFactory;
import com.echarm.apigateway.popular.util.PopularListDocumentId;

@RestController
public class CreatePopularListController {

    @Autowired
    private PopularArticleListRepository articleListRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/popular/articles/{category}", method = RequestMethod.POST)
    public IdStatusListResponse createPopularArticleList(
            @RequestBody(required=false) List<PopularArticleRequestWrapper> articleList,
            @PathVariable String category) throws Exception {

        if (articleListRepository == null) {
            throw PopularListExceptionFactory.getServerProblemException("Popular article list repository should not be null!");
        }

        // validate JSON body
        if (category == null || category.equals("")) {
            throw PopularListExceptionFactory.getMissingParamException(
                    "String: category", "Path", "Query parameter category should not be null or empty");
        }

        if (articleList == null) {
            throw PopularListExceptionFactory.getMissingParamException(
                    "JSON: popular article list", "Body", "JSON body should not be null");
        }
        if (articleList.size() == 0) {
            throw PopularListExceptionFactory.getEmptyParamException(
                    "JSON: popular article list", "Body", "JSON body should not be empty");
        }

        // validate popular article fields
        PopularArticleValidator nonNullValidator = PopularArticleValidatorFactory.getAllFieldNotNullValidator();
        PopularArticleValidator nonEmptyValidator = PopularArticleValidatorFactory.getAllFieldNonEmptyValidator();
        PopularArticleList createList = new PopularArticleList()
                                                .setListCategory(category)
                                                .setListId(PopularListDocumentId.getPopularArticleListId(category));
        Map<String, PopularArticle> createMap = new HashMap<String, PopularArticle>();
        for (PopularArticleRequestWrapper wrapper : articleList) {
            if (wrapper == null || wrapper.getPopularArticle() == null) {
                throw PopularListExceptionFactory.getMissingParamException(
                        "JSON Object: popular article",
                        "JSON Array in Body",
                        "Popular article object in body should not be null!");
            }

            PopularArticle article = wrapper.getPopularArticle();
            if (!nonNullValidator.validate(article)) {
                throw PopularListExceptionFactory.getMissingParamException(
                        "JSON Object: popular article",
                        "JSON Array in Body",
                        "Popular article object in body contains null fields!");
            }

            if (!nonEmptyValidator.validate(article)) {
                throw PopularListExceptionFactory.getEmptyParamException(
                        "JSON Object: popular article",
                        "JSON Array in Body",
                        "Popular article object in body contains empty fields!");
            }
            createMap.put(article.getArticleId(), article);
        }
        createList.setArticleMap(createMap);

        // validation done, create
        PopularArticleList result = articleListRepository.createPopularArticleList(createList);

        if (result == null || result.getArticleMap() == null) {
            throw PopularListExceptionFactory.getServerProblemException("Result popular article list repository should not be null or have null map!");
        }

        // create response
        return PopularArticleListResponseFactory.getIdStatusListResponse(result);
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
