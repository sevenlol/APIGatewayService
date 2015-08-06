package com.echarm.apigateway.favorite.repository;

import com.echarm.apigateway.favorite.model.FavoriteList;

public interface FavoriteQARepository {

    public FavoriteList createFavoriteQA(FavoriteList list) throws Exception;
    public FavoriteList readFavoriteQA(FavoriteList list) throws Exception;
    public FavoriteList updateFavoriteQA(FavoriteList list) throws Exception;
    public FavoriteList deleteFavoriteQA(FavoriteList list) throws Exception;
}
