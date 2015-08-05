package com.echarm.apigateway.favorite.model;

public class FavoriteQA {

    private String questionCategory;
    private String questionId;
    private String questionContent;
    private String questionCreatedAt;
    private String answerId;
    private String answerContent;
    private String answerCreatedAt;
    private String questionerId;
    private String questionerName;
    private String answererCategory;
    private String answererId;
    private String answererName;
    private String favoriteAt;

    public FavoriteQA() {}

    /* getter methods */

    public String getQuestionCategory() { return questionCategory; }
    public String getQuestionId() { return questionId; }
    public String getQuestionContent() { return questionContent; }
    public String getQuestionCreatedAt() { return questionCreatedAt; }
    public String getAnswerId() { return answerId; }
    public String getAnswerContent() { return answerContent; }
    public String getAnswerCreatedAt() { return answerCreatedAt; }
    public String getQuestionerId() { return questionerId; }
    public String getQuestionerName() { return questionerName; }
    public String getAnswererCategory() { return answererCategory; }
    public String getAnswererId() { return answererId; }
    public String getAnswererName() { return answererName; }
    public String getFavoriteAt() { return favoriteAt; }

    /* setter methods */

    public FavoriteQA setQuestionCategory(String questionCategory) {
        this.questionCategory = questionCategory; return this;
    }

    public FavoriteQA setQuestionId(String questionId) {
        this.questionId = questionId; return this;
    }

    public FavoriteQA setQuestionContent(String questionContent) {
        this.questionContent = questionContent; return this;
    }

    public FavoriteQA setQuestionCreatedAt(String questionCreatedAt) {
        this.questionCreatedAt = questionCreatedAt; return this;
    }

    public FavoriteQA setAnswerId(String answerId) {
        this.answererId = answerId; return this;
    }

    public FavoriteQA setAnswerContent(String answerContent) {
        this.answerContent = answerContent; return this;
    }

    public FavoriteQA setQuestionerId(String questionerId) {
        this.questionerId = questionerId; return this;
    }

    public FavoriteQA setQuestionerName(String questionerName) {
        this.questionerName = questionerName; return this;
    }

    public FavoriteQA setAnswererCategory(String answererCategory) {
        this.answererCategory = answererCategory; return this;
    }
}
