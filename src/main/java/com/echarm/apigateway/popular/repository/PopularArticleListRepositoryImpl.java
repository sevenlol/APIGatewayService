package com.echarm.apigateway.popular.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.echarm.apigateway.accountsystem.error.ResourceNotExistException;
import com.echarm.apigateway.accountsystem.error.ServerSideProblemException;
import com.echarm.apigateway.config.MongoConfig;
import com.echarm.apigateway.popular.error.PopularArticleListNotExistErrorBody;
import com.echarm.apigateway.popular.model.PopularArticle;
import com.echarm.apigateway.popular.model.PopularArticleList;
import com.echarm.apigateway.popular.util.PopularListDbUtilities;

@Repository
public class PopularArticleListRepositoryImpl implements PopularArticleListRepository {

    @Autowired
    @Qualifier("mongo_web_service")
    private MongoTemplate mongoTemplate;

    @Override
    public PopularArticleList createPopularArticleList(
            PopularArticleList articleList) throws Exception {

        // check for all required fields
        defensiveCheck(mongoTemplate, articleList);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(articleList.getListId()));
        if (!mongoTemplate.exists(searchQuery, PopularArticleList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME)) {
            // document not exists, create
            mongoTemplate.save(articleList, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            return articleList;
        } else {
            PopularArticleList listInDb = mongoTemplate.findOne(searchQuery, PopularArticleList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            if (listInDb != null && articleList.getArticleMap() != null) {
                Map<String, PopularArticle> mapInDb = listInDb.getArticleMap();
                if (mapInDb == null) {
                    listInDb.setArticleMap(articleList.getArticleMap());
                    mongoTemplate.save(listInDb, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
                    // create successfully
                    return articleList;
                }

                boolean isChanged = false;
                for (String id : articleList.getArticleMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // article not exists in db, create
                        mapInDb.put(id, articleList.getArticleMap().get(id));
                        isChanged = true;
                    } else {
                        // article exists in db, empty input article to indicate create fail
                        emptyPopularArticle(articleList.getArticleMap().get(id));
                    }
                }

                if (isChanged) {
                    mongoTemplate.save(listInDb, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
                }
                return articleList;
            } else {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input article list should not be null!");
            }
        }
    }

    @Override
    public PopularArticleList updatePopularArticleList(
            PopularArticleList articleList) throws Exception {
        // check for all required fields
        defensiveCheck(mongoTemplate, articleList);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(articleList.getListId()));
        if (!mongoTemplate.exists(searchQuery, PopularArticleList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME)) {
            // document not exists, update failed
            // throw resource not exist exception
            ResourceNotExistException e = new ResourceNotExistException("Popular article list does not exist!");
            e.setErrorBody(new PopularArticleListNotExistErrorBody(articleList.getListId()));
            throw e;
        } else {
            // document exists, perform update
            PopularArticleList listInDb = mongoTemplate.findOne(searchQuery, PopularArticleList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            if (listInDb != null && articleList.getArticleMap() != null) {
                Map<String, PopularArticle> mapInDb = listInDb.getArticleMap();
                if (mapInDb == null) {
                    // nothing in list, update failed
                    for (String key : articleList.getArticleMap().keySet()) {
                        // empty all lists indicating all failed
                        emptyPopularArticle(articleList.getArticleMap().get(key));
                    }
                    return articleList;
                }

                boolean isChanged = false;
                for (String id : articleList.getArticleMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // article not exists in db, empty input article to indicate update failed for this item
                        emptyPopularArticle(articleList.getArticleMap().get(id));
                    } else {
                        // article exists in db, update
                        mapInDb.put(id, articleList.getArticleMap().get(id));
                        isChanged = true;
                    }
                }

                if (isChanged) {
                    mongoTemplate.save(listInDb, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
                }
                return articleList;
            } else {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input article list should not be null!");
            }
        }
    }

    @Override
    public PopularArticleList deletePopularArticleList(
            PopularArticleList articleList) throws Exception {
        // check for all required fields
        defensiveCheck(mongoTemplate, articleList);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(articleList.getListId()));
        if (!mongoTemplate.exists(searchQuery, PopularArticleList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME)) {
            // document not exists, update failed
            // throw resource not exist exception
            ResourceNotExistException e = new ResourceNotExistException("Popular article list does not exist!");
            e.setErrorBody(new PopularArticleListNotExistErrorBody(articleList.getListId()));
            throw e;
        } else {
            // document exists, perform delete
            PopularArticleList listInDb = mongoTemplate.findOne(searchQuery, PopularArticleList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            if (listInDb != null && articleList.getArticleMap() != null) {
                Map<String, PopularArticle> mapInDb = listInDb.getArticleMap();
                if (mapInDb == null) {
                    // nothing in list, delete failed
                    for (String key : articleList.getArticleMap().keySet()) {
                        // empty all lists indicating all failed
                        emptyPopularArticle(articleList.getArticleMap().get(key));
                    }
                    return articleList;
                }

                boolean isChanged = false;
                for (String id : articleList.getArticleMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // article not exists in db, empty input article to indicate delete failed for this item
                        emptyPopularArticle(articleList.getArticleMap().get(id));
                    } else {
                        // article exists in db, delete
                        mapInDb.remove(id);
                        isChanged = true;
                    }
                }

                if (isChanged) {
                    mongoTemplate.save(listInDb, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
                }
                return articleList;
            } else {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input article list should not be null!");
            }
        }
    }

    @Override
    public PopularArticleList readPopularArticleList(
            PopularArticleList articleList) throws Exception {
        // check for all required fields
        defensiveCheck(mongoTemplate, articleList);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(articleList.getListId()));
        if (!mongoTemplate.exists(searchQuery, PopularArticleList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME)) {
            // throw resource not exist exception
            ResourceNotExistException e = new ResourceNotExistException("Popular article list does not exist!");
            e.setErrorBody(new PopularArticleListNotExistErrorBody(articleList.getListId()));
            throw e;
        } else {
            PopularArticleList listInDb = mongoTemplate.findOne(searchQuery, PopularArticleList.class, MongoConfig.POPULAR_LIST_COLLECTION_NAME);
            if (listInDb == null) {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input article list should not be null!");
            } else {
                return listInDb;
            }
        }
    }

    // indicating create, update or delete fail
    private void emptyPopularArticle(PopularArticle article) {
        if (article == null) return;

        article
            .setArticleCategory(null)
            .setArticleTitle(null)
            .setAuthorCategory(null)
            .setAuthorId(null)
            .setAuthorName(null);
    }

    private void defensiveCheck(MongoTemplate template, PopularArticleList articleList) throws Exception {

        // make sure mongo template is not null
        checkMongoTemplate(template);

        // make sure collection exists
        PopularListDbUtilities.checkCollection(template);

        // make sure object and id are not null
        checkRequiredField(articleList);
    }

    private void checkMongoTemplate(MongoTemplate template) throws Exception {
        if (template == null) {
            throw new ServerSideProblemException("Mongo template should not be null!");
        }
    }

    private void checkRequiredField(PopularArticleList articleList) throws Exception {
        String errorMsg = null;
        if (articleList == null) {
            errorMsg = "PopularArticleList should not be null!";
        } else if (articleList.getListId() == null) {
            errorMsg = "PopularArticleList should not have null ID!";
        }

        if (errorMsg != null) {
            throw new ServerSideProblemException(errorMsg);
        }
    }

}
