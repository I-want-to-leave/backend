package com.travel.leave.exception.BadReqeust;


import lombok.Getter;

@Getter
public class GPTResponseParsingException extends BadRequest {
    private final Throwable cause;

    public GPTResponseParsingException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }
}