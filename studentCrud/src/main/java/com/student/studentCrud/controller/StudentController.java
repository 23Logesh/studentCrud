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
    public ResponseEntity<StudentDto> getStudent(@RequestParam long rollNumber) {
        log.info("[getStudent] Received API request to find student with ID: {}", rollNumber);
        ResponseEntity<StudentDto> response = ResponseEntity.ok(studentService.findStudent(rollNumber));
        log.info("[getStudent] Response: {}", response.getStatusCode());
        return response;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<StudentDto>> getAll() {
        log.info("[getAll] Received API request to retrieve all students");
        ResponseEntity<List<StudentDto>> response = ResponseEntity.ok(studentService.findAllStudent());
        List<StudentDto> students = response.getBody();
        int studentCount = (students != null) ? students.size() : 0;
        log.info("[getAll] Found {} students. Response: {}", studentCount, response.getStatusCode());
        return response;
    }

    @GetMapping("/findTop3Rank")
    public ResponseEntity<List<StudentDto>> findTop3Rank(@RequestParam("class") String className) {
        log.info("[findTop3Student] Received API request to retrieve top 3 student for class: {}", className);
        ResponseEntity<List<StudentDto>> response = ResponseEntity.ok(studentService.findTop3Rank(className));
        List<StudentDto> students = response.getBody();
        int studentCount = (students != null) ? students.size() : 0;
        log.info("[findTop3Student] Found {} students. Response: {}", studentCount, response.getStatusCode());
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
    public ResponseEntity<StudentDto> updateStudentName(@RequestParam long rollNumber, @RequestParam String studentName) {
        log.info("[updateStudentName] Received API request to update name for student ID: {} to '{}'", rollNumber, studentName);
        ResponseEntity<StudentDto> response = ResponseEntity.ok(studentService.updateStudentName(rollNumber, studentName));
        log.info("[updateStudentName] Response: {}", response.getStatusCode());
        return response;
    }

    @DeleteMapping("/delete/{rollNumber}")
    public ResponseEntity<StudentDto> deleteStudent(@PathVariable long rollNumber) {
        log.info("[deleteStudent] Received API request to delete student with ID: {}", rollNumber);
        ResponseEntity<StudentDto> response = ResponseEntity.ok(studentService.deleteStudent(rollNumber));
        log.info("[deleteStudent] Response: {}", response.getStatusCode());
        return response;
    }
}
