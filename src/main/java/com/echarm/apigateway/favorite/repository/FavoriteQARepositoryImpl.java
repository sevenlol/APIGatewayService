package com.echarm.apigateway.favorite.repository;

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
import com.echarm.apigateway.favorite.error.FavoriteListNotExistErrorBody;
import com.echarm.apigateway.favorite.model.FavoriteList;
import com.echarm.apigateway.favorite.model.FavoriteQA;
import com.echarm.apigateway.favorite.util.FavoriteListDbUtilities;

@Repository
public class FavoriteQARepositoryImpl implements FavoriteQARepository {

    private static final int FAVORITE_COUNT_MAX = 1000;

    @Autowired
    @Qualifier("mongo_web_service")
    private MongoTemplate mongoTemplate;

    @Override
    public FavoriteList createFavoriteQA(FavoriteList list) throws Exception {

        // check for all required fields
        defensiveCheck(mongoTemplate, list);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(list.getListId()));
        if (!mongoTemplate.exists(searchQuery, FavoriteList.class, MongoConfig.FAVORITE_LIST_COLLECTION_NAME)) {
            // document not exists, create
            mongoTemplate.save(list, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
            return list;
        } else {
            FavoriteList listInDb = mongoTemplate.findOne(searchQuery, FavoriteList.class, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
            if (listInDb != null) {

                int favoriteCount = 0;
                if (listInDb.getArticleMap() != null) {
                    favoriteCount += listInDb.getArticleMap().size();
                }
                if (listInDb.getQAMap() != null) {
                    favoriteCount += listInDb.getQAMap().size();
                }
                if (favoriteCount + list.getQAMap().size() > FAVORITE_COUNT_MAX) {
                    // TODO favorite limit exceeded, throw exception
                }

                Map<String, FavoriteQA> mapInDb = listInDb.getQAMap();
                if (mapInDb == null) {
                    listInDb.setQAMap(list.getQAMap());
                    mongoTemplate.save(listInDb, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
                    // create successfully
                    return list;
                }

                boolean isChanged = false;
                for (String id : list.getQAMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // qa not exists in db, create
                        mapInDb.put(id, list.getQAMap().get(id));
                        isChanged = true;
                    } else {
                        // qa exists in db, empty input qa to indicate create fail
                        emptyFavoriteQA(list.getQAMap().get(id));
                    }
                }

                if (isChanged) {
                    mongoTemplate.save(listInDb, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
                }
                return list;
            } else {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input qa list should not be null!");
            }
        }
    }

    @Override
    public FavoriteList readFavoriteQA(FavoriteList list) throws Exception {

        // check for all required fields
        defensiveCheck(mongoTemplate, list);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(list.getListId()));
        if (!mongoTemplate.exists(searchQuery, FavoriteList.class, MongoConfig.FAVORITE_LIST_COLLECTION_NAME)) {
            // document not exists, throw no contentexception
            // TODO check if the account exists, if not throw resource not exist exception
            throw new NoContentException();
//            ResourceNotExistException e = new ResourceNotExistException("Favorite qa list does not exist!");
//            e.setErrorBody(new FavoriteListNotExistErrorBody(list.getListId()));
//            throw e;
        } else {
            FavoriteList listInDb = mongoTemplate.findOne(searchQuery, FavoriteList.class, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
            if (listInDb == null || listInDb.getQAMap() == null) {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input qa list should not be null!");
            } else {
                if (listInDb.getQAMap().size() == 0) {
                    throw new NoContentException();
                }
                return listInDb;
            }
        }
    }

    @Override
    public FavoriteList updateFavoriteQA(FavoriteList list) throws Exception {

        // check for all required fields
        defensiveCheck(mongoTemplate, list);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(list.getListId()));
        if (!mongoTemplate.exists(searchQuery, FavoriteList.class, MongoConfig.FAVORITE_LIST_COLLECTION_NAME)) {
            // document not exists, update failed
            // throw resource not exist exception
            ResourceNotExistException e = new ResourceNotExistException("Favorite qa list does not exist!");
            e.setErrorBody(new FavoriteListNotExistErrorBody(list.getListId()));
            throw e;
        } else {
            // document exists, perform update
            FavoriteList listInDb = mongoTemplate.findOne(searchQuery, FavoriteList.class, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
            if (listInDb != null && list.getQAMap() != null) {
                Map<String, FavoriteQA> mapInDb = listInDb.getQAMap();
                if (mapInDb == null) {
                    // nothing in list, update failed
                    for (String key : list.getQAMap().keySet()) {
                        // empty all lists indicating all failed
                        emptyFavoriteQA(list.getQAMap().get(key));
                    }
                    return list;
                }

                boolean isChanged = false;
                for (String id : list.getQAMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // qa not exists in db, empty input qa to indicate update failed for this item
                        emptyFavoriteQA(list.getQAMap().get(id));
                    } else {
                        // qa exists in db, update
                        mapInDb.put(id, list.getQAMap().get(id));
                        isChanged = true;
                    }
                }

                if (isChanged) {
                    mongoTemplate.save(listInDb, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
                }
                return list;
            } else {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input qa list should not be null!");
            }
        }
    }

    @Override
    public FavoriteList deleteFavoriteQA(FavoriteList list) throws Exception {

        // check for all required fields
        defensiveCheck(mongoTemplate, list);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(list.getListId()));
        if (!mongoTemplate.exists(searchQuery, FavoriteList.class, MongoConfig.FAVORITE_LIST_COLLECTION_NAME)) {
            // document not exists, update failed
            // throw resource not exist exception
            ResourceNotExistException e = new ResourceNotExistException("Favorite qa list does not exist!");
            e.setErrorBody(new FavoriteListNotExistErrorBody(list.getListId()));
            throw e;
        } else {
            // document exists, perform delete
            FavoriteList listInDb = mongoTemplate.findOne(searchQuery, FavoriteList.class, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
            if (listInDb != null && list.getQAMap() != null) {
                Map<String, FavoriteQA> mapInDb = listInDb.getQAMap();
                if (mapInDb == null) {
                    // nothing in list, delete failed
                    for (String key : list.getQAMap().keySet()) {
                        // empty all lists indicating all failed
                        emptyFavoriteQA(list.getQAMap().get(key));
                    }
                    return list;
                }

                boolean isChanged = false;
                for (String id : list.getQAMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // qa not exists in db, empty input qa to indicate delete failed for this item
                        emptyFavoriteQA(list.getQAMap().get(id));
                    } else {
                        // qa exists in db, delete
                        mapInDb.remove(id);
                        isChanged = true;
                    }
                }

                if (isChanged) {
                    mongoTemplate.save(listInDb, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
                }
                return list;
            } else {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input qa list should not be null!");
            }
        }
    }

    // indicating create, update or delete fail
    private void emptyFavoriteQA(FavoriteQA qa) {
        if (qa == null) return;

        qa.setAnswerContent(null)
          .setAnswerId(null)
          .setQuestionCreatedAt(null)
          .setAnswerCreatedAt(null)
          .setAnswererCategory(null)
          .setAnswererId(null)
          .setAnswererName(null)
          .setAnswerId(null)
          .setQuestionCategory(null)
          .setQuestionContent(null)
          .setQuestionerId(null)
          .setQuestionerName(null)
          .setFavoriteAt(null);
    }

    private void defensiveCheck(MongoTemplate template, FavoriteList list) throws Exception {

        // make sure mongo template is not null
        checkMongoTemplate(template);

        // make sure collection exists
        FavoriteListDbUtilities.checkCollection(template);

        // make sure object and id are not null
        checkRequiredField(list);
    }

    private void checkMongoTemplate(MongoTemplate template) throws Exception {
        if (template == null) {
            throw new ServerSideProblemException("Mongo template should not be null!");
        }
    }

    private void checkRequiredField(FavoriteList list) throws Exception {
        String errorMsg = null;
        if (list == null) {
            errorMsg = "FavoriteList should not be null!";
        } else if (list.getListId() == null) {
            errorMsg = "FavoriteList should not have null ID!";
        } else if (list.getQAMap() == null) {
            errorMsg = "FavoriteList should not have null qa map!";
        }

        if (errorMsg != null) {
            throw new ServerSideProblemException(errorMsg);
        }
    }

}
