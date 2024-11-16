package com.travel.leave.login.oauth.service.response;

import java.util.Map;

public class FaceBookResponse implements OAuth2Response {
    private final Map<String, Object> attributes;

    public FaceBookResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "facebook";
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

    @Override
    public String getUserId() {
        return "facebook " + attributes.get("id");
    }
}
