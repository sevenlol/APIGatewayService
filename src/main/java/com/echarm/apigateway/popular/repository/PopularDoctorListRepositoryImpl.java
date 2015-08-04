package com.echarm.apigateway.popular.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.popular.model.PopularDoctorList;

@Repository
public class PopularDoctorListRepositoryImpl implements PopularDoctorListRepository {

    @Autowired
    @Qualifier("mongo_web_service")
    private MongoTemplate mongoTemplate;

    @Override
    public PopularDoctorList createDoctorList(PopularDoctorList doctorList)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PopularDoctorList updateDoctorList(PopularDoctorList doctorList)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PopularDoctorList deleteDoctorList(PopularDoctorList doctorList)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PopularDoctorList readDoctorList(PopularDoctorList doctorList)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    private void checkMongoTemplate(MongoTemplate template) throws Exception {
        if (template == null) {
            throw new ServerSideProblemException("Mongo template should not be null!");
        }
    }

    private void checkRequiredField(PopularDoctorList doctorList) throws Exception {
        String errorMsg = null;
        if (doctorList == null) {
            errorMsg = "PopularDoctorList should not be null!";
        } else if (doctorList.getListId() == null) {
            errorMsg = "PopularDoctorList should not have null ID!";
        }

        if (errorMsg != null) {
            throw new ServerSideProblemException(errorMsg);
        }
    }

}
