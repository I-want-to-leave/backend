package com.travel.leave.login.oauth.service.response;

public interface OAuth2Response {

    String getProvider();
    String getProviderId();
    String getEmail();
    String getNickname();

    default String getUserId(){
        return getProvider() + " " + getProviderId();
    }
}
