package com.echarm.apigateway.favorite.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.echarm.apigateway.favorite.model.FavoriteArticle;
import com.echarm.apigateway.favorite.model.FavoriteList;
import com.echarm.apigateway.favorite.model.FavoriteQA;

public class FavoriteListResponseFactory {

    public static FavoriteListResponseWrapper getFavoriteListRespones(FavoriteList articleList, FavoriteList qaList) {



        List<FavoriteArticleResponseWrapper> articleWrapperList = null;
        List<FavoriteQAResponseWrapper> qaWrapperList = null;

        if (articleList != null) {
            articleWrapperList = new ArrayList<FavoriteArticleResponseWrapper>();
            Map<String, FavoriteArticle> articleMap = articleList.getArticleMap();
            for (String key : articleMap.keySet()) {
                articleWrapperList.add(new FavoriteArticleResponseWrapper(articleMap.get(key)));
            }
        }

        if (qaList != null) {
            qaWrapperList = new ArrayList<FavoriteQAResponseWrapper>();
            Map<String, FavoriteQA> qaMap = qaList.getQAMap();
            for (String key : qaMap.keySet()) {
                qaWrapperList.add(new FavoriteQAResponseWrapper(qaMap.get(key)));
            }
        }

        return new FavoriteListResponseWrapper().setArticleList(articleWrapperList).setQAList(qaWrapperList);
    }
}
