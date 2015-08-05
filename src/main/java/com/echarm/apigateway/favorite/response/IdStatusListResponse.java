package com.echarm.apigateway.favorite.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class IdStatusListResponse {

    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_FAIL = -1;

    private Map<String, Integer> idStatusMap;

    public IdStatusListResponse() {}

    /* getter methods */

    @JsonProperty("status_list") public Map<String, Integer> getIdStatusMap() { return idStatusMap; }

    /* setter methods */

    public IdStatusListResponse setIdStatusMap(Map<String, Integer> idStatusMap) {
        this.idStatusMap = idStatusMap; return this;
    }
}
