package com.echarm.apigateway.favorite.request;

import com.echarm.apigateway.favorite.model.FavoriteArticle;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class FavoriteArticleRequestWrapper {

    private FavoriteArticle article;

    public FavoriteArticleRequestWrapper() { this.article = new FavoriteArticle(); }

    /* getter methods */

    public FavoriteArticle getFavoriteArticle() { return article; }

    /* setter methods */

    @JsonProperty("article_title") public void setArticleTitle(String articleTitle) { article.setArticleTitle(articleTitle); }
    @JsonProperty("article_id") public void setArticleId(String articleId) { article.setArticleId(articleId); }
    @JsonProperty("article_category") public void setArticleCategory(String articleCategory) { article.setArticleCategory(articleCategory); }
    @JsonProperty("article_created_at") public void setArticleCreatedAt(String articleCreatedAt) { article.setArticleCreatedAt(articleCreatedAt); }
    @JsonProperty("author_category") public void setAuthorCategory(String authorCategory) { article.setAuthorCategory(authorCategory); }
    @JsonProperty("author_id") public void setAuthorId(String authorId) { article.setAuthorId(authorId); }
    @JsonProperty("author_name") public void setAuthorName(String authorName) { article.setAuthorName(authorName); }
    @JsonProperty("favorite_at") public void setFavoriteAt(String favoriteAt) { article.setFavoriteAt(favoriteAt); }
}
