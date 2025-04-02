package com.student.studentCrud.service;


import com.student.studentCrud.dto.StudentDto;

import java.util.List;

public interface StudentService {

    StudentDto saveStudent(StudentDto studentDto);

    StudentDto findStudent(int studentId);

    StudentDto updateStudent(StudentDto studentDto);

    StudentDto deleteStudent(int studentId);

    List<StudentDto> findAll();

    StudentDto findByPhoneNo(String studentPhoneNo);

    StudentDto updateStudentName(int studentId, String studentName);
}
