package com.echarm.apigateway.popular.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/*
 * Not using this atm
 * TODO remove the hardcoding category list and use this
 */
@Document
public class PopularCategoryList {

    @Id private String listId;

    @Field("article_category_list") private Set<String> articleCategorySet;
    @Field("doctor_category_list") private Set<String> doctorCategorySet;
    @Field("qa_category_list") private Set<String> qaCategorySet;

    public PopularCategoryList() {}

    /* getter methods */

    public String getListId() { return listId; }
    public Set<String> getArticleCategorySet() { return articleCategorySet; }
    public Set<String> getDoctorCategorySet() { return doctorCategorySet; }
    public Set<String> getQACategorySet() { return qaCategorySet; }

    /* setter methods */

    public PopularCategoryList setListId(String listId) { this.listId = listId; return this; }

    public PopularCategoryList setArticleCategorySet(Set<String> articleCategorySet) {
        this.articleCategorySet = articleCategorySet; return this;
    }

    public PopularCategoryList setDoctorCategorySet(Set<String> doctorCategorySet) {
        this.doctorCategorySet = doctorCategorySet; return this;
    }

    public PopularCategoryList setQACategorySet(Set<String> qaCategorySet) {
        this.qaCategorySet = qaCategorySet; return this;
    }
}
