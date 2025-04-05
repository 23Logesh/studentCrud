package com.student.studentCrud.service;

import com.student.studentCrud.dto.NotificationDto;
import com.student.studentCrud.util.ResponseStructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface NotificationService {

    void saveNotification(NotificationDto notificationDto);

    ResponseStructure<Map<LocalDateTime, String>> findNotificationsByStudent(long rollNumber);

    NotificationDto updateNotification(long id, String message);

    NotificationDto deleteNotification(long id);

    List<NotificationDto> findAllNotifications();

    NotificationDto findNotification(long notificationId);
}

