package com.echarm.apigateway.security.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.favorite.model.FavoriteArticle;
import com.echarm.apigateway.favorite.model.FavoriteList;
import com.echarm.apigateway.favorite.model.FavoriteQA;
import com.echarm.apigateway.favorite.repository.FavoriteArticleRepository;
import com.echarm.apigateway.favorite.repository.FavoriteQARepository;
import com.echarm.apigateway.favorite.response.FavoriteListResponseFactory;
import com.echarm.apigateway.favorite.response.FavoriteListResponseWrapper;
import com.echarm.apigateway.favorite.util.FavoriteListDbUtilities;
import com.echarm.apigateway.security.error.CustomExceptionFactory;
import com.echarm.apigateway.security.service.UserDetailsImpl;
import com.echarm.apigateway.security.util.SecurityUtilities;

@RestController
public class MyFavoriteListController {

    @Autowired
    private FavoriteArticleRepository articleRepository;

    @Autowired
    private FavoriteQARepository qaRepository;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/favorite/me", method = RequestMethod.GET)
    public FavoriteListResponseWrapper readMyFavoriteArticleList(Authentication auth) throws Exception {

        // check authentication
        SecurityUtilities.checkAuthenticationObject(auth);

        String accountId = ((UserDetailsImpl) auth.getPrincipal()).getAccount().getAccountId();

        /*
         * get favorite article list
         */
        // FIXME move the following code (duplicated in favorite article controller) to a shraed function
        if (articleRepository == null) {
            throw CustomExceptionFactory.getServerProblemException("Favorite article list articleRepository should not be null!");
        }

        if (accountId == null || accountId.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: accountId", "Path", "Query parameter accountId should not be null or empty");
        }

        FavoriteList articleQueryList = new FavoriteList()
                                                .setListId(FavoriteListDbUtilities.getFavoriteListId(accountId))
                                                .setArticleMap(new HashMap<String, FavoriteArticle>());

        FavoriteList articleResult = articleRepository.readFavoriteArticle(articleQueryList);

        if (articleResult == null || articleResult.getArticleMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result favorite article list should not be null or have null map!");
        }

        /*
         * get favorite qa list
         */

        // FIXME move the following code (duplicated with code in favorite qa controller) to a shared function
        if (qaRepository == null) {
            throw CustomExceptionFactory.getServerProblemException("Favorite qa list repository should not be null!");
        }

        if (accountId == null || accountId.equals("")) {
            throw CustomExceptionFactory.getMissingParamException(
                    "String: accountId", "Path", "Query parameter accountId should not be null or empty");
        }

        FavoriteList qaQueryList = new FavoriteList()
                                                .setListId(FavoriteListDbUtilities.getFavoriteListId(accountId))
                                                .setQAMap(new HashMap<String, FavoriteQA>());

        FavoriteList qaResult = qaRepository.readFavoriteQA(qaQueryList);

        if (qaResult == null || qaResult.getQAMap() == null) {
            throw CustomExceptionFactory.getServerProblemException("Result favorite qa list should not be null or have null map!");
        }

        return FavoriteListResponseFactory.getFavoriteListRespones(articleResult, qaResult);
    }
}
