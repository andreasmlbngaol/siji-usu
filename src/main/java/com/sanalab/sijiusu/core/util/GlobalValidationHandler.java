package com.sanalab.sijiusu.core.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationError(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors()
            .stream().map(err ->
                err.getDefaultMessage() != null ? err.getDefaultMessage() : "Invalid error"
            )
            .toList();

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                Map.of("errors", errors)
            );
    }
}
