package com.student.studentCrud.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Student_Dto{

    private int studentId;


    private String studentName;


    private String studentPhoneNo;

    private LocalDate enrollmentDate;
}