package com.echarm.apigateway.favorite.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.echarm.apigateway.favorite.model.FavoriteList;
import com.echarm.apigateway.favorite.model.FavoriteQA;

public class FavoriteQAListResponseFactory {

    public static IdStatusListResponse getIdStatusListResponse(FavoriteList list) {
        if (list == null || list.getQAMap() == null) return null;

        Map<String, Integer> idListMap = new HashMap<String, Integer>();
        for (String key : list.getQAMap().keySet()) {
            if (key == null) continue;

            FavoriteQA qa = list.getQAMap().get(key);
            if (qa == null) {
                continue;
            }

            Integer status = IdStatusListResponse.STATUS_SUCCESS;

            if (isQAEmpty(qa)) {
                status = IdStatusListResponse.STATUS_FAIL;
            }

            idListMap.put(key, status);
        }
        return new IdStatusListResponse().setIdStatusMap(idListMap);
    }

    public static List<FavoriteQAResponseWrapper> getFavoriteQAListResponse(FavoriteList list) {
        if (list == null || list.getQAMap() == null) return null;

        List<FavoriteQAResponseWrapper> wrapperList = new ArrayList<FavoriteQAResponseWrapper>();
        for (String key : list.getQAMap().keySet()) {
            if (key == null) continue;

            FavoriteQA qa = list.getQAMap().get(key);
            if (qa == null) {
                continue;
            }

            wrapperList.add(new FavoriteQAResponseWrapper(qa));
        }

        return wrapperList;
    }

    private static boolean isQAEmpty(FavoriteQA qa) {
        if (qa.getAnswerContent() == null && qa.getAnswererId() == null &&
            qa.getAnswerCreatedAt() == null && qa.getQuestionCreatedAt() == null &&
            qa.getAnswererName() == null && qa.getAnswerId() == null &&
            qa.getQuestionCategory() == null && qa.getQuestionContent() == null &&
            qa.getQuestionerId() == null && qa.getQuestionerName() == null &&
            qa.getAnswererCategory() == null && qa.getFavoriteAt() == null) {
            return true;
        }

        return false;
    }
}
