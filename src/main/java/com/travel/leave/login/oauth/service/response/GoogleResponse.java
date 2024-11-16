package com.travel.leave.login.oauth.service.response;

import java.util.Map;

public class GoogleResponse implements OAuth2Response {
    private final Map<String, Object> attributes;

    public GoogleResponse(Map<String, Object> attribute) {
        this.attributes = attribute;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attributes.get("sub").toString();
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
