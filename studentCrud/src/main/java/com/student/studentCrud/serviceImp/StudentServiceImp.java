package com.student.studentCrud.serviceImp;

import com.student.studentCrud.dto.StudentDto;
import com.student.studentCrud.entity.StudentEntity;
import com.student.studentCrud.repository.StudentRepo;
import com.student.studentCrud.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
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
        log.info("[saveStudent] SUCCESS - Student saved with ID: {}, Name: {}", savedStudent.getRollNumber(), savedStudent.getName());
        return savedStudent;
    }
    @Override
    public StudentDto saveStudent(String name, String email, String className) {
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name.trim());
        studentDto.setEmail(email.trim());
        studentDto.setClassName(className.trim().toLowerCase(Locale.ROOT));
        return saveStudent(studentDto);
    }

    @Override
    public StudentDto findStudent(long rollNumber) {
        Optional<StudentEntity> studentOpt = studentRepo.findById(rollNumber);
        log.info("[findStudent] SUCCESS - Found Student with ID: {}", rollNumber);
        return studentOpt.map(StudentEntity -> modelMapper.map(StudentEntity, StudentDto.class))
                .orElseGet(() -> {
                    log.warn("[findStudent] FAILED - No Student found with ID: {}", rollNumber);
                    return null;
                });
    }

    @Override
    public List<StudentDto> findAllStudent() {
        List<StudentDto> students = studentRepo.findAll().stream().map(this::convertEntityToDto).toList();
        log.info("[findAll] SUCCESS - Retrieved {} students from database", students.size());
        return students;
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto) {
        if (studentDto == null || studentDto.getRollNumber() == 0) {
            log.warn("[updateStudent] FAILED - Invalid student data provided");
            return null;
        }

        if (studentRepo.existsById(studentDto.getRollNumber())) {
            StudentDto updatedStudent = convertEntityToDto(studentRepo.save(convertDtoToEntity(studentDto)));
            log.info("[updateStudent] SUCCESS - Updated Student ID: {}, Name: {}", updatedStudent.getRollNumber(), updatedStudent.getName());
            return updatedStudent;
        } else {
            log.warn("[updateStudent] FAILED - No Student found with ID: {} for update", studentDto.getRollNumber());
            return null;
        }
    }

    @Override
    public StudentDto deleteStudent(long rollNumber) {
        return studentRepo.findById(rollNumber)
                .map(student -> {
                    studentRepo.deleteById(rollNumber);
                    log.info("[deleteStudent] SUCCESS - Deleted Student with ID: {}", rollNumber);
                    return convertEntityToDto(student);
                })
                .orElseGet(() -> {
                    log.warn("[deleteStudent] FAILED - No Student found with ID: {} for deletion", rollNumber);
                    return null;
                });
    }

    public List<StudentDto> findStudentsByClassName(String className) {

        List<StudentDto> studentsInClass = studentRepo.findByClassName(className.trim().toLowerCase(Locale.ROOT)).stream().map(this::convertEntityToDto).toList();
        return studentsInClass.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList();
    }

    @Override
    public List<StudentDto> findTop3Rank(String className) {
        return studentRepo.findByTop3Rank(className.trim().toLowerCase(Locale.ROOT)).stream().map(this::convertEntityToDto).toList();
    }

    public StudentDto convertEntityToDto(StudentEntity studentEntity) {
        return modelMapper.map(studentEntity, StudentDto.class);


    }

    public StudentEntity convertDtoToEntity(StudentDto studentDto) {
        return modelMapper.map(studentDto, StudentEntity.class);
    }

}
