package com.echarm.apigateway.favorite.model;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class FavoriteQA {

    @Field("question_category") private String questionCategory;
    @Field("question_id") private String questionId;
    @Field("question_content") private String questionContent;
    @Field("question_created_at") private String questionCreatedAt;
    @Field("answer_id") private String answerId;
    @Field("answer_content") private String answerContent;
    @Field("answer_created_at") private String answerCreatedAt;
    @Field("questioner_id") private String questionerId;
    @Field("questioner_name") private String questionerName;
    @Field("answerer_category") private String answererCategory;
    @Field("answerer_id") private String answererId;
    @Field("answerer_name") private String answererName;
    @Field("favorite_at") private String favoriteAt;

    public FavoriteQA() {}

    /* getter methods */

    @JsonIgnore public String getQuestionCategory() { return questionCategory; }
    @JsonIgnore public String getQuestionId() { return questionId; }
    @JsonIgnore public String getQuestionContent() { return questionContent; }
    @JsonIgnore public String getQuestionCreatedAt() { return questionCreatedAt; }
    @JsonIgnore public String getAnswerId() { return answerId; }
    @JsonIgnore public String getAnswerContent() { return answerContent; }
    @JsonIgnore public String getAnswerCreatedAt() { return answerCreatedAt; }
    @JsonIgnore public String getQuestionerId() { return questionerId; }
    @JsonIgnore public String getQuestionerName() { return questionerName; }
    @JsonIgnore public String getAnswererCategory() { return answererCategory; }
    @JsonIgnore public String getAnswererId() { return answererId; }
    @JsonIgnore public String getAnswererName() { return answererName; }
    @JsonIgnore public String getFavoriteAt() { return favoriteAt; }

    /* setter methods */

    @JsonIgnore
    public FavoriteQA setQuestionCategory(String questionCategory) {
        this.questionCategory = questionCategory; return this;
    }

    @JsonIgnore
    public FavoriteQA setQuestionId(String questionId) {
        this.questionId = questionId; return this;
    }

    @JsonIgnore
    public FavoriteQA setQuestionContent(String questionContent) {
        this.questionContent = questionContent; return this;
    }

    @JsonIgnore
    public FavoriteQA setQuestionCreatedAt(String questionCreatedAt) {
        this.questionCreatedAt = questionCreatedAt; return this;
    }

    @JsonIgnore
    public FavoriteQA setAnswerId(String answerId) {
        this.answererId = answerId; return this;
    }

    @JsonIgnore
    public FavoriteQA setAnswerContent(String answerContent) {
        this.answerContent = answerContent; return this;
    }

    @JsonIgnore
    public FavoriteQA setAnswerCreatedAt(String answerCreatedAt) {
        this.answerCreatedAt = answerCreatedAt; return this;
    }

    @JsonIgnore
    public FavoriteQA setQuestionerId(String questionerId) {
        this.questionerId = questionerId; return this;
    }

    @JsonIgnore
    public FavoriteQA setQuestionerName(String questionerName) {
        this.questionerName = questionerName; return this;
    }

    @JsonIgnore
    public FavoriteQA setAnswererCategory(String answererCategory) {
        this.answererCategory = answererCategory; return this;
    }

    @JsonIgnore
    public FavoriteQA setAnswererId(String answerId) {
        this.answerId = answerId; return this;
    }

    @JsonIgnore
    public FavoriteQA setAnswererName(String answererName) {
        this.answererName = answererName; return this;
    }

    @JsonIgnore
    public FavoriteQA setFavoriteAt(String favoriteAt) {
        this.favoriteAt = favoriteAt; return this;
    }
}
