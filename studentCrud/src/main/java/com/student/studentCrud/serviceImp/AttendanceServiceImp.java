package com.student.studentCrud.serviceImp;

import com.student.studentCrud.entity.AttendanceEntity;
import com.student.studentCrud.entity.StudentEntity;
import com.student.studentCrud.dto.AttendanceDto;
import com.student.studentCrud.dto.NotificationDto;
import com.student.studentCrud.dto.StudentDto;
import com.student.studentCrud.repository.AttendanceRepo;
import com.student.studentCrud.service.AttendanceService;
import com.student.studentCrud.service.NotificationService;
import com.student.studentCrud.service.StudentService;
import com.student.studentCrud.util.AttendanceStatus;
import com.student.studentCrud.util.ResponseStructure;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AttendanceServiceImp implements AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NotificationService notificationService;

    private StudentService studentService;



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
        StudentDto studentDto = studentService.findStudent(studentId);

        if (studentDto != null) {
            studentDto.setAttendancePercentage(percentage);

            studentService.saveStudent(studentDto);

            // Send notification if below 75%
            if (percentage < 75.0) {
                NotificationDto notification = new NotificationDto();
                notification.setStudent(studentDto); // Already in DTO format
                notification.setMessage("⚠️ Attendance below 75%. Please take necessary action.");
                notification.setTimestamp(LocalDateTime.now());
                notificationService.saveNotification(notification);
            }
        }
    }


    @Override
    public ResponseStructure<Map<LocalDate, AttendanceStatus>> getMonthlyAttendanceReport(long rollNumber, int month, int year) {
        // Fetch student DTO (avoid using entity)
        StudentDto studentDto = studentService.findStudent(rollNumber); // You must have this method already

        // Start and end date of month
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year, month, startDate.toLocalDate().lengthOfMonth(), 23, 59);

        // Fetch attendance records
        List<AttendanceEntity> monthlyAttendance = attendanceRepository
                .findByStudentRollNumberAndDateBetween(rollNumber, startDate, endDate);

        // Convert to Map<LocalDate, AttendanceStatus>
        Map<LocalDate, AttendanceStatus> reportMap = monthlyAttendance.stream()
                .collect(Collectors.toMap(
                        AttendanceEntity::getDate,
                        AttendanceEntity::getStatus
                ));

        // Prepare message from studentDto
        String message = "Student Details: " +
                "Roll No: " + studentDto.getRollNumber() +
                ", Name: " + studentDto.getName() +
                ", Email: " + studentDto.getEmail() +
                ", Class: " + studentDto.getClassName() +
                ", GPA: " + studentDto.getGpa() +
                ", Performance: " + studentDto.getPerformanceLevel() +
                ", Rank: " + studentDto.getRank();

        // Wrap result into ResponseStructure
        ResponseStructure<Map<LocalDate, AttendanceStatus>> response = new ResponseStructure<>();
        response.setData(reportMap);
        response.setMessage(message);
        response.setStatus(HttpStatus.OK.value());

        return response;
    }



    private AttendanceEntity convertDtoToEntity(AttendanceDto attendanceDto) {
        StudentEntity student = modelMapper.map(attendanceDto.getStudent(),StudentEntity.class);

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
