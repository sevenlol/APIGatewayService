package com.echarm.apigateway.favorite.util;

public enum FavoriteArticleField {

    ARTICLE_TITLE(0), ARTICLE_ID(1), ARTICLE_CATEGORY(2), ARTICLE_CREATED_AT(3),
    AUTHOR_ID(4), AUTHOR_NAME(5), AUTHOR_CATEGORY(6), FAVORITE_AT(7);

    public final int id;

    private FavoriteArticleField(int id) { this.id = id; }

    public static FavoriteArticleField getfavoriteArticleField(int id) {
        for (FavoriteArticleField favoriteArticleField : FavoriteArticleField.values()) {
            if (favoriteArticleField.id == id) {
                return favoriteArticleField;
            }
        }
        return null;
    }
}
