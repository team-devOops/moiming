package com.moiming.meet.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum Level {
    ADMIN,
    SUB_ADMIN,
    MEMBER;

    @JsonCreator
    public static Level fromInput(String input){
        return Arrays.stream(Level.values())
                .filter(level -> level.name().equals(input))
                .findFirst()
                .orElse(null);
    }
}
