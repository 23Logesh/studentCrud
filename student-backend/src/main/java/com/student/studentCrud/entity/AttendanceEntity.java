package com.student.studentCrud.entity;

import com.student.studentCrud.util.AttendanceStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(indexes = @Index(columnList = "student_rollNumber"), uniqueConstraints = @UniqueConstraint(columnNames = {"student_rollNumber", "date"}))
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "student_rollNumber", referencedColumnName = "rollNumber")
    private StudentEntity student;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}
