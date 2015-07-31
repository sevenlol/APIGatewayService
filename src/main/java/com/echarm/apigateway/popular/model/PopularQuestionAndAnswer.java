package com.echarm.apigateway.popular.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
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

    @JsonIgnore public String getQuestionCategory() { return questionCategory; }
    @JsonIgnore public String getQuestionId() { return questionId; }
    @JsonIgnore public String getQuestionContent() { return questionContent; }
    @JsonIgnore public String getQuestionerId() { return questionerId; }
    @JsonIgnore public String getQuestionerName() { return questionerName; }

    @JsonIgnore public String getAnswerId() { return answerId; }
    @JsonIgnore public String getAnswerContent() { return answerContent; }
    @JsonIgnore public String getAnswererId() { return answererId; }
    @JsonIgnore public String getAnswererName() { return answererName; }

    /* setter methods */

    @JsonIgnore
    public PopularQuestionAndAnswer setQuestionCategory(String questionCategory) {
        this.questionCategory = questionCategory; return this;
    }

    @JsonIgnore
    public PopularQuestionAndAnswer setQuestionId(String questionId) {
        this.questionId = questionId; return this;
    }

    @JsonIgnore
    public PopularQuestionAndAnswer setQuestionContent(String questionContent) {
        this.questionContent = questionContent; return this;
    }

    @JsonIgnore
    public PopularQuestionAndAnswer setQuestionerId(String questionerId) {
        this.questionerId = questionerId; return this;
    }

    @JsonIgnore
    public PopularQuestionAndAnswer setQuestionerName(String questionerName) {
        this.questionerName = questionerName; return this;
    }

    @JsonIgnore
    public PopularQuestionAndAnswer setAnswerId(String answerId) {
        this.answerId = answerId; return this;
    }

    @JsonIgnore
    public PopularQuestionAndAnswer setAnswerContent(String answerContent) {
        this.answerContent = answerContent; return this;
    }

    @JsonIgnore
    public PopularQuestionAndAnswer setAnswererId(String answererId) {
        this.answererId = answererId; return this;
    }

    @JsonIgnore
    public PopularQuestionAndAnswer setAnswererName(String answererName) {
        this.answererName = answererName; return this;
    }
}
