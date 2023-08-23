package com.moiming.core.exception;

import jakarta.persistence.NoResultException;

public class CustomNoResultException extends NoResultException {
    public CustomNoResultException() {
        super("조회된 데이터가 없습니다.");
    }

    public CustomNoResultException(String message) {
        super(message);
    }
}
