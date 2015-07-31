package com.echarm.apigateway.popular.model;

import java.util.List;

public class PopularQAList {

    private String listId;
    private String listCategory;
    private List<PopularQuestionAndAnswer> qaList;

    public PopularQAList() {}

    /* getter methods */

    public String getListId() { return listId; }
    public String getListCategory() { return listCategory; }
    public List<PopularQuestionAndAnswer> getQaList() { return qaList; }

    /* setter methods */

    public PopularQAList setListId(String listId) {
        this.listId = listId; return this;
    }

    public PopularQAList setListCategory(String listCategory) {
        this.listCategory = listCategory; return this;
    }

    public PopularQAList setQaList(List<PopularQuestionAndAnswer> qaList) {
        this.qaList = qaList; return this;
    }
}
