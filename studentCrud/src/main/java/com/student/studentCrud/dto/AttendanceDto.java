package com.student.studentCrud.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.student.studentCrud.util.AttendanceStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceDto {
    private long id;
    @NotNull(message = "student is required")
    private StudentDto student;
    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Attendance status is required")
    private AttendanceStatus status;
}
