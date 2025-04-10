package com.student.studentCrud.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentDto {

    private long rollNumber;

    private String name;
    private String email;
    private String className;

    private double gpa;
    private String performanceLevel;

    private int rank;

    private double attendancePercentage;

}