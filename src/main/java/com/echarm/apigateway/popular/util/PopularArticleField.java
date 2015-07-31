package com.echarm.apigateway.popular.util;

public enum PopularArticleField {

    ARTICLE_TITLE(0), ARTICLE_ID(1), ARTICLE_CATEGORY(2),
    AUTHOR_ID(3), AUTHOR_NAME(4), AUTHOR_CATEGORY(5);

    public final int id;

    private PopularArticleField(int id) { this.id = id; }

    public static PopularArticleField getPopularArticleField(int id) {
        for (PopularArticleField popularArticleField : PopularArticleField.values()) {
            if (popularArticleField.id == id) {
                return popularArticleField;
            }
        }
        return null;
    }

}
