package com.student.studentCrud.serviceImp;

import com.student.studentCrud.dto.GradeDto;
import com.student.studentCrud.dto.NotificationDto;
import com.student.studentCrud.dto.StudentDto;
import com.student.studentCrud.entity.GradeEntity;
import com.student.studentCrud.entity.StudentEntity;
import com.student.studentCrud.repository.GradeRepo;
import com.student.studentCrud.service.GradeService;
import com.student.studentCrud.service.NotificationService;
import com.student.studentCrud.service.StudentService;
import com.student.studentCrud.util.ResponseStructure;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GradeServiceImp implements GradeService {

    @Autowired
    private GradeRepo gradeRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    private ResponseStructure<Map<String, Double>> responseStructure;

    @Override
    public GradeDto saveGrade(long rollNumber, String subject, double score) {
        GradeDto gradeDto = new GradeDto();
        gradeDto.setStudent(studentService.findStudent(rollNumber));
        if (gradeDto.getStudent() != null) {
            gradeDto.setSubject(subject);
            gradeDto.setScore(score);
            gradeDto = convertEntityToDto(gradeRepo.save(convertDtoToEntity(gradeDto)));
            calculateGPAAndPerformance(gradeDto.getStudent().getRollNumber());
            updateRankForClass(gradeDto.getStudent().getClassName());
            return gradeDto;
        }
        return null;
    }

    @Override
    public List<GradeDto> findAllGrade() {
        return gradeRepo.findAll().stream().map(this::convertEntityToDto).toList();
    }

    @Override
    public GradeDto updateScore(long gradeId, double score) {
        GradeDto gradeDto = findGrade(gradeId);
        if (gradeDto != null) {
            gradeDto.setScore(score);
            GradeDto updatedGrade = convertEntityToDto(gradeRepo.save(convertDtoToEntity(gradeDto)));
            calculateGPAAndPerformance(gradeDto.getStudent().getRollNumber());
            updateRankForClass(gradeDto.getStudent().getClassName());
            return updatedGrade;
        } else {
            return null;
        }
    }

    @Override
    public ResponseStructure<Map<String, Double>> findGradeByStudent(long rollNumber) {

        List<GradeDto> gradeDtoList = gradeRepo.findByStudentRollNumber(rollNumber)
                .stream().map(this::convertEntityToDto).toList();

        Map<String, Double> reportMap = gradeDtoList.stream()
                .collect(Collectors.toMap(
                        GradeDto::getSubject,
                        GradeDto::getScore
                ));

        return responseStructure.getMapResponseStructure(gradeDtoList.getFirst().getStudent(), reportMap);

    }


    @Override
    public GradeDto updateGrade(GradeDto gradeDto) {
        if (gradeDto == null || gradeDto.getId() == 0) {
            return null;
        }
        if (gradeRepo.existsById(gradeDto.getId())) {
            GradeDto updatedGrade = convertEntityToDto(gradeRepo.save(convertDtoToEntity(gradeDto)));
            calculateGPAAndPerformance(gradeDto.getStudent().getRollNumber());
            updateRankForClass(gradeDto.getStudent().getClassName());
            return updatedGrade;
        } else {
            return null;
        }
    }

    @Override
    public GradeDto deleteGrade(long gradeId) {
        return gradeRepo.findById(gradeId)
                .map(grade -> {
                    gradeRepo.deleteById(gradeId);
                    calculateGPAAndPerformance(grade.getStudent().getRollNumber());
                    updateRankForClass(grade.getStudent().getClassName());
                    return convertEntityToDto(grade);
                })
                .orElse(null);
    }


    public void calculateGPAAndPerformance(Long studentId) {
        List<GradeDto> grades = gradeRepo.findByStudentRollNumber(studentId).stream().map(this::convertEntityToDto).toList();
        StudentDto student = studentService.findStudent(studentId);

        if (grades.isEmpty()) {
            student.setGpa(0.0);
            student.setPerformanceLevel("Needs Improvement");
        } else {
            double total = grades.stream().mapToDouble(GradeDto::getScore).sum();
            double gpa = ((total / grades.size())/100.00)*10.00;
            student.setGpa(gpa);

            if (gpa >= 9) {
                student.setPerformanceLevel("Excellent");
            } else if (gpa >= 7) {
                student.setPerformanceLevel("Good");
            } else if (gpa >= 5) {
                student.setPerformanceLevel("Average");
            } else {
                student.setPerformanceLevel("Needs Improvement");
                NotificationDto notification = new NotificationDto();
                notification.setStudent(student);
                notification.setMessage("❗Grade below 5GPA. ☹️Please take necessary action.");
                notification.setTimestamp(LocalDateTime.now());
                notificationService.saveNotification(notification);
            }
        }

        studentService.updateStudent(student);
    }


    public void updateRankForClass(String className) {
        List<StudentDto> studentsInClass = new ArrayList<>(studentService.findStudentsByClassName(className));

        if ( studentsInClass.isEmpty()) {
            return;
        }

        studentsInClass.sort((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()));

        int rank = 1;

        for (int i = 0; i < studentsInClass.size(); i++) {
            StudentDto currentStudent = studentsInClass.get(i);

            if (i > 0 && currentStudent.getGpa() == (studentsInClass.get(i - 1).getGpa())) {
                currentStudent.setRank(studentsInClass.get(i - 1).getRank());
            } else {
                currentStudent.setRank(rank);
                rank++;
            }
        }
        studentsInClass.forEach(studentService::updateStudent);
    }

    public GradeDto findGrade(long gradeId) {
        Optional<GradeEntity> gradeOpt = gradeRepo.findById(gradeId);
        return gradeOpt.map(this::convertEntityToDto)
                .orElse(null);
    }

    private GradeDto convertEntityToDto(GradeEntity gradeEntity) {
        StudentDto studentDto = modelMapper.map(gradeEntity.getStudent(), StudentDto.class);
        GradeDto gradeDto = modelMapper.map(gradeEntity, GradeDto.class);
        gradeDto.setStudent(studentDto);
        return gradeDto;
    }

    private GradeEntity convertDtoToEntity(GradeDto gradeDto) {
        StudentEntity studentEntity = modelMapper.map(gradeDto.getStudent(), StudentEntity.class);
        GradeEntity gradeEntity = modelMapper.map(gradeDto, GradeEntity.class);
        gradeEntity.setStudent(studentEntity);
        return gradeEntity;
    }
}
