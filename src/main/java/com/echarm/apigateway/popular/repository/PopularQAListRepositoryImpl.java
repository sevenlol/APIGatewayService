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
import com.echarm.apigateway.popular.error.PopularQAListNotExistErrorBody;
import com.echarm.apigateway.popular.model.PopularQA;
import com.echarm.apigateway.popular.model.PopularQAList;
import com.echarm.apigateway.popular.util.PopularListDbUtilities;

@Repository
public class PopularQAListRepositoryImpl implements PopularQAListRepository {

    @Autowired
    @Qualifier("mongo_web_service")
    private MongoTemplate mongoTemplate;

    @Override
    public PopularQAList createQAList(PopularQAList qaList) throws Exception {

        // check for all required fields
        defensiveCheck(mongoTemplate, qaList);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(qaList.getListId()));
        if (!mongoTemplate.exists(searchQuery, PopularQAList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME)) {
            // document not exists, create
            mongoTemplate.save(qaList, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            return qaList;
        } else {
            PopularQAList listInDb = mongoTemplate.findOne(searchQuery, PopularQAList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            if (listInDb != null && qaList.getQaMap() != null) {
                Map<String, PopularQA> mapInDb = listInDb.getQaMap();
                if (mapInDb == null) {
                    listInDb.setQaMap(qaList.getQaMap());
                    mongoTemplate.save(listInDb, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
                    // create successfully
                    return qaList;
                }

                boolean isChanged = false;
                for (String id : qaList.getQaMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // qa not exists in db, create
                        mapInDb.put(id, qaList.getQaMap().get(id));
                        isChanged = true;
                    } else {
                        // qa exists in db, empty input qa to indicate create fail
                        emptyPopularQA(qaList.getQaMap().get(id));
                    }
                }

                if (isChanged) {
                    mongoTemplate.save(listInDb, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
                }
                return qaList;
            } else {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input qa list should not be null!");
            }
        }
    }

    @Override
    public PopularQAList updateQAList(PopularQAList qaList) throws Exception {

        // check for all required fields
        defensiveCheck(mongoTemplate, qaList);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(qaList.getListId()));
        if (!mongoTemplate.exists(searchQuery, PopularQAList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME)) {
            // document not exists, update failed
            // throw resource not exist exception
            ResourceNotExistException e = new ResourceNotExistException("Popular qa list does not exist!");
            e.setErrorBody(new PopularQAListNotExistErrorBody(qaList.getListId()));
            throw e;
        } else {
            // document exists, perform update
            PopularQAList listInDb = mongoTemplate.findOne(searchQuery, PopularQAList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            if (listInDb != null && qaList.getQaMap() != null) {
                Map<String, PopularQA> mapInDb = listInDb.getQaMap();
                if (mapInDb == null) {
                    // nothing in list, update failed
                    for (String key : qaList.getQaMap().keySet()) {
                        // empty all lists indicating all failed
                        emptyPopularQA(qaList.getQaMap().get(key));
                    }
                    return qaList;
                }

                boolean isChanged = false;
                for (String id : qaList.getQaMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // qa not exists in db, empty input qa to indicate update failed for this item
                        emptyPopularQA(qaList.getQaMap().get(id));
                    } else {
                        // qa exists in db, update
                        mapInDb.put(id, qaList.getQaMap().get(id));
                        isChanged = true;
                    }
                }

                if (isChanged) {
                    mongoTemplate.save(listInDb, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
                }
                return qaList;
            } else {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input qa list should not be null!");
            }
        }
    }

    @Override
    public PopularQAList deleteQAList(PopularQAList qaList) throws Exception {

        // check for all required fields
        defensiveCheck(mongoTemplate, qaList);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(qaList.getListId()));
        if (!mongoTemplate.exists(searchQuery, PopularQAList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME)) {
            // document not exists, update failed
            // throw resource not exist exception
            ResourceNotExistException e = new ResourceNotExistException("Popular qa list does not exist!");
            e.setErrorBody(new PopularQAListNotExistErrorBody(qaList.getListId()));
            throw e;
        } else {
            // document exists, perform delete
            PopularQAList listInDb = mongoTemplate.findOne(searchQuery, PopularQAList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            if (listInDb != null && qaList.getQaMap() != null) {
                Map<String, PopularQA> mapInDb = listInDb.getQaMap();
                if (mapInDb == null) {
                    // nothing in list, delete failed
                    for (String key : qaList.getQaMap().keySet()) {
                        // empty all lists indicating all failed
                        emptyPopularQA(qaList.getQaMap().get(key));
                    }
                    return qaList;
                }

                boolean isChanged = false;
                for (String id : qaList.getQaMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // qa not exists in db, empty input qa to indicate delete failed for this item
                        emptyPopularQA(qaList.getQaMap().get(id));
                    } else {
                        // qa exists in db, delete
                        mapInDb.remove(id);
                        isChanged = true;
                    }
                }

                if (isChanged) {
                    mongoTemplate.save(listInDb, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
                }
                return qaList;
            } else {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input qa list should not be null!");
            }
        }
    }

    @Override
    public PopularQAList readQAList(PopularQAList qaList) throws Exception {
        // check for all required fields
        defensiveCheck(mongoTemplate, qaList);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(qaList.getListId()));
        if (!mongoTemplate.exists(searchQuery, PopularQAList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME)) {
            // throw resource not exist exception
            ResourceNotExistException e = new ResourceNotExistException("Popular qa list does not exist!");
            e.setErrorBody(new PopularQAListNotExistErrorBody(qaList.getListId()));
            throw e;
        } else {
            PopularQAList listInDb = mongoTemplate.findOne(searchQuery, PopularQAList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            if (listInDb == null || listInDb.getQaMap() == null) {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input qa list should not be null!");
            } else {
                if (listInDb.getQaMap().size() == 0) {
                    throw new NoContentException();
                }
                return listInDb;
            }
        }
    }

    // indicating create, update or delete fail
    private void emptyPopularQA(PopularQA qa) {
        if (qa == null) return;

        qa.setAnswerContent(null)
          .setAnswererId(null)
          .setAnswererName(null)
          .setAnswerId(null)
          .setQuestionCategory(null)
          .setQuestionContent(null)
          .setQuestionerId(null)
          .setQuestionerName(null);
    }

    private void defensiveCheck(MongoTemplate template, PopularQAList qaList) throws Exception {

        // make sure mongo template is not null
        checkMongoTemplate(template);

        // make sure collection exists
        PopularListDbUtilities.checkCollection(template);

        // make sure object and id are not null
        checkRequiredField(qaList);
    }

    private void checkMongoTemplate(MongoTemplate template) throws Exception {
        if (template == null) {
            throw new ServerSideProblemException("Mongo template should not be null!");
        }
    }

    private void checkRequiredField(PopularQAList qaList) throws Exception {
        String errorMsg = null;
        if (qaList == null) {
            errorMsg = "PopularQAList should not be null!";
        } else if (qaList.getListId() == null) {
            errorMsg = "PopularQAList should not have null ID!";
        }

        if (errorMsg != null) {
            throw new ServerSideProblemException(errorMsg);
        }
    }

}
