package com.moiming.user.domain;

import lombok.Getter;

public enum UserErrorMessage {
    EXIST_ID("이미 존재하는 아이디입니다."),
    EXIST_EMAIL("이미 존재하는 이메일입니다.");

    @Getter
    private final String message;

    UserErrorMessage(String message) {
        this.message = message;
    }
}
