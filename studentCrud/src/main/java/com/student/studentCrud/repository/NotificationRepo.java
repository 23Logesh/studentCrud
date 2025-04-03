package com.student.studentCrud.repository;

import com.student.studentCrud.Entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByStudentRollNumber(long rollNumber);

}
