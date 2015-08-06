package com.echarm.apigateway.favorite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.favorite.model.FavoriteList;
import com.echarm.apigateway.favorite.model.FavoriteQA;
import com.echarm.apigateway.favorite.repository.FavoriteQARepository;
import com.echarm.apigateway.favorite.request.FavoriteQARequestWrapper;
import com.echarm.apigateway.favorite.response.FavoriteQAListResponseFactory;
import com.echarm.apigateway.favorite.response.FavoriteQAResponseWrapper;
import com.echarm.apigateway.favorite.response.IdStatusListResponse;
import com.echarm.apigateway.favorite.util.FavoriteListDbUtilities;
import com.echarm.apigateway.favorite.util.FavoriteQAValidator;
import com.echarm.apigateway.favorite.util.FavoriteQAValidatorFactory;
import com.echarm.apigateway.security.error.CustomExceptionFactory;
import com.echarm.apigateway.security.util.CommaDelimitedStringParser;

@RestController
public class FavoriteQAController {

    @Autowired
    private FavoriteQARepository repository;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/favorite/qas/{accountId}", method = RequestMethod.POST)
    public IdStatusListResponse createFavoriteQAList(
            @RequestBody(required=false) List<FavoriteQARequestWrapper> qaList,
            @PathVariable String accountId) throws Exception {

        if (repository == null) {
            throw CustomExceptionFactory.getServerProblemException("Favorite qa list repository should not be null!");
        }

        if (accountId == null || accountId.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: accountId", "Path", "Query parameter accountId should not be null or empty");
        }

        if (qaList == null) {
            throw CustomExceptionFactory.getMissingParamException(
                    "JSON: favorite qa list", "Body", "JSON body should not be null");
        }
        if (qaList.size() == 0) {
            throw CustomExceptionFactory.getEmptyParamException(
                    "JSON: favorite qa list", "Body", "JSON body should not be empty");
        }

        // validate favorite qa fields
        FavoriteQAValidator nonNullValidator = FavoriteQAValidatorFactory.getAllFieldNotNullValidator();
        FavoriteQAValidator nonEmptyValidator = FavoriteQAValidatorFactory.getAllFieldNonEmptyValidator();
        Map<String, FavoriteQA> createMap = new HashMap<String, FavoriteQA>();
        for (FavoriteQARequestWrapper wrapper : qaList) {
            if (wrapper == null || wrapper.getFavoriteQA() == null) {
                throw CustomExceptionFactory.getMissingParamException(
                        "JSON Object: favorite qa",
                        "JSON Array in Body",
                        "Favorite qa object in body should not be null!");
            }

            FavoriteQA qa = wrapper.getFavoriteQA();
            if (!nonNullValidator.validate(qa)) {
                throw CustomExceptionFactory.getMissingParamException(
                        "JSON Object: favorite qa",
                        "JSON Array in Body",
                        "Favorite qa object in body contains null fields!");
            }

            if (!nonEmptyValidator.validate(qa)) {
                throw CustomExceptionFactory.getEmptyParamException(
                        "JSON Object: favorite qa",
                        "JSON Array in Body",
                        "Favorite qa object in body contains empty fields!");
            }
            createMap.put(qa.getQuestionId(), qa);
        }
        FavoriteList createList = new FavoriteList()
                                                .setListId(FavoriteListDbUtilities.getFavoriteListId(accountId))
                                                .setQAMap(createMap);

        // validation done, create
        FavoriteList result = repository.createFavoriteQA(createList);

        if (result == null || result.getQAMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result favorite qa list should not be null or have null map!");
        }

        return FavoriteQAListResponseFactory.getIdStatusListResponse(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/favorite/qas/{accountId}", method = RequestMethod.GET)
    public List<FavoriteQAResponseWrapper> readFavoriteQAList(@PathVariable String accountId) throws Exception {

        if (repository == null) {
            throw CustomExceptionFactory.getServerProblemException("Favorite qa list repository should not be null!");
        }

        if (accountId == null || accountId.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: accountId", "Path", "Query parameter accountId should not be null or empty");
        }

        FavoriteList queryList = new FavoriteList()
                                                .setListId(FavoriteListDbUtilities.getFavoriteListId(accountId))
                                                .setQAMap(new HashMap<String, FavoriteQA>());

        FavoriteList result = repository.readFavoriteQA(queryList);

        if (result == null || result.getQAMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result favorite qa list should not be null or have null map!");
        }

        return FavoriteQAListResponseFactory.getFavoriteQAListResponse(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/favorite/qas/{accountId}", method = RequestMethod.PUT)
    public IdStatusListResponse updateFavoriteQAList(
            @RequestBody(required=false) List<FavoriteQARequestWrapper> qaList,
            @PathVariable String accountId) throws Exception {

        if (repository == null) {
            throw CustomExceptionFactory.getServerProblemException("Favorite qa list repository should not be null!");
        }

        // validate JSON body
        if (accountId == null || accountId.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: accountId", "Path", "Query parameter accountId should not be null or empty");
        }

        if (qaList == null) {
            throw CustomExceptionFactory.getMissingParamException(
                    "JSON: favorite qa list", "Body", "JSON body should not be null");
        }
        if (qaList.size() == 0) {
            throw CustomExceptionFactory.getEmptyParamException(
                    "JSON: favorite qa list", "Body", "JSON body should not be empty");
        }

        // validate favorite qa fields
        FavoriteQAValidator nonNullValidator = FavoriteQAValidatorFactory.getAllFieldNotNullValidator();
        FavoriteQAValidator nonEmptyValidator = FavoriteQAValidatorFactory.getAllFieldNonEmptyValidator();
        Map<String, FavoriteQA> updateMap = new HashMap<String, FavoriteQA>();
        for (FavoriteQARequestWrapper wrapper : qaList) {
            if (wrapper == null || wrapper.getFavoriteQA() == null) {
                throw CustomExceptionFactory.getMissingParamException(
                        "JSON Object: favorite qa",
                        "JSON Array in Body",
                        "Favorite qa object in body should not be null!");
            }

            FavoriteQA qa = wrapper.getFavoriteQA();
            if (!nonNullValidator.validate(qa)) {
                throw CustomExceptionFactory.getMissingParamException(
                        "JSON Object: favorite qa",
                        "JSON Array in Body",
                        "Favorite qa object in body contains null fields!");
            }

            if (!nonEmptyValidator.validate(qa)) {
                throw CustomExceptionFactory.getEmptyParamException(
                        "JSON Object: favorite qa",
                        "JSON Array in Body",
                        "Favorite qa object in body contains empty fields!");
            }
            updateMap.put(qa.getQuestionId(), qa);
        }
        FavoriteList updateList = new FavoriteList()
                                                .setListId(FavoriteListDbUtilities.getFavoriteListId(accountId))
                                                .setQAMap(updateMap);

        // validation done, update
        FavoriteList result = repository.updateFavoriteQA(updateList);

        if (result == null || result.getQAMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result favorite qa list should not be null or have null map!");
        }

        return FavoriteQAListResponseFactory.getIdStatusListResponse(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/favorite/qas/{accountId}", method = RequestMethod.DELETE)
    public IdStatusListResponse deleteFavoriteQAList(
            @PathVariable String accountId,
            @RequestParam(value = "id_list", required = false) String idList) throws Exception {

        if (repository == null) {
            throw CustomExceptionFactory.getServerProblemException("Favorite qa list repository should not be null!");
        }

        if (accountId == null || accountId.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: accountId", "Path", "Query parameter accountId should not be null or empty");
        }

        // check id_list
        String[] idArr = CommaDelimitedStringParser.parse(idList);

        if (idArr == null || idArr.length == 0) {
            throw CustomExceptionFactory.getServerProblemException("Id array should not be null or has size 0!");
        }

        // create list for delete
        Map<String, FavoriteQA> map = new HashMap<String, FavoriteQA>();
        for (String id : idArr) {
            if (id == null || id.equals("")) {
                continue;
            }

            FavoriteQA qa = new FavoriteQA().setQuestionId(id).setQuestionerName("FOR EXAMINATION");
            map.put(id, qa);
        }

        FavoriteList deleteList = new FavoriteList()
                                                .setListId(FavoriteListDbUtilities.getFavoriteListId(accountId))
                                                .setQAMap(map);

        FavoriteList result = repository.deleteFavoriteQA(deleteList);

        if (result == null || result.getQAMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result favorite qa list should not be null or have null map!");
        }
        return FavoriteQAListResponseFactory.getIdStatusListResponse(result);
    }
}
