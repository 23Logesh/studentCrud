package com.student.studentCrud.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rollNumber;

    private String name;
    private String email;
    private String className;

    private double gpa;
    private String performanceLevel;

    @Column(name = "`rank`")
    private int rank;

    private double attendancePercentage;


}
