package com.echarm.apigateway.favorite.model;

public class FavoriteArticle {

    private String articleCategory;
    private String articleId;
    private String articleTitle;
    private String articleCreatedAt;
    private String authorCategory;
    private String authorId;
    private String authorName;
    private String favoriteAt;

    public FavoriteArticle() {}

    /* getter methods */

    public String getArticleCategory() { return articleCategory; }
    public String getArticleId() { return articleId; }
    public String getArticleTitle() { return articleTitle; }
    public String getArticleCreatedAt() { return articleCreatedAt; }
    public String getAuthorCategory() { return authorCategory; }
    public String getAuthorId() { return authorId; }
    public String getAuthorName() { return authorName; }
    public String getFavoriteAt() { return favoriteAt; }

    /* setter methods */

    public FavoriteArticle setArticleCategory(String articleCategory) {
        this.articleCategory = articleCategory; return this;
    }

    public FavoriteArticle setArticleId(String articleId) {
        this.articleId = articleId; return this;
    }

    public FavoriteArticle setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle; return this;
    }

    public FavoriteArticle setArticleCreatedAt(String articleCreatedAt) {
        this.articleCreatedAt = articleCreatedAt; return this;
    }

    public FavoriteArticle setAuthorCategory(String authorCategory) {
        this.authorCategory = authorCategory; return this;
    }

    public FavoriteArticle setAuthorId(String authorId) {
        this.authorId = authorId; return this;
    }

    public FavoriteArticle setAuthorName(String authorName) {
        this.authorName = authorName; return this;
    }

    public FavoriteArticle setFavoriteAt(String favoriteAt) {
        this.favoriteAt = favoriteAt; return this;
    }
}
