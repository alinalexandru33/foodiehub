package com.foodiehub.catalog_service.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> badReq(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiError("BAD_REQUEST", ex.getMessage())
        );
    }
    record ApiError(String code, String message) {}
}
