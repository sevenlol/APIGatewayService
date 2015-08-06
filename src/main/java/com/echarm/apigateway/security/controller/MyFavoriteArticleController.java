package com.echarm.apigateway.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.echarm.apigateway.favorite.repository.FavoriteArticleRepository;
import com.echarm.apigateway.favorite.request.FavoriteArticleRequestWrapper;
import com.echarm.apigateway.favorite.response.FavoriteArticleResponseWrapper;
import com.echarm.apigateway.favorite.response.IdStatusListResponse;

@RestController
public class MyFavoriteArticleController {

    @Autowired
    private FavoriteArticleRepository repository;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/favorite/me/articles", method = RequestMethod.POST)
    public IdStatusListResponse createMyFavoriteArticleList(
            @RequestBody(required=false) List<FavoriteArticleRequestWrapper> articleList) throws Exception {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/favorite/me/articles", method = RequestMethod.GET)
    public List<FavoriteArticleResponseWrapper> readMyFavoriteArticleList() throws Exception {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/favorite/me/articles", method = RequestMethod.PUT)
    public IdStatusListResponse updateMyFavoriteArticleList(
            @RequestBody(required=false) List<FavoriteArticleRequestWrapper> articleList) throws Exception {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/favorite/me/articles", method = RequestMethod.DELETE)
    public IdStatusListResponse deleteMyFavoriteArticleList(
            @RequestParam(value = "id_list", required = false) String idList) throws Exception {
        return null;
    }
}
