package com.student.studentCrud.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String rollNumber;
    private String className;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<AttendanceEntity> attendanceRecords;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<GradeEntity> grades;
}
