package com.student.studentCrud.repository;

import com.student.studentCrud.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<StudentEntity, Long> {

    List<StudentEntity> findByClassName(String className);

    @Query(value = """
    SELECT * FROM student 
    WHERE class_name = :className 
      AND rank IN ( 
          SELECT DISTINCT rank 
          FROM student 
          WHERE class_name = :className 
          ORDER BY rank ASC 
          LIMIT 3 
      ) 
    ORDER BY rank ASC 
""", nativeQuery = true)
    List<StudentEntity> findByTop3Rank(@Param("className") String className);

}
