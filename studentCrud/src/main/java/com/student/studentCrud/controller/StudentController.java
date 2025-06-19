package com.student.studentCrud.controller;


import com.student.studentCrud.dto.StudentDto;
import com.student.studentCrud.service.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<StudentDto> saveStudent(@RequestParam @NotBlank String name, @RequestParam @Email String email, @RequestParam @NotBlank String className) {
        log.info("[saveStudent] Received API request to save student: {}, {}, {}", name, email, className);
        ResponseEntity<StudentDto> response = ResponseEntity.ok(studentService.saveStudent(name, email, className));
        log.info("[saveStudent] Response: {}", response.getStatusCode());
        return response;
    }

    @GetMapping("/findByRollNumber")
    public ResponseEntity<StudentDto> getStudent(@RequestParam long rollNumber) {
        log.info("[getStudent] Received API request to find student with ID: {}", Optional.of(rollNumber));
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
        log.info("[getAll] Found {} students. Response: {}", Optional.of(studentCount), response.getStatusCode());
        return response;
    }

    @GetMapping("/findTop3Rank")
    public ResponseEntity<List<StudentDto>> findTop3Rank(@RequestParam("class Name") @NotBlank String className) {
        log.info("[findTop3Student] Received API request to retrieve top 3 student for class: {}", className);
        ResponseEntity<List<StudentDto>> response = ResponseEntity.ok(studentService.findTop3Rank(className));
        List<StudentDto> students = response.getBody();
        int studentCount = (students != null) ? students.size() : 0;
        log.info("[findTop3Student] Found {} students. Response: {}", Optional.of(studentCount), response.getStatusCode());
        return response;
    }


    @PutMapping("/update")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody @Valid StudentDto student) {
        log.info("[updateStudent] Received API request to update student: {}", student);
        ResponseEntity<StudentDto> response = ResponseEntity.ok(studentService.updateStudent(student));
        log.info("[updateStudent] Response: {}", response.getStatusCode());
        return response;
    }

    @DeleteMapping("/delete/{rollNumber}")
    public ResponseEntity<StudentDto> deleteStudent(@PathVariable long rollNumber) {
        log.info("[deleteStudent] Received API request to delete student with ID: {}", Optional.of(rollNumber));
        ResponseEntity<StudentDto> response = ResponseEntity.ok(studentService.deleteStudent(rollNumber));
        log.info("[deleteStudent] Response: {}", response.getStatusCode());
        return response;
    }
}
