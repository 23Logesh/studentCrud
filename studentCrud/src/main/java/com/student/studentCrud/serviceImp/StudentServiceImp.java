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
    public ResponseEntity<Student> saveStudent(Student student) {

        Student savedStudent = studentRepo.save(student);
        log.info("[saveStudent] SUCCESS - Student saved with ID: {}, Name: {}", savedStudent.getStudentId(), savedStudent.getStudentName());
        return ResponseEntity.ok(savedStudent);
    }

    @Override
    public ResponseEntity<Student> findStudent(int studentId) {
        Optional<Student> studentOpt = studentRepo.findById(studentId);
        if (studentOpt.isPresent()) {
            log.info("[findStudent] SUCCESS - Found Student with ID: {}", studentId);
            return ResponseEntity.ok(studentOpt.get());
        } else {
            log.warn("[findStudent] FAILED - No Student found with ID: {}", studentId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    public ResponseEntity<List<Student>> findAll() {
        List<Student> students = studentRepo.findAll();
        log.info("[findAll] SUCCESS - Retrieved {} students from database", students.size());
        return ResponseEntity.ok(students);
    }

    @Override
    public ResponseEntity<Student> findByPhoneNo(String studentPhoneNo) {
        Student student = studentRepo.findByStudentPhoneNo(studentPhoneNo);
        if (student != null) {
            log.info("[findByPhoneNo] SUCCESS - Found Student with Phone Number: {}", studentPhoneNo);
            return ResponseEntity.ok(student);
        } else {
            log.warn("[findByPhoneNo] FAILED - No Student found with Phone Number: {}", studentPhoneNo);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    public ResponseEntity<Student> updateStudent(Student student) {
        if (student == null || student.getStudentId() == 0) {
            log.warn("[updateStudent] FAILED - Invalid student data provided");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (studentRepo.existsById(student.getStudentId())) {
            Student updatedStudent = studentRepo.save(student);
            log.info("[updateStudent] SUCCESS - Updated Student ID: {}, Name: {}", updatedStudent.getStudentId(), updatedStudent.getStudentName());
            return ResponseEntity.ok(updatedStudent);
        } else {
            log.warn("[updateStudent] FAILED - No Student found with ID: {} for update", student.getStudentId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    public ResponseEntity<Student> updateStudentName(int studentId, String studentName) {
        return studentRepo.findById(studentId)
                .map(student -> {
                    student.setStudentName(studentName);
                    Student updatedStudent = studentRepo.save(student);
                    log.info("[updateStudentName] SUCCESS - Student ID: {} Name changed to: {}", studentId, studentName);
                    return ResponseEntity.ok(updatedStudent);
                })
                .orElseGet(() -> {
                    log.warn("[updateStudentName] FAILED - No Student found with ID: {} for name update", studentId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                });
    }

    @Override
    public ResponseEntity<Student> deleteStudent(int studentId) {
        return studentRepo.findById(studentId)
                .map(student -> {
                    studentRepo.deleteById(studentId);
                    log.info("[deleteStudent] SUCCESS - Deleted Student with ID: {}", studentId);
                    return ResponseEntity.ok(student);
                })
                .orElseGet(() -> {
                    log.warn("[deleteStudent] FAILED - No Student found with ID: {} for deletion", studentId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                });
    }
}
