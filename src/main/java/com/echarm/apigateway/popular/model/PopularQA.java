package com.echarm.apigateway.popular.model;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PopularQA {

    /* question fields */
    @Field("question_category") private String questionCategory;
    @Field("question_id") private String questionId;
    @Field("question_content") private String questionContent;
    @Field("questioner_id") private String questionerId;
    @Field("questioner_name") private String questionerName;

    /* answer fields */
    @Field("answer_id") private String answerId;
    @Field("answer_content") private String answerContent;
    @Field("answerer_id") private String answererId;
    @Field("answerer_name") private String answererName;

    public PopularQA() {}

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
    public PopularQA setQuestionCategory(String questionCategory) {
        this.questionCategory = questionCategory; return this;
    }

    @JsonIgnore
    public PopularQA setQuestionId(String questionId) {
        this.questionId = questionId; return this;
    }

    @JsonIgnore
    public PopularQA setQuestionContent(String questionContent) {
        this.questionContent = questionContent; return this;
    }

    @JsonIgnore
    public PopularQA setQuestionerId(String questionerId) {
        this.questionerId = questionerId; return this;
    }

    @JsonIgnore
    public PopularQA setQuestionerName(String questionerName) {
        this.questionerName = questionerName; return this;
    }

    @JsonIgnore
    public PopularQA setAnswerId(String answerId) {
        this.answerId = answerId; return this;
    }

    @JsonIgnore
    public PopularQA setAnswerContent(String answerContent) {
        this.answerContent = answerContent; return this;
    }

    @JsonIgnore
    public PopularQA setAnswererId(String answererId) {
        this.answererId = answererId; return this;
    }

    @JsonIgnore
    public PopularQA setAnswererName(String answererName) {
        this.answererName = answererName; return this;
    }
}
