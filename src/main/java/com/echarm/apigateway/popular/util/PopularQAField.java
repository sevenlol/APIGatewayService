package com.echarm.apigateway.popular.util;

public enum PopularQAField {

    QUESTION_CATEGORY(0), QUESTION_ID(1), QUESTION_CONTENT(2),
    QUESTIONER_ID(3), QUESTIONER_NAME(4), ANSWER_ID(5),
    ANSWER_CONTENT(6), ANSWERER_ID(7), ANSWERER_NAME(8);

    public final int id;

    private PopularQAField(int id) { this.id = id; }

    public static PopularQAField getPopularQAField(int id) {
        for (PopularQAField popularQAField : PopularQAField.values()) {
            if (popularQAField.id == id) {
                return popularQAField;
            }
        }
        return null;
    }
}
