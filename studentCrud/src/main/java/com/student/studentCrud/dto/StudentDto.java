package com.student.studentCrud.dto;

import com.student.studentCrud.Entity.AttendanceEntity;
import com.student.studentCrud.Entity.GradeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StudentDto {

    private Long rollNumber;

    private String name;
    private String email;
    private String className;

    private List<AttendanceEntity> attendanceRecords;

    private List<GradeEntity> grades;
}