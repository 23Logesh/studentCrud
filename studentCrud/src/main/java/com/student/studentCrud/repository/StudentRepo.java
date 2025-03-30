package com.student.studentCrud.repository;

import com.student.studentCrud.dto.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

    Student findByStudentPhoneNo(String studentPhoneNo);

}
