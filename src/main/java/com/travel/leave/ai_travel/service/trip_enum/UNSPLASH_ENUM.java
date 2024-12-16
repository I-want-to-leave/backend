package com.travel.leave.ai_travel.service.trip_enum;

import lombok.Getter;

@Getter
public enum UNSPLASH_ENUM {

    UNSPLASH_PATH("/search/photos"),
    QUERY_PARAM_QUERY("query"),
    QUERY_PARAM_CLIENT_ID("client_id"),
    QUERY_PARAM_PER_PAGE("per_page"),
    QUERY_PARAM_PAGE("page"),
    QUERY_RESULT("results"),
    QUERY_URLS("urls"),
    QUERY_REGULAR("regular");

    private final String field;

    UNSPLASH_ENUM(String field) {
        this.field = field;
    }
}
