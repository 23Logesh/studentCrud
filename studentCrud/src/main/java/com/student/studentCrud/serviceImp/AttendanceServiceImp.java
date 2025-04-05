package com.student.studentCrud.serviceImp;

import com.student.studentCrud.Entity.AttendanceEntity;
import com.student.studentCrud.Entity.StudentEntity;
import com.student.studentCrud.dto.AttendanceDto;
import com.student.studentCrud.dto.NotificationDto;
import com.student.studentCrud.dto.StudentDto;
import com.student.studentCrud.repository.AttendanceRepo;
import com.student.studentCrud.repository.StudentRepo;
import com.student.studentCrud.service.AttendanceService;
import com.student.studentCrud.service.NotificationService;
import com.student.studentCrud.util.AttendanceStatus;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AttendanceServiceImp implements AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepository;

    @Autowired
    private StudentRepo studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    private NotificationService notificationService;

    @Override
    public AttendanceDto saveAttendance(AttendanceDto attendanceDto) {
        AttendanceEntity attendance = convertDtoToEntity(attendanceDto);
        AttendanceEntity savedAttendance = attendanceRepository.save(attendance);
        log.info("Attendance saved for Student ID: {}, Date: {}, Status: {}",
                savedAttendance.getStudent().getRollNumber(), savedAttendance.getDate(), savedAttendance.getStatus());

        calculateAttendancePercentageForStudent(attendanceDto.getStudent().getRollNumber());
        return convertEntityToDto(savedAttendance);
    }


    @Override
    public List<AttendanceDto> findAttendanceByStudent(long rollNumber) {
        return attendanceRepository.findByStudentRollNumber(rollNumber)
                .stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    public AttendanceDto updateAttendanceStatus(long id, String status) {
        AttendanceEntity attendance = getAttendanceById(id);
        attendance.setStatus(AttendanceStatus.valueOf(status));
        calculateAttendancePercentageForStudent(attendance.getStudent().getRollNumber());
        return convertEntityToDto(attendanceRepository.save(attendance));
    }

    @Override
    public AttendanceDto deleteAttendance(long id) {
        AttendanceEntity attendance = getAttendanceById(id);
        attendanceRepository.delete(attendance);
        log.info("Deleted Attendance ID: {}", id);
        calculateAttendancePercentageForStudent(attendance.getStudent().getRollNumber());
        return convertEntityToDto(attendance);
    }

    @Override
    public List<AttendanceDto> findAllAttendances() {
        return attendanceRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    public AttendanceDto findAttendance(long attendanceId) {
        return convertEntityToDto(getAttendanceById(attendanceId));
    }

    @Override
    public AttendanceDto updateAttendance(AttendanceDto attendanceDto) {
        if (attendanceRepository.existsById(attendanceDto.getId())) {
            AttendanceEntity updatedAttendance = attendanceRepository.save(convertDtoToEntity(attendanceDto));
            calculateAttendancePercentageForStudent(attendanceDto.getStudent().getRollNumber());
            return convertEntityToDto(updatedAttendance);
        }
        return null;
    }
    private AttendanceEntity getAttendanceById(long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with ID: " + id));
    }

    public void calculateAttendancePercentageForStudent(Long studentId) {
        List<AttendanceEntity> attendanceList = attendanceRepository.findByStudentRollNumber(studentId);

        int totalDays = attendanceList.size();
        if (totalDays == 0) {
            updatePercentageInStudent(studentId, 0.0);
            return;
        }

        long presentDays = attendanceList.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                .count();

        double percentage = (presentDays * 100.0) / totalDays;
        updatePercentageInStudent(studentId, percentage);
    }


    private void updatePercentageInStudent(Long studentId, double percentage) {
        studentRepository.findById(studentId).ifPresent(student -> {
            student.setAttendancePercentage(percentage);
            studentRepository.save(student);

            if (percentage < 75.0) {
                NotificationDto notification = new NotificationDto();
                notification.setStudent(modelMapper.map(student, StudentDto.class));
                notification.setMessage("⚠️ Attendance below 75%. Please take necessary action.");
                notification.setTimestamp(LocalDateTime.now());
                notificationService.saveNotification(notification);
            }
        });
    }


    private AttendanceEntity convertDtoToEntity(AttendanceDto attendanceDto) {
        StudentEntity student = studentRepository.findById(attendanceDto.getStudent().getRollNumber())
                .orElseThrow(() -> new RuntimeException("Student not found with Roll Number: " + attendanceDto.getStudent().getRollNumber()));

        AttendanceEntity attendanceEntity = modelMapper.map(attendanceDto, AttendanceEntity.class);
        attendanceEntity.setStudent(student);
        attendanceEntity.setStatus(attendanceDto.getStatus());
        return attendanceEntity;
    }

    private AttendanceDto convertEntityToDto(AttendanceEntity attendanceEntity) {
        AttendanceDto attendanceDto = modelMapper.map(attendanceEntity, AttendanceDto.class);
        if (attendanceEntity.getStudent() != null) {
            attendanceDto.setStudent(modelMapper.map(attendanceEntity.getStudent(), StudentDto.class));
        }
        return attendanceDto;
    }

}
