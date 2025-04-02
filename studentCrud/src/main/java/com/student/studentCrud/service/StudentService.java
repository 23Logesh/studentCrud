package com.student.studentCrud.service;


import com.student.studentCrud.dto.Student_Dto;

import java.util.List;

public interface StudentService {

    Student_Dto saveStudent(Student_Dto studentDto);

    Student_Dto findStudent(int studentId);

    Student_Dto updateStudent(Student_Dto studentDto);

    Student_Dto deleteStudent(int studentId);

    List<Student_Dto> findAll();

    Student_Dto findByPhoneNo(String studentPhoneNo);

    Student_Dto updateStudentName(int studentId, String studentName);
}
