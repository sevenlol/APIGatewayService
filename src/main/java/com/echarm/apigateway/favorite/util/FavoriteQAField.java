package com.echarm.apigateway.favorite.util;

public enum FavoriteQAField {

    QUESTION_CATEGORY(0), QUESTION_ID(1), QUESTION_CONTENT(2), QUESTION_CREATED_AT(3),
    ANSWER_ID(4), ANSWER_CONTENT(5), ANSWER_CREATED_AT(6),
    QUESTIONER_ID(7), QUESTIONER_NAME(8),
    ANSWERER_CATEGORY(9), ANSWERER_ID(10), ANSWERER_NAME(11),
    FAVORITE_AT(12);

    public final int id;

    private FavoriteQAField(int id) { this.id = id; }

    public static FavoriteQAField getfavoriteQAField(int id) {
        for (FavoriteQAField favoriteQAField : FavoriteQAField.values()) {
            if (favoriteQAField.id == id) {
                return favoriteQAField;
            }
        }
        return null;
    }
}
