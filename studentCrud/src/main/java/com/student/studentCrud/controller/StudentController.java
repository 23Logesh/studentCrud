package com.student.studentCrud.controller;

import com.student.studentCrud.dto.Student;
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
    public ResponseEntity<Student> saveStudent( @RequestBody Student student) {
        log.info("[saveStudent] Received API request to save student: {}", student);
        ResponseEntity<Student> response = studentService.saveStudent(student);
        log.info("[saveStudent] Response: {}", response.getStatusCode());
        return response;
    }

    @GetMapping("/findById")
    public ResponseEntity<Student> getStudent(@RequestParam int studentId) {
        log.info("[getStudent] Received API request to find student with ID: {}", studentId);
        ResponseEntity<Student> response = studentService.findStudent(studentId);
        log.info("[getStudent] Response: {}", response.getStatusCode());
        return response;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Student>> getAll() {
        log.info("[getAll] Received API request to retrieve all students");
        ResponseEntity<List<Student>> response = studentService.findAll();
        List<Student> students = response.getBody();
        int studentCount = (students != null) ? students.size() : 0;
        log.info("[getAll] Found {} students. Response: {}", studentCount, response.getStatusCode());
        return response;
    }

    @GetMapping("/findByPhone/{studentPhoneNo}")
    public ResponseEntity<Student> findByPhoneNo(@PathVariable String studentPhoneNo) {
        log.info("[findByPhoneNo] Received API request to find student with phone number: {}", studentPhoneNo);
        ResponseEntity<Student> response = studentService.findByPhoneNo(studentPhoneNo);
        log.info("[findByPhoneNo] Response: {}", response.getStatusCode());
        return response;
    }

    @PutMapping("/update")
    public ResponseEntity<Student> updateStudent(@RequestBody  Student student) {
        log.info("[updateStudent] Received API request to update student: {}", student);
        ResponseEntity<Student> response = studentService.updateStudent(student);
        log.info("[updateStudent] Response: {}", response.getStatusCode());
        return response;
    }

    @PatchMapping("/updateName")
    public ResponseEntity<Student> updateStudentName(@RequestParam int studentId, @RequestParam String studentName) {
        log.info("[updateStudentName] Received API request to update name for student ID: {} to '{}'", studentId, studentName);
        ResponseEntity<Student> response = studentService.updateStudentName(studentId, studentName);
        log.info("[updateStudentName] Response: {}", response.getStatusCode());
        return response;
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<Student> deleteStudent(@PathVariable int studentId) {
        log.info("[deleteStudent] Received API request to delete student with ID: {}", studentId);
        ResponseEntity<Student> response = studentService.deleteStudent(studentId);
        log.info("[deleteStudent] Response: {}", response.getStatusCode());
        return response;
    }
}
