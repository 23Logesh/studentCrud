package com.student.studentCrud.repository;

import com.student.studentCrud.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<StudentEntity, Long> {


    List<StudentEntity> findByClassNameOrderByGpaDesc(String className);


    @Query("SELECT s FROM StudentEntity s WHERE s.className = ?1 ORDER BY s.gpa DESC")
    List<StudentEntity> findTop3ByClassNameOrderByGpaDesc(String className);

}
