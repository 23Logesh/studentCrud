package com.student.studentCrud.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    @NotBlank(message = "Student name cannot be blank")
    @Column(nullable = false)
    private String studentName;

    @Pattern(regexp = "\\d{10}", message = "Student Number should be 10 digits")
    @Column(unique = true, nullable = false)
    private String studentPhoneNo;

    private LocalDate enrollmentDate;
}