package com.echarm.apigateway.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.favorite.repository.FavoriteArticleRepository;
import com.echarm.apigateway.favorite.repository.FavoriteQARepository;
import com.echarm.apigateway.favorite.response.FavoriteListResponseWrapper;

@RestController
public class MyFavoriteListController {

    @Autowired
    private FavoriteArticleRepository articleRepository;

    @Autowired
    private FavoriteQARepository qaRepository;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/favorite/me", method = RequestMethod.GET)
    public FavoriteListResponseWrapper readMyFavoriteArticleList() throws Exception {
        return null;
    }
}
