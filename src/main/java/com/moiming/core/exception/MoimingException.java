package com.moiming.core.exception;

public class MoimingException extends RuntimeException {
    public MoimingException() {
        super("예기치 못한 오류가 발생하였습니다.");
    }

    public MoimingException(String message) {
        super(message);
    }
}
