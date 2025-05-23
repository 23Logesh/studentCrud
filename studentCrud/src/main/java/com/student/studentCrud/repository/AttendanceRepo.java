package com.student.studentCrud.repository;

import com.student.studentCrud.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<AttendanceEntity, Long> {

    List<AttendanceEntity> findByStudentRollNumber(long rollNumber);

    List<AttendanceEntity> findByStudentRollNumberAndDateBetween(long rollNumber, LocalDate start, LocalDate end);

}
