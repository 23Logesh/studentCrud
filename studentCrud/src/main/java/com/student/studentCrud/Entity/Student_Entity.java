package com.student.studentCrud.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;
@Entity
@Getter
public class Student_Entity {
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
