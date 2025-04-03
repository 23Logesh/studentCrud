package com.student.studentCrud.dto;

import com.student.studentCrud.Entity.AttendanceEntity;
import com.student.studentCrud.Entity.StudentEntity;
import com.student.studentCrud.util.AttendanceStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceDto {
    private Long id;

    private StudentDto student;

    private LocalDate date;

    private AttendanceStatus status;


}
