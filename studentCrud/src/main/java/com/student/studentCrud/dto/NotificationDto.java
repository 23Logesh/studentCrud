package com.student.studentCrud.dto;

import com.student.studentCrud.Entity.StudentEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;

    private StudentEntity student;

    private String message;
    private LocalDateTime timestamp;
}
