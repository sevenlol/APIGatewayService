package com.echarm.apigateway.popular.repository;

import com.echarm.apigateway.popular.model.PopularArticleList;

//FIXME think about the interface
public interface PopularArticleListRepository {

    public PopularArticleList createPopularArticleList(PopularArticleList articleList) throws Exception;
    public PopularArticleList updatePopularArticleList(PopularArticleList articleList) throws Exception;
    public PopularArticleList deletePopularArticleList(PopularArticleList articleList) throws Exception;
    public PopularArticleList readPopularArticleList(PopularArticleList articleList) throws Exception;
}
