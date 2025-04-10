package com.student.studentCrud.controller;

import com.student.studentCrud.dto.NotificationDto;
import com.student.studentCrud.service.NotificationService;
import com.student.studentCrud.util.ResponseStructure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
@Slf4j
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/student/{rollNumber}")
    public ResponseEntity<ResponseStructure<Map<String, String>>> getNotificationsByStudent(@PathVariable long rollNumber) {
        return ResponseEntity.ok(notificationService.findNotificationsByStudent(rollNumber));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<NotificationDto> updateNotification(@PathVariable long id, @RequestParam String message) {
        return ResponseEntity.ok(notificationService.updateNotification(id, message));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<NotificationDto> deleteNotification(@PathVariable long id) {
        return ResponseEntity.ok(notificationService.deleteNotification(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.findAllNotifications());
    }


}
