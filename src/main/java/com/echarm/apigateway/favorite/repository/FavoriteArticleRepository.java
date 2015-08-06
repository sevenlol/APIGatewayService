package com.echarm.apigateway.favorite.repository;

import com.echarm.apigateway.favorite.model.FavoriteList;

public interface FavoriteArticleRepository {

    public FavoriteList createFavoriteArticle(FavoriteList list) throws Exception;
    public FavoriteList readFavoriteArticle(FavoriteList list) throws Exception;
    public FavoriteList updateFavoriteArticle(FavoriteList list) throws Exception;
    public FavoriteList deleteFavoriteArticle(FavoriteList list) throws Exception;
}
