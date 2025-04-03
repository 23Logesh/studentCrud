package com.student.studentCrud.serviceImp;

import com.student.studentCrud.Entity.StudentEntity;
import com.student.studentCrud.dto.StudentDto;
import com.student.studentCrud.repository.StudentRepo;
import com.student.studentCrud.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentServiceImp implements StudentService {


    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StudentDto saveStudent(StudentDto studentDto) {

        StudentDto savedStudent = convertEntityToDto(studentRepo.save(convertDtoToEntity(studentDto)));
        log.info("[saveStudent] SUCCESS - Student saved with ID: {}, Name: {}", savedStudent.getStudentId(), savedStudent.getStudentName());
        return savedStudent;
    }

    @Override
    public StudentDto findStudent(int studentId) {
        Optional<StudentEntity> studentOpt = studentRepo.findById(studentId);
        log.info("[findStudent] SUCCESS - Found Student with ID: {}", studentId);
        return studentOpt.map(StudentEntity -> modelMapper.map(StudentEntity, StudentDto.class))
                .orElseGet(() -> {
                    log.warn("[findStudent] FAILED - No Student found with ID: {}", studentId);
                    return null;
                });
    }

    @Override
    public List<StudentDto> findAll() {
        List<StudentDto> students = studentRepo.findAll().stream().map(this::convertEntityToDto).toList();
        log.info("[findAll] SUCCESS - Retrieved {} students from database", students.size());
        return students;
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto) {
        if (studentDto == null || studentDto.getStudentId() == 0) {
            log.warn("[updateStudent] FAILED - Invalid student data provided");
            return null;
        }

        if (studentRepo.existsById(studentDto.getStudentId())) {
            StudentDto updatedStudent = convertEntityToDto(studentRepo.save(convertDtoToEntity(studentDto)));
            log.info("[updateStudent] SUCCESS - Updated Student ID: {}, Name: {}", updatedStudent.getStudentId(), updatedStudent.getStudentName());
            return updatedStudent;
        } else {
            log.warn("[updateStudent] FAILED - No Student found with ID: {} for update", studentDto.getStudentId());
            return null;
        }
    }

    @Override
    public StudentDto updateStudentName(int studentId, String studentName) {
        return Optional.ofNullable(findStudent(studentId))
                .map(student -> {
                    student.setStudentName(studentName);
                    StudentDto updatedStudent = convertEntityToDto(studentRepo.save(convertDtoToEntity(student)));
                    log.info("[updateStudentName] SUCCESS - Student ID: {} Name changed to: {}", studentId, studentName);
                    return updatedStudent;
                })
                .orElseGet(() -> {
                    log.warn("[updateStudentName] FAILED - No Student found with ID: {} for name update", studentId);
                    return null;
                });
    }

    @Override
    public StudentDto deleteStudent(int studentId) {
        return studentRepo.findById(studentId)
                .map(student -> {
                    studentRepo.deleteById(studentId);
                    log.info("[deleteStudent] SUCCESS - Deleted Student with ID: {}", studentId);
                    return convertEntityToDto(student);
                })
                .orElseGet(() -> {
                    log.warn("[deleteStudent] FAILED - No Student found with ID: {} for deletion", studentId);
                    return null;
                });
    }

    public StudentDto convertEntityToDto(StudentEntity studentEntity) {
        return modelMapper.map(studentEntity, StudentDto.class);
    }

    public StudentEntity convertDtoToEntity(StudentDto studentDto) {
        return modelMapper.map(studentDto, StudentEntity.class);
    }

}
