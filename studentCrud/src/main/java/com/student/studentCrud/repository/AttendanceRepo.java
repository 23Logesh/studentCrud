package com.student.studentCrud.repository;

import com.student.studentCrud.Entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepo extends JpaRepository<AttendanceEntity, Long> {
}
