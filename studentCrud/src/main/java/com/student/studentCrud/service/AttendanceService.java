package com.student.studentCrud.service;

import com.student.studentCrud.dto.AttendanceDto;
import com.student.studentCrud.util.AttendanceStatus;
import com.student.studentCrud.util.ResponseStructure;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AttendanceService {

    AttendanceDto saveAttendance(AttendanceDto attendanceDto);

    List<AttendanceDto> findAttendanceByStudent(long rollNumber);

    AttendanceDto updateAttendanceStatus(long id, String status);

    AttendanceDto deleteAttendance(long id);

    List<AttendanceDto> findAllAttendances();

    AttendanceDto findAttendance(long attendanceId);

    AttendanceDto updateAttendance(AttendanceDto attendanceDto);


    ResponseStructure<?> getMonthlyAttendanceReport(long rollNumber, int month, int year);

}
