package com.echarm.apigateway.favorite.response;

import com.echarm.apigateway.favorite.model.FavoriteArticle;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class FavoriteArticleResponseWrapper {

    private FavoriteArticle article;

    public FavoriteArticleResponseWrapper(FavoriteArticle article) { this.article = article; }

    /* getter methods */

    @JsonProperty("article_created_at")
    public String getArticleCreatedAt() {
        if (this.article != null) {
            return this.article.getArticleCreatedAt();
        }
        return null;
    }

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
    public String getAuthorCategory() {
        if (this.article != null) {
            return this.article.getAuthorCategory();
        }
        return null;
    }

    @JsonProperty("favorite_at")
    public String getFavoriteAt() {
        if (this.article != null) {
            return this.article.getFavoriteAt();
        }
        return null;
    }

}
