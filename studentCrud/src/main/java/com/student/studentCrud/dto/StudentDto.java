package com.student.studentCrud.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.student.studentCrud.Entity.AttendanceEntity;
import com.student.studentCrud.Entity.GradeEntity;
import lombok.Data;

import java.util.List;

@Data
public class StudentDto {

    private Long rollNumber;

    private String name;
    private String email;
    private String className;

    private List<AttendanceDto> attendanceRecords;

    private List<GradeDto> grades;
}