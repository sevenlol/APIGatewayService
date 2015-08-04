package com.echarm.apigateway.popular.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class PopularArticleList {

    @Id private String listId;
    @Field("list_category") private String listCategory;
    @Field("article_map") private Map<String, PopularArticle> articleMap;

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
