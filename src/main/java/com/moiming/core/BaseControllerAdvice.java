package com.moiming.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class BaseControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<InvalidResponse>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        List<InvalidResponse> responses = new ArrayList<>();

        bindingResult.getFieldErrors().stream().forEach(fieldError -> {
            responses.add(new InvalidResponse(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue()));
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responses);
    }
}
