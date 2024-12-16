package com.travel.leave.domain.board.service.enums;

import lombok.Getter;

@Getter
public enum SortField {
    LIKES("likes"),
    VIEWS("views"),
    CREATED_AT("created_at");

    private final String value;

    SortField(String value) {
        this.value = value;
    }
}