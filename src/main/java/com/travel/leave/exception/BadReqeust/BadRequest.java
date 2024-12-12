package com.travel.leave.exception.BadReqeust;

abstract public class BadRequest extends RuntimeException {
    public BadRequest(String message) {
        super(message);
    }
}