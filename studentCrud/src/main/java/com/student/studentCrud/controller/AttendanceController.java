package com.student.studentCrud.controller;

import com.student.studentCrud.dto.AttendanceDto;
import com.student.studentCrud.service.AttendanceService;
import com.student.studentCrud.util.ResponseStructure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@Slf4j
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Save attendance record
    @PostMapping("/save")
    public ResponseEntity<AttendanceDto> saveAttendance(@RequestBody AttendanceDto attendanceDto) {
        log.info("Saving attendance: {}", attendanceDto);
        return ResponseEntity.ok(attendanceService.saveAttendance(attendanceDto));
    }

    // Get all attendance records for a student
    @GetMapping("/student/{rollNumber}")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByStudent(@PathVariable long rollNumber) {
        log.info("Fetching attendance for student rollNumber: {}", rollNumber);
        return ResponseEntity.ok(attendanceService.findAttendanceByStudent(rollNumber));
    }

    // Update attendance status for a record
    @PutMapping("/update-status/{id}")
    public ResponseEntity<AttendanceDto> updateAttendanceStatus(@PathVariable long id, @RequestParam String status) {
        log.info("Updating status for attendance ID {} to {}", id, status);
        return ResponseEntity.ok(attendanceService.updateAttendanceStatus(id, status));
    }

    // Delete attendance record
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AttendanceDto> deleteAttendance(@PathVariable long id) {
        log.info("Deleting attendance with ID: {}", id);
        return ResponseEntity.ok(attendanceService.deleteAttendance(id));
    }

    // Get attendance by record ID
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDto> getAttendance(@PathVariable long id) {
        log.info("Fetching attendance with ID: {}", id);
        return ResponseEntity.ok(attendanceService.findAttendance(id));
    }

    // Update entire attendance record (optional endpoint)
    @PutMapping("/update")
    public ResponseEntity<AttendanceDto> updateAttendance(@RequestBody AttendanceDto attendanceDto) {
        log.info("Updating attendance: {}", attendanceDto);
        return ResponseEntity.ok(attendanceService.updateAttendance(attendanceDto));
    }

    // Optional: List all attendance (admin use)
    @GetMapping("/all")
    public ResponseEntity<List<AttendanceDto>> getAllAttendances() {
        log.info("Fetching all attendance records");
        return ResponseEntity.ok(attendanceService.findAllAttendances());
    }

    @GetMapping("/monthly-report")
    public ResponseEntity<?> getMonthlyReport(@RequestParam long rollNumber, @RequestParam int month, @RequestParam int year) {
        ResponseStructure<?> report = attendanceService.getMonthlyAttendanceReport(rollNumber, month, year);
        return ResponseEntity.ok(report);
    }

}
