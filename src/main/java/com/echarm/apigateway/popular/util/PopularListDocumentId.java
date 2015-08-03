package com.echarm.apigateway.popular.util;

public class PopularListDocumentId {

    public static String getPopularArticleListId(String category) {
        if (category == null) return null;
        return String.format("%s_popular_article", category);
    }

    public static String getPopularDoctorListId(String category) {
        if (category == null) return null;
        return String.format("%s_popular_doctor", category);
    }

    public static String getPopularQAListId(String category) {
        if (category == null) return null;
        return String.format("%s_popular_qa", category);
    }
}
