package com.echarm.apigateway.favorite.response;

import com.echarm.apigateway.favorite.model.FavoriteQA;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class FavoriteQAResponseWrapper {

    private FavoriteQA qa;

    public FavoriteQAResponseWrapper(FavoriteQA qa) { this.qa = qa; }

    /* getter methods */

    @JsonProperty("question_category")
    public String getQuestionCategory() {
        if (this.qa != null) {
            return qa.getQuestionCategory();
        }
        return null;
    }

    @JsonProperty("question_id")
    public String getQuestionId() {
        if (this.qa != null) {
            return qa.getQuestionId();
        }
        return null;
    }

    @JsonProperty("question_content")
    public String getQuestionContent() {
        if (this.qa != null) {
            return qa.getQuestionContent();
        }
        return null;
    }

    @JsonProperty("question_created_at")
    public String getQuestionCreatedAt() {
        if (this.qa != null) {
            return qa.getQuestionCreatedAt();
        }
        return null;
    }

    @JsonProperty("questioner_id")
    public String getQuestionerId() {
        if (this.qa != null) {
            return qa.getQuestionerId();
        }
        return null;
    }

    @JsonProperty("questioner_name")
    public String getQuestionerName() {
        if (this.qa != null) {
            return qa.getQuestionerName();
        }
        return null;
    }

    @JsonProperty("answer_id")
    public String getAnswerId() {
        if (this.qa != null) {
            return qa.getAnswerId();
        }
        return null;
    }

    @JsonProperty("answer_content")
    public String getAnswerContent() {
        if (this.qa != null) {
            return qa.getAnswerContent();
        }
        return null;
    }

    @JsonProperty("answer_created_at")
    public String getAnswerCreatedAt() {
        if (this.qa != null) {
            return qa.getAnswerCreatedAt();
        }
        return null;
    }

    @JsonProperty("answerer_category")
    public String getAnswererCategory() {
        if (this.qa != null) {
            return qa.getAnswererCategory();
        }
        return null;
    }

    @JsonProperty("answerer_id")
    public String getAnswererId() {
        if (this.qa != null) {
            return qa.getAnswererId();
        }
        return null;
    }

    @JsonProperty("answerer_name")
    public String getAnswererName() {
        if (this.qa != null) {
            return qa.getAnswererName();
        }
        return null;
    }

    @JsonProperty("favorite_at")
    public String getFavoriteAt() {
        if (this.qa != null) {
            return qa.getFavoriteAt();
        }
        return null;
    }
}
