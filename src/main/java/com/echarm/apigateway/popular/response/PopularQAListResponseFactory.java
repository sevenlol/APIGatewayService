package com.echarm.apigateway.popular.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.echarm.apigateway.popular.model.PopularQA;
import com.echarm.apigateway.popular.model.PopularQAList;

public class PopularQAListResponseFactory {

    public static IdStatusListResponse getIdStatusListResponse(PopularQAList list) {
        if (list == null || list.getQaMap() == null) return null;

        Map<String, Integer> idListMap = new HashMap<String, Integer>();
        for (String key : list.getQaMap().keySet()) {
            if (key == null) continue;

            PopularQA qa = list.getQaMap().get(key);
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

    public static List<PopularQAResponseWrapper> getPopularQAListResponse(PopularQAList list) {
        if (list == null || list.getQaMap() == null) return null;

        List<PopularQAResponseWrapper> wrapperList = new ArrayList<PopularQAResponseWrapper>();
        for (String key : list.getQaMap().keySet()) {
            if (key == null) continue;

            PopularQA qa = list.getQaMap().get(key);
            if (qa == null) {
                continue;
            }

            wrapperList.add(new PopularQAResponseWrapper(qa));
        }

        return wrapperList;
    }

    private static boolean isQAEmpty(PopularQA qa) {
        if (qa.getAnswerContent() == null && qa.getAnswererId() == null &&
            qa.getAnswererName() == null && qa.getAnswerId() == null &&
            qa.getQuestionCategory() == null && qa.getQuestionContent() == null &&
            qa.getQuestionerId() == null && qa.getQuestionerName() == null) {
            return true;
        }

        return false;
    }
}
