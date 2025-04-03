package com.student.studentCrud.repository;

import com.student.studentCrud.Entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<NotificationEntity, Long> {
}
