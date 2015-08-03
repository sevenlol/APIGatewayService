package com.echarm.apigateway.popular.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PopularDoctorList {

    private String listId;
    private String listCategory;
    private Map<String, PopularDoctor> doctorMap;

    public PopularDoctorList() {}

    /* getter methods */

    @JsonIgnore public String getListId() { return listId; }
    @JsonIgnore public String getListCategory() { return listCategory; }
    @JsonIgnore public Map<String, PopularDoctor> getDoctorMap() { return doctorMap; }

    /* setter methods */

    @JsonIgnore
    public PopularDoctorList setListId(String listId) {
        this.listId = listId; return this;
    }

    @JsonIgnore
    public PopularDoctorList setListCategory(String listCategory) {
        this.listCategory = listCategory; return this;
    }

    @JsonIgnore
    public PopularDoctorList setDoctorMap(Map<String, PopularDoctor> doctorMap) {
        this.doctorMap = doctorMap; return this;
    }
}
