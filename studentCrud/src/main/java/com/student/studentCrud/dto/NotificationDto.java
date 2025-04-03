package com.student.studentCrud.dto;

import com.student.studentCrud.Entity.StudentEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;

    private StudentDto student;

    private String message;
    private LocalDateTime timestamp;
}
