package com.student.studentCrud.exception;

import com.student.studentCrud.util.ResponseStructure;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @Autowired
    ResponseStructure<Object> structure;

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
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

    @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseStructure<Object>> handleDataIntegrityViolationException(DataIntegrityViolationException exe) {
        log.error("DataIntegrityViolationException caught: {}", exe.getMessage(), exe);

        String Message = exe.getMostSpecificCause().getMessage().replaceAll("for key '.*?'", "");
        structure.setMessage("Data integrity violation: " + Message);
        structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        structure.setData(null);

        log.info("Returning DataIntegrityViolationException response with message: {}", Message);
        return new ResponseEntity<>(structure, HttpStatus.NOT_ACCEPTABLE);
    }
}
