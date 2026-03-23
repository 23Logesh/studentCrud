package com.student.studentCrud.service;

import com.student.studentCrud.dto.GradeDto;
import com.student.studentCrud.util.ResponseStructure;

import java.util.List;
import java.util.Map;

public interface GradeService {

    GradeDto saveGrade(long rollNumber, String subject, double score);

    GradeDto updateGrade(GradeDto gradeDto);

    GradeDto deleteGrade(long gradeId);

    List<GradeDto> findAllGrade();

    GradeDto updateScore(long gradeId, double score);

    ResponseStructure<Map<String, Double>> findGradeByStudent(long rollNumber);

}
