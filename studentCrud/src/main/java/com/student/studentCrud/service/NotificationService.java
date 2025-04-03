package com.student.studentCrud.service;

import com.student.studentCrud.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    NotificationDto saveNotification(NotificationDto notificationDto);

    List<NotificationDto> findNotificationsByStudent(long rollNumber);

    NotificationDto updateNotification(Long id, String message);

    NotificationDto deleteNotification(Long id);

    List<NotificationDto> findAllNotifications();

    NotificationDto findNotification(Long notificationId);
}

