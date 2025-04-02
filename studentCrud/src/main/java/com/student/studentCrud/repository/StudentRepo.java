package com.student.studentCrud.repository;

import com.student.studentCrud.Entity.Student_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student_Entity, Integer> {

    Student_Entity findByStudentPhoneNo(String studentPhoneNo);

}
