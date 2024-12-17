package com.travel.leave.domain.board.board_enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SyncTravelExceptionMessage {

    CANNOT_ADD_OWN_POST("본인이 작성한 게시글은 자신의 여행 목록에 추가할 수 없습니다");

    private final String message;
}
