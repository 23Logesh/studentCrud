package com.student.studentCrud.repository;

import com.student.studentCrud.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepo extends JpaRepository<GradeEntity, Long> {
    List<GradeEntity> findByStudentRollNumber(long rollNumber);
}
