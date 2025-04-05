package com.student.studentCrud.dto;

import lombok.Data;

@Data
public class GradeDto {
    private long id;

    private StudentDto student;

    private String subject;
    private double score;
}
