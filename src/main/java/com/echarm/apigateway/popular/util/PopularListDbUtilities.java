package com.echarm.apigateway.popular.util;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.echarm.apigateway.config.MongoConfig;

public class PopularListDbUtilities {

    public static void checkCollection(MongoTemplate template) {
        if (template == null) return;

        if (!template.collectionExists(MongoConfig.POPULAR_LIST_COLLECTION_NAME)) {
            template.createCollection(MongoConfig.POPULAR_LIST_COLLECTION_NAME);
        }
    }
}
