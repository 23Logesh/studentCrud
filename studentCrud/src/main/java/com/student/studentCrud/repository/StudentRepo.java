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

    @Query(value = "SELECT * FROM student_entity WHERE class_name = :className AND `rank` IN ( SELECT * FROM (SELECT DISTINCT `rank` FROM student_entityWHERE class_name = :classNameORDER BY `rank` ASC LIMIT 3) AS top_ranks)ORDER BY `rank` ASC", nativeQuery = true)
    List<StudentEntity> findByTop3Rank(@Param("className") String className);
}
