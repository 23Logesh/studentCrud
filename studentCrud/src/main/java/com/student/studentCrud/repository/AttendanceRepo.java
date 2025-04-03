package com.student.studentCrud.repository;

import com.student.studentCrud.Entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<AttendanceEntity, Long> {
    List<AttendanceEntity> findByStudentRollNumber(long rollNumber);
}
