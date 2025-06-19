package com.student.studentCrud.exception;

import com.student.studentCrud.util.ResponseStructure;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    ResponseStructure<Object> structure;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseStructure<Object>> handleConstraintViolationException(ConstraintViolationException exe) {
        log.error("ConstraintViolationException caught: {}", exe.getMessage(), exe);

        Map<String, String> hashmap = new HashMap<>();
        for (ConstraintViolation<?> violation : exe.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            hashmap.put(field, message);
        }

        structure.setMessage("Validation failed: Please check input values.");
        structure.setStatus(HttpStatus.FORBIDDEN.value());
        structure.setData(hashmap);

        log.info("Returning validation error response: {}", hashmap);
        return new ResponseEntity<>(structure, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseStructure<Object>> handleDataIntegrityViolationException(DataIntegrityViolationException exe) {
        log.error("DataIntegrityViolationException caught: {}", exe.getMessage(), exe);

        String Message = exe.getMostSpecificCause().getMessage().replaceAll("for key '.*?'", "");
        structure.setMessage("Data integrity violation: " + Message);
        structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        structure.setData(null);

        log.info("Returning DataIntegrityViolationException response with message: {}", Message);
        return new ResponseEntity<>(structure, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException caught: {}", ex.getMessage(), ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        log.info("Validation errors: {}", errors);

        return ResponseEntity.badRequest().body(Map.of(
                "status", 400,
                "error", "Bad Request",
                "message", "Validation failed",
                "errors", errors
        ));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleHandlerValidationException(HandlerMethodValidationException ex) {
        log.error("HandlerMethodValidationException caught: {}", ex.getMessage(), ex);

        Map<String, String> errors = new HashMap<>();

        ex.getAllErrors().forEach(error -> {
            if (error instanceof FieldError fieldError) {
                errors.put(fieldError.getField(), Optional.ofNullable(fieldError.getDefaultMessage()).orElse("Invalid value"));
            } else {
                errors.put("global", Optional.ofNullable(error.getDefaultMessage()).orElse("Validation error"));
            }
        });

        log.info("Handler method validation errors: {}", errors);

        return ResponseEntity.badRequest().body(Map.of(
                "status", 400,
                "error", "Bad Request",
                "message", "Validation failed",
                "errors", errors
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherExceptions(Exception ex) {
        log.error("Unhandled exception caught: {}", ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "status", 500,
                "error", "Internal Server Error",
                "message", "Something went wrong"
        ));
    }

}
