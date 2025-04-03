package com.student.studentCrud.repository;

import com.student.studentCrud.Entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepo extends JpaRepository<AttendanceEntity, Long> {
    List<AttendanceEntity> findByStudentRollNumber(long rollNumber);
}
