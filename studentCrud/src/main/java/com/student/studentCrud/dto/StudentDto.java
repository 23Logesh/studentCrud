package com.student.studentCrud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class StudentDto {

    private long rollNumber;
    @NotBlank(message = "Name should Not be blank")
    @NotNull(message = "Name is required")
    private String name;
    @NotBlank(message = "Email should not be blank")
    @NotNull(message = "Email is required")
    @Email(message = "Email Format is Wrong")
    private String email;
    @NotBlank(message = "Class Name should not be blank")
    @NotNull(message = "Class Name is required")
    private String className;

    private double gpa;
    private String performanceLevel;
    private int rank;
    private double attendancePercentage;

}