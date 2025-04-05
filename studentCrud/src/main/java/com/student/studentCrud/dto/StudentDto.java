package com.student.studentCrud.dto;

import lombok.Data;

@Data
public class StudentDto {

    private Long rollNumber;

    private String name;
    private String email;
    private String className;

    private Double gpa;
    private String performanceLevel;

    private int rank;

}