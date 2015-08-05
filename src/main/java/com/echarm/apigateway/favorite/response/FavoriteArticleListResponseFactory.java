package com.echarm.apigateway.favorite.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.echarm.apigateway.favorite.model.FavoriteArticle;
import com.echarm.apigateway.favorite.model.FavoriteList;

public class FavoriteArticleListResponseFactory {

    public static IdStatusListResponse getIdStatusListResponse(FavoriteList list) {
        if (list == null || list.getArticleMap() == null) return null;

        Map<String, Integer> idListMap = new HashMap<String, Integer>();
        for (String key : list.getArticleMap().keySet()) {
            if (key == null) continue;

            FavoriteArticle article = list.getArticleMap().get(key);
            if (article == null) {
                continue;
            }

            Integer status = IdStatusListResponse.STATUS_SUCCESS;

            if (isArticleEmpty(article)) {
                status = IdStatusListResponse.STATUS_FAIL;
            }

            idListMap.put(key, status);
        }
        return new IdStatusListResponse().setIdStatusMap(idListMap);
    }

    public static List<FavoriteArticleResponseWrapper> getFavoriteArticleListResponse(FavoriteList list) {
        if (list == null || list.getArticleMap() == null) return null;

        List<FavoriteArticleResponseWrapper> wrapperList = new ArrayList<FavoriteArticleResponseWrapper>();
        for (String key : list.getArticleMap().keySet()) {
            if (key == null) continue;

            FavoriteArticle article = list.getArticleMap().get(key);
            if (article == null) {
                continue;
            }

            wrapperList.add(new FavoriteArticleResponseWrapper(article));
        }

        return wrapperList;
    }

    private static boolean isArticleEmpty(FavoriteArticle article) {
        if (article.getArticleCategory() == null && article.getArticleTitle() == null && article.getArticleCreatedAt() == null &&
            article.getAuthorCategory() == null && article.getAuthorId() == null && article.getAuthorName() == null &&
            article.getFavoriteAt() == null) {
            return true;
        }

        return false;
    }
}
