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

import com.echarm.apigateway.favorite.repository.FavoriteQARepository;
import com.echarm.apigateway.favorite.request.FavoriteQARequestWrapper;
import com.echarm.apigateway.favorite.response.FavoriteQAResponseWrapper;
import com.echarm.apigateway.favorite.response.IdStatusListResponse;

@RestController
public class MyFavoriteQAController {

    @Autowired
    private FavoriteQARepository repository;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/favorite/me/qas", method = RequestMethod.POST)
    public IdStatusListResponse createMyFavoriteQAList(
            @RequestBody(required=false) List<FavoriteQARequestWrapper> qaList) throws Exception {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/favorite/me/qas", method = RequestMethod.GET)
    public List<FavoriteQAResponseWrapper> readMyFavoriteQAList() throws Exception {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/favorite/me/qas", method = RequestMethod.PUT)
    public IdStatusListResponse updateMyFavoriteQAList(
            @RequestBody(required=false) List<FavoriteQARequestWrapper> qaList) throws Exception {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/favorite/me/qas", method = RequestMethod.DELETE)
    public IdStatusListResponse deleteFavoriteQAList(
            @RequestParam(value = "id_list", required = false) String idList) throws Exception {
        return null;
    }
}
