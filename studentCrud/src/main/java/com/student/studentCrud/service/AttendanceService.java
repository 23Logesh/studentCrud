package com.student.studentCrud.service;

import com.student.studentCrud.dto.AttendanceDto;

import java.util.List;

public interface AttendanceService {

    AttendanceDto saveAttendance(AttendanceDto attendanceDto);

    List<AttendanceDto> findAttendanceByStudent(long rollNumber);

    AttendanceDto updateAttendanceStatus(long id, String status);

    AttendanceDto deleteAttendance(long id);

    List<AttendanceDto> findAllAttendances();


    AttendanceDto findAttendance(long attendanceId);


    AttendanceDto updateAttendance(AttendanceDto attendanceDto);

}
