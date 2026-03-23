package com.student.studentCrud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class GradeDto {
    private long id;
    @NotNull(message = "student is required")
    private StudentDto student;
    @NotBlank(message = "Subject is Not be blank")
    @NotNull(message = "Subject is required")
    private String subject;
    @PositiveOrZero(message = "Score must be Positive Number")
    private double score;
}
