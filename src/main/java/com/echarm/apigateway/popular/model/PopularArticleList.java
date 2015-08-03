package com.echarm.apigateway.popular.model;

import java.util.Map;

public class PopularArticleList {

    private String listId;
    private String listCategory;
    private Map<String, PopularArticle> articleMap;

    public PopularArticleList() {}

    /* getter methods */

    public String getListId() { return listId; }
    public String getListCategory() { return listCategory; }
    public Map<String, PopularArticle> getArticleMap() { return articleMap; }

    /* setter methods */

    public PopularArticleList setListId(String listId) {
        this.listId = listId; return this;
    }

    public PopularArticleList setListCategory(String listCategory) {
        this.listCategory = listCategory; return this;
    }

    public PopularArticleList setArticleMap(Map<String, PopularArticle> articleMap) {
        this.articleMap = articleMap; return this;
    }
}
