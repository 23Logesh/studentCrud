package com.student.studentCrud.repository;

import com.student.studentCrud.Entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepo extends JpaRepository<GradeEntity,Long> {
}
