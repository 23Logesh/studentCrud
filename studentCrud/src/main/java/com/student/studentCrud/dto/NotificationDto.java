package com.student.studentCrud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private long id;
    @NotNull(message = "student is required")
    private StudentDto student;
    @NotBlank(message = "Message is Not be Blank")
    @NotNull(message = "Message is required")
    private String message;
    private LocalDateTime timestamp;
}
