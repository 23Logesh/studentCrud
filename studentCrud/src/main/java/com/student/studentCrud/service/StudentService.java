package com.student.studentCrud.service;

import com.student.studentCrud.dto.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {

    Student saveStudent(Student student);

    Student findStudent(int studentId);

    Student updateStudent(Student student);

    Student deleteStudent(int studentId);

    List<Student> findAll();

    Student findByPhoneNo(String studentPhoneNo);

    Student updateStudentName(int studentId, String studentName);
}
