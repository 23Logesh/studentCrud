package com.student.studentCrud.service;


import com.student.studentCrud.dto.StudentDto;

import java.util.List;

public interface StudentService {

    StudentDto saveStudent(StudentDto studentDto);

    StudentDto findStudent(long rollNumber);

    StudentDto updateStudent(StudentDto studentDto);

    StudentDto deleteStudent(long rollNumber);

    List<StudentDto> findAllStudent();

    StudentDto updateStudentName(long rollNumber, String studentName);
}
