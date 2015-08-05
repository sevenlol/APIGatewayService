package com.echarm.apigateway.popular.util;

import com.echarm.apigateway.util.ValidateRule;
import com.echarm.apigateway.util.ValidateType;

public class PopularQAValidatorFactory {

    public static PopularQAValidator getAllFieldNotNullValidator() {
        return new PopularQAValidatorBuilder()
                        .add(PopularQAField.QUESTION_CATEGORY, ValidateType.NON_NULL)
                        .add(PopularQAField.QUESTION_ID, ValidateType.NON_NULL)
                        .add(PopularQAField.QUESTION_CONTENT, ValidateType.NON_NULL)
                        .add(PopularQAField.QUESTIONER_ID, ValidateType.NON_NULL)
                        .add(PopularQAField.QUESTIONER_NAME, ValidateType.NON_NULL)
                        .add(PopularQAField.ANSWER_ID, ValidateType.NON_NULL)
                        .add(PopularQAField.ANSWER_CONTENT, ValidateType.NON_NULL)
                        .add(PopularQAField.ANSWERER_ID, ValidateType.NON_NULL)
                        .add(PopularQAField.ANSWERER_NAME, ValidateType.NON_NULL)
                        .getValidator(ValidateRule.ALL_PASS);
    }

    public static PopularQAValidator getAllFieldNonEmptyValidator() {
        return new PopularQAValidatorBuilder()
                        .add(PopularQAField.QUESTION_CATEGORY, ValidateType.NON_EMPTY)
                        .add(PopularQAField.QUESTION_ID, ValidateType.NON_EMPTY)
                        .add(PopularQAField.QUESTION_CONTENT, ValidateType.NON_EMPTY)
                        .add(PopularQAField.QUESTIONER_ID, ValidateType.NON_EMPTY)
                        .add(PopularQAField.QUESTIONER_NAME, ValidateType.NON_EMPTY)
                        .add(PopularQAField.ANSWER_ID, ValidateType.NON_EMPTY)
                        .add(PopularQAField.ANSWER_CONTENT, ValidateType.NON_EMPTY)
                        .add(PopularQAField.ANSWERER_ID, ValidateType.NON_EMPTY)
                        .add(PopularQAField.ANSWERER_NAME, ValidateType.NON_EMPTY)
                        .getValidator(ValidateRule.ALL_PASS);
    }
}
