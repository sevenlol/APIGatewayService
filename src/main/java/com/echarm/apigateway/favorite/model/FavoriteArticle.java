package com.echarm.apigateway.favorite.model;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class FavoriteArticle {

    @Field("article_category") private String articleCategory;
    @Field("article_id") private String articleId;
    @Field("article_title") private String articleTitle;
    @Field("article_created_at") private String articleCreatedAt;
    @Field("author_category") private String authorCategory;
    @Field("author_id") private String authorId;
    @Field("author_name") private String authorName;
    @Field("favorite_at") private String favoriteAt;

    public FavoriteArticle() {}

    /* getter methods */

    @JsonIgnore public String getArticleCategory() { return articleCategory; }
    @JsonIgnore public String getArticleId() { return articleId; }
    @JsonIgnore public String getArticleTitle() { return articleTitle; }
    @JsonIgnore public String getArticleCreatedAt() { return articleCreatedAt; }
    @JsonIgnore public String getAuthorCategory() { return authorCategory; }
    @JsonIgnore public String getAuthorId() { return authorId; }
    @JsonIgnore public String getAuthorName() { return authorName; }
    @JsonIgnore public String getFavoriteAt() { return favoriteAt; }

    /* setter methods */

    @JsonIgnore
    public FavoriteArticle setArticleCategory(String articleCategory) {
        this.articleCategory = articleCategory; return this;
    }

    @JsonIgnore
    public FavoriteArticle setArticleId(String articleId) {
        this.articleId = articleId; return this;
    }

    @JsonIgnore
    public FavoriteArticle setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle; return this;
    }

    @JsonIgnore
    public FavoriteArticle setArticleCreatedAt(String articleCreatedAt) {
        this.articleCreatedAt = articleCreatedAt; return this;
    }

    @JsonIgnore
    public FavoriteArticle setAuthorCategory(String authorCategory) {
        this.authorCategory = authorCategory; return this;
    }

    @JsonIgnore
    public FavoriteArticle setAuthorId(String authorId) {
        this.authorId = authorId; return this;
    }

    @JsonIgnore
    public FavoriteArticle setAuthorName(String authorName) {
        this.authorName = authorName; return this;
    }

    @JsonIgnore
    public FavoriteArticle setFavoriteAt(String favoriteAt) {
        this.favoriteAt = favoriteAt; return this;
    }
}
