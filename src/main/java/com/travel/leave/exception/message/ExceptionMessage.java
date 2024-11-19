package com.travel.leave.exception.message;

public enum ExceptionMessage {
    DUPLICATE_USERNAME("이미 %s가 존재합니다."),
    CONVERT_EXCEPTION("DTO 혹은 엔티티로 변환 중 문제가 발생했습니다."),
    DATABASE_SAVE_EXCEPTION("데이터베이스에 저장 중 문제가 발생했습니다."),
    DATABASE_ACCESS_EXCEPTION("데이터베이스에 연결할 수 없습니다."),
    NO_INFO_EXCEPTION("알 수 없는 에러!");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageWithOneARG(String arg){
        return message.formatted(arg);
    }
}
