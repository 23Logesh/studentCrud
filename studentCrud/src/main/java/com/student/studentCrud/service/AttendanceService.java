package com.student.studentCrud.service;

import com.student.studentCrud.dto.AttendanceDto;
import com.student.studentCrud.util.AttendanceStatus;
import com.student.studentCrud.util.ResponseStructure;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AttendanceService {

    AttendanceDto markAttendance(long rollNumber, LocalDate date, AttendanceStatus status);

    ResponseStructure<Map<LocalDate, AttendanceStatus>> getAttendanceForStudent(long rollNumber);

    AttendanceDto updateAttendanceStatus(long id, String status);

    AttendanceDto deleteAttendance(long id);

    List<AttendanceDto> findAllAttendances();

    AttendanceDto updateAttendance(AttendanceDto attendanceDto);

    ResponseStructure<Map<LocalDate, AttendanceStatus>> getMonthlyAttendanceReport(long rollNumber, int month, int year);

}
