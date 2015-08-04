package com.echarm.apigateway.popular.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.echarm.apigateway.popular.model.PopularArticle;
import com.echarm.apigateway.popular.model.PopularArticleList;

public class PopularArticleListResponseFactory {

    public static IdStatusListResponse getIdStatusListResponse(PopularArticleList list) {
        if (list == null || list.getArticleMap() == null) return null;

        Map<String, Integer> idListMap = new HashMap<String, Integer>();
        for (String key : list.getArticleMap().keySet()) {
            if (key == null) continue;

            PopularArticle article = list.getArticleMap().get(key);
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

    public static List<PopularArticleResponseWrapper> getPopularArticleListResponse(PopularArticleList list) {
        if (list == null || list.getArticleMap() == null) return null;

        List<PopularArticleResponseWrapper> wrapperList = new ArrayList<PopularArticleResponseWrapper>();
        for (String key : list.getArticleMap().keySet()) {
            if (key == null) continue;

            PopularArticle article = list.getArticleMap().get(key);
            if (article == null) {
                continue;
            }

            wrapperList.add(new PopularArticleResponseWrapper(article));
        }

        return wrapperList;
    }

    private static boolean isArticleEmpty(PopularArticle article) {
        if (article.getArticleCategory() == null && article.getArticleTitle() == null &&
            article.getAuthorCategory() == null && article.getAuthorId() == null && article.getAuthorName() == null) {
            return true;
        }

        return false;
    }
}
