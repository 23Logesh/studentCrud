package com.student.studentCrud.controller;

import com.student.studentCrud.dto.AttendanceDto;
import com.student.studentCrud.service.AttendanceService;
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

    @PostMapping("/save")
    public ResponseEntity<AttendanceDto> saveAttendance(@RequestBody AttendanceDto attendanceDto) {
        log.info("Request to save attendance: {}", attendanceDto);
        return ResponseEntity.ok(attendanceService.saveAttendance(attendanceDto));
    }

    @GetMapping("/student/{rollNumber}")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByStudent(@PathVariable long rollNumber) {
        log.info("Fetching attendance records for student ID: {}", rollNumber);
        return ResponseEntity.ok(attendanceService.findAttendanceByStudent(rollNumber));
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<AttendanceDto> updateAttendanceStatus(@PathVariable long id, @RequestParam String status) {
        log.info("Updating attendance ID {} with status {}", id, status);
        return ResponseEntity.ok(attendanceService.updateAttendanceStatus(id, status));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AttendanceDto> deleteAttendance(@PathVariable long id) {
        log.info("Deleting attendance ID: {}", id);
        return ResponseEntity.ok(attendanceService.deleteAttendance(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AttendanceDto>> getAllAttendances() {
        log.info("Fetching all attendance records");
        return ResponseEntity.ok(attendanceService.findAllAttendances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDto> getAttendance(@PathVariable long id) {
        log.info("Fetching attendance record with ID: {}", id);
        return ResponseEntity.ok(attendanceService.findAttendance(id));
    }

    @PutMapping("/update")
    public ResponseEntity<AttendanceDto> updateAttendance(@RequestBody AttendanceDto attendanceDto) {
        log.info("Updating attendance: {}", attendanceDto);
        return ResponseEntity.ok(attendanceService.updateAttendance(attendanceDto));
    }
}
