package com.student.studentCrud.controller;

import com.student.studentCrud.dto.GradeDto;
import com.student.studentCrud.service.GradeService;
import com.student.studentCrud.util.ResponseStructure;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @PostMapping("/save")
    public ResponseEntity<GradeDto> saveGrade(long rollNumber, @NotBlank String subject,@PositiveOrZero double score) {
        return ResponseEntity.ok(gradeService.saveGrade(rollNumber, subject, score));
    }

    @GetMapping("/student/{rollNumber}")
    public ResponseEntity<ResponseStructure<Map<String, Double>>> getGradeByStudent(@PathVariable long rollNumber) {
        return ResponseEntity.ok(gradeService.findGradeByStudent(rollNumber));
    }


    @GetMapping("/findAll")
    public ResponseEntity<List<GradeDto>> getAllGrades() {
        return ResponseEntity.ok(gradeService.findAllGrade());
    }

    @PutMapping("/update")
    public ResponseEntity<GradeDto> updateGrade(@RequestBody @Valid GradeDto gradeDto) {
        return ResponseEntity.ok(gradeService.updateGrade(gradeDto));
    }

    @PatchMapping("/update/score")
    public ResponseEntity<GradeDto> updateScore(@RequestParam long gradeId, @RequestParam @PositiveOrZero double score) {
        return ResponseEntity.ok(gradeService.updateScore(gradeId, score));
    }

    @DeleteMapping("/delete/{gradeId}")
    public ResponseEntity<GradeDto> deleteGrade(@PathVariable long gradeId) {
        return ResponseEntity.ok(gradeService.deleteGrade(gradeId));
    }
}
