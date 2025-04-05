package com.student.studentCrud.repository;

import com.student.studentCrud.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<AttendanceEntity, Long> {

    List<AttendanceEntity> findByStudentRollNumber(long rollNumber);

    List<AttendanceEntity> findByStudentRollNumberAndDateBetween(long rollNumber, LocalDateTime start, LocalDateTime end);


//    @Query("SELECT COUNT(a) FROM AttendanceEntity a WHERE a.student.rollNumber = :studentId")
//    long countByStudent(@Param("studentId") Long studentId);
//
//    @Query("SELECT COUNT(a) FROM AttendanceEntity a WHERE a.student.rollNumber = :studentId AND a.status = 'PRESENT'")
//    long countPresentByStudent(@Param("studentId") Long studentId);

}
