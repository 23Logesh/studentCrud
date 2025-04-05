package com.student.studentCrud.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private long id;

    private StudentDto student;

    private String message;
    private LocalDateTime timestamp;
}
