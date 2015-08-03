package com.echarm.apigateway.popular.repository;

import com.echarm.apigateway.popular.model.PopularDoctorList;

//FIXME think about the interface
public interface PopularDoctorListRepository {

    public PopularDoctorList createDoctorList(PopularDoctorList doctorList) throws Exception;
    public PopularDoctorList updateDoctorList(PopularDoctorList doctorList) throws Exception;
    public PopularDoctorList deleteDoctorList(PopularDoctorList doctorList) throws Exception;
    public PopularDoctorList readDoctorList(PopularDoctorList doctorList) throws Exception;
}
