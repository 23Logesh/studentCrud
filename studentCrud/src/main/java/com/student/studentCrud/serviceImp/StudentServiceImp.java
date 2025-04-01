package com.student.studentCrud.serviceImp;

import com.student.studentCrud.dto.Student;
import com.student.studentCrud.repository.StudentRepo;
import com.student.studentCrud.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentServiceImp implements StudentService {


    @Autowired
    private StudentRepo studentRepo;

    @Override
    public Student saveStudent(Student student) {

        Student savedStudent = studentRepo.save(student);
        log.info("[saveStudent] SUCCESS - Student saved with ID: {}, Name: {}", savedStudent.getStudentId(), savedStudent.getStudentName());
        return savedStudent;
    }

    @Override
    public Student findStudent(int studentId) {
        Optional<Student> studentOpt = studentRepo.findById(studentId);
        if (studentOpt.isPresent()) {
            log.info("[findStudent] SUCCESS - Found Student with ID: {}", studentId);
            return studentOpt.get();
        } else {
            log.warn("[findStudent] FAILED - No Student found with ID: {}", studentId);
            return null;
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = studentRepo.findAll();
        log.info("[findAll] SUCCESS - Retrieved {} students from database", students.size());
        return students;
    }

    @Override
    public Student findByPhoneNo(String studentPhoneNo) {
        Student student = studentRepo.findByStudentPhoneNo(studentPhoneNo);
        if (student != null) {
            log.info("[findByPhoneNo] SUCCESS - Found Student with Phone Number: {}", studentPhoneNo);
            return student;
        } else {
            log.warn("[findByPhoneNo] FAILED - No Student found with Phone Number: {}", studentPhoneNo);
            return null;
        }
    }

    @Override
    public Student updateStudent(Student student) {
        if (student == null || student.getStudentId() == 0) {
            log.warn("[updateStudent] FAILED - Invalid student data provided");
            return null;
        }

        if (studentRepo.existsById(student.getStudentId())) {
            Student updatedStudent = studentRepo.save(student);
            log.info("[updateStudent] SUCCESS - Updated Student ID: {}, Name: {}", updatedStudent.getStudentId(), updatedStudent.getStudentName());
            return updatedStudent;
        } else {
            log.warn("[updateStudent] FAILED - No Student found with ID: {} for update", student.getStudentId());
            return null;
        }
    }

    @Override
    public Student updateStudentName(int studentId, String studentName) {
        return Optional.ofNullable(findStudent(studentId))
                .map(student -> {
                    student.setStudentName(studentName);
                    Student updatedStudent = studentRepo.save(student);
                    log.info("[updateStudentName] SUCCESS - Student ID: {} Name changed to: {}", studentId, studentName);
                    return updatedStudent;
                })
                .orElseGet(() -> {
                    log.warn("[updateStudentName] FAILED - No Student found with ID: {} for name update", studentId);
                    return null;
                });
    }

    @Override
    public Student deleteStudent(int studentId) {
        return studentRepo.findById(studentId)
                .map(student -> {
                    studentRepo.deleteById(studentId);
                    log.info("[deleteStudent] SUCCESS - Deleted Student with ID: {}", studentId);
                    return student;
                })
                .orElseGet(() -> {
                    log.warn("[deleteStudent] FAILED - No Student found with ID: {} for deletion", studentId);
                    return null;
                });
    }
}
