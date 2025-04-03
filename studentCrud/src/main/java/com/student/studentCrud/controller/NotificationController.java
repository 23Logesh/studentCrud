package com.student.studentCrud.controller;

import com.student.studentCrud.dto.NotificationDto;
import com.student.studentCrud.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@Slf4j
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/save")
    public ResponseEntity<NotificationDto> saveNotification(@RequestBody NotificationDto notificationDto) {
        log.info("Request to save notification: {}", notificationDto);
        return ResponseEntity.ok(notificationService.saveNotification(notificationDto));
    }

    @GetMapping("/student/{rollNumber}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByStudent(@PathVariable long rollNumber) {
        log.info("Fetching notifications for student ID: {}", rollNumber);
        return ResponseEntity.ok(notificationService.findNotificationsByStudent(rollNumber));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<NotificationDto> updateNotification(@PathVariable Long id, @RequestParam String message) {
        log.info("Updating notification ID {} with message: {}", id, message);
        return ResponseEntity.ok(notificationService.updateNotification(id, message));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<NotificationDto> deleteNotification(@PathVariable Long id) {
        log.info("Deleting notification ID: {}", id);
        return ResponseEntity.ok(notificationService.deleteNotification(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        log.info("Fetching all notifications");
        return ResponseEntity.ok(notificationService.findAllNotifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotification(@PathVariable Long id) {
        log.info("Fetching notification with ID: {}", id);
        return ResponseEntity.ok(notificationService.findNotification(id));
    }
}
