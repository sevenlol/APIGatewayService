package com.echarm.apigateway.popular.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class PopularDoctorList {

    @Id private String listId;
    @Field("list_category") private String listCategory;
    @Field("article_map") private Map<String, PopularDoctor> doctorMap;

    public PopularDoctorList() {}

    /* getter methods */

    public String getListId() { return listId; }
    public String getListCategory() { return listCategory; }
    public Map<String, PopularDoctor> getDoctorMap() { return doctorMap; }

    /* setter methods */

    public PopularDoctorList setListId(String listId) {
        this.listId = listId; return this;
    }

    public PopularDoctorList setListCategory(String listCategory) {
        this.listCategory = listCategory; return this;
    }

    public PopularDoctorList setDoctorMap(Map<String, PopularDoctor> doctorMap) {
        this.doctorMap = doctorMap; return this;
    }
}
