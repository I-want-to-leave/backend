package com.travel.leave.exception;

import com.travel.leave.exception.message.ExceptionMessage;

public class SearchKeywordException extends IllegalArgumentException {
    public SearchKeywordException() {
        super(ExceptionMessage.SEARCH_KEYWORD_EXCEPTION.getMessage());
    }
}

