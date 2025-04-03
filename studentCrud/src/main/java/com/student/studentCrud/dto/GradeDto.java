package com.student.studentCrud.dto;

import com.student.studentCrud.Entity.StudentEntity;
import lombok.Data;

@Data
public class GradeDto {
    private Long id;

    private StudentEntity student;

    private String subject;
    private double score;
}
