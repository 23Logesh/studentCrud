package com.student.studentCrud.service;


import com.student.studentCrud.dto.StudentDto;
import jakarta.validation.constraints.Email;

import java.util.List;

public interface StudentService {

    StudentDto saveStudent(String name, @Email String email, String className);

    StudentDto saveStudent(StudentDto studentDto);

    StudentDto findStudent(long rollNumber);

    StudentDto updateStudent(StudentDto studentDto);

    StudentDto deleteStudent(long rollNumber);

    List<StudentDto> findAllStudent();


    List<StudentDto> findStudentsByClassName(String className);

    List<StudentDto> findTop3Rank(String className);
}
