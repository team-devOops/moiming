package com.moiming.core;

import com.moiming.core.exception.CustomNoResultException;
import com.moiming.core.exception.DuplicationException;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<InvalidResponse>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        List<InvalidResponse> responses =  bindingResult.getFieldErrors().stream()
                .map(fieldError -> new InvalidResponse(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue()))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responses);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<List<InvalidResponse>> constraintViolationExceptionHandler(ConstraintViolationException e) {

        List<InvalidResponse> responses = new ArrayList<>();

        e.getConstraintViolations().forEach(fieldError -> {
            responses.add(new InvalidResponse(fieldError.getPropertyPath().toString(), fieldError.getMessage(), fieldError.getInvalidValue()));
        });

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(responses);
    }

    @ExceptionHandler(CustomNoResultException.class)
    public ResponseEntity<String> noResultExceptionExceptionHandler(CustomNoResultException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<String> duplicationExceptionHandler(DuplicationException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}
