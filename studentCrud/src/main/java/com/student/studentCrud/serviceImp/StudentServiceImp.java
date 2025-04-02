package com.student.studentCrud.serviceImp;

import com.student.studentCrud.Entity.Student_Entity;
import com.student.studentCrud.dto.Student_Dto;
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


    private final ModelMapper modelMapper= new ModelMapper();

    @Override
    public Student_Dto saveStudent(Student_Dto studentDto) {

        Student_Dto savedStudent = convertEntityToDto(studentRepo.save(convertDtoToEntity(studentDto)));
        log.info("[saveStudent] SUCCESS - Student saved with ID: {}, Name: {}", savedStudent.getStudentId(), savedStudent.getStudentName());
        return savedStudent;
    }

    @Override
    public Student_Dto findStudent(int studentId) {
        Optional<Student_Entity> studentOpt = studentRepo.findById(studentId);
        log.info("[findStudent] SUCCESS - Found Student with ID: {}", studentId);
        return studentOpt.map(Student_Entity -> modelMapper.map(Student_Entity, Student_Dto.class))
                .orElseGet(() -> {
                    log.warn("[findStudent] FAILED - No Student found with ID: {}", studentId);
                    return null;
                });
    }

    @Override
    public List<Student_Dto> findAll() {
        List<Student_Dto> students = studentRepo.findAll().stream().map(this::convertEntityToDto).toList();
        log.info("[findAll] SUCCESS - Retrieved {} students from database", students.size());
        return students;
    }

    @Override
    public Student_Dto findByPhoneNo(String studentPhoneNo) {
        Student_Dto studentDto = convertEntityToDto(studentRepo.findByStudentPhoneNo(studentPhoneNo));
        if (studentDto != null) {
            log.info("[findByPhoneNo] SUCCESS - Found Student with Phone Number: {}", studentPhoneNo);
            return studentDto;
        } else {
            log.warn("[findByPhoneNo] FAILED - No Student found with Phone Number: {}", studentPhoneNo);
            return null;
        }
    }

    @Override
    public Student_Dto updateStudent(Student_Dto studentDto) {
        if (studentDto == null || studentDto.getStudentId() == 0) {
            log.warn("[updateStudent] FAILED - Invalid student data provided");
            return null;
        }

        if (studentRepo.existsById(studentDto.getStudentId())) {
            Student_Dto updatedStudent = convertEntityToDto(studentRepo.save(convertDtoToEntity(studentDto)));
            log.info("[updateStudent] SUCCESS - Updated Student ID: {}, Name: {}", updatedStudent.getStudentId(), updatedStudent.getStudentName());
            return updatedStudent;
        } else {
            log.warn("[updateStudent] FAILED - No Student found with ID: {} for update", studentDto.getStudentId());
            return null;
        }
    }

    @Override
    public Student_Dto updateStudentName(int studentId, String studentName) {
        return Optional.ofNullable(findStudent(studentId))
                .map(student -> {
                    student.setStudentName(studentName);
                    Student_Dto updatedStudent = convertEntityToDto(studentRepo.save(convertDtoToEntity(student)));
                    log.info("[updateStudentName] SUCCESS - Student ID: {} Name changed to: {}", studentId, studentName);
                    return updatedStudent;
                })
                .orElseGet(() -> {
                    log.warn("[updateStudentName] FAILED - No Student found with ID: {} for name update", studentId);
                    return null;
                });
    }

    @Override
    public Student_Dto deleteStudent(int studentId) {
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

    public Student_Dto convertEntityToDto(Student_Entity studentEntity) {
        return modelMapper.map(studentEntity, Student_Dto.class);
    }

    public Student_Entity convertDtoToEntity(Student_Dto studentDto) {
        return modelMapper.map(studentDto, Student_Entity.class);
    }

}
