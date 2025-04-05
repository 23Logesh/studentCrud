package com.student.studentCrud.controller;

import com.student.studentCrud.dto.AttendanceDto;
import com.student.studentCrud.service.AttendanceService;
import com.student.studentCrud.util.AttendanceStatus;
import com.student.studentCrud.util.ResponseStructure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
@Slf4j
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;


    @PostMapping("/markAttendance")
    public ResponseEntity<AttendanceDto> markAttendance(@RequestParam long rollNumber, @RequestParam LocalDate date, @RequestParam AttendanceStatus status) {
        return ResponseEntity.ok(attendanceService.markAttendance(rollNumber, date, status));
    }

    @GetMapping("/student/{rollNumber}")
    public ResponseEntity<ResponseStructure<Map<LocalDate, AttendanceStatus>>> getAttendanceByStudent(@PathVariable long rollNumber) {
        return ResponseEntity.ok(attendanceService.getAttendanceForStudent(rollNumber));
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<AttendanceDto> updateAttendanceStatus(@PathVariable long id, @RequestParam String status) {
        return ResponseEntity.ok(attendanceService.updateAttendanceStatus(id, status));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AttendanceDto> deleteAttendance(@PathVariable long id) {
        return ResponseEntity.ok(attendanceService.deleteAttendance(id));
    }

    @PutMapping("/update")
    public ResponseEntity<AttendanceDto> updateAttendance(@RequestBody AttendanceDto attendanceDto) {
        return ResponseEntity.ok(attendanceService.updateAttendance(attendanceDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AttendanceDto>> getAllAttendances() {
        return ResponseEntity.ok(attendanceService.findAllAttendances());
    }

    @GetMapping("/monthly-report")
    public ResponseEntity<ResponseStructure<Map<LocalDate, AttendanceStatus>>> getMonthlyReport(@RequestParam long rollNumber, @RequestParam int month, @RequestParam int year) {
        ResponseStructure<Map<LocalDate, AttendanceStatus>> report = attendanceService.getMonthlyAttendanceReport(rollNumber, month, year);
        return ResponseEntity.ok(report);
    }

}
