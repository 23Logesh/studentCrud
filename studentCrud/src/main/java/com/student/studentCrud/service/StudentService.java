package com.student.studentCrud.service;

import com.student.studentCrud.dto.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {

    ResponseEntity<Student> saveStudent(Student student);

    ResponseEntity<Student> findStudent(int studentId);

    ResponseEntity<Student> updateStudent(Student student);

    ResponseEntity<Student> deleteStudent(int studentId);

    ResponseEntity<List<Student>> findAll();

    ResponseEntity<Student> findByPhoneNo(String studentPhoneNo);

    ResponseEntity<Student> updateStudentName(int studentId, String studentName);
}
