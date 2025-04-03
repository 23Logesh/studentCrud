package com.student.studentCrud.serviceImp;

import com.student.studentCrud.Entity.NotificationEntity;
import com.student.studentCrud.Entity.StudentEntity;
import com.student.studentCrud.dto.NotificationDto;
import com.student.studentCrud.dto.StudentDto;
import com.student.studentCrud.repository.NotificationRepo;
import com.student.studentCrud.repository.StudentRepo;
import com.student.studentCrud.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificationServiceImp implements NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public NotificationDto saveNotification(NotificationDto notificationDto) {
        NotificationEntity savedNotification = notificationRepo.save(convertDtoToEntity(notificationDto));
        log.info("[saveNotification] SUCCESS - Notification saved for Student ID: {}, Message: {}",
                savedNotification.getStudent().getRollNumber(), savedNotification.getMessage());
        return convertEntityToDto(savedNotification);
    }

    @Override
    public List<NotificationDto> findNotificationsByStudent(long rollNumber) {
        return notificationRepo.findByStudentRollNumber(rollNumber)
                .stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    public NotificationDto updateNotification(Long id, String message) {
        NotificationEntity notification = getNotificationById(id);
        notification.setMessage(message);
        NotificationEntity updatedNotification = notificationRepo.save(notification);
        log.info("[updateNotification] SUCCESS - Updated Notification ID: {}, Message: {}",
                updatedNotification.getId(), updatedNotification.getMessage());
        return convertEntityToDto(updatedNotification);
    }

    @Override
    public NotificationDto deleteNotification(Long id) {
        NotificationEntity notification = getNotificationById(id);
        notificationRepo.delete(notification);
        log.info("[deleteNotification] SUCCESS - Deleted Notification ID: {}", id);
        return convertEntityToDto(notification);
    }

    @Override
    public List<NotificationDto> findAllNotifications() {
        return notificationRepo.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    public NotificationDto findNotification(Long notificationId) {
        return convertEntityToDto(getNotificationById(notificationId));
    }

    private NotificationEntity getNotificationById(Long id) {
        return notificationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + id));
    }

    private NotificationEntity convertDtoToEntity(NotificationDto notificationDto) {
        StudentEntity student = studentRepo.findById(notificationDto.getStudent().getRollNumber())
                .orElseThrow(() -> new RuntimeException("Student not found with Roll Number: " + notificationDto.getStudent().getRollNumber()));

        NotificationEntity notificationEntity = modelMapper.map(notificationDto, NotificationEntity.class);
        notificationEntity.setStudent(student);
        return notificationEntity;
    }

    private NotificationDto convertEntityToDto(NotificationEntity notificationEntity) {
        NotificationDto notificationDto = modelMapper.map(notificationEntity, NotificationDto.class);
        notificationDto.setStudent(modelMapper.map(notificationEntity.getStudent(), StudentDto.class));
        return notificationDto;
    }
}
