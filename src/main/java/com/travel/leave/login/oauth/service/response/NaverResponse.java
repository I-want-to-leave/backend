package com.travel.leave.login.oauth.service.response;

import java.util.Map;

public class NaverResponse implements OAuth2Response {
    private final Map<String, Object> attributes;

    public NaverResponse(Map<String, Object> attribute) {
        this.attributes =(Map<String, Object>) attribute.get("response");
    }


    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getNickname() {
        return attributes.get("name").toString();
    }
}
