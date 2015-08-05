package com.echarm.apigateway.favorite.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class FavoriteList {

    @Id private String listId;
    @Field("article_map") private Map<String, FavoriteArticle> articleMap;
    @Field("qa_map") private Map<String, FavoriteQA> qaMap;

    public FavoriteList() {}

    /* getter methods */

    public String getListId() { return listId; }
    public Map<String, FavoriteArticle> getArticleMap() { return articleMap; }
    public Map<String, FavoriteQA> getQAMap() { return qaMap; }

    /* setter methods */

    public FavoriteList setListId(String listId) {
        this.listId = listId; return this;
    }

    public FavoriteList setArticleMap(Map<String, FavoriteArticle> articleMap) {
        this.articleMap = articleMap; return this;
    }

    public FavoriteList setQAMap(Map<String, FavoriteQA> qaMap) {
        this.qaMap = qaMap; return this;
    }
}
