package com.student.studentCrud.serviceImp;

import com.student.studentCrud.dto.AttendanceDto;
import com.student.studentCrud.dto.NotificationDto;
import com.student.studentCrud.dto.StudentDto;
import com.student.studentCrud.entity.AttendanceEntity;
import com.student.studentCrud.entity.StudentEntity;
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

    @Autowired
    private StudentService studentService;


    @Override
    public AttendanceDto markAttendance(long rollNumber, LocalDate date, AttendanceStatus status) {
        AttendanceDto attendanceDto = new AttendanceDto();
        attendanceDto.setDate(date);
        attendanceDto.setStatus(status);
        attendanceDto.setStudent(studentService.findStudent(rollNumber));
        attendanceDto = convertEntityToDto(attendanceRepository.save(convertDtoToEntity(attendanceDto)));
        calculateAttendancePercentageForStudent(attendanceDto.getStudent().getRollNumber());
        return attendanceDto;
    }

    @Override
    public ResponseStructure<Map<LocalDate, AttendanceStatus>> getAttendanceForStudent(long rollNumber) {
        List<AttendanceDto> attendanceDtoList = attendanceRepository.findByStudentRollNumber(rollNumber)
                .stream()
                .map(this::convertEntityToDto)
                .toList();

        Map<LocalDate, AttendanceStatus> reportMap = attendanceDtoList.stream()
                .collect(Collectors.toMap(
                        AttendanceDto::getDate,
                        AttendanceDto::getStatus
                ));

        return getMapResponseStructure(attendanceDtoList.getFirst().getStudent(), reportMap);
    }

    @Override
    public AttendanceDto updateAttendanceStatus(long id, String status) {
        AttendanceEntity attendance = getAttendanceById(id);
        if (attendance != null) {
            AttendanceDto attendanceDto = convertEntityToDto(attendance);
            attendanceDto.setStatus(AttendanceStatus.valueOf(status));
            attendanceDto = convertEntityToDto(attendanceRepository.save(convertDtoToEntity(attendanceDto)));
            calculateAttendancePercentageForStudent(attendanceDto.getStudent().getRollNumber());
            return attendanceDto;
        }
        return null;
    }

    @Override
    public AttendanceDto deleteAttendance(long id) {
        AttendanceEntity attendance = getAttendanceById(id);
        if (attendance != null) {
            attendanceRepository.delete(attendance);
            log.info("Deleted Attendance ID: {}", id);
            calculateAttendancePercentageForStudent(attendance.getStudent().getRollNumber());
            return convertEntityToDto(attendance);
        }
        return null;
    }

    @Override
    public List<AttendanceDto> findAllAttendances() {
        return attendanceRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .toList();
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

    @Override
    public ResponseStructure<Map<LocalDate, AttendanceStatus>> getMonthlyAttendanceReport(long rollNumber, int month, int year) {

        StudentDto studentDto = studentService.findStudent(rollNumber);

        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year, month, startDate.toLocalDate().lengthOfMonth(), 23, 59);

        List<AttendanceDto> monthlyAttendance = attendanceRepository
                .findByStudentRollNumberAndDateBetween(rollNumber, startDate, endDate)
                .stream().map(this::convertEntityToDto).toList();

        Map<LocalDate, AttendanceStatus> reportMap = monthlyAttendance.stream()
                .collect(Collectors.toMap(
                        AttendanceDto::getDate,
                        AttendanceDto::getStatus
                ));

        return getMapResponseStructure(studentDto, reportMap);

    }

    private AttendanceEntity getAttendanceById(long id) {
        return attendanceRepository.findById(id)
                .orElse(null);
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

            if (percentage < 75.0) {
                NotificationDto notification = new NotificationDto();
                notification.setStudent(studentDto);
                notification.setMessage("⚠️ Attendance below 75%. Please take necessary action.");
                notification.setTimestamp(LocalDateTime.now());
                notificationService.saveNotification(notification);
            }
        }
    }


    private ResponseStructure<Map<LocalDate, AttendanceStatus>> getMapResponseStructure(StudentDto studentDto, Map<LocalDate, AttendanceStatus> reportMap) {

        String message = "Student Details: " +
                "\nRoll No: " + studentDto.getRollNumber() +
                ",\n Name: " + studentDto.getName() +
                ",\n Email: " + studentDto.getEmail() +
                ",\n Class: " + studentDto.getClassName() +
                ",\n GPA: " + studentDto.getGpa() +
                ",\n Performance: " + studentDto.getPerformanceLevel() +
                ",\n Rank: " + studentDto.getRank();


        ResponseStructure<Map<LocalDate, AttendanceStatus>> response = new ResponseStructure<>();
        response.setData(reportMap);
        response.setMessage(message);
        response.setStatus(HttpStatus.OK.value());
        return response;
    }

    private AttendanceEntity convertDtoToEntity(AttendanceDto attendanceDto) {

        StudentEntity student = modelMapper.map(attendanceDto.getStudent(), StudentEntity.class);

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
