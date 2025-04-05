package com.student.studentCrud.serviceImp;

import com.student.studentCrud.dto.NotificationDto;
import com.student.studentCrud.dto.StudentDto;
import com.student.studentCrud.entity.NotificationEntity;
import com.student.studentCrud.entity.StudentEntity;
import com.student.studentCrud.repository.NotificationRepo;
import com.student.studentCrud.repository.StudentRepo;
import com.student.studentCrud.service.NotificationService;
import com.student.studentCrud.util.ResponseStructure;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationServiceImp implements NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ModelMapper modelMapper;

    private ResponseStructure<Map<LocalDateTime, String>> responseStructure;

    @Override
    public void saveNotification(NotificationDto notificationDto) {
        notificationRepo.save(convertDtoToEntity(notificationDto));

    }

    @Override
    public ResponseStructure<Map<LocalDateTime, String>> findNotificationsByStudent(long rollNumber) {
        List<NotificationDto> notificationDtoList = notificationRepo.findByStudentRollNumber(rollNumber)
                .stream()
                .map(this::convertEntityToDto)
                .toList();
        Map<LocalDateTime, String> reportMap = notificationDtoList.stream()
                .collect(Collectors.toMap(
                        NotificationDto::getTimestamp,
                        NotificationDto::getMessage
                ));

        return responseStructure.getMapResponseStructure(notificationDtoList.getFirst().getStudent(), reportMap);
    }

    @Override
    public NotificationDto updateNotification(long id, String message) {
        NotificationDto notification = findNotification(id);
        notification.setMessage(message);
        return convertEntityToDto(notificationRepo.save(convertDtoToEntity(notification)));
    }

    @Override
    public NotificationDto deleteNotification(long id) {
        NotificationDto notification = findNotification(id);
        if (notification != null) {
            notificationRepo.delete(convertDtoToEntity(notification));
            return notification;
        }
        return null;
    }

    @Override
    public List<NotificationDto> findAllNotifications() {
        return notificationRepo.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    public NotificationDto findNotification(long notificationId) {
        return convertEntityToDto(notificationRepo.findById(notificationId).orElse(null));
    }

    private NotificationEntity convertDtoToEntity(NotificationDto notificationDto) {
        if (notificationDto == null) return null;
        StudentEntity student = studentRepo.findById(notificationDto.getStudent().getRollNumber())
                .orElse(null);
        NotificationEntity notificationEntity = modelMapper.map(notificationDto, NotificationEntity.class);
        if (student != null)
            notificationEntity.setStudent(student);
        return notificationEntity;
    }

    private NotificationDto convertEntityToDto(NotificationEntity notificationEntity) {
        if (notificationEntity == null) return null;

        NotificationDto notificationDto = modelMapper.map(notificationEntity, NotificationDto.class);
        if (notificationEntity.getStudent() != null)
            notificationDto.setStudent(modelMapper.map(notificationEntity.getStudent(), StudentDto.class));
        return notificationDto;
    }
}
