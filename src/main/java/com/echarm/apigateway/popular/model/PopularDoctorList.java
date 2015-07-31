package com.echarm.apigateway.popular.model;

import java.util.List;

public class PopularDoctorList {

    private String listId;
    private String listCategory;
    private List<PopularDoctor> doctorList;

    public PopularDoctorList() {}

    /* getter methods */

    public String getListId() { return listId; }
    public String getListCategory() { return listCategory; }
    public List<PopularDoctor> getDoctorList() { return doctorList; }

    /* setter methods */

    public PopularDoctorList setListId(String listId) {
        this.listId = listId; return this;
    }

    public PopularDoctorList setListCategory(String listCategory) {
        this.listCategory = listCategory; return this;
    }

    public PopularDoctorList setDoctorList(List<PopularDoctor> doctorList) {
        this.doctorList = doctorList; return this;
    }
}
