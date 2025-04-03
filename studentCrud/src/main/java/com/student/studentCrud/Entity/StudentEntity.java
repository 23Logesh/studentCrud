package com.student.studentCrud.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rollNumber;

    private String name;
    private String email;
    private String className;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<AttendanceEntity> attendanceRecords;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<GradeEntity> grades;
}
