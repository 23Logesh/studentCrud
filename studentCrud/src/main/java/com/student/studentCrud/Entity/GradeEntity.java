package com.student.studentCrud.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_rollNumber")
    private StudentEntity student;

    private String subject;
    private double score;
}
