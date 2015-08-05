package com.echarm.apigateway.favorite.util;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.echarm.apigateway.config.MongoConfig;

public class FavoriteListDbUtilities {

    public static void checkCollection(MongoTemplate template) {
        if (template == null) return;

        if (!template.collectionExists(MongoConfig.FAVORITE_LIST_COLLECTION_NAME)) {
            template.createCollection(MongoConfig.FAVORITE_LIST_COLLECTION_NAME);
        }
    }

    public static String getFavoriteListId(String id) {
        if (id == null) return null;
        return String.format("%s_favorite_list", id);
    }
}
