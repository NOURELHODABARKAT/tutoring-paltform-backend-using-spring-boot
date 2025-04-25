package com.nour.demo.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.hibernate.exception.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        return ResponseEntity.internalServerError().body("Database error");
    }
}