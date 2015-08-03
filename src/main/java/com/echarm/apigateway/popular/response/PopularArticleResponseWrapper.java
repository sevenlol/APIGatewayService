package com.echarm.apigateway.popular.response;

import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.popular.model.PopularArticle;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class PopularArticleResponseWrapper {

    private PopularArticle article;

    public PopularArticleResponseWrapper(PopularArticle article) { this.article = article; }

    /* getter methods */

    @JsonProperty("article_title")
    public String getArticleTitle() {
        if (this.article != null) {
            return this.article.getArticleTitle();
        }
        return null;
    }

    @JsonProperty("article_id")
    public String getArticleId() {
        if (this.article != null) {
            return this.article.getArticleId();
        }
        return null;
    }

    @JsonProperty("article_category")
    public String getArticleCategory() {
        if (this.article != null) {
            return this.article.getArticleCategory();
        }
        return null;
    }

    @JsonProperty("author_id")
    public String getAuthorId() {
        if (this.article != null) {
            return this.article.getAuthorId();
        }
        return null;
    }

    @JsonProperty("author_name")
    public String getAuthorName() {
        if (this.article != null) {
            return this.article.getAuthorName();
        }
        return null;
    }

    @JsonProperty("author_category")
    public Category getAuthorCategory() {
        if (this.article != null) {
            return this.article.getAuthorCategory();
        }
        return null;
    }

}
