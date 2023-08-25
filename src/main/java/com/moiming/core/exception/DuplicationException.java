package com.moiming.core.exception;

public class DuplicationException extends RuntimeException {
    public DuplicationException() {
        super("중복된 데이터가 존재합니다.");
    }

    public DuplicationException(String message) {
        super(message);
    }
}
