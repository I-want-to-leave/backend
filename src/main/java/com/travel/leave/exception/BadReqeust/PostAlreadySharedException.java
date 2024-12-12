package com.travel.leave.exception.BadReqeust;

public class PostAlreadySharedException extends BadRequest {
    public PostAlreadySharedException(String message) {
        super(message);
    }
}