package com.echarm.apigateway.popular.util;

import java.util.Map;
import java.util.Set;

import com.echarm.apigateway.popular.model.PopularQuestionAndAnswer;
import com.echarm.apigateway.util.ValidateRule;
import com.echarm.apigateway.util.ValidateType;

public class PopularQAValidator {
    private ValidateRule rule;
    private Map<PopularQAField, ValidateType> map;
    private StringBuilder builder;

    public PopularQAValidator(ValidateRule rule, Map<PopularQAField, ValidateType> map) {
        this.rule = rule;
        this.map = map;
        builder = new StringBuilder();
    }

    public boolean validate(PopularQuestionAndAnswer qa) {
        if (qa == null) return false;
        if (rule == null || map == null) return false;
        if (map.size() == 0) return true;

        // clear string builder
        builder.setLength(0);
        builder.append("Rule: " + rule);

        Set<PopularQAField> fieldSet = map.keySet();
        for (PopularQAField field : fieldSet) {
            boolean isFieldValidated = validateField(qa, field, map.get(field));

            if (rule == ValidateRule.ALL_PASS && !isFieldValidated) {
                builder.append(" => Failed!\nField: " + field + ", Type: " + map.get(field) + "\n");
                return false;
            } else if (rule == ValidateRule.NOT_ALL_FAIL && isFieldValidated) {
                return true;
            }
        }

        if (rule == ValidateRule.ALL_PASS)  return true;
        else if (rule == ValidateRule.NOT_ALL_FAIL) {
            builder.append(" => Failed!\n");
            return false;
        }

        return false;
    }

    public String getValidateStr() {
        if (builder != null)
            return builder.toString();
        else
            return null;
    }

    private boolean validateField(PopularQuestionAndAnswer qa, PopularQAField field, ValidateType type) {
        if (qa == null || field == null || type == null) return false;

        switch(field) {
            case QUESTION_CATEGORY:
                return validate(qa.getQuestionCategory(), type);
            case QUESTION_ID:
                return validate(qa.getQuestionId(), type);
            case QUESTION_CONTENT:
                return validate(qa.getQuestionContent(), type);
            case QUESTIONER_ID:
                return validate(qa.getQuestionerId(), type);
            case QUESTIONER_NAME:
                return validate(qa.getQuestionerName(), type);
            case ANSWER_ID:
                return validate(qa.getAnswerId(), type);
            case ANSWER_CONTENT:
                return validate(qa.getAnswerContent(), type);
            case ANSWERER_ID:
                return validate(qa.getAnswererId(), type);
            case ANSWERER_NAME:
                return validate(qa.getAnswererName(), type);
            default:
        }

        return false;
    }

    private boolean validate(Object obj, ValidateType type) {
        if (type == null) return false;

        switch(type) {
            case NA:
                return true;
            case NON_EMPTY:
                // allow obj to be null
                if (obj instanceof String){
                    return !"".equals(obj);
                } else {
                    // this type only works on string
                    return true;
                }
            case NON_NULL:
                return obj != null;
            case NON_NULL_AND_EMPTY:
                if (obj instanceof String) {
                    return obj != null && !"".equals(obj);
                } else {
                    return obj != null;
                }
            default:
        }

        return false;
    }
}
