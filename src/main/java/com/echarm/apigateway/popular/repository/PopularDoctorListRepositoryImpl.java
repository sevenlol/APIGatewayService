package com.echarm.apigateway.popular.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.echarm.apigateway.accountsystem.error.NoContentException;
import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.config.MongoConfig;
import com.echarm.apigateway.popular.error.PopularDoctorListNotExistErrorBody;
import com.echarm.apigateway.popular.model.PopularDoctor;
import com.echarm.apigateway.popular.model.PopularDoctorList;
import com.echarm.apigateway.popular.util.PopularListDbUtilities;

@Repository
public class PopularDoctorListRepositoryImpl implements PopularDoctorListRepository {

    @Autowired
    @Qualifier("mongo_web_service")
    private MongoTemplate mongoTemplate;

    @Override
    public PopularDoctorList createDoctorList(PopularDoctorList doctorList)
            throws Exception {

        // check for all required fields
        defensiveCheck(mongoTemplate, doctorList);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(doctorList.getListId()));
        if (!mongoTemplate.exists(searchQuery, PopularDoctorList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME)) {
            // document not exists, create
            mongoTemplate.save(doctorList, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            return doctorList;
        } else {
            PopularDoctorList listInDb = mongoTemplate.findOne(searchQuery, PopularDoctorList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            if (listInDb != null && doctorList.getDoctorMap() != null) {
                Map<String, PopularDoctor> mapInDb = listInDb.getDoctorMap();
                if (mapInDb == null) {
                    listInDb.setDoctorMap(doctorList.getDoctorMap());
                    mongoTemplate.save(listInDb, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
                    // create successfully
                    return doctorList;
                }

                boolean isChanged = false;
                for (String id : doctorList.getDoctorMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // doctor not exists in db, create
                        mapInDb.put(id, doctorList.getDoctorMap().get(id));
                        isChanged = true;
                    } else {
                        // doctor exists in db, empty input doctor to indicate create fail
                        emptyPopularDoctor(doctorList.getDoctorMap().get(id));
                    }
                }

                if (isChanged) {
                    mongoTemplate.save(listInDb, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
                }
                return doctorList;
            } else {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input doctor list should not be null!");
            }
        }
    }

    @Override
    public PopularDoctorList updateDoctorList(PopularDoctorList doctorList)
            throws Exception {

        // check for all required fields
        defensiveCheck(mongoTemplate, doctorList);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(doctorList.getListId()));
        if (!mongoTemplate.exists(searchQuery, PopularDoctorList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME)) {
            // document not exists, update failed
            // throw resource not exist exception
            ResourceNotExistException e = new ResourceNotExistException("Popular doctor list does not exist!");
            e.setErrorBody(new PopularDoctorListNotExistErrorBody(doctorList.getListId()));
            throw e;
        } else {
            // document exists, perform update
            PopularDoctorList listInDb = mongoTemplate.findOne(searchQuery, PopularDoctorList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            if (listInDb != null && doctorList.getDoctorMap() != null) {
                Map<String, PopularDoctor> mapInDb = listInDb.getDoctorMap();
                if (mapInDb == null) {
                    // nothing in list, update failed
                    for (String key : doctorList.getDoctorMap().keySet()) {
                        // empty all lists indicating all failed
                        emptyPopularDoctor(doctorList.getDoctorMap().get(key));
                    }
                    return doctorList;
                }

                boolean isChanged = false;
                for (String id : doctorList.getDoctorMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // doctor not exists in db, empty input doctor to indicate update failed for this item
                        emptyPopularDoctor(doctorList.getDoctorMap().get(id));
                    } else {
                        // doctor exists in db, update
                        mapInDb.put(id, doctorList.getDoctorMap().get(id));
                        isChanged = true;
                    }
                }

                if (isChanged) {
                    mongoTemplate.save(listInDb, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
                }
                return doctorList;
            } else {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input doctor list should not be null!");
            }
        }
    }

    @Override
    public PopularDoctorList deleteDoctorList(PopularDoctorList doctorList)
            throws Exception {
        // check for all required fields
        defensiveCheck(mongoTemplate, doctorList);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(doctorList.getListId()));
        if (!mongoTemplate.exists(searchQuery, PopularDoctorList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME)) {
            // document not exists, update failed
            // throw resource not exist exception
            ResourceNotExistException e = new ResourceNotExistException("Popular doctor list does not exist!");
            e.setErrorBody(new PopularDoctorListNotExistErrorBody(doctorList.getListId()));
            throw e;
        } else {
            // document exists, perform delete
            PopularDoctorList listInDb = mongoTemplate.findOne(searchQuery, PopularDoctorList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            if (listInDb != null && doctorList.getDoctorMap() != null) {
                Map<String, PopularDoctor> mapInDb = listInDb.getDoctorMap();
                if (mapInDb == null) {
                    // nothing in list, delete failed
                    for (String key : doctorList.getDoctorMap().keySet()) {
                        // empty all lists indicating all failed
                        emptyPopularDoctor(doctorList.getDoctorMap().get(key));
                    }
                    return doctorList;
                }

                boolean isChanged = false;
                for (String id : doctorList.getDoctorMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // doctor not exists in db, empty input doctor to indicate delete failed for this item
                        emptyPopularDoctor(doctorList.getDoctorMap().get(id));
                    } else {
                        // doctor exists in db, delete
                        mapInDb.remove(id);
                        isChanged = true;
                    }
                }

                if (isChanged) {
                    mongoTemplate.save(listInDb, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
                }
                return doctorList;
            } else {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input doctor list should not be null!");
            }
        }
    }

    @Override
    public PopularDoctorList readDoctorList(PopularDoctorList doctorList)
            throws Exception {
        // check for all required fields
        defensiveCheck(mongoTemplate, doctorList);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(doctorList.getListId()));
        if (!mongoTemplate.exists(searchQuery, PopularDoctorList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME)) {
            // throw resource not exist exception
            ResourceNotExistException e = new ResourceNotExistException("Popular doctor list does not exist!");
            e.setErrorBody(new PopularDoctorListNotExistErrorBody(doctorList.getListId()));
            throw e;
        } else {
            PopularDoctorList listInDb = mongoTemplate.findOne(searchQuery, PopularDoctorList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            if (listInDb == null || listInDb.getDoctorMap() == null) {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input doctor list should not be null!");
            } else {
                if (listInDb.getDoctorMap().size() == 0) {
                    throw new NoContentException();
                }
                return listInDb;
            }
        }
    }

    // indicating create, update or delete fail
    private void emptyPopularDoctor(PopularDoctor doctor) {
        if (doctor == null) return;

        doctor
            .setDoctorCategory(null)
            .setDoctorName(null)
            .setDoctorStickerUrl(null);
    }

    private void defensiveCheck(MongoTemplate template, PopularDoctorList doctorList) throws Exception {

        // make sure mongo template is not null
        checkMongoTemplate(template);

        // make sure collection exists
        PopularListDbUtilities.checkCollection(template);

        // make sure object and id are not null
        checkRequiredField(doctorList);
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
