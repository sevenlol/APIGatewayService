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
import com.echarm.apigateway.favorite.model.FavoriteArticle;
import com.echarm.apigateway.favorite.model.FavoriteList;
import com.echarm.apigateway.favorite.util.FavoriteListDbUtilities;

@Repository
public class FavoriteArticleRepositoryImpl implements FavoriteArticleRepository {

    private static final int FAVORITE_COUNT_MAX = 1000;

    @Autowired
    @Qualifier("mongo_web_service")
    private MongoTemplate mongoTemplate;

    @Override
    public FavoriteList createFavoriteArticle(FavoriteList list) throws Exception {

        // check input
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
                if (favoriteCount + list.getArticleMap().size() > FAVORITE_COUNT_MAX) {
                    // TODO favorite limit exceeded, throw exception
                }

                Map<String, FavoriteArticle> mapInDb = listInDb.getArticleMap();
                if (mapInDb == null) {
                    listInDb.setArticleMap(list.getArticleMap());
                    mongoTemplate.save(listInDb, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
                    // create successfully
                    return list;
                }

                boolean isChanged = false;
                for (String id : list.getArticleMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // article not exists in db, create
                        mapInDb.put(id, list.getArticleMap().get(id));
                        isChanged = true;
                    } else {
                        // article exists in db, empty input article to indicate create fail
                        emptyFavoriteArticle(list.getArticleMap().get(id));
                    }
                }

                if (isChanged) {
                    mongoTemplate.save(listInDb, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
                }
                return list;
            } else {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input article list should not be null!");
            }
        }
    }

    @Override
    public FavoriteList readFavoriteArticle(FavoriteList list) throws Exception {

        // check input
        defensiveCheck(mongoTemplate, list);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(list.getListId()));
        if (!mongoTemplate.exists(searchQuery, FavoriteList.class, MongoConfig.FAVORITE_LIST_COLLECTION_NAME)) {
            // changed to thorw no content exception
            // TODO check if the account exists, if not throw resource not exist exception
            throw new NoContentException();
//            ResourceNotExistException e = new ResourceNotExistException("Favorite article list does not exist!");
//            e.setErrorBody(new FavoriteListNotExistErrorBody(list.getListId()));
//            throw e;
        } else {
            FavoriteList listInDb = mongoTemplate.findOne(searchQuery, FavoriteList.class, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
            if (listInDb == null || listInDb.getArticleMap() == null) {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input article list should not be null!");
            } else {
                if (listInDb.getArticleMap().size() == 0) {
                    throw new NoContentException();
                }
                return listInDb;
            }
        }
    }

    @Override
    public FavoriteList updateFavoriteArticle(FavoriteList list) throws Exception {

        // check input
        defensiveCheck(mongoTemplate, list);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(list.getListId()));
        if (!mongoTemplate.exists(searchQuery, FavoriteList.class, MongoConfig.FAVORITE_LIST_COLLECTION_NAME)) {
            // document not exists, update failed
            // throw resource not exist exception
            ResourceNotExistException e = new ResourceNotExistException("Favorite article list does not exist!");
            e.setErrorBody(new FavoriteListNotExistErrorBody(list.getListId()));
            throw e;
        } else {
            // document exists, perform update
            FavoriteList listInDb = mongoTemplate.findOne(searchQuery, FavoriteList.class, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
            if (listInDb != null && list.getArticleMap() != null) {
                Map<String, FavoriteArticle> mapInDb = listInDb.getArticleMap();
                if (mapInDb == null) {
                    // nothing in list, update failed
                    for (String key : list.getArticleMap().keySet()) {
                        // empty all lists indicating all failed
                        emptyFavoriteArticle(list.getArticleMap().get(key));
                    }
                    return list;
                }

                boolean isChanged = false;
                for (String id : list.getArticleMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // article not exists in db, empty input article to indicate update failed for this item
                        emptyFavoriteArticle(list.getArticleMap().get(id));
                    } else {
                        // article exists in db, update
                        mapInDb.put(id, list.getArticleMap().get(id));
                        isChanged = true;
                    }
                }

                if (isChanged) {
                    mongoTemplate.save(listInDb, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
                }
                return list;
            } else {
                // not suppose to happen
                throw new ServerSideProblemException("Document found or input article list should not be null!");
            }
        }
    }

    @Override
    public FavoriteList deleteFavoriteArticle(FavoriteList list) throws Exception {

        // check input
        defensiveCheck(mongoTemplate, list);

        // check if the document exists
        Query searchQuery = new Query().addCriteria(Criteria.where("_id").is(list.getListId()));
        if (!mongoTemplate.exists(searchQuery, FavoriteList.class, MongoConfig.FAVORITE_LIST_COLLECTION_NAME)) {
            // document not exists, delete failed
            // throw resource not exist exception
            ResourceNotExistException e = new ResourceNotExistException("Favorite article list does not exist!");
            e.setErrorBody(new FavoriteListNotExistErrorBody(list.getListId()));
            throw e;
        } else {
            // document exists, perform delete
            FavoriteList listInDb = mongoTemplate.findOne(searchQuery, FavoriteList.class, MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
            if (listInDb != null && list.getArticleMap() != null) {
                Map<String, FavoriteArticle> mapInDb = listInDb.getArticleMap();
                if (mapInDb == null) {
                    // nothing in list, delete failed
                    for (String key : list.getArticleMap().keySet()) {
                        // empty all lists indicating all failed
                        emptyFavoriteArticle(list.getArticleMap().get(key));
                    }
                    return list;
                }

                boolean isChanged = false;
                for (String id : list.getArticleMap().keySet()) {
                    if (!mapInDb.containsKey(id)) {
                        // article not exists in db, empty input article to indicate delete failed for this item
                        emptyFavoriteArticle(list.getArticleMap().get(id));
                    } else {
                        // article exists in db, delete
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
                throw new ServerSideProblemException("Document found or input article list should not be null!");
            }
        }
    }

    private void emptyFavoriteArticle(FavoriteArticle article) {
        if (article == null) return;

        article
            .setArticleCategory(null)
            .setArticleTitle(null)
            .setArticleCreatedAt(null)
            .setAuthorCategory(null)
            .setAuthorId(null)
            .setAuthorName(null)
            .setFavoriteAt(null);
    }

    private void defensiveCheck(MongoTemplate template, FavoriteList list) throws Exception {

        // make sure mongo template is not null
        checkMongoTemplate(template);

        // make sure collection exists
        FavoriteListDbUtilities.checkCollection(template);

        // make sure object ,id and article map are not null
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
        } else if (list.getArticleMap() == null) {
            errorMsg = "FavoriteList should not have null article map!";
        }

        if (errorMsg != null) {
            throw new ServerSideProblemException(errorMsg);
        }
    }

}
