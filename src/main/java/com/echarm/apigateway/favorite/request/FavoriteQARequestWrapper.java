package com.echarm.apigateway.favorite.request;

import com.echarm.apigateway.favorite.model.FavoriteQA;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class FavoriteQARequestWrapper {

    private FavoriteQA qa;

    public FavoriteQARequestWrapper() { this.qa = new FavoriteQA(); }

    /* getter methods */

    public FavoriteQA getFavoriteQA() { return qa; }

    /* setter methods */

    @JsonProperty("question_category") public void setQuestionCategory(String questionCategory) { qa.setQuestionCategory(questionCategory); }
    @JsonProperty("question_id") public void setQuestionId(String questionId) { qa.setQuestionId(questionId); }
    @JsonProperty("question_content") public void setQuestionContent(String questionContent) { qa.setQuestionContent(questionContent); }
    @JsonProperty("question_created_at") public void setQuestionCreatedAt(String questionCreatedAt) { qa.setQuestionCreatedAt(questionCreatedAt); }
    @JsonProperty("questioner_id") public void setQuestionerId(String questionerId) { qa.setQuestionerId(questionerId); }
    @JsonProperty("questioner_name") public void setQuestionerName(String questionerName) { qa.setQuestionerName(questionerName); }

    @JsonProperty("answer_id") public void setAnswerId(String answerId) { qa.setAnswerId(answerId); }
    @JsonProperty("answer_content") public void setAnswerContent(String answerContent) { qa.setAnswerContent(answerContent); }
    @JsonProperty("answer_created_at") public void setAnswerCreatedAt(String answerCreatedAt) { qa.setAnswerCreatedAt(answerCreatedAt); }
    @JsonProperty("answerer_category") public void setAnswererCategory(String answererCategory) { qa.setAnswererCategory(answererCategory); }
    @JsonProperty("answerer_id") public void setAnswererId(String answererId) { qa.setAnswererId(answererId); }
    @JsonProperty("answerer_name") public void setAnswererName(String answererName) { qa.setAnswererName(answererName); }

    @JsonProperty("favorite_at") public void setFavoriteAt(String favoriteAt) { qa.setFavoriteAt(favoriteAt); }
}
