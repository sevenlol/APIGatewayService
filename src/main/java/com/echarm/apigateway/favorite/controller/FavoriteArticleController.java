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

import com.echarm.apigateway.favorite.model.FavoriteArticle;
import com.echarm.apigateway.favorite.model.FavoriteList;
import com.echarm.apigateway.favorite.repository.FavoriteArticleRepository;
import com.echarm.apigateway.favorite.request.FavoriteArticleRequestWrapper;
import com.echarm.apigateway.favorite.response.FavoriteArticleListResponseFactory;
import com.echarm.apigateway.favorite.response.FavoriteArticleResponseWrapper;
import com.echarm.apigateway.favorite.response.IdStatusListResponse;
import com.echarm.apigateway.favorite.util.FavoriteArticleValidator;
import com.echarm.apigateway.favorite.util.FavoriteArticleValidatorFactory;
import com.echarm.apigateway.favorite.util.FavoriteListDbUtilities;
import com.echarm.apigateway.security.error.CustomExceptionFactory;
import com.echarm.apigateway.security.util.CommaDelimitedStringParser;

@RestController
public class FavoriteArticleController {

    @Autowired
    private FavoriteArticleRepository repository;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/favorite/articles/{accountId}", method = RequestMethod.POST)
    public IdStatusListResponse createFavoriteArticleList(
            @RequestBody(required=false) List<FavoriteArticleRequestWrapper> articleList,
            @PathVariable String accountId) throws Exception {

        if (repository == null) {
            throw CustomExceptionFactory.getServerProblemException("Favorite list repository should not be null!");
        }

        // validate JSON body
        if (accountId == null || accountId.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: accountId", "Path", "Query parameter accountId should not be null or empty");
        }

        if (articleList == null) {
            throw CustomExceptionFactory.getMissingParamException(
                    "JSON: favorite list", "Body", "JSON body should not be null");
        }
        if (articleList.size() == 0) {
            throw CustomExceptionFactory.getEmptyParamException(
                    "JSON: favorite list", "Body", "JSON body should not be empty");
        }

        // validate favorite article fields
        FavoriteArticleValidator nonNullValidator = FavoriteArticleValidatorFactory.getAllFieldNotNullValidator();
        FavoriteArticleValidator nonEmptyValidator = FavoriteArticleValidatorFactory.getAllFieldNonEmptyValidator();
        FavoriteList createList = new FavoriteList()
                                            .setListId(FavoriteListDbUtilities.getFavoriteListId(accountId));
        Map<String, FavoriteArticle> createMap = new HashMap<String, FavoriteArticle>();
        for (FavoriteArticleRequestWrapper wrapper : articleList) {
            if (wrapper == null || wrapper.getFavoriteArticle() == null) {
                throw CustomExceptionFactory.getMissingParamException(
                        "JSON Object: favorite article",
                        "JSON Array in Body",
                        "Favorite article object in body should not be null!");
            }

            FavoriteArticle article = wrapper.getFavoriteArticle();
            if (!nonNullValidator.validate(article)) {
                throw CustomExceptionFactory.getMissingParamException(
                        "JSON Object: favorite article",
                        "JSON Array in Body",
                        "Favorite article object in body contains null fields!");
            }

            if (!nonEmptyValidator.validate(article)) {
                throw CustomExceptionFactory.getEmptyParamException(
                        "JSON Object: favorite article",
                        "JSON Array in Body",
                        "Favorite article object in body contains empty fields!");
            }
            createMap.put(article.getArticleId(), article);
        }
        createList.setArticleMap(createMap);

        // validation done, create
        FavoriteList result = repository.createFavoriteArticle(createList);

        if (result == null || result.getArticleMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result favorite list should not be null or have null map!");
        }

        // create response
        return FavoriteArticleListResponseFactory.getIdStatusListResponse(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/favorite/articles/{accountId}", method = RequestMethod.GET)
    public List<FavoriteArticleResponseWrapper> readFavoriteArticleList(@PathVariable String accountId) throws Exception {

        if (repository == null) {
            throw CustomExceptionFactory.getServerProblemException("Favorite article list repository should not be null!");
        }

        if (accountId == null || accountId.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: accountId", "Path", "Query parameter accountId should not be null or empty");
        }

        FavoriteList queryList = new FavoriteList()
                                                .setListId(FavoriteListDbUtilities.getFavoriteListId(accountId));

        FavoriteList result = repository.readFavoriteArticle(queryList);

        if (result == null || result.getArticleMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result favorite article list should not be null or have null map!");
        }

        return FavoriteArticleListResponseFactory.getFavoriteArticleListResponse(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/favorite/articles/{accountId}", method = RequestMethod.PUT)
    public IdStatusListResponse updateFavoriteArticleList(
            @RequestBody(required=false) List<FavoriteArticleRequestWrapper> articleList,
            @PathVariable String accountId) throws Exception {

        if (repository == null) {
            throw CustomExceptionFactory.getServerProblemException("Favorite article list repository should not be null!");
        }

        // validate JSON body
        if (accountId == null || accountId.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: accountId", "Path", "Query parameter accountId should not be null or empty");
        }

        if (articleList == null) {
            throw CustomExceptionFactory.getMissingParamException(
                    "JSON: favorite article list", "Body", "JSON body should not be null");
        }
        if (articleList.size() == 0) {
            throw CustomExceptionFactory.getEmptyParamException(
                    "JSON: favorite article list", "Body", "JSON body should not be empty");
        }

        // validate favorite article fields
        FavoriteArticleValidator nonNullValidator = FavoriteArticleValidatorFactory.getAllFieldNotNullValidator();
        FavoriteArticleValidator nonEmptyValidator = FavoriteArticleValidatorFactory.getAllFieldNonEmptyValidator();
        FavoriteList updateList = new FavoriteList()
                                                .setListId(FavoriteListDbUtilities.getFavoriteListId(accountId));
        Map<String, FavoriteArticle> updateMap = new HashMap<String, FavoriteArticle>();
        for (FavoriteArticleRequestWrapper wrapper : articleList) {
            if (wrapper == null || wrapper.getFavoriteArticle() == null) {
                throw CustomExceptionFactory.getMissingParamException(
                        "JSON Object: favorite article",
                        "JSON Array in Body",
                        "Favorite article object in body should not be null!");
            }

            FavoriteArticle article = wrapper.getFavoriteArticle();
            if (!nonNullValidator.validate(article)) {
                throw CustomExceptionFactory.getMissingParamException(
                        "JSON Object: favorite article",
                        "JSON Array in Body",
                        "Favorite article object in body contains null fields!");
            }

            if (!nonEmptyValidator.validate(article)) {
                throw CustomExceptionFactory.getEmptyParamException(
                        "JSON Object: favorite article",
                        "JSON Array in Body",
                        "Favorite article object in body contains empty fields!");
            }
            updateMap.put(article.getArticleId(), article);
        }
        updateList.setArticleMap(updateMap);

        // validation done, create
        FavoriteList result = repository.updateFavoriteArticle(updateList);

        if (result == null || result.getArticleMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result favorite list should not be null or have null map!");
        }

        // create response
        return FavoriteArticleListResponseFactory.getIdStatusListResponse(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/favorite/articles/{accountId}", method = RequestMethod.DELETE)
    public IdStatusListResponse deleteFavoriteArticleList(
            @PathVariable String accountId,
            @RequestParam(value = "id_list", required = false) String idList) throws Exception {

        if (repository == null) {
            throw CustomExceptionFactory.getServerProblemException("Favorite article list repository should not be null!");
        }

        // validate JSON body
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
        Map<String, FavoriteArticle> map = new HashMap<String, FavoriteArticle>();
        for (String id : idArr) {
            if (id == null || id.equals("")) {
                continue;
            }

            FavoriteArticle article = new FavoriteArticle().setArticleId(id).setArticleTitle("FOR EXAMINATION");
            map.put(id, article);
        }

        FavoriteList deleteList = new FavoriteList()
                                                .setListId(FavoriteListDbUtilities.getFavoriteListId(accountId))
                                                .setArticleMap(map);

        FavoriteList result = repository.deleteFavoriteArticle(deleteList);

        if (result == null || result.getArticleMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result Favorite list should not be null or have null map!");
        }

        return FavoriteArticleListResponseFactory.getIdStatusListResponse(result);
    }
}
