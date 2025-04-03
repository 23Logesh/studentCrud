package com.student.studentCrud.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    private String message;
    private LocalDateTime timestamp;
}
