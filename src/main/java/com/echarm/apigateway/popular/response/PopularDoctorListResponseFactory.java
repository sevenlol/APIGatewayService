package com.echarm.apigateway.popular.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.echarm.apigateway.popular.model.PopularDoctor;
import com.echarm.apigateway.popular.model.PopularDoctorList;

public class PopularDoctorListResponseFactory {

    public static IdStatusListResponse getIdStatusListResponse(PopularDoctorList list) {
        if (list == null || list.getDoctorMap() == null) return null;

        Map<String, Integer> idListMap = new HashMap<String, Integer>();
        for (String key : list.getDoctorMap().keySet()) {
            if (key == null) continue;

            PopularDoctor doctor = list.getDoctorMap().get(key);
            if (doctor == null) {
                continue;
            }

            Integer status = IdStatusListResponse.STATUS_SUCCESS;

            if (isDoctorEmpty(doctor)) {
                status = IdStatusListResponse.STATUS_FAIL;
            }

            idListMap.put(key, status);
        }
        return new IdStatusListResponse().setIdStatusMap(idListMap);
    }

    public static List<PopularDoctorResponseWrapper> getPopularDoctorListResponse(PopularDoctorList list) {
        if (list == null || list.getDoctorMap() == null) return null;

        List<PopularDoctorResponseWrapper> wrapperList = new ArrayList<PopularDoctorResponseWrapper>();
        for (String key : list.getDoctorMap().keySet()) {
            if (key == null) continue;

            PopularDoctor doctor = list.getDoctorMap().get(key);
            if (doctor == null) {
                continue;
            }

            wrapperList.add(new PopularDoctorResponseWrapper(doctor));
        }

        return wrapperList;
    }

    private static boolean isDoctorEmpty(PopularDoctor doctor) {
        if (doctor.getDoctorCategory() == null && doctor.getDoctorName() == null &&
            doctor.getDoctorStickerUrl() == null) {
            return true;
        }

        return false;
    }
}
