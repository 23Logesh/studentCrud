package com.student.studentCrud.Entity;

import jakarta.persistence.*;

@Entity
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    private String subject;
    private double score;
}
