package com.student.studentCrud.controller;


import com.student.studentCrud.dto.StudentDto;
import com.student.studentCrud.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<StudentDto> saveStudent(@RequestBody StudentDto studentDto) {
        log.info("[saveStudent] Received API request to save student: {}", studentDto);
        ResponseEntity<StudentDto> response = ResponseEntity.ok(studentService.saveStudent(studentDto));
        log.info("[saveStudent] Response: {}", response.getStatusCode());
        return response;
    }

    @GetMapping("/findById")
    public ResponseEntity<StudentDto> getStudent(@RequestParam int studentId) {
        log.info("[getStudent] Received API request to find student with ID: {}", studentId);
        ResponseEntity<StudentDto> response = ResponseEntity.ok(studentService.findStudent(studentId));
        log.info("[getStudent] Response: {}", response.getStatusCode());
        return response;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<StudentDto>> getAll() {
        log.info("[getAll] Received API request to retrieve all students");
        ResponseEntity<List<StudentDto>> response = ResponseEntity.ok(studentService.findAll());
        List<StudentDto> students = response.getBody();
        int studentCount = (students != null) ? students.size() : 0;
        log.info("[getAll] Found {} students. Response: {}", studentCount, response.getStatusCode());
        return response;
    }


    @PutMapping("/update")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto student) {
        log.info("[updateStudent] Received API request to update student: {}", student);
        ResponseEntity<StudentDto> response = ResponseEntity.ok(studentService.updateStudent(student));
        log.info("[updateStudent] Response: {}", response.getStatusCode());
        return response;
    }

    @PatchMapping("/updateName")
    public ResponseEntity<StudentDto> updateStudentName(@RequestParam int studentId, @RequestParam String studentName) {
        log.info("[updateStudentName] Received API request to update name for student ID: {} to '{}'", studentId, studentName);
        ResponseEntity<StudentDto> response = ResponseEntity.ok(studentService.updateStudentName(studentId, studentName));
        log.info("[updateStudentName] Response: {}", response.getStatusCode());
        return response;
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<StudentDto> deleteStudent(@PathVariable int studentId) {
        log.info("[deleteStudent] Received API request to delete student with ID: {}", studentId);
        ResponseEntity<StudentDto> response = ResponseEntity.ok(studentService.deleteStudent(studentId));
        log.info("[deleteStudent] Response: {}", response.getStatusCode());
        return response;
    }
}
