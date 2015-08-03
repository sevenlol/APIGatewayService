package com.echarm.apigateway.popular.request;

import com.echarm.apigateway.popular.model.PopularQA;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class PopularQARequestWrapper {

    private PopularQA qa;

    public PopularQARequestWrapper() { this.qa = new PopularQA(); }

    /* getter methods */

    public PopularQA getPopularQA() { return qa; }

    /* setter methods */

    @JsonProperty("question_category") public void setQuestionCategory(String questionCategory) { qa.setQuestionCategory(questionCategory); }
    @JsonProperty("question_id") public void setQuestionId(String questionId) { qa.setQuestionId(questionId); }
    @JsonProperty("question_content") public void setQuestionContent(String questionContent) { qa.setQuestionContent(questionContent); }
    @JsonProperty("questioner_id") public void setQuestionerId(String questionerId) { qa.setQuestionerId(questionerId); }
    @JsonProperty("questioner_name") public void setQuestionerName(String questionerName) { qa.setQuestionerName(questionerName); }

    @JsonProperty("answer_id") public void setAnswerId(String answerId) { qa.setAnswerId(answerId); }
    @JsonProperty("answer_content") public void setAnswerContent(String answerContent) { qa.setAnswerContent(answerContent); }
    @JsonProperty("answerer_id") public void setAnswererId(String answererId) { qa.setAnswererId(answererId); }
    @JsonProperty("answerer_name") public void setAnswererName(String answererName) { qa.setAnswererName(answererName); }
}
