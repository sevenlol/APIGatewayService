package com.echarm.apigateway.popular.model;

import com.echarm.apigateway.accountsystem.util.Category;

public class PopularArticle {

    private String articleTitle;
    private String articleId;
    private String articleCategory;
    private String authorId;
    private String authorName;
    private Category authorCategory;

    public PopularArticle() {}

    /* getter methods */

    public String getArticleTitle() { return articleTitle; }
    public String getArticleId() { return articleId; }
    public String getArticleCategory() { return articleCategory; }
    public String getAuthorId() { return authorId; }
    public String getAuthorName() { return authorName; }
    public Category getAuthorCategory() { return authorCategory; }

    /* setter methods */

    public PopularArticle setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle; return this;
    }

    public PopularArticle setArticleId(String articleId) {
        this.articleId = articleId; return this;
    }

    public PopularArticle setArticleCategory(String articleCategory) {
        this.articleCategory = articleCategory; return this;
    }

    public PopularArticle setAuthorId(String authorId) {
        this.authorId = authorId; return this;
    }

    public PopularArticle setAuthorName(String authorName) {
        this.authorName = authorName; return this;
    }

    public PopularArticle setAuthorCategory(Category authorCategory) {
        this.authorCategory = authorCategory; return this;
    }
}
