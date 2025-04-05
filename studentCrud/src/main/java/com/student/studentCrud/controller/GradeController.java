package com.student.studentCrud.controller;

import com.student.studentCrud.dto.GradeDto;
import com.student.studentCrud.service.GradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @PostMapping("/save")
    public ResponseEntity<GradeDto> saveGrade(@RequestBody GradeDto gradeDto) {
        log.info("[saveGrade] Received API request to save grade: {}", gradeDto);
        ResponseEntity<GradeDto> response = ResponseEntity.ok(gradeService.saveGrade(gradeDto));
        log.info("[saveGrade] Response: {}", response.getStatusCode());
        return response;
    }

    @GetMapping("/findById")
    public ResponseEntity<GradeDto> getGrade(@RequestParam long gradeId) {
        log.info("[getGrade] Received API request to find grade with ID: {}", gradeId);
        ResponseEntity<GradeDto> response = ResponseEntity.ok(gradeService.findGrade(gradeId));
        log.info("[getGrade] Response: {}", response.getStatusCode());
        return response;
    }

    @GetMapping("/student/{rollNumber}")
    public ResponseEntity<List<GradeDto>> getAttendanceByStudent(@PathVariable long rollNumber) {
        log.info("Fetching Grade records for student ID: {}", rollNumber);
        return ResponseEntity.ok(gradeService.findGradeByStudent(rollNumber));
    }


    @GetMapping("/findAll")
    public ResponseEntity<List<GradeDto>> getAllGrades() {
        log.info("[getAllGrades] Received API request to retrieve all grades");
        ResponseEntity<List<GradeDto>> response = ResponseEntity.ok(gradeService.findAllGrade());
        List<GradeDto> grades = response.getBody();
        int gradeCount = (grades != null) ? grades.size() : 0;
        log.info("[getAllGrades] Found {} grades. Response: {}", gradeCount, response.getStatusCode());
        return response;
    }

    @PutMapping("/update")
    public ResponseEntity<GradeDto> updateGrade(@RequestBody GradeDto gradeDto) {
        log.info("[updateGrade] Received API request to update grade: {}", gradeDto);
        ResponseEntity<GradeDto> response = ResponseEntity.ok(gradeService.updateGrade(gradeDto));
        log.info("[updateGrade] Response: {}", response.getStatusCode());
        return response;
    }

    @PutMapping("/update/score")
    public ResponseEntity<GradeDto> updateScore(@RequestParam long gradeId, @RequestParam double score) {
        log.info("[updateScore] Received API request to update score: {} for gradeId: {}", score, gradeId);
        ResponseEntity<GradeDto> response = ResponseEntity.ok(gradeService.updateScore(gradeId, score));
        log.info("[updateScore] Response: {}", response.getStatusCode());
        return response;
    }

    @DeleteMapping("/delete/{gradeId}")
    public ResponseEntity<GradeDto> deleteGrade(@PathVariable long gradeId) {
        log.info("[deleteGrade] Received API request to delete grade with ID: {}", gradeId);
        ResponseEntity<GradeDto> response = ResponseEntity.ok(gradeService.deleteGrade(gradeId));
        log.info("[deleteGrade] Response: {}", response.getStatusCode());
        return response;
    }
}
