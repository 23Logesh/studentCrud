package com.student.studentCrud.repository;

import com.student.studentCrud.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<StudentEntity, Long> {

    List<StudentEntity> findByClassName(String className);

    @Query(
            value = "SELECT * FROM student_entity s WHERE s.class_name = :className AND s.rank <> 0 ORDER BY s.rank ASC LIMIT 3",
            nativeQuery = true
    )
    List<StudentEntity> findTop3ByClassNameExcludingZeroRank(@Param("className") String className);
}