package com.echarm.apigateway.popular.model;

import java.util.Map;

public class PopularQAList {

    private String listId;
    private String listCategory;
    private Map<String, PopularQA> qaMap;

    public PopularQAList() {}

    /* getter methods */

    public String getListId() { return listId; }
    public String getListCategory() { return listCategory; }
    public Map<String, PopularQA> getQaMap() { return qaMap; }

    /* setter methods */

    public PopularQAList setListId(String listId) {
        this.listId = listId; return this;
    }

    public PopularQAList setListCategory(String listCategory) {
        this.listCategory = listCategory; return this;
    }

    public PopularQAList setQaMap(Map<String, PopularQA> qaMap) {
        this.qaMap = qaMap; return this;
    }
}
