package com.echarm.apigateway.config;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration
public class MongoConfig {

    public static final String MONGO_DB_HOST = "localhost";
    public static final int MONGO_DB_PORT = 27017;
    public static final String WEB_SERVICE_DB_NAME = "web_service_db";

    public static final String POPULAR_LIST_COLLECTION_NAME = "popular_list";

    @Bean
    public MongoDbFactory webServiceMongoFactory() throws UnknownHostException {
        return new SimpleMongoDbFactory(new MongoClient(MONGO_DB_HOST, MONGO_DB_PORT), WEB_SERVICE_DB_NAME);
    }

    @Bean(name = "mongo_web_service")
    public MongoTemplate webServiceMongoTemplate() throws Exception {
        return new MongoTemplate(webServiceMongoFactory());
    }
}
