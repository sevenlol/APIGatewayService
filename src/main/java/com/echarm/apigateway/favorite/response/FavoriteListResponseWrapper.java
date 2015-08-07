package com.echarm.apigateway.favorite.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class FavoriteListResponseWrapper {

    private List<FavoriteArticleResponseWrapper> articleList;
    private List<FavoriteQAResponseWrapper> qaList;

    public FavoriteListResponseWrapper() {}

    /* getter methods */

    @JsonProperty("favirote_articles")
    public List<FavoriteArticleResponseWrapper> getArticleList() {
        return articleList;
    }

    @JsonProperty("favorite_qas")
    public List<FavoriteQAResponseWrapper> getQAList() {
        return qaList;
    }

    /* setter methods */

    public FavoriteListResponseWrapper setArticleList(List<FavoriteArticleResponseWrapper> articleList) {
        this.articleList = articleList; return this;
    }

    public FavoriteListResponseWrapper setQAList(List<FavoriteQAResponseWrapper> qaList) {
        this.qaList = qaList; return this;
    }
}
