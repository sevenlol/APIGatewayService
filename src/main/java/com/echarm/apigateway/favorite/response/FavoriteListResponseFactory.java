package com.echarm.apigateway.favorite.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.echarm.apigateway.favorite.model.FavoriteArticle;
import com.echarm.apigateway.favorite.model.FavoriteList;
import com.echarm.apigateway.favorite.model.FavoriteQA;

public class FavoriteListResponseFactory {

    public static FavoriteListResponseWrapper getFavoriteListRespones(FavoriteList articleList, FavoriteList qaList) {
        Map<String, FavoriteArticle> articleMap = articleList.getArticleMap();
        Map<String, FavoriteQA> qaMap = qaList.getQAMap();

        List<FavoriteArticleResponseWrapper> articleWrapperList = new ArrayList<FavoriteArticleResponseWrapper>();
        List<FavoriteQAResponseWrapper> qaWrapperList = new ArrayList<FavoriteQAResponseWrapper>();
        for (String key : articleMap.keySet()) {
            articleWrapperList.add(new FavoriteArticleResponseWrapper(articleMap.get(key)));
        }
        for (String key : qaMap.keySet()) {
            qaWrapperList.add(new FavoriteQAResponseWrapper(qaMap.get(key)));
        }
        return new FavoriteListResponseWrapper().setArticleList(articleWrapperList).setQAList(qaWrapperList);
    }
}
