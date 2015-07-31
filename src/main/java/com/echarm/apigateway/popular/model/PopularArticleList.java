package com.echarm.apigateway.popular.model;

import java.util.List;

public class PopularArticleList {

    private String listId;
    private String listCategory;
    private List<PopularArticle> articleList;

    public PopularArticleList() {}

    /* getter methods */

    public String getListId() { return listId; }
    public String getListCategory() { return listCategory; }
    public List<PopularArticle> getArticleList() { return articleList; }

    /* setter methods */

    public PopularArticleList setListId(String listId) {
        this.listId = listId; return this;
    }

    public PopularArticleList setListCategory(String listCategory) {
        this.listCategory = listCategory; return this;
    }

    public PopularArticleList setArticleList(List<PopularArticle> articleList) {
        this.articleList = articleList; return this;
    }
}
