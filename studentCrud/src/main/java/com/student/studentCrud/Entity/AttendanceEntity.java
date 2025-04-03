package com.student.studentCrud.Entity;

import com.student.studentCrud.util.AttendanceStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}
