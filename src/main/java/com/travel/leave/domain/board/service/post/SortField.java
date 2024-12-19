package com.travel.leave.domain.board.service.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortField {
    LIKES("likes"),
    VIEWS("views"),
    CREATED_AT("created_at");

    private final String value;
}