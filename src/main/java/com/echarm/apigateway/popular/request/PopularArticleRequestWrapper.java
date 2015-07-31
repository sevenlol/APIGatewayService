package com.echarm.apigateway.popular.request;

import com.echarm.apigateway.accountsystem.util.Category;
import com.echarm.apigateway.popular.model.PopularArticle;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class PopularArticleRequestWrapper {

    private PopularArticle article;

    public PopularArticleRequestWrapper() { this.article = new PopularArticle(); }

    /* getter methods */

    public PopularArticle getPopularArticle() { return article; }

    /* setter methods */

    @JsonProperty("article_title") public void setArticleTitle(String articleTitle) { article.setArticleTitle(articleTitle); }
    @JsonProperty("article_id") public void setArticleId(String articleId) { article.setArticleId(articleId); }
    @JsonProperty("article_category") public void setArticleCategory(String articleCategory) { article.setArticleCategory(articleCategory); }
    @JsonProperty("author_id") public void setAuthorId(String authorId) { article.setAuthorId(authorId); }
    @JsonProperty("author_name") public void setAuthorName(String authorName) { article.setAuthorName(authorName); }
    @JsonProperty("author_category")
    public void setAuthorCategory(String authorCategoryStr) {
        article.setAuthorCategory(Category.isCategoryExist(authorCategoryStr));
    }
}
