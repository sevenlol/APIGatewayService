package com.echarm.apigateway.popular.model;

public class PopularQuestionAndAnswer {

    /* question fields */
    private String questionCategory;
    private String questionId;
    private String questionContent;
    private String questionerId;
    private String questionerName;

    /* answer fields */
    private String answerId;
    private String answerContent;
    private String answererId;
    private String answererName;

    public PopularQuestionAndAnswer() {}

    /* getter methods */

    public String getQuestionCategory() { return questionCategory; }
    public String getQuestionId() { return questionId; }
    public String getQuestionContent() { return questionContent; }
    public String getQuestionerId() { return questionerId; }
    public String getQuestionerName() { return questionerName; }

    public String getAnswerId() { return answerId; }
    public String getAnswerContent() { return answerContent; }
    public String getAnswererId() { return answererId; }
    public String getAnswererName() { return answererName; }

    /* setter methods */

    public PopularQuestionAndAnswer setQuestionCategory(String questionCategory) {
        this.questionCategory = questionCategory; return this;
    }

    public PopularQuestionAndAnswer setQuestionId(String questionId) {
        this.questionId = questionId; return this;
    }

    public PopularQuestionAndAnswer setQuestionContent(String questionContent) {
        this.questionContent = questionContent; return this;
    }

    public PopularQuestionAndAnswer setQuestionerId(String questionerId) {
        this.questionerId = questionerId; return this;
    }

    public PopularQuestionAndAnswer setQuestionerName(String questionerName) {
        this.questionerName = questionerName; return this;
    }

    public PopularQuestionAndAnswer setAnswerId(String answerId) {
        this.answerId = answerId; return this;
    }

    public PopularQuestionAndAnswer setAnswerContent(String answerContent) {
        this.answerContent = answerContent; return this;
    }

    public PopularQuestionAndAnswer setAnswererId(String answererId) {
        this.answererId = answererId; return this;
    }

    public PopularQuestionAndAnswer setAnswererName(String answererName) {
        this.answererName = answererName; return this;
    }
}
