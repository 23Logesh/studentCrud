package com.student.studentCrud.util;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ResponseStructure<T> {
    T data;
    int status;
    String message;
}