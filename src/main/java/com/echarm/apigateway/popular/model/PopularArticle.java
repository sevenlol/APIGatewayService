package com.echarm.apigateway.popular.model;

import com.echarm.apigateway.accountsystem.util.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PopularArticle {

    private String articleTitle;
    private String articleId;
    private String articleCategory;
    private String authorId;
    private String authorName;
    private Category authorCategory;

    public PopularArticle() {}

    /* getter methods */

    @JsonIgnore public String getArticleTitle() { return articleTitle; }
    @JsonIgnore public String getArticleId() { return articleId; }
    @JsonIgnore public String getArticleCategory() { return articleCategory; }
    @JsonIgnore public String getAuthorId() { return authorId; }
    @JsonIgnore public String getAuthorName() { return authorName; }
    @JsonIgnore public Category getAuthorCategory() { return authorCategory; }

    /* setter methods */

    @JsonIgnore
    public PopularArticle setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle; return this;
    }

    @JsonIgnore
    public PopularArticle setArticleId(String articleId) {
        this.articleId = articleId; return this;
    }

    @JsonIgnore
    public PopularArticle setArticleCategory(String articleCategory) {
        this.articleCategory = articleCategory; return this;
    }

    @JsonIgnore
    public PopularArticle setAuthorId(String authorId) {
        this.authorId = authorId; return this;
    }

    @JsonIgnore
    public PopularArticle setAuthorName(String authorName) {
        this.authorName = authorName; return this;
    }

    @JsonIgnore
    public PopularArticle setAuthorCategory(Category authorCategory) {
        this.authorCategory = authorCategory; return this;
    }
}
