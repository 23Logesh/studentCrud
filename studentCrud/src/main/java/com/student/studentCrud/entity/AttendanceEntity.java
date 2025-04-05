package com.student.studentCrud.entity;

import com.student.studentCrud.util.AttendanceStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_roll_number", referencedColumnName = "rollNumber")
    private StudentEntity student;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}
