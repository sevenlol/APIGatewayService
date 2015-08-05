package com.echarm.apigateway.favorite.util;

import com.echarm.apigateway.util.ValidateRule;
import com.echarm.apigateway.util.ValidateType;

public class FavoriteQAValidatorFactory {

    public static FavoriteQAValidator getAllFieldNotNullValidator() {
        return new FavoriteQAValidatorBuilder()
                        .add(FavoriteQAField.QUESTION_CATEGORY, ValidateType.NON_NULL)
                        .add(FavoriteQAField.QUESTION_ID, ValidateType.NON_NULL)
                        .add(FavoriteQAField.QUESTION_CONTENT, ValidateType.NON_NULL)
                        .add(FavoriteQAField.QUESTION_CREATED_AT, ValidateType.NON_NULL)
                        .add(FavoriteQAField.QUESTIONER_ID, ValidateType.NON_NULL)
                        .add(FavoriteQAField.QUESTIONER_NAME, ValidateType.NON_NULL)
                        .add(FavoriteQAField.ANSWER_ID, ValidateType.NON_NULL)
                        .add(FavoriteQAField.ANSWER_CONTENT, ValidateType.NON_NULL)
                        .add(FavoriteQAField.ANSWER_CREATED_AT, ValidateType.NON_NULL)
                        .add(FavoriteQAField.ANSWERER_CATEGORY, ValidateType.NON_NULL)
                        .add(FavoriteQAField.ANSWERER_ID, ValidateType.NON_NULL)
                        .add(FavoriteQAField.ANSWERER_NAME, ValidateType.NON_NULL)
                        .add(FavoriteQAField.FAVORITE_AT, ValidateType.NON_NULL)
                        .getValidator(ValidateRule.ALL_PASS);
    }

    public static FavoriteQAValidator getAllFieldNonEmptyValidator() {
        return new FavoriteQAValidatorBuilder()
                        .add(FavoriteQAField.QUESTION_CATEGORY, ValidateType.NON_EMPTY)
                        .add(FavoriteQAField.QUESTION_ID, ValidateType.NON_EMPTY)
                        .add(FavoriteQAField.QUESTION_CONTENT, ValidateType.NON_EMPTY)
                        .add(FavoriteQAField.QUESTION_CREATED_AT, ValidateType.NON_EMPTY)
                        .add(FavoriteQAField.QUESTIONER_ID, ValidateType.NON_EMPTY)
                        .add(FavoriteQAField.QUESTIONER_NAME, ValidateType.NON_EMPTY)
                        .add(FavoriteQAField.ANSWER_ID, ValidateType.NON_EMPTY)
                        .add(FavoriteQAField.ANSWER_CONTENT, ValidateType.NON_EMPTY)
                        .add(FavoriteQAField.ANSWER_CREATED_AT, ValidateType.NON_EMPTY)
                        .add(FavoriteQAField.ANSWERER_CATEGORY, ValidateType.NON_EMPTY)
                        .add(FavoriteQAField.ANSWERER_ID, ValidateType.NON_EMPTY)
                        .add(FavoriteQAField.ANSWERER_NAME, ValidateType.NON_EMPTY)
                        .add(FavoriteQAField.FAVORITE_AT, ValidateType.NON_EMPTY)
                        .getValidator(ValidateRule.ALL_PASS);
    }
}
